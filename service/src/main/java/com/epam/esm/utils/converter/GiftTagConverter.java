package com.epam.esm.utils.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ConvertResourceException;
import com.epam.esm.model.GiftTag;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class GiftTagConverter {

    public static GiftTag convertToServiceLayerEntity(TagDTO entityDto) {
        try {
            return GiftTag.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .build();
        } catch (NullPointerException ex) {
            log.error("An error occurred during conversion, an empty value", ex);
            throw new ConvertResourceException("An error occurred during conversion, an empty value", ex);
        }
    }

    public static TagDTO convertToPersistenceLayerEntity(GiftTag entityDto) {
        try {
            return TagDTO.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .build();
        } catch (NullPointerException ex) {
            log.error("An error occurred during conversion, an empty value", ex);
            throw new ConvertResourceException("An error occurred during conversion, an empty value", ex);
        }
    }
}
