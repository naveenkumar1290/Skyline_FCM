package planet.info.skyline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import planet.info.skyline.adapter.TabsPagerAdapter_ShowJobFiles;
import planet.info.skyline.util.Utility;


public class Show_Jobs_Activity_New extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    View mRoot;
    ArrayList<String> tabs;
    String jobtxt_id;
    SharedPreferences sp;
    List<HashMap<String, String>> list_ProjectPhotos = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Menu _menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__jobs___new);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(Utility.getTitle("Job File(s)"));
        //setTitle("Pay at Shop");
        tabs = new ArrayList<>();
        tabs.add("Job File(s)");
        tabs.add("Client File(s)");
        tabs.add("Project File(s)");
        sp = getApplicationContext().getSharedPreferences("skyline", getApplicationContext().MODE_PRIVATE);
        jobtxt_id = sp.getString(Utility.KEY_JOB_ID_FOR_JOBFILES, "");

        if (jobtxt_id != null && (!jobtxt_id.equalsIgnoreCase(""))) {
            setTabs_ViewPager();
        } else {
            Toast.makeText(Show_Jobs_Activity_New.this, "Job id not found !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.showChatHead(getApplicationContext());
    }

    void setTabs_ViewPager() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_payAtShop);
        for (int i = 0; i < tabs.size(); i++) {
            try {

                tabLayout.addTab(tabLayout.newTab().setText(tabs.get(i)));
            } catch (Exception e) {
                e.getMessage();
            }
        }

        /*<<adding tab dividers*/
        try {
            LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
            linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.gray_btn_bg_color));
            drawable.setSize(1, 1);
            linearLayout.setDividerPadding(10);
            linearLayout.setDividerDrawable(drawable);

        } catch (Exception e) {
            e.getMessage();
        }
        /*tab dividers>>>*/
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager_payAtShop);
        //Creating our pager adapter
        TabsPagerAdapter_ShowJobFiles adapter = new TabsPagerAdapter_ShowJobFiles(getSupportFragmentManager(), tabLayout.getTabCount(), jobtxt_id);
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        //tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0f, true);
                try {
                    if (position == 0) {
                        _menu.findItem(R.id.menu_share).setVisible(true);
                    } else {
                        _menu.findItem(R.id.menu_share).setVisible(false);
                    }
                }catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0f, true);
             try {
                 if (position == 1) {
                     _menu.findItem(R.id.menu_share).setVisible(false);
                 } else {
                     _menu.findItem(R.id.menu_share).setVisible(true);
                 }
             }catch (Exception e){
                 e.getMessage();
             }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {


            }
        });
*/

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {


    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            case R.id.menu_share:

                ShareToClient();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_jobfiles, menu);
        _menu = menu;

        return true;
    }

    public void ShareToClient() {

        List<HashMap<String, String>> list = getProjectPhotoList();
        List<HashMap<String, String>> list_selected = new ArrayList<>();

        int selectedFileLength = 0;

        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hashMap = list.get(i);
            if (hashMap.containsKey("isSelected")) {
                String isSelected = hashMap.get("isSelected");
                if (isSelected.equalsIgnoreCase("true")) {
                    selectedFileLength++;
                    list_selected.add(hashMap);
                }
            }
        }

        if (selectedFileLength < 1) {
            Toast.makeText(getApplicationContext(), "No file selected!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), selectedFileLength + " file(s) selected!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Show_Jobs_Activity_New.this, SharePhotosToClientActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) list_selected);
            intent.putExtra("BUNDLE", args);
            startActivity(intent);

            finish();

        }


    }

    public List<HashMap<String, String>> getProjectPhotoList() {
        return list_ProjectPhotos;
    }

    public void setProjectPhotoList(List<HashMap<String, String>> list) {
        list_ProjectPhotos = list;
    }
}
