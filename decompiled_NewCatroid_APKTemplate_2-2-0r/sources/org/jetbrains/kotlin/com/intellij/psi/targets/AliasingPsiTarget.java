package org.jetbrains.kotlin.com.intellij.psi.targets;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget;
import org.jetbrains.kotlin.com.intellij.psi.DelegatePsiTarget;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;

/* JADX INFO: loaded from: classes3.dex */
public class AliasingPsiTarget extends DelegatePsiTarget implements PomRenameableTarget<AliasingPsiTarget> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "newName";
                break;
            case 2:
            case 4:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/targets/AliasingPsiTarget";
                break;
            case 3:
                objArr[0] = "newAliasName";
                break;
            case 5:
                objArr[0] = "delegatePsiTargetName";
                break;
            case 6:
                objArr[0] = "aliasName";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        if (i == 2) {
            objArr[1] = "getName";
        } else if (i == 4) {
            objArr[1] = "setAliasName";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/targets/AliasingPsiTarget";
        } else {
            objArr[1] = "getTargetName";
        }
        switch (i) {
            case 1:
                objArr[2] = "setName";
                break;
            case 2:
            case 4:
            case 7:
                break;
            case 3:
                objArr[2] = "setAliasName";
                break;
            case 5:
                objArr[2] = "getNameAlias";
                break;
            case 6:
                objArr[2] = "getTargetName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public String getNameAlias(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    public AliasingPsiTarget setAliasName(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AliasingPsiTarget(PsiNamedElement psiNamedElement) {
        super(psiNamedElement);
        if (psiNamedElement == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return getNavigationElement().isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public AliasingPsiTarget setName(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return setAliasName(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String strNotNullize = StringUtil.notNullize(getNameAlias(StringUtil.notNullize(((PsiNamedElement) getNavigationElement()).getName())));
        if (strNotNullize == null) {
            $$$reportNull$$$0(2);
        }
        return strNotNullize;
    }

    public String getTargetName(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }
}
