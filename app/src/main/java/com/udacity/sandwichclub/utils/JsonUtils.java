package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    //string
    private static final String NAME = "name";
    //string
    private static final String MAIN_NAME = "mainName";
    //array
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    //string
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    //string
    private static final String DESCRIPTION = "description";
    //array
    private static final String INGREDIENTS = "ingredients";
    //image
    private static final String IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject sandwichName = jsonSandwich.getJSONObject(NAME);
            String mainName = sandwichName.getString(MAIN_NAME);
            List<String> alsoKnownAs = fromJsonArray(sandwichName.getJSONArray(ALSO_KNOWN_AS));
            String placeOfOrigin = jsonSandwich.getString(PLACE_OF_ORIGIN);
            String description = jsonSandwich.getString(DESCRIPTION);
            List<String> ingredients = fromJsonArray(jsonSandwich.getJSONArray(INGREDIENTS));
            String image = jsonSandwich.getString(IMAGE);
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            return null;
        }
    }

    private static List<String> fromJsonArray(JSONArray jsonArray)throws JSONException{
        List<String> arrayString = new ArrayList<String>();
        if(jsonArray != null){
            for (int i = 0; i < jsonArray.length(); i++){
                arrayString.add(jsonArray.getString(i));
            }
        }
        return arrayString;
    }

}
