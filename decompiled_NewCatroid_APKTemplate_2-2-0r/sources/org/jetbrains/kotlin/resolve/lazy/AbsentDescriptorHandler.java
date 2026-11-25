package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: AbsentDescriptorHandler.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "diagnoseDescriptorNotFound", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AbsentDescriptorHandler extends PlatformSpecificExtension<AbsentDescriptorHandler> {
    /* JADX INFO: renamed from: diagnoseDescriptorNotFound */
    DeclarationDescriptor mo7442diagnoseDescriptorNotFound(KtDeclaration declaration);
}
