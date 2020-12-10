package com.telekom.demo.infra.rest;


import com.telekom.demo.domain.CallService;
import com.telekom.demo.domain.model.Call;
import com.telekom.demo.infra.rest.request.CallRequest;
import com.telekom.demo.infra.rest.request.DeliveredCallRequest;
import com.telekom.demo.infra.rest.response.CallResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CallController {

    private final CallService callService;

    @PostMapping("/calls")
    @ResponseStatus(HttpStatus.CREATED)
    public CallResponse callNumber(@RequestBody @Valid CallRequest request) {
        Call call = callService.call(request.toModel());

        return CallResponse.from(call);
    }

    @PutMapping("/calls")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveMissedCalls(@RequestHeader(name="Accept-Language", required=false) Locale locale, @RequestBody @Valid DeliveredCallRequest request) {
        callService.approveCalls(request.getCallIdList());
        callService.notifyMissedCallers(request.getCallIdList(), locale);
    }
}
