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
public class ToDTOConverter {

    public static CertificateDTO convertToCertificateDTO(Certificate certificate) {
        try {
            return CertificateDTO.builder()
                    .id(certificate.getId())
                    .name(certificate.getName())
                    .description(certificate.getDescription())
                    .duration(certificate.getDuration())
                    .price(certificate.getPrice())
                    .createDate(certificate.getCreateDate())
                    .lastUpdateDate(certificate.getLastUpdateDate())
                    .tags(CollectionUtils.isNotEmpty(certificate.getTags()) ?
                            certificate.getTags().stream()
                                    .map(ToDTOConverter::convertToTagDTO)
                                    .collect(Collectors.toSet())
                            : null)
                    .build();
        } catch (NullPointerException ex) {
            log.error("An error occurred during conversion, an empty value", ex);
            throw new ConvertException("An error occurred during conversion, an empty value", ex);
        }
    }

    public static TagDTO convertToTagDTO(Tag tag) {
        try {
            return TagDTO.builder()
                    .id(tag.getId())
                    .name(tag.getName())
                    .build();
        } catch (NullPointerException ex) {
            log.error("An error occurred during conversion, an empty value", ex);
            throw new ConvertException("An error occurred during conversion, an empty value", ex);
        }
    }
}
