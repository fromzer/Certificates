package com.epam.esm.dao.mysql;

import com.epam.esm.configuration.DataConfiguration;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(DataConfiguration.class)
@ActiveProfiles("dev")
class TagDAOMySQLTest {
    @Autowired
    private TagDAOMySQL tagDAOMySQL;

    private TagDTO tagDTO = TagDTO.builder()
            .name("TagDaoTestTag")
            .build();

    @Test
    void create() throws CreateEntityException, EntityRetrievalException {
        Long idTag = tagDAOMySQL.create(tagDTO);
        TagDTO result = tagDAOMySQL.findById(idTag).get();
        assertEquals(result.getName(), tagDTO.getName());
    }

    @Test
    void findById() throws EntityRetrievalException {
        TagDTO dto = tagDAOMySQL.findById(9L).get();
        assertNotNull(dto);
        assertEquals(dto.getName(), "WoW");
    }

    @Test
    void delete() throws DeleteEntityException {
        TagDTO tag = TagDTO.builder()
                .id(13L)
                .build();
        tagDAOMySQL.delete(tag);
        assertThrows(NoSuchElementException.class, () -> tagDAOMySQL.findById(tag.getId()).get());
    }

    @Test
    void findAll() throws EntityRetrievalException {
        TagDTO tag = tagDAOMySQL.findById(24L).get();
        List<TagDTO> tags = tagDAOMySQL.findAll();
        assertEquals(tags.get(tags.size() - 1), tag);
    }

    @Test
    void findByName() throws EntityRetrievalException {
        TagDTO result = tagDAOMySQL.findByName("TagDaoTestTag");
        assertEquals(result.getName(), tagDTO.getName());
    }
}