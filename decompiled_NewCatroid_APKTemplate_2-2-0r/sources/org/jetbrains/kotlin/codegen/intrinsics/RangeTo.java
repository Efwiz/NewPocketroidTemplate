package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: RangeTo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/RangeTo;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "rangeTypeToPrimitiveType", "Lorg/jetbrains/org/objectweb/asm/Type;", "rangeType", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeTo extends IntrinsicMethod {
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final Type rangeTypeToPrimitiveType(Type rangeType) {
        String internalName = rangeType.getInternalName();
        Intrinsics.checkNotNull(internalName);
        String strSubstringBefore$default = StringsKt.substringBefore$default(StringsKt.substringAfter$default(internalName, "kotlin/ranges/", (String) null, 2, (Object) null), "Range", (String) null, 2, (Object) null);
        switch (strSubstringBefore$default.hashCode()) {
            case 73679:
                if (strSubstringBefore$default.equals("Int")) {
                    Type INT_TYPE = Type.INT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
                    return INT_TYPE;
                }
                break;
            case 2086184:
                if (strSubstringBefore$default.equals("Byte")) {
                    Type BYTE_TYPE = Type.BYTE_TYPE;
                    Intrinsics.checkNotNullExpressionValue(BYTE_TYPE, "BYTE_TYPE");
                    return BYTE_TYPE;
                }
                break;
            case 2099062:
                if (strSubstringBefore$default.equals("Char")) {
                    Type CHAR_TYPE = Type.CHAR_TYPE;
                    Intrinsics.checkNotNullExpressionValue(CHAR_TYPE, "CHAR_TYPE");
                    return CHAR_TYPE;
                }
                break;
            case 2374300:
                if (strSubstringBefore$default.equals("Long")) {
                    Type LONG_TYPE = Type.LONG_TYPE;
                    Intrinsics.checkNotNullExpressionValue(LONG_TYPE, "LONG_TYPE");
                    return LONG_TYPE;
                }
                break;
            case 67973692:
                if (strSubstringBefore$default.equals("Float")) {
                    Type FLOAT_TYPE = Type.FLOAT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(FLOAT_TYPE, "FLOAT_TYPE");
                    return FLOAT_TYPE;
                }
                break;
            case 79860828:
                if (strSubstringBefore$default.equals("Short")) {
                    Type SHORT_TYPE = Type.SHORT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(SHORT_TYPE, "SHORT_TYPE");
                    return SHORT_TYPE;
                }
                break;
            case 2052876273:
                if (strSubstringBefore$default.equals("Double")) {
                    Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
                    Intrinsics.checkNotNullExpressionValue(DOUBLE_TYPE, "DOUBLE_TYPE");
                    return DOUBLE_TYPE;
                }
                break;
        }
        throw new IllegalStateException("RangeTo intrinsic can only work for primitive types: " + internalName);
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        final Type typeRangeTypeToPrimitiveType = rangeTypeToPrimitiveType(method.getReturnType());
        Type returnType = method.getReturnType();
        List<Type> valueParameterTypes = method.getValueParameterTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypes, 10));
        for (Type type : valueParameterTypes) {
            arrayList.add(typeRangeTypeToPrimitiveType);
        }
        return new IntrinsicCallable(returnType, arrayList, nullOr(method.getDispatchReceiverType(), typeRangeTypeToPrimitiveType), nullOr(method.getExtensionReceiverType(), typeRangeTypeToPrimitiveType)) { // from class: org.jetbrains.kotlin.codegen.intrinsics.RangeTo.toCallable.1
            @Override // org.jetbrains.kotlin.codegen.Callable
            public void afterReceiverGeneration(InstructionAdapter v, FrameMap frameMap, GenerationState state) {
                Intrinsics.checkNotNullParameter(v, "v");
                Intrinsics.checkNotNullParameter(frameMap, "frameMap");
                Intrinsics.checkNotNullParameter(state, "state");
                Type type2 = typeRangeTypeToPrimitiveType;
                String internalName = getReturnType().getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                CodegenUtilKt.generateNewInstanceDupAndPlaceBeforeStackTop(v, frameMap, type2, internalName);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                String internalName = getReturnType().getInternalName();
                Type type2 = Type.VOID_TYPE;
                Type type3 = typeRangeTypeToPrimitiveType;
                v.invokespecial(internalName, "<init>", Type.getMethodDescriptor(type2, type3, type3), false);
            }
        };
    }
}
