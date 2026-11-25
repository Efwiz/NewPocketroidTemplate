package org.jetbrains.kotlin.js.translate.expression;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.translate.context.InlineFunctionContext;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;

/* JADX INFO: compiled from: InlineMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/InlineMetadata;", "", "tag", "Lorg/jetbrains/kotlin/js/backend/ast/JsStringLiteral;", "function", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStringLiteral;Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;)V", "getFunction", "()Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "getTag", "()Lorg/jetbrains/kotlin/js/backend/ast/JsStringLiteral;", "functionWithMetadata", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "sourceInfo", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineMetadata {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FunctionWithWrapper function;
    private final JsStringLiteral tag;

    @JvmStatic
    public static final InlineMetadata compose(JsFunction jsFunction, CallableDescriptor callableDescriptor, TranslationContext translationContext) {
        return INSTANCE.compose(jsFunction, callableDescriptor, translationContext);
    }

    @JvmStatic
    public static final InlineMetadata decompose(JsExpression jsExpression) {
        return INSTANCE.decompose(jsExpression);
    }

    @JvmStatic
    public static final FunctionWithWrapper decomposeWrapper(JsFunction jsFunction) {
        return INSTANCE.decomposeWrapper(jsFunction);
    }

    @JvmStatic
    public static final FunctionWithWrapper tryExtractFunction(JsExpression jsExpression) {
        return INSTANCE.tryExtractFunction(jsExpression);
    }

    @JvmStatic
    public static final JsExpression wrapFunction(TranslationContext translationContext, FunctionWithWrapper functionWithWrapper, Object obj) {
        return INSTANCE.wrapFunction(translationContext, functionWithWrapper, obj);
    }

    /* JADX INFO: compiled from: InlineMetadata.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\u00042\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0007J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0015\u001a\u00020\rH\u0007J\"\u0010\u0016\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u0007¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/InlineMetadata$Companion;", "", "()V", "compose", "Lorg/jetbrains/kotlin/js/translate/expression/InlineMetadata;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "decompose", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "decomposeCreateFunctionCall", "call", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "decomposeWrapper", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "wrapperFunction", "tryExtractFunction", "callExpression", "wrapFunction", "sourceInfo", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final InlineMetadata compose(JsFunction function, CallableDescriptor descriptor, TranslationContext context) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(context, "context");
            JsStringLiteral jsStringLiteral = new JsStringLiteral(Namer.getFunctionTag(descriptor, context.getConfig(), context.bindingContext()));
            InlineFunctionContext inlineFunctionContext = context.getInlineFunctionContext();
            Intrinsics.checkNotNull(inlineFunctionContext);
            List<JsStatement> statements = inlineFunctionContext.getImportBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            List<JsStatement> statements2 = inlineFunctionContext.getPrototypeBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            List listPlus = CollectionsKt.plus((Collection) statements, (Iterable) statements2);
            List<JsStatement> statements3 = inlineFunctionContext.getDeclarationsBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            JsBlock jsBlock = new JsBlock((List<JsStatement>) CollectionsKt.plus((Collection<? extends JsReturn>) CollectionsKt.plus((Collection) listPlus, (Iterable) statements3), new JsReturn(function)));
            context.reportInlineFunctionTag(jsStringLiteral.getValue());
            return new InlineMetadata(jsStringLiteral, new FunctionWithWrapper(function, jsBlock));
        }

        @JvmStatic
        public final InlineMetadata decompose(JsExpression expression) {
            if (expression instanceof JsInvocation) {
                return decomposeCreateFunctionCall((JsInvocation) expression);
            }
            return null;
        }

        private final InlineMetadata decomposeCreateFunctionCall(JsInvocation call) {
            JsExpression qualifier = call.getQualifier();
            JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
            if (jsNameRef == null) {
                return null;
            }
            if (!Intrinsics.areEqual(jsNameRef.getIdent(), Namer.DEFINE_INLINE_FUNCTION)) {
                JsName name2 = jsNameRef.getName();
                if ((name2 != null ? MetadataProperties.getSpecialFunction(name2) : null) != SpecialFunction.DEFINE_INLINE_FUNCTION) {
                    return null;
                }
            }
            List<JsExpression> arguments = call.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            if (arguments.size() != InlineMetadataKt.METADATA_PROPERTIES_COUNT) {
                return null;
            }
            JsExpression jsExpression = arguments.get(0);
            JsStringLiteral jsStringLiteral = jsExpression instanceof JsStringLiteral ? (JsStringLiteral) jsExpression : null;
            if (jsStringLiteral == null) {
                return null;
            }
            JsExpression jsExpression2 = arguments.get(1);
            Intrinsics.checkNotNullExpressionValue(jsExpression2, "get(...)");
            FunctionWithWrapper functionWithWrapperTryExtractFunction = tryExtractFunction(jsExpression2);
            if (functionWithWrapperTryExtractFunction == null) {
                return null;
            }
            return new InlineMetadata(jsStringLiteral, functionWithWrapperTryExtractFunction);
        }

        @JvmStatic
        public final FunctionWithWrapper tryExtractFunction(JsExpression callExpression) {
            Intrinsics.checkNotNullParameter(callExpression, "callExpression");
            if (callExpression instanceof JsFunction) {
                return new FunctionWithWrapper((JsFunction) callExpression, null);
            }
            if (!(callExpression instanceof JsInvocation)) {
                return null;
            }
            JsInvocation jsInvocation = (JsInvocation) callExpression;
            JsExpression qualifier = jsInvocation.getQualifier();
            JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
            if (jsNameRef == null) {
                return null;
            }
            JsName name2 = jsNameRef.getName();
            if ((name2 != null ? MetadataProperties.getSpecialFunction(name2) : null) != SpecialFunction.WRAP_FUNCTION || jsInvocation.getArguments().size() != 1) {
                return null;
            }
            JsExpression jsExpression = jsInvocation.getArguments().get(0);
            JsFunction jsFunction = jsExpression instanceof JsFunction ? (JsFunction) jsExpression : null;
            if (jsFunction == null) {
                return null;
            }
            return decomposeWrapper(jsFunction);
        }

        @JvmStatic
        public final FunctionWithWrapper decomposeWrapper(JsFunction wrapperFunction) {
            Intrinsics.checkNotNullParameter(wrapperFunction, "wrapperFunction");
            List<JsStatement> statements = wrapperFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) statements);
            JsReturn jsReturn = objLastOrNull instanceof JsReturn ? (JsReturn) objLastOrNull : null;
            if (jsReturn == null) {
                return null;
            }
            JsExpression expression = jsReturn.getExpression();
            JsFunction jsFunction = expression instanceof JsFunction ? (JsFunction) expression : null;
            if (jsFunction == null) {
                return null;
            }
            return new FunctionWithWrapper(jsFunction, wrapperFunction.getBody());
        }

        @JvmStatic
        public final JsExpression wrapFunction(TranslationContext context, FunctionWithWrapper function, Object sourceInfo) {
            List<JsStatement> statements;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(function, "function");
            JsBlock wrapperBody = function.getWrapperBody();
            if (wrapperBody == null) {
                wrapperBody = new JsBlock(new JsReturn(function.getFunction()));
            }
            JsFunction jsFunction = new JsFunction(function.getFunction().getScope(), wrapperBody, "");
            JsBlock wrapperBody2 = function.getWrapperBody();
            if (wrapperBody2 != null && (statements = wrapperBody2.getStatements()) != null) {
                for (JsStatement jsStatement : statements) {
                    if (jsStatement instanceof JsExpressionStatement) {
                        ((JsExpressionStatement) jsStatement).getExpression().setSource(sourceInfo);
                    } else {
                        jsStatement.setSource(sourceInfo);
                    }
                }
            }
            JsExpression jsExpressionSource = new JsInvocation(context.getNameForSpecialFunction(SpecialFunction.WRAP_FUNCTION).makeRef(), jsFunction).source(sourceInfo);
            Intrinsics.checkNotNullExpressionValue(jsExpressionSource, "source(...)");
            return jsExpressionSource;
        }
    }

    public InlineMetadata(JsStringLiteral tag, FunctionWithWrapper function) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(function, "function");
        this.tag = tag;
        this.function = function;
    }

    public final FunctionWithWrapper getFunction() {
        return this.function;
    }

    public final JsStringLiteral getTag() {
        return this.tag;
    }

    public final JsExpression functionWithMetadata(TranslationContext context, Object sourceInfo) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new JsInvocation(context.getNameForSpecialFunction(SpecialFunction.DEFINE_INLINE_FUNCTION).makeRef(), this.tag, INSTANCE.wrapFunction(context, this.function, sourceInfo));
    }
}
