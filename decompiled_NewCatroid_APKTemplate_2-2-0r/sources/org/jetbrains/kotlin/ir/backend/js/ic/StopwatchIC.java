package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: ICUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000e\u001a\u00020\u000fJ-\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u00112\u0006\u0010\u0012\u001a\u00020\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0014H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0004J\u0017\u0010\u0017\u001a\u00020\u000f2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0019R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\t0\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\t0\rX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/StopwatchIC;", "", "()V", "lapDescription", "", "lapStart", "", "laps", "", "Lkotlin/Pair;", "getLaps", "()Ljava/util/List;", "lapsImpl", "", LineReader.CLEAR, "", "measure", ExifInterface.GPS_DIRECTION_TRUE, MediaTrack.ROLE_DESCRIPTION, "f", "Lkotlin/Function0;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "startNext", "stop", "stopTime", "(Ljava/lang/Long;)V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StopwatchIC {
    private String lapDescription;
    private long lapStart;
    private final List<Pair<String, Long>> lapsImpl = new ArrayList();

    public final List<Pair<String, Long>> getLaps() {
        return this.lapsImpl;
    }

    public final void clear() {
        this.lapStart = 0L;
        this.lapDescription = null;
        this.lapsImpl.clear();
    }

    public final void startNext(String description) {
        Intrinsics.checkNotNullParameter(description, "description");
        long jNanoTime = System.nanoTime();
        stop(Long.valueOf(jNanoTime));
        this.lapDescription = description;
        this.lapStart = jNanoTime;
    }

    public static /* synthetic */ void stop$default(StopwatchIC stopwatchIC, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            l = null;
        }
        stopwatchIC.stop(l);
    }

    public final void stop(Long stopTime) {
        String str = this.lapDescription;
        if (str != null) {
            this.lapsImpl.add(TuplesKt.to(str, Long.valueOf((stopTime != null ? stopTime.longValue() : System.nanoTime()) - this.lapStart)));
        }
        this.lapDescription = null;
    }

    public final <T> T measure(String description, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(f, "f");
        startNext(description);
        T tInvoke = f.invoke();
        stop$default(this, null, 1, null);
        return tInvoke;
    }
}
