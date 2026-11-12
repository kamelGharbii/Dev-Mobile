package com.example.quizcalcul;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView questionText, scoreText, resultText;
    private Button btn1, btn2, btn3, btn4;

    private ArrayList<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        resultText = findViewById(R.id.resultText);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        loadQuestions();
        showQuestion();

        View.OnClickListener answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clicked = (Button) v;
                int selectedIndex = Integer.parseInt((String) v.getTag());

                if (selectedIndex == questionList.get(currentQuestionIndex).getCorrectAnswerIndex()) {
                    score++;
                }

                currentQuestionIndex++;

                if (currentQuestionIndex < questionList.size()) {
                    showQuestion();
                } else {
                    // Fin du quiz : afficher le score final
                    questionText.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    resultText.setVisibility(View.VISIBLE);

                    resultText.setText("Ton score final est : " + score + "/" + questionList.size());
                }
            }
        };

        btn1.setOnClickListener(answerClickListener);
        btn2.setOnClickListener(answerClickListener);
        btn3.setOnClickListener(answerClickListener);
        btn4.setOnClickListener(answerClickListener);
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("2 + 2 = ?", new String[]{"3", "4", "5", "6"}, 1));
        questionList.add(new Question("5 × 3 = ?", new String[]{"8", "15", "10", "12"}, 1));
        questionList.add(new Question("10 ÷ 2 = ?", new String[]{"4", "5", "6", "8"}, 1));
        questionList.add(new Question("9 - 4 = ?", new String[]{"3", "4", "5", "6"}, 2));
        // 👉 continue jusqu’à 10 questions
    }

    private void showQuestion() {
        Question q = questionList.get(currentQuestionIndex);
        questionText.setText(q.getQuestionText());
        btn1.setText(q.getOptions()[0]);
        btn1.setTag("0");
        btn2.setText(q.getOptions()[1]);
        btn2.setTag("1");
        btn3.setText(q.getOptions()[2]);
        btn3.setTag("2");
        btn4.setText(q.getOptions()[3]);
        btn4.setTag("3");

        scoreText.setText("Score: " + score);
    }
}
