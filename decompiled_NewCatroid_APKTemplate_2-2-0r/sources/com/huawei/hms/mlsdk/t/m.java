package com.huawei.hms.mlsdk.t;

import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsWarn;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONException;

/* JADX INFO: compiled from: SynthesisEngineOnline.java */
/* JADX INFO: loaded from: classes10.dex */
public class m extends i {
    private r e;
    private final Map<String, OkHttpClient> f = new HashMap();
    private q d = new q();

    /* JADX INFO: compiled from: SynthesisEngineOnline.java */
    class a implements Runnable {
        private String a;
        private h b;
        private t c;
        private OkHttpClient d;

        public a(OkHttpClient okHttpClient, h hVar, t tVar) {
            this.d = okHttpClient;
            this.b = hVar;
            this.a = hVar.c();
            this.c = tVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (m.this.a(this.a)) {
                return;
            }
            m.this.a(this.d, this.b, this.c);
            synchronized (m.this.f) {
                if (m.this.f.containsKey(this.a)) {
                    if (m.this.b != null) {
                        m.this.b.b(this.a);
                    }
                    l.b.a.a((OkHttpClient) m.this.f.get(this.a));
                    m.this.f.remove(this.a);
                }
            }
        }
    }

    public m() {
        this.a = new com.huawei.hms.mlsdk.tts.engine.common.a().c(MLTtsConstants.TTS_SPEAKER_FEMALE_ZH).a(1.0f).b(MLTtsAudioFragment.SAMPLE_RATE_16K).b(1.0f).c(1.0f);
    }

