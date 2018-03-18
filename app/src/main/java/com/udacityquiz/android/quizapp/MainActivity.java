package com.udacityquiz.android.quizapp;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.udacityquiz.android.quizapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding myActivity;
    int points;
    DecoView awesomeProgressbar;
    int green, blue, red, yellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity= DataBindingUtil.setContentView(this, R.layout.activity_main);

        awesomeProgressbar = myActivity.progressCountdown;

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value from saved state
            points= savedInstanceState.getInt("POINTS");
            DisplayResult(points);

        } else {
            //initial status of the loading bar
            AwesomePBInitialize();
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

        awesomeProgressbar.addEvent(new DecoEvent.Builder(percent).setIndex(green).setDuration(1000).build());
        awesomeProgressbar.addEvent(new DecoEvent.Builder(percent).setIndex(yellow).setDuration(1100).build());
        awesomeProgressbar.addEvent(new DecoEvent.Builder(percent).setIndex(blue).setDuration(1200).build());
        awesomeProgressbar.addEvent(new DecoEvent.Builder(percent).setIndex(red).setDuration(1300).build());

        String percentResult = percent+"%";
        myActivity.myResult.setText(percentResult);
    }

    private void AwesomePBInitialize(){
        float thickness = 15;
        float numberOfColours = 4;

        // Create background
        SeriesItem bck = new SeriesItem.Builder(Color.argb(20, 218, 218, 218))
                .setRange(0, 100, 100)
                .setLineWidth(numberOfColours*thickness)
                .setInset(new PointF(2*thickness, 2*thickness))
                .build();
        awesomeProgressbar.addSeries(bck);

        //Create colours of rainbow
        SeriesItem blueItem = new SeriesItem.Builder(Color.argb(255, 10, 179, 228))
                .setRange(0, 100, 0)
                .setInterpolator (new DecelerateInterpolator ())
                .setSpinDuration(3000)
                .setShowPointWhenEmpty(false)
                .setLineWidth(thickness)
                .setInset(new PointF(thickness, thickness))
                .build();

        SeriesItem greenItem = new SeriesItem.Builder(Color.argb(255, 152, 255, 102))
                .setRange(0, 100, 0)
                .setInterpolator (new DecelerateInterpolator ())
                .setSpinDuration(3000)
                .setShowPointWhenEmpty(false)
                .setLineWidth(thickness)
                .build();

        SeriesItem yellowItem = new SeriesItem.Builder(Color.argb(255, 255, 255, 102))
                .setRange(0, 100, 0)
                .setInterpolator (new DecelerateInterpolator ())
                .setSpinDuration(3000)
                .setShowPointWhenEmpty(false)
                .setLineWidth(thickness)
                .setInset(new PointF(thickness*3, thickness*3))
                .build();

        SeriesItem redItem = new SeriesItem.Builder(Color.argb(255, 255, 102, 152))
                .setRange(0, 100, 0)
                .setInterpolator (new DecelerateInterpolator ())
                .setSpinDuration(3000)
                .setShowPointWhenEmpty(false)
                .setLineWidth(thickness)
                .setInset(new PointF(thickness*2, thickness*2))
                .build();

        green = awesomeProgressbar.addSeries(greenItem);
        yellow = awesomeProgressbar.addSeries(yellowItem);
        red = awesomeProgressbar.addSeries(redItem);
        blue = awesomeProgressbar.addSeries(blueItem);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current quiz state
        savedInstanceState.putInt("POINTS", points);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
