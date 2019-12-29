package de.htw.spaetiapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.htw.spaetiapp.R;

import de.htw.spaetiapp.controller.AddSpaetiController;
import de.htw.spaetiapp.controller.DeleteSpaetiController;
import de.htw.spaetiapp.controller.UpdateSpaetiController;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.controller.ConnectionController;
import de.htw.spaetiapp.util.ToastResponse;

public class MainActivity extends AppCompatActivity {

    private final Fragment addFragment = new AddSpaetiFragment();
    private final Fragment settingsFragment = new SettingsFragment();
    private final Fragment listFragment = new ListFragment();
    private final Fragment mapsFragment = new MapsFragment();
    private final Fragment updateFragment = new UpdateSpaetiFragment();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment activeFragment;
    private boolean isLatestFragmentMap;
    private Menu menu;
    private ConnectionController connectionController;
    private AddSpaetiController addSpaetiController;
    private UpdateSpaetiController updateSpaetiController;
    private DeleteSpaetiController deleteSpaetiController;
    private SharedPreferences settings;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activeFragment = mapsFragment;
        isLatestFragmentMap = true;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        menu = bottomNavigationView.getMenu();

        settings =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        registerFragments();

        initController();
        connectionController.connectClient();
    }

    private void initController() {
        addSpaetiController = new AddSpaetiController(this);
        updateSpaetiController = new UpdateSpaetiController(this);
        deleteSpaetiController = new DeleteSpaetiController(this);
        connectionController = new ConnectionController().setAddController(addSpaetiController).setUpdateController(updateSpaetiController).setDeleteController(deleteSpaetiController);
    }

    private void registerFragments() {
        fm.beginTransaction().add(R.id.main_container, addFragment).hide(addFragment).commit();
        fm.beginTransaction().add(R.id.main_container, settingsFragment).hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.main_container, listFragment).hide(listFragment).commit();
        fm.beginTransaction().add(R.id.main_container, updateFragment).hide(updateFragment).commit();
        fm.beginTransaction().add(R.id.main_container, mapsFragment).commit();
    }

    public AddSpaetiController getAddSpaetiController() {
        return addSpaetiController;
    }

    public UpdateSpaetiController getUpdateSpaetiController() {
        return updateSpaetiController;
    }

    public DeleteSpaetiController getDeleteSpaetiController(){ return  deleteSpaetiController; }

    public void onAddSpaetiNavButtonClicked(MenuItem item) {
        fm.beginTransaction().hide(activeFragment).show(addFragment).commit();
        activeFragment = addFragment;
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

    public void addMarkerToMap(Spaeti spaeti, boolean isBroadcast) {
        ((ListFragment) listFragment).notifyAdapter();
        MapsFragment fragment = (MapsFragment) mapsFragment;
        clearAddFragmentFields();
        fragment.addMarker(spaeti);
        if (!isBroadcast) {
            hideKeyboard();
        }
    }

    private void clearAddFragmentFields() {
        ((AddSpaetiFragment) addFragment).clearFields();
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
        menu.findItem(R.id.bottomNavigationListMenuId).setIcon(R.drawable.ic_map_black_24dp);
        ((UpdateSpaetiFragment) updateFragment).setFields(spaeti);
    }


    public void removeMarkerFromMap(Marker marker, boolean isBroadcast) {
        MapsFragment fragment = (MapsFragment) mapsFragment;
        ((ListFragment) listFragment).notifyAdapter();
        fragment.removeMarker(marker, isBroadcast);
    }

    public void updateMarkerOnMap(Spaeti spaeti, boolean isBroadcast, Marker marker) {
        ((ListFragment) listFragment).notifyAdapter();
        MapsFragment fragment = (MapsFragment) mapsFragment;
        clearUpdateFragmentFields();
        fragment.removeMarker(marker, isBroadcast);
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
         //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
