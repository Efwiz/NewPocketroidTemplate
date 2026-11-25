package com.huawei.hms.mlsdk.t;

import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: TtsConstants.java */
/* JADX INFO: loaded from: classes10.dex */
public class k0 {
    public static final Map<String, String> a;

    static {
        HashMap map = new HashMap();
        map.put(MLTtsConstants.TTS_SPEAKER_FEMALE_EN, MLTtsConstants.TTS_SPEAKER_FEMALE_EN_1);
        map.put(MLTtsConstants.TTS_SPEAKER_FEMALE_ZH, MLTtsConstants.TTS_SPEAKER_FEMALE_ZH_1);
        map.put(MLTtsConstants.TTS_SPEAKER_MALE_EN, MLTtsConstants.TTS_SPEAKER_MALE_EN_1);
        map.put(MLTtsConstants.TTS_SPEAKER_MALE_ZH, MLTtsConstants.TTS_SPEAKER_MALE_ZH_1);
        a = Collections.unmodifiableMap(map);
    }
}
