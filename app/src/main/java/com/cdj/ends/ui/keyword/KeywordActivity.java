package com.cdj.ends.ui.keyword;

/**
 * Created by Dongjin on 2017. 8. 13..
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cdj.ends.R;

import butterknife.OnClick;

public class KeywordActivity extends AppCompatActivity {

    private TextInputEditText editKeyword;

    private GridLayout gridLayout;
    private GridLayout.LayoutParams layoutParams;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        editKeyword = (TextInputEditText) findViewById(R.id.edit_keyword);
        gridLayout = (GridLayout) findViewById(R.id.container_keyword);

//        layoutParams = new GridLayout.LayoutParams(ViewGroup.MarginLayoutParams(10, 10));
//        gridLayout.setLayoutParams(layoutParams);

    }

    private boolean validateData() {
        if (editKeyword.getText() == null) {
            Toast.makeText(getApplicationContext(), "키워드를 추가해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    @OnClick(R.id.btnAdd_keyword)
    public void onAddKeywordClicked(View view) {
        if(validateData()) {
            String keyword = editKeyword.getText().toString();

            final TextView textView = new TextView(this);
            textView.setText(keyword);
            textView.setMaxLines(1);


            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();


                    return false;
                }
            });

            gridLayout.addView(textView);
            editKeyword.setText("");
        }

    }
}
