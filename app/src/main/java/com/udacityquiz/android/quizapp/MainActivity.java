package com.udacityquiz.android.quizapp;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.udacityquiz.android.quizapp.databinding.ActivityMainBinding;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding myActivity;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity= DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value from saved state
            points= savedInstanceState.getInt("POINTS");
            DisplayResult(points);

        } else {
            //initial status of the loading bar
            myActivity.progressCountdown.setProgress(0);
        }


        //pressing the submit button
        myActivity.question4.nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                points = CountResult();
                DisplayResult(points);
            }
        });
    }

    private int CountResult(){

        int result = 0;
        //the variable stores and modifies the text from the editable field
        String resultQ4 = myActivity.question4.myEditText.getText().toString();
        resultQ4 = resultQ4.toLowerCase();

        //checks whether the correct answer has been given
        if(myActivity.question1.radioButton1Q1.isChecked()) {
            result ++;
        }
        if(myActivity.question2.checkBox1Q2.isChecked() && myActivity.question2.checkBox2Q2.isChecked()
                && !myActivity.question2.checkBox3Q2.isChecked() && !myActivity.question2.checkBox4Q2.isChecked()) {
            result ++;
        }
        if(myActivity.question3.radioButton1Q3.isChecked()) {
            result ++;
        }
        if(resultQ4.equals("4")||resultQ4.equals("four")) {
            result ++;
        }

        return result;
    }

    private void DisplayResult(int result){
        //to display responses in toast and under percentage points
        String howManyQuestionCorrect = result+"/4";
        myActivity.questionCounter.setText(howManyQuestionCorrect);
        Toast.makeText(MainActivity.this, "You answered correct for\n"+howManyQuestionCorrect, Toast.LENGTH_SHORT).show();

        //calculating the percentage of correct answers, bar animation and display in the text field
        int percent = (result*100)/4;

        setProgressMax(myActivity.progressCountdown,100);
        setProgressAnimate(myActivity.progressCountdown,percent);

        String percentResult = percent+"%";
        myActivity.myResult.setText(percentResult);
    }

    //animation of the progress bar
    private void setProgressMax(RingProgressBar pb, int max) {
        pb.setMax(max * 100);
    }

    private void setProgressAnimate(RingProgressBar pb, int progressTo)
    {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo * 100);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current quiz state
        savedInstanceState.putInt("POINTS", points);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
