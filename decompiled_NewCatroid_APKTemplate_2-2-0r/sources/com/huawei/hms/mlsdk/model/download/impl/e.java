package com.huawei.hms.mlsdk.model.download.impl;

import android.content.Context;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.p.j;
import com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

/* JADX INFO: compiled from: ModelFileManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class e {
    private com.huawei.hms.mlsdk.model.download.strategy.a a;
    private String b;

    public e(WeakReference<Context> weakReference, com.huawei.hms.mlsdk.model.download.strategy.a aVar) {
        String canonicalPath;
        this.a = aVar;
        Context context = weakReference.get();
        if (context == null) {
            canonicalPath = "";
        } else {
            try {
                canonicalPath = context.getNoBackupFilesDir().getCanonicalPath();
            } catch (IOException unused) {
                SmartLog.e("MLSDK_MODEL_FileFolderUtils", "getModelRootPath failed");
                canonicalPath = "";
            }
        }
        this.b = canonicalPath;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0215  */
    /* JADX WARN: Code duplicated, block: B:106:0x024d  */
    /* JADX WARN: Code duplicated, block: B:129:0x02b2 A[Catch: IOException -> 0x02b6, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x02b6, blocks: (B:129:0x02b2, B:120:0x029c, B:112:0x0279), top: B:138:0x0277, inners: #15 }] */
    /* JADX WARN: Code duplicated, block: B:147:0x020b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x019c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:157:0x0279 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:173:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x01d3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:82:0x01d5 A[Catch: IOException -> 0x0137, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x0137, blocks: (B:33:0x0132, B:42:0x0157, B:82:0x01d5, B:73:0x01bf, B:32:0x012f), top: B:136:0x010f, inners: #17 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x01e6  */
    /* JADX WARN: Code duplicated, block: B:89:0x01ed  */
    /* JADX WARN: Code duplicated, block: B:91:0x01f4  */
    /* JADX WARN: Code duplicated, block: B:93:0x01fd  */
    public String a(MLRemoteModel mLRemoteModel, File file) throws Throwable {
        String str;
        Throwable th;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream2;
        String canonicalPath;
        String modelFilePath;
        String strD;
        ModelFileManagerStrategy modelFileManagerStrategyC = this.a.c(mLRemoteModel);
        String str2 = this.b + File.separator + "com.huawei.hms.ml.model.download." + modelFileManagerStrategyC.getModelFolderPath(mLRemoteModel);
        File file2 = new File(str2);
        try {
            String[] strArrSplit = file.getCanonicalPath().split("\\/");
            str = strArrSplit[strArrSplit.length - 1];
        } catch (IOException e) {
            com.huawei.hms.mlsdk.model.download.p.a.a(e, com.huawei.hms.mlsdk.model.download.p.a.a("processName failed: "), "MLSDK_MODEL_ModelFileManager");
            str = "";
        }
        SmartLog.d("MLSDK_MODEL_ModelFileManager", "saveModelFile modelFileName: " + str);
        SmartLog.d("MLSDK_MODEL_ModelFileManager", "saveModelFile modelPath: " + str2);
        ModelFileManagerStrategy modelFileManagerStrategyC2 = this.a.c(mLRemoteModel);
        String string = this.b + File.separator + "com.huawei.hms.ml.model.download." + modelFileManagerStrategyC2.getModelFolderPath(mLRemoteModel);
        String modelFilePath2 = modelFileManagerStrategyC2.getModelFilePath(mLRemoteModel, str);
        if (modelFilePath2 != null && !modelFilePath2.isEmpty()) {
            string = com.huawei.hms.mlsdk.model.download.p.a.a(string).append(File.separator).append(modelFilePath2).toString();
        }
        SmartLog.d("MLSDK_MODEL_ModelFileManager", "deleteModelFile from path: " + string);
        a(string);
        if (!file2.exists() && !file2.mkdirs()) {
            return null;
        }
        File file3 = new File(com.huawei.hms.mlsdk.model.download.p.a.a(str2).append(File.separator).append(str).toString());
        if (!file3.exists() || file3.delete()) {
            try {
                try {
                    try {
                        try {
                            fileInputStream = new FileInputStream(file);
                            try {
                                fileOutputStream2 = new FileOutputStream(file3);
                                try {
                                    try {
                                        byte[] bArr = new byte[1024];
                                        while (true) {
                                            int i = fileInputStream.read(bArr);
                                            if (i == -1) {
                                                try {
                                                    break;
                                                } catch (Throwable th2) {
                                                    try {
                                                        fileInputStream.close();
                                                        throw th2;
                                                    } catch (IOException e2) {
                                                        com.huawei.hms.mlsdk.model.download.p.a.a(e2, com.huawei.hms.mlsdk.model.download.p.a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                                        throw th2;
                                                    }
                                                }
                                            }
                                            fileOutputStream2.write(bArr, 0, i);
                                        }
                                        fileOutputStream2.flush();
                                        try {
                                            fileOutputStream2.close();
                                            fileInputStream.close();
                                        } catch (IOException e3) {
                                            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e3.getMessage());
                                            fileInputStream.close();
                                        }
                                    } catch (IOException e4) {
                                        e = e4;
                                        SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile failed: " + e.getMessage());
                                        try {
                                            if (fileOutputStream2 != null) {
                                                try {
                                                    fileOutputStream2.close();
                                                    if (fileInputStream != null) {
                                                        fileInputStream.close();
                                                    }
                                                } catch (IOException e5) {
                                                    SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e5.getMessage());
                                                    if (fileInputStream != null) {
                                                        fileInputStream.close();
                                                    }
                                                }
                                            } else if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            if (!file.delete()) {
                                                SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete failed");
                                                return null;
                                            }
                                            if (modelFileManagerStrategyC.isNeedUnCompress()) {
                                                j.a(file3, file2);
                                                if (!file3.delete()) {
                                                    SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete destFile file failed");
                                                    return null;
                                                }
                                            }
                                            if (modelFileManagerStrategyC.isNeedUnCompress()) {
                                                try {
                                                    canonicalPath = file2.getCanonicalPath();
                                                    modelFilePath = modelFileManagerStrategyC.getModelFilePath(mLRemoteModel, str);
                                                    if (modelFilePath != null) {
                                                        canonicalPath = com.huawei.hms.mlsdk.model.download.p.a.a(canonicalPath).append(File.separator).append(modelFilePath).toString();
                                                    }
                                                    strD = j.d(new File(canonicalPath));
                                                    SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
                                                } catch (IOException unused) {
                                                    SmartLog.e("MLSDK_MODEL_ModelFileManager", "saveModelFile faild");
                                                    return "";
                                                }
                                            } else {
                                                strD = j.b(file3);
                                                SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
                                            }
                                            SmartLog.d("MLSDK_MODEL_ModelFileManager", "saveModelFile hashCode: " + strD);
                                            return strD;
                                        } catch (Throwable th3) {
                                            if (fileInputStream == null) {
                                                throw th3;
                                            }
                                            try {
                                                fileInputStream.close();
                                                throw th3;
                                            } catch (IOException e6) {
                                                com.huawei.hms.mlsdk.model.download.p.a.a(e6, com.huawei.hms.mlsdk.model.download.p.a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                                throw th3;
                                            }
                                        }
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    fileOutputStream = fileOutputStream2;
                                    try {
                                        if (fileOutputStream != null) {
                                            try {
                                                try {
                                                    fileOutputStream.close();
                                                } catch (IOException e7) {
                                                    SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile close failed: " + e7.getMessage());
                                                    if (fileInputStream == null) {
                                                        throw th;
                                                    }
                                                    fileInputStream.close();
                                                    throw th;
                                                }
                                            } catch (Throwable th5) {
                                                if (fileInputStream == null) {
                                                    throw th5;
                                                }
                                                try {
                                                    fileInputStream.close();
                                                    throw th5;
                                                } catch (IOException e8) {
                                                    com.huawei.hms.mlsdk.model.download.p.a.a(e8, com.huawei.hms.mlsdk.model.download.p.a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                                    throw th5;
                                                }
                                            }
                                        }
                                        if (fileInputStream != null) {
                                            throw th;
                                        }
                                        fileInputStream.close();
                                        throw th;
                                    } catch (IOException e9) {
                                        com.huawei.hms.mlsdk.model.download.p.a.a(e9, com.huawei.hms.mlsdk.model.download.p.a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                                        throw th;
                                    }
                                }
                            } catch (IOException e10) {
                                e = e10;
                                fileOutputStream2 = null;
                                SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile failed: " + e.getMessage());
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                } else if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (!file.delete()) {
                                    SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete failed");
                                    return null;
                                }
                                if (modelFileManagerStrategyC.isNeedUnCompress()) {
                                    j.a(file3, file2);
                                    if (!file3.delete()) {
                                        SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete destFile file failed");
                                        return null;
                                    }
                                }
                                if (modelFileManagerStrategyC.isNeedUnCompress()) {
                                    canonicalPath = file2.getCanonicalPath();
                                    modelFilePath = modelFileManagerStrategyC.getModelFilePath(mLRemoteModel, str);
                                    if (modelFilePath != null) {
                                        canonicalPath = com.huawei.hms.mlsdk.model.download.p.a.a(canonicalPath).append(File.separator).append(modelFilePath).toString();
                                    }
                                    strD = j.d(new File(canonicalPath));
                                    SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
                                } else {
                                    strD = j.b(file3);
                                    SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
                                }
                                SmartLog.d("MLSDK_MODEL_ModelFileManager", "saveModelFile hashCode: " + strD);
                                return strD;
                            } catch (Throwable th6) {
                                th = th6;
                                fileOutputStream = null;
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                if (fileInputStream != null) {
                                    throw th;
                                }
                                fileInputStream.close();
                                throw th;
                            }
                        } catch (IOException e11) {
                            e = e11;
                            fileInputStream = null;
                            fileOutputStream2 = null;
                            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile failed: " + e.getMessage());
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                            } else if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (!file.delete()) {
                                SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete failed");
                                return null;
                            }
                            if (modelFileManagerStrategyC.isNeedUnCompress()) {
                                j.a(file3, file2);
                                if (!file3.delete()) {
                                    SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete destFile file failed");
                                    return null;
                                }
                            }
                            if (modelFileManagerStrategyC.isNeedUnCompress()) {
                                canonicalPath = file2.getCanonicalPath();
                                modelFilePath = modelFileManagerStrategyC.getModelFilePath(mLRemoteModel, str);
                                if (modelFilePath != null) {
                                    canonicalPath = com.huawei.hms.mlsdk.model.download.p.a.a(canonicalPath).append(File.separator).append(modelFilePath).toString();
                                }
                                strD = j.d(new File(canonicalPath));
                                SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
                            } else {
                                strD = j.b(file3);
                                SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
                            }
                            SmartLog.d("MLSDK_MODEL_ModelFileManager", "saveModelFile hashCode: " + strD);
                            return strD;
                        }
                    } catch (IOException e12) {
                        com.huawei.hms.mlsdk.model.download.p.a.a(e12, com.huawei.hms.mlsdk.model.download.p.a.a("copyFile close failed: "), "MLSDK_MODEL_FileFolderUtils");
                    }
                } catch (Throwable th7) {
                    th = th7;
                    fileOutputStream = null;
                    fileInputStream = null;
                }
            } catch (IOException e13) {
                e = e13;
            }
        } else {
            SmartLog.e("MLSDK_MODEL_FileFolderUtils", "copyFile delete failed");
        }
        if (!file.delete()) {
            SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete failed");
            return null;
        }
        if (modelFileManagerStrategyC.isNeedUnCompress()) {
            j.a(file3, file2);
            if (!file3.delete()) {
                SmartLog.w("MLSDK_MODEL_ModelFileManager", "saveModelFile delete destFile file failed");
                return null;
            }
        }
        if (modelFileManagerStrategyC.isNeedUnCompress()) {
            canonicalPath = file2.getCanonicalPath();
            modelFilePath = modelFileManagerStrategyC.getModelFilePath(mLRemoteModel, str);
            if (modelFilePath != null && !modelFilePath.isEmpty()) {
                canonicalPath = com.huawei.hms.mlsdk.model.download.p.a.a(canonicalPath).append(File.separator).append(modelFilePath).toString();
            }
            strD = j.d(new File(canonicalPath));
            SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
        } else {
            strD = j.b(file3);
            SmartLog.d("MLSDK_MODEL_ModelFileManager", "FolderSha256: " + strD);
        }
        SmartLog.d("MLSDK_MODEL_ModelFileManager", "saveModelFile hashCode: " + strD);
        return strD;
    }

    public String b(MLRemoteModel mLRemoteModel, Map<String, String> map) {
        ModelFileManagerStrategy modelFileManagerStrategyC = this.a.c(mLRemoteModel);
        String modelFolderPath = modelFileManagerStrategyC.getModelFolderPath(mLRemoteModel);
        String modelFilePath = modelFileManagerStrategyC.getModelFilePath(mLRemoteModel, map);
        String str = this.b + File.separator + "com.huawei.hms.ml.model.download." + modelFolderPath;
        return (modelFilePath == null || modelFilePath.isEmpty()) ? str : com.huawei.hms.mlsdk.model.download.p.a.a(str).append(File.separator).append(modelFilePath).toString();
    }

    public void b(File file) {
        if (file.delete()) {
            return;
        }
        SmartLog.e("MLSDK_MODEL_ModelFileManager", "handleIntegrityCheckFail delete temp file failed");
    }

    public boolean a(MLRemoteModel mLRemoteModel, Map<String, String> map) {
        ModelFileManagerStrategy modelFileManagerStrategyC = this.a.c(mLRemoteModel);
        String string = this.b + File.separator + "com.huawei.hms.ml.model.download." + modelFileManagerStrategyC.getModelFolderPath(mLRemoteModel);
        String modelFilePath = modelFileManagerStrategyC.getModelFilePath(mLRemoteModel, map);
        if (modelFilePath != null && !modelFilePath.isEmpty()) {
            string = com.huawei.hms.mlsdk.model.download.p.a.a(string).append(File.separator).append(modelFilePath).toString();
        }
        SmartLog.d("MLSDK_MODEL_ModelFileManager", "deleteModelFile from path : " + string);
        return j.a(new File(string));
    }

    public boolean a(File file, String str) throws Throwable {
        if (str != null && !str.isEmpty()) {
            String strB = j.b(file);
            if (strB != null && !strB.isEmpty()) {
                SmartLog.d("MLSDK_MODEL_ModelFileManager", "integrityCheck: " + str + " / " + strB);
                return strB.equals(str);
            }
            SmartLog.e("MLSDK_MODEL_ModelFileManager", "integrityCheck sha256 invalid");
            return false;
        }
        SmartLog.e("MLSDK_MODEL_ModelFileManager", "integrityCheck originHash invalid");
        return false;
    }

    public String a(File file) {
        if (file.isDirectory()) {
            return j.d(file);
        }
        return j.b(file);
    }

    private boolean a(String str) {
        return j.a(new File(str));
    }
}
