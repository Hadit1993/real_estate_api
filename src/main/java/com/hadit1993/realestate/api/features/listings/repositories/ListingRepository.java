package com.hadit1993.realestate.api.features.listings.repositories;

import com.hadit1993.realestate.api.features.listings.entities.Listing;
import com.hadit1993.realestate.api.features.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByUser(User user);
}
