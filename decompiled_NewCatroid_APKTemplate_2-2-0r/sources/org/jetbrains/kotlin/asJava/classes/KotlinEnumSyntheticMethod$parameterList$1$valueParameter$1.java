package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.KtLightParameter;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"org/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod$parameterList$1$valueParameter$1", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameter;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightParameter;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtParameter;", "method", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "getMethod", "()Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getText", "", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinEnumSyntheticMethod$parameterList$1$valueParameter$1 extends LightParameter implements KtLightParameter {
    final /* synthetic */ KotlinEnumSyntheticMethod this$0;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtParameter getKotlinOrigin() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KotlinEnumSyntheticMethod$parameterList$1$valueParameter$1(PsiClassType psiClassType, LightParameterListBuilder lightParameterListBuilder, KotlinEnumSyntheticMethod kotlinEnumSyntheticMethod, String str, Language language) {
        super(str, (PsiType) psiClassType, (PsiElement) lightParameterListBuilder, language, false);
        this.this$0 = kotlinEnumSyntheticMethod;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightParameter
    public KtLightMethod getMethod() {
        return this.this$0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.this$0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this.this$0.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        String name2 = getMyName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        TextRange EMPTY_RANGE = TextRange.EMPTY_RANGE;
        Intrinsics.checkNotNullExpressionValue(EMPTY_RANGE, "EMPTY_RANGE");
        return EMPTY_RANGE;
    }
}
