package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.UpdateEntityException;

public interface CertificateDAO extends Dao<Long, CertificateDTO> {
    CertificateDTO update(CertificateDTO certificateDTO) throws UpdateEntityException;
}
