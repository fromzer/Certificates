package com.epam.esm.service.impl;

import com.epam.esm.dao.mysql.TagDAOMySQL;
import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.*;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.utils.converter.GiftTagDtoToTagDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftTagServiceImpl implements GiftTagService {
    private static final Logger logger = LoggerFactory.getLogger(GiftTagServiceImpl.class);
    private final TagDAOMySQL tagDAO;

    @Autowired
    public GiftTagServiceImpl(TagDAOMySQL tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Long create(GiftTagDTO entity) throws CreateResourceException {
        try {
            return tagDAO.create(GiftTagDtoToTagDtoConverter.convertToPersistenceLayerEntity(entity));
        } catch (CreateEntityException e) {
            logger.error("Failed to create tag", e);
            throw new CreateResourceException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTagDTO findById(Long id) throws ResourceNotFoundException {
        try {
            TagDTO tagDTO = tagDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = " + id + ")"));
            return GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tagDTO);
        } catch (EntityRetrievalException e) {
            logger.error("Failed to create tag", e);
            throw new ResourceNotFoundException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTagDTO findByName(String name) throws ResourceNotFoundException {
        try {
            return GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tagDAO.findByName(name));
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find tag", e);
            throw new ResourceNotFoundException("Failed to find tag", e);
        }
    }

    @Override
    public void delete(GiftTagDTO entity) throws DeleteResourceException {
        try {
            tagDAO.delete(GiftTagDtoToTagDtoConverter.convertToPersistenceLayerEntity(entity));
        } catch (DeleteEntityException e) {
            logger.error("Failed to delete tag", e);
            throw new DeleteResourceException("Failed to delete tag", e);
        }
    }

    @Override
    public List<GiftTagDTO> findAll() throws ResourceNotFoundException {
        List<TagDTO> tags = null;
        try {
            tags = tagDAO.findAll();
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find tags", e);
            throw new ResourceNotFoundException("Failed to find tags", e);
        }
        List<GiftTagDTO> giftTagDTOList = new ArrayList<>();
        for (TagDTO tag : tags) {
            giftTagDTOList.add(GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tag));
        }
        return giftTagDTOList;
    }
}
