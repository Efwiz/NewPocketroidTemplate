package org.jetbrains.kotlin.js.inline;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.inline.context.FunctionDefinitionLoader;
import org.jetbrains.kotlin.js.inline.context.InliningContext;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.translate.general.AstGenerationResult;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: JsInliner.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ \u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&J\u0006\u0010'\u001a\u00020(J(\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\"R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/js/inline/JsInliner;", "", "reporter", "Lorg/jetbrains/kotlin/js/config/JsConfig$Reporter;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "trace", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "translationResult", "Lorg/jetbrains/kotlin/js/translate/general/AstGenerationResult;", "(Lorg/jetbrains/kotlin/js/config/JsConfig$Reporter;Lorg/jetbrains/kotlin/js/config/JsConfig;Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/js/translate/general/AstGenerationResult;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getConfig", "()Lorg/jetbrains/kotlin/js/config/JsConfig;", "cycleReporter", "Lorg/jetbrains/kotlin/js/inline/InlinerCycleReporter;", "getCycleReporter", "()Lorg/jetbrains/kotlin/js/inline/InlinerCycleReporter;", "functionDefinitionLoader", "Lorg/jetbrains/kotlin/js/inline/context/FunctionDefinitionLoader;", "getFunctionDefinitionLoader", "()Lorg/jetbrains/kotlin/js/inline/context/FunctionDefinitionLoader;", "getReporter", "()Lorg/jetbrains/kotlin/js/config/JsConfig$Reporter;", "getTrace", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "getTranslationResult", "()Lorg/jetbrains/kotlin/js/translate/general/AstGenerationResult;", "inline", "Lorg/jetbrains/kotlin/js/inline/InlineableResult;", "scope", "Lorg/jetbrains/kotlin/js/inline/InliningScope;", "call", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "currentStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "process", "", "inlineFn", "Lorg/jetbrains/kotlin/js/inline/InlineFunctionDefinition;", "definitionFragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "callsiteScope", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInliner {
    private final BindingContext bindingContext;
    private final JsConfig config;
    private final InlinerCycleReporter cycleReporter;
    private final FunctionDefinitionLoader functionDefinitionLoader;
    private final JsConfig.Reporter reporter;
    private final DiagnosticSink trace;
    private final AstGenerationResult translationResult;

    public JsInliner(JsConfig.Reporter reporter, JsConfig config, DiagnosticSink trace, BindingContext bindingContext, AstGenerationResult translationResult) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(translationResult, "translationResult");
        this.reporter = reporter;
        this.config = config;
        this.trace = trace;
        this.bindingContext = bindingContext;
        this.translationResult = translationResult;
        FunctionDefinitionLoader functionDefinitionLoader = new FunctionDefinitionLoader(this);
        this.functionDefinitionLoader = functionDefinitionLoader;
        this.cycleReporter = new InlinerCycleReporter(trace, functionDefinitionLoader);
    }

    public final JsConfig.Reporter getReporter() {
        return this.reporter;
    }

    public final JsConfig getConfig() {
        return this.config;
    }

    public final DiagnosticSink getTrace() {
        return this.trace;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final AstGenerationResult getTranslationResult() {
        return this.translationResult;
    }

    public final FunctionDefinitionLoader getFunctionDefinitionLoader() {
        return this.functionDefinitionLoader;
    }

    public final InlinerCycleReporter getCycleReporter() {
        return this.cycleReporter;
    }

    public final void process() {
        Iterator<JsProgramFragment> it = this.translationResult.getNewFragments().iterator();
        while (it.hasNext()) {
            ImportIntoFragmentInliningScope.INSTANCE.process(it.next(), new Function1<ImportIntoFragmentInliningScope, Unit>() { // from class: org.jetbrains.kotlin.js.inline.JsInliner.process.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ImportIntoFragmentInliningScope importIntoFragmentInliningScope) {
                    invoke2(importIntoFragmentInliningScope);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ImportIntoFragmentInliningScope fragmentScope) {
                    Intrinsics.checkNotNullParameter(fragmentScope, "fragmentScope");
                    new InlineAstVisitor(JsInliner.this, fragmentScope).accept(fragmentScope.getAllCode());
                }
            });
        }
    }

    public final void process(final InlineFunctionDefinition inlineFn, JsInvocation call, final JsProgramFragment definitionFragment, final InliningScope callsiteScope) {
        Intrinsics.checkNotNullParameter(inlineFn, "inlineFn");
        Intrinsics.checkNotNullParameter(definitionFragment, "definitionFragment");
        Intrinsics.checkNotNullParameter(callsiteScope, "callsiteScope");
        if (this.translationResult.getNewFragments().contains(definitionFragment)) {
            this.cycleReporter.processInlineFunction(inlineFn.getFn(), call, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.inline.JsInliner.process.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    FunctionWithWrapper fn = inlineFn.getFn();
                    JsFunction function = fn.getFunction();
                    final JsBlock wrapperBody = fn.getWrapperBody();
                    if (wrapperBody != null) {
                        ImportIntoWrapperInliningScope.Companion companion = ImportIntoWrapperInliningScope.INSTANCE;
                        JsProgramFragment jsProgramFragment = definitionFragment;
                        final JsInliner jsInliner = this;
                        companion.process(wrapperBody, jsProgramFragment, new Function1<ImportIntoWrapperInliningScope, Unit>() { // from class: org.jetbrains.kotlin.js.inline.JsInliner.process.2.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(ImportIntoWrapperInliningScope importIntoWrapperInliningScope) {
                                invoke2(importIntoWrapperInliningScope);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(ImportIntoWrapperInliningScope scope) {
                                Intrinsics.checkNotNullParameter(scope, "scope");
                                new InlineAstVisitor(jsInliner, scope).accept(wrapperBody);
                            }
                        });
                        return;
                    }
                    new InlineAstVisitor(this, callsiteScope).accept(function);
                }
            });
        }
    }

    public final InlineableResult inline(InliningScope scope, JsInvocation call, JsStatement currentStatement) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(call, "call");
        JsFunction jsFunctionImportFunctionDefinition = scope.importFunctionDefinition(this.functionDefinitionLoader.getFunctionDefinition(call, scope));
        InliningContext inliningContext = new InliningContext(currentStatement);
        InlineableResult inlineableCallReplacement = FunctionInlineMutator.INSTANCE.getInlineableCallReplacement(call, jsFunctionImportFunctionDefinition, inliningContext);
        JsStatement inlineableBody = inlineableCallReplacement.getInlineableBody();
        JsExpression resultExpression = inlineableCallReplacement.getResultExpression();
        new InlineAstVisitor(this, scope).accept(inlineableBody);
        if (resultExpression != null) {
            MetadataProperties.setSynthetic(resultExpression, true);
        }
        return new InlineableResult(new JsBlock((List<JsStatement>) CollectionsKt.plus((Collection<? extends JsStatement>) inliningContext.getPreviousStatements(), inlineableBody)), resultExpression);
    }
}
