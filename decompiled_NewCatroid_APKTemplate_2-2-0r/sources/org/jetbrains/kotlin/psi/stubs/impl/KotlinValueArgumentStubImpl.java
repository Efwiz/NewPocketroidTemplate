package org.jetbrains.kotlin.psi.stubs.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.stubs.KotlinValueArgumentStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtValueArgumentElementType;

/* JADX INFO: compiled from: KotlinValueArgumentStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B-\u0012\u0010\u0010\u0005\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0007\u0018\u00010\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinValueArgumentStubImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinPlaceHolderStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinValueArgumentStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "elementType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtValueArgumentElementType;", "isSpread", "", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/psi/stubs/elements/KtValueArgumentElementType;Z)V", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinValueArgumentStubImpl<T extends KtValueArgument> extends KotlinPlaceHolderStubImpl<T> implements KotlinValueArgumentStub<T> {
    private final boolean isSpread;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinValueArgumentStubImpl(StubElement<? extends PsiElement> stubElement, KtValueArgumentElementType<T> elementType, boolean z) {
        super(stubElement, elementType);
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        this.isSpread = z;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinValueArgumentStub
    /* JADX INFO: renamed from: isSpread, reason: from getter */
    public boolean getIsSpread() {
        return this.isSpread;
    }
}
