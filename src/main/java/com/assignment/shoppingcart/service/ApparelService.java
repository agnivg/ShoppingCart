package com.assignment.shoppingcart.service;

import com.assignment.shoppingcart.model.ApparelDto;
import com.assignment.shoppingcart.model.Apparel;

import java.util.List;

public interface ApparelService {
    public Apparel save(ApparelDto dto);
    public List<Apparel> listApparel(String email);
    public List<Apparel> listApparel();
}
