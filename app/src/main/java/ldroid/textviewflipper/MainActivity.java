package ldroid.textviewflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private VerticalBannerTextLayout mVerticalBanner;
    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVerticalBanner = (VerticalBannerTextLayout) findViewById(R.id.vertical_banner);
        mBtnStart = (Button) findViewById(R.id.start);

        ArrayList data = new ArrayList();
        data.add("广告1");
        data.add("广告2");
        data.add("广告3");
        data.add("广告4");
        data.add("广告5");
        data.add("广告6");

        mVerticalBanner.setLayoutData(data);


        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVerticalBanner.isRunning()) {
                    mVerticalBanner.stopFlipping();
                    mBtnStart.setText("START");
                } else {
                    mBtnStart.setText("STOP");
                    mVerticalBanner.startFlipping();
                }
            }
        });
    }
}
