package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.VariableRemapper;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: MakePropertyDelegateMethodsStaticLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\f\u0010\u0014\u001a\u00020\u0015*\u00020\u0013H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/MakePropertyDelegateMethodsStaticLowering;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isSyntheticDelegateMethod", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MakePropertyDelegateMethodsStaticLowering extends IrElementTransformerVoid implements FileLoweringPass {
    private final JvmBackendContext context;

    public MakePropertyDelegateMethodsStaticLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.transform((IrElementTransformer<? super Object>) this, (Object) null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitSimpleFunction(IrSimpleFunction declaration) {
        IrValueParameter dispatchReceiverParameter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (isSyntheticDelegateMethod(declaration) && (dispatchReceiverParameter = declaration.getDispatchReceiverParameter()) != null) {
            IrSimpleFunction irSimpleFunction = declaration;
            IrValueParameter irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(dispatchReceiverParameter, irSimpleFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8186, null);
            List listListOf = CollectionsKt.listOf(irValueParameterCopyTo$default);
            List<IrValueParameter> valueParameters = declaration.getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            for (IrValueParameter irValueParameter : valueParameters) {
                ArrayList arrayList2 = arrayList;
                arrayList2.add(IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunction, null, irValueParameter.getIndex() + 1, 0, 0, null, null, null, null, null, false, false, false, 8186, null));
                arrayList = arrayList2;
                listListOf = listListOf;
                irValueParameterCopyTo$default = irValueParameterCopyTo$default;
            }
            IrValueParameter irValueParameter2 = irValueParameterCopyTo$default;
            declaration.setValueParameters(CollectionsKt.plus((Collection) listListOf, (Iterable) arrayList));
            declaration.setDispatchReceiverParameter(null);
            IrBody body = declaration.getBody();
            declaration.setBody(body != null ? body.transform((IrElementTransformer<? super Object>) new VariableRemapper(MapsKt.mapOf(TuplesKt.to(dispatchReceiverParameter, irValueParameter2))), (Object) null) : null);
            return declaration;
        }
        return super.visitSimpleFunction(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (isSyntheticDelegateMethod(expression.getSymbol().getOwner())) {
            throw new IllegalStateException(("`$delegate` method should not be called. Please either remove the call, or support remapping of dispatch receiver in MakePropertyDelegateMethodsStaticLowering: " + RenderIrElementKt.render$default(expression.getSymbol().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return super.visitCall(expression);
    }

    private final boolean isSyntheticDelegateMethod(IrSimpleFunction irSimpleFunction) {
        if (!Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE)) {
            return false;
        }
        String strAsString = irSimpleFunction.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return StringsKt.endsWith$default(strAsString, JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX, false, 2, (Object) null);
    }
}
