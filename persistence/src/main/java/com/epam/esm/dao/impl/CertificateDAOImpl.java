package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.extractor.CertificateListResultSetExtractor;
import com.epam.esm.dao.extractor.CertificateResultSetExtractor;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DAOException;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CertificateDAOImpl implements CertificateDAO {
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT gift_certificate.*, tag.* FROM gift_certificate\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on gift_certificate.id = gct.gift_certificate_id\n" +
            "LEFT OUTER JOIN tag tag on gct.tag_id = tag.id\n" +
            "WHERE gift_certificate.id = :id;";
    private static final String SQL_SELECT_FIND_ALL = "SELECT gift_certificate.*, tag.* FROM gift_certificate\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on gift_certificate.id = gct.gift_certificate_id\n" +
            "LEFT OUTER JOIN tag tag on gct.tag_id = tag.id";
    private static final String SQL_INSERT_CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration)\n" +
            "VALUES (:name, :description, :price , :duration);";
    private static final String SQL_INSERT_CREATE_CERTIFICATE_TAG = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (:certificateId, :tagId);";
    private static final String SQL_DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = :id;";
    // private static final String SQL_UPDATE_TAG = "UPDATE tag SET name = :name WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TagDAOImpl tagDAO;

    @Autowired
    public CertificateDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, TagDAOImpl tagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDAO = tagDAO;
    }

    @Override
    public CertificateDTO update(Map<String, Object> params, Set<Tag> tags) throws DAOException {
        return null;
    }

//    @Override
//    public Long create(CertificateDTO entity) throws DAOException {
//        KeyHolder holder = new GeneratedKeyHolder();
//        SqlParameterSource parameterSource = new MapSqlParameterSource()
//                .addValue("name", entity.getName())
//                .addValue("description", entity.getDescription())
//                .addValue("price", entity.getPrice())
//                .addValue("duration", entity.getDuration());
//        jdbcTemplate.update(SQL_INSERT_CREATE_CERTIFICATE, parameterSource, holder);
//        Long certificateId = holder.getKey().longValue();
//        if (entity.getTags() != null) {
//            List<Long> tagsIdList = createTags(entity.getTags());
//            AddLinkBetweenTagAndCertificate(certificateId, tagsIdList);
//        }
//        return certificateId;
//    }
//
//    @NotNull
//    private List<Long> createTags(List<TagD> tagList) {
//        return tagList.stream()
//                .map(tag -> tagDAO.create(TagDTO.builder().tag(tag).build()))
//                .collect(Collectors.toList());
//    }

    private void AddLinkBetweenTagAndCertificate(Long certificateId, List<Long> tagsIdList) {
        tagsIdList.forEach(id -> {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("certificateId", certificateId)
                    .addValue("tagId", id);
            jdbcTemplate.update(SQL_INSERT_CREATE_CERTIFICATE_TAG, params);
        });
    }

    @Override
    public Long create(CertificateDTO entity) throws DAOException {
        return null;
    }

    @Override
    public Optional<CertificateDTO> findById(Long id) throws DAOException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return Optional.of(jdbcTemplate.query(SQL_SELECT_FIND_BY_ID, parameterSource, new CertificateResultSetExtractor()));
    }

    @Override
    public void delete(CertificateDTO entity) throws DAOException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId());
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE, parameterSource);
    }

    @Override
    public List<CertificateDTO> findAll() throws DAOException {
        return jdbcTemplate.query(SQL_SELECT_FIND_ALL, new CertificateListResultSetExtractor());
    }

    private String querySelectFindByCreator(String tag, String name, String description, String sort) {
        String sqlRequest = "SELECT gift_certificate.*, tag.* FROM gift_certificate\n" +
                "LEFT OUTER JOIN gift_certificate_tag gct on gift_certificate.id = gct.gift_certificate_id\n" +
                "LEFT OUTER JOIN tag tag on gct.tag_id = tag.id ";
        if (sort == null) {
            sort = "name,ASC";
        }
        String[] sortParams = sort.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append(sqlRequest);
        if (tag != null) {
            sb.append("WHERE tag.name LIKE '%" + tag + "%'" + " ORDER BY gift_certificate." + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else if (name != null) {
            sb.append("WHERE gift_certificate.name LIKE '%" + name + "%'" + " ORDER BY gift_certificate." + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else if (description != null) {
            sb.append("WHERE gift_certificate.description LIKE '%" + description + "%'" + " ORDER BY gift_certificate." + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else {
            sb.append("ORDER BY gift_certificate." + sortParams[0] + " " + sortParams[1]);
        }
        return sb.toString();
    }

    public List<CertificateDTO> findCertificateByParams(String tag, String name, String description, String sort) {
        String query = querySelectFindByCreator(tag, name, description, sort);
        return jdbcTemplate.query(query, new CertificateListResultSetExtractor());
    }
}
