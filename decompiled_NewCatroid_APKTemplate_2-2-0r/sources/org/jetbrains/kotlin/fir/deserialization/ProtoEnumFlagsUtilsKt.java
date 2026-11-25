package org.jetbrains.kotlin.fir.deserialization;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;

/* JADX INFO: compiled from: ProtoEnumFlagsUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"projection", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument$Projection;", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoEnumFlags;", "projectionKind", "Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "fir-deserialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProtoEnumFlagsUtilsKt {

    /* JADX INFO: compiled from: ProtoEnumFlagsUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProjectionKind.values().length];
            try {
                iArr[ProjectionKind.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProjectionKind.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProjectionKind.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final ProtoBuf.Type.Argument.Projection projection(ProtoEnumFlags protoEnumFlags, ProjectionKind projectionKind) {
        Intrinsics.checkNotNullParameter(protoEnumFlags, "<this>");
        Intrinsics.checkNotNullParameter(projectionKind, "projectionKind");
        int i = WhenMappings.$EnumSwitchMapping$0[projectionKind.ordinal()];
        if (i == 1) {
            return ProtoBuf.Type.Argument.Projection.INV;
        }
        if (i == 2) {
            return ProtoBuf.Type.Argument.Projection.IN;
        }
        if (i == 3) {
            return ProtoBuf.Type.Argument.Projection.OUT;
        }
        if (i != 4) {
            throw new NoWhenBranchMatchedException();
        }
        throw new AssertionError("Should not be here");
    }
}
