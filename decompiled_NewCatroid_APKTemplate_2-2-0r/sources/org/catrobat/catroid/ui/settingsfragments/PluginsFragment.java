package org.catrobat.catroid.ui.settingsfragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.plugins.PluginInfo;
import org.catrobat.catroid.plugins.PluginManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/PluginsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PluginsFragment extends PreferenceFragmentCompat {
    public static final String TAG = "PluginsFragment";
    private ActivityResultLauncher<Intent> importPluginLauncher;
    private PreferenceCategory installedPluginsCategory;
    private PluginManager pluginManager;

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.importPluginLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: org.catrobat.catroid.ui.settingsfragments.PluginsFragment$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.m5818x34149b0b((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onCreate$0$org-catrobat-catroid-ui-settingsfragments-PluginsFragment, reason: not valid java name */
    /* synthetic */ void m5818x34149b0b(ActivityResult activityResult) {
        Intent data;
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null || data.getData() == null) {
            return;
        }
        installPlugin(data.getData());
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.plugins_preferences, rootKey);
        this.pluginManager = PluginManager.getInstance(getActivity());
        this.installedPluginsCategory = (PreferenceCategory) findPreference("plugins_installed_category");
        Preference preferenceFindPreference = findPreference("plugin_import_button");
        if (preferenceFindPreference != null) {
            preferenceFindPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.PluginsFragment$$ExternalSyntheticLambda0
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.m5819x22fc97b4(preference);
                }
            });
        }
        populatePluginList();
    }

    /* JADX INFO: renamed from: lambda$onCreatePreferences$1$org-catrobat-catroid-ui-settingsfragments-PluginsFragment, reason: not valid java name */
    /* synthetic */ boolean m5819x22fc97b4(Preference preference) {
        openFilePicker();
        return true;
    }

    private void openFilePicker() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        this.importPluginLauncher.launch(intent);
    }

    private void installPlugin(Uri uri) {
        if (this.pluginManager.installPluginFromUri(uri)) {
            Toast.makeText(getActivity(), "Плагин успешно установлен! (не забудьте перезапустить приложение)", 0).show();
        } else {
            Toast.makeText(getActivity(), "Ошибка установки. Убедитесь, что вы выбрали корректный .nplug файл и он не поврежден.", 1).show();
        }
        populatePluginList();
    }

    private void populatePluginList() {
        this.installedPluginsCategory.removeAll();
        List<PluginInfo> installedPlugins = this.pluginManager.getInstalledPlugins();
        if (installedPlugins.isEmpty()) {
            Preference preference = new Preference(getContext());
            preference.setTitle("Нет установленных плагинов");
            preference.setEnabled(false);
            this.installedPluginsCategory.addPreference(preference);
            return;
        }
        for (final PluginInfo pluginInfo : installedPlugins) {
            Preference preference2 = new Preference(getContext());
            preference2.setKey(pluginInfo.packageName);
            preference2.setSummary(pluginInfo.description);
            preference2.setTitle((pluginInfo.isEnabled ? "[ВКЛ]" : "[ВЫКЛ]") + " " + pluginInfo.f589name + " (v" + pluginInfo.version + Constant.AFTER_QUTO);
            preference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.PluginsFragment$$ExternalSyntheticLambda3
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference3) {
                    return this.f$0.m5820xf92b3f8d(pluginInfo, preference3);
                }
            });
            this.installedPluginsCategory.addPreference(preference2);
        }
    }

    /* JADX INFO: renamed from: lambda$populatePluginList$2$org-catrobat-catroid-ui-settingsfragments-PluginsFragment, reason: not valid java name */
    /* synthetic */ boolean m5820xf92b3f8d(PluginInfo pluginInfo, Preference preference) {
        showPluginActionsDialog(pluginInfo);
        return true;
    }

    private void showPluginActionsDialog(final PluginInfo plugin) {
        CharSequence[] charSequenceArr;
        String str = plugin.isEnabled ? "Отключить" : "Включить";
        if (plugin.hasSettings()) {
            charSequenceArr = new CharSequence[]{str, "Удалить", "Настроить"};
        } else {
            charSequenceArr = new CharSequence[]{str, "Удалить"};
        }
        new AlertDialog.Builder(getActivity()).setTitle("Действия для: " + plugin.f589name).setItems(charSequenceArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.PluginsFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5822x13f8dd6e(plugin, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showPluginActionsDialog$3$org-catrobat-catroid-ui-settingsfragments-PluginsFragment, reason: not valid java name */
    /* synthetic */ void m5822x13f8dd6e(PluginInfo pluginInfo, DialogInterface dialogInterface, int i) {
        if (i == 0) {
            this.pluginManager.setPluginEnabled(pluginInfo.packageName, !pluginInfo.isEnabled);
            populatePluginList();
        } else if (i == 1) {
            showDeleteConfirmationDialog(pluginInfo);
        } else if (i == 2) {
            openPluginSettings(pluginInfo);
        }
    }

    private void openPluginSettings(PluginInfo plugin) {
        PluginSettingsFragment pluginSettingsFragment = new PluginSettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("plugin_package_name", plugin.packageName);
        pluginSettingsFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, pluginSettingsFragment).addToBackStack(null).commit();
    }

    private void showDeleteConfirmationDialog(final PluginInfo plugin) {
        new AlertDialog.Builder(getActivity()).setTitle("Удаление плагина").setMessage("Вы уверены, что хотите навсегда удалить '" + plugin.f589name + "'?").setPositiveButton("Удалить", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.PluginsFragment$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5821x5f2e99d9(plugin, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    /* JADX INFO: renamed from: lambda$showDeleteConfirmationDialog$4$org-catrobat-catroid-ui-settingsfragments-PluginsFragment, reason: not valid java name */
    /* synthetic */ void m5821x5f2e99d9(PluginInfo pluginInfo, DialogInterface dialogInterface, int i) {
        this.pluginManager.deletePlugin(pluginInfo);
        populatePluginList();
        Toast.makeText(requireContext(), "Плагин удален! (не забудьте перезапустить приложение)", 0).show();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Плагины");
    }
}
