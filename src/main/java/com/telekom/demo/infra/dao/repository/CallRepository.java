package com.telekom.demo.infra.dao.repository;

import com.telekom.demo.infra.dao.entity.CallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CallRepository extends JpaRepository<CallEntity, Long> {

    List<CallEntity> getCallEntitiesByIsDeliveredFalseAndTargetNumberEquals(String targetNumber);

    Boolean existsByIsDeliveredFalseAndTargetNumberEqualsAndDestinationNumberIn(String targetNumber, List<String> destinationNumbers);

    @Modifying
    @Query(value = "update call set is_delivered = true where id in :ids", nativeQuery = true)
    void approveCallsById(@Param("ids") List<Long> ids);

    @Query(value = "select * from call where is_delivered = :isDelivered and target_number = :targetNumber and destination_number in :destinationNumbers", nativeQuery = true)
    List<CallEntity> getCalls(@Param("targetNumber") String targetNumber,
                                @Param("destinationNumbers") List<String> destinationNumbers,
                                @Param("isDelivered") Boolean isDelivered);
}
