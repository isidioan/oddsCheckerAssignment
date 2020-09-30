package com.iioannou.oddscheckertest.model.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author ioannou
 */

@Entity
@Table(name = "BET")
public class BetT {

    @Id
    @GeneratedValue
    private Long id;

    private Long betTrackingId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bet")
    private Set<OddT> odds;

    public BetT() {
        super();
    }

    public Long getId() {
        return id;
    }

    public Long getBetTrackingId() {
        return betTrackingId;
    }

    public void setBetTrackingId(Long betTrackingId) {
        this.betTrackingId = betTrackingId;
    }

    public void addOdd(OddT odd) {
        odd.setBet(this);
        getOdds().add(odd);
    }

    public void setOdds(Set<OddT> odds) {
        this.odds = odds;
    }

    public Set<OddT> getOdds() {

        if (this.odds == null) {
            this.odds = new HashSet<>();
        }
        return odds;
    }


}
