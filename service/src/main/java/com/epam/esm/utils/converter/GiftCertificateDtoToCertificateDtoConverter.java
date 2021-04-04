package com.epam.esm.utils.converter;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftTag;

import java.util.LinkedHashSet;
import java.util.Set;

public class GiftCertificateDtoToCertificateDtoConverter {

    public static GiftCertificate convertToServiceLayerEntity(CertificateDTO entityDto) {
        GiftCertificate giftCertificate = null;
        if (entityDto != null) {
            giftCertificate = GiftCertificate.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .description(entityDto.getDescription())
                    .price(entityDto.getPrice())
                    .duration(entityDto.getDuration())
                    .createDate(entityDto.getCreateDate())
                    .lastUpdateDate(entityDto.getLastUpdateDate())
                    .build();
            if (entityDto.getTags() != null) {
                Set<GiftTag> tagDTOSet = new LinkedHashSet<>();
                for (TagDTO tagDTO : entityDto.getTags()) {
                    tagDTOSet.add(GiftTag.builder()
                            .id(tagDTO.getId())
                            .name(tagDTO.getName())
                            .build());
                }
                giftCertificate.setTags(tagDTOSet);
            }
        }
        return giftCertificate;
    }

    public static CertificateDTO convertToPersistenceLayerEntity(GiftCertificate entityDto) {
        CertificateDTO certificateDTO = null;
        if (entityDto != null) {
            certificateDTO = CertificateDTO.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .description(entityDto.getDescription())
                    .price(entityDto.getPrice())
                    .duration(entityDto.getDuration())
                    .createDate(entityDto.getCreateDate())
                    .lastUpdateDate(entityDto.getLastUpdateDate())
                    .build();
            if (entityDto.getTags() != null) {
                Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
                for (GiftTag giftTag : entityDto.getTags()) {
                    tagDTOSet.add(TagDTO.builder()
                            .id(giftTag.getId())
                            .name(giftTag.getName())
                            .build());
                }
                certificateDTO.setTags(tagDTOSet);
            }
        }
        return certificateDTO;
    }
}
