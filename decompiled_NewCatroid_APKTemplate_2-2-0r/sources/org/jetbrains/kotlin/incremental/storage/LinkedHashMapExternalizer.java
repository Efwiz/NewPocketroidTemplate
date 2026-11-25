package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u000220\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012 \u0012\u001e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004j\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002`\u00050\u0003B!\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/LinkedHashMapExternalizer;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/incremental/storage/MapExternalizer;", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "keyExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "valueExternalizer", "(Lcom/intellij/util/io/DataExternalizer;Lcom/intellij/util/io/DataExternalizer;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LinkedHashMapExternalizer<K, V> extends MapExternalizer<K, V, LinkedHashMap<K, V>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LinkedHashMapExternalizer(DataExternalizer<K> keyExternalizer, DataExternalizer<V> valueExternalizer) {
        super(keyExternalizer, valueExternalizer, new Function1<Integer, Map<K, V>>() { // from class: org.jetbrains.kotlin.incremental.storage.LinkedHashMapExternalizer.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final Map<K, V> invoke(int i) {
                return new LinkedHashMap(i);
            }
        });
        Intrinsics.checkNotNullParameter(keyExternalizer, "keyExternalizer");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
    }
}
