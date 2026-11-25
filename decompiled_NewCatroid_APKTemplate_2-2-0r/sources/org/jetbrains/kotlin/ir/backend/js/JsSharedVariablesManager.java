package org.jetbrains.kotlin.ir.backend.js;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: JsSharedVariablesManager.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016J\u0018\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsSharedVariablesManager;", "Lorg/jetbrains/kotlin/backend/common/ir/SharedVariablesManager;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "createBox", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "dynamicType", "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "readBox", "writeBox", "declareSharedVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "originalDeclaration", "defineSharedValue", "sharedVariableDeclaration", "getSharedValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "sharedVariableSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "originalGet", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "setSharedValue", "originalSet", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsSharedVariablesManager implements SharedVariablesManager {
    private final IrBuiltIns builtIns;
    private final IrSimpleFunctionSymbol createBox;
    private final IrDynamicType dynamicType;
    private final IrSimpleFunctionSymbol readBox;
    private final IrSimpleFunctionSymbol writeBox;

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrVariable defineSharedValue(IrVariable originalDeclaration, IrVariable sharedVariableDeclaration) {
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        Intrinsics.checkNotNullParameter(sharedVariableDeclaration, "sharedVariableDeclaration");
        return sharedVariableDeclaration;
    }

    public JsSharedVariablesManager(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.builtIns = context.getIrBuiltIns();
        this.createBox = context.getIntrinsics().getCreateSharedBox();
        this.readBox = context.getIntrinsics().getReadSharedBox();
        this.writeBox = context.getIntrinsics().getWriteSharedBox();
        this.dynamicType = context.getDynamicType();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrVariable declareSharedVariable(IrVariable originalDeclaration) {
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        IrType type = originalDeclaration.getType();
        IrConstImpl initializer = originalDeclaration.getInitializer();
        if (initializer == null) {
            initializer = IrConstImpl.INSTANCE.constNull(originalDeclaration.getStartOffset(), originalDeclaration.getEndOffset(), type);
        }
        IrCallImpl irCallImpl = new IrCallImpl(initializer.getStartOffset(), initializer.getEndOffset(), this.dynamicType, this.createBox, 1, 1, null, null, 192, null);
        irCallImpl.putTypeArgument(0, type);
        irCallImpl.putValueArgument(0, initializer);
        IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(originalDeclaration.getParent(), originalDeclaration.getStartOffset(), originalDeclaration.getEndOffset(), originalDeclaration.getOrigin(), originalDeclaration.getName(), this.dynamicType, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        irVariableBuildVariable.setInitializer(irCallImpl);
        return irVariableBuildVariable;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrExpression getSharedValue(IrValueSymbol sharedVariableSymbol, IrGetValue originalGet) {
        Intrinsics.checkNotNullParameter(sharedVariableSymbol, "sharedVariableSymbol");
        Intrinsics.checkNotNullParameter(originalGet, "originalGet");
        IrCallImpl irCallImpl = new IrCallImpl(originalGet.getStartOffset(), originalGet.getEndOffset(), originalGet.getType(), this.readBox, 1, 1, originalGet.getOrigin(), null, 128, null);
        irCallImpl.putTypeArgument(0, originalGet.getType());
        irCallImpl.putValueArgument(0, new IrGetValueImpl(originalGet.getStartOffset(), originalGet.getEndOffset(), this.dynamicType, sharedVariableSymbol, originalGet.getOrigin()));
        return irCallImpl;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrExpression setSharedValue(IrValueSymbol sharedVariableSymbol, IrSetValue originalSet) {
        Intrinsics.checkNotNullParameter(sharedVariableSymbol, "sharedVariableSymbol");
        Intrinsics.checkNotNullParameter(originalSet, "originalSet");
        IrCallImpl irCallImpl = new IrCallImpl(originalSet.getStartOffset(), originalSet.getEndOffset(), this.builtIns.getUnitType(), this.writeBox, 1, 2, originalSet.getOrigin(), null, 128, null);
        irCallImpl.putTypeArgument(0, originalSet.getValue().getType());
        irCallImpl.putValueArgument(0, new IrGetValueImpl(originalSet.getStartOffset(), originalSet.getEndOffset(), this.dynamicType, sharedVariableSymbol, originalSet.getOrigin()));
        irCallImpl.putValueArgument(1, originalSet.getValue());
        return irCallImpl;
    }
}
