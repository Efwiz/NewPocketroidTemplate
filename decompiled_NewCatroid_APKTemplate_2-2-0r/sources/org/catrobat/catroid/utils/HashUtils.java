package org.catrobat.catroid.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/HashUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HashUtils {
    public static String hashString(String input, String algorithm) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance(algorithm).digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDigest) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    sb.append(ExternalAnnotationProvider.NULLABLE);
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String hashFile(File file, String algorithm) {
        int i;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = fileInputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    messageDigest.update(bArr, 0, i2);
                    e.printStackTrace();
                    return "";
                }
                fileInputStream.close();
                byte[] bArrDigest = messageDigest.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : bArrDigest) {
                    sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
                }
                return sb.toString();
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
