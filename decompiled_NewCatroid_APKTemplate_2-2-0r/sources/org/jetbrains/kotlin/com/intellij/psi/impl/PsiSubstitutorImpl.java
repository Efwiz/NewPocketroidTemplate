package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.EmptySubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy;
import org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiSubstitutorImpl implements PsiSubstitutor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final UnmodifiableHashMap<PsiTypeParameter, PsiType> EMPTY_MAP;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiSubstitutorImpl.class);
    private static final HashingStrategy<PsiTypeParameter> PSI_EQUIVALENCE;
    private final SubstitutionVisitor mySimpleSubstitutionVisitor;
    private final UnmodifiableHashMap<PsiTypeParameter, PsiType> mySubstitutionMap;

    /* JADX WARN: Code duplicated, block: B:11:0x001e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 18 && i != 19 && i != 22 && i != 23 && i != 25 && i != 26) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 18 && i != 19 && i != 22 && i != 23 && i != 25 && i != 26) {
            switch (i) {
                case 11:
                case 12:
                case 13:
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
            case 2:
                objArr[0] = "additionalKey";
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 15:
                objArr[0] = "typeParameter";
                break;
            case 4:
            case 17:
            case 20:
                objArr[0] = "parentClass";
                break;
            case 9:
                objArr[0] = "type1";
                break;
            case 10:
                objArr[0] = "type2";
                break;
            case 11:
            case 12:
            case 13:
            case 18:
            case 19:
            case 22:
            case 23:
            case 25:
            case 26:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSubstitutorImpl";
                break;
            case 14:
                objArr[0] = "original";
                break;
            case 16:
                objArr[0] = "originalMap";
                break;
            case 21:
                objArr[0] = "another";
                break;
            case 24:
            default:
                objArr[0] = "map";
                break;
        }
        if (i == 18 || i == 19) {
            objArr[1] = "putAllInternal";
        } else if (i == 22 || i == 23 || i == 25) {
            objArr[1] = "putAll";
        } else if (i != 26) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    objArr[1] = "getMergedProvider";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSubstitutorImpl";
                    break;
            }
        } else {
            objArr[1] = "getSubstitutionMap";
        }
        switch (i) {
            case 5:
                objArr[2] = "substitute";
                break;
            case 6:
                objArr[2] = "getFromMap";
                break;
            case 7:
                objArr[2] = "substituteWithBoundsPromotion";
                break;
            case 8:
                objArr[2] = "rawTypeForTypeParameter";
                break;
            case 9:
            case 10:
                objArr[2] = "getMergedProvider";
                break;
            case 11:
            case 12:
            case 13:
            case 18:
            case 19:
            case 22:
            case 23:
            case 25:
            case 26:
                break;
            case 14:
                objArr[2] = "correctExternalSubstitution";
                break;
            case 15:
                objArr[2] = "put";
                break;
            case 16:
            case 17:
                objArr[2] = "putAllInternal";
                break;
            case 20:
            case 21:
            case 24:
                objArr[2] = "putAll";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 18 && i != 19 && i != 22 && i != 23 && i != 25 && i != 26) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        HashingStrategy<PsiTypeParameter> hashingStrategy = new HashingStrategy<PsiTypeParameter>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSubstitutorImpl.1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
            public int hashCode(PsiTypeParameter psiTypeParameter) {
                return Comparing.hashcode(psiTypeParameter.getName());
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
            public boolean equals(PsiTypeParameter psiTypeParameter, PsiTypeParameter psiTypeParameter2) {
                return psiTypeParameter.getManager().areElementsEquivalent(psiTypeParameter, psiTypeParameter2);
            }
        };
        PSI_EQUIVALENCE = hashingStrategy;
        EMPTY_MAP = UnmodifiableHashMap.empty(hashingStrategy);
    }

    PsiSubstitutorImpl(Map<? extends PsiTypeParameter, ? extends PsiType> map) {
        if (map == null) {
            $$$reportNull$$$0(0);
        }
        this.mySimpleSubstitutionVisitor = new SubstitutionVisitor();
        this.mySubstitutionMap = UnmodifiableHashMap.fromMap(PSI_EQUIVALENCE, map);
    }

    private PsiSubstitutorImpl(UnmodifiableHashMap<PsiTypeParameter, PsiType> unmodifiableHashMap, PsiTypeParameter psiTypeParameter, PsiType psiType) {
        if (unmodifiableHashMap == null) {
            $$$reportNull$$$0(1);
        }
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(2);
        }
        this.mySimpleSubstitutionVisitor = new SubstitutionVisitor();
        this.mySubstitutionMap = unmodifiableHashMap.with(psiTypeParameter, psiType);
    }

    PsiSubstitutorImpl(PsiTypeParameter psiTypeParameter, PsiType psiType) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(3);
        }
        this.mySimpleSubstitutionVisitor = new SubstitutionVisitor();
        this.mySubstitutionMap = EMPTY_MAP.with(psiTypeParameter, psiType);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    PsiSubstitutorImpl(PsiClass psiClass, PsiType[] psiTypeArr) {
        this(putAllInternal(EMPTY_MAP, psiClass, psiTypeArr));
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiType substitute(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(5);
        }
        PsiType fromMap = getFromMap(psiTypeParameter);
        return PsiType.VOID.equals(fromMap) ? JavaPsiFacade.getElementFactory(psiTypeParameter.getProject()).createType(psiTypeParameter) : fromMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PsiType getFromMap(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(6);
        }
        if (psiTypeParameter instanceof LightTypeParameter) {
            LightTypeParameter lightTypeParameter = (LightTypeParameter) psiTypeParameter;
            if (lightTypeParameter.useDelegateToSubstitute()) {
                psiTypeParameter = lightTypeParameter.mo6090getDelegate();
            }
        }
        return this.mySubstitutionMap.getOrDefault(psiTypeParameter, PsiType.VOID);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiType substitute(PsiType psiType) {
        if (psiType == null) {
            return null;
        }
        PsiUtil.ensureValidType(psiType);
        return correctExternalSubstitution((PsiType) psiType.accept(this.mySimpleSubstitutionVisitor), psiType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiType substituteWithBoundsPromotion(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(7);
        }
        PsiType psiTypeSubstitute = substitute(psiTypeParameter);
        if (psiTypeSubstitute instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType = (PsiWildcardType) psiTypeSubstitute;
            if (!psiWildcardType.isSuper()) {
                PsiType psiTypeCaptureUpperBound = PsiCapturedWildcardType.captureUpperBound(psiTypeParameter, psiWildcardType, this);
                if (psiTypeCaptureUpperBound instanceof PsiWildcardType) {
                    return psiTypeCaptureUpperBound;
                }
                if (psiTypeCaptureUpperBound instanceof PsiCapturedWildcardType) {
                    PsiWildcardType wildcard = ((PsiCapturedWildcardType) psiTypeCaptureUpperBound).getWildcard();
                    if (!wildcard.isSuper()) {
                        return wildcard;
                    }
                }
                if (psiTypeCaptureUpperBound != null) {
                    return PsiWildcardType.createExtends(psiTypeParameter.getManager(), psiTypeCaptureUpperBound);
                }
            }
        }
        return psiTypeSubstitute;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PsiSubstitutorImpl) && this.mySubstitutionMap.equals(((PsiSubstitutorImpl) obj).mySubstitutionMap);
    }

    public int hashCode() {
        return this.mySubstitutionMap.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PsiType rawTypeForTypeParameter(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(8);
        }
        final PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
        if (extendsListTypes.length > 0) {
            return (PsiType) RecursionManager.doPreventingRecursion(extendsListTypes[0], true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSubstitutorImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return this.f$0.m6573xba1d09a3(extendsListTypes);
                }
            });
        }
        return PsiType.getJavaLangObject(psiTypeParameter.getManager(), psiTypeParameter.getResolveScope());
    }

    /* JADX INFO: renamed from: lambda$rawTypeForTypeParameter$0$org-jetbrains-kotlin-com-intellij-psi-impl-PsiSubstitutorImpl, reason: not valid java name */
    /* synthetic */ PsiType m6573xba1d09a3(PsiClassType[] psiClassTypeArr) {
        return substitute(psiClassTypeArr[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TypeAnnotationProvider getMergedProvider(final PsiType psiType, final PsiType psiType2) {
        if (psiType == null) {
            $$$reportNull$$$0(9);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(10);
        }
        if (psiType.getAnnotationProvider() == TypeAnnotationProvider.EMPTY && !(psiType instanceof PsiClassReferenceType)) {
            TypeAnnotationProvider annotationProvider = psiType2.getAnnotationProvider();
            if (annotationProvider == null) {
                $$$reportNull$$$0(11);
            }
            return annotationProvider;
        }
        if (psiType2.getAnnotationProvider() == TypeAnnotationProvider.EMPTY && !(psiType2 instanceof PsiClassReferenceType)) {
            TypeAnnotationProvider annotationProvider2 = psiType.getAnnotationProvider();
            if (annotationProvider2 == null) {
                $$$reportNull$$$0(12);
            }
            return annotationProvider2;
        }
        return new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSubstitutorImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
            public final PsiAnnotation[] getAnnotations() {
                return PsiSubstitutorImpl.lambda$getMergedProvider$1(psiType, psiType2);
            }
        };
    }

    static /* synthetic */ PsiAnnotation[] lambda$getMergedProvider$1(PsiType psiType, PsiType psiType2) {
        return (PsiAnnotation[]) ArrayUtil.mergeArrays(psiType.getAnnotations(), psiType2.getAnnotations());
    }

    private class SubstitutionVisitor extends PsiTypeMapper {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 4 || i == 5 || i == 6) ? 2 : 3];
            switch (i) {
                case 1:
                    objArr[0] = "wildcardType";
                    break;
                case 2:
                case 8:
                default:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 3:
                    objArr[0] = "newBound";
                    break;
                case 4:
                case 5:
                case 6:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSubstitutorImpl$SubstitutionVisitor";
                    break;
                case 7:
                    objArr[0] = "classType";
                    break;
                case 9:
                    objArr[0] = "resolve";
                    break;
                case 10:
                    objArr[0] = "originalSubstitutor";
                    break;
                case 11:
                    objArr[0] = "substMap";
                    break;
            }
            if (i == 4 || i == 5 || i == 6) {
                objArr[1] = "rebound";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSubstitutorImpl$SubstitutionVisitor";
            }
            switch (i) {
                case 1:
                    objArr[2] = "visitWildcardType";
                    break;
                case 2:
                case 3:
                    objArr[2] = "rebound";
                    break;
                case 4:
                case 5:
                case 6:
                    break;
                case 7:
                    objArr[2] = "visitClassType";
                    break;
                case 8:
                    objArr[2] = "substituteInternal";
                    break;
                case 9:
                case 10:
                case 11:
                    objArr[2] = "processClass";
                    break;
                default:
                    objArr[2] = "visitType";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 4 && i != 5 && i != 6) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitType(PsiType psiType) {
            if (psiType != null) {
                return null;
            }
            $$$reportNull$$$0(0);
            return null;
        }

        private SubstitutionVisitor() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitWildcardType(PsiWildcardType psiWildcardType) {
            if (psiWildcardType == null) {
                $$$reportNull$$$0(1);
            }
            PsiType bound = psiWildcardType.getBound();
            if (bound == null) {
                return psiWildcardType;
            }
            PsiType psiType = (PsiType) bound.accept(this);
            if (psiType == null) {
                return null;
            }
            if (!(psiType instanceof PsiWildcardType)) {
                return psiType == PsiType.NULL ? psiType : rebound(psiWildcardType, psiType);
            }
            PsiWildcardType psiWildcardType2 = (PsiWildcardType) psiType;
            return !psiWildcardType2.isBounded() ? PsiWildcardType.createUnbounded(psiWildcardType.getManager()) : rebound(psiWildcardType, psiWildcardType2.getBound());
        }

        private PsiWildcardType rebound(PsiWildcardType psiWildcardType, PsiType psiType) {
            if (psiWildcardType == null) {
                $$$reportNull$$$0(2);
            }
            if (psiType == null) {
                $$$reportNull$$$0(3);
            }
            LOG.assertTrue(psiWildcardType.getBound() != null);
            LOG.assertTrue(psiType.isValid());
            if (psiWildcardType.isExtends()) {
                if (psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
                    PsiWildcardType psiWildcardTypeCreateUnbounded = PsiWildcardType.createUnbounded(psiWildcardType.getManager());
                    if (psiWildcardTypeCreateUnbounded == null) {
                        $$$reportNull$$$0(4);
                    }
                    return psiWildcardTypeCreateUnbounded;
                }
                PsiWildcardType psiWildcardTypeCreateExtends = PsiWildcardType.createExtends(psiWildcardType.getManager(), psiType);
                if (psiWildcardTypeCreateExtends == null) {
                    $$$reportNull$$$0(5);
                }
                return psiWildcardTypeCreateExtends;
            }
            PsiWildcardType psiWildcardTypeCreateSuper = PsiWildcardType.createSuper(psiWildcardType.getManager(), psiType);
            if (psiWildcardTypeCreateSuper == null) {
                $$$reportNull$$$0(6);
            }
            return psiWildcardTypeCreateSuper;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitClassType(PsiClassType psiClassType) {
            if (psiClassType == null) {
                $$$reportNull$$$0(7);
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element == null) {
                return psiClassType;
            }
            PsiUtilCore.ensureValid(element);
            if (element instanceof PsiTypeParameter) {
                PsiType fromMap = PsiSubstitutorImpl.this.getFromMap((PsiTypeParameter) element);
                if (PsiType.VOID.equals(fromMap)) {
                    return psiClassType;
                }
                if (fromMap != null) {
                    PsiUtil.ensureValidType(fromMap);
                    if ((fromMap instanceof PsiClassType) || (fromMap instanceof PsiArrayType) || (fromMap instanceof PsiWildcardType)) {
                        return fromMap.annotate(PsiSubstitutorImpl.getMergedProvider(psiClassType, fromMap));
                    }
                }
                return fromMap;
            }
            HashMap map = new HashMap(2);
            if (!processClass(element, classResolveResultResolveGenerics.getSubstitutor(), map)) {
                return null;
            }
            PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(element.getProject()).createType(element, PsiSubstitutor.createSubstitutor(map), psiClassType.getLanguageLevel());
            PsiUtil.ensureValidType(psiClassTypeCreateType);
            return psiClassTypeCreateType.annotate(psiClassType.getAnnotationProvider());
        }

        private PsiType substituteInternal(PsiType psiType) {
            if (psiType == null) {
                $$$reportNull$$$0(8);
            }
            return (PsiType) psiType.accept(this);
        }

        private boolean processClass(PsiClass psiClass, PsiSubstitutor psiSubstitutor, Map<PsiTypeParameter, PsiType> map) {
            if (psiClass == null) {
                $$$reportNull$$$0(9);
            }
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(10);
            }
            if (map == null) {
                $$$reportNull$$$0(11);
            }
            for (PsiTypeParameter psiTypeParameter : psiClass.getTypeParameters()) {
                PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiTypeParameter);
                if (psiTypeSubstitute == null) {
                    map.put(psiTypeParameter, null);
                } else {
                    map.put(psiTypeParameter, substituteInternal(psiTypeSubstitute));
                }
            }
            if (psiClass.hasModifierProperty("static")) {
                return true;
            }
            PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
            return psiClassMo6092getContainingClass == null || processClass(psiClassMo6092getContainingClass, psiSubstitutor, map);
        }
    }

    private PsiType correctExternalSubstitution(PsiType psiType, PsiType psiType2) {
        if (psiType2 == null) {
            $$$reportNull$$$0(14);
        }
        return psiType != null ? psiType : (PsiType) psiType2.accept(new PsiTypeVisitor<PsiType>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSubstitutorImpl.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "ellipsisType";
                } else if (i != 2) {
                    objArr[0] = "arrayType";
                } else {
                    objArr[0] = "classType";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSubstitutorImpl$2";
                if (i == 1) {
                    objArr[2] = "visitEllipsisType";
                } else if (i != 2) {
                    objArr[2] = "visitArrayType";
                } else {
                    objArr[2] = "visitClassType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(0);
                }
                return new PsiArrayType((PsiType) psiArrayType.getComponentType().accept(this));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitEllipsisType(PsiEllipsisType psiEllipsisType) {
                if (psiEllipsisType == null) {
                    $$$reportNull$$$0(1);
                }
                return new PsiEllipsisType((PsiType) psiEllipsisType.getComponentType().accept(this));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(2);
                }
                PsiClass psiClassResolve = psiClassType.resolve();
                if (psiClassResolve == null) {
                    return psiClassType;
                }
                if (psiClassResolve instanceof PsiTypeParameter) {
                    return PsiSubstitutorImpl.this.rawTypeForTypeParameter((PsiTypeParameter) psiClassResolve);
                }
                return JavaPsiFacade.getElementFactory(psiClassResolve.getProject()).createType(psiClassResolve);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public PsiSubstitutorImpl clone() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor put(PsiTypeParameter psiTypeParameter, PsiType psiType) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(15);
        }
        if (psiType != null && !psiType.isValid()) {
            LOG.error("Invalid type in substitutor: " + psiType + "; " + psiType.getClass());
        }
        return new PsiSubstitutorImpl(this.mySubstitutionMap, psiTypeParameter, psiType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static UnmodifiableHashMap<PsiTypeParameter, PsiType> putAllInternal(UnmodifiableHashMap<PsiTypeParameter, PsiType> unmodifiableHashMap, PsiClass psiClass, PsiType[] psiTypeArr) {
        if (unmodifiableHashMap == null) {
            $$$reportNull$$$0(16);
        }
        if (psiClass == 0) {
            $$$reportNull$$$0(17);
        }
        PsiTypeParameter[] typeParameters = psiClass.getTypeParameters();
        if (typeParameters.length == 0) {
            if (unmodifiableHashMap == null) {
                $$$reportNull$$$0(18);
            }
            return unmodifiableHashMap;
        }
        for (int i = 0; i < typeParameters.length; i++) {
            PsiTypeParameter psiTypeParameter = typeParameters[i];
            if (psiTypeArr != null && psiTypeArr.length > i) {
                PsiType psiType = psiTypeArr[i];
                unmodifiableHashMap = unmodifiableHashMap.with(psiTypeParameter, psiType);
                if (psiType != null && !psiType.isValid()) {
                    LOG.error("Invalid type in substitutor: " + psiType);
                }
            } else {
                unmodifiableHashMap = unmodifiableHashMap.with(psiTypeParameter, null);
            }
        }
        if (unmodifiableHashMap == null) {
            $$$reportNull$$$0(19);
        }
        return unmodifiableHashMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor putAll(PsiClass psiClass, PsiType[] psiTypeArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(20);
        }
        return new PsiSubstitutorImpl(putAllInternal(this.mySubstitutionMap, psiClass, psiTypeArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor putAll(PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(21);
        }
        if (psiSubstitutor instanceof EmptySubstitutor) {
            return this;
        }
        PsiSubstitutor psiSubstitutorPutAll = putAll(((PsiSubstitutorImpl) psiSubstitutor).mySubstitutionMap);
        if (psiSubstitutorPutAll == null) {
            $$$reportNull$$$0(23);
        }
        return psiSubstitutorPutAll;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor putAll(Map<? extends PsiTypeParameter, ? extends PsiType> map) {
        if (map == null) {
            $$$reportNull$$$0(24);
        }
        return map.isEmpty() ? this : new PsiSubstitutorImpl(this.mySubstitutionMap.withAll(map));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<PsiTypeParameter, PsiType> entry : this.mySubstitutionMap.entrySet()) {
            PsiTypeParameter key = entry.getKey();
            sb.append(key.getName());
            PsiTypeParameterListOwner owner = key.getOwner();
            if (owner instanceof PsiClass) {
                sb.append(" of ");
                sb.append(((PsiClass) owner).getQualifiedName());
            } else if (owner instanceof PsiMethod) {
                sb.append(" of ");
                PsiMethod psiMethod = (PsiMethod) owner;
                sb.append(psiMethod.getName());
                sb.append(" in ");
                PsiClass containingClass = psiMethod.mo6092getContainingClass();
                sb.append(containingClass != null ? containingClass.getQualifiedName() : "<no class>");
            }
            sb.append(" -> ");
            if (entry.getValue() != null) {
                sb.append(entry.getValue().getCanonicalText());
            } else {
                sb.append("null");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public boolean isValid() {
        for (Map.Entry<PsiTypeParameter, PsiType> entry : this.mySubstitutionMap.entrySet()) {
            if (!entry.getKey().isValid()) {
                return false;
            }
            PsiType value = entry.getValue();
            if (value != null && !value.isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public void ensureValid() {
        this.mySubstitutionMap.values().forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSubstitutorImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PsiSubstitutorImpl.lambda$ensureValid$2((PsiType) obj);
            }
        });
    }

    static /* synthetic */ void lambda$ensureValid$2(PsiType psiType) {
        if (psiType != null) {
            PsiUtil.ensureValidType(psiType);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public Map<PsiTypeParameter, PsiType> getSubstitutionMap() {
        UnmodifiableHashMap<PsiTypeParameter, PsiType> unmodifiableHashMap = this.mySubstitutionMap;
        if (unmodifiableHashMap == null) {
            $$$reportNull$$$0(26);
        }
        return unmodifiableHashMap;
    }
}
