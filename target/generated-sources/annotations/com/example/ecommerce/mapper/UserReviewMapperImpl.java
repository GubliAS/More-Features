package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.UserReviewDTO;
import com.example.ecommerce.entity.SiteUser;
import com.example.ecommerce.entity.UserReview;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T23:35:17+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserReviewMapperImpl implements UserReviewMapper {

    @Override
    public UserReviewDTO toDto(UserReview review) {
        if ( review == null ) {
            return null;
        }

        UserReviewDTO userReviewDTO = new UserReviewDTO();

        userReviewDTO.setUserId( reviewUserId( review ) );
        userReviewDTO.setId( review.getId() );
        userReviewDTO.setOrderedProductId( review.getOrderedProductId() );
        userReviewDTO.setRatingValue( review.getRatingValue() );
        userReviewDTO.setComment( review.getComment() );

        return userReviewDTO;
    }

    @Override
    public UserReview toEntity(UserReviewDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserReview userReview = new UserReview();

        userReview.setUser( userReviewDTOToSiteUser( dto ) );
        userReview.setId( dto.getId() );
        userReview.setOrderedProductId( dto.getOrderedProductId() );
        userReview.setRatingValue( dto.getRatingValue() );
        userReview.setComment( dto.getComment() );

        return userReview;
    }

    private Long reviewUserId(UserReview userReview) {
        if ( userReview == null ) {
            return null;
        }
        SiteUser user = userReview.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected SiteUser userReviewDTOToSiteUser(UserReviewDTO userReviewDTO) {
        if ( userReviewDTO == null ) {
            return null;
        }

        SiteUser siteUser = new SiteUser();

        siteUser.setId( userReviewDTO.getUserId() );

        return siteUser;
    }
}
