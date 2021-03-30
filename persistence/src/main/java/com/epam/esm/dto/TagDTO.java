package com.epam.esm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TagDTO {
    private Long id;
    private String name;
    private List<CertificateDTO> certificates;
}
