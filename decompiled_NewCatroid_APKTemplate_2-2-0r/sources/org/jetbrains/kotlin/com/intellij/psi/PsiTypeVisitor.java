package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeVisitor<A> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "primitiveType";
                break;
            case 2:
                objArr[0] = "arrayType";
                break;
            case 3:
                objArr[0] = "classType";
                break;
            case 4:
                objArr[0] = "capturedWildcardType";
                break;
            case 5:
                objArr[0] = "wildcardType";
                break;
            case 6:
                objArr[0] = "ellipsisType";
                break;
            case 7:
                objArr[0] = "disjunctionType";
                break;
            case 8:
                objArr[0] = "intersectionType";
                break;
            case 9:
                objArr[0] = "diamondType";
                break;
            case 10:
                objArr[0] = "lambdaExpressionType";
                break;
            case 11:
                objArr[0] = "methodReferenceType";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiTypeVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitPrimitiveType";
                break;
            case 2:
                objArr[2] = "visitArrayType";
                break;
            case 3:
                objArr[2] = "visitClassType";
                break;
            case 4:
                objArr[2] = "visitCapturedWildcardType";
                break;
            case 5:
                objArr[2] = "visitWildcardType";
                break;
            case 6:
                objArr[2] = "visitEllipsisType";
                break;
            case 7:
                objArr[2] = "visitDisjunctionType";
                break;
            case 8:
                objArr[2] = "visitIntersectionType";
                break;
            case 9:
                objArr[2] = "visitDiamondType";
                break;
            case 10:
                objArr[2] = "visitLambdaExpressionType";
                break;
            case 11:
                objArr[2] = "visitMethodReferenceType";
                break;
            default:
                objArr[2] = "visitType";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public A visitType(PsiType psiType) {
        if (psiType != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    public A visitPrimitiveType(PsiPrimitiveType psiPrimitiveType) {
        if (psiPrimitiveType == null) {
            $$$reportNull$$$0(1);
        }
        return visitType(psiPrimitiveType);
    }

    public A visitArrayType(PsiArrayType psiArrayType) {
        if (psiArrayType == null) {
            $$$reportNull$$$0(2);
        }
        return visitType(psiArrayType);
    }

    public A visitClassType(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(3);
        }
        return visitType(psiClassType);
    }

    public A visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
        if (psiCapturedWildcardType == null) {
            $$$reportNull$$$0(4);
        }
        return visitWildcardType(psiCapturedWildcardType.getWildcard());
    }

    public A visitWildcardType(PsiWildcardType psiWildcardType) {
        if (psiWildcardType == null) {
            $$$reportNull$$$0(5);
        }
        return visitType(psiWildcardType);
    }

    public A visitEllipsisType(PsiEllipsisType psiEllipsisType) {
        if (psiEllipsisType == null) {
            $$$reportNull$$$0(6);
        }
        return visitArrayType(psiEllipsisType);
    }

    public A visitDisjunctionType(PsiDisjunctionType psiDisjunctionType) {
        if (psiDisjunctionType == null) {
            $$$reportNull$$$0(7);
        }
        return visitType(psiDisjunctionType);
    }

    public A visitIntersectionType(PsiIntersectionType psiIntersectionType) {
        if (psiIntersectionType == null) {
            $$$reportNull$$$0(8);
        }
        return (A) psiIntersectionType.getConjuncts()[0].accept(this);
    }

    public A visitDiamondType(PsiDiamondType psiDiamondType) {
        if (psiDiamondType == null) {
            $$$reportNull$$$0(9);
        }
        return visitType(psiDiamondType);
    }

    public A visitLambdaExpressionType(PsiLambdaExpressionType psiLambdaExpressionType) {
        if (psiLambdaExpressionType == null) {
            $$$reportNull$$$0(10);
        }
        PsiType functionalInterfaceType = psiLambdaExpressionType.getExpression().getFunctionalInterfaceType();
        return (functionalInterfaceType == null || !LambdaUtil.isFunctionalType(functionalInterfaceType)) ? visitType(psiLambdaExpressionType) : (A) functionalInterfaceType.accept(this);
    }

    public A visitMethodReferenceType(PsiMethodReferenceType psiMethodReferenceType) {
        if (psiMethodReferenceType == null) {
            $$$reportNull$$$0(11);
        }
        PsiType functionalInterfaceType = psiMethodReferenceType.getExpression().getFunctionalInterfaceType();
        return (functionalInterfaceType == null || !LambdaUtil.isFunctionalType(functionalInterfaceType)) ? visitType(psiMethodReferenceType) : (A) functionalInterfaceType.accept(this);
    }
}
