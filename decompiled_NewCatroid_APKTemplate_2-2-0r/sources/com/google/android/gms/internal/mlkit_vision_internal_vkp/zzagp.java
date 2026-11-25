package com.google.android.gms.internal.mlkit_vision_internal_vkp;

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

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzagp {
    public static final Component<?> zza = Component.builder(zzagp.class).add(Dependency.required((Class<?>) Context.class)).factory(new ComponentFactory() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzago
        @Override // com.google.firebase.components.ComponentFactory
        public final Object create(ComponentContainer componentContainer) {
            return new zzagp((Context) componentContainer.get(Context.class));
        }
    }).build();
    private static final Object zzb = new Object();
    private final Context zzc;

    public zzagp(Context context) {
        this.zzc = context;
    }

    public final zzagq zza(zzagm zzagmVar) {
        zzagq zzagqVar;
        synchronized (zzb) {
            File fileZzb = zzb(zzagmVar);
            zzagqVar = null;
            try {
                String str = new String(new AtomicFile(fileZzb).readFully(), Charset.forName("UTF-8"));
                try {
                    zzva zzvaVarZzb = zzvf.zzb(str);
                    if (zzvaVarZzb instanceof zzvd) {
                        zzvd zzvdVarZzb = zzvaVarZzb.zzb();
                        try {
                            zzagg zzaggVar = new zzagg(zzvdVarZzb.zzc("fid").zzd());
                            String strZzd = zzvdVarZzb.zzc("refreshToken").zzd();
                            String strZzd2 = zzvdVarZzb.zzc("temporaryToken").zzd();
                            long jZza = zzvdVarZzb.zzc("temporaryTokenExpiryTimestamp").zza();
                            String strValueOf = String.valueOf(zzaggVar);
                            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 5);
                            sb.append("fid: ");
                            sb.append(strValueOf);
                            Log.d("MLKitInstallationIdSaver", sb.toString());
                            String strValueOf2 = String.valueOf(strZzd);
                            Log.d("MLKitInstallationIdSaver", strValueOf2.length() != 0 ? "refresh_token: ".concat(strValueOf2) : new String("refresh_token: "));
                            String strValueOf3 = String.valueOf(strZzd2);
                            Log.d("MLKitInstallationIdSaver", strValueOf3.length() != 0 ? "temporary_token: ".concat(strValueOf3) : new String("temporary_token: "));
                            StringBuilder sb2 = new StringBuilder(44);
                            sb2.append("temporary token expiry: ");
                            sb2.append(jZza);
                            Log.d("MLKitInstallationIdSaver", sb2.toString());
                            zzagqVar = new zzagq(zzaggVar, strZzd, strZzd2, jZza);
                        } catch (ClassCastException | IllegalStateException | NullPointerException e) {
                            zzagmVar.zzc(zzafd.FILE_READ_RETURNED_INVALID_DATA);
                            String strValueOf4 = String.valueOf(zzvdVarZzb);
                            StringBuilder sb3 = new StringBuilder(str.length() + 72 + String.valueOf(strValueOf4).length());
                            sb3.append("Error traversing installation info JSON object:\nraw json:\n");
                            sb3.append(str);
                            sb3.append("\nparsed json:\n");
                            sb3.append(strValueOf4);
                            Log.e("MLKitInstallationIdSaver", sb3.toString(), e);
                        }
                    } else {
                        String strValueOf5 = String.valueOf(zzvaVarZzb);
                        StringBuilder sb4 = new StringBuilder(String.valueOf(strValueOf5).length() + 46);
                        sb4.append("Error parsing installation info JSON element:\n");
                        sb4.append(strValueOf5);
                        Log.e("MLKitInstallationIdSaver", sb4.toString());
                        zzagmVar.zzc(zzafd.FILE_READ_RETURNED_MALFORMED_DATA);
                    }
                } catch (zzvh e2) {
                    Log.e("MLKitInstallationIdSaver", str.length() != 0 ? "Error parsing installation info JSON object:\n".concat(str) : new String("Error parsing installation info JSON object:\n"), e2);
                    zzagmVar.zzc(zzafd.FILE_READ_RETURNED_MALFORMED_DATA);
                }
            } catch (IOException e3) {
                if (!fileZzb.exists()) {
                    String strValueOf6 = String.valueOf(fileZzb);
                    StringBuilder sb5 = new StringBuilder(String.valueOf(strValueOf6).length() + 38);
                    sb5.append("Installation id file not yet present: ");
                    sb5.append(strValueOf6);
                    Log.i("MLKitInstallationIdSaver", sb5.toString());
                    return null;
                }
                zzagmVar.zzc(zzafd.FILE_READ_FAILED);
                String strValueOf7 = String.valueOf(fileZzb);
                StringBuilder sb6 = new StringBuilder(String.valueOf(strValueOf7).length() + 36);
                sb6.append("Error reading installation id file: ");
                sb6.append(strValueOf7);
                Log.w("MLKitInstallationIdSaver", sb6.toString(), e3);
                return null;
            }
        }
        return zzagqVar;
    }

    final File zzb(zzagm zzagmVar) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(this.zzc);
        if (noBackupFilesDir == null || !noBackupFilesDir.isDirectory()) {
            Log.w("MLKitInstallationIdSaver", "noBackupFilesDir doesn't exist, using regular files directory instead");
            noBackupFilesDir = this.zzc.getFilesDir();
            if (noBackupFilesDir != null && !noBackupFilesDir.isDirectory()) {
                try {
                    if (!noBackupFilesDir.mkdirs()) {
                        String strValueOf = String.valueOf(noBackupFilesDir);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 15);
                        sb.append("mkdirs failed: ");
                        sb.append(strValueOf);
                        Log.w("MLKitInstallationIdSaver", sb.toString());
                        zzagmVar.zzd(zzafd.DIRECTORY_CREATION_FAILED);
                    }
                } catch (SecurityException e) {
                    String strValueOf2 = String.valueOf(noBackupFilesDir);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 27);
                    sb2.append("mkdirs threw an exception: ");
                    sb2.append(strValueOf2);
                    Log.w("MLKitInstallationIdSaver", sb2.toString(), e);
                    zzagmVar.zzd(zzafd.DIRECTORY_CREATION_FAILED);
                }
            }
        }
        return new File(noBackupFilesDir, "com.google.mlkit.InstallationId");
    }

    public final void zzc(zzagq zzagqVar, zzagm zzagmVar) {
        File fileZzb;
        String str = String.format("{\n \"fid\": \"%s\",\n \"refreshToken\": \"%s\",\n \"temporaryToken\": \"%s\",\n \"temporaryTokenExpiryTimestamp\": \"%d\"\n}\n", zzagqVar.zzb().zza(), zzagqVar.zzc(), zzagqVar.zzd(), Long.valueOf(zzagqVar.zza()));
        synchronized (zzb) {
            try {
                fileZzb = zzb(zzagmVar);
                try {
                    String strValueOf = String.valueOf(fileZzb);
                    StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 26);
                    sb.append("Creating installation id: ");
                    sb.append(strValueOf);
                    Log.i("MLKitInstallationIdSaver", sb.toString());
                    AtomicFile atomicFile = new AtomicFile(fileZzb);
                    FileOutputStream fileOutputStreamStartWrite = atomicFile.startWrite();
                    try {
                        PrintWriter printWriter = new PrintWriter(fileOutputStreamStartWrite);
                        printWriter.println(str);
                        printWriter.flush();
                        atomicFile.finishWrite(fileOutputStreamStartWrite);
                        String strValueOf2 = String.valueOf(fileZzb);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 37 + String.valueOf(str).length());
                        sb2.append("Succeeded writing installation id: ");
                        sb2.append(strValueOf2);
                        sb2.append(":\n");
                        sb2.append(str);
                        Log.d("MLKitInstallationIdSaver", sb2.toString());
                    } catch (Throwable th) {
                        atomicFile.failWrite(fileOutputStreamStartWrite);
                        throw th;
                    }
                } catch (IOException e) {
                    e = e;
                    zzagmVar.zzc(zzafd.FILE_WRITE_FAILED);
                    String strValueOf3 = String.valueOf(fileZzb);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 38);
                    sb3.append("Error writing to installation id file ");
                    sb3.append(strValueOf3);
                    Log.e("MLKitInstallationIdSaver", sb3.toString(), e);
                }
            } catch (IOException e2) {
                e = e2;
                fileZzb = null;
            }
        }
    }
}
