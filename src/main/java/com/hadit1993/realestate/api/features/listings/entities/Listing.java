package com.hadit1993.realestate.api.features.listings.entities;

import com.hadit1993.realestate.api.features.users.entities.ListingImage;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Listing extends Auditable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    private String description;

    @Column(nullable = false, columnDefinition = "VARCHAR(500)")
    private String address;

    @Column(nullable = false)
    private Double regularPrice;

    @Column()
    private Byte discount;

    @Column(nullable = false)
    private Byte bathrooms;

    @Column(nullable = false)
    private Byte bedrooms;

    @Column(nullable = false)
    private Boolean furnished = false;

    @Column(nullable = false)
    private Boolean hasParking = false;

    @Column(nullable = false)
    private Boolean offer = false;

    @Column(columnDefinition = "enum('RENT','SELL') default 'RENT'", nullable = false)
//    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingType listingType = ListingType.RENT;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "listingId", nullable = false)
    private Set<ListingImage> images;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userId", nullable = false)
    private User user;


}
