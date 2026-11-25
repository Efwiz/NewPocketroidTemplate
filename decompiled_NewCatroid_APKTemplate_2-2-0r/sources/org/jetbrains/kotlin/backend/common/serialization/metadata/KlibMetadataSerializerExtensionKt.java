package org.jetbrains.kotlin.backend.common.serialization.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.kdoc.psi.api.KDoc;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: KlibMetadataSerializerExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"findKDocString", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataSerializerExtensionKt {
    public static final String findKDocString(DeclarationDescriptorWithSource declarationDescriptorWithSource) {
        KDoc docComment;
        Intrinsics.checkNotNullParameter(declarationDescriptorWithSource, "<this>");
        SourceElement source = declarationDescriptorWithSource.getSource();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        if (!(psi instanceof KtDeclaration) || (psi instanceof KtPrimaryConstructor) || (docComment = ((KtDeclaration) psi).getDocComment()) == null) {
            return null;
        }
        return docComment.getDefaultSection().getLightParent().getText();
    }
}
