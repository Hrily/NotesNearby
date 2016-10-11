package com.hrily.notesnearby;
//////////////
// by hrily //
//////////////

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MapFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        AddNoteFragment.OnFragmentInteractionListener,
        ShowNoteFragment.OnFragmentInteractionListener{

    private String TAG = "MAIN";
    FloatingActionButton fab;

    private LatLng mCurrentLocation;
    private ArrayList<Note> notes;

    public LatLng getmCurrentLocation() {
        return mCurrentLocation;
    }

    public void setmCurrentLocation(LatLng mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<CustomItem> menuList = new ArrayList<CustomItem>();
        menuList.add(new CustomItem("View Map", R.drawable.map));
        menuList.add(new CustomItem("Post Note", R.drawable.note));
        menuList.add(new CustomItem("View in Air", R.drawable.air));

        ListView drawerList = (ListView) findViewById(R.id.my_menu);
        CustomAdapter adapter = new CustomAdapter(this, R.layout.my_custom_item, menuList);

        View header = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        drawerList.addHeaderView(header);

        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displaySelectedScreen(position);
            }
        });

        displaySelectedScreen(1);

    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case 1:
                fragment = new MapFragment();
                fab.setVisibility(View.VISIBLE);
                break;
            case 2:
                if(mCurrentLocation!=null) {
                    fragment = AddNoteFragment.newInstance(mCurrentLocation.latitude, mCurrentLocation.longitude);
                    fab.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(this, "No location data available. Please try later.", Toast.LENGTH_LONG).show();
                }
                break;
            case 3:
                double lats[] = new double[notes.size()];
                double lngs[] = new double[notes.size()];
                String titles[] = new String[notes.size()];
                String descs[] = new String[notes.size()];
                for(int i=0;i<notes.size();i++){
                    Note n = notes.get(i);
                    lats[i] = n.getLat();
                    lngs[i] = n.getLng();
                    titles[i] = n.getTitle();
                    descs[i] = n.getDesc();
                }
                Intent i = new Intent(this, ARActivity.class);
                i.putExtra("LATS", lats);
                i.putExtra("LNGS", lngs);
                i.putExtra("TITLES", titles);
                i.putExtra("DESCS", descs);
                startActivity(i);
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(LatLng latLng, ArrayList<Note> n) {
        mCurrentLocation = latLng;
        notes = n;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
