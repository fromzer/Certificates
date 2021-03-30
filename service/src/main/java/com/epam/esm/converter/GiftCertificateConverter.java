package com.epam.esm.converter;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftTag;
import com.epam.esm.entity.Tag;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class GiftCertificateConverter {

    public static GiftCertificateDTO convertToIncomingEntity(CertificateDTO certificateDTO) {
        GiftCertificateDTO giftCertificateDTO;
        GiftCertificate giftCertificate = new GiftCertificate();
        List<GiftTag> giftTagList = new ArrayList<>();
        BeanUtils.copyProperties(certificateDTO.getCertificate(), giftCertificate);
        if (certificateDTO.getTags() != null) {
            for (Tag tag : certificateDTO.getTags()) {
                GiftTag giftTag = new GiftTag();
                BeanUtils.copyProperties(tag, giftTag);
                giftTagList.add(giftTag);
            }
        }
        giftCertificateDTO = GiftCertificateDTO.builder()
                .giftCertificate(giftCertificate)
                .giftTags(giftTagList)
                .build();
        return giftCertificateDTO;
    }


    public static CertificateDTO convertToOutboundEntity(GiftCertificateDTO giftCertificateDTO) {
        CertificateDTO certificateDTO;
        Certificate certificate = new Certificate();
        List<Tag> tagList = new ArrayList<>();
        BeanUtils.copyProperties(giftCertificateDTO.getGiftCertificate(), certificate);
        if (giftCertificateDTO.getGiftTags() != null) {
            for (GiftTag giftTag : giftCertificateDTO.getGiftTags()) {
                Tag tag = new Tag();
                BeanUtils.copyProperties(giftTag, tag);
                tagList.add(tag);
            }
        }
        certificateDTO = CertificateDTO.builder()
                .certificate(certificate)
                .tags(tagList)
                .build();
        return certificateDTO;
    }
}
