package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ShoppingCartDTO;
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.SiteUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:16+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ShoppingCartMapperImpl implements ShoppingCartMapper {

    @Override
    public ShoppingCartDTO toDto(ShoppingCart cart) {
        if ( cart == null ) {
            return null;
        }

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();

        shoppingCartDTO.setUserId( cartUserId( cart ) );
        shoppingCartDTO.setId( cart.getId() );

        return shoppingCartDTO;
    }

    @Override
    public ShoppingCart toEntity(ShoppingCartDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setUser( shoppingCartDTOToSiteUser( dto ) );
        shoppingCart.setId( dto.getId() );

        return shoppingCart;
    }

    private Long cartUserId(ShoppingCart shoppingCart) {
        if ( shoppingCart == null ) {
            return null;
        }
        SiteUser user = shoppingCart.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected SiteUser shoppingCartDTOToSiteUser(ShoppingCartDTO shoppingCartDTO) {
        if ( shoppingCartDTO == null ) {
            return null;
        }

        SiteUser siteUser = new SiteUser();

        siteUser.setId( shoppingCartDTO.getUserId() );

        return siteUser;
    }
}
