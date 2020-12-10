package com.telekom.demo.domain;

import com.telekom.demo.domain.common.CallBusinessException;
import com.telekom.demo.domain.common.ExceptionType;
import com.telekom.demo.domain.port.CallPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallValidator {
    private final CallPort callPort;

    public void validateMissedCallsExist(String targetNumber, List<String> destinationNumbers) {
        Boolean exists = callPort.missedCallsExist(targetNumber, destinationNumbers);

        if (!exists) {
            throw new CallBusinessException(ExceptionType.NO_MISSED_CALLS);
        }
    }
}
