package com.epam.esm.dao;

import com.epam.esm.dto.TagDTO;

public interface TagDAO extends Dao<Long, TagDTO> {
    TagDTO findByName(String name);
}
