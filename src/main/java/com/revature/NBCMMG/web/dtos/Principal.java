package com.revature.NBCMMG.web.dtos;

import com.revature.NBCMMG.datasources.models.User;

public class Principal {

    private String id;
    private String username;
    private String pfp;
    private int wins;
    private int losses;

    public Principal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.pfp = user.getPfp();
        this.wins = user.getWins();
        this.losses = user.getLosses();
    }
}
