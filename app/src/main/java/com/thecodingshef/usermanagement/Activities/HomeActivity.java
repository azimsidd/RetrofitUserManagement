package com.thecodingshef.usermanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thecodingshef.usermanagement.NavFragments.DashboardFragment;
import com.thecodingshef.usermanagement.NavFragments.ProfileFragment;
import com.thecodingshef.usermanagement.NavFragments.UsersFragment;
import com.thecodingshef.usermanagement.R;
import com.thecodingshef.usermanagement.SharedPrefManager;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView=findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());


        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment=null;

        switch (menuItem.getItemId()){
            case R.id.dashboard:
                fragment=new DashboardFragment();
                break;
            case R.id.users:
                fragment=new UsersFragment();
                break;
            case R.id.profile:
                fragment=new ProfileFragment();
                break;
        }

        if(fragment!=null){
            loadFragment(fragment);
        }

        return true;
    }


    void loadFragment(Fragment fragment){

        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout,fragment).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logoutmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.logout:
                logoutUser();
                break;

            case R.id.deleteAccount:
                deleteAccount();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAccount() {
    }

    private void logoutUser() {

        sharedPrefManager.logout();
        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();


    }
}