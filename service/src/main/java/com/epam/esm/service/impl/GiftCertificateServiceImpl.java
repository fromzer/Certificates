package com.epam.esm.service.impl;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.utils.converter.GiftCertificateDtoToCertificateDtoConverter;
import com.epam.esm.dao.mysql.CertificateDAOMySQL;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private CertificateDAOMySQL certificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(CertificateDAOMySQL certificateDAO) {
        this.certificateDAO = certificateDAO;
    }


    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
        CertificateDTO dto = certificateDAO.update(GiftCertificateDtoToCertificateDtoConverter.convertToPersistenceLayerEntity(certificateDTO));
        return GiftCertificateDtoToCertificateDtoConverter.convertToServiceLayerEntity(dto);
    }

    @Override
    public Long create(GiftCertificateDTO entity) {
        return certificateDAO.create(GiftCertificateDtoToCertificateDtoConverter.convertToPersistenceLayerEntity(entity));
    }

    @Override
    public Optional<GiftCertificateDTO> findById(Long id) {
        return Optional.of(GiftCertificateDtoToCertificateDtoConverter.convertToServiceLayerEntity(certificateDAO.findById(id).get()));
    }

    @Override
    public void delete(GiftCertificateDTO entity) {
        certificateDAO.delete(CertificateDTO.builder().id(entity.getId()).build());
    }

    @Override
    public List<GiftCertificateDTO> findAll() {
        return certificateDAO.findAll().stream()
                .map(GiftCertificateDtoToCertificateDtoConverter::convertToServiceLayerEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDTO> findCertificateByParams(String tag, String name, String description, String sort) {
        if (tag == null & name == null && description == null && sort == null) {
            return findAll();
        }
        return certificateDAO.findCertificateByParams(tag, name, description, sort).stream()
                .map(GiftCertificateDtoToCertificateDtoConverter::convertToServiceLayerEntity)
                .collect(Collectors.toList());
    }
}
