package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;

/* JADX INFO: compiled from: ClassMembers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/DelegatedWrapperData;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "", "wrapped", "containingClass", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "delegateField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;Lorg/jetbrains/kotlin/fir/declarations/FirField;)V", "getContainingClass", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "getDelegateField", "()Lorg/jetbrains/kotlin/fir/declarations/FirField;", "getWrapped", "()Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatedWrapperData<D extends FirCallableDeclaration> {
    private final ConeClassLikeLookupTag containingClass;
    private final FirField delegateField;
    private final D wrapped;

    public DelegatedWrapperData(D wrapped, ConeClassLikeLookupTag containingClass, FirField delegateField) {
        Intrinsics.checkNotNullParameter(wrapped, "wrapped");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(delegateField, "delegateField");
        this.wrapped = wrapped;
        this.containingClass = containingClass;
        this.delegateField = delegateField;
    }

    public final D getWrapped() {
        return this.wrapped;
    }

    public final ConeClassLikeLookupTag getContainingClass() {
        return this.containingClass;
    }

    public final FirField getDelegateField() {
        return this.delegateField;
    }

    public String toString() {
        return "[wrapped=" + this.wrapped.getSymbol() + ", containingClass=" + this.containingClass + ", delegateField=" + this.delegateField.getSymbol();
    }
}
