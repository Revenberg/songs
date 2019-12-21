package info.revenberg.song.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import info.revenberg.song.dao.jpa.BundleRepository;
import info.revenberg.song.domain.Bundle;

@Controller
public class BundleWebController {
 
    @Autowired
    private BundleRepository bundleRepository;

    @GetMapping("/bundles")
    public String showSignUpForm(Bundle bundle) {
        return "add-bundle";
    }
     
    @PostMapping("/addBundle")
    public String addBundle(@Valid Bundle bundle, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-bundle";
        }         
        
        bundleRepository.save(bundle);
        model.addAttribute("bundles", bundleRepository.findAll());
        return "index";
    }
 
    // additional CRUD methods
}