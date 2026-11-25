package org.jetbrains.kotlin.psi.stubs.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
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
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.StubUtils;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: KotlinConstantValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u000b\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\f2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u000f\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00102\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0011\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00122\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0013\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00142\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0015\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00162\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0017\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00182\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0019\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u001a2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001b\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u001c2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001d\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u001e2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001f\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020 2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010!\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\"2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010#\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020$2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010%\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020&2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010'\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020(2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010)\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020*2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010+\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020,2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010-\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020.2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinConstantValueSerializationVisitor;", "Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "", "", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "(Lcom/intellij/psi/stubs/StubOutputStream;)V", "visitAnnotationValue", "value", "Lorg/jetbrains/kotlin/constant/AnnotationValue;", "data", "visitArrayValue", "Lorg/jetbrains/kotlin/constant/ArrayValue;", "visitBooleanValue", "Lorg/jetbrains/kotlin/constant/BooleanValue;", "visitByteValue", "Lorg/jetbrains/kotlin/constant/ByteValue;", "visitCharValue", "Lorg/jetbrains/kotlin/constant/CharValue;", "visitDoubleValue", "Lorg/jetbrains/kotlin/constant/DoubleValue;", "visitEnumValue", "Lorg/jetbrains/kotlin/constant/EnumValue;", "visitErrorValue", "Lorg/jetbrains/kotlin/constant/ErrorValue;", "visitFloatValue", "Lorg/jetbrains/kotlin/constant/FloatValue;", "visitIntValue", "Lorg/jetbrains/kotlin/constant/IntValue;", "visitKClassValue", "Lorg/jetbrains/kotlin/constant/KClassValue;", "visitLongValue", "Lorg/jetbrains/kotlin/constant/LongValue;", "visitNullValue", "Lorg/jetbrains/kotlin/constant/NullValue;", "visitShortValue", "Lorg/jetbrains/kotlin/constant/ShortValue;", "visitStringValue", "Lorg/jetbrains/kotlin/constant/StringValue;", "visitUByteValue", "Lorg/jetbrains/kotlin/constant/UByteValue;", "visitUIntValue", "Lorg/jetbrains/kotlin/constant/UIntValue;", "visitULongValue", "Lorg/jetbrains/kotlin/constant/ULongValue;", "visitUShortValue", "Lorg/jetbrains/kotlin/constant/UShortValue;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinConstantValueSerializationVisitor extends AnnotationArgumentVisitor {
    private final StubOutputStream dataStream;

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitAnnotationValue(AnnotationValue annotationValue, Object obj) throws IOException {
        visitAnnotationValue(annotationValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitArrayValue(ArrayValue arrayValue, Object obj) {
        visitArrayValue(arrayValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitBooleanValue(BooleanValue booleanValue, Object obj) {
        visitBooleanValue(booleanValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitByteValue(ByteValue byteValue, Object obj) {
        visitByteValue(byteValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitCharValue(CharValue charValue, Object obj) {
        visitCharValue(charValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitDoubleValue(DoubleValue doubleValue, Object obj) {
        visitDoubleValue(doubleValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitEnumValue(EnumValue enumValue, Object obj) throws IOException {
        visitEnumValue(enumValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitErrorValue(ErrorValue errorValue, Object obj) {
        visitErrorValue(errorValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitFloatValue(FloatValue floatValue, Object obj) {
        visitFloatValue(floatValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitIntValue(IntValue intValue, Object obj) {
        visitIntValue(intValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitKClassValue(KClassValue kClassValue, Object obj) throws IOException {
        visitKClassValue(kClassValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitLongValue(LongValue longValue, Object obj) {
        visitLongValue(longValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitNullValue(NullValue nullValue, Object obj) {
        visitNullValue(nullValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitShortValue(ShortValue shortValue, Object obj) {
        visitShortValue(shortValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitStringValue(StringValue stringValue, Object obj) throws IOException {
        visitStringValue(stringValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitUByteValue(UByteValue uByteValue, Object obj) {
        visitUByteValue(uByteValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitUIntValue(UIntValue uIntValue, Object obj) {
        visitUIntValue(uIntValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitULongValue(ULongValue uLongValue, Object obj) {
        visitULongValue(uLongValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.constant.AnnotationArgumentVisitor
    public /* bridge */ /* synthetic */ Object visitUShortValue(UShortValue uShortValue, Object obj) {
        visitUShortValue(uShortValue, (Void) obj);
        return Unit.INSTANCE;
    }

    public KotlinConstantValueSerializationVisitor(StubOutputStream dataStream) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        this.dataStream = dataStream;
    }

    public void visitArrayValue(ArrayValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.ARRAY.ordinal());
        this.dataStream.writeInt(value.getValue().size());
        Iterator<? extends ConstantValue<?>> it = value.getValue().iterator();
        while (it.hasNext()) {
            it.next().accept(this, data);
        }
    }

    public void visitBooleanValue(BooleanValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.BOOLEAN.ordinal());
        this.dataStream.writeBoolean(value.getValue().booleanValue());
    }

    public void visitByteValue(ByteValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.BYTE.ordinal());
        this.dataStream.writeByte(value.getValue().byteValue());
    }

    public void visitCharValue(CharValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.CHAR.ordinal());
        this.dataStream.writeChar(value.getValue().charValue());
    }

    public void visitShortValue(ShortValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.SHORT.ordinal());
        this.dataStream.writeShort(value.getValue().shortValue());
    }

    public void visitIntValue(IntValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.INT.ordinal());
        this.dataStream.writeInt(value.getValue().intValue());
    }

    public void visitLongValue(LongValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.LONG.ordinal());
        this.dataStream.writeLong(value.getValue().longValue());
    }

    public void visitDoubleValue(DoubleValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.DOUBLE.ordinal());
        this.dataStream.writeDouble(value.getValue().doubleValue());
    }

    public void visitFloatValue(FloatValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.FLOAT.ordinal());
        this.dataStream.writeFloat(value.getValue().floatValue());
    }

    public void visitEnumValue(EnumValue value, Void data) throws IOException {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.ENUM.ordinal());
        StubUtils.serializeClassId(this.dataStream, value.getEnumClassId());
        this.dataStream.writeName(value.getEnumEntryName().getIdentifier());
    }

    public void visitKClassValue(KClassValue value, Void data) throws IOException {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.KCLASS.ordinal());
        KClassValue.Value value2 = value.getValue();
        Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type org.jetbrains.kotlin.constant.KClassValue.Value.NormalClass");
        KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value2;
        StubUtils.serializeClassId(this.dataStream, normalClass.getClassId());
        this.dataStream.writeInt(normalClass.getArrayDimensions());
    }

    public void visitNullValue(NullValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.NULL.ordinal());
    }

    public void visitStringValue(StringValue value, Void data) throws IOException {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.STRING.ordinal());
        this.dataStream.writeName(value.getValue());
    }

    public void visitUByteValue(UByteValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.UBYTE.ordinal());
        this.dataStream.writeByte(value.getValue().byteValue());
    }

    public void visitUShortValue(UShortValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.USHORT.ordinal());
        this.dataStream.writeShort(value.getValue().shortValue());
    }

    public void visitUIntValue(UIntValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.UINT.ordinal());
        this.dataStream.writeInt(value.getValue().intValue());
    }

    public void visitULongValue(ULongValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.ULONG.ordinal());
        this.dataStream.writeLong(value.getValue().longValue());
    }

    public void visitAnnotationValue(AnnotationValue value, Void data) throws IOException {
        Intrinsics.checkNotNullParameter(value, "value");
        this.dataStream.writeInt(KotlinConstantValueKind.ANNO.ordinal());
        StubOutputStream stubOutputStream = this.dataStream;
        KotlinTypeMarker type = value.getValue().getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.stubs.impl.KotlinClassTypeBean");
        StubUtils.serializeClassId(stubOutputStream, ((KotlinClassTypeBean) type).getClassId());
        Map<Name, ConstantValue<?>> argumentsMapping = value.getValue().getArgumentsMapping();
        this.dataStream.writeInt(argumentsMapping.size());
        for (Map.Entry<Name, ConstantValue<?>> entry : argumentsMapping.entrySet()) {
            this.dataStream.writeName(entry.getKey().asString());
            entry.getValue().accept(this, data);
        }
    }

    public void visitErrorValue(ErrorValue value, Void data) {
        Intrinsics.checkNotNullParameter(value, "value");
        throw new IllegalStateException("Error values should not be reachable in compiled code".toString());
    }
}
