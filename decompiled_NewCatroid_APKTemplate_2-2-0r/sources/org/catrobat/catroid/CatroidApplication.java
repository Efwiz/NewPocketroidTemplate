package org.catrobat.catroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.mlsdk.common.AgConnectInfo;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import org.catrobat.catroid.formulaeditor.CustomFormulaManager;
import org.catrobat.catroid.koin.CatroidKoinHelperKt;
import org.catrobat.catroid.plugins.PluginEventBus;
import org.catrobat.catroid.plugins.PluginExecutor;
import org.catrobat.catroid.plugins.PluginOverlayManager;
import org.catrobat.catroid.utils.ThemeEngine;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/CatroidApplication.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class CatroidApplication extends Application {
    public static boolean IS_SAFE_MODE = false;
    private static final String TAG = "CatroidApplication";
    private static Context context;
    public static CatroidApplication current;
    public static String defaultSystemLanguage;
    public static PluginCompositeClassLoader globalPluginClassLoader;
    private static GoogleAnalytics googleAnalytics;
    private static Tracker googleTracker;
    private boolean pluginsLoaded = false;

    @Override // android.app.Application
    public void onCreate() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (defaultSharedPreferences.getBoolean("force_safe_mode", false)) {
            IS_SAFE_MODE = true;
            defaultSharedPreferences.edit().remove("force_safe_mode").apply();
        }
        super.onCreate();
        try {
            globalPluginClassLoader = new PluginCompositeClassLoader(getClassLoader());
            Context baseContext = getBaseContext();
            Field declaredField = Class.forName("android.app.ContextImpl").getDeclaredField("mClassLoader");
            declaredField.setAccessible(true);
            declaredField.set(baseContext, globalPluginClassLoader);
            Log.d(TAG, "ClassLoader: Глобальный PluginCompositeClassLoader успешно внедрен в ContextImpl.");
        } catch (Exception e) {
            Log.e(TAG, "ClassLoader: Ошибка внедрения глобального ClassLoader", e);
        }
        String str = TAG;
        Log.d(str, "CatroidApplication onCreate");
        Log.d(str, "git commit info: branch main @ca054031");
        context = getApplicationContext();
        CatroidKoinHelperKt.start(this, CatroidKoinHelperKt.getMyModules());
        Utils.fetchSpeechRecognitionSupportedLanguages(this);
        defaultSystemLanguage = Locale.getDefault().toLanguageTag();
        GoogleAnalytics googleAnalytics2 = GoogleAnalytics.getInstance(this);
        googleAnalytics = googleAnalytics2;
        googleAnalytics2.setDryRun(false);
        setupHuaweiMobileServices();
        CustomFormulaManager.INSTANCE.initialize();
        PluginExecutor.getInstance(this).prepareEnvironment();
        registerActivityLifecycleCallbacks(new OverlayLifecycleCallbacks());
        current = this;
    }

    public void loadPluginsIfNotLoaded() {
        if (this.pluginsLoaded) {
            return;
        }
        this.pluginsLoaded = true;
        if (IS_SAFE_MODE) {
            Log.w(TAG, "SAFE MODE is active. Skipping plugin loading.");
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.CatroidApplication$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4806x8007aa49();
                }
            });
        } else {
            Log.d(TAG, "Loading all enabled plugins now.");
            PluginExecutor.getInstance(this).loadAndRunAllEnabledPlugins();
        }
    }

    /* JADX INFO: renamed from: lambda$loadPluginsIfNotLoaded$0$org-catrobat-catroid-CatroidApplication, reason: not valid java name */
    /* synthetic */ void m4806x8007aa49() {
        Toast.makeText(this, "Safe Mode. Plugins disabled", 1).show();
    }

    private void setupHuaweiMobileServices() {
        if (AGConnectInstance.getInstance() == null) {
            AGConnectInstance.initialize(this);
        }
        MLApplication.getInstance().setApiKey(AGConnectServicesConfig.fromContext(this).getString(AgConnectInfo.AgConnectKey.API_KEY));
    }

    public synchronized Tracker getDefaultTracker() {
        if (googleTracker == null) {
            googleTracker = googleAnalytics.newTracker(org.DanVexTeam.NewCatroidApkTemplate.R.xml.global_tracker);
        }
        return googleTracker;
    }

    public static Context getAppContext() {
        return context;
    }

    private void resetThemeSettings() {
        Log.d("ThemeReset", "Сброс настроек темы...");
        SharedPreferences.Editor editorEdit = getSharedPreferences(ThemeEngine.THEME_PREFS_NAME, 0).edit();
        editorEdit.remove("theme_toolbar_background");
        editorEdit.remove("theme_primary_background");
        editorEdit.apply();
        Log.d("ThemeReset", "Настройки темы успешно сброшены.");
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/CatroidApplication$PluginCompositeClassLoader.class
      classes.dex
     */
    public static class PluginCompositeClassLoader extends ClassLoader {
        private final ThreadLocal<Set<String>> loadingClasses;
        private final List<ClassLoader> pluginLoaders;

        public static /* synthetic */ HashSet $r8$lambda$9sNe9Z0ULfWboQeEjPzVPZGTMDM() {
            return new HashSet();
        }

        public PluginCompositeClassLoader(ClassLoader parent) {
            super(parent);
            this.pluginLoaders = new CopyOnWriteArrayList();
            this.loadingClasses = ThreadLocal.withInitial(new Supplier() { // from class: org.catrobat.catroid.CatroidApplication$PluginCompositeClassLoader$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return CatroidApplication.PluginCompositeClassLoader.$r8$lambda$9sNe9Z0ULfWboQeEjPzVPZGTMDM();
                }
            });
        }

        public void addPluginLoader(ClassLoader cl) {
            if (cl == null || this.pluginLoaders.contains(cl)) {
                return;
            }
            this.pluginLoaders.add(cl);
        }

        @Override // java.lang.ClassLoader
        protected Class<?> findClass(String name2) throws ClassNotFoundException {
            Set<String> set = this.loadingClasses.get();
            if (set.contains(name2)) {
                throw new ClassNotFoundException(name2);
            }
            set.add(name2);
            try {
                Iterator<ClassLoader> it = this.pluginLoaders.iterator();
                while (it.hasNext()) {
                    try {
                        return it.next().loadClass(name2);
                    } catch (ClassNotFoundException unused) {
                    }
                }
                throw new ClassNotFoundException(name2);
            } finally {
                set.remove(name2);
            }
        }

        @Override // java.lang.ClassLoader
        public Class<?> loadClass(String name2, boolean resolve) throws ClassNotFoundException {
            try {
                return getParent().loadClass(name2);
            } catch (ClassNotFoundException unused) {
                return findClass(name2);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/CatroidApplication$OverlayLifecycleCallbacks.class
      classes.dex
     */
    private static class OverlayLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        private OverlayLifecycleCallbacks() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            PluginOverlayManager.getInstance().attach(activity);
            PluginEventBus.getInstance().dispatch("Activity.onShow", activity.getClass().getSimpleName());
            if (CatroidApplication.IS_SAFE_MODE) {
                return;
            }
            ThemeEngine.applyTheme(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            PluginEventBus.getInstance().dispatch("Activity.onHide", activity.getClass().getSimpleName());
            PluginOverlayManager.getInstance().detach(activity);
        }
    }
}
