package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.UserPaymentMethodDTO;
import com.example.ecommerce.entity.PaymentType;
import com.example.ecommerce.entity.SiteUser;
import com.example.ecommerce.entity.UserPaymentMethod;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:17+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserPaymentMethodMapperImpl implements UserPaymentMethodMapper {

    @Override
    public UserPaymentMethodDTO toDto(UserPaymentMethod entity) {
        if ( entity == null ) {
            return null;
        }

        UserPaymentMethodDTO userPaymentMethodDTO = new UserPaymentMethodDTO();

        userPaymentMethodDTO.setUserId( entityUserId( entity ) );
        userPaymentMethodDTO.setPaymentTypeId( entityPaymentTypeId( entity ) );
        userPaymentMethodDTO.setId( entity.getId() );
        userPaymentMethodDTO.setProvider( entity.getProvider() );
        userPaymentMethodDTO.setAccountNumber( entity.getAccountNumber() );
        userPaymentMethodDTO.setExpiryDate( entity.getExpiryDate() );
        userPaymentMethodDTO.setIsDefault( entity.getIsDefault() );

        return userPaymentMethodDTO;
    }

    @Override
    public UserPaymentMethod toEntity(UserPaymentMethodDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserPaymentMethod userPaymentMethod = new UserPaymentMethod();

        userPaymentMethod.setUser( userPaymentMethodDTOToSiteUser( dto ) );
        userPaymentMethod.setPaymentType( userPaymentMethodDTOToPaymentType( dto ) );
        userPaymentMethod.setId( dto.getId() );
        userPaymentMethod.setProvider( dto.getProvider() );
        userPaymentMethod.setAccountNumber( dto.getAccountNumber() );
        userPaymentMethod.setExpiryDate( dto.getExpiryDate() );
        userPaymentMethod.setIsDefault( dto.getIsDefault() );

        return userPaymentMethod;
    }

    private Long entityUserId(UserPaymentMethod userPaymentMethod) {
        if ( userPaymentMethod == null ) {
            return null;
        }
        SiteUser user = userPaymentMethod.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityPaymentTypeId(UserPaymentMethod userPaymentMethod) {
        if ( userPaymentMethod == null ) {
            return null;
        }
        PaymentType paymentType = userPaymentMethod.getPaymentType();
        if ( paymentType == null ) {
            return null;
        }
        Long id = paymentType.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected SiteUser userPaymentMethodDTOToSiteUser(UserPaymentMethodDTO userPaymentMethodDTO) {
        if ( userPaymentMethodDTO == null ) {
            return null;
        }

        SiteUser siteUser = new SiteUser();

        siteUser.setId( userPaymentMethodDTO.getUserId() );

        return siteUser;
    }

    protected PaymentType userPaymentMethodDTOToPaymentType(UserPaymentMethodDTO userPaymentMethodDTO) {
        if ( userPaymentMethodDTO == null ) {
            return null;
        }

        PaymentType paymentType = new PaymentType();

        paymentType.setId( userPaymentMethodDTO.getPaymentTypeId() );

        return paymentType;
    }
}
