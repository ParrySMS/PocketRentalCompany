package com.pocket.flyway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userId;
    private String jwtToken;
    private String productCode;

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String jwtToken) {
        this.userId = userId;
        this.jwtToken = jwtToken;
    }
}
