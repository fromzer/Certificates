package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.UpdateEntityException;

import java.util.List;

/**
 * Base interface class for CertificateDAO
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface CertificateDAO extends Dao<Long, CertificateDTO> {
    /**
     * Update entry in DB
     *
     * @param certificateDTO an DTO of business model
     * @return updated certificateDTO
     * @throws UpdateEntityException if fail to update data in DB
     */
    CertificateDTO update(CertificateDTO certificateDTO) throws UpdateEntityException;

    /**
     * Find entry in table
     *
     * @param tag the TagDTO name
     * @param name the CertificateDTO name or partial name
     * @param description the CertificateDTO description or partial description
     * @param sort string in format 'column_name,order_by'
     * @return list of CertificateDTO
     * @throws EntityRetrievalException if fail to retrieve data from DB
     */
    List<CertificateDTO> findCertificateByParams(String tag, String name, String description, String sort) throws EntityRetrievalException;
}
