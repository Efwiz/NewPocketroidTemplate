package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.stubs.KotlinContextReceiverStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtContextReceiverElementType;

/* JADX INFO: compiled from: KotlinContextReceiverStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B)\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\n\u0010\f\u001a\u0004\u0018\u00010\nH\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinContextReceiverStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinContextReceiverStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "elementType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtContextReceiverElementType;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/psi/stubs/elements/KtContextReceiverElementType;Ljava/lang/String;)V", "getLabel", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinContextReceiverStubImpl extends KotlinStubBaseImpl<KtContextReceiver> implements KotlinContextReceiverStub {
    private final String label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinContextReceiverStubImpl(StubElement<? extends PsiElement> stubElement, KtContextReceiverElementType elementType, String str) {
        super(stubElement, elementType);
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        this.label = str;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinContextReceiverStub
    public String getLabel() {
        return this.label;
    }
}
