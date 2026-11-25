package com.huawei.hms.mlsdk.tts.engine.common;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.t.g0;
import com.huawei.hms.mlsdk.t.h0;
import com.huawei.hms.mlsdk.t.i0;
import com.huawei.hms.mlsdk.t.k0;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsSpeaker;
import com.huawei.hms.mlsdk.tts.model.LanguageInfo;
import com.huawei.hms.mlsdk.tts.model.SpeakerInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: SpeakerProvider.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    static final /* synthetic */ boolean g = true;
    private volatile boolean b;
    private List<String> f;
    private long a = 0;
    private Set<String> c = new HashSet();
    private Set<ModelUrl> d = new HashSet();
    private SharedPreferences e = MLApplication.getInstance().getAppContext().getSharedPreferences("tts_speakers_info", 0);

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class a implements Callback {
        final /* synthetic */ l a;

        a(l lVar) {
            this.a = lVar;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (!b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
                l lVar = this.a;
                if (lVar != null) {
                    lVar.a(1);
                    return;
                }
                return;
            }
            b.this.d.addAll(b.this.c(strString));
            String json = new Gson().toJson(b.this.d);
            if (!TextUtils.equals(b.a(b.this), json)) {
                b.this.b = true;
                b.this.d(json);
            }
            l lVar2 = this.a;
            if (lVar2 != null) {
                lVar2.a(0);
            }
        }
    }

    /* JADX INFO: renamed from: com.huawei.hms.mlsdk.tts.engine.common.b$b, reason: collision with other inner class name */
    /* JADX INFO: compiled from: SpeakerProvider.java */
    class C0124b extends TypeToken<List<LanguageInfo>> {
        C0124b(b bVar) {
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class c implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ long b;

        c(l lVar, long j) {
            this.a = lVar;
            this.b = j;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            i0.a("SpeakerProvider", com.huawei.hms.mlsdk.t.a.a("request time is : ").append(System.currentTimeMillis() - this.b).append(LanguageCodeUtil.MS).toString());
            String strString = response.body().string();
            String str = "Voice list response=" + strString;
            if (!b.this.a("online", strString)) {
                i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
                l lVar = this.a;
                if (lVar != null) {
                    lVar.a(1);
                    return;
                }
                return;
            }
            JSONArray jSONArrayA = g0.a(g0.d(strString), "languages");
            if (!TextUtils.equals(b.this.f(), jSONArrayA.toString())) {
                b.this.b = true;
                b.this.e(jSONArrayA.toString());
            }
            l lVar2 = this.a;
            if (lVar2 != null) {
                lVar2.a(0);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class d implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ OkHttpClient b;

        d(l lVar, OkHttpClient okHttpClient) {
            this.a = lVar;
            this.b = okHttpClient;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (b.this.b(strString)) {
                b.this.c(this.a, this.b);
                return;
            }
            if (b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                b.this.d.addAll(b.this.c(strString));
                b.this.c(this.a, this.b);
                return;
            }
            i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
            l lVar = this.a;
            if (lVar != null) {
                lVar.a(1);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class e implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ OkHttpClient b;

        e(l lVar, OkHttpClient okHttpClient) {
            this.a = lVar;
            this.b = okHttpClient;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (b.this.b(strString)) {
                b.this.h(this.a, this.b);
                return;
            }
            if (b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                b.this.d.addAll(b.this.c(strString));
                b.this.h(this.a, this.b);
                return;
            }
            i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
            l lVar = this.a;
            if (lVar != null) {
                lVar.a(1);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class f implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ OkHttpClient b;

        f(l lVar, OkHttpClient okHttpClient) {
            this.a = lVar;
            this.b = okHttpClient;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (b.this.b(strString)) {
                b.this.d(this.a, this.b);
                return;
            }
            if (b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                b.this.d.addAll(b.this.c(strString));
                b.this.d(this.a, this.b);
                return;
            }
            i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
            l lVar = this.a;
            if (lVar != null) {
                lVar.a(1);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class g implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ OkHttpClient b;

        g(l lVar, OkHttpClient okHttpClient) {
            this.a = lVar;
            this.b = okHttpClient;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (b.this.b(strString)) {
                b.this.a(this.a, this.b);
                return;
            }
            if (b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                b.this.d.addAll(b.this.c(strString));
                b.this.a(this.a, this.b);
                return;
            }
            i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
            l lVar = this.a;
            if (lVar != null) {
                lVar.a(1);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class h implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ OkHttpClient b;

        h(l lVar, OkHttpClient okHttpClient) {
            this.a = lVar;
            this.b = okHttpClient;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (b.this.b(strString)) {
                b.this.f(this.a, this.b);
                return;
            }
            if (b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                b.this.d.addAll(b.this.c(strString));
                b.this.f(this.a, this.b);
                return;
            }
            i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
            l lVar = this.a;
            if (lVar != null) {
                lVar.a(1);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class i implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ OkHttpClient b;

        i(l lVar, OkHttpClient okHttpClient) {
            this.a = lVar;
            this.b = okHttpClient;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (b.this.b(strString)) {
                b.this.g(this.a, this.b);
                return;
            }
            if (b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                b.this.d.addAll(b.this.c(strString));
                b.this.g(this.a, this.b);
                return;
            }
            i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
            l lVar = this.a;
            if (lVar != null) {
                lVar.a(1);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    class j implements Callback {
        final /* synthetic */ l a;
        final /* synthetic */ OkHttpClient b;

        j(l lVar, OkHttpClient okHttpClient) {
            this.a = lVar;
            this.b = okHttpClient;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            b.this.a(iOException, this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            if (b.this.b(strString)) {
                b.this.e(this.a, this.b);
                return;
            }
            if (b.this.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
                b.this.d.addAll(b.this.c(strString));
                b.this.e(this.a, this.b);
                return;
            }
            i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
            l lVar = this.a;
            if (lVar != null) {
                lVar.a(1);
            }
        }
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    private static final class k {
        static final b a = new b(null);
    }

    /* JADX INFO: compiled from: SpeakerProvider.java */
    public interface l {
        void a(int i);
    }

    private b() {
        this.f = new ArrayList();
        this.f = com.huawei.hms.mlsdk.tts.client.c.b();
    }

    public static b e() {
        return k.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a("ru-RU", okHttpClient, "/v1/model/queryModel", new i(lVar, okHttpClient));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a("th-TH", okHttpClient, "/v1/model/queryModel", new j(lVar, okHttpClient));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a("es-ES", okHttpClient, "/v1/model/queryModel", new f(lVar, okHttpClient));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a("it-IT", okHttpClient, "/v1/model/queryModel", new a(lVar));
    }

    public Set<String> d() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a("de-DE", okHttpClient, "/v1/model/queryModel", new g(lVar, okHttpClient));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String f() {
        return this.e.getString("tts_speaker_json", "");
    }

    public void b(l lVar) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.a <= 300) {
            return;
        }
        this.a = jCurrentTimeMillis;
        if (this.b && lVar != null) {
            lVar.a(0);
        } else {
            com.huawei.hms.mlsdk.t.l.d().a(com.huawei.hms.mlsdk.t.l.d().b(), "/v1/tts/list", new c(lVar, jCurrentTimeMillis));
        }
    }

    public Set<MLTtsSpeaker> c() {
        JSONArray jSONArrayB;
        String strF = f();
        if (!TextUtils.isEmpty(strF)) {
            jSONArrayB = g0.b(strF);
        } else {
            jSONArrayB = g0.b("[{\"speakers\":[{\"codec\":\"4\",\"name\":\"tr-TR-st-1\",\"desc\":\"土耳其语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"tr-TR\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ms-MS-st-1\",\"desc\":\"马来语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ms-MS\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"pl-PL-st-1\",\"desc\":\"波兰语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"pl-PL\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"th-TH-st-1\",\"desc\":\"泰语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"th-TH\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ru-RU-st-1\",\"desc\":\"俄罗斯语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ru-RU\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ar-AR-st-1\",\"desc\":\"阿拉伯语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ar-AR\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"it-IT-st-1\",\"desc\":\"意大利语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"it-IT\"},{\"speakers\":[{\"codec\":\"1,3\",\"name\":\"zh-Hans-st-2\",\"desc\":\"中文标准男声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-st-1\",\"desc\":\"中文标准女声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-hq-2\",\"desc\":\"中文高音质男声\",\"sampleRate\":\"22050\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-hq-1\",\"desc\":\"中文高音质女声\",\"sampleRate\":\"22050\"}],\"language\":\"zh-Hans\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"de-DE-st-1\",\"desc\":\"德语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"de-DE\"},{\"speakers\":[{\"codec\":\"1,3\",\"name\":\"en-US-st-1\",\"desc\":\"美英标准女声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"en-US-st-2\",\"desc\":\"美英标准男声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"en-US-hq-1\",\"desc\":\"英文高音质女声\",\"sampleRate\":\"22050\"},{\"codec\":\"1,3\",\"name\":\"en-US-hq-2\",\"desc\":\"英文高音质男声\",\"sampleRate\":\"22050\"}],\"language\":\"en-US\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"es-ES-st-1\",\"desc\":\"西班牙标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"es-ES\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"fr-FR-st-1\",\"desc\":\"法语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"fr-FR\"}]");
        }
        return a(jSONArrayB, "st");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void e(String str) {
        SharedPreferences.Editor editorEdit = this.e.edit();
        editorEdit.putString("tts_speaker_json", str);
        editorEdit.apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void d(String str) {
        SharedPreferences.Editor editorEdit = this.e.edit();
        editorEdit.putString("tts_offline_speaker_json", str);
        editorEdit.apply();
    }

    public Set<MLTtsSpeaker> a() {
        JSONArray jSONArrayB;
        String strF = f();
        if (!TextUtils.isEmpty(strF)) {
            jSONArrayB = g0.b(strF);
        } else {
            jSONArrayB = g0.b("[{\"speakers\":[{\"codec\":\"4\",\"name\":\"tr-TR-st-1\",\"desc\":\"土耳其语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"tr-TR\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ms-MS-st-1\",\"desc\":\"马来语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ms-MS\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"pl-PL-st-1\",\"desc\":\"波兰语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"pl-PL\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"th-TH-st-1\",\"desc\":\"泰语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"th-TH\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ru-RU-st-1\",\"desc\":\"俄罗斯语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ru-RU\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ar-AR-st-1\",\"desc\":\"阿拉伯语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ar-AR\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"it-IT-st-1\",\"desc\":\"意大利语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"it-IT\"},{\"speakers\":[{\"codec\":\"1,3\",\"name\":\"zh-Hans-st-2\",\"desc\":\"中文标准男声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-st-1\",\"desc\":\"中文标准女声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-hq-2\",\"desc\":\"中文高音质男声\",\"sampleRate\":\"22050\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-hq-1\",\"desc\":\"中文高音质女声\",\"sampleRate\":\"22050\"}],\"language\":\"zh-Hans\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"de-DE-st-1\",\"desc\":\"德语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"de-DE\"},{\"speakers\":[{\"codec\":\"1,3\",\"name\":\"en-US-st-1\",\"desc\":\"美英标准女声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"en-US-st-2\",\"desc\":\"美英标准男声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"en-US-hq-1\",\"desc\":\"英文高音质女声\",\"sampleRate\":\"22050\"},{\"codec\":\"1,3\",\"name\":\"en-US-hq-2\",\"desc\":\"英文高音质男声\",\"sampleRate\":\"22050\"}],\"language\":\"en-US\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"es-ES-st-1\",\"desc\":\"西班牙标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"es-ES\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"fr-FR-st-1\",\"desc\":\"法语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"fr-FR\"}]");
        }
        return a(jSONArrayB, "hq");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a("fr-FR", okHttpClient, "/v1/model/queryModel", new e(lVar, okHttpClient));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Set<ModelUrl> c(String str) throws Throwable {
        HashSet<com.huawei.hms.mlsdk.tts.engine.offline.a> hashSet;
        HashSet hashSet2 = new HashSet();
        i0.a("SpeakerProvider", "handleQueryUrlResult result: " + str);
        i0.c("SpeakerProvider", "handleQueryUrlResult result: body is secret");
        ModelUrlQueryResponse modelUrlQueryResponse = (ModelUrlQueryResponse) new Gson().fromJson(str, ModelUrlQueryResponse.class);
        if (modelUrlQueryResponse.getData() == null || modelUrlQueryResponse.getData().size() == 0) {
            hashSet = null;
        } else {
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet();
            Iterator<ModelUrl> it = modelUrlQueryResponse.getData().iterator();
            while (it.hasNext()) {
                linkedHashSet.add(it.next().getModelName());
            }
            hashSet = new HashSet();
            for (String str2 : linkedHashSet) {
                com.huawei.hms.mlsdk.tts.engine.offline.a aVar = new com.huawei.hms.mlsdk.tts.engine.offline.a();
                aVar.a(str2);
                hashSet.add(aVar);
            }
            for (com.huawei.hms.mlsdk.tts.engine.offline.a aVar2 : hashSet) {
                for (ModelUrl modelUrl : modelUrlQueryResponse.getData()) {
                    if (modelUrl.getModelName().equals(aVar2.a())) {
                        if (aVar2.b() == null) {
                            aVar2.a(modelUrl);
                        } else {
                            String modelVersion = aVar2.b().getModelVersion();
                            String modelVersion2 = modelUrl.getModelVersion();
                            if (modelVersion == null || modelVersion2 == null) {
                                modelVersion2 = modelVersion;
                                break;
                            }
                            i0.c("SpeakerProvider", "getLatestVersion latest: " + modelVersion + " compared: " + modelVersion2);
                            String[] strArrSplit = modelVersion.split("\\.");
                            String[] strArrSplit2 = modelVersion2.split("\\.");
                            int i2 = 0;
                            while (true) {
                                try {
                                    if (i2 >= strArrSplit.length) {
                                        modelVersion2 = modelVersion;
                                        break;
                                    }
                                    if (Integer.parseInt(strArrSplit[i2]) < Integer.parseInt(strArrSplit2[i2])) {
                                        break;
                                    }
                                    i2++;
                                } catch (NumberFormatException unused) {
                                    i0.b("SpeakerProvider", "get version catch an exception");
                                }
                            }
                            if (!modelVersion.equals(modelVersion2)) {
                                aVar2.a(modelUrl);
                            }
                        }
                    }
                }
            }
        }
        List<String> listC = com.huawei.hms.mlsdk.tts.client.c.c();
        if (listC.size() > 0) {
            if (hashSet != null && hashSet.size() > 0) {
                for (com.huawei.hms.mlsdk.tts.engine.offline.a aVar3 : hashSet) {
                    Iterator<String> it2 = listC.iterator();
                    while (it2.hasNext()) {
                        if (aVar3.a().contains(it2.next())) {
                            hashSet2.add(aVar3.b());
                        }
                    }
                }
            }
        } else {
            if (!g && hashSet == null) {
                throw new AssertionError();
            }
            for (com.huawei.hms.mlsdk.tts.engine.offline.a aVar4 : hashSet) {
                if (this.f.contains(aVar4.a())) {
                    hashSet2.add(aVar4.b());
                }
            }
        }
        return hashSet2;
    }

    public int a(String str, boolean z) {
        Set<MLTtsSpeaker> setA;
        String language;
        if (z) {
            str = str.toLowerCase(Locale.ENGLISH);
        }
        String strF = f();
        JSONArray jSONArrayB = g0.b(strF);
        if (z) {
            setA = a(jSONArrayB);
        } else {
            setA = a(jSONArrayB, "available");
        }
        if (!TextUtils.isEmpty(strF)) {
            for (MLTtsSpeaker mLTtsSpeaker : setA) {
                if (z) {
                    language = mLTtsSpeaker.getLanguage().toLowerCase(Locale.ENGLISH);
                } else {
                    language = mLTtsSpeaker.getLanguage();
                }
                if (TextUtils.equals(str, language)) {
                    return 0;
                }
            }
        }
        return this.b ? 1 : 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a("en-US", okHttpClient, "/v1/model/queryModel", new d(lVar, okHttpClient));
    }

    /* synthetic */ b(c cVar) {
        this.f = new ArrayList();
        this.f = com.huawei.hms.mlsdk.tts.client.c.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        JSONArray jSONArrayA = g0.a(g0.d(str), "data");
        return jSONArrayA == null || jSONArrayA.length() == 0;
    }

    public Set<MLTtsSpeaker> b() {
        JSONArray jSONArrayB;
        String string = this.e.getString("tts_offline_speaker_json", "");
        if (!TextUtils.isEmpty(string)) {
            jSONArrayB = g0.b(string);
        } else {
            String json = new Gson().toJson(com.huawei.hms.mlsdk.tts.client.c.a(h0.a()));
            i0.a("SpeakerProvider", "default speakerJson==" + json);
            jSONArrayB = g0.b(json);
        }
        return a(jSONArrayB);
    }

    public void a(l lVar) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.a <= 300) {
            return;
        }
        this.a = jCurrentTimeMillis;
        if (this.b && lVar != null) {
            lVar.a(0);
        } else {
            OkHttpClient okHttpClientB = com.huawei.hms.mlsdk.t.l.d().b();
            com.huawei.hms.mlsdk.t.l.d().a(MLTtsConstants.TTS_ZH_HANS, okHttpClientB, "/v1/model/queryModel", new com.huawei.hms.mlsdk.tts.engine.common.c(this, lVar, okHttpClientB));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(l lVar, OkHttpClient okHttpClient) {
        com.huawei.hms.mlsdk.t.l.d().a(MLTtsConstants.TTS_LAN_AR_AR, okHttpClient, "/v1/model/queryModel", new h(lVar, okHttpClient));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(IOException iOException, l lVar) {
        i0.b("SpeakerProvider", com.huawei.hms.mlsdk.t.a.a("Use default List; Voice list unavailable:").append(iOException.getMessage()).toString());
        if (lVar != null) {
            lVar.a(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            i0.b("SpeakerProvider", "Voice list isEmpty.");
            return false;
        }
        JSONObject jSONObjectD = g0.d(str2);
        if (jSONObjectD == null) {
            i0.b("SpeakerProvider", "Response json incorrect format.");
            return false;
        }
        if (!"0".equals(g0.b(jSONObjectD, "retCode"))) {
            i0.b("SpeakerProvider", com.huawei.hms.mlsdk.t.a.a("Response error! errcode=[").append(g0.b(jSONObjectD, "retCode")).append("] retMsg=[").append(g0.b(jSONObjectD, "retMsg")).append("]").toString());
            return false;
        }
        if ("online".equals(str)) {
            JSONArray jSONArrayA = g0.a(jSONObjectD, "languages");
            if (jSONArrayA != null && jSONArrayA.length() != 0) {
                return true;
            }
            i0.b("SpeakerProvider", "Response json not contain languages");
            return false;
        }
        if (!MLTtsConstants.TTS_OFFLINE_MODE.equals(str)) {
            return true;
        }
        JSONArray jSONArrayA2 = g0.a(jSONObjectD, "data");
        if (jSONArrayA2 != null && jSONArrayA2.length() != 0) {
            return true;
        }
        i0.b("SpeakerProvider", "Response json not contain data");
        return false;
    }

    private Set<MLTtsSpeaker> a(JSONArray jSONArray, String str) {
        JSONArray jSONArray2;
        JSONArray jSONArray3 = jSONArray;
        HashSet hashSet = new HashSet();
        if (jSONArray3 != null && jSONArray.length() != 0) {
            this.c.clear();
            int i2 = 0;
            while (i2 < jSONArray.length()) {
                try {
                    JSONObject jSONObject = (JSONObject) jSONArray3.get(i2);
                    String strB = g0.b(jSONObject, "language");
                    JSONArray jSONArrayA = g0.a(jSONObject, "speakers");
                    if (jSONArrayA != null && jSONArrayA.length() != 0) {
                        int i3 = 0;
                        while (i3 < jSONArrayA.length()) {
                            try {
                                JSONObject jSONObject2 = (JSONObject) jSONArrayA.get(i3);
                                String strB2 = g0.b(jSONObject2, "name");
                                if (TextUtils.isEmpty(strB2)) {
                                    i0.b("SpeakerProvider", "language[" + strB + "] [" + i3 + "] parsing name failed.");
                                } else {
                                    String strB3 = g0.b(jSONObject2, "codec");
                                    if (TextUtils.isEmpty(strB3)) {
                                        i0.b("SpeakerProvider", "language[" + strB + "] [" + i3 + "] parsing codec failed.");
                                    } else {
                                        String[] strArrSplit = strB3.split(",");
                                        if (strArrSplit.length > 0) {
                                            int length = strArrSplit.length;
                                            int i4 = 0;
                                            while (i4 < length) {
                                                jSONArray2 = jSONArrayA;
                                                if (Integer.parseInt(strArrSplit[i4]) == 4) {
                                                    try {
                                                        this.c.add(strB2);
                                                    } catch (NumberFormatException unused) {
                                                        i0.b("SpeakerProvider", "JSONArray parsing codec failed. in  " + i2);
                                                        i3++;
                                                        jSONArrayA = jSONArray2;
                                                    } catch (JSONException unused2) {
                                                        i0.b("SpeakerProvider", "JSONArray parsing failed. in  " + i2);
                                                        i3++;
                                                        jSONArrayA = jSONArray2;
                                                    }
                                                }
                                                i4++;
                                                jSONArrayA = jSONArray2;
                                            }
                                        }
                                        jSONArray2 = jSONArrayA;
                                        String strB4 = g0.b(jSONObject2, "desc");
                                        if (strB2.contains(str)) {
                                            hashSet.add(new MLTtsSpeaker(strB, strB2, strB4));
                                        } else if (str.equals("available")) {
                                            hashSet.add(new MLTtsSpeaker(strB, strB2, strB4));
                                        }
                                    }
                                    i3++;
                                    jSONArrayA = jSONArray2;
                                }
                                jSONArray2 = jSONArrayA;
                            } catch (NumberFormatException unused3) {
                                jSONArray2 = jSONArrayA;
                            } catch (JSONException unused4) {
                                jSONArray2 = jSONArrayA;
                            }
                            i3++;
                            jSONArrayA = jSONArray2;
                        }
                    } else {
                        i0.b("SpeakerProvider", "language[" + strB + "] has no speaker");
                    }
                } catch (JSONException unused5) {
                    i0.b("SpeakerProvider", "JSONArray parsing failed. in  " + i2);
                }
                i2++;
                jSONArray3 = jSONArray;
            }
            return hashSet;
        }
        i0.b("SpeakerProvider", "Response json not contain languages");
        return hashSet;
    }

    private Set<MLTtsSpeaker> a(JSONArray jSONArray) {
        HashSet hashSet = new HashSet();
        if (jSONArray != null && jSONArray.length() != 0) {
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                try {
                    JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                    hashSet.add(new MLTtsSpeaker(g0.b(jSONObject, "language"), g0.b(jSONObject, "modelName"), g0.b(jSONObject, "modelSize"), ""));
                } catch (JSONException unused) {
                    i0.b("SpeakerProvider", "JSONObject parsing failed. in  " + i2);
                }
            }
            return hashSet;
        }
        i0.b("SpeakerProvider", "Response json not contain data");
        return hashSet;
    }

    static /* synthetic */ String a(b bVar) {
        return bVar.e.getString("tts_offline_speaker_json", "");
    }

    public int a(String str) {
        JSONArray jSONArrayB;
        if (k0.a.containsKey(str)) {
            str = k0.a.get(str);
        }
        String strF = f();
        if (!TextUtils.isEmpty(strF)) {
            jSONArrayB = g0.b(strF);
        } else {
            jSONArrayB = g0.b("[{\"speakers\":[{\"codec\":\"4\",\"name\":\"tr-TR-st-1\",\"desc\":\"土耳其语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"tr-TR\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ms-MS-st-1\",\"desc\":\"马来语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ms-MS\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"pl-PL-st-1\",\"desc\":\"波兰语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"pl-PL\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"th-TH-st-1\",\"desc\":\"泰语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"th-TH\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ru-RU-st-1\",\"desc\":\"俄罗斯语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ru-RU\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"ar-AR-st-1\",\"desc\":\"阿拉伯语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"ar-AR\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"it-IT-st-1\",\"desc\":\"意大利语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"it-IT\"},{\"speakers\":[{\"codec\":\"1,3\",\"name\":\"zh-Hans-st-2\",\"desc\":\"中文标准男声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-st-1\",\"desc\":\"中文标准女声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-hq-2\",\"desc\":\"中文高音质男声\",\"sampleRate\":\"22050\"},{\"codec\":\"1,3\",\"name\":\"zh-Hans-hq-1\",\"desc\":\"中文高音质女声\",\"sampleRate\":\"22050\"}],\"language\":\"zh-Hans\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"de-DE-st-1\",\"desc\":\"德语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"de-DE\"},{\"speakers\":[{\"codec\":\"1,3\",\"name\":\"en-US-st-1\",\"desc\":\"美英标准女声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"en-US-st-2\",\"desc\":\"美英标准男声\",\"sampleRate\":\"16000\"},{\"codec\":\"1,3\",\"name\":\"en-US-hq-1\",\"desc\":\"英文高音质女声\",\"sampleRate\":\"22050\"},{\"codec\":\"1,3\",\"name\":\"en-US-hq-2\",\"desc\":\"英文高音质男声\",\"sampleRate\":\"22050\"}],\"language\":\"en-US\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"es-ES-st-1\",\"desc\":\"西班牙标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"es-ES\"},{\"speakers\":[{\"codec\":\"4\",\"name\":\"fr-FR-st-1\",\"desc\":\"法语标准女声\",\"sampleRate\":\"16000\"}],\"language\":\"fr-FR\"}]");
        }
        if (!g && jSONArrayB == null) {
            throw new AssertionError();
        }
        Iterator it = ((List) new Gson().fromJson(jSONArrayB.toString(), new C0124b(this).getType())).iterator();
        int i2 = -1;
        while (it.hasNext()) {
            for (SpeakerInfo speakerInfo : ((LanguageInfo) it.next()).getSpeakers()) {
                if (speakerInfo.getName().equals(str)) {
                    String sampleRate = speakerInfo.getSampleRate();
                    if (!TextUtils.isEmpty(sampleRate)) {
                        i2 = Integer.parseInt(sampleRate);
                        break;
                    }
                    break;
                }
            }
        }
        if (i2 == -1) {
            i0.c("SpeakerProvider", str + "not found, sampleRate is -1");
            i2 = MLTtsAudioFragment.SAMPLE_RATE_16K;
        }
        i0.c("SpeakerProvider", str + " sampleRate is " + i2);
        return i2;
    }
}
