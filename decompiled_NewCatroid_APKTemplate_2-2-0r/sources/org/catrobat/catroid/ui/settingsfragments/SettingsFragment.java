package org.catrobat.catroid.ui.settingsfragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ai.KoveCallback;
import org.catrobat.catroid.ai.KoveManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.DroneConfigPreference;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.ProjectRecoveryManager;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.ide.IdeDashboardActivity;
import org.catrobat.catroid.sync.ProjectsCategoriesSync;
import org.catrobat.catroid.ui.CommunityLoginActivity;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.PluginsActivity;
import org.catrobat.catroid.ui.recyclerview.dialog.AppStoreDialogFragment;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.community.CommunityTokenManager;
import org.catrobat.catroid.utils.git.TokenManager;
import org.json.JSONObject;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/SettingsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SettingsFragment extends PreferenceFragment {
    public static final String ACCESSIBILITY_SCREEN_KEY = "setting_accessibility_screen";
    public static final String AI_SENSORS_SCREEN_KEY = "setting_ai_screen";
    public static final String DRONE_ALTITUDE_LIMIT = "setting_drone_altitude_limit";
    public static final String DRONE_CONFIGS = "setting_drone_basic_configs";
    public static final String DRONE_ROTATION_SPEED = "setting_drone_rotation_speed";
    public static final String DRONE_SCREEN_KEY = "settings_drone_screen";
    public static final String DRONE_SETTINGS_CATEGORY = "setting_drone_category";
    public static final String DRONE_TILT_ANGLE = "setting_drone_tilt_angle";
    public static final String DRONE_VERTICAL_SPEED = "setting_drone_vertical_speed";
    public static final String EV3_SCREEN_KEY = "setting_ev3_screen";
    public static final String EV3_SETTINGS_CATEGORY = "setting_ev3_category";
    private static final String GITHUB_CLIENT_ID = "Ov23liKoq3h0cTgAbVYA";
    private static final String GITHUB_REDIRECT_URI = "newcatroid://github-callback";
    public static final String IDE_SCREEN_KEY = "setting_ide_screen";
    public static final String NXT_SCREEN_KEY = "setting_nxt_screen";
    public static final String NXT_SETTINGS_CATEGORY = "setting_nxt_category";
    public static final String PLUGINS_SCREEN_KEY = "setting_plugins_screen";
    public static final String RASPBERRY_SCREEN_KEY = "settings_raspberry_screen";
    public static final String RASPI_CONNECTION_SETTINGS_CATEGORY = "setting_raspi_connection_settings_category";
    public static final String RASPI_HOST = "setting_raspi_host_preference";
    public static final String RASPI_PORT = "setting_raspi_port_preference";
    public static final String RASPI_VERSION_SPINNER = "setting_raspi_version_preference";
    public static final String SETTINGS_CAST_GLOBALLY_ENABLED = "setting_cast_globally_enabled";
    public static final String SETTINGS_CODE_ANALIZE = "pref_code_analysis_enabled";
    public static final String SETTINGS_CRASH_REPORTS = "setting_enable_crash_reports";
    public static final String SETTINGS_MINDSTORMS_EV3_BRICKS_CHECKBOX_PREFERENCE = "settings_mindstorms_ev3_bricks_checkbox_preference";
    public static final String SETTINGS_MINDSTORMS_EV3_BRICKS_PREFERENCE = "settings_mindstorms_ev3_bricks_preference";
    public static final String SETTINGS_MINDSTORMS_EV3_SHOW_SENSOR_INFO_BOX_DISABLED = "settings_mindstorms_ev3_show_sensor_info_box_disabled";
    public static final String SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE = "settings_mindstorms_nxt_bricks_checkbox_preference";
    public static final String SETTINGS_MINDSTORMS_NXT_BRICKS_PREFERENCE = "settings_mindstorms_nxt_bricks_preference";
    public static final String SETTINGS_MINDSTORMS_NXT_SHOW_SENSOR_INFO_BOX_DISABLED = "settings_mindstorms_nxt_show_sensor_info_box_disabled";
    public static final String SETTINGS_MULTILINGUAL = "setting_multilingual";
    public static final String SETTINGS_MULTIPLAYER_VARIABLES_ENABLED = "setting_multiplayer_variables_enabled";
    public static final String SETTINGS_OLD_DRAG = "setting_old_drag";
    public static final String SETTINGS_PARROT_AR_DRONE_CATROBAT_TERMS_OF_SERVICE_ACCEPTED_PERMANENTLY = "setting_parrot_ar_drone_catrobat_terms_of_service_accepted_permanently";
    public static final String SETTINGS_PARROT_JUMPING_SUMO_CATROBAT_TERMS_OF_SERVICE_ACCEPTED_PERMANENTLY = "setting_parrot_jumping_sumo_catrobat_terms_of_service_accepted_permanently";
    public static final String SETTINGS_RECOVER_PROJECTS = "setting_recover_projects";
    public static final String SETTINGS_SHOW_AI_FACE_DETECTION_SENSORS = "setting_ai_face_detection";
    public static final String SETTINGS_SHOW_AI_OBJECT_DETECTION_SENSORS = "setting_ai_object_detection";
    public static final String SETTINGS_SHOW_AI_POSE_DETECTION_SENSORS = "setting_ai_pose_detection";
    public static final String SETTINGS_SHOW_AI_SPEECH_RECOGNITION_SENSORS = "setting_ai_speech_recognition";
    public static final String SETTINGS_SHOW_AI_SPEECH_SYNTHETIZATION_SENSORS = "setting_ai_speech_synthetization";
    public static final String SETTINGS_SHOW_AI_TEXT_RECOGNITION_SENSORS = "setting_ai_text_recognition";
    public static final String SETTINGS_SHOW_ARDUINO_BRICKS = "setting_arduino_bricks";
    public static final String SETTINGS_SHOW_EMBROIDERY_BRICKS_CHECKBOX_PREFERENCE = "setting_embroidery_bricks_checkbox_preference";
    public static final String SETTINGS_SHOW_EMBROIDERY_BRICKS_PREFERENCE = "setting_embroidery_bricks_preference";
    public static final String SETTINGS_SHOW_FAST2D_BRICKS = "setting_fast2d_bricks";
    public static final String SETTINGS_SHOW_HINTS = "setting_enable_hints";
    public static final String SETTINGS_SHOW_INTERNET_BRICKS = "setting_internet_bricks";
    public static final String SETTINGS_SHOW_JUMPING_SUMO_BRICKS = "setting_parrot_jumping_sumo_bricks";
    public static final String SETTINGS_SHOW_NEURAL_BRICKS = "setting_neural_bricks";
    public static final String SETTINGS_SHOW_NFC_BRICKS = "setting_nfc_bricks";
    public static final String SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS = "setting_parrot_ar_drone_bricks";
    public static final String SETTINGS_SHOW_PHIRO_BRICKS_CHECKBOX_PREFERENCE = "setting_enable_phiro_bricks_checkbox_preference";
    public static final String SETTINGS_SHOW_PHIRO_BRICKS_PREFERENCE = "setting_enable_phiro_bricks_preference";
    public static final String SETTINGS_SHOW_PLOT_BRICKS = "setting_plot_bricks";
    public static final String SETTINGS_SHOW_POCKETENSOR_BRICKS = "setting_pocketensor_bricks";
    public static final String SETTINGS_SHOW_RASPI_BRICKS = "setting_raspi_bricks";
    public static final String SETTINGS_SHOW_THREED_BRICKS = "setting_threed_bricks";
    public static final String SETTINGS_TEST_BRICKS = "setting_test_bricks";
    public static final String SETTINGS_USE_CATBLOCKS = "settings_use_catblocks";
    private Preference communityPreference;
    private Preference githubPreference;
    PreferenceScreen screen = null;
    public static final String[] NXT_SENSORS = {"setting_mindstorms_nxt_sensor_1", "setting_mindstorms_nxt_sensor_2", "setting_mindstorms_nxt_sensor_3", "setting_mindstorms_nxt_sensor_4"};
    public static final String[] EV3_SENSORS = {"setting_mindstorms_ev3_sensor_1", "setting_mindstorms_ev3_sensor_2", "setting_mindstorms_ev3_sensor_3", "setting_mindstorms_ev3_sensor_4"};
    public static final String TAG = "SettingsFragment";

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        PreferenceScreen preferenceScreen;
        super.onCreate(savedInstanceState);
        setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.preferences);
        setHintPreferences();
        setLanguage();
        this.screen = getPreferenceScreen();
        PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference("category_additions");
        if (preferenceCategory != null && (preferenceScreen = (PreferenceScreen) findPreference(IDE_SCREEN_KEY)) != null) {
            preferenceCategory.removePreference(preferenceScreen);
        }
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(SETTINGS_SHOW_ARDUINO_BRICKS);
        checkBoxPreference.setEnabled(false);
        this.screen.removePreference(checkBoxPreference);
        Preference preferenceFindPreference = findPreference(SETTINGS_RECOVER_PROJECTS);
        if (preferenceFindPreference != null) {
            preferenceFindPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda7
                @Override // android.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.m5826xf39c51f8(preference);
                }
            });
        }
        Preference preferenceFindPreference2 = findPreference("setting_github_login");
        this.githubPreference = preferenceFindPreference2;
        preferenceFindPreference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda8
            @Override // android.preference.Preference.OnPreferenceClickListener
            public final boolean onPreferenceClick(Preference preference) {
                return this.f$0.m5827xf9a01d57(preference);
            }
        });
        Preference preferenceFindPreference3 = findPreference("setting_community_login");
        this.communityPreference = preferenceFindPreference3;
        if (preferenceFindPreference3 != null) {
            preferenceFindPreference3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda9
                @Override // android.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.m5828xffa3e8b6(preference);
                }
            });
        }
        setCorrectPreferenceViewForEmbroidery();
        setCorrectPreferenceViewForPhiro();
        final CheckBoxPreference checkBoxPreference2 = (CheckBoxPreference) findPreference("setting_developer_mode_enabled");
        final PreferenceScreen preferenceScreen2 = (PreferenceScreen) findPreference("setting_kove_ai_screen");
        final CheckBoxPreference checkBoxPreference3 = (CheckBoxPreference) findPreference("setting_kove_ai_autocomplete");
        if (checkBoxPreference2 != null && preferenceScreen2 != null && !checkBoxPreference2.isChecked()) {
            getPreferenceScreen().removePreference(preferenceScreen2);
        }
        if (checkBoxPreference2 != null) {
            checkBoxPreference2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment.1
                @Override // android.preference.Preference.OnPreferenceChangeListener
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (((Boolean) newValue).booleanValue()) {
                        new AlertDialog.Builder(SettingsFragment.this.getActivity()).setTitle(R.string.developer_mode_warning_title).setMessage(R.string.developer_mode_warning_message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment.1.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                checkBoxPreference2.setChecked(true);
                                PreferenceManager.getDefaultSharedPreferences(SettingsFragment.this.getActivity()).edit().putBoolean("setting_developer_mode_enabled", true).apply();
                                if (preferenceScreen2 != null) {
                                    SettingsFragment.this.getPreferenceScreen().addPreference(preferenceScreen2);
                                }
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment.1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                checkBoxPreference2.setChecked(false);
                            }
                        }).setCancelable(false).show();
                        return false;
                    }
                    if (preferenceScreen2 != null) {
                        SettingsFragment.this.getPreferenceScreen().removePreference(preferenceScreen2);
                    }
                    CheckBoxPreference checkBoxPreference4 = checkBoxPreference3;
                    if (checkBoxPreference4 == null) {
                        return true;
                    }
                    checkBoxPreference4.setChecked(false);
                    PreferenceManager.getDefaultSharedPreferences(SettingsFragment.this.getActivity()).edit().putBoolean("setting_kove_ai_autocomplete", false).apply();
                    return true;
                }
            });
        }
        if (checkBoxPreference3 != null) {
            checkBoxPreference3.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment.2
                @Override // android.preference.Preference.OnPreferenceChangeListener
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (!((Boolean) newValue).booleanValue() || KoveManager.isModelDownloaded(SettingsFragment.this.getActivity())) {
                        return true;
                    }
                    KoveManager.showDownloadDialog(SettingsFragment.this.getActivity(), new KoveCallback() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment.2.1
                        @Override // org.catrobat.catroid.ai.KoveCallback
                        public void onResult(boolean success) {
                            if (success) {
                                checkBoxPreference3.setChecked(true);
                            } else {
                                checkBoxPreference3.setChecked(false);
                            }
                        }
                    });
                    return false;
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onCreate$0$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ boolean m5826xf39c51f8(Preference preference) {
        showRecoveryDialog();
        return true;
    }

    /* JADX INFO: renamed from: lambda$onCreate$1$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ boolean m5827xf9a01d57(Preference preference) {
        if (TokenManager.INSTANCE.getToken(getActivity()) != null) {
            showLogoutDialog();
            return true;
        }
        startGitHubLogin();
        return true;
    }

    /* JADX INFO: renamed from: lambda$onCreate$2$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ boolean m5828xffa3e8b6(Preference preference) {
        if (CommunityTokenManager.isLoggedIn(getActivity())) {
            showCommunityLogoutDialog();
            return true;
        }
        startActivity(new Intent(getActivity(), (Class<?>) CommunityLoginActivity.class));
        return true;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.preference_title);
        updateGitHubPreference();
        updateCommunityPreference();
    }

    private void updateCommunityPreference() {
        Activity activity;
        if (this.communityPreference == null || (activity = getActivity()) == null) {
            return;
        }
        if (CommunityTokenManager.isLoggedIn(activity)) {
            this.communityPreference.setSummary("Вы вошли как @" + CommunityTokenManager.getUsername(activity) + " (Нажмите, чтобы выйти)");
        } else {
            this.communityPreference.setSummary("Войдите или зарегистрируйтесь, чтобы выкладывать свои проекты");
        }
    }

    private void showCommunityLogoutDialog() {
        new AlertDialog.Builder(getActivity()).setTitle("Выйти из сообщества?").setMessage("Вы уверены, что хотите выйти из своего аккаунта сообщества NewCatroid?").setPositiveButton("Выйти", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5831x2f3804c(dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showCommunityLogoutDialog$3$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ void m5831x2f3804c(DialogInterface dialogInterface, int i) {
        CommunityTokenManager.clearSession(getActivity());
        updateCommunityPreference();
    }

    private void updateGitHubPreference() {
        String token = TokenManager.INSTANCE.getToken(getActivity());
        if (token != null) {
            this.githubPreference.setSummary("Загрузка данных пользователя...");
            fetchGitHubUser(token);
        } else {
            this.githubPreference.setSummary(R.string.github_not_logged_in);
        }
    }

    private void fetchGitHubUser(final String token) {
        new Thread(new Runnable() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5824x75b9c200(token);
            }
        }).start();
    }

    /* JADX INFO: renamed from: lambda$fetchGitHubUser$5$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ void m5824x75b9c200(String str) {
        try {
            Response responseExecute = new OkHttpClient().newCall(new Request.Builder().url("https://api.github.com/user").header("Authorization", "Bearer " + str).build()).execute();
            try {
                if (responseExecute.isSuccessful()) {
                    final String string = new JSONObject(responseExecute.body().string()).getString(FirebaseAnalytics.Event.LOGIN);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda11
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m5823x6fb5f6a1(string);
                            }
                        });
                    }
                } else {
                    handleFailedFetch();
                }
                if (responseExecute != null) {
                    responseExecute.close();
                }
            } catch (Throwable th) {
                if (responseExecute != null) {
                    try {
                        responseExecute.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to fetch GitHub user", e);
            handleFailedFetch();
        }
    }

    /* JADX INFO: renamed from: lambda$fetchGitHubUser$4$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ void m5823x6fb5f6a1(String str) {
        this.githubPreference.setSummary(getString(R.string.github_logged_in_as, new Object[]{str}));
    }

    private void handleFailedFetch() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5825x82febc92();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$handleFailedFetch$6$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ void m5825x82febc92() {
        this.githubPreference.setSummary("Не удалось получить данные");
    }

    private void startGitHubLogin() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/login/oauth/authorize?client_id=Ov23liKoq3h0cTgAbVYA&redirect_uri=newcatroid://github-callback&scope=repo")));
    }

    private void showRecoveryDialog() {
        final List<ProjectRecoveryManager.LostProjectInfo> listScanForLostProjects = ProjectRecoveryManager.scanForLostProjects(getActivity());
        if (listScanForLostProjects.isEmpty()) {
            Toast.makeText(getActivity(), "Потерянных проектов не найдено", 0).show();
            return;
        }
        String[] strArr = new String[listScanForLostProjects.size()];
        final boolean[] zArr = new boolean[listScanForLostProjects.size()];
        for (int i = 0; i < listScanForLostProjects.size(); i++) {
            strArr[i] = listScanForLostProjects.get(i).projectName + "\n(" + listScanForLostProjects.get(i).folderName + Constant.AFTER_QUTO;
            zArr[i] = true;
        }
        new AlertDialog.Builder(getActivity()).setTitle("Найденные проекты (" + listScanForLostProjects.size() + Constant.AFTER_QUTO).setMultiChoiceItems(strArr, zArr, new DialogInterface.OnMultiChoiceClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public final void onClick(DialogInterface dialogInterface, int i2, boolean z) {
                SettingsFragment.lambda$showRecoveryDialog$7(zArr, dialogInterface, i2, z);
            }
        }).setPositiveButton("Восстановить", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.m5833xef2133b1(listScanForLostProjects, zArr, dialogInterface, i2);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    static /* synthetic */ void lambda$showRecoveryDialog$7(boolean[] zArr, DialogInterface dialogInterface, int i, boolean z) {
        zArr[i] = z;
    }

    /* JADX INFO: renamed from: lambda$showRecoveryDialog$8$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ void m5833xef2133b1(List list, boolean[] zArr, DialogInterface dialogInterface, int i) {
        performRecovery(list, zArr);
    }

    private void performRecovery(List<ProjectRecoveryManager.LostProjectInfo> projects, boolean[] checked) {
        int i = 0;
        for (int i2 = 0; i2 < projects.size(); i2++) {
            if (checked[i2]) {
                File file = new File(projects.get(i2).path, Constants.CODE_XML_FILE_NAME);
                if (file.exists()) {
                    file.setLastModified(System.currentTimeMillis());
                }
                i++;
            }
        }
        new AlertDialog.Builder(getActivity()).setTitle("Готово").setMessage("Обработано проектов: " + i + ". Приложение будет перезапущено для обновления списка.").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                this.f$0.m5829x3a4ccae(dialogInterface, i3);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$performRecovery$9$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ void m5829x3a4ccae(DialogInterface dialogInterface, int i) {
        restartApp();
    }

    private void restartApp() {
        Intent launchIntentForPackage;
        Activity activity = getActivity();
        if (activity == null || (launchIntentForPackage = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName())) == null) {
            return;
        }
        launchIntentForPackage.addFlags(335577088);
        activity.startActivity(launchIntentForPackage);
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.github_logout_dialog_title).setMessage(R.string.github_logout_dialog_message).setPositiveButton(R.string.github_logout_button, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5832x56a45125(dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showLogoutDialog$10$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ void m5832x56a45125(DialogInterface dialogInterface, int i) {
        TokenManager.INSTANCE.clearToken(getActivity());
        updateGitHubPreference();
    }

    @Override // android.preference.PreferenceFragment
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        key.hashCode();
        switch (key) {
            case "settings_drone_screen":
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new ParrotARDroneSettingsFragment(), ParrotARDroneSettingsFragment.TAG).addToBackStack(ParrotARDroneSettingsFragment.TAG).commit();
                break;
            case "setting_plugins_screen":
                startActivity(new Intent(getActivity(), (Class<?>) PluginsActivity.class));
                break;
            case "setting_ide_screen":
                startActivity(new Intent(getActivity(), (Class<?>) IdeDashboardActivity.class));
                break;
            case "setting_ai_screen":
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new AISettingsFragment(), AISettingsFragment.INSTANCE.getTAG()).addToBackStack(AISettingsFragment.INSTANCE.getTAG()).commit();
                break;
            case "setting_accessibility_screen":
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new AccessibilitySettingsFragment(), AccessibilitySettingsFragment.TAG).addToBackStack(AccessibilitySettingsFragment.TAG).commit();
                break;
            case "setting_ev3_screen":
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Ev3SensorsSettingsFragment(), Ev3SensorsSettingsFragment.TAG).addToBackStack(Ev3SensorsSettingsFragment.TAG).commit();
                break;
            case "settings_raspberry_screen":
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new RaspberryPiSettingsFragment(), RaspberryPiSettingsFragment.TAG).addToBackStack(RaspberryPiSettingsFragment.TAG).commit();
                break;
            case "setting_nxt_screen":
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new NXTSensorsSettingsFragment(), NXTSensorsSettingsFragment.TAG).addToBackStack(NXTSensorsSettingsFragment.TAG).commit();
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void setHintPreferences() {
        ((CheckBoxPreference) findPreference(SETTINGS_SHOW_HINTS)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment.3
            @Override // android.preference.Preference.OnPreferenceChangeListener
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.getEditor().remove(SnackbarUtil.SHOWN_HINT_LIST).commit();
                return true;
            }
        });
    }

    public static boolean isEmroiderySharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_EMBROIDERY_BRICKS_CHECKBOX_PREFERENCE, context);
    }

    public static boolean isPlotSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_PLOT_BRICKS, context);
    }

    public static boolean isNeuralSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_NEURAL_BRICKS, context);
    }

    public static boolean isPocketensorSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_POCKETENSOR_BRICKS, context);
    }

    public static boolean isFast2dSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_FAST2D_BRICKS, context);
    }

    public static boolean isOldDragEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_OLD_DRAG, context);
    }

    public static boolean isThreedSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_THREED_BRICKS, context);
    }

    public static boolean isInternetSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_INTERNET_BRICKS, context);
    }

    public static boolean isDroneSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS, context);
    }

    public static boolean isJSSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_JUMPING_SUMO_BRICKS, context);
    }

    public static boolean isMindstormsNXTSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE, context);
    }

    public static boolean isMindstormsEV3SharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_MINDSTORMS_EV3_BRICKS_CHECKBOX_PREFERENCE, context);
    }

    public static boolean isPhiroSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_PHIRO_BRICKS_CHECKBOX_PREFERENCE, context);
    }

    public static boolean isCastSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_CAST_GLOBALLY_ENABLED, context);
    }

    public static void setPhiroSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_PHIRO_BRICKS_CHECKBOX_PREFERENCE, value).apply();
    }

    public static void setJumpingSumoSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_JUMPING_SUMO_BRICKS, value).apply();
    }

    public static void setArduinoSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_ARDUINO_BRICKS, value).apply();
    }

    public static void setRaspiSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_RASPI_BRICKS, value).apply();
    }

    public static void setPlotSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_PLOT_BRICKS, value).apply();
    }

    public static void setNeuralSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_NEURAL_BRICKS, value).apply();
    }

    public static void setOldDragEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_OLD_DRAG, value).apply();
    }

    public static void setThreedSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_THREED_BRICKS, value).apply();
    }

    public static void setInternetSharedPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_INTERNET_BRICKS, value).apply();
    }

    public static boolean isAISpeechRecognitionSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_AI_SPEECH_RECOGNITION_SENSORS, context);
    }

    public static void setAISpeechReconitionPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_AI_SPEECH_RECOGNITION_SENSORS, value).apply();
    }

    public static boolean isAIFaceDetectionSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_AI_FACE_DETECTION_SENSORS, context);
    }

    public static void setAIFaceDetectionPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_AI_FACE_DETECTION_SENSORS, value).apply();
    }

    public static boolean isAIPoseDetectionSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_AI_POSE_DETECTION_SENSORS, context);
    }

    public static void setAIPoseDetectionPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_AI_POSE_DETECTION_SENSORS, value).apply();
    }

    public static boolean isAISpeechSynthetizationSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_AI_SPEECH_SYNTHETIZATION_SENSORS, context);
    }

    public static void setAISpeechSynthetizationPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_AI_SPEECH_SYNTHETIZATION_SENSORS, value).apply();
    }

    public static boolean isAITextRecognitionSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_AI_TEXT_RECOGNITION_SENSORS, context);
    }

    public static void setAITextRecognitionPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_AI_TEXT_RECOGNITION_SENSORS, value).apply();
    }

    public static boolean isAIObjectDetectionSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_AI_OBJECT_DETECTION_SENSORS, context);
    }

    public static void setAIObjectDetectionPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_AI_OBJECT_DETECTION_SENSORS, value).apply();
    }

    public static boolean isArduinoSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_ARDUINO_BRICKS, context);
    }

    public static boolean isRaspiSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_RASPI_BRICKS, context);
    }

    public static boolean isNfcSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_NFC_BRICKS, context);
    }

    public static boolean isTestSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_TEST_BRICKS, context);
    }

    private static boolean getBooleanSharedPreference(boolean defaultValue, String settingsString, Context context) {
        return getSharedPreferences(context).getBoolean(settingsString, defaultValue);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static NXTSensor.Sensor[] getLegoNXTSensorMapping(Context context) {
        NXTSensor.Sensor[] sensorArr = new NXTSensor.Sensor[4];
        for (int i = 0; i < 4; i++) {
            sensorArr[i] = NXTSensor.Sensor.getSensorFromSensorCode(getSharedPreferences(context).getString(NXT_SENSORS[i], null));
        }
        return sensorArr;
    }

    public static EV3Sensor.Sensor[] getLegoEV3SensorMapping(Context context) {
        EV3Sensor.Sensor[] sensorArr = new EV3Sensor.Sensor[4];
        for (int i = 0; i < 4; i++) {
            sensorArr[i] = EV3Sensor.Sensor.getSensorFromSensorCode(getSharedPreferences(context).getString(EV3_SENSORS[i], null));
        }
        return sensorArr;
    }

    public static String getRaspiHost(Context context) {
        return getSharedPreferences(context).getString(RASPI_HOST, null);
    }

    public static int getRaspiPort(Context context) {
        return Integer.parseInt(getSharedPreferences(context).getString(RASPI_PORT, null));
    }

    public static String getRaspiRevision(Context context) {
        return getSharedPreferences(context).getString(RASPI_VERSION_SPINNER, null);
    }

    public static void setLegoMindstormsNXTSensorMapping(Context context, NXTSensor.Sensor[] sensorMapping) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        int i = 0;
        while (true) {
            String[] strArr = NXT_SENSORS;
            if (i < strArr.length) {
                editorEdit.putString(strArr[i], sensorMapping[i].getSensorCode());
                i++;
            } else {
                editorEdit.apply();
                return;
            }
        }
    }

    public static void setLegoMindstormsEV3SensorMapping(Context context, EV3Sensor.Sensor[] sensorMapping) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        int i = 0;
        while (true) {
            String[] strArr = EV3_SENSORS;
            if (i < strArr.length) {
                editorEdit.putString(strArr[i], sensorMapping[i].getSensorCode());
                i++;
            } else {
                editorEdit.apply();
                return;
            }
        }
    }

    public static void setLegoMindstormsNXTSensorMapping(Context context, NXTSensor.Sensor sensor, String sensorSetting) {
        getSharedPreferences(context).edit().putString(sensorSetting, sensor.getSensorCode()).apply();
    }

    public static void setLegoMindstormsEV3SensorMapping(Context context, EV3Sensor.Sensor sensor, String sensorSetting) {
        getSharedPreferences(context).edit().putString(sensorSetting, sensor.getSensorCode()).apply();
    }

    public static DroneConfigPreference.Preferences[] getDronePreferenceMapping(Context context) {
        String[] strArr = {DRONE_CONFIGS, DRONE_ALTITUDE_LIMIT, DRONE_VERTICAL_SPEED, DRONE_ROTATION_SPEED, DRONE_TILT_ANGLE};
        DroneConfigPreference.Preferences[] preferencesArr = new DroneConfigPreference.Preferences[5];
        for (int i = 0; i < 5; i++) {
            preferencesArr[i] = DroneConfigPreference.Preferences.getPreferenceFromPreferenceCode(getSharedPreferences(context).getString(strArr[i], null));
        }
        return preferencesArr;
    }

    public static DroneConfigPreference.Preferences getDronePreferenceMapping(Context context, String preferenceSetting) {
        return DroneConfigPreference.Preferences.getPreferenceFromPreferenceCode(getSharedPreferences(context).getString(preferenceSetting, null));
    }

    public static void enableLegoMindstormsNXTBricks(Context context) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE, true).apply();
    }

    public static void enableLegoMindstormsEV3Bricks(Context context) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_MINDSTORMS_EV3_BRICKS_CHECKBOX_PREFERENCE, true).apply();
    }

    public static void setMultiplayerVariablesPreferenceEnabled(Context context, boolean value) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_MULTIPLAYER_VARIABLES_ENABLED, value).apply();
    }

    public static boolean isMultiplayerVariablesPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_MULTIPLAYER_VARIABLES_ENABLED, context) || ProjectManager.getInstance().getCurrentProject().hasMultiplayerVariables();
    }

    private void setLanguage() {
        ArrayList arrayList = new ArrayList();
        for (String str : SharedPreferenceKeys.LANGUAGE_TAGS) {
            if (SharedPreferenceKeys.DEVICE_LANGUAGE.equals(str)) {
                arrayList.add(getResources().getString(R.string.device_language));
            } else {
                Locale localeForLanguageTag = Locale.forLanguageTag(str);
                arrayList.add(localeForLanguageTag.getDisplayName(localeForLanguageTag));
            }
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        ListPreference listPreference = (ListPreference) findPreference(SETTINGS_MULTILINGUAL);
        listPreference.setEntries(strArr);
        listPreference.setEntryValues(SharedPreferenceKeys.LANGUAGE_TAGS);
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda0
            @Override // android.preference.Preference.OnPreferenceChangeListener
            public final boolean onPreferenceChange(Preference preference, Object obj) {
                return this.f$0.m5830x6d2b3654(preference, obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setLanguage$12$org-catrobat-catroid-ui-settingsfragments-SettingsFragment, reason: not valid java name */
    /* synthetic */ boolean m5830x6d2b3654(Preference preference, Object obj) {
        setLanguageSharedPreference(getActivity().getBaseContext(), obj.toString());
        startActivity(new Intent(getActivity().getBaseContext(), (Class<?>) MainMenuActivity.class));
        getActivity().finishAffinity();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                ((ProjectsCategoriesSync) KoinJavaComponent.inject(ProjectsCategoriesSync.class).getValue()).sync(true);
            }
        });
        return true;
    }

    public static void setToChosenLanguage(Activity activity) {
        Locale localeForLanguageTag;
        String string = getSharedPreferences(activity.getApplicationContext()).getString(SharedPreferenceKeys.LANGUAGE_TAG_KEY, "");
        if (!string.equals(SharedPreferenceKeys.DEVICE_LANGUAGE) && Arrays.asList(SharedPreferenceKeys.LANGUAGE_TAGS).contains(string)) {
            localeForLanguageTag = Locale.forLanguageTag(string);
        } else {
            localeForLanguageTag = Locale.forLanguageTag(CatroidApplication.defaultSystemLanguage);
        }
        Locale.setDefault(localeForLanguageTag);
        updateLocale(activity, localeForLanguageTag);
        updateLocale(activity.getApplicationContext(), localeForLanguageTag);
        SensorHandler.setUserLocaleTag(localeForLanguageTag.toLanguageTag());
    }

    public static void updateLocale(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static void setLanguageSharedPreference(Context context, String value) {
        getSharedPreferences(context).edit().putString(SharedPreferenceKeys.LANGUAGE_TAG_KEY, value).apply();
    }

    public static void removeLanguageSharedPreference(Context context) {
        getSharedPreferences(context).edit().remove(SharedPreferenceKeys.LANGUAGE_TAG_KEY).apply();
    }

    public static boolean useCatBlocks(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_USE_CATBLOCKS, context);
    }

    public static void setUseCatBlocks(Context context, boolean useCatBlocks) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_USE_CATBLOCKS, useCatBlocks).apply();
    }

    private void setCorrectPreferenceViewForEmbroidery() {
        getPreferenceScreen().removePreference(findPreference(SETTINGS_SHOW_EMBROIDERY_BRICKS_PREFERENCE));
    }

    private /* synthetic */ boolean lambda$setCorrectPreferenceViewForEmbroidery$13(Preference preference) {
        AppStoreDialogFragment.newInstance(AppStoreDialogFragment.Companion.Extension.EMBROIDERY).show(((FragmentActivity) getActivity()).getSupportFragmentManager(), AppStoreDialogFragment.INSTANCE.getTAG());
        return true;
    }

    private void setCorrectPreferenceViewForPhiro() {
        getPreferenceScreen().removePreference(findPreference(SETTINGS_SHOW_PHIRO_BRICKS_PREFERENCE));
    }

    private /* synthetic */ boolean lambda$setCorrectPreferenceViewForPhiro$14(Preference preference) {
        AppStoreDialogFragment.newInstance(AppStoreDialogFragment.Companion.Extension.PHIRO).show(((FragmentActivity) getActivity()).getSupportFragmentManager(), AppStoreDialogFragment.INSTANCE.getTAG());
        return true;
    }
}
