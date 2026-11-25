package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: BasicMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0004\b\u0001\u0010\u00032 \u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00050\u0004B1\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/NonAppendableBasicMap;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/incremental/storage/BasicMap;", "Lorg/jetbrains/kotlin/incremental/storage/LazyStorage;", "storageFile", "Ljava/io/File;", "keyDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "valueExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lcom/intellij/util/io/KeyDescriptor;Lcom/intellij/util/io/DataExternalizer;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NonAppendableBasicMap<K extends Comparable<? super K>, V> extends BasicMap<K, V, LazyStorage<K, V>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NonAppendableBasicMap(File storageFile, KeyDescriptor<K> keyDescriptor, DataExternalizer<V> valueExternalizer, IncrementalCompilationContext icContext) {
        super(storageFile, CachingLazyStorageKt.createLazyStorage(storageFile, keyDescriptor, valueExternalizer, icContext), icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }
}
