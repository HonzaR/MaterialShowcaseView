package uk.co.deanwild.materialshowcaseviewsample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class SequenceExample extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonOne;
    private Button mButtonTwo;
    private Button mButtonThree;

    private TextView mTextShowing;

    private Button mButtonReset;

    private MaterialShowcaseSequence sequence;

    private static final String SHOWCASE_ID = "sequence_example_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_example);
        mButtonOne = findViewById(R.id.btn_one);
        mButtonOne.setOnClickListener(this);

        mButtonTwo = findViewById(R.id.btn_two);
        mButtonTwo.setOnClickListener(this);

        mButtonThree = findViewById(R.id.btn_three);
        mButtonThree.setOnClickListener(this);

        mButtonReset = findViewById(R.id.btn_reset);
        mButtonReset.setOnClickListener(this);

        mTextShowing = findViewById(R.id.tv_is_showing);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String text = sequence.isShowing() ? "Showing " + sequence.getSequenceID() + " true" : "Showing " + sequence.getSequenceID() + " false";
                mTextShowing.setText(text);
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                sequence.stop();
            }
        }, 10000);

        presentShowcaseSequence(); // one second delay
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_one || v.getId() == R.id.btn_two || v.getId() == R.id.btn_three) {

            presentShowcaseSequence();

        } else if (v.getId() == R.id.btn_reset) {

            MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
            Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show();
        }

    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        config.setDismissOnTouch(true);
        config.setShowDismissButton(false);
        config.setTitleTextSize(getResources().getDimension(R.dimen.help_intro_title_text_size));
        config.setContentTextSize(getResources().getDimension(R.dimen.help_intro_content_text_size));

        sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);

        sequence.addSequenceItem(mButtonOne, "Gagaga", "This is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button one ", "GOT IT");

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(mButtonTwo)
                        .setDismissText("GOT IT")
                        .setDismissOnTouch(true)
                        .setTitleText("This")
                        .setContentText("This is button two")
                        .withRectangleShape(true)
                        .showDismissButton(false)
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(mButtonThree)
                        .setDismissText("GOT IT")
                        .setDismissOnTouch(true)
                        .setTitleText("three")
                        .setContentText("This is button three")
                        .withRectangleShape()
                        .build()
        );

        sequence.start();

    }
}
