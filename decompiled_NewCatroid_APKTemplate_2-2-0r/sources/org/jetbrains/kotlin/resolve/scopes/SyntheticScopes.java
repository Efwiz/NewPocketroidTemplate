package org.jetbrains.kotlin.resolve.scopes;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.resolve.scopes.synthetic.FunInterfaceConstructorsScopeProvider;

/* JADX INFO: compiled from: SyntheticScopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = FunInterfaceConstructorsScopeProvider.class)
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001:\u0001\u0007R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "", "scopes", "", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "getScopes", "()Ljava/util/Collection;", "Empty", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticScopes {
    Collection<SyntheticScope> getScopes();

    /* JADX INFO: compiled from: SyntheticScopes.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes$Empty;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "()V", "scopes", "", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "getScopes", "()Ljava/util/Collection;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements SyntheticScopes {
        public static final Empty INSTANCE = new Empty();
        private static final Collection<SyntheticScope> scopes = CollectionsKt.emptyList();

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScopes
        public Collection<SyntheticScope> getScopes() {
            return scopes;
        }
    }
}
