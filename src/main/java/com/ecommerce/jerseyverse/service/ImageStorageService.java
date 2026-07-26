package com.ecommerce.jerseyverse.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    String storeProductImage(MultipartFile image);

    void deleteProductImage(String imageUrl);

}
