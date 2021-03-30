package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.LinkedHashSet;

public class ToDTOConverter {
    public static CertificateDTO convertToCertificateDTO(Certificate certificate) {
        return CertificateDTO.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .duration(certificate.getDuration())
                .price(certificate.getPrice())
                .createDate(certificate.getCreateDate())
                .lastUpdateDate(certificate.getLastUpdateDate())
                .tags(new LinkedHashSet<>())
                .build();
    }

    public static TagDTO convertToTagDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
