package com.epam.esm.utils.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.GiftTag;

public class GiftTagDtoToTagDtoConverter {


    public static GiftTag convertToServiceLayerEntity(TagDTO entityDto) {
        GiftTag giftTag = null;
        if (entityDto != null) {
            giftTag = GiftTag.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .build();
        }
        return giftTag;
    }


    public static TagDTO convertToPersistenceLayerEntity(GiftTag entityDto) {
        TagDTO tagDTO = null;
        if (entityDto != null) {
            tagDTO = TagDTO.builder()
                    .id(entityDto.getId())
                    .name(entityDto.getName())
                    .build();
        }
        return tagDTO;
    }
}
