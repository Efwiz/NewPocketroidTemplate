package org.jetbrains.kotlin.resolve.calls.inference.model;

import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstraintStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "", "a", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "constraintKind", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;)V", "getA", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getB", "getConstraintKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "asStringWithoutPosition", "", "toString", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InitialConstraint {
    private final KotlinTypeMarker a;
    private final KotlinTypeMarker b;
    private final ConstraintKind constraintKind;
    private final ConstraintPosition position;

    /* JADX INFO: compiled from: ConstraintStorage.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public InitialConstraint(KotlinTypeMarker a, KotlinTypeMarker b, ConstraintKind constraintKind, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(constraintKind, "constraintKind");
        Intrinsics.checkNotNullParameter(position, "position");
        this.a = a;
        this.b = b;
        this.constraintKind = constraintKind;
        this.position = position;
    }

    public final KotlinTypeMarker getA() {
        return this.a;
    }

    public final KotlinTypeMarker getB() {
        return this.b;
    }

    public final ConstraintKind getConstraintKind() {
        return this.constraintKind;
    }

    public final ConstraintPosition getPosition() {
        return this.position;
    }

    public String toString() {
        return asStringWithoutPosition() + " from " + this.position;
    }

    public final String asStringWithoutPosition() {
        String str;
        int i = WhenMappings.$EnumSwitchMapping$0[this.constraintKind.ordinal()];
        if (i == 1) {
            str = "==";
        } else if (i == 2) {
            str = ":>";
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            str = "<:";
        }
        return this.a + StringUtil.SPACE + str + StringUtil.SPACE + this.b;
    }
}
