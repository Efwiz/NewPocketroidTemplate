package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiSwitchExpressionImpl extends PsiSwitchBlockImpl implements PsiSwitchExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "child";
        } else if (i != 2) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "newElement";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiSwitchExpressionImpl";
        if (i == 1 || i == 2) {
            objArr[2] = "replaceChildInternal";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiSwitchExpressionImpl() {
        super(JavaElementType.SWITCH_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        if (PsiUtil.isLanguageLevel8OrHigher(this) && PsiPolyExpressionUtil.isPolyExpression(this) && !MethodCandidateInfo.isOverloadCheck(PsiUtil.skipParenthesizedExprUp(getLightParent()))) {
            return InferenceSession.getTargetType(this);
        }
        List<PsiExpression> switchResultExpressions = PsiUtil.getSwitchResultExpressions(this);
        HashSet<PsiType> hashSet = new HashSet();
        Iterator<PsiExpression> it = switchResultExpressions.iterator();
        while (it.hasNext()) {
            PsiType type = it.next().getType();
            if (type == null) {
                return null;
            }
            hashSet.add(type);
        }
        if (hashSet.isEmpty()) {
            return null;
        }
        if (hashSet.size() == 1) {
            return (PsiType) ContainerUtil.getFirstItem(hashSet);
        }
        if (hashSet.stream().allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchExpressionImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PsiType.BOOLEAN.isAssignableFrom((PsiType) obj);
            }
        })) {
            return PsiType.BOOLEAN;
        }
        int[] array = hashSet.stream().mapToInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchExpressionImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return TypeConversionUtil.getTypeRank((PsiType) obj);
            }
        }).toArray();
        int iMax = ArrayUtil.max(array);
        if (!TypeConversionUtil.isNumericType(iMax)) {
            PsiType leastUpperBound = PsiType.NULL;
            for (PsiType boxedType : hashSet) {
                if (TypeConversionUtil.isPrimitiveAndNotNull(boxedType)) {
                    boxedType = ((PsiPrimitiveType) boxedType).getBoxedType(this);
                }
                leastUpperBound = GenericsUtil.getLeastUpperBound(boxedType, leastUpperBound, getManager());
            }
            if (leastUpperBound != null) {
                return PsiUtil.captureToplevelWildcards(leastUpperBound, this);
            }
            return null;
        }
        if (iMax == 7) {
            return PsiType.DOUBLE;
        }
        if (iMax == 6) {
            return PsiType.FLOAT;
        }
        if (iMax == 5) {
            return PsiType.LONG;
        }
        if (isNumericPromotion(switchResultExpressions, array, PsiType.CHAR)) {
            return PsiType.CHAR;
        }
        if (isNumericPromotion(switchResultExpressions, array, PsiType.SHORT)) {
            return PsiType.SHORT;
        }
        if (isNumericPromotion(switchResultExpressions, array, PsiType.BYTE)) {
            return PsiType.BYTE;
        }
        return PsiType.INT;
    }

    private static boolean isNumericPromotion(List<PsiExpression> list, int[] iArr, final PsiPrimitiveType psiPrimitiveType) {
        return ArrayUtil.find(iArr, TypeConversionUtil.getTypeRank(psiPrimitiveType)) > -1 && list.stream().allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchExpressionImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TypeConversionUtil.areTypesAssignmentCompatible(psiPrimitiveType, (PsiExpression) obj);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitSwitchExpression(this);
        } else {
            super.accept(psiElementVisitor);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void replaceChildInternal(ASTNode aSTNode, TreeElement treeElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(2);
        }
        super.replaceChildInternal(aSTNode, JavaSourceUtil.addParenthToReplacedChild(aSTNode, treeElement, getManager()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        PsiExpression expression = getExpression();
        return "PsiSwitchExpression: " + (expression != null ? expression.getText() : "(incomplete)");
    }
}
