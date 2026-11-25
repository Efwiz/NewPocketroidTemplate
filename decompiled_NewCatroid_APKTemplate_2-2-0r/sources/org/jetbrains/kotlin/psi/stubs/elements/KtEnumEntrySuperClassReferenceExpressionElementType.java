package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.psi.KtEnumEntrySuperclassReferenceExpression;
import org.jetbrains.kotlin.psi.stubs.KotlinEnumEntrySuperclassReferenceExpressionStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinEnumEntrySuperclassReferenceExpressionStubImpl;

/* JADX INFO: compiled from: KtEnumEntrySuperClassReferenceExpressionElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00032\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J\u001c\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0011H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtEnumEntrySuperClassReferenceExpressionElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinEnumEntrySuperclassReferenceExpressionStub;", "Lorg/jetbrains/kotlin/psi/KtEnumEntrySuperclassReferenceExpression;", "debugName", "", "(Ljava/lang/String;)V", "createStub", "psi", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serialize", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtEnumEntrySuperClassReferenceExpressionElementType extends KtStubElementType<KotlinEnumEntrySuperclassReferenceExpressionStub, KtEnumEntrySuperclassReferenceExpression> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtEnumEntrySuperclassReferenceExpression) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtEnumEntrySuperClassReferenceExpressionElementType(String debugName) {
        super(debugName, KtEnumEntrySuperclassReferenceExpression.class, KotlinEnumEntrySuperclassReferenceExpressionStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    public KotlinEnumEntrySuperclassReferenceExpressionStub createStub(KtEnumEntrySuperclassReferenceExpression psi, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        StringRef stringRefFromString = StringRef.fromString(psi.getReferencedName());
        Intrinsics.checkNotNull(stringRefFromString);
        return new KotlinEnumEntrySuperclassReferenceExpressionStubImpl(parentStub, stringRefFromString);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinEnumEntrySuperclassReferenceExpressionStub stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeName(stub.getReferencedName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinEnumEntrySuperclassReferenceExpressionStub deserialize(StubInputStream dataStream, StubElement<?> parentStub) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        StringRef name2 = dataStream.readName();
        Intrinsics.checkNotNull(name2);
        return new KotlinEnumEntrySuperclassReferenceExpressionStubImpl(parentStub, name2);
    }
}
