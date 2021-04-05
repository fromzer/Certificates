package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.UpdateEntityException;

import java.util.List;

public interface CertificateDAO extends Dao<Long, CertificateDTO> {
    CertificateDTO update(CertificateDTO certificateDTO) throws UpdateEntityException;

    List<CertificateDTO> findCertificateByParams(String tag, String name, String description, String sort) throws EntityRetrievalException;
}
