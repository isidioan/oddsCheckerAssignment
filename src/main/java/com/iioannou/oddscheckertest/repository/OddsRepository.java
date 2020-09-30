package com.iioannou.oddscheckertest.repository;

import com.iioannou.oddscheckertest.model.entity.OddT;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ioannou
 */
@Repository
public interface OddsRepository extends JpaRepository<OddT, Long> {

    @Query("select t from OddT t join fetch t.bet bet where bet.betTrackingId = ?1 order by t.submittedDate desc ")
    List<OddT> findAllOddsByBetTrackingIdOrderBySubmittedDateDesc(Long betId);
}
