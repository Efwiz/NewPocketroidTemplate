package org.jetbrains.kotlin.incremental.storage;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.io.File;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: LookupMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\"\n\u0000\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0004J\u001c\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00022\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0002H\u0014J\u0016\u0010\u0018\u001a\u00020\u00102\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0014J\u0019\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\u0017\u001a\u00020\u0002H\u0086\u0002J\u000e\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0002J\u001f\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00022\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u001dH\u0086\u0002R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/LookupMap;", "Lorg/jetbrains/kotlin/incremental/storage/AppendableBasicMap;", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "", "", "storage", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", UserMetadata.KEYDATA_FILENAME, "getKeys", "()Ljava/util/Collection;", ProductAction.ACTION_ADD, "", "name", "", "scope", "fileId", "append", "lookup", "fileIds", "dumpKey", ASTExpr.DEFAULT_MAP_KEY_NAME, "dumpValue", "value", "get", ProductAction.ACTION_REMOVE, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookupMap extends AppendableBasicMap<LookupSymbolKey, Collection<? extends Integer>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LookupMap(File storage, IncrementalCompilationContext icContext) {
        super(storage, new LookupSymbolKeyDescriptor(icContext.getStoreFullFqNamesInLookupCache()), IntCollectionExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storage, "storage");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpKey(LookupSymbolKey key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return key.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(Collection<Integer> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value.toString();
    }

    public final void add(String name2, String scope, int fileId) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(scope, "scope");
        getStorage().append(new LookupSymbolKey(name2, scope), CollectionsKt.listOf(Integer.valueOf(fileId)));
    }

    public final void append(LookupSymbolKey lookup, Collection<Integer> fileIds) {
        Intrinsics.checkNotNullParameter(lookup, "lookup");
        Intrinsics.checkNotNullParameter(fileIds, "fileIds");
        getStorage().append(lookup, fileIds);
    }

    public final Collection<Integer> get(LookupSymbolKey key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (Collection) getStorage().get(key);
    }

    public final void set(LookupSymbolKey key, Set<Integer> fileIds) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(fileIds, "fileIds");
        getStorage().set(key, fileIds);
    }

    public final void remove(LookupSymbolKey key) {
        Intrinsics.checkNotNullParameter(key, "key");
        getStorage().remove(key);
    }

    public final Collection<LookupSymbolKey> getKeys() {
        return getStorage().getKeys();
    }
}
