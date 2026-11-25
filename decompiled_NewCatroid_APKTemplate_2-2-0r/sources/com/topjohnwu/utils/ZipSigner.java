package com.topjohnwu.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class ZipSigner {
    public static void usage() {
        System.err.println("ZipSigner usage:");
        System.err.println("  zipsigner.jar input.jar output.jar");
        System.err.println("    sign jar with AOSP test keys");
        System.err.println("  zipsigner.jar x509.pem pk8 input.jar output.jar");
        System.err.println("    sign jar with certificate / private key pair");
        System.err.println("  zipsigner.jar jks keyStorePass keyAlias keyPass input.jar output.jar");
        System.err.println("    sign jar with Java KeyStore");
        System.exit(2);
    }

    public static void main(String[] strArr) throws Exception {
        if (strArr.length != 2 && strArr.length != 4 && strArr.length != 6) {
            usage();
        }
        JarMap jarMap = new JarMap(strArr[strArr.length - 2], false);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(strArr[strArr.length - 1]);
            try {
                if (strArr.length == 2) {
                    SignAPK.sign(jarMap, fileOutputStream);
                } else if (strArr.length == 4) {
                    FileInputStream fileInputStream = new FileInputStream(strArr[0]);
                    try {
                        FileInputStream fileInputStream2 = new FileInputStream(strArr[1]);
                        try {
                            SignAPK.sign(fileInputStream, fileInputStream2, jarMap, fileOutputStream);
                            fileInputStream2.close();
                            fileInputStream.close();
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                try {
                                    fileInputStream2.close();
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                }
                                throw th2;
                            }
                        }
                    } catch (Throwable th4) {
                        try {
                            throw th4;
                        } catch (Throwable th5) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable th6) {
                                th4.addSuppressed(th6);
                            }
                            throw th5;
                        }
                    }
                } else if (strArr.length == 6) {
                    FileInputStream fileInputStream3 = new FileInputStream(strArr[0]);
                    try {
                        SignAPK.sign(fileInputStream3, strArr[1], strArr[2], strArr[3], jarMap, fileOutputStream);
                        fileInputStream3.close();
                    } catch (Throwable th7) {
                        try {
                            throw th7;
                        } catch (Throwable th8) {
                            try {
                                fileInputStream3.close();
                            } catch (Throwable th9) {
                                th7.addSuppressed(th9);
                            }
                            throw th8;
                        }
                    }
                }
                fileOutputStream.close();
                jarMap.close();
            } catch (Throwable th10) {
                try {
                    throw th10;
                } catch (Throwable th11) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th12) {
                        th10.addSuppressed(th12);
                    }
                    throw th11;
                }
            }
        } catch (Throwable th13) {
            try {
                throw th13;
            } catch (Throwable th14) {
                try {
                    jarMap.close();
                } catch (Throwable th15) {
                    th13.addSuppressed(th15);
                }
                throw th14;
            }
        }
    }
}
