package de.htw.spaetiapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import de.htw.spaetiapp.R;

public class MainActivity extends AppCompatActivity {

    private final Fragment addSpaetiFragment = new AddSpaetiFragment();
    private final Fragment settingsFragment = new SettingsFragment();
    private final Fragment listFragment = new ListFragment();
    private final Fragment mapsFragment = new MapsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment activeFragment = mapsFragment;
    private boolean isLatestFragmentMap = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        fm.beginTransaction().add(R.id.main_container, addSpaetiFragment).hide(addSpaetiFragment).commit();
        fm.beginTransaction().add(R.id.main_container, settingsFragment).hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.main_container, listFragment).hide(listFragment).commit();
        fm.beginTransaction().add(R.id.main_container, mapsFragment).commit();

    }

    public void onAddSpaetiNavButtonClicked(MenuItem item) {
        fm.beginTransaction().hide(activeFragment).show(addSpaetiFragment).commit();
        activeFragment = addSpaetiFragment;
    }

    public void onSettingsNavButtonClicked(MenuItem item) {
        fm.beginTransaction().hide(activeFragment).show(settingsFragment).commit();
        activeFragment = settingsFragment;
    }

    public void onListNavButtonClicked(MenuItem item) {
        if (activeFragment == listFragment && isLatestFragmentMap){
            fm.beginTransaction().hide(activeFragment).show(mapsFragment).commit();
            activeFragment = mapsFragment;
            isLatestFragmentMap = true;
        } else {
            fm.beginTransaction().hide(activeFragment).show(listFragment).commit();
            activeFragment = listFragment;
            isLatestFragmentMap = false;
        }
    }

}
