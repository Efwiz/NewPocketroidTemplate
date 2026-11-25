package org.jetbrains.kotlin.resolve.lazy.data;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtClassOrObjectInfo<E extends KtClassOrObject> implements KtClassLikeInfo {
    protected final E element;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getCorrespondingClassOrObject";
                break;
            case 2:
                objArr[1] = "getDeclarations";
                break;
            case 3:
            case 4:
                objArr[1] = "getCompanionObjects";
                break;
            case 5:
                objArr[1] = "getScopeAnchor";
                break;
            case 6:
                objArr[1] = "getContainingPackageFqName";
                break;
            case 7:
                objArr[1] = "getDanglingAnnotations";
                break;
            case 8:
                objArr[1] = "getPrimaryConstructorParameters";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    protected KtClassOrObjectInfo(E e) {
        if (e == null) {
            $$$reportNull$$$0(0);
        }
        this.element = e;
    }

    public Name getName() {
        return this.element.getNameAsName();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getCorrespondingClassOrObject */
    public KtClassOrObject mo7451getCorrespondingClassOrObject() {
        E e = this.element;
        if (e == null) {
            $$$reportNull$$$0(1);
        }
        return e;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getModifierList */
    public KtModifierList mo7452getModifierList() {
        return this.element.getModifierList();
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationContainer
    public List<KtDeclaration> getDeclarations() {
        List<KtDeclaration> declarations = this.element.getDeclarations();
        if (declarations == null) {
            $$$reportNull$$$0(2);
        }
        return declarations;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public List<KtObjectDeclaration> getCompanionObjects() {
        KtClassBody body = this.element.getBody();
        if (body == null) {
            List<KtObjectDeclaration> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }
        List<KtObjectDeclaration> allCompanionObjects = body.getAllCompanionObjects();
        if (allCompanionObjects == null) {
            $$$reportNull$$$0(4);
        }
        return allCompanionObjects;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getScopeAnchor */
    public PsiElement getScript() {
        E e = this.element;
        if (e == null) {
            $$$reportNull$$$0(5);
        }
        return e;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public FqName getContainingPackageFqName() {
        PsiFile containingFile = this.element.getContainingFile();
        if (containingFile instanceof KtFile) {
            FqName packageFqName = ((KtFile) containingFile).getPackageFqName();
            if (packageFqName == null) {
                $$$reportNull$$$0(6);
            }
            return packageFqName;
        }
        throw new IllegalArgumentException("Not in a KtFile: " + this.element);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public List<KtAnnotationEntry> getDanglingAnnotations() {
        KtClassBody body = this.element.getBody();
        List<KtAnnotationEntry> listEmptyList = body == null ? Collections.emptyList() : body.getDanglingAnnotations();
        if (listEmptyList == null) {
            $$$reportNull$$$0(7);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public List<? extends KtParameter> getPrimaryConstructorParameters() {
        List<KtParameter> primaryConstructorParameters = this.element.getPrimaryConstructorParameters();
        if (primaryConstructorParameters == null) {
            $$$reportNull$$$0(8);
        }
        return primaryConstructorParameters;
    }

    public String toString() {
        return "info for " + this.element.getText();
    }
}
