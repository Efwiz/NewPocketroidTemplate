package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiMethodReferenceCompatibilityConstraint implements ConstraintFormula {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiMethodReferenceCompatibilityConstraint.class);
    private final PsiMethodReferenceExpression myExpression;
    private PsiType myT;

    public PsiMethodReferenceCompatibilityConstraint(PsiMethodReferenceExpression psiMethodReferenceExpression, PsiType psiType) {
        this.myExpression = psiMethodReferenceExpression;
        this.myT = psiType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(InferenceSession inferenceSession, List<? super ConstraintFormula> list) {
        PsiType psiTypePatchMethodGetClassReturnType;
        String str;
        PsiType psiTypeCreateType;
        int i = 0;
        if (!LambdaUtil.isFunctionalType(this.myT)) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.not.a.functional.interface", inferenceSession.getPresentableText(this.myT)));
            return false;
        }
        PsiType groundTargetType = this.myExpression.getGroundTargetType(this.myT);
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(groundTargetType);
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
        if (functionalInterfaceMethod == null) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.no.valid.function.type.found", inferenceSession.getPresentableText(this.myT)));
            return false;
        }
        PsiSubstitutor substitutor = LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType);
        MethodSignature signature = functionalInterfaceMethod.getSignature(substitutor);
        PsiParameter[] parameters = functionalInterfaceMethod.getParameterList().getParameters();
        PsiType psiTypeSubstitute = substitutor.substitute(functionalInterfaceMethod.getReturnType());
        PsiType[] typeParameters = this.myExpression.getTypeParameters();
        PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult = PsiMethodReferenceUtil.getQualifierResolveResult(this.myExpression);
        String str2 = "unsound.capture.conversion.java.spec.change";
        if (this.myExpression.isExact()) {
            PsiMember potentiallyApplicableMember = this.myExpression.getPotentiallyApplicableMember();
            LOG.assertTrue(potentiallyApplicableMember != null);
            PsiClass containingClass = potentiallyApplicableMember.getContainingClass();
            PsiClass containingClass2 = qualifierResolveResult.getContainingClass();
            PsiSubstitutor substitutor2 = getSubstitutor(signature, qualifierResolveResult, potentiallyApplicableMember, containingClass, this.myExpression);
            PsiTypeParameter[] typeParameters2 = ((PsiTypeParameterListOwner) potentiallyApplicableMember).getTypeParameters();
            int length = typeParameters2.length;
            int i2 = 0;
            while (i2 < length) {
                PsiTypeParameter psiTypeParameter = typeParameters2[i2];
                PsiTypeParameter[] psiTypeParameterArr = typeParameters2;
                if (i < typeParameters.length) {
                    substitutor2 = substitutor2.put(psiTypeParameter, typeParameters[i]);
                    i++;
                }
                i2++;
                typeParameters2 = psiTypeParameterArr;
            }
            boolean z = potentiallyApplicableMember instanceof PsiMethod;
            PsiParameter[] parameters2 = z ? ((PsiMethod) potentiallyApplicableMember).getParameterList().getParameters() : PsiParameter.EMPTY_ARRAY;
            if (parameters.length == parameters2.length + 1) {
                PsiType qualifierType = PsiMethodReferenceUtil.getQualifierType(this.myExpression);
                if (PsiUtil.resolveClassInType(qualifierType) != null) {
                    list.add(new StrictSubtypingConstraint(inferenceSession.substituteWithInferenceVariables(qualifierType), signature.getParameterTypes()[0]));
                }
                int i3 = 1;
                while (i3 < parameters.length) {
                    list.add(new TypeCompatibilityConstraint(inferenceSession.substituteWithInferenceVariables(substitutor2.substitute(parameters2[i3 - 1].getType())), PsiUtil.captureToplevelWildcards(signature.getParameterTypes()[i3], this.myExpression)));
                    i3++;
                    str2 = str2;
                }
                str = str2;
            } else {
                str = "unsound.capture.conversion.java.spec.change";
                if (parameters.length == parameters2.length) {
                    for (int i4 = 0; i4 < parameters.length; i4++) {
                        list.add(new TypeCompatibilityConstraint(inferenceSession.substituteWithInferenceVariables(substitutor2.substitute(parameters2[i4].getType())), PsiUtil.captureToplevelWildcards(signature.getParameterTypes()[i4], this.myExpression)));
                    }
                } else {
                    inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.incompatible.parameter.types.in.method.reference", new Object[0]));
                    return false;
                }
            }
            if (PsiType.VOID.equals(psiTypeSubstitute) || psiTypeSubstitute == null) {
                return true;
            }
            if (z) {
                PsiMethod psiMethod = (PsiMethod) potentiallyApplicableMember;
                psiTypeCreateType = PsiTypesUtil.patchMethodGetClassReturnType(this.myExpression, psiMethod);
                if (psiTypeCreateType == null) {
                    psiTypeCreateType = psiMethod.getReturnType();
                }
            } else {
                psiTypeCreateType = null;
            }
            if (PsiType.VOID.equals(psiTypeCreateType)) {
                inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.incompatible.types.expected.not.void.got.void.method.reference", new Object[0]));
                return false;
            }
            if (psiTypeCreateType == null && (((potentiallyApplicableMember instanceof PsiClass) || (z && ((PsiMethod) potentiallyApplicableMember).isConstructor())) && containingClass2 != null)) {
                psiTypeCreateType = JavaPsiFacade.getElementFactory(potentiallyApplicableMember.getProject()).createType(containingClass2, PsiSubstitutor.EMPTY);
            }
            if (psiTypeCreateType == null) {
                return true;
            }
            PsiType psiTypeSubstitute2 = substitutor2.substitute(psiTypeCreateType);
            if (!Registry.is(str)) {
                psiTypeSubstitute2 = PsiUtil.captureToplevelWildcards(psiTypeSubstitute2, this.myExpression);
            }
            list.add(new TypeCompatibilityConstraint(psiTypeSubstitute, inferenceSession.substituteWithInferenceVariables(psiTypeSubstitute2)));
            return true;
        }
        for (PsiType psiType : signature.getParameterTypes()) {
            if (!inferenceSession.isProperType(psiType)) {
                return false;
            }
        }
        JavaResolveResult javaResolveResultAdvancedResolve = this.myExpression.advancedResolve(false);
        PsiElement element = javaResolveResultAdvancedResolve.getElement();
        if (element == null || ((javaResolveResultAdvancedResolve instanceof MethodCandidateInfo) && !((MethodCandidateInfo) javaResolveResultAdvancedResolve).isApplicable())) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.declaration.for.the.method.reference.not.found", new Object[0]));
            return false;
        }
        if (PsiType.VOID.equals(psiTypeSubstitute) || psiTypeSubstitute == null || !(element instanceof PsiMethod)) {
            return true;
        }
        PsiMethod psiMethod2 = (PsiMethod) element;
        PsiClass containingClass3 = psiMethod2.getContainingClass();
        Logger logger = LOG;
        logger.assertTrue(containingClass3 != 0, psiMethod2);
        PsiSubstitutor substitutor3 = getSubstitutor(signature, qualifierResolveResult, psiMethod2, containingClass3, this.myExpression);
        if (psiMethod2.isConstructor()) {
            psiTypePatchMethodGetClassReturnType = JavaPsiFacade.getElementFactory(psiMethod2.getProject()).createType(containingClass3, PsiSubstitutor.EMPTY);
        } else {
            psiTypePatchMethodGetClassReturnType = PsiTypesUtil.patchMethodGetClassReturnType(this.myExpression, psiMethod2);
            if (psiTypePatchMethodGetClassReturnType == null) {
                psiTypePatchMethodGetClassReturnType = psiMethod2.getReturnType();
            }
        }
        logger.assertTrue(psiTypePatchMethodGetClassReturnType != null, psiMethod2);
        if (typeParameters.length == 0) {
            PsiTypeParameter[] typeParameters3 = psiMethod2.isConstructor() ? containingClass3.getTypeParameters() : psiMethod2.getTypeParameters();
            if (typeParameters3.length > 0) {
                logger.assertTrue(classResolveResultResolveGenericsClassInType.getElement() != null);
                if (PsiTypesUtil.mentionsTypeParameters(psiTypePatchMethodGetClassReturnType, ContainerUtil.newHashSet(typeParameters3))) {
                    inferenceSession.initBounds(this.myExpression, substitutor3, typeParameters3);
                    inferenceSession.collectApplicabilityConstraints(this.myExpression, (MethodCandidateInfo) javaResolveResultAdvancedResolve, groundTargetType);
                    inferenceSession.registerReturnTypeConstraints(substitutor3.substitute(psiTypePatchMethodGetClassReturnType), psiTypeSubstitute, this.myExpression);
                    return true;
                }
            }
        }
        if (PsiType.VOID.equals(psiTypePatchMethodGetClassReturnType)) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.expected.non.void.got.void.method.reference", new Object[0]));
            return false;
        }
        int i5 = 0;
        for (PsiTypeParameter psiTypeParameter2 : psiMethod2.getTypeParameters()) {
            if (i5 < typeParameters.length) {
                substitutor3 = substitutor3.put(psiTypeParameter2, typeParameters[i5]);
                i5++;
            }
        }
        PsiClass containingClass4 = qualifierResolveResult.getContainingClass();
        if (containingClass4 != 0 && PsiUtil.isRawSubstitutor(containingClass4, qualifierResolveResult.getSubstitutor()) && getParameterization(signature, qualifierResolveResult, psiMethod2, this.myExpression, containingClass4) == null) {
            if (!PsiMethodReferenceUtil.isSecondSearchPossible(signature.getParameterTypes(), qualifierResolveResult, this.myExpression)) {
                inferenceSession.initBounds(this.myExpression, containingClass4.getTypeParameters());
            } else {
                psiTypePatchMethodGetClassReturnType = TypeConversionUtil.erasure(psiTypePatchMethodGetClassReturnType);
            }
        }
        PsiType psiTypeSubstitute3 = substitutor3.substitute(psiTypePatchMethodGetClassReturnType);
        if (!Registry.is("unsound.capture.conversion.java.spec.change")) {
            psiTypeSubstitute3 = PsiUtil.captureToplevelWildcards(psiTypeSubstitute3, this.myExpression);
        }
        list.add(new TypeCompatibilityConstraint(psiTypeSubstitute, inferenceSession.substituteWithInferenceVariables(psiTypeSubstitute3)));
        return true;
    }

    public static PsiSubstitutor getSubstitutor(MethodSignature methodSignature, PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult, PsiMember psiMember, PsiClass psiClass, PsiMethodReferenceExpression psiMethodReferenceExpression) {
        PsiClass containingClass = qualifierResolveResult.getContainingClass();
        PsiSubstitutor substitutor = qualifierResolveResult.getSubstitutor();
        if (containingClass != null && psiClass != null) {
            if (PsiUtil.isRawSubstitutor(containingClass, substitutor)) {
                PsiClassType parameterization = getParameterization(methodSignature, qualifierResolveResult, psiMember, psiMethodReferenceExpression, containingClass);
                if (parameterization != null) {
                    substitutor = TypeConversionUtil.getSuperClassSubstitutor(containingClass, (PsiClassType) PsiUtil.captureToplevelWildcards(parameterization, psiMethodReferenceExpression));
                } else {
                    substitutor = PsiSubstitutor.EMPTY;
                }
            }
            if (containingClass.isInheritor(psiClass, true)) {
                substitutor = TypeConversionUtil.getClassSubstitutor(psiClass, containingClass, substitutor);
                LOG.assertTrue(substitutor != null);
            }
        }
        return substitutor;
    }

    private static PsiClassType getParameterization(MethodSignature methodSignature, PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult, PsiMember psiMember, PsiMethodReferenceExpression psiMethodReferenceExpression, PsiClass psiClass) {
        PsiClassType subclassType;
        if (!(psiMember instanceof PsiMethod) || !PsiMethodReferenceUtil.isSecondSearchPossible(methodSignature.getParameterTypes(), qualifierResolveResult, psiMethodReferenceExpression) || (subclassType = StrictSubtypingConstraint.getSubclassType(psiClass, methodSignature.getParameterTypes()[0], true)) == null || subclassType.isRaw()) {
            return null;
        }
        return subclassType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public void apply(PsiSubstitutor psiSubstitutor, boolean z) {
        this.myT = psiSubstitutor.substitute(this.myT);
    }

    public String toString() {
        return this.myExpression.getText() + " -> " + this.myT.getPresentableText();
    }
}
