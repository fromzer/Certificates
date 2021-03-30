package com.epam.esm.service;


import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftTagService extends GiftService<GiftTagDTO> {

    List<GiftTagDTO> getCertificateTags(GiftCertificate giftCertificate) throws ServiceException;

    public GiftTagDTO findByName(String name) throws ServiceException;
}
