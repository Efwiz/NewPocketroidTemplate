package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.ErrorDeclarationLowering;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JsErrorExpressionLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsErrorDeclarationLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/ErrorDeclarationLowering;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "errorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "nothingType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "stringType", "transformErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsErrorDeclarationLowering extends ErrorDeclarationLowering {
    private final IrSimpleFunctionSymbol errorSymbol;
    private final IrFactory irFactory;
    private final IrType nothingType;
    private final IrType stringType;

    public JsErrorDeclarationLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.nothingType = context.getIrBuiltIns().getNothingType();
        this.stringType = context.getIrBuiltIns().getStringType();
        this.errorSymbol = context.getErrorCodeSymbol();
        this.irFactory = context.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.ErrorDeclarationLowering
    public IrDeclaration transformErrorDeclaration(IrErrorDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.errorSymbol == null) {
            throw new IllegalArgumentException("Should be non-null if errors are allowed".toString());
        }
        IrFactory irFactory = this.irFactory;
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom((IrDeclaration) declaration);
        irFunctionBuilder.setReturnType(this.nothingType);
        Name nameIdentifier = Name.identifier("$errorDeclaration");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(declaration.getParent());
        irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.irFactory, irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.JsErrorDeclarationLowering$transformErrorDeclaration$3$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                List<IrStatement> statements = createBlockBody.getStatements();
                IrCallImpl irCallImpl = new IrCallImpl(createBlockBody.getStartOffset(), createBlockBody.getEndOffset(), this.this$0.nothingType, this.this$0.errorSymbol, 0, 1, null, null);
                irCallImpl.putValueArgument(0, IrConstImpl.INSTANCE.string(irCallImpl.getStartOffset(), irCallImpl.getEndOffset(), this.this$0.stringType, "ERROR DECLARATION"));
                statements.add(irCallImpl);
            }
        }));
        return irSimpleFunctionBuildFunction;
    }
}
