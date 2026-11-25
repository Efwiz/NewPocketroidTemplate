package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: compiled from: KtUltraLightClassForInterfaceDefaultImpls.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\bH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightReceiverParameterForDefaultImpls;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameter;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "method", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "typeGetter", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "(Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;Lkotlin/jvm/functions/Function0;)V", "qualifiedNameForNullabilityAnnotation", "", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", "getType", "isVarArgs", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightReceiverParameterForDefaultImpls extends KtUltraLightParameter {
    private final String qualifiedNameForNullabilityAnnotation;
    private final Function0<PsiType> typeGetter;

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtUltraLightReceiverParameterForDefaultImpls(KtUltraLightSupport support, KtUltraLightMethod method, Function0<? extends PsiType> typeGetter) {
        super("$this", null, support, method);
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(typeGetter, "typeGetter");
        this.typeGetter = typeGetter;
        String name2 = NotNull.class.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        this.qualifiedNameForNullabilityAnnotation = name2;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        return this.typeGetter.invoke();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public String getQualifiedNameForNullabilityAnnotation() {
        return this.qualifiedNameForNullabilityAnnotation;
    }
}
