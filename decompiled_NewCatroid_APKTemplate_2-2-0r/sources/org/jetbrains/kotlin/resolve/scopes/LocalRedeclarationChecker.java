package org.jetbrains.kotlin.resolve.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: LexicalScopeStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;", "", "checkBeforeAddingToScope", "", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "newDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "DO_NOTHING", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LocalRedeclarationChecker {
    void checkBeforeAddingToScope(LexicalScope scope, DeclarationDescriptor newDescriptor);

    /* JADX INFO: compiled from: LexicalScopeStorage.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker$DO_NOTHING;", "Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;", "()V", "checkBeforeAddingToScope", "", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "newDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DO_NOTHING implements LocalRedeclarationChecker {
        public static final DO_NOTHING INSTANCE = new DO_NOTHING();

        @Override // org.jetbrains.kotlin.resolve.scopes.LocalRedeclarationChecker
        public void checkBeforeAddingToScope(LexicalScope scope, DeclarationDescriptor newDescriptor) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(newDescriptor, "newDescriptor");
        }

        private DO_NOTHING() {
        }
    }
}
