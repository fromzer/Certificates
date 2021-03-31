package com.epam.esm.dao.mysql;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.extractor.CertificateListResultSetExtractor;
import com.epam.esm.dao.extractor.CertificateResultSetExtractor;
import com.epam.esm.dao.extractor.TagListResultSetExtractor;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.UpdateEntityException;
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

import java.util.*;

@Repository
public class CertificateDAOMySQL implements CertificateDAO {
    private static final Logger logger = LoggerFactory.getLogger(CertificateDAOMySQL.class);
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT gift_certificate.*, tag.* FROM gift_certificate\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on gift_certificate.id = gct.gift_certificate_id\n" +
            "LEFT OUTER JOIN tag tag on gct.tag_id = tag.id\n" +
            "WHERE gift_certificate.id = :id;";
    private static final String SQL_SELECT_FIND_ALL = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate;";
    private static final String SQL_SELECT_FIND_TAGS_BY_CERTIFICATE_ID = "SELECT tag.id, tag.name FROM tag\n" +
            "JOIN gift_certificate_tag gct on tag.id = gct.tag_id\n" +
            "JOIN gift_certificate gc on gc.id = gct.gift_certificate_id\n" +
            "WHERE gc.id = :id ;";
    private static final String SQL_INSERT_CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration)\n" +
            "VALUES (:name, :description, :price , :duration);";
    private static final String SQL_INSERT_CREATE_CERTIFICATE_TAG = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (:certificateId, :tagId);";
    private static final String SQL_DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = :id;";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TagDAOMySQL tagDAO;

    @Autowired
    public CertificateDAOMySQL(NamedParameterJdbcTemplate jdbcTemplate, TagDAOMySQL tagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDAO = tagDAO;
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) throws UpdateEntityException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", certificateDTO.getId());
        CertificateDTO result = null;
        try {
            jdbcTemplate.update(getUpdateSqlQuery(certificateDTO), parameterSource);
            if (certificateDTO.getTags() != null) {
                List<Long> tagsIdList = createTags(certificateDTO.getTags());
                AddLinkBetweenTagAndCertificate(certificateDTO.getId(), tagsIdList);
                result = findById(certificateDTO.getId()).get();
            }
        } catch (DataAccessException | CreateEntityException | EntityRetrievalException ex) {
            logger.error("Request update certificate execution error", ex);
            throw new UpdateEntityException("Request update certificate execution error", ex);
        }
        return result;
    }

    @Override
    public Long create(CertificateDTO entity) throws CreateEntityException {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", entity.getName())
                .addValue("description", entity.getDescription())
                .addValue("price", entity.getPrice())
                .addValue("duration", entity.getDuration());
        try {
            jdbcTemplate.update(SQL_INSERT_CREATE_CERTIFICATE, parameterSource, holder);
            Long certificateId = holder.getKey().longValue();
            if (entity.getTags() != null) {
                List<Long> tagsIdList = createTags(entity.getTags());
                AddLinkBetweenTagAndCertificate(certificateId, tagsIdList);
            }
            return certificateId;
        } catch (DataAccessException ex) {
            logger.error("Request create certificate execution error", ex);
            throw new CreateEntityException("Request create certificate execution error", ex);
        }
    }

    private List<Long> createTags(Set<TagDTO> tagList) throws CreateEntityException {
        List<Long> tagsIdList = new ArrayList<>();
        for (TagDTO tagDTO : tagList) {
            tagsIdList.add(tagDAO.create(tagDTO));
        }
        return tagsIdList;
    }

    private void AddLinkBetweenTagAndCertificate(Long certificateId, List<Long> tagsIdList) {
        tagsIdList.forEach(id -> {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("certificateId", certificateId)
                    .addValue("tagId", id);
            jdbcTemplate.update(SQL_INSERT_CREATE_CERTIFICATE_TAG, params);
        });
    }

    @Override
    public Optional<CertificateDTO> findById(Long id) throws EntityRetrievalException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.ofNullable(jdbcTemplate.query(SQL_SELECT_FIND_BY_ID, parameterSource, new CertificateResultSetExtractor()));
        } catch (DataAccessException ex) {
            logger.error("Request find by id certificate execution error", ex);
            throw new EntityRetrievalException("Request find by id certificate execution error", ex);
        }
    }

    @Override
    public void delete(CertificateDTO entity) throws DeleteEntityException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId());
        try {
            jdbcTemplate.update(SQL_DELETE_CERTIFICATE, parameterSource);
        } catch (DataAccessException ex) {
            logger.error("Request delete certificate execution error", ex);
            throw new DeleteEntityException("Request delete certificate execution error", ex);
        }
    }

    @Override
    public List<CertificateDTO> findAll() throws EntityRetrievalException {
        return getCertificateWithTags(SQL_SELECT_FIND_ALL);
    }

    public List<CertificateDTO> findCertificateByParams(String tag, String name, String description, String sort) throws EntityRetrievalException {
        String query = querySelectFindByParams(tag, name, description, sort);
        return getCertificateWithTags(query);
    }

    private String getUpdateSqlQuery(CertificateDTO certificateDTO) {
        String sqlRequest = "UPDATE gift_certificate SET ";
        String update = " last_update_date = NOW() ";
        String where = "WHERE id = :id";
        StringBuilder sb = new StringBuilder();
        sb.append(sqlRequest);
        sb.append(certificateDTO.getName() == null ? "" : "name = '" + certificateDTO.getName() + "', ");
        sb.append(certificateDTO.getDescription() == null ? "" : "description = '" + certificateDTO.getDescription() + "', ");
        sb.append(certificateDTO.getPrice() == null ? "" : "price = " + certificateDTO.getPrice() + ", ");
        sb.append(certificateDTO.getDuration() == null ? "" : "duration = " + certificateDTO.getDuration() + ", ");
        sb.append(update);
        sb.append(where);
        return sb.toString();
    }

    private String querySelectFindByParams(String tag, String name, String description, String sort) {
        String sqlRequest = "SELECT gift_certificate.* FROM gift_certificate\n" +
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

    private List<CertificateDTO> getCertificateWithTags(String query) {
        List<CertificateDTO> tmp = jdbcTemplate.query(query, new CertificateListResultSetExtractor());
        List<CertificateDTO> result = new ArrayList<>();
        for (CertificateDTO certificateDTO : tmp) {
            CertificateDTO cert = certificateDTO;
            cert.getTags().addAll(findTagsByCertificateId(cert.getId()));
            result.add(cert);
        }
        return result;
    }

    private List<TagDTO> findTagsByCertificateId(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(SQL_SELECT_FIND_TAGS_BY_CERTIFICATE_ID, parameterSource, new TagListResultSetExtractor());
    }
}
