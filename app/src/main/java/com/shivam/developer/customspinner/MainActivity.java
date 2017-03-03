package com.shivam.developer.customspinner;

import android.animation.Animator;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday",
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday",
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    LinearLayout llSelectArea;
    ListView lvAreaList;
    View crossView;
    TextView tvSelectedArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelectedArea = (TextView) findViewById(R.id.tvSelectedArea);

        lvAreaList = (ListView) findViewById(R.id.lvLocationList);
        lvAreaList.setAdapter(new LocationListAdapter());
        lvAreaList.setOnItemClickListener(this);

        crossView = findViewById(R.id.cross_view);
        crossView.setOnClickListener(this);

        llSelectArea = (LinearLayout) findViewById(R.id.ll_select_area);
        llSelectArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealAnimateIn();
            }
        });
    }

    public void revealAnimateIn() {

        final View revealView = findViewById(R.id.reveal_view);

        int centerX = (int) ((llSelectArea.getX() + (llSelectArea.getX() + llSelectArea.getLayoutParams().width)) / 2);
        int centerY = (int) ((llSelectArea.getY() + (llSelectArea.getY() + llSelectArea.getLayoutParams().height)) / 2);

        int finalRadius = (int) Math.hypot(revealView.getWidth(), revealView.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, centerX, centerY, 0, finalRadius);

            anim.setDuration(400);
            anim.setInterpolator(new FastOutSlowInInterpolator());

            anim.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    revealView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    crossView.setVisibility(View.VISIBLE);
                    lvAreaList.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            anim.start();
        }
    }

    public void revealAnimateOut() {

        final View revealView = findViewById(R.id.reveal_view);

        int centerX = (int) ((llSelectArea.getX() + (llSelectArea.getX() + llSelectArea.getLayoutParams().width)) / 2);
        int centerY = (int) ((llSelectArea.getY() + (llSelectArea.getY() + llSelectArea.getLayoutParams().height)) / 2);

        int finalRadius = (int) Math.hypot(revealView.getWidth(), revealView.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, centerX, centerY, finalRadius, 0);

            anim.setDuration(200);
            anim.setInterpolator(new FastOutSlowInInterpolator());

            anim.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    crossView.setVisibility(View.INVISIBLE);
                    lvAreaList.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    revealView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            anim.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_view:
                revealAnimateOut();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tvSelectedArea.setText(days[position]);
        revealAnimateOut();
    }

    public class LocationListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return days.length;
        }

        @Override
        public String getItem(int position) {
            return days[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_area_text_view, parent, false);
            TextView tvAreaName = (TextView) convertView.findViewById(R.id.tvAreaName);
            tvAreaName.setText(getItem(position));
            return convertView;
        }
    }
}
