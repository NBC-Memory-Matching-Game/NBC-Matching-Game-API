package com.revature.NBCMMG.datasources.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "users")
public class User {

    private String id;
    @NotBlank(message = "You need to provide a username!")
    private String username;
    @NotBlank(message = "You need to provide a password!")
    private String password;
    private String pfp;
    private int wins;
    private int losses;

    public User(String username, String password, String pfp, int wins, int losses) {
        this.username = username;
        this.password = password;
        this.pfp = pfp;
        this.wins = wins;
        this.losses = losses;
    }

}
