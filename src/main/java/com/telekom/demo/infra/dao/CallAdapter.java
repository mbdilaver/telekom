package com.telekom.demo.infra.dao;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
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
    public CallNotification getMissedCalls(String number) {
        List<CallEntity> callEntities = callRepository.getCallEntitiesByIsDeliveredFalseAndTargetNumberEquals(number);

        return CallEntity.toModel(callEntities, number);
    }

    @Override
    public Calls getCalls(String targetNumber, List<String> destinationNumbers, Boolean isDelivered) {
        List<CallEntity> callEntities = callRepository.getCalls(targetNumber, destinationNumbers, isDelivered);

        return CallEntity.toModel(callEntities);
    }

    @Override
    @Transactional
    public void approveCalls(List<Long> ids) {
        callRepository.approveCallsById(ids);
    }

    @Override
    public Boolean missedCallsExist(String targetNumber, List<String> destinationNumbers) {
        return callRepository.existsByIsDeliveredFalseAndTargetNumberEqualsAndDestinationNumberIn(targetNumber, destinationNumbers);
    }


}
