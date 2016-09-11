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

@Controller
public class GifController {

    @Autowired
    private GifRepository gifRepo;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/")
    public String home(ModelMap modelMap) {
        List<Gif> allGifs = gifRepo.getAllGifs();
        modelMap.put("gifs", allGifs);
        return "home";
    }

    @RequestMapping("/favorites")
    public String favorites(ModelMap modelMap) {
        List<Gif> gifs = gifRepo.findFavorites();
        modelMap.put("gifs", gifs);
        return "favorites";
    }

    @RequestMapping("/categories")
    public String categories(ModelMap modelMap) {
        List<Category> categories = categoryRepository.getAllCategories();
        modelMap.put("categories", categories);
        return "categories";
    }

    @RequestMapping("/category/{id}")
    public String category(@PathVariable int id,ModelMap modelMap) {
        Category category = categoryRepository.findById(id);
        modelMap.put("category", category);

        List<Gif> gifs = gifRepo.findByCategoryId(id);
        modelMap.put("gifs", gifs);

        return "category";
    }

    @RequestMapping("/gif/{name}")
    public String gifDetails(@PathVariable String name, ModelMap modelMap) {
        Gif gif = gifRepo.getGifByName(name);
        modelMap.put("gif",gif);
        return "gif-details";
    }

    @RequestMapping(value = "/search", params = {"search"})
    public @ResponseBody String searchResults(ModelMap modelMap, @RequestParam(value = "search") String search) {
        List<Gif> results = gifRepo.searchMethod(search);
        modelMap.put("search", results);
        return "search";
    }
}
