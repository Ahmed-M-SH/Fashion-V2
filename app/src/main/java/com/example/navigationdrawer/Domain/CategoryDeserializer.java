package com.example.navigationdrawer.Domain;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryDeserializer implements JsonDeserializer<Category> {

    @Override
    public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("sub_category")) {
            JsonElement subCategoryElement = jsonObject.get("sub_category");

            if (subCategoryElement.isJsonObject()) {
                // If sub_category is an object, convert it to a list with a single item
                Category category = new Gson().fromJson(json, typeOfT);
                List<Category> subCategoryList = new ArrayList<>();
                subCategoryList.add(new Gson().fromJson(subCategoryElement, Category.class));
                category.setSubCategory(subCategoryList);
                return category;
            }
        }

        // If sub_category is missing or an array, proceed with normal deserialization
        return new Gson().fromJson(json, typeOfT);
    }
}