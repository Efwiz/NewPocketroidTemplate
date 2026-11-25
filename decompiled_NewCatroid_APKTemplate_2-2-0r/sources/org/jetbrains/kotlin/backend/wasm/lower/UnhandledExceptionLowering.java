package org.jetbrains.kotlin.backend.wasm.lower;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.DeclarationGeneratorKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrThrowImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: UnhandledExceptionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/UnhandledExceptionLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "irBooleanType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isNotFirstWasmExportCallGetter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isNotFirstWasmExportCallSetter", "throwAsJsException", "getThrowAsJsException", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "throwableType", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "processExportFunction", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnhandledExceptionLowering implements FileLoweringPass {
    private final WasmBackendContext context;
    private final IrType irBooleanType;
    private final IrSimpleFunctionSymbol isNotFirstWasmExportCallGetter;
    private final IrSimpleFunctionSymbol isNotFirstWasmExportCallSetter;
    private final IrType throwableType;

    public UnhandledExceptionLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.throwableType = context.getIrBuiltIns().getThrowableType();
        this.irBooleanType = context.getWasmSymbols().getIrBuiltIns().getBooleanType();
        IrSimpleFunction getter = context.getWasmSymbols().getIsNotFirstWasmExportCall().getOwner().getGetter();
        Intrinsics.checkNotNull(getter);
        this.isNotFirstWasmExportCallGetter = getter.getSymbol();
        IrSimpleFunction setter = context.getWasmSymbols().getIsNotFirstWasmExportCall().getOwner().getSetter();
        Intrinsics.checkNotNull(setter);
        this.isNotFirstWasmExportCallSetter = setter.getSymbol();
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    private final IrSimpleFunctionSymbol getThrowAsJsException() {
        return this.context.getWasmSymbols().getJsRelatedSymbols().getThrowAsJsException();
    }

    private final void processExportFunction(IrFunction irFunction) {
        IrType unitType;
        IrBody body = irFunction.getBody();
        if (body == null) {
            return;
        }
        boolean z = body instanceof IrBlockBody;
        if (z && ((IrBlockBody) body).getStatements().isEmpty()) {
            return;
        }
        boolean z2 = body instanceof IrExpressionBody;
        if (z2) {
            unitType = ((IrExpressionBody) body).getExpression().getType();
        } else if (z) {
            unitType = this.context.getIrBuiltIns().getUnitType();
        } else {
            String qualifiedName = Reflection.getOrCreateKotlinClass(getClass()).getQualifiedName();
            Intrinsics.checkNotNull(qualifiedName);
            throw new NotImplementedError("An operation is not implemented: " + qualifiedName);
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrFunction irFunction2 = irFunction;
        IrDeclarationOrigin.IR_TEMPORARY_VARIABLE ir_temporary_variable = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
        Name nameIdentifier = Name.identifier("currentIsNotFirstWasmExportCall");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(irFunction2, -1, -1, ir_temporary_variable, nameIdentifier, this.irBooleanType, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        IrDeclarationOrigin.CATCH_PARAMETER catch_parameter = IrDeclarationOrigin.CATCH_PARAMETER.INSTANCE;
        Name nameIdentifier2 = Name.identifier("e");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        IrVariable irVariableBuildVariable2 = DeclarationBuildersKt.buildVariable(irFunction2, -1, -1, catch_parameter, nameIdentifier2, this.throwableType, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        irVariableBuildVariable.setInitializer(ExpressionHelpersKt.irGet(declarationIrBuilder, this.irBooleanType, null, this.isNotFirstWasmExportCallGetter));
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset(), null, null, true);
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irSet(irBlockBuilder, this.irBooleanType, null, this.isNotFirstWasmExportCallSetter, IrUtilsKt.toIrConst$default(true, this.irBooleanType, 0, 0, 6, null)));
        if (z) {
            Iterator<T> it = ((IrBlockBody) body).getStatements().iterator();
            while (it.hasNext()) {
                irBlockBuilder.unaryPlus((IrStatement) it.next());
            }
        } else if (z2) {
            irBlockBuilder.unaryPlus(((IrExpressionBody) body).getExpression());
        } else {
            String qualifiedName2 = Reflection.getOrCreateKotlinClass(irBlockBuilder.getClass()).getQualifiedName();
            Intrinsics.checkNotNull(qualifiedName2);
            throw new NotImplementedError("An operation is not implemented: " + qualifiedName2);
        }
        IrContainerExpression irBlockBody = irBlockBuilder.getIrBlockBody();
        IrVariable irVariable = irVariableBuildVariable;
        IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable, this.irBooleanType);
        IrVariable irVariable2 = irVariableBuildVariable2;
        IrThrowImpl irThrowImplIrThrow = LowerUtilsKt.irThrow(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable2, this.throwableType));
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, getThrowAsJsException());
        irCall.putValueArgument(0, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable2, this.throwableType));
        Unit unit = Unit.INSTANCE;
        IrCatch irCatch = LowerUtilsKt.irCatch(declarationIrBuilder, irVariableBuildVariable2, ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder, unitType, irGetValueImplIrGet, irThrowImplIrThrow, irCall, null, 16, null));
        IrType irType = this.irBooleanType;
        IrTryImpl irTryImplIrTry = ExpressionHelpersKt.irTry(declarationIrBuilder, unitType, irBlockBody, CollectionsKt.listOf(irCatch), ExpressionHelpersKt.irSet(declarationIrBuilder, irType, null, this.isNotFirstWasmExportCallSetter, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable, irType)));
        if (z2) {
            IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset(), null, null, true);
            irBlockBuilder2.unaryPlus(irVariableBuildVariable);
            irBlockBuilder2.unaryPlus(irTryImplIrTry);
            ((IrExpressionBody) body).setExpression(irBlockBuilder2.getIrBlockBody());
            return;
        }
        if (z) {
            List<IrStatement> statements = ((IrBlockBody) body).getStatements();
            statements.clear();
            statements.add(irVariableBuildVariable);
            statements.add(irTryImplIrTry);
            return;
        }
        String qualifiedName3 = Reflection.getOrCreateKotlinClass(declarationIrBuilderCreateIrBuilder$default.getClass()).getQualifiedName();
        Intrinsics.checkNotNull(qualifiedName3);
        throw new NotImplementedError("An operation is not implemented: " + qualifiedName3);
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if (this.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.WASI) {
            return;
        }
        for (IrDeclaration irDeclaration : irFile.getDeclarations()) {
            if (irDeclaration instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) irDeclaration;
                if (DeclarationGeneratorKt.isExported(irFunction)) {
                    processExportFunction(irFunction);
                }
            }
        }
    }
}
