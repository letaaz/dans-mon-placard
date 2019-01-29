package com.danstonplacard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import com.danstonplacard.view.SampleFragmentPagerAdapter;
import com.danstonplacard.R;

/**
 * Main activity of the application - Contains the toolbar - the fragments - Navigation Drawer
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TabLayout tabLayout;
    private int tab_position;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* content_main xml */ /* Help : https://guides.codepath.com/android/Google-Play-Style-Tabs-using-TabLayout#sliding-tabs-layout*/
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), this));

        /* Set the tabLayout of the application*/
        setTabLayout(viewPager);
    }

    /**
     * Set the tabLayout
     * @param viewPager The ViewPager which contains the TabLayout
     */
    private void setTabLayout(ViewPager viewPager) {
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /* Set icons to TabLayout */
//        int[] imageResId = {R.drawable.ic_fridge, R.drawable.ic_list, R.drawable.ic_recipe_book, R.drawable.ic_discount};
        int[] imageResId = {R.drawable.ic_fridge, R.drawable.ic_list};
        for(int i = 0; i < imageResId.length; i++)
        {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                tab_position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                return;
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(tab_position != 0 && this.getSupportFragmentManager().getBackStackEntryCount() == 0)
        {
            tabLayout.getTabAt(0).select();

        }
        else {
            super.onBackPressed();
        }
    }

    /**
     * Method that manages the different actions to be performed by the items in the navigation drawer
     * @param item The menuItem selected by the user
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        resetAllMenuItemsTextColor(navigationView);
        boolean selected = false;

        switch (item.getItemId()) {
            case R.id.nav_accueil:
                selected = true;
                setTextColorForMenuItem(item, R.color.white);
                break;
            case R.id.nav_blog:
                openPage("http://danstonplacardapp.wordpress.com");
                break;
            case R.id.nav_facebook:
                String facebookPageURLurl = getFacebookPageURL(this);
                openPage(facebookPageURLurl);
                break;
            case R.id.nav_about:
                Intent it = new Intent(this, AboutActivity.class);
                startActivity(it);
                break;
            case R.id.nav_notez_nous:
                String googlePlayRedirect = "market://details?id=" + getPackageName();
                openPage(googlePlayRedirect);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return selected;
    }

    /**
     * Method used to open a web page in a browser
     * @param url the address link to open
     */
    private void openPage(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    /**
     * Get the link to Facebook Page of the Application - In the format compatible with the mobile application Facebook
     * @param context Context of the activity
     * @return valid address
     */
    private String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String FACEBOOK_URL = "https://www.facebook.com/danstonplacardapp/";
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                long FACEBOOK_PAGE_ID = 255891538403374l;
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }

    private void setTextColorForMenuItem(MenuItem menuItem, @ColorRes int color) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, color)), 0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }

    private void resetAllMenuItemsTextColor(NavigationView navigationView) {
        for (int i = 0; i < navigationView.getMenu().size(); i++)
            setTextColorForMenuItem(navigationView.getMenu().getItem(i), R.color.text_black);
    }
}