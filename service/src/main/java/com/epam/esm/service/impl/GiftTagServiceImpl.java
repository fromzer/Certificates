package com.epam.esm.service.impl;


import com.epam.esm.converter.GiftTagConverter;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftTagServiceImpl implements GiftTagService {
    private final TagDAOImpl tagDAO;

    @Autowired
    public GiftTagServiceImpl(TagDAOImpl tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Long create(GiftTagDTO entity) throws ServiceException {
        return tagDAO.create(GiftTagConverter.convertToOutboundEntity(entity));
    }

    @Override
    public Optional<GiftTagDTO> findById(Long id) throws ServiceException {
        return Optional.of(GiftTagConverter.convertToIncomingEntity(tagDAO.findById(id).get()));
    }

    @Override
    public GiftTagDTO findByName(String name) throws ServiceException {
        return GiftTagConverter.convertToIncomingEntity(tagDAO.findByName(name));
    }

    @Override
    public void delete(GiftTagDTO entity) throws ServiceException {
        tagDAO.delete(GiftTagConverter.convertToOutboundEntity(entity));
    }

    @Override
    public List<GiftTagDTO> findAll() throws ServiceException {
        List<TagDTO> tags = tagDAO.findAll();
        List<GiftTagDTO> giftTagDTOList = new ArrayList<>();
        for (TagDTO tag : tags) {
            giftTagDTOList.add(GiftTagConverter.convertToIncomingEntity(tag));
        }
        return giftTagDTOList;
    }

    @Override
    public List<GiftTagDTO> getCertificateTags(GiftCertificate giftCertificate) throws ServiceException {
//        List<TagDTO> tags = tagDAO.findAll();
//        List<GiftTagDTO> giftTagDTOList = new ArrayList<>();
//        for (TagDTO tag : tags) {
//            giftTagDTOList.add(GiftTagConverter.convertToIncomingEntity(tag));
//        }
//        return giftTagDTOList;
        return null;
    }
}
