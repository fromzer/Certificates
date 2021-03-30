package com.epam.esm.service.impl;

import com.epam.esm.utils.converter.GiftTagDtoToTagDtoConverter;
import com.epam.esm.dao.mysql.TagDAOMySQL;
import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.GiftTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftTagServiceImpl implements GiftTagService {
    private final TagDAOMySQL tagDAO;

    @Autowired
    public GiftTagServiceImpl(TagDAOMySQL tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Long create(GiftTagDTO entity) {
        return tagDAO.create(GiftTagDtoToTagDtoConverter.convertToPersistenceLayerEntity(entity));
    }

    @Override
    public Optional<GiftTagDTO> findById(Long id) {
        return Optional.of(GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tagDAO.findById(id).get()));
    }

    @Override
    public GiftTagDTO findByName(String name) {
        return GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tagDAO.findByName(name));
    }

    @Override
    public void delete(GiftTagDTO entity) {
        tagDAO.delete(GiftTagDtoToTagDtoConverter.convertToPersistenceLayerEntity(entity));
    }

    @Override
    public List<GiftTagDTO> findAll() {
        List<TagDTO> tags = tagDAO.findAll();
        List<GiftTagDTO> giftTagDTOList = new ArrayList<>();
        for (TagDTO tag : tags) {
            giftTagDTOList.add(GiftTagDtoToTagDtoConverter.convertToServiceLayerEntity(tag));
        }
        return giftTagDTOList;
    }
}
