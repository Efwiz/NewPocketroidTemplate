package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0014\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00030\u0005BR\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005\u0012/\b\u0002\u0010\b\u001a)\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000e0\t¢\u0006\u0002\u0010\u000fJ\u0015\u0010\u0010\u001a\u00028\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0016¢\u0006\u0002\u0010\u0013J\u001d\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0019R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R5\u0010\b\u001a)\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000e0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/MapExternalizer;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "M", "", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "keyExternalizer", "valueExternalizer", "newMap", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "size", "", "(Lcom/intellij/util/io/DataExternalizer;Lcom/intellij/util/io/DataExternalizer;Lkotlin/jvm/functions/Function1;)V", "read", "input", "Ljava/io/DataInput;", "(Ljava/io/DataInput;)Ljava/util/Map;", "save", "", "output", "Ljava/io/DataOutput;", "map", "(Ljava/io/DataOutput;Ljava/util/Map;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class MapExternalizer<K, V, M extends Map<K, ? extends V>> implements DataExternalizer<M> {
    private final DataExternalizer<K> keyExternalizer;
    private final Function1<Integer, Map<K, V>> newMap;
    private final DataExternalizer<V> valueExternalizer;

    /* JADX WARN: Multi-variable type inference failed */
    public MapExternalizer(DataExternalizer<K> keyExternalizer, DataExternalizer<V> valueExternalizer, Function1<? super Integer, ? extends Map<K, V>> newMap) {
        Intrinsics.checkNotNullParameter(keyExternalizer, "keyExternalizer");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(newMap, "newMap");
        this.keyExternalizer = keyExternalizer;
        this.valueExternalizer = valueExternalizer;
        this.newMap = newMap;
    }

    public /* synthetic */ MapExternalizer(DataExternalizer dataExternalizer, DataExternalizer dataExternalizer2, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(dataExternalizer, dataExternalizer2, (i & 4) != 0 ? new Function1<Integer, LinkedHashMap<K, V>>() { // from class: org.jetbrains.kotlin.incremental.storage.MapExternalizer.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final LinkedHashMap<K, V> invoke(int i2) {
                return new LinkedHashMap<>(i2);
            }
        } : anonymousClass1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, M map) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(map, "map");
        output.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            this.keyExternalizer.save(output, key);
            this.valueExternalizer.save(output, value);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public M read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        int i = input.readInt();
        M m = (M) this.newMap.invoke(Integer.valueOf(i));
        for (int i2 = 0; i2 < i; i2++) {
            m.put(this.keyExternalizer.read(input), this.valueExternalizer.read(input));
        }
        Intrinsics.checkNotNull(m, "null cannot be cast to non-null type M of org.jetbrains.kotlin.incremental.storage.MapExternalizer");
        return m;
    }
}
