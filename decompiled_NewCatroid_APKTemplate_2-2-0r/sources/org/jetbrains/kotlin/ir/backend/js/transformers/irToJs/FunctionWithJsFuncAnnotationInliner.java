package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;

/* JADX INFO: compiled from: FunctionWithJsFuncAnnotationInliner.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nH\u0002J\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\b\u0010\u0011\u001a\u00020\bH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/FunctionWithJsFuncAnnotationInliner;", "", "jsFuncCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;)V", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "replacements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "collectReplacementsForCall", "generateResultStatement", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getJsFunctionImplementation", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionWithJsFuncAnnotationInliner {
    private final JsGenerationContext context;
    private final JsFunction function;
    private final IrCall jsFuncCall;
    private final Map<JsName, JsExpression> replacements;

    public FunctionWithJsFuncAnnotationInliner(IrCall jsFuncCall, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(jsFuncCall, "jsFuncCall");
        Intrinsics.checkNotNullParameter(context, "context");
        this.jsFuncCall = jsFuncCall;
        this.context = context;
        this.function = getJsFunctionImplementation();
        this.replacements = collectReplacementsForCall();
    }

    public final List<JsStatement> generateResultStatement() {
        List<JsStatement> statements = this.function.getBody().getStatements();
        new JsNameRemappingTransformer(this.replacements).acceptList(statements);
        Intrinsics.checkNotNullExpressionValue(statements, "also(...)");
        return statements;
    }

    private final JsFunction getJsFunctionImplementation() {
        JsFunction jsCodeForFunction = this.context.getStaticContext().getBackendContext().getJsCodeForFunction(this.jsFuncCall.getSymbol());
        JsFunction jsFunctionDeepCopy = jsCodeForFunction != null ? jsCodeForFunction.deepCopy() : null;
        if (jsFunctionDeepCopy != null) {
            return jsFunctionDeepCopy;
        }
        CompilationExceptionKt.compilationException("JS function not found", this.jsFuncCall);
        throw null;
    }

    private final Map<JsName, JsExpression> collectReplacementsForCall() {
        int valueArgumentsCount = this.jsFuncCall.getValueArgumentsCount();
        ArrayList arrayList = new ArrayList(valueArgumentsCount);
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = this.jsFuncCall.getValueArgument(i);
            Intrinsics.checkNotNull(valueArgument);
            arrayList.add((JsExpression) valueArgument.accept(new IrElementToJsExpressionTransformer(), this.context));
        }
        ArrayList arrayList2 = arrayList;
        List<JsParameter> parameters = this.function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList3.add(((JsParameter) it.next()).getName());
        }
        return MapsKt.toMap(CollectionsKt.zip(arrayList3, arrayList2));
    }
}
