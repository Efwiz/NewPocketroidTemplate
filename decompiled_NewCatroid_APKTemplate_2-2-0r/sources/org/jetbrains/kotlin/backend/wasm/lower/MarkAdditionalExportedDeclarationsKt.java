package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: markAdditionalExportedDeclarations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¨\u0006\t"}, d2 = {"markExportedDeclarations", "", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "exportedFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MarkAdditionalExportedDeclarationsKt {

    /* JADX INFO: compiled from: markAdditionalExportedDeclarations.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WasmTarget.values().length];
            try {
                iArr[WasmTarget.WASI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void markExportedDeclarations(WasmBackendContext context, IrFile irFile, Set<FqName> exportedFqNames) {
        IrConstructorSymbol jsExportConstructor;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(exportedFqNames, "exportedFqNames");
        if (WhenMappings.$EnumSwitchMapping$0[((WasmTarget) context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS)).ordinal()] == 1) {
            jsExportConstructor = context.getWasmSymbols().getWasmExportConstructor();
        } else {
            jsExportConstructor = context.getWasmSymbols().getJsRelatedSymbols().getJsExportConstructor();
        }
        for (IrDeclaration irDeclaration : irFile.getDeclarations()) {
            if ((irDeclaration instanceof IrFunction) && CollectionsKt.contains(exportedFqNames, IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) irDeclaration))) {
                irDeclaration.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) irDeclaration.getAnnotations(), ExpressionHelpersKt.irCallConstructor(LowerUtilsKt.createIrBuilder$default(context, irFile.getSymbol(), 0, 0, 6, (Object) null), jsExportConstructor, CollectionsKt.emptyList())));
            }
        }
    }
}
