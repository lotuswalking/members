package com.sushi.members.Controller;

import com.sushi.members.jpa.LocalStoreRepository;
import com.sushi.members.jpa.entity.LocalShop;
import com.sushi.members.jpa.entity.Person;
import lombok.extern.java.Log;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log
@Controller
public class StoreController {
    @Autowired
    private LocalStoreRepository storeRepository;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {
        LocalShop localShop;
        localShop = model.containsAttribute("store") ? (LocalShop) model.get("store") : new LocalShop();
        model.addAttribute("parentStoreId",'0');
        model.addAttribute("store", localShop);
    }
    @GetMapping("/stores")
    public String listStores(Model model) {
        List<LocalShop> stores=storeRepository.findAll();
        model.addAttribute("stores",stores);
        return "stores";
    }
    @GetMapping("/store/new")
    public String NewStore(Model model) {
        LocalShop localShop = new LocalShop();
        model.addAttribute("parentStoreId",0);
        model.addAttribute("store",localShop);
        return "store";

    }
    @GetMapping("/store/edit/{id}")
    public String editStore(Model model, @PathVariable Long id) {
        LocalShop localShop = storeRepository.getLocalShopById(id);
        model.addAttribute("store", localShop);
        model.addAttribute("curMode", "edit");
        return "store";
    }
    @GetMapping("/store/remove/{id}")
    public  String removeStore(@PathVariable Long id) {
        storeRepository.deleteById(id);
        return "redirect:/stores";
    }
    @PostMapping("/store/save")
    public String saveStore(@ModelAttribute LocalShop store) {

        storeRepository.save(store);
        return "redirect:/stores";
    }
}
