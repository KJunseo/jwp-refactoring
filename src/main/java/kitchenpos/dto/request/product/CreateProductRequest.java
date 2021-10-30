package kitchenpos.dto.request.product;

import java.math.BigDecimal;

import kitchenpos.domain.Product;

public class CreateProductRequest {
    private String name;
    private BigDecimal price;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity() {
        return new Product(name, price);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
