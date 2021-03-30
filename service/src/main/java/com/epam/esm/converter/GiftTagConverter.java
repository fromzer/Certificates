package com.epam.esm.converter;

import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftTag;
import com.epam.esm.entity.Tag;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class GiftTagConverter {
    public static GiftTagDTO convertToIncomingEntity(TagDTO tagDTO) {
        GiftTagDTO giftTagDTO;
        GiftTag giftTag = new GiftTag();
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        BeanUtils.copyProperties(tagDTO.getTag(), giftTag);
        if (tagDTO.getCertificates() != null) {
            for (Certificate certificate : tagDTO.getCertificates()) {
                GiftCertificate giftCertificate = new GiftCertificate();
                BeanUtils.copyProperties(certificate, giftCertificate);
                giftCertificateList.add(giftCertificate);
            }
        }
        giftTagDTO = GiftTagDTO.builder()
                .giftTag(giftTag)
                .giftCertificates(giftCertificateList)
                .build();
        return giftTagDTO;
    }

    public static TagDTO convertToOutboundEntity(GiftTagDTO giftTagDTO) {
        TagDTO tagDTO;
        Tag tag = new Tag();
        List<Certificate> certificateList = new ArrayList<>();
        BeanUtils.copyProperties(giftTagDTO.getGiftTag(), tag);
        if (giftTagDTO.getGiftCertificates() != null) {
            for (GiftCertificate giftCertificate : giftTagDTO.getGiftCertificates()) {
                Certificate certificate = new Certificate();
                BeanUtils.copyProperties(giftCertificate, certificate);
                certificateList.add(certificate);
            }
        }
        tagDTO = TagDTO.builder()
                .tag(tag)
                .certificates(certificateList)
                .build();
        return tagDTO;
    }

}
