package org.jetbrains.kotlin.fir.builder;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;

/* JADX INFO: compiled from: FirBuilderDsl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010!\n\u0000\u001a%\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007¢\u0006\u0004\b\u0004\u0010\u0005\u001a!\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006¢\u0006\u0002\u0010\u0005¨\u0006\u0007"}, d2 = {"toMutableOrEmpty", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", ExifInterface.GPS_DIRECTION_TRUE, "", "toMutableOrEmptyForImmutable", "(Ljava/util/List;)Ljava/util/List;", "", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirBuilderDslKt {
    public static final <T> List<T> toMutableOrEmpty(List<T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return list.isEmpty() ? MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI() : MutableOrEmptyList.m6758constructorimpl(list);
    }

    public static final <T> List<T> toMutableOrEmptyForImmutable(List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return list.isEmpty() ? MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI() : MutableOrEmptyList.m6758constructorimpl(CollectionsKt.toMutableList((Collection) list));
    }
}
