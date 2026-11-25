package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.psi.PsiMirrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LightParameterListWrapper extends LightElement implements PsiMirrorElement, PsiParameterList {
    private final PsiParameterList myDelegate;
    private final PsiSubstitutor mySubstitutor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "substitutor";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameterListWrapper";
        } else if (i != 4) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = "parameter";
        }
        if (i == 2) {
            objArr[1] = "getPrototype";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameterListWrapper";
        } else {
            objArr[1] = "getParameters";
        }
        if (i != 2 && i != 3) {
            if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getParameterIndex";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightParameterListWrapper(PsiParameterList psiParameterList, PsiSubstitutor psiSubstitutor) {
        super(psiParameterList.getManager(), psiParameterList.getLanguage());
        if (psiParameterList == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        this.myDelegate = psiParameterList;
        this.mySubstitutor = psiSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMirrorElement
    public PsiParameterList getPrototype() {
        PsiParameterList psiParameterList = this.myDelegate;
        if (psiParameterList == null) {
            $$$reportNull$$$0(2);
        }
        return psiParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter[] getParameters() {
        PsiParameter[] parameters;
        if (this.mySubstitutor == PsiSubstitutor.EMPTY) {
            parameters = this.myDelegate.getParameters();
        } else {
            parameters = (PsiParameter[]) ContainerUtil.map2Array(this.myDelegate.getParameters(), PsiParameter.class, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListWrapper$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6623xf5df4958((PsiParameter) obj);
                }
            });
        }
        if (parameters == null) {
            $$$reportNull$$$0(3);
        }
        return parameters;
    }

    /* JADX INFO: renamed from: lambda$getParameters$0$org-jetbrains-kotlin-com-intellij-psi-impl-light-LightParameterListWrapper, reason: not valid java name */
    /* synthetic */ PsiParameter m6623xf5df4958(PsiParameter psiParameter) {
        return new LightParameterWrapper(psiParameter, this.mySubstitutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParameterIndex(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(4);
        }
        if (psiParameter instanceof LightParameterWrapper) {
            psiParameter = ((LightParameterWrapper) psiParameter).getPrototype();
        }
        return this.myDelegate.getParameterIndex(psiParameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParametersCount() {
        return this.myDelegate.getParametersCount();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "List PSI parameter list wrapper: " + this.myDelegate;
    }
}
