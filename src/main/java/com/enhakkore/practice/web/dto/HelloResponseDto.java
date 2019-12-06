package com.enhakkore.practice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
//@AllArgsConstructor
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
