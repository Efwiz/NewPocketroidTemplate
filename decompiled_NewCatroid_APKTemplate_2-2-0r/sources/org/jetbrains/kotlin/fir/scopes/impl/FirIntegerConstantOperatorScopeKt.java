package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorType;

/* JADX INFO: compiled from: FirIntegerConstantOperatorScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0013\u001a\u00020\u0003*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u001b\u0010\u0019\u001a\u00020\u0002*\u00020\u001a\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a\u001f\u0010\u0019\u001a\u00020\u0002*\u0006\u0012\u0002\b\u00030\u001b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0001\u001a\u000e\u0010\u001c\u001a\u00020\u0002*\u0006\u0012\u0002\b\u00030\u001b\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"3\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u00020\u00062\b\u0010\u0004\u001a\u0004\u0018\u00010\u00028B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\t\"3\u0010\r\u001a\u0004\u0018\u00010\f*\u00020\u00062\b\u0010\u0004\u001a\u0004\u0018\u00010\f8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011ò\u0001\b\n\u00020\u0006\n\u00020\f¨\u0006\u001d"}, d2 = {"INTEGER_CONSTANT_OPERATOR_SCOPE", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirIntegerConstantOperatorScope;", "<set-?>", "isUnsignedWrappedIntegerOperator", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Ljava/lang/Boolean;", "setUnsignedWrappedIntegerOperator", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Ljava/lang/Boolean;)V", "isUnsignedWrappedIntegerOperator$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "originalForWrappedIntegerOperator", "getOriginalForWrappedIntegerOperator", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "setOriginalForWrappedIntegerOperator", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)V", "originalForWrappedIntegerOperator$delegate", "getOrBuildScopeForIntegerConstantOperatorType", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeIntegerConstantOperatorType;", "isWrappedIntegerOperator", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isWrappedIntegerOperatorForUnsignedType", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirIntegerConstantOperatorScopeKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirIntegerConstantOperatorScopeKt.class, "providers"), "originalForWrappedIntegerOperator", "getOriginalForWrappedIntegerOperator(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirIntegerConstantOperatorScopeKt.class, "providers"), "isUnsignedWrappedIntegerOperator", "isUnsignedWrappedIntegerOperator(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Ljava/lang/Boolean;"))};
    private static final ScopeSessionKey<Boolean, FirIntegerConstantOperatorScope> INTEGER_CONSTANT_OPERATOR_SCOPE = new ScopeSessionKey<Boolean, FirIntegerConstantOperatorScope>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt$special$$inlined$scopeSessionKey$1
    };
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor originalForWrappedIntegerOperator$delegate = FirDeclarationDataRegistry.INSTANCE.data(OriginalForWrappedIntegerOperator.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isUnsignedWrappedIntegerOperator$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsUnsignedForWrappedIntegerOperator.INSTANCE);

    public static final FirIntegerConstantOperatorScope getOrBuildScopeForIntegerConstantOperatorType(ScopeSession scopeSession, FirSession session, ConeIntegerConstantOperatorType type) {
        Intrinsics.checkNotNullParameter(scopeSession, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(type, "type");
        Boolean boolValueOf = Boolean.valueOf(type.getIsUnsigned());
        ScopeSessionKey<Boolean, FirIntegerConstantOperatorScope> scopeSessionKey = INTEGER_CONSTANT_OPERATOR_SCOPE;
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(boolValueOf);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(boolValueOf, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        Object firIntegerConstantOperatorScope = map2.get(scopeSessionKey);
        if (firIntegerConstantOperatorScope == null) {
            firIntegerConstantOperatorScope = new FirIntegerConstantOperatorScope(session, scopeSession, type.getIsUnsigned());
            map2.put(scopeSessionKey, firIntegerConstantOperatorScope);
        }
        return (FirIntegerConstantOperatorScope) firIntegerConstantOperatorScope;
    }

    public static final FirNamedFunctionSymbol getOriginalForWrappedIntegerOperator(FirSimpleFunction firSimpleFunction) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        return (FirNamedFunctionSymbol) originalForWrappedIntegerOperator$delegate.getValue(firSimpleFunction, $$delegatedProperties[0]);
    }

    public static final void setOriginalForWrappedIntegerOperator(FirSimpleFunction firSimpleFunction, FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        originalForWrappedIntegerOperator$delegate.setValue(firSimpleFunction, $$delegatedProperties[0], firNamedFunctionSymbol);
    }

    private static final Boolean isUnsignedWrappedIntegerOperator(FirSimpleFunction firSimpleFunction) {
        return (Boolean) isUnsignedWrappedIntegerOperator$delegate.getValue(firSimpleFunction, $$delegatedProperties[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setUnsignedWrappedIntegerOperator(FirSimpleFunction firSimpleFunction, Boolean bool) {
        isUnsignedWrappedIntegerOperator$delegate.setValue(firSimpleFunction, $$delegatedProperties[1], bool);
    }

    public static final boolean isWrappedIntegerOperator(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        FirSimpleFunction firSimpleFunction = firDeclaration instanceof FirSimpleFunction ? (FirSimpleFunction) firDeclaration : null;
        return (firSimpleFunction != null ? getOriginalForWrappedIntegerOperator(firSimpleFunction) : null) != null;
    }

    public static final boolean isWrappedIntegerOperator(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        return isWrappedIntegerOperator(firBasedSymbol.getFir());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isWrappedIntegerOperatorForUnsignedType(FirBasedSymbol<?> firBasedSymbol) {
        FirSimpleFunction firSimpleFunction;
        Boolean boolIsUnsignedWrappedIntegerOperator;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        FirNamedFunctionSymbol firNamedFunctionSymbol = firBasedSymbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) firBasedSymbol : null;
        if (firNamedFunctionSymbol == null || (firSimpleFunction = (FirSimpleFunction) firNamedFunctionSymbol.getFir()) == null || (boolIsUnsignedWrappedIntegerOperator = isUnsignedWrappedIntegerOperator(firSimpleFunction)) == null) {
            return false;
        }
        return boolIsUnsignedWrappedIntegerOperator.booleanValue();
    }
}
