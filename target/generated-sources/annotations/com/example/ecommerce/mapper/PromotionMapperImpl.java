package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.PromotionDTO;
import com.example.ecommerce.entity.Promotion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:16+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PromotionMapperImpl implements PromotionMapper {

    @Override
    public PromotionDTO toDto(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionDTO promotionDTO = new PromotionDTO();

        promotionDTO.setId( promotion.getId() );
        promotionDTO.setName( promotion.getName() );
        promotionDTO.setDescription( promotion.getDescription() );
        promotionDTO.setDiscountRate( promotion.getDiscountRate() );
        promotionDTO.setStartDate( promotion.getStartDate() );
        promotionDTO.setEndDate( promotion.getEndDate() );

        return promotionDTO;
    }

    @Override
    public Promotion toEntity(PromotionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Promotion promotion = new Promotion();

        promotion.setId( dto.getId() );
        promotion.setName( dto.getName() );
        promotion.setDescription( dto.getDescription() );
        promotion.setDiscountRate( dto.getDiscountRate() );
        promotion.setStartDate( dto.getStartDate() );
        promotion.setEndDate( dto.getEndDate() );

        return promotion;
    }
}
