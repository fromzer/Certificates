package com.epam.esm.dao.impl;

import com.epam.esm.dto.TagDTO;
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
    void isCreated_ShouldCreateTag() throws CreateEntityException, EntityRetrievalException {
        Long idTag = tagDAOImpl.create(tagDTO);
        TagDTO result = tagDAOImpl.findById(idTag);
        assertEquals(result.getName(), tagDTO.getName());
    }

    @Test
    void isFound_ShouldFindTagById() throws EntityRetrievalException {
        TagDTO dto = tagDAOImpl.findById(9L);
        assertNotNull(dto);
        assertEquals(dto.getName(), "WoW");
    }

    @Test
    void isNotFound_ShouldNotFindTagByIdNegative() throws EntityRetrievalException {
        TagDTO dto = tagDAOImpl.findById(66L);
        assertNull(dto);
    }

    @Test
    void isDeleted_ShouldDeleteTag() throws DeleteEntityException, EntityRetrievalException {
        TagDTO tag = TagDTO.builder()
                .id(13L)
                .build();
        tagDAOImpl.delete(tag);
        assertNull(tagDAOImpl.findById(tag.getId()));
    }

    @Test
    void isFound_ShouldFindAllTags() throws EntityRetrievalException {
        TagDTO tag = tagDAOImpl.findById(2L);
        List<TagDTO> tags = tagDAOImpl.findAll();
        assertEquals(tags.get(0), tag);
    }

    @Test
    void isFound_ShouldFindTagByName() throws EntityRetrievalException {
        TagDTO result = tagDAOImpl.findByName("SportMaster");
        assertEquals(result.getName(), "SportMaster");
    }
}