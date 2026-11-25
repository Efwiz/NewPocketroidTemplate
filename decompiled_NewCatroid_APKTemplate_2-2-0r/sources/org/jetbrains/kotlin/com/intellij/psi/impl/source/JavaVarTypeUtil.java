package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpressionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitorEx;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaVarTypeUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "t";
        } else {
            objArr[0] = ModuleXmlParser.TYPE;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaVarTypeUtil";
        if (i != 1) {
            objArr[2] = "getUpwardProjection";
        } else {
            objArr[2] = "getDownwardProjection";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static PsiType getUpwardProjection(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
        return (PsiType) psiType.accept(new UpwardProjectionTypeVisitor());
    }

    public static PsiType getDownwardProjection(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        return (PsiType) psiType.accept(new DownwardProjectionTypeVisitor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean mentionsRestrictedTypeVariables(PsiType psiType) {
        return ((Boolean) psiType.accept(new PsiTypeVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaVarTypeUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else {
                    objArr[0] = "capturedWildcardType";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaVarTypeUtil$1";
                if (i != 1) {
                    objArr[2] = "visitType";
                } else {
                    objArr[2] = "visitCapturedWildcardType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitType(PsiType psiType2) {
                if (psiType2 == null) {
                    $$$reportNull$$$0(0);
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
                if (psiCapturedWildcardType == null) {
                    $$$reportNull$$$0(1);
                }
                return true;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class UpwardProjectionTypeVisitor extends PsiTypeVisitorEx<PsiType> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "capturedWildcardType";
                    break;
                case 2:
                    objArr[0] = "arrayType";
                    break;
                case 3:
                    objArr[0] = "disjunctionType";
                    break;
                case 4:
                    objArr[0] = "lambdaExpressionType";
                    break;
                case 5:
                    objArr[0] = "methodReferenceType";
                    break;
                case 6:
                    objArr[0] = "intersectionType";
                    break;
                case 7:
                    objArr[0] = "classType";
                    break;
                default:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaVarTypeUtil$UpwardProjectionTypeVisitor";
            switch (i) {
                case 1:
                    objArr[2] = "visitCapturedWildcardType";
                    break;
                case 2:
                    objArr[2] = "visitArrayType";
                    break;
                case 3:
                    objArr[2] = "visitDisjunctionType";
                    break;
                case 4:
                    objArr[2] = "visitLambdaExpressionType";
                    break;
                case 5:
                    objArr[2] = "visitMethodReferenceType";
                    break;
                case 6:
                    objArr[2] = "visitIntersectionType";
                    break;
                case 7:
                    objArr[2] = "visitClassType";
                    break;
                default:
                    objArr[2] = "visitType";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitLambdaExpressionType(PsiLambdaExpressionType psiLambdaExpressionType) {
            if (psiLambdaExpressionType == null) {
                $$$reportNull$$$0(4);
            }
            return psiLambdaExpressionType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitMethodReferenceType(PsiMethodReferenceType psiMethodReferenceType) {
            if (psiMethodReferenceType == null) {
                $$$reportNull$$$0(5);
            }
            return psiMethodReferenceType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitType(PsiType psiType) {
            if (psiType == null) {
                $$$reportNull$$$0(0);
            }
            return psiType;
        }

        private UpwardProjectionTypeVisitor() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
            if (psiCapturedWildcardType == null) {
                $$$reportNull$$$0(1);
            }
            return (PsiType) psiCapturedWildcardType.getUpperBound().accept(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitArrayType(PsiArrayType psiArrayType) {
            if (psiArrayType == null) {
                $$$reportNull$$$0(2);
            }
            return ((PsiType) psiArrayType.getComponentType().accept(this)).createArrayType();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitDisjunctionType(PsiDisjunctionType psiDisjunctionType) {
            if (psiDisjunctionType == null) {
                $$$reportNull$$$0(3);
            }
            return psiDisjunctionType.getLeastUpperBound();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitIntersectionType(PsiIntersectionType psiIntersectionType) {
            if (psiIntersectionType == null) {
                $$$reportNull$$$0(6);
            }
            return PsiIntersectionType.createIntersection((PsiType[]) Arrays.stream(psiIntersectionType.getConjuncts()).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaVarTypeUtil$UpwardProjectionTypeVisitor$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m6648x1d9718fa((PsiType) obj);
                }
            }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaVarTypeUtil$UpwardProjectionTypeVisitor$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return JavaVarTypeUtil.UpwardProjectionTypeVisitor.lambda$visitIntersectionType$1(i);
                }
            }));
        }

        /* JADX INFO: renamed from: lambda$visitIntersectionType$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-JavaVarTypeUtil$UpwardProjectionTypeVisitor, reason: not valid java name */
        /* synthetic */ PsiType m6648x1d9718fa(PsiType psiType) {
            return (PsiType) psiType.accept(this);
        }

        static /* synthetic */ PsiType[] lambda$visitIntersectionType$1(int i) {
            return new PsiType[i];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitClassType(PsiClassType psiClassType) {
            if (psiClassType == null) {
                $$$reportNull$$$0(7);
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element == null) {
                return psiClassType;
            }
            PsiManager manager = element.getManager();
            PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
            PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
            for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(element)) {
                final PsiType psiTypeSubstitute = substitutor.substitute(psiTypeParameter);
                psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, psiTypeSubstitute);
                if (psiTypeSubstitute != null && JavaVarTypeUtil.mentionsRestrictedTypeVariables(psiTypeSubstitute)) {
                    if (psiTypeSubstitute instanceof PsiWildcardType) {
                        PsiWildcardType psiWildcardType = (PsiWildcardType) psiTypeSubstitute;
                        if (psiWildcardType.isExtends()) {
                            psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, PsiWildcardType.createExtends(manager, (PsiType) psiWildcardType.getExtendsBound().accept(this)));
                        }
                        if (psiWildcardType.isSuper()) {
                            psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, createDownwardProjection(manager, psiWildcardType.getSuperBound()));
                        }
                    } else {
                        PsiType psiType = (PsiType) RecursionManager.doPreventingRecursion(psiTypeSubstitute, true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaVarTypeUtil$UpwardProjectionTypeVisitor$$ExternalSyntheticLambda2
                            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                            public final Object compute() {
                                return this.f$0.m6647x8354240f(psiTypeSubstitute);
                            }
                        });
                        psiSubstitutorPut = psiType == null ? psiSubstitutorPut.put(psiTypeParameter, PsiWildcardType.createUnbounded(manager)) : (psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) || !tryUpperBound(element, psiTypeParameter, psiType)) ? psiSubstitutorPut.put(psiTypeParameter, createDownwardProjection(manager, psiTypeSubstitute)) : psiSubstitutorPut.put(psiTypeParameter, PsiWildcardType.createExtends(manager, psiType));
                    }
                }
            }
            return JavaPsiFacade.getElementFactory(element.getProject()).createType(element, psiSubstitutorPut);
        }

        /* JADX INFO: renamed from: lambda$visitClassType$2$org-jetbrains-kotlin-com-intellij-psi-impl-source-JavaVarTypeUtil$UpwardProjectionTypeVisitor, reason: not valid java name */
        /* synthetic */ PsiType m6647x8354240f(PsiType psiType) {
            return (PsiType) psiType.accept(this);
        }

        private static PsiWildcardType createDownwardProjection(PsiManager psiManager, PsiType psiType) {
            PsiType downwardProjection = JavaVarTypeUtil.getDownwardProjection(psiType);
            return downwardProjection != PsiType.NULL ? PsiWildcardType.createSuper(psiManager, downwardProjection) : PsiWildcardType.createUnbounded(psiManager);
        }

        private static boolean tryUpperBound(PsiClass psiClass, PsiTypeParameter psiTypeParameter, PsiType psiType) {
            PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
            if (extendsListTypes.length == 0) {
                return true;
            }
            PsiType psiTypeCreateIntersection = PsiIntersectionType.createIntersection(extendsListTypes);
            return PsiTypesUtil.mentionsTypeParameters(psiTypeCreateIntersection, ContainerUtil.newHashSet(psiClass.getTypeParameters())) || !psiType.isAssignableFrom(psiTypeCreateIntersection);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DownwardProjectionTypeVisitor extends PsiTypeVisitor<PsiType> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "capturedWildcardType";
                    break;
                case 2:
                    objArr[0] = "arrayType";
                    break;
                case 3:
                    objArr[0] = "intersectionType";
                    break;
                case 4:
                    objArr[0] = "lambdaExpressionType";
                    break;
                case 5:
                    objArr[0] = "methodReferenceType";
                    break;
                case 6:
                    objArr[0] = "classType";
                    break;
                default:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaVarTypeUtil$DownwardProjectionTypeVisitor";
            switch (i) {
                case 1:
                    objArr[2] = "visitCapturedWildcardType";
                    break;
                case 2:
                    objArr[2] = "visitArrayType";
                    break;
                case 3:
                    objArr[2] = "visitIntersectionType";
                    break;
                case 4:
                    objArr[2] = "visitLambdaExpressionType";
                    break;
                case 5:
                    objArr[2] = "visitMethodReferenceType";
                    break;
                case 6:
                    objArr[2] = "visitClassType";
                    break;
                default:
                    objArr[2] = "visitType";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitLambdaExpressionType(PsiLambdaExpressionType psiLambdaExpressionType) {
            if (psiLambdaExpressionType == null) {
                $$$reportNull$$$0(4);
            }
            return psiLambdaExpressionType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitMethodReferenceType(PsiMethodReferenceType psiMethodReferenceType) {
            if (psiMethodReferenceType == null) {
                $$$reportNull$$$0(5);
            }
            return psiMethodReferenceType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitType(PsiType psiType) {
            if (psiType == null) {
                $$$reportNull$$$0(0);
            }
            return psiType;
        }

        private DownwardProjectionTypeVisitor() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
            if (psiCapturedWildcardType == null) {
                $$$reportNull$$$0(1);
            }
            return (PsiType) psiCapturedWildcardType.getLowerBound().accept(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitArrayType(PsiArrayType psiArrayType) {
            if (psiArrayType == null) {
                $$$reportNull$$$0(2);
            }
            PsiType psiType = (PsiType) psiArrayType.getComponentType().accept(this);
            return psiType == PsiType.NULL ? PsiType.NULL : psiType.createArrayType();
        }

        static /* synthetic */ PsiType[] lambda$visitIntersectionType$1(int i) {
            return new PsiType[i];
        }

        /* JADX INFO: renamed from: lambda$visitIntersectionType$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-JavaVarTypeUtil$DownwardProjectionTypeVisitor, reason: not valid java name */
        /* synthetic */ PsiType m6646x2246a5d3(PsiType psiType) {
            return (PsiType) psiType.accept(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitIntersectionType(PsiIntersectionType psiIntersectionType) {
            if (psiIntersectionType == null) {
                $$$reportNull$$$0(3);
            }
            PsiType[] psiTypeArr = (PsiType[]) Arrays.stream(psiIntersectionType.getConjuncts()).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaVarTypeUtil$DownwardProjectionTypeVisitor$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m6646x2246a5d3((PsiType) obj);
                }
            }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaVarTypeUtil$DownwardProjectionTypeVisitor$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return JavaVarTypeUtil.DownwardProjectionTypeVisitor.lambda$visitIntersectionType$1(i);
                }
            });
            return ArrayUtil.find((PsiPrimitiveType[]) psiTypeArr, PsiType.NULL) > -1 ? PsiType.NULL : PsiIntersectionType.createIntersection(psiTypeArr);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public PsiType visitClassType(PsiClassType psiClassType) {
            if (psiClassType == null) {
                $$$reportNull$$$0(6);
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element != null) {
                PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
                for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(element)) {
                    PsiType psiTypeSubstitute = substitutor.substitute(psiTypeParameter);
                    if (psiTypeSubstitute != null) {
                        if (!JavaVarTypeUtil.mentionsRestrictedTypeVariables(psiTypeSubstitute)) {
                            psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, psiTypeSubstitute);
                        } else if (psiTypeSubstitute instanceof PsiWildcardType) {
                            PsiWildcardType psiWildcardType = (PsiWildcardType) psiTypeSubstitute;
                            if (psiWildcardType.isExtends()) {
                                PsiType psiType = (PsiType) psiWildcardType.getExtendsBound().accept(this);
                                if (psiType == PsiType.NULL) {
                                    return PsiType.NULL;
                                }
                                psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, PsiWildcardType.createExtends(psiTypeParameter.getManager(), psiType));
                            } else if (psiWildcardType.isSuper()) {
                                psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, JavaVarTypeUtil.getUpwardProjection(psiWildcardType.getSuperBound()));
                            } else {
                                return PsiType.NULL;
                            }
                        } else {
                            return PsiType.NULL;
                        }
                    } else {
                        return PsiType.NULL;
                    }
                }
                return JavaPsiFacade.getElementFactory(element.getProject()).createType(element, psiSubstitutorPut);
            }
            return PsiType.NULL;
        }
    }
}
