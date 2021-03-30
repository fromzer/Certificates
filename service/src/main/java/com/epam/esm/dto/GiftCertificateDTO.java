package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftTag;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GiftCertificateDTO {
    private GiftCertificate giftCertificate;
    private List<GiftTag> giftTags;
}
