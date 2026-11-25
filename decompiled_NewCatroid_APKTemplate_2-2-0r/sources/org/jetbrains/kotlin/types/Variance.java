package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: Variance.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B'\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0000J\u0006\u0010\u0011\u001a\u00020\u0000J\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0000J\b\u0010\u0014\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/types/Variance;", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "allowsInPosition", "", "allowsOutPosition", "superpositionFactor", "", "(Ljava/lang/String;ILjava/lang/String;ZZI)V", "getAllowsInPosition", "()Z", "getAllowsOutPosition", "getLabel", "()Ljava/lang/String;", "allowsPosition", "position", "opposite", "superpose", "other", "toString", "INVARIANT", "IN_VARIANCE", "OUT_VARIANCE", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum Variance {
    INVARIANT("", true, true, 0),
    IN_VARIANCE("in", true, false, -1),
    OUT_VARIANCE("out", false, true, 1);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean allowsInPosition;
    private final boolean allowsOutPosition;
    private final String label;
    private final int superpositionFactor;

    /* JADX INFO: compiled from: Variance.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.INVARIANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static EnumEntries<Variance> getEntries() {
        return $ENTRIES;
    }

    Variance(String str, boolean z, boolean z2, int i) {
        this.label = str;
        this.allowsInPosition = z;
        this.allowsOutPosition = z2;
        this.superpositionFactor = i;
    }

    public final String getLabel() {
        return this.label;
    }

    public final boolean getAllowsInPosition() {
        return this.allowsInPosition;
    }

    public final boolean getAllowsOutPosition() {
        return this.allowsOutPosition;
    }

    public final boolean allowsPosition(Variance position) {
        Intrinsics.checkNotNullParameter(position, "position");
        int i = WhenMappings.$EnumSwitchMapping$0[position.ordinal()];
        if (i == 1) {
            return this.allowsInPosition;
        }
        if (i == 2) {
            return this.allowsOutPosition;
        }
        if (i == 3) {
            return this.allowsInPosition && this.allowsOutPosition;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final Variance superpose(Variance other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int i = this.superpositionFactor * other.superpositionFactor;
        if (i == -1) {
            return IN_VARIANCE;
        }
        if (i == 0) {
            return INVARIANT;
        }
        if (i == 1) {
            return OUT_VARIANCE;
        }
        throw new IllegalStateException("Illegal factor: " + i);
    }

    public final Variance opposite() {
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1) {
            return OUT_VARIANCE;
        }
        if (i == 2) {
            return IN_VARIANCE;
        }
        if (i == 3) {
            return INVARIANT;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.label;
    }
}
