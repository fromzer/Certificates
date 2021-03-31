package com.epam.esm.service.impl;


import com.epam.esm.dao.mysql.CertificateDAOMySQL;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.*;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.converter.GiftCertificateDtoToCertificateDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);
    private CertificateDAOMySQL certificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(CertificateDAOMySQL certificateDAO) {
        this.certificateDAO = certificateDAO;
    }


    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) throws UpdateResourceException {
        CertificateDTO dto = null;
        try {
            dto = certificateDAO.update(GiftCertificateDtoToCertificateDtoConverter.convertToPersistenceLayerEntity(certificateDTO));
        } catch (UpdateEntityException e) {
            logger.error("Failed to update certificate", e);
            throw new UpdateResourceException("Failed to update certificate", e);
        }
        return GiftCertificateDtoToCertificateDtoConverter.convertToServiceLayerEntity(dto);
    }

    @Override
    public Long create(GiftCertificateDTO entity) throws CreateResourceException {
        try {
            return certificateDAO.create(GiftCertificateDtoToCertificateDtoConverter.convertToPersistenceLayerEntity(entity));
        } catch (CreateEntityException e) {
            logger.error("Failed to create certificate", e);
            throw new CreateResourceException("Failed to create certificate", e);
        }
    }

    @Override
    public GiftCertificateDTO findById(Long id) throws ResourceNotFoundException {
        try {
            CertificateDTO certificateDTO = certificateDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = " + id + ")"));
            return GiftCertificateDtoToCertificateDtoConverter.convertToServiceLayerEntity(certificateDTO);
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by id", e);
            throw new ResourceNotFoundException("Failed to find certificate by id", e);
        }
    }

    @Override
    public void delete(GiftCertificateDTO entity) throws DeleteResourceException {
        try {
            certificateDAO.delete(CertificateDTO.builder().id(entity.getId()).build());
        } catch (DeleteEntityException e) {
            logger.error("Failed to delete certificate", e);
            throw new DeleteResourceException("Failed to delete certificate", e);
        }
    }

    @Override
    public List<GiftCertificateDTO> findAll() throws ResourceNotFoundException {
        try {
            return certificateDAO.findAll().stream()
                    .map(GiftCertificateDtoToCertificateDtoConverter::convertToServiceLayerEntity)
                    .collect(Collectors.toList());
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificates", e);
            throw new ResourceNotFoundException("Failed to find certificates", e);
        }
    }

    @Override
    public List<GiftCertificateDTO> findCertificateByParams(String tag, String name, String description, String sort) throws ResourceNotFoundException {
        if (tag == null & name == null && description == null && sort == null) {
            return findAll();
        }
        try {
            return certificateDAO.findCertificateByParams(tag, name, description, sort).stream()
                    .map(GiftCertificateDtoToCertificateDtoConverter::convertToServiceLayerEntity)
                    .collect(Collectors.toList());
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by params", e);
            throw new ResourceNotFoundException("Failed to find certificate by params", e);
        }
    }
}
