package com.epam.esm.dao.extractor;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CertificateResultSetExtractor implements ResultSetExtractor<CertificateDTO> {

    @Override
    public CertificateDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Tag> tagList = new ArrayList<>();
        Certificate certificate = null;
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
                tagList.add(tag);
            }
        }
        return convertToDTO(tagList, certificate);
    }

    private CertificateDTO convertToDTO(List<Tag> tagList, Certificate certificate) {
        CertificateDTO certificateDTO;
        if (tagList.isEmpty()) {
            certificateDTO = CertificateDTO.builder()
                    .certificate(certificate)
                    .build();
        } else {
            certificateDTO = CertificateDTO.builder()
                    .certificate(certificate)
                    .tags(tagList)
                    .build();
        }
        return certificateDTO;
    }
}
