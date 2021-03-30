package com.epam.esm.dao.extractor;

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

public class TagListResultSetExtractor implements ResultSetExtractor<List<TagDTO>> {
    @Override
    public List<TagDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<TagDTO> dtoList = new ArrayList<>();
        List<Certificate> certificateList = new ArrayList<>();
        while (rs.next()) {
            Tag tag = Tag.builder()
                    .id(rs.getLong("tag.id"))
                    .name(rs.getString("tag.name"))
                    .build();
            Long giftCertificateId = rs.getLong("gift_certificate.id");
            if (giftCertificateId > 0) {
                Certificate certificate = Certificate.builder()
                        .id(rs.getLong("gift_certificate.id"))
                        .name(rs.getString("gift_certificate.name"))
                        .description(rs.getString("gift_certificate.description"))
                        .price(rs.getBigDecimal("gift_certificate.price"))
                        .duration(rs.getInt("gift_certificate.duration"))
                        .createDate((rs.getTimestamp("gift_certificate.create_date").toInstant().atZone(ZoneId.systemDefault())))
                        .lastUpdateDate((rs.getTimestamp("gift_certificate.last_update_date").toInstant().atZone(ZoneId.systemDefault())))
                        .build();
                certificateList.add(certificate);
            }
            TagDTO tagDTO = convertToDTO(certificateList, tag);
            if (!dtoList.contains(tagDTO)) {
                dtoList.add(tagDTO);
            }
            dtoList.add(convertToDTO(certificateList, tag));
        }
        return dtoList;
    }

    private TagDTO convertToDTO(List<Certificate> certificateList, Tag tag) {
        TagDTO tagDTO;
        if (certificateList.isEmpty()) {
            tagDTO = TagDTO.builder()
                    .tag(tag)
                    .build();
        } else {
            tagDTO = TagDTO.builder()
                    .tag(tag)
                    .certificates(certificateList)
                    .build();
        }
        return tagDTO;
    }
}
