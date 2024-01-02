package com.hadit1993.realestate.api.features.listings.entities;

import com.hadit1993.realestate.api.features.users.entities.ListingImage;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
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
    private List<ListingImage> images;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public void setDiscount(Byte discount) {
        this.discount = discount;
    }

    public void setBathrooms(Byte bathrooms) {
        this.bathrooms = bathrooms;
    }

    public void setBedrooms(Byte bedrooms) {
        this.bedrooms = bedrooms;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public void setHasParking(Boolean hasParking) {
        this.hasParking = hasParking;
    }

    public void setOffer(Boolean offer) {
        this.offer = offer;
    }

    public void setListingType(ListingType listingType) {
        this.listingType = listingType;
    }

    public void setImages(List<ListingImage> images) {
        this.images = images;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
