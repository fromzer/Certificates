package com.epam.esm.dao.extractor;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TagResultSetExtractor implements ResultSetExtractor<TagDTO> {

    @Override
    public TagDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Certificate> certificateList = new ArrayList<>();
        Tag tag = null;
        while (rs.next()) {
            if (tag == null) {
                tag = Tag.builder()
                        .id(rs.getLong("tag.id"))
                        .name(rs.getString("tag.name"))
                        .build();
            }
            Long certificateId = rs.getLong("gift_certificate.id");
            if (certificateId > 0) {
                Certificate certificate = Certificate.builder()
                        .id(rs.getLong("gift_certificate.id"))
                        .name(rs.getString("gift_certificate.name"))
                        .description(rs.getString("gift_certificate.description"))
                        .price(rs.getBigDecimal("gift_certificate.price"))
                        .duration(rs.getInt("gift_certificate.duration"))
                        .createDate((rs.getTimestamp("gift_certificate.create_date").toInstant().atZone(ZoneId.systemDefault())))
                        .lastUpdateDate((rs.getTimestamp("gift_certificate.last_update_date").toInstant().atZone(ZoneId.systemDefault())))
                        .build();
                if (!certificateList.contains(certificate)) {
                    certificateList.add(certificate);
                }
            }
        }
        return convertToDTO(certificateList, tag);
    }

    private TagDTO convertToDTO(List<Certificate> certificateList, Tag tag) {
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        for (Certificate certificate : certificateList) {
            CertificateDTO certificateDTO = CertificateDTO.builder()
                    .id(certificate.getId())
                    .name(certificate.getName())
                    .description(certificate.getDescription())
                    .price(certificate.getPrice())
                    .duration(certificate.getDuration())
                    .createDate(certificate.getCreateDate())
                    .lastUpdateDate(certificate.getLastUpdateDate())
                    .build();
            certificateDTOList.add(certificateDTO);
        }
        TagDTO tagDTO = TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .certificates(certificateDTOList)
                .build();
        return tagDTO;
    }
}
