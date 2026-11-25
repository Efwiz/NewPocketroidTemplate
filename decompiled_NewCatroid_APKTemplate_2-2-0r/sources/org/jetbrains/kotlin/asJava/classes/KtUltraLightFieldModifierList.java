package org.jetbrains.kotlin.asJava.classes;

import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.asJava.elements.KtUltraLightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtProperty;

/* JADX INFO: compiled from: ultraLightField.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B7\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u0000H\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\fH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightFieldModifierList;", "Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightModifierList;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "declaration", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "owner", "modifiers", "", "", "(Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;Ljava/util/Set;)V", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "hasFieldAnnotation", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "hasModifierProperty", "name", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KtUltraLightFieldModifierList extends KtUltraLightModifierList<KtLightElement<? extends KtModifierListOwner, ? extends PsiModifierListOwner>> {
    private final KtNamedDeclaration declaration;
    private final Set<String> modifiers;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightFieldModifierList(KtUltraLightSupport support, KtNamedDeclaration declaration, KtLightElement<? extends KtModifierListOwner, ? extends PsiModifierListOwner> owner, Set<String> modifiers) {
        super(owner, support);
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
        this.declaration = declaration;
        this.modifiers = modifiers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (Intrinsics.areEqual(name2, "volatile")) {
            return hasFieldAnnotation(JvmNames.VOLATILE_ANNOTATION_FQ_NAME);
        }
        return Intrinsics.areEqual(name2, "transient") ? hasFieldAnnotation(JvmNames.TRANSIENT_ANNOTATION_FQ_NAME) : this.modifiers.contains(name2);
    }

    private final boolean hasFieldAnnotation(FqName fqName) {
        KtAnnotationEntry first;
        AnnotationUseSiteTarget annotationUseSiteTarget;
        Pair<KtAnnotationEntry, AnnotationDescriptor> pairFindAnnotation = UltraLightUtilsKt.findAnnotation(getSupport(), this.declaration, fqName);
        if (pairFindAnnotation == null || (first = pairFindAnnotation.getFirst()) == null) {
            return false;
        }
        KtAnnotationUseSiteTarget useSiteTarget = first.getUseSiteTarget();
        if (useSiteTarget == null || (annotationUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget()) == null) {
            return true;
        }
        KtNamedDeclaration ktNamedDeclaration = this.declaration;
        return annotationUseSiteTarget == ((!(ktNamedDeclaration instanceof KtProperty) || !((KtProperty) ktNamedDeclaration).hasDelegate()) ? AnnotationUseSiteTarget.FIELD : AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [org.jetbrains.kotlin.asJava.elements.KtLightElement] */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightFieldModifierList copy() {
        return new KtUltraLightFieldModifierList(getSupport(), this.declaration, getOwner(), this.modifiers);
    }
}
