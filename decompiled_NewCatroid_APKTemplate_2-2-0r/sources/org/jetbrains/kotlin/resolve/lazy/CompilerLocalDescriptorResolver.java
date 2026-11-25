package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: LocalDescriptorResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/CompilerLocalDescriptorResolver;", "Lorg/jetbrains/kotlin/resolve/lazy/LocalDescriptorResolver;", "lazyDeclarationResolver", "Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;", "(Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;)V", "resolveLocalDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilerLocalDescriptorResolver implements LocalDescriptorResolver {
    private final LazyDeclarationResolver lazyDeclarationResolver;

    public CompilerLocalDescriptorResolver(LazyDeclarationResolver lazyDeclarationResolver) {
        Intrinsics.checkNotNullParameter(lazyDeclarationResolver, "lazyDeclarationResolver");
        this.lazyDeclarationResolver = lazyDeclarationResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LocalDescriptorResolver
    public DeclarationDescriptor resolveLocalDeclaration(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return this.lazyDeclarationResolver.resolveToDescriptor(declaration);
    }
}
