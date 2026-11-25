package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.StrictSubtypingConstraint;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.TypeCompatibilityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.TypeEqualityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class InferenceIncorporationPhase {
    private static final Logger LOG = Logger.getInstance((Class<?>) InferenceIncorporationPhase.class);
    private final List<Pair<InferenceVariable[], PsiClassType>> myCaptures = new ArrayList();
    private final Map<InferenceVariable, Map<InferenceBound, Set<PsiType>>> myCurrentBounds = new HashMap();
    private final InferenceSession mySession;

    public InferenceIncorporationPhase(InferenceSession inferenceSession) {
        this.mySession = inferenceSession;
    }

    public void addCapture(InferenceVariable[] inferenceVariableArr, PsiClassType psiClassType) {
        this.myCaptures.add(Pair.create(inferenceVariableArr, psiClassType));
    }

    public void forgetCaptures(List<? extends InferenceVariable> list) {
        for (InferenceVariable inferenceVariable : list) {
            Iterator<Pair<InferenceVariable[], PsiClassType>> it = this.myCaptures.iterator();
            while (it.hasNext()) {
                if (isCapturedVariable(inferenceVariable, it.next())) {
                    it.remove();
                }
            }
        }
    }

    public boolean hasCaptureConstraints(Iterable<? extends InferenceVariable> iterable) {
        for (InferenceVariable inferenceVariable : iterable) {
            Iterator<Pair<InferenceVariable[], PsiClassType>> it = this.myCaptures.iterator();
            while (it.hasNext()) {
                if (isCapturedVariable(inferenceVariable, it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isCapturedVariable(InferenceVariable inferenceVariable, Pair<InferenceVariable[], PsiClassType> pair) {
        for (InferenceVariable inferenceVariable2 : pair.first) {
            if (inferenceVariable == inferenceVariable2) {
                return true;
            }
        }
        return false;
    }

    public void collectCaptureDependencies(InferenceVariable inferenceVariable, Set<? super InferenceVariable> set) {
        for (Pair<InferenceVariable[], PsiClassType> pair : this.myCaptures) {
            if (isCapturedVariable(inferenceVariable, pair)) {
                this.mySession.collectDependencies(pair.second, set);
                ContainerUtil.addAll(set, pair.first);
            }
        }
    }

    public List<Pair<InferenceVariable[], PsiClassType>> getCaptures() {
        return this.myCaptures;
    }

    public boolean incorporate() {
        Iterator<Pair<InferenceVariable[], PsiClassType>> it;
        Iterator<Pair<InferenceVariable[], PsiClassType>> it2;
        Map<InferenceBound, Set<PsiType>> map;
        for (InferenceVariable inferenceVariable : this.mySession.getInferenceVariables()) {
            if (inferenceVariable.getInstantiation() == PsiType.NULL && (map = this.myCurrentBounds.get(inferenceVariable)) != null) {
                List<PsiType> bounds = inferenceVariable.getBounds(InferenceBound.EQ);
                List<PsiType> bounds2 = inferenceVariable.getBounds(InferenceBound.UPPER);
                List<PsiType> bounds3 = inferenceVariable.getBounds(InferenceBound.LOWER);
                Set<PsiType> set = map.get(InferenceBound.EQ);
                Set<PsiType> set2 = map.get(InferenceBound.UPPER);
                Set<PsiType> set3 = map.get(InferenceBound.LOWER);
                if (set != null) {
                    eqEq(bounds, set);
                }
                upDown(bounds3, set3, bounds2, set2);
                upDown(bounds, set, bounds2, set2);
                upDown(bounds3, set3, bounds, set);
                if (set2 != null) {
                    upUp(bounds2);
                }
            }
        }
        Iterator<Pair<InferenceVariable[], PsiClassType>> it3 = this.myCaptures.iterator();
        while (it3.hasNext()) {
            Pair<InferenceVariable[], PsiClassType> next = it3.next();
            PsiClassType psiClassType = next.second;
            PsiClass psiClassResolve = psiClassType.resolve();
            LOG.assertTrue(psiClassResolve != null);
            InferenceVariable[] inferenceVariableArr = next.first;
            PsiType[] parameters = psiClassType.getParameters();
            PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
            if (Registry.is("javac.fresh.variables.for.captured.wildcards.only")) {
                ArrayList arrayList = new ArrayList();
                PsiTypeParameter[] typeParameters = psiClassResolve.getTypeParameters();
                for (int i = 0; i < parameters.length; i++) {
                    PsiType psiType = parameters[i];
                    if (psiType instanceof PsiWildcardType) {
                        arrayList.add(psiType);
                    } else {
                        psiSubstitutorPut = psiSubstitutorPut.put(typeParameters[i], psiType);
                    }
                }
                parameters = (PsiType[]) arrayList.toArray(PsiType.EMPTY_ARRAY);
            }
            if (inferenceVariableArr.length == parameters.length) {
                int i2 = 0;
                while (i2 < parameters.length) {
                    PsiType psiType2 = parameters[i2];
                    InferenceVariable inferenceVariable2 = inferenceVariableArr[i2];
                    List<PsiType> bounds4 = inferenceVariable2.getBounds(InferenceBound.EQ);
                    List<PsiType> bounds5 = inferenceVariable2.getBounds(InferenceBound.UPPER);
                    List<PsiType> bounds6 = inferenceVariable2.getBounds(InferenceBound.LOWER);
                    if (psiType2 instanceof PsiWildcardType) {
                        Iterator<PsiType> it4 = bounds4.iterator();
                        while (it4.hasNext()) {
                            if (!isInferenceVariableOrFreshTypeParameter(inferenceVariable2, it4.next()).booleanValue()) {
                                return false;
                            }
                        }
                        PsiType[] extendsListTypes = inferenceVariable2.getParameter().getExtendsListTypes();
                        PsiType psiType3 = null;
                        for (PsiType greatestLowerBound : extendsListTypes) {
                            if (psiType3 != null) {
                                greatestLowerBound = GenericsUtil.getGreatestLowerBound(psiType3, greatestLowerBound);
                            }
                            psiType3 = greatestLowerBound;
                        }
                        PsiType psiTypeSubstitute = psiSubstitutorPut.substitute(psiType3);
                        PsiWildcardType psiWildcardType = (PsiWildcardType) psiType2;
                        if (!psiWildcardType.isBounded()) {
                            for (PsiType psiType4 : bounds5) {
                                if (psiTypeSubstitute != null && this.mySession.getInferenceVariable(psiType4) == null) {
                                    addConstraint(new StrictSubtypingConstraint(psiType4, this.mySession.substituteWithInferenceVariables(psiTypeSubstitute)));
                                }
                            }
                            Iterator<PsiType> it5 = bounds6.iterator();
                            while (it5.hasNext()) {
                                if (isInferenceVariableOrFreshTypeParameter(inferenceVariable2, it5.next()).booleanValue()) {
                                    return false;
                                }
                            }
                            it = it3;
                        } else if (psiWildcardType.isExtends()) {
                            PsiType extendsBound = psiWildcardType.getExtendsBound();
                            for (PsiType psiType5 : bounds5) {
                                if (this.mySession.getInferenceVariable(psiType5) == null) {
                                    it2 = it3;
                                    if ((extendsListTypes.length == 1 && extendsListTypes[0].equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) || extendsListTypes.length == 0) {
                                        addConstraint(new StrictSubtypingConstraint(psiType5, extendsBound));
                                    } else if (extendsBound.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) && psiTypeSubstitute != null) {
                                        addConstraint(new StrictSubtypingConstraint(psiType5, this.mySession.substituteWithInferenceVariables(psiTypeSubstitute)));
                                    }
                                } else {
                                    it2 = it3;
                                }
                                it3 = it2;
                            }
                            it = it3;
                            Iterator<PsiType> it6 = bounds6.iterator();
                            while (it6.hasNext()) {
                                if (isInferenceVariableOrFreshTypeParameter(inferenceVariable2, it6.next()).booleanValue()) {
                                    return false;
                                }
                            }
                        } else {
                            it = it3;
                            LOG.assertTrue(psiWildcardType.isSuper());
                            PsiType superBound = psiWildcardType.getSuperBound();
                            for (PsiType psiType6 : bounds5) {
                                if (psiTypeSubstitute != null && this.mySession.getInferenceVariable(psiType6) == null) {
                                    addConstraint(new StrictSubtypingConstraint(this.mySession.substituteWithInferenceVariables(psiTypeSubstitute), psiType6));
                                }
                            }
                            for (PsiType psiType7 : bounds6) {
                                if (this.mySession.getInferenceVariable(psiType7) == null) {
                                    addConstraint(new StrictSubtypingConstraint(superBound, psiType7));
                                }
                            }
                        }
                    } else {
                        it = it3;
                        inferenceVariable2.addBound(psiType2, InferenceBound.EQ, this);
                    }
                    i2++;
                    it3 = it;
                }
            }
        }
        return true;
    }

    protected void upDown(List<? extends PsiType> list, Collection<? extends PsiType> collection, List<? extends PsiType> list2, Collection<? extends PsiType> collection2) {
        if (collection != null) {
            upDown(collection, list2);
        }
        if (collection2 != null) {
            upDown(list, collection2);
        }
    }

    private static Boolean isInferenceVariableOrFreshTypeParameter(InferenceVariable inferenceVariable, PsiType psiType) {
        PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiType);
        if ((psiClassResolveClassInClassTypeOnly instanceof InferenceVariable) || (((psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter) && TypeConversionUtil.isFreshVariable((PsiTypeParameter) psiClassResolveClassInClassTypeOnly)) || ((psiType instanceof PsiCapturedWildcardType) && psiType.equals(inferenceVariable.getUserData(InferenceSession.ORIGINAL_CAPTURE))))) {
            return true;
        }
        return false;
    }

    boolean isFullyIncorporated() {
        Map<InferenceBound, Set<PsiType>> mapRemove;
        boolean zCrossVariables = false;
        for (InferenceVariable inferenceVariable : this.mySession.getInferenceVariables()) {
            if (inferenceVariable.getInstantiation() == PsiType.NULL && (mapRemove = this.myCurrentBounds.remove(inferenceVariable)) != null) {
                Set<PsiType> set = mapRemove.get(InferenceBound.UPPER);
                Set<PsiType> set2 = mapRemove.get(InferenceBound.LOWER);
                if (set != null) {
                    zCrossVariables |= crossVariables(inferenceVariable, set, set2, InferenceBound.LOWER);
                }
                if (set2 != null) {
                    zCrossVariables |= crossVariables(inferenceVariable, set2, set, InferenceBound.UPPER);
                }
            }
        }
        return !zCrossVariables;
    }

    private boolean crossVariables(InferenceVariable inferenceVariable, Collection<? extends PsiType> collection, Collection<? extends PsiType> collection2, InferenceBound inferenceBound) {
        InferenceBound inferenceBound2;
        if (inferenceBound == InferenceBound.LOWER) {
            inferenceBound2 = InferenceBound.UPPER;
        } else {
            inferenceBound2 = InferenceBound.LOWER;
        }
        Iterator<? extends PsiType> it = collection.iterator();
        boolean zAddBound = false;
        while (it.hasNext()) {
            InferenceVariable inferenceVariable2 = this.mySession.getInferenceVariable(it.next());
            if (inferenceVariable2 != null && inferenceVariable != inferenceVariable2) {
                if (collection2 != null) {
                    Iterator<? extends PsiType> it2 = collection2.iterator();
                    while (it2.hasNext()) {
                        zAddBound |= inferenceVariable2.addBound(it2.next(), inferenceBound, this);
                    }
                }
                Iterator<PsiType> it3 = inferenceVariable2.getBounds(inferenceBound2).iterator();
                while (it3.hasNext()) {
                    zAddBound |= inferenceVariable.addBound(it3.next(), inferenceBound2, this);
                }
            }
        }
        return zAddBound;
    }

    private void upDown(Collection<? extends PsiType> collection, Collection<? extends PsiType> collection2) {
        for (PsiType psiType : collection2) {
            if (psiType != null && !PsiType.NULL.equals(psiType) && !(psiType instanceof PsiWildcardType)) {
                for (PsiType psiType2 : collection) {
                    if (psiType2 != null && !PsiType.NULL.equals(psiType2) && !(psiType2 instanceof PsiWildcardType)) {
                        if (Registry.is("javac.unchecked.subtyping.during.incorporation", true)) {
                            if (TypeCompatibilityConstraint.isUncheckedConversion(psiType, psiType2, this.mySession)) {
                                if ((PsiUtil.resolveClassInType(psiType2) instanceof PsiTypeParameter) && !this.mySession.isProperType(psiType)) {
                                    this.mySession.setErased();
                                }
                            } else if (!this.mySession.isProperType(psiType) && (psiType2 instanceof PsiCapturedWildcardType) && TypeCompatibilityConstraint.isUncheckedConversion(psiType, ((PsiCapturedWildcardType) psiType2).getUpperBound(), this.mySession)) {
                                this.mySession.setErased();
                            }
                        }
                        addConstraint(new StrictSubtypingConstraint(psiType, psiType2));
                    }
                }
            }
        }
    }

    private void eqEq(List<? extends PsiType> list, Collection<? extends PsiType> collection) {
        int i = 0;
        while (i < list.size()) {
            PsiType psiType = list.get(i);
            boolean zContains = collection.contains(psiType);
            i++;
            for (int i2 = i; i2 < list.size(); i2++) {
                PsiType psiType2 = list.get(i2);
                if (zContains || collection.contains(psiType2)) {
                    addConstraint(new TypeEqualityConstraint(psiType2, psiType));
                }
            }
        }
    }

    private void upUp(List<? extends PsiType> list) {
        InferenceSession.findParameterizationOfTheSameGenericClass(list, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceIncorporationPhase$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return this.f$0.m6661x6cffc31f((Pair) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$upUp$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-InferenceIncorporationPhase, reason: not valid java name */
    /* synthetic */ boolean m6661x6cffc31f(Pair pair) {
        PsiType psiType = (PsiType) pair.first;
        PsiType psiType2 = (PsiType) pair.second;
        if ((psiType instanceof PsiWildcardType) || (psiType2 instanceof PsiWildcardType) || psiType == null || psiType2 == null) {
            return false;
        }
        addConstraint(new TypeEqualityConstraint(psiType, psiType2));
        return false;
    }

    private void addConstraint(ConstraintFormula constraintFormula) {
        this.mySession.addConstraint(constraintFormula);
    }

    static /* synthetic */ Map lambda$addBound$1(InferenceVariable inferenceVariable) {
        return new HashMap();
    }

    public void addBound(InferenceVariable inferenceVariable, PsiType psiType, InferenceBound inferenceBound) {
        this.myCurrentBounds.computeIfAbsent(inferenceVariable, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceIncorporationPhase$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InferenceIncorporationPhase.lambda$addBound$1((InferenceVariable) obj);
            }
        }).computeIfAbsent(inferenceBound, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceIncorporationPhase$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InferenceIncorporationPhase.lambda$addBound$2((InferenceBound) obj);
            }
        }).add(psiType);
    }

    static /* synthetic */ Set lambda$addBound$2(InferenceBound inferenceBound) {
        return new LinkedHashSet();
    }
}
