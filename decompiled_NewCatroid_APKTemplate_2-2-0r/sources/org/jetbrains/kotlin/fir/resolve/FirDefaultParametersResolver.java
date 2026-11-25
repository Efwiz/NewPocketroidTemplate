package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;

/* JADX INFO: compiled from: FirDefaultParametersResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirDefaultParametersResolver;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "declaresDefaultValue", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "originScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "index", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultParametersResolver implements FirSessionComponent {
    public final boolean declaresDefaultValue(FirSession session, ScopeSession scopeSession, FirFunction function, FirScope originScope, final int index) {
        FirRegularClass containingClass;
        FirTypeScope firTypeScopeScopeForClass;
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(function, "function");
        if (org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.itOrExpectHasDefaultParameterValue(function, index)) {
            return true;
        }
        if (!(function instanceof FirSimpleFunction)) {
            return false;
        }
        FirNamedFunctionSymbol symbol = ((FirSimpleFunction) function).getSymbol();
        if (originScope instanceof FirTypeScope) {
            firTypeScopeScopeForClass = (FirTypeScope) originScope;
        } else {
            if (!(originScope instanceof FirAbstractImportingScope) || (containingClass = ResolveUtilsKt.getContainingClass(function, session)) == null) {
                return false;
            }
            firTypeScopeScopeForClass = FirKotlinScopeProviderKt.scopeForClass(containingClass, ConeSubstitutor.Empty.INSTANCE, session, scopeSession, containingClass.getSymbol().getLookupTag(), null);
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FirTypeScopeKt.processOverriddenFunctions(firTypeScopeScopeForClass, symbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.FirDefaultParametersResolver.declaresDefaultValue.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirNamedFunctionSymbol overridden) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                if (org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.itOrExpectHasDefaultParameterValue((FirFunction) overridden.getFir(), index)) {
                    booleanRef.element = true;
                    return ProcessorAction.STOP;
                }
                return ProcessorAction.NEXT;
            }
        });
        return booleanRef.element;
    }
}
