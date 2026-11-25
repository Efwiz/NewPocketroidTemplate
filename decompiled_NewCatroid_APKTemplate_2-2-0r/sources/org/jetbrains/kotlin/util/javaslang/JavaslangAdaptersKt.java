package org.jetbrains.kotlin.util.javaslang;

import androidx.exifinterface.media.ExifInterface;
import javaslang.Tuple2;
import javaslang.collection.Map;
import javaslang.control.Option;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: javaslangAdapters.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\f\u0012\u0004\u0012\u0002H\u0001\u0012\u0002\b\u00030\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0003\u001a\"\u0010\u0004\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0003\u001a\u001d\u0010\u0005\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0006¢\u0006\u0002\u0010\u0007\u001aA\u0010\u0005\u001a\u0004\u0018\u0001H\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\b*\u001e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\nj\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b`\u000b2\u0006\u0010\f\u001a\u0002H\t¢\u0006\u0002\u0010\r*.\u0010\u000e\u001a\u0004\b\u0000\u0010\t\u001a\u0004\b\u0001\u0010\b\"\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\u000f2\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\u000f*\u001c\u0010\u0010\u001a\u0004\b\u0000\u0010\u0011\"\b\u0012\u0004\u0012\u0002H\u00110\u00122\b\u0012\u0004\u0012\u0002H\u00110\u0012*\u001c\u0010\u0013\u001a\u0004\b\u0000\u0010\u0011\"\b\u0012\u0004\u0012\u0002H\u00110\u00142\b\u0012\u0004\u0012\u0002H\u00110\u0014*.\u0010\u0015\u001a\u0004\b\u0000\u0010\t\u001a\u0004\b\u0001\u0010\b\"\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\n2\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\n*\u001c\u0010\u0016\u001a\u0004\b\u0000\u0010\u0011\"\b\u0012\u0004\u0012\u0002H\u00110\u00172\b\u0012\u0004\u0012\u0002H\u00110\u0017¨\u0006\u0018"}, d2 = {"component1", ExifInterface.GPS_DIRECTION_TRUE, "Ljavaslang/Tuple2;", "(Ljavaslang/Tuple2;)Ljava/lang/Object;", "component2", "getOrNull", "Ljavaslang/control/Option;", "(Ljavaslang/control/Option;)Ljava/lang/Object;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", JvmAnnotationNames.KIND_FIELD_NAME, "(Ljavaslang/collection/Map;Ljava/lang/Object;)Ljava/lang/Object;", "ImmutableHashMap", "Ljavaslang/collection/HashMap;", "ImmutableHashSet", ExifInterface.LONGITUDE_EAST, "Ljavaslang/collection/HashSet;", "ImmutableLinkedHashSet", "Ljavaslang/collection/LinkedHashSet;", "ImmutableMap", "ImmutableSet", "Ljavaslang/collection/Set;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaslangAdaptersKt {
    public static final <T> T component1(Tuple2<T, ?> tuple2) {
        Intrinsics.checkNotNullParameter(tuple2, "<this>");
        return tuple2._1();
    }

    public static final <T> T component2(Tuple2<?, T> tuple2) {
        Intrinsics.checkNotNullParameter(tuple2, "<this>");
        return tuple2._2();
    }

    public static final <T> T getOrNull(Option<T> option) {
        Intrinsics.checkNotNullParameter(option, "<this>");
        return option.getOrElse(null);
    }

    public static final <K, V> V getOrNull(Map<K, V> map, K k) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Option<V> option = map.get(k);
        if (option != null) {
            return option.getOrElse(null);
        }
        return null;
    }
}
