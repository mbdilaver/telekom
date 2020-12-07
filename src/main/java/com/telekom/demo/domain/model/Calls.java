package com.telekom.demo.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Calls {
    private final List<Call> callList;
}
