package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FakeOverrideTypeCalculator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001:\u0003\u0007\b\tB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "", "()V", "computeReturnType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "AbstractFakeOverrideTypeCalculator", "DoNothing", "Forced", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FakeOverrideTypeCalculator {
    public abstract FirTypeRef computeReturnType(FirCallableDeclaration declaration);

    /* JADX INFO: compiled from: FakeOverrideTypeCalculator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator$DoNothing;", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "()V", "computeReturnType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DoNothing extends FakeOverrideTypeCalculator {
        public static final DoNothing INSTANCE = new DoNothing();

        private DoNothing() {
        }

        @Override // org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator
        public FirTypeRef computeReturnType(FirCallableDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return declaration.getReturnTypeRef();
        }
    }

    /* JADX INFO: compiled from: FakeOverrideTypeCalculator.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u000e\u0010\u0007\u001a\u0004\u0018\u00010\u0004*\u00020\u0006H$¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator$AbstractFakeOverrideTypeCalculator;", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "()V", "computeReturnType", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getResolvedTypeRef", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class AbstractFakeOverrideTypeCalculator extends FakeOverrideTypeCalculator {
        protected abstract FirResolvedTypeRef getResolvedTypeRef(FirCallableDeclaration firCallableDeclaration);

        @Override // org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator
        public FirResolvedTypeRef computeReturnType(FirCallableDeclaration declaration) {
            ConeKotlinType type;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            FakeOverrideSubstitution fakeOverrideSubstitution = FakeOverrideTypeCalculatorKt.getFakeOverrideSubstitution(declaration.getAttributes());
            if (fakeOverrideSubstitution == null) {
                return getResolvedTypeRef(declaration);
            }
            synchronized (fakeOverrideSubstitution) {
                if (FakeOverrideTypeCalculatorKt.getFakeOverrideSubstitution(declaration.getAttributes()) == null) {
                    FirTypeRef returnTypeRef = declaration.getReturnTypeRef();
                    Intrinsics.checkNotNull(returnTypeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
                    return (FirResolvedTypeRef) returnTypeRef;
                }
                ConeSubstitutor substitutor = fakeOverrideSubstitution.getSubstitutor();
                FirDeclaration fir = fakeOverrideSubstitution.component2().getFir();
                Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration");
                FirResolvedTypeRef firResolvedTypeRefComputeReturnType = computeReturnType((FirCallableDeclaration) fir);
                if (firResolvedTypeRefComputeReturnType != null && (type = firResolvedTypeRefComputeReturnType.getType()) != null) {
                    FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(declaration.getReturnTypeRef(), substitutor.substituteOrSelf(type), null, 2, null);
                    declaration.replaceReturnTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
                    FakeOverrideTypeCalculatorKt.setFakeOverrideSubstitution(declaration.getAttributes(), null);
                    return firResolvedTypeRefResolvedTypeFromPrototype$default;
                }
                return null;
            }
        }
    }

    /* JADX INFO: compiled from: FakeOverrideTypeCalculator.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u0005H\u0014¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator$Forced;", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator$AbstractFakeOverrideTypeCalculator;", "()V", "getResolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Forced extends AbstractFakeOverrideTypeCalculator {
        public static final Forced INSTANCE = new Forced();

        private Forced() {
        }

        @Override // org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator.AbstractFakeOverrideTypeCalculator
        protected FirResolvedTypeRef getResolvedTypeRef(FirCallableDeclaration firCallableDeclaration) {
            Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
            FirTypeRef returnTypeRef = firCallableDeclaration.getReturnTypeRef();
            if (returnTypeRef instanceof FirResolvedTypeRef) {
                return (FirResolvedTypeRef) returnTypeRef;
            }
            return null;
        }
    }
}
