package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: CompileTimeConstant.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001&B/\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u001a\u001a\u00020\t2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\b\u0010 \u001a\u00020!H\u0016J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00020#2\u0006\u0010\u001f\u001a\u00020\u001eH\u0016J\b\u0010$\u001a\u00020%H\u0016R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant;", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "", "value", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "newInferenceEnabled", "", "convertedFromSigned", "(Ljava/lang/Number;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;ZZ)V", "getConvertedFromSigned", "()Z", "hasIntegerLiteralType", "getHasIntegerLiteralType", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getParameters", "()Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "unknownIntegerType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getUnknownIntegerType", "()Lorg/jetbrains/kotlin/types/SimpleType;", Namer.EQUALS_METHOD_NAME, "other", "", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expectedType", "hashCode", "", "toConstantValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "toString", "", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntegerValueTypeConstant implements CompileTimeConstant<Number> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean convertedFromSigned;
    private final ModuleDescriptor moduleDescriptor;
    private final boolean newInferenceEnabled;
    private final CompileTimeConstant.Parameters parameters;
    private final TypeConstructor typeConstructor;
    private final SimpleType unknownIntegerType;
    private final Number value;

    @JvmStatic
    public static final IntegerValueTypeConstant convertToUnsignedConstant(IntegerValueTypeConstant integerValueTypeConstant, ModuleDescriptor moduleDescriptor) {
        return INSTANCE.convertToUnsignedConstant(integerValueTypeConstant, moduleDescriptor);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getHasIntegerLiteralType() {
        return true;
    }

    public IntegerValueTypeConstant(Number value, ModuleDescriptor moduleDescriptor, CompileTimeConstant.Parameters parameters, boolean z, boolean z2) {
        IntegerValueTypeConstructor integerValueTypeConstructor;
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.value = value;
        this.moduleDescriptor = moduleDescriptor;
        this.parameters = parameters;
        this.newInferenceEnabled = z;
        this.convertedFromSigned = z2;
        if (z) {
            integerValueTypeConstructor = new IntegerLiteralTypeConstructor(value.longValue(), getModuleDescriptor(), getParameters());
        } else {
            integerValueTypeConstructor = new IntegerValueTypeConstructor(value.longValue(), getModuleDescriptor(), getParameters());
        }
        this.typeConstructor = integerValueTypeConstructor;
        this.unknownIntegerType = KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(TypeAttributes.INSTANCE.getEmpty(), integerValueTypeConstructor, CollectionsKt.emptyList(), false, ErrorUtils.createErrorScope(ErrorScopeKind.INTEGER_LITERAL_TYPE_SCOPE, true, integerValueTypeConstructor.toString()));
    }

    public /* synthetic */ IntegerValueTypeConstant(Number number, ModuleDescriptor moduleDescriptor, CompileTimeConstant.Parameters parameters, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(number, moduleDescriptor, parameters, z, (i & 16) != 0 ? false : z2);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getCanBeUsedInAnnotations() {
        return CompileTimeConstant.DefaultImpls.getCanBeUsedInAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getUsesNonConstValAsConstant() {
        return CompileTimeConstant.DefaultImpls.getUsesNonConstValAsConstant(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getUsesVariableAsConstant() {
        return CompileTimeConstant.DefaultImpls.getUsesVariableAsConstant(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public Number getValue(KotlinType kotlinType) {
        return (Number) CompileTimeConstant.DefaultImpls.getValue(this, kotlinType);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isError() {
        return CompileTimeConstant.DefaultImpls.isError(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isPure() {
        return CompileTimeConstant.DefaultImpls.isPure(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isUnsignedNumberLiteral() {
        return CompileTimeConstant.DefaultImpls.isUnsignedNumberLiteral(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public CompileTimeConstant.Parameters getParameters() {
        return this.parameters;
    }

    public final boolean getConvertedFromSigned() {
        return this.convertedFromSigned;
    }

    /* JADX INFO: compiled from: CompileTimeConstant.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0014\u0010\u0007\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant$Companion;", "", "()V", "convertToSignedConstant", "Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "convertToUnsignedConstant", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final IntegerValueTypeConstant convertToUnsignedConstant(IntegerValueTypeConstant integerValueTypeConstant, ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(integerValueTypeConstant, "<this>");
            Intrinsics.checkNotNullParameter(module, "module");
            return new IntegerValueTypeConstant(integerValueTypeConstant.value, module, new CompileTimeConstant.Parameters(integerValueTypeConstant.getParameters().getCanBeUsedInAnnotation(), integerValueTypeConstant.getParameters().isPure(), true, integerValueTypeConstant.getParameters().isUnsignedLongNumberLiteral(), integerValueTypeConstant.getParameters().getUsesVariableAsConstant(), integerValueTypeConstant.getParameters().getUsesNonConstValAsConstant(), integerValueTypeConstant.getParameters().isConvertableConstVal()), integerValueTypeConstant.newInferenceEnabled, true);
        }

        public final IntegerValueTypeConstant convertToSignedConstant(IntegerValueTypeConstant integerValueTypeConstant, ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(integerValueTypeConstant, "<this>");
            Intrinsics.checkNotNullParameter(module, "module");
            return new IntegerValueTypeConstant(integerValueTypeConstant.value, module, new CompileTimeConstant.Parameters(integerValueTypeConstant.getParameters().getCanBeUsedInAnnotation(), integerValueTypeConstant.getParameters().isPure(), false, integerValueTypeConstant.getParameters().isUnsignedLongNumberLiteral(), integerValueTypeConstant.getParameters().getUsesVariableAsConstant(), integerValueTypeConstant.getParameters().getUsesNonConstValAsConstant(), integerValueTypeConstant.getParameters().isConvertableConstVal()), integerValueTypeConstant.newInferenceEnabled, true);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public ConstantValue<Number> toConstantValue(KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        KotlinType type = getType(expectedType);
        if (KotlinBuiltIns.isInt(type)) {
            return new IntValue(this.value.intValue());
        }
        if (KotlinBuiltIns.isByte(type)) {
            return new ByteValue(this.value.byteValue());
        }
        if (KotlinBuiltIns.isShort(type)) {
            return new ShortValue(this.value.shortValue());
        }
        if (KotlinBuiltIns.isLong(type)) {
            return new LongValue(this.value.longValue());
        }
        if (KotlinBuiltIns.isUInt(type)) {
            return new UIntValue(this.value.intValue());
        }
        if (KotlinBuiltIns.isUByte(type)) {
            return new UByteValue(this.value.byteValue());
        }
        if (KotlinBuiltIns.isUShort(type)) {
            return new UShortValue(this.value.shortValue());
        }
        return KotlinBuiltIns.isULong(type) ? new ULongValue(this.value.longValue()) : new LongValue(this.value.longValue());
    }

    public final SimpleType getUnknownIntegerType() {
        return this.unknownIntegerType;
    }

    public final KotlinType getType(KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (this.newInferenceEnabled) {
            TypeConstructor typeConstructor = this.typeConstructor;
            Intrinsics.checkNotNull(typeConstructor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor");
            KotlinType primitiveNumberType = TypeUtils.getPrimitiveNumberType((IntegerLiteralTypeConstructor) typeConstructor, expectedType);
            Intrinsics.checkNotNull(primitiveNumberType);
            return primitiveNumberType;
        }
        TypeConstructor typeConstructor2 = this.typeConstructor;
        Intrinsics.checkNotNull(typeConstructor2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstructor");
        KotlinType primitiveNumberType2 = TypeUtils.getPrimitiveNumberType((IntegerValueTypeConstructor) typeConstructor2, expectedType);
        Intrinsics.checkNotNull(primitiveNumberType2);
        return primitiveNumberType2;
    }

    public String toString() {
        return this.typeConstructor.toString();
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean equals(Object other) {
        if (other instanceof IntegerValueTypeConstant) {
            IntegerValueTypeConstant integerValueTypeConstant = (IntegerValueTypeConstant) other;
            if (Intrinsics.areEqual(this.value, integerValueTypeConstant.value) && Intrinsics.areEqual(getParameters(), integerValueTypeConstant.getParameters())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public int hashCode() {
        return (this.value.hashCode() * 31) + getParameters().hashCode();
    }
}
