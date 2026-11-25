package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;

/* JADX INFO: compiled from: ReifiedTypeInliner.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"operationKind", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$OperationKind;", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "getOperationKind", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;)Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$OperationKind;", "reificationArgument", "Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", "getReificationArgument", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;)Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReifiedTypeInlinerKt {
    public static final ReificationArgument getReificationArgument(MethodInsnNode methodInsnNode) {
        Intrinsics.checkNotNullParameter(methodInsnNode, "<this>");
        AbstractInsnNode previous = methodInsnNode.getPrevious();
        Intrinsics.checkNotNull(previous);
        if (previous.getOpcode() != 18) {
            return null;
        }
        Object obj = ((LdcInsnNode) previous).cst;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
        String str = (String) obj;
        String str2 = str;
        int length = str2.length();
        int i = 0;
        while (i < length) {
            if (str2.charAt(i) != '[') {
                String strSubstring = str.substring(i);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                return new ReificationArgument(StringsKt.removeSuffix(strSubstring, (CharSequence) TypeDescription.Generic.OfWildcardType.SYMBOL), StringsKt.endsWith$default((CharSequence) str2, MangleConstant.Q_MARK, false, 2, (Object) null), i);
            }
            i++;
        }
        i = -1;
        String strSubstring2 = str.substring(i);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        return new ReificationArgument(StringsKt.removeSuffix(strSubstring2, (CharSequence) TypeDescription.Generic.OfWildcardType.SYMBOL), StringsKt.endsWith$default((CharSequence) str2, MangleConstant.Q_MARK, false, 2, (Object) null), i);
    }

    public static final ReifiedTypeInliner.OperationKind getOperationKind(MethodInsnNode methodInsnNode) {
        AbstractInsnNode previous;
        Integer intConstant;
        Intrinsics.checkNotNullParameter(methodInsnNode, "<this>");
        AbstractInsnNode previous2 = methodInsnNode.getPrevious();
        if (previous2 == null || (previous = previous2.getPrevious()) == null || (intConstant = UtilKt.getIntConstant(previous)) == null) {
            return null;
        }
        return (ReifiedTypeInliner.OperationKind) ArraysKt.getOrNull(ReifiedTypeInliner.OperationKind.values(), intConstant.intValue());
    }
}
