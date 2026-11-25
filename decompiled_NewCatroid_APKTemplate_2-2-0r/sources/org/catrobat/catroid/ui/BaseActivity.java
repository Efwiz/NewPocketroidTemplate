package org.catrobat.catroid.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.BuildConfig;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.Survey;
import org.catrobat.catroid.content.MyActivityManager;
import org.catrobat.catroid.python.PythonEngine;
import org.catrobat.catroid.ui.runtimepermissions.PermissionHandlingActivity;
import org.catrobat.catroid.ui.runtimepermissions.PermissionRequestActivityExtension;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfile;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BaseActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: BaseActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J\u0018\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001aH\u0002J\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!H\u0002J\u0012\u0010\"\u001a\u00020\u00142\b\u0010#\u001a\u0004\u0018\u00010$H\u0002J\u0010\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020\u0014H\u0004J\u0010\u0010)\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001aH\u0002J\u0010\u0010*\u001a\u00020\u00142\u0006\u0010+\u001a\u00020,H\u0016J\u0012\u0010-\u001a\u00020\u00142\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\u0010\u0010.\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\bH\u0017J\b\u00100\u001a\u00020\u0014H\u0014J\u0010\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u000203H\u0016J\b\u00104\u001a\u00020\u0014H\u0014J+\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u00020:092\u0006\u0010;\u001a\u00020<H\u0016¢\u0006\u0002\u0010=J\b\u0010>\u001a\u00020\u0014H\u0014R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lorg/catrobat/catroid/ui/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/catrobat/catroid/ui/runtimepermissions/PermissionHandlingActivity;", "()V", "isRecoveringFromCrash", "", "()Z", "optionsMenu", "Landroid/view/Menu;", "getOptionsMenu", "()Landroid/view/Menu;", "setOptionsMenu", "(Landroid/view/Menu;)V", "permissionRequestActivityExtension", "Lorg/catrobat/catroid/ui/runtimepermissions/PermissionRequestActivityExtension;", "savedInstanceStateExpected", "touchDownX", "", "touchDownY", "addToRequiresPermissionTaskList", "", "task", "Lorg/catrobat/catroid/ui/runtimepermissions/RequiresPermissionTask;", "applyAccessibilityStyles", "attachBaseContext", "newBase", "Landroid/content/Context;", "checkActiveProcess", "processInfo", "Landroid/app/ActivityManager$RunningAppProcessInfo;", "context", "checkIfCrashRecoveryAndFinishActivity", "activity", "Landroid/app/Activity;", "checkIfProcessRecreatedAndFinishActivity", "savedInstanceState", "Landroid/os/Bundle;", "dispatchTouchEvent", "ev", "Landroid/view/MotionEvent;", "googleAnalyticsTrackScreenResume", "isApplicationSentToBackground", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "onCreateOptionsMenu", LineReader.MENU, "onDestroy", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPause", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseActivity extends AppCompatActivity implements PermissionHandlingActivity {
    public Menu optionsMenu;
    private final PermissionRequestActivityExtension permissionRequestActivityExtension = new PermissionRequestActivityExtension();
    private boolean savedInstanceStateExpected;
    private float touchDownX;
    private float touchDownY;

    public final Menu getOptionsMenu() {
        Menu menu = this.optionsMenu;
        if (menu != null) {
            return menu;
        }
        Intrinsics.throwUninitializedPropertyAccessException("optionsMenu");
        return null;
    }

    public final void setOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "<set-?>");
        this.optionsMenu = menu;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity baseActivity = this;
        SettingsFragment.setToChosenLanguage(baseActivity);
        applyAccessibilityStyles();
        checkIfCrashRecoveryAndFinishActivity(baseActivity);
        checkIfProcessRecreatedAndFinishActivity(savedInstanceState);
        if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
            CastManager.getInstance().initializeCast(this);
        }
        MyActivityManager.INSTANCE.setBase_activity(baseActivity);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context newBase) {
        Intrinsics.checkNotNullParameter(newBase, "newBase");
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String str = BuildConfig.VERSION_NAME;
        String string = defaultSharedPreferences.getString("accessibility_brick_scale", BuildConfig.VERSION_NAME);
        if (string != null) {
            str = string;
        }
        Float floatOrNull = StringsKt.toFloatOrNull(str);
        float fFloatValue = floatOrNull != null ? floatOrNull.floatValue() : 1.0f;
        Configuration configuration = new Configuration(newBase.getResources().getConfiguration());
        configuration.fontScale = fFloatValue;
        super.attachBaseContext(newBase.createConfigurationContext(configuration));
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        boolean z = PreferenceManager.getDefaultSharedPreferences(CatroidApplication.getAppContext()).getBoolean("pref_1_april_enabled", false);
        if (LimboMinigameManager.INSTANCE.isPlaying() || SansMinigameManager.INSTANCE.isPlaying() || OtherPranksManager.INSTANCE.isPrankRunning()) {
            return super.dispatchTouchEvent(ev);
        }
        if (z) {
            if (ev.getAction() == 0) {
                this.touchDownX = ev.getRawX();
                this.touchDownY = ev.getRawY();
            } else if (ev.getAction() == 1 && Math.abs(ev.getRawX() - this.touchDownX) < 50.0f && Math.abs(ev.getRawY() - this.touchDownY) < 50.0f) {
                LimboMinigameManager limboMinigameManager = LimboMinigameManager.INSTANCE;
                View decorView = getWindow().getDecorView();
                Intrinsics.checkNotNullExpressionValue(decorView, "getDecorView(...)");
                View viewFindClickableView = limboMinigameManager.findClickableView(decorView, (int) ev.getRawX(), (int) ev.getRawY());
                if (viewFindClickableView != null && Math.random() < 0.1d) {
                    double dRandom = Math.random();
                    if (dRandom < 0.2d) {
                        LimboMinigameManager.INSTANCE.startLimbo(this, viewFindClickableView);
                        return true;
                    }
                    if (dRandom < 0.4d) {
                        SansMinigameManager.INSTANCE.startSans(this, viewFindClickableView);
                        return true;
                    }
                    if (dRandom < 0.6d) {
                        OtherPranksManager.INSTANCE.runAway(this, viewFindClickableView);
                        return true;
                    }
                    if (dRandom < 0.8d) {
                        OtherPranksManager.INSTANCE.gravityDrop(this, viewFindClickableView);
                        return true;
                    }
                    OtherPranksManager.INSTANCE.doBarrelRoll(this, viewFindClickableView);
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        setOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private final void checkIfProcessRecreatedAndFinishActivity(Bundle savedInstanceState) {
        if (this.savedInstanceStateExpected || savedInstanceState == null || (this instanceof MainMenuActivity)) {
            this.savedInstanceStateExpected = true;
            return;
        }
        String simpleName = getClass().getSimpleName();
        Log.e(simpleName, simpleName + " does not support recovery from process recreation, finishing activity.");
        finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        Log.d("BaseActivity", "Configuration changed! New orientation: " + newConfig.orientation);
    }

    private final void applyAccessibilityStyles() {
        AccessibilityProfile.fromCurrentPreferences(PreferenceManager.getDefaultSharedPreferences(this)).applyAccessibilityStyles(getTheme());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        SettingsFragment.setToChosenLanguage(this);
        BaseActivity baseActivity = this;
        if (SettingsFragment.isCastSharedPreferenceEnabled(baseActivity)) {
            CastManager.getInstance().initializeCast(this);
        }
        invalidateOptionsMenu();
        googleAnalyticsTrackScreenResume();
        Survey survey = MainMenuActivity.surveyCampaign;
        if (survey != null) {
            survey.startAppTime(baseActivity);
        }
    }

    protected final void googleAnalyticsTrackScreenResume() {
        Application application = getApplication();
        Intrinsics.checkNotNull(application, "null cannot be cast to non-null type org.catrobat.catroid.CatroidApplication");
        Tracker defaultTracker = ((CatroidApplication) application).getDefaultTracker();
        defaultTracker.setScreenName(getClass().getName());
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final void checkIfCrashRecoveryAndFinishActivity(Activity activity) {
        if (isRecoveringFromCrash()) {
            if (activity instanceof MainMenuActivity) {
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(BaseActivityKt.RECOVERED_FROM_CRASH, false).apply();
            } else {
                activity.finish();
            }
        }
    }

    private final boolean isRecoveringFromCrash() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(BaseActivityKt.RECOVERED_FROM_CRASH, false);
    }

    @Override // org.catrobat.catroid.ui.runtimepermissions.PermissionHandlingActivity
    public void addToRequiresPermissionTaskList(RequiresPermissionTask task) {
        Intrinsics.checkNotNullParameter(task, "task");
        this.permissionRequestActivityExtension.addToRequiresPermissionTaskList(task);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        try {
            PythonEngine pythonEngine = MainMenuActivity.pythonEngine;
            if (pythonEngine != null) {
                pythonEngine.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.permissionRequestActivityExtension.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        Survey survey;
        super.onPause();
        Object systemService = getSystemService("power");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.PowerManager");
        PowerManager powerManager = (PowerManager) systemService;
        BaseActivity baseActivity = this;
        if ((isApplicationSentToBackground(baseActivity) || !powerManager.isInteractive()) && (survey = MainMenuActivity.surveyCampaign) != null) {
            survey.endAppTime(baseActivity);
        }
    }

    private final boolean isApplicationSentToBackground(Context context) {
        Object systemService = context.getSystemService("activity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
        ActivityManager activityManager = (ActivityManager) systemService;
        if (Build.VERSION.SDK_INT >= 29) {
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks == null || runningTasks.isEmpty()) {
                return true;
            }
            ComponentName componentName = runningTasks.get(0).topActivity;
            if (Intrinsics.areEqual(componentName != null ? componentName.getPackageName() : null, context.getPackageName())) {
                return false;
            }
        } else {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.importance == 100) {
                    Intrinsics.checkNotNull(runningAppProcessInfo);
                    return checkActiveProcess(runningAppProcessInfo, context);
                }
            }
        }
        return true;
    }

    private final boolean checkActiveProcess(ActivityManager.RunningAppProcessInfo processInfo, Context context) {
        String[] pkgList = processInfo.pkgList;
        Intrinsics.checkNotNullExpressionValue(pkgList, "pkgList");
        for (String str : pkgList) {
            if (Intrinsics.areEqual(str, context.getPackageName())) {
                return false;
            }
        }
        return true;
    }
}
