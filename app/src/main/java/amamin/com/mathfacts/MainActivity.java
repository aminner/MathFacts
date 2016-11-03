package amamin.com.mathfacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    private int maxNumber = 6;
    private int maxTime = 6;
    private int correct = 0;

    @BindView(R.id.submit)
    Button submitAnswer;

    @BindView(R.id.answer)
    EditText tv_answer;

    @BindView(R.id.addend1)
    TextView tv_addend1;

    @BindView(R.id.addend2)
    TextView tv_addend2;

    @BindView(R.id.correctCount)
    TextView correctCount;

    @BindView(R.id.timeElapsed)
    TextView timeRemaining;

    @BindView(R.id.activeGame)
    RelativeLayout rl_activeGame;

    @BindView(R.id.gameOver)
    RelativeLayout rl_gameOver;

    @BindView(R.id.tv_operator)
    TextView tv_operator;


    private CountDownTimer timer;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private MathFactsType factType;
    private int solution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSavedPreferences();
        createTimer();
    }

    private void getSavedPreferences() {
        maxNumber = MathFactsUtility.getPreference(this, MathFactsConstants.SP_Max_Addend);
        maxTime = MathFactsUtility.getPreference(this, MathFactsConstants.SP_Max_Min);
        factType = MathFactsType.values()[MathFactsUtility.getPreference(this, MathFactsConstants.SP_Fact_Type, MathFactsType.ADDITION.ordinal())];
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(this.getLocalClassName(), "onResume");
    }
    private void createTimer()
    {
        timer = new CountDownTimer((maxTime*30000), 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemaining.setText("Timer: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                gameOver();
            }
        };
    }

    private void gameOver() {
        submitAnswer.setEnabled(false);
        rl_gameOver.setVisibility(View.VISIBLE);
        rl_activeGame.setVisibility(View.INVISIBLE);
    }

    private void setProblem()
    {
        Random r = new Random();
        int num1 = r.nextInt(maxNumber);
        int num2 = r.nextInt(maxNumber);
        solution = 0;

        switch(factType)
        {
            case ADDITION:
                solution = num1 + num2;
                break;
            case SUBTRACTION:
                solution = num1;
                num1 = num2 + solution;
                break;
            case MULTIPLICATION:
                solution = num1*num2;
                break;
            case DIVISION:
                if(num1 >= num2) {
                    solution = num2;
                    num2 = num1 * solution;
                } else {
                    solution = num1;
                    num1 = num2 * solution;
                }
        }
        tv_addend1.setText(String.valueOf(num1));
        tv_addend2.setText(String.valueOf(num2));
        tv_operator.setText(factType.getOperator());
        tv_answer.setText("");
        promptSpeechInput();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnTextChanged(R.id.answer)
    public void onAnswerChanged()
    {
        if(tv_answer.getText().toString().isEmpty()) {
            submitAnswer.setEnabled(false);
        } else {
            submitAnswer.setEnabled(true);
        }
    }

    @OnClick(R.id.resetButton)
    public void reset()
    {
        setProblem();
        correct = 0;
        correctCount.setText("Count: " + correct);
        timeRemaining.setText("Timer: 60");
        timer.start();
        rl_gameOver.setVisibility(View.INVISIBLE);
        rl_activeGame.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.submit)
    public void checkAnswer()
    {
        if(solution == parseInt(tv_answer.getText().toString())) {
            if(correct == 0)
            {
                timer.start();
            }
            correct++;
            correctCount.setText("Count: " + correct);
            tv_answer.setBackgroundColor(Color.WHITE);
            setProblem();
        } else {
            tv_answer.setBackgroundColor(Color.parseColor("#FFBBAA"));
            tv_answer.setText("");
        }
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        sr.startListening(intent);
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv_answer.setText(result.get(0));
                    checkAnswer();
                }
                break;
            }

        }
    }
}
