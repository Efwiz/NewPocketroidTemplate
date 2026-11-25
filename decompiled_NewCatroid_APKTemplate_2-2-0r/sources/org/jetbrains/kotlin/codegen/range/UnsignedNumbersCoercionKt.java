package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.ws.WebSocketProtocol;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: UnsignedNumbersCoercion.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0016\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a \u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00022\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\n\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\b\u001a \u0010\n\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00022\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\b\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\f"}, d2 = {"unsignedType", "Lorg/jetbrains/kotlin/builtins/UnsignedType;", "Lorg/jetbrains/kotlin/codegen/StackValue;", "getUnsignedType", "(Lorg/jetbrains/kotlin/codegen/StackValue;)Lorg/jetbrains/kotlin/builtins/UnsignedType;", "coerceUnsignedToUInt", "stackValue", "uIntKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "valueKotlinType", "coerceUnsignedToULong", "uLongKotlinType", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UnsignedNumbersCoercionKt {
    public static final UnsignedType getUnsignedType(StackValue stackValue) {
        Intrinsics.checkNotNullParameter(stackValue, "<this>");
        KotlinType kotlinType = stackValue.kotlinType;
        if (kotlinType != null) {
            return UnsignedTypes.INSTANCE.toUnsignedType(kotlinType);
        }
        return null;
    }

    public static final StackValue coerceUnsignedToUInt(StackValue stackValue, KotlinType uIntKotlinType) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(uIntKotlinType, "uIntKotlinType");
        return coerceUnsignedToUInt(stackValue, stackValue.kotlinType, uIntKotlinType);
    }

    public static final StackValue coerceUnsignedToUInt(final StackValue stackValue, final KotlinType kotlinType, KotlinType uIntKotlinType) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(uIntKotlinType, "uIntKotlinType");
        KotlinType kotlinType2 = stackValue.kotlinType;
        if (kotlinType2 != null && TypeUtilsKt.isNothing(kotlinType2)) {
            return stackValue;
        }
        final UnsignedType unsignedType = getUnsignedType(stackValue);
        if (unsignedType == null) {
            throw new AssertionError("Unsigned type expected: " + stackValue.kotlinType);
        }
        if (unsignedType == UnsignedType.UINT) {
            return stackValue;
        }
        StackValue stackValueOperation = StackValue.operation(Type.INT_TYPE, uIntKotlinType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.range.UnsignedNumbersCoercionKt.coerceUnsignedToUInt.2

            /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.range.UnsignedNumbersCoercionKt$coerceUnsignedToUInt$2$WhenMappings */
            /* JADX INFO: compiled from: UnsignedNumbersCoercion.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[UnsignedType.values().length];
                    try {
                        iArr[UnsignedType.UBYTE.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[UnsignedType.USHORT.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[UnsignedType.ULONG.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                StackValue stackValue2 = stackValue;
                stackValue2.put(stackValue2.type, kotlinType, instructionAdapter);
                int i = WhenMappings.$EnumSwitchMapping$0[unsignedType.ordinal()];
                if (i == 1) {
                    instructionAdapter.iconst(255);
                    instructionAdapter.and(Type.INT_TYPE);
                } else if (i == 2) {
                    instructionAdapter.iconst(65535);
                    instructionAdapter.and(Type.INT_TYPE);
                } else {
                    if (i == 3) {
                        instructionAdapter.cast(Type.LONG_TYPE, Type.INT_TYPE);
                        return;
                    }
                    throw new AssertionError("Unexpected value type: " + kotlinType);
                }
            }
        });
        Intrinsics.checkNotNullExpressionValue(stackValueOperation, "operation(...)");
        return stackValueOperation;
    }

    public static final StackValue coerceUnsignedToULong(StackValue stackValue, KotlinType uLongKotlinType) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(uLongKotlinType, "uLongKotlinType");
        return coerceUnsignedToULong(stackValue, stackValue.kotlinType, uLongKotlinType);
    }

    public static final StackValue coerceUnsignedToULong(final StackValue stackValue, final KotlinType kotlinType, KotlinType uLongKotlinType) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(uLongKotlinType, "uLongKotlinType");
        final UnsignedType unsignedType = getUnsignedType(stackValue);
        if (unsignedType == null) {
            throw new AssertionError("Unsigned type expected: " + kotlinType);
        }
        if (unsignedType == UnsignedType.ULONG) {
            return stackValue;
        }
        StackValue stackValueOperation = StackValue.operation(Type.LONG_TYPE, uLongKotlinType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.range.UnsignedNumbersCoercionKt.coerceUnsignedToULong.1

            /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.range.UnsignedNumbersCoercionKt$coerceUnsignedToULong$1$WhenMappings */
            /* JADX INFO: compiled from: UnsignedNumbersCoercion.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[UnsignedType.values().length];
                    try {
                        iArr[UnsignedType.UBYTE.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[UnsignedType.USHORT.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[UnsignedType.UINT.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                StackValue stackValue2 = stackValue;
                stackValue2.put(stackValue2.type, kotlinType, instructionAdapter);
                int i = WhenMappings.$EnumSwitchMapping$0[unsignedType.ordinal()];
                if (i == 1) {
                    instructionAdapter.cast(Type.INT_TYPE, Type.LONG_TYPE);
                    instructionAdapter.lconst(255L);
                    instructionAdapter.and(Type.LONG_TYPE);
                } else if (i == 2) {
                    instructionAdapter.cast(Type.INT_TYPE, Type.LONG_TYPE);
                    instructionAdapter.lconst(WebSocketProtocol.PAYLOAD_SHORT_MAX);
                    instructionAdapter.and(Type.LONG_TYPE);
                } else {
                    if (i == 3) {
                        instructionAdapter.cast(Type.INT_TYPE, Type.LONG_TYPE);
                        instructionAdapter.lconst(4294967295L);
                        instructionAdapter.and(Type.LONG_TYPE);
                        return;
                    }
                    throw new AssertionError("Unexpected value type: " + kotlinType);
                }
            }
        });
        Intrinsics.checkNotNullExpressionValue(stackValueOperation, "operation(...)");
        return stackValueOperation;
    }
}
