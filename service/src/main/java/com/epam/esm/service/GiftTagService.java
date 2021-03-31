package com.epam.esm.service;


import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.exception.BadParametersException;
import com.epam.esm.exception.ResourceNotFoundException;


public interface GiftTagService extends GiftService<GiftTagDTO> {

    public GiftTagDTO findByName(String name) throws ResourceNotFoundException, BadParametersException;
}
