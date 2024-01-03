package com.hadit1993.realestate.api.features.listings.dtos;

import com.hadit1993.realestate.api.features.listings.entities.Listing;
import com.hadit1993.realestate.api.features.listings.entities.ListingType;
import com.hadit1993.realestate.api.features.users.entities.ListingImage;
import com.hadit1993.realestate.api.features.users.entities.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record CreateListingDto(

        @NotBlank(message = "name is required")
        @Size(min = 3, max = 100, message = "name must be between 3 and 100 characters long")
        String name,
        @NotBlank(message = "description is required")
        @Size(min = 10, max = 1000, message = "description must be between 10 and 1000 characters long")
        String description,
        @NotBlank(message = "address is required")
        @Size(min = 10, max = 500,  message = "address must be between 10 and 500 characters long")
        String address,
        @NotNull(message = "regularPrice is required")
        Double regularPrice,
        @Max(value = 100, message = "discount must be between 0 and 100")
        Byte discount,

        @NotNull( message = "bathrooms is required")
        Byte bathrooms,
        @NotNull( message = "bedrooms is required")
        Byte bedrooms,

        @NotNull( message = "furnished is required")
        Boolean furnished,

        @NotNull( message = "hasParking is required")
        Boolean hasParking,

        @NotNull( message = "offer is required")
        Boolean offer,

        @NotNull(message = "listingType is required")
        @Enumerated(EnumType.STRING)
        ListingType listingType,
        @NotNull(message = "images are required")
        @NotEmpty(message = "images must not be empty")
        List<String> images

) {

       public Listing toEntity(User user, Listing existing) {
               Listing listing = existing != null ? existing : new Listing();
               listing.setName(name());
               listing.setDescription(description());
               listing.setAddress(address());
               listing.setRegularPrice(regularPrice());
               listing.setDiscount(discount());
               listing.setBathrooms(bathrooms());
               listing.setBedrooms(bedrooms());
               listing.setFurnished(furnished());
               listing.setHasParking(hasParking());
               listing.setOffer(offer());
               listing.setListingType(listingType());
                if(existing == null) listing.setImages(images.stream().map(ListingImage::new).collect(Collectors.toSet()));
               listing.setUser(user);
               return listing;
       }
}
