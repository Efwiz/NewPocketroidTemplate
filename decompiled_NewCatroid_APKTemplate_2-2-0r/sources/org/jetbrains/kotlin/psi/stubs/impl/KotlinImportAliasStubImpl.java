package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.psi.KtImportAlias;
import org.jetbrains.kotlin.psi.stubs.KotlinImportAliasStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtImportAliasElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinImportAliasStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B!\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\n\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinImportAliasStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtImportAlias;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinImportAliasStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "name", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;)V", "getName", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinImportAliasStubImpl extends KotlinStubBaseImpl<KtImportAlias> implements KotlinImportAliasStub {
    private final StringRef name;

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinImportAliasStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef) {
        KtImportAliasElementType IMPORT_ALIAS = KtStubElementTypes.IMPORT_ALIAS;
        Intrinsics.checkNotNullExpressionValue(IMPORT_ALIAS, "IMPORT_ALIAS");
        super(stubElement, IMPORT_ALIAS);
        this.name = stringRef;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinImportAliasStub
    public String getName() {
        return StringRef.toString(this.name);
    }
}
