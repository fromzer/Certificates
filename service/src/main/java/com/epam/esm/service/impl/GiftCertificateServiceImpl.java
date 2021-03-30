package com.epam.esm.service.impl;


import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.entity.GiftTag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private CertificateDAOImpl certificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(CertificateDAOImpl certificateDAO) {
        this.certificateDAO = certificateDAO;
    }


    @Override
    public GiftCertificateDTO update(Map<String, Object> params, List<GiftTag> tags) throws ServiceException {
        return null;
    }

    @Override
    public Long create(GiftCertificateDTO entity) throws ServiceException {
        try {
            return certificateDAO.create(GiftCertificateConverter.convertToOutboundEntity(entity));
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<GiftCertificateDTO> findById(Long id) throws ServiceException {
        try {
            return Optional.of(GiftCertificateConverter.convertToIncomingEntity(certificateDAO.findById(id).get()));
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(GiftCertificateDTO entity) throws ServiceException {

    }

    @Override
    public List<GiftCertificateDTO> findAll() throws ServiceException {
        try {
            return certificateDAO.findAll().stream()
                    .map(GiftCertificateConverter::convertToIncomingEntity)
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GiftCertificateDTO> findCertificateByParams(@Nullable String tag,
                                                            @Nullable String name,
                                                            @Nullable String description,
                                                            @Nullable String sort) {
        return certificateDAO.findCertificateByParams(tag, name, description, sort).stream()
                .map(GiftCertificateConverter::convertToIncomingEntity)
                .collect(Collectors.toList());
    }
}
