package org.jetbrains.kotlin.resolve.constants.evaluate;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000;\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0001J\u001c\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00022\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0002J\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00022\u0006\u0010\t\u001a\u00020\nJ \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\t\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\t\u001a\u00020\u000f2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J\"\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00022\u0006\u0010\t\u001a\u00020\u00112\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016¨\u0006\u0012"}, d2 = {"org/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "Lorg/jetbrains/kotlin/resolve/constants/TypedCompileTimeConstant;", "", "", "createStringConstant", "compileTimeConstant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "evaluate", "entry", "Lorg/jetbrains/kotlin/psi/KtStringTemplateEntry;", "visitEscapeStringTemplateEntry", "Lorg/jetbrains/kotlin/psi/KtEscapeStringTemplateEntry;", "data", "visitLiteralStringTemplateEntry", "Lorg/jetbrains/kotlin/psi/KtLiteralStringTemplateEntry;", "visitStringTemplateEntryWithExpression", "Lorg/jetbrains/kotlin/psi/KtStringTemplateEntryWithExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1 extends KtVisitor {
    final /* synthetic */ ConstantExpressionEvaluatorVisitor this$0;

    ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1(ConstantExpressionEvaluatorVisitor constantExpressionEvaluatorVisitor) {
        this.this$0 = constantExpressionEvaluatorVisitor;
    }

    private final TypedCompileTimeConstant<String> createStringConstant(CompileTimeConstant<?> compileTimeConstant) {
        SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
        ConstantValue<?> constantValue = compileTimeConstant.toConstantValue(NO_EXPECTED_TYPE);
        if (ConstantExpressionEvaluatorKt.isStandaloneOnlyConstant(constantValue)) {
            return null;
        }
        ConstantExpressionEvaluatorVisitor constantExpressionEvaluatorVisitor = this.this$0;
        if (constantValue instanceof ErrorValue ? true : constantValue instanceof EnumValue) {
            return null;
        }
        return constantExpressionEvaluatorVisitor.wrap(constantValue instanceof NullValue ? new StringValue("null") : new StringValue(String.valueOf(constantValue.boxedValue())), compileTimeConstant.getParameters());
    }

    public final TypedCompileTimeConstant<String> evaluate(KtStringTemplateEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        return (TypedCompileTimeConstant) entry.accept(this, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public TypedCompileTimeConstant<String> visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression entry, Void data) {
        ConstantExpressionEvaluatorVisitor constantExpressionEvaluatorVisitor;
        CompileTimeConstant<?> compileTimeConstantEvaluate;
        Intrinsics.checkNotNullParameter(entry, "entry");
        KtExpression expression = entry.getExpression();
        if (expression == null || (compileTimeConstantEvaluate = (constantExpressionEvaluatorVisitor = this.this$0).evaluate(expression, constantExpressionEvaluatorVisitor.builtIns.getStringType())) == null) {
            return null;
        }
        return createStringConstant(compileTimeConstantEvaluate);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public TypedCompileTimeConstant<String> visitLiteralStringTemplateEntry(KtLiteralStringTemplateEntry entry, Void data) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        ConstantExpressionEvaluatorVisitor constantExpressionEvaluatorVisitor = this.this$0;
        String text = entry.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return ConstantExpressionEvaluatorVisitor.wrap$default(constantExpressionEvaluatorVisitor, new StringValue(text), false, false, false, false, false, false, false, 127, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public TypedCompileTimeConstant<String> visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry entry, Void data) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        ConstantExpressionEvaluatorVisitor constantExpressionEvaluatorVisitor = this.this$0;
        String unescapedValue = entry.getUnescapedValue();
        Intrinsics.checkNotNullExpressionValue(unescapedValue, "getUnescapedValue(...)");
        return ConstantExpressionEvaluatorVisitor.wrap$default(constantExpressionEvaluatorVisitor, new StringValue(unescapedValue), false, false, false, false, false, false, false, 127, null);
    }
}
