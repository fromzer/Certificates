package com.epam.esm.service;


import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;


public interface GiftTagService extends GiftService<GiftTag> {

    public GiftTag findByName(String name) throws ResourceNotFoundException;
}
