package org.jetbrains.kotlin.backend.jvm.mapping;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IrTypeMapping.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u000e\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000\u001a \u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u0003\u001a\u0012\u0010\u000e\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u0003\u001a\u001a\u0010\u000f\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010\u0014\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016\u001a\u0012\u0010\u0014\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0012\u0010\u0014\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001a\u001a\u0012\u0010\u001b\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u001a\u0010\u001c\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013¨\u0006\u001d"}, d2 = {"getJvmShortName", "", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "buildPossiblyInnerType", "Lorg/jetbrains/kotlin/backend/jvm/mapping/PossiblyInnerIrType;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "classifier", "index", "", "mapClass", "Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "irClass", "mapOwner", "mapSupertype", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "sw", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", "mapType", "irField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "irVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "mapTypeAsDeclaration", "mapTypeParameter", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeMappingKt {
    public static final Type mapType(IrTypeMapper irTypeMapper, IrVariable irVariable) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irVariable, "irVariable");
        return IrTypeMapper.mapType$default(irTypeMapper, irVariable.getType(), null, null, false, 14, null);
    }

    public static final Type mapType(IrTypeMapper irTypeMapper, IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irValueParameter, "irValueParameter");
        return IrTypeMapper.mapType$default(irTypeMapper, irValueParameter.getType(), null, null, false, 14, null);
    }

    public static final Type mapType(IrTypeMapper irTypeMapper, IrField irField) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irField, "irField");
        return IrTypeMapper.mapType$default(irTypeMapper, irField.getType(), null, null, false, 14, null);
    }

    public static final Type mapSupertype(IrTypeMapper irTypeMapper, IrType irType, JvmSignatureWriter sw) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(sw, "sw");
        return IrTypeMapper.mapType$default(irTypeMapper, irType, TypeMappingMode.SUPER_TYPE, sw, false, 8, null);
    }

    public static final Type mapClass(IrTypeMapper irTypeMapper, IrClass irClass) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return IrTypeMapper.mapType$default(irTypeMapper, IrUtilsKt.getDefaultType(irClass), TypeMappingMode.CLASS_DECLARATION, null, false, 12, null);
    }

    public static final Type mapOwner(IrTypeMapper irTypeMapper, IrClass irClass) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return IrTypeMapper.mapType$default(irTypeMapper, IrUtilsKt.getDefaultType(irClass), TypeMappingMode.GENERIC_ARGUMENT, null, false, 12, null);
    }

    public static final Type mapTypeAsDeclaration(IrTypeMapper irTypeMapper, IrType irType) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        return IrTypeMapper.mapType$default(irTypeMapper, irType, TypeMappingMode.CLASS_DECLARATION, null, false, 12, null);
    }

    public static final Type mapTypeParameter(IrTypeMapper irTypeMapper, IrType irType, JvmSignatureWriter sw) {
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(sw, "sw");
        return IrTypeMapper.mapType$default(irTypeMapper, irType, TypeMappingMode.GENERIC_ARGUMENT, sw, false, 8, null);
    }

    public static final String getJvmShortName(IrClass klass) {
        FqNameUnsafe unsafe;
        Name shortClassName;
        Intrinsics.checkNotNullParameter(klass, "klass");
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(klass);
        if (fqNameWhenAvailable != null && (unsafe = fqNameWhenAvailable.toUnsafe()) != null) {
            ClassId classIdMapKotlinToJava = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(unsafe);
            String strAsString = (classIdMapKotlinToJava == null || (shortClassName = classIdMapKotlinToJava.getShortClassName()) == null) ? null : shortClassName.asString();
            if (strAsString != null) {
                return strAsString;
            }
        }
        String identifier = SpecialNames.safeIdentifier(klass.getName()).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return identifier;
    }

    public static final PossiblyInnerIrType buildPossiblyInnerType(IrSimpleType irSimpleType) {
        Intrinsics.checkNotNullParameter(irSimpleType, "<this>");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irSimpleType);
        return buildPossiblyInnerType(irSimpleType, classOrNull != null ? classOrNull.getOwner() : null, 0);
    }

    private static final PossiblyInnerIrType buildPossiblyInnerType(IrSimpleType irSimpleType, IrClass irClass, int i) {
        if (irClass == null) {
            return null;
        }
        int size = irClass.getTypeParameters().size() + i;
        if (!irClass.getIsInner()) {
            if (size != irSimpleType.getArguments().size()) {
                Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.LOCAL);
            }
            return new PossiblyInnerIrType(irClass, irSimpleType.getArguments().subList(i, irSimpleType.getArguments().size()), null);
        }
        return new PossiblyInnerIrType(irClass, irSimpleType.getArguments().subList(i, size), buildPossiblyInnerType(irSimpleType, IrUtilsKt.getParentAsClass(irClass), size));
    }
}
