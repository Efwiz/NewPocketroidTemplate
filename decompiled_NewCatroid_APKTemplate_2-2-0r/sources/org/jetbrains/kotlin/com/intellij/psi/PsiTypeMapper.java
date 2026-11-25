package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Iterator;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiTypeMapper extends PsiTypeVisitorEx<PsiType> {
    protected static final Logger LOG = Logger.getInstance((Class<?>) PsiTypeMapper.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 3:
                objArr[0] = PsiKeyword.VAR;
                break;
            case 4:
                objArr[0] = "bottom";
                break;
            case 5:
            case 7:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 6:
                objArr[0] = "primitiveType";
                break;
            case 8:
                objArr[0] = "wildcardType";
                break;
            case 9:
                objArr[0] = "intersectionType";
                break;
            case 10:
                objArr[0] = "disjunctionType";
                break;
            case 11:
                objArr[0] = "diamondType";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiTypeMapper";
        switch (i) {
            case 1:
                objArr[2] = "visitArrayType";
                break;
            case 2:
                objArr[2] = "visitEllipsisType";
                break;
            case 3:
                objArr[2] = "visitTypeVariable";
                break;
            case 4:
                objArr[2] = "visitBottom";
                break;
            case 5:
                objArr[2] = "visitCapturedWildcardType";
                break;
            case 6:
                objArr[2] = "visitPrimitiveType";
                break;
            case 7:
                objArr[2] = "visitType";
                break;
            case 8:
                objArr[2] = "visitWildcardType";
                break;
            case 9:
                objArr[2] = "visitIntersectionType";
                break;
            case 10:
                objArr[2] = "visitDisjunctionType";
                break;
            case 11:
                objArr[2] = "visitDiamondType";
                break;
            default:
                objArr[2] = "mapType";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitorEx
    public PsiType visitBottom(Bottom bottom) {
        if (bottom == null) {
            $$$reportNull$$$0(4);
        }
        return bottom;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
        if (psiCapturedWildcardType == null) {
            $$$reportNull$$$0(5);
        }
        return psiCapturedWildcardType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public abstract PsiType visitClassType(PsiClassType psiClassType);

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitDiamondType(PsiDiamondType psiDiamondType) {
        if (psiDiamondType == null) {
            $$$reportNull$$$0(11);
        }
        return psiDiamondType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitPrimitiveType(PsiPrimitiveType psiPrimitiveType) {
        if (psiPrimitiveType == null) {
            $$$reportNull$$$0(6);
        }
        return psiPrimitiveType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitorEx
    public PsiType visitTypeVariable(PsiTypeVariable psiTypeVariable) {
        if (psiTypeVariable == null) {
            $$$reportNull$$$0(3);
        }
        return psiTypeVariable;
    }

    public <T extends PsiType> T mapType(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        return (T) t.accept(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitArrayType(PsiArrayType psiArrayType) {
        if (psiArrayType == null) {
            $$$reportNull$$$0(1);
        }
        PsiType componentType = psiArrayType.getComponentType();
        PsiType psiTypeMapType = mapType(componentType);
        if (psiTypeMapType == null) {
            return null;
        }
        return psiTypeMapType == componentType ? psiArrayType : new PsiArrayType(psiTypeMapType, psiArrayType.getAnnotationProvider());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitEllipsisType(PsiEllipsisType psiEllipsisType) {
        if (psiEllipsisType == null) {
            $$$reportNull$$$0(2);
        }
        PsiType componentType = psiEllipsisType.getComponentType();
        PsiType psiTypeMapType = mapType(componentType);
        if (psiTypeMapType == null) {
            return null;
        }
        return psiTypeMapType == componentType ? psiEllipsisType : new PsiEllipsisType(psiTypeMapType, psiEllipsisType.getAnnotationProvider());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitType(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(7);
        }
        LOG.error(psiType);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitWildcardType(PsiWildcardType psiWildcardType) {
        if (psiWildcardType == null) {
            $$$reportNull$$$0(8);
        }
        PsiType bound = psiWildcardType.getBound();
        PsiManager manager = psiWildcardType.getManager();
        if (bound == null) {
            return PsiWildcardType.createUnbounded(manager);
        }
        PsiType psiTypeMapType = mapType(bound);
        if (psiTypeMapType == null) {
            return null;
        }
        return psiWildcardType.isExtends() ? PsiWildcardType.createExtends(manager, psiTypeMapType) : PsiWildcardType.createSuper(manager, psiTypeMapType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitIntersectionType(PsiIntersectionType psiIntersectionType) {
        if (psiIntersectionType == null) {
            $$$reportNull$$$0(9);
        }
        SmartList smartList = new SmartList();
        for (PsiType psiType : psiIntersectionType.getConjuncts()) {
            PsiType psiTypeMapType = mapType(psiType);
            if (psiTypeMapType == null) {
                return null;
            }
            smartList.add(psiTypeMapType);
        }
        return PsiIntersectionType.createIntersection(false, (PsiType[]) smartList.toArray(PsiType.EMPTY_ARRAY));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitDisjunctionType(PsiDisjunctionType psiDisjunctionType) {
        if (psiDisjunctionType == null) {
            $$$reportNull$$$0(10);
        }
        SmartList smartList = new SmartList();
        Iterator<PsiType> it = psiDisjunctionType.getDisjunctions().iterator();
        while (it.hasNext()) {
            PsiType psiTypeMapType = mapType(it.next());
            if (psiTypeMapType == null) {
                return null;
            }
            smartList.add(psiTypeMapType);
        }
        return psiDisjunctionType.newDisjunctionType(smartList);
    }
}
