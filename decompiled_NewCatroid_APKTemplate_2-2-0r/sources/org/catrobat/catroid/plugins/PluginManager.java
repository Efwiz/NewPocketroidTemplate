package org.catrobat.catroid.plugins;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.android.gms.cast.MediaTrack;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plugins/PluginManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PluginManager {
    private static final String DISABLED_PLUGINS_KEY = "disabled_plugins_set";
    private static final String PLUGINS_DIR_NAME = "plugins";
    private static final String TAG = "PluginManager";
    private static volatile PluginManager instance;
    private final Context context;
    private final File pluginsDir;

    private PluginManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        File file = new File(applicationContext.getFilesDir(), PLUGINS_DIR_NAME);
        this.pluginsDir = file;
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(context);
                }
            }
        }
        return instance;
    }

    /* JADX WARN: Code duplicated, block: B:85:0x0136 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public boolean installPluginFromUri(Uri uri) {
        File file = new File(this.context.getCacheDir(), "plugin_install_temp");
        if (file.exists()) {
            deleteRecursive(file);
        }
        file.mkdirs();
        try {
            InputStream inputStreamOpenInputStream = this.context.getContentResolver().openInputStream(uri);
            try {
                ZipInputStream zipInputStream = new ZipInputStream(inputStreamOpenInputStream);
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry != null) {
                            File file2 = new File(file, nextEntry.getName());
                            if (!file2.getCanonicalPath().startsWith(file.getCanonicalPath() + File.separator)) {
                                throw new SecurityException("Zip Path Traversal Vulnerability");
                            }
                            if (nextEntry.isDirectory()) {
                                file2.mkdirs();
                            } else {
                                file2.getParentFile().mkdirs();
                                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                try {
                                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                                    try {
                                        byte[] bArr = new byte[1024];
                                        while (true) {
                                            int i = zipInputStream.read(bArr);
                                            if (i <= 0) {
                                                break;
                                            }
                                            bufferedOutputStream.write(bArr, 0, i);
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th) {
                                                th.addSuppressed(th);
                                            }
                                            throw th;
                                        }
                                        bufferedOutputStream.close();
                                        fileOutputStream.close();
                                    } catch (Throwable th2) {
                                        try {
                                            bufferedOutputStream.close();
                                        } catch (Throwable th3) {
                                            th2.addSuppressed(th3);
                                        }
                                        throw th2;
                                    }
                                } catch (Throwable th4) {
                                    fileOutputStream.close();
                                    throw th4;
                                }
                            }
                            zipInputStream.closeEntry();
                        } else {
                            zipInputStream.close();
                            if (inputStreamOpenInputStream != null) {
                                inputStreamOpenInputStream.close();
                            }
                            File file3 = new File(file, "plugin.json");
                            if (!file3.exists()) {
                                Log.e(TAG, "Install failed: plugin.json not found in the archive.");
                                deleteRecursive(file);
                                return false;
                            }
                            try {
                                String string = new JSONObject(readFileToString(file3)).getString("packageName");
                                File file4 = new File(this.pluginsDir, string);
                                if (file4.exists()) {
                                    Log.e(TAG, "Install failed: Plugin with package name '" + string + "' already exists.");
                                    deleteRecursive(file);
                                    return false;
                                }
                                if (!file.renameTo(file4)) {
                                    throw new IOException("Failed to move plugin directory.");
                                }
                                Log.d(TAG, "Plugin installed successfully!");
                                return true;
                            } catch (Exception e) {
                                Log.e(TAG, "Install failed: Invalid manifest or could not move plugin directory.", e);
                                deleteRecursive(file);
                                return false;
                            }
                        }
                    } catch (Throwable th5) {
                        try {
                            zipInputStream.close();
                        } catch (Throwable th6) {
                            th5.addSuppressed(th6);
                        }
                        throw th5;
                    }
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (Throwable th7) {
                            th.addSuppressed(th7);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th8) {
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
                throw th8;
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to unzip plugin archive", e2);
            deleteRecursive(file);
            return false;
        }
    }

    public List<PluginInfo> getInstalledPlugins() {
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = this.pluginsDir.listFiles();
        if (fileArrListFiles == null) {
            return arrayList;
        }
        Set<String> disabledPluginSet = getDisabledPluginSet();
        for (File file : fileArrListFiles) {
            if (file.isDirectory()) {
                File file2 = new File(file, "plugin.json");
                if (file2.exists()) {
                    try {
                        JSONObject jSONObject = new JSONObject(readFileToString(file2));
                        PluginInfo pluginInfo = new PluginInfo(jSONObject.getString("packageName"), jSONObject.getString("name"), jSONObject.getString("version"), jSONObject.getString(MediaTrack.ROLE_DESCRIPTION), file);
                        pluginInfo.isEnabled = !disabledPluginSet.contains(pluginInfo.packageName);
                        arrayList.add(pluginInfo);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to parse manifest for plugin in " + file.getName(), e);
                    }
                }
            }
        }
        return arrayList;
    }

    public void setPluginEnabled(String packageName, boolean enabled) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        HashSet hashSet = new HashSet(getDisabledPluginSet());
        if (enabled) {
            hashSet.remove(packageName);
        } else {
            hashSet.add(packageName);
        }
        defaultSharedPreferences.edit().putStringSet(DISABLED_PLUGINS_KEY, hashSet).apply();
    }

    public boolean deletePlugin(PluginInfo pluginInfo) {
        setPluginEnabled(pluginInfo.packageName, true);
        return deleteRecursive(pluginInfo.pluginDirectory);
    }

    private Set<String> getDisabledPluginSet() {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getStringSet(DISABLED_PLUGINS_KEY, new HashSet());
    }

    private String readFileToString(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[(int) file.length()];
        fileInputStream.read(bArr);
        fileInputStream.close();
        return new String(bArr, StandardCharsets.UTF_8);
    }

    private boolean deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File file : fileOrDirectory.listFiles()) {
                deleteRecursive(file);
            }
        }
        return fileOrDirectory.delete();
    }

    public PluginInfo getPluginByPackageName(String packageName) {
        for (PluginInfo pluginInfo : getInstalledPlugins()) {
            if (pluginInfo.packageName.equals(packageName)) {
                return pluginInfo;
            }
        }
        return null;
    }
}
