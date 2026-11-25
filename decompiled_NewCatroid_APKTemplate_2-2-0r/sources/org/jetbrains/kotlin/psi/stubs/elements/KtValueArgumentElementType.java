package org.jetbrains.kotlin.psi.stubs.elements;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.stubs.KotlinValueArgumentStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinValueArgumentStubImpl;

/* JADX INFO: compiled from: KtValueArgumentElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0004\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tJ-\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u000b\u001a\u00028\u00002\u0010\u0010\f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000e\u0018\u00010\rH\u0016¢\u0006\u0002\u0010\u000fJ&\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rH\u0016J\u001e\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0011\u001a\u00020\u0016H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtValueArgumentElementType;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinValueArgumentStub;", "debugName", "", "psiClass", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)V", "createStub", "psi", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/psi/KtValueArgument;Lcom/intellij/psi/stubs/StubElement;)Lorg/jetbrains/kotlin/psi/stubs/KotlinValueArgumentStub;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serialize", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtValueArgumentElementType<T extends KtValueArgument> extends KtStubElementType<KotlinValueArgumentStub<T>, T> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtValueArgument) psiElement, (StubElement<? extends PsiElement>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtValueArgumentElementType(String debugName, Class<T> psiClass) {
        super(debugName, psiClass, KotlinValueArgumentStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(psiClass, "psiClass");
    }

    public KotlinValueArgumentStub<T> createStub(T psi, StubElement<? extends PsiElement> stubElement) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        return new KotlinValueArgumentStubImpl(stubElement, this, psi.isSpread());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinValueArgumentStub<T> stub, StubOutputStream dataStream) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeBoolean(stub.getIsSpread());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinValueArgumentStub<T> deserialize(StubInputStream dataStream, StubElement<PsiElement> parentStub) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        return new KotlinValueArgumentStubImpl(parentStub, this, dataStream.readBoolean());
    }
}
