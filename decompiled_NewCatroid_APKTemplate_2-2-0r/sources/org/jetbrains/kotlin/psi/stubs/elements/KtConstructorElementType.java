package org.jetbrains.kotlin.psi.stubs.elements;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub;

/* JADX INFO: compiled from: KtConstructorElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\b&\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0004\u0012\u0004\u0012\u0002H\u00010\u0003B/\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0010\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\b¢\u0006\u0002\u0010\nJ'\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\f\u001a\u00028\u00002\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016¢\u0006\u0002\u0010\u000fJ\"\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\u001e\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0015\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u001bJ4\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0019H$J\u001e\u0010 \u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0011\u001a\u00020!H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstructorElementType;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtConstructor;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;", "debugName", "", "tClass", "Ljava/lang/Class;", "stubClass", "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Class;)V", "createStub", "psi", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "(Lorg/jetbrains/kotlin/psi/KtConstructor;Lcom/intellij/psi/stubs/StubElement;)Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "indexStub", "", "stub", "sink", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IndexSink;", "isDelegatedCallToThis", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "(Lorg/jetbrains/kotlin/psi/KtConstructor;)Z", "newStub", "nameRef", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "hasBody", "serialize", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtConstructorElementType<T extends KtConstructor<T>> extends KtStubElementType<KotlinConstructorStub<T>, T> {
    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinConstructorStub<T> stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    protected abstract boolean isDelegatedCallToThis(T constructor);

    protected abstract KotlinConstructorStub<T> newStub(StubElement<?> parentStub, StringRef nameRef, boolean hasBody, boolean isDelegatedCallToThis);

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtConstructor) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtConstructorElementType(String debugName, Class<T> tClass, Class<KotlinConstructorStub<?>> stubClass) {
        super(debugName, tClass, stubClass);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(tClass, "tClass");
        Intrinsics.checkNotNullParameter(stubClass, "stubClass");
    }

    public KotlinConstructorStub<T> createStub(T psi, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        return newStub(parentStub, StringRef.fromString(psi.getName()), psi.hasBody(), isDelegatedCallToThis(psi));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinConstructorStub<T> stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeName(stub.getName());
        dataStream.writeBoolean(stub.hasBody());
        dataStream.writeBoolean(stub.isDelegatedCallToThis());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinConstructorStub<T> deserialize(StubInputStream dataStream, StubElement<?> parentStub) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        return newStub(parentStub, dataStream.readName(), dataStream.readBoolean(), dataStream.readBoolean());
    }
}
