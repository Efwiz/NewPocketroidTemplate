package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.nullCheck.RedundantNullCheckMethodTransformerKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: inlineArgumentsInPlace.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\n\u0010\u000e\u001a\u00020\u000f*\u00020\u0010\u001a\n\u0010\u0011\u001a\u00020\u000f*\u00020\u0010\u001a\n\u0010\u0012\u001a\u00020\u000f*\u00020\u0010\u001a\n\u0010\u0013\u001a\u00020\u000f*\u00020\u0010\u001a\u0014\u0010\u0014\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0001H\u0002\u001a\f\u0010\u0016\u001a\u00020\u000b*\u00020\u0017H\u0000\u001a\f\u0010\u0018\u001a\u00020\u000b*\u00020\u0017H\u0000\u001a\f\u0010\u0019\u001a\u00020\u000b*\u00020\u0017H\u0000\u001a\f\u0010\u001a\u001a\u00020\u000b*\u00020\u0017H\u0000\u001a\f\u0010\u001b\u001a\u00020\u000b*\u00020\u0017H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"MARKER_INPLACE_ARGUMENT_END", "", "MARKER_INPLACE_ARGUMENT_START", "MARKER_INPLACE_CALL_END", "MARKER_INPLACE_CALL_START", "opcodeProhibitedDuringArgumentsEvaluation", "", "whitelistedStaticFields", "", "Lorg/jetbrains/kotlin/codegen/inline/FieldSignature;", "canInlineArgumentsInPlace", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "addInplaceArgumentEndMarker", "", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "addInplaceArgumentStartMarker", "addInplaceCallEndMarker", "addInplaceCallStartMarker", "addMarker", "name", "isInplaceArgumentEndMarker", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isInplaceArgumentStartMarker", "isInplaceCallEndMarker", "isInplaceCallStartMarker", "isProhibitedDuringArgumentsEvaluation", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineArgumentsInPlaceKt {
    private static final boolean[] opcodeProhibitedDuringArgumentsEvaluation;
    private static final Set<FieldSignature> whitelistedStaticFields = SetsKt.hashSetOf(new FieldSignature("kotlin/Result", "Companion", "Lkotlin/Result$Companion;"), new FieldSignature("kotlin/_Assertions", "ENABLED", "Z"));

    public static final boolean canInlineArgumentsInPlace(MethodNode methodNode) {
        int opcode;
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
        Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
        HashSet hashSet = new HashSet();
        Iterator<T> it = tryCatchBlocks.iterator();
        while (it.hasNext()) {
            hashSet.add(((TryCatchBlockNode) it.next()).start);
        }
        HashSet hashSet2 = hashSet;
        Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
        ArrayList arrayList = new ArrayList(argumentTypes.length + 1);
        if ((methodNode.access & 8) == 0) {
            arrayList.add(AsmTypes.OBJECT_TYPE);
        }
        Intrinsics.checkNotNull(argumentTypes);
        CollectionsKt.addAll(arrayList, argumentTypes);
        Iterator it2 = arrayList.iterator();
        int size = 0;
        while (it2.hasNext()) {
            size += ((Type) it2.next()).getSize();
        }
        AbstractInsnNode first = methodNode.instructions.getFirst();
        int size2 = 0;
        int i = 0;
        while (first != null && size2 < size) {
            if (CollectionsKt.contains(hashSet2, first) || isProhibitedDuringArgumentsEvaluation(first)) {
                return false;
            }
            if (first.getOpcode() == 178) {
                FieldInsnNode fieldInsnNode = (FieldInsnNode) first;
                String owner = fieldInsnNode.owner;
                Intrinsics.checkNotNullExpressionValue(owner, "owner");
                String name2 = fieldInsnNode.f1010name;
                Intrinsics.checkNotNullExpressionValue(name2, "name");
                String desc = fieldInsnNode.desc;
                Intrinsics.checkNotNullExpressionValue(desc, "desc");
                if (!whitelistedStaticFields.contains(new FieldSignature(owner, name2, desc))) {
                    return false;
                }
            }
            int opcode2 = first.getOpcode();
            if (54 <= opcode2 && opcode2 < 59 && ((VarInsnNode) first).var < size) {
                return false;
            }
            if (first.getOpcode() == 132 && ((IincInsnNode) first).var < size) {
                return false;
            }
            int opcode3 = first.getOpcode();
            if (21 <= opcode3 && opcode3 < 26) {
                if (first.getOpcode() == 25 && RedundantNullCheckMethodTransformerKt.isParameterCheckedForNull(first)) {
                    first = first.getNext().getNext().getNext();
                } else {
                    VarInsnNode varInsnNode = (VarInsnNode) first;
                    int i2 = varInsnNode.var;
                    if (i2 == size2) {
                        size2 += ((Type) arrayList.get(i)).getSize();
                        i++;
                        do {
                            first = first.getNext();
                            if (first == null || first.getOpcode() != varInsnNode.getOpcode()) {
                                break;
                            }
                        } while (((VarInsnNode) first).var == i2);
                    } else {
                        if (i2 < size) {
                            return false;
                        }
                        first = varInsnNode.getNext();
                    }
                }
            } else {
                first = first.getNext();
            }
        }
        if (size2 < size) {
            return false;
        }
        while (first != null) {
            int opcode4 = first.getOpcode();
            if ((21 <= opcode4 && opcode4 < 26) || (54 <= (opcode = first.getOpcode()) && opcode < 59)) {
                if (((VarInsnNode) first).var < size) {
                    return false;
                }
            } else if (first.getOpcode() == 132 && ((IincInsnNode) first).var < size) {
                return false;
            }
            first = first.getNext();
        }
        return true;
    }

    static {
        boolean[] zArr = new boolean[256];
        for (int i = 153; i < 178; i++) {
            zArr[i] = true;
        }
        zArr[198] = true;
        zArr[199] = true;
        zArr[191] = true;
        zArr[179] = true;
        zArr[181] = true;
        zArr[182] = true;
        zArr[183] = true;
        zArr[184] = true;
        zArr[185] = true;
        zArr[186] = true;
        zArr[194] = true;
        zArr[195] = true;
        zArr[108] = true;
        zArr[109] = true;
        zArr[112] = true;
        zArr[113] = true;
        zArr[192] = true;
        zArr[188] = true;
        zArr[189] = true;
        zArr[197] = true;
        for (int i2 = 46; i2 < 54; i2++) {
            zArr[i2] = true;
        }
        for (int i3 = 79; i3 < 87; i3++) {
            zArr[i3] = true;
        }
        opcodeProhibitedDuringArgumentsEvaluation = zArr;
    }

    private static final boolean isProhibitedDuringArgumentsEvaluation(AbstractInsnNode abstractInsnNode) {
        boolean[] zArr = opcodeProhibitedDuringArgumentsEvaluation;
        int length = zArr.length;
        int opcode = abstractInsnNode.getOpcode();
        return opcode >= 0 && opcode < length && zArr[abstractInsnNode.getOpcode()];
    }

    private static final void addMarker(InstructionAdapter instructionAdapter, String str) {
        instructionAdapter.visitMethodInsn(184, InlineCodegenUtilsKt.INLINE_MARKER_CLASS_NAME, str, "()V", false);
    }

    public static final void addInplaceCallStartMarker(InstructionAdapter instructionAdapter) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        addMarker(instructionAdapter, "<INPLACE-CALL-START>");
    }

    public static final void addInplaceCallEndMarker(InstructionAdapter instructionAdapter) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        addMarker(instructionAdapter, "<INPLACE-CALL-END>");
    }

    public static final void addInplaceArgumentStartMarker(InstructionAdapter instructionAdapter) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        addMarker(instructionAdapter, "<INPLACE-ARGUMENT-START>");
    }

    public static final void addInplaceArgumentEndMarker(InstructionAdapter instructionAdapter) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        addMarker(instructionAdapter, "<INPLACE-ARGUMENT-END>");
    }

    public static final boolean isInplaceCallStartMarker(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return InlineCodegenUtilsKt.isInlineMarker(abstractInsnNode, "<INPLACE-CALL-START>");
    }

    public static final boolean isInplaceCallEndMarker(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return InlineCodegenUtilsKt.isInlineMarker(abstractInsnNode, "<INPLACE-CALL-END>");
    }

    public static final boolean isInplaceArgumentStartMarker(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return InlineCodegenUtilsKt.isInlineMarker(abstractInsnNode, "<INPLACE-ARGUMENT-START>");
    }

    public static final boolean isInplaceArgumentEndMarker(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return InlineCodegenUtilsKt.isInlineMarker(abstractInsnNode, "<INPLACE-ARGUMENT-END>");
    }
}
