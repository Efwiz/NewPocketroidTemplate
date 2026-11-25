package org.jetbrains.kotlin.fir.lightTree.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ClassWrapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\bHÆ\u0003J/\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bHÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/DelegatedConstructorWrapper;", "", "delegatedSuperTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Lorg/jetbrains/kotlin/KtLightSourceElement;)V", "getArguments", "()Ljava/util/List;", "getDelegatedSuperTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSource", "()Lorg/jetbrains/kotlin/KtLightSourceElement;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DelegatedConstructorWrapper {
    private final List<FirExpression> arguments;
    private final FirTypeRef delegatedSuperTypeRef;
    private final KtLightSourceElement source;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DelegatedConstructorWrapper copy$default(DelegatedConstructorWrapper delegatedConstructorWrapper, FirTypeRef firTypeRef, List list, KtLightSourceElement ktLightSourceElement, int i, Object obj) {
        if ((i & 1) != 0) {
            firTypeRef = delegatedConstructorWrapper.delegatedSuperTypeRef;
        }
        if ((i & 2) != 0) {
            list = delegatedConstructorWrapper.arguments;
        }
        if ((i & 4) != 0) {
            ktLightSourceElement = delegatedConstructorWrapper.source;
        }
        return delegatedConstructorWrapper.copy(firTypeRef, list, ktLightSourceElement);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirTypeRef getDelegatedSuperTypeRef() {
        return this.delegatedSuperTypeRef;
    }

    public final List<FirExpression> component2() {
        return this.arguments;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final KtLightSourceElement getSource() {
        return this.source;
    }

    public final DelegatedConstructorWrapper copy(FirTypeRef delegatedSuperTypeRef, List<? extends FirExpression> arguments, KtLightSourceElement source) {
        Intrinsics.checkNotNullParameter(delegatedSuperTypeRef, "delegatedSuperTypeRef");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new DelegatedConstructorWrapper(delegatedSuperTypeRef, arguments, source);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DelegatedConstructorWrapper)) {
            return false;
        }
        DelegatedConstructorWrapper delegatedConstructorWrapper = (DelegatedConstructorWrapper) other;
        return Intrinsics.areEqual(this.delegatedSuperTypeRef, delegatedConstructorWrapper.delegatedSuperTypeRef) && Intrinsics.areEqual(this.arguments, delegatedConstructorWrapper.arguments) && Intrinsics.areEqual(this.source, delegatedConstructorWrapper.source);
    }

    public int hashCode() {
        int iHashCode = ((this.delegatedSuperTypeRef.hashCode() * 31) + this.arguments.hashCode()) * 31;
        KtLightSourceElement ktLightSourceElement = this.source;
        return iHashCode + (ktLightSourceElement == null ? 0 : ktLightSourceElement.hashCode());
    }

    public String toString() {
        return "DelegatedConstructorWrapper(delegatedSuperTypeRef=" + this.delegatedSuperTypeRef + ", arguments=" + this.arguments + ", source=" + this.source + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DelegatedConstructorWrapper(FirTypeRef delegatedSuperTypeRef, List<? extends FirExpression> arguments, KtLightSourceElement ktLightSourceElement) {
        Intrinsics.checkNotNullParameter(delegatedSuperTypeRef, "delegatedSuperTypeRef");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.delegatedSuperTypeRef = delegatedSuperTypeRef;
        this.arguments = arguments;
        this.source = ktLightSourceElement;
    }

    public final FirTypeRef getDelegatedSuperTypeRef() {
        return this.delegatedSuperTypeRef;
    }

    public final List<FirExpression> getArguments() {
        return this.arguments;
    }

    public final KtLightSourceElement getSource() {
        return this.source;
    }
}
