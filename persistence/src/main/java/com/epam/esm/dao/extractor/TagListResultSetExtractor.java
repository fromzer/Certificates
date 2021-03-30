package com.epam.esm.dao.extractor;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.ToDTOConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagListResultSetExtractor implements ResultSetExtractor<List<TagDTO>> {
    @Override
    public List<TagDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<TagDTO> dtoList = new ArrayList<>();
        while (rs.next()) {
            Tag tag = Tag.builder()
                        .id(rs.getLong("tag.id"))
                        .name(rs.getString("tag.name"))
                        .build();
            dtoList.add(ToDTOConverter.convertToTagDTO(tag));
        }
        return dtoList;
    }
}
