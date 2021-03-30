package com.epam.esm.utils.converter;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.dto.TagDTO;

import java.util.LinkedHashSet;
import java.util.Set;

public class GiftCertificateDtoToCertificateDtoConverter {

    public static GiftCertificateDTO convertToServiceLayerEntity(CertificateDTO entityDto) {
        if (entityDto.getTags().isEmpty()) {
            return GiftCertificateDTO.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .description(entityDto.getDescription())
                    .price(entityDto.getPrice())
                    .duration(entityDto.getDuration())
                    .createDate(entityDto.getCreateDate())
                    .lastUpdateDate(entityDto.getLastUpdateDate())
                    .build();
        } else {
            Set<GiftTagDTO> tagDTOSet = new LinkedHashSet<>();
            for (TagDTO tagDTO : entityDto.getTags()) {
                tagDTOSet.add(GiftTagDTO.builder()
                        .id(tagDTO.getId())
                        .name(tagDTO.getName())
                        .build());
            }
            return GiftCertificateDTO.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .description(entityDto.getDescription())
                    .price(entityDto.getPrice())
                    .duration(entityDto.getDuration())
                    .createDate(entityDto.getCreateDate())
                    .lastUpdateDate(entityDto.getLastUpdateDate())
                    .tags(tagDTOSet)
                    .build();
        }
    }

    public static CertificateDTO convertToPersistenceLayerEntity(GiftCertificateDTO entityDto) {
        if (entityDto.getTags() == null) {
            return CertificateDTO.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .description(entityDto.getDescription())
                    .price(entityDto.getPrice())
                    .duration(entityDto.getDuration())
                    .createDate(entityDto.getCreateDate())
                    .lastUpdateDate(entityDto.getLastUpdateDate())
                    .build();
        } else {
            Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
            for (GiftTagDTO giftTagDTO : entityDto.getTags()) {
                tagDTOSet.add(TagDTO.builder()
                        .id(giftTagDTO.getId())
                        .name(giftTagDTO.getName())
                        .build());
            }
            return CertificateDTO.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .description(entityDto.getDescription())
                    .price(entityDto.getPrice())
                    .duration(entityDto.getDuration())
                    .createDate(entityDto.getCreateDate())
                    .lastUpdateDate(entityDto.getLastUpdateDate())
                    .tags(tagDTOSet)
                    .build();
        }
    }
}
