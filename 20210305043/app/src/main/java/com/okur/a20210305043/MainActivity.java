package com.okur.a20210305043;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtStudentInfo;
    private Button btnTutorial;
    private Button btnCapitalsQuiz;
    private Button btnTurkeyQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStudentInfo = findViewById(R.id.txtStudentInfo);
        btnTutorial = findViewById(R.id.btnTutorial);
        btnCapitalsQuiz = findViewById(R.id.btnCapitalsQuiz);
        btnTurkeyQuiz = findViewById(R.id.btnTurkeyQuiz);

        txtStudentInfo.setText("20210305043 - Feride Okur");

        btnTutorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        });

        btnCapitalsQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("quizType", "capitals");
            startActivity(intent);
        });

        btnTurkeyQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("quizType", "turkey");
            startActivity(intent);
        });
    }
}
