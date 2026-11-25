package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;

/* JADX INFO: compiled from: BoxedBasicValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a&\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0001\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u0000\u001a\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u000f"}, d2 = {"getMultiFieldValueClassUnboxInfo", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$MultiFieldValueClassUnboxInfo;", "boxedType", "Lorg/jetbrains/org/objectweb/asm/Type;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getUnboxedTypes", "", "multiFieldValueClassUnboxInfo", "isValueClassValue", "", "makePops", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "unboxedTypes", "unboxedTypeOfInlineClass", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BoxedBasicValueKt {
    public static final InsnList makePops(List<Type> unboxedTypes) {
        Intrinsics.checkNotNullParameter(unboxedTypes, "unboxedTypes");
        InsnList insnList = new InsnList();
        Iterator it = CollectionsKt.asReversed(unboxedTypes).iterator();
        while (true) {
            boolean z = false;
            while (true) {
                if (!it.hasNext()) {
                    if (z) {
                        insnList.add(new InsnNode(87));
                    }
                    return insnList;
                }
                Type type = (Type) it.next();
                int size = type.getSize();
                if (size == 1) {
                    if (z) {
                        insnList.add(new InsnNode(88));
                    }
                    if (z) {
                        break;
                    }
                    z = true;
                } else {
                    if (size == 2) {
                        if (z) {
                            insnList.add(new InsnNode(87));
                        }
                        insnList.add(new InsnNode(88));
                        break;
                    }
                    throw new IllegalStateException(("Illegal type size: " + type.getSize()).toString());
                }
            }
        }
    }

    public static final List<Type> getUnboxedTypes(Type boxedType, GenerationState state, GenerationState.MultiFieldValueClassUnboxInfo multiFieldValueClassUnboxInfo) {
        Intrinsics.checkNotNullParameter(boxedType, "boxedType");
        Intrinsics.checkNotNullParameter(state, "state");
        Type typeUnboxPrimitiveTypeOrNull = AsmUtil.unboxPrimitiveTypeOrNull(boxedType);
        if (typeUnboxPrimitiveTypeOrNull != null) {
            return CollectionsKt.listOf(typeUnboxPrimitiveTypeOrNull);
        }
        if (Intrinsics.areEqual(boxedType, AsmTypes.K_CLASS_TYPE)) {
            return CollectionsKt.listOf(AsmTypes.JAVA_CLASS_TYPE);
        }
        Type typeUnboxedTypeOfInlineClass = unboxedTypeOfInlineClass(boxedType, state);
        if (typeUnboxedTypeOfInlineClass != null) {
            return CollectionsKt.listOf(typeUnboxedTypeOfInlineClass);
        }
        if (multiFieldValueClassUnboxInfo != null) {
            return multiFieldValueClassUnboxInfo.getUnboxedTypes();
        }
        throw new IllegalArgumentException("Expected primitive type wrapper or KClass or inline class wrapper, got: " + boxedType);
    }

    public static final Type unboxedTypeOfInlineClass(Type boxedType, GenerationState state) {
        Intrinsics.checkNotNullParameter(boxedType, "boxedType");
        Intrinsics.checkNotNullParameter(state, "state");
        ClassDescriptor classDescriptor = (ClassDescriptor) CollectionsKt.singleOrNull((List) state.getJvmBackendClassResolver().resolveToClassDescriptors(boxedType));
        if (classDescriptor != null) {
            if (!InlineClassesUtilsKt.isInlineClass(classDescriptor)) {
                classDescriptor = null;
            }
            if (classDescriptor != null) {
                return state.getMapInlineClass().invoke(classDescriptor);
            }
        }
        return null;
    }

    public static final GenerationState.MultiFieldValueClassUnboxInfo getMultiFieldValueClassUnboxInfo(Type boxedType, GenerationState state) {
        ClassDescriptor classDescriptor;
        Intrinsics.checkNotNullParameter(boxedType, "boxedType");
        Intrinsics.checkNotNullParameter(state, "state");
        if (state.getConfig().getSupportMultiFieldValueClasses() && (classDescriptor = (ClassDescriptor) CollectionsKt.singleOrNull((List) state.getJvmBackendClassResolver().resolveToClassDescriptors(boxedType))) != null) {
            if (!InlineClassesUtilsKt.isMultiFieldValueClass(classDescriptor)) {
                classDescriptor = null;
            }
            if (classDescriptor != null) {
                return state.getMultiFieldValueClassUnboxInfo().invoke(classDescriptor);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isValueClassValue(Type type) {
        return (AsmUtil.isBoxedPrimitiveType(type) || Intrinsics.areEqual(type, AsmTypes.K_CLASS_TYPE)) ? false : true;
    }
}
