package com.epam.esm.dao.extractor;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.ToDTOConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CertificateResultSetExtractor implements ResultSetExtractor<CertificateDTO> {

    @Override
    public CertificateDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
        Set<Tag> tagSet = new LinkedHashSet<>();
        Certificate certificate = null;
        CertificateDTO certificateDTO = null;
        while (rs.next()) {
            if (certificate == null) {
                certificate = Certificate.builder()
                        .id(rs.getLong("gift_certificate.id"))
                        .name(rs.getString("gift_certificate.name"))
                        .description(rs.getString("gift_certificate.description"))
                        .price(rs.getBigDecimal("gift_certificate.price"))
                        .duration(rs.getInt("gift_certificate.duration"))
                        .createDate((rs.getTimestamp("gift_certificate.create_date").toInstant().atZone(ZoneId.systemDefault())))
                        .lastUpdateDate((rs.getTimestamp("gift_certificate.last_update_date").toInstant().atZone(ZoneId.systemDefault())))
                        .build();
            }
            Long tagId = rs.getLong("tag.id");
            if (tagId > 0) {
                Tag tag = Tag.builder()
                        .id(rs.getLong("tag.id"))
                        .name(rs.getString("tag.name"))
                        .build();
                tagSet.add(tag);
            }
        }

        if (certificate != null) {
            certificateDTO = ToDTOConverter.convertToCertificateDTO(certificate);
            certificateDTO.getTags().addAll(tagSet.stream().map(ToDTOConverter::convertToTagDTO).collect(Collectors.toSet()));
        }
        return certificateDTO;
    }
}
