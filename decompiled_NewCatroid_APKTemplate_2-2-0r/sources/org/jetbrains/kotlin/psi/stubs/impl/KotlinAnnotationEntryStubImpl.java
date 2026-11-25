package org.jetbrains.kotlin.psi.stubs.impl;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.stubs.KotlinAnnotationEntryStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtAnnotationEntryElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinAnnotationEntryStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003BC\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0018\u00010\f¢\u0006\u0002\u0010\u000fJ\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinAnnotationEntryStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinAnnotationEntryStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "shortName", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "hasValueArguments", "", "valueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;ZLjava/util/Map;)V", "getValueArguments", "()Ljava/util/Map;", "getShortName", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinAnnotationEntryStubImpl extends KotlinStubBaseImpl<KtAnnotationEntry> implements KotlinAnnotationEntryStub {
    private final boolean hasValueArguments;
    private final StringRef shortName;
    private final Map<Name, ConstantValue<?>> valueArguments;

    public final Map<Name, ConstantValue<?>> getValueArguments() {
        return this.valueArguments;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    public KotlinAnnotationEntryStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef, boolean z, Map<Name, ? extends ConstantValue<?>> map) {
        KtAnnotationEntryElementType ANNOTATION_ENTRY = KtStubElementTypes.ANNOTATION_ENTRY;
        Intrinsics.checkNotNullExpressionValue(ANNOTATION_ENTRY, "ANNOTATION_ENTRY");
        super(stubElement, ANNOTATION_ENTRY);
        this.shortName = stringRef;
        this.hasValueArguments = z;
        this.valueArguments = map;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinAnnotationEntryStub
    public String getShortName() {
        StringRef stringRef = this.shortName;
        if (stringRef != null) {
            return stringRef.getString();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinAnnotationEntryStub
    /* JADX INFO: renamed from: hasValueArguments, reason: from getter */
    public boolean getHasValueArguments() {
        return this.hasValueArguments;
    }
}
