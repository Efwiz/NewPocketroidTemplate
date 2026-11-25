package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithNavigationSubstitute;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: DescriptorToSourceUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\u0007H\u0007J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\u0007H\u0002J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\f\u001a\u00020\u0007H\u0007J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00102\u0006\u0010\b\u001a\u00020\u0007H\u0007J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\u0012H\u0003J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\b\u001a\u00020\u0015H\u0007J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\u0007H\u0007¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DescriptorToSourceUtils;", "", "()V", "collectEffectiveReferencedDescriptors", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptor", "descriptorToDeclaration", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "findTopLevelParent", "declarationDescriptor", "getContainingFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "getEffectiveReferencedDescriptors", "", "getSourceForExtensionReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getSourceFromAnnotation", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getSourceFromDescriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorToSourceUtils {
    public static final DescriptorToSourceUtils INSTANCE = new DescriptorToSourceUtils();

    private DescriptorToSourceUtils() {
    }

    private final void collectEffectiveReferencedDescriptors(List<DeclarationDescriptor> result, DeclarationDescriptor descriptor) {
        if (descriptor instanceof DeclarationDescriptorWithNavigationSubstitute) {
            collectEffectiveReferencedDescriptors(result, ((DeclarationDescriptorWithNavigationSubstitute) descriptor).getSubstitute());
            return;
        }
        if (descriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
            CallableMemberDescriptor.Kind kind = callableMemberDescriptor.getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            if (kind != CallableMemberDescriptor.Kind.DECLARATION && kind != CallableMemberDescriptor.Kind.SYNTHESIZED) {
                Iterator<? extends CallableMemberDescriptor> it = callableMemberDescriptor.getOverriddenDescriptors().iterator();
                while (it.hasNext()) {
                    CallableMemberDescriptor original = it.next().getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    collectEffectiveReferencedDescriptors(result, original);
                }
                return;
            }
            if (descriptor instanceof SyntheticMemberDescriptor) {
                collectEffectiveReferencedDescriptors(result, ((SyntheticMemberDescriptor) descriptor).getBaseDescriptorForSynthetic());
                return;
            }
        }
        result.add(descriptor);
    }

    @JvmStatic
    public static final Collection<DeclarationDescriptor> getEffectiveReferencedDescriptors(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ArrayList arrayList = new ArrayList();
        DeclarationDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        INSTANCE.collectEffectiveReferencedDescriptors(arrayList, original);
        return arrayList;
    }

    @JvmStatic
    private static final PsiElement getSourceForExtensionReceiverParameterDescriptor(ReceiverParameterDescriptor descriptor) {
        if (!Intrinsics.areEqual(descriptor.getSourceElement(), SourceElement.NO_SOURCE) || !(descriptor.getValue() instanceof ExtensionReceiver)) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = descriptor.getPackageFragment();
        CallableDescriptor callableDescriptor = containingDeclaration instanceof CallableDescriptor ? (CallableDescriptor) containingDeclaration : null;
        if (callableDescriptor == null) {
            return null;
        }
        SourceElement source = callableDescriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        KtCallableDeclaration ktCallableDeclaration = psi instanceof KtCallableDeclaration ? (KtCallableDeclaration) psi : null;
        if (ktCallableDeclaration == null) {
            return null;
        }
        return ktCallableDeclaration.mo7393getReceiverTypeReference();
    }

    @JvmStatic
    public static final PsiElement getSourceFromDescriptor(DeclarationDescriptor descriptor) {
        SourceElement source;
        PsiElement sourceForExtensionReceiverParameterDescriptor;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if ((descriptor instanceof ReceiverParameterDescriptor) && (sourceForExtensionReceiverParameterDescriptor = getSourceForExtensionReceiverParameterDescriptor((ReceiverParameterDescriptor) descriptor)) != null) {
            return sourceForExtensionReceiverParameterDescriptor;
        }
        DeclarationDescriptorWithSource declarationDescriptorWithSource = descriptor instanceof DeclarationDescriptorWithSource ? (DeclarationDescriptorWithSource) descriptor : null;
        if (declarationDescriptorWithSource == null || (source = declarationDescriptorWithSource.getSourceElement()) == null) {
            return null;
        }
        return PsiSourceElementKt.getPsi(source);
    }

    @JvmStatic
    public static final KtAnnotationEntry getSourceFromAnnotation(AnnotationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        PsiElement psi = PsiSourceElementKt.getPsi(descriptor.getSource());
        if (psi instanceof KtAnnotationEntry) {
            return (KtAnnotationEntry) psi;
        }
        return null;
    }

    @JvmStatic
    public static final PsiElement descriptorToDeclaration(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Collection<DeclarationDescriptor> effectiveReferencedDescriptors = getEffectiveReferencedDescriptors(descriptor);
        if (effectiveReferencedDescriptors.size() != 1) {
            return null;
        }
        Object objFirstOrNull = CollectionsKt.firstOrNull(effectiveReferencedDescriptors);
        Intrinsics.checkNotNull(objFirstOrNull);
        return getSourceFromDescriptor((DeclarationDescriptor) objFirstOrNull);
    }

    @JvmStatic
    public static final KtFile getContainingFile(DeclarationDescriptor declarationDescriptor) {
        PsiElement psiElementDescriptorToDeclaration;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        DeclarationDescriptor declarationDescriptorFindTopLevelParent = INSTANCE.findTopLevelParent(declarationDescriptor);
        if (declarationDescriptorFindTopLevelParent == null || (psiElementDescriptorToDeclaration = descriptorToDeclaration(declarationDescriptorFindTopLevelParent)) == null) {
            return null;
        }
        PsiFile containingFile = psiElementDescriptorToDeclaration.getContainingFile();
        if (containingFile instanceof KtFile) {
            return (KtFile) containingFile;
        }
        return null;
    }

    private final DeclarationDescriptor findTopLevelParent(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof PropertyAccessorDescriptor) {
            Intrinsics.checkNotNull(declarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor");
            declarationDescriptor = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
        }
        while (declarationDescriptor != null && !DescriptorUtils.isTopLevelDeclaration(declarationDescriptor)) {
            declarationDescriptor = declarationDescriptor.getPackageFragment();
        }
        return declarationDescriptor;
    }
}
