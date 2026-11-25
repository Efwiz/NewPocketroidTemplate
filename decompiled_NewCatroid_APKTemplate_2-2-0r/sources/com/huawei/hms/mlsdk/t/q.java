package com.huawei.hms.mlsdk.t;

import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: RequestTtsConfig.java */
/* JADX INFO: loaded from: classes10.dex */
public class q implements p {
    private String a = MLTtsConstants.TTS_SPEAKER_FEMALE_ZH_1;
    private int b = 1;
    private int c = MLTtsAudioFragment.SAMPLE_RATE_16K;
    private float d;
    private float e;
    private float f;

    public q a(int i) {
        if (i != 9) {
            this.b = 1;
        } else {
            this.b = 3;
        }
        return this;
    }

    public q b(float f) {
        this.d = f;
        return this;
    }

    public q c(float f) {
        this.e = f;
        return this;
    }

    public String toString() {
        return a.a("TtsConfig{person=").append(this.a).append(", codec=").append(this.b).append(", sampleRate=").append(this.c).append(", speed=").append(this.d).append(", volume=").append(this.e).append(", pitch=").append(this.f).append('}').toString();
    }

    public q b(int i) {
        this.c = i;
        return this;
    }

    public q a(String str) {
        this.a = str;
        return this;
    }

    public q a(float f) {
        this.f = f;
        return this;
    }

    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String str = this.a;
        if (str == null) {
            str = "";
        }
        jSONObject.put("person", str);
        jSONObject.put("codec", this.b);
        jSONObject.put("sampleRate", this.c);
        jSONObject.put("speed", String.valueOf(this.d));
        jSONObject.put("volume", String.valueOf(this.e));
        jSONObject.put("pitch", String.valueOf(this.f));
        return jSONObject;
    }
}
