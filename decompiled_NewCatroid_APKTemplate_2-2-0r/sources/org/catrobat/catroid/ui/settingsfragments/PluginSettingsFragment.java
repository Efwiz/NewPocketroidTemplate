package org.catrobat.catroid.ui.settingsfragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;
import com.google.android.gms.cast.MediaError;
import com.reandroid.arsc.chunk.TypeBlock;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.bouncycastle.i18n.ErrorBundle;
import org.catrobat.catroid.plugins.PluginEventBus;
import org.catrobat.catroid.plugins.PluginInfo;
import org.catrobat.catroid.plugins.PluginManager;
import org.catrobat.catroid.utils.ThemeEngine;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/PluginSettingsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PluginSettingsFragment extends PreferenceFragmentCompat {
    public static final String SHARED_PREFS_PREFIX = "plugin_settings_";

    /* JADX WARN: Code duplicated, block: B:36:0x00d5  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        PluginInfo pluginByPackageName;
        int i;
        SwitchPreferenceCompat switchPreferenceCompat;
        Preference preference;
        Preference preference2;
        SeekBarPreference seekBarPreference;
        Preference preference3;
        final String string = getArguments().getString("plugin_package_name");
        if (string == null || (pluginByPackageName = PluginManager.getInstance(getContext()).getPluginByPackageName(string)) == null || !pluginByPackageName.hasSettings()) {
            return;
        }
        getPreferenceManager().setSharedPreferencesName(hasSharedSettings(pluginByPackageName) ? ThemeEngine.THEME_PREFS_NAME : SHARED_PREFS_PREFIX + string);
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(getContext()));
        try {
            FileInputStream fileInputStream = new FileInputStream(pluginByPackageName.settingsFile);
            byte[] bArr = new byte[(int) pluginByPackageName.settingsFile.length()];
            fileInputStream.read(bArr);
            fileInputStream.close();
            JSONArray jSONArray = new JSONArray(new String(bArr, StandardCharsets.UTF_8));
            int i2 = 0;
            for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                final JSONObject jSONObject = jSONArray.getJSONObject(i3);
                String string2 = jSONObject.getString(ASTExpr.DEFAULT_MAP_KEY_NAME);
                String string3 = jSONObject.getString(ModuleXmlParser.TYPE);
                String string4 = jSONObject.getString("title");
                switch (string3.hashCode()) {
                    case -1377687758:
                        if (string3.equals("button")) {
                            i = 4;
                        } else {
                            i = -1;
                        }
                        break;
                    case -899647263:
                        if (string3.equals("slider")) {
                            i = 3;
                        } else {
                            i = -1;
                        }
                        break;
                    case -891985903:
                        if (string3.equals("string")) {
                            i = 1;
                        } else {
                            i = -1;
                        }
                        break;
                    case 3322014:
                        if (string3.equals(SchemaSymbols.ATTVAL_LIST)) {
                            i = 2;
                        } else {
                            i = -1;
                        }
                        break;
                    case 64711720:
                        if (string3.equals("boolean")) {
                            i = i2;
                        } else {
                            i = -1;
                        }
                        break;
                    default:
                        i = -1;
                        break;
                }
                if (i != 0) {
                    if (i == 1) {
                        EditTextPreference editTextPreference = new EditTextPreference(getContext());
                        editTextPreference.setDefaultValue(jSONObject.optString("defaultValue", ""));
                        preference2 = editTextPreference;
                    } else if (i == 2) {
                        ListPreference listPreference = new ListPreference(getContext());
                        JSONArray jSONArray2 = jSONObject.getJSONArray(TypeBlock.NAME_entries);
                        JSONArray jSONArray3 = jSONObject.getJSONArray("entryValues");
                        CharSequence[] charSequenceArr = new CharSequence[jSONArray2.length()];
                        CharSequence[] charSequenceArr2 = new CharSequence[jSONArray3.length()];
                        for (int i4 = i2; i4 < jSONArray2.length(); i4++) {
                            charSequenceArr[i4] = jSONArray2.getString(i4);
                            charSequenceArr2[i4] = jSONArray3.getString(i4);
                        }
                        listPreference.setEntries(charSequenceArr);
                        listPreference.setEntryValues(charSequenceArr2);
                        listPreference.setDefaultValue(jSONObject.optString("defaultValue", ""));
                        preference2 = listPreference;
                    } else if (i == 3) {
                        seekBarPreference = new SeekBarPreference(getContext());
                        seekBarPreference.setDefaultValue(Integer.valueOf(jSONObject.optInt("defaultValue", 50)));
                        seekBarPreference.setMin(jSONObject.optInt("min", i2));
                        seekBarPreference.setMax(jSONObject.optInt("max", 100));
                        seekBarPreference.setShowSeekBarValue(true);
                    } else if (i != 4) {
                        preference = null;
                    } else {
                        preference3 = new Preference(getContext());
                        preference3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.PluginSettingsFragment$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference4) {
                                return this.f$0.m5817x6390baa3(jSONObject, string, preference4);
                            }
                        });
                    }
                    i2 = 0;
                    preference = preference2;
                } else {
                    switchPreferenceCompat = new SwitchPreferenceCompat(getContext());
                    i2 = 0;
                    switchPreferenceCompat.setDefaultValue(Boolean.valueOf(jSONObject.optBoolean("defaultValue", false)));
                }
                if (preference != null) {
                    preference = switchPreferenceCompat;
                    preference = seekBarPreference;
                    preference = preference3;
                    preference.setKey(string2);
                    preference.setTitle(string4);
                    preference.setSummary(jSONObject.optString(ErrorBundle.SUMMARY_ENTRY, ""));
                    getPreferenceScreen().addPreference(preference);
                } else {
                    preference = switchPreferenceCompat;
                    preference = seekBarPreference;
                    preference = preference3;
                }
            }
        } catch (Exception e) {
            Log.e("PluginSettingsFragment", MediaError.ERROR_TYPE_ERROR, e);
        }
    }

    /* JADX INFO: renamed from: lambda$onCreatePreferences$0$org-catrobat-catroid-ui-settingsfragments-PluginSettingsFragment, reason: not valid java name */
    /* synthetic */ boolean m5817x6390baa3(JSONObject jSONObject, String str, Preference preference) {
        String strOptString = jSONObject.optString("action", "");
        if (strOptString.isEmpty()) {
            return true;
        }
        Log.d("PluginSettings", "Button clicked, dispatching action: " + strOptString);
        PluginEventBus.getInstance().dispatch("Settings.onButtonAction", str, strOptString);
        Toast.makeText(getContext(), jSONObject.optString("toast", "Действие выполнено"), 0).show();
        return true;
    }

    private boolean hasSharedSettings(PluginInfo plugin) {
        try {
            FileInputStream fileInputStream = new FileInputStream(plugin.settingsFile);
            byte[] bArr = new byte[(int) plugin.settingsFile.length()];
            fileInputStream.read(bArr);
            fileInputStream.close();
            JSONArray jSONArray = new JSONArray(new String(bArr, StandardCharsets.UTF_8));
            for (int i = 0; i < jSONArray.length(); i++) {
                if ("shared".equals(jSONArray.getJSONObject(i).optString("storage"))) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        PluginInfo pluginByPackageName = PluginManager.getInstance(getContext()).getPluginByPackageName(getArguments().getString("plugin_package_name"));
        if (pluginByPackageName != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Настройки: " + pluginByPackageName.f589name);
        }
    }
}
