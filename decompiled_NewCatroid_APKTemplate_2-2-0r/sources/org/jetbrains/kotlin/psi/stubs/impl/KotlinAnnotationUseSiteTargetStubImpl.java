package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.stubs.KotlinAnnotationUseSiteTargetStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtAnnotationUseSiteTargetElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinAnnotationUseSiteStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u001f\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinAnnotationUseSiteTargetStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtAnnotationUseSiteTarget;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinAnnotationUseSiteTargetStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "target", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;)V", "getUseSiteTarget", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinAnnotationUseSiteTargetStubImpl extends KotlinStubBaseImpl<KtAnnotationUseSiteTarget> implements KotlinAnnotationUseSiteTargetStub {
    private final StringRef target;

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinAnnotationUseSiteTargetStubImpl(StubElement<? extends PsiElement> stubElement, StringRef target) {
        Intrinsics.checkNotNullParameter(target, "target");
        KtAnnotationUseSiteTargetElementType ANNOTATION_TARGET = KtStubElementTypes.ANNOTATION_TARGET;
        Intrinsics.checkNotNullExpressionValue(ANNOTATION_TARGET, "ANNOTATION_TARGET");
        super(stubElement, ANNOTATION_TARGET);
        this.target = target;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinAnnotationUseSiteTargetStub
    public String getUseSiteTarget() {
        return this.target.getString();
    }
}
