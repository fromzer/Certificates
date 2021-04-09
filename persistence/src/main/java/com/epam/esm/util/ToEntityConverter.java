package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ConvertException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class ToEntityConverter {

    public static Certificate convertToCertificate(CertificateDTO certificateDTO) {
        try {
            return Certificate.builder()
                    .id(certificateDTO.getId())
                    .name(certificateDTO.getName())
                    .description(certificateDTO.getDescription())
                    .price(certificateDTO.getPrice())
                    .duration(certificateDTO.getDuration())
                    .createDate(certificateDTO.getCreateDate())
                    .lastUpdateDate(certificateDTO.getLastUpdateDate())
                    .tags(CollectionUtils.isNotEmpty(certificateDTO.getTags()) ?
                            certificateDTO.getTags().stream()
                                    .map(ToEntityConverter::convertToTag)
                                    .collect(Collectors.toSet())
                            : null)
                    .build();
        } catch (NullPointerException ex) {
            log.error("An error occurred during conversion, an empty value", ex);
            throw new ConvertException("An error occurred during conversion, an empty value", ex);
        }
    }

    public static Tag convertToTag(TagDTO tagDTO) {
        try {
            return Tag.builder()
                    .id(tagDTO.getId())
                    .name(tagDTO.getName())
                    .build();
        } catch (NullPointerException ex) {
            log.error("An error occurred during conversion, an empty value", ex);
            throw new ConvertException("An error occurred during conversion, an empty value", ex);
        }
    }
}
