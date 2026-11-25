package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import kotlin.Metadata;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CapturedVarsOptimizationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"0\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0005`\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"INIT_METHOD_NAME", "", "REF_ELEMENT_FIELD", "REF_TYPE_TO_ELEMENT_TYPE", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/Type;", "Lkotlin/collections/HashMap;", "getREF_TYPE_TO_ELEMENT_TYPE", "()Ljava/util/HashMap;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CapturedVarsOptimizationMethodTransformerKt {
    public static final String INIT_METHOD_NAME = "<init>";
    public static final String REF_ELEMENT_FIELD = "element";
    private static final HashMap<String, Type> REF_TYPE_TO_ELEMENT_TYPE;

    static {
        HashMap<String, Type> map = new HashMap<>();
        map.put(AsmTypes.OBJECT_REF_TYPE.getInternalName(), AsmTypes.OBJECT_TYPE);
        for (PrimitiveType primitiveType : PrimitiveType.values()) {
            map.put(AsmTypes.sharedTypeForPrimitive(primitiveType).getInternalName(), AsmTypes.valueTypeForPrimitive(primitiveType));
        }
        REF_TYPE_TO_ELEMENT_TYPE = map;
    }

    public static final HashMap<String, Type> getREF_TYPE_TO_ELEMENT_TYPE() {
        return REF_TYPE_TO_ELEMENT_TYPE;
    }
}
