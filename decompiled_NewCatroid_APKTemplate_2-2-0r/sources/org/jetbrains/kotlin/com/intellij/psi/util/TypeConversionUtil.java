package org.jetbrains.kotlin.com.intellij.psi.util;

import com.google.android.gms.cast.MediaTrack;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import okhttp3.internal.ws.WebSocketProtocol;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmPrimitiveTypeKind;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.ProjectRootModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpressionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ObjectIntHashMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ObjectIntMap;

/* JADX INFO: loaded from: classes3.dex */
public final class TypeConversionUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int BYTE_RANK = 1;
    public static final int CHAR_RANK = 3;
    public static final int DOUBLE_RANK = 7;
    public static final int FLOAT_RANK = 6;
    private static final Set<String> INTEGER_NUMBER_TYPES;
    public static final int INT_RANK = 4;
    public static final int LONG_RANK = 5;
    private static final Key<PsiType> LOWER_BOUND;
    public static final PsiType NULL_TYPE;
    private static final Key<PsiElement> ORIGINAL_CONTEXT;
    private static final Key<CachedValue<Set<String>>> POSSIBLE_BOXED_HOLDER_TYPES;
    private static final Set<String> PRIMITIVE_TYPES;
    private static final Set<String> PRIMITIVE_WRAPPER_FQNS;
    private static final Set<String> PRIMITIVE_WRAPPER_SIMPLE_NAMES;
    public static final int SHORT_RANK = 2;
    private static final ObjectIntMap<PsiType> TYPE_TO_RANK_MAP;
    private static final Key<PsiType> UPPER_BOUND;
    private static final Map<Class<?>, PsiType> WRAPPER_TO_PRIMITIVE;
    private static final RecursionGuard<PsiType> ourGuard;
    private static final Logger LOG = Logger.getInstance((Class<?>) TypeConversionUtil.class);
    private static final boolean[][] IS_ASSIGNABLE_BIT_SET = {new boolean[]{true, true, false, true, true, true, true}, new boolean[]{false, true, false, true, true, true, true}, new boolean[]{false, false, true, true, true, true, true}, new boolean[]{false, false, false, true, true, true, true}, new boolean[]{false, false, false, false, true, true, true}, new boolean[]{false, false, false, false, false, true, true}, new boolean[]{false, false, false, false, false, false, true}};

    @interface TypeRank {
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001b A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:71:0x013b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 12 && i != 54 && i != 75) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        case 90:
                                        case 91:
                                        case 92:
                                        case 93:
                                            break;
                                        default:
                                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                            break;
                                    }
                                case 77:
                                case 78:
                                case 79:
                                    str = "@NotNull method %s.%s must not return null";
                                    break;
                            }
                        case 67:
                        case 68:
                        case 69:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 16:
                case 17:
                case 18:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 12 && i != 54 && i != 75) {
            switch (i) {
                case 16:
                case 17:
                case 18:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 67:
                        case 68:
                        case 69:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 77:
                                case 78:
                                case 79:
                                    i2 = 2;
                                    break;
                                default:
                                    switch (i) {
                                        case 90:
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
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i != 1 && i != 3) {
            switch (i) {
                case 5:
                    objArr[0] = "toType";
                    break;
                case 6:
                case 8:
                case 11:
                    objArr[0] = "sealedClass";
                    break;
                case 7:
                case 9:
                case 13:
                case 53:
                case 100:
                case 101:
                    objArr[0] = "psiClass";
                    break;
                case 10:
                case 87:
                    objArr[0] = "visited";
                    break;
                case 12:
                case 16:
                case 17:
                case 18:
                case 54:
                case 67:
                case 68:
                case 69:
                case 75:
                case 77:
                case 78:
                case 79:
                case 90:
                case 91:
                case 92:
                case 93:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/TypeConversionUtil";
                    break;
                case 14:
                    objArr[0] = "sealedClasses";
                    break;
                case 15:
                case 83:
                case 84:
                case 86:
                case 98:
                    objArr[0] = "typeParameter";
                    break;
                case 19:
                    objArr[0] = "baseResult";
                    break;
                case 20:
                    objArr[0] = "derived";
                    break;
                case 21:
                    objArr[0] = "manager";
                    break;
                case 22:
                case 63:
                case 66:
                case 72:
                    objArr[0] = "derivedSubstitutor";
                    break;
                case 23:
                    objArr[0] = "languageLevel";
                    break;
                case 24:
                    objArr[0] = "type1";
                    break;
                case 25:
                    objArr[0] = "type2";
                    break;
                case 26:
                case 29:
                    objArr[0] = "aClass";
                    break;
                case 27:
                case 30:
                    objArr[0] = "substitutor1";
                    break;
                case 28:
                case 31:
                    objArr[0] = "substitutor2";
                    break;
                case 32:
                case 37:
                case 76:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 33:
                case 34:
                    objArr[0] = "tokenType";
                    break;
                case 35:
                case 36:
                    objArr[0] = "token";
                    break;
                case 38:
                case 40:
                case 42:
                case 44:
                case 48:
                case 51:
                    objArr[0] = "left";
                    break;
                case 39:
                case 41:
                case 43:
                case 47:
                case 49:
                case 52:
                    objArr[0] = "right";
                    break;
                case 45:
                    objArr[0] = "rightWildcardType";
                    break;
                case 46:
                    objArr[0] = "wildcardType";
                    break;
                case 50:
                    objArr[0] = SpecTypePair.NAME_types;
                    break;
                case 55:
                case 57:
                    objArr[0] = "leftResult";
                    break;
                case 56:
                case 58:
                    objArr[0] = "rightResult";
                    break;
                case 59:
                    objArr[0] = "typeLeft";
                    break;
                case 60:
                    objArr[0] = "typeRight";
                    break;
                case 61:
                    objArr[0] = "superClassCandidate";
                    break;
                case 62:
                    objArr[0] = "derivedClassCandidate";
                    break;
                case 64:
                case 70:
                case 73:
                    objArr[0] = "superClass";
                    break;
                case 65:
                case 71:
                    objArr[0] = "derivedClass";
                    break;
                case 74:
                    objArr[0] = "classType";
                    break;
                case 80:
                case 81:
                    objArr[0] = "typeName";
                    break;
                case 82:
                    objArr[0] = "fullyQualifiedName";
                    break;
                case 85:
                case 88:
                case 89:
                    objArr[0] = "beforeSubstitutor";
                    break;
                case 94:
                    objArr[0] = MediaTrack.ROLE_SIGN;
                    break;
                case 95:
                    objArr[0] = "text";
                    break;
                case 96:
                    objArr[0] = "p1";
                    break;
                case 97:
                    objArr[0] = "p2";
                    break;
                case 99:
                case 102:
                    objArr[0] = "parameter";
                    break;
                case 103:
                    objArr[0] = "upperBound";
                    break;
                case 104:
                    objArr[0] = "operand";
                    break;
                case 105:
                    objArr[0] = "o";
                    break;
                default:
                    objArr[0] = "fromType";
                    break;
            }
        } else {
            objArr[0] = "toType";
        }
        if (i == 12) {
            objArr[1] = "findDirectSubClassesInFile";
        } else if (i == 54) {
            objArr[1] = "getAllBoxedTypeSupers";
        } else if (i != 75) {
            switch (i) {
                case 16:
                case 17:
                case 18:
                    objArr[1] = "obtainSafeSuperType";
                    break;
                default:
                    switch (i) {
                        case 67:
                        case 68:
                        case 69:
                            objArr[1] = "getSuperClassSubstitutor";
                            break;
                        default:
                            switch (i) {
                                case 77:
                                case 78:
                                case 79:
                                    objArr[1] = "unbox";
                                    break;
                                default:
                                    switch (i) {
                                        case 90:
                                        case 91:
                                        case 92:
                                        case 93:
                                            objArr[1] = "unboxAndBalanceTypes";
                                            break;
                                        default:
                                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/TypeConversionUtil";
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "binaryNumericPromotion";
        }
        switch (i) {
            case 4:
            case 5:
                objArr[2] = "isNarrowingReferenceConversionAllowed";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "canConvertSealedTo";
                break;
            case 11:
                objArr[2] = "findDirectSubClassesInFile";
                break;
            case 12:
            case 16:
            case 17:
            case 18:
            case 54:
            case 67:
            case 68:
            case 69:
            case 75:
            case 77:
            case 78:
            case 79:
            case 90:
            case 91:
            case 92:
            case 93:
                break;
            case 13:
            case 14:
                objArr[2] = "subClassExtendsClass";
                break;
            case 15:
                objArr[2] = "obtainSafeSuperType";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "checkSuperTypesWithDifferentTypeArguments";
                break;
            case 24:
            case 25:
                objArr[2] = "areSameParameterTypes";
                break;
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                objArr[2] = "areSameArgumentTypes";
                break;
            case 32:
                objArr[2] = "getTypeRank";
                break;
            case 33:
            case 34:
                objArr[2] = "isBinaryOperatorApplicable";
                break;
            case 35:
            case 36:
            case 37:
                objArr[2] = "isUnaryOperatorApplicable";
                break;
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
                objArr[2] = "isAssignable";
                break;
            case 44:
            case 45:
                objArr[2] = "isAssignableFromWildcard";
                break;
            case 46:
            case 47:
                objArr[2] = "isAssignableToWildcard";
                break;
            case 48:
            case 49:
            case 50:
                objArr[2] = "isUnboxable";
                break;
            case 51:
            case 52:
                objArr[2] = "isBoxable";
                break;
            case 53:
                objArr[2] = "getAllBoxedTypeSupers";
                break;
            case 55:
            case 56:
                objArr[2] = "isClassAssignable";
                break;
            case 57:
            case 58:
                objArr[2] = "typeParametersAgree";
                break;
            case 59:
            case 60:
                objArr[2] = "typesAgree";
                break;
            case 61:
            case 62:
            case 63:
                objArr[2] = "getClassSubstitutor";
                break;
            case 64:
            case 65:
            case 66:
            case 73:
            case 74:
                objArr[2] = "getSuperClassSubstitutor";
                break;
            case 70:
            case 71:
            case 72:
                objArr[2] = "getMaybeSuperClassSubstitutor";
                break;
            case 76:
                objArr[2] = "unbox";
                break;
            case 80:
                objArr[2] = "isIntegerNumber";
                break;
            case 81:
                objArr[2] = "isPrimitive";
                break;
            case 82:
                objArr[2] = "isPrimitiveWrapper";
                break;
            case 83:
            case 84:
            case 85:
                objArr[2] = "typeParameterErasure";
                break;
            case 86:
            case 87:
            case 88:
                objArr[2] = "typeParameterErasureInner";
                break;
            case 89:
                objArr[2] = "erasure";
                break;
            case 94:
                objArr[2] = "calcTypeForBinaryExpression";
                break;
            case 95:
                objArr[2] = "isFPZero";
                break;
            case 96:
            case 97:
                objArr[2] = "areSameFreshVariables";
                break;
            case 98:
                objArr[2] = "isFreshVariable";
                break;
            case 99:
                objArr[2] = "markAsFreshVariable";
                break;
            case 100:
                objArr[2] = "getInferredUpperBoundForSynthetic";
                break;
            case 101:
                objArr[2] = "getInferredLowerBoundForSynthetic";
                break;
            case 102:
            case 103:
                objArr[2] = "setInferredBoundsForSynthetic";
                break;
            case 104:
                objArr[2] = "cast";
                break;
            case 105:
                objArr[2] = "wrapperToPrimitive";
                break;
            default:
                objArr[2] = "areTypesConvertible";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12 && i != 54 && i != 75) {
            switch (i) {
                case 16:
                case 17:
                case 18:
                    break;
                default:
                    switch (i) {
                        case 67:
                        case 68:
                        case 69:
                            break;
                        default:
                            switch (i) {
                                case 77:
                                case 78:
                                case 79:
                                    break;
                                default:
                                    switch (i) {
                                        case 90:
                                        case 91:
                                        case 92:
                                        case 93:
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

    public static boolean isNumericType(int i) {
        return i <= 7;
    }

    static {
        ObjectIntHashMap objectIntHashMap = new ObjectIntHashMap();
        TYPE_TO_RANK_MAP = objectIntHashMap;
        NULL_TYPE = new PsiEllipsisType(PsiType.NULL) { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayType, org.jetbrains.kotlin.com.intellij.psi.PsiType
            public boolean isValid() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType, org.jetbrains.kotlin.com.intellij.psi.PsiArrayType, org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
            public String getPresentableText(boolean z) {
                return "FAKE TYPE";
            }
        };
        ORIGINAL_CONTEXT = Key.create("ORIGINAL_CONTEXT");
        LOWER_BOUND = Key.create("LowBound");
        UPPER_BOUND = Key.create("UpperBound");
        objectIntHashMap.put(PsiType.BYTE, 1);
        objectIntHashMap.put(PsiType.SHORT, 2);
        objectIntHashMap.put(PsiType.CHAR, 3);
        objectIntHashMap.put(PsiType.INT, 4);
        objectIntHashMap.put(PsiType.LONG, 5);
        objectIntHashMap.put(PsiType.FLOAT, 6);
        objectIntHashMap.put(PsiType.DOUBLE, 7);
        objectIntHashMap.put(PsiType.BOOLEAN, 10);
        POSSIBLE_BOXED_HOLDER_TYPES = Key.create("Types that may be possibly assigned from primitive ones");
        ourGuard = RecursionManager.createGuard("isAssignable");
        HashSet hashSet = new HashSet(5);
        INTEGER_NUMBER_TYPES = hashSet;
        hashSet.add(PsiType.BYTE.getName());
        hashSet.add(PsiType.CHAR.getName());
        hashSet.add(PsiType.LONG.getName());
        hashSet.add(PsiType.INT.getName());
        hashSet.add(PsiType.SHORT.getName());
        HashSet hashSet2 = new HashSet(9);
        PRIMITIVE_TYPES = hashSet2;
        hashSet2.add(PsiType.VOID.getName());
        hashSet2.add(PsiType.BYTE.getName());
        hashSet2.add(PsiType.CHAR.getName());
        hashSet2.add(PsiType.DOUBLE.getName());
        hashSet2.add(PsiType.FLOAT.getName());
        hashSet2.add(PsiType.LONG.getName());
        hashSet2.add(PsiType.INT.getName());
        hashSet2.add(PsiType.SHORT.getName());
        hashSet2.add(PsiType.BOOLEAN.getName());
        Set<String> setImmutableSet = ContainerUtil.immutableSet(CommonClassNames.JAVA_LANG_BYTE, CommonClassNames.JAVA_LANG_CHARACTER, CommonClassNames.JAVA_LANG_DOUBLE, CommonClassNames.JAVA_LANG_FLOAT, CommonClassNames.JAVA_LANG_LONG, CommonClassNames.JAVA_LANG_INTEGER, CommonClassNames.JAVA_LANG_SHORT, CommonClassNames.JAVA_LANG_BOOLEAN);
        PRIMITIVE_WRAPPER_FQNS = setImmutableSet;
        PRIMITIVE_WRAPPER_SIMPLE_NAMES = ContainerUtil.map2Set(setImmutableSet, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return StringUtil.getShortName((String) obj);
            }
        });
        HashMap map = new HashMap(8);
        WRAPPER_TO_PRIMITIVE = map;
        map.put(Boolean.class, PsiType.BOOLEAN);
        map.put(Byte.class, PsiType.BYTE);
        map.put(Character.class, PsiType.CHAR);
        map.put(Short.class, PsiType.SHORT);
        map.put(Integer.class, PsiType.INT);
        map.put(Long.class, PsiType.LONG);
        map.put(Float.class, PsiType.FLOAT);
        map.put(Double.class, PsiType.DOUBLE);
    }

    private TypeConversionUtil() {
    }

    public static boolean areTypesConvertible(PsiType psiType, PsiType psiType2) {
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(1);
        }
        return areTypesConvertible(psiType, psiType2, null);
    }

    public static boolean areTypesConvertible(PsiType psiType, PsiType psiType2, LanguageLevel languageLevel) {
        PsiClass psiClassResolve;
        if (psiType == null) {
            $$$reportNull$$$0(2);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(3);
        }
        if (psiType == psiType2) {
            return true;
        }
        boolean zIsPrimitiveAndNotNull = isPrimitiveAndNotNull(psiType);
        boolean zIsPrimitiveAndNotNull2 = isPrimitiveAndNotNull(psiType2);
        if (zIsPrimitiveAndNotNull || zIsPrimitiveAndNotNull2) {
            if (isVoidType(psiType) || isVoidType(psiType2)) {
                return false;
            }
            int typeRank = getTypeRank(psiType);
            int typeRank2 = getTypeRank(psiType2);
            if (!zIsPrimitiveAndNotNull2) {
                if (typeRank == typeRank2) {
                    return true;
                }
                if (psiType2 instanceof PsiIntersectionType) {
                    for (PsiType psiType3 : ((PsiIntersectionType) psiType2).getConjuncts()) {
                        if (!areTypesConvertible(psiType, psiType3)) {
                            return false;
                        }
                    }
                    return true;
                }
                if (!(psiType2 instanceof PsiClassType) || (psiClassResolve = ((PsiClassType) psiType2).resolve()) == null || (psiClassResolve instanceof PsiTypeParameter)) {
                    return false;
                }
                PsiClassType boxedType = ((PsiPrimitiveType) psiType).getBoxedType(psiClassResolve.getManager(), psiType2.getResolveScope());
                return boxedType != null && areTypesConvertible(boxedType, psiType2);
            }
            if (zIsPrimitiveAndNotNull) {
                if (typeRank != typeRank2) {
                    return typeRank <= 7 && typeRank2 <= 7;
                }
                return true;
            }
            if ((typeRank == 2 || typeRank == 1) && typeRank2 == 3) {
                return false;
            }
            PsiType psiTypeUncapture = uncapture(psiType);
            if (psiTypeUncapture instanceof PsiClassType) {
                if (languageLevel == null) {
                    languageLevel = ((PsiClassType) psiTypeUncapture).getLanguageLevel();
                }
                if (languageLevel.isAtLeast(LanguageLevel.JDK_1_7)) {
                    PsiClass psiClassResolve2 = ((PsiClassType) psiTypeUncapture).resolve();
                    if (psiClassResolve2 == null) {
                        return false;
                    }
                    PsiClassType boxedType2 = ((PsiPrimitiveType) psiType2).getBoxedType(psiClassResolve2.getManager(), psiClassResolve2.getResolveScope());
                    if (boxedType2 != null && isNarrowingReferenceConversionAllowed(psiTypeUncapture, boxedType2)) {
                        return true;
                    }
                }
            }
            if (typeRank != typeRank2) {
                return typeRank < typeRank2 && typeRank2 <= 7;
            }
            return true;
        }
        if (isAssignable(psiType2, psiType) || isNullType(psiType) || isNullType(psiType2)) {
            return true;
        }
        return isNarrowingReferenceConversionAllowed(psiType, psiType2);
    }

    private static boolean isNarrowingReferenceConversionAllowed(PsiType psiType, PsiType psiType2) {
        PsiSubstitutor substitutor;
        PsiClassType.ClassResolveResult classResolveResult;
        PsiClass psiClass;
        if (psiType == null) {
            $$$reportNull$$$0(4);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(5);
        }
        PsiType psiTypeUncapture = uncapture(psiType2);
        PsiType psiTypeUncapture2 = uncapture(psiType);
        if ((psiTypeUncapture instanceof PsiPrimitiveType) || (psiTypeUncapture2 instanceof PsiPrimitiveType)) {
            return psiTypeUncapture2.equals(psiTypeUncapture);
        }
        if (!(psiTypeUncapture instanceof PsiDiamondType) && !(psiTypeUncapture2 instanceof PsiDiamondType)) {
            boolean z = psiTypeUncapture instanceof PsiArrayType;
            if (z && !(psiTypeUncapture2 instanceof PsiArrayType)) {
                if (psiTypeUncapture2 instanceof PsiClassType) {
                    PsiClass psiClassResolve = ((PsiClassType) psiTypeUncapture2).resolve();
                    if (psiClassResolve instanceof PsiTypeParameter) {
                        for (PsiClassType psiClassType : psiClassResolve.getExtendsListTypes()) {
                            if (!isNarrowingReferenceConversionAllowed(psiClassType, psiTypeUncapture)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return isAssignable(psiTypeUncapture2, psiTypeUncapture);
            }
            if (psiTypeUncapture2 instanceof PsiArrayType) {
                if (psiTypeUncapture instanceof PsiClassType) {
                    PsiClass psiClassResolve2 = ((PsiClassType) psiTypeUncapture).resolve();
                    if (psiClassResolve2 instanceof PsiTypeParameter) {
                        PsiClassType[] extendsListTypes = psiClassResolve2.getExtendsListTypes();
                        for (PsiClassType psiClassType2 : extendsListTypes) {
                            if (!areTypesConvertible(psiTypeUncapture2, psiClassType2)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return z && isNarrowingReferenceConversionAllowed(((PsiArrayType) psiTypeUncapture2).getComponentType(), ((PsiArrayType) psiTypeUncapture).getComponentType());
            }
            if (psiTypeUncapture2 instanceof PsiIntersectionType) {
                for (PsiType psiType3 : ((PsiIntersectionType) psiTypeUncapture2).getConjuncts()) {
                    if (isNarrowingReferenceConversionAllowed(psiType3, psiTypeUncapture)) {
                        return true;
                    }
                }
                return false;
            }
            if (psiTypeUncapture instanceof PsiIntersectionType) {
                if (!(psiTypeUncapture2 instanceof PsiClassType) || !((PsiClassType) psiTypeUncapture2).getLanguageLevel().isAtLeast(LanguageLevel.JDK_1_8)) {
                    return false;
                }
                for (PsiType psiType4 : ((PsiIntersectionType) psiTypeUncapture).getConjuncts()) {
                    if (!isNarrowingReferenceConversionAllowed(psiTypeUncapture2, psiType4)) {
                        return false;
                    }
                }
                return true;
            }
            if (psiTypeUncapture2 instanceof PsiDisjunctionType) {
                return isNarrowingReferenceConversionAllowed(((PsiDisjunctionType) psiTypeUncapture2).getLeastUpperBound(), psiTypeUncapture);
            }
            if (psiTypeUncapture instanceof PsiDisjunctionType) {
                return false;
            }
            if (psiTypeUncapture2 instanceof PsiWildcardType) {
                PsiWildcardType psiWildcardType = (PsiWildcardType) psiTypeUncapture2;
                PsiType bound = psiWildcardType.getBound();
                if (bound == null) {
                    return true;
                }
                if (psiWildcardType.isSuper()) {
                    return isAssignable(psiTypeUncapture, bound);
                }
                return isNarrowingReferenceConversionAllowed(bound, psiTypeUncapture);
            }
            if (psiTypeUncapture instanceof PsiWildcardType) {
                PsiWildcardType psiWildcardType2 = (PsiWildcardType) psiTypeUncapture;
                if (psiWildcardType2.isSuper()) {
                    return false;
                }
                PsiType bound2 = psiWildcardType2.getBound();
                return bound2 == null || isNarrowingReferenceConversionAllowed(psiTypeUncapture2, bound2);
            }
            if (isAssignable(psiTypeUncapture2, psiTypeUncapture)) {
                return true;
            }
            if ((psiTypeUncapture2 instanceof PsiClassType) && (psiTypeUncapture instanceof PsiClassType)) {
                PsiClassType psiClassType3 = (PsiClassType) psiTypeUncapture2;
                PsiClassType psiClassType4 = (PsiClassType) psiTypeUncapture;
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType3.resolveGenerics();
                PsiClass element = classResolveResultResolveGenerics.getElement();
                if (element == null) {
                    return false;
                }
                if (element instanceof PsiTypeParameter) {
                    return isNarrowingReferenceConversionAllowed(obtainSafeSuperType((PsiTypeParameter) element), psiTypeUncapture);
                }
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = psiClassType4.resolveGenerics();
                PsiClass element2 = classResolveResultResolveGenerics2.getElement();
                if (element2 == null) {
                    return false;
                }
                if (element2 instanceof PsiTypeParameter) {
                    return isNarrowingReferenceConversionAllowed(psiTypeUncapture2, obtainSafeSuperType((PsiTypeParameter) element2));
                }
                PsiManager manager = element.getManager();
                LanguageLevel languageLevel = psiClassType4.getLanguageLevel();
                if (languageLevel.isAtLeast(LanguageLevel.JDK_16_PREVIEW) && (element.isInterface() || element2.isInterface())) {
                    if (element.hasModifierProperty("sealed")) {
                        if (!canConvertSealedTo(element, element2)) {
                            return false;
                        }
                    } else if (element2.hasModifierProperty("sealed") && !canConvertSealedTo(element2, element)) {
                        return false;
                    }
                }
                if (!element.isInterface()) {
                    if (element2.isInterface()) {
                        return (!element.hasModifierProperty("final") || element.isInheritor(element2, true)) && checkSuperTypesWithDifferentTypeArguments(classResolveResultResolveGenerics2, element, manager, classResolveResultResolveGenerics.getSubstitutor(), null, languageLevel);
                    }
                    if (manager.areElementsEquivalent(element, element2)) {
                        return areSameParameterTypes(psiClassType3, psiClassType4);
                    }
                    if (element2.isInheritor(element, true)) {
                        return checkSuperTypesWithDifferentTypeArguments(classResolveResultResolveGenerics, element2, manager, classResolveResultResolveGenerics2.getSubstitutor(), null, languageLevel);
                    }
                    if (element.isInheritor(element2, true)) {
                        return checkSuperTypesWithDifferentTypeArguments(classResolveResultResolveGenerics2, element, manager, classResolveResultResolveGenerics.getSubstitutor(), null, languageLevel);
                    }
                    return false;
                }
                if (!element2.isInterface()) {
                    if (!element2.hasModifierProperty("final")) {
                        return checkSuperTypesWithDifferentTypeArguments(classResolveResultResolveGenerics, element2, manager, classResolveResultResolveGenerics2.getSubstitutor(), null, languageLevel);
                    }
                    PsiSubstitutor maybeSuperClassSubstitutor = getMaybeSuperClassSubstitutor(element, element2, classResolveResultResolveGenerics2.getSubstitutor());
                    return maybeSuperClassSubstitutor != null && areSameArgumentTypes(element, classResolveResultResolveGenerics.getSubstitutor(), maybeSuperClassSubstitutor);
                }
                if (languageLevel.compareTo(LanguageLevel.JDK_1_5) < 0) {
                    Collection<HierarchicalMethodSignature> visibleSignatures = element.getVisibleSignatures();
                    Collection<HierarchicalMethodSignature> visibleSignatures2 = element2.getVisibleSignatures();
                    for (HierarchicalMethodSignature hierarchicalMethodSignature : visibleSignatures) {
                        for (HierarchicalMethodSignature hierarchicalMethodSignature2 : visibleSignatures2) {
                            if (hierarchicalMethodSignature.equals(hierarchicalMethodSignature2)) {
                                PsiType returnType = hierarchicalMethodSignature.getMethod().getReturnType();
                                PsiType returnType2 = hierarchicalMethodSignature2.getMethod().getReturnType();
                                if (returnType != null && returnType2 != null && !returnType.equals(returnType2)) {
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                }
                if (element2.isInheritor(element, true)) {
                    psiClass = element2;
                    substitutor = classResolveResultResolveGenerics2.getSubstitutor();
                    classResolveResult = classResolveResultResolveGenerics;
                } else {
                    substitutor = classResolveResultResolveGenerics.getSubstitutor();
                    classResolveResult = classResolveResultResolveGenerics2;
                    psiClass = element;
                }
                return checkSuperTypesWithDifferentTypeArguments(classResolveResult, psiClass, manager, substitutor, null, languageLevel);
            }
        }
        return false;
    }

    public static boolean canConvertSealedTo(PsiClass psiClass, PsiClass psiClass2) {
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(7);
        }
        LOG.assertTrue(psiClass.isInterface() || psiClass2.isInterface());
        return canConvertSealedTo(psiClass, psiClass2, new HashSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canConvertSealedTo(PsiClass psiClass, final PsiClass psiClass2, final Set<PsiClass> set) {
        boolean zAnyMatch;
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(9);
        }
        if (set == null) {
            $$$reportNull$$$0(10);
        }
        if (set.contains(psiClass)) {
            return true;
        }
        set.add(psiClass);
        PsiReferenceList permitsList = psiClass.getPermitsList();
        final SmartList smartList = new SmartList();
        if (permitsList == null) {
            zAnyMatch = findDirectSubClassesInFile(psiClass).stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return TypeConversionUtil.subClassExtendsClass((PsiClass) obj, psiClass2, smartList);
                }
            });
        } else {
            zAnyMatch = Arrays.stream(permitsList.getReferencedTypes()).map(new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((PsiClassType) obj).resolve();
                }
            }).anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return TypeConversionUtil.subClassExtendsClass((PsiClass) obj, psiClass2, smartList);
                }
            });
        }
        return zAnyMatch || smartList.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TypeConversionUtil.canConvertSealedTo((PsiClass) obj, psiClass2, set);
            }
        });
    }

    private static Set<PsiClass> findDirectSubClassesInFile(final PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(11);
        }
        final HashSet hashSet = new HashSet();
        psiClass.getContainingFile().accept(new JavaElementVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil.2
            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitJavaFile(PsiJavaFile psiJavaFile) {
                for (PsiClass psiClass2 : psiJavaFile.getClasses()) {
                    visitClass(psiClass2);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitClass(PsiClass psiClass2) {
                for (PsiClass psiClass3 : psiClass2.getInnerClasses()) {
                    visitClass(psiClass3);
                }
                if (psiClass2.isInheritor(psiClass, false)) {
                    hashSet.add(psiClass2);
                }
            }
        });
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean subClassExtendsClass(PsiClass psiClass, PsiClass psiClass2, List<PsiClass> list) {
        if (psiClass2 == null) {
            $$$reportNull$$$0(13);
        }
        if (list == null) {
            $$$reportNull$$$0(14);
        }
        if (psiClass == null) {
            return false;
        }
        if (psiClass.hasModifierProperty("non-sealed") || InheritanceUtil.isInheritorOrSelf(psiClass, psiClass2, true)) {
            return true;
        }
        if (psiClass.hasModifierProperty("sealed")) {
            list.add(psiClass);
        }
        return false;
    }

    private static PsiClassType obtainSafeSuperType(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(15);
        }
        PsiClassType psiClassType = psiTypeParameter.getSuperTypes()[0];
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null) {
            if (psiClassType == null) {
                $$$reportNull$$$0(18);
            }
            return psiClassType;
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiTypeParameter.getProject());
        if ((element instanceof PsiTypeParameter) && InheritanceUtil.getCircularClass(element) != null) {
            PsiClassType psiClassTypeCreateTypeByFQClassName = elementFactory.createTypeByFQClassName(CommonClassNames.JAVA_LANG_OBJECT, psiTypeParameter.getResolveScope());
            if (psiClassTypeCreateTypeByFQClassName == null) {
                $$$reportNull$$$0(16);
            }
            return psiClassTypeCreateTypeByFQClassName;
        }
        PsiClassType psiClassTypeCreateType = elementFactory.createType(element, classResolveResultResolveGenerics.getSubstitutor().put(psiTypeParameter, null));
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(17);
        }
        return psiClassTypeCreateType;
    }

    private static boolean checkSuperTypesWithDifferentTypeArguments(PsiClassType.ClassResolveResult classResolveResult, PsiClass psiClass, PsiManager psiManager, PsiSubstitutor psiSubstitutor, Set<? super PsiClass> set, LanguageLevel languageLevel) {
        PsiSubstitutor superClassSubstitutor = psiSubstitutor;
        if (classResolveResult == null) {
            $$$reportNull$$$0(19);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(20);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(21);
        }
        if (superClassSubstitutor == null) {
            $$$reportNull$$$0(22);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(23);
        }
        if ((set != null && set.contains(psiClass)) || languageLevel.compareTo(LanguageLevel.JDK_1_5) < 0) {
            return true;
        }
        PsiClass element = classResolveResult.getElement();
        PsiClass[] supers = psiClass.getSupers();
        if (psiManager.areElementsEquivalent(element, psiClass)) {
            return areSameArgumentTypes(psiClass, classResolveResult.getSubstitutor(), getSuperClassSubstitutor(psiClass, psiClass, superClassSubstitutor), 1);
        }
        PsiSubstitutor maybeSuperClassSubstitutor = getMaybeSuperClassSubstitutor(psiClass, element, classResolveResult.getSubstitutor());
        if (maybeSuperClassSubstitutor != null) {
            superClassSubstitutor = getSuperClassSubstitutor(psiClass, psiClass, superClassSubstitutor);
            if (!areSameArgumentTypes(psiClass, maybeSuperClassSubstitutor, superClassSubstitutor)) {
                return false;
            }
        }
        PsiSubstitutor psiSubstitutor2 = superClassSubstitutor;
        Set<? super PsiClass> hashSet = set == null ? new HashSet() : set;
        hashSet.add(psiClass);
        for (PsiClass psiClass2 : supers) {
            if (!checkSuperTypesWithDifferentTypeArguments(classResolveResult, psiClass2, psiManager, getSuperClassSubstitutor(psiClass2, psiClass, psiSubstitutor2), hashSet, languageLevel)) {
                return false;
            }
        }
        return true;
    }

    private static boolean areSameParameterTypes(PsiClassType psiClassType, PsiClassType psiClassType2) {
        if (psiClassType == null) {
            $$$reportNull$$$0(24);
        }
        if (psiClassType2 == null) {
            $$$reportNull$$$0(25);
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = psiClassType2.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        PsiClass element2 = classResolveResultResolveGenerics2.getElement();
        return element != null && element2 != null && element.getManager().areElementsEquivalent(element, element2) && areSameArgumentTypes(element, classResolveResultResolveGenerics.getSubstitutor(), classResolveResultResolveGenerics2.getSubstitutor(), 1);
    }

    private static boolean areSameArgumentTypes(PsiClass psiClass, PsiSubstitutor psiSubstitutor, PsiSubstitutor psiSubstitutor2) {
        if (psiClass == null) {
            $$$reportNull$$$0(26);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(27);
        }
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(28);
        }
        return areSameArgumentTypes(psiClass, psiSubstitutor, psiSubstitutor2, 0);
    }

    private static boolean areSameArgumentTypes(PsiClass psiClass, PsiSubstitutor psiSubstitutor, PsiSubstitutor psiSubstitutor2, int i) {
        if (psiClass == null) {
            $$$reportNull$$$0(29);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(30);
        }
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(31);
        }
        for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(psiClass)) {
            PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiTypeParameter);
            PsiType psiTypeSubstitute2 = psiSubstitutor2.substitute(psiTypeParameter);
            if (psiTypeSubstitute == null || psiTypeSubstitute2 == null) {
                break;
            }
            if (TypesDistinctProver.provablyDistinct(psiTypeSubstitute, psiTypeSubstitute2, i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimitiveAndNotNull(PsiType psiType) {
        return (psiType instanceof PsiPrimitiveType) && !isNullType(psiType);
    }

    public static boolean isEnumType(PsiType psiType) {
        PsiType psiTypeUncapture = uncapture(psiType);
        if (!(psiTypeUncapture instanceof PsiClassType)) {
            return false;
        }
        PsiClass psiClassResolve = ((PsiClassType) psiTypeUncapture).resolve();
        if (psiClassResolve instanceof PsiTypeParameter) {
            return InheritanceUtil.isInheritor(psiClassResolve, true, CommonClassNames.JAVA_LANG_ENUM);
        }
        return psiClassResolve != null && psiClassResolve.isEnum();
    }

    public static boolean isNullType(PsiType psiType) {
        return PsiType.NULL.equals(psiType);
    }

    public static boolean isFloatOrDoubleType(PsiType psiType) {
        return isFloatType(psiType) || isDoubleType(psiType);
    }

    public static boolean isDoubleType(PsiType psiType) {
        PsiType psiTypeUncapture = uncapture(psiType);
        return PsiType.DOUBLE.equals(psiTypeUncapture) || PsiType.DOUBLE.equals(PsiPrimitiveType.getUnboxedType(psiTypeUncapture));
    }

    public static boolean isFloatType(PsiType psiType) {
        PsiType psiTypeUncapture = uncapture(psiType);
        return PsiType.FLOAT.equals(psiTypeUncapture) || PsiType.FLOAT.equals(PsiPrimitiveType.getUnboxedType(psiTypeUncapture));
    }

    public static boolean isLongType(PsiType psiType) {
        PsiType psiTypeUncapture = uncapture(psiType);
        return PsiType.LONG.equals(psiTypeUncapture) || PsiType.LONG.equals(PsiPrimitiveType.getUnboxedType(psiTypeUncapture));
    }

    public static boolean isVoidType(PsiType psiType) {
        return PsiType.VOID.equals(psiType);
    }

    public static boolean isBooleanType(PsiType psiType) {
        PsiType psiTypeUncapture = uncapture(psiType);
        return PsiType.BOOLEAN.equals(psiTypeUncapture) || PsiType.BOOLEAN.equals(PsiPrimitiveType.getUnboxedType(psiTypeUncapture));
    }

    private static PsiType uncapture(PsiType psiType) {
        while (psiType instanceof PsiCapturedWildcardType) {
            psiType = ((PsiCapturedWildcardType) psiType).getUpperBound();
        }
        return psiType;
    }

    public static boolean isNumericType(PsiType psiType) {
        return psiType != null && isNumericType(getTypeRank(psiType));
    }

    public static boolean isIntegralNumberType(PsiType psiType) {
        return psiType != null && getTypeRank(psiType) <= 5;
    }

    public static int getTypeRank(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(32);
        }
        PsiType psiTypeUncapture = uncapture(psiType);
        PsiPrimitiveType unboxedType = PsiPrimitiveType.getUnboxedType(psiTypeUncapture);
        if (unboxedType != null) {
            psiTypeUncapture = unboxedType;
        }
        int i = TYPE_TO_RANK_MAP.get(psiTypeUncapture);
        if (i != -1) {
            return i;
        }
        return psiTypeUncapture.equalsToText(CommonClassNames.JAVA_LANG_STRING) ? 100 : 1000;
    }

    public static boolean isBinaryOperatorApplicable(IElementType iElementType, PsiExpression psiExpression, PsiExpression psiExpression2, boolean z) {
        if (iElementType == null) {
            $$$reportNull$$$0(33);
        }
        if (psiExpression == null || psiExpression2 == null) {
            return true;
        }
        return isBinaryOperatorApplicable(iElementType, psiExpression.getType(), psiExpression2.getType(), z);
    }

    /* JADX WARN: Code duplicated, block: B:166:0x01af A[PHI: r4
      0x01af: PHI (r4v1 int) = 
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v4 int)
      (r4v4 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v0 int)
      (r4v6 int)
      (r4v6 int)
     binds: [B:160:0x01a1, B:162:0x01a7, B:163:0x01a9, B:164:0x01ab, B:157:0x0199, B:123:0x0133, B:124:0x0135, B:106:0x0105, B:108:0x010b, B:110:0x0111, B:111:0x0113, B:97:0x00ef, B:99:0x00f5, B:83:0x00cd, B:85:0x00d3, B:74:0x00b4, B:76:0x00ba, B:78:0x00c0, B:80:0x00c6, B:72:0x00ae, B:39:0x0067, B:41:0x006d, B:43:0x0073, B:44:0x0075] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:45:0x0077 A[PHI: r4
      0x0077: PHI (r4v2 int) = (r4v0 int), (r4v0 int), (r4v0 int), (r4v0 int), (r4v0 int), (r4v4 int), (r4v0 int), (r4v6 int) binds: [B:164:0x01ab, B:155:0x0193, B:157:0x0199, B:124:0x0135, B:122:0x0131, B:111:0x0113, B:80:0x00c6, B:44:0x0075] A[DONT_GENERATE, DONT_INLINE]] */
    public static boolean isBinaryOperatorApplicable(IElementType iElementType, PsiType psiType, PsiType psiType2, boolean z) {
        boolean z2;
        if (iElementType == null) {
            $$$reportNull$$$0(34);
        }
        boolean z3 = true;
        if (psiType == null || psiType2 == null) {
            return true;
        }
        int typeRank = getTypeRank(psiType);
        int typeRank2 = getTypeRank(psiType2);
        int iMax = 10;
        if (iElementType == JavaTokenType.LT || iElementType == JavaTokenType.LE || iElementType == JavaTokenType.GT || iElementType == JavaTokenType.GE) {
            if (!isPrimitiveAndNotNullOrWrapper(psiType) || !isPrimitiveAndNotNullOrWrapper(psiType2) || typeRank > 7 || typeRank2 > 7) {
                z2 = false;
            } else {
                z2 = true;
            }
        } else if (iElementType == JavaTokenType.EQEQ || iElementType == JavaTokenType.NE) {
            if (isPrimitiveAndNotNullOrWrapper(psiType) && isPrimitiveAndNotNullOrWrapper(psiType2) && (isPrimitiveAndNotNull(psiType) || isPrimitiveAndNotNull(psiType2))) {
                if ((typeRank > 7 || typeRank2 > 7) && !(typeRank == 10 && typeRank2 == 10)) {
                    z2 = false;
                } else {
                    z2 = true;
                }
            } else {
                if (isPrimitiveAndNotNull(psiType)) {
                    if (!(psiType2 instanceof PsiClassType)) {
                        return false;
                    }
                    LanguageLevel languageLevel = ((PsiClassType) psiType2).getLanguageLevel();
                    return languageLevel.isAtLeast(LanguageLevel.JDK_1_5) && !languageLevel.isAtLeast(LanguageLevel.JDK_1_8) && areTypesConvertible(psiType, psiType2);
                }
                if (isPrimitiveAndNotNull(psiType2)) {
                    if (!(psiType instanceof PsiClassType)) {
                        return false;
                    }
                    LanguageLevel languageLevel2 = ((PsiClassType) psiType).getLanguageLevel();
                    return languageLevel2.isAtLeast(LanguageLevel.JDK_1_7) && !languageLevel2.isAtLeast(LanguageLevel.JDK_1_8) && areTypesConvertible(psiType2, psiType);
                }
                if (areTypesConvertible(psiType, psiType2) || areTypesConvertible(psiType2, psiType)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
        } else if (iElementType == JavaTokenType.PLUS) {
            if (psiType.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
                z2 = !isVoidType(psiType2);
                iMax = 100;
            } else {
                if (psiType2.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
                    if (isVoidType(psiType)) {
                        return false;
                    }
                    return !z || psiType.isAssignableFrom(psiType2);
                }
                if (isPrimitiveAndNotNullOrWrapper(psiType) && isPrimitiveAndNotNullOrWrapper(psiType2)) {
                    iMax = Math.max(typeRank, typeRank2);
                    if (typeRank <= 7 && typeRank2 <= 7) {
                        z2 = true;
                    }
                }
                z2 = false;
            }
        } else if (iElementType == JavaTokenType.ASTERISK || iElementType == JavaTokenType.DIV || iElementType == JavaTokenType.PERC || iElementType == JavaTokenType.MINUS) {
            if (isPrimitiveAndNotNullOrWrapper(psiType) && isPrimitiveAndNotNullOrWrapper(psiType2)) {
                iMax = Math.max(typeRank, typeRank2);
                if (typeRank <= 7 && typeRank2 <= 7) {
                    z2 = true;
                }
            }
            z2 = false;
        } else if (iElementType == JavaTokenType.LTLT || iElementType == JavaTokenType.GTGT || iElementType == JavaTokenType.GTGTGT) {
            if (isPrimitiveAndNotNullOrWrapper(psiType) && isPrimitiveAndNotNullOrWrapper(psiType2)) {
                z2 = typeRank <= 5 && typeRank2 <= 5;
                iMax = 4;
            } else {
                z2 = false;
            }
        } else {
            if (iElementType == JavaTokenType.AND || iElementType == JavaTokenType.OR || iElementType == JavaTokenType.XOR) {
                if (isPrimitiveAndNotNullOrWrapper(psiType) && isPrimitiveAndNotNullOrWrapper(psiType2)) {
                    z2 = (typeRank <= 5 && typeRank2 <= 5) || (isBooleanType(psiType) && isBooleanType(psiType2));
                    if (typeRank <= 5) {
                        iMax = 4;
                    }
                }
            } else if ((iElementType == JavaTokenType.ANDAND || iElementType == JavaTokenType.OROR) && isPrimitiveAndNotNullOrWrapper(psiType) && isPrimitiveAndNotNullOrWrapper(psiType2) && isBooleanType(psiType) && isBooleanType(psiType2)) {
                z2 = true;
            }
            z2 = false;
        }
        if (!z2 || !z || iMax <= 7) {
            return z2;
        }
        if (typeRank != iMax && !psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
            z3 = false;
        }
        return z3;
    }

    public static boolean isPrimitiveAndNotNullOrWrapper(PsiType psiType) {
        PsiType psiTypeUncapture = uncapture(psiType);
        if (psiTypeUncapture instanceof PsiClassType) {
            return PsiPrimitiveType.getUnboxedType(psiTypeUncapture) != null;
        }
        return isPrimitiveAndNotNull(psiTypeUncapture);
    }

    public static boolean isUnaryOperatorApplicable(PsiJavaToken psiJavaToken, PsiExpression psiExpression) {
        PsiType type;
        if (psiJavaToken == null) {
            $$$reportNull$$$0(35);
        }
        return (psiExpression == null || (type = psiExpression.getType()) == null || !isUnaryOperatorApplicable(psiJavaToken, type)) ? false : true;
    }

    public static boolean isUnaryOperatorApplicable(PsiJavaToken psiJavaToken, PsiType psiType) {
        if (psiJavaToken == null) {
            $$$reportNull$$$0(36);
        }
        if (psiType == null) {
            $$$reportNull$$$0(37);
        }
        IElementType tokenType = psiJavaToken.getTokenType();
        int typeRank = getTypeRank(psiType);
        if (tokenType == JavaTokenType.MINUSMINUS || tokenType == JavaTokenType.PLUSPLUS) {
            return typeRank <= 7;
        }
        if (tokenType == JavaTokenType.MINUS || tokenType == JavaTokenType.PLUS) {
            return typeRank <= 7;
        }
        if (tokenType == JavaTokenType.TILDE) {
            return typeRank <= 5;
        }
        if (tokenType == JavaTokenType.EXCL) {
            return typeRank == 10;
        }
        LOG.error("unknown token: " + psiJavaToken);
        return true;
    }

    public static boolean isLValue(PsiExpression psiExpression) {
        PsiExpression indexExpression;
        PsiType type;
        if (psiExpression instanceof PsiReferenceExpression) {
            return ((PsiReferenceExpression) psiExpression).resolve() instanceof PsiVariable;
        }
        if (psiExpression instanceof PsiParenthesizedExpression) {
            return isLValue(((PsiParenthesizedExpression) psiExpression).getExpression());
        }
        if (!(psiExpression instanceof PsiArrayAccessExpression)) {
            return false;
        }
        PsiArrayAccessExpression psiArrayAccessExpression = (PsiArrayAccessExpression) psiExpression;
        return (psiArrayAccessExpression.getArrayExpression().getType() instanceof PsiArrayType) && (indexExpression = psiArrayAccessExpression.getIndexExpression()) != null && (type = indexExpression.getType()) != null && getTypeRank(type) <= 4;
    }

    public static boolean areTypesAssignmentCompatible(PsiType psiType, PsiExpression psiExpression) {
        long jCharValue;
        if (psiType == null || psiExpression == null) {
            return true;
        }
        PsiType type = psiExpression.getType();
        if (type == null) {
            return false;
        }
        if (isAssignable(psiType, type)) {
            return true;
        }
        if ((psiType instanceof PsiClassType) && (psiType = PsiPrimitiveType.getUnboxedType(psiType)) == null) {
            return false;
        }
        int typeRank = getTypeRank(type);
        if ((psiType instanceof PsiPrimitiveType) && (type instanceof PsiPrimitiveType) && typeRank >= 1 && typeRank <= 4) {
            Object objComputeConstantExpression = JavaPsiFacade.getInstance(psiExpression.getProject()).getConstantEvaluationHelper().computeConstantExpression(psiExpression);
            if (objComputeConstantExpression instanceof Number) {
                jCharValue = ((Number) objComputeConstantExpression).longValue();
            } else if (objComputeConstantExpression instanceof Character) {
                jCharValue = ((Character) objComputeConstantExpression).charValue();
            }
            if (PsiType.BYTE.equals(psiType)) {
                return -128 <= jCharValue && jCharValue <= 127;
            }
            if (PsiType.SHORT.equals(psiType)) {
                return -32768 <= jCharValue && jCharValue <= 32767;
            }
            if (PsiType.CHAR.equals(psiType)) {
                return 0 <= jCharValue && jCharValue <= WebSocketProtocol.PAYLOAD_SHORT_MAX;
            }
        }
        return false;
    }

    public static boolean isAssignable(PsiType psiType, PsiType psiType2) {
        if (psiType == null) {
            $$$reportNull$$$0(38);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(39);
        }
        return isAssignable(psiType, psiType2, true);
    }

    public static boolean isAssignable(PsiType psiType, PsiType psiType2, boolean z) {
        if (psiType == null) {
            $$$reportNull$$$0(40);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(41);
        }
        return isAssignable(psiType, psiType2, z, true);
    }

    private static boolean isAssignable(PsiType psiType, PsiType psiType2, boolean z, boolean z2) {
        PsiClass psiClassResolveClassInType;
        if (psiType == null) {
            $$$reportNull$$$0(42);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(43);
        }
        if (psiType == psiType2) {
            return true;
        }
        boolean z3 = psiType instanceof PsiClassType;
        if (z3 && psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) && ((psiType2 instanceof PsiMethodReferenceType) || (psiType2 instanceof PsiLambdaExpressionType))) {
            return false;
        }
        if (psiType.equals(psiType2)) {
            return true;
        }
        if (isNullType(psiType2)) {
            return !(psiType instanceof PsiPrimitiveType) || isNullType(psiType);
        }
        if (psiType2 instanceof PsiMethodReferenceType) {
            PsiMethodReferenceExpression expression = ((PsiMethodReferenceType) psiType2).getExpression();
            if (psiType instanceof PsiLambdaExpressionType) {
                return Comparing.equal(expression.getFunctionalInterfaceType(), ((PsiLambdaExpressionType) psiType).getExpression().getFunctionalInterfaceType());
            }
            if (psiType instanceof PsiMethodReferenceType) {
                return Comparing.equal(expression.getFunctionalInterfaceType(), ((PsiMethodReferenceType) psiType).getExpression().getFunctionalInterfaceType());
            }
            return !(psiType instanceof PsiArrayType) && expression.isAcceptable(psiType);
        }
        if (psiType2 instanceof PsiLambdaExpressionType) {
            PsiLambdaExpression expression2 = ((PsiLambdaExpressionType) psiType2).getExpression();
            if (psiType instanceof PsiLambdaExpressionType) {
                return Comparing.equal(expression2.getFunctionalInterfaceType(), ((PsiLambdaExpressionType) psiType).getExpression().getFunctionalInterfaceType());
            }
            return !(psiType instanceof PsiArrayType) && expression2.isAcceptable(psiType);
        }
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType3 : ((PsiIntersectionType) psiType).getConjuncts()) {
                if (!isAssignable(psiType3, psiType2, z, z2)) {
                    return false;
                }
            }
            return true;
        }
        if (psiType2 instanceof PsiIntersectionType) {
            for (PsiType psiType4 : ((PsiIntersectionType) psiType2).getConjuncts()) {
                if (isAssignable(psiType, psiType4, z, z2)) {
                    return true;
                }
            }
            return false;
        }
        if (psiType2 instanceof PsiCapturedWildcardType) {
            return isAssignable(psiType, ((PsiCapturedWildcardType) psiType2).getUpperBound(z2), z, z2);
        }
        if (psiType instanceof PsiCapturedWildcardType) {
            return psiType.equals(psiType2) || isAssignable(((PsiCapturedWildcardType) psiType).getLowerBound(), psiType2, z, z2);
        }
        if (psiType instanceof PsiWildcardType) {
            return isAssignableToWildcard((PsiWildcardType) psiType, psiType2);
        }
        if (psiType2 instanceof PsiWildcardType) {
            return isAssignableFromWildcard(psiType, (PsiWildcardType) psiType2);
        }
        if (psiType2 instanceof PsiArrayType) {
            if (!(psiType instanceof PsiArrayType)) {
                if ((psiType instanceof PsiPrimitiveType) || PsiUtil.resolveClassInType(psiType) == null || (psiClassResolveClassInType = PsiUtil.resolveClassInType(psiType)) == null) {
                    return false;
                }
                if (psiClassResolveClassInType.isInterface()) {
                    String qualifiedName = psiClassResolveClassInType.getQualifiedName();
                    return CommonClassNames.JAVA_IO_SERIALIZABLE.equals(qualifiedName) || CommonClassNames.JAVA_LANG_CLONEABLE.equals(qualifiedName);
                }
                return psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT);
            }
            PsiType componentType = ((PsiArrayType) psiType).getComponentType();
            PsiType componentType2 = ((PsiArrayType) psiType2).getComponentType();
            if (componentType instanceof PsiPrimitiveType) {
                return componentType.equals(componentType2);
            }
            return !(componentType2 instanceof PsiPrimitiveType) && isAssignable(componentType, componentType2, z, z2);
        }
        if (psiType instanceof PsiDisjunctionType) {
            Iterator<PsiType> it = ((PsiDisjunctionType) psiType).getDisjunctions().iterator();
            while (it.hasNext()) {
                if (isAssignable(it.next(), psiType2, z, z2)) {
                    return true;
                }
            }
            return false;
        }
        if (psiType2 instanceof PsiDisjunctionType) {
            return isAssignable(psiType, ((PsiDisjunctionType) psiType2).getLeastUpperBound(), z, z2);
        }
        if (psiType instanceof PsiArrayType) {
            if (psiType2 instanceof PsiClassType) {
                PsiClass psiClassResolve = ((PsiClassType) psiType2).resolve();
                if (psiClassResolve instanceof PsiTypeParameter) {
                    PsiType inferredUpperBoundForSynthetic = getInferredUpperBoundForSynthetic((PsiTypeParameter) psiClassResolve);
                    return inferredUpperBoundForSynthetic != null && isAssignable(psiType, inferredUpperBoundForSynthetic, z, z2);
                }
            }
            return false;
        }
        if (psiType2 instanceof PsiPrimitiveType) {
            if (isVoidType(psiType2)) {
                return false;
            }
            if (!(psiType instanceof PsiPrimitiveType)) {
                return z3 && isBoxable((PsiClassType) psiType, (PsiPrimitiveType) psiType2);
            }
            ObjectIntMap<PsiType> objectIntMap = TYPE_TO_RANK_MAP;
            int i = objectIntMap.get(psiType);
            int i2 = objectIntMap.get(psiType2);
            if (i == -1) {
                i = 0;
            }
            int i3 = i - 1;
            if (i2 == -1) {
                i2 = 0;
            }
            int i4 = i2 - 1;
            if (i3 >= 0 && i4 >= 0) {
                boolean[][] zArr = IS_ASSIGNABLE_BIT_SET;
                if (i4 < zArr.length && i3 < zArr.length && zArr[i4][i3]) {
                    return true;
                }
            }
            return false;
        }
        if (!(psiType2 instanceof PsiClassType)) {
            return false;
        }
        if (psiType instanceof PsiPrimitiveType) {
            return isUnboxable((PsiPrimitiveType) psiType, (PsiClassType) psiType2, new HashSet());
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType2 = PsiUtil.resolveGenericsClassInType(psiType2);
        PsiClass element = classResolveResultResolveGenericsClassInType.getElement();
        PsiClass element2 = classResolveResultResolveGenericsClassInType2.getElement();
        if (element == null || element2 == null) {
            if (element == null && element2 != null && z3 && psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
                return true;
            }
            if (element != element2) {
                return false;
            }
            String presentableText = psiType.getPresentableText();
            String presentableText2 = psiType2.getPresentableText();
            if (presentableText.equals(presentableText2)) {
                return true;
            }
            if (presentableText.length() > presentableText2.length() && presentableText.endsWith(presentableText2) && presentableText.charAt((presentableText.length() - presentableText2.length()) - 1) == '.') {
                return true;
            }
            return presentableText2.length() > presentableText.length() && presentableText2.endsWith(presentableText) && presentableText2.charAt((presentableText2.length() - presentableText.length()) - 1) == '.';
        }
        return isClassAssignable(classResolveResultResolveGenericsClassInType, classResolveResultResolveGenericsClassInType2, z, psiType.getResolveScope(), z2);
    }

    private static boolean isAssignableFromWildcard(PsiType psiType, PsiWildcardType psiWildcardType) {
        if (psiType == null) {
            $$$reportNull$$$0(44);
        }
        if (psiWildcardType == null) {
            $$$reportNull$$$0(45);
        }
        if (psiWildcardType.isSuper()) {
            PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiWildcardType.getSuperBound());
            if (psiClassResolveClassInType instanceof PsiTypeParameter) {
                for (PsiClassType psiClassType : psiClassResolveClassInType.getExtendsListTypes()) {
                    if (isAssignable(psiType, psiClassType)) {
                        return true;
                    }
                }
            }
        }
        return isAssignable(psiType, psiWildcardType.getExtendsBound());
    }

    private static boolean isAssignableToWildcard(PsiWildcardType psiWildcardType, PsiType psiType) {
        if (psiWildcardType == null) {
            $$$reportNull$$$0(46);
        }
        if (psiType == null) {
            $$$reportNull$$$0(47);
        }
        if (psiWildcardType.isSuper()) {
            return isAssignable(psiWildcardType.getSuperBound(), psiType);
        }
        return isAssignable(psiWildcardType.getExtendsBound(), psiType);
    }

    private static boolean isUnboxable(PsiPrimitiveType psiPrimitiveType, PsiClassType psiClassType, Set<? super PsiClassType> set) {
        PsiClass psiClassResolve;
        if (psiPrimitiveType == null) {
            $$$reportNull$$$0(48);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(49);
        }
        if (set == null) {
            $$$reportNull$$$0(50);
        }
        if (!psiClassType.getLanguageLevel().isAtLeast(LanguageLevel.JDK_1_5) || (psiClassResolve = psiClassType.resolve()) == null) {
            return false;
        }
        if (psiClassResolve instanceof PsiTypeParameter) {
            for (PsiClassType psiClassType2 : psiClassResolve.getExtendsListTypes()) {
                if (set.add(psiClassType2) && isUnboxable(psiPrimitiveType, psiClassType2, set)) {
                    return true;
                }
            }
            return false;
        }
        PsiPrimitiveType unboxedType = PsiPrimitiveType.getUnboxedType(psiClassType);
        return unboxedType != null && isAssignable(psiPrimitiveType, unboxedType);
    }

    public static boolean boxingConversionApplicable(PsiType psiType, PsiType psiType2) {
        if ((psiType instanceof PsiPrimitiveType) && !PsiType.NULL.equals(psiType)) {
            return (psiType2 instanceof PsiClassType) && isAssignable(psiType, psiType2);
        }
        if (!(psiType instanceof PsiIntersectionType)) {
            return (psiType instanceof PsiClassType) && (psiType2 instanceof PsiPrimitiveType) && !PsiType.NULL.equals(psiType2) && isAssignable(psiType, psiType2);
        }
        for (PsiType psiType3 : ((PsiIntersectionType) psiType).getConjuncts()) {
            if (!boxingConversionApplicable(psiType3, psiType2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBoxable(PsiClassType psiClassType, PsiPrimitiveType psiPrimitiveType) {
        PsiClass psiClassResolve;
        String qualifiedName;
        PsiClassType boxedType;
        if (psiClassType == null) {
            $$$reportNull$$$0(51);
        }
        if (psiPrimitiveType == null) {
            $$$reportNull$$$0(52);
        }
        if (!psiClassType.getLanguageLevel().isAtLeast(LanguageLevel.JDK_1_5) || (psiClassResolve = psiClassType.resolve()) == null || (qualifiedName = psiClassResolve.getQualifiedName()) == null) {
            return false;
        }
        return ((psiClassResolve instanceof PsiTypeParameter) || getAllBoxedTypeSupers(psiClassResolve).contains(qualifiedName)) && (boxedType = psiPrimitiveType.getBoxedType(psiClassResolve.getManager(), psiClassType.getResolveScope())) != null && isAssignable(psiClassType, boxedType);
    }

    private static Set<String> getAllBoxedTypeSupers(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(53);
        }
        PsiManager manager = psiClass.getManager();
        final Project project = psiClass.getProject();
        Key<CachedValue<Set<String>>> key = POSSIBLE_BOXED_HOLDER_TYPES;
        CachedValue cachedValueCreateCachedValue = (CachedValue) project.getUserData(key);
        if (cachedValueCreateCachedValue == null) {
            cachedValueCreateCachedValue = CachedValuesManager.getManager(manager.getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda8
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return TypeConversionUtil.lambda$getAllBoxedTypeSupers$5(project);
                }
            }, false);
            project.putUserData(key, cachedValueCreateCachedValue);
        }
        Set<String> set = (Set) cachedValueCreateCachedValue.getValue();
        if (set == null) {
            $$$reportNull$$$0(54);
        }
        return set;
    }

    static /* synthetic */ CachedValueProvider.Result lambda$getAllBoxedTypeSupers$5(Project project) {
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        final HashSet hashSet = new HashSet();
        Iterator<String> it = JvmPrimitiveTypeKind.getBoxedFqns().iterator();
        while (it.hasNext()) {
            InheritanceUtil.processSupers(javaPsiFacade.findClass(it.next(), GlobalSearchScope.allScope(project)), true, (Processor<? super PsiClass>) new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj) {
                    return TypeConversionUtil.lambda$getAllBoxedTypeSupers$4(hashSet, (PsiClass) obj);
                }
            });
        }
        return CachedValueProvider.Result.create(hashSet, ProjectRootModificationTracker.getInstance(project));
    }

    static /* synthetic */ boolean lambda$getAllBoxedTypeSupers$4(Set set, PsiClass psiClass) {
        ContainerUtil.addIfNotNull(set, psiClass.getQualifiedName());
        return true;
    }

    private static boolean isClassAssignable(PsiClassType.ClassResolveResult classResolveResult, PsiClassType.ClassResolveResult classResolveResult2, boolean z, GlobalSearchScope globalSearchScope, boolean z2) {
        PsiSubstitutor superClassSubstitutor;
        if (classResolveResult == null) {
            $$$reportNull$$$0(55);
        }
        if (classResolveResult2 == null) {
            $$$reportNull$$$0(56);
        }
        PsiClass element = classResolveResult.getElement();
        PsiClass element2 = classResolveResult2.getElement();
        return (element == null || element2 == null || (superClassSubstitutor = JavaClassSupers.getInstance().getSuperClassSubstitutor(element, element2, globalSearchScope, classResolveResult2.getSubstitutor())) == null || !typeParametersAgree(classResolveResult, classResolveResult2, z, superClassSubstitutor, z2)) ? false : true;
    }

    private static boolean typeParametersAgree(PsiClassType.ClassResolveResult classResolveResult, PsiClassType.ClassResolveResult classResolveResult2, boolean z, PsiSubstitutor psiSubstitutor, boolean z2) {
        PsiType psiTypeSubstitute;
        if (classResolveResult == null) {
            $$$reportNull$$$0(57);
        }
        if (classResolveResult2 == null) {
            $$$reportNull$$$0(58);
        }
        PsiSubstitutor substitutor = classResolveResult2.getSubstitutor();
        PsiClass element = classResolveResult.getElement();
        PsiClass element2 = classResolveResult2.getElement();
        Iterator<PsiTypeParameter> itTypeParametersIterator = PsiUtil.typeParametersIterator(element);
        if (!itTypeParametersIterator.hasNext()) {
            return true;
        }
        PsiSubstitutor substitutor2 = classResolveResult.getSubstitutor();
        if (element.getManager().areElementsEquivalent(element, element2)) {
            if (!PsiUtil.typeParametersIterator(element2).hasNext()) {
                return true;
            }
            element = element2;
            psiSubstitutor = substitutor;
        }
        Iterator<PsiTypeParameter> itTypeParametersIterator2 = PsiUtil.typeParametersIterator(element);
        while (itTypeParametersIterator.hasNext()) {
            if (!itTypeParametersIterator2.hasNext()) {
                return false;
            }
            PsiTypeParameter next = itTypeParametersIterator.next();
            PsiTypeParameter next2 = itTypeParametersIterator2.next();
            PsiType psiTypeSubstitute2 = substitutor2.substitute(next);
            if (psiTypeSubstitute2 != null) {
                if (PsiCapturedWildcardType.isCapture() && z2) {
                    psiTypeSubstitute = psiSubstitutor.substituteWithBoundsPromotion(next2);
                } else {
                    psiTypeSubstitute = psiSubstitutor.substitute(next2);
                }
                if (psiTypeSubstitute == null) {
                    return z;
                }
                if (!typesAgree(psiTypeSubstitute2, psiTypeSubstitute, z)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean typesAgree(PsiType psiType, final PsiType psiType2, final boolean z) {
        if (psiType == null) {
            $$$reportNull$$$0(59);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(60);
        }
        if (psiType instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
            final PsiType bound = psiWildcardType.getBound();
            if (bound == null) {
                return true;
            }
            if (bound.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) && (!psiWildcardType.isSuper() || psiType2.equalsToText(CommonClassNames.JAVA_LANG_OBJECT))) {
                return true;
            }
            if (psiType2 instanceof PsiWildcardType) {
                PsiWildcardType psiWildcardType2 = (PsiWildcardType) psiType2;
                final PsiType bound2 = psiWildcardType2.getBound();
                if (psiWildcardType.isExtends() && bound2 != null) {
                    return psiWildcardType2.isExtends() && isAssignable(bound, bound2, z, false);
                }
                if (!psiWildcardType2.isSuper() || bound2 == null) {
                    return false;
                }
                Boolean bool = (Boolean) ourGuard.doPreventingRecursion(psiWildcardType2, true, new NotNullComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda6
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable, org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                    public final Object compute() {
                        return Boolean.valueOf(TypeConversionUtil.isAssignable(bound2, bound, z, false));
                    }
                });
                return bool != null && bool.booleanValue();
            }
            if (psiWildcardType.isExtends()) {
                return isAssignable(bound, psiType2, false, false);
            }
            Boolean bool2 = (Boolean) ourGuard.doPreventingRecursion(psiWildcardType, true, new NotNullComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil$$ExternalSyntheticLambda7
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable, org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return Boolean.valueOf(TypeConversionUtil.isAssignable(psiType2, bound, false, false));
                }
            });
            return bool2 == null || bool2.booleanValue();
        }
        return psiType.equals(psiType2);
    }

    public static PsiSubstitutor getClassSubstitutor(PsiClass psiClass, PsiClass psiClass2, PsiSubstitutor psiSubstitutor) {
        if (psiClass == null) {
            $$$reportNull$$$0(61);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(62);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(63);
        }
        if (psiClass.getManager().areElementsEquivalent(psiClass, psiClass2)) {
            return (psiClass.getTypeParameters().length <= 0 || psiClass2.getTypeParameters().length != 0) ? psiSubstitutor : JavaPsiFacade.getElementFactory(psiClass.getProject()).createRawSubstitutor(psiClass);
        }
        return getMaybeSuperClassSubstitutor(psiClass, psiClass2, psiSubstitutor);
    }

    public static PsiSubstitutor getSuperClassSubstitutor(PsiClass psiClass, PsiClass psiClass2, PsiSubstitutor psiSubstitutor) {
        if (psiClass == null) {
            $$$reportNull$$$0(64);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(65);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(66);
        }
        if (!psiClass.hasTypeParameters() && psiClass.getContainingClass() == null) {
            PsiSubstitutor psiSubstitutor2 = PsiSubstitutor.EMPTY;
            if (psiSubstitutor2 == null) {
                $$$reportNull$$$0(67);
            }
            return psiSubstitutor2;
        }
        PsiSubstitutor maybeSuperClassSubstitutor = getMaybeSuperClassSubstitutor(psiClass, psiClass2, psiSubstitutor);
        if (maybeSuperClassSubstitutor != null) {
            if (maybeSuperClassSubstitutor == null) {
                $$$reportNull$$$0(69);
            }
            return maybeSuperClassSubstitutor;
        }
        JavaClassSupers.getInstance().reportHierarchyInconsistency(psiClass, psiClass2);
        PsiSubstitutor psiSubstitutor3 = PsiSubstitutor.EMPTY;
        if (psiSubstitutor3 == null) {
            $$$reportNull$$$0(68);
        }
        return psiSubstitutor3;
    }

    public static PsiSubstitutor getMaybeSuperClassSubstitutor(PsiClass psiClass, PsiClass psiClass2, PsiSubstitutor psiSubstitutor) {
        if (psiClass == null) {
            $$$reportNull$$$0(70);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(71);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(72);
        }
        return JavaClassSupers.getInstance().getSuperClassSubstitutor(psiClass, psiClass2, psiClass2.getResolveScope(), psiSubstitutor);
    }

    public static PsiSubstitutor getSuperClassSubstitutor(PsiClass psiClass, PsiClassType psiClassType) {
        if (psiClass == null) {
            $$$reportNull$$$0(73);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(74);
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        return getSuperClassSubstitutor(psiClass, classResolveResultResolveGenerics.getElement(), classResolveResultResolveGenerics.getSubstitutor());
    }

    public static PsiType binaryNumericPromotion(PsiType psiType, PsiType psiType2) {
        PsiType psiTypeUncapture = uncapture(psiType);
        PsiType psiTypeUncapture2 = uncapture(psiType2);
        if (isDoubleType(psiTypeUncapture)) {
            return unbox(psiTypeUncapture);
        }
        if (isDoubleType(psiTypeUncapture2)) {
            return unbox(psiTypeUncapture2);
        }
        if (isFloatType(psiTypeUncapture)) {
            return unbox(psiTypeUncapture);
        }
        if (isFloatType(psiTypeUncapture2)) {
            return unbox(psiTypeUncapture2);
        }
        if (isLongType(psiTypeUncapture)) {
            return unbox(psiTypeUncapture);
        }
        if (isLongType(psiTypeUncapture2)) {
            return unbox(psiTypeUncapture2);
        }
        PsiPrimitiveType psiPrimitiveType = PsiType.INT;
        if (psiPrimitiveType == null) {
            $$$reportNull$$$0(75);
        }
        return psiPrimitiveType;
    }

    private static PsiType unbox(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(76);
        }
        if (psiType instanceof PsiPrimitiveType) {
            if (psiType == null) {
                $$$reportNull$$$0(77);
            }
            return psiType;
        }
        if (psiType instanceof PsiClassType) {
            PsiPrimitiveType unboxedType = PsiPrimitiveType.getUnboxedType(psiType);
            LOG.assertTrue(unboxedType != null);
            if (unboxedType == null) {
                $$$reportNull$$$0(78);
            }
            return unboxedType;
        }
        LOG.error("Invalid type for unboxing " + psiType);
        if (psiType == null) {
            $$$reportNull$$$0(79);
        }
        return psiType;
    }

    public static boolean isIntegerNumber(String str) {
        if (str == null) {
            $$$reportNull$$$0(80);
        }
        return INTEGER_NUMBER_TYPES.contains(str);
    }

    public static boolean isPrimitive(String str) {
        if (str == null) {
            $$$reportNull$$$0(81);
        }
        return PRIMITIVE_TYPES.contains(str);
    }

    public static boolean isPrimitiveWrapper(String str) {
        if (str == null) {
            $$$reportNull$$$0(82);
        }
        return PRIMITIVE_WRAPPER_FQNS.contains(str);
    }

    public static boolean isAssignableFromPrimitiveWrapper(PsiType psiType) {
        if (psiType == null) {
            return false;
        }
        if (isPrimitiveWrapper(psiType)) {
            return true;
        }
        for (PsiType psiType2 : psiType instanceof PsiIntersectionType ? ((PsiIntersectionType) psiType).getConjuncts() : new PsiType[]{psiType}) {
            if (!(psiType2 instanceof PsiClassType)) {
                return false;
            }
            if (!psiType2.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) && !psiType2.equalsToText(CommonClassNames.JAVA_LANG_NUMBER) && !psiType2.equalsToText(CommonClassNames.JAVA_IO_SERIALIZABLE) && !psiType2.equalsToText("java.lang.constant.Constable") && !psiType2.equalsToText("java.lang.constant.ConstantDesc") && !PsiTypesUtil.classNameEquals(psiType2, CommonClassNames.JAVA_LANG_COMPARABLE)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimitiveWrapper(PsiType psiType) {
        if (psiType instanceof PsiClassType) {
            return PRIMITIVE_WRAPPER_SIMPLE_NAMES.contains(((PsiClassType) psiType).getClassName()) && isPrimitiveWrapper(psiType.getCanonicalText());
        }
        return false;
    }

    public static boolean isComposite(PsiType psiType) {
        return (psiType instanceof PsiDisjunctionType) || (psiType instanceof PsiIntersectionType);
    }

    public static PsiType typeParameterErasure(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(83);
        }
        return typeParameterErasure(psiTypeParameter, PsiSubstitutor.EMPTY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiType typeParameterErasure(PsiTypeParameter psiTypeParameter, PsiSubstitutor psiSubstitutor) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(84);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(85);
        }
        PsiClassType[] referencedTypes = psiTypeParameter.getExtendsList().getReferencedTypes();
        if (referencedTypes.length > 0) {
            PsiClass psiClassResolve = referencedTypes[0].resolve();
            if (psiClassResolve instanceof PsiTypeParameter) {
                HashSet hashSet = new HashSet();
                hashSet.add(psiClassResolve);
                PsiTypeParameter psiTypeParameter2 = (PsiTypeParameter) psiClassResolve;
                if (psiSubstitutor.getSubstitutionMap().containsKey(psiTypeParameter2)) {
                    return erasure(psiSubstitutor.substitute(psiTypeParameter2));
                }
                return typeParameterErasureInner(psiTypeParameter2, hashSet, psiSubstitutor);
            }
            if (psiClassResolve != null) {
                return JavaPsiFacade.getElementFactory(psiTypeParameter.getProject()).createType(psiClassResolve);
            }
        }
        return PsiType.getJavaLangObject(psiTypeParameter.getManager(), psiTypeParameter.getResolveScope());
    }

    private static PsiClassType typeParameterErasureInner(PsiTypeParameter psiTypeParameter, Set<? super PsiClass> set, PsiSubstitutor psiSubstitutor) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(86);
        }
        if (set == null) {
            $$$reportNull$$$0(87);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(88);
        }
        PsiClassType[] referencedTypes = psiTypeParameter.getExtendsList().getReferencedTypes();
        if (referencedTypes.length > 0) {
            PsiClass psiClassResolve = referencedTypes[0].resolve();
            if (psiClassResolve instanceof PsiTypeParameter) {
                if (!set.contains(psiClassResolve)) {
                    set.add(psiClassResolve);
                    if (psiSubstitutor.getSubstitutionMap().containsKey(psiClassResolve)) {
                        return (PsiClassType) erasure(psiSubstitutor.substitute((PsiTypeParameter) psiClassResolve));
                    }
                    return typeParameterErasureInner((PsiTypeParameter) psiClassResolve, set, psiSubstitutor);
                }
            } else if (psiClassResolve != null) {
                return JavaPsiFacade.getElementFactory(psiTypeParameter.getProject()).createType(psiClassResolve);
            }
        }
        return PsiType.getJavaLangObject(psiTypeParameter.getManager(), psiTypeParameter.getResolveScope());
    }

    public static PsiType erasure(PsiType psiType) {
        return erasure(psiType, PsiSubstitutor.EMPTY);
    }

    public static PsiType erasure(PsiType psiType, final PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(89);
        }
        if (psiType == null) {
            return null;
        }
        return (PsiType) psiType.accept(new PsiTypeVisitor<PsiType>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 1 ? 3 : 2];
                switch (i) {
                    case 1:
                        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/TypeConversionUtil$3";
                        break;
                    case 2:
                        objArr[0] = "classType";
                        break;
                    case 3:
                        objArr[0] = "wildcardType";
                        break;
                    case 4:
                        objArr[0] = "capturedWildcardType";
                        break;
                    case 5:
                        objArr[0] = "primitiveType";
                        break;
                    case 6:
                        objArr[0] = "arrayType";
                        break;
                    case 7:
                        objArr[0] = "disjunctionType";
                        break;
                    default:
                        objArr[0] = ModuleXmlParser.TYPE;
                        break;
                }
                if (i != 1) {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/TypeConversionUtil$3";
                } else {
                    objArr[1] = "visitType";
                }
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        objArr[2] = "visitClassType";
                        break;
                    case 3:
                        objArr[2] = "visitWildcardType";
                        break;
                    case 4:
                        objArr[2] = "visitCapturedWildcardType";
                        break;
                    case 5:
                        objArr[2] = "visitPrimitiveType";
                        break;
                    case 6:
                        objArr[2] = "visitArrayType";
                        break;
                    case 7:
                        objArr[2] = "visitDisjunctionType";
                        break;
                    default:
                        objArr[2] = "visitType";
                        break;
                }
                String str2 = String.format(str, objArr);
                if (i == 1) {
                    throw new IllegalStateException(str2);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitPrimitiveType(PsiPrimitiveType psiPrimitiveType) {
                if (psiPrimitiveType == null) {
                    $$$reportNull$$$0(5);
                }
                return psiPrimitiveType;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitWildcardType(PsiWildcardType psiWildcardType) {
                if (psiWildcardType == null) {
                    $$$reportNull$$$0(3);
                }
                return psiWildcardType;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitType(PsiType psiType2) {
                if (psiType2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (psiType2 == null) {
                    $$$reportNull$$$0(1);
                }
                return psiType2;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(2);
                }
                PsiClass psiClassResolve = psiClassType.resolve();
                if (psiClassResolve instanceof PsiTypeParameter) {
                    PsiTypeParameter psiTypeParameter = (PsiTypeParameter) psiClassResolve;
                    if (!TypeConversionUtil.isFreshVariable(psiTypeParameter)) {
                        return TypeConversionUtil.typeParameterErasure(psiTypeParameter, psiSubstitutor);
                    }
                }
                return psiClassType.rawType();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
                if (psiCapturedWildcardType == null) {
                    $$$reportNull$$$0(4);
                }
                return (PsiType) psiCapturedWildcardType.getUpperBound().accept(this);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(6);
                }
                PsiType componentType = psiArrayType.getComponentType();
                PsiType psiType2 = (PsiType) componentType.accept(this);
                if (psiType2 == componentType) {
                    return psiArrayType;
                }
                if (psiType2 != null) {
                    return psiType2.createArrayType();
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitDisjunctionType(PsiDisjunctionType psiDisjunctionType) {
                if (psiDisjunctionType == null) {
                    $$$reportNull$$$0(7);
                }
                PsiClassType lowestUpperBoundClassType = PsiTypesUtil.getLowestUpperBoundClassType(psiDisjunctionType);
                return lowestUpperBoundClassType != null ? TypeConversionUtil.erasure(lowestUpperBoundClassType, psiSubstitutor) : psiDisjunctionType;
            }
        });
    }

    public static Object computeCastTo(Object obj, PsiType psiType) {
        if (obj == null || psiType == null) {
            return null;
        }
        if ((obj instanceof String) && psiType.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
            return obj;
        }
        if ((obj instanceof Boolean) && PsiType.BOOLEAN.equals(psiType)) {
            return obj;
        }
        PsiType psiTypeWrapperToPrimitive = wrapperToPrimitive(obj);
        if (psiTypeWrapperToPrimitive == null) {
            return null;
        }
        return psiType.equals(psiTypeWrapperToPrimitive) ? obj : cast(obj, getTypeRank(psiType));
    }

    public static PsiType unboxAndBalanceTypes(PsiType psiType, PsiType psiType2) {
        if (psiType instanceof PsiClassType) {
            psiType = PsiPrimitiveType.getUnboxedType(psiType);
        }
        if (psiType2 instanceof PsiClassType) {
            psiType2 = PsiPrimitiveType.getUnboxedType(psiType2);
        }
        if (PsiType.DOUBLE.equals(psiType) || PsiType.DOUBLE.equals(psiType2)) {
            PsiPrimitiveType psiPrimitiveType = PsiType.DOUBLE;
            if (psiPrimitiveType == null) {
                $$$reportNull$$$0(90);
            }
            return psiPrimitiveType;
        }
        if (PsiType.FLOAT.equals(psiType) || PsiType.FLOAT.equals(psiType2)) {
            PsiPrimitiveType psiPrimitiveType2 = PsiType.FLOAT;
            if (psiPrimitiveType2 == null) {
                $$$reportNull$$$0(91);
            }
            return psiPrimitiveType2;
        }
        if (PsiType.LONG.equals(psiType) || PsiType.LONG.equals(psiType2)) {
            PsiPrimitiveType psiPrimitiveType3 = PsiType.LONG;
            if (psiPrimitiveType3 == null) {
                $$$reportNull$$$0(92);
            }
            return psiPrimitiveType3;
        }
        PsiPrimitiveType psiPrimitiveType4 = PsiType.INT;
        if (psiPrimitiveType4 == null) {
            $$$reportNull$$$0(93);
        }
        return psiPrimitiveType4;
    }

    public static IElementType convertEQtoOperation(IElementType iElementType) {
        if (iElementType == JavaTokenType.ANDEQ) {
            return JavaTokenType.AND;
        }
        if (iElementType == JavaTokenType.ASTERISKEQ) {
            return JavaTokenType.ASTERISK;
        }
        if (iElementType == JavaTokenType.DIVEQ) {
            return JavaTokenType.DIV;
        }
        if (iElementType == JavaTokenType.GTGTEQ) {
            return JavaTokenType.GTGT;
        }
        if (iElementType == JavaTokenType.GTGTGTEQ) {
            return JavaTokenType.GTGTGT;
        }
        if (iElementType == JavaTokenType.LTLTEQ) {
            return JavaTokenType.LTLT;
        }
        if (iElementType == JavaTokenType.MINUSEQ) {
            return JavaTokenType.MINUS;
        }
        if (iElementType == JavaTokenType.OREQ) {
            return JavaTokenType.OR;
        }
        if (iElementType == JavaTokenType.PERCEQ) {
            return JavaTokenType.PERC;
        }
        if (iElementType == JavaTokenType.PLUSEQ) {
            return JavaTokenType.PLUS;
        }
        if (iElementType == JavaTokenType.XOREQ) {
            return JavaTokenType.XOR;
        }
        return null;
    }

    public static PsiType calcTypeForBinaryExpression(PsiType psiType, PsiType psiType2, IElementType iElementType, boolean z) {
        if (iElementType == null) {
            $$$reportNull$$$0(94);
        }
        if (iElementType == JavaTokenType.PLUS) {
            if (psiType2 == null) {
                return null;
            }
            if (psiType2.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
                return psiType2;
            }
            if (!z) {
                return NULL_TYPE;
            }
            if (psiType == null) {
                return null;
            }
            return psiType.equalsToText(CommonClassNames.JAVA_LANG_STRING) ? psiType : unboxAndBalanceTypes(psiType, psiType2);
        }
        if (iElementType == JavaTokenType.MINUS || iElementType == JavaTokenType.ASTERISK || iElementType == JavaTokenType.DIV || iElementType == JavaTokenType.PERC) {
            if (psiType2 == null) {
                return null;
            }
            if (!z) {
                return NULL_TYPE;
            }
            if (psiType == null) {
                return null;
            }
            return unboxAndBalanceTypes(psiType, psiType2);
        }
        if (iElementType == JavaTokenType.LTLT || iElementType == JavaTokenType.GTGT || iElementType == JavaTokenType.GTGTGT) {
            if (!z) {
                return NULL_TYPE;
            }
            if (psiType instanceof PsiClassType) {
                psiType = PsiPrimitiveType.getUnboxedType(psiType);
            }
            return (PsiType.BYTE.equals(psiType) || PsiType.CHAR.equals(psiType) || PsiType.SHORT.equals(psiType)) ? PsiType.INT : psiType;
        }
        if (PsiBinaryExpression.BOOLEAN_OPERATION_TOKENS.contains(iElementType)) {
            return PsiType.BOOLEAN;
        }
        if (iElementType == JavaTokenType.OR || iElementType == JavaTokenType.XOR || iElementType == JavaTokenType.AND) {
            if (psiType2 instanceof PsiClassType) {
                psiType2 = PsiPrimitiveType.getUnboxedType(psiType2);
            }
            if (psiType instanceof PsiClassType) {
                psiType = PsiPrimitiveType.getUnboxedType(psiType);
            }
            if (psiType2 == null) {
                return null;
            }
            if (PsiType.BOOLEAN.equals(psiType2)) {
                return PsiType.BOOLEAN;
            }
            if (!z) {
                return NULL_TYPE;
            }
            if (psiType == null) {
                return null;
            }
            if (PsiType.BOOLEAN.equals(psiType)) {
                return PsiType.BOOLEAN;
            }
            if (PsiType.LONG.equals(psiType) || PsiType.LONG.equals(psiType2)) {
                return PsiType.LONG;
            }
            return PsiType.INT;
        }
        LOG.error("Unknown token: " + iElementType);
        return null;
    }

    public static boolean isFPZero(String str) {
        if (str == null) {
            $$$reportNull$$$0(95);
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (Character.isDigit(cCharAt) && cCharAt != '0') {
                return false;
            }
            char upperCase = Character.toUpperCase(cCharAt);
            if (upperCase == 'E' || upperCase == 'P') {
                return true;
            }
        }
        return true;
    }

    public static boolean areSameFreshVariables(PsiTypeParameter psiTypeParameter, PsiTypeParameter psiTypeParameter2) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(96);
        }
        if (psiTypeParameter2 == null) {
            $$$reportNull$$$0(97);
        }
        Key<PsiElement> key = ORIGINAL_CONTEXT;
        PsiElement psiElement = (PsiElement) psiTypeParameter.getUserData(key);
        return psiElement != null && psiElement == psiTypeParameter2.getUserData(key);
    }

    public static boolean isFreshVariable(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(98);
        }
        return psiTypeParameter.getUserData(ORIGINAL_CONTEXT) != null;
    }

    public static void markAsFreshVariable(PsiTypeParameter psiTypeParameter, PsiElement psiElement) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(99);
        }
        psiTypeParameter.putUserData(ORIGINAL_CONTEXT, psiElement);
    }

    public static PsiType getInferredUpperBoundForSynthetic(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(100);
        }
        return (PsiType) psiTypeParameter.getUserData(UPPER_BOUND);
    }

    public static PsiType getInferredLowerBoundForSynthetic(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(101);
        }
        return (PsiType) psiTypeParameter.getUserData(LOWER_BOUND);
    }

    public static void setInferredBoundsForSynthetic(PsiTypeParameter psiTypeParameter, PsiType psiType, PsiType psiType2) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(102);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(103);
        }
        psiTypeParameter.putUserData(UPPER_BOUND, psiType2);
        psiTypeParameter.putUserData(LOWER_BOUND, psiType);
    }

    public static boolean isSafeConversion(PsiType psiType, PsiType psiType2) {
        if (psiType != null && psiType2 != null) {
            if (psiType.equals(psiType2)) {
                return true;
            }
            ObjectIntMap<PsiType> objectIntMap = TYPE_TO_RANK_MAP;
            int i = objectIntMap.get(psiType2);
            int i2 = objectIntMap.get(psiType);
            if (i != -1 && i <= 7 && i2 != -1 && i2 <= 7 && IS_ASSIGNABLE_BIT_SET[i - 1][i2 - 1]) {
                if (PsiType.INT.equals(psiType2) && PsiType.FLOAT.equals(psiType)) {
                    return false;
                }
                return (PsiType.LONG.equals(psiType2) && isFloatOrDoubleType(psiType)) ? false : true;
            }
        }
        return false;
    }

    private static Object cast(Object obj, int i) {
        Number numberValueOf;
        if (obj == null) {
            $$$reportNull$$$0(104);
        }
        if (obj instanceof Character) {
            numberValueOf = Integer.valueOf(((Character) obj).charValue());
        } else {
            numberValueOf = obj instanceof Number ? (Number) obj : null;
        }
        if (numberValueOf == null) {
            return null;
        }
        switch (i) {
            case 1:
                return Byte.valueOf(numberValueOf.byteValue());
            case 2:
                return Short.valueOf(numberValueOf.shortValue());
            case 3:
                return Character.valueOf((char) numberValueOf.intValue());
            case 4:
                return Integer.valueOf(numberValueOf.intValue());
            case 5:
                return Long.valueOf(numberValueOf.longValue());
            case 6:
                return Float.valueOf(numberValueOf.floatValue());
            case 7:
                return Double.valueOf(numberValueOf.doubleValue());
            default:
                return null;
        }
    }

    private static PsiType wrapperToPrimitive(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(105);
        }
        return WRAPPER_TO_PRIMITIVE.get(obj.getClass());
    }
}
