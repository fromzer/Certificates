package com.epam.esm.service;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.entity.GiftTag;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends GiftService<GiftCertificateDTO> {
    GiftCertificateDTO update(Map<String, Object> params, List<GiftTag> tags) throws ServiceException;

//    List<GiftCertificateDTO> findByPartialName(String partialName) throws ServiceException;
//
//    List<GiftCertificateDTO> findCertificatesByTagName(String tagName) throws ServiceException;
}
