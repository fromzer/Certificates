package com.epam.esm.utils.converter;

import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.dto.TagDTO;

public class GiftTagDtoToTagDtoConverter {


    public static GiftTagDTO convertToServiceLayerEntity(TagDTO entityDto) {
        return GiftTagDTO.builder()
                .id(entityDto.getId())
                .name(entityDto.getName())
                .build();
    }


    public static TagDTO convertToPersistenceLayerEntity(GiftTagDTO entityDto) {
        return TagDTO.builder()
                .id(entityDto.getId())
                .name(entityDto.getName())
                .build();
    }
}
