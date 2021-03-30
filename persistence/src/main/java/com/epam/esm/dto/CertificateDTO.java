package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class CertificateDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;
    private List<TagDTO> tags;
}
