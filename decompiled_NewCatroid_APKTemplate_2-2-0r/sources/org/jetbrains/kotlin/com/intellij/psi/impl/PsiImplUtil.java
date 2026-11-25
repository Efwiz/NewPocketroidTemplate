package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiLabeledStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiLoopStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMirrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceListElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.ServerPageFile;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.ThreadLocalTypes;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PackageScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaPsiPatternUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PairFunction;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiImplUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiImplUtil.class);
    private static final Key<Boolean> TYPE_ANNO_MARK = Key.create("type.annotation.mark");

    /* JADX WARN: Code duplicated, block: B:120:0x01dd  */
    /* JADX WARN: Code duplicated, block: B:22:0x0046 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 9 && i != 16 && i != 38 && i != 79 && i != 88 && i != 98 && i != 5 && i != 6 && i != 35 && i != 36 && i != 65 && i != 66 && i != 100 && i != 101) {
            switch (i) {
                default:
                    switch (i) {
                        case 91:
                        case 92:
                        case 93:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        String str2 = str;
        if (i != 1 && i != 9 && i != 16 && i != 38 && i != 79 && i != 88 && i != 98 && i != 5 && i != 6 && i != 35 && i != 36 && i != 65 && i != 66 && i != 100 && i != 101) {
            switch (i) {
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 91:
                        case 92:
                        case 93:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
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
            case 5:
            case 6:
            case 9:
            case 16:
            case 35:
            case 36:
            case 38:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 65:
            case 66:
            case 79:
            case 88:
            case 91:
            case 92:
            case 93:
            case 98:
            case 100:
            case 101:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiImplUtil";
                break;
            case 2:
            case 3:
                objArr[0] = "annotation";
                break;
            case 4:
            case 32:
            case 53:
            case 54:
                objArr[0] = "owner";
                break;
            case 7:
                objArr[0] = "manager";
                break;
            case 8:
                objArr[0] = "names";
                break;
            case 10:
                objArr[0] = "parameter";
                break;
            case 11:
            case 33:
                objArr[0] = "parameterList";
                break;
            case 12:
                objArr[0] = "typeParameter";
                break;
            case 13:
                objArr[0] = "typeParameterList";
                break;
            case 14:
                objArr[0] = "reference";
                break;
            case 15:
                objArr[0] = "filter";
                break;
            case 17:
            case 67:
                objArr[0] = "method";
                break;
            case 18:
            case 22:
            case 26:
            case 30:
                objArr[0] = "processor";
                break;
            case 19:
            case 23:
            case 27:
            case 31:
                objArr[0] = "state";
                break;
            case 20:
            case 24:
            case 28:
                objArr[0] = "place";
                break;
            case 21:
                objArr[0] = "lambda";
                break;
            case 25:
            case 40:
            case 51:
            case 86:
            case 89:
            case 96:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 29:
                objArr[0] = "resourceList";
                break;
            case 34:
                objArr[0] = "typeElements";
                break;
            case 37:
                objArr[0] = "classAccessExpression";
                break;
            case 39:
                objArr[0] = "qualifiedName";
                break;
            case 41:
            case 81:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 42:
            case 43:
                objArr[0] = "expression";
                break;
            case 44:
                objArr[0] = "topLevel";
                break;
            case 45:
                objArr[0] = "member";
                break;
            case 52:
                objArr[0] = "name";
                break;
            case 55:
                objArr[0] = "psiElement";
                break;
            case 56:
                objArr[0] = "comment";
                break;
            case 57:
                objArr[0] = "psiAnnotation";
                break;
            case 58:
            case 61:
                objArr[0] = "annotationCreator";
                break;
            case 59:
                objArr[0] = "value";
                break;
            case 60:
                objArr[0] = "namePrefix";
                break;
            case 62:
                objArr[0] = "alsoSkip";
                break;
            case 63:
                objArr[0] = "statement";
                break;
            case 64:
                objArr[0] = "psiCodeBlock";
                break;
            case 68:
                objArr[0] = "ref";
                break;
            case 69:
                objArr[0] = "modifierList";
                break;
            case 70:
                objArr[0] = "annotations";
                break;
            case 71:
            case 72:
                objArr[0] = "typeElement";
                break;
            case 73:
            case 74:
            case 75:
            case 76:
            case 78:
                objArr[0] = "start";
                break;
            case 77:
                objArr[0] = CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME;
                break;
            case 80:
                objArr[0] = "labelElement";
                break;
            case 82:
                objArr[0] = "tokenSet";
                break;
            case 83:
                objArr[0] = "typeName";
                break;
            case 84:
                objArr[0] = "context";
                break;
            case 85:
            case 95:
                objArr[0] = "psiFile";
                break;
            case 87:
            case 90:
            case 97:
                objArr[0] = "resolver";
                break;
            case 94:
                objArr[0] = "project";
                break;
            case 99:
                objArr[0] = "module";
                break;
            default:
                objArr[0] = "aClass";
                break;
        }
        if (i == 1) {
            objArr[1] = "getConstructors";
        } else if (i == 9) {
            objArr[1] = "namesToPackageReferences";
        } else if (i == 16) {
            objArr[1] = "getReferenceVariantsByFilter";
        } else if (i == 38) {
            objArr[1] = "getType";
        } else if (i == 79) {
            objArr[1] = "findAllEnclosingLabels";
        } else if (i != 88 && i != 98) {
            if (i != 5 && i != 6) {
                if (i != 35 && i != 36) {
                    if (i != 65 && i != 66) {
                        if (i != 100 && i != 101) {
                            switch (i) {
                                case 46:
                                case 47:
                                case 48:
                                case 49:
                                case 50:
                                    objArr[1] = "getMemberUseScope";
                                    break;
                                default:
                                    switch (i) {
                                        case 91:
                                        case 92:
                                        case 93:
                                            objArr[1] = "multiResolveImpl";
                                            break;
                                        default:
                                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiImplUtil";
                                            break;
                                    }
                                    break;
                            }
                        } else {
                            objArr[1] = "getModuleVirtualFile";
                        }
                    } else {
                        objArr[1] = "getChildStatements";
                    }
                } else {
                    objArr[1] = "typesByTypeElements";
                }
            } else {
                objArr[1] = "getTypeParameters";
            }
        } else {
            objArr[1] = "multiResolveImpl";
        }
        switch (i) {
            case 1:
            case 5:
            case 6:
            case 9:
            case 16:
            case 35:
            case 36:
            case 38:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 65:
            case 66:
            case 79:
            case 88:
            case 91:
            case 92:
            case 93:
            case 98:
            case 100:
            case 101:
                break;
            case 2:
                objArr[2] = "findDeclaredAttributeValue";
                break;
            case 3:
                objArr[2] = "findAttributeValue";
                break;
            case 4:
                objArr[2] = "getTypeParameters";
                break;
            case 7:
            case 8:
                objArr[2] = "namesToPackageReferences";
                break;
            case 10:
            case 11:
                objArr[2] = "getParameterIndex";
                break;
            case 12:
            case 13:
                objArr[2] = "getTypeParameterIndex";
                break;
            case 14:
            case 15:
                objArr[2] = "getReferenceVariantsByFilter";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[2] = "processDeclarationsInMethod";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "processDeclarationsInLambda";
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "processDeclarationsInMethodLike";
                break;
            case 29:
            case 30:
            case 31:
                objArr[2] = "processDeclarationsInResourceList";
                break;
            case 32:
                objArr[2] = "hasTypeParameters";
                break;
            case 33:
                objArr[2] = "typesByReferenceParameterList";
                break;
            case 34:
                objArr[2] = "typesByTypeElements";
                break;
            case 37:
                objArr[2] = "getType";
                break;
            case 39:
                objArr[2] = "findAnnotation";
                break;
            case 40:
                objArr[2] = "findDocComment";
                break;
            case 41:
            case 42:
                objArr[2] = "normalizeWildcardTypeByPosition";
                break;
            case 43:
            case 44:
                objArr[2] = "doNormalizeWildcardByPosition";
                break;
            case 45:
                objArr[2] = "getMemberUseScope";
                break;
            case 51:
            case 52:
                objArr[2] = "setName";
                break;
            case 53:
                objArr[2] = "isDeprecatedByAnnotation";
                break;
            case 54:
                objArr[2] = "isDeprecatedByDocTag";
                break;
            case 55:
                objArr[2] = "isDeprecated";
                break;
            case 56:
                objArr[2] = "findDocCommentOwner";
                break;
            case 57:
            case 58:
                objArr[2] = "setDeclaredAttributeValue";
                break;
            case 59:
            case 60:
            case 61:
                objArr[2] = "createNameValuePair";
                break;
            case 62:
                objArr[2] = "skipWhitespaceCommentsAndTokens";
                break;
            case 63:
                objArr[2] = "findStatementChild";
                break;
            case 64:
                objArr[2] = "getChildStatements";
                break;
            case 67:
                objArr[2] = "isVarArgs";
                break;
            case 68:
                objArr[2] = "findNeighbourModifierList";
                break;
            case 69:
            case 70:
                objArr[2] = "collectTypeUseAnnotations";
                break;
            case 71:
                objArr[2] = "markTypeAnnotations";
                break;
            case 72:
                objArr[2] = "deleteTypeAnnotations";
                break;
            case 73:
                objArr[2] = "findEnclosingLoop";
                break;
            case 74:
                objArr[2] = "findEnclosingSwitchOrLoop";
                break;
            case 75:
                objArr[2] = "findEnclosingSwitchExpression";
                break;
            case 76:
            case 77:
                objArr[2] = "findEnclosingLabeledStatement";
                break;
            case 78:
                objArr[2] = "findAllEnclosingLabels";
                break;
            case 80:
                objArr[2] = "getSwitchLabel";
                break;
            case 81:
            case 82:
                objArr[2] = "isLeafElementOfType";
                break;
            case 83:
            case 84:
            case 85:
                objArr[2] = "buildTypeFromTypeString";
                break;
            case 86:
            case 87:
            case 89:
            case 90:
            case 94:
            case 95:
            case 96:
            case 97:
                objArr[2] = "multiResolveImpl";
                break;
            case 99:
                objArr[2] = "getModuleVirtualFile";
                break;
            default:
                objArr[2] = "getConstructors";
                break;
        }
        String str3 = String.format(str2, objArr);
        if (i != 1 && i != 9 && i != 16 && i != 38 && i != 79 && i != 88 && i != 98 && i != 5 && i != 6 && i != 35 && i != 36 && i != 65 && i != 66 && i != 100 && i != 101) {
            switch (i) {
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                    break;
                default:
                    switch (i) {
                        case 91:
                        case 92:
                        case 93:
                            break;
                        default:
                            throw new IllegalArgumentException(str3);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str3);
    }

    private PsiImplUtil() {
    }

    public static PsiMethod[] getConstructors(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        SmartList smartList = null;
        for (PsiMethod psiMethod : psiClass.getMethods()) {
            if (psiMethod.isConstructor() && psiMethod.get_name().equals(psiClass.get_name())) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(psiMethod);
            }
        }
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (smartList != null) {
            psiMethodArr = (PsiMethod[]) smartList.toArray(psiMethodArr);
        }
        if (psiMethodArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiMethodArr;
    }

    public static PsiAnnotationMemberValue findDeclaredAttributeValue(PsiAnnotation psiAnnotation, String str) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(2);
        }
        PsiNameValuePair psiNameValuePairFindDeclaredAttribute = AnnotationUtil.findDeclaredAttribute(psiAnnotation, str);
        if (psiNameValuePairFindDeclaredAttribute == null) {
            return null;
        }
        return psiNameValuePairFindDeclaredAttribute.getValue();
    }

    public static PsiAnnotationMemberValue findAttributeValue(PsiAnnotation psiAnnotation, String str) {
        PsiElement psiElementResolve;
        if (psiAnnotation == null) {
            $$$reportNull$$$0(3);
        }
        PsiAnnotationMemberValue psiAnnotationMemberValueFindDeclaredAttributeValue = findDeclaredAttributeValue(psiAnnotation, str);
        if (psiAnnotationMemberValueFindDeclaredAttributeValue != null) {
            return psiAnnotationMemberValueFindDeclaredAttributeValue;
        }
        if (str == null) {
            str = "value";
        }
        PsiJavaCodeReferenceElement nameReferenceElement = psiAnnotation.getNameReferenceElement();
        if (nameReferenceElement == null || (psiElementResolve = nameReferenceElement.resolve()) == null) {
            return null;
        }
        for (PsiMethod psiMethod : ((PsiClass) psiElementResolve).findMethodsByName(str, false)) {
            if (PsiUtil.isAnnotationMethod(psiMethod)) {
                return ((PsiAnnotationMethod) psiMethod).getDefaultValue();
            }
        }
        return null;
    }

    public static PsiTypeParameter[] getTypeParameters(PsiTypeParameterListOwner psiTypeParameterListOwner) {
        if (psiTypeParameterListOwner == null) {
            $$$reportNull$$$0(4);
        }
        PsiTypeParameterList psiTypeParameterListMo6093getTypeParameterList = psiTypeParameterListOwner.mo6093getTypeParameterList();
        if (psiTypeParameterListMo6093getTypeParameterList != null) {
            PsiTypeParameter[] typeParameters = psiTypeParameterListMo6093getTypeParameterList.getTypeParameters();
            if (typeParameters == null) {
                $$$reportNull$$$0(5);
            }
            return typeParameters;
        }
        PsiTypeParameter[] psiTypeParameterArr = PsiTypeParameter.EMPTY_ARRAY;
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiTypeParameterArr;
    }

    public static PsiJavaCodeReferenceElement[] namesToPackageReferences(PsiManager psiManager, String[] strArr) {
        if (psiManager == null) {
            $$$reportNull$$$0(7);
        }
        if (strArr == null) {
            $$$reportNull$$$0(8);
        }
        PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr = new PsiJavaCodeReferenceElement[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                psiJavaCodeReferenceElementArr[i] = JavaPsiFacade.getElementFactory(psiManager.getProject()).createPackageReferenceElement(strArr[i]);
            } catch (IncorrectOperationException e) {
                LOG.error((Throwable) e);
            }
        }
        return psiJavaCodeReferenceElementArr;
    }

    public static int getParameterIndex(PsiParameter psiParameter, PsiParameterList psiParameterList) {
        PsiParameter psiParameter2;
        if (psiParameter == null) {
            $$$reportNull$$$0(10);
        }
        if (psiParameterList == null) {
            $$$reportNull$$$0(11);
        }
        psiParameter.getLightOwner();
        PsiParameter[] parameters = psiParameterList.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (psiParameter.equals(parameters[i])) {
                return i;
            }
        }
        String name2 = psiParameter.get_name();
        int length = parameters.length - 1;
        while (true) {
            if (length < 0) {
                psiParameter2 = null;
                break;
            }
            psiParameter2 = parameters[length];
            if (Objects.equals(name2, psiParameter2.get_name())) {
                break;
            }
            length--;
        }
        LOG.error(new StringBuilder().append(psiParameter).append(":").append(psiParameter.getClass()).append(" not found among parameters: ").append(Arrays.asList(parameters)).append(". parameterList' parent: ").append(psiParameterList.getLightOwner()).append("; parameter.isValid()=").append(psiParameter.isValid()).append("; parameterList.isValid()= ").append(psiParameterList.isValid()).append("; parameterList stub: ").append(psiParameterList instanceof StubBasedPsiElement ? ((StubBasedPsiElement) psiParameterList).getStub() : "---").append(";  parameter stub: ").append(psiParameter instanceof StubBasedPsiElement ? ((StubBasedPsiElement) psiParameter).getStub() : "---").append("; suspect: ").append(psiParameter2).append(" (index=").append(length).append("); ").append(psiParameter2 == null ? null : psiParameter2.getClass()).append(" suspect stub: ").append(psiParameter2 instanceof StubBasedPsiElement ? ((StubBasedPsiElement) psiParameter2).getStub() : psiParameter2 == null ? "-null-" : "---" + psiParameter2.getClass()).append("; parameter.equals(suspect) = ").append(psiParameter.equals(psiParameter2)).append(";  parameter.getNode() == suspect.getNode():  ").append(psiParameter.getNode() == (psiParameter2 != null ? psiParameter2.getNode() : null)).append("; .").toString());
        return length;
    }

    public static int getTypeParameterIndex(PsiTypeParameter psiTypeParameter, PsiTypeParameterList psiTypeParameterList) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(12);
        }
        if (psiTypeParameterList == null) {
            $$$reportNull$$$0(13);
        }
        PsiTypeParameter[] typeParameters = psiTypeParameterList.getTypeParameters();
        for (int i = 0; i < typeParameters.length; i++) {
            if (psiTypeParameter.equals(typeParameters[i])) {
                return i;
            }
        }
        LOG.error(psiTypeParameter + " in " + psiTypeParameterList);
        return -1;
    }

    public static Object[] getReferenceVariantsByFilter(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, ElementFilter elementFilter) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(14);
        }
        if (elementFilter == null) {
            $$$reportNull$$$0(15);
        }
        FilterScopeProcessor filterScopeProcessor = new FilterScopeProcessor(elementFilter);
        PsiScopesUtil.resolveAndWalk(filterScopeProcessor, psiJavaCodeReferenceElement, null, true);
        Object[] array = filterScopeProcessor.getResults().toArray();
        if (array == null) {
            $$$reportNull$$$0(16);
        }
        return array;
    }

    public static boolean processDeclarationsInMethod(PsiMethod psiMethod, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(17);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(18);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(19);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(20);
        }
        if (psiElement instanceof DummyHolder) {
            psiElement = psiElement.getFirstChild();
        }
        return processDeclarationsInMethodLike(psiMethod, psiScopeProcessor, resolveState, psiElement2, psiElement instanceof PsiCodeBlock, psiMethod.mo6093getTypeParameterList());
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0038  */
    public static boolean processDeclarationsInLambda(PsiLambdaExpression psiLambdaExpression, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiLambdaExpression == null) {
            $$$reportNull$$$0(21);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(22);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(23);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(24);
        }
        boolean z = true;
        if (psiElement instanceof DummyHolder) {
            PsiElement firstChild = psiElement.getFirstChild();
            if (!(firstChild instanceof PsiExpression) && !(firstChild instanceof PsiCodeBlock)) {
                z = false;
            }
        } else if (psiElement == null || psiElement != psiLambdaExpression.getBody()) {
            z = false;
        }
        return processDeclarationsInMethodLike(psiLambdaExpression, psiScopeProcessor, resolveState, psiElement2, z, null);
    }

    private static boolean processDeclarationsInMethodLike(PsiParameterListOwner psiParameterListOwner, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, boolean z, PsiTypeParameterList psiTypeParameterList) {
        ElementClassHint elementClassHint;
        if (psiParameterListOwner == null) {
            $$$reportNull$$$0(25);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(26);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(27);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, psiParameterListOwner);
        if (psiTypeParameterList != null && (((elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY)) == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) && !psiTypeParameterList.processDeclarations(psiScopeProcessor, resolveState, null, psiElement))) {
            return false;
        }
        if (!z) {
            return true;
        }
        for (PsiParameter psiParameter : psiParameterListOwner.getParameterList().getParameters()) {
            if (!psiScopeProcessor.execute(psiParameter, resolveState)) {
                return false;
            }
        }
        return true;
    }

    public static boolean processDeclarationsInResourceList(PsiResourceList psiResourceList, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement) {
        PsiResourceListElement next;
        if (psiResourceList == null) {
            $$$reportNull$$$0(29);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(30);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(31);
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) {
            return true;
        }
        Iterator<PsiResourceListElement> it = psiResourceList.iterator();
        while (it.hasNext() && (next = it.next()) != psiElement) {
            if ((next instanceof PsiResourceVariable) && !psiScopeProcessor.execute(next, resolveState)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasTypeParameters(PsiTypeParameterListOwner psiTypeParameterListOwner) {
        if (psiTypeParameterListOwner == null) {
            $$$reportNull$$$0(32);
        }
        PsiTypeParameterList psiTypeParameterListMo6093getTypeParameterList = psiTypeParameterListOwner.mo6093getTypeParameterList();
        return (psiTypeParameterListMo6093getTypeParameterList == null || psiTypeParameterListMo6093getTypeParameterList.getTypeParameters().length == 0) ? false : true;
    }

    public static PsiType[] typesByReferenceParameterList(PsiReferenceParameterList psiReferenceParameterList) {
        if (psiReferenceParameterList == null) {
            $$$reportNull$$$0(33);
        }
        return typesByTypeElements(psiReferenceParameterList.getTypeParameterElements());
    }

    public static PsiType[] typesByTypeElements(PsiTypeElement[] psiTypeElementArr) {
        if (psiTypeElementArr == null) {
            $$$reportNull$$$0(34);
        }
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(psiTypeElementArr.length);
        for (int i = 0; i < psiTypeArrCreateArray.length; i++) {
            psiTypeArrCreateArray[i] = psiTypeElementArr[i].getType();
        }
        if (psiTypeArrCreateArray.length == 1) {
            PsiType psiType = psiTypeArrCreateArray[0];
            if (psiType instanceof PsiDiamondType) {
                PsiType[] types = ((PsiDiamondType) psiType).resolveInferredTypes().getTypes();
                if (types == null) {
                    $$$reportNull$$$0(35);
                }
                return types;
            }
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(36);
        }
        return psiTypeArrCreateArray;
    }

    public static PsiType getType(PsiClassObjectAccessExpression psiClassObjectAccessExpression) {
        if (psiClassObjectAccessExpression == null) {
            $$$reportNull$$$0(37);
        }
        GlobalSearchScope resolveScope = psiClassObjectAccessExpression.getResolveScope();
        PsiManager manager = psiClassObjectAccessExpression.getManager();
        PsiClass psiClassFindClass = JavaPsiFacade.getInstance(manager.getProject()).findClass(CommonClassNames.JAVA_LANG_CLASS, resolveScope);
        if (psiClassFindClass == null) {
            return new PsiClassReferenceType(new LightClassReference(manager, "Class", CommonClassNames.JAVA_LANG_CLASS, resolveScope), null);
        }
        if (!PsiUtil.isLanguageLevel5OrHigher(psiClassObjectAccessExpression)) {
            PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(manager.getProject()).createType(psiClassFindClass);
            if (psiClassTypeCreateType == null) {
                $$$reportNull$$$0(38);
            }
            return psiClassTypeCreateType;
        }
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        PsiType type = psiClassObjectAccessExpression.getOperand().getType();
        if ((type instanceof PsiPrimitiveType) && !PsiType.NULL.equals(type)) {
            type = PsiType.VOID.equals(type) ? JavaPsiFacade.getElementFactory(manager.getProject()).createTypeByFQClassName(CommonClassNames.JAVA_LANG_VOID, psiClassObjectAccessExpression.getResolveScope()) : ((PsiPrimitiveType) type).getBoxedType(psiClassObjectAccessExpression);
        }
        PsiTypeParameter[] typeParameters = psiClassFindClass.getTypeParameters();
        if (typeParameters.length == 1) {
            PsiTypeParameter psiTypeParameter = typeParameters[0];
            if (type instanceof PsiClassType) {
                type = ((PsiClassType) type).rawType();
            }
            psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, type);
        }
        return new PsiImmediateClassType(psiClassFindClass, psiSubstitutorPut);
    }

    public static PsiAnnotation findAnnotation(PsiAnnotationOwner psiAnnotationOwner, String str) {
        if (str == null) {
            $$$reportNull$$$0(39);
        }
        if (psiAnnotationOwner == null) {
            return null;
        }
        PsiAnnotation[] annotations = psiAnnotationOwner.getAnnotations();
        if (annotations.length == 0) {
            return null;
        }
        String shortName = StringUtil.getShortName(str);
        for (PsiAnnotation psiAnnotation : annotations) {
            PsiJavaCodeReferenceElement nameReferenceElement = psiAnnotation.getNameReferenceElement();
            if (nameReferenceElement != null && shortName.equals(nameReferenceElement.getReferenceName()) && str.equals(psiAnnotation.getQualifiedName())) {
                return psiAnnotation;
            }
        }
        return null;
    }

    public static ASTNode findDocComment(CompositeElement compositeElement) {
        if (compositeElement == null) {
            $$$reportNull$$$0(40);
        }
        TreeElement firstChildNode = compositeElement.getFirstChildNode();
        while (firstChildNode != null && isWhitespaceOrComment(firstChildNode) && !(firstChildNode.getPsi() instanceof PsiDocComment)) {
            firstChildNode = firstChildNode.getTreeNext();
        }
        if (firstChildNode == null || firstChildNode.getElementType() != JavaDocElementType.DOC_COMMENT) {
            return null;
        }
        return firstChildNode;
    }

    @Deprecated
    public static PsiType normalizeWildcardTypeByPosition(PsiType psiType, PsiExpression psiExpression) {
        if (psiType == null) {
            $$$reportNull$$$0(41);
        }
        if (psiExpression == null) {
            $$$reportNull$$$0(42);
        }
        PsiUtil.ensureValidType(psiType);
        PsiExpression psiExpression2 = psiExpression;
        while ((psiExpression2.getLightOwner() instanceof PsiArrayAccessExpression) && ((PsiArrayAccessExpression) psiExpression2.getLightOwner()).getArrayExpression() == psiExpression2) {
            psiExpression2 = (PsiExpression) psiExpression2.getLightOwner();
        }
        if ((psiExpression2 instanceof PsiArrayAccessExpression) && !PsiUtil.isAccessedForWriting(psiExpression2)) {
            return PsiUtil.captureToplevelWildcards(psiType, psiExpression);
        }
        PsiType psiTypeDoNormalizeWildcardByPosition = doNormalizeWildcardByPosition(psiType, psiExpression, psiExpression2);
        LOG.assertTrue(psiTypeDoNormalizeWildcardByPosition.isValid(), psiType);
        return (!(psiTypeDoNormalizeWildcardByPosition instanceof PsiClassType) || PsiUtil.isAccessedForWriting(psiExpression2)) ? psiTypeDoNormalizeWildcardByPosition : PsiUtil.captureToplevelWildcards(psiTypeDoNormalizeWildcardByPosition, psiExpression);
    }

    private static PsiType doNormalizeWildcardByPosition(PsiType psiType, PsiExpression psiExpression, PsiExpression psiExpression2) {
        PsiType componentType;
        PsiType psiTypeDoNormalizeWildcardByPosition;
        if (psiExpression == null) {
            $$$reportNull$$$0(43);
        }
        if (psiExpression2 == null) {
            $$$reportNull$$$0(44);
        }
        if (!(psiType instanceof PsiWildcardType)) {
            return (!(psiType instanceof PsiArrayType) || (psiTypeDoNormalizeWildcardByPosition = doNormalizeWildcardByPosition((componentType = ((PsiArrayType) psiType).getComponentType()), psiExpression, psiExpression2)) == componentType) ? psiType : psiTypeDoNormalizeWildcardByPosition.createArrayType();
        }
        PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
        if (PsiUtil.isAccessedForWriting(psiExpression2)) {
            return psiWildcardType.isSuper() ? psiWildcardType.getBound() : PsiCapturedWildcardType.create(psiWildcardType, psiExpression);
        }
        if (psiWildcardType.isExtends()) {
            return psiWildcardType.getBound();
        }
        return PsiType.getJavaLangObject(psiExpression.getManager(), psiExpression.getResolveScope());
    }

    public static SearchScope getMemberUseScope(PsiMember psiMember) {
        PsiClass psiClassMo6092getContainingClass;
        PsiPackage psiPackageFindPackage;
        if (psiMember == null) {
            $$$reportNull$$$0(45);
        }
        PsiFile containingFile = psiMember.getContainingFile();
        PsiMember psiMember2 = containingFile == null ? psiMember : containingFile;
        Project project = psiMember2.getProject();
        GlobalSearchScope useScope = ResolveScopeManager.getInstance(project).getUseScope(psiMember2);
        if (isInServerPage(containingFile)) {
            if (useScope == null) {
                $$$reportNull$$$0(46);
            }
            return useScope;
        }
        PsiElement psiElementMo6092getContainingClass = psiMember.getContainingClass();
        if ((psiElementMo6092getContainingClass instanceof PsiAnonymousClass) && (!(psiElementMo6092getContainingClass instanceof PsiEnumConstantInitializer) || !(psiMember instanceof PsiMethod) || !psiMember.hasModifierProperty("public") || ((PsiMethod) psiMember).findSuperMethods().length <= 0)) {
            PsiElement topmostParentOfType = PsiUtil.isLanguageLevel8OrHigher(psiElementMo6092getContainingClass) ? PsiTreeUtil.getTopmostParentOfType(psiElementMo6092getContainingClass, PsiStatement.class) : PsiTreeUtil.getParentOfType(psiElementMo6092getContainingClass, PsiMethodCallExpression.class);
            if (topmostParentOfType instanceof PsiDeclarationStatement) {
                PsiElement[] declaredElements = ((PsiDeclarationStatement) topmostParentOfType).getDeclaredElements();
                if (declaredElements.length == 1) {
                    PsiElement psiElement = declaredElements[0];
                    if ((psiElement instanceof PsiLocalVariable) && ((PsiLocalVariable) psiElement).getTypeElement().isInferredType()) {
                        topmostParentOfType = topmostParentOfType.getLightOwner();
                    }
                }
            }
            if (topmostParentOfType != null) {
                psiElementMo6092getContainingClass = topmostParentOfType;
            }
            return new LocalSearchScope(psiElementMo6092getContainingClass);
        }
        PsiModifierList modifierList = psiMember.mo6095getModifierList();
        int accessLevel = modifierList == null ? 4 : PsiUtil.getAccessLevel(modifierList);
        if (accessLevel == 4 || accessLevel == 3) {
            if (!(psiMember instanceof PsiMethod) || !((PsiMethod) psiMember).isConstructor() || (psiClassMo6092getContainingClass = psiMember.getContainingClass()) == null) {
                if (useScope == null) {
                    $$$reportNull$$$0(48);
                }
                return useScope;
            }
            SearchScope useScope2 = psiClassMo6092getContainingClass.getUseScope();
            if (useScope2 == null) {
                $$$reportNull$$$0(47);
            }
            return useScope2;
        }
        if (accessLevel == 1) {
            PsiClass topLevelClass = PsiUtil.getTopLevelClass(psiMember);
            if (topLevelClass != null) {
                return new LocalSearchScope(topLevelClass);
            }
            return containingFile == null ? useScope : new LocalSearchScope(containingFile);
        }
        if (!(containingFile instanceof PsiJavaFile) || (psiPackageFindPackage = JavaPsiFacade.getInstance(project).findPackage(((PsiJavaFile) containingFile).getPackageName())) == null) {
            if (useScope == null) {
                $$$reportNull$$$0(50);
            }
            return useScope;
        }
        SearchScope searchScopeIntersectWith = PackageScope.packageScope(psiPackageFindPackage, false).intersectWith((SearchScope) useScope);
        if (searchScopeIntersectWith == null) {
            $$$reportNull$$$0(49);
        }
        return searchScopeIntersectWith;
    }

    public static boolean isInServerPage(PsiElement psiElement) {
        return getServerPageFile(psiElement) != null;
    }

    private static ServerPageFile getServerPageFile(PsiElement psiElement) {
        PsiFile templateLanguageFile = PsiUtilCore.getTemplateLanguageFile(psiElement);
        if (templateLanguageFile instanceof ServerPageFile) {
            return (ServerPageFile) templateLanguageFile;
        }
        return null;
    }

    public static PsiElement setName(PsiElement psiElement, String str) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(51);
        }
        if (str == null) {
            $$$reportNull$$$0(52);
        }
        return psiElement.replace(JavaPsiFacade.getElementFactory(psiElement.getManager().getProject()).createIdentifier(str));
    }

    public static boolean isDeprecatedByAnnotation(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(53);
        }
        return AnnotationUtil.findAnnotation(psiModifierListOwner, CommonClassNames.JAVA_LANG_DEPRECATED) != null;
    }

    public static boolean isDeprecatedByDocTag(PsiJavaDocumentedElement psiJavaDocumentedElement) {
        if (psiJavaDocumentedElement == null) {
            $$$reportNull$$$0(54);
        }
        PsiDocComment psiDocCommentMo6091getDocComment = psiJavaDocumentedElement.mo6091getDocComment();
        return (psiDocCommentMo6091getDocComment == null || psiDocCommentMo6091getDocComment.findTagByName("deprecated") == null) ? false : true;
    }

    public static boolean isDeprecated(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(55);
        }
        if (psiElement instanceof PsiDocCommentOwner) {
            return ((PsiDocCommentOwner) psiElement).isDeprecated();
        }
        if ((psiElement instanceof PsiModifierListOwner) && isDeprecatedByAnnotation((PsiModifierListOwner) psiElement)) {
            return true;
        }
        if (psiElement instanceof PsiJavaDocumentedElement) {
            return isDeprecatedByDocTag((PsiJavaDocumentedElement) psiElement);
        }
        return false;
    }

    public static PsiJavaDocumentedElement findDocCommentOwner(PsiDocComment psiDocComment) {
        if (psiDocComment == null) {
            $$$reportNull$$$0(56);
        }
        PsiElement parent = psiDocComment.getLightOwner();
        if (!(parent instanceof PsiJavaDocumentedElement)) {
            return null;
        }
        PsiJavaDocumentedElement psiJavaDocumentedElement = (PsiJavaDocumentedElement) parent;
        if (psiJavaDocumentedElement.mo6091getDocComment() == psiDocComment) {
            return psiJavaDocumentedElement;
        }
        return null;
    }

    public static PsiAnnotationMemberValue setDeclaredAttributeValue(PsiAnnotation psiAnnotation, String str, PsiAnnotationMemberValue psiAnnotationMemberValue, PairFunction<? super Project, ? super String, ? extends PsiAnnotation> pairFunction) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(57);
        }
        if (pairFunction == null) {
            $$$reportNull$$$0(58);
        }
        PsiAnnotationMemberValue psiAnnotationMemberValueFindDeclaredAttributeValue = psiAnnotation.findDeclaredAttributeValue(str);
        if (psiAnnotationMemberValue == null) {
            if (psiAnnotationMemberValueFindDeclaredAttributeValue == null) {
                return null;
            }
            psiAnnotationMemberValueFindDeclaredAttributeValue.getLightOwner().delete();
        } else if (psiAnnotationMemberValueFindDeclaredAttributeValue != null) {
            ((PsiNameValuePair) psiAnnotationMemberValueFindDeclaredAttributeValue.getLightOwner()).mo6100setValue(psiAnnotationMemberValue);
        } else {
            PsiNameValuePair[] attributes = psiAnnotation.getParameterList().getAttributes();
            if (attributes.length == 1) {
                PsiNameValuePair psiNameValuePair = attributes[0];
                if (psiNameValuePair.getName() == null) {
                    psiNameValuePair.replace(createNameValuePair(psiNameValuePair.getValue(), "value=", pairFunction));
                }
            }
            psiAnnotation.getParameterList().addBefore(createNameValuePair(psiAnnotationMemberValue, (attributes.length == 0 && ("value".equals(str) || str == null)) ? "" : str + ContainerUtils.KEY_VALUE_DELIMITER, pairFunction), null);
        }
        return psiAnnotation.findDeclaredAttributeValue(str);
    }

    private static PsiNameValuePair createNameValuePair(PsiAnnotationMemberValue psiAnnotationMemberValue, String str, PairFunction<? super Project, ? super String, ? extends PsiAnnotation> pairFunction) {
        if (psiAnnotationMemberValue == null) {
            $$$reportNull$$$0(59);
        }
        if (str == null) {
            $$$reportNull$$$0(60);
        }
        if (pairFunction == null) {
            $$$reportNull$$$0(61);
        }
        return pairFunction.fun(psiAnnotationMemberValue.getProject(), "@A(" + str + psiAnnotationMemberValue.getText() + Constant.AFTER_QUTO).getParameterList().getAttributes()[0];
    }

    public static ASTNode skipWhitespaceAndComments(ASTNode aSTNode) {
        return TreeUtil.skipWhitespaceAndComments(aSTNode, true);
    }

    public static ASTNode skipWhitespaceCommentsAndTokens(ASTNode aSTNode, TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(62);
        }
        return TreeUtil.skipWhitespaceCommentsAndTokens(aSTNode, tokenSet, true);
    }

    public static boolean isWhitespaceOrComment(ASTNode aSTNode) {
        return TreeUtil.isWhitespaceOrComment(aSTNode);
    }

    public static ASTNode skipWhitespaceAndCommentsBack(ASTNode aSTNode) {
        ASTNode aSTNode2 = null;
        if (aSTNode == null) {
            return null;
        }
        if (!isWhitespaceOrComment(aSTNode)) {
            return aSTNode;
        }
        ASTNode treeParent = aSTNode.getTreeParent();
        while (aSTNode instanceof CompositeElement) {
            if (!isWhitespaceOrComment(aSTNode)) {
                return aSTNode;
            }
            aSTNode = aSTNode.getTreePrev();
        }
        if (aSTNode == null) {
            return null;
        }
        for (ASTNode firstChildNode = treeParent.getFirstChildNode(); firstChildNode != aSTNode; firstChildNode = firstChildNode.getTreeNext()) {
            if (!isWhitespaceOrComment(firstChildNode)) {
                aSTNode2 = firstChildNode;
            }
        }
        return aSTNode2;
    }

    public static ASTNode findStatementChild(CompositePsiElement compositePsiElement) {
        if (compositePsiElement == null) {
            $$$reportNull$$$0(63);
        }
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED) {
            ApplicationManager.getApplication().assertReadAccessAllowed();
        }
        for (ASTNode firstChildNode = compositePsiElement.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getPsi() instanceof PsiStatement) {
                return firstChildNode;
            }
        }
        return null;
    }

    public static PsiStatement[] getChildStatements(CompositeElement compositeElement) {
        if (compositeElement == null) {
            $$$reportNull$$$0(64);
        }
        ApplicationManager.getApplication().assertReadAccessAllowed();
        int i = 0;
        int i2 = 0;
        for (ASTNode firstChildNode = compositeElement.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getPsi() instanceof PsiStatement) {
                i2++;
            }
        }
        PsiStatement[] psiStatementArrCreate = PsiStatement.ARRAY_FACTORY.create(i2);
        if (i2 == 0) {
            if (psiStatementArrCreate == null) {
                $$$reportNull$$$0(65);
            }
            return psiStatementArrCreate;
        }
        for (ASTNode firstChildNode2 = compositeElement.getFirstChildNode(); firstChildNode2 != null && i < i2; firstChildNode2 = firstChildNode2.getTreeNext()) {
            PsiElement psi = firstChildNode2.getPsi();
            if (psi instanceof PsiStatement) {
                psiStatementArrCreate[i] = (PsiStatement) psi;
                i++;
            }
        }
        if (psiStatementArrCreate == null) {
            $$$reportNull$$$0(66);
        }
        return psiStatementArrCreate;
    }

    public static boolean isVarArgs(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(67);
        }
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        return parameters.length > 0 && parameters[parameters.length - 1].isVarArgs();
    }

    public static PsiElement handleMirror(PsiElement psiElement) {
        return psiElement instanceof PsiMirrorElement ? ((PsiMirrorElement) psiElement).getPrototype() : psiElement;
    }

    public static PsiModifierList findNeighbourModifierList(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(68);
        }
        PsiElement psiElementSkipParentsOfType = PsiTreeUtil.skipParentsOfType(psiJavaCodeReferenceElement, PsiJavaCodeReferenceElement.class);
        if (!(psiElementSkipParentsOfType instanceof PsiTypeElement)) {
            return null;
        }
        PsiElement parent = psiElementSkipParentsOfType.getLightOwner();
        if (parent instanceof PsiModifierListOwner) {
            return ((PsiModifierListOwner) parent).mo6095getModifierList();
        }
        return null;
    }

    public static boolean isTypeAnnotation(PsiElement psiElement) {
        return (psiElement instanceof PsiAnnotation) && AnnotationTargetUtil.isTypeAnnotation((PsiAnnotation) psiElement);
    }

    public static void collectTypeUseAnnotations(PsiModifierList psiModifierList, List<? super PsiAnnotation> list) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(69);
        }
        if (list == null) {
            $$$reportNull$$$0(70);
        }
        AnnotationTargetUtil.collectStrictlyTypeUseAnnotations(psiModifierList, list);
    }

    public static void markTypeAnnotations(PsiTypeElement psiTypeElement) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(71);
        }
        PsiElement psiElementSkipSiblingsBackward = PsiTreeUtil.skipSiblingsBackward(psiTypeElement, PsiComment.class, PsiWhiteSpace.class, PsiTypeParameterList.class);
        if (psiElementSkipSiblingsBackward instanceof PsiModifierList) {
            for (PsiAnnotation psiAnnotation : ((PsiModifierList) psiElementSkipSiblingsBackward).getAnnotations()) {
                if (AnnotationTargetUtil.isTypeAnnotation(psiAnnotation)) {
                    psiAnnotation.putUserData(TYPE_ANNO_MARK, Boolean.TRUE);
                }
            }
        }
    }

    public static void deleteTypeAnnotations(PsiTypeElement psiTypeElement) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(72);
        }
        PsiElement psiElementSkipSiblingsBackward = PsiTreeUtil.skipSiblingsBackward(psiTypeElement, PsiComment.class, PsiWhiteSpace.class, PsiTypeParameterList.class);
        if (psiElementSkipSiblingsBackward instanceof PsiModifierList) {
            for (PsiAnnotation psiAnnotation : ((PsiModifierList) psiElementSkipSiblingsBackward).getAnnotations()) {
                if (TYPE_ANNO_MARK.get(psiAnnotation) == Boolean.TRUE) {
                    psiAnnotation.delete();
                }
            }
        }
    }

    public static PsiLoopStatement findEnclosingLoop(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(73);
        }
        while (!isCodeBoundary(psiElement)) {
            if (psiElement instanceof PsiLoopStatement) {
                return (PsiLoopStatement) psiElement;
            }
            psiElement = psiElement.getLightOwner();
        }
        return null;
    }

    public static PsiStatement findEnclosingSwitchOrLoop(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(74);
        }
        while (!isCodeBoundary(psiElement)) {
            if (!(psiElement instanceof PsiSwitchStatement) && !(psiElement instanceof PsiLoopStatement)) {
                psiElement = psiElement.getLightOwner();
            } else {
                return (PsiStatement) psiElement;
            }
        }
        return null;
    }

    public static PsiSwitchExpression findEnclosingSwitchExpression(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(75);
        }
        while (!isCodeBoundary(psiElement)) {
            if (psiElement instanceof PsiSwitchExpression) {
                return (PsiSwitchExpression) psiElement;
            }
            psiElement = psiElement.getLightOwner();
        }
        return null;
    }

    public static PsiLabeledStatement findEnclosingLabeledStatement(PsiElement psiElement, String str) {
        if (psiElement == null) {
            $$$reportNull$$$0(76);
        }
        if (str == null) {
            $$$reportNull$$$0(77);
        }
        while (!isCodeBoundary(psiElement)) {
            if (psiElement instanceof PsiLabeledStatement) {
                PsiLabeledStatement psiLabeledStatement = (PsiLabeledStatement) psiElement;
                if (str.equals(psiLabeledStatement.get_name())) {
                    return psiLabeledStatement;
                }
            }
            psiElement = psiElement.getLightOwner();
        }
        return null;
    }

    public static List<String> findAllEnclosingLabels(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(78);
        }
        SmartList smartList = new SmartList();
        while (!isCodeBoundary(psiElement)) {
            if (psiElement instanceof PsiLabeledStatement) {
                smartList.add(((PsiLabeledStatement) psiElement).get_name());
            }
            psiElement = psiElement.getContext();
        }
        return smartList;
    }

    private static boolean isCodeBoundary(PsiElement psiElement) {
        return psiElement == null || (psiElement instanceof PsiMethod) || (psiElement instanceof PsiClassInitializer) || (psiElement instanceof PsiLambdaExpression);
    }

    public static PsiSwitchLabelStatementBase getSwitchLabel(PsiCaseLabelElement psiCaseLabelElement) {
        PsiElement psiElementSkipParenthesizedExprUp;
        if (psiCaseLabelElement == null) {
            $$$reportNull$$$0(80);
        }
        if (psiCaseLabelElement instanceof PsiParenthesizedPattern) {
            psiElementSkipParenthesizedExprUp = JavaPsiPatternUtil.skipParenthesizedPatternUp(psiCaseLabelElement.getLightOwner());
        } else {
            psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiCaseLabelElement.getLightOwner());
        }
        if (!(psiElementSkipParenthesizedExprUp instanceof PsiCaseLabelElementList)) {
            return null;
        }
        PsiElement parent = psiElementSkipParenthesizedExprUp.getLightOwner();
        if (parent instanceof PsiSwitchLabelStatementBase) {
            return (PsiSwitchLabelStatementBase) parent;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isLeafElementOfType(PsiElement psiElement, IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(81);
        }
        return (psiElement instanceof LeafElement) && ((LeafElement) psiElement).getElementType() == iElementType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isLeafElementOfType(PsiElement psiElement, TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(82);
        }
        return (psiElement instanceof LeafElement) && tokenSet.contains(((LeafElement) psiElement).getElementType());
    }

    public static PsiType buildTypeFromTypeString(String str, PsiElement psiElement, PsiFile psiFile) {
        if (str == null) {
            $$$reportNull$$$0(83);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(84);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(85);
        }
        PsiManager manager = psiFile.getManager();
        if (str.indexOf(60) != -1 || str.indexOf(91) != -1 || str.indexOf(46) == -1) {
            try {
                return JavaPsiFacade.getElementFactory(manager.getProject()).createTypeFromText(str, psiElement);
            } catch (Exception unused) {
            }
        }
        PsiClass psiClassFindClass = JavaPsiFacade.getInstance(manager.getProject()).findClass(str, psiElement.getResolveScope());
        if (psiClassFindClass == null) {
            return new PsiClassReferenceType(new LightClassReference(manager, PsiNameHelper.getShortClassName(str), str, PsiSubstitutor.EMPTY, psiFile), null);
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(manager.getProject());
        return elementFactory.createType(psiClassFindClass, elementFactory.createRawSubstitutor(psiClassFindClass));
    }

    public static <T extends PsiJavaCodeReferenceElement> JavaResolveResult[] multiResolveImpl(T t, boolean z, ResolveCache.PolyVariantContextResolver<? super T> polyVariantContextResolver) {
        if (t == null) {
            $$$reportNull$$$0(86);
        }
        if (polyVariantContextResolver == null) {
            $$$reportNull$$$0(87);
        }
        FileASTNode fileASTNodeFindFileElement = SharedImplUtil.findFileElement(t.getNode());
        if (fileASTNodeFindFileElement == null) {
            PsiUtilCore.ensureValid(t);
            LOG.error("fileElement == null!");
            JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(88);
            }
            return javaResolveResultArr;
        }
        return multiResolveImpl(t, SharedImplUtil.getContainingFile(fileASTNodeFindFileElement), z, polyVariantContextResolver);
    }

    public static <T extends PsiJavaCodeReferenceElement> JavaResolveResult[] multiResolveImpl(T t, PsiFile psiFile, boolean z, ResolveCache.PolyVariantContextResolver<? super T> polyVariantContextResolver) {
        if (t == null) {
            $$$reportNull$$$0(89);
        }
        if (polyVariantContextResolver == null) {
            $$$reportNull$$$0(90);
        }
        PsiManager manager = psiFile == null ? null : psiFile.getManager();
        if (manager == null) {
            PsiUtilCore.ensureValid(t);
            LOG.error("getManager() == null!");
            JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(91);
            }
            return javaResolveResultArr;
        }
        if (!psiFile.isValid()) {
            PsiUtilCore.ensureValid(t);
            LOG.error("psiFile.isValid() == false!");
            JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
            if (javaResolveResultArr2 == null) {
                $$$reportNull$$$0(92);
            }
            return javaResolveResultArr2;
        }
        if ((t instanceof PsiMethodReferenceExpression) && ThreadLocalTypes.hasBindingFor(t)) {
            JavaResolveResult[] javaResolveResultArr3 = (JavaResolveResult[]) polyVariantContextResolver.resolve(t, psiFile, z);
            if (javaResolveResultArr3 == null) {
                $$$reportNull$$$0(93);
            }
            return javaResolveResultArr3;
        }
        return multiResolveImpl(manager.getProject(), psiFile, t, z, polyVariantContextResolver);
    }

    public static <T extends PsiJavaCodeReferenceElement> JavaResolveResult[] multiResolveImpl(Project project, PsiFile psiFile, T t, boolean z, ResolveCache.PolyVariantContextResolver<? super T> polyVariantContextResolver) {
        if (project == null) {
            $$$reportNull$$$0(94);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(95);
        }
        if (t == null) {
            $$$reportNull$$$0(96);
        }
        if (polyVariantContextResolver == null) {
            $$$reportNull$$$0(97);
        }
        ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(project).resolveWithCaching(t, (ResolveCache.PolyVariantContextResolver<T>) polyVariantContextResolver, true, z, psiFile);
        JavaResolveResult[] javaResolveResultArr = resolveResultArrResolveWithCaching.length == 0 ? JavaResolveResult.EMPTY_ARRAY : (JavaResolveResult[]) resolveResultArrResolveWithCaching;
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(98);
        }
        return javaResolveResultArr;
    }

    public static VirtualFile getModuleVirtualFile(PsiJavaModule psiJavaModule) {
        if (psiJavaModule == null) {
            $$$reportNull$$$0(99);
        }
        if (psiJavaModule instanceof LightJavaModule) {
            VirtualFile rootVirtualFile = ((LightJavaModule) psiJavaModule).getRootVirtualFile();
            if (rootVirtualFile == null) {
                $$$reportNull$$$0(100);
            }
            return rootVirtualFile;
        }
        VirtualFile virtualFile = PsiUtilCore.getVirtualFile(psiJavaModule);
        if (virtualFile == null) {
            throw new IllegalArgumentException("Module '" + psiJavaModule + "' lost its VF; file=" + psiJavaModule.getContainingFile() + "; valid=" + psiJavaModule.isValid());
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(101);
        }
        return virtualFile;
    }
}
