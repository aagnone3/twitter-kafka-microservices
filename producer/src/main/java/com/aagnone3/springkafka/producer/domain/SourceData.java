package com.aagnone3.springkafka.producer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SourceData {
    @NonNull
    private Long id;
    @NonNull
    private String description;
    @NonNull
    private Boolean completed;
}
