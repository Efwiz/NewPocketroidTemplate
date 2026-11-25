package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.stubs.KotlinScriptStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtScriptElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinScriptStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B!\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinScriptStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtScript;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinScriptStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "_fqName", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;)V", "getFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getName", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinScriptStubImpl extends KotlinStubBaseImpl<KtScript> implements KotlinScriptStub {
    private final StringRef _fqName;

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinScriptStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef) {
        KtScriptElementType SCRIPT = KtStubElementTypes.SCRIPT;
        Intrinsics.checkNotNullExpressionValue(SCRIPT, "SCRIPT");
        super(stubElement, SCRIPT);
        this._fqName = stringRef;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        String strAsString = getFqName().shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinScriptStub, org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public FqName getFqName() {
        String string = StringRef.toString(this._fqName);
        Intrinsics.checkNotNull(string);
        return new FqName(string);
    }
}
