package org.jetbrains.kotlin.backend.common.serialization.metadata;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.SerializedMetadata;

/* JADX INFO: compiled from: KlibMetadataIncrementalSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0010\u0012\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u00012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00032\u0006\u0010\u0007\u001a\u00020\u0006¨\u0006\b"}, d2 = {"makeSerializedKlibMetadata", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", "fragments", "", "", "", "", "header", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataIncrementalSerializerKt {
    public static final SerializedMetadata makeSerializedKlibMetadata(Map<String, ? extends List<byte[]>> fragments, byte[] header) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        Intrinsics.checkNotNullParameter(header, "header");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : CollectionsKt.sortedWith(fragments.entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataIncrementalSerializerKt$makeSerializedKlibMetadata$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues((String) ((Map.Entry) t).getKey(), (String) ((Map.Entry) t2).getKey());
            }
        })) {
            String str = (String) entry.getKey();
            List list = (List) entry.getValue();
            arrayList.add(str);
            arrayList2.add(list);
        }
        return new SerializedMetadata(header, arrayList2, arrayList);
    }
}
