package com.epam.esm.dao.mysql;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.extractor.TagListResultSetExtractor;
import com.epam.esm.dao.extractor.TagResultSetExtractor;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagDAOMySQL implements TagDAO {
    private static final Logger logger = LoggerFactory.getLogger(TagDAOMySQL.class);
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT id, name FROM tag WHERE id = :id;";
    private static final String SQL_SELECT_FIND_NAME = "SELECT id, name FROM tag WHERE name=:name;";
    private static final String SQL_SELECT_FIND_ALL = "SELECT id, name FROM tag";
    private static final String SQL_INSERT_CREATE_TAG = "INSERT INTO tag(name) VALUE (:name);";
    private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id = :id;";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOMySQL(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(TagDTO entity) throws CreateEntityException {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", entity.getName());
        try {
            jdbcTemplate.update(SQL_INSERT_CREATE_TAG, parameterSource, holder);
        } catch (DataAccessException ex) {
            logger.error("Request create tag execution error", ex);
            throw new CreateEntityException("Request create tag execution error", ex);
        }
        return holder.getKey().longValue();
    }

    @Override
    public Optional<TagDTO> findById(Long id) throws EntityRetrievalException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.ofNullable(jdbcTemplate.query(SQL_SELECT_FIND_BY_ID, parameterSource, new TagResultSetExtractor()));
        } catch (DataAccessException ex) {
            logger.error("Request find tag by id execution error", ex);
            throw new EntityRetrievalException("Request find tag by id execution error", ex);
        }
    }

    @Override
    public void delete(TagDTO entity) throws DeleteEntityException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId());
        try {
            jdbcTemplate.update(SQL_DELETE_TAG, parameterSource);
        } catch (DataAccessException ex) {
            logger.error("Tag delete request error", ex);
            throw new DeleteEntityException("Tag delete request error", ex);
        }
    }

    @Override
    public List<TagDTO> findAll() throws EntityRetrievalException {
        try {
            return jdbcTemplate.query(SQL_SELECT_FIND_ALL, new TagListResultSetExtractor());
        } catch (DataAccessException ex) {
            logger.error("Request find all tags execution error", ex);
            throw new EntityRetrievalException("Request find all tags execution error", ex);
        }
    }

    @Override
    public TagDTO findByName(String name) throws EntityRetrievalException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        try {
            return jdbcTemplate.query(SQL_SELECT_FIND_NAME, params, new TagResultSetExtractor());
        } catch (DataAccessException ex) {
            logger.error("Request find tag by name execution error", ex);
            throw new EntityRetrievalException("Request find tag by name execution error", ex);
        }
    }
}
