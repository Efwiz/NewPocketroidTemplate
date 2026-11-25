package org.jetbrains.kotlin.psi.stubs.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstructorStubImpl;

/* JADX INFO: compiled from: KtPrimaryConstructorElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002H\u0014J4\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007H\u0014¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtPrimaryConstructorElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstructorElementType;", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "debugName", "", "(Ljava/lang/String;)V", "isDelegatedCallToThis", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "newStub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "nameRef", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "hasBody", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtPrimaryConstructorElementType extends KtConstructorElementType<KtPrimaryConstructor> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtConstructorElementType
    public boolean isDelegatedCallToThis(KtPrimaryConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPrimaryConstructorElementType(String debugName) {
        super(debugName, KtPrimaryConstructor.class, KotlinConstructorStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtConstructorElementType
    protected KotlinConstructorStub<KtPrimaryConstructor> newStub(StubElement<?> parentStub, StringRef nameRef, boolean hasBody, boolean isDelegatedCallToThis) {
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        KtPrimaryConstructorElementType PRIMARY_CONSTRUCTOR = KtStubElementTypes.PRIMARY_CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(PRIMARY_CONSTRUCTOR, "PRIMARY_CONSTRUCTOR");
        return new KotlinConstructorStubImpl(parentStub, PRIMARY_CONSTRUCTOR, nameRef, hasBody, isDelegatedCallToThis);
    }
}
