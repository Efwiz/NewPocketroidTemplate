package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.res.AssetFileDescriptor;
import android.os.Build;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzn {
    public static final /* synthetic */ int zza = 0;
    private static final String[] zzb = {"com.android.", "com.google.", "com.chrome.", "com.nest.", "com.waymo.", "com.waze"};
    private static final String[] zzc;
    private static final String[] zzd;

    static {
        String[] strArr = new String[2];
        strArr[0] = "media";
        strArr[1] = (Build.HARDWARE.equals("goldfish") || Build.HARDWARE.equals("ranchu")) ? "androidx.test.services.storage.runfiles" : "";
        zzc = strArr;
        zzd = new String[]{"", "", "com.google.android.apps.docs.storage.legacy"};
    }

    /* JADX WARN: Code duplicated, block: B:105:0x01ce A[Catch: IOException -> 0x021c, FileNotFoundException -> 0x022b, TryCatch #2 {FileNotFoundException -> 0x022b, IOException -> 0x021c, blocks: (B:62:0x0107, B:65:0x012a, B:67:0x0136, B:69:0x0140, B:70:0x0143, B:68:0x013b, B:71:0x0144, B:73:0x014c, B:75:0x0154, B:115:0x01e8, B:117:0x01f4, B:119:0x01fe, B:120:0x0201, B:118:0x01f9, B:78:0x015e, B:80:0x0166, B:82:0x016f, B:111:0x01e0, B:88:0x018b, B:90:0x0191, B:92:0x0197, B:95:0x01a2, B:97:0x01af, B:99:0x01b3, B:102:0x01be, B:103:0x01c1, B:105:0x01ce, B:107:0x01d2, B:110:0x01dd, B:85:0x017c, B:121:0x0202, B:123:0x020e, B:125:0x0218, B:126:0x021b, B:124:0x0213), top: B:135:0x0107 }] */
    /* JADX WARN: Code duplicated, block: B:107:0x01d2 A[Catch: IOException -> 0x021c, FileNotFoundException -> 0x022b, TryCatch #2 {FileNotFoundException -> 0x022b, IOException -> 0x021c, blocks: (B:62:0x0107, B:65:0x012a, B:67:0x0136, B:69:0x0140, B:70:0x0143, B:68:0x013b, B:71:0x0144, B:73:0x014c, B:75:0x0154, B:115:0x01e8, B:117:0x01f4, B:119:0x01fe, B:120:0x0201, B:118:0x01f9, B:78:0x015e, B:80:0x0166, B:82:0x016f, B:111:0x01e0, B:88:0x018b, B:90:0x0191, B:92:0x0197, B:95:0x01a2, B:97:0x01af, B:99:0x01b3, B:102:0x01be, B:103:0x01c1, B:105:0x01ce, B:107:0x01d2, B:110:0x01dd, B:85:0x017c, B:121:0x0202, B:123:0x020e, B:125:0x0218, B:126:0x021b, B:124:0x0213), top: B:135:0x0107 }] */
    /* JADX WARN: Code duplicated, block: B:113:0x01e6  */
    /* JADX WARN: Code duplicated, block: B:145:0x01c1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x01be A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:151:0x01dd A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:88:0x018b A[Catch: IOException -> 0x021c, FileNotFoundException -> 0x022b, TryCatch #2 {FileNotFoundException -> 0x022b, IOException -> 0x021c, blocks: (B:62:0x0107, B:65:0x012a, B:67:0x0136, B:69:0x0140, B:70:0x0143, B:68:0x013b, B:71:0x0144, B:73:0x014c, B:75:0x0154, B:115:0x01e8, B:117:0x01f4, B:119:0x01fe, B:120:0x0201, B:118:0x01f9, B:78:0x015e, B:80:0x0166, B:82:0x016f, B:111:0x01e0, B:88:0x018b, B:90:0x0191, B:92:0x0197, B:95:0x01a2, B:97:0x01af, B:99:0x01b3, B:102:0x01be, B:103:0x01c1, B:105:0x01ce, B:107:0x01d2, B:110:0x01dd, B:85:0x017c, B:121:0x0202, B:123:0x020e, B:125:0x0218, B:126:0x021b, B:124:0x0213), top: B:135:0x0107 }] */
    /* JADX WARN: Code duplicated, block: B:95:0x01a2 A[Catch: IOException -> 0x021c, FileNotFoundException -> 0x022b, TryCatch #2 {FileNotFoundException -> 0x022b, IOException -> 0x021c, blocks: (B:62:0x0107, B:65:0x012a, B:67:0x0136, B:69:0x0140, B:70:0x0143, B:68:0x013b, B:71:0x0144, B:73:0x014c, B:75:0x0154, B:115:0x01e8, B:117:0x01f4, B:119:0x01fe, B:120:0x0201, B:118:0x01f9, B:78:0x015e, B:80:0x0166, B:82:0x016f, B:111:0x01e0, B:88:0x018b, B:90:0x0191, B:92:0x0197, B:95:0x01a2, B:97:0x01af, B:99:0x01b3, B:102:0x01be, B:103:0x01c1, B:105:0x01ce, B:107:0x01d2, B:110:0x01dd, B:85:0x017c, B:121:0x0202, B:123:0x020e, B:125:0x0218, B:126:0x021b, B:124:0x0213), top: B:135:0x0107 }] */
    /* JADX WARN: Code duplicated, block: B:97:0x01af A[Catch: IOException -> 0x021c, FileNotFoundException -> 0x022b, TryCatch #2 {FileNotFoundException -> 0x022b, IOException -> 0x021c, blocks: (B:62:0x0107, B:65:0x012a, B:67:0x0136, B:69:0x0140, B:70:0x0143, B:68:0x013b, B:71:0x0144, B:73:0x014c, B:75:0x0154, B:115:0x01e8, B:117:0x01f4, B:119:0x01fe, B:120:0x0201, B:118:0x01f9, B:78:0x015e, B:80:0x0166, B:82:0x016f, B:111:0x01e0, B:88:0x018b, B:90:0x0191, B:92:0x0197, B:95:0x01a2, B:97:0x01af, B:99:0x01b3, B:102:0x01be, B:103:0x01c1, B:105:0x01ce, B:107:0x01d2, B:110:0x01dd, B:85:0x017c, B:121:0x0202, B:123:0x020e, B:125:0x0218, B:126:0x021b, B:124:0x0213), top: B:135:0x0107 }] */
    /* JADX WARN: Code duplicated, block: B:99:0x01b3 A[Catch: IOException -> 0x021c, FileNotFoundException -> 0x022b, TryCatch #2 {FileNotFoundException -> 0x022b, IOException -> 0x021c, blocks: (B:62:0x0107, B:65:0x012a, B:67:0x0136, B:69:0x0140, B:70:0x0143, B:68:0x013b, B:71:0x0144, B:73:0x014c, B:75:0x0154, B:115:0x01e8, B:117:0x01f4, B:119:0x01fe, B:120:0x0201, B:118:0x01f9, B:78:0x015e, B:80:0x0166, B:82:0x016f, B:111:0x01e0, B:88:0x018b, B:90:0x0191, B:92:0x0197, B:95:0x01a2, B:97:0x01af, B:99:0x01b3, B:102:0x01be, B:103:0x01c1, B:105:0x01ce, B:107:0x01d2, B:110:0x01dd, B:85:0x017c, B:121:0x0202, B:123:0x020e, B:125:0x0218, B:126:0x021b, B:124:0x0213), top: B:135:0x0107 }] */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005b, code lost:
    
        if (r13.zzc == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007e, code lost:
    
        if (r13.zzc != false) goto L58;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.content.res.AssetFileDescriptor zza(final android.content.Context r11, android.net.Uri r12, java.lang.String r13) throws java.io.FileNotFoundException {
        /*
            Method dump skipped, instruction units count: 568
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzn.zza(android.content.Context, android.net.Uri, java.lang.String):android.content.res.AssetFileDescriptor");
    }

    private static <T> T zzb(T t) throws FileNotFoundException {
        if (t != null) {
            return t;
        }
        throw new FileNotFoundException("Content resolver returned null value.");
    }

    private static String zzc(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        return !canonicalPath.endsWith("/") ? String.valueOf(canonicalPath).concat("/") : canonicalPath;
    }

    private static void zzd(AssetFileDescriptor assetFileDescriptor, FileNotFoundException fileNotFoundException) {
        try {
            assetFileDescriptor.close();
        } catch (IOException unused) {
        }
    }

    private static File[] zze(Callable<File[]> callable) {
        try {
            return callable.call();
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
