package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.extractor.TagListResultSetExtractor;
import com.epam.esm.dao.extractor.TagResultSetExtractor;
import com.epam.esm.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TagDAOImpl implements TagDAO {
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT tag.*, gift_certificate.* FROM tag\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on tag.id = gct.tag_id\n" +
            "LEFT OUTER JOIN gift_certificate gift_certificate on gift_certificate.id = gct.gift_certificate_id WHERE tag.id = :id;";
    private static final String SQL_SELECT_FIND_NAME = "SELECT tag.*, gift_certificate.* FROM tag\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on tag.id = gct.tag_id\n" +
            "LEFT OUTER JOIN gift_certificate gift_certificate on gift_certificate.id = gct.gift_certificate_id WHERE tag.name=:name;";
    private static final String SQL_SELECT_FIND_ALL = "SELECT tag.*, gift_certificate.* FROM tag\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on tag.id = gct.tag_id\n" +
            "LEFT OUTER JOIN gift_certificate gift_certificate on gift_certificate.id = gct.gift_certificate_id";
    private static final String SQL_INSERT_CREATE_TAG = "INSERT INTO tag(name) VALUE (:name);";
    private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id = :id;";
    private static final String SQL_UPDATE_TAG = "UPDATE tag SET name = :name WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(TagDTO entity) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", entity.getName());
        jdbcTemplate.update(SQL_INSERT_CREATE_TAG, parameterSource, holder);
        return holder.getKey().longValue();
    }

    @Override
    public Optional<TagDTO> findById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return Optional.of(jdbcTemplate.query(SQL_SELECT_FIND_BY_ID, parameterSource, new TagResultSetExtractor()));
    }

    @Override
    public void delete(TagDTO entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId());
        jdbcTemplate.update(SQL_DELETE_TAG, parameterSource);
    }

    @Override
    public List<TagDTO> findAll() {
        return jdbcTemplate.query(SQL_SELECT_FIND_ALL, new TagListResultSetExtractor());
    }

    @Override
    public TagDTO findByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return jdbcTemplate.query(SQL_SELECT_FIND_NAME, params, new TagResultSetExtractor());
    }
}
