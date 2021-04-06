package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.extractor.TagListResultSetExtractor;
import com.epam.esm.dao.extractor.TagResultSetExtractor;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.util.ToDTOConverter;
import com.epam.esm.util.ToEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TagDAOImpl implements TagDAO {
    private static final Logger logger = LoggerFactory.getLogger(TagDAOImpl.class);
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT id, name FROM tag WHERE id = :id;";
    private static final String SQL_SELECT_FIND_NAME = "SELECT id, name FROM tag WHERE name=:name;";
    private static final String SQL_SELECT_FIND_ALL = "SELECT id, name FROM tag";
    private static final String SQL_INSERT_CREATE_TAG = "INSERT INTO tag(name) VALUES (:name);";
    private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id = :id;";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Long create(TagDTO entity) throws CreateEntityException {
        Tag tag = ToEntityConverter.convertDTOToTag(entity);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", tag.getName());
        try {
            jdbcTemplate.update(SQL_INSERT_CREATE_TAG, parameterSource, holder, new String[]{"id"});
        } catch (DataAccessException ex) {
            logger.error("Request create tag execution error", ex);
            throw new CreateEntityException(ex);
        }
        return holder.getKey().longValue();
    }

    @Override
    public TagDTO findById(Long id) throws EntityRetrievalException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            Tag tag =jdbcTemplate.query(SQL_SELECT_FIND_BY_ID, parameterSource, new TagResultSetExtractor());
            return ToDTOConverter.convertToTagDTO(tag);
        } catch (DataAccessException ex) {
            logger.error("Tag is not found", ex);
            throw new EntityRetrievalException(ex);
        }
    }

    @Override
    public void delete(TagDTO entity) throws DeleteEntityException {
        Tag tag = ToEntityConverter.convertDTOToTag(entity);
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", tag.getId());
        try {
            jdbcTemplate.update(SQL_DELETE_TAG, parameterSource);
        } catch (DataAccessException ex) {
            logger.error("Tag delete request error", ex);
            throw new DeleteEntityException(ex);
        }
    }

    @Override
    public List<TagDTO> findAll() throws EntityRetrievalException {
        try {
            Set<Tag> tags = jdbcTemplate.query(SQL_SELECT_FIND_ALL, new TagListResultSetExtractor());
            return tags.stream()
                    .map(ToDTOConverter::convertToTagDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            logger.error("Request find all tags execution error", ex);
            throw new EntityRetrievalException(ex);
        }
    }

    @Override
    public TagDTO findByName(String name) throws EntityRetrievalException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        try {
            Tag tag = jdbcTemplate.query(SQL_SELECT_FIND_NAME, params, new TagResultSetExtractor());
            return ToDTOConverter.convertToTagDTO(tag);
        } catch (DataAccessException ex) {
            logger.error("Request find tag by name execution error", ex);
            throw new EntityRetrievalException(ex);
        }
    }
}
