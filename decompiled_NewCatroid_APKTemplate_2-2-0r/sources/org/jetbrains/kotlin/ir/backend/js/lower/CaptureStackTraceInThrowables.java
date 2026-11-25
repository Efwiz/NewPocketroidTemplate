package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: CaptureStackTraceInThrowables.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\f\u0010\r\u001a\u00020\u000e*\u00020\u000fH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CaptureStackTraceInThrowables;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "addThrowableConstructorSlot", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CaptureStackTraceInThrowables implements BodyLoweringPass {
    private final JsIrBackendContext context;

    public CaptureStackTraceInThrowables(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        List<IrStatement> statements;
        int iNextIndex;
        IrRawFunctionReference irRawFunctionReferenceBuildRawReference;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (container instanceof IrConstructor) {
            IrConstructor irConstructor = (IrConstructor) container;
            if (irConstructor.getIsPrimary()) {
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(container);
                if (IrUtilsKt.isSubclassOf(parentAsClass, this.context.getIrBuiltIns().getThrowableClass().getOwner())) {
                    IrBlockBody irBlockBody = irBody instanceof IrBlockBody ? (IrBlockBody) irBody : null;
                    if (irBlockBody == null || (statements = irBlockBody.getStatements()) == null) {
                        return;
                    }
                    ListIterator<IrStatement> listIterator = statements.listIterator(statements.size());
                    while (true) {
                        if (!listIterator.hasPrevious()) {
                            iNextIndex = -1;
                            break;
                        } else if (listIterator.previous() instanceof IrDelegatingConstructorCall) {
                            iNextIndex = listIterator.nextIndex();
                            break;
                        }
                    }
                    int i = iNextIndex + 1;
                    IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIntrinsics().getCaptureStack(), null, null, null, null, 30, null);
                    IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
                    Intrinsics.checkNotNull(thisReceiver);
                    IrValueParameterSymbol symbol = thisReceiver.getSymbol();
                    if (!this.context.getEs6mode()) {
                        irRawFunctionReferenceBuildRawReference = JsIrBuilder.INSTANCE.buildRawReference(irConstructor.getSymbol(), this.context.getIrBuiltIns().getAnyType());
                    } else {
                        irRawFunctionReferenceBuildRawReference = JsIrBuilder.INSTANCE.buildGetField(addThrowableConstructorSlot(parentAsClass).getSymbol(), (124 & 2) != 0 ? null : JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, symbol, null, 2, null), (124 & 4) != 0 ? null : null, (124 & 8) == 0 ? null : null, (124 & 16) != 0 ? -1 : 0, (124 & 32) == 0 ? 0 : -1, (124 & 64) != 0 ? JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE : null);
                    }
                    irCallBuildCall$default.putValueArgument(0, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, symbol, null, 2, null));
                    irCallBuildCall$default.putValueArgument(1, irRawFunctionReferenceBuildRawReference);
                    Unit unit = Unit.INSTANCE;
                    statements.add(i, irCallBuildCall$default);
                }
            }
        }
    }

    private final IrField addThrowableConstructorSlot(IrClass irClass) {
        IrFactory factory = irClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setType(this.context.getDynamicType());
        irFieldBuilder.setFinal(false);
        irFieldBuilder.setExternal(false);
        irFieldBuilder.setStatic(false);
        irFieldBuilder.setMetadata(null);
        Name nameIdentifier = Name.identifier(Namer.INSTANCE.getTHROWABLE_CONSTRUCTOR());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier);
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFieldBuilder.setVisibility(PRIVATE);
        irFieldBuilder.setOrigin(ES6_THROWABLE_CONSTRUCTOR_SLOT.INSTANCE);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        irClass.getDeclarations().add(irFieldBuildField);
        return irFieldBuildField;
    }
}
