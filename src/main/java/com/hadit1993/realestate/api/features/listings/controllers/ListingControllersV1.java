package com.hadit1993.realestate.api.features.listings.controllers;

import com.hadit1993.realestate.api.features.listings.dtos.CreateListingDto;
import com.hadit1993.realestate.api.features.listings.dtos.ListingDto;
import com.hadit1993.realestate.api.features.listings.services.ListingServiceV1;
import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingControllersV1 {

    private final ListingServiceV1 listingService;

    public ListingControllersV1(ListingServiceV1 listingService) {
        this.listingService = listingService;
    }


    @PostMapping
    public ResponseEntity<ResponseTemplate<ListingDto>> createListing(@RequestBody @Valid CreateListingDto listingDto, Authentication authentication) {

        String email = authentication.getName();

        ListingDto listing = listingService.createListing(email, listingDto);

        return ResponseTemplate.<ListingDto>builder().data(listing).message("listing created successfully").build().convertToResponse(HttpStatus.CREATED);

    }
}