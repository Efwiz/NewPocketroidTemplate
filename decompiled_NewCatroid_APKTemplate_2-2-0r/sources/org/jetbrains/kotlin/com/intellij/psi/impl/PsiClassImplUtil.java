package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.swing.Icon;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.LastComputedIconCache;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.ThreadLocalTypes;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiExtensionMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PackageScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiSuperMethodUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBTreeTraverser;
import org.jetbrains.kotlin.one.util.streamex.StreamEx;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiClassImplUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiClassImplUtil.class);
    private static final Function<ClassIconRequest, Icon> FULL_ICON_EVALUATOR = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return PsiClassImplUtil.lambda$static$3((PsiClassImplUtil.ClassIconRequest) obj);
        }
    };

    /* JADX WARN: Code duplicated, block: B:108:0x0191  */
    /* JADX WARN: Code duplicated, block: B:110:0x0199  */
    /* JADX WARN: Code duplicated, block: B:111:0x019c  */
    /* JADX WARN: Code duplicated, block: B:22:0x0045 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:42:0x0072 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 3 && i != 5 && i != 16 && i != 17 && i != 25 && i != 26 && i != 105 && i != 106 && i != 115 && i != 116 && i != 129 && i != 130) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        default:
                                            switch (i) {
                                                case 111:
                                                case 112:
                                                case 113:
                                                    break;
                                                default:
                                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                                    break;
                                            }
                                        case 95:
                                        case 96:
                                        case 97:
                                        case 98:
                                            str = "@NotNull method %s.%s must not return null";
                                            break;
                                    }
                                case 88:
                                case 89:
                                case 90:
                                case 91:
                                case 92:
                                case 93:
                                    str = "@NotNull method %s.%s must not return null";
                                    break;
                            }
                        case 38:
                        case 39:
                        case 40:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 5:
                case 8:
                case 13:
                case 31:
                case 44:
                case 86:
                case 101:
                case 108:
                case 118:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 127:
                case 138:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 3 && i != 5 && i != 16 && i != 17 && i != 25 && i != 26 && i != 105 && i != 106 && i != 115 && i != 116 && i != 129 && i != 130) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        default:
                                            switch (i) {
                                                case 111:
                                                case 112:
                                                case 113:
                                                    break;
                                                default:
                                                    i2 = 3;
                                                    break;
                                            }
                                        case 95:
                                        case 96:
                                        case 97:
                                        case 98:
                                            i2 = 2;
                                            break;
                                    }
                                case 88:
                                case 89:
                                case 90:
                                case 91:
                                case 92:
                                case 93:
                                    i2 = 2;
                                    break;
                            }
                        case 38:
                        case 39:
                        case 40:
                            i2 = 2;
                            break;
                    }
                case 5:
                case 8:
                case 13:
                case 31:
                case 44:
                case 86:
                case 101:
                case 108:
                case 118:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 127:
                case 138:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 13:
            case 16:
            case 17:
            case 25:
            case 26:
            case 31:
            case 38:
            case 39:
            case 40:
            case 44:
            case 86:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 97:
            case 98:
            case 101:
            case 105:
            case 106:
            case 108:
            case 111:
            case 112:
            case 113:
            case 115:
            case 116:
            case 118:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 129:
            case 130:
            case 138:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiClassImplUtil";
                break;
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
            case 14:
            case 18:
            case 23:
            case 27:
            case 29:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 46:
            case 51:
            case 57:
            case 69:
            case 73:
            case 80:
            case 136:
            case 137:
            default:
                objArr[0] = "aClass";
                break;
            case 10:
            case 12:
            case 15:
                objArr[0] = "patternMethod";
                break;
            case 19:
            case 83:
            case 84:
            case 85:
            case 87:
            case 94:
            case 99:
            case 102:
            case 107:
            case 109:
            case 114:
            case 117:
            case 119:
            case 128:
            case 131:
                objArr[0] = "psiClass";
                break;
            case 20:
            case 47:
            case 52:
            case 58:
            case 74:
                objArr[0] = "processor";
                break;
            case 21:
            case 48:
            case 53:
            case 59:
            case 75:
                objArr[0] = "state";
                break;
            case 22:
                objArr[0] = "members";
                break;
            case 24:
            case 28:
            case 30:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 34:
                objArr[0] = "scope";
                break;
            case 41:
                objArr[0] = "method";
                break;
            case 42:
            case 64:
            case 81:
            case 100:
            case 103:
                objArr[0] = "factory";
                break;
            case 43:
                objArr[0] = "text";
                break;
            case 45:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 49:
            case 54:
            case 60:
            case 76:
                objArr[0] = "place";
                break;
            case 50:
            case 55:
            case 62:
            case 72:
            case 77:
            case 82:
                objArr[0] = "languageLevel";
                break;
            case 56:
            case 63:
            case 78:
            case 79:
                objArr[0] = "resolveScope";
                break;
            case 61:
            case 66:
            case 70:
                objArr[0] = "substitutor";
                break;
            case 65:
                objArr[0] = "candidateMethod";
                break;
            case 67:
                objArr[0] = "candidateClass";
                break;
            case 68:
                objArr[0] = "candidateSubstitutor";
                break;
            case 71:
                objArr[0] = "elementFactory";
                break;
            case 104:
                objArr[0] = "typeParameter";
                break;
            case 110:
                objArr[0] = "listOfTypes";
                break;
            case 132:
            case 134:
                objArr[0] = "baseClass";
                break;
            case 133:
            case 135:
            case 141:
                objArr[0] = "manager";
                break;
            case 139:
                objArr[0] = "field";
                break;
            case 140:
                objArr[0] = "method1";
                break;
            case 142:
                objArr[0] = "type1";
                break;
            case 143:
                objArr[0] = "type2";
                break;
        }
        if (i == 1) {
            objArr[1] = "getAllFields";
        } else if (i == 3) {
            objArr[1] = "getAllMethods";
        } else if (i != 5) {
            if (i != 16 && i != 17) {
                if (i != 25 && i != 26) {
                    if (i != 105 && i != 106) {
                        if (i != 115 && i != 116) {
                            if (i != 129 && i != 130) {
                                switch (i) {
                                    case 5:
                                        objArr[1] = "getAllInnerClasses";
                                        break;
                                    case 8:
                                        objArr[1] = "findMethodsByName";
                                        break;
                                    case 13:
                                        objArr[1] = "findMethodsBySignature";
                                        break;
                                    case 31:
                                        objArr[1] = "getAllByMap";
                                        break;
                                    case 44:
                                        objArr[1] = "createSignatureFromText";
                                        break;
                                    case 86:
                                        objArr[1] = "getSupers";
                                        break;
                                    case 101:
                                        objArr[1] = "getAnnotationSuperType";
                                        break;
                                    case 108:
                                        objArr[1] = "getInterfaces";
                                        break;
                                    case 118:
                                        objArr[1] = "withSubstitutors";
                                        break;
                                    case 120:
                                    case 121:
                                    case 122:
                                    case 123:
                                    case 124:
                                    case 125:
                                    case 126:
                                    case 127:
                                        objArr[1] = "getExtendsListTypes";
                                        break;
                                    case 138:
                                        objArr[1] = "getOriginalFile";
                                        break;
                                    default:
                                        switch (i) {
                                            case 38:
                                            case 39:
                                            case 40:
                                                objArr[1] = "getClassUseScope";
                                                break;
                                            default:
                                                switch (i) {
                                                    case 88:
                                                    case 89:
                                                    case 90:
                                                    case 91:
                                                    case 92:
                                                    case 93:
                                                        objArr[1] = "getSupersInner";
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case 95:
                                                            case 96:
                                                            case 97:
                                                            case 98:
                                                                objArr[1] = "getSuperTypes";
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case 111:
                                                                    case 112:
                                                                    case 113:
                                                                        objArr[1] = "resolveClassReferenceList";
                                                                        break;
                                                                    default:
                                                                        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiClassImplUtil";
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                            } else {
                                objArr[1] = "getImplementsListTypes";
                            }
                        } else {
                            objArr[1] = "findMethodsAndTheirSubstitutorsByName";
                        }
                    } else {
                        objArr[1] = "getInterfaces";
                    }
                } else {
                    objArr[1] = "findByMap";
                }
            } else {
                objArr[1] = "findMethodsBySignature";
            }
        } else {
            objArr[1] = "getAllInnerClasses";
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 13:
            case 16:
            case 17:
            case 25:
            case 26:
            case 31:
            case 38:
            case 39:
            case 40:
            case 44:
            case 86:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 97:
            case 98:
            case 101:
            case 105:
            case 106:
            case 108:
            case 111:
            case 112:
            case 113:
            case 115:
            case 116:
            case 118:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 129:
            case 130:
            case 138:
                break;
            case 2:
                objArr[2] = "getAllMethods";
                break;
            case 4:
                objArr[2] = "getAllInnerClasses";
                break;
            case 6:
                objArr[2] = "findFieldByName";
                break;
            case 7:
                objArr[2] = "findMethodsByName";
                break;
            case 9:
            case 10:
                objArr[2] = "findMethodBySignature";
                break;
            case 11:
            case 12:
            case 14:
            case 15:
                objArr[2] = "findMethodsBySignature";
                break;
            case 18:
                objArr[2] = "findInnerByName";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "processAllMembersWithoutSubstitutors";
                break;
            case 22:
                objArr[2] = "processMembers";
                break;
            case 23:
            case 24:
                objArr[2] = "findByMap";
                break;
            case 27:
            case 28:
                objArr[2] = "getAllWithSubstitutorsByMap";
                break;
            case 29:
            case 30:
                objArr[2] = "getAllByMap";
                break;
            case 32:
            case 33:
            case 34:
                objArr[2] = "getMap";
                break;
            case 35:
            case 36:
                objArr[2] = "getClassIcon";
                break;
            case 37:
                objArr[2] = "getClassUseScope";
                break;
            case 41:
                objArr[2] = "isMainOrPremainMethod";
                break;
            case 42:
            case 43:
                objArr[2] = "createSignatureFromText";
                break;
            case 45:
                objArr[2] = "skipInvalid";
                break;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
                objArr[2] = "processDeclarationsInClass";
                break;
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
                objArr[2] = "processCachedMembersByName";
                break;
            case 64:
            case 65:
            case 66:
                objArr[2] = "checkRaw";
                break;
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
                objArr[2] = "obtainFinalSubstitutor";
                break;
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
                objArr[2] = "processClassMembersWithAllNames";
                break;
            case 79:
                objArr[2] = "correctType";
                break;
            case 80:
            case 81:
            case 82:
                objArr[2] = "processSuperTypes";
                break;
            case 83:
                objArr[2] = "getSuperClass";
                break;
            case 84:
                objArr[2] = "findSpecialSuperClass";
                break;
            case 85:
                objArr[2] = "getSupers";
                break;
            case 87:
                objArr[2] = "getSupersInner";
                break;
            case 94:
                objArr[2] = "getSuperTypes";
                break;
            case 99:
            case 100:
                objArr[2] = "getAnnotationSuperType";
                break;
            case 102:
            case 103:
                objArr[2] = "getEnumSuperType";
                break;
            case 104:
            case 107:
                objArr[2] = "getInterfaces";
                break;
            case 109:
            case 110:
                objArr[2] = "resolveClassReferenceList";
                break;
            case 114:
                objArr[2] = "findMethodsAndTheirSubstitutorsByName";
                break;
            case 117:
                objArr[2] = "withSubstitutors";
                break;
            case 119:
                objArr[2] = "getExtendsListTypes";
                break;
            case 128:
                objArr[2] = "getImplementsListTypes";
                break;
            case 131:
            case 132:
            case 133:
                objArr[2] = "isInExtendsList";
                break;
            case 134:
            case 135:
                objArr[2] = "isInReferenceList";
                break;
            case 136:
                objArr[2] = "isClassEquivalentTo";
                break;
            case 137:
                objArr[2] = "getOriginalFile";
                break;
            case 139:
                objArr[2] = "isFieldEquivalentTo";
                break;
            case 140:
                objArr[2] = "isMethodEquivalentTo";
                break;
            case 141:
            case 142:
            case 143:
                objArr[2] = "compareParamTypes";
                break;
            default:
                objArr[2] = "getAllFields";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5 && i != 16 && i != 17 && i != 25 && i != 26 && i != 105 && i != 106 && i != 115 && i != 116 && i != 129 && i != 130) {
            switch (i) {
                case 5:
                case 8:
                case 13:
                case 31:
                case 44:
                case 86:
                case 101:
                case 108:
                case 118:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 127:
                case 138:
                    break;
                default:
                    switch (i) {
                        case 38:
                        case 39:
                        case 40:
                            break;
                        default:
                            switch (i) {
                                case 88:
                                case 89:
                                case 90:
                                case 91:
                                case 92:
                                case 93:
                                    break;
                                default:
                                    switch (i) {
                                        case 95:
                                        case 96:
                                        case 97:
                                        case 98:
                                            break;
                                        default:
                                            switch (i) {
                                                case 111:
                                                case 112:
                                                case 113:
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
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private PsiClassImplUtil() {
    }

    public static PsiField[] getAllFields(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        PsiField[] psiFieldArr = (PsiField[]) getAllByMap(psiClass, MemberType.FIELD).toArray(PsiField.EMPTY_ARRAY);
        if (psiFieldArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiFieldArr;
    }

    public static PsiMethod[] getAllMethods(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        PsiMethod[] psiMethodArr = (PsiMethod[]) getAllByMap(psiClass, MemberType.METHOD).toArray(PsiMethod.EMPTY_ARRAY);
        if (psiMethodArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiMethodArr;
    }

    public static PsiClass[] getAllInnerClasses(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        PsiClass[] psiClassArr = (PsiClass[]) getAllByMap(psiClass, MemberType.CLASS).toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiClassArr;
    }

    public static PsiField findFieldByName(PsiClass psiClass, String str, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        List<PsiMember> listFindByMap = findByMap(psiClass, str, z, MemberType.FIELD);
        if (listFindByMap.isEmpty()) {
            return null;
        }
        return (PsiField) listFindByMap.get(0);
    }

    public static PsiMethod[] findMethodsByName(PsiClass psiClass, String str, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(7);
        }
        PsiMethod[] psiMethodArr = (PsiMethod[]) findByMap(psiClass, str, z, MemberType.METHOD).toArray(PsiMethod.EMPTY_ARRAY);
        if (psiMethodArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiMethodArr;
    }

    public static PsiMethod findMethodBySignature(PsiClass psiClass, PsiMethod psiMethod, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(9);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(10);
        }
        List<PsiMethod> listFindMethodsBySignature = findMethodsBySignature(psiClass, psiMethod, z, true);
        if (listFindMethodsBySignature.isEmpty()) {
            return null;
        }
        return listFindMethodsBySignature.get(0);
    }

    public static PsiMethod[] findMethodsBySignature(PsiClass psiClass, PsiMethod psiMethod, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(11);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(12);
        }
        PsiMethod[] psiMethodArr = (PsiMethod[]) findMethodsBySignature(psiClass, psiMethod, z, false).toArray(PsiMethod.EMPTY_ARRAY);
        if (psiMethodArr == null) {
            $$$reportNull$$$0(13);
        }
        return psiMethodArr;
    }

    private static List<PsiMethod> findMethodsBySignature(PsiClass psiClass, PsiMethod psiMethod, boolean z, boolean z2) {
        PsiSubstitutor superClassSubstitutor;
        if (psiClass == null) {
            $$$reportNull$$$0(14);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(15);
        }
        PsiMethod[] psiMethodArrFindMethodsByName = psiClass.findMethodsByName(psiMethod.getMyName(), z);
        if (psiMethodArrFindMethodsByName.length == 0) {
            List<PsiMethod> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(16);
            }
            return listEmptyList;
        }
        SmartList smartList = new SmartList();
        MethodSignature signature = psiMethod.getSignature(PsiSubstitutor.EMPTY);
        for (PsiMethod psiMethod2 : psiMethodArrFindMethodsByName) {
            PsiClass containingClass = psiMethod2.mo6092getContainingClass();
            if (z && !psiClass.equals(containingClass) && containingClass != null) {
                superClassSubstitutor = TypeConversionUtil.getSuperClassSubstitutor(containingClass, psiClass, PsiSubstitutor.EMPTY);
            } else {
                superClassSubstitutor = PsiSubstitutor.EMPTY;
            }
            if (psiMethod2.getSignature(superClassSubstitutor).equals(signature)) {
                smartList.add(psiMethod2);
                if (z2) {
                    break;
                }
            }
        }
        return smartList;
    }

    public static PsiClass findInnerByName(PsiClass psiClass, String str, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(18);
        }
        List<PsiMember> listFindByMap = findByMap(psiClass, str, z, MemberType.CLASS);
        if (listFindByMap.isEmpty()) {
            return null;
        }
        return (PsiClass) listFindByMap.get(0);
    }

    public static boolean processAllMembersWithoutSubstitutors(PsiClass psiClass, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState) {
        if (psiClass == null) {
            $$$reportNull$$$0(19);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(20);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(21);
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        String name2 = nameHint == null ? null : nameHint.getName(resolveState);
        if ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.METHOD)) && !processMembers(resolveState, psiScopeProcessor, getMap(psiClass).getAllMembers(MemberType.METHOD, name2))) {
            return false;
        }
        if ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.FIELD)) && !processMembers(resolveState, psiScopeProcessor, getMap(psiClass).getAllMembers(MemberType.FIELD, name2))) {
            return false;
        }
        return !(elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) || processMembers(resolveState, psiScopeProcessor, getMap(psiClass).getAllMembers(MemberType.CLASS, name2));
    }

    private static boolean processMembers(ResolveState resolveState, PsiScopeProcessor psiScopeProcessor, PsiMember[] psiMemberArr) {
        if (psiMemberArr == null) {
            $$$reportNull$$$0(22);
        }
        for (PsiMember psiMember : psiMemberArr) {
            if (!psiScopeProcessor.execute(psiMember, resolveState)) {
                return false;
            }
        }
        return true;
    }

    private static List<PsiMember> findByMap(PsiClass psiClass, final String str, boolean z, MemberType memberType) {
        List<PsiMember> listFilter;
        if (psiClass == null) {
            $$$reportNull$$$0(23);
        }
        if (memberType == null) {
            $$$reportNull$$$0(24);
        }
        if (str == null) {
            List<PsiMember> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(25);
            }
            return listEmptyList;
        }
        if (z) {
            listFilter = getMap(psiClass).calcMembersByName(memberType, str, null, null);
        } else {
            listFilter = ContainerUtil.filter(memberType.getMembers(psiClass), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return str.equals(((PsiMember) obj).getMyName());
                }
            });
        }
        if (listFilter == null) {
            $$$reportNull$$$0(26);
        }
        return listFilter;
    }

    public static <T extends PsiMember> List<Pair<T, PsiSubstitutor>> getAllWithSubstitutorsByMap(PsiClass psiClass, MemberType memberType) {
        if (psiClass == null) {
            $$$reportNull$$$0(27);
        }
        if (memberType == null) {
            $$$reportNull$$$0(28);
        }
        return withSubstitutors(psiClass, getMap(psiClass).getAllMembers(memberType, null));
    }

    private static <T extends PsiMember> List<T> getAllByMap(PsiClass psiClass, MemberType memberType) {
        if (psiClass == null) {
            $$$reportNull$$$0(29);
        }
        if (memberType == null) {
            $$$reportNull$$$0(30);
        }
        List<T> listAsList = Arrays.asList(getMap(psiClass).getAllMembers(memberType, null));
        if (listAsList == null) {
            $$$reportNull$$$0(31);
        }
        return listAsList;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$impl$PsiClassImplUtil$MemberType;

        static {
            int[] iArr = new int[MemberType.values().length];
            $SwitchMap$com$intellij$psi$impl$PsiClassImplUtil$MemberType = iArr;
            try {
                iArr[MemberType.METHOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiClassImplUtil$MemberType[MemberType.CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum MemberType {
        CLASS,
        FIELD,
        METHOD;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
            if (i == 1 || i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiClassImplUtil$MemberType";
            } else {
                objArr[0] = "aClass";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[1] = "getMembers";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiClassImplUtil$MemberType";
            }
            if (i != 1 && i != 2 && i != 3) {
                objArr[2] = "getMembers";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        PsiMember[] getMembers(PsiClass psiClass) {
            if (psiClass == null) {
                $$$reportNull$$$0(0);
            }
            int i = AnonymousClass2.$SwitchMap$com$intellij$psi$impl$PsiClassImplUtil$MemberType[ordinal()];
            if (i == 1) {
                PsiMethod[] methods = psiClass.getMethods();
                if (methods == null) {
                    $$$reportNull$$$0(1);
                }
                return methods;
            }
            if (i == 2) {
                PsiClass[] innerClasses = psiClass.getInnerClasses();
                if (innerClasses == null) {
                    $$$reportNull$$$0(2);
                }
                return innerClasses;
            }
            PsiField[] fields = psiClass.getFields();
            if (fields == null) {
                $$$reportNull$$$0(3);
            }
            return fields;
        }
    }

    private static MemberCache getMap(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(32);
        }
        return getMap(psiClass, psiClass.getResolveScope());
    }

    private static MemberCache getMap(PsiClass psiClass, GlobalSearchScope globalSearchScope) {
        if (psiClass == null) {
            $$$reportNull$$$0(33);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(34);
        }
        return (MemberCache) ((ConcurrentMap) CachedValuesManager.getProjectPsiDependentCache(psiClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ConcurrentFactoryMap.createMap(new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$$ExternalSyntheticLambda6
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj2) {
                        return PsiClassImplUtil.lambda$getMap$1(psiClass, (GlobalSearchScope) obj2);
                    }
                });
            }
        })).get(globalSearchScope);
    }

    static /* synthetic */ MemberCache lambda$getMap$1(PsiClass psiClass, GlobalSearchScope globalSearchScope) {
        return new MemberCache(psiClass, globalSearchScope);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ClassIconRequest {
        private final int flags;
        private final PsiClass psiClass;
        private final Icon symbolIcon;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "psiClass", "org/jetbrains/kotlin/com/intellij/psi/impl/PsiClassImplUtil$ClassIconRequest", "<init>"));
        }

        private ClassIconRequest(PsiClass psiClass, int i, Icon icon) {
            if (psiClass == null) {
                $$$reportNull$$$0(0);
            }
            this.psiClass = psiClass;
            this.flags = i;
            this.symbolIcon = icon;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClassIconRequest)) {
                return false;
            }
            ClassIconRequest classIconRequest = (ClassIconRequest) obj;
            return this.flags == classIconRequest.flags && this.psiClass.equals(classIconRequest.psiClass);
        }

        public int hashCode() {
            return (this.psiClass.hashCode() * 31) + this.flags;
        }
    }

    static /* synthetic */ Icon lambda$static$3(ClassIconRequest classIconRequest) {
        if (!classIconRequest.psiClass.isValid() || classIconRequest.psiClass.getProject().isDisposed()) {
            return null;
        }
        Icon iconAddVisibilityIcon = ElementPresentationUtil.addVisibilityIcon(classIconRequest.psiClass, classIconRequest.flags, IconManager.getInstance().createLayeredIcon(classIconRequest.psiClass, classIconRequest.symbolIcon != null ? classIconRequest.symbolIcon : ElementPresentationUtil.getClassIconOfKind(classIconRequest.psiClass, ElementPresentationUtil.getClassKind(classIconRequest.psiClass)), ElementPresentationUtil.getFlags(classIconRequest.psiClass, BitUtil.isSet(classIconRequest.flags, 2) && !classIconRequest.psiClass.isWritable())));
        LastComputedIconCache.put(classIconRequest.psiClass, iconAddVisibilityIcon, classIconRequest.flags);
        return iconAddVisibilityIcon;
    }

    public static Icon getClassIcon(int i, PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(35);
        }
        return getClassIcon(i, psiClass, null);
    }

    public static Icon getClassIcon(int i, PsiClass psiClass, Icon icon) {
        if (psiClass == null) {
            $$$reportNull$$$0(36);
        }
        Icon iconAddVisibilityIcon = LastComputedIconCache.get(psiClass, i);
        if (iconAddVisibilityIcon == null) {
            if (icon == null) {
                icon = ElementPresentationUtil.getClassIconOfKind(psiClass, ElementPresentationUtil.getBasicClassKind(psiClass));
            }
            iconAddVisibilityIcon = ElementPresentationUtil.addVisibilityIcon(psiClass, i, IconManager.getInstance().createLayeredIcon(psiClass, icon, 0));
        }
        return IconManager.getInstance().createDeferredIcon(iconAddVisibilityIcon, new ClassIconRequest(psiClass, i, icon), FULL_ICON_EVALUATOR);
    }

    public static SearchScope getClassUseScope(PsiClass psiClass) {
        PsiDirectory containingDirectory;
        if (psiClass == null) {
            $$$reportNull$$$0(37);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            return new LocalSearchScope(psiClass);
        }
        SearchScope elementUseScope = ResolveScopeManager.getElementUseScope(psiClass);
        PsiFile containingFile = psiClass.getContainingFile();
        if (PsiImplUtil.isInServerPage(containingFile)) {
            if (elementUseScope == null) {
                $$$reportNull$$$0(38);
            }
            return elementUseScope;
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
        if (psiClass.hasModifierProperty("public") || psiClass.hasModifierProperty("protected")) {
            if (psiClassMo6092getContainingClass != null) {
                elementUseScope = psiClassMo6092getContainingClass.getUseScope();
            }
            if (elementUseScope == null) {
                $$$reportNull$$$0(39);
            }
            return elementUseScope;
        }
        if (psiClass.hasModifierProperty("private") || (psiClass instanceof PsiTypeParameter)) {
            PsiElement topLevelClass = PsiUtil.getTopLevelClass(psiClass);
            if (topLevelClass == null) {
                topLevelClass = psiClass.getContainingFile();
            }
            return new LocalSearchScope(topLevelClass);
        }
        PsiPackage psiPackageFindPackage = containingFile instanceof PsiJavaFile ? JavaPsiFacade.getInstance(psiClass.getProject()).findPackage(((PsiJavaFile) containingFile).getPackageName()) : null;
        if (psiPackageFindPackage == null && (containingDirectory = containingFile.getContainingDirectory()) != null) {
            psiPackageFindPackage = JavaDirectoryService.getInstance().getPackage(containingDirectory);
        }
        if (psiPackageFindPackage != null) {
            SearchScope searchScopeIntersectWith = PackageScope.packageScope(psiPackageFindPackage, false).intersectWith(elementUseScope);
            if (searchScopeIntersectWith == null) {
                $$$reportNull$$$0(40);
            }
            return searchScopeIntersectWith;
        }
        return new LocalSearchScope(containingFile);
    }

    public static boolean isMainOrPremainMethod(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(41);
        }
        String name2 = psiMethod.getMyName();
        if ((!"main".equals(name2) && !"premain".equals(name2) && !"agentmain".equals(name2)) || !PsiType.VOID.equals(psiMethod.getReturnType())) {
            return false;
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiMethod.getProject());
        MethodSignature signature = psiMethod.getSignature(PsiSubstitutor.EMPTY);
        try {
            return MethodSignatureUtil.areSignaturesEqual(signature, createSignatureFromText(elementFactory, "void main(String[] args);")) || MethodSignatureUtil.areSignaturesEqual(signature, createSignatureFromText(elementFactory, "void premain(String args, java.lang.instrument.Instrumentation i);")) || MethodSignatureUtil.areSignaturesEqual(signature, createSignatureFromText(elementFactory, "void agentmain(String args, java.lang.instrument.Instrumentation i);"));
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
        }
    }

    private static MethodSignature createSignatureFromText(PsiElementFactory psiElementFactory, String str) {
        if (psiElementFactory == null) {
            $$$reportNull$$$0(42);
        }
        if (str == null) {
            $$$reportNull$$$0(43);
        }
        MethodSignature signature = psiElementFactory.createMethodFromText(str, null).getSignature(PsiSubstitutor.EMPTY);
        if (signature == null) {
            $$$reportNull$$$0(44);
        }
        return signature;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MemberCache {
        private final ConcurrentMap<MemberType, PsiMember[]> myAllMembers = ConcurrentFactoryMap.createMap(new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$MemberCache$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6552x9c5c902e((PsiClassImplUtil.MemberType) obj);
            }
        });
        private final List<PsiClass> myAllSupers;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "name";
            } else if (i != 2) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiClassImplUtil$MemberCache";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiClassImplUtil$MemberCache";
            } else {
                objArr[1] = "calcMembersByName";
            }
            if (i != 2) {
                if (i != 3) {
                    objArr[2] = "calcMembersByName";
                } else {
                    objArr[2] = "getAllMembers";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        MemberCache(PsiClass psiClass, final GlobalSearchScope globalSearchScope) {
            this.myAllSupers = JBTreeTraverser.from(new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$MemberCache$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return ContainerUtil.mapNotNull(((PsiClass) obj).getSupers(), new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$MemberCache$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.util.Function
                        public final Object fun(Object obj2) {
                            return PsiSuperMethodUtil.correctClassByScope((PsiClass) obj2, globalSearchScope);
                        }
                    });
                }
            }).unique().withRoot(psiClass).toList();
        }

        static /* synthetic */ boolean lambda$new$2(PsiMember psiMember) {
            return !PsiClassImplUtil.skipInvalid(psiMember);
        }

        /* JADX INFO: renamed from: lambda$new$3$org-jetbrains-kotlin-com-intellij-psi-impl-PsiClassImplUtil$MemberCache, reason: not valid java name */
        /* synthetic */ PsiMember[] m6552x9c5c902e(final MemberType memberType) {
            StreamEx streamExOf = StreamEx.of((Collection) this.myAllSupers);
            Objects.requireNonNull(memberType);
            return (PsiMember[]) ((StreamEx) streamExOf.flatArray(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$MemberCache$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return memberType.getMembers((PsiClass) obj);
                }
            }).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$MemberCache$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PsiClassImplUtil.MemberCache.lambda$new$2((PsiMember) obj);
                }
            })).toArray(PsiMember.EMPTY_ARRAY);
        }

        List<PsiMember> calcMembersByName(MemberType memberType, String str, PsiClass psiClass, PsiElement psiElement) {
            PsiMember psiMemberFindFieldByName;
            if (memberType == null) {
                $$$reportNull$$$0(0);
            }
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            ArrayList arrayList = null;
            for (PsiClass psiClass2 : this.myAllSupers) {
                if (memberType == MemberType.METHOD) {
                    PsiMethod[] psiMethodArrFindMethodsByName = psiClass2.findMethodsByName(str, false);
                    if (psiMethodArrFindMethodsByName.length > 0) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        Collections.addAll(arrayList, psiMethodArrFindMethodsByName);
                    }
                } else {
                    if (memberType == MemberType.CLASS) {
                        psiMemberFindFieldByName = psiClass2.findInnerClassByName(str, false);
                    } else {
                        psiMemberFindFieldByName = psiClass2.findFieldByName(str, false);
                    }
                    if (psiMemberFindFieldByName != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(psiMemberFindFieldByName);
                    }
                }
            }
            if (memberType == MemberType.METHOD && psiClass != null && psiElement != null) {
                List<PsiExtensionMethod> listCollectExtensionMethods = PsiAugmentProvider.collectExtensionMethods(psiClass, str, psiElement);
                if (!listCollectExtensionMethods.isEmpty()) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.addAll(listCollectExtensionMethods);
                }
            }
            List<PsiMember> listEmptyList = arrayList == null ? Collections.emptyList() : ContainerUtil.filter(arrayList, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$MemberCache$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return PsiClassImplUtil.MemberCache.lambda$calcMembersByName$4((PsiMember) obj);
                }
            });
            if (listEmptyList == null) {
                $$$reportNull$$$0(2);
            }
            return listEmptyList;
        }

        static /* synthetic */ boolean lambda$calcMembersByName$4(PsiMember psiMember) {
            return !PsiClassImplUtil.skipInvalid(psiMember);
        }

        PsiMember[] getAllMembers(MemberType memberType, String str) {
            if (memberType == null) {
                $$$reportNull$$$0(3);
            }
            return (PsiMember[]) (str == null ? this.myAllMembers.get(memberType) : calcMembersByName(memberType, str, null, null).toArray(PsiMember.EMPTY_ARRAY));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean skipInvalid(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(45);
        }
        try {
            PsiUtilCore.ensureValid(psiElement);
            return false;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            LOG.error(th);
            return true;
        }
    }

    public static boolean processDeclarationsInClass(PsiClass psiClass, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, Set<PsiClass> set, PsiElement psiElement, PsiElement psiElement2, LanguageLevel languageLevel, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(46);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(47);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(48);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(49);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(50);
        }
        return processDeclarationsInClass(psiClass, psiScopeProcessor, resolveState, set, psiElement, psiElement2, languageLevel, z, psiElement2.getResolveScope());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean processDeclarationsInClass(PsiClass psiClass, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, Set<PsiClass> set, PsiElement psiElement, PsiElement psiElement2, LanguageLevel languageLevel, boolean z, GlobalSearchScope globalSearchScope) {
        if (psiClass == null) {
            $$$reportNull$$$0(51);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(52);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(53);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(54);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(55);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(56);
        }
        boolean z2 = true;
        if ((psiElement instanceof PsiTypeParameterList) || ((psiElement instanceof PsiModifierList) && psiClass.mo6095getModifierList() == psiElement)) {
            return true;
        }
        if (set != null && set.contains(psiClass)) {
            return true;
        }
        PsiSubstitutor psiSubstitutor = (PsiSubstitutor) resolveState.get(PsiSubstitutor.KEY);
        if (!z && !PsiUtil.isRawSubstitutor(psiClass, psiSubstitutor)) {
            z2 = false;
        }
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        String name2 = nameHint == null ? null : nameHint.getName(resolveState);
        if (name2 != null) {
            return processCachedMembersByName(psiClass, psiScopeProcessor, resolveState, set, psiElement, psiElement2, z2, psiSubstitutor, name2, languageLevel, globalSearchScope);
        }
        return processClassMembersWithAllNames(psiClass, psiScopeProcessor, resolveState, set, psiElement, psiElement2, z2, languageLevel, globalSearchScope);
    }

    private static boolean processCachedMembersByName(PsiClass psiClass, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, Set<? super PsiClass> set, PsiElement psiElement, PsiElement psiElement2, boolean z, PsiSubstitutor psiSubstitutor, String str, LanguageLevel languageLevel, GlobalSearchScope globalSearchScope) {
        Iterator<PsiMember> it;
        boolean z2;
        Object objMo6092getContainingClass;
        if (psiClass == null) {
            $$$reportNull$$$0(57);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(58);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(59);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(60);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(61);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(62);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(63);
        }
        Function<PsiMember, PsiSubstitutor> function = new Function<PsiMember, PsiSubstitutor>(globalSearchScope, psiSubstitutor, z, languageLevel) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil.1
            final PsiElementFactory factory;
            final ScopedClassHierarchy hierarchy;
            final /* synthetic */ boolean val$isRaw;
            final /* synthetic */ LanguageLevel val$languageLevel;
            final /* synthetic */ GlobalSearchScope val$resolveScope;
            final /* synthetic */ PsiSubstitutor val$substitutor;

            {
                this.val$resolveScope = globalSearchScope;
                this.val$substitutor = psiSubstitutor;
                this.val$isRaw = z;
                this.val$languageLevel = languageLevel;
                this.hierarchy = ScopedClassHierarchy.getHierarchy(this.val$aClass, globalSearchScope);
                this.factory = JavaPsiFacade.getElementFactory(this.val$aClass.getProject());
            }

            @Override // java.util.function.Function
            public PsiSubstitutor apply(PsiMember psiMember) {
                PsiSubstitutor psiSubstitutorObtainSubstitutor = psiMember.hasModifierProperty("static") ? this.val$substitutor : obtainSubstitutor(psiMember);
                return psiMember instanceof PsiMethod ? PsiClassImplUtil.checkRaw(this.val$isRaw, this.factory, (PsiMethod) psiMember, psiSubstitutorObtainSubstitutor) : psiSubstitutorObtainSubstitutor;
            }

            private PsiSubstitutor obtainSubstitutor(PsiMember psiMember) {
                PsiClass psiClass2 = (PsiClass) Objects.requireNonNull(psiMember.mo6092getContainingClass());
                return PsiClassImplUtil.obtainFinalSubstitutor(psiClass2, (PsiSubstitutor) ObjectUtils.notNull(this.hierarchy.getSuperMembersSubstitutor(psiClass2, this.val$languageLevel), PsiSubstitutor.EMPTY), this.val$aClass, this.val$substitutor, this.factory, this.val$languageLevel);
            }
        };
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.FIELD)) {
            PsiElement psiElementFindFieldByName = psiClass.findFieldByName(str, false);
            if (psiElementFindFieldByName != null) {
                if (!skipInvalid(psiElementFindFieldByName)) {
                    psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, psiClass);
                    if (!psiScopeProcessor.execute(psiElementFindFieldByName, resolveState)) {
                        return false;
                    }
                }
            } else {
                List<PsiMember> listCalcMembersByName = getMap(psiClass, globalSearchScope).calcMembersByName(MemberType.FIELD, str, psiClass, psiElement2);
                if (!listCalcMembersByName.isEmpty()) {
                    Iterator<PsiMember> it2 = listCalcMembersByName.iterator();
                    boolean z3 = false;
                    while (it2.hasNext()) {
                        PsiMember next = it2.next();
                        Object objMo6092getContainingClass2 = next.mo6092getContainingClass();
                        if (skipInvalid(next)) {
                            it = it2;
                        } else if (objMo6092getContainingClass2 == null) {
                            PsiElement parent = next.getMyParent();
                            it = it2;
                            LOG.error("No class for field " + next.getMyName() + " of " + next.getClass() + ", parent " + parent + " of " + (parent == null ? null : parent.getClass()));
                        } else {
                            it = it2;
                            psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, objMo6092getContainingClass2);
                            if (!psiScopeProcessor.execute(next, resolveState.put(PsiSubstitutor.KEY, function.apply(next)))) {
                                z3 = true;
                            }
                        }
                        it2 = it;
                    }
                    if (z3) {
                        return false;
                    }
                }
            }
        }
        if (elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) {
            if (psiElement == null || psiElement.getContext() != psiClass) {
                z2 = false;
            } else {
                if ((psiElement instanceof PsiClass) && !psiScopeProcessor.execute(psiElement, resolveState)) {
                    return false;
                }
                z2 = false;
                PsiTypeParameterList typeParameterList = psiClass.mo6093getTypeParameterList();
                if (typeParameterList != null && !typeParameterList.processDeclarations(psiScopeProcessor, resolveState, psiElement, psiElement2)) {
                    return false;
                }
            }
            if (!(psiElement instanceof PsiReferenceList)) {
                PsiElement psiElementFindInnerClassByName = psiClass.findInnerClassByName(str, z2);
                if (psiElementFindInnerClassByName != null) {
                    if (!skipInvalid(psiElementFindInnerClassByName)) {
                        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, psiClass);
                        if (!psiScopeProcessor.execute(psiElementFindInnerClassByName, resolveState)) {
                            return z2;
                        }
                    }
                } else {
                    List<PsiMember> listCalcMembersByName2 = getMap(psiClass, globalSearchScope).calcMembersByName(MemberType.CLASS, str, psiClass, psiElement2);
                    if (!listCalcMembersByName2.isEmpty()) {
                        boolean z4 = false;
                        for (PsiMember psiMember : listCalcMembersByName2) {
                            if (!skipInvalid(psiMember) && (objMo6092getContainingClass = psiMember.mo6092getContainingClass()) != null) {
                                psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, objMo6092getContainingClass);
                                if (!psiScopeProcessor.execute(psiMember, resolveState.put(PsiSubstitutor.KEY, function.apply(psiMember)))) {
                                    z4 = true;
                                }
                            }
                        }
                        if (z4) {
                            return false;
                        }
                    }
                }
            }
        }
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.METHOD)) {
            return true;
        }
        boolean z5 = psiScopeProcessor instanceof MethodResolverProcessor;
        if (z5) {
            MethodResolverProcessor methodResolverProcessor = (MethodResolverProcessor) psiScopeProcessor;
            if (methodResolverProcessor.isConstructor()) {
                PsiMethod[] constructors = psiClass.getConstructors();
                methodResolverProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, psiClass);
                for (PsiMethod psiMethod : constructors) {
                    if (!skipInvalid(psiMethod) && !methodResolverProcessor.execute(psiMethod, resolveState)) {
                        return false;
                    }
                }
                return true;
            }
        }
        List<PsiMember> listCalcMembersByName3 = getMap(psiClass, globalSearchScope).calcMembersByName(MemberType.METHOD, str, psiClass, psiElement2);
        if (listCalcMembersByName3.isEmpty()) {
            return true;
        }
        boolean z6 = false;
        for (PsiMember psiMember2 : listCalcMembersByName3) {
            ProgressIndicatorProvider.checkCanceled();
            PsiMethod psiMethod2 = (PsiMethod) psiMember2;
            if (!skipInvalid(psiMethod2) && (!z5 || psiMethod2.isConstructor() == ((MethodResolverProcessor) psiScopeProcessor).isConstructor())) {
                Object containingClass = psiMethod2.mo6092getContainingClass();
                if (containingClass != null && (set == null || !set.contains(containingClass))) {
                    psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, containingClass);
                    if (!psiScopeProcessor.execute(psiMethod2, resolveState.put(PsiSubstitutor.KEY, function.apply(psiMethod2)))) {
                        z6 = true;
                    }
                }
            }
        }
        if (z6) {
            return false;
        }
        if (set == null) {
            return true;
        }
        Iterator<PsiMember> it3 = listCalcMembersByName3.iterator();
        while (it3.hasNext()) {
            set.add(it3.next().mo6092getContainingClass());
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static PsiSubstitutor checkRaw(boolean z, PsiElementFactory psiElementFactory, PsiMethod psiMethod, PsiSubstitutor psiSubstitutor) {
        PsiClass containingClass;
        if (psiElementFactory == null) {
            $$$reportNull$$$0(64);
        }
        if (psiMethod == 0) {
            $$$reportNull$$$0(65);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(66);
        }
        return (!z || psiMethod.hasModifierProperty("static") || (containingClass = psiMethod.mo6092getContainingClass()) == null || !containingClass.hasTypeParameters()) ? psiSubstitutor : psiElementFactory.createRawSubstitutor(psiSubstitutor, psiMethod.getTypeParameters());
    }

    public static PsiSubstitutor obtainFinalSubstitutor(PsiClass psiClass, PsiSubstitutor psiSubstitutor, PsiClass psiClass2, PsiSubstitutor psiSubstitutor2, PsiElementFactory psiElementFactory, LanguageLevel languageLevel) {
        if (psiClass == null) {
            $$$reportNull$$$0(67);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(68);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(69);
        }
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(70);
        }
        if (psiElementFactory == null) {
            $$$reportNull$$$0(71);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(72);
        }
        if (PsiUtil.isRawSubstitutor(psiClass2, psiSubstitutor2)) {
            return psiElementFactory.createRawSubstitutor(psiClass).putAll(psiSubstitutor2);
        }
        PsiType psiTypeSubstitute = psiSubstitutor2.substitute(psiElementFactory.createType(psiClass, psiSubstitutor, languageLevel));
        return !(psiTypeSubstitute instanceof PsiClassType) ? psiSubstitutor : ((PsiClassType) psiTypeSubstitute).resolveGenerics().getSubstitutor();
    }

    private static boolean processClassMembersWithAllNames(PsiClass psiClass, final PsiScopeProcessor psiScopeProcessor, final ResolveState resolveState, Set<PsiClass> set, final PsiElement psiElement, final PsiElement psiElement2, final boolean z, final LanguageLevel languageLevel, final GlobalSearchScope globalSearchScope) {
        PsiTypeParameterList typeParameterList;
        if (psiClass == null) {
            $$$reportNull$$$0(73);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(74);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(75);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(76);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(77);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(78);
        }
        ProgressManager.checkCanceled();
        final Set<PsiClass> hashSet = set == null ? new HashSet() : set;
        if (!hashSet.add(psiClass)) {
            return true;
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, psiClass);
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.FIELD)) && !processMembers(resolveState, psiScopeProcessor, psiClass.getFields())) {
            return false;
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        if (elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.METHOD)) {
            PsiSubstitutor psiSubstitutor = (PsiSubstitutor) resolveState.get(PsiSubstitutor.KEY);
            for (PsiMethod psiMethod : psiClass.getMethods()) {
                PsiSubstitutor psiSubstitutorCheckRaw = checkRaw(z, elementFactory, psiMethod, psiSubstitutor);
                if (!psiScopeProcessor.execute(psiMethod, psiSubstitutorCheckRaw == psiSubstitutor ? resolveState : resolveState.put(PsiSubstitutor.KEY, psiSubstitutorCheckRaw))) {
                    return false;
                }
            }
        }
        if (elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) {
            if (psiElement != null && psiElement.getContext() == psiClass && (typeParameterList = psiClass.mo6093getTypeParameterList()) != null && !typeParameterList.processDeclarations(psiScopeProcessor, ResolveState.initial(), psiElement, psiElement2)) {
                return false;
            }
            if (!(psiElement instanceof PsiReferenceList) && !(psiElement instanceof PsiModifierList) && !processMembers(resolveState, psiScopeProcessor, psiClass.getInnerClasses())) {
                return false;
            }
        }
        if (psiElement instanceof PsiReferenceList) {
            return true;
        }
        return processSuperTypes(psiClass, (PsiSubstitutor) resolveState.get(PsiSubstitutor.KEY), elementFactory, languageLevel, globalSearchScope, new PairProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.PairProcessor
            public final boolean process(Object obj, Object obj2) {
                return PsiClassImplUtil.processDeclarationsInClass((PsiClass) obj, psiScopeProcessor, resolveState.put(PsiSubstitutor.KEY, (PsiSubstitutor) obj2), hashSet, psiElement, psiElement2, languageLevel, z, globalSearchScope);
            }
        });
    }

    public static <T extends PsiType> T correctType(T t, GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(79);
        }
        return (t == null || !Registry.is("java.correct.class.type.by.place.resolve.scope")) ? t : (T) new TypeCorrector(globalSearchScope).correctType(t);
    }

    public static List<PsiClassType.ClassResolveResult> getScopeCorrectedSuperTypes(PsiClass psiClass, GlobalSearchScope globalSearchScope) {
        if (skipInvalid(psiClass)) {
            return Collections.emptyList();
        }
        return ScopedClassHierarchy.getHierarchy(psiClass, globalSearchScope).getImmediateSupersWithCapturing();
    }

    static boolean processSuperTypes(PsiClass psiClass, PsiSubstitutor psiSubstitutor, PsiElementFactory psiElementFactory, LanguageLevel languageLevel, GlobalSearchScope globalSearchScope, PairProcessor<? super PsiClass, ? super PsiSubstitutor> pairProcessor) {
        if (psiClass == null) {
            $$$reportNull$$$0(80);
        }
        if (psiElementFactory == null) {
            $$$reportNull$$$0(81);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(82);
        }
        boolean z = false;
        for (PsiClassType.ClassResolveResult classResolveResult : getScopeCorrectedSuperTypes(psiClass, globalSearchScope)) {
            PsiClass element = classResolveResult.getElement();
            if (!pairProcessor.process(element, obtainFinalSubstitutor(element, classResolveResult.getSubstitutor(), psiClass, psiSubstitutor, psiElementFactory, languageLevel))) {
                z = true;
            }
        }
        return !z;
    }

    public static PsiClass getSuperClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(83);
        }
        if (psiClass.isInterface()) {
            return findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT);
        }
        if (psiClass.isEnum()) {
            return findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_ENUM);
        }
        if (psiClass.isRecord()) {
            return findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_RECORD);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClass psiClassResolve = ((PsiAnonymousClass) psiClass).getBaseClassType().resolve();
            return (psiClassResolve == null || psiClassResolve.isInterface()) ? findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT) : psiClassResolve;
        }
        if (CommonClassNames.JAVA_LANG_OBJECT.equals(psiClass.getQualifiedName())) {
            return null;
        }
        PsiClassType[] extendsListTypes = psiClass.getExtendsListTypes();
        if (extendsListTypes.length == 0) {
            return findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT);
        }
        PsiClass psiClassResolve2 = extendsListTypes[0].resolve();
        return psiClassResolve2 == null ? findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT) : psiClassResolve2;
    }

    private static PsiClass findSpecialSuperClass(PsiClass psiClass, String str) {
        if (psiClass == null) {
            $$$reportNull$$$0(84);
        }
        return JavaPsiFacade.getInstance(psiClass.getProject()).findClass(str, psiClass.getResolveScope());
    }

    public static PsiClass[] getSupers(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(85);
        }
        PsiClass[] supersInner = getSupersInner(psiClass);
        for (PsiClass psiClass2 : supersInner) {
            LOG.assertTrue(psiClass2 != null);
        }
        if (supersInner == null) {
            $$$reportNull$$$0(86);
        }
        return supersInner;
    }

    private static PsiClass[] getSupersInner(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(87);
        }
        PsiClassType[] extendsListTypes = psiClass.getExtendsListTypes();
        if (psiClass.isInterface()) {
            return resolveClassReferenceList(extendsListTypes, psiClass, true);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClass psiClassResolve = ((PsiAnonymousClass) psiClass).getBaseClassType().resolve();
            if (psiClassResolve != null) {
                if (psiClassResolve.isInterface()) {
                    PsiClass psiClassFindSpecialSuperClass = findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT);
                    return psiClassFindSpecialSuperClass != null ? new PsiClass[]{psiClassFindSpecialSuperClass, psiClassResolve} : new PsiClass[]{psiClassResolve};
                }
                return new PsiClass[]{psiClassResolve};
            }
            PsiClass psiClassFindSpecialSuperClass2 = findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT);
            PsiClass[] psiClassArr = psiClassFindSpecialSuperClass2 != null ? new PsiClass[]{psiClassFindSpecialSuperClass2} : PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(90);
            }
            return psiClassArr;
        }
        if (psiClass instanceof PsiTypeParameter) {
            if (extendsListTypes.length == 0) {
                PsiClass psiClassFindSpecialSuperClass3 = findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT);
                PsiClass[] psiClassArr2 = psiClassFindSpecialSuperClass3 != null ? new PsiClass[]{psiClassFindSpecialSuperClass3} : PsiClass.EMPTY_ARRAY;
                if (psiClassArr2 == null) {
                    $$$reportNull$$$0(91);
                }
                return psiClassArr2;
            }
            return resolveClassReferenceList(extendsListTypes, psiClass, false);
        }
        PsiClass[] psiClassArrResolveClassReferenceList = resolveClassReferenceList(psiClass.getImplementsListTypes(), psiClass, false);
        PsiClass superClass = getSuperClass(psiClass);
        if (superClass == null) {
            if (psiClassArrResolveClassReferenceList == null) {
                $$$reportNull$$$0(92);
            }
            return psiClassArrResolveClassReferenceList;
        }
        PsiClass[] psiClassArr3 = new PsiClass[psiClassArrResolveClassReferenceList.length + 1];
        psiClassArr3[0] = superClass;
        System.arraycopy(psiClassArrResolveClassReferenceList, 0, psiClassArr3, 1, psiClassArrResolveClassReferenceList.length);
        return psiClassArr3;
    }

    public static PsiClassType[] getSuperTypes(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(94);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClassType baseClassType = ((PsiAnonymousClass) psiClass).getBaseClassType();
            PsiClass psiClassResolve = baseClassType.resolve();
            if (psiClassResolve == null || !psiClassResolve.isInterface()) {
                return new PsiClassType[]{baseClassType};
            }
            return new PsiClassType[]{PsiType.getJavaLangObject(psiClass.getManager(), psiClass.getResolveScope()), baseClassType};
        }
        PsiClassType[] extendsListTypes = psiClass.getExtendsListTypes();
        PsiClassType[] implementsListTypes = psiClass.getImplementsListTypes();
        int i = extendsListTypes.length == 0 ? 0 : 1;
        int length = extendsListTypes.length + (i ^ 1);
        PsiClassType[] psiClassTypeArr = new PsiClassType[implementsListTypes.length + length];
        System.arraycopy(extendsListTypes, 0, psiClassTypeArr, 0, extendsListTypes.length);
        if (i == 0) {
            if (CommonClassNames.JAVA_LANG_OBJECT.equals(psiClass.getQualifiedName())) {
                PsiClassType[] psiClassTypeArr2 = PsiClassType.EMPTY_ARRAY;
                if (psiClassTypeArr2 == null) {
                    $$$reportNull$$$0(97);
                }
                return psiClassTypeArr2;
            }
            psiClassTypeArr[0] = PsiType.getJavaLangObject(psiClass.getManager(), psiClass.getResolveScope());
        }
        System.arraycopy(implementsListTypes, 0, psiClassTypeArr, length, implementsListTypes.length);
        return psiClassTypeArr;
    }

    private static PsiClassType getAnnotationSuperType(PsiClass psiClass, PsiElementFactory psiElementFactory) {
        if (psiClass == null) {
            $$$reportNull$$$0(99);
        }
        if (psiElementFactory == null) {
            $$$reportNull$$$0(100);
        }
        PsiClassType psiClassTypeCreateTypeByFQClassName = psiElementFactory.createTypeByFQClassName(CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION, psiClass.getResolveScope());
        if (psiClassTypeCreateTypeByFQClassName == null) {
            $$$reportNull$$$0(101);
        }
        return psiClassTypeCreateTypeByFQClassName;
    }

    private static PsiClassType getEnumSuperType(PsiClass psiClass, PsiElementFactory psiElementFactory) {
        if (psiClass == null) {
            $$$reportNull$$$0(102);
        }
        if (psiElementFactory == null) {
            $$$reportNull$$$0(103);
        }
        PsiClass psiClassFindSpecialSuperClass = findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_ENUM);
        if (psiClassFindSpecialSuperClass == null) {
            try {
                return (PsiClassType) psiElementFactory.createTypeFromText(CommonClassNames.JAVA_LANG_ENUM, null);
            } catch (IncorrectOperationException unused) {
                return null;
            }
        }
        PsiTypeParameter[] typeParameters = psiClassFindSpecialSuperClass.getTypeParameters();
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        if (typeParameters.length == 1) {
            psiSubstitutorPut = psiSubstitutorPut.put(typeParameters[0], psiElementFactory.createType(psiClass));
        }
        return new PsiImmediateClassType(psiClassFindSpecialSuperClass, psiSubstitutorPut);
    }

    public static PsiClass[] getInterfaces(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(104);
        }
        PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
        if (extendsListTypes.length == 0) {
            PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(105);
            }
            return psiClassArr;
        }
        ArrayList arrayList = new ArrayList(extendsListTypes.length);
        for (PsiClassType psiClassType : extendsListTypes) {
            PsiClass psiClassResolve = psiClassType.resolve();
            if (psiClassResolve != null && psiClassResolve.isInterface()) {
                arrayList.add(psiClassResolve);
            }
        }
        PsiClass[] psiClassArr2 = (PsiClass[]) arrayList.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr2 == null) {
            $$$reportNull$$$0(106);
        }
        return psiClassArr2;
    }

    public static PsiClass[] getInterfaces(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(107);
        }
        if (psiClass.isInterface()) {
            return resolveClassReferenceList(psiClass.getExtendsListTypes(), psiClass, false);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClass psiClassResolve = ((PsiAnonymousClass) psiClass).getBaseClassType().resolve();
            PsiClass[] psiClassArr = (psiClassResolve == null || !psiClassResolve.isInterface()) ? PsiClass.EMPTY_ARRAY : new PsiClass[]{psiClassResolve};
            if (psiClassArr == null) {
                $$$reportNull$$$0(108);
            }
            return psiClassArr;
        }
        return resolveClassReferenceList(psiClass.getImplementsListTypes(), psiClass, false);
    }

    private static PsiClass[] resolveClassReferenceList(PsiClassType[] psiClassTypeArr, PsiClass psiClass, boolean z) {
        PsiClass psiClassFindSpecialSuperClass;
        if (psiClass == null) {
            $$$reportNull$$$0(109);
        }
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(110);
        }
        if (z) {
            psiClassFindSpecialSuperClass = findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_OBJECT);
            if (psiClassFindSpecialSuperClass == null) {
                z = false;
            }
        } else {
            psiClassFindSpecialSuperClass = null;
        }
        int i = 1;
        if (psiClassTypeArr.length == 0) {
            if (z) {
                return new PsiClass[]{psiClassFindSpecialSuperClass};
            }
            PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(112);
            }
            return psiClassArr;
        }
        int length = psiClassTypeArr.length;
        if (z) {
            length++;
        }
        PsiClass[] psiClassArr2 = new PsiClass[length];
        if (z) {
            psiClassArr2[0] = psiClassFindSpecialSuperClass;
        } else {
            i = 0;
        }
        for (PsiClassType psiClassType : psiClassTypeArr) {
            PsiClass psiClassResolve = psiClassType.resolve();
            if (psiClassResolve != null) {
                psiClassArr2[i] = psiClassResolve;
                i++;
            }
        }
        if (i < length) {
            psiClassArr2 = (PsiClass[]) ArrayUtil.realloc(psiClassArr2, i, PsiClass.ARRAY_FACTORY);
        }
        if (psiClassArr2 == null) {
            $$$reportNull$$$0(113);
        }
        return psiClassArr2;
    }

    public static List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(PsiClass psiClass, String str, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(114);
        }
        if (!z) {
            PsiMethod[] psiMethodArrFindMethodsByName = psiClass.findMethodsByName(str, false);
            ArrayList arrayList = new ArrayList(psiMethodArrFindMethodsByName.length);
            for (PsiMethod psiMethod : psiMethodArrFindMethodsByName) {
                arrayList.add(Pair.create(psiMethod, PsiSubstitutor.EMPTY));
            }
            return arrayList;
        }
        List<PsiMember> listCalcMembersByName = getMap(psiClass).calcMembersByName(MemberType.METHOD, str, null, null);
        if (!listCalcMembersByName.isEmpty()) {
            return withSubstitutors(psiClass, (PsiMember[]) listCalcMembersByName.toArray(PsiMember.EMPTY_ARRAY));
        }
        List<Pair<PsiMethod, PsiSubstitutor>> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(116);
        }
        return listEmptyList;
    }

    private static <T extends PsiMember> List<Pair<T, PsiSubstitutor>> withSubstitutors(PsiClass psiClass, PsiMember[] psiMemberArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(117);
        }
        final ScopedClassHierarchy hierarchy = ScopedClassHierarchy.getHierarchy(psiClass, psiClass.getResolveScope());
        final LanguageLevel languageLevel = PsiUtil.getLanguageLevel(psiClass);
        List<Pair<T, PsiSubstitutor>> map = ContainerUtil.map(psiMemberArr, new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return PsiClassImplUtil.lambda$withSubstitutors$5(hierarchy, languageLevel, (PsiMember) obj);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(118);
        }
        return map;
    }

    static /* synthetic */ Pair lambda$withSubstitutors$5(ScopedClassHierarchy scopedClassHierarchy, LanguageLevel languageLevel, PsiMember psiMember) {
        PsiClass psiClassMo6092getContainingClass = psiMember.mo6092getContainingClass();
        PsiSubstitutor superMembersSubstitutor = psiClassMo6092getContainingClass == null ? null : scopedClassHierarchy.getSuperMembersSubstitutor(psiClassMo6092getContainingClass, languageLevel);
        if (superMembersSubstitutor == null) {
            superMembersSubstitutor = PsiSubstitutor.EMPTY;
        }
        return Pair.create(psiMember, superMembersSubstitutor);
    }

    public static PsiClassType[] getExtendsListTypes(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(119);
        }
        if (psiClass.isEnum()) {
            PsiClassType enumSuperType = getEnumSuperType(psiClass, JavaPsiFacade.getElementFactory(psiClass.getProject()));
            PsiClassType[] psiClassTypeArr = enumSuperType == null ? PsiClassType.EMPTY_ARRAY : new PsiClassType[]{enumSuperType};
            if (psiClassTypeArr == null) {
                $$$reportNull$$$0(120);
            }
            return psiClassTypeArr;
        }
        if (psiClass.isRecord()) {
            PsiClass psiClassFindSpecialSuperClass = findSpecialSuperClass(psiClass, CommonClassNames.JAVA_LANG_RECORD);
            if (psiClassFindSpecialSuperClass != null) {
                return new PsiClassType[]{new PsiImmediateClassType(psiClassFindSpecialSuperClass, PsiSubstitutor.EMPTY)};
            }
            return new PsiClassType[]{JavaPsiFacade.getElementFactory(psiClass.getProject()).createTypeByFQClassName(CommonClassNames.JAVA_LANG_RECORD, psiClass.getResolveScope())};
        }
        if (psiClass.isAnnotationType()) {
            return new PsiClassType[]{getAnnotationSuperType(psiClass, JavaPsiFacade.getElementFactory(psiClass.getProject()))};
        }
        boolean z = psiClass instanceof PsiTypeParameter;
        PsiType inferredUpperBoundForSynthetic = z ? TypeConversionUtil.getInferredUpperBoundForSynthetic((PsiTypeParameter) psiClass) : null;
        if (inferredUpperBoundForSynthetic == null && z) {
            inferredUpperBoundForSynthetic = ThreadLocalTypes.getElementType(psiClass);
        }
        if (inferredUpperBoundForSynthetic instanceof PsiIntersectionType) {
            PsiType[] conjuncts = ((PsiIntersectionType) inferredUpperBoundForSynthetic).getConjuncts();
            ArrayList arrayList = new ArrayList();
            for (PsiType psiType : conjuncts) {
                if (psiType instanceof PsiClassType) {
                    arrayList.add((PsiClassType) psiType);
                }
            }
            PsiClassType[] psiClassTypeArr2 = (PsiClassType[]) arrayList.toArray(PsiClassType.EMPTY_ARRAY);
            if (psiClassTypeArr2 == null) {
                $$$reportNull$$$0(124);
            }
            return psiClassTypeArr2;
        }
        if (inferredUpperBoundForSynthetic instanceof PsiClassType) {
            return new PsiClassType[]{(PsiClassType) inferredUpperBoundForSynthetic};
        }
        PsiReferenceList extendsList = psiClass.getExtendsList();
        if (extendsList != null) {
            PsiClassType[] referencedTypes = extendsList.getReferencedTypes();
            if (referencedTypes == null) {
                $$$reportNull$$$0(126);
            }
            return referencedTypes;
        }
        PsiClassType[] psiClassTypeArr3 = PsiClassType.EMPTY_ARRAY;
        if (psiClassTypeArr3 == null) {
            $$$reportNull$$$0(127);
        }
        return psiClassTypeArr3;
    }

    public static PsiClassType[] getImplementsListTypes(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(128);
        }
        PsiReferenceList implementsList = psiClass.getImplementsList();
        if (implementsList != null) {
            PsiClassType[] referencedTypes = implementsList.getReferencedTypes();
            if (referencedTypes == null) {
                $$$reportNull$$$0(129);
            }
            return referencedTypes;
        }
        PsiClassType[] psiClassTypeArr = PsiClassType.EMPTY_ARRAY;
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(130);
        }
        return psiClassTypeArr;
    }

    static boolean isInExtendsList(PsiClass psiClass, PsiClass psiClass2, String str, PsiManager psiManager) {
        if (psiClass == null) {
            $$$reportNull$$$0(131);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(132);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(133);
        }
        if (psiClass.isEnum()) {
            return CommonClassNames.JAVA_LANG_ENUM.equals(psiClass2.getQualifiedName());
        }
        if (psiClass.isAnnotationType()) {
            return CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION.equals(psiClass2.getQualifiedName());
        }
        boolean z = psiClass instanceof PsiTypeParameter;
        PsiType inferredUpperBoundForSynthetic = z ? TypeConversionUtil.getInferredUpperBoundForSynthetic((PsiTypeParameter) psiClass) : null;
        if (inferredUpperBoundForSynthetic == null && z) {
            inferredUpperBoundForSynthetic = ThreadLocalTypes.getElementType(psiClass);
        }
        if (inferredUpperBoundForSynthetic instanceof PsiIntersectionType) {
            for (PsiType psiType : ((PsiIntersectionType) inferredUpperBoundForSynthetic).getConjuncts()) {
                if (psiType instanceof PsiClassType) {
                    PsiClassType psiClassType = (PsiClassType) psiType;
                    if (psiClassType.getClassName().equals(str) && psiClass2.equals(psiClassType.resolve())) {
                        return true;
                    }
                }
            }
            return false;
        }
        if (inferredUpperBoundForSynthetic instanceof PsiClassType) {
            PsiClassType psiClassType2 = (PsiClassType) inferredUpperBoundForSynthetic;
            return psiClassType2.getClassName().equals(str) && psiClass2.equals(psiClassType2.resolve());
        }
        return isInReferenceList(psiClass.getExtendsList(), psiClass2, str, psiManager);
    }

    static boolean isInReferenceList(PsiReferenceList psiReferenceList, PsiClass psiClass, String str, PsiManager psiManager) {
        if (psiClass == null) {
            $$$reportNull$$$0(134);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(135);
        }
        if (psiReferenceList == null) {
            return false;
        }
        if (psiReferenceList instanceof StubBasedPsiElement) {
            StubElement stub = ((StubBasedPsiElement) psiReferenceList).getStub();
            if ((stub instanceof PsiClassReferenceListStub) && str != null) {
                PsiClassReferenceListStub psiClassReferenceListStub = (PsiClassReferenceListStub) stub;
                String[] referencedNames = psiClassReferenceListStub.getReferencedNames();
                int i = 0;
                while (i < referencedNames.length) {
                    if (PsiNameHelper.getShortClassName(referencedNames[i]).endsWith(str)) {
                        PsiClassType[] referencedTypes = psiClassReferenceListStub.getReferencedTypes();
                        PsiClassType psiClassType = i >= referencedTypes.length ? null : referencedTypes[i];
                        if (psiManager.areElementsEquivalent(psiClass, psiClassType != null ? psiClassType.resolve() : null)) {
                            return true;
                        }
                    }
                    i++;
                }
                return false;
            }
            if (stub != null) {
                for (PsiClassType psiClassType2 : psiReferenceList.getReferencedTypes()) {
                    if (Objects.equals(psiClassType2.getClassName(), str) && psiManager.areElementsEquivalent(psiClass, psiClassType2.resolve())) {
                        return true;
                    }
                }
                return false;
            }
        }
        if (psiReferenceList.getLanguage() == JavaLanguage.INSTANCE) {
            for (PsiJavaCodeReferenceElement psiJavaCodeReferenceElement : psiReferenceList.getReferenceElements()) {
                if (Comparing.strEqual(str, psiJavaCodeReferenceElement.getCustomReferenceName()) && psiManager.areElementsEquivalent(psiClass, psiJavaCodeReferenceElement.resolve())) {
                    return true;
                }
            }
            return false;
        }
        for (PsiClassType psiClassType3 : psiReferenceList.getReferencedTypes()) {
            if (Objects.equals(psiClassType3.getClassName(), str) && psiManager.areElementsEquivalent(psiClass, psiClassType3.resolve())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isClassEquivalentTo(final PsiClass psiClass, PsiElement psiElement) {
        String name2;
        PsiClass psiClass2;
        String name3;
        if (psiClass == null) {
            $$$reportNull$$$0(136);
        }
        if (psiClass == psiElement) {
            return true;
        }
        if (!(psiElement instanceof PsiClass) || (name2 = psiClass.getMyName()) == null || !psiElement.isValid() || (name3 = (psiClass2 = (PsiClass) psiElement).getMyName()) == null || name2.hashCode() != name3.hashCode() || !name2.equals(name3)) {
            return false;
        }
        String qualifiedName = psiClass.getQualifiedName();
        String qualifiedName2 = psiClass2.getQualifiedName();
        if (qualifiedName == null || qualifiedName2 == null) {
            if (qualifiedName != qualifiedName2 || !(psiClass instanceof PsiTypeParameter) || !(psiElement instanceof PsiTypeParameter)) {
                return false;
            }
            final PsiTypeParameter psiTypeParameter = (PsiTypeParameter) psiClass;
            final PsiTypeParameter psiTypeParameter2 = (PsiTypeParameter) psiElement;
            if (psiTypeParameter.getIndex() != psiTypeParameter2.getIndex()) {
                return false;
            }
            if (TypeConversionUtil.areSameFreshVariables(psiTypeParameter, psiTypeParameter2)) {
                return true;
            }
            return !Boolean.FALSE.equals(RecursionManager.doPreventingRecursion(Pair.create(psiTypeParameter, psiTypeParameter2), true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return Boolean.valueOf(psiClass.getManager().areElementsEquivalent(psiTypeParameter.getOwner(), psiTypeParameter2.getOwner()));
                }
            }));
        }
        if (!qualifiedName.equals(qualifiedName2)) {
            return false;
        }
        if (psiClass.getOriginalElement().equals(psiElement.getOriginalElement())) {
            return true;
        }
        PsiFile originalFile = getOriginalFile(psiClass);
        PsiFile originalFile2 = getOriginalFile(psiClass2);
        PsiFile psiFile = (PsiFile) originalFile.getUserData(PsiFileFactory.ORIGINAL_FILE);
        PsiFile psiFile2 = (PsiFile) originalFile2.getUserData(PsiFileFactory.ORIGINAL_FILE);
        if ((psiFile == psiFile2 && psiFile != null) || psiFile == originalFile2 || psiFile2 == originalFile || originalFile == originalFile2) {
            return true;
        }
        FileIndexFacade fileIndexFacade = (FileIndexFacade) originalFile.getProject().getService(FileIndexFacade.class);
        FileIndexFacade fileIndexFacade2 = (FileIndexFacade) originalFile2.getProject().getService(FileIndexFacade.class);
        VirtualFile virtualFile = originalFile.getViewProvider().getVirtualFile();
        VirtualFile virtualFile2 = originalFile2.getViewProvider().getVirtualFile();
        return (fileIndexFacade.isInSource(virtualFile) || fileIndexFacade.isInLibraryClasses(virtualFile)) && (fileIndexFacade2.isInSource(virtualFile2) || fileIndexFacade2.isInLibraryClasses(virtualFile2));
    }

    private static PsiFile getOriginalFile(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(137);
        }
        PsiFile containingFile = psiClass.getContainingFile();
        if (containingFile == null) {
            PsiUtilCore.ensureValid(psiClass);
            throw new IllegalStateException("No containing file for " + psiClass.getLanguage() + " " + psiClass.getClass());
        }
        PsiFile originalFile = containingFile.getOriginalFile();
        if (originalFile == null) {
            $$$reportNull$$$0(138);
        }
        return originalFile;
    }

    public static boolean isFieldEquivalentTo(PsiField psiField, PsiElement psiElement) {
        if (psiField == null) {
            $$$reportNull$$$0(139);
        }
        if (!(psiElement instanceof PsiField)) {
            return false;
        }
        String name2 = psiField.getMyName();
        if (!psiElement.isValid()) {
            return false;
        }
        PsiField psiField2 = (PsiField) psiElement;
        if (!name2.equals(psiField2.getMyName())) {
            return false;
        }
        PsiClass containingClass = psiField.mo6092getContainingClass();
        PsiClass containingClass2 = psiField2.mo6092getContainingClass();
        return (containingClass == null || containingClass2 == null || !psiField.getManager().areElementsEquivalent(containingClass, containingClass2)) ? false : true;
    }

    public static boolean isMethodEquivalentTo(PsiMethod psiMethod, PsiElement psiElement) {
        if (psiMethod == null) {
            $$$reportNull$$$0(140);
        }
        if (psiMethod == psiElement) {
            return true;
        }
        if (!(psiElement instanceof PsiMethod)) {
            return false;
        }
        PsiMethod psiMethod2 = (PsiMethod) psiElement;
        if (!psiElement.isValid() || !psiMethod.getMyName().equals(psiMethod2.getMyName())) {
            return false;
        }
        PsiClass containingClass = psiMethod.mo6092getContainingClass();
        PsiClass containingClass2 = psiMethod2.mo6092getContainingClass();
        PsiManager manager = psiMethod.getManager();
        if (containingClass == null || containingClass2 == null || !manager.areElementsEquivalent(containingClass, containingClass2)) {
            return false;
        }
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        PsiParameter[] parameters2 = psiMethod2.getParameterList().getParameters();
        if (parameters.length != parameters2.length) {
            return false;
        }
        for (int i = 0; i < parameters.length; i++) {
            if (!compareParamTypes(manager, parameters[i].getType(), parameters2[i].getType(), new HashSet())) {
                return false;
            }
        }
        return true;
    }

    private static boolean compareParamTypes(PsiManager psiManager, PsiType psiType, PsiType psiType2, Set<? super String> set) {
        if (psiManager == null) {
            $$$reportNull$$$0(141);
        }
        if (psiType == null) {
            $$$reportNull$$$0(142);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(143);
        }
        if (psiType instanceof PsiArrayType) {
            return (psiType2 instanceof PsiArrayType) && compareParamTypes(psiManager, ((PsiArrayType) psiType).getComponentType(), ((PsiArrayType) psiType2).getComponentType(), set);
        }
        if (!(psiType instanceof PsiClassType) || !(psiType2 instanceof PsiClassType)) {
            return psiType.equals(psiType2);
        }
        PsiClass psiClassResolve = ((PsiClassType) psiType).resolve();
        PsiClass psiClassResolve2 = ((PsiClassType) psiType2).resolve();
        set.add(psiType.getCanonicalText());
        set.add(psiType2.getCanonicalText());
        if ((psiClassResolve instanceof PsiTypeParameter) && (psiClassResolve2 instanceof PsiTypeParameter)) {
            if (!Objects.equals(psiClassResolve.getMyName(), psiClassResolve2.getMyName()) || ((PsiTypeParameter) psiClassResolve).getIndex() != ((PsiTypeParameter) psiClassResolve2).getIndex()) {
                return false;
            }
            PsiClassType[] extendsListTypes = psiClassResolve.getExtendsListTypes();
            PsiClassType[] extendsListTypes2 = psiClassResolve2.getExtendsListTypes();
            if (extendsListTypes.length != extendsListTypes2.length) {
                return false;
            }
            for (int i = 0; i < extendsListTypes.length; i++) {
                PsiClassType psiClassType = extendsListTypes[i];
                PsiClassType psiClassType2 = extendsListTypes2[i];
                if (set.contains(psiClassType.getCanonicalText()) || set.contains(psiClassType2.getCanonicalText()) || !compareParamTypes(psiManager, psiClassType, psiClassType2, set)) {
                    return false;
                }
            }
            return true;
        }
        return psiManager.areElementsEquivalent(psiClassResolve, psiClassResolve2);
    }
}
