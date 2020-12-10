package com.telekom.demo.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@ToString
public class Number {

    @Size(min = 9, max = 10)
    private final String number;
}
