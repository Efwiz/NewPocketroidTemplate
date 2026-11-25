package org.jetbrains.kotlin.com.intellij.codeInsight;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceListElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiThrowStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.AnalysisCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowFactory;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.LocalsOrMyInstanceFieldsControlFlowPolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.MethodProcessorSetupFailedException;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.one.util.streamex.StreamEx;

/* JADX INFO: loaded from: classes3.dex */
public final class ExceptionUtil {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:121:0x0179 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:125:0x018d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0055 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 6 && i != 8 && i != 10 && i != 21 && i != 24 && i != 28 && i != 31 && i != 47 && i != 49 && i != 3 && i != 4 && i != 14 && i != 15 && i != 33 && i != 34 && i != 86 && i != 87) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        case 68:
                                        case 69:
                                        case 70:
                                        case 71:
                                        case 72:
                                        case 73:
                                        case 74:
                                        case 75:
                                        case 76:
                                        case 77:
                                        case 78:
                                        case 79:
                                        case 80:
                                        case 81:
                                            break;
                                        default:
                                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                            break;
                                    }
                                case 57:
                                case 58:
                                case 59:
                                    str = "@NotNull method %s.%s must not return null";
                                    break;
                            }
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 6 && i != 8 && i != 10 && i != 21 && i != 24 && i != 28 && i != 31 && i != 47 && i != 49 && i != 3 && i != 4 && i != 14 && i != 15 && i != 33 && i != 34 && i != 86 && i != 87) {
            switch (i) {
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 57:
                                case 58:
                                case 59:
                                    i2 = 2;
                                    break;
                                default:
                                    switch (i) {
                                        case 68:
                                        case 69:
                                        case 70:
                                        case 71:
                                        case 72:
                                        case 73:
                                        case 74:
                                        case 75:
                                        case 76:
                                        case 77:
                                        case 78:
                                        case 79:
                                        case 80:
                                        case 81:
                                            i2 = 2;
                                            break;
                                        default:
                                            i2 = 3;
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 14:
            case 15:
            case 21:
            case 24:
            case 28:
            case 31:
            case 33:
            case 34:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 47:
            case 49:
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
            case 58:
            case 59:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 86:
            case 87:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/codeInsight/ExceptionUtil";
                break;
            case 2:
            case 30:
            default:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 5:
            case 17:
            case 97:
                objArr[0] = "exceptions";
                break;
            case 7:
            case 19:
            case 22:
            case 23:
            case 25:
            case 32:
            case 35:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 9:
            case 84:
                objArr[0] = "tryStatement";
                break;
            case 11:
            case 55:
            case 60:
            case 89:
            case 91:
            case 98:
                objArr[0] = "method";
                break;
            case 12:
            case 56:
                objArr[0] = "substitutor";
                break;
            case 13:
                objArr[0] = "place";
                break;
            case 16:
            case 18:
                objArr[0] = "array";
                break;
            case 20:
                objArr[0] = "skippedCall";
                break;
            case 26:
                objArr[0] = "callFilter";
                break;
            case 27:
                objArr[0] = "methodReferenceExpression";
                break;
            case 29:
                objArr[0] = "constructorBody";
                break;
            case 36:
            case 37:
                objArr[0] = "methodCall";
                break;
            case 38:
                objArr[0] = "skipCondition";
                break;
            case 46:
            case 48:
                objArr[0] = "resource";
                break;
            case 50:
                objArr[0] = "throwStatement";
                break;
            case 61:
            case 63:
            case 64:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 62:
                objArr[0] = "psiClass";
                break;
            case 65:
            case 67:
            case 82:
            case 83:
            case 85:
            case 90:
            case 92:
            case 93:
            case 95:
                objArr[0] = "exceptionType";
                break;
            case 66:
                objArr[0] = "throwPlace";
                break;
            case 88:
                objArr[0] = "finallyBlock";
                break;
            case 94:
            case 96:
                objArr[0] = "referencedTypes";
                break;
        }
        if (i == 1) {
            objArr[1] = "getThrownExceptions";
        } else if (i == 6) {
            objArr[1] = "filterOutUncheckedExceptions";
        } else if (i == 8) {
            objArr[1] = "getThrownExceptions";
        } else if (i == 10) {
            objArr[1] = "getTryExceptions";
        } else if (i == 21 || i == 24) {
            objArr[1] = "collectUnhandledExceptions";
        } else if (i == 28 || i == 31) {
            objArr[1] = "getUnhandledExceptions";
        } else if (i == 47) {
            objArr[1] = "getCloserExceptions";
        } else if (i == 49) {
            objArr[1] = "getUnhandledCloserExceptions";
        } else if (i != 3 && i != 4) {
            if (i != 14 && i != 15) {
                if (i != 33 && i != 34) {
                    if (i != 86 && i != 87) {
                        switch (i) {
                            default:
                                switch (i) {
                                    case 51:
                                        break;
                                    case 52:
                                    case 53:
                                    case 54:
                                        objArr[1] = "getPreciseThrowTypes";
                                        break;
                                    default:
                                        switch (i) {
                                            case 57:
                                            case 58:
                                            case 59:
                                                break;
                                            default:
                                                switch (i) {
                                                    case 68:
                                                    case 69:
                                                    case 70:
                                                    case 71:
                                                    case 72:
                                                    case 73:
                                                    case 74:
                                                    case 75:
                                                    case 76:
                                                    case 77:
                                                    case 78:
                                                    case 79:
                                                    case 80:
                                                    case 81:
                                                        objArr[1] = "getHandlePlace";
                                                        break;
                                                    default:
                                                        objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/ExceptionUtil";
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                            case 39:
                            case 40:
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                                objArr[1] = "getUnhandledExceptions";
                                break;
                        }
                    } else {
                        objArr[1] = "getCaughtPlace";
                    }
                } else {
                    objArr[1] = "getOwnUnhandledExceptions";
                }
            } else {
                objArr[1] = "getExceptionsByMethod";
            }
        } else {
            objArr[1] = "getThrownCheckedExceptions";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 14:
            case 15:
            case 21:
            case 24:
            case 28:
            case 31:
            case 33:
            case 34:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 47:
            case 49:
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
            case 58:
            case 59:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 86:
            case 87:
                break;
            case 2:
                objArr[2] = "getThrownCheckedExceptions";
                break;
            case 5:
                objArr[2] = "filterOutUncheckedExceptions";
                break;
            case 7:
            default:
                objArr[2] = "getThrownExceptions";
                break;
            case 9:
                objArr[2] = "getTryExceptions";
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "getExceptionsByMethod";
                break;
            case 16:
            case 17:
                objArr[2] = "addExceptions";
                break;
            case 18:
                objArr[2] = "addException";
                break;
            case 19:
            case 20:
            case 22:
            case 23:
            case 25:
            case 26:
                objArr[2] = "collectUnhandledExceptions";
                break;
            case 27:
            case 30:
            case 35:
            case 36:
            case 37:
            case 38:
            case 50:
            case 55:
            case 56:
                objArr[2] = "getUnhandledExceptions";
                break;
            case 29:
                objArr[2] = "firstStatementIsConstructorCall";
                break;
            case 32:
                objArr[2] = "getOwnUnhandledExceptions";
                break;
            case 46:
                objArr[2] = "getCloserExceptions";
                break;
            case 48:
                objArr[2] = "getUnhandledCloserExceptions";
                break;
            case 60:
                objArr[2] = "isArrayClone";
                break;
            case 61:
            case 62:
                objArr[2] = "isUncheckedException";
                break;
            case 63:
                objArr[2] = "isUncheckedExceptionOrSuperclass";
                break;
            case 64:
                objArr[2] = "isGeneralExceptionType";
                break;
            case 65:
            case 66:
                objArr[2] = "isHandled";
                break;
            case 67:
                objArr[2] = "getHandlePlace";
                break;
            case 82:
                objArr[2] = "isDeclaredBySAMMethod";
                break;
            case 83:
                objArr[2] = "areAllConstructorsThrow";
                break;
            case 84:
            case 85:
                objArr[2] = "getCaughtPlace";
                break;
            case 88:
                objArr[2] = "blockCompletesAbruptly";
                break;
            case 89:
            case 90:
            case 91:
            case 92:
                objArr[2] = "isHandledByMethodThrowsClause";
                break;
            case 93:
            case 94:
            case 95:
            case 96:
                objArr[2] = "isHandledBy";
                break;
            case 97:
                objArr[2] = "sortExceptionsByHierarchy";
                break;
            case 98:
                objArr[2] = "canDeclareThrownExceptions";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6 && i != 8 && i != 10 && i != 21 && i != 24 && i != 28 && i != 31 && i != 47 && i != 49 && i != 3 && i != 4 && i != 14 && i != 15 && i != 33 && i != 34 && i != 86 && i != 87) {
            switch (i) {
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                    break;
                default:
                    switch (i) {
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                            break;
                        default:
                            switch (i) {
                                case 57:
                                case 58:
                                case 59:
                                    break;
                                default:
                                    switch (i) {
                                        case 68:
                                        case 69:
                                        case 70:
                                        case 71:
                                        case 72:
                                        case 73:
                                        case 74:
                                        case 75:
                                        case 76:
                                        case 77:
                                        case 78:
                                        case 79:
                                        case 80:
                                        case 81:
                                            break;
                                        default:
                                            throw new IllegalArgumentException(str2);
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$collectUnhandledExceptions$1(PsiCallExpression psiCallExpression) {
        return false;
    }

    static /* synthetic */ boolean lambda$getUnhandledExceptions$4(PsiCallExpression psiCallExpression) {
        return false;
    }

    public static List<PsiClassType> getThrownExceptions(PsiElement[] psiElementArr) {
        if (psiElementArr == null) {
            $$$reportNull$$$0(0);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElement : psiElementArr) {
            addExceptions(arrayList, getThrownExceptions(psiElement));
        }
        return arrayList;
    }

    public static List<PsiClassType> getThrownExceptions(final PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        final ArrayList arrayList = new ArrayList();
        psiElement.accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil.1Visitor
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "psiElement", "org/jetbrains/kotlin/com/intellij/codeInsight/ExceptionUtil$1Visitor", "visitElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitClass(PsiClass psiClass) {
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitLambdaExpression(PsiLambdaExpression psiLambdaExpression) {
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement psiElement2) {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (psiElement2 == psiElement || !(psiElement2.getLightParent() instanceof PsiAnonymousClass) || (psiElement2 instanceof PsiExpressionList)) {
                    super.visitElement(psiElement2);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitAnonymousClass(PsiAnonymousClass psiAnonymousClass) {
                visitElement(psiAnonymousClass);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitMethodCallExpression(PsiMethodCallExpression psiMethodCallExpression) {
                JavaResolveResult javaResolveResultAdvancedResolve = psiMethodCallExpression.getMethodExpression().advancedResolve(false);
                PsiMethod psiMethod = (PsiMethod) javaResolveResultAdvancedResolve.getElement();
                if (psiMethod != null) {
                    ExceptionUtil.addExceptions(arrayList, ExceptionUtil.getExceptionsByMethod(psiMethod, javaResolveResultAdvancedResolve.getSubstitutor(), psiElement));
                }
                super.visitMethodCallExpression(psiMethodCallExpression);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitNewExpression(PsiNewExpression psiNewExpression) {
                JavaResolveResult javaResolveResultResolveMethodGenerics = psiNewExpression.resolveMethodGenerics();
                PsiMethod psiMethod = (PsiMethod) javaResolveResultResolveMethodGenerics.getElement();
                if (psiMethod != null) {
                    ExceptionUtil.addExceptions(arrayList, ExceptionUtil.getExceptionsByMethod(psiMethod, javaResolveResultResolveMethodGenerics.getSubstitutor(), psiElement));
                }
                super.visitNewExpression(psiNewExpression);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitThrowStatement(PsiThrowStatement psiThrowStatement) {
                PsiExpression exception = psiThrowStatement.getException();
                if (exception != null) {
                    ExceptionUtil.addExceptions(arrayList, StreamEx.of((Collection) ExceptionUtil.getPreciseThrowTypes(exception)).select(PsiClassType.class).toList());
                }
                super.visitThrowStatement(psiThrowStatement);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitResourceList(PsiResourceList psiResourceList) {
                Iterator<PsiResourceListElement> it = psiResourceList.iterator();
                while (it.hasNext()) {
                    ExceptionUtil.addExceptions(arrayList, ExceptionUtil.getCloserExceptions(it.next()));
                }
                super.visitResourceList(psiResourceList);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitTryStatement(PsiTryStatement psiTryStatement) {
                ExceptionUtil.addExceptions(arrayList, ExceptionUtil.getTryExceptions(psiTryStatement));
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<PsiClassType> getTryExceptions(PsiTryStatement psiTryStatement) {
        if (psiTryStatement == null) {
            $$$reportNull$$$0(9);
        }
        ArrayList arrayList = new ArrayList();
        PsiResourceList resourceList = psiTryStatement.getResourceList();
        if (resourceList != null) {
            Iterator<PsiResourceListElement> it = resourceList.iterator();
            while (it.hasNext()) {
                addExceptions(arrayList, getUnhandledCloserExceptions(it.next(), resourceList));
            }
        }
        PsiCodeBlock tryBlock = psiTryStatement.getTryBlock();
        if (tryBlock != null) {
            addExceptions(arrayList, getThrownExceptions(tryBlock));
        }
        for (PsiParameter psiParameter : psiTryStatement.getCatchBlockParameters()) {
            PsiType type = psiParameter.getType();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                PsiClassType psiClassType = (PsiClassType) arrayList.get(size);
                if (type.isAssignableFrom(psiClassType)) {
                    arrayList.remove(psiClassType);
                }
            }
        }
        for (PsiCodeBlock psiCodeBlock : psiTryStatement.getCatchBlocks()) {
            addExceptions(arrayList, getThrownExceptions(psiCodeBlock));
        }
        PsiCodeBlock finallyBlock = psiTryStatement.getFinallyBlock();
        if (finallyBlock != null) {
            try {
                ControlFlow controlFlow = ControlFlowFactory.getInstance(finallyBlock.getProject()).getControlFlow((PsiElement) finallyBlock, (ControlFlowPolicy) LocalsOrMyInstanceFieldsControlFlowPolicy.getInstance(), false);
                int completionReasons = ControlFlowUtil.getCompletionReasons(controlFlow, 0, controlFlow.getSize());
                List<PsiClassType> thrownExceptions = getThrownExceptions(finallyBlock);
                if (!BitUtil.isSet(completionReasons, 1)) {
                    arrayList = new ArrayList(thrownExceptions);
                } else {
                    addExceptions(arrayList, thrownExceptions);
                }
            } catch (AnalysisCanceledException unused) {
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<PsiClassType> getExceptionsByMethod(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, PsiElement psiElement) {
        if (psiMethod == null) {
            $$$reportNull$$$0(11);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(12);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        PsiClassType[] referencedTypes = psiMethod.getThrowsList().getReferencedTypes();
        if (referencedTypes.length == 0) {
            List<PsiClassType> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(14);
            }
            return listEmptyList;
        }
        GlobalSearchScope resolveScope = psiElement.getResolveScope();
        ArrayList arrayList = new ArrayList();
        for (PsiClassType psiClassType : referencedTypes) {
            PsiType psiTypeCorrectType = PsiClassImplUtil.correctType(psiSubstitutor.substitute(psiClassType), resolveScope);
            if (psiTypeCorrectType instanceof PsiCapturedWildcardType) {
                psiTypeCorrectType = ((PsiCapturedWildcardType) psiTypeCorrectType).getUpperBound();
            }
            if (psiTypeCorrectType instanceof PsiClassType) {
                arrayList.add((PsiClassType) psiTypeCorrectType);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void addExceptions(List<PsiClassType> list, Collection<? extends PsiClassType> collection) {
        if (list == null) {
            $$$reportNull$$$0(16);
        }
        if (collection == null) {
            $$$reportNull$$$0(17);
        }
        Iterator<? extends PsiClassType> it = collection.iterator();
        while (it.hasNext()) {
            addException(list, it.next());
        }
    }

    private static void addException(List<PsiClassType> list, PsiClassType psiClassType) {
        if (list == null) {
            $$$reportNull$$$0(18);
        }
        if (psiClassType == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            PsiClassType psiClassType2 = list.get(size);
            if (psiClassType2.isAssignableFrom(psiClassType)) {
                return;
            }
            if (psiClassType.isAssignableFrom(psiClassType2)) {
                list.remove(size);
            }
        }
        list.add(psiClassType);
    }

    public static Collection<PsiClassType> collectUnhandledExceptions(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(22);
        }
        return collectUnhandledExceptions(psiElement, psiElement2, true);
    }

    public static Collection<PsiClassType> collectUnhandledExceptions(PsiElement psiElement, PsiElement psiElement2, boolean z) {
        Predicate predicate;
        if (psiElement == null) {
            $$$reportNull$$$0(23);
        }
        if (z) {
            predicate = new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ExceptionUtil.lambda$collectUnhandledExceptions$1((PsiCallExpression) obj);
                }
            };
        } else {
            predicate = new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ExceptionUtil.lambda$collectUnhandledExceptions$2((PsiCallExpression) obj);
                }
            };
        }
        Set setNotNullize = ContainerUtil.notNullize(collectUnhandledExceptions(psiElement, psiElement2, null, predicate));
        if (setNotNullize == null) {
            $$$reportNull$$$0(24);
        }
        return setNotNullize;
    }

    static /* synthetic */ boolean lambda$collectUnhandledExceptions$2(PsiCallExpression psiCallExpression) {
        PsiMethod psiMethodResolveMethod = psiCallExpression.resolveMethod();
        return psiMethodResolveMethod != null && psiMethodResolveMethod == PsiTreeUtil.getParentOfType(psiCallExpression, PsiMethod.class);
    }

    private static Set<PsiClassType> collectUnhandledExceptions(PsiElement psiElement, PsiElement psiElement2, Set<PsiClassType> set, Predicate<? super PsiCallExpression> predicate) {
        Collection<? extends PsiClassType> unhandledExceptions;
        if (psiElement == null) {
            $$$reportNull$$$0(25);
        }
        if (predicate == null) {
            $$$reportNull$$$0(26);
        }
        if (psiElement instanceof PsiCallExpression) {
            unhandledExceptions = getUnhandledExceptions((PsiCallExpression) psiElement, psiElement2, predicate);
        } else {
            ArrayList arrayList = null;
            arrayList = null;
            if (psiElement instanceof PsiMethodReferenceExpression) {
                PsiExpression qualifierExpression = ((PsiMethodReferenceExpression) psiElement).getQualifierExpression();
                if (qualifierExpression != null) {
                    return collectUnhandledExceptions(qualifierExpression, psiElement2, null, predicate);
                }
                return null;
            }
            if (psiElement instanceof PsiLambdaExpression) {
                return null;
            }
            if (psiElement instanceof PsiThrowStatement) {
                unhandledExceptions = getUnhandledExceptions((PsiThrowStatement) psiElement, psiElement2);
            } else if ((psiElement instanceof PsiCodeBlock) && (psiElement.getLightParent() instanceof PsiMethod) && ((PsiMethod) psiElement.getLightParent()).isConstructor() && !firstStatementIsConstructorCall((PsiCodeBlock) psiElement)) {
                PsiMethod psiMethod = (PsiMethod) psiElement.getLightParent();
                PsiClass containingClass = psiMethod.getContainingClass();
                PsiClass superClass = containingClass != null ? containingClass.getSuperClass() : null;
                PsiMethod[] constructors = superClass == null ? PsiMethod.EMPTY_ARRAY : superClass.getConstructors();
                HashSet hashSet = new HashSet();
                for (PsiMethod psiMethod2 : constructors) {
                    if (!psiMethod2.hasModifierProperty("private") && psiMethod2.getParameterList().isEmpty()) {
                        for (PsiClassType psiClassType : psiMethod2.getThrowsList().getReferencedTypes()) {
                            if (!isUncheckedException(psiClassType) && getHandlePlace(psiElement, psiClassType, psiElement2) == HandlePlace.UNHANDLED) {
                                hashSet.add(psiClassType);
                            }
                        }
                        break;
                    }
                }
                if (containingClass != null) {
                    PsiClassInitializer[] initializers = containingClass.getInitializers();
                    HashSet<PsiClassType> hashSet2 = new HashSet();
                    for (PsiClassInitializer psiClassInitializer : initializers) {
                        if (!psiClassInitializer.hasModifierProperty("static")) {
                            hashSet2.clear();
                            collectUnhandledExceptions(psiClassInitializer.getBody(), psiClassInitializer, hashSet2, predicate);
                            for (PsiClassType psiClassType2 : hashSet2) {
                                if (getHandlePlace(psiMethod.getBody(), psiClassType2, psiElement2) == HandlePlace.UNHANDLED) {
                                    hashSet.add(psiClassType2);
                                }
                            }
                        }
                    }
                }
                unhandledExceptions = hashSet;
            } else {
                if (psiElement instanceof PsiResourceListElement) {
                    List<PsiClassType> unhandledCloserExceptions = getUnhandledCloserExceptions((PsiResourceListElement) psiElement, psiElement2);
                    if (!unhandledCloserExceptions.isEmpty()) {
                        arrayList = new ArrayList(unhandledCloserExceptions);
                    }
                }
                unhandledExceptions = arrayList;
            }
        }
        if (unhandledExceptions != null) {
            if (set == null) {
                set = new HashSet<>();
            }
            set.addAll(unhandledExceptions);
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            Set<PsiClassType> setCollectUnhandledExceptions = collectUnhandledExceptions(firstChild, psiElement2, set, predicate);
            if (set == null) {
                set = setCollectUnhandledExceptions;
            } else if (setCollectUnhandledExceptions != null) {
                set.addAll(setCollectUnhandledExceptions);
            }
        }
        return set;
    }

    private static List<PsiClassType> getUnhandledExceptions(PsiMethodReferenceExpression psiMethodReferenceExpression, PsiElement psiElement) {
        if (psiMethodReferenceExpression == null) {
            $$$reportNull$$$0(27);
        }
        JavaResolveResult javaResolveResultAdvancedResolve = psiMethodReferenceExpression.advancedResolve(false);
        PsiElement element = javaResolveResultAdvancedResolve.getElement();
        if (element instanceof PsiMethod) {
            PsiElement referenceNameElement = psiMethodReferenceExpression.getReferenceNameElement();
            Objects.requireNonNull(javaResolveResultAdvancedResolve);
            return getUnhandledExceptions((PsiMethod) element, referenceNameElement, psiElement, new ExceptionUtil$$ExternalSyntheticLambda3(javaResolveResultAdvancedResolve));
        }
        List<PsiClassType> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(28);
        }
        return listEmptyList;
    }

    private static boolean firstStatementIsConstructorCall(PsiCodeBlock psiCodeBlock) {
        PsiMethod psiMethod;
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(29);
        }
        PsiStatement[] statements = psiCodeBlock.getStatements();
        if (statements.length == 0) {
            return false;
        }
        PsiStatement psiStatement = statements[0];
        if (!(psiStatement instanceof PsiExpressionStatement)) {
            return false;
        }
        PsiExpression expression = ((PsiExpressionStatement) psiStatement).getExpression();
        return (expression instanceof PsiMethodCallExpression) && (psiMethod = (PsiMethod) ((PsiMethodCallExpression) expression).getMethodExpression().resolve()) != null && psiMethod.isConstructor();
    }

    public static List<PsiClassType> getUnhandledExceptions(final PsiElement[] psiElementArr) {
        if (psiElementArr == null) {
            $$$reportNull$$$0(30);
        }
        final ArrayList arrayList = new ArrayList();
        JavaRecursiveElementWalkingVisitor javaRecursiveElementWalkingVisitor = new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                } else {
                    objArr[0] = "expression";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/ExceptionUtil$1";
                if (i != 1) {
                    objArr[2] = "visitElement";
                } else {
                    objArr[2] = "visitMethodReferenceExpression";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitClass(PsiClass psiClass) {
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(0);
                }
                ExceptionUtil.addExceptions(arrayList, ExceptionUtil.getOwnUnhandledExceptions(psiElement));
                super.visitElement(psiElement);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitLambdaExpression(PsiLambdaExpression psiLambdaExpression) {
                if (ArrayUtil.find((PsiLambdaExpression[]) psiElementArr, psiLambdaExpression) >= 0) {
                    visitElement(psiLambdaExpression);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitMethodReferenceExpression(PsiMethodReferenceExpression psiMethodReferenceExpression) {
                if (psiMethodReferenceExpression == null) {
                    $$$reportNull$$$0(1);
                }
                if (ArrayUtil.find((PsiMethodReferenceExpression[]) psiElementArr, psiMethodReferenceExpression) >= 0) {
                    visitElement(psiMethodReferenceExpression);
                }
            }
        };
        for (PsiElement psiElement : psiElementArr) {
            psiElement.accept(javaRecursiveElementWalkingVisitor);
        }
        return arrayList;
    }

    public static List<PsiClassType> getOwnUnhandledExceptions(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(32);
        }
        if (psiElement instanceof PsiEnumConstant) {
            PsiMethod psiMethodResolveMethod = ((PsiEnumConstant) psiElement).resolveMethod();
            if (psiMethodResolveMethod != null) {
                return getUnhandledExceptions(psiMethodResolveMethod, psiElement, (PsiElement) null, (Supplier<? extends PsiSubstitutor>) new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil$$ExternalSyntheticLambda5
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return PsiSubstitutor.EMPTY;
                    }
                });
            }
            List<PsiClassType> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(33);
            }
            return listEmptyList;
        }
        if (psiElement instanceof PsiCallExpression) {
            return getUnhandledExceptions((PsiCallExpression) psiElement, (PsiElement) null);
        }
        if (psiElement instanceof PsiThrowStatement) {
            return getUnhandledExceptions((PsiThrowStatement) psiElement, (PsiElement) null);
        }
        if (psiElement instanceof PsiMethodReferenceExpression) {
            return getUnhandledExceptions((PsiMethodReferenceExpression) psiElement, (PsiElement) null);
        }
        if (psiElement instanceof PsiResourceListElement) {
            return getUnhandledCloserExceptions((PsiResourceListElement) psiElement, null);
        }
        List<PsiClassType> listEmptyList2 = Collections.emptyList();
        if (listEmptyList2 == null) {
            $$$reportNull$$$0(34);
        }
        return listEmptyList2;
    }

    public static List<PsiClassType> getUnhandledExceptions(PsiCallExpression psiCallExpression, PsiElement psiElement) {
        if (psiCallExpression == null) {
            $$$reportNull$$$0(36);
        }
        return getUnhandledExceptions(psiCallExpression, psiElement, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ExceptionUtil.lambda$getUnhandledExceptions$4((PsiCallExpression) obj);
            }
        });
    }

    private static List<PsiClassType> getUnhandledExceptions(PsiCallExpression psiCallExpression, PsiElement psiElement, Predicate<? super PsiCallExpression> predicate) {
        if (psiCallExpression == null) {
            $$$reportNull$$$0(37);
        }
        if (predicate == null) {
            $$$reportNull$$$0(38);
        }
        if (MethodCandidateInfo.isOverloadCheck()) {
            List<PsiClassType> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(39);
            }
            return listEmptyList;
        }
        JavaResolveResult diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCallExpression);
        PsiElement element = diamondsAwareResolveResult.getElement();
        final PsiMethod psiMethod = element instanceof PsiMethod ? (PsiMethod) element : null;
        if (psiMethod == null) {
            List<PsiClassType> listEmptyList2 = Collections.emptyList();
            if (listEmptyList2 == null) {
                $$$reportNull$$$0(40);
            }
            return listEmptyList2;
        }
        if (predicate.test(psiCallExpression)) {
            List<PsiClassType> listEmptyList3 = Collections.emptyList();
            if (listEmptyList3 == null) {
                $$$reportNull$$$0(41);
            }
            return listEmptyList3;
        }
        PsiClassType[] referencedTypes = psiMethod.getThrowsList().getReferencedTypes();
        if (referencedTypes.length == 0) {
            List<PsiClassType> listEmptyList4 = Collections.emptyList();
            if (listEmptyList4 == null) {
                $$$reportNull$$$0(42);
            }
            return listEmptyList4;
        }
        if (!isArrayClone(psiMethod, psiCallExpression) && (psiCallExpression instanceof PsiMethodCallExpression)) {
            MethodResolverProcessor methodResolverProcessor = new MethodResolverProcessor((PsiMethodCallExpression) psiCallExpression, psiCallExpression.getContainingFile());
            try {
                PsiScopesUtil.setupAndRunProcessor(methodResolverProcessor, psiCallExpression, false);
                List<Pair> listMapNotNull = ContainerUtil.mapNotNull(methodResolverProcessor.getResults(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil$$ExternalSyntheticLambda4
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return ExceptionUtil.lambda$getUnhandledExceptions$5(psiMethod, (CandidateInfo) obj);
                    }
                });
                if (!listMapNotNull.isEmpty()) {
                    GlobalSearchScope resolveScope = psiCallExpression.getResolveScope();
                    List<PsiClassType> listCollectSubstituted = collectSubstituted(diamondsAwareResolveResult.getSubstitutor(), referencedTypes, resolveScope);
                    for (Pair pair : listMapNotNull) {
                        PsiClassType[] referencedTypes2 = ((PsiMethod) pair.first).getThrowsList().getReferencedTypes();
                        if (referencedTypes2.length == 0) {
                            List<PsiClassType> listEmptyList5 = Collections.emptyList();
                            if (listEmptyList5 == null) {
                                $$$reportNull$$$0(43);
                            }
                            return listEmptyList5;
                        }
                        retainExceptions(listCollectSubstituted, collectSubstituted((PsiSubstitutor) pair.second, referencedTypes2, resolveScope));
                    }
                    List<PsiClassType> unhandledExceptions = getUnhandledExceptions(psiCallExpression, psiElement, PsiSubstitutor.EMPTY, (PsiClassType[]) listCollectSubstituted.toArray(PsiClassType.EMPTY_ARRAY));
                    if (unhandledExceptions == null) {
                        $$$reportNull$$$0(44);
                    }
                    return unhandledExceptions;
                }
            } catch (MethodProcessorSetupFailedException unused) {
                List<PsiClassType> listEmptyList6 = Collections.emptyList();
                if (listEmptyList6 == null) {
                    $$$reportNull$$$0(45);
                }
                return listEmptyList6;
            }
        }
        Objects.requireNonNull(diamondsAwareResolveResult);
        return getUnhandledExceptions(psiMethod, psiCallExpression, psiElement, new ExceptionUtil$$ExternalSyntheticLambda3(diamondsAwareResolveResult));
    }

    static /* synthetic */ Pair lambda$getUnhandledExceptions$5(PsiMethod psiMethod, CandidateInfo candidateInfo) {
        PsiElement element = candidateInfo.getElement();
        if (!(candidateInfo instanceof MethodCandidateInfo) || element == psiMethod) {
            return null;
        }
        PsiMethod psiMethod2 = (PsiMethod) element;
        if (!MethodSignatureUtil.areSignaturesEqual(psiMethod, psiMethod2) || psiMethod2.hasModifierProperty("private") || MethodSignatureUtil.isSuperMethod(psiMethod2, psiMethod)) {
            return null;
        }
        MethodCandidateInfo methodCandidateInfo = (MethodCandidateInfo) candidateInfo;
        if (!methodCandidateInfo.isToInferApplicability() || methodCandidateInfo.isApplicable()) {
            return Pair.create(psiMethod2, methodCandidateInfo.getSubstitutor(false));
        }
        return null;
    }

    public static void retainExceptions(List<PsiClassType> list, List<? extends PsiClassType> list2) {
        ArrayList arrayList = new ArrayList();
        Iterator<PsiClassType> it = list.iterator();
        while (it.hasNext()) {
            PsiClassType next = it.next();
            Iterator<? extends PsiClassType> it2 = list2.iterator();
            while (true) {
                if (it2.hasNext()) {
                    PsiClassType next2 = it2.next();
                    if (next2.isAssignableFrom(next)) {
                        break;
                    } else if (next.isAssignableFrom(next2) && isUncheckedException(next) == isUncheckedException(next2)) {
                        arrayList.add(next2);
                    }
                } else {
                    it.remove();
                    break;
                }
            }
        }
        list.removeAll(arrayList);
        list.addAll(arrayList);
    }

    public static List<PsiClassType> collectSubstituted(PsiSubstitutor psiSubstitutor, PsiClassType[] psiClassTypeArr, GlobalSearchScope globalSearchScope) {
        ArrayList arrayList = new ArrayList();
        for (PsiClassType psiClassType : psiClassTypeArr) {
            PsiType psiTypeCorrectType = PsiClassImplUtil.correctType(psiSubstitutor.substitute(psiClassType), globalSearchScope);
            if (psiTypeCorrectType instanceof PsiClassType) {
                arrayList.add((PsiClassType) psiTypeCorrectType);
            } else if (psiTypeCorrectType instanceof PsiCapturedWildcardType) {
                PsiType upperBound = ((PsiCapturedWildcardType) psiTypeCorrectType).getUpperBound();
                if (upperBound instanceof PsiClassType) {
                    arrayList.add((PsiClassType) upperBound);
                }
            }
        }
        return arrayList;
    }

    public static List<PsiClassType> getCloserExceptions(PsiResourceListElement psiResourceListElement) {
        if (psiResourceListElement == null) {
            $$$reportNull$$$0(46);
        }
        List<PsiClassType> exceptionsFromClose = getExceptionsFromClose(psiResourceListElement);
        if (exceptionsFromClose == null) {
            exceptionsFromClose = Collections.emptyList();
        }
        if (exceptionsFromClose == null) {
            $$$reportNull$$$0(47);
        }
        return exceptionsFromClose;
    }

    public static List<PsiClassType> getUnhandledCloserExceptions(PsiResourceListElement psiResourceListElement, PsiElement psiElement) {
        if (psiResourceListElement == null) {
            $$$reportNull$$$0(48);
        }
        return getUnhandledCloserExceptions(psiResourceListElement, psiElement, psiResourceListElement.getType());
    }

    public static List<PsiClassType> getUnhandledCloserExceptions(PsiElement psiElement, PsiElement psiElement2, PsiType psiType) {
        List<PsiClassType> exceptionsFromClose = psiType instanceof PsiClassType ? getExceptionsFromClose(psiType, psiElement.getResolveScope()) : null;
        List<PsiClassType> unhandledExceptions = exceptionsFromClose != null ? getUnhandledExceptions(psiElement, psiElement2, PsiSubstitutor.EMPTY, (PsiClassType[]) exceptionsFromClose.toArray(PsiClassType.EMPTY_ARRAY)) : Collections.emptyList();
        if (unhandledExceptions == null) {
            $$$reportNull$$$0(49);
        }
        return unhandledExceptions;
    }

    private static List<PsiClassType> getExceptionsFromClose(PsiResourceListElement psiResourceListElement) {
        PsiType type = psiResourceListElement.getType();
        if (type instanceof PsiClassType) {
            return getExceptionsFromClose(type, psiResourceListElement.getResolveScope());
        }
        return null;
    }

    private static List<PsiClassType> getExceptionsFromClose(PsiType psiType, GlobalSearchScope globalSearchScope) {
        PsiSubstitutor classSubstitutor;
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        PsiClass element = classResolveResultResolveGenericsClassInType.getElement();
        List<PsiClassType> listCollectSubstituted = null;
        if (element == null) {
            return null;
        }
        PsiMethod[] resourceCloserMethodsForType = PsiUtil.getResourceCloserMethodsForType((PsiClassType) psiType);
        if (resourceCloserMethodsForType != null) {
            for (PsiMethod psiMethod : resourceCloserMethodsForType) {
                PsiClass containingClass = psiMethod.getContainingClass();
                if (containingClass != null && (classSubstitutor = TypeConversionUtil.getClassSubstitutor(containingClass, element, classResolveResultResolveGenericsClassInType.getSubstitutor())) != null) {
                    PsiClassType[] referencedTypes = psiMethod.getThrowsList().getReferencedTypes();
                    if (referencedTypes.length == 0) {
                        return Collections.emptyList();
                    }
                    if (listCollectSubstituted == null) {
                        listCollectSubstituted = collectSubstituted(classSubstitutor, referencedTypes, globalSearchScope);
                    } else {
                        retainExceptions(listCollectSubstituted, collectSubstituted(classSubstitutor, referencedTypes, globalSearchScope));
                    }
                }
            }
        }
        return listCollectSubstituted;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x005b  */
    public static List<PsiClassType> getUnhandledExceptions(PsiThrowStatement psiThrowStatement, PsiElement psiElement) {
        PsiClassType psiClassType;
        if (psiThrowStatement == null) {
            $$$reportNull$$$0(50);
        }
        SmartList smartList = new SmartList();
        for (PsiType psiType : getPreciseThrowTypes(psiThrowStatement.getException())) {
            for (PsiType psiType2 : psiType instanceof PsiDisjunctionType ? ((PsiDisjunctionType) psiType).getDisjunctions() : Collections.singletonList(psiType)) {
                if (psiType2 instanceof PsiClassType) {
                    psiClassType = (PsiClassType) psiType2;
                } else if (psiType2 instanceof PsiCapturedWildcardType) {
                    PsiType upperBound = ((PsiCapturedWildcardType) psiType2).getUpperBound();
                    if (upperBound instanceof PsiClassType) {
                        psiClassType = (PsiClassType) upperBound;
                    } else {
                        psiClassType = null;
                    }
                } else {
                    psiClassType = null;
                }
                if (psiClassType != null && !isUncheckedException(psiClassType) && getHandlePlace(psiThrowStatement, psiClassType, psiElement) == HandlePlace.UNHANDLED) {
                    smartList.add(psiClassType);
                }
            }
        }
        return smartList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<PsiType> getPreciseThrowTypes(PsiExpression psiExpression) {
        PsiType type;
        PsiElement psiElementResolve;
        PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(psiExpression);
        if ((psiExpressionSkipParenthesizedExprDown instanceof PsiReferenceExpression) && (psiElementResolve = ((PsiReferenceExpression) psiExpressionSkipParenthesizedExprDown).resolve()) != null && PsiUtil.isCatchParameter(psiElementResolve)) {
            List<PsiType> preciseCatchTypes = ((PsiCatchSection) psiElementResolve.getLightParent()).getPreciseCatchTypes();
            if (preciseCatchTypes == null) {
                $$$reportNull$$$0(52);
            }
            return preciseCatchTypes;
        }
        if (psiExpressionSkipParenthesizedExprDown != null && (type = psiExpressionSkipParenthesizedExprDown.getType()) != null) {
            List<PsiType> listSingletonList = Collections.singletonList(type);
            if (listSingletonList == null) {
                $$$reportNull$$$0(53);
            }
            return listSingletonList;
        }
        List<PsiType> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(54);
        }
        return listEmptyList;
    }

    private static List<PsiClassType> getUnhandledExceptions(PsiMethod psiMethod, PsiElement psiElement, PsiElement psiElement2, Supplier<? extends PsiSubstitutor> supplier) {
        if (psiMethod == null) {
            $$$reportNull$$$0(55);
        }
        if (supplier == null) {
            $$$reportNull$$$0(56);
        }
        if (isArrayClone(psiMethod, psiElement)) {
            List<PsiClassType> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(57);
            }
            return listEmptyList;
        }
        PsiClassType[] referencedTypes = psiMethod.getThrowsList().getReferencedTypes();
        if (referencedTypes.length == 0) {
            List<PsiClassType> listEmptyList2 = Collections.emptyList();
            if (listEmptyList2 == null) {
                $$$reportNull$$$0(58);
            }
            return listEmptyList2;
        }
        List<PsiClassType> unhandledExceptions = getUnhandledExceptions(psiElement, psiElement2, supplier.get(), referencedTypes);
        if (unhandledExceptions == null) {
            $$$reportNull$$$0(59);
        }
        return unhandledExceptions;
    }

    private static List<PsiClassType> getUnhandledExceptions(PsiElement psiElement, PsiElement psiElement2, PsiSubstitutor psiSubstitutor, PsiClassType[] psiClassTypeArr) {
        if (psiClassTypeArr.length > 0) {
            ArrayList arrayList = new ArrayList();
            for (PsiClassType psiClassType : psiClassTypeArr) {
                PsiType psiTypeCorrectType = PsiClassImplUtil.correctType(GenericsUtil.eliminateWildcards(psiSubstitutor.substitute(psiClassType), false), psiElement.getResolveScope());
                if (psiTypeCorrectType instanceof PsiClassType) {
                    PsiClassType psiClassType2 = (PsiClassType) psiTypeCorrectType;
                    if (psiClassType2.resolve() != null && !isUncheckedException(psiClassType2) && getHandlePlace(psiElement, psiClassType2, psiElement2) == HandlePlace.UNHANDLED) {
                        arrayList.add(psiClassType2);
                    }
                }
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    private static boolean isArrayClone(PsiMethod psiMethod, PsiElement psiElement) {
        PsiClass containingClass;
        PsiExpression qualifierExpression;
        if (psiMethod == null) {
            $$$reportNull$$$0(60);
        }
        if (!psiMethod.get$name().equals("clone") || (containingClass = psiMethod.getContainingClass()) == null || !CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass.getQualifiedName())) {
            return false;
        }
        if (!(psiElement instanceof PsiMethodReferenceExpression)) {
            return (psiElement instanceof PsiMethodCallExpression) && (qualifierExpression = ((PsiMethodCallExpression) psiElement).getMethodExpression().getQualifierExpression()) != null && (qualifierExpression.getType() instanceof PsiArrayType);
        }
        PsiExpression qualifierExpression2 = ((PsiMethodReferenceExpression) psiElement).getQualifierExpression();
        return qualifierExpression2 != null && (qualifierExpression2.getType() instanceof PsiArrayType);
    }

    public static boolean isUncheckedException(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(61);
        }
        return InheritanceUtil.isInheritor(psiClassType, CommonClassNames.JAVA_LANG_RUNTIME_EXCEPTION) || InheritanceUtil.isInheritor(psiClassType, CommonClassNames.JAVA_LANG_ERROR);
    }

    public static boolean isUncheckedExceptionOrSuperclass(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(63);
        }
        return isGeneralExceptionType(psiClassType) || isUncheckedException(psiClassType);
    }

    public static boolean isGeneralExceptionType(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(64);
        }
        return psiType.equalsToText(CommonClassNames.JAVA_LANG_THROWABLE) || psiType.equalsToText(CommonClassNames.JAVA_LANG_EXCEPTION) || psiType.equalsToText(CommonClassNames.JAVA_LANG_ERROR);
    }

    public interface HandlePlace {
        public static final HandlePlace UNHANDLED = new HandlePlace() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil.HandlePlace.1
        };
        public static final HandlePlace UNKNOWN = new HandlePlace() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil.HandlePlace.2
        };

        public static class TryCatch implements HandlePlace {
            private final PsiParameter myParameter;
            private final PsiTryStatement myTryStatement;

            public TryCatch(PsiTryStatement psiTryStatement, PsiParameter psiParameter) {
                this.myTryStatement = psiTryStatement;
                this.myParameter = psiParameter;
            }
        }

        static HandlePlace fromBoolean(boolean z) {
            return z ? UNKNOWN : UNHANDLED;
        }
    }

    /* JADX WARN: Code duplicated, block: B:111:0x014e  */
    /* JADX WARN: Code duplicated, block: B:114:0x015e  */
    /* JADX WARN: Code duplicated, block: B:118:0x016e  */
    /* JADX WARN: Code duplicated, block: B:131:0x016a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:132:? A[LOOP:0: B:112:0x0158->B:132:?, LOOP_END, SYNTHETIC] */
    public static HandlePlace getHandlePlace(PsiElement psiElement, PsiClassType psiClassType, PsiElement psiElement2) {
        Iterator<CustomExceptionHandler> it;
        HandlePlace handlePlace;
        HandlePlace caughtPlace;
        HandlePlace caughtPlace2;
        if (psiClassType == null) {
            $$$reportNull$$$0(67);
        }
        if (psiElement == null || psiElement.getLightParent() == psiElement2 || psiElement.getLightParent() == null) {
            HandlePlace handlePlace2 = HandlePlace.UNHANDLED;
            if (handlePlace2 == null) {
                $$$reportNull$$$0(68);
            }
            return handlePlace2;
        }
        PsiElement myParent = psiElement.getLightParent();
        if (myParent instanceof PsiMethod) {
            HandlePlace handlePlaceFromBoolean = HandlePlace.fromBoolean(isHandledByMethodThrowsClause((PsiMethod) myParent, psiClassType));
            if (handlePlaceFromBoolean == null) {
                $$$reportNull$$$0(69);
            }
            return handlePlaceFromBoolean;
        }
        if (myParent instanceof PsiClass) {
            if (myParent instanceof PsiAnonymousClass) {
                return getHandlePlace(myParent, psiClassType, psiElement2);
            }
            HandlePlace handlePlace3 = HandlePlace.UNHANDLED;
            if (handlePlace3 == null) {
                $$$reportNull$$$0(70);
            }
            return handlePlace3;
        }
        if ((myParent instanceof PsiLambdaExpression) || ((myParent instanceof PsiMethodReferenceExpression) && psiElement == ((PsiMethodReferenceExpression) myParent).getReferenceNameElement())) {
            HandlePlace handlePlaceFromBoolean2 = HandlePlace.fromBoolean(isDeclaredBySAMMethod(psiClassType, ((PsiFunctionalExpression) myParent).getFunctionalInterfaceType()));
            if (handlePlaceFromBoolean2 == null) {
                $$$reportNull$$$0(71);
            }
            return handlePlaceFromBoolean2;
        }
        if (myParent instanceof PsiClassInitializer) {
            PsiClassInitializer psiClassInitializer = (PsiClassInitializer) myParent;
            if (psiClassInitializer.hasModifierProperty("static")) {
                HandlePlace handlePlace4 = HandlePlace.UNHANDLED;
                if (handlePlace4 == null) {
                    $$$reportNull$$$0(72);
                }
                return handlePlace4;
            }
            if (!(myParent.getLightParent() instanceof PsiAnonymousClass)) {
                HandlePlace handlePlaceFromBoolean3 = HandlePlace.fromBoolean(areAllConstructorsThrow(psiClassInitializer.getContainingClass(), psiClassType));
                if (handlePlaceFromBoolean3 == null) {
                    $$$reportNull$$$0(73);
                }
                return handlePlaceFromBoolean3;
            }
        } else if (myParent instanceof PsiTryStatement) {
            PsiTryStatement psiTryStatement = (PsiTryStatement) myParent;
            if (psiTryStatement.getTryBlock() == psiElement && (caughtPlace2 = getCaughtPlace(psiTryStatement, psiClassType)) != HandlePlace.UNHANDLED) {
                if (caughtPlace2 == null) {
                    $$$reportNull$$$0(74);
                }
                return caughtPlace2;
            }
            if (psiTryStatement.getResourceList() == psiElement && (caughtPlace = getCaughtPlace(psiTryStatement, psiClassType)) != HandlePlace.UNHANDLED) {
                if (caughtPlace == null) {
                    $$$reportNull$$$0(75);
                }
                return caughtPlace;
            }
            PsiCodeBlock finallyBlock = psiTryStatement.getFinallyBlock();
            if ((psiElement instanceof PsiCatchSection) && finallyBlock != null && blockCompletesAbruptly(finallyBlock)) {
                HandlePlace handlePlace5 = HandlePlace.UNKNOWN;
                if (handlePlace5 == null) {
                    $$$reportNull$$$0(76);
                }
                return handlePlace5;
            }
        } else {
            boolean z = false;
            if (myParent instanceof JavaCodeFragment) {
                JavaCodeFragment.ExceptionHandler exceptionHandler = ((JavaCodeFragment) myParent).getExceptionHandler();
                if (exceptionHandler != null && exceptionHandler.isHandledException(psiClassType)) {
                    z = true;
                }
                HandlePlace handlePlaceFromBoolean4 = HandlePlace.fromBoolean(z);
                if (handlePlaceFromBoolean4 == null) {
                    $$$reportNull$$$0(77);
                }
                return handlePlaceFromBoolean4;
            }
            if (PsiImplUtil.isInServerPage(myParent) && (myParent instanceof PsiFile)) {
                HandlePlace handlePlace6 = HandlePlace.UNKNOWN;
                if (handlePlace6 == null) {
                    $$$reportNull$$$0(78);
                }
                return handlePlace6;
            }
            if (myParent instanceof PsiFile) {
                HandlePlace handlePlaceFromBoolean5 = HandlePlace.fromBoolean(false);
                if (handlePlaceFromBoolean5 == null) {
                    $$$reportNull$$$0(79);
                }
                return handlePlaceFromBoolean5;
            }
            if (myParent instanceof PsiField) {
                PsiField psiField = (PsiField) myParent;
                if (psiField.getInitializer() == psiElement) {
                    PsiClass containingClass = psiField.getContainingClass();
                    if (containingClass != null && !(containingClass instanceof PsiAnonymousClass) && !psiField.hasModifierProperty("static")) {
                        HandlePlace handlePlaceFromBoolean6 = HandlePlace.fromBoolean(areAllConstructorsThrow(containingClass, psiClassType));
                        if (handlePlaceFromBoolean6 == null) {
                            $$$reportNull$$$0(80);
                        }
                        return handlePlaceFromBoolean6;
                    }
                } else {
                    it = CustomExceptionHandler.KEY.getExtensionList().iterator();
                    while (it.hasNext()) {
                        if (it.next().isHandled(psiElement, psiClassType, psiElement2)) {
                            handlePlace = HandlePlace.UNKNOWN;
                            if (handlePlace == null) {
                                $$$reportNull$$$0(81);
                            }
                            return handlePlace;
                        }
                    }
                }
            } else {
                it = CustomExceptionHandler.KEY.getExtensionList().iterator();
                while (it.hasNext()) {
                    if (it.next().isHandled(psiElement, psiClassType, psiElement2)) {
                        handlePlace = HandlePlace.UNKNOWN;
                        if (handlePlace == null) {
                            $$$reportNull$$$0(81);
                        }
                        return handlePlace;
                    }
                }
            }
        }
        return getHandlePlace(myParent, psiClassType, psiElement2);
    }

    private static boolean isDeclaredBySAMMethod(PsiClassType psiClassType, PsiType psiType) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType;
        PsiMethod functionalInterfaceMethod;
        if (psiClassType == null) {
            $$$reportNull$$$0(82);
        }
        if (psiType == null || (functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod((classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType)))) == null) {
            return true;
        }
        return isHandledByMethodThrowsClause(functionalInterfaceMethod, psiClassType, LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType));
    }

    private static boolean areAllConstructorsThrow(PsiClass psiClass, PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(83);
        }
        if (psiClass == null) {
            return false;
        }
        PsiMethod[] constructors = psiClass.getConstructors();
        boolean z = constructors.length != 0;
        for (PsiMethod psiMethod : constructors) {
            if (!isHandledByMethodThrowsClause(psiMethod, psiClassType)) {
                return false;
            }
        }
        return z;
    }

    private static HandlePlace getCaughtPlace(PsiTryStatement psiTryStatement, PsiClassType psiClassType) {
        if (psiTryStatement == null) {
            $$$reportNull$$$0(84);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(85);
        }
        PsiCodeBlock finallyBlock = psiTryStatement.getFinallyBlock();
        if (finallyBlock != null && blockCompletesAbruptly(finallyBlock)) {
            HandlePlace handlePlace = HandlePlace.UNKNOWN;
            if (handlePlace == null) {
                $$$reportNull$$$0(86);
            }
            return handlePlace;
        }
        for (PsiParameter psiParameter : psiTryStatement.getCatchBlockParameters()) {
            if (psiParameter.getType().isAssignableFrom(psiClassType)) {
                return new HandlePlace.TryCatch(psiTryStatement, psiParameter);
            }
        }
        HandlePlace handlePlace2 = HandlePlace.UNHANDLED;
        if (handlePlace2 == null) {
            $$$reportNull$$$0(87);
        }
        return handlePlace2;
    }

    private static boolean blockCompletesAbruptly(PsiCodeBlock psiCodeBlock) {
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(88);
        }
        try {
            ControlFlow controlFlow = ControlFlowFactory.getInstance(psiCodeBlock.getProject()).getControlFlow((PsiElement) psiCodeBlock, (ControlFlowPolicy) LocalsOrMyInstanceFieldsControlFlowPolicy.getInstance(), false);
            return !BitUtil.isSet(ControlFlowUtil.getCompletionReasons(controlFlow, 0, controlFlow.getSize()), 1);
        } catch (AnalysisCanceledException unused) {
            return true;
        }
    }

    private static boolean isHandledByMethodThrowsClause(PsiMethod psiMethod, PsiClassType psiClassType) {
        if (psiMethod == null) {
            $$$reportNull$$$0(89);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(90);
        }
        return isHandledByMethodThrowsClause(psiMethod, psiClassType, PsiSubstitutor.EMPTY);
    }

    private static boolean isHandledByMethodThrowsClause(PsiMethod psiMethod, PsiClassType psiClassType, PsiSubstitutor psiSubstitutor) {
        if (psiMethod == null) {
            $$$reportNull$$$0(91);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(92);
        }
        return isHandledBy(psiClassType, psiMethod.getThrowsList().getReferencedTypes(), psiSubstitutor);
    }

    public static boolean isHandledBy(PsiClassType psiClassType, PsiClassType[] psiClassTypeArr, PsiSubstitutor psiSubstitutor) {
        if (psiClassType == null) {
            $$$reportNull$$$0(95);
        }
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(96);
        }
        for (PsiClassType psiClassType2 : psiClassTypeArr) {
            PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiClassType2);
            if (psiTypeSubstitute != null && psiTypeSubstitute.isAssignableFrom(psiClassType)) {
                return true;
            }
        }
        return false;
    }
}
