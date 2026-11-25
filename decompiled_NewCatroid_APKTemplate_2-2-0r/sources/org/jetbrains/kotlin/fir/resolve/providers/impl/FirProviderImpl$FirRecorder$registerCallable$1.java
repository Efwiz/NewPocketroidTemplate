package org.jetbrains.kotlin.fir.resolve.providers.impl;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [S] */
/* JADX INFO: compiled from: FirProviderImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u0004\"\u000e\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\n¢\u0006\u0002\b\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.LATITUDE_SOUTH, "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirProviderImpl$FirRecorder$registerCallable$1<S> extends Lambda implements Function2<List<? extends S>, List<? extends S>, List<? extends S>> {
    public static final FirProviderImpl$FirRecorder$registerCallable$1 INSTANCE = new FirProviderImpl$FirRecorder$registerCallable$1();

    public FirProviderImpl$FirRecorder$registerCallable$1() {
        super(2);
    }

    @Override // kotlin.jvm.functions.Function2
    public final List<S> invoke(List<? extends S> a, List<? extends S> b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return CollectionsKt.plus((Collection) a, (Iterable) b);
    }
}
