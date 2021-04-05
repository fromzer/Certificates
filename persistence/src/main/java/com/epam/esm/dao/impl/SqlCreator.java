package com.epam.esm.dao.impl;

import com.epam.esm.entity.Certificate;

public class SqlCreator {
    private static final String ORDER_BY = " ORDER BY gift_certificate.";
    private static final String WHERE_TAG_NAME = "WHERE tag.name LIKE ";
    private static final String WHERE_CERTIFICATE_NAME = "WHERE gift_certificate.name LIKE ";
    private static final String WHERE_CERTIFICATE_DESCRIPTION = "WHERE gift_certificate.description LIKE ";

    private SqlCreator() {
    }

    public static String getQuerySelectFindByParams(String tag, String name, String description, String sort, String sqlRequest) {
        if (sort == null) {
            sort = "name,ASC";
        }
        String[] sortParams = sort.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append(sqlRequest);
        if (tag != null) {
            sb.append(WHERE_TAG_NAME + "'%" + tag + "%'" + ORDER_BY + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else if (name != null) {
            sb.append(WHERE_CERTIFICATE_NAME + "'%" + name + "%'" + ORDER_BY + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else if (description != null) {
            sb.append(WHERE_CERTIFICATE_DESCRIPTION + "'%" + description + "%'" + ORDER_BY + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else {
            sb.append(ORDER_BY + sortParams[0] + " " + sortParams[1]);
        }
        return sb.toString();
    }

    public static String getQueryUpdateByPart(Certificate certificate) {
        String sqlRequest = "UPDATE gift_certificate SET ";
        String update = " last_update_date = NOW() ";
        String where = "WHERE id = :id";
        StringBuilder sb = new StringBuilder();
        sb.append(sqlRequest);
        sb.append(certificate.getName() == null ? "" : "name = '" + certificate.getName() + "', ");
        sb.append(certificate.getDescription() == null ? "" : "description = '" + certificate.getDescription() + "', ");
        sb.append(certificate.getPrice() == null ? "" : "price = " + certificate.getPrice() + ", ");
        sb.append(certificate.getDuration() == null ? "" : "duration = " + certificate.getDuration() + ", ");
        sb.append(update);
        sb.append(where);
        return sb.toString();
    }
}
