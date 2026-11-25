package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtAnnotatedExpression extends KtExpressionImpl implements KtAnnotated, KtAnnotationsContainer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtAnnotatedExpression";
        } else {
            objArr[0] = "node";
        }
        if (i == 2) {
            objArr[1] = "getAnnotations";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtAnnotatedExpression";
        } else {
            objArr[1] = "getAnnotationEntries";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAnnotatedExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitAnnotatedExpression(this, d);
    }

    public KtExpression getBaseExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotation> getAnnotations() {
        List<KtAnnotation> listFindChildrenByType = findChildrenByType(KtNodeTypes.ANNOTATION);
        if (listFindChildrenByType == null) {
            $$$reportNull$$$0(2);
        }
        return listFindChildrenByType;
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotationEntry> getAnnotationEntries() {
        List<KtAnnotationEntry> listCollectAnnotationEntriesFromStubOrPsi = KtPsiUtilKt.collectAnnotationEntriesFromStubOrPsi(this);
        if (listCollectAnnotationEntriesFromStubOrPsi == null) {
            $$$reportNull$$$0(3);
        }
        return listCollectAnnotationEntriesFromStubOrPsi;
    }
}
