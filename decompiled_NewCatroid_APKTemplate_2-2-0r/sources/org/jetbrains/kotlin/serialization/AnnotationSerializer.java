package org.jetbrains.kotlin.serialization;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.ByteValue;
import org.jetbrains.kotlin.resolve.constants.CharValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.DoubleValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.FloatValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.ShortValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.UByteValue;
import org.jetbrains.kotlin.resolve.constants.UIntValue;
import org.jetbrains.kotlin.resolve.constants.ULongValue;
import org.jetbrains.kotlin.resolve.constants.UShortValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: AnnotationSerializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bJ\u0012\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/AnnotationSerializer;", "", "stringTable", "Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "(Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;)V", "getAnnotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "serializeAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "valueProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value$Builder;", "constant", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AnnotationSerializer {
    private final DescriptorAwareStringTable stringTable;

    public AnnotationSerializer(DescriptorAwareStringTable stringTable) {
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        this.stringTable = stringTable;
    }

    public final ProtoBuf.Annotation serializeAnnotation(AnnotationDescriptor annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        ProtoBuf.Annotation.Builder builderNewBuilder = ProtoBuf.Annotation.newBuilder();
        ClassId annotationClassId = getAnnotationClassId(annotation);
        if (annotationClassId == null) {
            return null;
        }
        builderNewBuilder.setId(this.stringTable.getQualifiedClassNameIndex(annotationClassId));
        for (Map.Entry<Name, ConstantValue<?>> entry : annotation.getAllValueArguments().entrySet()) {
            Name key = entry.getKey();
            ConstantValue<?> value = entry.getValue();
            ProtoBuf.Annotation.Argument.Builder builderNewBuilder2 = ProtoBuf.Annotation.Argument.newBuilder();
            DescriptorAwareStringTable descriptorAwareStringTable = this.stringTable;
            String strAsString = key.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            builderNewBuilder2.setNameId(descriptorAwareStringTable.getStringIndex(strAsString));
            builderNewBuilder2.setValue(valueProto(value));
            builderNewBuilder.addArgument(builderNewBuilder2);
        }
        return builderNewBuilder.build();
    }

    protected ClassId getAnnotationClassId(AnnotationDescriptor annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotation);
        if (annotationClass == null) {
            throw new IllegalStateException(("Annotation type is not a class: " + annotation.getType()).toString());
        }
        if (ErrorUtils.isError(annotationClass)) {
            throw new IllegalStateException(("Unresolved annotation type: " + annotation.getType() + " at " + annotation.getSource().getContainingFile()).toString());
        }
        return DescriptorUtilsKt.getClassId((ClassifierDescriptor) annotationClass);
    }

    public final ProtoBuf.Annotation.Argument.Value.Builder valueProto(ConstantValue<?> constant) {
        Intrinsics.checkNotNullParameter(constant, "constant");
        final ProtoBuf.Annotation.Argument.Value.Builder builderNewBuilder = ProtoBuf.Annotation.Argument.Value.newBuilder();
        constant.accept((AnnotationArgumentVisitor) new AnnotationArgumentVisitor<Unit, Unit>() { // from class: org.jetbrains.kotlin.serialization.AnnotationSerializer$valueProto$1$1
            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitAnnotationValue(AnnotationValue annotationValue, Unit unit) {
                visitAnnotationValue2(annotationValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitArrayValue(ArrayValue arrayValue, Unit unit) {
                visitArrayValue2(arrayValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitBooleanValue(BooleanValue booleanValue, Unit unit) {
                visitBooleanValue2(booleanValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitByteValue(ByteValue byteValue, Unit unit) {
                visitByteValue2(byteValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitCharValue(CharValue charValue, Unit unit) {
                visitCharValue2(charValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitDoubleValue(DoubleValue doubleValue, Unit unit) {
                visitDoubleValue2(doubleValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitEnumValue(EnumValue enumValue, Unit unit) {
                visitEnumValue2(enumValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitErrorValue(ErrorValue errorValue, Unit unit) {
                visitErrorValue2(errorValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitFloatValue(FloatValue floatValue, Unit unit) {
                visitFloatValue2(floatValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitIntValue(IntValue intValue, Unit unit) {
                visitIntValue2(intValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitKClassValue(KClassValue kClassValue, Unit unit) {
                visitKClassValue2(kClassValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitLongValue(LongValue longValue, Unit unit) {
                visitLongValue2(longValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitNullValue(NullValue nullValue, Unit unit) {
                visitNullValue2(nullValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitShortValue(ShortValue shortValue, Unit unit) {
                visitShortValue2(shortValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitStringValue(StringValue stringValue, Unit unit) {
                visitStringValue2(stringValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitUByteValue(UByteValue uByteValue, Unit unit) {
                visitUByteValue2(uByteValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitUIntValue(UIntValue uIntValue, Unit unit) {
                visitUIntValue2(uIntValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitULongValue(ULongValue uLongValue, Unit unit) {
                visitULongValue2(uLongValue, unit);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public /* bridge */ /* synthetic */ Unit visitUShortValue(UShortValue uShortValue, Unit unit) {
                visitUShortValue2(uShortValue, unit);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitAnnotationValue, reason: avoid collision after fix types in other method */
            public void visitAnnotationValue2(AnnotationValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.ANNOTATION);
                builderNewBuilder.setAnnotation(this.serializeAnnotation(value.getValue()));
            }

            /* JADX INFO: renamed from: visitArrayValue, reason: avoid collision after fix types in other method */
            public void visitArrayValue2(ArrayValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.ARRAY);
                Iterator<? extends ConstantValue<?>> it = value.getValue().iterator();
                while (it.hasNext()) {
                    builderNewBuilder.addArrayElement(this.valueProto(it.next()).build());
                }
            }

            /* JADX INFO: renamed from: visitBooleanValue, reason: avoid collision after fix types in other method */
            public void visitBooleanValue2(BooleanValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.BOOLEAN);
                builderNewBuilder.setIntValue(value.getValue().booleanValue() ? 1L : 0L);
            }

            /* JADX INFO: renamed from: visitByteValue, reason: avoid collision after fix types in other method */
            public void visitByteValue2(ByteValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.BYTE);
                builderNewBuilder.setIntValue(value.getValue().byteValue());
            }

            /* JADX INFO: renamed from: visitCharValue, reason: avoid collision after fix types in other method */
            public void visitCharValue2(CharValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.CHAR);
                builderNewBuilder.setIntValue(value.getValue().charValue());
            }

            /* JADX INFO: renamed from: visitDoubleValue, reason: avoid collision after fix types in other method */
            public void visitDoubleValue2(DoubleValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.DOUBLE);
                builderNewBuilder.setDoubleValue(value.getValue().doubleValue());
            }

            /* JADX INFO: renamed from: visitEnumValue, reason: avoid collision after fix types in other method */
            public void visitEnumValue2(EnumValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.ENUM);
                builderNewBuilder.setClassId(this.stringTable.getQualifiedClassNameIndex(value.getEnumClassId()));
                ProtoBuf.Annotation.Argument.Value.Builder builder = builderNewBuilder;
                DescriptorAwareStringTable descriptorAwareStringTable = this.stringTable;
                String strAsString = value.getEnumEntryName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                builder.setEnumValueId(descriptorAwareStringTable.getStringIndex(strAsString));
            }

            /* JADX INFO: renamed from: visitErrorValue, reason: avoid collision after fix types in other method */
            public void visitErrorValue2(ErrorValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                throw new UnsupportedOperationException("Error value: " + value);
            }

            /* JADX INFO: renamed from: visitFloatValue, reason: avoid collision after fix types in other method */
            public void visitFloatValue2(FloatValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.FLOAT);
                builderNewBuilder.setFloatValue(value.getValue().floatValue());
            }

            /* JADX INFO: renamed from: visitIntValue, reason: avoid collision after fix types in other method */
            public void visitIntValue2(IntValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.INT);
                builderNewBuilder.setIntValue(value.getValue().intValue());
            }

            /* JADX INFO: renamed from: visitKClassValue, reason: avoid collision after fix types in other method */
            public void visitKClassValue2(KClassValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.CLASS);
                KClassValue.Value value2 = value.getValue();
                if (value2 instanceof KClassValue.Value.NormalClass) {
                    KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value2;
                    builderNewBuilder.setClassId(this.stringTable.getQualifiedClassNameIndex(normalClass.getClassId()));
                    if (normalClass.getArrayDimensions() > 0) {
                        builderNewBuilder.setArrayDimensionCount(normalClass.getArrayDimensions());
                        return;
                    }
                    return;
                }
                if (value2 instanceof KClassValue.Value.LocalClass) {
                    KotlinType type = ((KClassValue.Value.LocalClass) value2).getType();
                    int i = 0;
                    while (KotlinBuiltIns.isArray(type)) {
                        i++;
                        type = ((TypeProjection) CollectionsKt.single((List) type.getArguments())).getNullableAnyType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    }
                    ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
                    ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                    if (classDescriptor != null) {
                        builderNewBuilder.setClassId(this.stringTable.getFqNameIndex(classDescriptor));
                        if (i > 0) {
                            builderNewBuilder.setArrayDimensionCount(i);
                            return;
                        }
                        return;
                    }
                    throw new IllegalStateException(("Type parameters are not allowed in class literal annotation arguments: " + value2).toString());
                }
            }

            /* JADX INFO: renamed from: visitLongValue, reason: avoid collision after fix types in other method */
            public void visitLongValue2(LongValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.LONG);
                builderNewBuilder.setIntValue(value.getValue().longValue());
            }

            /* JADX INFO: renamed from: visitNullValue, reason: avoid collision after fix types in other method */
            public void visitNullValue2(NullValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                throw new UnsupportedOperationException("Null should not appear in annotation arguments");
            }

            /* JADX INFO: renamed from: visitShortValue, reason: avoid collision after fix types in other method */
            public void visitShortValue2(ShortValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.SHORT);
                builderNewBuilder.setIntValue(value.getValue().shortValue());
            }

            /* JADX INFO: renamed from: visitStringValue, reason: avoid collision after fix types in other method */
            public void visitStringValue2(StringValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(data, "data");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.STRING);
                builderNewBuilder.setStringValue(this.stringTable.getStringIndex(value.getValue()));
            }

            /* JADX INFO: renamed from: visitUByteValue, reason: avoid collision after fix types in other method */
            public void visitUByteValue2(UByteValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.BYTE);
                builderNewBuilder.setIntValue(value.getValue().byteValue());
                builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
            }

            /* JADX INFO: renamed from: visitUShortValue, reason: avoid collision after fix types in other method */
            public void visitUShortValue2(UShortValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.SHORT);
                builderNewBuilder.setIntValue(value.getValue().shortValue());
                builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
            }

            /* JADX INFO: renamed from: visitUIntValue, reason: avoid collision after fix types in other method */
            public void visitUIntValue2(UIntValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.INT);
                builderNewBuilder.setIntValue(value.getValue().intValue());
                builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
            }

            /* JADX INFO: renamed from: visitULongValue, reason: avoid collision after fix types in other method */
            public void visitULongValue2(ULongValue value, Unit data) {
                Intrinsics.checkNotNullParameter(value, "value");
                builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.LONG);
                builderNewBuilder.setIntValue(value.getValue().longValue());
                builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
            }
        }, Unit.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
        return builderNewBuilder;
    }
}
