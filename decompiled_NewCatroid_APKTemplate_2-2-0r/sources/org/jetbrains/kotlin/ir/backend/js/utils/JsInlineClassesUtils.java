package org.jetbrains.kotlin.ir.backend.js.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IdSignatureValues;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;

/* JADX INFO: compiled from: JsInlineClassesUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/JsInlineClassesUtils;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonInlineClassesUtils;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "boxIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getBoxIntrinsic", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "unboxIntrinsic", "getUnboxIntrinsic", "getInlinedClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "isClassInlineLike", "", "klass", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInlineClassesUtils implements JsCommonInlineClassesUtils {
    private final JsIrBackendContext context;

    public JsInlineClassesUtils(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils
    public IrClass getInlinedClass(IrType type) {
        IrClass irClassErase;
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof IrSimpleType) || (irClassErase = IrTypeUtilsKt.erase(type)) == null || !isClassInlineLike(irClassErase)) {
            return null;
        }
        if (IrTypePredicatesKt.isMarkedNullable((IrSimpleType) type)) {
            IrClass inlinedClass = irClassErase;
            do {
                IrType inlineClassUnderlyingType = getInlineClassUnderlyingType(inlinedClass);
                if (IrTypePredicatesKt.isMarkedNullable(inlineClassUnderlyingType)) {
                    return null;
                }
                inlinedClass = getInlinedClass(inlineClassUnderlyingType);
            } while (inlinedClass != null);
        }
        return irClassErase;
    }

    @Override // org.jetbrains.kotlin.backend.common.InlineClassesUtils
    public boolean isClassInlineLike(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return super.isClassInlineLike(klass) || Intrinsics.areEqual(klass.getSymbol().getSignature(), IdSignatureValues._char);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils
    public IrSimpleFunctionSymbol getBoxIntrinsic() {
        return this.context.getIntrinsics().getJsBoxIntrinsic();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils
    public IrSimpleFunctionSymbol getUnboxIntrinsic() {
        return this.context.getIntrinsics().getJsUnboxIntrinsic();
    }
}
