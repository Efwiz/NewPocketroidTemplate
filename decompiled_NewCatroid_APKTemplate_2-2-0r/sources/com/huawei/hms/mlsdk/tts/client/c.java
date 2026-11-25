package com.huawei.hms.mlsdk.tts.client;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.t.i0;
import com.huawei.hms.mlsdk.tts.model.LanguageInfo;
import com.huawei.hms.mlsdk.tts.model.ModuleInfoBean;
import com.huawei.hms.mlsdk.tts.model.SpeakerInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: RemoteOnDeviceUtil.java */
/* JADX INFO: loaded from: classes10.dex */
public class c {

    /* JADX INFO: compiled from: RemoteOnDeviceUtil.java */
    static class a extends TypeToken<List<ModuleInfoBean>> {
        a() {
        }
    }

    /* JADX WARN: Code duplicated, block: B:61:0x00e5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x00d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:? A[SYNTHETIC] */
    public static List<ModuleInfoBean> a() throws Throwable {
        Throwable th;
        BufferedReader bufferedReader;
        IOException e;
        Context appContext = MLApplication.getInstance().getAppContext();
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        try {
            try {
                InputStreamReader inputStreamReader2 = new InputStreamReader(appContext.getAssets().open("tts_sdk_model_config.json"), StandardCharsets.UTF_8);
                try {
                    bufferedReader = new BufferedReader(inputStreamReader2);
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                sb.append(line);
                            } else {
                                try {
                                    break;
                                } catch (IOException e2) {
                                    com.huawei.hms.mlsdk.t.a.a(e2, com.huawei.hms.mlsdk.t.a.a("readFile close stream failed: "), "FileUtils");
                                }
                            }
                        } catch (IOException e3) {
                            e = e3;
                            inputStreamReader = inputStreamReader2;
                            try {
                                SmartLog.e("FileUtils", "file read failed:" + e.getMessage());
                                if (inputStreamReader != null) {
                                    try {
                                        inputStreamReader.close();
                                    } catch (IOException e4) {
                                        com.huawei.hms.mlsdk.t.a.a(e4, com.huawei.hms.mlsdk.t.a.a("readFile close stream failed: "), "FileUtils");
                                    }
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                SmartLog.d("FileUtils", sb.toString());
                                String strTrim = sb.toString().trim();
                                ArrayList arrayList = new ArrayList();
                                return (List) new Gson().fromJson(new JSONObject(strTrim).getJSONArray("modelModules").toString(), new a().getType());
                            } catch (Throwable th2) {
                                th = th2;
                                if (inputStreamReader != null) {
                                    try {
                                        inputStreamReader.close();
                                    } catch (IOException e5) {
                                        com.huawei.hms.mlsdk.t.a.a(e5, com.huawei.hms.mlsdk.t.a.a("readFile close stream failed: "), "FileUtils");
                                    }
                                }
                                if (bufferedReader != null) {
                                    throw th;
                                }
                                try {
                                    bufferedReader.close();
                                    throw th;
                                } catch (IOException e6) {
                                    com.huawei.hms.mlsdk.t.a.a(e6, com.huawei.hms.mlsdk.t.a.a("readFile close stream failed: "), "FileUtils");
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            inputStreamReader = inputStreamReader2;
                            if (inputStreamReader != null) {
                                inputStreamReader.close();
                            }
                            if (bufferedReader != null) {
                                throw th;
                            }
                            bufferedReader.close();
                            throw th;
                        }
                    }
                    inputStreamReader2.close();
                    bufferedReader.close();
                } catch (IOException e7) {
                    e = e7;
                    bufferedReader = null;
                } catch (Throwable th4) {
                    th = th4;
                    bufferedReader = null;
                }
            } catch (IOException e8) {
                com.huawei.hms.mlsdk.t.a.a(e8, com.huawei.hms.mlsdk.t.a.a("readFile close stream failed: "), "FileUtils");
            }
        } catch (IOException e9) {
            e = e9;
            bufferedReader = null;
        } catch (Throwable th5) {
            th = th5;
            bufferedReader = null;
        }
        SmartLog.d("FileUtils", sb.toString());
        String strTrim2 = sb.toString().trim();
        ArrayList arrayList2 = new ArrayList();
        try {
            return (List) new Gson().fromJson(new JSONObject(strTrim2).getJSONArray("modelModules").toString(), new a().getType());
        } catch (JSONException e10) {
            i0.b("RemoteOnDeviceUtil", e10.getMessage());
            return arrayList2;
        }
    }

