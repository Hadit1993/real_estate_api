package com.hadit1993.realestate.api.features.listings.repositories;

import com.hadit1993.realestate.api.features.listings.entities.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
