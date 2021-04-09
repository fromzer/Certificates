package com.epam.esm.dao.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ConvertException;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagDAOImplTest {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private TagDAOImpl tagDAOImpl;

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("create_tables.sql")
                .addScript("add_data.sql")
                .build();
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        tagDAOImpl = new TagDAOImpl(namedParameterJdbcTemplate);
    }

    private TagDTO tagDTO = TagDTO.builder()
            .name("TagDaoTestTag")
            .build();

    @Test
    void shouldCreateTag() throws CreateEntityException, EntityRetrievalException {
        Long idTag = tagDAOImpl.create(tagDTO);
        TagDTO actual = tagDAOImpl.findById(idTag);
        assertEquals(actual.getName(), tagDTO.getName());
    }

    @Test
    void shouldFindTagById() throws EntityRetrievalException {
        TagDTO dto = tagDAOImpl.findById(9L);
        assertNotNull(dto);
        assertEquals(dto.getName(), "WoW");
    }

    @Test
    void shouldNotFindTagById() throws EntityRetrievalException {
        assertThrows(ConvertException.class, () -> tagDAOImpl.findById(66L));
    }

    @Test
    void shouldDeleteTag() throws DeleteEntityException, EntityRetrievalException {
        TagDTO tag = TagDTO.builder()
                .id(13L)
                .build();
        tagDAOImpl.delete(tag);
        assertThrows(ConvertException.class, () -> tagDAOImpl.findById(tag.getId()));
    }

    @Test
    void shouldFindAllTags() throws EntityRetrievalException {
        TagDTO tag = tagDAOImpl.findById(2L);
        List<TagDTO> tags = tagDAOImpl.findAll();
        assertEquals(tags.get(0), tag);
    }

    @Test
    void shouldFindTagByName() throws EntityRetrievalException {
        TagDTO actual = tagDAOImpl.findByName("SportMaster");
        assertEquals(actual.getName(), "SportMaster");
    }
}