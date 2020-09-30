package com.iioannou.oddscheckertest.repository;

import com.iioannou.oddscheckertest.model.entity.BetT;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ioannou
 */
@Repository
public interface BetRepository extends JpaRepository<BetT, Long> {

    Optional<BetT> findByBetTrackingId(Long betTrackingId);
}
