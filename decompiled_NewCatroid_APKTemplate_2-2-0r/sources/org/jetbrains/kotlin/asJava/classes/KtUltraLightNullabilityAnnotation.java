package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightNullabilityAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: ultraLightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00020\u0001B\u001d\u0012\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightNullabilityAnnotation;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightNullabilityAnnotation;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotation;", "member", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotation;Lcom/intellij/psi/PsiElement;)V", "getQualifiedName", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightNullabilityAnnotation extends KtLightNullabilityAnnotation<KtUltraLightElementWithNullabilityAnnotation<?, ?>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightNullabilityAnnotation(KtUltraLightElementWithNullabilityAnnotation<?, ?> member, PsiElement parent) {
        super(member, parent);
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(parent, "parent");
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightNullabilityAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public String getQualifiedName() {
        return getMember().getQualifiedNameForNullabilityAnnotation();
    }
}
