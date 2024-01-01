package com.hadit1993.realestate.api.features.listings.dtos;

import com.hadit1993.realestate.api.features.listings.entities.Listing;
import com.hadit1993.realestate.api.features.listings.entities.ListingType;
import com.hadit1993.realestate.api.features.users.entities.ListingImage;
import java.util.Date;
import java.util.List;

public record ListingDto (

        Long listingId,
        String name,
        String description,
        String address,
        Double regularPrice,
        Byte discount,
        Double discountedPrice,
        Byte bathrooms,
        Byte bedrooms,
        Boolean furnished,
        Boolean hasParking,
        Boolean offer,
        ListingType listingType,
        List<String> images,
        Date createdDate,
        Date lastModifiedDate

)  {
    
    public static ListingDto fromEntity(Listing listing) {

        Double discountedPrice = null;
        if(listing.getDiscount() != null) {
           discountedPrice = listing.getRegularPrice() - (listing.getRegularPrice() * (listing.getDiscount() / 100));
        }
        return new ListingDto(
                listing.getListingId(),
                listing.getName(),
                listing.getDescription(),
                listing.getAddress(),
                listing.getRegularPrice(),
                listing.getDiscount(),
                discountedPrice,
                listing.getBathrooms(),
                listing.getBedrooms(),
                listing.getFurnished(),
                listing.getHasParking(),
                listing.getOffer(),
                listing.getListingType(),
                listing.getImages().stream().map(ListingImage::getImageUrl).toList(),
                listing.getCreationDate(),
                listing.getLastModifiedDate()
        );
    }
}
