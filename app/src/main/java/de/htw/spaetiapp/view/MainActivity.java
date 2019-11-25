package de.htw.spaetiapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.htw.spaetiapp.R;

public class MainActivity extends AppCompatActivity {

    private final Fragment addSpaetiFragment = new AddSpaetiFragment();
    private final Fragment settingsFragment = new SettingsFragment();
    private final Fragment listFragment = new ListFragment();
    private final Fragment mapsFragment = new MapsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment activeFragment = mapsFragment;
    private boolean isLatestFragmentMap = true;

    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        menu = navigation.getMenu();
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        fm.beginTransaction().add(R.id.main_container, addSpaetiFragment).hide(addSpaetiFragment).commit();
        fm.beginTransaction().add(R.id.main_container, settingsFragment).hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.main_container, listFragment).hide(listFragment).commit();
        fm.beginTransaction().add(R.id.main_container, mapsFragment).commit();
    }

    public void onAddSpaetiNavButtonClicked(MenuItem item) {
        fm.beginTransaction().hide(activeFragment).show(addSpaetiFragment).commit();
        activeFragment = addSpaetiFragment;
        assignMiddleBarButtonIcon();
    }

    private void assignMiddleBarButtonIcon() {
        MenuItem tmpItem = menu.findItem(R.id.bottomNavigationListMenuId);
        if (isLatestFragmentMap) {
            tmpItem.setIcon(R.drawable.ic_map_black_24dp);
        } else {
            tmpItem.setIcon(R.drawable.ic_list_gray);
        }
    }

    public void onSettingsNavButtonClicked(MenuItem item) {

        fm.beginTransaction().hide(activeFragment).show(settingsFragment).commit();
        activeFragment = settingsFragment;
        assignMiddleBarButtonIcon();
    }

    public void onListNavButtonClicked(MenuItem item) {
        if (activeFragment == listFragment){
            fm.beginTransaction().hide(activeFragment).show(mapsFragment).commit();
            activeFragment = mapsFragment;
            isLatestFragmentMap = true;
            item.setIcon(R.drawable.ic_list_gray);
        } else if (activeFragment == mapsFragment){
            fm.beginTransaction().hide(activeFragment).show(listFragment).commit();
            activeFragment = listFragment;
            isLatestFragmentMap = false;
            item.setIcon(R.drawable.ic_map_black_24dp);
        } else {
            if (isLatestFragmentMap){
                fm.beginTransaction().hide(activeFragment).show(mapsFragment).commit();
                activeFragment = mapsFragment;
                item.setIcon(R.drawable.ic_list_gray);
            } else {
                fm.beginTransaction().hide(activeFragment).show(listFragment).commit();
                activeFragment = listFragment;
                item.setIcon(R.drawable.ic_map_black_24dp);
            }
        }
    }

}
