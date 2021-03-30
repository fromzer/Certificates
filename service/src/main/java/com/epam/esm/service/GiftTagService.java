package com.epam.esm.service;


import com.epam.esm.dto.GiftTagDTO;


public interface GiftTagService extends GiftService<GiftTagDTO> {

    public GiftTagDTO findByName(String name);
}
