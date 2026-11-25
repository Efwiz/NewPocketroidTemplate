package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/ListExternalizer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/incremental/storage/GenericCollectionExternalizer;", "", "elementExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "(Lcom/intellij/util/io/DataExternalizer;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ListExternalizer<T> extends GenericCollectionExternalizer<T, List<? extends T>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ListExternalizer(DataExternalizer<T> elementExternalizer) {
        super(elementExternalizer, new Function1<Integer, Collection<T>>() { // from class: org.jetbrains.kotlin.incremental.storage.ListExternalizer.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final Collection<T> invoke(int i) {
                return new ArrayList(i);
            }
        });
        Intrinsics.checkNotNullParameter(elementExternalizer, "elementExternalizer");
    }
}
