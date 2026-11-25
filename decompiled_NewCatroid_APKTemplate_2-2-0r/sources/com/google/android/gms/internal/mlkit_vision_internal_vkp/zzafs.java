package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.util.AtomicFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzafs {
    private static final Object zza = new Object();
    private final Context zzb;
    private final zzafr zzc;

    public zzafs(Context context) {
        zzafr zzafrVar = new zzafr(null);
        this.zzb = context;
        this.zzc = zzafrVar;
    }

    public final zzarq zza(String str, String str2, String str3, int i, zzagb zzagbVar, long j) {
        File fileZzb;
        synchronized (zza) {
            try {
                try {
                    fileZzb = zzb(str, "com.google.perception", 2);
                    try {
                        byte[] fully = new AtomicFile(fileZzb).readFully();
                        if (fully == null) {
                            return null;
                        }
                        try {
                            zzafy zzafyVarZzd = zzafy.zzd(fully, zzanc.zza());
                            if (!zzafyVarZzd.zzk()) {
                                Log.w("AccelerationAllowlistPersistence", "No persistence time in cached entry, discarding it");
                            } else if (System.currentTimeMillis() / 1000 <= zzafyVarZzd.zze().zzc() + j) {
                                if (zzafyVarZzd.zzg().equals(str2)) {
                                    return zzafyVarZzd.zzf();
                                }
                                Log.i("AccelerationAllowlistPersistence", "Different client library version, discarding cached content");
                                return null;
                            }
                            Log.i("AccelerationAllowlistPersistence", "Cache expired");
                            return null;
                        } catch (zzaoe e) {
                            String strValueOf = String.valueOf(fileZzb);
                            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 29);
                            sb.append("Invalid cached data in file: ");
                            sb.append(strValueOf);
                            Log.e("AccelerationAllowlistPersistence", sb.toString(), e);
                            zzagbVar.zzc(zzyf.FILE_READ_RETURNED_MALFORMED_DATA);
                            return null;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        if (fileZzb == null) {
                            String strValueOf2 = String.valueOf((Object) null);
                            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 52);
                            sb2.append("Unable to access acceleration allowlist cache file: ");
                            sb2.append(strValueOf2);
                            Log.w("AccelerationAllowlistPersistence", sb2.toString(), e);
                            zzagbVar.zzc(zzyf.FILE_READ_FAILED);
                        } else if (fileZzb.exists()) {
                            String strValueOf3 = String.valueOf(fileZzb);
                            StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 49);
                            sb3.append("Error reading acceleration allowlist cache file: ");
                            sb3.append(strValueOf3);
                            Log.w("AccelerationAllowlistPersistence", sb3.toString(), e);
                            zzagbVar.zzc(zzyf.FILE_READ_FAILED);
                        } else {
                            String strValueOf4 = String.valueOf(fileZzb);
                            StringBuilder sb4 = new StringBuilder(String.valueOf(strValueOf4).length() + 41);
                            sb4.append("No acceleration allowlist cache file at: ");
                            sb4.append(strValueOf4);
                            Log.i("AccelerationAllowlistPersistence", sb4.toString());
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                fileZzb = null;
            }
        }
    }

    final File zzb(String str, String str2, int i) throws IOException {
        String str3 = String.format("com.google.mlkit.AccelerationAllowList.%s.%s.%s", str, "com.google.perception", "NNAPI");
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(this.zzb);
        if (noBackupFilesDir == null || !noBackupFilesDir.isDirectory()) {
            Log.w("AccelerationAllowlistPersistence", "noBackupFilesDir doesn't exist, using regular files directory instead");
            noBackupFilesDir = this.zzb.getFilesDir();
            if (!noBackupFilesDir.isDirectory()) {
                try {
                    if (!noBackupFilesDir.mkdirs()) {
                        String strValueOf = String.valueOf(noBackupFilesDir);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 15);
                        sb.append("mkdirs failed: ");
                        sb.append(strValueOf);
                        Log.w("AccelerationAllowlistPersistence", sb.toString());
                        String strValueOf2 = String.valueOf(noBackupFilesDir);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 33);
                        sb2.append("Unable to create persistence dir ");
                        sb2.append(strValueOf2);
                        throw new IOException(sb2.toString());
                    }
                } catch (SecurityException e) {
                    String strValueOf3 = String.valueOf(noBackupFilesDir);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 27);
                    sb3.append("mkdirs threw an exception: ");
                    sb3.append(strValueOf3);
                    Log.w("AccelerationAllowlistPersistence", sb3.toString(), e);
                    String strValueOf4 = String.valueOf(noBackupFilesDir);
                    StringBuilder sb4 = new StringBuilder(String.valueOf(strValueOf4).length() + 33);
                    sb4.append("Unable to create persistence dir ");
                    sb4.append(strValueOf4);
                    throw new IOException(sb4.toString(), e);
                }
            }
        }
        return new File(noBackupFilesDir, str3);
    }

    public final void zzc(zzarq zzarqVar, String str, String str2, String str3, int i, zzagb zzagbVar) {
        File fileZzb;
        zzafx zzafxVarZza = zzafy.zza();
        zzafxVarZza.zza(zzarqVar);
        zzaqq zzaqqVarZze = zzaqr.zze();
        zzaqqVarZze.zza(System.currentTimeMillis() / 1000);
        zzafxVarZza.zzc(zzaqqVarZze.zzt());
        zzafxVarZza.zzb(str2);
        zzafy zzafyVarZzt = zzafxVarZza.zzt();
        synchronized (zza) {
            try {
                fileZzb = zzb(str, "com.google.perception", 2);
                try {
                    String strValueOf = String.valueOf(fileZzb);
                    StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 33);
                    sb.append("Saving nnapi allowlist cache to: ");
                    sb.append(strValueOf);
                    Log.i("AccelerationAllowlistPersistence", sb.toString());
                    AtomicFile atomicFile = new AtomicFile(fileZzb);
                    FileOutputStream fileOutputStreamStartWrite = atomicFile.startWrite();
                    try {
                        zzafyVarZzt.zzP(fileOutputStreamStartWrite);
                        atomicFile.finishWrite(fileOutputStreamStartWrite);
                        String strValueOf2 = String.valueOf(fileZzb);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 36);
                        sb2.append("Succeeded storing allowlist to file ");
                        sb2.append(strValueOf2);
                        Log.d("AccelerationAllowlistPersistence", sb2.toString());
                    } catch (Throwable th) {
                        atomicFile.failWrite(fileOutputStreamStartWrite);
                        zzagbVar.zzc(zzyf.FILE_WRITE_FAILED);
                        throw th;
                    }
                } catch (IOException e) {
                    e = e;
                    String strValueOf3 = String.valueOf(fileZzb);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 44);
                    sb3.append("Error writing to nnapi allowlist cache file ");
                    sb3.append(strValueOf3);
                    Log.e("AccelerationAllowlistPersistence", sb3.toString(), e);
                    zzagbVar.zzc(zzyf.FILE_WRITE_FAILED);
                }
            } catch (IOException e2) {
                e = e2;
                fileZzb = null;
            }
        }
    }
}
