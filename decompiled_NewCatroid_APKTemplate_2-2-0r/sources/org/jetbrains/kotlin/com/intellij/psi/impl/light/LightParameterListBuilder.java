package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;

/* JADX INFO: loaded from: classes3.dex */
public class LightParameterListBuilder extends LightElement implements PsiParameterList {
    private PsiParameter[] myCachedParameters;
    private final List<PsiParameter> myParameters;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "parameter";
        } else if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameterListBuilder";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameterListBuilder";
        } else {
            objArr[1] = "getParameters";
        }
        if (i == 1) {
            objArr[2] = "getParameterIndex";
        } else if (i == 2) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public LightParameterListBuilder(PsiManager psiManager, Language language) {
        super(psiManager, language);
        this.myParameters = new ArrayList();
    }

    public void addParameter(PsiParameter psiParameter) {
        this.myParameters.add(psiParameter);
        this.myCachedParameters = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Light parameter list";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter[] getParameters() {
        if (this.myCachedParameters == null) {
            if (this.myParameters.isEmpty()) {
                this.myCachedParameters = PsiParameter.EMPTY_ARRAY;
            } else {
                this.myCachedParameters = (PsiParameter[]) this.myParameters.toArray(PsiParameter.EMPTY_ARRAY);
            }
        }
        PsiParameter[] psiParameterArr = this.myCachedParameters;
        if (psiParameterArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter getParameter(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index is negative: " + i);
        }
        if (i < this.myParameters.size()) {
            return this.myParameters.get(i);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParameterIndex(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(1);
        }
        return this.myParameters.indexOf(psiParameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParametersCount() {
        return this.myParameters.size();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitParameterList(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return (String) this.myParameters.stream().map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PsiParameter) obj).getText();
            }
        }).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((String) obj);
            }
        }).collect(Collectors.joining(",", "(", Constant.AFTER_QUTO));
    }
}
