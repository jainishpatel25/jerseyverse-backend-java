package com.ecommerce.jerseyverse.entity;

import com.ecommerce.jerseyverse.common.entity.BaseEntity;
import com.ecommerce.jerseyverse.enums.Size;
import jakarta.persistence.*;

@Entity
@Table(
        name = "product_variants",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_variant_size",
                        columnNames = {"product_id", "size"}
                )
        }
)
public class ProductVariant extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false, length = 10)
    private Size size;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}