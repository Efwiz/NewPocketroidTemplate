package org.jetbrains.kotlin.psi.stubs.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.stubs.KotlinClassStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtClassElementType;

/* JADX INFO: compiled from: KotlinClassStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003Bk\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0010\u0010\u0006\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\n\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0011\u0012\u0006\u0010\u0014\u001a\u00020\u0011¢\u0006\u0002\u0010\u0015J\n\u0010\u0017\u001a\u0004\u0018\u00010\fH\u0016J\n\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0010\u0010\u001a\u001a\n \u001c*\u0004\u0018\u00010\u001b0\u001bH\u0016J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001eH\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0013\u001a\u00020\u0011H\u0016J\b\u0010\u0014\u001a\u00020\u0011H\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinClassStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtClass;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassStub;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/psi/stubs/elements/KtClassElementType;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "qualifiedName", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "name", "superNames", "", "isInterface", "", "isEnumEntry", "isLocal", "isTopLevel", "(Lorg/jetbrains/kotlin/psi/stubs/elements/KtClassElementType;Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;Lorg/jetbrains/kotlin/name/ClassId;Lcom/intellij/util/io/StringRef;[Lcom/intellij/util/io/StringRef;ZZZZ)V", "[Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "getClassId", "getFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getSuperNames", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassStubImpl extends KotlinStubBaseImpl<KtClass> implements KotlinClassStub {
    private final ClassId classId;
    private final boolean isEnumEntry;
    private final boolean isInterface;
    private final boolean isLocal;
    private final boolean isTopLevel;
    private final StringRef name;
    private final StringRef qualifiedName;
    private final StringRef[] superNames;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinClassStubImpl(KtClassElementType type, StubElement<? extends PsiElement> stubElement, StringRef stringRef, ClassId classId, StringRef stringRef2, StringRef[] superNames, boolean z, boolean z2, boolean z3, boolean z4) {
        super(stubElement, type);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(superNames, "superNames");
        this.qualifiedName = stringRef;
        this.classId = classId;
        this.name = stringRef2;
        this.superNames = superNames;
        this.isInterface = z;
        this.isEnumEntry = z2;
        this.isLocal = z3;
        this.isTopLevel = z4;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public FqName getFqName() {
        String string = StringRef.toString(this.qualifiedName);
        if (string == null) {
            return null;
        }
        return new FqName(string);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassStub
    /* JADX INFO: renamed from: isInterface, reason: from getter */
    public boolean getIsInterface() {
        return this.isInterface;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassStub
    /* JADX INFO: renamed from: isEnumEntry, reason: from getter */
    public boolean getIsEnumEntry() {
        return this.isEnumEntry;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub
    /* JADX INFO: renamed from: isLocal, reason: from getter */
    public boolean getIsLocal() {
        return this.isLocal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.name);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub
    public List<String> getSuperNames() {
        ArrayList arrayList = new ArrayList();
        for (StringRef stringRef : this.superNames) {
            arrayList.add(stringRef.toString());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassifierStub
    public ClassId getClassId() {
        return this.classId;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub
    /* JADX INFO: renamed from: isTopLevel, reason: from getter */
    public boolean getIsTopLevel() {
        return this.isTopLevel;
    }
}
