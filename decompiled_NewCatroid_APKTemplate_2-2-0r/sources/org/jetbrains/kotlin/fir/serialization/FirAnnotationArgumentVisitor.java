package org.jetbrains.kotlin.fir.serialization;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.constant.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.constant.AnnotationValue;
import org.jetbrains.kotlin.constant.ArrayValue;
import org.jetbrains.kotlin.constant.BooleanValue;
import org.jetbrains.kotlin.constant.ByteValue;
import org.jetbrains.kotlin.constant.CharValue;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.DoubleValue;
import org.jetbrains.kotlin.constant.EnumValue;
import org.jetbrains.kotlin.constant.ErrorValue;
import org.jetbrains.kotlin.constant.FloatValue;
import org.jetbrains.kotlin.constant.IntValue;
import org.jetbrains.kotlin.constant.KClassValue;
import org.jetbrains.kotlin.constant.LongValue;
import org.jetbrains.kotlin.constant.NullValue;
import org.jetbrains.kotlin.constant.ShortValue;
import org.jetbrains.kotlin.constant.StringValue;
import org.jetbrains.kotlin.constant.UByteValue;
import org.jetbrains.kotlin.constant.UIntValue;
import org.jetbrains.kotlin.constant.ULongValue;
import org.jetbrains.kotlin.constant.UShortValue;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FirAnnotationArgumentVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001e2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020 2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010!\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020$2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010%\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020&2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010'\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020(2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010)\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020*2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010+\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020,2\u0006\u0010\b\u001a\u00020\u0003H\u0016¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationArgumentVisitor;", "Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "", "Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationArgumentVisitorData;", "()V", "visitAnnotationValue", "value", "Lorg/jetbrains/kotlin/constant/AnnotationValue;", "data", "visitArrayValue", "Lorg/jetbrains/kotlin/constant/ArrayValue;", "visitBooleanValue", "Lorg/jetbrains/kotlin/constant/BooleanValue;", "visitByteValue", "Lorg/jetbrains/kotlin/constant/ByteValue;", "visitCharValue", "Lorg/jetbrains/kotlin/constant/CharValue;", "visitDoubleValue", "Lorg/jetbrains/kotlin/constant/DoubleValue;", "visitEnumValue", "Lorg/jetbrains/kotlin/constant/EnumValue;", "visitErrorValue", "Lorg/jetbrains/kotlin/constant/ErrorValue;", "visitFloatValue", "Lorg/jetbrains/kotlin/constant/FloatValue;", "visitIntValue", "Lorg/jetbrains/kotlin/constant/IntValue;", "visitKClassValue", "Lorg/jetbrains/kotlin/constant/KClassValue;", "visitLongValue", "Lorg/jetbrains/kotlin/constant/LongValue;", "visitNullValue", "Lorg/jetbrains/kotlin/constant/NullValue;", "visitShortValue", "Lorg/jetbrains/kotlin/constant/ShortValue;", "visitStringValue", "Lorg/jetbrains/kotlin/constant/StringValue;", "visitUByteValue", "Lorg/jetbrains/kotlin/constant/UByteValue;", "visitUIntValue", "Lorg/jetbrains/kotlin/constant/UIntValue;", "visitULongValue", "Lorg/jetbrains/kotlin/constant/ULongValue;", "visitUShortValue", "Lorg/jetbrains/kotlin/constant/UShortValue;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationArgumentVisitor extends AnnotationArgumentVisitor<Unit, FirAnnotationArgumentVisitorData> {
    public static final FirAnnotationArgumentVisitor INSTANCE = new FirAnnotationArgumentVisitor();

    private FirAnnotationArgumentVisitor() {
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitAnnotationValue(AnnotationValue annotationValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitAnnotationValue2(annotationValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitArrayValue(ArrayValue arrayValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitArrayValue2(arrayValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitBooleanValue(BooleanValue booleanValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitBooleanValue2(booleanValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitByteValue(ByteValue byteValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitByteValue2(byteValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitCharValue(CharValue charValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitCharValue2(charValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitDoubleValue(DoubleValue doubleValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitDoubleValue2(doubleValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitEnumValue(EnumValue enumValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitEnumValue2(enumValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorValue(ErrorValue errorValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitErrorValue2(errorValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitFloatValue(FloatValue floatValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitFloatValue2(floatValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitIntValue(IntValue intValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitIntValue2(intValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitKClassValue(KClassValue kClassValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitKClassValue2(kClassValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitLongValue(LongValue longValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitLongValue2(longValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitNullValue(NullValue nullValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitNullValue2(nullValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitShortValue(ShortValue shortValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitShortValue2(shortValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitStringValue(StringValue stringValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitStringValue2(stringValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitUByteValue(UByteValue uByteValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitUByteValue2(uByteValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitUIntValue(UIntValue uIntValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitUIntValue2(uIntValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitULongValue(ULongValue uLongValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitULongValue2(uLongValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Unit visitUShortValue(UShortValue uShortValue, FirAnnotationArgumentVisitorData firAnnotationArgumentVisitorData) {
        visitUShortValue2(uShortValue, firAnnotationArgumentVisitorData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitAnnotationValue, reason: avoid collision after fix types in other method */
    public void visitAnnotationValue2(AnnotationValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.ANNOTATION);
        data.getBuilder().setAnnotation(data.getSerializer().serializeAnnotation(value));
    }

    /* JADX INFO: renamed from: visitArrayValue, reason: avoid collision after fix types in other method */
    public void visitArrayValue2(ArrayValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.ARRAY);
        Iterator<? extends ConstantValue<?>> it = value.getValue().iterator();
        while (it.hasNext()) {
            data.getBuilder().addArrayElement(data.getSerializer().valueProto$fir_serialization(it.next()).build());
        }
    }

    /* JADX INFO: renamed from: visitBooleanValue, reason: avoid collision after fix types in other method */
    public void visitBooleanValue2(BooleanValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.BOOLEAN);
        data.getBuilder().setIntValue(value.getValue().booleanValue() ? 1L : 0L);
    }

    /* JADX INFO: renamed from: visitByteValue, reason: avoid collision after fix types in other method */
    public void visitByteValue2(ByteValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.BYTE);
        data.getBuilder().setIntValue(value.getValue().byteValue());
    }

    /* JADX INFO: renamed from: visitCharValue, reason: avoid collision after fix types in other method */
    public void visitCharValue2(CharValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.CHAR);
        data.getBuilder().setIntValue(value.getValue().charValue());
    }

    /* JADX INFO: renamed from: visitDoubleValue, reason: avoid collision after fix types in other method */
    public void visitDoubleValue2(DoubleValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.DOUBLE);
        data.getBuilder().setDoubleValue(value.getValue().doubleValue());
    }

    /* JADX INFO: renamed from: visitEnumValue, reason: avoid collision after fix types in other method */
    public void visitEnumValue2(EnumValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.ENUM);
        data.getBuilder().setClassId(data.getStringTable().getQualifiedClassNameIndex(value.getEnumClassId()));
        ProtoBuf.Annotation.Argument.Value.Builder builder = data.getBuilder();
        FirElementAwareStringTable stringTable = data.getStringTable();
        String strAsString = value.getEnumEntryName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        builder.setEnumValueId(stringTable.getStringIndex(strAsString));
    }

    /* JADX INFO: renamed from: visitErrorValue, reason: avoid collision after fix types in other method */
    public void visitErrorValue2(ErrorValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new UnsupportedOperationException("Error value: " + value);
    }

    /* JADX INFO: renamed from: visitFloatValue, reason: avoid collision after fix types in other method */
    public void visitFloatValue2(FloatValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.FLOAT);
        data.getBuilder().setFloatValue(value.getValue().floatValue());
    }

    /* JADX INFO: renamed from: visitIntValue, reason: avoid collision after fix types in other method */
    public void visitIntValue2(IntValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.INT);
        data.getBuilder().setIntValue(value.getValue().intValue());
    }

    /* JADX INFO: renamed from: visitKClassValue, reason: avoid collision after fix types in other method */
    public void visitKClassValue2(KClassValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.CLASS);
        KClassValue.Value value2 = value.getValue();
        if (value2 instanceof KClassValue.Value.NormalClass) {
            KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value2;
            data.getBuilder().setClassId(data.getStringTable().getQualifiedClassNameIndex(normalClass.getClassId()));
            if (normalClass.getArrayDimensions() > 0) {
                data.getBuilder().setArrayDimensionCount(normalClass.getArrayDimensions());
                return;
            }
            return;
        }
        if (value2 instanceof KClassValue.Value.LocalClass) {
            KotlinTypeMarker type = ((KClassValue.Value.LocalClass) value2).getType();
            if (type == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
            }
            ConeKotlinType coneKotlinTypeArrayElementType = (ConeKotlinType) type;
            int i = 0;
            while (true) {
                coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(coneKotlinTypeArrayElementType);
                if (coneKotlinTypeArrayElementType == null) {
                    break;
                } else {
                    i++;
                }
            }
            if (i > 0) {
                data.getBuilder().setArrayDimensionCount(i);
            }
        }
    }

    /* JADX INFO: renamed from: visitLongValue, reason: avoid collision after fix types in other method */
    public void visitLongValue2(LongValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.LONG);
        data.getBuilder().setIntValue(value.getValue().longValue());
    }

    /* JADX INFO: renamed from: visitNullValue, reason: avoid collision after fix types in other method */
    public void visitNullValue2(NullValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new UnsupportedOperationException("Null should not appear in annotation arguments");
    }

    /* JADX INFO: renamed from: visitShortValue, reason: avoid collision after fix types in other method */
    public void visitShortValue2(ShortValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.SHORT);
        data.getBuilder().setIntValue(value.getValue().shortValue());
    }

    /* JADX INFO: renamed from: visitStringValue, reason: avoid collision after fix types in other method */
    public void visitStringValue2(StringValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.STRING);
        data.getBuilder().setStringValue(data.getStringTable().getStringIndex(value.getValue()));
    }

    /* JADX INFO: renamed from: visitUByteValue, reason: avoid collision after fix types in other method */
    public void visitUByteValue2(UByteValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.BYTE);
        data.getBuilder().setIntValue(value.getValue().byteValue());
        data.getBuilder().setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
    }

    /* JADX INFO: renamed from: visitUShortValue, reason: avoid collision after fix types in other method */
    public void visitUShortValue2(UShortValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.SHORT);
        data.getBuilder().setIntValue(value.getValue().shortValue());
        data.getBuilder().setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
    }

    /* JADX INFO: renamed from: visitUIntValue, reason: avoid collision after fix types in other method */
    public void visitUIntValue2(UIntValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.INT);
        data.getBuilder().setIntValue(value.getValue().intValue());
        data.getBuilder().setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
    }

    /* JADX INFO: renamed from: visitULongValue, reason: avoid collision after fix types in other method */
    public void visitULongValue2(ULongValue value, FirAnnotationArgumentVisitorData data) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(data, "data");
        data.getBuilder().setType(ProtoBuf.Annotation.Argument.Value.Type.LONG);
        data.getBuilder().setIntValue(value.getValue().longValue());
        data.getBuilder().setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
    }
}
