package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtTypeConstraint extends KtElementImplStub<KotlinPlaceHolderStub<KtTypeConstraint>> implements KtAnnotated, KtAnnotationsContainer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeConstraint";
        } else {
            objArr[0] = "node";
        }
        if (i == 3) {
            objArr[1] = "getAnnotations";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtTypeConstraint";
        } else {
            objArr[1] = "getAnnotationEntries";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeConstraint(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeConstraint(KotlinPlaceHolderStub<KtTypeConstraint> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.TYPE_CONSTRAINT);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitTypeConstraint(this, d);
    }

    @IfNotParsed
    public KtSimpleNameExpression getSubjectTypeParameterName() {
        return (KtSimpleNameExpression) getStubOrPsiChild(KtStubElementTypes.REFERENCE_EXPRESSION);
    }

    @IfNotParsed
    public KtTypeReference getBoundTypeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotation> getAnnotations() {
        List<KtAnnotation> listFindChildrenByType = findChildrenByType(KtNodeTypes.ANNOTATION);
        if (listFindChildrenByType == null) {
            $$$reportNull$$$0(3);
        }
        return listFindChildrenByType;
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotationEntry> getAnnotationEntries() {
        List<KtAnnotationEntry> listCollectAnnotationEntriesFromStubOrPsi = KtPsiUtilKt.collectAnnotationEntriesFromStubOrPsi(this);
        if (listCollectAnnotationEntriesFromStubOrPsi == null) {
            $$$reportNull$$$0(4);
        }
        return listCollectAnnotationEntriesFromStubOrPsi;
    }
}
