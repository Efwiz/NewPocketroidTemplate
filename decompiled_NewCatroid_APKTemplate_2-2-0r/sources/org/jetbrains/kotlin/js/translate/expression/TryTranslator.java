package org.jetbrains.kotlin.js.translate.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFinallySection;
import org.jetbrains.kotlin.psi.KtTryExpression;

/* JADX INFO: compiled from: TryTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\t\u001a\u00020\nJ\u0014\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\u0002\u001a\u0004\u0018\u00010\rH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/TryTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "expression", "Lorg/jetbrains/kotlin/psi/KtTryExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/psi/KtTryExpression;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtTryExpression;", "translate", "Lorg/jetbrains/kotlin/js/backend/ast/JsTry;", "translateAsBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TryTranslator extends AbstractTranslator {
    private final KtTryExpression expression;

    public final KtTryExpression getExpression() {
        return this.expression;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TryTranslator(KtTryExpression expression, TranslationContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        this.expression = expression;
    }

    public final JsTry translate() {
        JsBlock jsBlockTranslateAsBlock = translateAsBlock(this.expression.getTryBlock());
        List<KtCatchClause> catchClauses = this.expression.getCatchClauses();
        Intrinsics.checkNotNullExpressionValue(catchClauses, "getCatchClauses(...)");
        KtTryExpression ktTryExpression = this.expression;
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        JsCatch jsCatchTranslate = new CatchTranslator(catchClauses, ktTryExpression, translationContextContext).translate();
        KtFinallySection finallyBlock = this.expression.getFinallyBlock();
        return new JsTry(jsBlockTranslateAsBlock, jsCatchTranslate, translateAsBlock(finallyBlock != null ? finallyBlock.getFinalExpression() : null));
    }

    private final JsBlock translateAsBlock(KtExpression expression) {
        if (expression == null) {
            return null;
        }
        JsStatement jsStatementTranslateAsStatementAndMergeInBlockIfNeeded = Translation.translateAsStatementAndMergeInBlockIfNeeded(expression, context());
        Intrinsics.checkNotNullExpressionValue(jsStatementTranslateAsStatementAndMergeInBlockIfNeeded, "translateAsStatementAndMergeInBlockIfNeeded(...)");
        return JsAstUtils.convertToBlock(jsStatementTranslateAsStatementAndMergeInBlockIfNeeded);
    }
}
