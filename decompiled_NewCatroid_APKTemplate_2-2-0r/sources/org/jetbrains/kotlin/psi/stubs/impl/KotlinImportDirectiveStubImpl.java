package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.stubs.KotlinImportDirectiveStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtImportDirectiveElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinImportDirectiveStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B+\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bJ\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\n\u001a\u00020\u0007H\u0016R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinImportDirectiveStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinImportDirectiveStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "isAllUnder", "", "importedFqName", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "isValid", "(Lcom/intellij/psi/stubs/StubElement;ZLcom/intellij/util/io/StringRef;Z)V", "getImportedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinImportDirectiveStubImpl extends KotlinStubBaseImpl<KtImportDirective> implements KotlinImportDirectiveStub {
    private final StringRef importedFqName;
    private final boolean isAllUnder;
    private final boolean isValid;

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinImportDirectiveStubImpl(StubElement<?> parent, boolean z, StringRef stringRef, boolean z2) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        KtImportDirectiveElementType IMPORT_DIRECTIVE = KtStubElementTypes.IMPORT_DIRECTIVE;
        Intrinsics.checkNotNullExpressionValue(IMPORT_DIRECTIVE, "IMPORT_DIRECTIVE");
        super(parent, IMPORT_DIRECTIVE);
        this.isAllUnder = z;
        this.importedFqName = stringRef;
        this.isValid = z2;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinImportDirectiveStub
    /* JADX INFO: renamed from: isAllUnder, reason: from getter */
    public boolean getIsAllUnder() {
        return this.isAllUnder;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinImportDirectiveStub
    public FqName getImportedFqName() {
        String string = StringRef.toString(this.importedFqName);
        if (string != null) {
            return new FqName(string);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinImportDirectiveStub
    /* JADX INFO: renamed from: isValid, reason: from getter */
    public boolean getIsValid() {
        return this.isValid;
    }
}
