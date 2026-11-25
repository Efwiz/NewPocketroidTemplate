package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbi;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: PromisedValue.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0017\u001a\u00020\u0018H&J \u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getCodegen", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "getIrType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "getMv", "()Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "getTypeMapper", "()Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "discard", "", "materializeAt", "target", "irTarget", "castForReified", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PromisedValue {
    private final ExpressionCodegen codegen;
    private final IrType irType;
    private final Type type;

    public abstract void discard();

    public PromisedValue(ExpressionCodegen codegen, Type type, IrType irType) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(irType, "irType");
        this.codegen = codegen;
        this.type = type;
        this.irType = irType;
    }

    public final ExpressionCodegen getCodegen() {
        return this.codegen;
    }

    public final IrType getIrType() {
        return this.irType;
    }

    public final Type getType() {
        return this.type;
    }

    public void materializeAt(Type target, IrType irTarget, boolean castForReified) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        IrType irTypeEraseTypeParameters = JvmIrTypeUtilsKt.eraseTypeParameters(this.irType);
        IrType irTypeEraseTypeParameters2 = JvmIrTypeUtilsKt.eraseTypeParameters(irTarget);
        IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(irTypeEraseTypeParameters);
        boolean zAreEqual = Intrinsics.areEqual(irTypeUnboxType != null ? IrTypeMapper.mapType$default(getTypeMapper(), irTypeUnboxType, null, null, false, 14, null) : null, this.type);
        IrType irTypeUnboxType2 = InlineClassAbi.INSTANCE.unboxType(irTypeEraseTypeParameters2);
        boolean zAreEqual2 = Intrinsics.areEqual(irTypeUnboxType2 != null ? IrTypeMapper.mapType$default(getTypeMapper(), irTypeUnboxType2, null, null, false, 14, null) : null, target);
        if (zAreEqual && !zAreEqual2) {
            Type typeMapType$default = IrTypeMapper.mapType$default(getTypeMapper(), irTypeEraseTypeParameters, TypeMappingMode.CLASS_DECLARATION, null, false, 12, null);
            StackValue.boxInlineClass(this.type, typeMapType$default, IrTypeUtilsKt.isNullable(irTypeEraseTypeParameters), getMv());
            if (!Intrinsics.areEqual(IrTypeMapper.mapType$default(getTypeMapper(), irTypeEraseTypeParameters2, null, null, false, 14, null), target) || IrTypeUtilsKt.isSubtypeOf(irTypeEraseTypeParameters, irTypeEraseTypeParameters2, this.codegen.getContext().getTypeSystem())) {
                return;
            }
            StackValue.coerce(typeMapType$default, target, getMv(), false);
            return;
        }
        if (!zAreEqual && zAreEqual2) {
            Type typeMapType$default2 = IrTypeMapper.mapType$default(getTypeMapper(), irTypeEraseTypeParameters2, TypeMappingMode.CLASS_DECLARATION, null, false, 12, null);
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(this.codegen.getIrFunction());
            if (IrDeclarationsKt.isSingleFieldValueClass(parentAsClass) && Intrinsics.areEqual(parentAsClass.getSymbol(), IrTypesKt.getClassifierOrNull(this.irType)) && !IrTypeUtilsKt.isNullable(this.irType)) {
                this.codegen.getMv().getfield(typeMapType$default2.getInternalName(), InlineClassAbiKt.getInlineClassFieldName(parentAsClass).asString(), target.getDescriptor());
                return;
            } else {
                StackValue.unboxInlineClass(this.type, typeMapType$default2, target, IrTypeUtilsKt.isNullable(irTypeEraseTypeParameters2), getMv());
                return;
            }
        }
        if (!Intrinsics.areEqual(this.type, target) || (castForReified && PromisedValueKt.anyTypeArgument(this.irType, new Function1<IrTypeParameter, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue.materializeAt.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrTypeParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getIsReified());
            }
        }))) {
            StackValue.coerce(this.type, target, getMv(), Intrinsics.areEqual(this.type, target));
        }
    }

    public final InstructionAdapter getMv() {
        return this.codegen.getMv();
    }

    public final IrTypeMapper getTypeMapper() {
        return this.codegen.getTypeMapper();
    }
}
