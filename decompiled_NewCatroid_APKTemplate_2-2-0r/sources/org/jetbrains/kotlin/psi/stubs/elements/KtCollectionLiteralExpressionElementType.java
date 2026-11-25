package org.jetbrains.kotlin.psi.stubs.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.stubs.KotlinCollectionLiteralExpressionStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinCollectionLiteralExpressionStubImpl;

/* JADX INFO: compiled from: KtCollectionLiteralExpressionElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u001e\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0011H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtCollectionLiteralExpressionElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinCollectionLiteralExpressionStub;", "Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "debugName", "", "(Ljava/lang/String;)V", "createStub", "psi", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serialize", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtCollectionLiteralExpressionElementType extends KtStubElementType<KotlinCollectionLiteralExpressionStub, KtCollectionLiteralExpression> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinCollectionLiteralExpressionStub stub, StubOutputStream dataStream) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtCollectionLiteralExpression) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtCollectionLiteralExpressionElementType(String debugName) {
        super(debugName, KtCollectionLiteralExpression.class, KotlinCollectionLiteralExpressionStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinCollectionLiteralExpressionStub deserialize(StubInputStream dataStream, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        return new KotlinCollectionLiteralExpressionStubImpl(parentStub);
    }

    public KotlinCollectionLiteralExpressionStub createStub(KtCollectionLiteralExpression psi, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        return new KotlinCollectionLiteralExpressionStubImpl(parentStub);
    }
}
