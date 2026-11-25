package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.psi.KtCallElement;

/* JADX INFO: compiled from: KtLightAbstractAnnotation.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u00022\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\n\u0010\f\u001a\u0004\u0018\u00010\rH&J\n\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H&¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)V", "fqNameMatches", "", "fqName", "", "getNameReferenceElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "getOwner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationOwner;", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationParameterList;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightAbstractAnnotation extends KtLightElementBase implements PsiAnnotation, KtLightElement<KtCallElement, PsiAnnotation> {
    public abstract PsiJavaCodeReferenceElement getNameReferenceElement();

    public abstract PsiAnnotationParameterList getParameterList();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightAbstractAnnotation(PsiElement parent) {
        super(parent);
        Intrinsics.checkNotNullParameter(parent, "parent");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationOwner getOwner() {
        PsiElement parent = getLightParent();
        if (parent instanceof PsiAnnotationOwner) {
            return (PsiAnnotationOwner) parent;
        }
        return null;
    }

    public boolean fqNameMatches(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return Intrinsics.areEqual(getQualifiedName(), fqName);
    }
}
