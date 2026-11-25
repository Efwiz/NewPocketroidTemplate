package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreterKt;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: ChangeBoxingMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/ChangeBoxingMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "wrapperToInternalBoxing", "", "", "transform", "", "internalClassName", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ChangeBoxingMethodTransformer extends MethodTransformer {
    public static final ChangeBoxingMethodTransformer INSTANCE = new ChangeBoxingMethodTransformer();
    private static final Map<String, String> wrapperToInternalBoxing;

    private ChangeBoxingMethodTransformer() {
    }

    static {
        HashMap map = new HashMap();
        for (JvmPrimitiveType jvmPrimitiveType : JvmPrimitiveType.values()) {
            FqName wrapperFqName = jvmPrimitiveType.getWrapperFqName();
            Intrinsics.checkNotNullExpressionValue(wrapperFqName, "getWrapperFqName(...)");
            String str = CodegenUtilKt.topLevelClassInternalName(wrapperFqName);
            HashMap map2 = map;
            StringBuilder sb = new StringBuilder("box");
            String javaKeywordName = jvmPrimitiveType.getJavaKeywordName();
            Intrinsics.checkNotNullExpressionValue(javaKeywordName, "getJavaKeywordName(...)");
            if (javaKeywordName.length() > 0) {
                char upperCase = Character.toUpperCase(javaKeywordName.charAt(0));
                String strSubstring = javaKeywordName.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                javaKeywordName = upperCase + strSubstring;
            }
            map2.put(str, sb.append(javaKeywordName).toString());
        }
        wrapperToInternalBoxing = map;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : SequencesKt.filter(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.coroutines.ChangeBoxingMethodTransformer.transform.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(BoxingInterpreterKt.isPrimitiveBoxing(it));
            }
        })) {
            abstractInsnNode.getOpcode();
            Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            String str = wrapperToInternalBoxing.get(methodInsnNode.owner);
            if (str != null) {
                methodNode.instructions.set(abstractInsnNode, new MethodInsnNode(methodInsnNode.getOpcode(), ChangeBoxingMethodTransformerKt.BOXING_CLASS_INTERNAL_NAME, str, methodInsnNode.desc, false));
            } else {
                throw new AssertionError("expected primitive wrapper, but got " + methodInsnNode.owner);
            }
        }
    }
}
