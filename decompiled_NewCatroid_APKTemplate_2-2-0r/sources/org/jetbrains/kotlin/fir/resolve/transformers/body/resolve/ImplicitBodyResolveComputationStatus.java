package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;

/* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus;", "", "()V", "Computed", "Computing", "NotComputed", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus$Computed;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus$Computing;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus$NotComputed;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ImplicitBodyResolveComputationStatus {
    public /* synthetic */ ImplicitBodyResolveComputationStatus(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus$NotComputed;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotComputed extends ImplicitBodyResolveComputationStatus {
        public static final NotComputed INSTANCE = new NotComputed();

        private NotComputed() {
            super(null);
        }
    }

    private ImplicitBodyResolveComputationStatus() {
    }

    /* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus$Computing;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Computing extends ImplicitBodyResolveComputationStatus {
        public static final Computing INSTANCE = new Computing();

        private Computing() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus$Computed;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus;", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "transformedDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)V", "getResolvedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "getTransformedDeclaration", "()Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Computed extends ImplicitBodyResolveComputationStatus {
        private final FirResolvedTypeRef resolvedTypeRef;
        private final FirCallableDeclaration transformedDeclaration;

        public final FirResolvedTypeRef getResolvedTypeRef() {
            return this.resolvedTypeRef;
        }

        public final FirCallableDeclaration getTransformedDeclaration() {
            return this.transformedDeclaration;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Computed(FirResolvedTypeRef resolvedTypeRef, FirCallableDeclaration transformedDeclaration) {
            super(null);
            Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
            Intrinsics.checkNotNullParameter(transformedDeclaration, "transformedDeclaration");
            this.resolvedTypeRef = resolvedTypeRef;
            this.transformedDeclaration = transformedDeclaration;
        }
    }
}
