package com.kapitanov.giflib.controller;

import com.kapitanov.giflib.data.CategoryRepository;
import com.kapitanov.giflib.data.GifRepository;
import com.kapitanov.giflib.model.Category;
import com.kapitanov.giflib.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/*
Controller file. Handles requests and responses to pages: home, favorites, categories, category and gif details

 */
@Controller
public class GifController {

    // Initiates gif repository
    @Autowired
    private GifRepository gifRepo;

    // Initiates category repository
    @Autowired
    private CategoryRepository categoryRepository;

    //Handles requests to home page
    @RequestMapping("/")
    public String home(ModelMap modelMap) {
        List<Gif> allGifs = gifRepo.getAllGifs();
        modelMap.put("gifs", allGifs);
        return "home";
    }

    //Handles requests to favorites page
    @RequestMapping("/favorites")
    public String favorites(ModelMap modelMap) {
        List<Gif> gifs = gifRepo.findFavorites();
        modelMap.put("gifs", gifs);
        return "favorites";
    }

    //Handles requests to categories page
    @RequestMapping("/categories")
    public String categories(ModelMap modelMap) {
        List<Category> categories = categoryRepository.getAllCategories();
        modelMap.put("categories", categories);
        return "categories";
    }

    //Handles requests to specific category
    @RequestMapping("/category/{id}")
    public String category(@PathVariable int id,ModelMap modelMap) {
        Category category = categoryRepository.findById(id);
        modelMap.put("category", category);

        List<Gif> gifs = gifRepo.findByCategoryId(id);
        modelMap.put("gifs", gifs);

        return "category";
    }

    //Handles requests to individual gif page
    @RequestMapping("/gif/{name}")
    public String gifDetails(@PathVariable String name, ModelMap modelMap) {
        Gif gif = gifRepo.getGifByName(name);
        modelMap.put("gif",gif);
        return "gif-details";
    }

    //Handles search requests
    @RequestMapping(value = "/search", params = {"search"})
    public @ResponseBody String searchResults(ModelMap modelMap, @RequestParam(value = "search") String search) {
        List<Gif> results = gifRepo.searchMethod(search);
        modelMap.put("search", results);
        return "search";
    }
}
