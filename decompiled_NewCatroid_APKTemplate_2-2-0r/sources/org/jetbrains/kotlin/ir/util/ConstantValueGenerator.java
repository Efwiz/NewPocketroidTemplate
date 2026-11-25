package org.jetbrains.kotlin.ir.util;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
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
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.error.ErrorClassDescriptor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: ConstantValueGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ \u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001c\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H$J$\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H$J\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ,\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\n\u0010 \u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010!\u001a\u00020\u000fJ:\u0010\"\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\n\u0010 \u001a\u0006\u0012\u0002\b\u00030\f2\b\u0010#\u001a\u0004\u0018\u00010\u001b2\b\u0010$\u001a\u0004\u0018\u00010\u001bH\u0002J\"\u0010%\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\n\u0010 \u001a\u0006\u0012\u0002\b\u00030\fJ\f\u0010&\u001a\u00020\u001b*\u00020\u001bH\u0002J\f\u0010'\u001a\u00020(*\u00020\u001bH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "allowErrorTypeInAnnotations", "", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Z)V", "adjustAnnotationArgumentValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "value", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "extractAnnotationOffsets", "Lkotlin/Pair;", "", "annotationDescriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "extractAnnotationParameterOffsets", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "generateAnnotationConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "realType", "Lorg/jetbrains/kotlin/types/KotlinType;", "generateAnnotationValueAsExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "endOffset", "constantValue", "valueParameter", "generateConstantOrAnnotationValueAsExpression", "expectedType", "expectedArrayElementType", "generateConstantValueAsExpression", "getArrayElementType", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstantValueGenerator {
    private final boolean allowErrorTypeInAnnotations;
    private final ModuleDescriptor moduleDescriptor;
    private final ReferenceSymbolTable symbolTable;
    private final TypeTranslator typeTranslator;

    protected abstract Pair<Integer, Integer> extractAnnotationOffsets(AnnotationDescriptor annotationDescriptor);

    protected abstract Pair<Integer, Integer> extractAnnotationParameterOffsets(AnnotationDescriptor annotationDescriptor, Name argumentName);

    public ConstantValueGenerator(ModuleDescriptor moduleDescriptor, ReferenceSymbolTable symbolTable, TypeTranslator typeTranslator, boolean z) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        this.moduleDescriptor = moduleDescriptor;
        this.symbolTable = symbolTable;
        this.typeTranslator = typeTranslator;
        this.allowErrorTypeInAnnotations = z;
    }

    private final IrType toIrType(KotlinType kotlinType) {
        return this.typeTranslator.translateType(kotlinType);
    }

    public final IrExpression generateConstantValueAsExpression(int startOffset, int endOffset, ConstantValue<?> constantValue) {
        Intrinsics.checkNotNullParameter(constantValue, "constantValue");
        IrExpression irExpressionGenerateConstantOrAnnotationValueAsExpression = generateConstantOrAnnotationValueAsExpression(startOffset, endOffset, constantValue, null, null);
        Intrinsics.checkNotNull(irExpressionGenerateConstantOrAnnotationValueAsExpression);
        return irExpressionGenerateConstantOrAnnotationValueAsExpression;
    }

    public final IrExpression generateAnnotationValueAsExpression(int startOffset, int endOffset, ConstantValue<?> constantValue, ValueParameterDescriptor valueParameter) {
        Intrinsics.checkNotNullParameter(constantValue, "constantValue");
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return generateConstantOrAnnotationValueAsExpression(startOffset, endOffset, constantValue, valueParameter.getType(), valueParameter.getVarargElementType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression generateConstantOrAnnotationValueAsExpression(int startOffset, int endOffset, ConstantValue<?> constantValue, KotlinType expectedType, KotlinType expectedArrayElementType) {
        KotlinType type = constantValue.getType(this.moduleDescriptor);
        KotlinType kotlinType = expectedType == null ? type : expectedType;
        IrType irType = toIrType(kotlinType);
        if (constantValue instanceof StringValue) {
            return IrConstImpl.INSTANCE.string(startOffset, endOffset, irType, ((StringValue) constantValue).getValue());
        }
        if (constantValue instanceof IntValue) {
            return IrConstImpl.INSTANCE.m7211int(startOffset, endOffset, irType, ((IntValue) constantValue).getValue().intValue());
        }
        if (constantValue instanceof UIntValue) {
            return IrConstImpl.INSTANCE.m7211int(startOffset, endOffset, irType, ((UIntValue) constantValue).getValue().intValue());
        }
        if (constantValue instanceof NullValue) {
            return IrConstImpl.INSTANCE.constNull(startOffset, endOffset, irType);
        }
        if (constantValue instanceof BooleanValue) {
            return IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, irType, ((BooleanValue) constantValue).getValue().booleanValue());
        }
        if (constantValue instanceof LongValue) {
            return IrConstImpl.INSTANCE.m7212long(startOffset, endOffset, irType, ((LongValue) constantValue).getValue().longValue());
        }
        if (constantValue instanceof ULongValue) {
            return IrConstImpl.INSTANCE.m7212long(startOffset, endOffset, irType, ((ULongValue) constantValue).getValue().longValue());
        }
        if (constantValue instanceof DoubleValue) {
            return IrConstImpl.INSTANCE.m7209double(startOffset, endOffset, irType, ((DoubleValue) constantValue).getValue().doubleValue());
        }
        if (constantValue instanceof FloatValue) {
            return IrConstImpl.INSTANCE.m7210float(startOffset, endOffset, irType, ((FloatValue) constantValue).getValue().floatValue());
        }
        if (constantValue instanceof CharValue) {
            return IrConstImpl.INSTANCE.m7208char(startOffset, endOffset, irType, ((CharValue) constantValue).getValue().charValue());
        }
        if (constantValue instanceof ByteValue) {
            return IrConstImpl.INSTANCE.m7207byte(startOffset, endOffset, irType, ((ByteValue) constantValue).getValue().byteValue());
        }
        if (constantValue instanceof UByteValue) {
            return IrConstImpl.INSTANCE.m7207byte(startOffset, endOffset, irType, ((UByteValue) constantValue).getValue().byteValue());
        }
        if (constantValue instanceof ShortValue) {
            return IrConstImpl.INSTANCE.m7213short(startOffset, endOffset, irType, ((ShortValue) constantValue).getValue().shortValue());
        }
        if (constantValue instanceof UShortValue) {
            return IrConstImpl.INSTANCE.m7213short(startOffset, endOffset, irType, ((UShortValue) constantValue).getValue().shortValue());
        }
        IrClassReferenceImpl irClassReferenceImpl = null;
        if (constantValue instanceof ArrayValue) {
            KotlinType arrayElementType = expectedArrayElementType == null ? getArrayElementType(type) : expectedArrayElementType;
            IrType irType2 = toIrType(arrayElementType);
            List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = value.iterator();
            while (it.hasNext()) {
                IrExpression irExpressionGenerateConstantOrAnnotationValueAsExpression = generateConstantOrAnnotationValueAsExpression(startOffset, endOffset, (ConstantValue) it.next(), expectedType != null ? arrayElementType : null, null);
                if (irExpressionGenerateConstantOrAnnotationValueAsExpression != null) {
                    arrayList.add(irExpressionGenerateConstantOrAnnotationValueAsExpression);
                }
            }
            return new IrVarargImpl(startOffset, endOffset, irType, irType2, CollectionsKt.compactIfPossible(arrayList));
        }
        if (constantValue instanceof EnumValue) {
            ClassifierDescriptor contributedClassifier = type.getMemberScope().mo7465getContributedClassifier(((EnumValue) constantValue).getEnumEntryName(), NoLookupLocation.FROM_BACKEND);
            if (contributedClassifier == null) {
                return null;
            }
            if (!(contributedClassifier instanceof ClassDescriptor)) {
                throw new AssertionError("Enum entry " + contributedClassifier + " should be a ClassDescriptor");
            }
            if (DescriptorUtils.isEnumEntry(contributedClassifier)) {
                return new IrGetEnumValueImpl(startOffset, endOffset, irType, this.symbolTable.getDescriptorExtension().referenceEnumEntry(contributedClassifier));
            }
            return null;
        }
        if (constantValue instanceof AnnotationValue) {
            return generateAnnotationConstructorCall(((AnnotationValue) constantValue).getValue(), kotlinType);
        }
        if (constantValue instanceof KClassValue) {
            KotlinType argumentType = ((KClassValue) constantValue).getArgumentType(this.moduleDescriptor);
            if (!KotlinTypeKt.isError(argumentType)) {
                ClassifierDescriptor this$0 = argumentType.getConstructor().getThis$0();
                if (this$0 == null) {
                    throw new AssertionError("Unexpected KClassValue: " + argumentType);
                }
                irClassReferenceImpl = new IrClassReferenceImpl(startOffset, endOffset, toIrType(constantValue.getType(this.moduleDescriptor)), IrUtilsKt.referenceClassifier(this.symbolTable, this$0), toIrType(argumentType));
            }
            return irClassReferenceImpl;
        }
        if (constantValue instanceof ErrorValue) {
            return null;
        }
        throw new NotImplementedError("An operation is not implemented: " + ("Unexpected constant value: " + constantValue.getClass().getSimpleName() + StringUtil.SPACE + constantValue));
    }

    public static /* synthetic */ IrConstructorCall generateAnnotationConstructorCall$default(ConstantValueGenerator constantValueGenerator, AnnotationDescriptor annotationDescriptor, KotlinType kotlinType, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: generateAnnotationConstructorCall");
        }
        if ((i & 2) != 0) {
            kotlinType = null;
        }
        return constantValueGenerator.generateAnnotationConstructorCall(annotationDescriptor, kotlinType);
    }

    public final IrConstructorCall generateAnnotationConstructorCall(AnnotationDescriptor annotationDescriptor, KotlinType realType) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        KotlinType type = realType == null ? annotationDescriptor.getType() : realType;
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null || (classDescriptor instanceof NotFoundClasses.MockClassDescriptor)) {
            return null;
        }
        if (classDescriptor instanceof ErrorClassDescriptor) {
            if (!this.allowErrorTypeInAnnotations) {
                return null;
            }
        } else if (!DescriptorUtils.isAnnotationClass(classDescriptor)) {
            return null;
        }
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
            classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = (ClassConstructorDescriptor) kotlin.collections.CollectionsKt.singleOrNull(constructors);
            if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
                throw new AssertionError("No constructor for annotation class " + classDescriptor);
            }
        }
        IrConstructorSymbol irConstructorSymbolReferenceConstructor = this.symbolTable.getDescriptorExtension().referenceConstructor(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor);
        Pair<Integer, Integer> pairExtractAnnotationOffsets = extractAnnotationOffsets(annotationDescriptor);
        IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(pairExtractAnnotationOffsets.component1().intValue(), pairExtractAnnotationOffsets.component2().intValue(), toIrType(type), irConstructorSymbolReferenceConstructor, classDescriptor.getDeclaredTypeParameters().size(), 0, classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters().size(), null, annotationDescriptor.getSource(), 128, null);
        ClassConstructorDescriptor classConstructorDescriptorSubstitute = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.substitute(TypeConstructorSubstitution.INSTANCE.create(type).buildSubstitutor());
        if (classConstructorDescriptorSubstitute == null) {
            throw new IllegalStateException("Cannot substitute constructor".toString());
        }
        List<TypeProjection> arguments = type.getArguments();
        arguments.size();
        classDescriptor.getDeclaredTypeParameters().size();
        int size = arguments.size();
        for (int i = 0; i < size; i++) {
            KotlinType type2 = arguments.get(i).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            irConstructorCallImpl.putTypeArgument(i, toIrType(type2));
        }
        for (ValueParameterDescriptor valueParameterDescriptor : classConstructorDescriptorSubstitute.getValueParameters()) {
            int index = valueParameterDescriptor.getIndex();
            ConstantValue<?> constantValue = annotationDescriptor.getAllValueArguments().get(valueParameterDescriptor.getShortName());
            if (constantValue != null) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                ConstantValue<?> constantValueAdjustAnnotationArgumentValue = adjustAnnotationArgumentValue(constantValue, valueParameterDescriptor);
                Name name2 = valueParameterDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                Pair<Integer, Integer> pairExtractAnnotationParameterOffsets = extractAnnotationParameterOffsets(annotationDescriptor, name2);
                irConstructorCallImpl.putValueArgument(index, generateAnnotationValueAsExpression(pairExtractAnnotationParameterOffsets.component1().intValue(), pairExtractAnnotationParameterOffsets.component2().intValue(), constantValueAdjustAnnotationArgumentValue, valueParameterDescriptor));
            }
        }
        return irConstructorCallImpl;
    }

    private final ConstantValue<?> adjustAnnotationArgumentValue(ConstantValue<?> value, final ValueParameterDescriptor parameter) {
        return (!KotlinBuiltIns.isArrayOrPrimitiveArray(parameter.getType()) || (value instanceof ArrayValue)) ? value : new ArrayValue(kotlin.collections.CollectionsKt.listOf(value), new Function1<ModuleDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.ir.util.ConstantValueGenerator.adjustAnnotationArgumentValue.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(ModuleDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KotlinType type = parameter.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                return type;
            }
        });
    }

    private final KotlinType getArrayElementType(KotlinType kotlinType) {
        KotlinType arrayElementType = TypeUtilsKt.getBuiltIns(kotlinType).getArrayElementType(kotlinType);
        Intrinsics.checkNotNullExpressionValue(arrayElementType, "getArrayElementType(...)");
        return arrayElementType;
    }
}
