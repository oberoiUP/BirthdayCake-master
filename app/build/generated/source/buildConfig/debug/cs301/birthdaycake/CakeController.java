package cs301.birthdaycake;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener
{
    private CakeView cakeView;
    private CakeModel cakeModel;

    public CakeController(CakeView cakeView) {
        this.cakeView = cakeView;
        this.cakeModel = cakeView.getCakeModel();
    }

    public void onClick(View v) {
        Log.d("Blow Out", "Inside onClick");
        Button blowoutButton = (Button) v;

        if(this.cakeModel.areCandlesLit) {
            blowoutButton.setText("Re-light");
        }
        else {
            blowoutButton.setText("Blow out");
        }

        this.cakeModel.areCandlesLit = !this.cakeModel.areCandlesLit;
        this.cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d("Switch", "Inside onCheckedChanged");

        if(buttonView.getText().equals("Frosting")) {
            if(isChecked) {
                this.cakeView.frostingPaint.setColor(0xFFFFFACD);
            }
            else {
                this.cakeView.frostingPaint.setColor(this.cakeView.cakePaint.getColor());
            }
        }
        else if(buttonView.getText().equals("Candles")) {
           this.cakeModel.hasCandles = !this.cakeModel.hasCandles;
        }

        this.cakeView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d("ProgressBar", "progress = " + progress);
        this.cakeModel.numCandles = progress;
        this.cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
