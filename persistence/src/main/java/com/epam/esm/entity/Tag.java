package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag implements Entity {
    private Long id;
    private String name;
}
