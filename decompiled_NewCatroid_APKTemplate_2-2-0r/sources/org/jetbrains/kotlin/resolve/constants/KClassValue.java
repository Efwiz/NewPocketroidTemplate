package org.jetbrains.kotlin.resolve.constants;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0019\u001aB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u000bJ=\u0010\f\u001a\n \u000e*\u0004\u0018\u0001H\rH\r\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/KClassValue;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value;", "value", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "(Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;)V", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "arrayDimensions", "", "(Lorg/jetbrains/kotlin/name/ClassId;I)V", "(Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value;)V", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getArgumentType", "Lorg/jetbrains/kotlin/types/KotlinType;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getType", "Companion", "Value", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KClassValue extends ConstantValue<Value> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: constantValues.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value;", "", "()V", "LocalClass", "NormalClass", "Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value$LocalClass;", "Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value$NormalClass;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Value {
        public /* synthetic */ Value(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: constantValues.kt */
        @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value$NormalClass;", "Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value;", "value", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "(Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;)V", "arrayDimensions", "", "getArrayDimensions", "()I", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getValue", "()Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class NormalClass extends Value {
            private final ClassLiteralValue value;

            public static /* synthetic */ NormalClass copy$default(NormalClass normalClass, ClassLiteralValue classLiteralValue, int i, Object obj) {
                if ((i & 1) != 0) {
                    classLiteralValue = normalClass.value;
                }
                return normalClass.copy(classLiteralValue);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final ClassLiteralValue getValue() {
                return this.value;
            }

            public final NormalClass copy(ClassLiteralValue value) {
                Intrinsics.checkNotNullParameter(value, "value");
                return new NormalClass(value);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof NormalClass) && Intrinsics.areEqual(this.value, ((NormalClass) other).value);
            }

            public int hashCode() {
                return this.value.hashCode();
            }

            public String toString() {
                return "NormalClass(value=" + this.value + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public NormalClass(ClassLiteralValue value) {
                super(null);
                Intrinsics.checkNotNullParameter(value, "value");
                this.value = value;
            }

            public final ClassLiteralValue getValue() {
                return this.value;
            }

            public final ClassId getClassId() {
                return this.value.getClassId();
            }

            public final int getArrayDimensions() {
                return this.value.getArrayNestedness();
            }
        }

        private Value() {
        }

        /* JADX INFO: compiled from: constantValues.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value$LocalClass;", "Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Value;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class LocalClass extends Value {
            private final KotlinType type;

            public static /* synthetic */ LocalClass copy$default(LocalClass localClass, KotlinType kotlinType, int i, Object obj) {
                if ((i & 1) != 0) {
                    kotlinType = localClass.type;
                }
                return localClass.copy(kotlinType);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final KotlinType getType() {
                return this.type;
            }

            public final LocalClass copy(KotlinType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return new LocalClass(type);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof LocalClass) && Intrinsics.areEqual(this.type, ((LocalClass) other).type);
            }

            public int hashCode() {
                return this.type.hashCode();
            }

            public String toString() {
                return "LocalClass(type=" + this.type + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public LocalClass(KotlinType type) {
                super(null);
                Intrinsics.checkNotNullParameter(type, "type");
                this.type = type;
            }

            public final KotlinType getType() {
                return this.type;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KClassValue(Value value) {
        super(value);
        Intrinsics.checkNotNullParameter(value, "value");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KClassValue(ClassLiteralValue value) {
        this(new Value.NormalClass(value));
        Intrinsics.checkNotNullParameter(value, "value");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KClassValue(ClassId classId, int i) {
        this(new ClassLiteralValue(classId, i));
        Intrinsics.checkNotNullParameter(classId, "classId");
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public KotlinType getType(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
        ClassDescriptor kClass = module.getBuiltIns().getKClass();
        Intrinsics.checkNotNullExpressionValue(kClass, "getKClass(...)");
        return KotlinTypeFactory.simpleNotNullType(empty, kClass, CollectionsKt.listOf(new TypeProjectionImpl(getArgumentType(module))));
    }

    public final KotlinType getArgumentType(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        Value value = getValue();
        if (value instanceof Value.LocalClass) {
            return ((Value.LocalClass) getValue()).getType();
        }
        if (!(value instanceof Value.NormalClass)) {
            throw new NoWhenBranchMatchedException();
        }
        ClassLiteralValue value2 = ((Value.NormalClass) getValue()).getValue();
        ClassId classId = value2.getClassId();
        int arrayNestedness = value2.getArrayNestedness();
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, classId);
        if (classDescriptorFindClassAcrossModuleDependencies == null) {
            ErrorTypeKind errorTypeKind = ErrorTypeKind.UNRESOLVED_KCLASS_CONSTANT_VALUE;
            String string = classId.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return ErrorUtils.createErrorType(errorTypeKind, string, String.valueOf(arrayNestedness));
        }
        SimpleType defaultType = classDescriptorFindClassAcrossModuleDependencies.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        KotlinType kotlinTypeReplaceArgumentsWithStarProjections = TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType);
        for (int i = 0; i < arrayNestedness; i++) {
            kotlinTypeReplaceArgumentsWithStarProjections = module.getBuiltIns().getArrayType(Variance.INVARIANT, kotlinTypeReplaceArgumentsWithStarProjections);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeReplaceArgumentsWithStarProjections, "getArrayType(...)");
        }
        return kotlinTypeReplaceArgumentsWithStarProjections;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitKClassValue(this, data);
    }

    /* JADX INFO: compiled from: constantValues.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/KClassValue$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "argumentType", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ConstantValue<?> create(KotlinType argumentType) {
            Intrinsics.checkNotNullParameter(argumentType, "argumentType");
            if (KotlinTypeKt.isError(argumentType)) {
                return null;
            }
            KotlinType type = argumentType;
            int i = 0;
            while (KotlinBuiltIns.isArray(type)) {
                type = ((TypeProjection) CollectionsKt.single((List) type.getArguments())).getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                i++;
            }
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
            if (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) {
                ClassId classId = DescriptorUtilsKt.getClassId(classifierDescriptorMo7434getDeclarationDescriptor);
                return classId == null ? new KClassValue(new Value.LocalClass(argumentType)) : new KClassValue(classId, i);
            }
            if (!(classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor)) {
                return null;
            }
            ClassId classId2 = ClassId.topLevel(StandardNames.FqNames.any.toSafe());
            Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
            return new KClassValue(classId2, 0);
        }
    }
}
