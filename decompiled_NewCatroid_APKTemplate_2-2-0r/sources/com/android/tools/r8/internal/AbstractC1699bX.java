package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1699bX {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Spliterator a(Spliterator spliterator) {
        return spliterator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Stream[] streamArr) {
        for (Stream stream : streamArr) {
            stream.close();
        }
    }

    public static Stream a(Iterable iterable) {
        return iterable instanceof Collection ? ((Collection) iterable).stream() : StreamSupport.stream(iterable.spliterator(), false);
    }

    public static Stream a(final Stream... streamArr) {
        C1507Xl c1507Xl = new C1507Xl(streamArr.length);
        int iCharacteristics = 336;
        boolean zIsParallel = false;
        long j = 0;
        for (Stream stream : streamArr) {
            zIsParallel |= stream.isParallel();
            Spliterator spliterator = stream.spliterator();
            c1507Xl.a(spliterator);
            iCharacteristics &= spliterator.characteristics();
            long jEstimateSize = spliterator.estimateSize();
            long j2 = j + jEstimateSize;
            j = (((jEstimateSize ^ j) > 0L ? 1 : ((jEstimateSize ^ j) == 0L ? 0 : -1)) < 0) | ((j ^ j2) >= 0) ? j2 : ((j2 >>> 63) ^ 1) + Long.MAX_VALUE;
        }
        return (Stream) StreamSupport.stream(AbstractC3142ub.a(c1507Xl.a().spliterator(), new Function() { // from class: com.android.tools.r8.internal.bX$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC1699bX.a((Spliterator) obj);
            }
        }, iCharacteristics, j), zIsParallel).onClose(new Runnable() { // from class: com.android.tools.r8.internal.bX$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AbstractC1699bX.b(streamArr);
            }
        });
    }

    public static Stream a(Stream stream, Stream stream2, BiFunction biFunction) {
        int i = AbstractC3123uM.a;
        stream.getClass();
        stream2.getClass();
        biFunction.getClass();
        boolean z = stream.isParallel() || stream2.isParallel();
        Spliterator spliterator = stream.spliterator();
        Spliterator spliterator2 = stream2.spliterator();
        return (Stream) ((Stream) StreamSupport.stream(new WW(Math.min(spliterator.estimateSize(), spliterator2.estimateSize()), spliterator.characteristics() & spliterator2.characteristics() & 80, Spliterators.iterator(spliterator), Spliterators.iterator(spliterator2), biFunction), z).onClose(new bX$$ExternalSyntheticLambda2(stream))).onClose(new bX$$ExternalSyntheticLambda2(stream2));
    }

    public static Stream a(Stream stream, ZW zw) {
        int i = AbstractC3123uM.a;
        stream.getClass();
        zw.getClass();
        boolean zIsParallel = stream.isParallel();
        Spliterator spliterator = stream.spliterator();
        if (!spliterator.hasCharacteristics(16384)) {
            return (Stream) StreamSupport.stream(new YW(spliterator.estimateSize(), spliterator.characteristics() & 80, Spliterators.iterator(spliterator), zw), zIsParallel).onClose(new bX$$ExternalSyntheticLambda2(stream));
        }
        return (Stream) StreamSupport.stream(new XW(spliterator, 0L, zw), zIsParallel).onClose(new bX$$ExternalSyntheticLambda2(stream));
    }
}
