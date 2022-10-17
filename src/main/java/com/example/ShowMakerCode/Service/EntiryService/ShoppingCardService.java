package com.example.ShowMakerCode.Service.EntiryService;


import com.example.ShowMakerCode.Entity.Product;
import com.example.ShowMakerCode.Entity.ShoppingCard;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public interface ShoppingCardService {

    void add(ShoppingCard item);

    void remove(int productID);

    Collection<ShoppingCard> getCardItems();

    void clear();


    void update(Long productIdMax, int Quantity);

    double getAmount();

    int getCount();
}
