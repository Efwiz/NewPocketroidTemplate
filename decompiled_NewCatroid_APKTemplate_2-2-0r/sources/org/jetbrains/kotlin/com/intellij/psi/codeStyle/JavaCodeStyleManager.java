package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaCodeStyleManager {
    public static final int DO_NOT_ADD_IMPORTS = 4096;
    public static final int INCOMPLETE_CODE = 8192;

    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 13) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 13) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "psiClass";
                break;
            case 2:
                objArr[0] = "referenceName";
                break;
            case 3:
                objArr[0] = "variable";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/JavaCodeStyleManager";
                break;
            case 10:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 11:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 12:
                objArr[0] = "baseNameInfo";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i != 13) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    objArr[1] = "getVariableKind";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/JavaCodeStyleManager";
                    break;
            }
        } else {
            objArr[1] = "suggestUniqueVariableName";
        }
        switch (i) {
            case 3:
                objArr[2] = "getVariableKind";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 13:
                break;
            case 10:
                objArr[2] = "suggestVariableName";
                break;
            case 11:
                objArr[2] = "suggestCompiledParameterName";
                break;
            case 12:
                objArr[2] = "suggestUniqueVariableName";
                break;
            default:
                objArr[2] = "hasConflictingOnDemandImport";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public abstract boolean addImport(PsiJavaFile psiJavaFile, PsiClass psiClass);

    public abstract int findEntryIndex(PsiImportStatementBase psiImportStatementBase);

    public abstract Collection<PsiImportStatementBase> findRedundantImports(PsiJavaFile psiJavaFile);

    public abstract String getPrefixByVariableKind(VariableKind variableKind);

    public abstract String getSuffixByVariableKind(VariableKind variableKind);

    public boolean hasConflictingOnDemandImport(PsiJavaFile psiJavaFile, PsiClass psiClass, String str) {
        if (psiJavaFile == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return false;
    }

    public abstract void optimizeImports(PsiFile psiFile) throws IncorrectOperationException;

    public abstract PsiImportList prepareOptimizeImportsResult(PsiJavaFile psiJavaFile);

    public abstract String propertyNameToVariableName(String str, VariableKind variableKind);

    public abstract PsiElement qualifyClassReferences(PsiElement psiElement);

    public abstract void removeRedundantImports(PsiJavaFile psiJavaFile) throws IncorrectOperationException;

    public abstract PsiElement shortenClassReferences(PsiElement psiElement) throws IncorrectOperationException;

    public abstract PsiElement shortenClassReferences(PsiElement psiElement, int i) throws IncorrectOperationException;

    public abstract void shortenClassReferences(PsiElement psiElement, int i, int i2) throws IncorrectOperationException;

    public abstract SuggestedNameInfo suggestNames(Collection<String> collection, VariableKind variableKind, PsiType psiType);

    public abstract Collection<String> suggestSemanticNames(PsiExpression psiExpression);

    public abstract String suggestUniqueVariableName(String str, PsiElement psiElement, Predicate<? super PsiVariable> predicate);

    public abstract String suggestUniqueVariableName(String str, PsiElement psiElement, boolean z);

    public abstract SuggestedNameInfo suggestUniqueVariableName(SuggestedNameInfo suggestedNameInfo, PsiElement psiElement, boolean z, boolean z2);

    public abstract SuggestedNameInfo suggestVariableName(VariableKind variableKind, String str, PsiExpression psiExpression, PsiType psiType, boolean z);

    public abstract String variableNameToPropertyName(String str, VariableKind variableKind);

    public static JavaCodeStyleManager getInstance(Project project) {
        return (JavaCodeStyleManager) project.getService(JavaCodeStyleManager.class);
    }

    public VariableKind getVariableKind(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(3);
        }
        if (psiVariable instanceof PsiField) {
            if (psiVariable.hasModifierProperty("static")) {
                if (psiVariable.hasModifierProperty("final")) {
                    VariableKind variableKind = VariableKind.STATIC_FINAL_FIELD;
                    if (variableKind == null) {
                        $$$reportNull$$$0(4);
                    }
                    return variableKind;
                }
                VariableKind variableKind2 = VariableKind.STATIC_FIELD;
                if (variableKind2 == null) {
                    $$$reportNull$$$0(5);
                }
                return variableKind2;
            }
            VariableKind variableKind3 = VariableKind.FIELD;
            if (variableKind3 == null) {
                $$$reportNull$$$0(6);
            }
            return variableKind3;
        }
        if (psiVariable instanceof PsiParameter) {
            if (((PsiParameter) psiVariable).getDeclarationScope() instanceof PsiForeachStatement) {
                VariableKind variableKind4 = VariableKind.LOCAL_VARIABLE;
                if (variableKind4 == null) {
                    $$$reportNull$$$0(7);
                }
                return variableKind4;
            }
            VariableKind variableKind5 = VariableKind.PARAMETER;
            if (variableKind5 == null) {
                $$$reportNull$$$0(8);
            }
            return variableKind5;
        }
        VariableKind variableKind6 = VariableKind.LOCAL_VARIABLE;
        if (variableKind6 == null) {
            $$$reportNull$$$0(9);
        }
        return variableKind6;
    }

    public SuggestedNameInfo suggestVariableName(VariableKind variableKind, String str, PsiExpression psiExpression, PsiType psiType) {
        if (variableKind == null) {
            $$$reportNull$$$0(10);
        }
        return suggestVariableName(variableKind, str, psiExpression, psiType, true);
    }

    public String suggestCompiledParameterName(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(11);
        }
        return (String) ContainerUtil.getFirstItem(Arrays.asList(suggestVariableName(VariableKind.PARAMETER, null, null, psiType, true).names));
    }

    public SuggestedNameInfo suggestUniqueVariableName(SuggestedNameInfo suggestedNameInfo, PsiElement psiElement, boolean z) {
        if (suggestedNameInfo == null) {
            $$$reportNull$$$0(12);
        }
        SuggestedNameInfo suggestedNameInfoSuggestUniqueVariableName = suggestUniqueVariableName(suggestedNameInfo, psiElement, false, z);
        if (suggestedNameInfoSuggestUniqueVariableName == null) {
            $$$reportNull$$$0(13);
        }
        return suggestedNameInfoSuggestUniqueVariableName;
    }
}
