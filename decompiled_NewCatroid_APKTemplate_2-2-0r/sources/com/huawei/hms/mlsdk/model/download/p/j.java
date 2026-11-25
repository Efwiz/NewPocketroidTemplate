package com.huawei.hms.mlsdk.model.download.p;

import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: compiled from: FileFolderUtils.java */
/* JADX INFO: loaded from: classes10.dex */
public class j {
    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                sb.append(ExternalAnnotationProvider.NULLABLE);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:45:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x0076 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x0073: MOVE (r2 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:37:0x0073 */
    public static String b(File file) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        if (file == null || !file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA256);
                fileInputStream = new FileInputStream(file);
                while (true) {
                    try {
                        int i = fileInputStream.read(bArr, 0, 1024);
                        if (i == -1) {
                            break;
                        }
                        messageDigest.update(bArr, 0, i);
                    } catch (IOException e) {
                        e = e;
                        SmartLog.e("MLSDK_MODEL_Sha256Utils", "getFileSha256 failed: " + e.getMessage());
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                                a.a(e2, a.a("getFileSha256 failed "), "MLSDK_MODEL_Sha256Utils");
                            }
                        }
                        return null;
                    } catch (NoSuchAlgorithmException e3) {
                        e = e3;
                        SmartLog.e("MLSDK_MODEL_Sha256Utils", "getFileSha256 failed: " + e.getMessage());
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return null;
                    }
                }
                String strA = a(messageDigest.digest());
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                    a.a(e4, a.a("getFileSha256 failed "), "MLSDK_MODEL_Sha256Utils");
                }
                return strA;
            } catch (Throwable th) {
                th = th;
                fileInputStream3 = fileInputStream2;
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException e5) {
                        a.a(e5, a.a("getFileSha256 failed "), "MLSDK_MODEL_Sha256Utils");
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            fileInputStream = null;
            SmartLog.e("MLSDK_MODEL_Sha256Utils", "getFileSha256 failed: " + e.getMessage());
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return null;
        } catch (NoSuchAlgorithmException e7) {
            e = e7;
            fileInputStream = null;
            SmartLog.e("MLSDK_MODEL_Sha256Utils", "getFileSha256 failed: " + e.getMessage());
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream3 != null) {
                fileInputStream3.close();
            }
            throw th;
        }
    }

    private static List<File> c(File file) {
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return Collections.emptyList();
        }
        for (File file2 : fileArrListFiles) {
            arrayList.add(file2);
            if (file2.isDirectory()) {
                arrayList.addAll(c(file2));
            }
        }
        return arrayList;
    }

    public static String d(File file) {
        String strA;
        StringBuilder sb = new StringBuilder();
        Iterator<File> it = c(file).iterator();
        while (it.hasNext()) {
            sb.append(b(it.next()));
        }
        String string = sb.toString();
        if (string == null || string.isEmpty()) {
            strA = null;
        } else {
            try {
                strA = a(MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA256).digest(string.getBytes(StandardCharsets.UTF_8)));
            } catch (NoSuchAlgorithmException e) {
                SmartLog.e("MLSDK_MODEL_Sha256Utils", a.a("getSha256ByString failed: ").append(e.getMessage()).toString());
                strA = null;
            }
        }
        if (strA == null || strA.isEmpty()) {
            return null;
        }
        return strA;
    }

    public static boolean a(File file) {
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        String[] list = file.list();
        if (list != null) {
            for (String str : list) {
                if (!a(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /* JADX WARN: Code duplicated, block: B:107:0x0188 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:108:0x018a A[Catch: IOException -> 0x018e, TRY_ENTER, TRY_LEAVE, TryCatch #10 {IOException -> 0x018e, blocks: (B:108:0x018a, B:99:0x0175, B:92:0x0154), top: B:118:0x0154, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:118:0x0154 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static void a(File file, File file2) throws Throwable {
        FileInputStream fileInputStream;
        IOException e;
        Throwable th;
        if (file == null) {
            return;
        }
        ZipInputStream zipInputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    ZipInputStream zipInputStream2 = new ZipInputStream(fileInputStream);
                    try {
                        for (ZipEntry nextEntry = zipInputStream2.getNextEntry(); nextEntry != null; nextEntry = zipInputStream2.getNextEntry()) {
                            String name2 = nextEntry.getName();
                            if (!name2.contains("../")) {
                                String str = file2 + File.separator + name2;
                                if (!nextEntry.isDirectory()) {
                                    a(zipInputStream2, str);
                                } else if (!new File(str).mkdir()) {
                                    try {
                                        SmartLog.e("MLSDK_MODEL_FileFolderUtils", "unZipFile mkdir failed");
                                        try {
                                            try {
                                                zipInputStream2.close();
                                                fileInputStream.close();
                                            } catch (Throwable th2) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (IOException e2) {
                                                    a.a(e2, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                                }
                                                throw th2;
                                            }
                                        } catch (IOException e3) {
                                            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e3.getMessage());
                                            fileInputStream.close();
                                        }
                                        return;
                                    } catch (IOException e4) {
                                        a.a(e4, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                        return;
                                    }
                                }
                                zipInputStream2.closeEntry();
                            } else {
                                SmartLog.w("MLSDK_MODEL_FileFolderUtils", "The unzip path invalid, stop unzip");
                                throw new MLException("The unzip path invalid, stop unzip", 2);
                            }
                        }
                        try {
                            try {
                                zipInputStream2.close();
                                fileInputStream.close();
                            } catch (IOException e5) {
                                SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e5.getMessage());
                                fileInputStream.close();
                            }
                        } catch (Throwable th3) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e6) {
                                a.a(e6, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                            }
                            throw th3;
                        }
                    } catch (IOException e7) {
                        e = e7;
                        zipInputStream = zipInputStream2;
                        try {
                            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "unZipFile failed: " + e.getMessage());
                            if (zipInputStream != null) {
                                try {
                                    try {
                                        zipInputStream.close();
                                    } catch (IOException e8) {
                                        SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e8.getMessage());
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                            return;
                                        }
                                        return;
                                    }
                                } catch (Throwable th4) {
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e9) {
                                            a.a(e9, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                        }
                                    }
                                    throw th4;
                                }
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            th = th;
                            if (zipInputStream != null) {
                                try {
                                    try {
                                        try {
                                            zipInputStream.close();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                        } catch (IOException e10) {
                                            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e10.getMessage());
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            throw th;
                                        }
                                    } catch (IOException e11) {
                                        a.a(e11, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                        throw th;
                                    }
                                } catch (Throwable th6) {
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e12) {
                                            a.a(e12, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                        }
                                    }
                                    throw th6;
                                }
                            } else if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        zipInputStream = zipInputStream2;
                        th = th;
                        if (zipInputStream != null) {
                            zipInputStream.close();
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } else if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e13) {
                    e = e13;
                } catch (Throwable th8) {
                    th = th8;
                    if (zipInputStream != null) {
                        zipInputStream.close();
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } else if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e14) {
                a.a(e14, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
            }
        } catch (IOException e15) {
            e = e15;
            fileInputStream = null;
        } catch (Throwable th9) {
            th = th9;
            fileInputStream = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:81:0x0104 A[Catch: IOException -> 0x0108, TRY_ENTER, TRY_LEAVE, TryCatch #10 {IOException -> 0x0108, blocks: (B:81:0x0104, B:72:0x00ef), top: B:105:0x00cc }] */
    private static void a(ZipInputStream zipInputStream, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        if (zipInputStream == null) {
            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "ZipInputStream can not null");
            return;
        }
        if (!Pattern.matches("^[0-9A-Za-z_/\\-.]+$", str)) {
            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "extractFile untrusted file: " + str);
            return;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
                try {
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = zipInputStream.read(bArr);
                            try {
                                if (i != -1) {
                                    bufferedOutputStream2.write(bArr, 0, i);
                                } else {
                                    try {
                                        break;
                                    } catch (IOException e) {
                                        SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e.getMessage());
                                        fileOutputStream.close();
                                    }
                                }
                            } catch (Throwable th) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e2) {
                                    a.a(e2, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                }
                                throw th;
                            }
                        }
                        bufferedOutputStream2.close();
                        fileOutputStream.close();
                    } catch (IOException unused) {
                        bufferedOutputStream = bufferedOutputStream2;
                        SmartLog.e("MLSDK_MODEL_FileFolderUtils", "extractFile failed");
                        if (bufferedOutputStream != null) {
                            try {
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException e3) {
                                    SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e3.getMessage());
                                    if (fileOutputStream == null) {
                                        return;
                                    } else {
                                        fileOutputStream.close();
                                    }
                                }
                            } catch (Throwable th2) {
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e4) {
                                        a.a(e4, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                    }
                                }
                                throw th2;
                            }
                        }
                        if (fileOutputStream == null) {
                        } else {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedOutputStream = bufferedOutputStream2;
                        try {
                            if (bufferedOutputStream != null) {
                                try {
                                    try {
                                        bufferedOutputStream.close();
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                    } catch (IOException e5) {
                                        SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e5.getMessage());
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th4) {
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException e6) {
                                            a.a(e6, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                        }
                                    }
                                    throw th4;
                                }
                            } else if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        } catch (IOException e7) {
                            a.a(e7, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                        }
                        throw th;
                    }
                } catch (IOException unused2) {
                }
            } catch (IOException e8) {
                a.a(e8, a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
            }
        } catch (IOException unused3) {
            fileOutputStream = null;
        } catch (Throwable th5) {
            th = th5;
            fileOutputStream = null;
        }
    }
}
