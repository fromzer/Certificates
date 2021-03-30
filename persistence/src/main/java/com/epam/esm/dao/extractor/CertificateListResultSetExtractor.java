package com.epam.esm.dao.extractor;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.util.ToDTOConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;

public class CertificateListResultSetExtractor implements ResultSetExtractor<List<CertificateDTO>> {

    @Override
    public List<CertificateDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<CertificateDTO> dtoList = new ArrayList<>();
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
            CertificateDTO certificateDTO = ToDTOConverter.convertToCertificateDTO(certificate);
            if (!dtoList.contains(certificateDTO)) {
                dtoList.add(certificateDTO);
            }
        }
        return dtoList;
    }
}
