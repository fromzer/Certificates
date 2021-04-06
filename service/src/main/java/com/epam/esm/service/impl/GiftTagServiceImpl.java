package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.*;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.utils.converter.GiftTagDtoToTagDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GiftTagServiceImpl implements GiftTagService {
    private static final Logger logger = LoggerFactory.getLogger(GiftTagServiceImpl.class);
    private final TagDAO tagDAO;

    @Autowired
    public GiftTagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional
    public Long create(GiftTag entity) throws CreateResourceException {
        try {
            return tagDAO.create(GiftTagDtoToTagDtoConverter.convertToPersistenceLayerEntity(entity));
        } catch (CreateEntityException e) {
            logger.error("Failed to create tag", e);
            throw new CreateResourceException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTag findById(Long id) throws ResourceNotFoundException {
        try {
            TagDTO tagDTO = tagDAO.findById(id);
            if (tagDTO == null) {
                throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
            }
            return GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tagDTO);
        } catch (EntityRetrievalException e) {
            logger.error("Failed to create tag", e);
            throw new ResourceNotFoundException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTag findByName(String name) throws ResourceNotFoundException {
        try {
            return GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tagDAO.findByName(name));
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find tag", e);
            throw new ResourceNotFoundException("Failed to find tag", e);
        }
    }

    @Override
    @Transactional
    public void delete(GiftTag entity) throws DeleteResourceException {
        try {
            findById(entity.getId());
            tagDAO.delete(GiftTagDtoToTagDtoConverter.convertToPersistenceLayerEntity(entity));
        } catch (ResourceNotFoundException | DeleteEntityException e) {
            logger.error("Failed to delete tag", e);
            throw new DeleteResourceException("Failed to delete tag", e);
        }
    }

    @Override
    public List<GiftTag> findAll() throws ResourceNotFoundException {
        List<TagDTO> tags;
        try {
            tags = tagDAO.findAll();
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find tags", e);
            throw new ResourceNotFoundException("Failed to find tags", e);
        }
        List<GiftTag> giftTagList = new ArrayList<>();
        for (TagDTO tag : tags) {
            giftTagList.add(GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tag));
        }
        return giftTagList;
    }
}