    public void b() {
        i0.c("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("stopSpeechSynthesis taskRecording:").append(this.f.size()).toString());
        synchronized (this.f) {
            for (Map.Entry<String, OkHttpClient> entry : this.f.entrySet()) {
                this.c.add(entry.getKey());
                l.b.a.a(entry.getValue());
                if (this.b != null) {
                    this.b.b(entry.getKey());
                }
            }
            this.f.clear();
        }
    }

    public void a(com.huawei.hms.mlsdk.tts.engine.common.a aVar) {
        this.a = aVar;
        this.d.a(this.a.d()).a(this.a.e()).b(this.a.g()).c(this.a.h()).a(this.a.a()).b(this.a.f());
    }

    public com.huawei.hms.mlsdk.tts.engine.common.a a() {
        return this.a;
    }

    public void a(h hVar) {
        i0.a("SynthesisEngine", "startSpeechSynthesis|event=" + hVar);
        k kVar = this.b;
        if (kVar != null) {
            kVar.a(hVar.c());
        }
        com.huawei.hms.mlsdk.tts.engine.common.a aVar = this.a;
        aVar.a(g0.a(aVar.d()));
        this.d.a(this.a.a());
        this.e = new r(hVar.d(), hVar.a());
        t tVar = new t(this.e, this.d);
        OkHttpClient okHttpClientB = l.b.a.b();
        synchronized (this.f) {
            this.f.put(hVar.c(), okHttpClientB);
        }
        j0.b().a(new a(okHttpClientB, hVar, tVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:111:0x02ee  */
    /* JADX WARN: Code duplicated, block: B:112:0x02f0  */
    public void a(OkHttpClient okHttpClient, h hVar, t tVar) {
        n nVarA;
        int i;
        int i2;
        u uVar;
        byte[] bArr;
        byte[] bArr2;
        String strC = hVar.c();
        try {
            String string = tVar.a().toString();
            i0.a("SynthesisEngine", "requestJson:" + string);
            if (TextUtils.isEmpty(string)) {
                i0.b("SynthesisEngine", "RequestJson is empty");
                a(strC, 103, "input is empty");
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (hVar.e()) {
                nVarA = l.b.a.a(okHttpClient, "/v1/tts/text2audio", string);
            } else {
                synchronized (this) {
                    nVarA = l.b.a.a(okHttpClient, "/v1/tts/text2audio", string);
                }
            }
            com.huawei.hms.mlsdk.t.a.a("Network request duration:").append(System.currentTimeMillis() - jCurrentTimeMillis).toString();
            Response responseC = nVarA.c();
            int i3 = 2;
            if (responseC == null) {
                i0.b("SynthesisEngine", "Speech synthesis request failed!");
                int iA = nVarA.a();
                if (iA == 1) {
                    a(strC, 198, com.huawei.hms.mlsdk.t.a.a("Failed to receive streams.Reason: ").append(nVarA.b()).toString());
                    return;
                }
                if (iA == 2) {
                    a(strC, 198, "the call has already been executed");
                    return;
                }
                if (iA == 3) {
                    a(strC, 104, "The network is unavailable.");
                    return;
                } else if (iA != 4) {
                    a(strC, 105, "network no response");
                    return;
                } else {
                    a(strC, 198, "Socket connection timed out.");
                    return;
                }
            }
            if (responseC.isSuccessful()) {
                o oVar = new o(responseC);
                if (oVar.b()) {
                    String strC2 = hVar.c();
                    boolean z = false;
                    int i4 = -1;
                    boolean z2 = false;
                    int i5 = 0;
                    int i6 = 0;
                    boolean z3 = false;
                    int i7 = 0;
                    u uVar2 = null;
                    byte[] bArr3 = null;
                    while (!z2) {
                        try {
                            if (a(strC2)) {
                                break;
                            }
                            int i8 = i4 + 1;
                            if (i8 == 0) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("playExter", hVar.f());
                                a(hVar.c(), 0, bundle);
                            }
                            u uVarC = oVar.c();
                            if (uVarC.a() == 0 && uVarC.d() == 0) {
                                if (uVar2 != null && uVarC.f() == uVar2.f() && uVarC.e() == uVar2.e()) {
                                    uVar = uVar2;
                                    i = i5;
                                    i2 = i6;
                                } else {
                                    int iF = uVarC.f();
                                    int iF2 = uVarC.f() + uVarC.e();
                                    i0.c("SynthesisEngine", "TtsResponseHeader start=" + uVarC.f() + " len = " + uVarC.e());
                                    if (!hVar.f()) {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putInt("startIndex", iF);
                                        bundle2.putInt("endIndex", iF2);
                                        bundle2.putBoolean("playExter", hVar.f());
                                        a(strC2, i3, bundle2);
                                    }
                                    i = iF;
                                    i2 = iF2;
                                    uVar = uVarC;
                                }
                                int iA2 = this.a.a();
                                if (iA2 == i3) {
                                    bArr = bArr3;
                                    if (i8 == 0) {
                                        int i9 = 2000;
                                        int iC = uVarC.c() / 2000;
                                        int iC2 = uVarC.c() % 2000;
                                        int i10 = 0;
                                        while (i10 < iC) {
                                            a(hVar, oVar.a(i9), 2, i, i2, i8);
                                            i8++;
                                            i10++;
                                            iC2 = iC2;
                                            iC = iC;
                                            i9 = i9;
                                        }
                                        a(hVar, oVar.a(iC2), 2, i, i2, i8);
                                    } else {
                                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                                        byte[] bArrA = oVar.a(uVarC.c());
                                        long jCurrentTimeMillis3 = System.currentTimeMillis();
                                        if (!z3) {
                                            long j = jCurrentTimeMillis3 - jCurrentTimeMillis2;
                                            int length = (bArrA.length * 1000) / 32000;
                                            long j2 = length;
                                            if (j2 < j) {
                                                i0.d("SynthesisEngine", "The current bandwidth is insufficient.audioDuration[" + length + "],requestDuration[" + j + "]");
                                            }
                                            if (j2 < j) {
                                                if (this.b != null) {
                                                    this.b.b(strC2, MLTtsWarn.WARN_INSUFFICIENT_BANDWIDTH, "Insufficient network bandwidth");
                                                }
                                                z3 = true;
                                            }
                                        }
                                        a(hVar, bArrA, 2, i, i2, i8);
                                    }
                                } else if (iA2 != 9) {
                                    bArr = bArr3;
                                } else if (g0.c(hVar.b())) {
                                    byte[] bArr4 = bArr3;
                                    if (i7 == 0) {
                                        i7++;
                                        bArr = new byte[432];
                                        byte[] bArrA2 = oVar.a(uVarC.c());
                                        if (bArrA2.length > 432) {
                                            System.arraycopy(bArrA2, bArrA2.length - 432, bArr, 0, 432);
                                        }
                                        a(hVar, bArrA2, 9, i, i2, i8);
                                    } else {
                                        byte[] bArrA3 = oVar.a(uVarC.c());
                                        if (bArr4 == null || bArrA3 == null) {
                                            bArr2 = null;
                                        } else {
                                            bArr2 = new byte[bArr4.length + bArrA3.length];
                                            System.arraycopy(bArr4, 0, bArr2, 0, bArr4.length);
                                            System.arraycopy(bArrA3, 0, bArr2, bArr4.length, bArrA3.length);
                                        }
                                        bArr = new byte[432];
                                        if (bArrA3.length > 432) {
                                            System.arraycopy(bArrA3, bArrA3.length - 432, bArr, 0, 432);
                                        }
                                        a(hVar, bArr2, 9, i, i2, i8);
                                        if (uVarC.b() == 0) {
                                            i7 = 0;
                                            i4 = i8;
                                            bArr3 = null;
                                        }
                                        if (uVarC.b() == 0) {
                                            z2 = true;
                                        } else {
                                            z2 = false;
                                        }
                                        String str = "Has more segment? " + (!z2);
                                        uVar2 = uVar;
                                        i5 = i;
                                        i6 = i2;
                                    }
                                } else {
                                    bArr = bArr3;
                                    a(hVar, oVar.a(uVarC.c()), 9, i, i2, i8);
                                }
                                bArr3 = bArr;
                                i4 = i8;
                                if (uVarC.b() == 0) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                String str2 = "Has more segment? " + (!z2);
                                uVar2 = uVar;
                                i5 = i;
                                i6 = i2;
                            } else {
                                byte[] bArr5 = bArr3;
                                i0.b("SynthesisEngine", "Error happened during processing this request: getErrCode=" + uVarC.a() + " getReplyType = " + uVarC.d());
                                a(strC2, 102, "An audio segment is abnormal.");
                                bArr3 = bArr5;
                                z2 = uVarC.b() == 0;
                                i4 = i8;
                            }
                            i3 = 2;
                        } catch (IOException e) {
                            i0.b("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("while getting the buffer, catch an exception: ").append(e.getClass().getSimpleName()).append(" ").append(e.getMessage()).toString());
                            a(strC2, 100, "An I/O exception occurred when parsing audio streams.");
                        }
                    }
                    if (!a(strC2) || z2) {
                        z = true;
                    }
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("interrupted", !z);
                    bundle3.putBoolean("playExter", hVar.f());
                    a(hVar.c(), 1, bundle3);
                } else if (!a(hVar, oVar)) {
                    a(hVar.c(), 199, "Unknown Error.");
                }
            } else if (!a(hVar, new o(responseC))) {
                i0.b("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("HTTP ERROR:").append(responseC.code()).toString());
                a(hVar.c(), responseC.code(), "http error.");
            }
            responseC.close();
        } catch (JSONException unused) {
            a(strC, 103, "get Json string wrong");
        }
    }

    private boolean a(h hVar, o oVar) {
        String strC = hVar.c();
        try {
            s sVarA = oVar.a();
            i0.a("SynthesisEngine", "TtsErrorResponse is " + sVarA);
            if (sVarA != null) {
                i0.b("SynthesisEngine", "Cloud error code = " + sVarA.a());
                a(strC, Integer.parseInt(sVarA.a()), sVarA.b());
                return true;
            }
            i0.b("SynthesisEngine", "Unresolvable error.");
            return false;
        } catch (IOException e) {
            i0.b("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("getErrResponse failed").append(e.getMessage()).toString());
            return false;
        } catch (NumberFormatException unused) {
            i0.b("SynthesisEngine", "The error code returned by the cloud cannot be identified.");
            return false;
        }
    }

    private void a(String str, int i, String str2) {
        if (this.b == null) {
            i0.d("SynthesisEngine", "sendErrorResult listener has been clear");
        } else {
            if (a(str)) {
                return;
            }
            this.b.a(str, i, str2);
        }
    }
}
