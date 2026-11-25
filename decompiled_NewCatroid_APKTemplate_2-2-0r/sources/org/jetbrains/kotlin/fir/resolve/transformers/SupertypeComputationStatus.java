package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;

/* JADX INFO: compiled from: FirSupertypesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus;", "", "()V", "Computed", "Computing", "NotComputed", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus$Computed;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus$Computing;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus$NotComputed;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SupertypeComputationStatus {
    public /* synthetic */ SupertypeComputationStatus(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: FirSupertypesResolution.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus$NotComputed;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotComputed extends SupertypeComputationStatus {
        public static final NotComputed INSTANCE = new NotComputed();

        private NotComputed() {
            super(null);
        }
    }

    private SupertypeComputationStatus() {
    }

    /* JADX INFO: compiled from: FirSupertypesResolution.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus$Computing;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Computing extends SupertypeComputationStatus {
        public static final Computing INSTANCE = new Computing();

        private Computing() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: FirSupertypesResolution.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus$Computed;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus;", "supertypeRefs", "", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Ljava/util/List;)V", "getSupertypeRefs", "()Ljava/util/List;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Computed extends SupertypeComputationStatus {
        private final List<FirResolvedTypeRef> supertypeRefs;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public Computed(List<? extends FirResolvedTypeRef> supertypeRefs) {
            super(null);
            Intrinsics.checkNotNullParameter(supertypeRefs, "supertypeRefs");
            this.supertypeRefs = supertypeRefs;
        }

        public final List<FirResolvedTypeRef> getSupertypeRefs() {
            return this.supertypeRefs;
        }
    }
}
