package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.AddressDTO;
import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.Country;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:17+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressDTO toDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setCountryId( addressCountryId( address ) );
        addressDTO.setId( address.getId() );
        addressDTO.setUnitNumber( address.getUnitNumber() );
        addressDTO.setStreetNumber( address.getStreetNumber() );
        addressDTO.setAddressLine1( address.getAddressLine1() );
        addressDTO.setAddressLine2( address.getAddressLine2() );
        addressDTO.setCity( address.getCity() );
        addressDTO.setRegion( address.getRegion() );
        addressDTO.setPostalCode( address.getPostalCode() );

        return addressDTO;
    }

    @Override
    public Address toEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setCountry( addressDTOToCountry( dto ) );
        address.setId( dto.getId() );
        address.setUnitNumber( dto.getUnitNumber() );
        address.setStreetNumber( dto.getStreetNumber() );
        address.setAddressLine1( dto.getAddressLine1() );
        address.setAddressLine2( dto.getAddressLine2() );
        address.setCity( dto.getCity() );
        address.setRegion( dto.getRegion() );
        address.setPostalCode( dto.getPostalCode() );

        return address;
    }

    private Long addressCountryId(Address address) {
        if ( address == null ) {
            return null;
        }
        Country country = address.getCountry();
        if ( country == null ) {
            return null;
        }
        Long id = country.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Country addressDTOToCountry(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Country country = new Country();

        country.setId( addressDTO.getCountryId() );

        return country;
    }
}
