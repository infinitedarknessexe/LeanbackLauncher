package com.rockon999.android.leanbacklauncher.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v17.preference.LeanbackPreferenceFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceScreen;
import com.rockon999.android.leanbacklauncher.R;
import com.rockon999.android.leanbacklauncher.apps.AppsManager;

public class AppsAndGamesPreferenceFragment extends LeanbackPreferenceFragment implements OnPreferenceChangeListener {
    private Preference mReorderAppsPref;
    private Preference mReorderGamesPref;

    public void onCreatePreferences(Bundle bundle, String s) {
        Context preferenceContext = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(preferenceContext);
        screen.setTitle((int) R.string.home_screen_order_content_title);
        AppsManager.SortingMode sortingMode = AppsManager.getSavedSortingMode(preferenceContext);
        SwitchPreference autoSort = new SwitchPreference(preferenceContext);
        autoSort.setTitle((int) R.string.apps_order_pref_label);
        autoSort.setOnPreferenceChangeListener(this);
        autoSort.setChecked(sortingMode == AppsManager.SortingMode.RECENCY);
        screen.addPreference(autoSort);
        this.mReorderAppsPref = new Preference(preferenceContext);
        this.mReorderAppsPref.setKey("reorderapps");
        this.mReorderAppsPref.setTitle((int) R.string.customize_app_order_action_title);
        screen.addPreference(this.mReorderAppsPref);
        this.mReorderGamesPref = new Preference(preferenceContext);
        this.mReorderGamesPref.setKey("reordergames");
        this.mReorderGamesPref.setTitle((int) R.string.customize_game_order_action_title);
        screen.addPreference(this.mReorderGamesPref);
        updateSortingPreferenceVisibility(sortingMode);
        setPreferenceScreen(screen);
    }

    public boolean onPreferenceChange(Preference preference, Object o) {
        AppsManager.SortingMode sortingMode = ((Boolean) o).booleanValue() ? AppsManager.SortingMode.RECENCY : AppsManager.SortingMode.FIXED;
        if (sortingMode != AppsManager.getSavedSortingMode(getContext())) {
            AppsManager.saveSortingMode(getActivity(), sortingMode);
            updateSortingPreferenceVisibility(sortingMode);
        }
        return true;
    }

    private void updateSortingPreferenceVisibility(AppsManager.SortingMode sortingMode) {
        boolean visible = sortingMode == AppsManager.SortingMode.FIXED;
        this.mReorderAppsPref.setVisible(visible);
        this.mReorderGamesPref.setVisible(visible);
    }

    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        Intent startMain;
        if ("reorderapps".equals(key)) {
            startMain = new Intent("android.intent.action.MAIN");
            startMain.addCategory("android.intent.category.HOME");
            startMain.putExtra("extra_start_customize_apps", true);
            startActivity(startMain);
            return true;
        } else if (!"reordergames".equals(key)) {
            return super.onPreferenceTreeClick(preference);
        } else {
            startMain = new Intent("android.intent.action.MAIN");
            startMain.addCategory("android.intent.category.HOME");
            startMain.putExtra("extra_start_customize_games", true);
            startActivity(startMain);
            return true;
        }
    }
}