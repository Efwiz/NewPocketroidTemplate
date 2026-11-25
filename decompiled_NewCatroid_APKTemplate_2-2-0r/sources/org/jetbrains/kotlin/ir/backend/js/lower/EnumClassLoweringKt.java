package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0004H\u0002\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0005H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0013"}, d2 = {"ENTRIES_FIELD_NAME", "", "enumEntries", "", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getEnumEntries", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/util/List;", "createEntryAccessorName", "enumName", "enumEntry", "fixReferencesToConstructorParameters", "", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "irClass", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EnumClassLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String createEntryAccessorName(String str, IrEnumEntry irEnumEntry) {
        return str + '_' + irEnumEntry.getName().getIdentifier() + Namer.OBJECT_INSTANCE_FUNCTION_SUFFIX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleType getType(IrEnumEntry irEnumEntry, IrClass irClass) {
        IrClass correspondingClass = irEnumEntry.getCorrespondingClass();
        if (correspondingClass != null) {
            irClass = correspondingClass;
        }
        return IrUtilsKt.getDefaultType(irClass);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fixReferencesToConstructorParameters(JsCommonBackendContext jsCommonBackendContext, IrClass irClass, IrBody irBody) {
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid(irClass) { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumClassLoweringKt.fixReferencesToConstructorParameters.1
            private final DeclarationIrBuilder builder;

            {
                this.builder = LowerUtilsKt.createIrBuilder$default(this.$this_fixReferencesToConstructorParameters, irClass.getSymbol(), 0, 0, 6, (Object) null);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrValueParameter irValueParameter = this.$this_fixReferencesToConstructorParameters.getMapping().getEnumConstructorOldToNewValueParameters().get(expression.getSymbol().getOwner());
                return irValueParameter != null ? ExpressionHelpersKt.irGet(this.builder, irValueParameter) : super.visitGetValue(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitSetValue(IrSetValue expression) {
                IrSetValueImpl irSetValueImplIrSet$default;
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                IrValueParameter irValueParameter = this.$this_fixReferencesToConstructorParameters.getMapping().getEnumConstructorOldToNewValueParameters().get(expression.getSymbol().getOwner());
                if (irValueParameter != null && (irSetValueImplIrSet$default = ExpressionHelpersKt.irSet$default(this.builder, irValueParameter.getSymbol(), expression.getValue(), (IrStatementOrigin) null, 4, (Object) null)) != null) {
                    return irSetValueImplIrSet$default;
                }
                return expression;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<IrEnumEntry> getEnumEntries(IrClass irClass) {
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrEnumEntry) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
