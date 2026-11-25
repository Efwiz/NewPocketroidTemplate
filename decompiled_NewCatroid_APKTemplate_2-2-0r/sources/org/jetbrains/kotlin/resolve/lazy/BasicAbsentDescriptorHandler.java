package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: AbsentDescriptorHandler.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/BasicAbsentDescriptorHandler;", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "()V", "diagnoseDescriptorNotFound", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BasicAbsentDescriptorHandler implements AbsentDescriptorHandler {
    @Override // org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler
    /* JADX INFO: renamed from: diagnoseDescriptorNotFound, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ DeclarationDescriptor mo7442diagnoseDescriptorNotFound(KtDeclaration ktDeclaration) {
        return (DeclarationDescriptor) diagnoseDescriptorNotFound(ktDeclaration);
    }

    public Void diagnoseDescriptorNotFound(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        throw new NoDescriptorForDeclarationException(declaration, null, 2, null);
    }
}
