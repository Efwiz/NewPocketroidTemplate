package org.jetbrains.kotlin.com.intellij.core;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class CoreJavaPsiImplementationHelper extends JavaPsiImplementationHelper {
    private final Project myProject;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 15 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 15 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 10:
                objArr[0] = "psiClass";
                break;
            case 2:
            case 4:
            case 6:
            case 7:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaPsiImplementationHelper";
                break;
            case 3:
                objArr[0] = "module";
                break;
            case 5:
                objArr[0] = "clsFile";
                break;
            case 8:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 9:
                objArr[0] = "statement";
                break;
            case 11:
                objArr[0] = "firstPsi";
                break;
            case 12:
                objArr[0] = "exceptionName";
                break;
            case 13:
                objArr[0] = "exceptionType";
                break;
            case 14:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 2) {
            objArr[1] = "getOriginalClass";
        } else if (i == 4) {
            objArr[1] = "getOriginalModule";
        } else if (i == 15) {
            objArr[1] = "getProject";
        } else if (i == 6) {
            objArr[1] = "getClsFileNavigationElement";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaPsiImplementationHelper";
        } else {
            objArr[1] = "getEffectiveLanguageLevel";
        }
        switch (i) {
            case 1:
                objArr[2] = "getOriginalClass";
                break;
            case 2:
            case 4:
            case 6:
            case 7:
            case 15:
                break;
            case 3:
                objArr[2] = "getOriginalModule";
                break;
            case 5:
                objArr[2] = "getClsFileNavigationElement";
                break;
            case 8:
            case 9:
                objArr[2] = "getDefaultImportAnchor";
                break;
            case 10:
            case 11:
                objArr[2] = "getDefaultMemberAnchor";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "setupCatchBlock";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 15 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public CoreJavaPsiImplementationHelper(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myProject = project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper
    public PsiClass getOriginalClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        return psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper
    public PsiJavaModule getOriginalModule(PsiJavaModule psiJavaModule) {
        if (psiJavaModule == null) {
            $$$reportNull$$$0(3);
        }
        if (psiJavaModule == null) {
            $$$reportNull$$$0(4);
        }
        return psiJavaModule;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper
    public PsiElement getClsFileNavigationElement(PsiJavaFile psiJavaFile) {
        if (psiJavaFile == null) {
            $$$reportNull$$$0(5);
        }
        if (psiJavaFile == null) {
            $$$reportNull$$$0(6);
        }
        return psiJavaFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper
    public LanguageLevel getEffectiveLanguageLevel(VirtualFile virtualFile) {
        LanguageLevel languageLevel = PsiUtil.getLanguageLevel(this.myProject);
        if (languageLevel == null) {
            $$$reportNull$$$0(7);
        }
        return languageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper
    public ASTNode getDefaultImportAnchor(PsiImportList psiImportList, PsiImportStatementBase psiImportStatementBase) {
        if (psiImportList == null) {
            $$$reportNull$$$0(8);
        }
        if (psiImportStatementBase == null) {
            $$$reportNull$$$0(9);
        }
        throw new UnsupportedOperationException("TODO");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper
    public PsiElement getDefaultMemberAnchor(PsiClass psiClass, PsiMember psiMember) {
        if (psiClass == null) {
            $$$reportNull$$$0(10);
        }
        if (psiMember == null) {
            $$$reportNull$$$0(11);
        }
        throw new UnsupportedOperationException("TODO");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper
    public void setupCatchBlock(String str, PsiType psiType, PsiElement psiElement, PsiCatchSection psiCatchSection) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (psiType == null) {
            $$$reportNull$$$0(13);
        }
        if (psiCatchSection == null) {
            $$$reportNull$$$0(14);
        }
        throw new UnsupportedOperationException("TODO");
    }
}
