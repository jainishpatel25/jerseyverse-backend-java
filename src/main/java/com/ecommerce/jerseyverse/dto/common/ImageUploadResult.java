package com.ecommerce.jerseyverse.dto.common;

public class ImageUploadResult {

    private final String imageUrl;

    private final String publicId;

    public ImageUploadResult(String imageUrl, String publicId) {
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublicId() {
        return publicId;
    }
}
