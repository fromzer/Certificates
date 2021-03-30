package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
public class Certificate implements Entity{
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;
}
