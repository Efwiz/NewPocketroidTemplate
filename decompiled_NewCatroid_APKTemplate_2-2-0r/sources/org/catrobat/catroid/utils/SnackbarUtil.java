package org.catrobat.catroid.utils;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SnackbarUtil.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class SnackbarUtil {
    private static final HashMap<Integer, Snackbar> CURRENTLY_SHOWN_HINTS = new HashMap<>();
    private static final int MAX_LINES = 5;
    public static final String SHOWN_HINT_LIST = "shown_hint_list";

    private SnackbarUtil() {
        throw new AssertionError("no");
    }

    public static void showHintSnackbar(final Activity activity, int resourceId) {
        dismissAllHints();
        String resourceName = activity.getResources().getResourceName(resourceId);
        if (areHintsEnabled(activity) && !wasHintDialogAlreadyShown(activity)) {
            showDisableHintsDialog(activity, resourceId);
        } else {
            if (wasHintAlreadyShown(activity, resourceName) || !areHintsEnabled(activity)) {
                return;
            }
            hintSnackbar(activity, resourceId);
        }
    }

    public static void hintSnackbar(final Activity activity, final int resourceId) {
        final String resourceName = activity.getResources().getResourceName(resourceId);
        View viewFindViewById = activity.findViewById(R.id.content);
        if (viewFindViewById == null) {
            return;
        }
        Snackbar snackbarMake = Snackbar.make(viewFindViewById, resourceId, -2);
        snackbarMake.setAction(org.DanVexTeam.NewCatroidApkTemplate.R.string.got_it, new View.OnClickListener() { // from class: org.catrobat.catroid.utils.SnackbarUtil.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SnackbarUtil.setHintShown(activity, resourceName);
            }
        });
        snackbarMake.addCallback(new Snackbar.Callback() { // from class: org.catrobat.catroid.utils.SnackbarUtil.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.google.android.material.snackbar.Snackbar.Callback, com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
            public void onShown(Snackbar snackbar) {
                SnackbarUtil.CURRENTLY_SHOWN_HINTS.put(Integer.valueOf(resourceId), snackbar);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.google.android.material.snackbar.Snackbar.Callback, com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
            public void onDismissed(Snackbar snackbar, int event) {
                SnackbarUtil.CURRENTLY_SHOWN_HINTS.remove(Integer.valueOf(resourceId));
            }
        });
        snackbarMake.setActionTextColor(ContextCompat.getColor(activity, org.DanVexTeam.NewCatroidApkTemplate.R.color.solid_black));
        View view = snackbarMake.getView();
        TextView textView = (TextView) view.findViewById(org.DanVexTeam.NewCatroidApkTemplate.R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextColor(ContextCompat.getColor(activity, org.DanVexTeam.NewCatroidApkTemplate.R.color.solid_white));
        view.setBackgroundColor(ContextCompat.getColor(activity, org.DanVexTeam.NewCatroidApkTemplate.R.color.snackbar));
        snackbarMake.show();
    }

    public static void setHintShown(Activity activity, String messageId) {
        Set<String> stringSetFromSharedPreferences = getStringSetFromSharedPreferences(activity);
        stringSetFromSharedPreferences.add(messageId);
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putStringSet(SHOWN_HINT_LIST, stringSetFromSharedPreferences).apply();
    }

    public static boolean wasHintAlreadyShown(Activity activity, String messageId) {
        return getStringSetFromSharedPreferences(activity).contains(messageId);
    }

    public static boolean areHintsEnabled(Activity activity) {
        return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(SettingsFragment.SETTINGS_SHOW_HINTS, false);
    }

    private static Set<String> getStringSetFromSharedPreferences(Context context) {
        return new HashSet(PreferenceManager.getDefaultSharedPreferences(context).getStringSet(SHOWN_HINT_LIST, new HashSet()));
    }

    public static void setHintDialogShown(Activity activity) {
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean(SharedPreferenceKeys.DISABLE_HINTS_DIALOG_SHOWN_PREFERENCE_KEY, true).apply();
    }

    public static boolean wasHintDialogAlreadyShown(Activity activity) {
        return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(SharedPreferenceKeys.DISABLE_HINTS_DIALOG_SHOWN_PREFERENCE_KEY, false);
    }

    public static boolean showDisableHintsDialog(final Activity activity, final int resourceId) {
        new AlertDialog.Builder(activity).setTitle(org.DanVexTeam.NewCatroidApkTemplate.R.string.dialog_disable_hints_title).setMessage(org.DanVexTeam.NewCatroidApkTemplate.R.string.dialog_disable_hints_text).setPositiveButton(org.DanVexTeam.NewCatroidApkTemplate.R.string.dialog_disable_hints_button_show, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.SnackbarUtil$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SnackbarUtil.handleShowHints(activity, resourceId);
            }
        }).setNegativeButton(org.DanVexTeam.NewCatroidApkTemplate.R.string.dialog_disable_hints_button_hide, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.SnackbarUtil$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SnackbarUtil.handleHideHints(activity);
            }
        }).setCancelable(false).show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void handleHideHints(Activity activity) {
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean(SettingsFragment.SETTINGS_SHOW_HINTS, false).apply();
        setHintDialogShown(activity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void handleShowHints(Activity activity, int resourceId) {
        setHintDialogShown(activity);
        hintSnackbar(activity, resourceId);
    }

    public static void dismissAllHints() {
        Iterator<Snackbar> it = CURRENTLY_SHOWN_HINTS.values().iterator();
        while (it.hasNext()) {
            it.next().dismiss();
        }
    }
}
