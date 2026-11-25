package org.jetbrains.kotlin.resolve.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: IntegerValueTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0002J\u0018\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000bH\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0019H\u0016J\u0006\u0010\u001a\u001a\u00020\u0003J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 H\u0017J\b\u0010!\u001a\u00020\"H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstructor;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "value", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "(JLorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;)V", "supertypes", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/types/KotlinType;", "addSignedSuperTypes", "", "addUnsignedSuperTypes", "checkBoundsAndAddSuperType", "kotlinType", "getBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getDeclarationDescriptor", "", "getParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getSupertypes", "", "getValue", "isDenotable", "", "isFinal", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntegerValueTypeConstructor implements TypeConstructor {
    private final ModuleDescriptor module;
    private final ArrayList<KotlinType> supertypes;
    private final long value;

    public Void getDeclarationDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isDenotable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isFinal() {
        return false;
    }

    public IntegerValueTypeConstructor(long j, ModuleDescriptor module, CompileTimeConstant.Parameters parameters) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.value = j;
        this.module = module;
        this.supertypes = new ArrayList<>(4);
        boolean zIsUnsignedNumberLiteral = parameters.isUnsignedNumberLiteral();
        boolean zIsConvertableConstVal = parameters.isConvertableConstVal();
        if (zIsUnsignedNumberLiteral || zIsConvertableConstVal) {
            CompileTimeConstantKt.hasUnsignedTypesInModuleDependencies(module);
        }
        if (zIsConvertableConstVal) {
            addSignedSuperTypes();
            addUnsignedSuperTypes();
        } else if (zIsUnsignedNumberLiteral) {
            addUnsignedSuperTypes();
        } else {
            addSignedSuperTypes();
        }
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public /* bridge */ /* synthetic */ ClassifierDescriptor getThis$0() {
        return (ClassifierDescriptor) getDeclarationDescriptor();
    }

    private final void addSignedSuperTypes() {
        long j = this.value;
        SimpleType intType = getBuiltIns().getIntType();
        Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
        checkBoundsAndAddSuperType(j, intType);
        long j2 = this.value;
        SimpleType byteType = getBuiltIns().getByteType();
        Intrinsics.checkNotNullExpressionValue(byteType, "getByteType(...)");
        checkBoundsAndAddSuperType(j2, byteType);
        long j3 = this.value;
        SimpleType shortType = getBuiltIns().getShortType();
        Intrinsics.checkNotNullExpressionValue(shortType, "getShortType(...)");
        checkBoundsAndAddSuperType(j3, shortType);
        this.supertypes.add(getBuiltIns().getLongType());
    }

    private final void addUnsignedSuperTypes() {
        checkBoundsAndAddSuperType(this.value, PrimitiveTypeUtilKt.unsignedType(this.module, StandardNames.FqNames.uInt));
        checkBoundsAndAddSuperType(this.value, PrimitiveTypeUtilKt.unsignedType(this.module, StandardNames.FqNames.uByte));
        checkBoundsAndAddSuperType(this.value, PrimitiveTypeUtilKt.unsignedType(this.module, StandardNames.FqNames.uShort));
        this.supertypes.add(PrimitiveTypeUtilKt.unsignedType(this.module, StandardNames.FqNames.uLong));
    }

    private final void checkBoundsAndAddSuperType(long value, KotlinType kotlinType) {
        long jMinValue = PrimitiveTypeUtilKt.minValue(kotlinType);
        if (value > PrimitiveTypeUtilKt.maxValue(kotlinType) || jMinValue > value) {
            return;
        }
        this.supertypes.add(kotlinType);
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public Collection<KotlinType> mo7438getSupertypes() {
        return this.supertypes;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    public final long getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        return this.module.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    public String toString() {
        return "IntegerValueType(" + this.value + ')';
    }
}
