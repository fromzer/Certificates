package com.epam.esm.dao.mysql;

import com.epam.esm.configuration.DataConfiguration;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.UpdateEntityException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(DataConfiguration.class)
@ActiveProfiles("dev")
class CertificateDAOMySQLTest {
    @Autowired
    private CertificateDAOMySQL certificateDAO;

    @Autowired
    private TagDAOMySQL tagDAOMySQL;

    private CertificateDTO certificate = CertificateDTO.builder()
            .name("New certificate name")
            .description("New certificate description")
            .price(BigDecimal.valueOf(12.13))
            .duration(1)
            .build();

    @Test
    void update() throws EntityRetrievalException, UpdateEntityException {
        CertificateDTO certificateDTO = certificateDAO.findById(2L).get();
        certificateDTO.setDescription("No description");
        certificateDTO.setDuration(77);
        certificateDTO.setTags(new LinkedHashSet<>());
        TagDTO tagDTO = TagDTO.builder()
                .name("Update")
                .build();
        certificateDTO.getTags().add(tagDTO);
        CertificateDTO result = certificateDAO.update(certificateDTO);
        CertificateDTO update = certificateDAO.findById(2L).get();
        assertEquals(result, update);
    }

    @Test
    void create() throws CreateEntityException, EntityRetrievalException {
        Long id = certificateDAO.create(certificate);
        CertificateDTO created = certificateDAO.findById(id).get();
        assertEquals(certificate.getName(), created.getName());
        assertEquals(certificate.getDescription(), created.getDescription());
        assertEquals(certificate.getPrice(), created.getPrice());
        assertEquals(certificate.getDuration(), created.getDuration());
    }

    @Test
    void findById() throws EntityRetrievalException {
        CertificateDTO dto = certificateDAO.findById(1L).get();
        assertNotNull(dto);
        assertEquals(dto.getName(), "TestCertificate");
    }

    @Test
    void delete() throws DeleteEntityException {
        CertificateDTO cert = CertificateDTO.builder()
                .id(5L)
                .build();
        certificateDAO.delete(cert);
        assertThrows(NoSuchElementException.class, () -> certificateDAO.findById(cert.getId()).get());
    }

    @Test
    void findAll() throws EntityRetrievalException {
        CertificateDTO certificateDTO = certificateDAO.findById(1L).get();
        List<CertificateDTO> certificateDTOS = certificateDAO.findAll();
        assertEquals(certificateDTOS.get(0), certificateDTO);
    }

    @Test
    void findCertificateByParams() throws EntityRetrievalException {
        CertificateDTO certificateDTO = certificateDAO.findById(1L).get();
        TagDTO tagDTO = tagDAOMySQL.findById(2L).get();
        List<CertificateDTO> certificateByTagName = certificateDAO.findCertificateByParams(tagDTO.getName(), null, null, null);
        List<CertificateDTO> certificateByName = certificateDAO.findCertificateByParams(null, certificateDTO.getName(), null, null);
        List<CertificateDTO> certificateByDescription = certificateDAO.findCertificateByParams(null, null, certificateDTO.getDescription(), null);
        assertEquals(certificateByTagName.get(0).getId(), certificateDTO.getId());
        assertEquals(certificateByName.get(0).getName(), certificateDTO.getName());
        assertEquals(certificateByDescription.get(0).getDuration(), certificateDTO.getDuration());
    }
}