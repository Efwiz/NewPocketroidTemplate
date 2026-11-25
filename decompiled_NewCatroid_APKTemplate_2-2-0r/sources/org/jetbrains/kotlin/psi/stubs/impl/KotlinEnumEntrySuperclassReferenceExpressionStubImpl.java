package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtEnumEntrySuperclassReferenceExpression;
import org.jetbrains.kotlin.psi.stubs.KotlinEnumEntrySuperclassReferenceExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtEnumEntrySuperClassReferenceExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinEnumEntrySuperclassReferenceExpressionStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0019\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinEnumEntrySuperclassReferenceExpressionStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtEnumEntrySuperclassReferenceExpression;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinEnumEntrySuperclassReferenceExpressionStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "referencedName", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;)V", "getReferencedName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinEnumEntrySuperclassReferenceExpressionStubImpl extends KotlinStubBaseImpl<KtEnumEntrySuperclassReferenceExpression> implements KotlinEnumEntrySuperclassReferenceExpressionStub {
    private final StringRef referencedName;

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinEnumEntrySuperclassReferenceExpressionStubImpl(StubElement<?> parent, StringRef referencedName) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(referencedName, "referencedName");
        KtEnumEntrySuperClassReferenceExpressionElementType ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION = KtStubElementTypes.ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION;
        Intrinsics.checkNotNullExpressionValue(ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION, "ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION");
        super(parent, ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION);
        this.referencedName = referencedName;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinEnumEntrySuperclassReferenceExpressionStub
    public String getReferencedName() {
        return this.referencedName.getString();
    }
}
