package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: sourceLocationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"findPsi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SourceLocationUtilsKt {
    public static final PsiElement findPsi(DeclarationDescriptor declarationDescriptor) {
        SourceElement source;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        PsiElement psi = null;
        DeclarationDescriptorWithSource declarationDescriptorWithSource = declarationDescriptor instanceof DeclarationDescriptorWithSource ? (DeclarationDescriptorWithSource) declarationDescriptor : null;
        if (declarationDescriptorWithSource != null && (source = declarationDescriptorWithSource.getSourceElement()) != null) {
            psi = PsiSourceElementKt.getPsi(source);
        }
        if (psi != null || !(declarationDescriptor instanceof CallableMemberDescriptor)) {
            return psi;
        }
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
        if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return psi;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (CallableMemberDescriptor callableMemberDescriptor2 : overriddenDescriptors) {
            Intrinsics.checkNotNull(callableMemberDescriptor2);
            PsiElement psiElementFindPsi = findPsi(callableMemberDescriptor2);
            if (psiElementFindPsi != null) {
                arrayList.add(psiElementFindPsi);
            }
        }
        return (PsiElement) CollectionsKt.firstOrNull((List) arrayList);
    }
}
