package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.exception.BadParametersException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;

import java.util.List;

public interface GiftCertificateService extends GiftService<GiftCertificate> {
    GiftCertificate update(GiftCertificate certificateDTO) throws UpdateResourceException, BadParametersException;

    List<GiftCertificate> findCertificateByParams(String tag, String name, String description, String sort) throws ResourceNotFoundException, BadParametersException;
}
