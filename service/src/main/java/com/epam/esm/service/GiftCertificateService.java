package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateService extends GiftService<GiftCertificate> {
    GiftCertificate update(GiftCertificate certificateDTO) throws UpdateResourceException;

    List<GiftCertificate> findCertificateByParams(String tag, String name, String description, String sort) throws ResourceNotFoundException;
}
