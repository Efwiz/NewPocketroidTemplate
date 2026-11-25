package org.catrobat.catroid.plugins;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ide.IdeSettings;
import org.catrobat.catroid.ide.SdkManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plugins/PluginExecutor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PluginExecutor {
    private static final String TAG = "PluginExecutor";
    private static volatile PluginExecutor instance;
    private final Context context;
    private final PluginManager pluginManager;
    private final Map<String, PluginEntry> runningPlugins = new HashMap();
    private volatile boolean isEnvPrepared = false;

    private PluginExecutor(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.pluginManager = PluginManager.getInstance(applicationContext);
    }

    public static PluginExecutor getInstance(Context context) {
        if (instance == null) {
            synchronized (PluginExecutor.class) {
                if (instance == null) {
                    instance = new PluginExecutor(context);
                }
            }
        }
        return instance;
    }

    public void prepareEnvironment() {
        new Thread(new Runnable() { // from class: org.catrobat.catroid.plugins.PluginExecutor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5422x6f81faa6();
            }
        }).start();
    }

    /* JADX INFO: renamed from: lambda$prepareEnvironment$1$org-catrobat-catroid-plugins-PluginExecutor, reason: not valid java name */
    /* synthetic */ void m5422x6f81faa6() {
        try {
            String str = TAG;
            Log.d(str, "Checking environment dependencies in background...");
            File file = new File(this.context.getFilesDir(), "core-lambda-stubs.jar");
            if (!file.exists()) {
                extractAsset("core-lambda-stubs.jar", file);
            }
            extractAsset("app-classes.jar", new File(this.context.getFilesDir(), "app-classes.jar"));
            File file2 = new File(this.context.getFilesDir(), "gdx.jar");
            if (!file2.exists()) {
                extractAsset("gdx.jar", file2);
            }
            File file3 = new File(this.context.getFilesDir(), "xstream.jar");
            if (!file3.exists()) {
                extractAsset("xstream.jar", file3);
            }
            File file4 = new File(this.context.getFilesDir(), "koin-core.jar");
            if (!file4.exists()) {
                extractAsset("koin-core.jar", file4);
            }
            File file5 = new File(this.context.getFilesDir(), "fragment.jar");
            if (!file5.exists()) {
                extractAsset("fragment.jar", file5);
            }
            File file6 = new File(this.context.getFilesDir(), "activity.jar");
            if (!file6.exists()) {
                extractAsset("activity.jar", file6);
            }
            File file7 = new File(this.context.getFilesDir(), "core.jar");
            if (!file7.exists()) {
                extractAsset("core.jar", file7);
            }
            if (!IdeSettings.INSTANCE.getAndroidJar(this.context, 34).exists()) {
                Log.w(str, "android.jar for API 34 is missing. Downloading...");
                if (!SdkManager.INSTANCE.downloadPlatform(this.context, 34, new Function2() { // from class: org.catrobat.catroid.plugins.PluginExecutor$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return Unit.INSTANCE;
                    }
                })) {
                    return;
                }
            }
            this.isEnvPrepared = true;
            Log.d(str, "Environment successfully prepared.");
        } catch (Exception e) {
            Log.e(TAG, "Error during environment preparation", e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0067 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private void extractAsset(String assetName, File destFile) throws IOException {
        Log.i(TAG, "Extracting " + assetName + " from assets...");
        InputStream inputStreamOpen = this.context.getAssets().open(assetName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int i = inputStreamOpen.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        fileOutputStream.write(bArr, 0, i);
                    }
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Throwable th) {
                            th.addSuppressed(th);
                        }
                    }
                    throw th;
                }
                Log.i(TAG, assetName + " extracted successfully.");
                fileOutputStream.close();
                if (inputStreamOpen != null) {
                    inputStreamOpen.close();
                }
            } catch (Throwable th2) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                }
                throw th2;
            }
        } catch (Throwable th4) {
            if (inputStreamOpen != null) {
                inputStreamOpen.close();
            }
            throw th4;
        }
    }

    public void loadAndRunAllEnabledPlugins() {
        if (CatroidApplication.IS_SAFE_MODE) {
            Log.w(TAG, "SAFE MODE DETECTED. Skipping all plugins.");
            return;
        }
        Log.d(TAG, "Loading all enabled plugins now.");
        for (PluginInfo pluginInfo : this.pluginManager.getInstalledPlugins()) {
            if (pluginInfo.isEnabled && !this.runningPlugins.containsKey(pluginInfo.packageName)) {
                try {
                    String str = TAG;
                    Log.i(str, "Preparing plugin: " + pluginInfo.f589name);
                    File androidJar = IdeSettings.INSTANCE.getAndroidJar(this.context, 34);
                    if (!androidJar.exists()) {
                        Log.e(str, "android.jar not found at " + androidJar.getAbsolutePath() + ". Compilation aborted. (Is background download still in progress?)");
                    } else if (!PluginCompiler.INSTANCE.compile(this.context, pluginInfo)) {
                        Log.e(str, "Failed to compile plugin: " + pluginInfo.f589name);
                    } else {
                        File file = new File(pluginInfo.pluginDirectory, "plugin.dex");
                        if (!file.exists()) {
                            Log.e(str, "plugin.dex not found for: " + pluginInfo.f589name);
                        } else {
                            Class clsLoadClass = new DexClassLoader(file.getAbsolutePath(), this.context.getCodeCacheDir().getAbsolutePath(), null, this.context.getClassLoader()).loadClass("plugin.Main");
                            if (PluginEntry.class.isAssignableFrom(clsLoadClass)) {
                                PluginEntry pluginEntry = (PluginEntry) clsLoadClass.newInstance();
                                Log.i(str, "Launching plugin: " + pluginInfo.f589name);
                                pluginEntry.onLoad(this.context);
                                this.runningPlugins.put(pluginInfo.packageName, pluginEntry);
                                Log.i(str, "Successfully loaded and running plugin: " + pluginInfo.f589name);
                            } else {
                                Log.e(str, "Main class 'plugin.Main' does not implement PluginEntry in " + pluginInfo.f589name);
                            }
                        }
                    }
                } catch (Throwable th) {
                    Log.e(TAG, "Failed to load/execute plugin: " + pluginInfo.f589name, th);
                }
            }
        }
    }
}
