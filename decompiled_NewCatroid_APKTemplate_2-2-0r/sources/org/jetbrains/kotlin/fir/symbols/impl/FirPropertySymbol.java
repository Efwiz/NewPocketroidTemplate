package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.mpp.PropertySymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirVariableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u001e\u001a\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b#\u0010!R\u0011\u0010$\u001a\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b$\u0010!R\u0011\u0010%\u001a\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b%\u0010!R\u0011\u0010&\u001a\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b&\u0010!R\u0013\u0010'\u001a\u0004\u0018\u00010\u00138F¢\u0006\u0006\u001a\u0004\b(\u0010\u0015R\u0013\u0010)\u001a\u0004\u0018\u00010\u001b8F¢\u0006\u0006\u001a\u0004\b*\u0010\u001d¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "backingFieldSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirBackingFieldSymbol;", "getBackingFieldSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirBackingFieldSymbol;", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "delegateFieldSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "getDelegateFieldSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "getterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "getGetterSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "hasDelegate", "", "getHasDelegate", "()Z", "hasInitializer", "getHasInitializer", "isLocal", "isVal", "isVar", "resolvedInitializer", "getResolvedInitializer", "setterSymbol", "getSetterSymbol", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirPropertySymbol extends FirVariableSymbol<FirProperty> implements PropertySymbolMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirPropertySymbol(CallableId callableId) {
        super(callableId, null);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirPropertySymbol(Name name2) {
        this(new CallableId(name2, (FqName) null, 2, (DefaultConstructorMarker) null));
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isLocal() {
        return ((FirProperty) getFir()).isLocal();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirPropertyAccessorSymbol getGetterSymbol() {
        FirPropertyAccessor getter = ((FirProperty) getFir()).getGetter();
        if (getter != null) {
            return getter.getSymbol();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirPropertyAccessorSymbol getSetterSymbol() {
        FirPropertyAccessor setter = ((FirProperty) getFir()).getSetter();
        if (setter != null) {
            return setter.getSymbol();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirBackingFieldSymbol getBackingFieldSymbol() {
        FirBackingField backingField = ((FirProperty) getFir()).getBackingField();
        if (backingField != null) {
            return backingField.getSymbol();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirDelegateFieldSymbol getDelegateFieldSymbol() {
        return ((FirProperty) getFir()).getDelegateFieldSymbol();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirExpression getDelegate() {
        return ((FirProperty) getFir()).getDelegate();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getHasDelegate() {
        return ((FirProperty) getFir()).getDelegate() != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getHasInitializer() {
        return ((FirProperty) getFir()).getInitializer() != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirExpression getResolvedInitializer() {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.BODY_RESOLVE);
        return ((FirProperty) getFir()).getInitializer();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirControlFlowGraphReference getControlFlowGraphReference() {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.BODY_RESOLVE);
        return ((FirProperty) getFir()).getControlFlowGraphReference();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isVal() {
        return ((FirProperty) getFir()).isVal();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isVar() {
        return ((FirProperty) getFir()).isVar();
    }
}
