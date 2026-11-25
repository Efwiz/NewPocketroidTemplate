package org.jetbrains.kotlin.asJava.elements;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtLightAnnotationsValues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B/\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000fH\u0016¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0006H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightPsiArrayInitializerMemberValue;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiArrayInitializerMemberValue;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "lightParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "arguments", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function1;)V", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getInitializers", "", "()[Lcom/intellij/psi/PsiAnnotationMemberValue;", "getParent", "isPhysical", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightPsiArrayInitializerMemberValue extends KtLightElementBase implements PsiArrayInitializerMemberValue {
    private final Function1<KtLightPsiArrayInitializerMemberValue, List<PsiAnnotationMemberValue>> arguments;
    private final KtElement kotlinOrigin;
    private final PsiElement lightParent;

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isPhysical() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtElement getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtLightPsiArrayInitializerMemberValue(KtElement kotlinOrigin, PsiElement lightParent, Function1<? super KtLightPsiArrayInitializerMemberValue, ? extends List<? extends PsiAnnotationMemberValue>> arguments) {
        super(lightParent);
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        Intrinsics.checkNotNullParameter(lightParent, "lightParent");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.kotlinOrigin = kotlinOrigin;
        this.lightParent = lightParent;
        this.arguments = arguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue
    public PsiAnnotationMemberValue[] getInitializers() {
        return (PsiAnnotationMemberValue[]) this.arguments.invoke(this).toArray(new PsiAnnotationMemberValue[0]);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent, reason: from getter */
    public PsiElement getLightParent() {
        return this.lightParent;
    }
}
