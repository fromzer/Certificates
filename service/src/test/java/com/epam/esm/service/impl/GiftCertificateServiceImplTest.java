package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.*;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private CertificateDAOImpl certificateDAO;
    GiftCertificateServiceImpl giftCertificateService;

    private CertificateDTO correctCertificate;
    private GiftCertificate correctGiftCertificate;

    @BeforeEach
    void init() {
        giftCertificateService = new GiftCertificateServiceImpl(certificateDAO);
        correctCertificate = CertificateDTO.builder()
                .id(1l)
                .name("New certificate name")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
        correctGiftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("New certificate name")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
    }

    @Test
    void isUpdated_ShouldUpdateOnlyName() throws UpdateEntityException, UpdateResourceException, EntityRetrievalException {
        CertificateDTO certificate = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .build();
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("new NAME")
                .build();
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
        when(certificateDAO.findById(anyLong())).thenReturn(certificateDTO);
        when(certificateDAO.update(certificate)).thenReturn(certificateDTO);
        GiftCertificate update = giftCertificateService.update(giftCertificate);
        assertEquals(certificateDTO.getName(), update.getName());
    }

    @Test
    void isUpdated_ShouldUpdateNameAndDescription() throws EntityRetrievalException, UpdateEntityException, UpdateResourceException {
        CertificateDTO certificate = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .build();
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .build();
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
        when(certificateDAO.findById(anyLong())).thenReturn(certificateDTO);
        when(certificateDAO.update(certificate)).thenReturn(certificateDTO);
        GiftCertificate update = giftCertificateService.update(giftCertificate);
        assertEquals(certificateDTO.getDescription(), update.getDescription());
    }
    @Test
    void isUpdated_ShouldUpdateAllFieldsAndCreateTag() throws EntityRetrievalException, UpdateEntityException, UpdateResourceException {
        TagDTO tagDTO = TagDTO.builder()
                .id(1l)
                .name("moto")
                .build();
        GiftTag giftTag = GiftTag.builder()
                .id(1l)
                .name("moto")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        Set<GiftTag> giftTags = new LinkedHashSet<>();
        giftTags.add(giftTag);
        CertificateDTO certificate = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(11.00))
                .duration(7)
                .tags(tagDTOSet)
                .build();
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(11.00))
                .duration(7)
                .tags(giftTags)
                .build();
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(11.00))
                .duration(7)
                .tags(tagDTOSet)
                .build();
        when(certificateDAO.findById(anyLong())).thenReturn(certificateDTO);
        when(certificateDAO.update(certificate)).thenReturn(certificateDTO);
        GiftCertificate update = giftCertificateService.update(giftCertificate);
        boolean contains = update.getTags().contains(giftTag);
        assertEquals(certificateDTO.getDescription(), update.getDescription());
        assertEquals(certificateDTO.getName(), update.getName());
        assertEquals(certificateDTO.getPrice(), update.getPrice());
        assertEquals(certificateDTO.getDuration(), update.getDuration());
        assertEquals(contains, true);
    }

    @Test
    void isCreated_ShouldCreateCertificate() throws CreateEntityException, CreateResourceException {
        when(certificateDAO.create(correctCertificate)).thenReturn(1l);
        assertEquals(correctCertificate.getId(), giftCertificateService.create(correctGiftCertificate));
    }

    @Test
    void isFound_ShouldFindCertificateById() throws ResourceNotFoundException, EntityRetrievalException {
        when(certificateDAO.findById(anyLong())).thenReturn(correctCertificate);
        GiftCertificate actual = giftCertificateService.findById(1L);
        assertEquals(correctGiftCertificate, actual);
    }
    @Test
    void isNotFound_ShouldNotFindCertificateByIdNegative() throws ResourceNotFoundException, EntityRetrievalException {
        when(certificateDAO.findById(anyLong())).thenReturn(correctCertificate);
        GiftCertificate actual = giftCertificateService.findById(1L);
        GiftCertificate ex = GiftCertificate.builder()
                .id(1l)
                .name("New name")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.00))
                .duration(1)
                .build();

        assertNotEquals(ex, actual);
    }

    @Test
    void isDeleted_ShouldDeleteCertificate() throws DeleteEntityException {
        lenient().doThrow(new DeleteEntityException()).when(certificateDAO).delete(any(CertificateDTO.class));
        assertThrows(DeleteResourceException.class, () -> giftCertificateService.delete(correctGiftCertificate));
    }

    @Test
    void isFound_ShouldFindAllCertificates() throws EntityRetrievalException, ResourceNotFoundException {
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificateDTOList.add(correctCertificate);
        when(certificateDAO.findAll()).thenReturn(certificateDTOList);
        assertEquals(1, giftCertificateService.findAll().size());
    }

    @Test
    void isFound_ShouldFindCertificateByParams() throws EntityRetrievalException, ResourceNotFoundException {
        TagDTO tagDTO = TagDTO.builder()
                .id(1l)
                .name("moto")
                .build();
        GiftTag giftTag = GiftTag.builder()
                .id(1l)
                .name("moto")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(2l)
                .name("Hello")
                .description("World")
                .price(BigDecimal.valueOf(12.00))
                .duration(1)
                .tags(tagDTOSet)
                .build();
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificateDTOList.add(certificateDTO);
        String tagName = "moto";
        String findCertificateName = "Hello";
        String findCertificateDescription = "World";
        when(certificateDAO.findCertificateByParams(tagName, null, null, null)).thenReturn(certificateDTOList);
        when(certificateDAO.findCertificateByParams(null, findCertificateName, null, null)).thenReturn(certificateDTOList);
        when(certificateDAO.findCertificateByParams(null, null, findCertificateDescription, null)).thenReturn(certificateDTOList);
        GiftCertificate giftCertificateFindByTag = giftCertificateService.findCertificateByParams(tagName, null, null, null).get(0);
        GiftCertificate giftCertificateFindByName = giftCertificateService.findCertificateByParams(null, findCertificateName, null, null).get(0);
        GiftCertificate giftCertificateFindByDescription = giftCertificateService.findCertificateByParams(null, null, findCertificateDescription, null).get(0);
        boolean containTag = giftCertificateFindByTag.getTags().contains(giftTag);
        boolean findByName = giftCertificateFindByName.getName().equals(findCertificateName);
        boolean findByDescription = giftCertificateFindByDescription.getDescription().equals(findCertificateDescription);
        assertEquals(containTag, true);
        assertEquals(findByName, true);
        assertEquals(findByDescription, true);
    }
}