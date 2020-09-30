package com.iioannou.oddscheckertest.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author ioannou
 */
@Entity
@Table(name = "ODD")
public class OddT {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private BetT bet;

    private String value;

    private String userId;

    private LocalDateTime submittedDate;


    public OddT() {
        super();
    }

    public OddT(String value, String userId) {
        this.value = value;
        this.userId = userId;
        this.submittedDate = LocalDateTime.now();
    }


    public BetT getBet() {
        return bet;
    }

    public void setBet(BetT bet) {
        this.bet = bet;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

}
