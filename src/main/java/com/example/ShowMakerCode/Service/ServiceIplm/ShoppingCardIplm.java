package com.example.ShowMakerCode.Service.ServiceIplm;

import com.example.ShowMakerCode.Entity.ShoppingCard;
import com.example.ShowMakerCode.Service.EntiryService.ShoppingCardService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
@SessionScope
public class ShoppingCardIplm implements ShoppingCardService {
    private Map<Long, ShoppingCard> map = new HashMap<Long,ShoppingCard>();

    @Override
    public void add(ShoppingCard item){
        ShoppingCard addedItem = map.get(item.getId());
        System.out.println(map.size() + "sizeMap");
        if (addedItem != null){
            addedItem.setQuantity(item.getQuantity() + addedItem.getQuantity());
            System.out.println("hello Ser");
        }else {
        map.put(item.getId(),item);}

    }
    @Override
    public void remove(int productID){
        map.remove(productID);
    }
    @Override
    public Collection<ShoppingCard> getCardItems(){
        return map.values();
    }
    @Override
    public void clear(){
        map.clear();
    }
    @Override
    public void update(Long productIdMax, int Quantity){
        ShoppingCard item = map.get(productIdMax);
        item.setQuantity(Quantity);
    }
    @Override
    public double getAmount(){
      return map.values().stream().mapToDouble(item ->  item.getQuantity()*item.getPrice().doubleValue()).sum();
    }

    @Override
    public int getCount(){
        if (map.isEmpty()){
            return 0;
        }
        return map.values().size();
    }


}
