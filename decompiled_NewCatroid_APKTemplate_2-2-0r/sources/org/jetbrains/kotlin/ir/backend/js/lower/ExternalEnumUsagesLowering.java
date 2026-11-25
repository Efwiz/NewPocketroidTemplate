package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: ExternalEnumUsagesLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ExternalEnumUsagesLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "classAsReceiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "createFieldForEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "entry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "lowerExternalEnumEntry", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetFieldImpl;", "enumEntry", "klass", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExternalEnumUsagesLowering implements BodyLoweringPass {
    private final JsIrBackendContext context;

    public ExternalEnumUsagesLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ExternalEnumUsagesLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetEnumValue(IrGetEnumValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrEnumEntry owner = expression.getSymbol().getOwner();
                IrDeclarationParent parent = owner.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                IrClass irClass = (IrClass) parent;
                IrExpression irExpressionLowerExternalEnumEntry = expression;
                if (irClass.getIsExternal()) {
                    irExpressionLowerExternalEnumEntry = ExternalEnumUsagesLowering.this.lowerExternalEnumEntry(owner, irClass);
                }
                return irExpressionLowerExternalEnumEntry;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrGetFieldImpl lowerExternalEnumEntry(final IrEnumEntry enumEntry, final IrClass klass) {
        return JsIrBuilder.INSTANCE.buildGetField(((IrField) MappingsKt.getOrPut(this.context.getMapping().getEnumEntryToInstanceField(), enumEntry, new Function0<IrField>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ExternalEnumUsagesLowering.lowerExternalEnumEntry.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                return ExternalEnumUsagesLowering.this.createFieldForEntry(enumEntry, klass);
            }
        })).getSymbol(), (124 & 2) != 0 ? null : classAsReceiver(klass), (124 & 4) != 0 ? null : null, (124 & 8) == 0 ? IrUtilsKt.getDefaultType(klass) : null, (124 & 16) != 0 ? -1 : 0, (124 & 32) == 0 ? 0 : -1, (124 & 64) != 0 ? JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE : null);
    }

    private final IrExpression classAsReceiver(IrClass irClass) {
        return JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIntrinsics().getJsClass(), this.context.getIrBuiltIns().getAnyType(), CollectionsKt.listOf(IrUtilsKt.getDefaultType(irClass)), null, null, 24, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrField createFieldForEntry(IrEnumEntry entry, IrClass irClass) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setStartOffset(entry.getStartOffset());
        irFieldBuilder.setEndOffset(entry.getEndOffset());
        irFieldBuilder.setOrigin(entry.getOrigin());
        irFieldBuilder.setName(entry.getName());
        irFieldBuilder.setType(IrUtilsKt.getDefaultType(irClass));
        irFieldBuilder.setFinal(false);
        irFieldBuilder.setExternal(true);
        irFieldBuilder.setStatic(true);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        irClass.getDeclarations().add(irFieldBuildField);
        return irFieldBuildField;
    }
}
