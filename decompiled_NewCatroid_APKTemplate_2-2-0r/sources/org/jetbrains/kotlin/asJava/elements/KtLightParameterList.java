package org.jetbrains.kotlin.asJava.elements;

import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFunction;

/* JADX INFO: compiled from: KtLightParameterList.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0012\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0096\u0002J\u0010\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\nH\u0016J\u0013\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\rH\u0016¢\u0006\u0002\u0010\u000fJ\b\u0010!\u001a\u00020\u0006H\u0016J\b\u0010\"\u001a\u00020\u0006H\u0016R!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightParameterList;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameterList;", "parent", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "parametersCount", "", "computeParameters", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;ILkotlin/jvm/functions/Function0;)V", "_parameters", "", "get_parameters", "()[Lcom/intellij/psi/PsiParameter;", "_parameters$delegate", "Lkotlin/Lazy;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getParameterIndex", "parameter", "getParameters", "getParametersCount", "hashCode", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightParameterList extends KtLightElementBase implements PsiParameterList {

    /* JADX INFO: renamed from: _parameters$delegate, reason: from kotlin metadata */
    private final Lazy _parameters;
    private final int parametersCount;
    private final KtLightMethod parent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightParameterList(KtLightMethod parent, int i, final Function0<? extends List<? extends PsiParameter>> computeParameters) {
        super(parent);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(computeParameters, "computeParameters");
        this.parent = parent;
        this.parametersCount = i;
        this._parameters = ImplUtilsKt.lazyPub(new Function0<PsiParameter[]>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightParameterList$_parameters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiParameter[] invoke() {
                return (PsiParameter[]) computeParameters.invoke().toArray(new PsiParameter[0]);
            }
        });
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtElement getKotlinOrigin() {
        T kotlinOrigin = this.parent.getKotlinOrigin();
        KtFunction ktFunction = kotlinOrigin instanceof KtFunction ? (KtFunction) kotlinOrigin : null;
        return ktFunction != null ? ktFunction.getValueParameterList() : null;
    }

    private final PsiParameter[] get_parameters() {
        return (PsiParameter[]) this._parameters.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter[] getParameters() {
        return get_parameters();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParameterIndex(PsiParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return ArraysKt.indexOf(get_parameters(), parameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParametersCount() {
        return this.parametersCount;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitParameterList(this);
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.asJava.elements.KtLightParameterList");
        return Intrinsics.areEqual(this.parent, ((KtLightParameterList) other).parent);
    }

    public int hashCode() {
        return this.parent.hashCode();
    }
}
