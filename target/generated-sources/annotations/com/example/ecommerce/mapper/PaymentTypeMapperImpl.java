package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.PaymentTypeDTO;
import com.example.ecommerce.entity.PaymentType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:16+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PaymentTypeMapperImpl implements PaymentTypeMapper {

    @Override
    public PaymentTypeDTO toDto(PaymentType entity) {
        if ( entity == null ) {
            return null;
        }

        PaymentTypeDTO paymentTypeDTO = new PaymentTypeDTO();

        paymentTypeDTO.setId( entity.getId() );
        paymentTypeDTO.setValue( entity.getValue() );

        return paymentTypeDTO;
    }

    @Override
    public PaymentType toEntity(PaymentTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentType paymentType = new PaymentType();

        paymentType.setId( dto.getId() );
        paymentType.setValue( dto.getValue() );

        return paymentType;
    }
}
