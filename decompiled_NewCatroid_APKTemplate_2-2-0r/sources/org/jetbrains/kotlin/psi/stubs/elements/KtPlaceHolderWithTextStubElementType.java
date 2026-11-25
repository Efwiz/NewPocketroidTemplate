package org.jetbrains.kotlin.psi.stubs.elements;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderWithTextStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPlaceHolderWithTextStubImpl;

/* JADX INFO: compiled from: KtPlaceHolderWithTextStubElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u0000*\u0014\b\u0000\u0010\u0001*\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00030\u00022\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0005\u0012\u0004\u0012\u0002H\u00010\u0004B\u001d\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\nJ'\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\f\u001a\u00028\u00002\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¢\u0006\u0002\u0010\u000eJ\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0010\u001a\u00020\u00112\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016J\u001e\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0010\u001a\u00020\u0015H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtPlaceHolderWithTextStubElementType;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderWithTextStub;", "debugName", "", "psiClass", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)V", "createStub", "psi", "parentStub", "(Lorg/jetbrains/kotlin/psi/KtElementImplStub;Lcom/intellij/psi/stubs/StubElement;)Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderWithTextStub;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serialize", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtPlaceHolderWithTextStubElementType<T extends KtElementImplStub<? extends StubElement<?>>> extends KtStubElementType<KotlinPlaceHolderWithTextStub<T>, T> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtElementImplStub) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPlaceHolderWithTextStubElementType(String debugName, Class<T> psiClass) {
        super(debugName, psiClass, KotlinPlaceHolderWithTextStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(psiClass, "psiClass");
    }

    public KotlinPlaceHolderWithTextStub<T> createStub(T psi, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        String text = psi.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return new KotlinPlaceHolderWithTextStubImpl(parentStub, this, text);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinPlaceHolderWithTextStub<T> stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeUTFFast(stub.getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinPlaceHolderWithTextStub<T> deserialize(StubInputStream dataStream, StubElement<?> parentStub) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        String uTFFast = dataStream.readUTFFast();
        Intrinsics.checkNotNullExpressionValue(uTFFast, "readUTFFast(...)");
        return new KotlinPlaceHolderWithTextStubImpl(parentStub, this, uTFFast);
    }
}
