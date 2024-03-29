
package com.example.fashion.Domain;

import com.example.fashion.Helper.ServerDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("lft")
    @Expose
    private Integer lft;
    @SerializedName("rght")
    @Expose
    private Integer rght;
    @SerializedName("tree_id")
    @Expose
    private Integer treeId;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("parent")
    @Expose
    private Integer parent;

    @SerializedName("have_children")
    @Expose

    private Boolean have_children;

    public Boolean getHave_children() {
        return have_children;
    }

    public void setHave_children(Boolean have_children) {
        this.have_children = have_children;
    }

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

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRght() {
        return rght;
    }

    public void setRght(Integer rght) {
        this.rght = rght;
    }

    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

}
