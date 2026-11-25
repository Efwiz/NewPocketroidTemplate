package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.stubs.KotlinClassLiteralExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtClassLiteralExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinClassLiteralExpressionStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0017\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinClassLiteralExpressionStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassLiteralExpressionStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/stubs/StubElement;)V", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassLiteralExpressionStubImpl extends KotlinStubBaseImpl<KtClassLiteralExpression> implements KotlinClassLiteralExpressionStub {
    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinClassLiteralExpressionStubImpl(StubElement<? extends PsiElement> stubElement) {
        KtClassLiteralExpressionElementType CLASS_LITERAL_EXPRESSION = KtStubElementTypes.CLASS_LITERAL_EXPRESSION;
        Intrinsics.checkNotNullExpressionValue(CLASS_LITERAL_EXPRESSION, "CLASS_LITERAL_EXPRESSION");
        super(stubElement, CLASS_LITERAL_EXPRESSION);
    }
}
