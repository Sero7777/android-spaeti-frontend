package de.htw.spaetiapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URISyntaxException;

import de.htw.spaetiapp.R;

import de.htw.spaetiapp.controller.AddSpaetiController;
import de.htw.spaetiapp.controller.DeleteSpaetiController;
import de.htw.spaetiapp.controller.UpdateSpaetiController;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.controller.ConnectionController;
import de.htw.spaetiapp.util.ToastResponse;


public class MainActivity extends AppCompatActivity {

    private final Fragment addSpaetiFragment = new AddSpaetiFragment();
    private final Fragment settingsFragment = new SettingsFragment();
    private final Fragment listFragment = new ListFragment();
    private final Fragment mapsFragment = new MapsFragment();
    private final Fragment updateFragment = new UpdateSpaetiFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment activeFragment = mapsFragment;
    private boolean isLatestFragmentMap = true;
    private static SpaetiRepository repo = SpaetiRepository.getInstance();
    private Menu menu;
    private ConnectionController connectionController;
    private AddSpaetiController addSpaetiController;
    private UpdateSpaetiController updateSpaetiController;
    private DeleteSpaetiController deleteSpaetiController;


    public AddSpaetiController getAddSpaetiController() {
        return addSpaetiController;
    }

    public UpdateSpaetiController getUpdateSpaetiController() {
        return updateSpaetiController;
    }

    public DeleteSpaetiController getDeleteSpaetiController() {
        return deleteSpaetiController;
    }

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
        fm.beginTransaction().add(R.id.main_container, updateFragment).hide(updateFragment).commit();
        fm.beginTransaction().add(R.id.main_container, mapsFragment).commit();


        addSpaetiController = new AddSpaetiController(this);
        updateSpaetiController = new UpdateSpaetiController(this);
        deleteSpaetiController = new DeleteSpaetiController(this);
        connectionController = new ConnectionController(addSpaetiController, updateSpaetiController, deleteSpaetiController);


    }

    public void onAddSpaetiNavButtonClicked(MenuItem item) {
        fm.beginTransaction().hide(activeFragment).show(addSpaetiFragment).commit();
        activeFragment = addSpaetiFragment;
        assignMiddleBarButtonIcon();
    }

    public void AddSpaetiToRepo(Spaeti obj) {
        repo.addSpaeti(obj);
        //  MapsFragment fragment = fm.findFragmentById(R.id.mapFragment);
        // fragment.yourPublicMethod();
        ((MapsFragment) mapsFragment).addMarker(obj);
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
        if (activeFragment == listFragment) {
            fm.beginTransaction().hide(activeFragment).show(mapsFragment).commit();
            activeFragment = mapsFragment;
            isLatestFragmentMap = true;
            item.setIcon(R.drawable.ic_list_gray);
        } else if (activeFragment == mapsFragment) {
            fm.beginTransaction().hide(activeFragment).show(listFragment).commit();
            activeFragment = listFragment;
            isLatestFragmentMap = false;
            item.setIcon(R.drawable.ic_map_black_24dp);
        } else {
            if (isLatestFragmentMap) {
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

    public Fragment getMapsFragment() {
        return mapsFragment;
    }

    public void addMarkerToMap(Spaeti s) {
        MapsFragment fragment = (MapsFragment) mapsFragment;
        fragment.addMarker(s);
    }

    public void removeSpaeti(String id) {
        deleteSpaetiController.deleteSpaeti(id);
    }

    public void updateSpaeti(Spaeti spaeti) {
        fm.beginTransaction().hide(activeFragment).show(updateFragment).commit();
        activeFragment = updateFragment;
        ((UpdateSpaetiFragment) updateFragment).setFields(spaeti);
    }


    public void removeMarkerFromMap() {
        MapsFragment fragment = (MapsFragment) mapsFragment;
        fragment.removeMarker();
    }

    public void updateMarkerOnMap(Spaeti spaeti) {
        MapsFragment fragment = (MapsFragment) mapsFragment;
        fragment.removeMarker();
        fragment.addMarker(spaeti);
    }


    public void showMainView() {
        if (isLatestFragmentMap) {
            fm.beginTransaction().hide(activeFragment).show(mapsFragment).commit();
            activeFragment = mapsFragment;
        } else {
            fm.beginTransaction().hide(activeFragment).show(listFragment).commit();
            activeFragment = listFragment;
        }
    }

    public void toastInMap(ToastResponse response) {
        ((MapsFragment) mapsFragment).toastOperationAdd(response);
    }
}
