package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.stubs.KotlinContextReceiverStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinContextReceiverStubImpl;

/* JADX INFO: compiled from: KtContextReceiverElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0012H\u0016¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtContextReceiverElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinContextReceiverStub;", "Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "debugName", "", "(Ljava/lang/String;)V", "createStub", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "deserialize", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinContextReceiverStubImpl;", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serialize", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtContextReceiverElementType extends KtStubElementType<KotlinContextReceiverStub, KtContextReceiver> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtContextReceiver) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContextReceiverElementType(String debugName) {
        super(debugName, KtContextReceiver.class, KotlinContextReceiverStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    public KotlinContextReceiverStub createStub(KtContextReceiver element, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(element, "element");
        return new KotlinContextReceiverStubImpl(parentStub, this, element.labelName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinContextReceiverStub stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeName(stub.getLabel());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinContextReceiverStubImpl deserialize(StubInputStream dataStream, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        return new KotlinContextReceiverStubImpl(parentStub, this, dataStream.readNameString());
    }
}
