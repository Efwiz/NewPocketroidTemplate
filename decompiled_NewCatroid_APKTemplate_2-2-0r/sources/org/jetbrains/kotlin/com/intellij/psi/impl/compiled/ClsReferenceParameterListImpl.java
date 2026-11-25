package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class ClsReferenceParameterListImpl extends ClsElementImpl implements PsiReferenceParameterList {
    private static final Pattern EXTENDS_PREFIX = Pattern.compile("^(\\?\\s*extends\\s*)(.*)");
    private static final Pattern SUPER_PREFIX = Pattern.compile("^(\\?\\s*super\\s*)(.*)");
    private final PsiElement myParent;
    private final ClsTypeElementImpl[] myTypeParameters;
    private volatile PsiType[] myTypeParametersCachedTypes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "annotations";
                break;
            case 2:
                objArr[0] = "buffer";
                break;
            case 3:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsReferenceParameterListImpl";
                break;
            default:
                objArr[0] = "classParameters";
                break;
        }
        if (i == 4) {
            objArr[1] = "getTypeParameterElements";
        } else if (i == 5) {
            objArr[1] = "getTypeArguments";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsReferenceParameterListImpl";
        } else {
            objArr[1] = "getChildren";
        }
        if (i == 2) {
            objArr[2] = "appendMirrorText";
        } else if (i == 3) {
            objArr[2] = "setMirror";
        } else if (i != 4 && i != 5 && i != 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
    }

    public ClsReferenceParameterListImpl(PsiElement psiElement, String[] strArr, TypeAnnotationContainer typeAnnotationContainer) {
        char c;
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
        if (typeAnnotationContainer == null) {
            $$$reportNull$$$0(1);
        }
        this.myParent = psiElement;
        int length = strArr.length;
        this.myTypeParameters = new ClsTypeElementImpl[length];
        for (int i = 0; i < length; i++) {
            int i2 = (length - i) - 1;
            String strSubstring = strArr[i2];
            Matcher matcher = EXTENDS_PREFIX.matcher(strSubstring);
            if (matcher.find()) {
                strSubstring = matcher.group(2);
                c = '+';
            } else {
                Matcher matcher2 = SUPER_PREFIX.matcher(strSubstring);
                if (matcher2.find()) {
                    strSubstring = matcher2.group(2);
                    c = '-';
                } else if (StringUtil.startsWithChar(strSubstring, MangleConstant.Q_MARK)) {
                    strSubstring = strSubstring.substring(1);
                    c = '*';
                } else {
                    c = 0;
                }
            }
            this.myTypeParameters[i] = new ClsTypeElementImpl(this, strSubstring, c, typeAnnotationContainer.forTypeArgument(i2));
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList
    public PsiTypeElement[] getTypeParameterElements() {
        ClsTypeElementImpl[] clsTypeElementImplArr = this.myTypeParameters;
        if (clsTypeElementImplArr == null) {
            $$$reportNull$$$0(4);
        }
        return clsTypeElementImplArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList
    public PsiType[] getTypeArguments() {
        PsiType[] psiTypeArrCreateArray = this.myTypeParametersCachedTypes;
        if (psiTypeArrCreateArray == null) {
            psiTypeArrCreateArray = PsiType.createArray(this.myTypeParameters.length);
            for (int i = 0; i < psiTypeArrCreateArray.length; i++) {
                psiTypeArrCreateArray[(psiTypeArrCreateArray.length - i) - 1] = this.myTypeParameters[i].getType();
            }
            this.myTypeParametersCachedTypes = psiTypeArrCreateArray;
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(5);
        }
        return psiTypeArrCreateArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        ClsTypeElementImpl[] clsTypeElementImplArr = this.myTypeParameters;
        if (clsTypeElementImplArr == null) {
            $$$reportNull$$$0(6);
        }
        return clsTypeElementImplArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myParent;
    }
}
