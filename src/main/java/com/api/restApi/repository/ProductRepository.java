package com.api.restApi.repository;

import org.springframework.stereotype.Repository;

import com.api.restApi.entity.request.CreateProductReq;
import com.api.restApi.entity.request.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private List<Product> list = new ArrayList<Product>();

    public List<Product> createProducts(CreateProductReq req) {
        int start = 1;
        if (req.isClearExisting())
            this.list.clear();

        if (req.isAddToExisting()) {
            start = this.list.size() > 0 ? getMaxId() : 1;
        }

        for (int i = start; i <= req.getSize(); i++) {
            if (findById(i) == null) {

                int quantity = generateQuantity();
                int unitPrice = generatePrice();
                double totalPrice = unitPrice * quantity;
                Product product = new Product(i,
                        UUID.randomUUID(), "product " + i, quantity, unitPrice, totalPrice);
                this.list.add(product);
            }
        }

        return this.list;
    }

    private int getMaxId() {
        Optional<Product> product = this.list.stream().max(Comparator.comparing(Product::getId));
        return product.isPresent() ? product.get().getId() : 1;
    }

    public List<Product> getAllProducts() {
        return this.list;
    }

    public Product findById(int id) {
        Optional<Product> product = this.list.stream().filter(a -> a.getId() == id).findFirst();
        return product.isPresent() ? product.get() : null;
    }

    public List<Product> search(String name) {
        return this.list.stream().filter(x -> x.getName().startsWith(name)).collect(Collectors.toList());
    }

    public Product save(Product p) {
        Product product = new Product();
        product.setId(p.getId());
        product.setUuid(UUID.randomUUID());
        product.setName(p.getName());
        product.setQuantity(p.getQuantity());
        product.setUnitPrice(p.getUnitPrice());
        product.setTotalPrice(p.getUnitPrice() * p.getQuantity());
        this.list.add(product);
        return product;
    }

    public String delete(Integer id) {
        this.list.removeIf(x -> x.getId() == (id));
        return null;
    }

    public Product update(Product product) {
        int idx = 0;
        int id = 0;
        for (int i = 0; i < list.size(); i++) {
            if (this.list.get(i).getId() == (product.getId())) {
                id = product.getId();
                idx = i;
                break;
            }
        }

        Product product1 = new Product();
        product1.setId(id);
        product1.setName(product.getName());
        product1.setQuantity(product.getQuantity());
        product1.setUnitPrice(product.getUnitPrice());
        product1.setUnitPrice(product.getQuantity() * product.getUnitPrice());
        this.list.set(idx, product);
        return product1;
    }

    public int generateQuantity() {
        return getRandomNumber(1, 20);
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int generatePrice() {
        return getRandomNumber(100, 2000);
    }
}
