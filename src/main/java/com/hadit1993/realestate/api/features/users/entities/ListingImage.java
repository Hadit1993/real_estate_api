package com.hadit1993.realestate.api.features.users.entities;


import com.hadit1993.realestate.api.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class ListingImage extends Auditable {

    public ListingImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String imageUrl;

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        final ListingImage newObj = (ListingImage) obj;
        return newObj.getImageUrl().equals(imageUrl) && newObj.getImageId().equals(imageId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, imageUrl);
    }
}
