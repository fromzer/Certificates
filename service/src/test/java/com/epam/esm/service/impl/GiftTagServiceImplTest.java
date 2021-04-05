package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.*;
import com.epam.esm.model.GiftTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftTagServiceImplTest {
    @Mock
    TagDAOImpl tagDAO;
    GiftTagServiceImpl giftTagService;
    TagDTO correctTag;
    GiftTag correctGiftTag;


    @BeforeEach
    public void init() {
        giftTagService = new GiftTagServiceImpl(tagDAO);
        correctTag = new TagDTO(1L, "name");
        correctGiftTag = new GiftTag(1L, "name");
    }

    @Test
    void isCreated_ShouldCreateTag() throws CreateEntityException, CreateResourceException {
        when(tagDAO.create(correctTag)).thenReturn(1l);
        assertEquals(correctTag.getId(), giftTagService.create(correctGiftTag));
    }

    @Test
    void isFound_ShouldFindTagById() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findById(anyLong())).thenReturn(correctTag);
        GiftTag actual = giftTagService.findById(1L);
        assertEquals(correctGiftTag, actual);
    }

    @Test
    void isNotFound_ShouldNotFindTagByIdNegative() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findById(anyLong())).thenReturn(correctTag);
        GiftTag actual = giftTagService.findById(1L);
        GiftTag ex = new GiftTag(1l, "exception");
        assertNotEquals(ex, actual);
    }

    @Test
    void isFound_ShouldFindTagByName() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findByName(anyString())).thenReturn(correctTag);
        GiftTag actual = giftTagService.findByName("name");
        assertEquals(correctGiftTag, actual);
    }

    @Test
    void isDeleted_ShouldDeleteTagReturnException() throws DeleteEntityException {
        lenient().doThrow(new DeleteEntityException()).when(tagDAO).delete(any(TagDTO.class));
        assertThrows(DeleteResourceException.class, () -> giftTagService.delete(correctGiftTag));
    }

    @Test
    void isFound_ShouldFindAllTags() throws EntityRetrievalException, ResourceNotFoundException {
        List<TagDTO> tagDTOList = new ArrayList<>();
        tagDTOList.add(correctTag);
        when(tagDAO.findAll()).thenReturn(tagDTOList);
        assertEquals(1, giftTagService.findAll().size());
    }
}