package com.ecommerce.jerseyverse.service.impl;

import com.ecommerce.jerseyverse.exception.ImageStorageException;
import com.ecommerce.jerseyverse.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final String PUBLIC_PRODUCT_IMAGE_PATH =
            "/uploads/products/";

    private final Path productUploadPath;

    public ImageStorageServiceImpl(
            @Value("${app.upload.product-dir}") String productUploadDir) {

        this.productUploadPath = Paths.get(productUploadDir)
                .toAbsolutePath()
                .normalize();

        initializeStorageDirectory();
    }

    private void initializeStorageDirectory() {
        try {
            Files.createDirectories(productUploadPath);
        } catch (IOException e) {
            throw new ImageStorageException(
                    "Could not initialize product image storage.",
                    e
            );
        }
    }

    @Override
    public String storeProductImage(MultipartFile image) {

        validateImage(image);

        String extension = getExtension(image.getContentType());

        String fileName = UUID.randomUUID() + extension;

        Path destinationPath = productUploadPath
                .resolve(fileName)
                .normalize();

        validateStoragePath(destinationPath);

        try (InputStream inputStream = image.getInputStream()) {

            Files.copy(
                    inputStream,
                    destinationPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {
            throw new ImageStorageException(
                    "Failed to store product image.",
                    e
            );
        }

        return PUBLIC_PRODUCT_IMAGE_PATH + fileName;
    }

    @Override
    public void deleteProductImage(String imageUrl) {

        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        String fileName = extractFileName(imageUrl);

        Path imagePath = productUploadPath
                .resolve(fileName)
                .normalize();

        validateStoragePath(imagePath);

        try {
            Files.deleteIfExists(imagePath);
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

    private String getExtension(String contentType) {

        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> throw new ImageStorageException(
                    "Unsupported product image type."
            );
        };
    }

    private void validateStoragePath(Path path) {

        if (!path.startsWith(productUploadPath)) {
            throw new ImageStorageException(
                    "Invalid product image storage path."
            );
        }
    }

    private String extractFileName(String imageUrl) {

        try {
            Path path = Paths.get(imageUrl);
            Path fileName = path.getFileName();

            if (fileName == null) {
                throw new ImageStorageException(
                        "Invalid product image URL."
                );
            }

            return fileName.toString();

        } catch (Exception e) {

            if (e instanceof ImageStorageException) {
                throw e;
            }

            throw new ImageStorageException(
                    "Invalid product image URL.",
                    e
            );
        }
    }
}