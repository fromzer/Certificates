package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.LinkedHashSet;
import java.util.Set;

public class ToEntityConverter {
    public static Certificate convertDTOToCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = Certificate.builder()
                .id(certificateDTO.getId())
                .name(certificateDTO.getName())
                .description(certificateDTO.getDescription())
                .price(certificateDTO.getPrice())
                .duration(certificateDTO.getDuration())
                .createDate(certificateDTO.getCreateDate())
                .lastUpdateDate(certificateDTO.getLastUpdateDate())
                .build();
        if (!certificateDTO.getTags().isEmpty()) {
            Set<Tag> tags = new LinkedHashSet<>();
            for (TagDTO tagDTO : certificateDTO.getTags()) {
                tags.add(Tag.builder()
                        .id(tagDTO.getId())
                        .name(tagDTO.getName())
                        .build());
                certificate.setTags(tags);
            }
        }
        return certificate;
    }

    public static Tag convertDTOToTag(TagDTO tagDTO) {
        Tag tag = null;
        if (tagDTO != null) {
            tag = Tag.builder()
                    .id(tagDTO.getId())
                    .name(tagDTO.getName())
                    .build();
        }
        return tag;
    }
}
