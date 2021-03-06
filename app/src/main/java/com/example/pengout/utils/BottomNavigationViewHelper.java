package com.example.pengout.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.pengout.R;
import com.example.pengout.view.activity.BrowseActivity;
import com.example.pengout.view.activity.CreateEventActivity;
import com.example.pengout.view.activity.SearchActivity;
import com.example.pengout.view.activity.ChatActivity;
import com.example.pengout.view.activity.HomeActivity;
import com.example.pengout.view.activity.MyProfileActivity;
import com.example.pengout.view.activity.StalkActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHelper";

    public static void setupBottonNavigationView(BottomNavigationViewEx bottomNavigationViewEx){

        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx viewEx){
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.navigation_feed:
                        if(menuItem.isChecked()){
                            menuItem.setChecked(false);
                            break;
                        }
                        Intent intent1 = new Intent(context, HomeActivity.class); //ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.navigation_events:
                        if(menuItem.isChecked()){
                            menuItem.setChecked(false);
                            break;
                        }

                        Intent intent2 = new Intent(context, BrowseActivity.class); //ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.navigation_create:
                        if(menuItem.isChecked()){
                            menuItem.setChecked(false);
                            break;
                        }
                        Intent createEventIntent = new Intent(context, CreateEventActivity.class); //ACTIVITY_NUM = 2
                        context.startActivity(createEventIntent);
                        break;

                    case R.id.navigation_activity:
                        if(menuItem.isChecked()){
                            menuItem.setChecked(false);
                            break;
                        }
                        Intent stalkIntent = new Intent(context, StalkActivity.class); //ACTIVITY_NUM = 3
                        context.startActivity(stalkIntent);
                        break;


                    case R.id.navigation_my_profile:
                        if(menuItem.isChecked()){
                            menuItem.setChecked(false);
                            break;
                        }
                        Intent intent4 = new Intent(context, MyProfileActivity.class); //ACTIVITY_NUM = 4
                        context.startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }
}
