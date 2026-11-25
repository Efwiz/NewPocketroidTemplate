package com.huawei.hms.mlsdk.tts;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.artemis.WorldConfigurationBuilder;
import com.huawei.hms.mlsdk.t.b;
import com.huawei.hms.mlsdk.t.e;
import com.huawei.hms.mlsdk.t.i0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes10.dex */
public class MLTtsEngine {
    public static final int EXTERNAL_PLAYBACK = 2;
    public static final int OPEN_STREAM = 4;
    public static final int QUEUE_APPEND = 0;
    public static final int QUEUE_FLUSH = 1;
    private static final String TAG = "MLTtsEngine";
    private e.InterfaceC0123e callback = new e.InterfaceC0123e() { // from class: com.huawei.hms.mlsdk.tts.MLTtsEngine.1
        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchAudioAvailable(String str, MLTtsAudioFragment mLTtsAudioFragment, int i, Pair<Integer, Integer> pair, Bundle bundle) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null || mLTtsAudioFragment == null || mLTtsAudioFragment.getAudioData() == null || pair == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onAudioAvailable, data[" + mLTtsAudioFragment.getAudioData().length + "], offset[" + i + "], textStart[" + pair.first + "], textEnd[" + pair.second + "]");
            MLTtsEngine.this.mOnProgressListener.onAudioAvailable(str, mLTtsAudioFragment, i, pair, bundle);
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchError(String str, MLTtsError mLTtsError) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchError[" + mLTtsError.getErrorId() + "] ;");
            MLTtsEngine.this.mOnProgressListener.onError(str, mLTtsError);
            Bundle bundle = new Bundle();
            bundle.putString("errMsg", mLTtsError.toString());
            b.b().a(str, 6, bundle);
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchPause(String str) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchPause;");
            MLTtsEngine.this.mOnProgressListener.onEvent(str, 3, null);
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchRangeStart(String str, int i, int i2) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchRangestart[" + i + "," + i2 + "];");
            MLTtsEngine.this.mOnProgressListener.onRangeStart(str, i, i2);
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchResume(String str) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchResume;");
            MLTtsEngine.this.mOnProgressListener.onEvent(str, 2, null);
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchStart(String str) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchStart;");
            MLTtsEngine.this.mOnProgressListener.onEvent(str, 1, null);
            b.b().a(str, 3, new Bundle());
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchStop(String str, boolean z) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("interrupted", z);
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchStop; interrupted[" + z + "]");
            MLTtsEngine.this.mOnProgressListener.onEvent(str, 4, bundle);
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchSynthesisComplete(String str, boolean z) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("interrupted", z);
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchSynthesisComplete; interrupted[" + z + "]");
            MLTtsEngine.this.mOnProgressListener.onEvent(str, 7, bundle);
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("result", !z);
            b.b().a(str, 5, bundle2);
            b.b().b(str);
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchSynthesisEnd(String str) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchSynthesisEnd");
            MLTtsEngine.this.mOnProgressListener.onEvent(str, 6, null);
            b.b().a(str, 2, new Bundle());
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchSynthesisStart(String str) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchSynthesisStart");
            MLTtsEngine.this.mOnProgressListener.onEvent(str, 5, null);
            b.b().a(str, 1, new Bundle());
        }

