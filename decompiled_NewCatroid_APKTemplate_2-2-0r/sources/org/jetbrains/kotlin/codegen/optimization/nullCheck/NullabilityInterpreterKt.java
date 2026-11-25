package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;

/* JADX INFO: compiled from: NullabilityInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getObjectType", "Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NullabilityInterpreterKt {
    public static final Type getObjectType(TypeInsnNode typeInsnNode) {
        Intrinsics.checkNotNullParameter(typeInsnNode, "<this>");
        Type objectType = Type.getObjectType(typeInsnNode.desc);
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        return objectType;
    }
}
