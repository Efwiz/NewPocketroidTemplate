package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.incremental.storage.LookupMap;
import org.jetbrains.kotlin.incremental.storage.LookupSymbolKey;

/* JADX INFO: compiled from: LookupStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\rJ\u0019\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\r2\u0006\u0010\u0014\u001a\u00020\tH\u0086\u0002J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tH\u0002J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tH\u0002J\u000e\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tJ\u001f\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u001cH\u0086\u0002R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/incremental/TrackedLookupMap;", "", "lookupMap", "Lorg/jetbrains/kotlin/incremental/storage/LookupMap;", "trackChanges", "", "(Lorg/jetbrains/kotlin/incremental/storage/LookupMap;Z)V", "addedKeys", "", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "getAddedKeys", "()Ljava/util/Set;", UserMetadata.KEYDATA_FILENAME, "", "getKeys", "()Ljava/util/Collection;", "removedKeys", "getRemovedKeys", "append", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "fileIds", "", "get", "recordRemove", "recordSet", ProductAction.ACTION_REMOVE, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TrackedLookupMap {
    private final Set<LookupSymbolKey> addedKeys;
    private final LookupMap lookupMap;
    private final Set<LookupSymbolKey> removedKeys;
    private final boolean trackChanges;

    public TrackedLookupMap(LookupMap lookupMap, boolean z) {
        Intrinsics.checkNotNullParameter(lookupMap, "lookupMap");
        this.lookupMap = lookupMap;
        this.trackChanges = z;
        this.addedKeys = z ? new LinkedHashSet() : null;
        this.removedKeys = z ? new LinkedHashSet() : null;
    }

    public final Set<LookupSymbolKey> getAddedKeys() {
        return this.addedKeys;
    }

    public final Set<LookupSymbolKey> getRemovedKeys() {
        return this.removedKeys;
    }

    public final Collection<LookupSymbolKey> getKeys() {
        return this.lookupMap.getKeys();
    }

    public final Collection<Integer> get(LookupSymbolKey key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.lookupMap.get(key);
    }

    public final void set(LookupSymbolKey key, Set<Integer> fileIds) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(fileIds, "fileIds");
        recordSet(key);
        this.lookupMap.set(key, fileIds);
    }

    public final void append(LookupSymbolKey key, Collection<Integer> fileIds) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(fileIds, "fileIds");
        recordSet(key);
        this.lookupMap.append(key, fileIds);
    }

    public final void remove(LookupSymbolKey key) {
        Intrinsics.checkNotNullParameter(key, "key");
        recordRemove(key);
        this.lookupMap.remove(key);
    }

    private final void recordSet(LookupSymbolKey key) {
        if (this.trackChanges && this.lookupMap.get(key) == null) {
            Set<LookupSymbolKey> set = this.removedKeys;
            Intrinsics.checkNotNull(set);
            if (set.contains(key)) {
                this.removedKeys.remove(key);
                return;
            }
            Set<LookupSymbolKey> set2 = this.addedKeys;
            Intrinsics.checkNotNull(set2);
            set2.add(key);
        }
    }

    private final void recordRemove(LookupSymbolKey key) {
        if (this.trackChanges && this.lookupMap.get(key) != null) {
            Set<LookupSymbolKey> set = this.addedKeys;
            Intrinsics.checkNotNull(set);
            if (set.contains(key)) {
                this.addedKeys.remove(key);
                return;
            }
            Set<LookupSymbolKey> set2 = this.removedKeys;
            Intrinsics.checkNotNull(set2);
            set2.add(key);
        }
    }
}
