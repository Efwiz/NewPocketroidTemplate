package com.huawei.hms.mlsdk.t;

import com.huawei.hms.ml.common.utils.SmartLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: compiled from: PhoneTypeUtils.java */
/* JADX INFO: loaded from: classes10.dex */
public class h0 {
    private static Set<String> a;

    static {
        HashSet hashSet = new HashSet();
        a = hashSet;
        hashSet.add("0x803");
        a.add("0x805");
        a.add("0xd05");
        a.add("0x802");
        a.add("0x804");
        a.add("0xd01");
        a.add("0xd40");
        a.add("0xd41");
        a.add("0xd44");
        a.add("0xd0a");
        a.add("0xd0b");
        a.add("0xd0d");
    }

    /* JADX WARN: Code duplicated, block: B:127:0x016b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:142:0x014f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:154:0x015d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:168:? A[SYNTHETIC] */
    public static String a() throws Throwable {
        Throwable th;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        File file = new File("/proc/cpuinfo");
        if (!file.exists()) {
            return "f32";
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    inputStreamReader = new InputStreamReader(fileInputStream2, StandardCharsets.UTF_8);
                    try {
                        bufferedReader = new BufferedReader(inputStreamReader);
                        do {
                            try {
                                line = bufferedReader.readLine();
                                if (line == null) {
                                    try {
                                        fileInputStream2.close();
                                    } catch (IOException e) {
                                        a.a(e, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                                    }
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e2) {
                                        a.a(e2, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                                    }
                                    inputStreamReader.close();
                                }
                            } catch (IOException e3) {
                                e = e3;
                                fileInputStream = fileInputStream2;
                                try {
                                    SmartLog.e("MLLocalModelManager", "getPhoneType get stream failed: " + e.getMessage());
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e4) {
                                            a.a(e4, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e5) {
                                            a.a(e5, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                                        }
                                    }
                                    if (inputStreamReader != null) {
                                        inputStreamReader.close();
                                    }
                                    return "f32";
                                } catch (Throwable th2) {
                                    th = th2;
                                    inputStreamReader = inputStreamReader;
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e6) {
                                            a.a(e6, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e7) {
                                            a.a(e7, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                                        }
                                    }
                                    if (inputStreamReader != null) {
                                        throw th;
                                    }
                                    try {
                                        inputStreamReader.close();
                                        throw th;
                                    } catch (IOException e8) {
                                        a.a(e8, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                                        throw th;
                                    }
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                fileInputStream = fileInputStream2;
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                if (inputStreamReader != null) {
                                    throw th;
                                }
                                inputStreamReader.close();
                                throw th;
                            }
                            return "f32";
                        } while (!line.contains("CPU part"));
                        String[] strArrSplit = line.trim().split(":");
                        if (strArrSplit.length < 2) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e9) {
                                a.a(e9, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                            }
                            try {
                                bufferedReader.close();
                            } catch (IOException e10) {
                                a.a(e10, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                            }
                            try {
                                inputStreamReader.close();
                            } catch (IOException e11) {
                                a.a(e11, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                            }
                            return "f32";
                        }
                        if (!a.contains(strArrSplit[1].trim())) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e12) {
                                a.a(e12, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                            }
                            try {
                                bufferedReader.close();
                            } catch (IOException e13) {
                                a.a(e13, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                            }
                            try {
                                inputStreamReader.close();
                            } catch (IOException e14) {
                                a.a(e14, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                            }
                            return "f32";
                        }
                        try {
                            fileInputStream2.close();
                        } catch (IOException e15) {
                            a.a(e15, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                        }
                        try {
                            bufferedReader.close();
                        } catch (IOException e16) {
                            a.a(e16, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                        }
                        try {
                            inputStreamReader.close();
                            return "f16";
                        } catch (IOException e17) {
                            a.a(e17, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
                            return "f16";
                        }
                    } catch (IOException e18) {
                        e = e18;
                        bufferedReader = null;
                    } catch (Throwable th4) {
                        th = th4;
                        bufferedReader = null;
                    }
                } catch (IOException e19) {
                    e = e19;
                    bufferedReader = null;
                    fileInputStream = fileInputStream2;
                    inputStreamReader = null;
                } catch (Throwable th5) {
                    th = th5;
                    inputStreamReader = null;
                    bufferedReader = null;
                }
            } catch (IOException e20) {
                a.a(e20, a.a("getPhoneType close stream failed: "), "MLLocalModelManager");
            }
        } catch (IOException e21) {
            e = e21;
            inputStreamReader = null;
            bufferedReader = null;
        } catch (Throwable th6) {
            th = th6;
            inputStreamReader = null;
            bufferedReader = null;
        }
        return "f32";
    }
}
