package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class InferenceVariable extends LightTypeParameter {
    private final Map<InferenceBound, List<PsiType>> myBounds;
    private final PsiElement myContext;
    private PsiType myInstantiation;
    private final String myName;
    private boolean myThrownBound;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceVariable";
        } else {
            objArr[0] = "baseClass";
        }
        if (i != 1) {
            objArr[1] = "getExtendsListTypes";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceVariable";
        }
        if (i == 1) {
            objArr[2] = "isInheritor";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameter, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public PsiTypeParameterListOwner getOwner() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return this == psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameter
    public boolean useDelegateToSubstitute() {
        return false;
    }

    public PsiTypeParameter getParameter() {
        return mo6090getDelegate();
    }

    InferenceVariable(PsiElement psiElement, PsiTypeParameter psiTypeParameter, String str) {
        super(psiTypeParameter);
        this.myBounds = new EnumMap(InferenceBound.class);
        this.myInstantiation = PsiType.NULL;
        this.myName = str;
        this.myContext = psiElement;
        TypeConversionUtil.markAsFreshVariable(this, psiElement);
    }

    public PsiType getInstantiation() {
        return this.myInstantiation;
    }

    public void setInstantiation(PsiType psiType) {
        this.myInstantiation = psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getExtendsListTypes() {
        ArrayList arrayList = new ArrayList();
        for (PsiType psiType : getBounds(InferenceBound.UPPER)) {
            if (psiType instanceof PsiClassType) {
                arrayList.add((PsiClassType) psiType);
            }
        }
        PsiClassType[] psiClassTypeArr = (PsiClassType[]) arrayList.toArray(PsiClassType.EMPTY_ARRAY);
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiClassTypeArr;
    }

    public static void addBound(PsiType psiType, PsiType psiType2, InferenceBound inferenceBound, InferenceSession inferenceSession) {
        InferenceVariable inferenceVariable = inferenceSession.getInferenceVariable(psiType);
        if (inferenceVariable != null) {
            inferenceVariable.addBound(psiType2, inferenceBound, inferenceSession.myIncorporationPhase);
        }
    }

    boolean addBound(PsiType psiType, InferenceBound inferenceBound, InferenceIncorporationPhase inferenceIncorporationPhase) {
        if (PsiUtil.resolveClassInClassTypeOnly(psiType) == this) {
            return false;
        }
        List<PsiType> listComputeIfAbsent = this.myBounds.computeIfAbsent(inferenceBound, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InferenceVariable.lambda$addBound$0((InferenceBound) obj);
            }
        });
        if (psiType == null) {
            psiType = PsiType.NULL;
        }
        if (inferenceIncorporationPhase != null && listComputeIfAbsent.contains(psiType)) {
            return false;
        }
        listComputeIfAbsent.add(psiType);
        if (inferenceIncorporationPhase == null) {
            return true;
        }
        inferenceIncorporationPhase.addBound(this, psiType, inferenceBound);
        return true;
    }

    static /* synthetic */ List lambda$addBound$0(InferenceBound inferenceBound) {
        return new ArrayList();
    }

    public List<PsiType> getBounds(InferenceBound inferenceBound) {
        List<PsiType> list = this.myBounds.get(inferenceBound);
        return list != null ? new ArrayList(list) : Collections.emptyList();
    }

    public Set<InferenceVariable> getDependencies(final InferenceSession inferenceSession) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectBoundDependencies(inferenceSession, linkedHashSet);
        collectTransitiveDependencies(inferenceSession, linkedHashSet, linkedHashSet);
        if ((!inferenceSession.hasCapture(this) && linkedHashSet.isEmpty()) || !inferenceSession.hasCapture(this)) {
            return linkedHashSet;
        }
        linkedHashSet.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InferenceVariable.lambda$getDependencies$1(inferenceSession, (InferenceVariable) obj);
            }
        });
        inferenceSession.collectCaptureDependencies(this, linkedHashSet);
        return linkedHashSet;
    }

    static /* synthetic */ boolean lambda$getDependencies$1(InferenceSession inferenceSession, InferenceVariable inferenceVariable) {
        return !inferenceSession.hasCapture(inferenceVariable);
    }

    private void collectTransitiveDependencies(InferenceSession inferenceSession, Set<? extends InferenceVariable> set, Set<? super InferenceVariable> set2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<? extends InferenceVariable> it = set.iterator();
        while (it.hasNext()) {
            it.next().collectBoundDependencies(inferenceSession, linkedHashSet);
        }
        linkedHashSet.removeAll(set2);
        linkedHashSet.remove(this);
        if (linkedHashSet.isEmpty()) {
            return;
        }
        set2.addAll(linkedHashSet);
        collectTransitiveDependencies(inferenceSession, linkedHashSet, set2);
    }

    private void collectBoundDependencies(InferenceSession inferenceSession, Set<? super InferenceVariable> set) {
        for (List<PsiType> list : this.myBounds.values()) {
            if (list != null) {
                Iterator<PsiType> it = list.iterator();
                while (it.hasNext()) {
                    inferenceSession.collectDependencies(it.next(), set);
                }
            }
        }
    }

    boolean isThrownBound() {
        return this.myThrownBound;
    }

    public void setThrownBound() {
        this.myThrownBound = true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        Iterator<PsiType> it = getBounds(InferenceBound.UPPER).iterator();
        while (it.hasNext()) {
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(it.next());
            if (psiClassResolveClassInClassTypeOnly != null) {
                if (getManager().areElementsEquivalent(psiClass, psiClassResolveClassInClassTypeOnly)) {
                    return true;
                }
                if (z && psiClassResolveClassInClassTypeOnly.isInheritor(psiClass, true)) {
                    return true;
                }
            }
        }
        return super.isInheritor(psiClass, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameter, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return mo6090getDelegate().toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        return this.myName;
    }

    PsiElement getCallContext() {
        return this.myContext;
    }
}
