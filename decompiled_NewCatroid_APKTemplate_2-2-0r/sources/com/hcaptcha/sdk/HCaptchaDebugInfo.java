package com.hcaptcha.sdk;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import dalvik.system.DexFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.HttpUrl;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
final class HCaptchaDebugInfo implements Serializable {
    private static final String CHARSET_NAME = "UTF-8";
    private static final String GET_PROP_BIN = "/system/bin/getprop";
    public static final String JS_INTERFACE_TAG = "JSDI";
    private static final int MAX_ENTRIES = 512;
    private static String sDebugInfo = "[]";
    private static String sSysDebug;
    private final transient WeakReference<Context> contextRef;

    public HCaptchaDebugInfo(WeakReference<Context> weakReference) {
        if (weakReference == null) {
            throw new NullPointerException("contextRef is marked non-null but is null");
        }
        this.contextRef = weakReference;
    }

    public List<String> debugInfo(String str, String str2) throws NoSuchAlgorithmException, IOException {
        ArrayList arrayList = new ArrayList(8);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");
        MessageDigest messageDigest3 = MessageDigest.getInstance("MD5");
        DexFile dexFile = new DexFile(str2);
        try {
            Enumeration<String> enumerationEntries = dexFile.entries();
            int i = 0;
            while (enumerationEntries.hasMoreElements()) {
                String strNextElement = enumerationEntries.nextElement();
                if (strNextElement.startsWith("com.google.android.") || strNextElement.startsWith("android.")) {
                    messageDigest.update(strNextElement.getBytes("UTF-8"));
                } else if (strNextElement.startsWith(str)) {
                    messageDigest2.update(strNextElement.getBytes("UTF-8"));
                } else {
                    messageDigest3.update(strNextElement.getBytes("UTF-8"));
                }
                i++;
                if (i > 512) {
                    break;
                }
            }
            dexFile.close();
            arrayList.add("sys_".concat(String.format("%032x", new BigInteger(1, messageDigest.digest()))));
            arrayList.add("deps_".concat(String.format("%032x", new BigInteger(1, messageDigest3.digest()))));
            arrayList.add("app_".concat(String.format("%032x", new BigInteger(1, messageDigest2.digest()))));
            arrayList.add("aver_" + Build.VERSION.RELEASE);
            arrayList.add("sdk_" + BuildConfig.VERSION_NAME.replace('.', '_'));
            return arrayList;
        } catch (Throwable th) {
            dexFile.close();
            throw th;
        }
    }

    public boolean equals(Object obj) {
        return obj == this || (obj instanceof HCaptchaDebugInfo);
    }

    public WeakReference<Context> getContextRef() {
        return this.contextRef;
    }

    @JavascriptInterface
    public String getDebugInfo() {
        String str = sDebugInfo;
        if (str != null) {
            return str;
        }
        synchronized (this) {
            String str2 = sDebugInfo;
            if (str2 != null) {
                return str2;
            }
            Context context = this.contextRef.get();
            if (context == null) {
                Log.w(JS_INTERFACE_TAG, "Context is null, cannot build debugInfo");
                return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
            }
            try {
                String strWriteValueAsString = new ObjectMapper().writeValueAsString(debugInfo(context.getPackageName(), context.getPackageCodePath()));
                sDebugInfo = strWriteValueAsString;
                return strWriteValueAsString;
            } catch (IOException | NoSuchAlgorithmException unused) {
                Log.d(JS_INTERFACE_TAG, "Cannot build debugInfo");
                return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
            }
        }
    }

    @JavascriptInterface
    public String getSysDebug() {
        String str = sSysDebug;
        if (str != null) {
            return str;
        }
        synchronized (this) {
            String str2 = sSysDebug;
            if (str2 != null) {
                return str2;
            }
            try {
                String strWriteValueAsString = new ObjectMapper().writeValueAsString(roBuildProps());
                sSysDebug = strWriteValueAsString;
                return strWriteValueAsString;
            } catch (IOException unused) {
                Log.d(JS_INTERFACE_TAG, "Cannot build sysDebug");
                return "{}";
            }
        }
    }

    public int hashCode() {
        return 1;
    }

    public Map<String, String> roBuildProps() {
        HashMap map = new HashMap();
        Process processStart = null;
        try {
            processStart = new ProcessBuilder(GET_PROP_BIN).start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processStart.getInputStream(), "UTF-8"));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        bufferedReader.close();
                        processStart.destroy();
                        return map;
                    }
                    if (line.endsWith("]")) {
                        sb.replace(0, sb.length() == 0 ? 0 : sb.length() - 1, line);
                        String[] strArrSplit = sb.toString().split("]: \\[");
                        String strSubstring = strArrSplit[0].substring(1);
                        if (strSubstring.startsWith(LanguageCodeUtil.RO)) {
                            String str = strArrSplit[1];
                            map.put(strSubstring, str.substring(0, str.length() - 2));
                        }
                    } else {
                        sb.append(line);
                    }
                }
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (processStart != null) {
                processStart.destroy();
            }
            throw th3;
        }
    }

    public String toString() {
        return "HCaptchaDebugInfo(contextRef=" + getContextRef() + Constant.AFTER_QUTO;
    }

    public HCaptchaDebugInfo(Context context) {
        if (context == null) {
            throw new NullPointerException("context is marked non-null but is null");
        }
        this.contextRef = new WeakReference<>(context);
    }
}
