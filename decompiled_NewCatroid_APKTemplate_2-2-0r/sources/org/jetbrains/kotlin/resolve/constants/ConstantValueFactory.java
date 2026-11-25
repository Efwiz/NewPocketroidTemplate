package org.jetbrains.kotlin.resolve.constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: ConstantValueFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\u00062\u0006\u0010\f\u001a\u00020\rJ\"\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bJ$\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b2\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00152\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/ConstantValueFactory;", "", "()V", "createArrayValue", "Lorg/jetbrains/kotlin/resolve/constants/ArrayValue;", "value", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "componentType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "createConstantValue", "createIntegerConstantValue", "", "expectedType", "isUnsigned", "", "createUnsignedValue", "Lorg/jetbrains/kotlin/resolve/constants/UnsignedValueConstant;", "constantValue", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantValueFactory {
    public static final ConstantValueFactory INSTANCE = new ConstantValueFactory();

    private ConstantValueFactory() {
    }

    public final ArrayValue createArrayValue(List<? extends ConstantValue<?>> value, KotlinType type) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        return new TypedArrayValue(value, type);
    }

    public static /* synthetic */ ConstantValue createConstantValue$default(ConstantValueFactory constantValueFactory, Object obj, ModuleDescriptor moduleDescriptor, int i, Object obj2) {
        if ((i & 2) != 0) {
            moduleDescriptor = null;
        }
        return constantValueFactory.createConstantValue(obj, moduleDescriptor);
    }

    public final ConstantValue<?> createConstantValue(Object value, ModuleDescriptor module) {
        if (value instanceof Byte) {
            return new ByteValue(((Number) value).byteValue());
        }
        if (value instanceof Short) {
            return new ShortValue(((Number) value).shortValue());
        }
        if (value instanceof Integer) {
            return new IntValue(((Number) value).intValue());
        }
        if (value instanceof Long) {
            return new LongValue(((Number) value).longValue());
        }
        if (value instanceof Character) {
            return new CharValue(((Character) value).charValue());
        }
        if (value instanceof Float) {
            return new FloatValue(((Number) value).floatValue());
        }
        if (value instanceof Double) {
            return new DoubleValue(((Number) value).doubleValue());
        }
        if (value instanceof Boolean) {
            return new BooleanValue(((Boolean) value).booleanValue());
        }
        if (value instanceof String) {
            return new StringValue((String) value);
        }
        if (value instanceof byte[]) {
            return createArrayValue(ArraysKt.toList((byte[]) value), module, PrimitiveType.BYTE);
        }
        if (value instanceof short[]) {
            return createArrayValue(ArraysKt.toList((short[]) value), module, PrimitiveType.SHORT);
        }
        if (value instanceof int[]) {
            return createArrayValue(ArraysKt.toList((int[]) value), module, PrimitiveType.INT);
        }
        if (value instanceof long[]) {
            return createArrayValue(ArraysKt.toList((long[]) value), module, PrimitiveType.LONG);
        }
        if (value instanceof char[]) {
            return createArrayValue(ArraysKt.toList((char[]) value), module, PrimitiveType.CHAR);
        }
        if (value instanceof float[]) {
            return createArrayValue(ArraysKt.toList((float[]) value), module, PrimitiveType.FLOAT);
        }
        if (value instanceof double[]) {
            return createArrayValue(ArraysKt.toList((double[]) value), module, PrimitiveType.DOUBLE);
        }
        if (value instanceof boolean[]) {
            return createArrayValue(ArraysKt.toList((boolean[]) value), module, PrimitiveType.BOOLEAN);
        }
        if (value == null) {
            return new NullValue();
        }
        return null;
    }

    public final UnsignedValueConstant<?> createUnsignedValue(ConstantValue<?> constantValue) {
        Intrinsics.checkNotNullParameter(constantValue, "constantValue");
        if (constantValue instanceof ByteValue) {
            return new UByteValue(((ByteValue) constantValue).getValue().byteValue());
        }
        if (constantValue instanceof ShortValue) {
            return new UShortValue(((ShortValue) constantValue).getValue().shortValue());
        }
        if (constantValue instanceof IntValue) {
            return new UIntValue(((IntValue) constantValue).getValue().intValue());
        }
        if (constantValue instanceof LongValue) {
            return new ULongValue(((LongValue) constantValue).getValue().longValue());
        }
        return null;
    }

    private final ArrayValue createArrayValue(List<?> value, ModuleDescriptor module, final PrimitiveType componentType) {
        List list = CollectionsKt.toList(value);
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ConstantValue constantValueCreateConstantValue$default = createConstantValue$default(this, it.next(), null, 2, null);
            if (constantValueCreateConstantValue$default != null) {
                arrayList.add(constantValueCreateConstantValue$default);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (module != null) {
            SimpleType primitiveArrayKotlinType = module.getBuiltIns().getPrimitiveArrayKotlinType(componentType);
            Intrinsics.checkNotNullExpressionValue(primitiveArrayKotlinType, "getPrimitiveArrayKotlinType(...)");
            return new TypedArrayValue(arrayList2, primitiveArrayKotlinType);
        }
        return new ArrayValue(arrayList2, new Function1<ModuleDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.constants.ConstantValueFactory.createArrayValue.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(ModuleDescriptor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                SimpleType primitiveArrayKotlinType2 = it2.getBuiltIns().getPrimitiveArrayKotlinType(componentType);
                Intrinsics.checkNotNullExpressionValue(primitiveArrayKotlinType2, "getPrimitiveArrayKotlinType(...)");
                return primitiveArrayKotlinType2;
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0030  */
    /* JADX WARN: Code duplicated, block: B:13:0x003a  */
    /* JADX WARN: Code duplicated, block: B:14:0x0043  */
    /* JADX WARN: Code duplicated, block: B:16:0x0049  */
    /* JADX WARN: Code duplicated, block: B:18:0x0052  */
    /* JADX WARN: Code duplicated, block: B:19:0x005b  */
    /* JADX WARN: Code duplicated, block: B:21:0x0061  */
    /* JADX WARN: Code duplicated, block: B:31:0x0090  */
    /* JADX WARN: Code duplicated, block: B:33:0x0096  */
    /* JADX WARN: Code duplicated, block: B:35:0x009d  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:38:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:40:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:41:0x00bc  */
    /* JADX WARN: Code duplicated, block: B:43:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:9:0x002a  */
    public final ConstantValue<?> createIntegerConstantValue(long value, KotlinType expectedType, boolean isUnsigned) {
        byte b;
        short s;
        int i;
        short s2;
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(expectedType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(...)");
        IntValue intValue = null;
        if (isUnsigned) {
            if (KotlinBuiltIns.isUByte(kotlinTypeMakeNotNullable)) {
                byte b2 = (byte) value;
                if (value == ConstantValueFactoryKt.fromUByteToLong(b2)) {
                    intValue = new UByteValue(b2);
                } else if (KotlinBuiltIns.isUShort(kotlinTypeMakeNotNullable)) {
                    s2 = (short) value;
                    if (value == ConstantValueFactoryKt.fromUShortToLong(s2)) {
                        intValue = new UShortValue(s2);
                    } else if (KotlinBuiltIns.isUInt(kotlinTypeMakeNotNullable)) {
                        i = (int) value;
                        if (value == ConstantValueFactoryKt.fromUIntToLong(i)) {
                            intValue = new UIntValue(i);
                        } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                            intValue = new ULongValue(value);
                        }
                    } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                        intValue = new ULongValue(value);
                    }
                } else if (KotlinBuiltIns.isUInt(kotlinTypeMakeNotNullable)) {
                    i = (int) value;
                    if (value == ConstantValueFactoryKt.fromUIntToLong(i)) {
                        intValue = new UIntValue(i);
                    } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                        intValue = new ULongValue(value);
                    }
                } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                    intValue = new ULongValue(value);
                }
            } else if (KotlinBuiltIns.isUShort(kotlinTypeMakeNotNullable)) {
                s2 = (short) value;
                if (value == ConstantValueFactoryKt.fromUShortToLong(s2)) {
                    intValue = new UShortValue(s2);
                } else if (KotlinBuiltIns.isUInt(kotlinTypeMakeNotNullable)) {
                    i = (int) value;
                    if (value == ConstantValueFactoryKt.fromUIntToLong(i)) {
                        intValue = new UIntValue(i);
                    } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                        intValue = new ULongValue(value);
                    }
                } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                    intValue = new ULongValue(value);
                }
            } else if (KotlinBuiltIns.isUInt(kotlinTypeMakeNotNullable)) {
                i = (int) value;
                if (value == ConstantValueFactoryKt.fromUIntToLong(i)) {
                    intValue = new UIntValue(i);
                } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                    intValue = new ULongValue(value);
                }
            } else if (KotlinBuiltIns.isULong(kotlinTypeMakeNotNullable)) {
                intValue = new ULongValue(value);
            }
            return intValue;
        }
        if (KotlinBuiltIns.isLong(kotlinTypeMakeNotNullable)) {
            intValue = new LongValue(value);
        } else if (KotlinBuiltIns.isInt(kotlinTypeMakeNotNullable)) {
            int i2 = (int) value;
            if (value == i2) {
                intValue = new IntValue(i2);
            } else if (KotlinBuiltIns.isShort(kotlinTypeMakeNotNullable)) {
                s = (short) value;
                if (value == s) {
                    intValue = new ShortValue(s);
                } else if (KotlinBuiltIns.isByte(kotlinTypeMakeNotNullable)) {
                    b = (byte) value;
                    if (value == b) {
                        intValue = new ByteValue(b);
                    } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
                        intValue = new IntValue((int) value);
                    }
                } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
                    intValue = new IntValue((int) value);
                }
            } else if (KotlinBuiltIns.isByte(kotlinTypeMakeNotNullable)) {
                b = (byte) value;
                if (value == b) {
                    intValue = new ByteValue(b);
                } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
                    intValue = new IntValue((int) value);
                }
            } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
                intValue = new IntValue((int) value);
            }
        } else if (KotlinBuiltIns.isShort(kotlinTypeMakeNotNullable)) {
            s = (short) value;
            if (value == s) {
                intValue = new ShortValue(s);
            } else if (KotlinBuiltIns.isByte(kotlinTypeMakeNotNullable)) {
                b = (byte) value;
                if (value == b) {
                    intValue = new ByteValue(b);
                } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
                    intValue = new IntValue((int) value);
                }
            } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
                intValue = new IntValue((int) value);
            }
        } else if (KotlinBuiltIns.isByte(kotlinTypeMakeNotNullable)) {
            b = (byte) value;
            if (value == b) {
                intValue = new ByteValue(b);
            } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
                intValue = new IntValue((int) value);
            }
        } else if (KotlinBuiltIns.isChar(kotlinTypeMakeNotNullable)) {
            intValue = new IntValue((int) value);
        }
        return intValue;
    }
}
