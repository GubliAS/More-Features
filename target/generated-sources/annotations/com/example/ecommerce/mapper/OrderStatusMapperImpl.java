package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderStatusDTO;
import com.example.ecommerce.entity.OrderStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:17+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class OrderStatusMapperImpl implements OrderStatusMapper {

    @Override
    public OrderStatusDTO toDto(OrderStatus entity) {
        if ( entity == null ) {
            return null;
        }

        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

        orderStatusDTO.setId( entity.getId() );
        orderStatusDTO.setStatus( entity.getStatus() );

        return orderStatusDTO;
    }

    @Override
    public OrderStatus toEntity(OrderStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setId( dto.getId() );
        orderStatus.setStatus( dto.getStatus() );

        return orderStatus;
    }
}
