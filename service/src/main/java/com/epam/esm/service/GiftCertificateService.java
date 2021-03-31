package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.BadParametersException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;

import java.util.List;

public interface GiftCertificateService extends GiftService<GiftCertificateDTO> {
    GiftCertificateDTO update(GiftCertificateDTO certificateDTO) throws UpdateResourceException, BadParametersException;

    List<GiftCertificateDTO> findCertificateByParams(String tag, String name, String description, String sort) throws ResourceNotFoundException, BadParametersException;
}
