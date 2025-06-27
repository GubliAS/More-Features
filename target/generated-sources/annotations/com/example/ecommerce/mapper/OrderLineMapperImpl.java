package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderLineDTO;
import com.example.ecommerce.entity.OrderLine;
import com.example.ecommerce.entity.ProductItem;
import com.example.ecommerce.entity.ShopOrder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T16:47:41+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Oracle Corporation)"
)
@Component
public class OrderLineMapperImpl implements OrderLineMapper {

    @Override
    public OrderLineDTO toDto(OrderLine orderLine) {
        if ( orderLine == null ) {
            return null;
        }

        OrderLineDTO orderLineDTO = new OrderLineDTO();

        orderLineDTO.setId( orderLine.getId() );
        orderLineDTO.setPrice( orderLine.getPrice() );
        orderLineDTO.setQty( orderLine.getQty() );
        orderLineDTO.setProductItemId( orderLineProductItemId( orderLine ) );
        orderLineDTO.setOrderId( orderLineOrderId( orderLine ) );

        return orderLineDTO;
    }

    @Override
    public OrderLine toEntity(OrderLineDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderLine orderLine = new OrderLine();

        orderLine.setProductItem( orderLineDTOToProductItem( dto ) );
        orderLine.setOrder( orderLineDTOToShopOrder( dto ) );
        orderLine.setId( dto.getId() );
        orderLine.setPrice( dto.getPrice() );
        if ( dto.getQty() != null ) {
            orderLine.setQty( dto.getQty() );
        }

        return orderLine;
    }

    private Long orderLineProductItemId(OrderLine orderLine) {
        if ( orderLine == null ) {
            return null;
        }
        ProductItem productItem = orderLine.getProductItem();
        if ( productItem == null ) {
            return null;
        }
        Long id = productItem.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long orderLineOrderId(OrderLine orderLine) {
        if ( orderLine == null ) {
            return null;
        }
        ShopOrder order = orderLine.getOrder();
        if ( order == null ) {
            return null;
        }
        Long id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ProductItem orderLineDTOToProductItem(OrderLineDTO orderLineDTO) {
        if ( orderLineDTO == null ) {
            return null;
        }

        ProductItem productItem = new ProductItem();

        productItem.setId( orderLineDTO.getProductItemId() );

        return productItem;
    }

    protected ShopOrder orderLineDTOToShopOrder(OrderLineDTO orderLineDTO) {
        if ( orderLineDTO == null ) {
            return null;
        }

        ShopOrder shopOrder = new ShopOrder();

        shopOrder.setId( orderLineDTO.getOrderId() );

        return shopOrder;
    }
}
