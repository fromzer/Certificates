package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.SearchAndSortParams;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private CertificateDAOImpl certificateDAO;
    GiftCertificateServiceImpl giftCertificateService;

    private CertificateDTO correctCertificate;
    private GiftCertificate correctGiftCertificate;

    @BeforeEach
    void createCertificate() {
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
    void shouldUpdateOnlyName() throws UpdateEntityException, UpdateResourceException, EntityRetrievalException {
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
        GiftCertificate actual = giftCertificateService.update(giftCertificate);
        assertEquals(certificateDTO.getName(), actual.getName());
    }

    @Test
    void shouldUpdateNameAndDescription() throws EntityRetrievalException, UpdateEntityException, UpdateResourceException {
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
        GiftCertificate actual = giftCertificateService.update(giftCertificate);
        assertEquals(certificateDTO.getDescription(), actual.getDescription());
    }

    @Test
    void shouldUpdateAllFieldsAndCreateTag() throws EntityRetrievalException, UpdateEntityException, UpdateResourceException {
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
    void shouldCreateCertificate() throws CreateEntityException, CreateResourceException {
        when(certificateDAO.create(correctCertificate)).thenReturn(1l);
        assertEquals(correctCertificate.getId(), giftCertificateService.create(correctGiftCertificate));
    }

    @Test
    void ShouldFindCertificateById() throws ResourceNotFoundException, EntityRetrievalException {
        when(certificateDAO.findById(anyLong())).thenReturn(correctCertificate);
        GiftCertificate actual = giftCertificateService.findById(1L);
        assertEquals(correctGiftCertificate, actual);
    }

    @Test
    void ShouldNotFindCertificateById() throws ResourceNotFoundException, EntityRetrievalException {
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
    void shouldDeleteCertificate() throws DeleteEntityException {
        assertThrows(ConvertResourceException.class, () -> giftCertificateService.delete(correctGiftCertificate));
    }

    @Test
    void shouldFindAllCertificates() throws EntityRetrievalException, ResourceNotFoundException {
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificateDTOList.add(correctCertificate);
        when(certificateDAO.findAll()).thenReturn(certificateDTOList);
        assertEquals(1, giftCertificateService.findAll().size());
    }

    @Test
    void shouldFindCertificateByParams() throws EntityRetrievalException, ResourceNotFoundException {
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
        when(certificateDAO.findCertificateByParams(any(SearchAndSortParams.class))).thenReturn(certificateDTOList);
        Map<String, String> params = new HashMap<>();
        params.put("tag", "moto");
        GiftCertificate giftCertificateFindByTag = giftCertificateService.findCertificateByParams(params).get(0);
        params.remove("tag");
        params.put("name", "Hello");
        GiftCertificate giftCertificateFindByName = giftCertificateService.findCertificateByParams(params).get(0);
        params.remove("name");
        params.put("description", "World");
        GiftCertificate giftCertificateFindByDescription = giftCertificateService.findCertificateByParams(params).get(0);
        boolean containTag = giftCertificateFindByTag.getTags().contains(giftTag);
        boolean findByName = giftCertificateFindByName.getName().equals("Hello");
        boolean findByDescription = giftCertificateFindByDescription.getDescription().equals("World");
        assertEquals(containTag, true);
        assertEquals(findByName, true);
        assertEquals(findByDescription, true);
    }
}