package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtPropertyElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinPropertyStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B{\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\n\u0012\u0006\u0010\u000e\u001a\u00020\n\u0012\u0006\u0010\u000f\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\n\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\f\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0014\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\u0002\u0010\u0017J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0012H\u0016J\u0010\u0010\u001d\u001a\n \u001f*\u0004\u0018\u00010\u001e0\u001eH\u0016J\b\u0010\f\u001a\u00020\nH\u0016J\b\u0010\r\u001a\u00020\nH\u0016J\b\u0010\u000e\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0017\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinPropertyStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtProperty;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPropertyStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "name", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "isVar", "", "isTopLevel", "hasDelegate", "hasDelegateExpression", "hasInitializer", "isExtension", "hasReturnTypeRef", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "constantInitializer", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "origin", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;ZZZZZZZLorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/constant/ConstantValue;Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;)V", "getConstantInitializer", "()Lorg/jetbrains/kotlin/constant/ConstantValue;", "getOrigin", "()Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "getFqName", "getName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinPropertyStubImpl extends KotlinStubBaseImpl<KtProperty> implements KotlinPropertyStub {
    private final ConstantValue<?> constantInitializer;
    private final FqName fqName;
    private final boolean hasDelegate;
    private final boolean hasDelegateExpression;
    private final boolean hasInitializer;
    private final boolean hasReturnTypeRef;
    private final boolean isExtension;
    private final boolean isTopLevel;
    private final boolean isVar;
    private final StringRef name;
    private final KotlinStubOrigin origin;

    public final ConstantValue<?> getConstantInitializer() {
        return this.constantInitializer;
    }

    public final KotlinStubOrigin getOrigin() {
        return this.origin;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinPropertyStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, FqName fqName, ConstantValue<?> constantValue, KotlinStubOrigin kotlinStubOrigin) {
        KtPropertyElementType PROPERTY = KtStubElementTypes.PROPERTY;
        Intrinsics.checkNotNullExpressionValue(PROPERTY, "PROPERTY");
        super(stubElement, PROPERTY);
        this.name = stringRef;
        this.isVar = z;
        this.isTopLevel = z2;
        this.hasDelegate = z3;
        this.hasDelegateExpression = z4;
        this.hasInitializer = z5;
        this.isExtension = z6;
        this.hasReturnTypeRef = z7;
        this.fqName = fqName;
        this.constantInitializer = constantValue;
        this.origin = kotlinStubOrigin;
        if (z2 && fqName == null) {
            throw new IllegalArgumentException("fqName shouldn't be null for top level properties");
        }
        if (z4 && !z3) {
            throw new IllegalArgumentException("Can't have delegate expression without delegate");
        }
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public FqName getFqName() {
        return this.fqName;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub
    /* JADX INFO: renamed from: isVar, reason: from getter */
    public boolean getIsVar() {
        return this.isVar;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinCallableStubBase
    /* JADX INFO: renamed from: isTopLevel, reason: from getter */
    public boolean getIsTopLevel() {
        return this.isTopLevel;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub
    /* JADX INFO: renamed from: hasDelegate, reason: from getter */
    public boolean getHasDelegate() {
        return this.hasDelegate;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub
    /* JADX INFO: renamed from: hasDelegateExpression, reason: from getter */
    public boolean getHasDelegateExpression() {
        return this.hasDelegateExpression;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub
    /* JADX INFO: renamed from: hasInitializer, reason: from getter */
    public boolean getHasInitializer() {
        return this.hasInitializer;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinCallableStubBase
    /* JADX INFO: renamed from: isExtension, reason: from getter */
    public boolean getIsExtension() {
        return this.isExtension;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub
    /* JADX INFO: renamed from: hasReturnTypeRef, reason: from getter */
    public boolean getHasReturnTypeRef() {
        return this.hasReturnTypeRef;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.name);
    }
}
