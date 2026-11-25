package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;

/* JADX INFO: compiled from: JsClassGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0005\u001a\f\u0010\u0006\u001a\u00020\u0001*\u00020\u0007H\u0002\u001a\u0012\u0010\b\u001a\u00020\u0001*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u0012\u0010\f\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005\u001a\u0012\u0010\u000f\u001a\u00020\u0001*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u0010\u0010\u0010\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0011H\u0002\u001a\u0014\u0010\u0012\u001a\u00020\u0001*\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0013"}, d2 = {"isInterface", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)Z", "escapedIfNeed", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "existsInRuntime", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "isAccessorOfOverriddenStableProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "isSimpleSuperCall", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "container", "overriddenStableProperty", "overridesExternal", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "shouldExportAccessor", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsClassGeneratorKt {
    public static final JsFunction escapedIfNeed(JsFunction jsFunction) {
        String ident;
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        JsName name2 = jsFunction.getName();
        if (name2 != null && (ident = name2.getIdent()) != null && !IdentifierPolicyKt.isValidES5Identifier(ident)) {
            jsFunction.setName(new JsName(FormatHelper.QUOTE + jsFunction.getName().getIdent() + '\'', jsFunction.getName().isTemporary()));
        }
        return jsFunction;
    }

    public static final boolean isSimpleSuperCall(JsStatement jsStatement, JsFunction container) {
        Intrinsics.checkNotNullParameter(jsStatement, "<this>");
        Intrinsics.checkNotNullParameter(container, "container");
        if (!(jsStatement instanceof JsExpressionStatement)) {
            return false;
        }
        JsExpression expression = ((JsExpressionStatement) jsStatement).getExpression();
        JsInvocation jsInvocation = expression instanceof JsInvocation ? (JsInvocation) expression : null;
        if (jsInvocation == null || !(jsInvocation.getQualifier() instanceof JsSuperRef) || container.getParameters().size() != jsInvocation.getArguments().size()) {
            return false;
        }
        List<JsParameter> parameters = container.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        int lastIndex = CollectionsKt.getLastIndex(parameters);
        if (lastIndex < 0) {
            return true;
        }
        int i = 0;
        while (true) {
            JsParameter jsParameter = container.getParameters().get(i);
            JsExpression jsExpression = jsInvocation.getArguments().get(i);
            JsNameRef jsNameRef = jsExpression instanceof JsNameRef ? (JsNameRef) jsExpression : null;
            if (jsNameRef != null) {
                if (jsNameRef.getQualifier() != null) {
                    jsNameRef = null;
                }
                if (jsNameRef == null || !Intrinsics.areEqual(jsParameter.getName(), jsNameRef.getName())) {
                    return false;
                }
                if (i == lastIndex) {
                    return true;
                }
                i++;
            }
            return false;
        }
    }

    public static final boolean shouldExportAccessor(IrSimpleFunction irSimpleFunction, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (irSimpleFunction == null) {
            return false;
        }
        if (ExportModelGeneratorKt.isExported(IrUtilsKt.getParentAsClass(irSimpleFunction), context)) {
            return true;
        }
        return isAccessorOfOverriddenStableProperty(irSimpleFunction, context);
    }

    public static final boolean overriddenStableProperty(IrSimpleFunction irSimpleFunction, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        IrProperty owner = correspondingPropertySymbol.getOwner();
        if (ExportModelGeneratorKt.isOverriddenExported(owner, context)) {
            return ExportModelGeneratorKt.isOverriddenExported(irSimpleFunction, context);
        }
        return overridesExternal(irSimpleFunction) || AnnotationUtilsKt.getJsName(owner) != null;
    }

    public static final boolean isAccessorOfOverriddenStableProperty(IrSimpleFunction irSimpleFunction, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!overriddenStableProperty(irSimpleFunction, context)) {
            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
            Intrinsics.checkNotNull(correspondingPropertySymbol);
            if (!overridesExternal(correspondingPropertySymbol.getOwner())) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean overridesExternal(IrOverridableDeclaration<?> irOverridableDeclaration) {
        if (IrUtilsKt.isEffectivelyExternal(irOverridableDeclaration)) {
            return true;
        }
        Iterable overriddenSymbols = irOverridableDeclaration.getOverriddenSymbols();
        if (!(overriddenSymbols instanceof Collection) || !((Collection) overriddenSymbols).isEmpty()) {
            Iterator it = overriddenSymbols.iterator();
            while (it.hasNext()) {
                IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration<*>");
                if (overridesExternal((IrOverridableDeclaration) owner)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isInterface(IrClassifierSymbol irClassifierSymbol) {
        IrSymbolOwner owner = irClassifierSymbol.getOwner();
        IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
        return irClass != null && IrUtilsKt.isInterface(irClass);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean existsInRuntime(IrClassSymbol irClassSymbol) {
        return (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irClassSymbol.getOwner()) && IrUtilsKt.isInterface(irClassSymbol.getOwner())) ? false : true;
    }
}
