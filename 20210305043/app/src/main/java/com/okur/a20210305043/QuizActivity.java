package com.okur.a20210305043;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private TextView txtQuestionNumber;
    private ImageView imgQuestion;
    private RadioGroup rgOptions;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button btnNext;

    private String quizType;
    private int currentIndex = 0;
    private int correctCount = 0;
    private boolean isAnswerChecked = false;

    private String[] questions;
    private String[][] options;
    private int[] correctIndex;
    private int[] imageResIds;
    private RadioButton[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtQuestionNumber = findViewById(R.id.txtQuestionNumber);
        imgQuestion = findViewById(R.id.imgQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnNext = findViewById(R.id.btnNext);

        buttons = new RadioButton[]{rb1, rb2, rb3, rb4};

        quizType = getIntent().getStringExtra("quizType");
        if (quizType == null) {
            quizType = "capitals";
        }

        setupQuestions();
        showQuestion();
        btnNext.setText("Check answer");

        btnNext.setOnClickListener(v -> {
            if (!isAnswerChecked) {
                int selectedId = rgOptions.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int answerIndex = getSelectedAnswerIndex(selectedId);

                if (answerIndex == correctIndex[currentIndex]) {
                    correctCount++;
                }

                showAnswerDialog(answerIndex);

                isAnswerChecked = true;

            } else {
                if (currentIndex == questions.length - 1) {
                    showFinalResult();
                } else {
                    currentIndex++;
                    isAnswerChecked = false;
                    rgOptions.clearCheck();
                    resetOptionColors();
                    btnNext.setText("Check answer");
                    showQuestion();
                }
            }
        });
    }

    private void showAnswerDialog(int answerIndex) {
        int correct = correctIndex[currentIndex];
        boolean isCorrect = (answerIndex == correct);
        String message;
        String title;

        if (isCorrect) {
            title = "Correct! 🎉";
            message = "Well done! Your answer is correct.";
        } else {
            title = "Wrong! 😔";
            String correctAnswerText = options[currentIndex][correct];
            message = "The correct answer is: " + correctAnswerText;
        }

        buttons[correct].setBackgroundColor(Color.parseColor("#C8E6C9"));
        if (!isCorrect && answerIndex >= 0 && answerIndex < buttons.length) {
            buttons[answerIndex].setBackgroundColor(Color.parseColor("#FFCDD2"));
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Continue", (dialog, which) -> {
                    if (currentIndex == questions.length - 1) {
                        btnNext.setText("Show result");
                    } else {
                        btnNext.setText("Next question");
                    }
                })
                .show();
    }


    private int getSelectedAnswerIndex(int selectedId) {
        if (selectedId == rb1.getId()) return 0;
        if (selectedId == rb2.getId()) return 1;
        if (selectedId == rb3.getId()) return 2;
        if (selectedId == rb4.getId()) return 3;
        return -1;
    }

    private void resetOptionColors() {
        for (RadioButton b : buttons) {
            b.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void showFinalResult() {
        int total = questions.length;

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("total", total);
        startActivity(intent);
        finish();
    }

    private void setupQuestions() {
        if ("capitals".equals(quizType)) {
            setTitle("World Capitals Quiz");

            questions = new String[]{
                    "What is the capital of Turkey?",
                    "What is the capital of France?",
                    "What is the capital of Germany?",
                    "What is the capital of Italy?",
                    "What is the capital of Japan?",
                    "What is the capital of Spain?",
                    "What is the capital of the United Kingdom?",
                    "What is the capital of Russia?",
                    "What is the capital of Canada?",
                    "What is the capital of China?"
            };

            options = new String[][]{
                    {"Istanbul", "Ankara", "Izmir", "Antalya"},
                    {"Lyon", "Paris", "Nice", "Marseille"},
                    {"Munich", "Berlin", "Hamburg", "Frankfurt"},
                    {"Rome", "Milan", "Naples", "Turin"},
                    {"Osaka", "Tokyo", "Kyoto", "Nagoya"},
                    {"Barcelona", "Madrid", "Seville", "Valencia"},
                    {"Manchester", "Birmingham", "London", "Edinburgh"},
                    {"Saint Petersburg", "Moscow", "Kazan", "Sochi"},
                    {"Toronto", "Vancouver", "Montreal", "Ottawa"},
                    {"Shanghai", "Beijing", "Guangzhou", "Shenzhen"}
            };

            correctIndex = new int[]{
                    1, 1, 1, 0, 1, 1, 2, 1, 3, 1
            };

            imageResIds = new int[]{
                    R.drawable.img1,
                    R.drawable.img2,
                    R.drawable.img3,
                    R.drawable.img4,
                    R.drawable.img5,
                    R.drawable.img6,
                    R.drawable.img7,
                    R.drawable.img8,
                    R.drawable.img9,
                    R.drawable.img10
            };

        } else {
            setTitle("Turkey Geography Quiz");

            questions = new String[]{
                    "Turkey is located on which continents?",
                    "Which city is the capital of Turkey?",
                    "Which city is the most populated city in Turkey?",
                    "How many geographical regions does Turkey have?",
                    "Which sea is to the north of Turkey?",
                    "What is the highest mountain in Turkey?",
                    "Which country is a western neighbor of Turkey?",
                    "Which strait connects the Black Sea and the Sea of Marmara?",
                    "What is the official currency of Turkey?",
                    "What is the official language of Turkey?"
            };

            options = new String[][]{
                    {"Europe", "Asia", "Europe and Asia", "Africa"},
                    {"Istanbul", "Ankara", "Izmir", "Bursa"},
                    {"Ankara", "Istanbul", "Izmir", "Antalya"},
                    {"5", "6", "7", "8"},
                    {"Mediterranean Sea", "Aegean Sea", "Black Sea", "Caspian Sea"},
                    {"Mount Ararat", "Mount Erciyes", "Mount Uludag", "Mount Kaçkar"},
                    {"Greece", "Georgia", "Iran", "Iraq"},
                    {"Dardanelles", "Bosphorus", "Suez Canal", "Gibraltar"},
                    {"Euro", "Dollar", "Turkish lira", "Pound"},
                    {"Arabic", "Turkish", "Persian", "English"}
            };

            correctIndex = new int[]{
                    2, 1, 1, 2, 2, 0, 0, 1, 2, 1
            };

            imageResIds = new int[]{
                    R.drawable.img11,
                    R.drawable.img12,
                    R.drawable.img13,
                    R.drawable.img14,
                    R.drawable.img15,
                    R.drawable.img16,
                    R.drawable.img17,
                    R.drawable.img18,
                    R.drawable.img19,
                    R.drawable.img20
            };
        }
    }

    private void showQuestion() {
        String numberText = "Question " + (currentIndex + 1) + " / " + questions.length;
        txtQuestionNumber.setText(numberText);
        txtQuestion.setText(questions[currentIndex]);

        rb1.setText(options[currentIndex][0]);
        rb2.setText(options[currentIndex][1]);
        rb3.setText(options[currentIndex][2]);
        rb4.setText(options[currentIndex][3]);

        if (imageResIds != null && currentIndex < imageResIds.length) {
            int resId = imageResIds[currentIndex];
            if (resId != 0) {
                imgQuestion.setVisibility(ImageView.VISIBLE);
                imgQuestion.setImageResource(resId);
            } else {
                imgQuestion.setVisibility(ImageView.GONE);
                imgQuestion.setImageDrawable(null);
            }
        } else {
            imgQuestion.setVisibility(ImageView.GONE);
            imgQuestion.setImageDrawable(null);
        }
    }
}