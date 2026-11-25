package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.kdoc.psi.impl.KDocName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KDocReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KDocReference;", "Lorg/jetbrains/kotlin/idea/references/KtMultiReference;", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocName;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocName;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "canRename", "", "getCanonicalText", "", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "resolve", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KDocReference extends KtMultiReference<KDocName> {
    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference
    public boolean canRename() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KDocReference(KDocName element) {
        super(element);
        Intrinsics.checkNotNullParameter(element, "element");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return ((KDocName) getPsiElement()).getNameTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        ResolveResult resolveResult = (ResolveResult) ArraysKt.firstOrNull(multiResolve(false));
        if (resolveResult != null) {
            return resolveResult.getElement();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        return ((KDocName) getPsiElement()).getNameText();
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        Name nameIdentifier = Name.identifier(((KDocName) getPsiElement()).getNameText());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return CollectionsKt.listOf(nameIdentifier);
    }
}
