package org.catrobat.catroid.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NewCatroidPayloadHelper.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NewCatroidPayloadHelper {
    private static final byte[] MAGIC_MARKER = "NEWCATROID_MAGIC_PAYLOAD_V1".getBytes();

    public static void hideZipInAudio(File realAudio, File zipPayload, File output) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(realAudio);
        try {
            FileInputStream fileInputStream2 = new FileInputStream(zipPayload);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(output);
                try {
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i <= 0) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                        try {
                            fileInputStream2.close();
                        } catch (Throwable th) {
                            th.addSuppressed(th);
                        }
                        throw th;
                    }
                    fileOutputStream.write(MAGIC_MARKER);
                    while (true) {
                        int i2 = fileInputStream2.read(bArr);
                        if (i2 > 0) {
                            fileOutputStream.write(bArr, 0, i2);
                        } else {
                            fileOutputStream.close();
                            fileInputStream2.close();
                            fileInputStream.close();
                            return;
                        }
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
                fileInputStream2.close();
                throw th4;
            }
        } catch (Throwable th5) {
            try {
                fileInputStream.close();
            } catch (Throwable th6) {
                th5.addSuppressed(th6);
            }
            throw th5;
        }
    }

    public static boolean extractPayloadFromAudio(File fakeAudioFile, File outputZip) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fakeAudioFile);
        while (true) {
            int i = 0;
            while (true) {
                try {
                    int i2 = fileInputStream.read();
                    if (i2 != -1) {
                        byte[] bArr = MAGIC_MARKER;
                        if (i2 == bArr[i]) {
                            i++;
                            if (i == bArr.length) {
                                FileOutputStream fileOutputStream = new FileOutputStream(outputZip);
                                try {
                                    byte[] bArr2 = new byte[8192];
                                    while (true) {
                                        int i3 = fileInputStream.read(bArr2);
                                        if (i3 > 0) {
                                            fileOutputStream.write(bArr2, 0, i3);
                                        } else {
                                            fileOutputStream.close();
                                            fileInputStream.close();
                                            return true;
                                        }
                                        try {
                                            fileInputStream.close();
                                        } catch (Throwable th) {
                                            th.addSuppressed(th);
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (Throwable th3) {
                                        th2.addSuppressed(th3);
                                    }
                                    throw th2;
                                }
                            }
                        } else if (i2 == bArr[0]) {
                            i = 1;
                        }
                    } else {
                        fileInputStream.close();
                        return false;
                    }
                } catch (Throwable th4) {
                    fileInputStream.close();
                    throw th4;
                }
            }
        }
    }
}
