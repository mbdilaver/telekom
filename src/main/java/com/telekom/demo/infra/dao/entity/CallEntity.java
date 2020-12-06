package com.telekom.demo.infra.dao.entity;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.model.NotificationSubscription;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "call")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "call")
@Data
public class CallEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private String destinationNumber;

    @Column(nullable = false)
    private String targetNumber;

    @Column(nullable = false)
    private Boolean isDelivered;

    public static CallEntity from(Call call) {
        CallEntity callEntity = new CallEntity();
        callEntity.setId(call.getId());
        callEntity.setTargetNumber(call.getTargetNumber());
        callEntity.setDestinationNumber(call.getDestinationNumber());
        callEntity.setIsDelivered(Boolean.FALSE);
        return callEntity;
    }

    public static CallNotification toModel(List<CallEntity> callEntities, NotificationSubscription subscription) {
        return CallNotification.builder()
                .targetNumber(subscription.getNumber())
                .callList(callEntities.stream().map(CallEntity::toModel).collect(Collectors.toList()))
                .build();
    }

    public static Calls toModel(List<CallEntity> callEntities) {
        return Calls.builder().callList(
                callEntities.stream()
                .map(callEntity -> callEntity.toModel())
                        .collect(Collectors.toList()))
                .build();
    }

    public Call toModel() {
        return Call.builder()
                .id(id)
                .destinationNumber(destinationNumber)
                .targetNumber(targetNumber)
                .createdDate(createdDate)
                .isDelivered(isDelivered)
                .build();
    }
}
