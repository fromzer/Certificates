package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.stream.Collectors;

public class ToDTOConverter {
    public static CertificateDTO convertToCertificateDTO(Certificate certificate) {
        CertificateDTO certificateDTO = null;
        if (certificate != null) {
            certificateDTO = CertificateDTO.builder()
                    .id(certificate.getId())
                    .name(certificate.getName())
                    .description(certificate.getDescription())
                    .duration(certificate.getDuration())
                    .price(certificate.getPrice())
                    .createDate(certificate.getCreateDate())
                    .lastUpdateDate(certificate.getLastUpdateDate())
                    .build();
                if (certificate.getTags() != null && !certificate.getTags().isEmpty()) {
                    certificateDTO.setTags(certificate.getTags().stream()
                            .map(ToDTOConverter::convertToTagDTO)
                            .collect(Collectors.toSet()));
                }
        }
        return certificateDTO;
    }

    public static TagDTO convertToTagDTO(Tag tag) {
        TagDTO tagDTO = null;
        if (tag != null) {
            tagDTO = TagDTO.builder()
                    .id(tag.getId())
                    .name(tag.getName())
                    .build();
        }
        return tagDTO;
    }
}
