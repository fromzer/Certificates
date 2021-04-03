package com.epam.esm.utils.converter;

import com.epam.esm.model.GiftTag;
import com.epam.esm.dto.TagDTO;

public class GiftTagDtoToTagDtoConverter {


    public static GiftTag convertToServiceLayerEntity(TagDTO entityDto) {
        return GiftTag.builder()
                .id(entityDto.getId())
                .name(entityDto.getName())
                .build();
    }


    public static TagDTO convertToPersistenceLayerEntity(GiftTag entityDto) {
        return TagDTO.builder()
                .id(entityDto.getId())
                .name(entityDto.getName())
                .build();
    }
}
