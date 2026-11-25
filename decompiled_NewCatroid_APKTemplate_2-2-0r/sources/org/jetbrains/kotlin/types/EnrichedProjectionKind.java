package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EnrichedProjectionKind.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u0000 \u00072\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/types/EnrichedProjectionKind;", "", "(Ljava/lang/String;I)V", "IN", "OUT", "INV", "STAR", "Companion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum EnrichedProjectionKind {
    IN,
    OUT,
    INV,
    STAR;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final EnrichedProjectionKind fromVariance(Variance variance) {
        return INSTANCE.fromVariance(variance);
    }

    public static EnumEntries<EnrichedProjectionKind> getEntries() {
        return $ENTRIES;
    }

    /* JADX INFO: compiled from: EnrichedProjectionKind.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/types/EnrichedProjectionKind$Companion;", "", "()V", "fromVariance", "Lorg/jetbrains/kotlin/types/EnrichedProjectionKind;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getEffectiveProjectionKind", "typeParameterVariance", "typeArgumentVariance", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: EnrichedProjectionKind.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Variance.values().length];
                try {
                    iArr[Variance.INVARIANT.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Variance.IN_VARIANCE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final EnrichedProjectionKind fromVariance(Variance variance) {
            Intrinsics.checkNotNullParameter(variance, "variance");
            int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
            if (i == 1) {
                return EnrichedProjectionKind.INV;
            }
            if (i == 2) {
                return EnrichedProjectionKind.IN;
            }
            if (i == 3) {
                return EnrichedProjectionKind.OUT;
            }
            throw new NoWhenBranchMatchedException();
        }

        public final EnrichedProjectionKind getEffectiveProjectionKind(Variance typeParameterVariance, Variance typeArgumentVariance) {
            Intrinsics.checkNotNullParameter(typeParameterVariance, "typeParameterVariance");
            Intrinsics.checkNotNullParameter(typeArgumentVariance, "typeArgumentVariance");
            if (typeArgumentVariance == Variance.INVARIANT) {
                typeArgumentVariance = typeParameterVariance;
                typeParameterVariance = typeArgumentVariance;
            }
            if (typeParameterVariance == Variance.IN_VARIANCE && typeArgumentVariance == Variance.OUT_VARIANCE) {
                return EnrichedProjectionKind.STAR;
            }
            if (typeParameterVariance == Variance.OUT_VARIANCE && typeArgumentVariance == Variance.IN_VARIANCE) {
                return EnrichedProjectionKind.STAR;
            }
            return fromVariance(typeArgumentVariance);
        }
    }
}
