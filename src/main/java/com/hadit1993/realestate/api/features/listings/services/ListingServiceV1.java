package com.hadit1993.realestate.api.features.listings.services;

import com.hadit1993.realestate.api.features.listings.dtos.CreateListingDto;
import com.hadit1993.realestate.api.features.listings.dtos.ListingDto;
import com.hadit1993.realestate.api.features.listings.entities.Listing;
import com.hadit1993.realestate.api.features.listings.repositories.ListingRepository;
import com.hadit1993.realestate.api.features.users.entities.ListingImage;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.services.UsersServiceV1;
import com.hadit1993.realestate.api.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        final Listing createdListing = listingRepository.save(createListingDto.toEntity(user, null));
        return ListingDto.fromEntity(createdListing);

    }

    public List<ListingDto> getListings(String email) {
        User user = usersService.findUserByEmail(email);
        List<Listing> listings = listingRepository.findByUser(user);
        return  listings.stream().map(ListingDto::fromEntity).toList();
    }

    public Listing getListing(User user, Long listingId) {

        return listingRepository.findByUserAndListingId(user, listingId).orElseThrow(() -> new NotFoundException("no listing found with this id for you"));
    }

    @Transactional
    public void deleteListing(String email, Long listingId) {
        User user = usersService.findUserByEmail(email);
        listingRepository.deleteByUserAndListingId(user, listingId);
    }

    public ListingDto updateListing(CreateListingDto createListingDto, String email, Long listingId) {
        User user = usersService.findUserByEmail(email);
        Listing existing = getListing(user, listingId);

            existing.getImages().removeIf(image -> !createListingDto.images().contains(image.getImageUrl()));
            for (String url: createListingDto.images()) {
                if(existing.getImages().stream().noneMatch(i -> i.getImageUrl().equals(url))) {
                    existing.getImages().add(new ListingImage(url));
                }
            }

        Listing updatedListing = createListingDto.toEntity(user, existing);
        return ListingDto.fromEntity(listingRepository.save(updatedListing));

    }


}
