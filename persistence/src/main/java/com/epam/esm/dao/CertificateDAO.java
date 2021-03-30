package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDTO;

public interface CertificateDAO extends Dao<Long, CertificateDTO> {
    CertificateDTO update(CertificateDTO certificateDTO);
}
