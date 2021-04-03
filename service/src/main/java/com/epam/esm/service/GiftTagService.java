package com.epam.esm.service;


import com.epam.esm.model.GiftTag;
import com.epam.esm.exception.BadParametersException;
import com.epam.esm.exception.ResourceNotFoundException;


public interface GiftTagService extends GiftService<GiftTag> {

    public GiftTag findByName(String name) throws ResourceNotFoundException, BadParametersException;
}
