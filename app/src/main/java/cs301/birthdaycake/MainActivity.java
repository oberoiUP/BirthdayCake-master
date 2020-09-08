package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cakeView = findViewById(R.id.cakeview);
        CakeController cakeController = new CakeController(cakeView);

        Button blowoutButton = findViewById(R.id.button);
        blowoutButton.setOnClickListener(cakeController);

        Switch candlesSwitch = findViewById(R.id.switch5);
        candlesSwitch.setOnCheckedChangeListener(cakeController);

        Switch frostingSwitch = findViewById(R.id.switch4);
        frostingSwitch.setOnCheckedChangeListener(cakeController);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(cakeController);

        cakeView.setOnTouchListener(cakeController);
    }

    public void goodbye(View button) {
        Log.i("button", "Goodbye");
        //System.out.println("GOOD NIGHT!!");
        finishAffinity();
    }

}
