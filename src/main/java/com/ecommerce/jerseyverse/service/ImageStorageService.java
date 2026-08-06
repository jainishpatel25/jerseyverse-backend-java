package com.ecommerce.jerseyverse.service;

import com.ecommerce.jerseyverse.dto.common.ImageUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    ImageUploadResult storeProductImage(MultipartFile image);

    void deleteProductImage(String publicId);

}
