package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.DAOException;
import com.epam.esm.entity.Tag;

import java.util.Map;
import java.util.Set;

public interface CertificateDAO extends Dao<Long, CertificateDTO> {
    CertificateDTO update(Map<String, Object> params, Set<Tag> tags) throws DAOException;
}
