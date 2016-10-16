package lam.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

public class FoodActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String foodName = intent.getStringExtra("foodName");
        String foodPrice = intent.getStringExtra("foodPrice");
        setContentView(R.layout.food_content);
        if(foodName != null)
        {
            LinearLayout layout = (LinearLayout)findViewById(R.id.food_content_textField);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            TextView tv1 = new TextView(this);
            tv1.setId(View.generateViewId());
            tv1.setText(foodName);

            TextView tv2 = new TextView(this);
            params2.addRule(RelativeLayout.RIGHT_OF, tv1.getId());
            tv2.setId(View.generateViewId());
            tv2.setText(". -->" + foodPrice);

            layout.addView(tv1, params1);
            layout.addView(tv2, params2);
        }

    }

    public void addItem_btnOnClick(View v)
    {
        Intent intent = new Intent(FoodActivity.this, Food_new_item_activity.class);
        this.startActivity(intent);
    }

}
