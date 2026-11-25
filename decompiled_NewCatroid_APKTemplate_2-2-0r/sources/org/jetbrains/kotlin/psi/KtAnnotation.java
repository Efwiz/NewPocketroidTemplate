package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtAnnotation extends KtElementImplStub<KotlinPlaceHolderStub<KtAnnotation>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "entry";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtAnnotation";
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "removeEntry";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAnnotation(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    public KtAnnotation(KotlinPlaceHolderStub<KtAnnotation> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.ANNOTATION);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitAnnotation(this, d);
    }

    public List<KtAnnotationEntry> getEntries() {
        return getStubOrPsiChildrenAsList(KtStubElementTypes.ANNOTATION_ENTRY);
    }

    public KtAnnotationUseSiteTarget getUseSiteTarget() {
        return (KtAnnotationUseSiteTarget) getStubOrPsiChild(KtStubElementTypes.ANNOTATION_TARGET);
    }

    public void removeEntry(KtAnnotationEntry ktAnnotationEntry) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(2);
        }
        if (getEntries().size() > 1) {
            ktAnnotationEntry.delete();
        } else {
            delete();
        }
    }
}
