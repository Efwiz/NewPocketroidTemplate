package org.jetbrains.kotlin.psi.stubs.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtConstructorElementType;

/* JADX INFO: compiled from: KotlinConstructorStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B?\u0012\u0010\u0010\u0005\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0007\u0018\u00010\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r¢\u0006\u0002\u0010\u000fJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0012\u001a\n \u0014*\u0004\u0018\u00010\u00130\u0013H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u0015\u001a\u00020\rH\u0016J\b\u0010\u0016\u001a\u00020\rH\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinConstructorStubImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtConstructor;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "elementType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstructorElementType;", "containingClassName", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "hasBody", "", "isDelegatedCallToThis", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstructorElementType;Lcom/intellij/util/io/StringRef;ZZ)V", "getFqName", "", "getName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "isExtension", "isTopLevel", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinConstructorStubImpl<T extends KtConstructor<T>> extends KotlinStubBaseImpl<T> implements KotlinConstructorStub<T> {
    private final StringRef containingClassName;
    private final boolean hasBody;
    private final boolean isDelegatedCallToThis;

    public Void getFqName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinCallableStubBase
    public boolean isExtension() {
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinCallableStubBase
    public boolean isTopLevel() {
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public /* bridge */ /* synthetic */ FqName getFqName() {
        return (FqName) getFqName();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinConstructorStubImpl(StubElement<? extends PsiElement> stubElement, KtConstructorElementType<T> elementType, StringRef stringRef, boolean z, boolean z2) {
        super(stubElement, elementType);
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        this.containingClassName = stringRef;
        this.hasBody = z;
        this.isDelegatedCallToThis = z2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.containingClassName);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub
    /* JADX INFO: renamed from: hasBody, reason: from getter */
    public boolean getHasBody() {
        return this.hasBody;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub
    /* JADX INFO: renamed from: isDelegatedCallToThis, reason: from getter */
    public boolean getIsDelegatedCallToThis() {
        return this.isDelegatedCallToThis;
    }
}
