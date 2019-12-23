package de.htw.spaetiapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
    private SharedPreferences settings;


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

        settings =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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

    public void addMarkerToMap(Spaeti s, boolean isBroadcast) {
        ((ListFragment) listFragment).notifyAdapter();
        MapsFragment fragment = (MapsFragment) mapsFragment;
        clearAddFragmentFields();
        fragment.addMarker(s);
        if (!isBroadcast) {
            hideKeyboard();
        }
    }

    private void clearAddFragmentFields() {
        ((AddSpaetiFragment) addSpaetiFragment).clearFields();
    }

    private void clearUpdateFragmentFields() {
        ((UpdateSpaetiFragment) updateFragment).clearFields();
    }

    public void removeSpaeti(String id) {
        deleteSpaetiController.deleteSpaeti(id);
    }

    public void updateSpaeti(Spaeti spaeti) {
        fm.beginTransaction().hide(activeFragment).show(updateFragment).commit();
        activeFragment = updateFragment;
        ((UpdateSpaetiFragment) updateFragment).setFields(spaeti);
    }


    public void removeMarkerFromMap(String id, boolean isBroadcast) {
        MapsFragment fragment = (MapsFragment) mapsFragment;
        ((ListFragment) listFragment).notifyAdapter();
        fragment.removeMarker(id, isBroadcast);
    }

    public void updateMarkerOnMap(Spaeti spaeti, boolean isBroadcast) {
        ((ListFragment) listFragment).notifyAdapter();
        MapsFragment fragment = (MapsFragment) mapsFragment;
        clearUpdateFragmentFields();
        fragment.removeMarker(spaeti.get_id(), isBroadcast);
        fragment.addMarker(spaeti);
        if (!isBroadcast) {
            hideKeyboard();
        }
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

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        view.clearFocus();

    }

    public void saveName(String name){
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("username", name);
        edit.apply();
    }

    public String loadName(){
        return settings.getString("username", "anonymous user");
    }

}
