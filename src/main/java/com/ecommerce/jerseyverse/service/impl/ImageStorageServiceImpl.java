package com.ecommerce.jerseyverse.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecommerce.jerseyverse.dto.common.ImageUploadResult;
import com.ecommerce.jerseyverse.exception.ImageStorageException;
import com.ecommerce.jerseyverse.service.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final Cloudinary cloudinary;

    public ImageStorageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public ImageUploadResult storeProductImage(MultipartFile image) {

        validateImage(image);

        try {

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    image.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jerseyverse/products",
                            "resource_type", "image"
                    )
            );

            String imageUrl = uploadResult.get("secure_url").toString();

            String publicId = uploadResult.get("public_id").toString();

            return new ImageUploadResult(imageUrl, publicId);

        } catch (IOException e) {

            throw new ImageStorageException(
                    "Failed to upload product image.",
                    e
            );
        }
    }

    @Override
    public void deleteProductImage(String publicId) {

        if (publicId == null || publicId.isBlank()) {
            return;
        }

        try {

            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
            );

        } catch (IOException e) {

            throw new ImageStorageException(
                    "Failed to delete product image.",
                    e
            );
        }
    }

    private void validateImage(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            throw new ImageStorageException(
                    "Product image is required."
            );
        }

        if (image.getSize() > MAX_FILE_SIZE) {
            throw new ImageStorageException(
                    "Product image must not exceed 5 MB."
            );
        }

        String contentType = image.getContentType();

        if (contentType == null
                || !ALLOWED_CONTENT_TYPES.contains(contentType)) {

            throw new ImageStorageException(
                    "Unsupported image type. Only JPEG, PNG and WebP are allowed."
            );
        }
    }

}