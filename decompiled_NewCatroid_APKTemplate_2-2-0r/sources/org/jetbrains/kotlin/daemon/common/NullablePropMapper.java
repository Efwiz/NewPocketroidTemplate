package org.jetbrains.kotlin.daemon.common;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty1;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0016\b\u0002\u0010\u0003 \u0001*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005Bs\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0002\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00010\f\u0012\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00028\u0001\u0012\u0006\u0012\u0004\u0018\u00010\n0\f\u0012\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000f0\f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/NullablePropMapper;", "C", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "P", "Lkotlin/reflect/KMutableProperty1;", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "dest", "prop", "names", "", "", "fromString", "Lkotlin/Function1;", "toString", "skipIf", "", "mergeDelimiter", "(Ljava/lang/Object;Lkotlin/reflect/KMutableProperty1;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Ljava/lang/String;)V", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullablePropMapper<C, V, P extends KMutableProperty1<C, V>> extends PropMapper<C, V, P> {
    public /* synthetic */ NullablePropMapper(Object obj, KMutableProperty1 kMutableProperty1, List list, Function1 function1, Function1 function2, Function1 function3, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, kMutableProperty1, (i & 4) != 0 ? CollectionsKt.emptyList() : list, function1, (i & 16) != 0 ? new Function1<V, String>() { // from class: org.jetbrains.kotlin.daemon.common.NullablePropMapper.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(V v) {
                return String.valueOf(v);
            }
        } : function2, (i & 32) != 0 ? new Function1<V, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.NullablePropMapper.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(V v) {
                return Boolean.valueOf(v == null);
            }
        } : function3, (i & 64) != 0 ? null : str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NullablePropMapper(C c, P prop, List<String> names, Function1<? super String, ? extends V> fromString, Function1<? super V, String> toString, Function1<? super V, Boolean> skipIf, String str) {
        super(c, prop, CollectionsKt.any(names) ? names : CollectionsKt.listOf(prop.getName()), fromString, toString, skipIf, str);
        Intrinsics.checkNotNullParameter(prop, "prop");
        Intrinsics.checkNotNullParameter(names, "names");
        Intrinsics.checkNotNullParameter(fromString, "fromString");
        Intrinsics.checkNotNullParameter(toString, "toString");
        Intrinsics.checkNotNullParameter(skipIf, "skipIf");
    }
}
