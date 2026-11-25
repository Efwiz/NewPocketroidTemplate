package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: statements.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0000¢\u0006\u0002\u0010\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/Operation;", "", "(Ljava/lang/String;I)V", "toString", "", "valueIfKnown", "", "given", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/Operation;)Ljava/lang/Boolean;", "EqTrue", "EqFalse", "EqNull", "NotEqNull", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum Operation {
    EqTrue,
    EqFalse,
    EqNull,
    NotEqNull;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: compiled from: statements.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Operation.values().length];
            try {
                iArr[Operation.EqTrue.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Operation.EqFalse.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Operation.EqNull.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Operation.NotEqNull.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static EnumEntries<Operation> getEntries() {
        return $ENTRIES;
    }

    public final Boolean valueIfKnown(Operation given) {
        Intrinsics.checkNotNullParameter(given, "given");
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1 || i == 2) {
            if (given == NotEqNull) {
                return null;
            }
            return Boolean.valueOf(given == this);
        }
        if (i == 3) {
            return Boolean.valueOf(given == EqNull);
        }
        if (i == 4) {
            return Boolean.valueOf(given != EqNull);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // java.lang.Enum
    public String toString() {
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1) {
            return "== True";
        }
        if (i == 2) {
            return "== False";
        }
        if (i == 3) {
            return "== Null";
        }
        if (i == 4) {
            return "!= Null";
        }
        throw new NoWhenBranchMatchedException();
    }
}
