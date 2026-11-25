package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import org.jetbrains.kotlin.backend.jvm.serialization.JvmIdSignatureDescriptor;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmDescriptorMangler;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmReplCompilerBase.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003BM\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00126\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u00110\n¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rR\u001b\u0010\u000e\u001a\u00028\u00008FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010RA\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u00110\n¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020&8F¢\u0006\u0006\u001a\u0004\b'\u0010(R'\u0010)\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020+0*8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\u0012\u001a\u0004\b,\u0010-R\u0011\u0010/\u001a\u00020+8F¢\u0006\u0006\u001a\u0004\b0\u00101¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ReplCompilationState;", "AnalyzerT", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "context", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "analyzerInit", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplImplicitsExtensionsResolutionFilter;", "(Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;Lkotlin/jvm/functions/Function2;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplImplicitsExtensionsResolutionFilter;)V", "analyzerEngine", "getAnalyzerEngine", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "analyzerEngine$delegate", "Lkotlin/Lazy;", "getAnalyzerInit", "()Lkotlin/jvm/functions/Function2;", "baseScriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getBaseScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getContext", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "getDisposable", "()Lcom/intellij/openapi/Disposable;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "getEnvironment", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "getImplicitsResolutionFilter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplImplicitsExtensionsResolutionFilter;", "mangler", "Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;", "getMangler", "()Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;", "manglerAndSymbolTable", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getManglerAndSymbolTable", "()Lkotlin/Pair;", "manglerAndSymbolTable$delegate", "symbolTable", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplCompilationState<AnalyzerT extends ReplCodeAnalyzerBase> implements JvmReplCompilerState.Compilation {

    /* JADX INFO: renamed from: analyzerEngine$delegate, reason: from kotlin metadata */
    private final Lazy analyzerEngine;
    private final Function2<SharedScriptCompilationContext, ImplicitsExtensionsResolutionFilter, AnalyzerT> analyzerInit;
    private final SharedScriptCompilationContext context;
    private final ReplImplicitsExtensionsResolutionFilter implicitsResolutionFilter;

    /* JADX INFO: renamed from: manglerAndSymbolTable$delegate, reason: from kotlin metadata */
    private final Lazy manglerAndSymbolTable;

    /* JADX WARN: Multi-variable type inference failed */
    public ReplCompilationState(SharedScriptCompilationContext context, Function2<? super SharedScriptCompilationContext, ? super ImplicitsExtensionsResolutionFilter, ? extends AnalyzerT> analyzerInit, ReplImplicitsExtensionsResolutionFilter implicitsResolutionFilter) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(analyzerInit, "analyzerInit");
        Intrinsics.checkNotNullParameter(implicitsResolutionFilter, "implicitsResolutionFilter");
        this.context = context;
        this.analyzerInit = analyzerInit;
        this.implicitsResolutionFilter = implicitsResolutionFilter;
        this.analyzerEngine = LazyKt.lazy(new Function0<AnalyzerT>(this) { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ReplCompilationState$analyzerEngine$2
            final /* synthetic */ ReplCompilationState<AnalyzerT> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Incorrect return type in method signature: ()TAnalyzerT; */
            @Override // kotlin.jvm.functions.Function0
            public final ReplCodeAnalyzerBase invoke() {
                ReplCodeAnalyzerBase replCodeAnalyzerBase = (ReplCodeAnalyzerBase) this.this$0.getAnalyzerInit().invoke(this.this$0.getContext(), this.this$0.getImplicitsResolutionFilter());
                KotlinJavaPsiFacade.getInstance(this.this$0.getEnvironment().getProject()).setNotFoundPackagesCachingStrategy(ReplNotFoundPackagesCachingStrategy.INSTANCE);
                return replCodeAnalyzerBase;
            }
        });
        this.manglerAndSymbolTable = LazyKt.lazy(new Function0<Pair<? extends JvmDescriptorMangler, ? extends SymbolTable>>(this) { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ReplCompilationState$manglerAndSymbolTable$2
            final /* synthetic */ ReplCompilationState<AnalyzerT> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Pair<? extends JvmDescriptorMangler, ? extends SymbolTable> invoke() {
                BindingContext context2 = this.this$0.getAnalyzerEngine().getTrace().getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                JvmDescriptorMangler jvmDescriptorMangler = new JvmDescriptorMangler(new MainFunctionDetector(context2, CommonConfigurationKeysKt.getLanguageVersionSettings(this.this$0.getEnvironment().getConfiguration())));
                return TuplesKt.to(jvmDescriptorMangler, new SymbolTable(new JvmIdSignatureDescriptor(jvmDescriptorMangler), IrFactoryImpl.INSTANCE, null, 4, null));
            }
        });
    }

    public final SharedScriptCompilationContext getContext() {
        return this.context;
    }

    public final Function2<SharedScriptCompilationContext, ImplicitsExtensionsResolutionFilter, AnalyzerT> getAnalyzerInit() {
        return this.analyzerInit;
    }

    public final ReplImplicitsExtensionsResolutionFilter getImplicitsResolutionFilter() {
        return this.implicitsResolutionFilter;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState.Compilation
    public Disposable getDisposable() {
        return this.context.getDisposable();
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState.Compilation
    public ScriptCompilationConfiguration getBaseScriptCompilationConfiguration() {
        return this.context.getBaseScriptCompilationConfiguration();
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState.Compilation
    public KotlinCoreEnvironment getEnvironment() {
        return this.context.getEnvironment();
    }

    public final AnalyzerT getAnalyzerEngine() {
        return (AnalyzerT) this.analyzerEngine.getValue();
    }

    private final Pair<JvmDescriptorMangler, SymbolTable> getManglerAndSymbolTable() {
        return (Pair) this.manglerAndSymbolTable.getValue();
    }

    public final JvmDescriptorMangler getMangler() {
        return getManglerAndSymbolTable().getFirst();
    }

    public final SymbolTable getSymbolTable() {
        return getManglerAndSymbolTable().getSecond();
    }
}
