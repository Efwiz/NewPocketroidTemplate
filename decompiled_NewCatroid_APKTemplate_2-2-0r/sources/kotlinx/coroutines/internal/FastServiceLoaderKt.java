package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/internal/FastServiceLoaderKt.class
  classes12.dex
 */
/* JADX INFO: compiled from: FastServiceLoader.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"ANDROID_DETECTED", "", "getANDROID_DETECTED", "()Z", "kotlinx-coroutines-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class FastServiceLoaderKt {
    private static final boolean ANDROID_DETECTED;

    static {
        Object objM2898constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(Class.forName("android.os.Build"));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        ANDROID_DETECTED = Result.m2905isSuccessimpl(objM2898constructorimpl);
    }

    public static final boolean getANDROID_DETECTED() {
        return ANDROID_DETECTED;
    }
}
