package org.jetbrains.kotlin.fir.java.scopes;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaScopeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u0004\u0018\u00010\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eJ\u001a\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0015\u0010\b\u001a\u00020\u0004*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/BuiltinMethodsWithSpecialGenericSignature;", "", "()V", "hasErasedValueParametersInJava", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getHasErasedValueParametersInJava", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)Z", "sameAsBuiltinMethodWithErasedValueParameters", "Lorg/jetbrains/kotlin/name/Name;", "getSameAsBuiltinMethodWithErasedValueParameters", "(Lorg/jetbrains/kotlin/name/Name;)Z", "getOverriddenBuiltinFunctionWithErasedValueParametersInJava", "memberWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "functionSymbol", "containingScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltinMethodsWithSpecialGenericSignature {
    public static final BuiltinMethodsWithSpecialGenericSignature INSTANCE = new BuiltinMethodsWithSpecialGenericSignature();

    private BuiltinMethodsWithSpecialGenericSignature() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getHasErasedValueParametersInJava(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        return CollectionsKt.contains(SpecialGenericSignatures.INSTANCE.getERASED_VALUE_PARAMETERS_SIGNATURES(), SignatureUtilsKt.computeJvmSignature$default((FirFunction) firNamedFunctionSymbol.getFir(), null, 1, null));
    }

    public final FirNamedFunctionSymbol getOverriddenBuiltinFunctionWithErasedValueParametersInJava(MemberWithBaseScope<? extends FirNamedFunctionSymbol> memberWithBaseScope) {
        Intrinsics.checkNotNullParameter(memberWithBaseScope, "memberWithBaseScope");
        return getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FirNamedFunctionSymbol) memberWithBaseScope.getMember(), memberWithBaseScope.getBaseScope());
    }

    @JvmStatic
    public static final FirNamedFunctionSymbol getOverriddenBuiltinFunctionWithErasedValueParametersInJava(FirNamedFunctionSymbol functionSymbol, FirTypeScope containingScope) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(containingScope, "containingScope");
        if (INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(functionSymbol.getName())) {
            return JavaScopeUtilsKt.firstOverriddenFunction(functionSymbol, containingScope, new Function1<FirNamedFunctionSymbol, Boolean>() { // from class: org.jetbrains.kotlin.fir.java.scopes.BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getHasErasedValueParametersInJava(it));
                }
            });
        }
        return null;
    }

    public final boolean getSameAsBuiltinMethodWithErasedValueParameters(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<this>");
        return SpecialGenericSignatures.INSTANCE.getERASED_VALUE_PARAMETERS_SHORT_NAMES().contains(name2);
    }
}
