package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: CachingLazyStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aJ\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00030\t2\u0006\u0010\n\u001a\u00020\u000b\u001aJ\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00030\r2\u0006\u0010\n\u001a\u00020\u000b\u001aL\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00030\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000f"}, d2 = {"createLazyStorage", "Lorg/jetbrains/kotlin/incremental/storage/LazyStorage;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "storageFile", "Ljava/io/File;", "keyDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "valueExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "Lorg/jetbrains/kotlin/incremental/storage/AppendableLazyStorage;", "Lorg/jetbrains/kotlin/incremental/storage/AppendableDataExternalizer;", "createLazyStorageImpl", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CachingLazyStorageKt {
    private static final <K, V> AppendableLazyStorage<K, V> createLazyStorageImpl(File file, KeyDescriptor<K> keyDescriptor, DataExternalizer<V> dataExternalizer, IncrementalCompilationContext incrementalCompilationContext) {
        CachingLazyStorage cachingLazyStorage = new CachingLazyStorage(file, keyDescriptor, dataExternalizer);
        if (incrementalCompilationContext.getKeepIncrementalCompilationCachesInMemory()) {
            DefaultInMemoryStorageWrapper defaultInMemoryStorageWrapper = new DefaultInMemoryStorageWrapper(cachingLazyStorage, dataExternalizer);
            incrementalCompilationContext.getTransaction().registerInMemoryStorageWrapper(defaultInMemoryStorageWrapper);
            return defaultInMemoryStorageWrapper;
        }
        return cachingLazyStorage;
    }

    public static final <K, V> LazyStorage<K, V> createLazyStorage(File storageFile, KeyDescriptor<K> keyDescriptor, DataExternalizer<V> valueExternalizer, IncrementalCompilationContext icContext) {
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        return createLazyStorageImpl(storageFile, keyDescriptor, valueExternalizer, icContext);
    }

    public static final <K, V> AppendableLazyStorage<K, V> createLazyStorage(File storageFile, KeyDescriptor<K> keyDescriptor, AppendableDataExternalizer<V> valueExternalizer, IncrementalCompilationContext icContext) {
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        return createLazyStorageImpl(storageFile, keyDescriptor, valueExternalizer, icContext);
    }
}
