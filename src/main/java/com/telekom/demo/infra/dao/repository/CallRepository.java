package com.telekom.demo.infra.dao.repository;

import com.telekom.demo.infra.dao.entity.CallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CallRepository extends JpaRepository<CallEntity, Long> {

    List<CallEntity> getCallEntitiesByIsDeliveredFalseAndTargetNumberEquals(String targetNumber);

//    List<CallEntity> getDistinctByDestinationNumberAndIdIn(List<Long> ids);

    @Query(value = "select * from call where id in :ids", nativeQuery = true)
    List<CallEntity> getCallsById(@Param("ids") List<Long> ids);

    @Modifying
    @Query(value = "update call set is_delivered = true where id in :ids", nativeQuery = true)
    void approveCallsById(@Param("ids") List<Long> ids);
}
