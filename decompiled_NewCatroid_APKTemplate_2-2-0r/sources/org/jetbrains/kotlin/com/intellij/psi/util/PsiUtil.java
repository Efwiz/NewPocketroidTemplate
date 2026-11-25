package org.jetbrains.kotlin.com.intellij.psi.util;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiBlockStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassLevelDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalLoopStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionListStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiForStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException$$ExternalSyntheticLambda0;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabeledRuleStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiYieldStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.infos.ClassCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaOwner;
import org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.TimeoutUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiUtil extends PsiUtilCore {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ACCESS_LEVEL_PACKAGE_LOCAL = 2;
    public static final int ACCESS_LEVEL_PRIVATE = 1;
    public static final int ACCESS_LEVEL_PROTECTED = 3;
    public static final int ACCESS_LEVEL_PUBLIC = 4;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiUtil.class);
    public static final Key<Boolean> VALID_VOID_TYPE_IN_CODE_FRAGMENT = Key.create("VALID_VOID_TYPE_IN_CODE_FRAGMENT");
    private static final Pattern IGNORED_NAMES = Pattern.compile("ignored?[A-Z]?[a-z]*\\d*");
    private static final String[] accessModifiers = {"private", PsiModifier.PACKAGE_LOCAL, "protected", "public"};
    public static final Key<LanguageLevel> FILE_LANGUAGE_LEVEL_KEY = Key.create("FORCE_LANGUAGE_LEVEL");
    public static final Comparator<PsiElement> BY_POSITION = new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil$$ExternalSyntheticLambda4
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return PsiUtil.compareElementsByPosition((PsiElement) obj, (PsiElement) obj2);
        }
    };

    public @interface AccessLevel {
    }

    /* JADX WARN: Code duplicated, block: B:120:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:19:0x003e A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:95:0x019d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 33 && i != 41 && i != 88 && i != 92 && i != 98 && i != 118 && i != 134 && i != 143 && i != 85 && i != 86 && i != 94 && i != 95) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 114:
                                case 115:
                                case 116:
                                    break;
                                default:
                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                    break;
                            }
                        case 37:
                        case 38:
                        case 39:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 13:
                case 14:
                case 15:
                case 16:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 33 && i != 41 && i != 88 && i != 92 && i != 98 && i != 118 && i != 134 && i != 143 && i != 85 && i != 86 && i != 94 && i != 95) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 37:
                        case 38:
                        case 39:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 114:
                                case 115:
                                case 116:
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
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i == 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 2) {
            switch (i) {
                case 5:
                case 8:
                case 10:
                case 132:
                    objArr[0] = "member";
                    break;
                case 6:
                case 9:
                case 11:
                case 17:
                case 25:
                case 81:
                    objArr[0] = "place";
                    break;
                case 7:
                case 117:
                    objArr[0] = "project";
                    break;
                case 12:
                    objArr[0] = "expression";
                    break;
                case 13:
                case 14:
                case 15:
                case 16:
                case 33:
                case 37:
                case 38:
                case 39:
                case 41:
                case 85:
                case 86:
                case 88:
                case 92:
                case 94:
                case 95:
                case 98:
                case 114:
                case 115:
                case 116:
                case 118:
                case 134:
                case 143:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiUtil";
                    break;
                case 18:
                case 20:
                case 22:
                case 23:
                case 42:
                case 45:
                case 48:
                case 51:
                case 55:
                case 96:
                case 133:
                    objArr[0] = "method";
                    break;
                case 19:
                    objArr[0] = "exceptionFQName";
                    break;
                case 21:
                    objArr[0] = "exceptionClass";
                    break;
                case 24:
                case 100:
                    objArr[0] = "name";
                    break;
                case 26:
                case 27:
                    objArr[0] = "psiClass";
                    break;
                case 28:
                case 119:
                case 120:
                case 121:
                case 122:
                case 137:
                    objArr[0] = "clazz";
                    break;
                case 29:
                    objArr[0] = "variable";
                    break;
                case 30:
                    objArr[0] = "result";
                    break;
                case 31:
                    objArr[0] = "container";
                    break;
                case 32:
                case 78:
                    objArr[0] = "modifierList";
                    break;
                case 34:
                case 79:
                case 82:
                case 99:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 110:
                case 111:
                case 112:
                case 113:
                case 136:
                case 142:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                case 35:
                    objArr[0] = "scope";
                    break;
                case 36:
                    objArr[0] = "range";
                    break;
                case 40:
                case 83:
                case 129:
                case 130:
                case 131:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 43:
                case 46:
                case 49:
                case 52:
                case 56:
                case 61:
                    objArr[0] = "substitutorForMethod";
                    break;
                case 44:
                case 47:
                case 50:
                    objArr[0] = "argList";
                    break;
                case 53:
                case 57:
                case 60:
                case 65:
                    objArr[0] = "languageLevel";
                    break;
                case 54:
                case 59:
                case 62:
                    objArr[0] = "args";
                    break;
                case 58:
                    objArr[0] = "function";
                    break;
                case 63:
                    objArr[0] = "parameters";
                    break;
                case 64:
                case 87:
                    objArr[0] = "parameter";
                    break;
                case 66:
                case 103:
                    objArr[0] = "substitutor";
                    break;
                case 67:
                    objArr[0] = "thisClassType";
                    break;
                case 68:
                case 72:
                case 77:
                case 80:
                    objArr[0] = "aClass";
                    break;
                case 69:
                    objArr[0] = "otherClassType";
                    break;
                case 70:
                case 74:
                    objArr[0] = "bClass";
                    break;
                case 71:
                    objArr[0] = "s1";
                    break;
                case 73:
                    objArr[0] = "s2";
                    break;
                case 75:
                    objArr[0] = "field";
                    break;
                case 76:
                    objArr[0] = "methods";
                    break;
                case 84:
                case 101:
                    objArr[0] = "context";
                    break;
                case 89:
                    objArr[0] = "manager";
                    break;
                case 90:
                    objArr[0] = "candidate";
                    break;
                case 91:
                case 93:
                case 102:
                case 125:
                    objArr[0] = "owner";
                    break;
                case 97:
                    objArr[0] = "candidates";
                    break;
                case 123:
                case 124:
                    objArr[0] = "superClass";
                    break;
                case 126:
                    objArr[0] = "property";
                    break;
                case 127:
                    objArr[0] = SpecTypePair.NAME_types;
                    break;
                case 128:
                    objArr[0] = "resourceType";
                    break;
                case 135:
                    objArr[0] = "vector";
                    break;
                case 138:
                    objArr[0] = "stopAt";
                    break;
                case 139:
                    objArr[0] = "file";
                    break;
                case 140:
                    objArr[0] = "packageName";
                    break;
                case 141:
                    objArr[0] = "directories";
                    break;
                case 144:
                case 146:
                    objArr[0] = "module";
                    break;
                case 145:
                    objArr[0] = "text";
                    break;
                case 147:
                    objArr[0] = "moduleStatement";
                    break;
                default:
                    objArr[0] = "expr";
                    break;
            }
        } else {
            objArr[0] = "aPackage";
        }
        if (i == 33) {
            objArr[1] = "getAccessModifier";
        } else if (i == 41) {
            objArr[1] = "convertAnonymousToBaseType";
        } else if (i == 88) {
            objArr[1] = "getParameterTypeElements";
        } else if (i == 92) {
            objArr[1] = "typeParametersIterator";
        } else if (i == 98) {
            objArr[1] = "mapElements";
        } else if (i == 118) {
            objArr[1] = "getLanguageLevel";
        } else if (i == 134) {
            objArr[1] = "findReturnStatements";
        } else if (i == 143) {
            objArr[1] = "preferCompiledElement";
        } else if (i != 85 && i != 86) {
            if (i != 94 && i != 95) {
                switch (i) {
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                        objArr[1] = "getAccessObjectClass";
                        break;
                    default:
                        switch (i) {
                            case 37:
                            case 38:
                            case 39:
                                objArr[1] = "resolveGenericsClassInType";
                                break;
                            default:
                                switch (i) {
                                    case 114:
                                    case 115:
                                    case 116:
                                        objArr[1] = "getLanguageLevel";
                                        break;
                                    default:
                                        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiUtil";
                                        break;
                                }
                                break;
                        }
                        break;
                }
            } else {
                objArr[1] = "typeParametersIterable";
            }
        } else {
            objArr[1] = "captureToplevelWildcards";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "isAccessibleFromPackage";
                break;
            case 3:
                objArr[2] = "isAccessedForWriting";
                break;
            case 4:
                objArr[2] = "isAccessedForReading";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "isAccessible";
                break;
            case 10:
            case 11:
                objArr[2] = "isMemberAccessibleAt";
                break;
            case 12:
            case 17:
                objArr[2] = "getAccessObjectClass";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 33:
            case 37:
            case 38:
            case 39:
            case 41:
            case 85:
            case 86:
            case 88:
            case 92:
            case 94:
            case 95:
            case 98:
            case 114:
            case 115:
            case 116:
            case 118:
            case 134:
            case 143:
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[2] = "addException";
                break;
            case 23:
                objArr[2] = "removeException";
                break;
            case 24:
            case 25:
                objArr[2] = "isVariableNameUnique";
                break;
            case 26:
                objArr[2] = "isLocalOrAnonymousClass";
                break;
            case 27:
                objArr[2] = "isLocalClass";
                break;
            case 28:
                objArr[2] = "isAbstractClass";
                break;
            case 29:
                objArr[2] = "getVariableCodeBlock";
                break;
            case 30:
            case 31:
                objArr[2] = "collectSwitchResultExpressions";
                break;
            case 32:
                objArr[2] = "getAccessLevel";
                break;
            case 34:
                objArr[2] = "isStatement";
                break;
            case 35:
            case 36:
                objArr[2] = "getElementInclusiveRange";
                break;
            case 40:
                objArr[2] = "convertAnonymousToBaseType";
                break;
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
                objArr[2] = "isApplicable";
                break;
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                objArr[2] = "getApplicabilityLevel";
                break;
            case 60:
            case 61:
            case 62:
            case 63:
                objArr[2] = "areFirstArgumentsApplicable";
                break;
            case 64:
            case 65:
            case 66:
                objArr[2] = "getParameterType";
                break;
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
                objArr[2] = "equalOnEquivalentClasses";
                break;
            case 75:
                objArr[2] = "isCompileTimeConstant";
                break;
            case 76:
                objArr[2] = "allMethodsHaveSameSignature";
                break;
            case 77:
                objArr[2] = "isInnerClass";
                break;
            case 78:
                objArr[2] = "findModifierInList";
                break;
            case 79:
                objArr[2] = "getTopLevelClass";
                break;
            case 80:
                objArr[2] = "getPackageName";
                break;
            case 81:
                objArr[2] = "getEnclosingStaticElement";
                break;
            case 82:
                objArr[2] = "getTypeByPsiElement";
                break;
            case 83:
            case 84:
                objArr[2] = "captureToplevelWildcards";
                break;
            case 87:
                objArr[2] = "getParameterTypeElements";
                break;
            case 89:
                objArr[2] = "checkIsIdentifier";
                break;
            case 90:
                objArr[2] = "getJarFile";
                break;
            case 91:
                objArr[2] = "typeParametersIterator";
                break;
            case 93:
                objArr[2] = "typeParametersIterable";
                break;
            case 96:
                objArr[2] = "canBeOverridden";
                break;
            case 97:
                objArr[2] = "mapElements";
                break;
            case 99:
            case 100:
            case 101:
                objArr[2] = "checkName";
                break;
            case 102:
            case 103:
                objArr[2] = "isRawSubstitutor";
                break;
            case 104:
                objArr[2] = "isLanguageLevel5OrHigher";
                break;
            case 105:
                objArr[2] = "isLanguageLevel6OrHigher";
                break;
            case 106:
                objArr[2] = "isLanguageLevel7OrHigher";
                break;
            case 107:
                objArr[2] = "isLanguageLevel8OrHigher";
                break;
            case 108:
                objArr[2] = "isLanguageLevel9OrHigher";
                break;
            case 109:
                objArr[2] = "isLanguageLevel10OrHigher";
                break;
            case 110:
                objArr[2] = "isLanguageLevel11OrHigher";
                break;
            case 111:
                objArr[2] = "isLanguageLevel14OrHigher";
                break;
            case 112:
                objArr[2] = "isLanguageLevel16OrHigher";
                break;
            case 113:
            case 117:
                objArr[2] = "getLanguageLevel";
                break;
            case 119:
                objArr[2] = "isInstantiatable";
                break;
            case 120:
            case 121:
            case 122:
                objArr[2] = "hasDefaultConstructor";
                break;
            case 123:
            case 124:
                objArr[2] = "substituteTypeParameter";
                break;
            case 125:
            case 126:
                objArr[2] = "setModifierProperty";
                break;
            case 127:
                objArr[2] = "isJavaToken";
                break;
            case 128:
                objArr[2] = "getResourceCloserMethodsForType";
                break;
            case 129:
            case 130:
            case 131:
                objArr[2] = "ensureValidType";
                break;
            case 132:
                objArr[2] = "getMemberQualifiedName";
                break;
            case 133:
                objArr[2] = "findReturnStatements";
                break;
            case 135:
            case 136:
            case 137:
            case 138:
                objArr[2] = "addStatements";
                break;
            case 139:
                objArr[2] = "isModuleFile";
                break;
            case 140:
            case 141:
                objArr[2] = "isPackageEmpty";
                break;
            case 142:
                objArr[2] = "preferCompiledElement";
                break;
            case 144:
            case 145:
            case 146:
            case 147:
                objArr[2] = "addModuleStatement";
                break;
            default:
                objArr[2] = "isOnAssignmentLeftHand";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 33 && i != 41 && i != 88 && i != 92 && i != 98 && i != 118 && i != 134 && i != 143 && i != 85 && i != 86 && i != 94 && i != 95) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    switch (i) {
                        case 37:
                        case 38:
                        case 39:
                            break;
                        default:
                            switch (i) {
                                case 114:
                                case 115:
                                case 116:
                                    break;
                                default:
                                    throw new IllegalArgumentException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$findReturnStatements$4(PsiElement psiElement) {
        return false;
    }

    private PsiUtil() {
    }

    public static boolean isOnAssignmentLeftHand(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        PsiElement psiElementSkipParentsOfType = PsiTreeUtil.skipParentsOfType(psiExpression, PsiParenthesizedExpression.class);
        return (psiElementSkipParentsOfType instanceof PsiAssignmentExpression) && PsiTreeUtil.isAncestor(((PsiAssignmentExpression) psiElementSkipParentsOfType).getLExpression(), psiExpression, false);
    }

    public static boolean isAccessibleFromPackage(PsiModifierListOwner psiModifierListOwner, PsiPackage psiPackage) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(1);
        }
        if (psiPackage == null) {
            $$$reportNull$$$0(2);
        }
        if (psiModifierListOwner.hasModifierProperty("public")) {
            return true;
        }
        return !psiModifierListOwner.hasModifierProperty("private") && JavaPsiFacade.getInstance(psiModifierListOwner.getProject()).isInPackage(psiModifierListOwner, psiPackage);
    }

    public static boolean isAccessedForWriting(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(3);
        }
        if (isOnAssignmentLeftHand(psiExpression)) {
            return true;
        }
        return isIncrementDecrementOperation(skipParenthesizedExprUp(psiExpression.getLightOwner()));
    }

    public static boolean isAccessedForReading(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(4);
        }
        PsiElement psiElementSkipParentsOfType = PsiTreeUtil.skipParentsOfType(psiExpression, PsiParenthesizedExpression.class);
        if (!(psiElementSkipParentsOfType instanceof PsiAssignmentExpression)) {
            return true;
        }
        PsiAssignmentExpression psiAssignmentExpression = (PsiAssignmentExpression) psiElementSkipParentsOfType;
        return (PsiTreeUtil.isAncestor(psiAssignmentExpression.getLExpression(), psiExpression, false) && psiAssignmentExpression.getOperationTokenType() == JavaTokenType.EQ) ? false : true;
    }

    public static boolean isAccessible(PsiMember psiMember, PsiElement psiElement, PsiClass psiClass) {
        if (psiMember == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        return isAccessible(psiElement.getProject(), psiMember, psiElement, psiClass);
    }

    public static boolean isAccessible(Project project, PsiMember psiMember, PsiElement psiElement, PsiClass psiClass) {
        if (project == null) {
            $$$reportNull$$$0(7);
        }
        if (psiMember == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        return JavaPsiFacade.getInstance(project).getResolveHelper().isAccessible(psiMember, psiElement, psiClass);
    }

    public static boolean isMemberAccessibleAt(PsiMember psiMember, PsiElement psiElement) {
        if (psiMember == null) {
            $$$reportNull$$$0(10);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        VirtualFile virtualFile = PsiUtilCore.getVirtualFile(psiMember);
        return (virtualFile == null || psiElement.getResolveScope().contains(virtualFile)) && isAccessible(psiMember, psiElement, null);
    }

    public static JavaResolveResult getAccessObjectClass(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(12);
        }
        if (psiExpression instanceof PsiSuperExpression) {
            JavaResolveResult javaResolveResult = JavaResolveResult.EMPTY;
            if (javaResolveResult == null) {
                $$$reportNull$$$0(13);
            }
            return javaResolveResult;
        }
        PsiType type = psiExpression.getType();
        JavaResolveResult accessObjectClass = getAccessObjectClass(type, psiExpression);
        if (accessObjectClass != null) {
            if (accessObjectClass == null) {
                $$$reportNull$$$0(14);
            }
            return accessObjectClass;
        }
        if (type == null && (psiExpression instanceof PsiReferenceExpression)) {
            JavaResolveResult javaResolveResultAdvancedResolve = ((PsiReferenceExpression) psiExpression).advancedResolve(false);
            if (javaResolveResultAdvancedResolve.getElement() instanceof PsiClass) {
                if (javaResolveResultAdvancedResolve == null) {
                    $$$reportNull$$$0(15);
                }
                return javaResolveResultAdvancedResolve;
            }
        }
        JavaResolveResult javaResolveResult2 = JavaResolveResult.EMPTY;
        if (javaResolveResult2 == null) {
            $$$reportNull$$$0(16);
        }
        return javaResolveResult2;
    }

    private static JavaResolveResult getAccessObjectClass(PsiType psiType, PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        if (psiType instanceof PsiClassType) {
            return ((PsiClassType) psiType).resolveGenerics();
        }
        if (psiType instanceof PsiDisjunctionType) {
            PsiType leastUpperBound = ((PsiDisjunctionType) psiType).getLeastUpperBound();
            if (leastUpperBound instanceof PsiClassType) {
                return ((PsiClassType) leastUpperBound).resolveGenerics();
            }
        }
        if (psiType instanceof PsiCapturedWildcardType) {
            PsiType upperBound = ((PsiCapturedWildcardType) psiType).getUpperBound();
            if (upperBound instanceof PsiClassType) {
                PsiClass psiClassResolve = ((PsiClassType) upperBound).resolve();
                PsiFile containingFile = psiClassResolve != null ? psiClassResolve.getContainingFile() : null;
                String packageName = containingFile instanceof PsiClassOwner ? ((PsiClassOwner) containingFile).getPackageName() : null;
                return new ClassCandidateInfo(((PsiJavaFile) PsiFileFactory.getInstance(psiElement.getProject()).createFileFromText("inference_dummy.java", JavaLanguage.INSTANCE, (StringUtil.isEmptyOrSpaces(packageName) ? "" : "package " + packageName + ";\n ") + "class I<T extends " + upperBound.getCanonicalText() + "> {}")).getClasses()[0].getTypeParameters()[0], PsiSubstitutor.EMPTY);
            }
        }
        if (psiType instanceof PsiArrayType) {
            return getAccessObjectClass(((PsiArrayType) psiType).getComponentType(), psiElement);
        }
        return null;
    }

    public static boolean isConstantExpression(PsiExpression psiExpression) {
        return psiExpression != null && JavaPsiFacade.getInstance(psiExpression.getProject()).isConstantExpression(psiExpression);
    }

    public static void addException(PsiMethod psiMethod, String str) throws IncorrectOperationException {
        if (psiMethod == null) {
            $$$reportNull$$$0(18);
        }
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        addException(psiMethod, JavaPsiFacade.getInstance(psiMethod.getProject()).findClass(str, psiMethod.getResolveScope()), str);
    }

    public static void addException(PsiMethod psiMethod, PsiClass psiClass) throws IncorrectOperationException {
        if (psiMethod == null) {
            $$$reportNull$$$0(20);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(21);
        }
        addException(psiMethod, psiClass, psiClass.getQualifiedName());
    }

    private static void addException(PsiMethod psiMethod, PsiClass psiClass, String str) throws IncorrectOperationException {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceElementByType;
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceElementByType2;
        if (psiMethod == null) {
            $$$reportNull$$$0(22);
        }
        if (psiClass == null && str == null) {
            throw new IllegalArgumentException("One of exceptionName, exceptionClass must be not null");
        }
        PsiReferenceList throwsList = psiMethod.getThrowsList();
        boolean z = false;
        for (PsiJavaCodeReferenceElement psiJavaCodeReferenceElement : throwsList.getReferenceElements()) {
            if (psiClass != null && psiJavaCodeReferenceElement.isReferenceTo(psiClass)) {
                return;
            }
            PsiClass psiClass2 = (PsiClass) psiJavaCodeReferenceElement.resolve();
            if (psiClass != null && psiClass2 != null) {
                if (!psiClass2.isInheritor(psiClass, true)) {
                    if (psiClass.isInheritor(psiClass2, true)) {
                        return;
                    }
                } else if (z) {
                    psiJavaCodeReferenceElement.delete();
                } else {
                    PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiMethod.getProject());
                    if (str != null) {
                        psiJavaCodeReferenceElementCreateReferenceElementByType2 = elementFactory.createReferenceElementByFQClassName(str, psiMethod.getResolveScope());
                    } else {
                        psiJavaCodeReferenceElementCreateReferenceElementByType2 = elementFactory.createReferenceElementByType(elementFactory.createType(psiClass));
                    }
                    psiJavaCodeReferenceElement.replace(psiJavaCodeReferenceElementCreateReferenceElementByType2);
                    z = true;
                }
            }
        }
        if (z) {
            return;
        }
        PsiElementFactory elementFactory2 = JavaPsiFacade.getElementFactory(psiMethod.getProject());
        if (str != null) {
            psiJavaCodeReferenceElementCreateReferenceElementByType = elementFactory2.createReferenceElementByFQClassName(str, psiMethod.getResolveScope());
        } else {
            PsiClass superClass = psiClass.getSuperClass();
            while (superClass != null && isLocalOrAnonymousClass(superClass)) {
                superClass = superClass.getSuperClass();
            }
            if (superClass != null) {
                psiClass = superClass;
            }
            psiJavaCodeReferenceElementCreateReferenceElementByType = elementFactory2.createReferenceElementByType(elementFactory2.createType(psiClass));
        }
        throwsList.add(psiJavaCodeReferenceElementCreateReferenceElementByType);
    }

    public static void removeException(PsiMethod psiMethod, String str) throws IncorrectOperationException {
        if (psiMethod == null) {
            $$$reportNull$$$0(23);
        }
        for (PsiJavaCodeReferenceElement psiJavaCodeReferenceElement : psiMethod.getThrowsList().getReferenceElements()) {
            if (psiJavaCodeReferenceElement.getCanonicalText().equals(str)) {
                psiJavaCodeReferenceElement.delete();
            }
        }
    }

    public static boolean isVariableNameUnique(String str, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(25);
        }
        return JavaPsiFacade.getInstance(psiElement.getProject()).getResolveHelper().resolveAccessibleReferencedVariable(str, psiElement) == null;
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0074 A[LOOP:0: B:3:0x0001->B:47:0x0074, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:54:0x0076 A[EDGE_INSN: B:54:0x0076->B:48:0x0076 BREAK  A[LOOP:0: B:3:0x0001->B:47:0x0074], SYNTHETIC] */
    public static PsiElement getTopLevelEnclosingCodeBlock(PsiElement psiElement, PsiElement psiElement2) {
        PsiElement psiElement3 = null;
        while (psiElement != null) {
            PsiElement parent = psiElement.getLightOwner();
            if (!(parent instanceof PsiExpression) || (parent instanceof PsiLambdaExpression)) {
                if ((psiElement instanceof PsiCodeBlock) || (psiElement instanceof PsiForStatement) || (psiElement instanceof PsiForeachStatement)) {
                    psiElement3 = psiElement;
                }
                if (((parent instanceof PsiMethod) && (parent.getLightOwner() instanceof PsiClass) && !isLocalOrAnonymousClass((PsiClass) parent.getLightOwner())) || ((parent instanceof PsiClassInitializer) && !(parent.getLightOwner() instanceof PsiAnonymousClass))) {
                    break;
                }
                if ((parent instanceof PsiField) && ((PsiField) parent).getInitializer() == psiElement) {
                    psiElement3 = psiElement;
                }
                if (parent instanceof PsiClassLevelDeclarationStatement) {
                    parent = parent.getLightOwner();
                }
                if ((psiElement instanceof PsiClass) && !isLocalOrAnonymousClass((PsiClass) psiElement)) {
                    break;
                }
                if ((psiElement instanceof PsiFile) && PsiUtilCore.getTemplateLanguageFile(psiElement) != null) {
                    return psiElement;
                }
                if (psiElement == psiElement2) {
                    break;
                }
                psiElement = parent;
            } else {
                if (psiElement == psiElement2) {
                    break;
                    break;
                }
                psiElement = parent;
            }
        }
        return psiElement3;
    }

    public static boolean isLocalOrAnonymousClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(26);
        }
        return (psiClass instanceof PsiAnonymousClass) || isLocalClass(psiClass);
    }

    public static boolean isLocalClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(27);
        }
        PsiElement parent = psiClass.getLightOwner();
        if ((parent instanceof PsiDeclarationStatement) && (parent.getLightOwner() instanceof PsiCodeBlock)) {
            return true;
        }
        if (parent instanceof PsiClass) {
            return isLocalOrAnonymousClass((PsiClass) parent);
        }
        return false;
    }

    public static boolean isAbstractClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(28);
        }
        PsiModifierList modifierList = psiClass.mo6095getModifierList();
        return modifierList != null && modifierList.hasModifierProperty("abstract");
    }

    public static PsiElement getVariableCodeBlock(PsiVariable psiVariable, PsiElement psiElement) {
        if (psiVariable == null) {
            $$$reportNull$$$0(29);
        }
        if (psiVariable instanceof PsiParameter) {
            PsiElement declarationScope = ((PsiParameter) psiVariable).getDeclarationScope();
            if (declarationScope instanceof PsiCatchSection) {
                return ((PsiCatchSection) declarationScope).getCatchBlock();
            }
            if (declarationScope instanceof PsiForeachStatement) {
                return ((PsiForeachStatement) declarationScope).getBody();
            }
            if (declarationScope instanceof PsiMethod) {
                return ((PsiMethod) declarationScope).getBody();
            }
            if (declarationScope instanceof PsiLambdaExpression) {
                return ((PsiLambdaExpression) declarationScope).getBody();
            }
            return null;
        }
        if (psiVariable instanceof PsiResourceVariable) {
            PsiElement parent = psiVariable.getLightOwner();
            if (parent != null) {
                return parent.getLightOwner();
            }
            return null;
        }
        if ((psiVariable instanceof PsiLocalVariable) && (psiVariable.getLightOwner() instanceof PsiForStatement)) {
            return psiVariable.getLightOwner();
        }
        if ((psiVariable instanceof PsiField) && psiElement != null) {
            PsiClass containingClass = ((PsiField) psiVariable).getContainingClass();
            while (psiElement != null && psiElement.getLightOwner() != containingClass) {
                psiElement = psiElement.getLightOwner();
                if (psiElement instanceof PsiClassLevelDeclarationStatement) {
                    return null;
                }
            }
            if (psiElement instanceof PsiMethod) {
                return ((PsiMethod) psiElement).getBody();
            }
            if (psiElement instanceof PsiClassInitializer) {
                return ((PsiClassInitializer) psiElement).getBody();
            }
            return null;
        }
        PsiElement topLevelEnclosingCodeBlock = getTopLevelEnclosingCodeBlock(psiVariable, psiVariable.getLightOwner() != null ? psiVariable.getLightOwner().getLightOwner() : null);
        return (topLevelEnclosingCodeBlock == null || !(topLevelEnclosingCodeBlock.getLightOwner() instanceof PsiSwitchStatement)) ? topLevelEnclosingCodeBlock : topLevelEnclosingCodeBlock.getLightOwner().getLightOwner();
    }

    public static boolean isIncrementDecrementOperation(PsiElement psiElement) {
        if (!(psiElement instanceof PsiUnaryExpression)) {
            return false;
        }
        IElementType operationTokenType = ((PsiUnaryExpression) psiElement).getOperationTokenType();
        return operationTokenType == JavaTokenType.PLUSPLUS || operationTokenType == JavaTokenType.MINUSMINUS;
    }

    public static List<PsiExpression> getSwitchResultExpressions(PsiSwitchExpression psiSwitchExpression) {
        PsiCodeBlock body = psiSwitchExpression.getBody();
        if (body != null) {
            ArrayList arrayList = new ArrayList();
            for (PsiStatement psiStatement : body.getStatements()) {
                if (psiStatement instanceof PsiSwitchLabeledRuleStatement) {
                    PsiStatement body2 = ((PsiSwitchLabeledRuleStatement) psiStatement).getBody();
                    if (body2 instanceof PsiExpressionStatement) {
                        arrayList.add(((PsiExpressionStatement) body2).getExpression());
                    } else if (body2 instanceof PsiBlockStatement) {
                        collectSwitchResultExpressions(arrayList, body2);
                    }
                } else {
                    collectSwitchResultExpressions(arrayList, psiStatement);
                }
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    private static void collectSwitchResultExpressions(List<? super PsiExpression> list, PsiElement psiElement) {
        if (list == null) {
            $$$reportNull$$$0(30);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(31);
        }
        ArrayList arrayList = new ArrayList();
        addStatements(arrayList, psiElement, PsiYieldStatement.class, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PsiUtil.lambda$collectSwitchResultExpressions$0((PsiElement) obj);
            }
        });
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContainerUtil.addIfNotNull(list, ((PsiYieldStatement) it.next()).getExpression());
        }
    }

    static /* synthetic */ boolean lambda$collectSwitchResultExpressions$0(PsiElement psiElement) {
        return psiElement instanceof PsiSwitchExpression;
    }

    public static int getAccessLevel(PsiModifierList psiModifierList) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(32);
        }
        if (psiModifierList.hasModifierProperty("private")) {
            return 1;
        }
        if (psiModifierList.hasModifierProperty(PsiModifier.PACKAGE_LOCAL)) {
            return 2;
        }
        return psiModifierList.hasModifierProperty("protected") ? 3 : 4;
    }

    public static String getAccessModifier(int i) {
        if (i > 0) {
            String[] strArr = accessModifiers;
            if (i <= strArr.length) {
                String str = strArr[i - 1];
                if (str == null) {
                    $$$reportNull$$$0(33);
                }
                return str;
            }
        }
        throw new IllegalArgumentException("Unknown level:" + i);
    }

    public static boolean isStatement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(34);
        }
        PsiElement parent = psiElement.getLightOwner();
        if (psiElement instanceof PsiExpressionListStatement) {
            if (!(parent instanceof PsiForStatement)) {
                return false;
            }
            PsiForStatement psiForStatement = (PsiForStatement) parent;
            if (psiElement != psiForStatement.getInitialization() && psiElement != psiForStatement.getUpdate()) {
                return false;
            }
            for (PsiExpression psiExpression : ((PsiExpressionListStatement) psiElement).getExpressionList().getExpressions()) {
                if (!isStatement(psiExpression)) {
                    return false;
                }
            }
            return true;
        }
        if (psiElement instanceof PsiExpressionStatement) {
            return ((parent instanceof PsiSwitchLabeledRuleStatement) && (((PsiSwitchLabeledRuleStatement) parent).getEnclosingSwitchBlock() instanceof PsiSwitchExpression)) || isStatement(((PsiExpressionStatement) psiElement).getExpression());
        }
        if (psiElement instanceof PsiDeclarationStatement) {
            if ((parent instanceof PsiCodeBlock) || (parent instanceof PsiCodeFragment)) {
                return true;
            }
            if (!(parent instanceof PsiForStatement) || ((PsiForStatement) parent).getBody() == psiElement) {
                return false;
            }
        }
        if ((psiElement instanceof PsiStatement) || (psiElement instanceof PsiAssignmentExpression) || isIncrementDecrementOperation(psiElement) || (psiElement instanceof PsiMethodCallExpression)) {
            return true;
        }
        if (psiElement instanceof PsiNewExpression) {
            return !(((PsiNewExpression) psiElement).getType() instanceof PsiArrayType);
        }
        return psiElement instanceof PsiCodeBlock;
    }

    public static PsiElement getEnclosingStatement(PsiElement psiElement) {
        while (psiElement != null) {
            if (psiElement.getLightOwner() instanceof PsiCodeBlock) {
                return psiElement;
            }
            psiElement = psiElement.getLightOwner();
        }
        return null;
    }

    public static PsiElement getElementInclusiveRange(PsiElement psiElement, TextRange textRange) {
        if (psiElement == null) {
            $$$reportNull$$$0(35);
        }
        if (textRange == null) {
            $$$reportNull$$$0(36);
        }
        PsiElement psiElementFindElementAt = psiElement.findElementAt(textRange.getStartOffset());
        while (psiElementFindElementAt != null && !psiElementFindElementAt.getTextRange().contains(textRange)) {
            if (psiElementFindElementAt == psiElement) {
                return null;
            }
            psiElementFindElementAt = psiElementFindElementAt.getLightOwner();
        }
        return psiElementFindElementAt;
    }

    public static PsiClass resolveClassInType(PsiType psiType) {
        if (psiType instanceof PsiClassType) {
            return ((PsiClassType) psiType).resolve();
        }
        if (psiType instanceof PsiArrayType) {
            return resolveClassInType(((PsiArrayType) psiType).getComponentType());
        }
        if (!(psiType instanceof PsiDisjunctionType)) {
            return null;
        }
        PsiType leastUpperBound = ((PsiDisjunctionType) psiType).getLeastUpperBound();
        if (leastUpperBound instanceof PsiClassType) {
            return ((PsiClassType) leastUpperBound).resolve();
        }
        return null;
    }

    public static PsiClass resolveClassInClassTypeOnly(PsiType psiType) {
        if (psiType instanceof PsiClassType) {
            return ((PsiClassType) psiType).resolve();
        }
        return null;
    }

    public static PsiClassType.ClassResolveResult resolveGenericsClassInType(PsiType psiType) {
        if (psiType instanceof PsiClassType) {
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiType).resolveGenerics();
            if (classResolveResultResolveGenerics == null) {
                $$$reportNull$$$0(37);
            }
            return classResolveResultResolveGenerics;
        }
        if (psiType instanceof PsiArrayType) {
            return resolveGenericsClassInType(((PsiArrayType) psiType).getComponentType());
        }
        if (psiType instanceof PsiDisjunctionType) {
            PsiType leastUpperBound = ((PsiDisjunctionType) psiType).getLeastUpperBound();
            if (leastUpperBound instanceof PsiClassType) {
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = ((PsiClassType) leastUpperBound).resolveGenerics();
                if (classResolveResultResolveGenerics2 == null) {
                    $$$reportNull$$$0(38);
                }
                return classResolveResultResolveGenerics2;
            }
        }
        PsiClassType.ClassResolveResult classResolveResult = PsiClassType.ClassResolveResult.EMPTY;
        if (classResolveResult == null) {
            $$$reportNull$$$0(39);
        }
        return classResolveResult;
    }

    public static PsiType convertAnonymousToBaseType(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(40);
        }
        PsiClass psiClassResolveClassInType = resolveClassInType(psiType);
        if (psiClassResolveClassInType instanceof PsiAnonymousClass) {
            psiType = PsiTypesUtil.createArrayType(((PsiAnonymousClass) psiClassResolveClassInType).getBaseClassType(), psiType.getArrayDimensions());
        }
        if (psiType == null) {
            $$$reportNull$$$0(41);
        }
        return psiType;
    }

    public static boolean isApplicable(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, PsiExpressionList psiExpressionList) {
        if (psiMethod == null) {
            $$$reportNull$$$0(42);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(43);
        }
        if (psiExpressionList == null) {
            $$$reportNull$$$0(44);
        }
        return getApplicabilityLevel(psiMethod, psiSubstitutor, psiExpressionList) != 1;
    }

    public static boolean isApplicable(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, PsiExpression[] psiExpressionArr) {
        if (psiMethod == null) {
            $$$reportNull$$$0(45);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(46);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(47);
        }
        return getApplicabilityLevel(psiMethod, psiSubstitutor, (PsiType[]) ContainerUtil.map2Array(psiExpressionArr, PsiType.class, PsiExpression.EXPRESSION_TO_TYPE), getLanguageLevel(psiMethod)) != 1;
    }

    public static int getApplicabilityLevel(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, PsiExpressionList psiExpressionList) {
        if (psiMethod == null) {
            $$$reportNull$$$0(48);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(49);
        }
        if (psiExpressionList == null) {
            $$$reportNull$$$0(50);
        }
        return getApplicabilityLevel(psiMethod, psiSubstitutor, psiExpressionList.getExpressionTypes(), getLanguageLevel(psiExpressionList));
    }

    public static int getApplicabilityLevel(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, PsiType[] psiTypeArr, LanguageLevel languageLevel) {
        if (psiMethod == null) {
            $$$reportNull$$$0(51);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(52);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(53);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(54);
        }
        return getApplicabilityLevel(psiMethod, psiSubstitutor, psiTypeArr, languageLevel, true, true, ApplicabilityChecker.ASSIGNABILITY_CHECKER);
    }

    @FunctionalInterface
    public interface ApplicabilityChecker {
        public static final ApplicabilityChecker ASSIGNABILITY_CHECKER = new ApplicabilityChecker() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil$ApplicabilityChecker$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil.ApplicabilityChecker
            public final boolean isApplicable(PsiType psiType, PsiType psiType2, boolean z, int i) {
                return TypeConversionUtil.isAssignable(psiType, psiType2, z);
            }
        };

        boolean isApplicable(PsiType psiType, PsiType psiType2, boolean z, int i);
    }

    public static int getApplicabilityLevel(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, PsiType[] psiTypeArr, LanguageLevel languageLevel, boolean z, boolean z2, ApplicabilityChecker applicabilityChecker) {
        PsiType psiTypeErasure;
        if (psiMethod == null) {
            $$$reportNull$$$0(55);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(56);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(57);
        }
        if (applicabilityChecker == null) {
            $$$reportNull$$$0(58);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(59);
        }
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        if (psiTypeArr.length < parameters.length - 1) {
            return 1;
        }
        PsiClass containingClass = psiMethod.getContainingClass();
        boolean z3 = containingClass != null && isRawSubstitutor(psiMethod, psiSubstitutor) && isRawSubstitutor(containingClass, psiSubstitutor);
        if (!areFirstArgumentsApplicable(psiTypeArr, parameters, languageLevel, psiSubstitutor, z3, z, applicabilityChecker)) {
            return 1;
        }
        if (psiTypeArr.length == parameters.length) {
            if (parameters.length == 0) {
                return 3;
            }
            PsiType parameterType = getParameterType(parameters[parameters.length - 1], languageLevel, psiSubstitutor);
            PsiType psiType = psiTypeArr[psiTypeArr.length - 1];
            if (psiType == null) {
                return 1;
            }
            if (applicabilityChecker.isApplicable(parameterType, psiType, z, parameters.length - 1)) {
                return 3;
            }
            if (z3 && (psiTypeErasure = TypeConversionUtil.erasure(parameterType)) != null && applicabilityChecker.isApplicable(psiTypeErasure, psiType, z, parameters.length - 1)) {
                return 3;
            }
        }
        if (z2 && psiMethod.isVarArgs() && languageLevel.compareTo(LanguageLevel.JDK_1_5) >= 0) {
            if (psiTypeArr.length < parameters.length) {
                return 2;
            }
            PsiParameter psiParameter = parameters.length == 0 ? null : parameters[parameters.length - 1];
            if (psiParameter != null && psiParameter.isVarArgs()) {
                PsiType parameterType2 = getParameterType(psiParameter, languageLevel, psiSubstitutor);
                if (!(parameterType2 instanceof PsiArrayType)) {
                    return 1;
                }
                PsiType componentType = ((PsiArrayType) parameterType2).getComponentType();
                if (componentType instanceof PsiCapturedWildcardType) {
                    PsiCapturedWildcardType psiCapturedWildcardType = (PsiCapturedWildcardType) componentType;
                    if (!JavaVersionService.getInstance().isAtLeast(psiCapturedWildcardType.getContext(), JavaSdkVersion.JDK_1_8)) {
                        componentType = psiCapturedWildcardType.getWildcard();
                    }
                }
                if (componentType instanceof PsiClassType) {
                    componentType = ((PsiClassType) componentType).setLanguageLevel(languageLevel);
                }
                for (int length = parameters.length - 1; length < psiTypeArr.length; length++) {
                    PsiType psiType2 = psiTypeArr[length];
                    if (psiType2 == null || !applicabilityChecker.isApplicable(componentType, psiType2, z, length)) {
                        return 1;
                    }
                }
                return 2;
            }
        }
        return 1;
    }

    private static boolean areFirstArgumentsApplicable(PsiType[] psiTypeArr, PsiParameter[] psiParameterArr, LanguageLevel languageLevel, PsiSubstitutor psiSubstitutor, boolean z, boolean z2, ApplicabilityChecker applicabilityChecker) {
        if (languageLevel == null) {
            $$$reportNull$$$0(60);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(61);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(62);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(63);
        }
        for (int i = 0; i < psiParameterArr.length - 1; i++) {
            PsiType psiType = psiTypeArr[i];
            if (psiType == null) {
                return false;
            }
            PsiType parameterType = getParameterType(psiParameterArr[i], languageLevel, psiSubstitutor);
            if (z) {
                PsiType psiTypeErasure = TypeConversionUtil.erasure(parameterType);
                if (psiTypeErasure != null && !applicabilityChecker.isApplicable(psiTypeErasure, psiType, z2, i)) {
                    return false;
                }
            } else if (!applicabilityChecker.isApplicable(parameterType, psiType, z2, i)) {
                return false;
            }
        }
        return true;
    }

    private static PsiType getParameterType(PsiParameter psiParameter, LanguageLevel languageLevel, PsiSubstitutor psiSubstitutor) {
        if (psiParameter == null) {
            $$$reportNull$$$0(64);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(65);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(66);
        }
        PsiType type = psiParameter.getType();
        if (type instanceof PsiClassType) {
            type = ((PsiClassType) type).setLanguageLevel(languageLevel);
        }
        return psiSubstitutor.substitute(type);
    }

    public static boolean equalOnEquivalentClasses(PsiClassType psiClassType, PsiClass psiClass, PsiClassType psiClassType2, PsiClass psiClass2) {
        if (psiClassType == null) {
            $$$reportNull$$$0(67);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(68);
        }
        if (psiClassType2 == null) {
            $$$reportNull$$$0(69);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(70);
        }
        if (PsiCapturedWildcardType.isCapture()) {
            psiClassType = (PsiClassType) captureToplevelWildcards(psiClassType, psiClass);
        }
        if (PsiCapturedWildcardType.isCapture()) {
            psiClassType2 = (PsiClassType) captureToplevelWildcards(psiClassType2, psiClass2);
        }
        return equalOnEquivalentClasses(psiClassType.resolveGenerics().getSubstitutor(), psiClass, psiClassType2.resolveGenerics().getSubstitutor(), psiClass2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean equalOnEquivalentClasses(PsiSubstitutor psiSubstitutor, PsiClass psiClass, PsiSubstitutor psiSubstitutor2, PsiClass psiClass2) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(71);
        }
        if (psiClass == 0) {
            $$$reportNull$$$0(72);
        }
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(73);
        }
        if (psiClass2 == 0) {
            $$$reportNull$$$0(74);
        }
        if ((psiSubstitutor == psiSubstitutor2 && psiClass == psiClass2) || psiClass.hasTypeParameters() != psiClass2.hasTypeParameters()) {
            return true;
        }
        PsiTypeParameter[] typeParameters = psiClass.getTypeParameters();
        PsiTypeParameter[] typeParameters2 = psiClass2.getTypeParameters();
        if (typeParameters.length != typeParameters2.length) {
            return false;
        }
        for (int i = 0; i < typeParameters.length; i++) {
            if (!Comparing.equal(psiSubstitutor.substitute(typeParameters[i]), psiSubstitutor2.substitute(typeParameters2[i]))) {
                return false;
            }
        }
        if (psiClass.hasModifierProperty("static")) {
            return true;
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.getContainingClass();
        PsiClass psiClassMo6092getContainingClass2 = psiClass2.getContainingClass();
        if (psiClassMo6092getContainingClass != null && psiClassMo6092getContainingClass2 != null) {
            return equalOnEquivalentClasses(psiSubstitutor, psiClassMo6092getContainingClass, psiSubstitutor2, psiClassMo6092getContainingClass2);
        }
        if (psiClassMo6092getContainingClass != null || psiClassMo6092getContainingClass2 != null) {
            return false;
        }
        if (psiClass != psiClass2 || !isLocalClass(psiClass)) {
            return true;
        }
        PsiClass psiClass3 = (PsiClass) PsiTreeUtil.getParentOfType(psiClass, PsiClass.class);
        return psiClass3 != null && equalOnEquivalentClasses(psiSubstitutor, psiClass3, psiSubstitutor2, psiClass3);
    }

    public static boolean isCompileTimeConstant(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(75);
        }
        if (!psiVariable.hasModifierProperty("final")) {
            return false;
        }
        PsiType type = psiVariable.getType();
        return (TypeConversionUtil.isPrimitiveAndNotNull(type) || type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) && psiVariable.hasInitializer() && isConstantExpression(psiVariable.getInitializer());
    }

    public static boolean allMethodsHaveSameSignature(PsiMethod[] psiMethodArr) {
        if (psiMethodArr == null) {
            $$$reportNull$$$0(76);
        }
        if (psiMethodArr.length == 0) {
            return true;
        }
        MethodSignature signature = psiMethodArr[0].getSignature(PsiSubstitutor.EMPTY);
        for (int i = 1; i < psiMethodArr.length; i++) {
            if (!signature.equals(psiMethodArr[i].getSignature(PsiSubstitutor.EMPTY))) {
                return false;
            }
        }
        return true;
    }

    public static PsiExpression deparenthesizeExpression(PsiExpression psiExpression) {
        while (true) {
            if (psiExpression instanceof PsiParenthesizedExpression) {
                psiExpression = ((PsiParenthesizedExpression) psiExpression).getExpression();
            } else {
                if (!(psiExpression instanceof PsiTypeCastExpression)) {
                    return psiExpression;
                }
                psiExpression = ((PsiTypeCastExpression) psiExpression).getOperand();
            }
        }
    }

    public static boolean isInnerClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(77);
        }
        return (psiClass.hasModifierProperty("static") || psiClass.getContainingClass() == null) ? false : true;
    }

    public static PsiElement findModifierInList(PsiModifierList psiModifierList, String str) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(78);
        }
        for (PsiElement psiElement : psiModifierList.getChildren()) {
            if (psiElement.getText().equals(str)) {
                return psiElement;
            }
        }
        return null;
    }

    public static PsiClass getTopLevelClass(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(79);
        }
        PsiClass psiClass = (PsiClass) JBIterable.generate(psiElement, new PsiInvalidElementAccessException$$ExternalSyntheticLambda0()).takeWhile(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiUtil.lambda$getTopLevelClass$1((PsiElement) obj);
            }
        }).filter(PsiClass.class).last();
        if (psiClass instanceof PsiTypeParameter) {
            return null;
        }
        return psiClass;
    }

    static /* synthetic */ boolean lambda$getTopLevelClass$1(PsiElement psiElement) {
        return !(psiElement instanceof PsiFile);
    }

    public static String getPackageName(PsiClass psiClass) {
        String qualifiedName;
        if (psiClass == null) {
            $$$reportNull$$$0(80);
        }
        PsiClass topLevelClass = getTopLevelClass(psiClass);
        if (topLevelClass != null && (qualifiedName = topLevelClass.getQualifiedName()) != null) {
            return StringUtil.getPackageName(qualifiedName);
        }
        PsiFile containingFile = psiClass.getContainingFile();
        if (containingFile instanceof PsiClassOwner) {
            return ((PsiClassOwner) containingFile).getPackageName();
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0018  */
    public static PsiModifierListOwner getEnclosingStaticElement(PsiElement psiElement, PsiClass psiClass) {
        boolean z;
        if (psiElement == null) {
            $$$reportNull$$$0(81);
        }
        Logger logger = LOG;
        if (psiClass != null && psiElement.getIsPhysical()) {
            z = PsiTreeUtil.isContextAncestor(psiClass, psiElement, false);
        }
        logger.assertTrue(z);
        while (psiElement != psiClass && !(psiElement instanceof PsiFile)) {
            if (psiElement instanceof PsiModifierListOwner) {
                PsiModifierListOwner psiModifierListOwner = (PsiModifierListOwner) psiElement;
                if (psiModifierListOwner.hasModifierProperty("static")) {
                    return psiModifierListOwner;
                }
            }
            psiElement = psiElement.getLightOwner();
        }
        return null;
    }

    public static PsiType getTypeByPsiElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(82);
        }
        if (psiElement instanceof PsiVariable) {
            return ((PsiVariable) psiElement).getType();
        }
        if (psiElement instanceof PsiMethod) {
            return ((PsiMethod) psiElement).getReturnType();
        }
        return null;
    }

    public static PsiType captureToplevelWildcards(PsiType psiType, PsiElement psiElement) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics;
        PsiClass element;
        if (psiType == null) {
            $$$reportNull$$$0(83);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(84);
        }
        if ((psiType instanceof PsiClassType) && (element = (classResolveResultResolveGenerics = ((PsiClassType) psiType).resolveGenerics()).getElement()) != null) {
            PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
            PsiSubstitutor psiSubstitutorPut = substitutor;
            for (PsiTypeParameter psiTypeParameter : typeParametersIterable(element)) {
                PsiType psiTypeSubstitute = substitutor.substitute(psiTypeParameter);
                if (psiTypeSubstitute instanceof PsiWildcardType) {
                    psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, PsiCapturedWildcardType.create((PsiWildcardType) psiTypeSubstitute, psiElement, psiTypeParameter));
                }
            }
            if (psiSubstitutorPut != substitutor) {
                HashMap map = null;
                for (PsiTypeParameter psiTypeParameter2 : typeParametersIterable(element)) {
                    PsiType psiTypeSubstitute2 = substitutor.substitute(psiTypeParameter2);
                    if (psiTypeSubstitute2 instanceof PsiWildcardType) {
                        if (map == null) {
                            map = new HashMap(substitutor.getSubstitutionMap());
                        }
                        PsiCapturedWildcardType psiCapturedWildcardType = (PsiCapturedWildcardType) psiSubstitutorPut.substitute(psiTypeParameter2);
                        LOG.assertTrue(psiCapturedWildcardType != null);
                        PsiType psiTypeCaptureUpperBound = PsiCapturedWildcardType.captureUpperBound(psiTypeParameter2, (PsiWildcardType) psiTypeSubstitute2, psiSubstitutorPut);
                        if (psiTypeCaptureUpperBound != null) {
                            psiCapturedWildcardType.setUpperBound(psiTypeCaptureUpperBound);
                        }
                        map.put(psiTypeParameter2, psiCapturedWildcardType);
                    }
                }
                if (map != null) {
                    PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(element.getProject());
                    PsiClassType psiClassTypeCreateType = elementFactory.createType(element, elementFactory.createSubstitutor(map));
                    if (psiClassTypeCreateType == null) {
                        $$$reportNull$$$0(85);
                    }
                    return psiClassTypeCreateType;
                }
            }
        }
        if (psiType == null) {
            $$$reportNull$$$0(86);
        }
        return psiType;
    }

    public static boolean isInsideJavadocComment(PsiElement psiElement) {
        return PsiTreeUtil.getParentOfType(psiElement, PsiDocComment.class, true) != null;
    }

    public static List<PsiTypeElement> getParameterTypeElements(PsiParameter psiParameter) {
        List<PsiTypeElement> listSingletonList;
        if (psiParameter == null) {
            $$$reportNull$$$0(87);
        }
        PsiTypeElement typeElement = psiParameter.getTypeElement();
        if (typeElement != null && (typeElement.getType() instanceof PsiDisjunctionType)) {
            listSingletonList = PsiTreeUtil.getChildrenOfTypeAsList(typeElement, PsiTypeElement.class);
        } else {
            listSingletonList = Collections.singletonList(typeElement);
        }
        if (listSingletonList == null) {
            $$$reportNull$$$0(88);
        }
        return listSingletonList;
    }

    public static void checkIsIdentifier(PsiManager psiManager, String str) throws IncorrectOperationException {
        if (psiManager == null) {
            $$$reportNull$$$0(89);
        }
        if (!PsiNameHelper.getInstance(psiManager.getProject()).isIdentifier(str)) {
            throw new IncorrectOperationException(JavaPsiBundle.message("0.is.not.an.identifier", str));
        }
    }

    public static VirtualFile getJarFile(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(90);
        }
        VirtualFile virtualFile = psiElement.getContainingFile().getVirtualFile();
        return (virtualFile == null || !virtualFile.getFileSystem().getProtocol().equals("jar")) ? virtualFile : VfsUtilCore.getVirtualFileForJar(virtualFile);
    }

    public static boolean isAnnotationMethod(PsiElement psiElement) {
        PsiClass containingClass;
        return (psiElement instanceof PsiAnnotationMethod) && (containingClass = ((PsiAnnotationMethod) psiElement).getContainingClass()) != null && containingClass.isAnnotationType();
    }

    public static String getMaximumModifierForMember(PsiClass psiClass, boolean z) {
        if (!z && psiClass.hasModifierProperty("abstract") && !psiClass.isEnum()) {
            return "protected";
        }
        String str = PsiModifier.PACKAGE_LOCAL;
        if (!psiClass.hasModifierProperty(PsiModifier.PACKAGE_LOCAL) && !psiClass.isEnum()) {
            str = "private";
            if (!psiClass.hasModifierProperty("private")) {
                return "public";
            }
        }
        return str;
    }

    public static Iterator<PsiTypeParameter> typeParametersIterator(PsiTypeParameterListOwner psiTypeParameterListOwner) {
        if (psiTypeParameterListOwner == null) {
            $$$reportNull$$$0(91);
        }
        Iterator<PsiTypeParameter> it = typeParametersIterable(psiTypeParameterListOwner).iterator();
        if (it == null) {
            $$$reportNull$$$0(92);
        }
        return it;
    }

    public static Iterable<PsiTypeParameter> typeParametersIterable(PsiTypeParameterListOwner psiTypeParameterListOwner) {
        if (psiTypeParameterListOwner == null) {
            $$$reportNull$$$0(93);
        }
        Iterable<PsiTypeParameter> iterableConcat = null;
        while (psiTypeParameterListOwner != null) {
            final PsiTypeParameter[] typeParameters = psiTypeParameterListOwner.getTypeParameters();
            if (typeParameters.length > 0) {
                Iterable<PsiTypeParameter> iterable = new Iterable() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil$$ExternalSyntheticLambda3
                    @Override // java.lang.Iterable
                    public final Iterator iterator() {
                        return PsiUtil.lambda$typeParametersIterable$2(typeParameters);
                    }
                };
                iterableConcat = iterableConcat == null ? iterable : ContainerUtil.concat(iterableConcat, iterable);
            }
            if (psiTypeParameterListOwner.hasModifierProperty("static")) {
                break;
            }
            if ((psiTypeParameterListOwner instanceof PsiClass) && isLocalClass((PsiClass) psiTypeParameterListOwner)) {
                psiTypeParameterListOwner = (PsiTypeParameterListOwner) PsiTreeUtil.getParentOfType(psiTypeParameterListOwner, PsiTypeParameterListOwner.class);
            } else {
                psiTypeParameterListOwner = psiTypeParameterListOwner.mo6092getContainingClass();
            }
        }
        if (iterableConcat != null) {
            if (iterableConcat == null) {
                $$$reportNull$$$0(95);
            }
            return iterableConcat;
        }
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(94);
        }
        return listEmptyList;
    }

    static /* synthetic */ Iterator lambda$typeParametersIterable$2(PsiTypeParameter[] psiTypeParameterArr) {
        return new Iterator<PsiTypeParameter>(psiTypeParameterArr) { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil.1
            int idx;
            final /* synthetic */ PsiTypeParameter[] val$typeParameters;

            {
                this.val$typeParameters = psiTypeParameterArr;
                this.idx = psiTypeParameterArr.length - 1;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.idx >= 0;
            }

            @Override // java.util.Iterator
            public PsiTypeParameter next() {
                int i = this.idx;
                if (i < 0) {
                    throw new NoSuchElementException();
                }
                PsiTypeParameter[] psiTypeParameterArr2 = this.val$typeParameters;
                this.idx = i - 1;
                return psiTypeParameterArr2[i];
            }
        };
    }

    public static boolean canBeOverridden(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(96);
        }
        PsiClass containingClass = psiMethod.getContainingClass();
        return (containingClass == null || psiMethod.isConstructor() || psiMethod.hasModifierProperty("static") || psiMethod.hasModifierProperty("final") || psiMethod.hasModifierProperty("private") || (containingClass instanceof PsiAnonymousClass) || containingClass.hasModifierProperty("final")) ? false : true;
    }

    public static PsiElement[] mapElements(ResolveResult[] resolveResultArr) {
        if (resolveResultArr == null) {
            $$$reportNull$$$0(97);
        }
        PsiElement[] psiElementArr = new PsiElement[resolveResultArr.length];
        for (int i = 0; i < resolveResultArr.length; i++) {
            psiElementArr[i] = resolveResultArr[i].getElement();
        }
        return psiElementArr;
    }

    public static PsiMember findEnclosingConstructorOrInitializer(PsiElement psiElement) {
        PsiMember psiMember = (PsiMember) PsiTreeUtil.getParentOfType(psiElement, PsiClassInitializer.class, PsiEnumConstantInitializer.class, PsiMethod.class, PsiField.class);
        if (!(psiMember instanceof PsiMethod) || ((PsiMethod) psiMember).isConstructor()) {
            return psiMember;
        }
        return null;
    }

    public static boolean checkName(PsiElement psiElement, String str, PsiElement psiElement2) {
        PsiMetaData metaData;
        if (psiElement == null) {
            $$$reportNull$$$0(99);
        }
        if (str == null) {
            $$$reportNull$$$0(100);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(101);
        }
        if (!(psiElement instanceof PsiMetaOwner) || (metaData = ((PsiMetaOwner) psiElement).getMetaData()) == null) {
            return (psiElement instanceof PsiNamedElement) && str.equals(((PsiNamedElement) psiElement).get_name());
        }
        return str.equals(metaData.getName(psiElement2));
    }

    public static boolean isRawSubstitutor(PsiTypeParameterListOwner psiTypeParameterListOwner, PsiSubstitutor psiSubstitutor) {
        if (psiTypeParameterListOwner == null) {
            $$$reportNull$$$0(102);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(103);
        }
        if (psiSubstitutor == PsiSubstitutor.EMPTY) {
            return false;
        }
        Iterator<PsiTypeParameter> it = typeParametersIterable(psiTypeParameterListOwner).iterator();
        while (it.hasNext()) {
            if (psiSubstitutor.substitute(it.next()) == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLanguageLevel5OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(104);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_1_5);
    }

    public static boolean isLanguageLevel6OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(105);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_1_6);
    }

    public static boolean isLanguageLevel7OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(106);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_1_7);
    }

    public static boolean isLanguageLevel8OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(107);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_1_8);
    }

    public static boolean isLanguageLevel9OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(108);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_1_9);
    }

    public static boolean isLanguageLevel10OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(109);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_10);
    }

    public static boolean isLanguageLevel11OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(110);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_11);
    }

    public static boolean isLanguageLevel14OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(111);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_14);
    }

    public static boolean isLanguageLevel16OrHigher(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(112);
        }
        return getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_16);
    }

    public static LanguageLevel getLanguageLevel(PsiElement psiElement) {
        PsiElement context;
        if (psiElement == null) {
            $$$reportNull$$$0(113);
        }
        if (psiElement instanceof PsiDirectory) {
            LanguageLevel languageLevel = JavaDirectoryService.getInstance().getLanguageLevel((PsiDirectory) psiElement);
            if (languageLevel == null) {
                $$$reportNull$$$0(114);
            }
            return languageLevel;
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile instanceof PsiJavaFile) {
            LanguageLevel languageLevel2 = ((PsiJavaFile) containingFile).getLanguageLevel();
            if (languageLevel2 == null) {
                $$$reportNull$$$0(115);
            }
            return languageLevel2;
        }
        if (containingFile != null && (context = containingFile.getContext()) != null) {
            if (!context.isValid()) {
                throw new PsiInvalidElementAccessException(context, "Invalid context in " + containingFile + " of " + containingFile.getClass());
            }
            return getLanguageLevel(context);
        }
        PsiResolveHelper psiResolveHelper = (PsiResolveHelper) psiElement.getProject().getService(PsiResolveHelper.class);
        LanguageLevel effectiveLanguageLevel = psiResolveHelper != null ? psiResolveHelper.getEffectiveLanguageLevel(getVirtualFile(containingFile)) : LanguageLevel.HIGHEST;
        if (effectiveLanguageLevel == null) {
            $$$reportNull$$$0(116);
        }
        return effectiveLanguageLevel;
    }

    public static LanguageLevel getLanguageLevel(Project project) {
        if (project == null) {
            $$$reportNull$$$0(117);
        }
        LanguageLevelProjectExtension languageLevelProjectExtension = LanguageLevelProjectExtension.getInstance(project);
        LanguageLevel languageLevel = languageLevelProjectExtension != null ? languageLevelProjectExtension.getLanguageLevel() : LanguageLevel.HIGHEST;
        if (languageLevel == null) {
            $$$reportNull$$$0(118);
        }
        return languageLevel;
    }

    public static boolean isInstantiatable(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(119);
        }
        return !psiClass.hasModifierProperty("abstract") && psiClass.hasModifierProperty("public") && hasDefaultConstructor(psiClass);
    }

    public static boolean hasDefaultConstructor(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(120);
        }
        return hasDefaultConstructor(psiClass, false);
    }

    public static boolean hasDefaultConstructor(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(121);
        }
        return hasDefaultConstructor(psiClass, z, false);
    }

    public static boolean hasDefaultConstructor(PsiClass psiClass, boolean z, boolean z2) {
        if (psiClass == null) {
            $$$reportNull$$$0(122);
        }
        PsiMethod[] constructors = psiClass.getConstructors();
        if (constructors.length == 0) {
            return true;
        }
        for (PsiMethod psiMethod : constructors) {
            if ((psiMethod.hasModifierProperty("public") || ((z && psiMethod.hasModifierProperty("protected")) || (z2 && !psiMethod.hasModifierProperty("protected")))) && psiMethod.getParameterList().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static PsiType extractIterableTypeParameter(PsiType psiType, boolean z) {
        PsiType psiTypeSubstituteTypeParameter = substituteTypeParameter(psiType, CommonClassNames.JAVA_LANG_ITERABLE, 0, z);
        return psiTypeSubstituteTypeParameter != null ? psiTypeSubstituteTypeParameter : substituteTypeParameter(psiType, CommonClassNames.JAVA_UTIL_COLLECTION, 0, z);
    }

    public static PsiType substituteTypeParameter(PsiType psiType, String str, int i, boolean z) {
        PsiClass psiClassFindClass;
        if (str == null) {
            $$$reportNull$$$0(123);
        }
        PsiClassType.ClassResolveResult classResolveResultResolveClass = resolveClass(psiType);
        PsiClass element = classResolveResultResolveClass.getElement();
        if (element == null || (psiClassFindClass = JavaPsiFacade.getInstance(element.getProject()).findClass(str, element.getResolveScope())) == null) {
            return null;
        }
        return substituteType(i, z, classResolveResultResolveClass, element, psiClassFindClass);
    }

    public static PsiType substituteTypeParameter(PsiType psiType, PsiClass psiClass, int i, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(124);
        }
        PsiClassType.ClassResolveResult classResolveResultResolveClass = resolveClass(psiType);
        PsiClass element = classResolveResultResolveClass.getElement();
        if (element == null) {
            return null;
        }
        return substituteType(i, z, classResolveResultResolveClass, element, psiClass);
    }

    private static PsiClassType.ClassResolveResult resolveClass(PsiType psiType) {
        return psiType instanceof PsiClassType ? ((PsiClassType) psiType).resolveGenerics() : PsiClassType.ClassResolveResult.EMPTY;
    }

    private static PsiType substituteType(int i, boolean z, PsiClassType.ClassResolveResult classResolveResult, PsiClass psiClass, PsiClass psiClass2) {
        if (!psiClass.isEquivalentTo(psiClass2) && !psiClass.isInheritor(psiClass2, true)) {
            return null;
        }
        PsiTypeParameter[] typeParameters = psiClass2.getTypeParameters();
        if (typeParameters.length <= i) {
            return PsiType.getJavaLangObject(psiClass.getManager(), psiClass.getResolveScope());
        }
        PsiType psiTypeSubstitute = TypeConversionUtil.getSuperClassSubstitutor(psiClass2, psiClass, classResolveResult.getSubstitutor()).substitute(typeParameters[i]);
        return (psiTypeSubstitute == null && z) ? TypeConversionUtil.typeParameterErasure(typeParameters[i]) : psiTypeSubstitute;
    }

    public static void setModifierProperty(PsiModifierListOwner psiModifierListOwner, String str, boolean z) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(125);
        }
        if (str == null) {
            $$$reportNull$$$0(126);
        }
        psiModifierListOwner.mo6095getModifierList().setModifierProperty(str, z);
    }

    public static boolean isElseBlock(PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        }
        PsiElement parent = psiElement.getLightOwner();
        return (parent instanceof PsiIfStatement) && psiElement == ((PsiIfStatement) parent).getElseBranch();
    }

    public static boolean isJavaToken(PsiElement psiElement, IElementType iElementType) {
        return (psiElement instanceof PsiJavaToken) && ((PsiJavaToken) psiElement).getTokenType() == iElementType;
    }

    public static boolean isJavaToken(PsiElement psiElement, TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(127);
        }
        return (psiElement instanceof PsiJavaToken) && tokenSet.contains(((PsiJavaToken) psiElement).getTokenType());
    }

    public static boolean isCatchParameter(PsiElement psiElement) {
        return (psiElement instanceof PsiParameter) && (psiElement.getLightOwner() instanceof PsiCatchSection);
    }

    public static boolean isIgnoredName(String str) {
        return str != null && IGNORED_NAMES.matcher(str).matches();
    }

    public static PsiMethod[] getResourceCloserMethodsForType(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(128);
        }
        PsiClass psiClassResolve = psiClassType.resolve();
        if (psiClassResolve == null) {
            return null;
        }
        Project project = psiClassResolve.getProject();
        PsiClass psiClassFindClass = JavaPsiFacade.getInstance(project).findClass(CommonClassNames.JAVA_LANG_AUTO_CLOSEABLE, ProjectScope.getLibrariesScope(project));
        if (psiClassFindClass == null || JavaClassSupers.getInstance().getSuperClassSubstitutor(psiClassFindClass, psiClassResolve, psiClassType.getResolveScope(), PsiSubstitutor.EMPTY) == null) {
            return null;
        }
        PsiMethod[] psiMethodArrFindMethodsByName = psiClassFindClass.findMethodsByName("close", false);
        if (psiMethodArrFindMethodsByName.length == 1) {
            return psiClassResolve.findMethodsBySignature(psiMethodArrFindMethodsByName[0], true);
        }
        return null;
    }

    public static PsiExpression skipParenthesizedExprDown(PsiExpression psiExpression) {
        while (psiExpression instanceof PsiParenthesizedExpression) {
            psiExpression = ((PsiParenthesizedExpression) psiExpression).getExpression();
        }
        return psiExpression;
    }

    public static PsiElement skipParenthesizedExprUp(PsiElement psiElement) {
        while (psiElement instanceof PsiParenthesizedExpression) {
            psiElement = psiElement.getLightOwner();
        }
        return psiElement;
    }

    public static void ensureValidType(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(129);
        }
        ensureValidType(psiType, (String) null);
    }

    public static void ensureValidType(PsiType psiType, PsiElement psiElement) {
        if (psiType == null) {
            $$$reportNull$$$0(130);
        }
        try {
            ensureValidType(psiType);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            if (psiElement == null) {
                throw th;
            }
            PsiUtilCore.ensureValid(psiElement);
            throw new RuntimeException("Via " + psiElement.getClass() + " #" + psiElement.getLanguage(), th);
        }
    }

    public static void ensureValidType(PsiType psiType, String str) throws Exception {
        if (psiType == null) {
            $$$reportNull$$$0(131);
        }
        if (psiType.isValid()) {
            return;
        }
        TimeoutUtil.sleep(1L);
        if (psiType.isValid()) {
            LOG.error("PsiType resurrected: " + psiType + " of " + psiType.getClass() + " " + str);
            return;
        }
        if (psiType instanceof PsiClassType) {
            try {
                PsiClass psiClassResolve = ((PsiClassType) psiType).resolve();
                if (psiClassResolve != null) {
                    ensureValid(psiClassResolve);
                }
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Exception e2) {
                if (str == null) {
                    throw e2;
                }
                throw new RuntimeException(str, e2);
            }
        }
        throw new AssertionError("Invalid type: " + psiType + " of class " + psiType.getClass() + " " + str);
    }

    public static String getMemberQualifiedName(PsiMember psiMember) {
        String qualifiedName;
        if (psiMember == null) {
            $$$reportNull$$$0(132);
        }
        if (psiMember instanceof PsiClass) {
            return ((PsiClass) psiMember).getQualifiedName();
        }
        PsiClass psiClassMo6092getContainingClass = psiMember.getContainingClass();
        if (psiClassMo6092getContainingClass == null || (qualifiedName = psiClassMo6092getContainingClass.getQualifiedName()) == null) {
            return null;
        }
        return qualifiedName + "." + psiMember.get_name();
    }

    public static boolean isFromDefaultPackage(PsiClass psiClass) {
        return isFromDefaultPackage((PsiElement) psiClass);
    }

    public static boolean isFromDefaultPackage(PsiElement psiElement) {
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile instanceof PsiClassOwner) {
            return StringUtil.isEmpty(((PsiClassOwner) containingFile).getPackageName());
        }
        if (!(containingFile instanceof JavaCodeFragment)) {
            return false;
        }
        PsiElement context = containingFile.getContext();
        if (context instanceof PsiPackage) {
            return StringUtil.isEmpty(((PsiPackage) context).get_name());
        }
        if (context == null || context == containingFile) {
            return false;
        }
        return isFromDefaultPackage(context);
    }

    static boolean checkSameExpression(PsiElement psiElement, PsiExpression psiExpression) {
        return psiElement.equals(skipParenthesizedExprDown(psiExpression));
    }

    public static boolean isCondition(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement2 instanceof PsiIfStatement) {
            return checkSameExpression(psiElement, ((PsiIfStatement) psiElement2).getCondition());
        }
        if (psiElement2 instanceof PsiConditionalLoopStatement) {
            return checkSameExpression(psiElement, ((PsiConditionalLoopStatement) psiElement2).getCondition());
        }
        if (psiElement2 instanceof PsiConditionalExpression) {
            return checkSameExpression(psiElement, ((PsiConditionalExpression) psiElement2).getCondition());
        }
        return false;
    }

    public static PsiReturnStatement[] findReturnStatements(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(133);
        }
        return findReturnStatements(psiMethod.getBody());
    }

    public static PsiReturnStatement[] findReturnStatements(PsiCodeBlock psiCodeBlock) {
        ArrayList arrayList = new ArrayList();
        if (psiCodeBlock != null) {
            addStatements(arrayList, psiCodeBlock, PsiReturnStatement.class, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PsiUtil.lambda$findReturnStatements$4((PsiElement) obj);
                }
            });
        }
        PsiReturnStatement[] psiReturnStatementArr = (PsiReturnStatement[]) arrayList.toArray(PsiReturnStatement.EMPTY_ARRAY);
        if (psiReturnStatementArr == null) {
            $$$reportNull$$$0(134);
        }
        return psiReturnStatementArr;
    }

    private static <T extends PsiElement> void addStatements(List<? super T> list, PsiElement psiElement, Class<? extends T> cls, Predicate<? super PsiElement> predicate) {
        if (list == null) {
            $$$reportNull$$$0(135);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(136);
        }
        if (cls == null) {
            $$$reportNull$$$0(137);
        }
        if (predicate == null) {
            $$$reportNull$$$0(138);
        }
        if (PsiTreeUtil.instanceOf(psiElement, cls)) {
            list.add(psiElement);
            return;
        }
        if ((psiElement instanceof PsiClass) || (psiElement instanceof PsiLambdaExpression) || predicate.test(psiElement)) {
            return;
        }
        for (PsiElement psiElement2 : psiElement.getChildren()) {
            addStatements(list, psiElement2, cls, predicate);
        }
    }

    public static boolean isModuleFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(139);
        }
        return (psiFile instanceof PsiJavaFile) && ((PsiJavaFile) psiFile).getModuleDeclaration() != null;
    }

    public static boolean isPackageEmpty(PsiDirectory[] psiDirectoryArr, String str) {
        if (str == null) {
            $$$reportNull$$$0(140);
        }
        if (psiDirectoryArr == null) {
            $$$reportNull$$$0(141);
        }
        for (PsiDirectory psiDirectory : psiDirectoryArr) {
            for (PsiFile psiFile : psiDirectory.getFiles()) {
                if (psiFile instanceof PsiClassOwner) {
                    PsiClassOwner psiClassOwner = (PsiClassOwner) psiFile;
                    if (str.equals(psiClassOwner.getPackageName()) && psiClassOwner.getClasses().length > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static PsiModifierListOwner preferCompiledElement(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(142);
        }
        PsiElement originalElement = psiModifierListOwner.getOriginalElement();
        if (originalElement instanceof PsiModifierListOwner) {
            psiModifierListOwner = (PsiModifierListOwner) originalElement;
        }
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(143);
        }
        return psiModifierListOwner;
    }

    public static PsiElement addModuleStatement(PsiJavaModule psiJavaModule, String str) {
        if (psiJavaModule == null) {
            $$$reportNull$$$0(144);
        }
        if (str == null) {
            $$$reportNull$$$0(145);
        }
        return addModuleStatement(psiJavaModule, JavaPsiFacade.getInstance(psiJavaModule.getProject()).getParserFacade().createModuleStatementFromText(str, null));
    }

    public static PsiElement addModuleStatement(PsiJavaModule psiJavaModule, PsiStatement psiStatement) {
        if (psiJavaModule == null) {
            $$$reportNull$$$0(146);
        }
        if (psiStatement == null) {
            $$$reportNull$$$0(147);
        }
        PsiElement psiElementFirst = (PsiElement) SyntaxTraverser.psiTraverser().children(psiJavaModule).filter(psiStatement.getClass()).last();
        if (psiElementFirst == null) {
            psiElementFirst = SyntaxTraverser.psiTraverser().children(psiJavaModule).filter(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return PsiUtil.isJavaToken((PsiElement) obj, JavaTokenType.LBRACE);
                }
            }).first();
        }
        if (psiElementFirst == null) {
            throw new IllegalStateException("No anchor in " + Arrays.toString(psiJavaModule.getChildren()));
        }
        return psiJavaModule.addAfter(psiStatement, psiElementFirst);
    }

    public static boolean isArrayClass(PsiElement psiElement) {
        return psiElement != null && psiElement.getManager().areElementsEquivalent(psiElement, JavaPsiFacade.getElementFactory(psiElement.getProject()).getArrayClass(getLanguageLevel(psiElement)));
    }

    public static boolean isJvmLocalVariable(PsiElement psiElement) {
        return (psiElement instanceof PsiLocalVariable) || (psiElement instanceof PsiParameter);
    }
}
