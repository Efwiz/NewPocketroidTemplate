package org.jetbrains.kotlin.codegen.pseudoInsns;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;

/* JADX INFO: compiled from: PseudoInsns.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/pseudoInsns/PseudoInsn;", "", "signature", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSignature", "()Ljava/lang/String;", "createInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "emit", "", "iv", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "isa", "", "node", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "FIX_STACK_BEFORE_JUMP", "FAKE_ALWAYS_TRUE_IFEQ", "FAKE_ALWAYS_FALSE_IFEQ", "SAVE_STACK_BEFORE_TRY", "RESTORE_STACK_IN_TRY_CATCH", "STORE_NOT_NULL", "AS_NOT_NULL", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum PseudoInsn {
    FIX_STACK_BEFORE_JUMP(null, 1, null),
    FAKE_ALWAYS_TRUE_IFEQ("()I"),
    FAKE_ALWAYS_FALSE_IFEQ("()I"),
    SAVE_STACK_BEFORE_TRY(null, 1, null),
    RESTORE_STACK_IN_TRY_CATCH(null, 1, null),
    STORE_NOT_NULL(null, 1, null),
    AS_NOT_NULL("(Ljava/lang/Object;)Ljava/lang/Object;");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String signature;

    public static EnumEntries<PseudoInsn> getEntries() {
        return $ENTRIES;
    }

    PseudoInsn(String str) {
        this.signature = str;
    }

    /* synthetic */ PseudoInsn(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "()V" : str);
    }

    public final String getSignature() {
        return this.signature;
    }

    public final void emit(InstructionAdapter iv) {
        Intrinsics.checkNotNullParameter(iv, "iv");
        iv.invokestatic(PseudoInsnsKt.getPSEUDO_INSN_CALL_OWNER(), toString(), this.signature, false);
    }

    public final MethodInsnNode createInsnNode() {
        return new MethodInsnNode(184, PseudoInsnsKt.getPSEUDO_INSN_CALL_OWNER(), toString(), this.signature, false);
    }

    public final boolean isa(AbstractInsnNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return this == PseudoInsnsKt.parsePseudoInsnOrNull(node);
    }
}
