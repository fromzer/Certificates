package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface GiftCertificateService extends GiftService<GiftCertificateDTO> {
    GiftCertificateDTO update(GiftCertificateDTO certificateDTO) throws ServiceException;

    List<GiftCertificateDTO> findCertificateByParams(String tag, String name, String description, String sort);
}
