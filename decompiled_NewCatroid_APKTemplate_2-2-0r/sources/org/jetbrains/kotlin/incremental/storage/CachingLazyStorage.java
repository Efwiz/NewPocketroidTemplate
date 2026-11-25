package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.util.CommonProcessors;
import org.jetbrains.kotlin.com.intellij.util.io.AppendablePersistentMap;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.com.intellij.util.io.IOUtil;
import org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor;
import org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMap;

/* JADX INFO: compiled from: CachingLazyStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B)\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\t¢\u0006\u0002\u0010\nJ\u001d\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0014H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0016J\u0016\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001bJ\u0014\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0012H\u0002J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\fH\u0016J\u0018\u0010\u001f\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010 J\u0016\u0010!\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0012H\u0002J\u0014\u0010\"\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0012H\u0002J\u0015\u0010#\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010$J\u001e\u0010%\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u0017R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/CachingLazyStorage;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/incremental/storage/AppendableLazyStorage;", "storageFile", "Ljava/io/File;", "keyDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "valueExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "(Ljava/io/File;Lcom/intellij/util/io/KeyDescriptor;Lcom/intellij/util/io/DataExternalizer;)V", "isStorageFileExist", "", UserMetadata.KEYDATA_FILENAME, "", "getKeys", "()Ljava/util/Collection;", "storage", "Lorg/jetbrains/kotlin/com/intellij/util/io/PersistentHashMap;", "append", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", Constants.ATTR_FILTER_TYPE_CLEAN, "close", "contains", "(Ljava/lang/Object;)Z", "createMap", "flush", "memoryCachesOnly", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "getStorageIfExists", "getStorageOrCreateNew", ProductAction.ACTION_REMOVE, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CachingLazyStorage<K, V> implements AppendableLazyStorage<K, V> {
    private boolean isStorageFileExist;
    private final KeyDescriptor<K> keyDescriptor;
    private PersistentHashMap<K, V> storage;
    private final File storageFile;
    private final DataExternalizer<V> valueExternalizer;

    public CachingLazyStorage(File storageFile, KeyDescriptor<K> keyDescriptor, DataExternalizer<V> valueExternalizer) {
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        this.storageFile = storageFile;
        this.keyDescriptor = keyDescriptor;
        this.valueExternalizer = valueExternalizer;
        this.isStorageFileExist = true;
    }

    private final PersistentHashMap<K, V> getStorageIfExists() {
        PersistentHashMap<K, V> persistentHashMap = this.storage;
        if (persistentHashMap != null) {
            return persistentHashMap;
        }
        if (!this.isStorageFileExist) {
            return null;
        }
        if (this.storageFile.exists()) {
            PersistentHashMap<K, V> persistentHashMapCreateMap = createMap();
            this.storage = persistentHashMapCreateMap;
            return persistentHashMapCreateMap;
        }
        this.isStorageFileExist = false;
        return null;
    }

    private final PersistentHashMap<K, V> getStorageOrCreateNew() {
        if (this.storage == null) {
            this.storage = createMap();
        }
        PersistentHashMap<K, V> persistentHashMap = this.storage;
        Intrinsics.checkNotNull(persistentHashMap);
        return persistentHashMap;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized Collection<K> getKeys() {
        List listCreateListBuilder;
        listCreateListBuilder = CollectionsKt.createListBuilder();
        PersistentHashMap<K, V> storageIfExists = getStorageIfExists();
        if (storageIfExists != null) {
            storageIfExists.processKeysWithExistingMapping(new CommonProcessors.CollectProcessor(listCreateListBuilder));
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized boolean contains(K key) {
        PersistentHashMap<K, V> storageIfExists;
        storageIfExists = getStorageIfExists();
        return storageIfExists != null ? storageIfExists.containsMapping(key) : false;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized V get(K key) {
        PersistentHashMap<K, V> storageIfExists;
        storageIfExists = getStorageIfExists();
        return storageIfExists != null ? storageIfExists.get(key) : null;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void set(K key, V value) {
        getStorageOrCreateNew().put(key, value);
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void remove(K key) {
        PersistentHashMap<K, V> storageIfExists = getStorageIfExists();
        if (storageIfExists != null) {
            storageIfExists.remove(key);
        }
    }

    @Override // org.jetbrains.kotlin.incremental.storage.AppendableLazyStorage
    public synchronized void append(K key, final V value) {
        if (!(this.valueExternalizer instanceof AppendableDataExternalizer)) {
            throw new IllegalStateException("`valueExternalizer` should implement the `AppendableDataExternalizer` interface to be able to call `append`".toString());
        }
        getStorageOrCreateNew().appendData(key, new AppendablePersistentMap.ValueDataAppender() { // from class: org.jetbrains.kotlin.incremental.storage.CachingLazyStorage$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.io.AppendablePersistentMap.ValueDataAppender
            public final void append(DataOutput dataOutput) throws IOException {
                CachingLazyStorage.append$lambda$2(this.f$0, value, dataOutput);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void append$lambda$2(CachingLazyStorage this$0, Object obj, DataOutput it) throws IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.valueExternalizer.save(it, obj);
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void clean() {
        try {
            PersistentHashMap<K, V> persistentHashMap = this.storage;
            if (persistentHashMap != null) {
                persistentHashMap.close();
            }
            this.storage = null;
            if (!IOUtil.deleteAllFilesStartingWith(this.storageFile)) {
                throw new IOException("Could not delete internal storage: " + this.storageFile.getAbsolutePath());
            }
        } catch (Throwable th) {
            this.storage = null;
            if (!IOUtil.deleteAllFilesStartingWith(this.storageFile)) {
                throw new IOException("Could not delete internal storage: " + this.storageFile.getAbsolutePath());
            }
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void flush(boolean memoryCachesOnly) {
        PersistentHashMap<K, V> persistentHashMap = this.storage;
        if (persistentHashMap == null) {
            return;
        }
        if (memoryCachesOnly) {
            if (persistentHashMap.isDirty()) {
                persistentHashMap.dropMemoryCaches();
            }
        } else {
            persistentHashMap.force();
        }
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        try {
            PersistentHashMap<K, V> persistentHashMap = this.storage;
            if (persistentHashMap != null) {
                persistentHashMap.close();
            }
            this.storage = null;
        } catch (Throwable th) {
            this.storage = null;
            throw th;
        }
    }

    private final PersistentHashMap<K, V> createMap() {
        return new PersistentHashMap<>(this.storageFile, this.keyDescriptor, this.valueExternalizer);
    }
}
