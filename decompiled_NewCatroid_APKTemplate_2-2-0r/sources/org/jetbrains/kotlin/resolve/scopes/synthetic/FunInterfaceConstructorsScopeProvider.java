package org.jetbrains.kotlin.resolve.scopes.synthetic;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: FunInterfaceConstructorsSyntheticScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/synthetic/FunInterfaceConstructorsScopeProvider;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "samResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;)V", "scopes", "", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "getScopes", "()Ljava/util/Collection;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunInterfaceConstructorsScopeProvider implements SyntheticScopes {
    private final Collection<SyntheticScope> scopes;

    public FunInterfaceConstructorsScopeProvider(StorageManager storageManager, LookupTracker lookupTracker, SamConversionResolver samResolver, SamConversionOracle samConversionOracle) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(samResolver, "samResolver");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        this.scopes = CollectionsKt.listOf(new FunInterfaceConstructorsSyntheticScope(storageManager, lookupTracker, samResolver, samConversionOracle));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScopes
    public Collection<SyntheticScope> getScopes() {
        return this.scopes;
    }
}
