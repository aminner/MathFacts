package amamin.com.mathfacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private CountDownTimer timer;

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
    }

    private void createTimer()
    {
        timer = new CountDownTimer(maxTime*10000, 1000) {

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
        tv_addend1.setText(String.valueOf(r.nextInt(maxNumber)));
        tv_addend2.setText(String.valueOf(r.nextInt(maxNumber)));
        tv_answer.setText("");
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
        if(parseInt(tv_addend1.getText().toString())+ parseInt(tv_addend2.getText().toString()) == parseInt(tv_answer.getText().toString())) {
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
}
