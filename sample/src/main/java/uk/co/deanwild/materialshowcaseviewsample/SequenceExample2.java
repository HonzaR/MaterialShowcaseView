package uk.co.deanwild.materialshowcaseviewsample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class SequenceExample2 extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonOne;
    private Button mButtonTwo;
    private Button mButtonThree;

    private TextView mTextShowing;

    private Button mButtonReset;

    private MaterialShowcaseSequence sequence;

    private static final String SHOWCASE_ID = "sequence_example_id_2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_example_2);

        MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);

        mButtonOne = findViewById(R.id.btn_one);
        mButtonOne.setOnClickListener(this);

        mButtonTwo = findViewById(R.id.btn_two);
        mButtonTwo.setOnClickListener(this);

        mButtonThree = findViewById(R.id.btn_three);
        mButtonThree.setOnClickListener(this);

        mTextShowing = findViewById(R.id.tv_is_showing);

        presentShowcaseSequence(); // one second delay
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_one || v.getId() == R.id.btn_two || v.getId() == R.id.btn_three) {

            presentShowcaseSequence();

        }

    }

    private ShowcaseConfig createConfig(int padding)
    {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        config.setDismissOnTouch(true);
        config.setShowDismissButton(false);
        config.setTitleTextSize(getResources().getDimension(R.dimen.help_intro_title_text_size));
        config.setContentTextSize(getResources().getDimension(R.dimen.help_intro_content_text_size));
        config.setShapePadding(padding);
        config.setCloseEnabled(true);
        config.setNextEnabled(true);
        config.setRenderOverNavigationBar(true);
        return config;
    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = createConfig(10);

        sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                Log.i("Sequence item: " + position, "Show view: " + itemView.toString());
            }
        });

        sequence.setOnItemClosedListener(new MaterialShowcaseSequence.OnSequenceItemClosedListener() {
            @Override
            public void onClosed(MaterialShowcaseView itemView, int position) {
                Log.i("Sequence item: " + position, "Closed view: " + itemView.toString());
            }
        });

        sequence.setOnItemNextListener(new MaterialShowcaseSequence.OnSequenceItemNextListener() {
            @Override
            public void onNext(MaterialShowcaseView itemView, int position) {
                Log.i("Sequence item: " + position, "Next clicked view: " + itemView.toString());
            }
        });

        sequence.setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
                                                @Override
                                                public void onDismiss(MaterialShowcaseView itemView, int position) {
                                                    if (position == 0)
                                                        sequence.setConfig(createConfig(156));
                                                    else if (position == 1)
                                                        sequence.setConfig(createConfig(10));
                                                }
                                            });

        sequence.setConfig(config);

        sequence.addSequenceItem(mButtonOne, "Gagaga", "This is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button one ", "GOT IT");

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(mButtonTwo)
                        .setDismissText("GOT IT")
                        .setCloseText("closing here...")
                        .setNextText("next here...")
                        .setDismissOnTouch(true)
                        .setTitleText("This")
                        .setContentText("This is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button one This is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button oneoneThis is button one ")
                        .withCircleShape()
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
                        .withCircleShape()
                        .build()
        );

        sequence.start();

    }
}
