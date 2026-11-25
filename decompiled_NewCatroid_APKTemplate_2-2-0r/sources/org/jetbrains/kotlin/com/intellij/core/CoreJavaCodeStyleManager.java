package org.jetbrains.kotlin.com.intellij.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.SuggestedNameInfo;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.VariableKind;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class CoreJavaCodeStyleManager extends JavaCodeStyleManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 8 || i == 13 || i == 16 || i == 19 || i == 22 || i == 26 || i == 30 || i == 33) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 8 || i == 13 || i == 16 || i == 19 || i == 22 || i == 26 || i == 30 || i == 33) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "refClass";
                break;
            case 2:
            case 7:
            case 9:
            case 25:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 3:
            case 8:
            case 13:
            case 16:
            case 19:
            case 22:
            case 26:
            case 30:
            case 33:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaCodeStyleManager";
                break;
            case 4:
            case 5:
            case 15:
            case 18:
                objArr[0] = "variableKind";
                break;
            case 6:
                objArr[0] = "statement";
                break;
            case 10:
            case 11:
            case 27:
            case 28:
            default:
                objArr[0] = "file";
                break;
            case 12:
            case 32:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 14:
                objArr[0] = "name";
                break;
            case 17:
                objArr[0] = "propertyName";
                break;
            case 20:
            case 21:
            case 23:
                objArr[0] = "baseName";
                break;
            case 24:
                objArr[0] = "baseNameInfo";
                break;
            case 29:
                objArr[0] = "expression";
                break;
            case 31:
                objArr[0] = "semanticNames";
                break;
        }
        if (i == 3 || i == 8) {
            objArr[1] = "shortenClassReferences";
        } else if (i == 13) {
            objArr[1] = "suggestVariableName";
        } else if (i == 16) {
            objArr[1] = "variableNameToPropertyName";
        } else if (i == 19) {
            objArr[1] = "propertyNameToVariableName";
        } else if (i == 22) {
            objArr[1] = "suggestUniqueVariableName";
        } else if (i == 26) {
            objArr[1] = "qualifyClassReferences";
        } else if (i == 30) {
            objArr[1] = "suggestSemanticNames";
        } else if (i != 33) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaCodeStyleManager";
        } else {
            objArr[1] = "suggestNames";
        }
        switch (i) {
            case 2:
            case 7:
            case 9:
                objArr[2] = "shortenClassReferences";
                break;
            case 3:
            case 8:
            case 13:
            case 16:
            case 19:
            case 22:
            case 26:
            case 30:
            case 33:
                break;
            case 4:
                objArr[2] = "getPrefixByVariableKind";
                break;
            case 5:
                objArr[2] = "getSuffixByVariableKind";
                break;
            case 6:
                objArr[2] = "findEntryIndex";
                break;
            case 10:
                objArr[2] = "optimizeImports";
                break;
            case 11:
                objArr[2] = "prepareOptimizeImportsResult";
                break;
            case 12:
                objArr[2] = "suggestVariableName";
                break;
            case 14:
            case 15:
                objArr[2] = "variableNameToPropertyName";
                break;
            case 17:
            case 18:
                objArr[2] = "propertyNameToVariableName";
                break;
            case 20:
            case 21:
            case 23:
            case 24:
                objArr[2] = "suggestUniqueVariableName";
                break;
            case 25:
                objArr[2] = "qualifyClassReferences";
                break;
            case 27:
                objArr[2] = "removeRedundantImports";
                break;
            case 28:
                objArr[2] = "findRedundantImports";
                break;
            case 29:
                objArr[2] = "suggestSemanticNames";
                break;
            case 31:
            case 32:
                objArr[2] = "suggestNames";
                break;
            default:
                objArr[2] = "addImport";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 8 && i != 13 && i != 16 && i != 19 && i != 22 && i != 26 && i != 30 && i != 33) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$suggestUniqueVariableName$0(PsiVariable psiVariable) {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public boolean addImport(PsiJavaFile psiJavaFile, PsiClass psiClass) {
        if (psiJavaFile == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public int findEntryIndex(PsiImportStatementBase psiImportStatementBase) {
        if (psiImportStatementBase != null) {
            return 0;
        }
        $$$reportNull$$$0(6);
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public Collection<PsiImportStatementBase> findRedundantImports(PsiJavaFile psiJavaFile) {
        if (psiJavaFile != null) {
            return null;
        }
        $$$reportNull$$$0(28);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public void optimizeImports(PsiFile psiFile) throws IncorrectOperationException {
        if (psiFile == null) {
            $$$reportNull$$$0(10);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public PsiImportList prepareOptimizeImportsResult(PsiJavaFile psiJavaFile) {
        if (psiJavaFile != null) {
            return null;
        }
        $$$reportNull$$$0(11);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public void removeRedundantImports(PsiJavaFile psiJavaFile) throws IncorrectOperationException {
        if (psiJavaFile == null) {
            $$$reportNull$$$0(27);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public void shortenClassReferences(PsiElement psiElement, int i, int i2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public PsiElement shortenClassReferences(PsiElement psiElement, int i) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public String getPrefixByVariableKind(VariableKind variableKind) {
        if (variableKind == null) {
            $$$reportNull$$$0(4);
            return "";
        }
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public String getSuffixByVariableKind(VariableKind variableKind) {
        if (variableKind == null) {
            $$$reportNull$$$0(5);
            return "";
        }
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public PsiElement shortenClassReferences(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public SuggestedNameInfo suggestVariableName(VariableKind variableKind, String str, PsiExpression psiExpression, PsiType psiType, boolean z) {
        if (variableKind == null) {
            $$$reportNull$$$0(12);
        }
        SuggestedNameInfo suggestedNameInfo = SuggestedNameInfo.NULL_INFO;
        if (suggestedNameInfo == null) {
            $$$reportNull$$$0(13);
        }
        return suggestedNameInfo;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public String variableNameToPropertyName(String str, VariableKind variableKind) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (variableKind == null) {
            $$$reportNull$$$0(15);
        }
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public String propertyNameToVariableName(String str, VariableKind variableKind) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (variableKind == null) {
            $$$reportNull$$$0(18);
        }
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public String suggestUniqueVariableName(String str, PsiElement psiElement, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        return suggestUniqueVariableName(str, psiElement, z, (Predicate<? super PsiVariable>) new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.core.CoreJavaCodeStyleManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CoreJavaCodeStyleManager.lambda$suggestUniqueVariableName$0((PsiVariable) obj);
            }
        });
    }

    private static String suggestUniqueVariableName(String str, PsiElement psiElement, boolean z, final Predicate<? super PsiVariable> predicate) {
        final String str2;
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        int i = 0;
        PsiElement nonStrictParentOfType = PsiTreeUtil.getNonStrictParentOfType(psiElement, PsiStatement.class, PsiCodeBlock.class, PsiMethod.class);
        loop0: while (true) {
            str2 = i > 0 ? str + i : str;
            i++;
            if (PsiUtil.isVariableNameUnique(str2, psiElement)) {
                if (!z) {
                    break;
                }
                PsiElement nextSibling = nonStrictParentOfType;
                while (nextSibling != null) {
                    try {
                        nextSibling.accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.core.CoreJavaCodeStyleManager.1
                            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                            public void visitAnonymousClass(PsiAnonymousClass psiAnonymousClass) {
                            }

                            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                            public void visitVariable(PsiVariable psiVariable) {
                                if (str2.equals(psiVariable.getMyName()) && !predicate.test(psiVariable)) {
                                    throw new RuntimeException() { // from class: org.jetbrains.kotlin.com.intellij.core.CoreJavaCodeStyleManager.1CancelException
                                    };
                                }
                            }
                        });
                        nextSibling = nextSibling.getNextSibling();
                        if (nonStrictParentOfType instanceof PsiMethod) {
                            break loop0;
                        }
                    } catch (C1CancelException unused) {
                        continue;
                    }
                }
                break loop0;
            }
        }
        if (str2 == null) {
            $$$reportNull$$$0(22);
        }
        return str2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public String suggestUniqueVariableName(String str, PsiElement psiElement, Predicate<? super PsiVariable> predicate) {
        if (str == null) {
            $$$reportNull$$$0(23);
        }
        return suggestUniqueVariableName(str, psiElement, true, predicate);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public SuggestedNameInfo suggestUniqueVariableName(final SuggestedNameInfo suggestedNameInfo, PsiElement psiElement, boolean z, boolean z2) {
        if (suggestedNameInfo == null) {
            $$$reportNull$$$0(24);
        }
        String[] strArr = suggestedNameInfo.names;
        LinkedHashSet linkedHashSet = new LinkedHashSet(strArr.length);
        for (String str : strArr) {
            if (z && (psiElement instanceof PsiNamedElement) && Comparing.strEqual(((PsiNamedElement) psiElement).getMyName(), str)) {
                linkedHashSet.add(str);
            } else {
                linkedHashSet.add(suggestUniqueVariableName(str, psiElement, z2));
            }
        }
        return new SuggestedNameInfo(ArrayUtilRt.toStringArray(linkedHashSet)) { // from class: org.jetbrains.kotlin.com.intellij.core.CoreJavaCodeStyleManager.2
            @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.SuggestedNameInfo
            public void nameChosen(String str2) {
                suggestedNameInfo.nameChosen(str2);
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public PsiElement qualifyClassReferences(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(25);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(26);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public Collection<String> suggestSemanticNames(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(29);
        }
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(30);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager
    public SuggestedNameInfo suggestNames(Collection<String> collection, VariableKind variableKind, PsiType psiType) {
        if (collection == null) {
            $$$reportNull$$$0(31);
        }
        if (variableKind == null) {
            $$$reportNull$$$0(32);
        }
        SuggestedNameInfo suggestedNameInfo = SuggestedNameInfo.NULL_INFO;
        if (suggestedNameInfo == null) {
            $$$reportNull$$$0(33);
        }
        return suggestedNameInfo;
    }
}
