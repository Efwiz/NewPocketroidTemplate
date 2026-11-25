package org.jetbrains.kotlin.backend.common;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Ir;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: CommonBackendContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003JD\u0010*\u001a\u00020+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020.0-2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002000-2\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u0002020-H\u0016J\u0010\u00103\u001a\u00020\u001d2\u0006\u00104\u001a\u000205H\u0016J.\u00106\u001a\u00020+2\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u0002082\u0014\u0010:\u001a\u0010\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020<\u0018\u00010;H\u0016J\u0018\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020BH\u0016R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00000\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u001fR\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u001fR\u0012\u0010(\u001a\u00020\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u001fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006CÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "Lorg/jetbrains/kotlin/backend/common/LoggingContext;", "Lorg/jetbrains/kotlin/backend/common/ErrorReportingContext;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "doWhileCounterLoopOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getDoWhileCounterLoopOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "inductionVariableOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getInductionVariableOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "inlineClassesUtils", "Lorg/jetbrains/kotlin/backend/common/InlineClassesUtils;", "getInlineClassesUtils", "()Lorg/jetbrains/kotlin/backend/common/InlineClassesUtils;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "getIr", "()Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "mapping", "Lorg/jetbrains/kotlin/backend/common/Mapping;", "getMapping", "()Lorg/jetbrains/kotlin/backend/common/Mapping;", "optimizeLoopsOverUnsignedArrays", "", "getOptimizeLoopsOverUnsignedArrays", "()Z", "optimizeNullChecksUsingKotlinNullability", "getOptimizeNullChecksUsingKotlinNullability", "partialLinkageSupport", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "getPartialLinkageSupport", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "preferJavaLikeCounterLoop", "getPreferJavaLikeCounterLoop", "scriptMode", "getScriptMode", "handleDeepCopy", "", "fileSymbolMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "classSymbolMap", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "functionSymbolMap", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isSideEffectFree", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "remapMultiFieldValueClassStructure", "oldFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "newFunction", "parametersMappingOrNull", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "throwUninitializedPropertyAccessException", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "name", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CommonBackendContext extends BackendContext, ErrorReportingContext, LoggingContext {
    CompilerConfiguration getConfiguration();

    default IrStatementOrigin getDoWhileCounterLoopOrigin() {
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    Ir<CommonBackendContext> getIr();

    Mapping getMapping();

    default boolean getOptimizeLoopsOverUnsignedArrays() {
        return false;
    }

    default boolean getOptimizeNullChecksUsingKotlinNullability() {
        return true;
    }

    default boolean getPreferJavaLikeCounterLoop() {
        return false;
    }

    boolean getScriptMode();

    default void handleDeepCopy(Map<IrFileSymbol, IrFileSymbol> fileSymbolMap, Map<IrClassSymbol, IrClassSymbol> classSymbolMap, Map<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> functionSymbolMap) {
        Intrinsics.checkNotNullParameter(fileSymbolMap, "fileSymbolMap");
        Intrinsics.checkNotNullParameter(classSymbolMap, "classSymbolMap");
        Intrinsics.checkNotNullParameter(functionSymbolMap, "functionSymbolMap");
    }

    default boolean isSideEffectFree(IrCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return false;
    }

    default void remapMultiFieldValueClassStructure(IrFunction oldFunction, IrFunction newFunction, Map<IrValueParameter, ? extends IrValueParameter> parametersMappingOrNull) {
        Intrinsics.checkNotNullParameter(oldFunction, "oldFunction");
        Intrinsics.checkNotNullParameter(newFunction, "newFunction");
    }

    default IrExpression throwUninitializedPropertyAccessException(IrBuilderWithScope builder, String name2) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(builder, getIr().getSymbols().getThrowUninitializedPropertyAccessException().getOwner());
        irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irString(builder, name2));
        return irFunctionAccessExpressionIrCall;
    }

    default IrDeclarationOrigin getInductionVariableOrigin() {
        return IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
    }

    default InlineClassesUtils getInlineClassesUtils() {
        return DefaultInlineClassesUtils.INSTANCE;
    }

    default PartialLinkageSupportForLowerings getPartialLinkageSupport() {
        return PartialLinkageSupportForLowerings.INSTANCE.getDISABLED();
    }
}
