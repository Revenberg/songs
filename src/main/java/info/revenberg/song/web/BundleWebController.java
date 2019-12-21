package info.revenberg.song.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import info.revenberg.song.dao.jpa.BundleRepository;
import info.revenberg.song.domain.Bundle;

@Controller
@RequestMapping(value = "bundle")
public class BundleWebController {
 
    @Autowired
    private BundleRepository bundleRepository;

    @GetMapping("/")
    public String getBundles(Model model) {
        model.addAttribute("bundles", bundleRepository.findAll());
        return "bundle-list";
    }

    @GetMapping("/edit")
    public String showSignUpForm(Bundle bundle) {
        return "bundle-edit";
    }
     
    @PostMapping("/add")
    public String addBundle(@Valid Bundle bundle, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bundle-add";
        }         
        
        bundleRepository.save(bundle);
        model.addAttribute("bundles", bundleRepository.findAll());
        return "index";
    }
 
    // additional CRUD methods
}