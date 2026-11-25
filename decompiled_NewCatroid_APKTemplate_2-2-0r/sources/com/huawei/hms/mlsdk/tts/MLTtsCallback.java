package com.huawei.hms.mlsdk.tts;

import android.os.Bundle;
import android.util.Pair;

/* JADX INFO: loaded from: classes10.dex */
public interface MLTtsCallback {
    void onAudioAvailable(String str, MLTtsAudioFragment mLTtsAudioFragment, int i, Pair<Integer, Integer> pair, Bundle bundle);

    void onError(String str, MLTtsError mLTtsError);

    void onEvent(String str, int i, Bundle bundle);

    void onRangeStart(String str, int i, int i2);

    void onWarn(String str, MLTtsWarn mLTtsWarn);
}
