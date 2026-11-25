package org.jetbrains.kotlin.ir.interpreter.state.reflection;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: ReflectionState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0004J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0004J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0004J\u001c\u0010\u0014\u001a\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0016H\u0002J\f\u0010\u0018\u001a\u00020\u000e*\u00020\u0016H\u0004R$\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004j\u0002`\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "()V", "fields", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Fields;", "getFields", "()Ljava/util/Map;", "getIrFunctionByIrCall", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "renderFunction", "", "irFunction", "renderLambda", "renderProperty", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "renderReceivers", "dispatchReceiver", "Lorg/jetbrains/kotlin/ir/types/IrType;", "extensionReceiver", "renderType", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReflectionState implements State {
    private final Map<IrSymbol, State> fields = new LinkedHashMap();

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrFunction getIrFunctionByIrCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public Map<IrSymbol, State> getFields() {
        return this.fields;
    }

    private final String renderReceivers(IrType dispatchReceiver, IrType extensionReceiver) {
        StringBuilder sb = new StringBuilder();
        if (dispatchReceiver != null) {
            sb.append(renderType(dispatchReceiver)).append(".");
        }
        if (extensionReceiver != null) {
            boolean z = dispatchReceiver != null;
            if (z) {
                sb.append("(");
            }
            sb.append(renderType(extensionReceiver)).append(".");
            if (z) {
                sb.append(Constant.AFTER_QUTO);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    protected final String renderLambda(IrFunction irFunction) {
        IrType type;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null) {
            IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
            type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        }
        String strRenderType = type != null ? renderType(type) : null;
        String str = CollectionsKt.joinToString$default(irFunction.getValueParameters(), null, "(", Constant.AFTER_QUTO, 0, null, new Function1<IrValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.ir.interpreter.state.reflection.ReflectionState$renderLambda$arguments$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrValueParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.renderType(it.getType());
            }
        }, 25, null) + " -> " + renderType(irFunction.getReturnType());
        return strRenderType != null ? strRenderType + '.' + str : str;
    }

    protected final String renderFunction(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irFunction);
        IrSimpleType defaultType = parentClassOrNull != null ? IrUtilsKt.getDefaultType(parentClassOrNull) : null;
        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
        return "fun " + (irFunction instanceof IrConstructor ? "" : renderReceivers(defaultType, extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null)) + irFunction.getName() + CollectionsKt.joinToString$default(irFunction.getValueParameters(), null, "(", Constant.AFTER_QUTO, 0, null, new Function1<IrValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.ir.interpreter.state.reflection.ReflectionState$renderFunction$arguments$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrValueParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.renderType(it.getType());
            }
        }, 25, null) + ": " + renderType(irFunction.getReturnType());
    }

    protected final String renderProperty(IrProperty property) {
        IrValueParameter extensionReceiverParameter;
        IrValueParameter dispatchReceiverParameter;
        Intrinsics.checkNotNullParameter(property, "property");
        String str = property.getIsVar() ? PsiKeyword.VAR : "val";
        IrSimpleFunction getter = property.getGetter();
        IrType type = null;
        IrType type2 = (getter == null || (dispatchReceiverParameter = getter.getDispatchReceiverParameter()) == null) ? null : dispatchReceiverParameter.getType();
        IrSimpleFunction getter2 = property.getGetter();
        if (getter2 != null && (extensionReceiverParameter = getter2.getExtensionReceiverParameter()) != null) {
            type = extensionReceiverParameter.getType();
        }
        String strRenderReceivers = renderReceivers(type2, type);
        IrSimpleFunction getter3 = property.getGetter();
        Intrinsics.checkNotNull(getter3);
        return str + StringUtil.SPACE + strRenderReceivers + property.getName() + ": " + renderType(getter3.getReturnType());
    }

    protected final String renderType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        String strReplace$default = StringsKt.replace$default(RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null), "<root>.", "", false, 4, (Object) null);
        String str = strReplace$default;
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) "<get-", false, 2, (Object) null)) {
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, "<get-", 0, false, 6, (Object) null);
            strReplace$default = StringsKt.replaceRange((CharSequence) str, iIndexOf$default, StringsKt.indexOf$default((CharSequence) str, '>', iIndexOf$default, false, 4, (Object) null) + 1, (CharSequence) "get").toString();
        }
        while (true) {
            String str2 = strReplace$default;
            int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) str2, " of ", 0, false, 6, (Object) null);
            if (iIndexOf$default2 == -1) {
                return strReplace$default;
            }
            int iIndexOf$default3 = StringsKt.indexOf$default((CharSequence) str2, ',', iIndexOf$default2, false, 4, (Object) null);
            int iIndexOf$default4 = StringsKt.indexOf$default((CharSequence) str2, '>', iIndexOf$default2, false, 4, (Object) null);
            if (iIndexOf$default3 == -1 && iIndexOf$default4 == -1) {
                iIndexOf$default3 = strReplace$default.length();
            } else if (iIndexOf$default3 == -1) {
                iIndexOf$default3 = iIndexOf$default4;
            } else if (iIndexOf$default4 != -1) {
                iIndexOf$default3 = Math.min(iIndexOf$default3, iIndexOf$default4);
            }
            strReplace$default = StringsKt.replaceRange((CharSequence) str2, iIndexOf$default2, iIndexOf$default3, (CharSequence) "").toString();
        }
    }
}
