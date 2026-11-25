package org.jetbrains.kotlin.js.inline.util;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;

/* JADX INFO: compiled from: sideEffectUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u001a\u0018\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¨\u0006\b"}, d2 = {"canHaveOwnSideEffect", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "vars", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "canHaveSideEffect", "localVars", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SideEffectUtilsKt {
    public static final boolean canHaveSideEffect(JsExpression jsExpression, final Set<? extends JsName> localVars) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        Intrinsics.checkNotNullParameter(localVars, "localVars");
        return AstUtilsKt.any(jsExpression, new Function1<JsNode, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.util.SideEffectUtilsKt.canHaveSideEffect.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JsNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof JsExpression) && SideEffectUtilsKt.canHaveOwnSideEffect((JsExpression) it, localVars));
            }
        });
    }

    public static final boolean canHaveOwnSideEffect(JsExpression jsExpression, Set<? extends JsName> vars) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        Intrinsics.checkNotNullParameter(vars, "vars");
        if (!(jsExpression instanceof JsConditional ? true : jsExpression instanceof JsLiteral)) {
            if (jsExpression instanceof JsBinaryOperation) {
                return ((JsBinaryOperation) jsExpression).getOperator().isAssignment();
            }
            if (jsExpression instanceof JsNameRef) {
                if (!CollectionsKt.contains(vars, ((JsNameRef) jsExpression).getName()) && MetadataProperties.getSideEffects(jsExpression) != SideEffectKind.PURE) {
                    return true;
                }
            } else if (MetadataProperties.getSideEffects(jsExpression) != SideEffectKind.PURE) {
                return true;
            }
        }
        return false;
    }
}
