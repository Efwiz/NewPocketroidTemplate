package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeAliasStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTypeAliasElementType;

/* JADX INFO: compiled from: KotlinTypeAliasStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B=\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\n\u0010\u000f\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeAliasStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinTypeAliasStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "name", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "qualifiedName", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "isTopLevel", "", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;Lcom/intellij/util/io/StringRef;Lorg/jetbrains/kotlin/name/ClassId;Z)V", "getClassId", "getFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getName", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeAliasStubImpl extends KotlinStubBaseImpl<KtTypeAlias> implements KotlinTypeAliasStub {
    private final ClassId classId;
    private final boolean isTopLevel;
    private final StringRef name;
    private final StringRef qualifiedName;

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinTypeAliasStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef, StringRef stringRef2, ClassId classId, boolean z) {
        KtTypeAliasElementType TYPEALIAS = KtStubElementTypes.TYPEALIAS;
        Intrinsics.checkNotNullExpressionValue(TYPEALIAS, "TYPEALIAS");
        super(stubElement, TYPEALIAS);
        this.name = stringRef;
        this.qualifiedName = stringRef2;
        this.classId = classId;
        this.isTopLevel = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.name);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public FqName getFqName() {
        String string = StringRef.toString(this.qualifiedName);
        if (string != null) {
            return new FqName(string);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassifierStub
    public ClassId getClassId() {
        return this.classId;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinTypeAliasStub
    /* JADX INFO: renamed from: isTopLevel, reason: from getter */
    public boolean getIsTopLevel() {
        return this.isTopLevel;
    }
}
