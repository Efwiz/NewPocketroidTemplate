package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.stubs.KotlinScriptStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinScriptStubImpl;

/* JADX INFO: compiled from: KtScriptElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00032\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\nH\u0016J\u001e\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0015H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtScriptElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinScriptStub;", "Lorg/jetbrains/kotlin/psi/KtScript;", "debugName", "", "(Ljava/lang/String;)V", "createStub", "psi", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "indexStub", "", "stub", "sink", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IndexSink;", "serialize", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtScriptElementType extends KtStubElementType<KotlinScriptStub, KtScript> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtScript) psiElement, (StubElement<? extends PsiElement>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtScriptElementType(String debugName) {
        super(debugName, KtScript.class, KotlinScriptStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    public KotlinScriptStub createStub(KtScript psi, StubElement<? extends PsiElement> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        return new KotlinScriptStubImpl(parentStub, StringRef.fromString(psi.mo7391getFqName().asString()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinScriptStub stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeName(stub.getFqName().asString());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinScriptStub deserialize(StubInputStream dataStream, StubElement<PsiElement> parentStub) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        return new KotlinScriptStubImpl(parentStub, dataStream.readName());
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinScriptStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
        StubIndexService.INSTANCE.getInstance().indexScript(stub, sink);
    }
}
