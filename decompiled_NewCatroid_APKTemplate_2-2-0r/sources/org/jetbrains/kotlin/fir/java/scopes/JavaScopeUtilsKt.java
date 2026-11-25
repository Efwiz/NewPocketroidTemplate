package org.jetbrains.kotlin.fir.java.scopes;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.load.java.BuiltinSpecialProperties;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaScopeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001aq\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\f\b\u0000\u0010\b*\u0006\u0012\u0002\b\u00030\u0002*\u0002H\b2\u0006\u0010\u0003\u001a\u00020\u00042/\u0010\t\u001a+\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\b\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\u0002\b\r2\u0014\b\b\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0082\b¢\u0006\u0002\u0010\u000f\u001a*\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u000bH\u0002\u001a \u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a/\u0010\u0014\u001a\u0004\u0018\u0001H\b\"\f\b\u0000\u0010\b*\u0006\u0012\u0002\b\u00030\u0002*\u0002H\b2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0015\u001a\u0018\u0010\u0016\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0017"}, d2 = {"doesOverrideBuiltinWithDifferentJvmName", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "containingScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "firstOverriddenCallable", ExifInterface.GPS_DIRECTION_TRUE, "processFunction", "Lkotlin/Function3;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "Lkotlin/ExtensionFunctionType;", "predicate", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "firstOverriddenFunction", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getJvmMethodNameIfSpecial", "Lorg/jetbrains/kotlin/name/Name;", "getOverriddenBuiltinWithDifferentJvmName", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "isFromBuiltinClass", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaScopeUtilsKt {
    public static final boolean doesOverrideBuiltinWithDifferentJvmName(FirCallableSymbol<?> firCallableSymbol, FirTypeScope containingScope, FirSession session) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Intrinsics.checkNotNullParameter(containingScope, "containingScope");
        Intrinsics.checkNotNullParameter(session, "session");
        return getOverriddenBuiltinWithDifferentJvmName(firCallableSymbol, containingScope, session) != null;
    }

    public static final <T extends FirCallableSymbol<?>> T getOverriddenBuiltinWithDifferentJvmName(T t, FirTypeScope containingScope, final FirSession session) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(containingScope, "containingScope");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!SpecialGenericSignatures.INSTANCE.getORIGINAL_SHORT_NAMES().contains(t.getName()) && !BuiltinSpecialProperties.INSTANCE.getSPECIAL_SHORT_NAMES().contains(t.getName())) {
            return null;
        }
        if (t instanceof FirNamedFunctionSymbol) {
            return firstOverriddenFunction((FirNamedFunctionSymbol) t, containingScope, new Function1<FirNamedFunctionSymbol, Boolean>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaScopeUtilsKt.getOverriddenBuiltinWithDifferentJvmName.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(BuiltinMethodsWithDifferentJvmName.INSTANCE.isBuiltinFunctionWithDifferentNameInJvm(it, session));
                }
            });
        }
        if (t instanceof FirPropertySymbol) {
            return (T) ClassicBuiltinSpecialProperties.INSTANCE.findBuiltinSpecialPropertyFqName(t, containingScope);
        }
        return null;
    }

    public static final Name getJvmMethodNameIfSpecial(FirCallableSymbol<?> firCallableSymbol, FirTypeScope containingScope, FirSession session) {
        String strAsString;
        Name jvmName;
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Intrinsics.checkNotNullParameter(containingScope, "containingScope");
        Intrinsics.checkNotNullParameter(session, "session");
        FirCallableSymbol<?> overriddenBuiltinWithDifferentJvmName = getOverriddenBuiltinWithDifferentJvmName(firCallableSymbol, containingScope, session);
        if (overriddenBuiltinWithDifferentJvmName == null) {
            return null;
        }
        if (overriddenBuiltinWithDifferentJvmName instanceof FirPropertySymbol) {
            strAsString = ClassicBuiltinSpecialProperties.INSTANCE.getBuiltinSpecialPropertyGetterName(overriddenBuiltinWithDifferentJvmName, containingScope);
        } else {
            strAsString = (!(overriddenBuiltinWithDifferentJvmName instanceof FirNamedFunctionSymbol) || (jvmName = BuiltinMethodsWithDifferentJvmName.INSTANCE.getJvmName((FirNamedFunctionSymbol) overriddenBuiltinWithDifferentJvmName)) == null) ? null : jvmName.asString();
        }
        if (strAsString == null) {
            return null;
        }
        return Name.identifier(strAsString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isFromBuiltinClass(FirCallableSymbol<?> firCallableSymbol, FirSession firSession) {
        FirClassLikeSymbol<?> symbol;
        FirClassLikeDeclaration firClassLikeDeclaration;
        ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firCallableSymbol);
        return Intrinsics.areEqual((coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull, firSession)) == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir()) == null) ? null : firClassLikeDeclaration.getOrigin(), FirDeclarationOrigin.BuiltIns.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirNamedFunctionSymbol firstOverriddenFunction(FirNamedFunctionSymbol firNamedFunctionSymbol, FirTypeScope firTypeScope, final Function1<? super FirNamedFunctionSymbol, Boolean> function1) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        FirScopeKt.processOverriddenFunctionsAndSelf(firTypeScope, firNamedFunctionSymbol, new Function1<T, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaScopeUtilsKt$firstOverriddenCallable$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX WARN: Incorrect types in method signature: (TT;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirCallableSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                if (function1.invoke(symbol).booleanValue()) {
                    objectRef.element = symbol;
                    return ProcessorAction.STOP;
                }
                return ProcessorAction.NEXT;
            }
        });
        return (FirNamedFunctionSymbol) ((FirCallableSymbol) objectRef.element);
    }
}