    public static synchronized b b(String str) throws RuntimeException {
        b bVarA;
        ModuleInfoBean next;
        boolean z;
        Context appContext = MLApplication.getInstance().getAppContext();
        List<ModuleInfoBean> listA = a();
        List<String> listA2 = a(listA);
        i0.a("RemoteOnDeviceUtil", com.huawei.hms.mlsdk.t.a.a("support speakers list==").append(new Gson().toJson(listA2)).toString());
        bVarA = null;
        if (!listA2.contains(str)) {
            next = null;
            break;
        }
        Iterator<ModuleInfoBean> it = listA.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (str.contains(next.getFramework()) && !TextUtils.isEmpty(a(appContext, com.huawei.hms.mlsdk.t.a.a("com.huawei.hms.client.service.name:ml-computer-voice-tts-model-").append(next.getFramework()).toString()))) {
                break;
            }
        }
        if (next == null) {
            Iterator<ModuleInfoBean> it2 = a().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                ModuleInfoBean next2 = it2.next();
                if (str.contains(next2.getFramework())) {
                    next = next2;
                    break;
                }
            }
            z = true;
            Log.d("RemoteOnDeviceUtil", "getRemoteOnDeviceTts:liteFlag ->true");
        } else {
            z = false;
        }
        if (!TextUtils.isEmpty(next.getFramework())) {
            String string = com.huawei.hms.mlsdk.t.a.a("com.huawei.hms.mlkit.tts.").append(next.getFramework()).append(".TtsCreator").toString();
            String str2 = "creatorClassName==" + string;
            String str3 = "huawei_module_mlkit_tts_" + next.getFramework();
            String str4 = "moduleName==" + str3;
            String str5 = "minApkVersion==10002300";
            bVarA = com.huawei.hms.mlsdk.tts.client.a.a(new ModuleInfo(str3, string, 10002300, z));
        }
        return bVarA;
    }

    public static List<String> c() throws Throwable {
        ArrayList arrayList = new ArrayList();
        Context appContext = MLApplication.getInstance().getAppContext();
        List<ModuleInfoBean> listA = a();
        if (listA != null && listA.size() > 0) {
            for (ModuleInfoBean moduleInfoBean : listA) {
                if (!TextUtils.isEmpty(a(appContext, com.huawei.hms.mlsdk.t.a.a("com.huawei.hms.client.service.name:ml-computer-voice-tts-model-").append(moduleInfoBean.getFramework()).toString()))) {
                    arrayList.add(moduleInfoBean.getFramework());
                    i0.c("RemoteOnDeviceUtil", "Invoke the algorithm APK of the " + moduleInfoBean.getFramework());
                }
            }
        }
        return arrayList;
    }

    public static List<String> b() {
        return a(a());
    }

    public static synchronized List<SpeakerInfo> a(String str) {
        ArrayList arrayList;
        List<LanguageInfo> languages;
        arrayList = new ArrayList();
        List<String> listC = c();
        List<ModuleInfoBean> listA = a();
        for (String str2 : listC) {
            for (ModuleInfoBean moduleInfoBean : listA) {
                if (str2.equals(moduleInfoBean.getFramework()) && (languages = moduleInfoBean.getLanguages()) != null && languages.size() > 0) {
                    Iterator<LanguageInfo> it = languages.iterator();
                    while (it.hasNext()) {
                        for (SpeakerInfo speakerInfo : it.next().getSpeakers()) {
                            if (speakerInfo.getModelAccuracyLevel().equals(str)) {
                                arrayList.add(speakerInfo);
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public static String a(Context context, String str) {
        String string = "";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                string = applicationInfo.metaData.getString(str, "");
            } else {
                i0.c("RemoteOnDeviceUtil", "appInfo is null or appInfo metaData is null");
            }
        } catch (PackageManager.NameNotFoundException e) {
            i0.b("RemoteOnDeviceUtil", com.huawei.hms.mlsdk.t.a.a("PackageManager.NameNotFoundException = ").append(e.getMessage()).toString());
        }
        return string;
    }

    private static List<String> a(List<ModuleInfoBean> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<ModuleInfoBean> it = list.iterator();
        while (it.hasNext()) {
            List<LanguageInfo> languages = it.next().getLanguages();
            if (languages != null && languages.size() > 0) {
                Iterator<LanguageInfo> it2 = languages.iterator();
                while (it2.hasNext()) {
                    List<SpeakerInfo> speakers = it2.next().getSpeakers();
                    if (speakers != null && speakers.size() > 0) {
                        Iterator<SpeakerInfo> it3 = speakers.iterator();
                        while (it3.hasNext()) {
                            arrayList.add(it3.next().getModelName());
                        }
                    }
                }
            }
        }
        return arrayList;
    }
}
