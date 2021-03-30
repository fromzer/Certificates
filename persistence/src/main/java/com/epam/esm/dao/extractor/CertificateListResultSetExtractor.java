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

public class CertificateListResultSetExtractor implements ResultSetExtractor<List<CertificateDTO>> {

    @Override
    public List<CertificateDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<CertificateDTO> dtoList = new ArrayList<>();
        List<Tag> tagList = null;
        Certificate copyCertificate = null;
        while (rs.next()) {
            Certificate certificate = Certificate.builder()
                    .id(rs.getLong("gift_certificate.id"))
                    .name(rs.getString("gift_certificate.name"))
                    .description(rs.getString("gift_certificate.description"))
                    .price(rs.getBigDecimal("gift_certificate.price"))
                    .duration(rs.getInt("gift_certificate.duration"))
                    .createDate((rs.getTimestamp("gift_certificate.create_date").toInstant().atZone(ZoneId.systemDefault())))
                    .lastUpdateDate((rs.getTimestamp("gift_certificate.last_update_date").toInstant().atZone(ZoneId.systemDefault())))
                    .build();
            if (copyCertificate != null && !copyCertificate.equals(certificate)) {
                dtoList.add(convertToDTO(tagList, certificate));
            } else {
                tagList = new ArrayList<>();
            }
            Long tagId = rs.getLong("tag.id");
            if (tagId > 0) {
                Tag tag = Tag.builder()
                        .id(rs.getLong("tag.id"))
                        .name(rs.getString("tag.name"))
                        .build();
                tagList.add(tag);
            }
            copyCertificate = certificate;
            if (!rs.isBeforeFirst()) {
                dtoList.add(convertToDTO(tagList, certificate));
            }
        }
        return dtoList;
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
