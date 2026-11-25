package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: PromisedValue.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "discard", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MaterialValue extends PromisedValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MaterialValue(ExpressionCodegen codegen, Type type, IrType irType) {
        super(codegen, type, irType);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(irType, "irType");
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void discard() {
        if (getType() != Type.VOID_TYPE) {
            AsmUtil.pop(getMv(), getType());
        }
    }
}
