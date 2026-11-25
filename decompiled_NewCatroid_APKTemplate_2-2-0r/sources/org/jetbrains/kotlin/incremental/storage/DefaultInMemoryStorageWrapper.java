package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: InMemoryStorageWrapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0016\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001/B'\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\bJ\u001d\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001aJ\b\u0010\u001b\u001a\u00020\u0017H\u0016J\b\u0010\u001c\u001a\u00020\u0017H\u0016J\u0016\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001eJ\u0010\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u000eH\u0016J\u0018\u0010!\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0018\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\"J'\u0010#\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010$\u001a\u00020\u000b2\b\b\u0002\u0010%\u001a\u00020\u000eH\u0002¢\u0006\u0002\u0010&J\u0017\u0010'\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\"J\u0015\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010)J\b\u0010*\u001a\u00020\u0017H\u0016J\u001e\u0010+\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u001aJ\u0019\u0010,\u001a\u0002H-\"\u0004\b\u0002\u0010-*\u0004\u0018\u00010.H\u0002¢\u0006\u0002\u0010\"R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000b0\nj\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0014j\b\u0012\u0004\u0012\u00028\u0000`\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/incremental/storage/InMemoryStorageWrapper;", "origin", "Lorg/jetbrains/kotlin/incremental/storage/CachingLazyStorage;", "valueExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "(Lorg/jetbrains/kotlin/incremental/storage/CachingLazyStorage;Lcom/intellij/util/io/DataExternalizer;)V", "inMemoryStorage", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper;", "Lkotlin/collections/LinkedHashMap;", "isCleanRequested", "", UserMetadata.KEYDATA_FILENAME, "", "getKeys", "()Ljava/util/Collection;", "removedKeys", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "append", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", Constants.ATTR_FILTER_TYPE_CLEAN, "close", "contains", "(Ljava/lang/Object;)Z", "flush", "memoryCachesOnly", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "getMergedValue", "wrapper", "useOriginValue", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper;Z)Ljava/lang/Object;", "getOriginValue", ProductAction.ACTION_REMOVE, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "resetInMemoryChanges", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "cast", ExifInterface.GPS_DIRECTION_TRUE, "", "ValueWrapper", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultInMemoryStorageWrapper<K, V> implements InMemoryStorageWrapper<K, V> {
    private final LinkedHashMap<K, ValueWrapper> inMemoryStorage;
    private boolean isCleanRequested;
    private final CachingLazyStorage<K, V> origin;
    private final HashSet<K> removedKeys;
    private final DataExternalizer<V> valueExternalizer;

    /* JADX WARN: Multi-variable type inference failed */
    private final <T> T cast(Object obj) {
        return obj;
    }

    public DefaultInMemoryStorageWrapper(CachingLazyStorage<K, V> origin, DataExternalizer<V> valueExternalizer) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        this.origin = origin;
        this.valueExternalizer = valueExternalizer;
        this.inMemoryStorage = new LinkedHashMap<>();
        this.removedKeys = new HashSet<>();
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized Collection<K> getKeys() {
        Collection<K> collectionPlus;
        if (this.isCleanRequested) {
            collectionPlus = this.inMemoryStorage.keySet();
            Intrinsics.checkNotNullExpressionValue(collectionPlus, "<get-keys>(...)");
        } else {
            List listMinus = CollectionsKt.minus((Iterable) this.origin.getKeys(), (Iterable) this.removedKeys);
            Set<K> setKeySet = this.inMemoryStorage.keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
            collectionPlus = CollectionsKt.plus((Collection) listMinus, (Iterable) setKeySet);
        }
        return collectionPlus;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.InMemoryStorageWrapper
    public synchronized void resetInMemoryChanges() {
        this.isCleanRequested = false;
        this.inMemoryStorage.clear();
        this.removedKeys.clear();
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void clean() {
        this.inMemoryStorage.clear();
        this.removedKeys.clear();
        this.isCleanRequested = true;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void flush(boolean memoryCachesOnly) {
        if (this.isCleanRequested) {
            this.origin.clean();
        } else {
            Iterator<K> it = this.removedKeys.iterator();
            while (it.hasNext()) {
                this.origin.remove(it.next());
            }
        }
        for (Map.Entry<K, ValueWrapper> entry : this.inMemoryStorage.entrySet()) {
            K key = entry.getKey();
            ValueWrapper value = entry.getValue();
            if (value instanceof ValueWrapper.Value) {
                this.origin.set(key, (V) cast(((ValueWrapper.Value) value).getValue()));
            } else if (value instanceof ValueWrapper.AppendChain) {
                this.origin.append(key, getMergedValue(key, value, false));
                Unit unit = Unit.INSTANCE;
                this.origin.get(key);
            }
        }
        resetInMemoryChanges();
        this.origin.flush(memoryCachesOnly);
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        this.origin.close();
    }

    @Override // org.jetbrains.kotlin.incremental.storage.AppendableLazyStorage
    public synchronized void append(K key, V value) {
        if (!(this.valueExternalizer instanceof AppendableDataExternalizer)) {
            throw new IllegalStateException("`valueExternalizer` should implement the `AppendableDataExternalizer` interface to be able to call `append`".toString());
        }
        ValueWrapper valueWrapper = this.inMemoryStorage.get(key);
        if (valueWrapper instanceof ValueWrapper.AppendChain) {
            ((List) cast(((ValueWrapper.AppendChain) valueWrapper).getParts())).add(value);
        } else {
            this.inMemoryStorage.put(key, valueWrapper instanceof ValueWrapper.Value ? new ValueWrapper.AppendChain(CollectionsKt.mutableListOf(cast(((ValueWrapper.Value) valueWrapper).getValue()), value), false) : new ValueWrapper.AppendChain(CollectionsKt.mutableListOf(value), true));
        }
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void remove(K key) {
        this.removedKeys.add(key);
        this.inMemoryStorage.remove(key);
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized void set(K key, V value) {
        this.inMemoryStorage.put(key, new ValueWrapper.Value(value));
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized V get(K key) {
        V v;
        ValueWrapper valueWrapper = this.inMemoryStorage.get(key);
        if (valueWrapper instanceof ValueWrapper.Value) {
            v = (V) cast(((ValueWrapper.Value) valueWrapper).getValue());
        } else if (valueWrapper instanceof ValueWrapper.AppendChain) {
            Object mergedValue$default = getMergedValue$default(this, key, valueWrapper, false, 4, null);
            this.inMemoryStorage.put(key, new ValueWrapper.Value(mergedValue$default));
            v = (V) mergedValue$default;
        } else {
            v = !this.removedKeys.contains(key) ? this.origin.get(key) : null;
        }
        return v;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.LazyStorage
    public synchronized boolean contains(K key) {
        return this.inMemoryStorage.containsKey(key) || (!this.removedKeys.contains(key) && this.origin.contains(key));
    }

    static /* synthetic */ Object getMergedValue$default(DefaultInMemoryStorageWrapper defaultInMemoryStorageWrapper, Object obj, ValueWrapper valueWrapper, boolean z, int i, Object obj2) {
        if ((i & 4) != 0) {
            z = true;
        }
        return defaultInMemoryStorageWrapper.getMergedValue(obj, valueWrapper, z);
    }

    private final V getMergedValue(K key, ValueWrapper wrapper, boolean useOriginValue) {
        Object objCreateNil;
        if (wrapper instanceof ValueWrapper.Value) {
            throw new IllegalStateException(("There's no need to merge values for " + key).toString());
        }
        if (!(this.valueExternalizer instanceof AppendableDataExternalizer)) {
            throw new IllegalStateException("`valueExternalizer` should implement the `AppendableDataExternalizer` interface to be able to handle `append`".toString());
        }
        if (wrapper instanceof ValueWrapper.AppendChain) {
            if (useOriginValue && ((ValueWrapper.AppendChain) wrapper).getAppendToOrigin()) {
                List listListOfNotNull = CollectionsKt.listOfNotNull(getOriginValue(key));
                objCreateNil = ((AppendableDataExternalizer) this.valueExternalizer).createNil();
                Iterator it = listListOfNotNull.iterator();
                while (it.hasNext()) {
                    objCreateNil = (V) getMergedValue$merge(this, objCreateNil, it.next());
                }
            } else {
                objCreateNil = ((AppendableDataExternalizer) this.valueExternalizer).createNil();
            }
            Iterator it2 = ((Iterable) cast(((ValueWrapper.AppendChain) wrapper).getParts())).iterator();
            while (it2.hasNext()) {
                objCreateNil = (V) getMergedValue$merge(this, objCreateNil, it2.next());
            }
            return (V) objCreateNil;
        }
        throw new IllegalStateException(("In-memory storage contains no value for " + key).toString());
    }

    private static final <V, K> V getMergedValue$merge(DefaultInMemoryStorageWrapper<K, V> defaultInMemoryStorageWrapper, V v, V v2) {
        return (V) ((AppendableDataExternalizer) ((DefaultInMemoryStorageWrapper) defaultInMemoryStorageWrapper).valueExternalizer).append(v, v2);
    }

    private final V getOriginValue(K key) {
        if (this.removedKeys.contains(key)) {
            return null;
        }
        return this.origin.get(key);
    }

    /* JADX INFO: compiled from: InMemoryStorageWrapper.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper;", "", "AppendChain", "Value", "Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper$AppendChain;", "Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper$Value;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface ValueWrapper {

        /* JADX INFO: compiled from: InMemoryStorageWrapper.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0002¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0003\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper$Value;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper;", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Value<V> implements ValueWrapper {
            private final V value;

            public Value(V v) {
                this.value = v;
            }

            public final V getValue() {
                return this.value;
            }
        }

        /* JADX INFO: compiled from: InMemoryStorageWrapper.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper$AppendChain;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/incremental/storage/DefaultInMemoryStorageWrapper$ValueWrapper;", "parts", "", "appendToOrigin", "", "(Ljava/util/List;Z)V", "getAppendToOrigin", "()Z", "getParts", "()Ljava/util/List;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class AppendChain<V> implements ValueWrapper {
            private final boolean appendToOrigin;
            private final List<V> parts;

            public AppendChain(List<V> parts, boolean z) {
                Intrinsics.checkNotNullParameter(parts, "parts");
                this.parts = parts;
                this.appendToOrigin = z;
            }

            public final boolean getAppendToOrigin() {
                return this.appendToOrigin;
            }

            public final List<V> getParts() {
                return this.parts;
            }
        }
    }
}
