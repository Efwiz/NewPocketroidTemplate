package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.content.Context;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.util.AtomicFile;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.Dependency;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzox {
    public static final Component<?> zza = Component.builder(zzox.class).add(Dependency.required((Class<?>) Context.class)).factory(new ComponentFactory() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzow
        @Override // com.google.firebase.components.ComponentFactory
        public final Object create(ComponentContainer componentContainer) {
            return new zzox((Context) componentContainer.get(Context.class));
        }
    }).build();
    private static final Object zzb = new Object();
    private final Context zzc;

    public zzox(Context context) {
        this.zzc = context;
    }

    private final File zzc() {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(this.zzc);
        if (noBackupFilesDir == null || !noBackupFilesDir.isDirectory()) {
            Log.w("MLKitRemoteConfigSaver", "noBackupFilesDir doesn't exist, using regular files directory instead");
            noBackupFilesDir = this.zzc.getFilesDir();
            if (noBackupFilesDir != null && !noBackupFilesDir.isDirectory()) {
                try {
                    if (!noBackupFilesDir.mkdirs()) {
                        String strValueOf = String.valueOf(noBackupFilesDir);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 15);
                        sb.append("mkdirs failed: ");
                        sb.append(strValueOf);
                        Log.w("MLKitRemoteConfigSaver", sb.toString());
                    }
                } catch (SecurityException e) {
                    String strValueOf2 = String.valueOf(noBackupFilesDir);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 27);
                    sb2.append("mkdirs threw an exception: ");
                    sb2.append(strValueOf2);
                    Log.w("MLKitRemoteConfigSaver", sb2.toString(), e);
                }
            }
        }
        return new File(noBackupFilesDir, "com.google.mlkit.RemoteConfig");
    }

    public final zzok zza(zzng zzngVar) {
        zzok zzokVar;
        synchronized (zzb) {
            File fileZzc = zzc();
            zzokVar = null;
            try {
                String str = new String(new AtomicFile(fileZzc).readFully(), Charset.forName("UTF-8"));
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    try {
                        zzokVar = new zzok(jSONObject.getJSONObject("configs_key"), new Date(jSONObject.getLong("fetch_time_key")), jSONObject.getJSONArray("abt_experiments_key"));
                    } catch (JSONException e) {
                        zzngVar.zzc(zzmq.FILE_READ_RETURNED_INVALID_DATA);
                        String strValueOf = String.valueOf(jSONObject);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 50);
                        sb.append("Error parsing remote config settings JSON object:\n");
                        sb.append(strValueOf);
                        Log.e("MLKitRemoteConfigSaver", sb.toString(), e);
                    }
                } catch (JSONException e2) {
                    zzngVar.zzc(zzmq.FILE_READ_RETURNED_MALFORMED_DATA);
                    Log.e("MLKitRemoteConfigSaver", str.length() != 0 ? "Error parsing remote config settings JSON string:\n".concat(str) : new String("Error parsing remote config settings JSON string:\n"), e2);
                }
            } catch (IOException e3) {
                if (!fileZzc.exists()) {
                    String strValueOf2 = String.valueOf(fileZzc);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 45);
                    sb2.append("remote config settings file not yet present: ");
                    sb2.append(strValueOf2);
                    Log.i("MLKitRemoteConfigSaver", sb2.toString());
                    return null;
                }
                zzngVar.zzc(zzmq.FILE_READ_FAILED);
                String strValueOf3 = String.valueOf(fileZzc);
                StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 43);
                sb3.append("Error reading remote config settings file: ");
                sb3.append(strValueOf3);
                Log.w("MLKitRemoteConfigSaver", sb3.toString(), e3);
                return null;
            }
        }
        return zzokVar;
    }

    public final void zzb(zzok zzokVar, zzng zzngVar) {
        File fileZzc;
        String string = zzokVar.toString();
        synchronized (zzb) {
            try {
                fileZzc = zzc();
                try {
                    String strValueOf = String.valueOf(fileZzc);
                    StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 33);
                    sb.append("Creating remote config settings: ");
                    sb.append(strValueOf);
                    Log.i("MLKitRemoteConfigSaver", sb.toString());
                    AtomicFile atomicFile = new AtomicFile(fileZzc);
                    FileOutputStream fileOutputStreamStartWrite = atomicFile.startWrite();
                    try {
                        PrintWriter printWriter = new PrintWriter(fileOutputStreamStartWrite);
                        printWriter.println(string);
                        printWriter.flush();
                        atomicFile.finishWrite(fileOutputStreamStartWrite);
                        String strValueOf2 = String.valueOf(fileZzc);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 44 + String.valueOf(string).length());
                        sb2.append("Succeeded writing remote config settings: ");
                        sb2.append(strValueOf2);
                        sb2.append(":\n");
                        sb2.append(string);
                        Log.d("MLKitRemoteConfigSaver", sb2.toString());
                    } catch (Throwable th) {
                        atomicFile.failWrite(fileOutputStreamStartWrite);
                        throw th;
                    }
                } catch (IOException e) {
                    e = e;
                    zzngVar.zzc(zzmq.FILE_WRITE_FAILED);
                    String strValueOf3 = String.valueOf(fileZzc);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 45);
                    sb3.append("Error writing to remote config settings file ");
                    sb3.append(strValueOf3);
                    Log.e("MLKitRemoteConfigSaver", sb3.toString(), e);
                }
            } catch (IOException e2) {
                e = e2;
                fileZzc = null;
            }
        }
    }
}
