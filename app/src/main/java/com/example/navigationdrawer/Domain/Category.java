
package com.example.navigationdrawer.Domain;

import com.example.navigationdrawer.Helper.ServerDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("sub_category")
    @Expose
    private List<Category> subCategory;
    @SerializedName("level")
    @Expose
    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryImage() {
        if (categoryImage != null && !categoryImage.startsWith("http")) {
            return ServerDetail.endpoint + categoryImage;
        }
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Category> getAllSubCategories() {
        List<Category> allSubCategories = new ArrayList<>();
        if (subCategory != null) {
            for (Category subCategory : subCategory) {
                allSubCategories.add(subCategory);
                allSubCategories.addAll(subCategory.getAllSubCategories());
            }
        }
        return allSubCategories;
    }
}
