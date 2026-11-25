package org.jetbrains.kotlin.psi.stubs.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.stubs.KotlinObjectStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtObjectElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinObjectStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003Bc\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0010¢\u0006\u0002\u0010\u0014J\n\u0010\u0016\u001a\u0004\u0018\u00010\fH\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\nH\u0016J\u0010\u0010\u0018\u001a\n \u001a*\u0004\u0018\u00010\u00190\u0019H\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u0010H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0015¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinObjectStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinObjectStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "name", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "superNames", "", "isTopLevel", "", "isDefault", "isLocal", "isObjectLiteral", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/ClassId;[Lcom/intellij/util/io/StringRef;ZZZZ)V", "[Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "getClassId", "getFqName", "getName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getSuperNames", "", "isCompanion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinObjectStubImpl extends KotlinStubBaseImpl<KtObjectDeclaration> implements KotlinObjectStub {
    private final ClassId classId;
    private final FqName fqName;
    private final boolean isDefault;
    private final boolean isLocal;
    private final boolean isObjectLiteral;
    private final boolean isTopLevel;
    private final StringRef name;
    private final StringRef[] superNames;

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinObjectStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef, FqName fqName, ClassId classId, StringRef[] superNames, boolean z, boolean z2, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(superNames, "superNames");
        KtObjectElementType OBJECT_DECLARATION = KtStubElementTypes.OBJECT_DECLARATION;
        Intrinsics.checkNotNullExpressionValue(OBJECT_DECLARATION, "OBJECT_DECLARATION");
        super(stubElement, OBJECT_DECLARATION);
        this.name = stringRef;
        this.fqName = fqName;
        this.classId = classId;
        this.superNames = superNames;
        this.isTopLevel = z;
        this.isDefault = z2;
        this.isLocal = z3;
        this.isObjectLiteral = z4;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public FqName getFqName() {
        return this.fqName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.name);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub
    public List<String> getSuperNames() {
        StringRef[] stringRefArr = this.superNames;
        ArrayList arrayList = new ArrayList(stringRefArr.length);
        for (StringRef stringRef : stringRefArr) {
            arrayList.add(stringRef.toString());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub
    /* JADX INFO: renamed from: isTopLevel, reason: from getter */
    public boolean getIsTopLevel() {
        return this.isTopLevel;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinObjectStub
    /* JADX INFO: renamed from: isCompanion, reason: from getter */
    public boolean getIsDefault() {
        return this.isDefault;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinObjectStub
    /* JADX INFO: renamed from: isObjectLiteral, reason: from getter */
    public boolean getIsObjectLiteral() {
        return this.isObjectLiteral;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub
    /* JADX INFO: renamed from: isLocal, reason: from getter */
    public boolean getIsLocal() {
        return this.isLocal;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassifierStub
    public ClassId getClassId() {
        return this.classId;
    }
}
