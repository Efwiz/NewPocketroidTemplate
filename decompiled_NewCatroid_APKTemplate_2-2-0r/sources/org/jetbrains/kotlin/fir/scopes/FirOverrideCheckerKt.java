package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0007\u001a\u00020\b*\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\f"}, d2 = {"firOverrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getFirOverrideChecker", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "firOverrideChecker$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "isOverriddenFunction", "", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "baseDeclaration", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirOverrideCheckerKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirOverrideCheckerKt.class, "providers"), "firOverrideChecker", "getFirOverrideChecker(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;"))};
    private static final ArrayMapAccessor firOverrideChecker$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirOverrideChecker.class), (Object) null, 2, (Object) null);

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isOverriddenFunction(FirOverrideChecker firOverrideChecker, FirNamedFunctionSymbol overrideCandidate, FirNamedFunctionSymbol baseDeclaration) {
        Intrinsics.checkNotNullParameter(firOverrideChecker, "<this>");
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        return firOverrideChecker.isOverriddenFunction((FirSimpleFunction) overrideCandidate.getFir(), (FirSimpleFunction) baseDeclaration.getFir());
    }

    public static final FirOverrideChecker getFirOverrideChecker(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirOverrideChecker) firOverrideChecker$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
