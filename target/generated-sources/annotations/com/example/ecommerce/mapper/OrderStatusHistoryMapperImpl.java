package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderStatusHistoryDTO;
import com.example.ecommerce.entity.OrderStatus;
import com.example.ecommerce.entity.OrderStatusHistory;
import com.example.ecommerce.entity.ShopOrder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:17+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class OrderStatusHistoryMapperImpl implements OrderStatusHistoryMapper {

    @Override
    public OrderStatusHistoryDTO toDto(OrderStatusHistory entity) {
        if ( entity == null ) {
            return null;
        }

        OrderStatusHistoryDTO orderStatusHistoryDTO = new OrderStatusHistoryDTO();

        orderStatusHistoryDTO.setOrderId( entityOrderId( entity ) );
        orderStatusHistoryDTO.setStatusId( entityStatusId( entity ) );
        orderStatusHistoryDTO.setId( entity.getId() );
        orderStatusHistoryDTO.setChangedAt( entity.getChangedAt() );

        return orderStatusHistoryDTO;
    }

    @Override
    public OrderStatusHistory toEntity(OrderStatusHistoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();

        orderStatusHistory.setOrder( orderStatusHistoryDTOToShopOrder( dto ) );
        orderStatusHistory.setStatus( orderStatusHistoryDTOToOrderStatus( dto ) );
        orderStatusHistory.setId( dto.getId() );
        orderStatusHistory.setChangedAt( dto.getChangedAt() );

        return orderStatusHistory;
    }

    private Long entityOrderId(OrderStatusHistory orderStatusHistory) {
        if ( orderStatusHistory == null ) {
            return null;
        }
        ShopOrder order = orderStatusHistory.getOrder();
        if ( order == null ) {
            return null;
        }
        Long id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityStatusId(OrderStatusHistory orderStatusHistory) {
        if ( orderStatusHistory == null ) {
            return null;
        }
        OrderStatus status = orderStatusHistory.getStatus();
        if ( status == null ) {
            return null;
        }
        Long id = status.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ShopOrder orderStatusHistoryDTOToShopOrder(OrderStatusHistoryDTO orderStatusHistoryDTO) {
        if ( orderStatusHistoryDTO == null ) {
            return null;
        }

        ShopOrder shopOrder = new ShopOrder();

        shopOrder.setId( orderStatusHistoryDTO.getOrderId() );

        return shopOrder;
    }

    protected OrderStatus orderStatusHistoryDTOToOrderStatus(OrderStatusHistoryDTO orderStatusHistoryDTO) {
        if ( orderStatusHistoryDTO == null ) {
            return null;
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setId( orderStatusHistoryDTO.getStatusId() );

        return orderStatus;
    }
}
