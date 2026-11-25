package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.psi.stubs.elements.KtContextReceiverElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinContextReceiverStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPlaceHolderStubImpl;

/* JADX INFO: compiled from: ContextReceiversListStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/ContextReceiversListStubBuilder;", "", "c", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "(Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;)V", "typeStubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeClsStubBuilder;", "createContextReceiverStubs", "", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "contextReceiverTypes", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContextReceiversListStubBuilder {
    private final TypeClsStubBuilder typeStubBuilder;

    public ContextReceiversListStubBuilder(ClsStubBuilderContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.typeStubBuilder = new TypeClsStubBuilder(c);
    }

    public final void createContextReceiverStubs(StubElement<?> parent, List<ProtoBuf.Type> contextReceiverTypes) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(contextReceiverTypes, "contextReceiverTypes");
        if (contextReceiverTypes.isEmpty()) {
            return;
        }
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.CONTEXT_RECEIVER_LIST);
        for (ProtoBuf.Type type : contextReceiverTypes) {
            KtContextReceiverElementType CONTEXT_RECEIVER = KtStubElementTypes.CONTEXT_RECEIVER;
            Intrinsics.checkNotNullExpressionValue(CONTEXT_RECEIVER, "CONTEXT_RECEIVER");
            TypeClsStubBuilder.createTypeReferenceStub$default(this.typeStubBuilder, new KotlinContextReceiverStubImpl(kotlinPlaceHolderStubImpl, CONTEXT_RECEIVER, null), type, null, null, 12, null);
        }
    }
}
