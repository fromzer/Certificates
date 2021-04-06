package com.epam.esm.service.impl;


import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.*;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.converter.GiftCertificateDtoToCertificateDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);
    private CertificateDAO certificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate certificateDTO) throws UpdateResourceException {
        CertificateDTO dto;
        try {
            findById(certificateDTO.getId());
            dto = certificateDAO.update(GiftCertificateDtoToCertificateDtoConverter.convertToPersistenceLayerEntity(certificateDTO));
        } catch (UpdateEntityException | ResourceNotFoundException e) {
            logger.error("Failed to update certificate", e);
            throw new UpdateResourceException("Failed to update certificate", e);
        }
        return GiftCertificateDtoToCertificateDtoConverter.convertToServiceLayerEntity(dto);
    }

    @Override
    @Transactional
    public Long create(GiftCertificate entity) throws CreateResourceException {
        try {
            return certificateDAO.create(GiftCertificateDtoToCertificateDtoConverter.convertToPersistenceLayerEntity(entity));
        } catch (CreateEntityException e) {
            logger.error("Failed to create certificate", e);
            throw new CreateResourceException("Failed to create certificate", e);
        }
    }

    @Override
    public GiftCertificate findById(Long id) throws ResourceNotFoundException {
        try {
            CertificateDTO certificateDTO = certificateDAO.findById(id);
            if (certificateDTO == null) {
                throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
            }
            return GiftCertificateDtoToCertificateDtoConverter.convertToServiceLayerEntity(certificateDTO);
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by id", e);
            throw new ResourceNotFoundException("Failed to find certificate by id", e);
        }
    }

    @Override
    public void delete(GiftCertificate entity) throws DeleteResourceException {
        try {
            findById(entity.getId());
            certificateDAO.delete(CertificateDTO.builder().id(entity.getId()).build());
        } catch (DeleteEntityException | ResourceNotFoundException e) {
            logger.error("Failed to delete certificate", e);
            throw new DeleteResourceException("Failed to delete certificate", e);
        }
    }

    @Override
    public List<GiftCertificate> findAll() throws ResourceNotFoundException {
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
    public List<GiftCertificate> findCertificateByParams(String tag, String name, String description, String sort) throws ResourceNotFoundException {
        try {
            List<GiftCertificate> giftCertificates = certificateDAO.findCertificateByParams(tag, name, description, sort).stream()
                    .map(GiftCertificateDtoToCertificateDtoConverter::convertToServiceLayerEntity)
                    .collect(Collectors.toList());
            if (giftCertificates.isEmpty()) {
                throw new ResourceNotFoundException("Not found");
            }
            return giftCertificates;
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by params", e);
            throw new ResourceNotFoundException("Failed to find certificate by params", e);
        }
    }
}
