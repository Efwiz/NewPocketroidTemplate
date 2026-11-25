package org.jetbrains.kotlin.asJava.classes;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightParameters.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014R\u001c\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightReceiverParameter;", "Lorg/jetbrains/kotlin/asJava/classes/KtAbstractUltraLightParameterForDeclaration;", "containingDeclaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "method", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "(Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;)V", "givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "getGivenAnnotations", "()Ljava/util/List;", "isVarArgs", "", "tryGetKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightReceiverParameter extends KtAbstractUltraLightParameterForDeclaration {
    private final List<KtLightAbstractAnnotation> givenAnnotations;

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        return false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtUltraLightReceiverParameter(KtCallableDeclaration containingDeclaration, KtUltraLightSupport support, KtUltraLightMethod method) {
        KtModifierList modifierList;
        List<KtAnnotationEntry> annotationEntries;
        List<KtLightAnnotationForSourceEntry> lightAnnotations;
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(method, "method");
        String name2 = containingDeclaration.getName();
        String labeledThisName = AsmUtil.getLabeledThisName(name2 == null ? method.getName() : name2, AsmUtil.LABELED_THIS_PARAMETER, AsmUtil.RECEIVER_PARAMETER_NAME);
        Intrinsics.checkNotNullExpressionValue(labeledThisName, "getLabeledThisName(...)");
        super(labeledThisName, null, support, method, containingDeclaration);
        KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = containingDeclaration.mo7393getReceiverTypeReference();
        this.givenAnnotations = (ktTypeReferenceMo7393getReceiverTypeReference == null || (modifierList = ktTypeReferenceMo7393getReceiverTypeReference.getModifierList()) == null || (annotationEntries = modifierList.getAnnotationEntries()) == null || (lightAnnotations = UltraLightUtilsKt.toLightAnnotations(annotationEntries, this, AnnotationUseSiteTarget.RECEIVER)) == null) ? CollectionsKt.emptyList() : lightAnnotations;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public List<KtLightAbstractAnnotation> getGivenAnnotations() {
        return this.givenAnnotations;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtAbstractUltraLightParameterForDeclaration
    protected KotlinType tryGetKotlinType() {
        ReceiverParameterDescriptor extensionReceiverParameter;
        CallableDescriptor callableDescriptorTryGetContainingDescriptor = tryGetContainingDescriptor();
        if (callableDescriptorTryGetContainingDescriptor == null || (extensionReceiverParameter = callableDescriptorTryGetContainingDescriptor.getExtensionReceiverParameter()) == null) {
            return null;
        }
        return extensionReceiverParameter.getType();
    }
}
