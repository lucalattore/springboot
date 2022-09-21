package com.waveinformatica.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;

    @Column(name = "city")
    private String city;

    @Column(name = "n_stars")
    private Integer numOfStar;

    @Column(name = "v")
    private Integer v;

    @Column(name = "p")
    private Integer p;

    @Column(name = "s")
    private Integer s;

    @Column(name = "rank")
    private Integer rank;

    @OneToMany(mappedBy = "team")
    private final List<Player> players;

    @OneToMany(mappedBy = "teamA")
    private final List<Match> homeMatches;

    @OneToMany(mappedBy = "teamB")
    private final List<Match> visitorMatches;

    public Team() {
        players = new ArrayList<>();
        homeMatches = new ArrayList<>();
        visitorMatches = new ArrayList<>();
    }
}
