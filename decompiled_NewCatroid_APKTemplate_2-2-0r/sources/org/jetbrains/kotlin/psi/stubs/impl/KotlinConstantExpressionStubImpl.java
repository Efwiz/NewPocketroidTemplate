package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.stubs.ConstantValueKind;
import org.jetbrains.kotlin.psi.stubs.KotlinConstantExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtConstantExpressionElementType;

/* JADX INFO: compiled from: KotlinConstantExpressionStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B/\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\u000eH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinConstantExpressionStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstantExpressionStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "elementType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstantExpressionElementType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/psi/stubs/ConstantValueKind;", "value", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstantExpressionElementType;Lorg/jetbrains/kotlin/psi/stubs/ConstantValueKind;Lcom/intellij/util/io/StringRef;)V", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinConstantExpressionStubImpl extends KotlinStubBaseImpl<KtConstantExpression> implements KotlinConstantExpressionStub {
    private final ConstantValueKind kind;
    private final StringRef value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinConstantExpressionStubImpl(StubElement<? extends PsiElement> stubElement, KtConstantExpressionElementType elementType, ConstantValueKind kind, StringRef value) {
        super(stubElement, elementType);
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(value, "value");
        this.kind = kind;
        this.value = value;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinConstantExpressionStub
    /* JADX INFO: renamed from: kind, reason: from getter */
    public ConstantValueKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinConstantExpressionStub
    public String value() {
        String string = StringRef.toString(this.value);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
