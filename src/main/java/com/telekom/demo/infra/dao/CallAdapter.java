package com.telekom.demo.infra.dao;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.model.NotificationSubscription;
import com.telekom.demo.domain.port.CallPort;
import com.telekom.demo.infra.dao.entity.CallEntity;
import com.telekom.demo.infra.dao.repository.CallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallAdapter implements CallPort {

    private final CallRepository callRepository;

    @Override
    public Call makeCall(Call call) {
        CallEntity callEntity = CallEntity.from(call);
        return callRepository.save(callEntity).toModel();
    }

    @Override
    public CallNotification getMissedCalls(NotificationSubscription subscription) {
        List<CallEntity> callEntities = callRepository.getCallEntitiesByIsDeliveredFalseAndTargetNumberEquals(subscription.getNumber());

        return CallEntity.toModel(callEntities, subscription);
    }

    @Override
    @Transactional
    public void approveCalls(List<Long> ids) {
        callRepository.approveCallsById(ids);
    }

    @Override
    public Calls getCallsById(List<Long> callIds) {
        List<CallEntity> calls = callRepository.getCallsById(callIds);

        return CallEntity.toModel(calls);
    }

}
