package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity{

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Declare TextViews
    private TextView alsoKnownAs;
    private TextView origin;
    private TextView description;
    private TextView ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //populating UI 
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //set and display alsoKnownAs
        alsoKnownAs = findViewById(R.id.also_known_tv);
        sandwichList(sandwich.getAlsoKnownAs(), alsoKnownAs);

        //set and display ingredients using sandwichList function
        ingredients = findViewById(R.id.ingredients_tv);
        sandwichList(sandwich.getIngredients(), ingredients);

        //set and display origin
        origin = findViewById(R.id.origin_tv);
        origin.setText(sandwich.getPlaceOfOrigin());

        //set and display description using sandwichList function
        description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());
    }

    //Loop through the list of Strings
    private void sandwichList(List<String> sandwichL, TextView sandwichTxt){
        String sandwichTemp;
        if (sandwichL != null && !sandwichL.isEmpty()){
            for (int i = 0; i < sandwichL.size(); i++){
                sandwichTemp = sandwichL.get(i).toString();
                if( i != (sandwichL.size() -1))
                    sandwichTxt.append(sandwichTemp + ", "); //if not last item add ","
                else
                    sandwichTxt.append(sandwichTemp + "."); //if last item add "."
            }
        }

    }
}
