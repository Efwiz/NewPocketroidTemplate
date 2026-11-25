package org.jetbrains.kotlin.serialization.deserialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorImpl;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.ByteValue;
import org.jetbrains.kotlin.resolve.constants.CharValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.resolve.constants.DoubleValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.FloatValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.ShortValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.UByteValue;
import org.jetbrains.kotlin.resolve.constants.UIntValue;
import org.jetbrains.kotlin.resolve.constants.ULongValue;
import org.jetbrains.kotlin.resolve.constants.UShortValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: AnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J$\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J>\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u001b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0014\u0018\u00010\u001a2\u0006\u0010\r\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\"\u0010$\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0010J$\u0010%\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0010H\u0002JN\u0010&\u001a\u0002H'\"\u0004\b\u0000\u0010(\"\u0004\b\u0001\u0010'*\u0002H(2\u0006\u0010)\u001a\u00020\u00122\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H'0+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H'0+H\u0082\b¢\u0006\u0002\u0010-R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationDeserializer;", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "deserializeAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "doesValueConformToExpectedType", "", "result", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "value", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value;", "resolveArgument", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument;", "parameterByName", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "resolveValue", "resolveValueAndCheckExpectedType", "letIf", "R", ExifInterface.GPS_DIRECTION_TRUE, "predicate", "f", "Lkotlin/Function1;", "g", "(Ljava/lang/Object;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationDeserializer {
    private final ModuleDescriptor module;
    private final NotFoundClasses notFoundClasses;

    /* JADX INFO: compiled from: AnnotationDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.Annotation.Argument.Value.Type.values().length];
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.BYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.INT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.LONG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.DOUBLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.CLASS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ENUM.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ANNOTATION.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ARRAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public AnnotationDeserializer(ModuleDescriptor module, NotFoundClasses notFoundClasses) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        this.module = module;
        this.notFoundClasses = notFoundClasses;
    }

    private final KotlinBuiltIns getBuiltIns() {
        return this.module.getBuiltIns();
    }

    public final AnnotationDescriptor deserializeAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        ClassDescriptor classDescriptorResolveClass = resolveClass(NameResolverUtilKt.getClassId(nameResolver, proto.getId()));
        Map mapEmptyMap = MapsKt.emptyMap();
        if (proto.getArgumentCount() != 0) {
            ClassDescriptor classDescriptor = classDescriptorResolveClass;
            if (!ErrorUtils.isError(classDescriptor) && DescriptorUtils.isAnnotationClass(classDescriptor)) {
                Collection<ClassConstructorDescriptor> constructors = classDescriptorResolveClass.getConstructors();
                Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
                ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.singleOrNull(constructors);
                if (classConstructorDescriptor != null) {
                    List<ValueParameterDescriptor> valueParameters = classConstructorDescriptor.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    List<ValueParameterDescriptor> list = valueParameters;
                    LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                    for (Object obj : list) {
                        linkedHashMap.put(((ValueParameterDescriptor) obj).getShortName(), obj);
                    }
                    List<ProtoBuf.Annotation.Argument> argumentList = proto.getArgumentList();
                    Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
                    ArrayList arrayList = new ArrayList();
                    for (ProtoBuf.Annotation.Argument argument : argumentList) {
                        Intrinsics.checkNotNull(argument);
                        Pair<Name, ConstantValue<?>> pairResolveArgument = resolveArgument(argument, linkedHashMap, nameResolver);
                        if (pairResolveArgument != null) {
                            arrayList.add(pairResolveArgument);
                        }
                    }
                    mapEmptyMap = MapsKt.toMap(arrayList);
                }
            }
        }
        return new AnnotationDescriptorImpl(classDescriptorResolveClass.getDefaultType(), mapEmptyMap, SourceElement.NO_SOURCE);
    }

    private final Pair<Name, ConstantValue<?>> resolveArgument(ProtoBuf.Annotation.Argument proto, Map<Name, ? extends ValueParameterDescriptor> parameterByName, NameResolver nameResolver) {
        ValueParameterDescriptor valueParameterDescriptor = parameterByName.get(NameResolverUtilKt.getName(nameResolver, proto.getNameId()));
        if (valueParameterDescriptor == null) {
            return null;
        }
        Name name2 = NameResolverUtilKt.getName(nameResolver, proto.getNameId());
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        ProtoBuf.Annotation.Argument.Value value = proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return new Pair<>(name2, resolveValueAndCheckExpectedType(type, value, nameResolver));
    }

    private final ConstantValue<?> resolveValueAndCheckExpectedType(KotlinType expectedType, ProtoBuf.Annotation.Argument.Value value, NameResolver nameResolver) {
        ConstantValue<?> constantValueResolveValue = resolveValue(expectedType, value, nameResolver);
        if (!doesValueConformToExpectedType(constantValueResolveValue, expectedType, value)) {
            constantValueResolveValue = null;
        }
        return constantValueResolveValue == null ? ErrorValue.INSTANCE.create("Unexpected argument value: actual type " + value.getType() + " != expected type " + expectedType) : constantValueResolveValue;
    }

    public final ConstantValue<?> resolveValue(KotlinType expectedType, ProtoBuf.Annotation.Argument.Value value, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Boolean bool = Flags.IS_UNSIGNED.get(value.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        boolean zBooleanValue = bool.booleanValue();
        ProtoBuf.Annotation.Argument.Value.Type type = value.getType();
        switch (type == null ? -1 : WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
            case 1:
                byte intValue = (byte) value.getIntValue();
                return zBooleanValue ? new UByteValue(intValue) : new ByteValue(intValue);
            case 2:
                return new CharValue((char) value.getIntValue());
            case 3:
                short intValue2 = (short) value.getIntValue();
                return zBooleanValue ? new UShortValue(intValue2) : new ShortValue(intValue2);
            case 4:
                int intValue3 = (int) value.getIntValue();
                return zBooleanValue ? new UIntValue(intValue3) : new IntValue(intValue3);
            case 5:
                long intValue4 = value.getIntValue();
                return zBooleanValue ? new ULongValue(intValue4) : new LongValue(intValue4);
            case 6:
                return new FloatValue(value.getFloatValue());
            case 7:
                return new DoubleValue(value.getDoubleValue());
            case 8:
                return new BooleanValue(value.getIntValue() != 0);
            case 9:
                return new StringValue(nameResolver.getString(value.getStringValue()));
            case 10:
                return new KClassValue(NameResolverUtilKt.getClassId(nameResolver, value.getClassId()), value.getArrayDimensionCount());
            case 11:
                return new EnumValue(NameResolverUtilKt.getClassId(nameResolver, value.getClassId()), NameResolverUtilKt.getName(nameResolver, value.getEnumValueId()));
            case 12:
                ProtoBuf.Annotation annotation = value.getAnnotation();
                Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
                return new AnnotationValue(deserializeAnnotation(annotation, nameResolver));
            case 13:
                ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
                List<ProtoBuf.Annotation.Argument.Value> arrayElementList = value.getArrayElementList();
                Intrinsics.checkNotNullExpressionValue(arrayElementList, "getArrayElementList(...)");
                List<ProtoBuf.Annotation.Argument.Value> list = arrayElementList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (ProtoBuf.Annotation.Argument.Value value2 : list) {
                    SimpleType anyType = getBuiltIns().getAnyType();
                    Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
                    Intrinsics.checkNotNull(value2);
                    arrayList.add(resolveValue(anyType, value2, nameResolver));
                }
                return constantValueFactory.createArrayValue(arrayList, expectedType);
            default:
                throw new IllegalStateException(("Unsupported annotation argument type: " + value.getType() + " (expected " + expectedType + ')').toString());
        }
    }

    private final boolean doesValueConformToExpectedType(ConstantValue<?> result, KotlinType expectedType, ProtoBuf.Annotation.Argument.Value value) {
        ProtoBuf.Annotation.Argument.Value.Type type = value.getType();
        int i = type == null ? -1 : WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 10) {
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = expectedType.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor != null && !KotlinBuiltIns.isKClass(classDescriptor)) {
                return false;
            }
        } else if (i == 13) {
            if (!(result instanceof ArrayValue) || ((ArrayValue) result).getValue().size() != value.getArrayElementList().size()) {
                throw new IllegalStateException(("Deserialized ArrayValue should have the same number of elements as the original array value: " + result).toString());
            }
            KotlinType arrayElementType = getBuiltIns().getArrayElementType(expectedType);
            Intrinsics.checkNotNullExpressionValue(arrayElementType, "getArrayElementType(...)");
            ArrayValue arrayValue = (ArrayValue) result;
            Iterable indices = CollectionsKt.getIndices(arrayValue.getValue());
            if (!(indices instanceof Collection) || !((Collection) indices).isEmpty()) {
                Iterator it = indices.iterator();
                while (it.hasNext()) {
                    int iNextInt = ((IntIterator) it).nextInt();
                    ConstantValue<?> constantValue = arrayValue.getValue().get(iNextInt);
                    ProtoBuf.Annotation.Argument.Value arrayElement = value.getArrayElement(iNextInt);
                    Intrinsics.checkNotNullExpressionValue(arrayElement, "getArrayElement(...)");
                    if (!doesValueConformToExpectedType(constantValue, arrayElementType, arrayElement)) {
                        return false;
                    }
                }
            }
        } else {
            return Intrinsics.areEqual(result.getType(this.module), expectedType);
        }
        return true;
    }

    private final ClassDescriptor resolveClass(ClassId classId) {
        return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.module, classId, this.notFoundClasses);
    }
}
