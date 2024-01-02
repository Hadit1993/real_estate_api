package com.hadit1993.realestate.api.features.listings.services;

import com.hadit1993.realestate.api.features.listings.dtos.CreateListingDto;
import com.hadit1993.realestate.api.features.listings.dtos.ListingDto;
import com.hadit1993.realestate.api.features.listings.entities.Listing;
import com.hadit1993.realestate.api.features.listings.repositories.ListingRepository;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.services.UsersServiceV1;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListingServiceV1 {
    
    
    private final ListingRepository listingRepository;
    private final UsersServiceV1 usersService;

    public ListingServiceV1(ListingRepository listingRepository, UsersServiceV1 usersService) {
        this.listingRepository = listingRepository;
        this.usersService = usersService;
    }

    public ListingDto createListing(String email, CreateListingDto createListingDto) {
        User user = usersService.findUserByEmail(email);
        final Listing createdListing = listingRepository.save(createListingDto.toEntity(user));
        return ListingDto.fromEntity(createdListing);

    }

    public List<ListingDto> getListings(String email) {
        User user = usersService.findUserByEmail(email);
        List<Listing> listings = listingRepository.findByUser(user);
        return  listings.stream().map(ListingDto::fromEntity).toList();
    }

    @Transactional
    public void deleteListing(String email, Long listingId) {
        User user = usersService.findUserByEmail(email);
        listingRepository.deleteByUserAndListingId(user, listingId);
    }


}
