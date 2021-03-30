package com.epam.esm.dao;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DAOException;

public interface TagDAO extends Dao<Long, TagDTO> {
    TagDTO findByName(String name) throws DAOException;
}
