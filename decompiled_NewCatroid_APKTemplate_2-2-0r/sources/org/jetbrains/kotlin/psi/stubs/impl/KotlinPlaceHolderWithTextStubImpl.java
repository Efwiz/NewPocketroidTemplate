package org.jetbrains.kotlin.psi.stubs.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderWithTextStub;

/* JADX INFO: compiled from: KotlinPlaceHolderWithTextStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000*\u0014\b\u0000\u0010\u0001*\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005B)\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u000e\u0010\u0007\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinPlaceHolderWithTextStubImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderWithTextStub;", "parent", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IStubElementType;", "text", "", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/psi/stubs/IStubElementType;Ljava/lang/String;)V", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinPlaceHolderWithTextStubImpl<T extends KtElementImplStub<? extends StubElement<?>>> extends KotlinStubBaseImpl<T> implements KotlinPlaceHolderWithTextStub<T> {
    private final String text;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinPlaceHolderWithTextStubImpl(StubElement<?> parent, IStubElementType<?, ?> elementType, String text) {
        super(parent, elementType);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderWithTextStub
    /* JADX INFO: renamed from: text, reason: from getter */
    public String getText() {
        return this.text;
    }
}
