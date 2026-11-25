package org.jetbrains.kotlin.asJava.classes;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: ultraLightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006¢\u0006\u0002\u0010\tJ\u0013\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0011H\u0016¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0004H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightPsiArrayInitializerMemberValue;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiArrayInitializerMemberValue;", "lightParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "arguments", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "(Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function1;)V", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getLightParent", "()Lcom/intellij/psi/PsiElement;", "getInitializers", "", "()[Lcom/intellij/psi/PsiAnnotationMemberValue;", "getParent", "getText", "", "isPhysical", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KtUltraLightPsiArrayInitializerMemberValue extends KtLightElementBase implements PsiArrayInitializerMemberValue {
    private final Function1<KtUltraLightPsiArrayInitializerMemberValue, List<PsiAnnotationMemberValue>> arguments;
    private final PsiElement lightParent;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtElement getKotlinOrigin() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return false;
    }

    public final PsiElement getLightParent() {
        return this.lightParent;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtUltraLightPsiArrayInitializerMemberValue(PsiElement lightParent, Function1<? super KtUltraLightPsiArrayInitializerMemberValue, ? extends List<? extends PsiAnnotationMemberValue>> arguments) {
        super(lightParent);
        Intrinsics.checkNotNullParameter(lightParent, "lightParent");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.lightParent = lightParent;
        this.arguments = arguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue
    public PsiAnnotationMemberValue[] getInitializers() {
        return (PsiAnnotationMemberValue[]) this.arguments.invoke(this).toArray(new PsiAnnotationMemberValue[0]);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.lightParent;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return "{" + ArraysKt.joinToString$default(getInitializers(), (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<PsiAnnotationMemberValue, CharSequence>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightPsiArrayInitializerMemberValue.getText.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(PsiAnnotationMemberValue it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String text = it.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                return text;
            }
        }, 31, (Object) null) + '}';
    }
}
