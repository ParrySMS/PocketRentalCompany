package com.pocket.flyway;

import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.ValidateOutput;
import org.flywaydb.core.api.output.ValidateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Map<String, String> params = getParams(args);
        String url = params.get("url");
        String user = params.get("username");
        String password = params.get("password");
        logger.info("url:" + url);
        migrate(url, user, password);
    }

    private static Map<String, String> getParams(String[] args) {
        Map<String, Map<String, String>> config = new Yaml().load(Main.class.getClassLoader().getResourceAsStream("application.yaml"));
        Map<String, String> databaseConfig = config.get("database");

        Map<String, String> params = new HashMap<>();
        for (String arg : args) {
            int colonIndex = arg.indexOf(":");
            if (colonIndex > 1 && arg.length() > colonIndex + 1) {
                String key = arg.substring(0, colonIndex);
                String value = arg.substring(colonIndex + 1);
                params.put(key, value);
            }
        }

        databaseConfig.forEach((key, value) -> {
            if (StringUtils.isBlank(params.get(key))) {
                params.put(key, value);
            }
        });
        return params;
    }

    private static void migrate(String url, String user, String password) {
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .load();

        /**
         * if you drop the tables to rebuild
         * you may meet error as follow:
         *
         * Exception in thread "main" org.flywaydb.core.api.FlywayException: Found non-empty schema(s) "public" but no schema history table. Use baseline() or set baselineOnMigrate to true to initialize the schema history table.
         *
         * please use this following func to reset the version as 1 to fix it.
         *  run flyway.baseline() before flyway.migrate()
         */

        // ignoreRepeatableMigrations issues. // https://github.com/flyway/flyway/issues/3121
        // This feature is not free. So we validateWithResult manually.
        ValidateResult validateResult = flyway.validateWithResult();
        if (!validateResult.validationSuccessful) {
            tryRepairWhenAppliedRepeatableMigrationChangedHistory(flyway, validateResult);
        }

        flyway.migrate();
    }

    private static void tryRepairWhenAppliedRepeatableMigrationChangedHistory(Flyway flyway, ValidateResult validateResult) {
        logger.info("tryRepairWhenAppliedRepeatableMigrationChangedHistory");
        assert (!validateResult.invalidMigrations.isEmpty());
        boolean needToBeRepaired = false;
        for (ValidateOutput validateOutput : validateResult.invalidMigrations) {
            switch (validateOutput.errorDetails.errorCode) {
                case OUTDATED_REPEATABLE_MIGRATION:
                case RESOLVED_REPEATABLE_MIGRATION_NOT_APPLIED:
                case RESOLVED_VERSIONED_MIGRATION_NOT_APPLIED:
                    logger.info("Pending migration. version:{}, description:{}",
                            validateOutput.version,
                            validateOutput.description);
                    break;
                case APPLIED_REPEATABLE_MIGRATION_NOT_RESOLVED:
                    logger.warn("Applied repeatable migration has been changed history. version:{}, description:{}",
                            validateOutput.version,
                            validateOutput.description);
                    needToBeRepaired = true;
                    break;
                default:
                    logger.info("version:{}, description:{}, errorMessage:{}",
                            validateOutput.version,
                            validateOutput.description,
                            validateOutput.errorDetails.errorMessage);
            }
        } // end for

        if (needToBeRepaired) {
            logger.warn("Run flyway.repair() for APPLIED_REPEATABLE_MIGRATION_NOT_RESOLVED");
            logger.warn("Remove any failed migrations on databases without DDL transactions (User objects left behind must still be cleaned up manually)");
            logger.warn("Realign the checksums, descriptions and types of the applied migrations with the ones of the available migrations");
            flyway.repair();
        }
    }
}
