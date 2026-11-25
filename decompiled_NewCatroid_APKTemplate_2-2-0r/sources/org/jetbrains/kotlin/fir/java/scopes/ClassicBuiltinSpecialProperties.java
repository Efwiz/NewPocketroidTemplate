package org.jetbrains.kotlin.fir.java.scopes;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.load.java.BuiltinSpecialProperties;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JavaScopeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u0018\u0010\b\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u001e\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\u000b\u001a\u00020\f*\u0006\u0012\u0002\b\u00030\u0004H\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/ClassicBuiltinSpecialProperties;", "", "()V", "findBuiltinSpecialPropertyFqName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", NativeSymbol.TYPE_NAME, "containingScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "getBuiltinSpecialPropertyGetterName", "", "hasBuiltinSpecialPropertyFqNameImpl", "valueParametersAreEmpty", "", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassicBuiltinSpecialProperties {
    public static final ClassicBuiltinSpecialProperties INSTANCE = new ClassicBuiltinSpecialProperties();

    private ClassicBuiltinSpecialProperties() {
    }

    public final String getBuiltinSpecialPropertyGetterName(FirCallableSymbol<?> firCallableSymbol, FirTypeScope containingScope) {
        Name name2;
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Intrinsics.checkNotNullParameter(containingScope, "containingScope");
        FirCallableSymbol<?> firCallableSymbolFindBuiltinSpecialPropertyFqName = findBuiltinSpecialPropertyFqName(firCallableSymbol, containingScope);
        if (firCallableSymbolFindBuiltinSpecialPropertyFqName == null || (name2 = BuiltinSpecialProperties.INSTANCE.getPROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP().get(firCallableSymbolFindBuiltinSpecialPropertyFqName.getCallableId().asSingleFqName())) == null) {
            return null;
        }
        return name2.asString();
    }

    public final FirCallableSymbol<?> findBuiltinSpecialPropertyFqName(FirCallableSymbol<?> symbol, FirTypeScope containingScope) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(containingScope, "containingScope");
        if (BuiltinSpecialProperties.INSTANCE.getSPECIAL_SHORT_NAMES().contains(symbol.getName())) {
            return hasBuiltinSpecialPropertyFqNameImpl(symbol, containingScope);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirCallableSymbol<?> hasBuiltinSpecialPropertyFqNameImpl(FirCallableSymbol<?> firCallableSymbol, FirTypeScope firTypeScope) {
        if (BuiltinSpecialProperties.INSTANCE.getSPECIAL_FQ_NAMES().contains(firCallableSymbol.getCallableId().asSingleFqName()) && valueParametersAreEmpty(firCallableSymbol)) {
            return firCallableSymbol;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (firCallableSymbol instanceof FirNamedFunctionSymbol) {
            firTypeScope.processDirectOverriddenFunctionsWithBaseScope((FirNamedFunctionSymbol) firCallableSymbol, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.java.scopes.ClassicBuiltinSpecialProperties.hasBuiltinSpecialPropertyFqNameImpl.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final ProcessorAction invoke(FirNamedFunctionSymbol overridden, FirTypeScope scope) {
                    Intrinsics.checkNotNullParameter(overridden, "overridden");
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    return ClassicBuiltinSpecialProperties.hasBuiltinSpecialPropertyFqNameImpl$process(objectRef, overridden, scope);
                }
            });
        } else if (firCallableSymbol instanceof FirPropertySymbol) {
            firTypeScope.processDirectOverriddenPropertiesWithBaseScope((FirPropertySymbol) firCallableSymbol, new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.java.scopes.ClassicBuiltinSpecialProperties.hasBuiltinSpecialPropertyFqNameImpl.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final ProcessorAction invoke(FirPropertySymbol overridden, FirTypeScope scope) {
                    Intrinsics.checkNotNullParameter(overridden, "overridden");
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    return ClassicBuiltinSpecialProperties.hasBuiltinSpecialPropertyFqNameImpl$process(objectRef, overridden, scope);
                }
            });
        }
        return (FirCallableSymbol) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol] */
    public static final ProcessorAction hasBuiltinSpecialPropertyFqNameImpl$process(Ref.ObjectRef<FirCallableSymbol<?>> objectRef, FirCallableSymbol<?> firCallableSymbol, FirTypeScope firTypeScope) {
        ?? FindBuiltinSpecialPropertyFqName = INSTANCE.findBuiltinSpecialPropertyFqName(firCallableSymbol, firTypeScope);
        if (FindBuiltinSpecialPropertyFqName != 0) {
            objectRef.element = FindBuiltinSpecialPropertyFqName;
            return ProcessorAction.STOP;
        }
        return ProcessorAction.NEXT;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean valueParametersAreEmpty(FirCallableSymbol<?> firCallableSymbol) {
        if (firCallableSymbol instanceof FirNamedFunctionSymbol) {
            return ((FirSimpleFunction) ((FirNamedFunctionSymbol) firCallableSymbol).getFir()).getValueParameters().isEmpty();
        }
        return true;
    }
}
