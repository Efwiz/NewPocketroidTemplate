package org.jetbrains.kotlin.backend.common.actualizer;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: FunctionDefaultParametersActualizer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/FunctionDefaultParametersActualizer;", "", "symbolRemapper", "Lorg/jetbrains/kotlin/backend/common/actualizer/ActualizerSymbolRemapper;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/DeepCopyTypeRemapper;", "expectActualMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/backend/common/actualizer/ActualizerSymbolRemapper;Lorg/jetbrains/kotlin/ir/util/DeepCopyTypeRemapper;Ljava/util/Map;)V", "visitor", "Lorg/jetbrains/kotlin/backend/common/actualizer/FunctionDefaultParametersActualizerVisitor;", "actualize", "", "expectFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "actualFunction", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionDefaultParametersActualizer {
    private final Map<IrSymbol, IrSymbol> expectActualMap;
    private final FunctionDefaultParametersActualizerVisitor visitor;

    /* JADX WARN: Multi-variable type inference failed */
    public FunctionDefaultParametersActualizer(ActualizerSymbolRemapper symbolRemapper, DeepCopyTypeRemapper typeRemapper, Map<IrSymbol, ? extends IrSymbol> expectActualMap) {
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        Intrinsics.checkNotNullParameter(expectActualMap, "expectActualMap");
        this.expectActualMap = expectActualMap;
        this.visitor = new FunctionDefaultParametersActualizerVisitor(symbolRemapper, typeRemapper);
    }

    public final void actualize() {
        for (Map.Entry<IrSymbol, IrSymbol> entry : this.expectActualMap.entrySet()) {
            IrSymbol key = entry.getKey();
            IrSymbol value = entry.getValue();
            if (key instanceof IrFunctionSymbol) {
                IrFunction owner = ((IrFunctionSymbol) key).getOwner();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol");
                actualize(owner, ((IrFunctionSymbol) value).getOwner());
            }
        }
    }

    private final void actualize(IrFunction expectFunction, IrFunction actualFunction) {
        for (Pair pair : CollectionsKt.zip(expectFunction.getValueParameters(), actualFunction.getValueParameters())) {
            IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
            IrValueParameter irValueParameter2 = (IrValueParameter) pair.component2();
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (irValueParameter2.getDefaultValue() == null && defaultValue != null) {
                IrExpressionBody irExpressionBody = defaultValue;
                DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                IrVisitorsKt.acceptVoid(irExpressionBody, deepCopySymbolRemapper);
                DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpressionBody.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), actualFunction);
                if (irElementPatchDeclarationParents != null) {
                    irValueParameter2.setDefaultValue(((IrExpressionBody) irElementPatchDeclarationParents).transform((IrElementTransformer<? super Object>) this.visitor, (Object) null));
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
                }
            }
        }
    }
}
