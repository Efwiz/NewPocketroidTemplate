package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;

/* JADX INFO: compiled from: KtDestructuringDeclarationReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\u000f\u0010\f\u001a\t\u0018\u00010\r¢\u0006\u0002\b\u000eH\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtDestructuringDeclarationReference;", "Lorg/jetbrains/kotlin/idea/references/KtMultiReference;", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "resolve", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/annotations/Nullable;", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtDestructuringDeclarationReference extends KtMultiReference<KtDestructuringDeclarationEntry> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDestructuringDeclarationReference(KtDestructuringDeclarationEntry element) {
        super(element);
        Intrinsics.checkNotNullParameter(element, "element");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return new TextRange(0, ((KtDestructuringDeclarationEntry) getPsiElement()).getTextLength());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        Object next;
        Iterator it = SequencesKt.map(ArraysKt.asSequence(multiResolve(false)), new Function1<ResolveResult, PsiElement>() { // from class: org.jetbrains.kotlin.idea.references.KtDestructuringDeclarationReference.resolve.1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(ResolveResult it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getElement();
            }
        }).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((PsiElement) next) instanceof KtDestructuringDeclarationEntry) {
                return (PsiElement) next;
            }
        }
        next = null;
        return (PsiElement) next;
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        PsiElement parent = ((KtDestructuringDeclarationEntry) getPsiElement()).getMyParent();
        KtDestructuringDeclaration ktDestructuringDeclaration = parent instanceof KtDestructuringDeclaration ? (KtDestructuringDeclaration) parent : null;
        if (ktDestructuringDeclaration == null) {
            return CollectionsKt.emptyList();
        }
        Name nameIdentifier = Name.identifier("component" + (ktDestructuringDeclaration.getEntries().indexOf(getPsiElement()) + 1));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return CollectionsKt.listOf(nameIdentifier);
    }
}
