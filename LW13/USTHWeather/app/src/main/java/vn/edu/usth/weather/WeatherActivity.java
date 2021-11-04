package vn.edu.usth.weather;

import static android.widget.Toast.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class WeatherActivity extends AppCompatActivity {
    public static final String TAG = WeatherActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        // Create a new Fragment to be placed in the activity layout


        PagerAdapter adapter = new HomeFragmentPagerAdapter(
                getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.header);
        tabLayout.setupWithViewPager(pager);






        Log.i(TAG, "onStart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.refresh) {
            makeText(this, "Refresh...", LENGTH_SHORT).show();
            final Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                // This method is executed in main thread
                    String content = msg.getData().getString("server_response");
                    Toast.makeText(WeatherActivity.this, content, Toast.LENGTH_SHORT).show();
                }
            };
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    // this method is run in a worker thread
                    try {
                    // wait for 5 seconds to simulate a long network access
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Assume that we got our data from server
                    Bundle bundle = new Bundle();
                    bundle.putString("server_response", "some sample json here");

                    // notify main thread
                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            });
            t.start();





        }else if(item.getItemId()== R.id.search) {
            makeText(this, "Searching...", LENGTH_SHORT).show();
        }else if(item.getItemId()== R.id.setting) {
            Intent myIntent = new Intent(WeatherActivity.this, SettingsActivity.class);
            WeatherActivity.this.startActivity(myIntent);
            makeText(this, "Setting selected", LENGTH_SHORT).show();
        }else if(item.getItemId()== R.id.notification) {
            Intent myIntent = new Intent(WeatherActivity.this, NotificationActivity.class);
            WeatherActivity.this.startActivity(myIntent);

            makeText(this, " Notification selected", LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }


}