        @Override // com.huawei.hms.mlsdk.t.e.InterfaceC0123e
        public void onDispatchWarn(String str, MLTtsWarn mLTtsWarn) {
            if (MLTtsEngine.this.mOnProgressListener == null || str == null) {
                return;
            }
            i0.c(MLTtsEngine.TAG, "CALLBACK:[" + str + "] onDispatchWarn[" + mLTtsWarn + "];");
            MLTtsEngine.this.mOnProgressListener.onWarn(str, mLTtsWarn);
        }
    };
    private MLTtsCallback mOnProgressListener;
    private e synthesisController;
    private String ttsMode;

    public MLTtsEngine(MLTtsConfig mLTtsConfig) {
        i0.c(TAG, "API:create instance [" + this + "] Version:[3.7.0.303]");
        this.ttsMode = mLTtsConfig.getSynthesizeMode();
        if (isOfflineMode()) {
            this.synthesisController = new e(this.callback, mLTtsConfig, isOfflineMode());
        } else {
            this.synthesisController = new e(this.callback, mLTtsConfig);
        }
        startup();
    }

    private boolean isOfflineMode() {
        return TextUtils.equals(this.ttsMode, MLTtsConstants.TTS_OFFLINE_MODE);
    }

    private void startup() {
        this.synthesisController.f();
    }

    public List<String> getHqLanguages() {
        List<MLTtsSpeaker> hqSpeakers = getHqSpeakers();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (hqSpeakers.size() > 0) {
            Iterator<MLTtsSpeaker> it = hqSpeakers.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(it.next().getLanguage());
            }
        }
        return new ArrayList(linkedHashSet);
    }

    public List<MLTtsSpeaker> getHqSpeaker(String str) {
        List<MLTtsSpeaker> hqSpeakers = getHqSpeakers();
        ArrayList arrayList = new ArrayList();
        if (!hqSpeakers.isEmpty() && !TextUtils.isEmpty(str)) {
            List<String> hqLanguages = getHqLanguages();
            if (!hqLanguages.isEmpty() && hqLanguages.contains(str)) {
                for (MLTtsSpeaker mLTtsSpeaker : hqSpeakers) {
                    if (TextUtils.equals(str, mLTtsSpeaker.getLanguage())) {
                        arrayList.add(mLTtsSpeaker);
                    }
                }
            }
        }
        return arrayList;
    }

    public List<MLTtsSpeaker> getHqSpeakers() {
        return new ArrayList(this.synthesisController.a());
    }

    public List<String> getLanguages() {
        List<MLTtsSpeaker> speakers = getSpeakers();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (speakers.size() > 0) {
            for (MLTtsSpeaker mLTtsSpeaker : speakers) {
                boolean zIsOfflineMode = isOfflineMode();
                String language = mLTtsSpeaker.getLanguage();
                if (zIsOfflineMode) {
                    language = language.toLowerCase(Locale.ENGLISH);
                }
                linkedHashSet.add(language);
            }
        }
        return new ArrayList(linkedHashSet);
    }

    public List<MLTtsSpeaker> getSpeaker(String str) {
        if (isOfflineMode()) {
            str = str.toLowerCase(Locale.ENGLISH);
        }
        List<MLTtsSpeaker> speakers = getSpeakers();
        ArrayList arrayList = new ArrayList();
        if (!speakers.isEmpty() && !TextUtils.isEmpty(str)) {
            List<String> languages = getLanguages();
            if (!languages.isEmpty() && languages.contains(str)) {
                for (MLTtsSpeaker mLTtsSpeaker : speakers) {
                    if (TextUtils.equals(str, isOfflineMode() ? mLTtsSpeaker.getLanguage().toLowerCase(Locale.ENGLISH) : mLTtsSpeaker.getLanguage())) {
                        arrayList.add(mLTtsSpeaker);
                    }
                }
            }
        }
        return arrayList;
    }

    public List<MLTtsSpeaker> getSpeakers() {
        return new ArrayList(this.synthesisController.b());
    }

    public int isLanguageAvailable(String str) {
        return this.synthesisController.a(str);
    }

    public void pause() {
        i0.c(TAG, "[" + this + "] API:pause");
        this.synthesisController.c();
    }

    public void resume() {
        i0.c(TAG, "[" + this + "] API:resume");
        this.synthesisController.d();
    }

    public void setPlayerVolume(int i) {
        int i2;
        if (i <= 0) {
            i2 = WorldConfigurationBuilder.Priority.LOW;
        } else {
            i2 = i >= 100 ? 10000 : i * 100;
        }
        i0.c(TAG, "AudioTrack Volume:" + i + "分贝(mB)");
        e eVar = this.synthesisController;
        if (eVar != null) {
            eVar.a(i2);
        }
    }

    public void setTtsCallback(MLTtsCallback mLTtsCallback) {
        this.mOnProgressListener = mLTtsCallback;
    }

    public void shutdown() {
        i0.c(TAG, "[" + this + "] API:shutdown");
        this.synthesisController.e();
    }

    public String speak(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            this.mOnProgressListener.onError("", new MLTtsError.Builder().setErrorId(MLTtsError.ERR_ILLEGAL_PARAMETER).setErrorMsg("please input correct text").build());
            return "";
        }
        i0.c(TAG, "[" + this + "] API:speak text[" + str.length() + "] | Mode[" + i + "]");
        String strA = this.synthesisController.a(str, i);
        i0.c(TAG, "[" + this + "] API:speak return ID [" + strA + "]");
        return strA;
    }

    public void stop() {
        i0.c(TAG, "[" + this + "] API:stop");
        this.synthesisController.g();
    }

    public void updateConfig(MLTtsConfig mLTtsConfig) {
        i0.c(TAG, "[" + this + "] API:updateConfig " + mLTtsConfig);
        this.synthesisController.a(mLTtsConfig);
    }
}
