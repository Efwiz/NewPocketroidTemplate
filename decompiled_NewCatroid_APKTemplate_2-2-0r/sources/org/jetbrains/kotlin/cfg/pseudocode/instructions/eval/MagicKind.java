package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'NOT_NULL_ASSERTION' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: operationInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0015\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;", "", "sideEffectFree", "", "(Ljava/lang/String;IZ)V", "getSideEffectFree", "()Z", "STRING_TEMPLATE", "AND", "OR", "NOT_NULL_ASSERTION", "EQUALS_IN_WHEN_CONDITION", "IS", "CAST", "UNBOUND_CALLABLE_REFERENCE", "BOUND_CALLABLE_REFERENCE", "LOOP_RANGE_ITERATION", "IMPLICIT_RECEIVER", "VALUE_CONSUMER", "UNRESOLVED_CALL", "UNSUPPORTED_ELEMENT", "UNRECOGNIZED_WRITE_RHS", "FAKE_INITIALIZER", "EXHAUSTIVE_WHEN_ELSE", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MagicKind {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ MagicKind[] $VALUES;
    public static final MagicKind CAST;
    public static final MagicKind EQUALS_IN_WHEN_CONDITION;
    public static final MagicKind EXHAUSTIVE_WHEN_ELSE;
    public static final MagicKind FAKE_INITIALIZER;
    public static final MagicKind IMPLICIT_RECEIVER;
    public static final MagicKind IS;
    public static final MagicKind LOOP_RANGE_ITERATION;
    public static final MagicKind NOT_NULL_ASSERTION;
    public static final MagicKind UNRECOGNIZED_WRITE_RHS;
    public static final MagicKind UNRESOLVED_CALL;
    public static final MagicKind UNSUPPORTED_ELEMENT;
    public static final MagicKind VALUE_CONSUMER;
    private final boolean sideEffectFree;
    public static final MagicKind STRING_TEMPLATE = new MagicKind("STRING_TEMPLATE", 0, true);
    public static final MagicKind AND = new MagicKind("AND", 1, true);
    public static final MagicKind OR = new MagicKind("OR", 2, true);
    public static final MagicKind UNBOUND_CALLABLE_REFERENCE = new MagicKind("UNBOUND_CALLABLE_REFERENCE", 7, true);
    public static final MagicKind BOUND_CALLABLE_REFERENCE = new MagicKind("BOUND_CALLABLE_REFERENCE", 8, true);

    private static final /* synthetic */ MagicKind[] $values() {
        return new MagicKind[]{STRING_TEMPLATE, AND, OR, NOT_NULL_ASSERTION, EQUALS_IN_WHEN_CONDITION, IS, CAST, UNBOUND_CALLABLE_REFERENCE, BOUND_CALLABLE_REFERENCE, LOOP_RANGE_ITERATION, IMPLICIT_RECEIVER, VALUE_CONSUMER, UNRESOLVED_CALL, UNSUPPORTED_ELEMENT, UNRECOGNIZED_WRITE_RHS, FAKE_INITIALIZER, EXHAUSTIVE_WHEN_ELSE};
    }

    public static EnumEntries<MagicKind> getEntries() {
        return $ENTRIES;
    }

    public static MagicKind valueOf(String str) {
        return (MagicKind) Enum.valueOf(MagicKind.class, str);
    }

    public static MagicKind[] values() {
        return (MagicKind[]) $VALUES.clone();
    }

    private MagicKind(String str, int i, boolean z) {
        super(str, i);
        this.sideEffectFree = z;
    }

    /* synthetic */ MagicKind(String str, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 1) != 0 ? false : z);
    }

    public final boolean getSideEffectFree() {
        return this.sideEffectFree;
    }

    static {
        int i = 1;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        NOT_NULL_ASSERTION = new MagicKind("NOT_NULL_ASSERTION", 3, z, i, defaultConstructorMarker);
        int i2 = 1;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z2 = false;
        EQUALS_IN_WHEN_CONDITION = new MagicKind("EQUALS_IN_WHEN_CONDITION", 4, z2, i2, defaultConstructorMarker2);
        IS = new MagicKind("IS", 5, z, i, defaultConstructorMarker);
        CAST = new MagicKind("CAST", 6, z2, i2, defaultConstructorMarker2);
        LOOP_RANGE_ITERATION = new MagicKind("LOOP_RANGE_ITERATION", 9, z, i, defaultConstructorMarker);
        IMPLICIT_RECEIVER = new MagicKind("IMPLICIT_RECEIVER", 10, z2, i2, defaultConstructorMarker2);
        int i3 = 1;
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        boolean z3 = false;
        VALUE_CONSUMER = new MagicKind("VALUE_CONSUMER", 11, z3, i3, defaultConstructorMarker3);
        int i4 = 1;
        DefaultConstructorMarker defaultConstructorMarker4 = null;
        boolean z4 = false;
        UNRESOLVED_CALL = new MagicKind("UNRESOLVED_CALL", 12, z4, i4, defaultConstructorMarker4);
        UNSUPPORTED_ELEMENT = new MagicKind("UNSUPPORTED_ELEMENT", 13, z3, i3, defaultConstructorMarker3);
        UNRECOGNIZED_WRITE_RHS = new MagicKind("UNRECOGNIZED_WRITE_RHS", 14, z4, i4, defaultConstructorMarker4);
        FAKE_INITIALIZER = new MagicKind("FAKE_INITIALIZER", 15, z3, i3, defaultConstructorMarker3);
        EXHAUSTIVE_WHEN_ELSE = new MagicKind("EXHAUSTIVE_WHEN_ELSE", 16, z4, i4, defaultConstructorMarker4);
        MagicKind[] magicKindArr$values = $values();
        $VALUES = magicKindArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(magicKindArr$values);
    }
}
