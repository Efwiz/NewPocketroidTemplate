package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UninitializedPropertyAccessException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.script.experimental.api.SourceCode;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.repl.ILineId;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.NoScopeRecordCliBindingTrace;
import org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.container.ComponentProvider;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PackageViewDescriptorFactory;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.resolve.BindingTraceContext;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.TopDownAnalysisContext;
import org.jetbrains.kotlin.resolve.TopDownAnalysisMode;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoFactory;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.extensions.ExtraImportsProviderExtension;
import org.jetbrains.kotlin.resolve.lazy.FileScopeFactory;
import org.jetbrains.kotlin.resolve.lazy.FileScopeProviderKt;
import org.jetbrains.kotlin.resolve.lazy.FileScopes;
import org.jetbrains.kotlin.resolve.lazy.FileScopesCustomizer;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.declarations.CombinedPackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedDeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ReplPackageViewDescriptorFactory;
import org.jetbrains.kotlin.scripting.definitions.ScriptPriorities;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001:\u0004;<=>B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*J,\u0010+\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020(0-2\u0006\u0010)\u001a\u00020.2\u0006\u0010/\u001a\u000200J&\u00101\u001a\u00020&2\u0006\u00102\u001a\u00020(2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020(0-2\u0006\u0010)\u001a\u000203H\u0002J\u0018\u00104\u001a\u0002052\u0006\u0010'\u001a\u00020(2\u0006\u0010/\u001a\u000200H\u0004J\f\u00106\u001a\b\u0012\u0004\u0012\u0002030-J\u0014\u00107\u001a\b\u0012\u0004\u0012\u0002030-2\u0006\u00108\u001a\u000209J\u001e\u0010:\u001a\u00020\u001e2\u0006\u00102\u001a\u00020(2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020(0-H\u0004R\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001aX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTraceContext;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;Lorg/jetbrains/kotlin/resolve/BindingTraceContext;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;)V", "container", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "getContainer", "()Lorg/jetbrains/kotlin/container/ComponentProvider;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "replState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState;", "getReplState", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState;", "resolveSession", "Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "getResolveSession", "()Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "scriptDeclarationFactory", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ScriptMutableDeclarationProviderFactory;", "getScriptDeclarationFactory", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ScriptMutableDeclarationProviderFactory;", "topDownAnalysisContext", "Lorg/jetbrains/kotlin/resolve/TopDownAnalysisContext;", "getTopDownAnalysisContext", "()Lorg/jetbrains/kotlin/resolve/TopDownAnalysisContext;", "topDownAnalyzer", "Lorg/jetbrains/kotlin/resolve/LazyTopDownAnalyzer;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTraceContext;", "analyzeReplLine", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ReplLineAnalysisResult;", "psiFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "analyzeReplLineWithImportedScripts", "importedScripts", "", "Lkotlin/script/experimental/api/SourceCode;", "priority", "", "doAnalyze", "linePsi", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "prepareForAnalyze", "", "reset", "resetToLine", "lineId", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "runAnalyzer", "CompiledCode", "ReplLineAnalysisResult", "ResettableAnalyzerState", "ScriptMutableDeclarationProviderFactory", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ReplCodeAnalyzerBase {
    private final ComponentProvider container;
    private final ModuleDescriptorImpl module;
    private final ResettableAnalyzerState replState;
    private final ResolveSession resolveSession;
    private final ScriptMutableDeclarationProviderFactory scriptDeclarationFactory;
    private final TopDownAnalysisContext topDownAnalysisContext;
    private final LazyTopDownAnalyzer topDownAnalyzer;
    private final BindingTraceContext trace;

    public ReplCodeAnalyzerBase(KotlinCoreEnvironment environment, BindingTraceContext trace, ImplicitsExtensionsResolutionFilter implicitsExtensionsResolutionFilter) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.trace = trace;
        this.replState = new ResettableAnalyzerState();
        ComponentProvider componentProviderCreateContainer$default = TopDownAnalyzerFacadeForJVM.createContainer$default(TopDownAnalyzerFacadeForJVM.INSTANCE, environment.getProject(), CollectionsKt.emptyList(), trace, environment.getConfiguration(), new AnonymousClass1(environment), new Function2<StorageManager, Collection<? extends KtFile>, DeclarationProviderFactory>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.2
            @Override // kotlin.jvm.functions.Function2
            public final DeclarationProviderFactory invoke(StorageManager storageManager, Collection<? extends KtFile> collection) {
                Intrinsics.checkNotNullParameter(storageManager, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(collection, "<anonymous parameter 1>");
                return new ScriptMutableDeclarationProviderFactory();
            }
        }, null, null, null, implicitsExtensionsResolutionFilter, null, null, MapsKt.mapOf(TuplesKt.to(PackageViewDescriptorFactory.INSTANCE.getCAPABILITY(), ReplPackageViewDescriptorFactory.INSTANCE)), XKeySym.XK_Thai_phosamphao, null);
        this.container = componentProviderCreateContainer$default;
        this.module = (ModuleDescriptorImpl) DslKt.getService(componentProviderCreateContainer$default, ModuleDescriptorImpl.class);
        this.scriptDeclarationFactory = (ScriptMutableDeclarationProviderFactory) DslKt.getService(componentProviderCreateContainer$default, ScriptMutableDeclarationProviderFactory.class);
        ResolveSession resolveSession = (ResolveSession) DslKt.getService(componentProviderCreateContainer$default, ResolveSession.class);
        this.resolveSession = resolveSession;
        this.topDownAnalysisContext = new TopDownAnalysisContext(TopDownAnalysisMode.TopLevelDeclarations, DataFlowInfoFactory.EMPTY, resolveSession.getDeclarationScopeProvider());
        this.topDownAnalyzer = (LazyTopDownAnalyzer) DslKt.getService(componentProviderCreateContainer$default, LazyTopDownAnalyzer.class);
    }

    public /* synthetic */ ReplCodeAnalyzerBase(KotlinCoreEnvironment kotlinCoreEnvironment, NoScopeRecordCliBindingTrace noScopeRecordCliBindingTrace, ImplicitsExtensionsResolutionFilter implicitsExtensionsResolutionFilter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinCoreEnvironment, (i & 2) != 0 ? new NoScopeRecordCliBindingTrace() : noScopeRecordCliBindingTrace, (i & 4) != 0 ? null : implicitsExtensionsResolutionFilter);
    }

    public final BindingTraceContext getTrace() {
        return this.trace;
    }

    protected final ScriptMutableDeclarationProviderFactory getScriptDeclarationFactory() {
        return this.scriptDeclarationFactory;
    }

    protected final ComponentProvider getContainer() {
        return this.container;
    }

    protected final TopDownAnalysisContext getTopDownAnalysisContext() {
        return this.topDownAnalysisContext;
    }

    protected final ResolveSession getResolveSession() {
        return this.resolveSession;
    }

    protected final ResettableAnalyzerState getReplState() {
        return this.replState;
    }

    public final ModuleDescriptorImpl getModule() {
        return this.module;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase$1, reason: invalid class name */
    /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<GlobalSearchScope, JvmPackagePartProvider> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "createPackagePartProvider(Lcom/intellij/psi/search/GlobalSearchScope;)Lorg/jetbrains/kotlin/cli/jvm/compiler/JvmPackagePartProvider;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "createPackagePartProvider";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(KotlinCoreEnvironment.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final JvmPackagePartProvider invoke(GlobalSearchScope p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((KotlinCoreEnvironment) this.receiver).createPackagePartProvider(p0);
        }
    }

    /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0002\n\u000bR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ReplLineAnalysisResult;", "", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "getScriptDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "Successful", "WithErrors", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ReplLineAnalysisResult {
        Diagnostics getDiagnostics();

        ClassDescriptorWithResolutionScopes getScriptDescriptor();

        /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ReplLineAnalysisResult$Successful;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ReplLineAnalysisResult;", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;)V", "getDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getScriptDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Successful implements ReplLineAnalysisResult {
            private final Diagnostics diagnostics;
            private final ClassDescriptorWithResolutionScopes scriptDescriptor;

            public static /* synthetic */ Successful copy$default(Successful successful, ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes, Diagnostics diagnostics, int i, Object obj) {
                if ((i & 1) != 0) {
                    classDescriptorWithResolutionScopes = successful.scriptDescriptor;
                }
                if ((i & 2) != 0) {
                    diagnostics = successful.diagnostics;
                }
                return successful.copy(classDescriptorWithResolutionScopes, diagnostics);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final ClassDescriptorWithResolutionScopes getScriptDescriptor() {
                return this.scriptDescriptor;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final Diagnostics getDiagnostics() {
                return this.diagnostics;
            }

            public final Successful copy(ClassDescriptorWithResolutionScopes scriptDescriptor, Diagnostics diagnostics) {
                Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
                Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
                return new Successful(scriptDescriptor, diagnostics);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Successful)) {
                    return false;
                }
                Successful successful = (Successful) other;
                return Intrinsics.areEqual(this.scriptDescriptor, successful.scriptDescriptor) && Intrinsics.areEqual(this.diagnostics, successful.diagnostics);
            }

            public int hashCode() {
                return (this.scriptDescriptor.hashCode() * 31) + this.diagnostics.hashCode();
            }

            public String toString() {
                return "Successful(scriptDescriptor=" + this.scriptDescriptor + ", diagnostics=" + this.diagnostics + ')';
            }

            public Successful(ClassDescriptorWithResolutionScopes scriptDescriptor, Diagnostics diagnostics) {
                Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
                Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
                this.scriptDescriptor = scriptDescriptor;
                this.diagnostics = diagnostics;
            }

            @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ReplLineAnalysisResult
            public ClassDescriptorWithResolutionScopes getScriptDescriptor() {
                return this.scriptDescriptor;
            }

            @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ReplLineAnalysisResult
            public Diagnostics getDiagnostics() {
                return this.diagnostics;
            }
        }

        /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
        @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ReplLineAnalysisResult$WithErrors;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ReplLineAnalysisResult;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "(Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;)V", "getDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "getScriptDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class WithErrors implements ReplLineAnalysisResult {
            private final Diagnostics diagnostics;

            public static /* synthetic */ WithErrors copy$default(WithErrors withErrors, Diagnostics diagnostics, int i, Object obj) {
                if ((i & 1) != 0) {
                    diagnostics = withErrors.diagnostics;
                }
                return withErrors.copy(diagnostics);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final Diagnostics getDiagnostics() {
                return this.diagnostics;
            }

            public final WithErrors copy(Diagnostics diagnostics) {
                Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
                return new WithErrors(diagnostics);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof WithErrors) && Intrinsics.areEqual(this.diagnostics, ((WithErrors) other).diagnostics);
            }

            @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ReplLineAnalysisResult
            public ClassDescriptorWithResolutionScopes getScriptDescriptor() {
                return null;
            }

            public int hashCode() {
                return this.diagnostics.hashCode();
            }

            public String toString() {
                return "WithErrors(diagnostics=" + this.diagnostics + ')';
            }

            public WithErrors(Diagnostics diagnostics) {
                Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
                this.diagnostics = diagnostics;
            }

            @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ReplLineAnalysisResult
            public Diagnostics getDiagnostics() {
                return this.diagnostics;
            }
        }
    }

    public final List<SourceCodeByReplLine> resetToLine(ILineId lineId) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        return this.replState.resetToLine(lineId);
    }

    public final List<SourceCodeByReplLine> reset() {
        return this.replState.reset();
    }

    protected final void prepareForAnalyze(KtFile psiFile, int priority) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        this.topDownAnalysisContext.getScripts().clear();
        this.trace.clearDiagnostics();
        KtScript script = psiFile.getScript();
        Intrinsics.checkNotNull(script);
        script.putUserData(ScriptPriorities.PRIORITY_KEY, Integer.valueOf(priority));
    }

    public final ReplLineAnalysisResult analyzeReplLine(KtFile psiFile, ReplCodeLine codeLine) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        prepareForAnalyze(psiFile, codeLine.getNo());
        return doAnalyze(psiFile, CollectionsKt.emptyList(), ReplCodeAnalyzerKt.toSourceCode(codeLine));
    }

    public final ReplLineAnalysisResult analyzeReplLineWithImportedScripts(KtFile psiFile, List<? extends KtFile> importedScripts, SourceCode codeLine, int priority) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        Intrinsics.checkNotNullParameter(importedScripts, "importedScripts");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        prepareForAnalyze(psiFile, priority);
        return doAnalyze(psiFile, importedScripts, ReplCodeAnalyzerKt.addNo(codeLine, priority));
    }

    protected final TopDownAnalysisContext runAnalyzer(KtFile linePsi, List<? extends KtFile> importedScripts) {
        Intrinsics.checkNotNullParameter(linePsi, "linePsi");
        Intrinsics.checkNotNullParameter(importedScripts, "importedScripts");
        LazyTopDownAnalyzer lazyTopDownAnalyzer = this.topDownAnalyzer;
        TopDownAnalysisMode topDownAnalysisMode = this.topDownAnalysisContext.getTopDownAnalysisMode();
        Intrinsics.checkNotNullExpressionValue(topDownAnalysisMode, "getTopDownAnalysisMode(...)");
        return LazyTopDownAnalyzer.analyzeDeclarations$default(lazyTopDownAnalyzer, topDownAnalysisMode, CollectionsKt.plus((Collection) CollectionsKt.listOf(linePsi), (Iterable) importedScripts), null, null, 12, null);
    }

    private final ReplLineAnalysisResult doAnalyze(KtFile linePsi, List<? extends KtFile> importedScripts, SourceCodeByReplLine codeLine) {
        this.scriptDeclarationFactory.setDelegateFactory(new FileBasedDeclarationProviderFactory(this.resolveSession.getStorageManager(), CollectionsKt.plus((Collection) CollectionsKt.listOf(linePsi), (Iterable) importedScripts)));
        this.replState.submitLine(linePsi);
        TopDownAnalysisContext topDownAnalysisContextRunAnalyzer = runAnalyzer(linePsi, importedScripts);
        Diagnostics diagnostics = this.trace.getContext().getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        Diagnostics diagnostics2 = diagnostics;
        if (!(diagnostics2 instanceof Collection) || !((Collection) diagnostics2).isEmpty()) {
            Iterator<T> it = diagnostics2.iterator();
            while (it.hasNext()) {
                if (((Diagnostic) it.next()).getSeverity() == Severity.ERROR) {
                    this.replState.lineFailure(linePsi);
                    return new ReplLineAnalysisResult.WithErrors(diagnostics);
                }
            }
        }
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = topDownAnalysisContextRunAnalyzer.getScripts().get(linePsi.getScript());
        Intrinsics.checkNotNull(classDescriptorWithResolutionScopes);
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes2 = classDescriptorWithResolutionScopes;
        this.replState.lineSuccess(linePsi, codeLine, classDescriptorWithResolutionScopes2);
        return new ReplLineAnalysisResult.Successful(classDescriptorWithResolutionScopes2, diagnostics);
    }

    /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\tH\u0016J\u000e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0001R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ScriptMutableDeclarationProviderFactory;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "()V", "delegateFactory", "rootPackageProvider", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ScriptMutableDeclarationProviderFactory$AdaptablePackageMemberDeclarationProvider;", "diagnoseMissingPackageFragment", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getClassMemberDeclarationProvider", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "classLikeInfo", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "getPackageMemberDeclarationProvider", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "packageFqName", "setDelegateFactory", "AdaptablePackageMemberDeclarationProvider", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class ScriptMutableDeclarationProviderFactory implements DeclarationProviderFactory {
        private DeclarationProviderFactory delegateFactory;
        private AdaptablePackageMemberDeclarationProvider rootPackageProvider;

        public final void setDelegateFactory(DeclarationProviderFactory delegateFactory) {
            Intrinsics.checkNotNullParameter(delegateFactory, "delegateFactory");
            this.delegateFactory = delegateFactory;
            PackageMemberDeclarationProvider packageMemberDeclarationProvider = delegateFactory.getPackageMemberDeclarationProvider(FqName.ROOT);
            Intrinsics.checkNotNull(packageMemberDeclarationProvider);
            try {
                AdaptablePackageMemberDeclarationProvider adaptablePackageMemberDeclarationProvider = this.rootPackageProvider;
                if (adaptablePackageMemberDeclarationProvider == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rootPackageProvider");
                    adaptablePackageMemberDeclarationProvider = null;
                }
                adaptablePackageMemberDeclarationProvider.addDelegateProvider(packageMemberDeclarationProvider);
            } catch (UninitializedPropertyAccessException unused) {
                this.rootPackageProvider = new AdaptablePackageMemberDeclarationProvider(packageMemberDeclarationProvider);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
        public ClassMemberDeclarationProvider getClassMemberDeclarationProvider(KtClassLikeInfo classLikeInfo) {
            Intrinsics.checkNotNullParameter(classLikeInfo, "classLikeInfo");
            DeclarationProviderFactory declarationProviderFactory = this.delegateFactory;
            if (declarationProviderFactory == null) {
                Intrinsics.throwUninitializedPropertyAccessException("delegateFactory");
                declarationProviderFactory = null;
            }
            ClassMemberDeclarationProvider classMemberDeclarationProvider = declarationProviderFactory.getClassMemberDeclarationProvider(classLikeInfo);
            Intrinsics.checkNotNullExpressionValue(classMemberDeclarationProvider, "getClassMemberDeclarationProvider(...)");
            return classMemberDeclarationProvider;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
        public PackageMemberDeclarationProvider getPackageMemberDeclarationProvider(FqName packageFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            DeclarationProviderFactory declarationProviderFactory = null;
            AdaptablePackageMemberDeclarationProvider adaptablePackageMemberDeclarationProvider = null;
            if (packageFqName.isRoot()) {
                AdaptablePackageMemberDeclarationProvider adaptablePackageMemberDeclarationProvider2 = this.rootPackageProvider;
                if (adaptablePackageMemberDeclarationProvider2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rootPackageProvider");
                } else {
                    adaptablePackageMemberDeclarationProvider = adaptablePackageMemberDeclarationProvider2;
                }
                return adaptablePackageMemberDeclarationProvider;
            }
            DeclarationProviderFactory declarationProviderFactory2 = this.delegateFactory;
            if (declarationProviderFactory2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("delegateFactory");
            } else {
                declarationProviderFactory = declarationProviderFactory2;
            }
            return declarationProviderFactory.getPackageMemberDeclarationProvider(packageFqName);
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
        public void diagnoseMissingPackageFragment(FqName fqName, KtFile file) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            DeclarationProviderFactory declarationProviderFactory = this.delegateFactory;
            if (declarationProviderFactory == null) {
                Intrinsics.throwUninitializedPropertyAccessException("delegateFactory");
                declarationProviderFactory = null;
            }
            declarationProviderFactory.diagnoseMissingPackageFragment(fqName, file);
        }

        /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ScriptMutableDeclarationProviderFactory$AdaptablePackageMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/DelegatePackageMemberDeclarationProvider;", "delegateProvider", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "(Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;)V", "addDelegateProvider", "", Constants.EXTRA_PROVIDER, PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class AdaptablePackageMemberDeclarationProvider extends DelegatePackageMemberDeclarationProvider {
            private PackageMemberDeclarationProvider delegateProvider;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AdaptablePackageMemberDeclarationProvider(PackageMemberDeclarationProvider delegateProvider) {
                super(delegateProvider);
                Intrinsics.checkNotNullParameter(delegateProvider, "delegateProvider");
                this.delegateProvider = delegateProvider;
            }

            public final void addDelegateProvider(PackageMemberDeclarationProvider provider) {
                List listListOf;
                Intrinsics.checkNotNullParameter(provider, "provider");
                PackageMemberDeclarationProvider packageMemberDeclarationProvider = this.delegateProvider;
                CombinedPackageMemberDeclarationProvider combinedPackageMemberDeclarationProvider = packageMemberDeclarationProvider instanceof CombinedPackageMemberDeclarationProvider ? (CombinedPackageMemberDeclarationProvider) packageMemberDeclarationProvider : null;
                if (combinedPackageMemberDeclarationProvider != null) {
                    listListOf = CollectionsKt.plus((Collection) CollectionsKt.listOf(provider), (Iterable) combinedPackageMemberDeclarationProvider.getProviders());
                } else {
                    listListOf = CollectionsKt.listOf((Object[]) new PackageMemberDeclarationProvider[]{provider, packageMemberDeclarationProvider});
                }
                CombinedPackageMemberDeclarationProvider combinedPackageMemberDeclarationProvider2 = new CombinedPackageMemberDeclarationProvider(listListOf);
                this.delegateProvider = combinedPackageMemberDeclarationProvider2;
                setDelegate(combinedPackageMemberDeclarationProvider2);
            }
        }
    }

    /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$CompiledCode;", "", "className", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;)V", "getClassName", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class CompiledCode {
        private final String className;
        private final SourceCodeByReplLine source;

        public static /* synthetic */ CompiledCode copy$default(CompiledCode compiledCode, String str, SourceCodeByReplLine sourceCodeByReplLine, int i, Object obj) {
            if ((i & 1) != 0) {
                str = compiledCode.className;
            }
            if ((i & 2) != 0) {
                sourceCodeByReplLine = compiledCode.source;
            }
            return compiledCode.copy(str, sourceCodeByReplLine);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getClassName() {
            return this.className;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final SourceCodeByReplLine getSource() {
            return this.source;
        }

        public final CompiledCode copy(String className, SourceCodeByReplLine source) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(source, "source");
            return new CompiledCode(className, source);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CompiledCode)) {
                return false;
            }
            CompiledCode compiledCode = (CompiledCode) other;
            return Intrinsics.areEqual(this.className, compiledCode.className) && Intrinsics.areEqual(this.source, compiledCode.source);
        }

        public int hashCode() {
            return (this.className.hashCode() * 31) + this.source.hashCode();
        }

        public String toString() {
            return "CompiledCode(className=" + this.className + ", source=" + this.source + ')';
        }

        public CompiledCode(String className, SourceCodeByReplLine source) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(source, "source");
            this.className = className;
            this.source = source;
        }

        public final String getClassName() {
            return this.className;
        }

        public final SourceCodeByReplLine getSource() {
            return this.source;
        }
    }

    /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u001fB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0013\u001a\u00020\u0005H\u0002J\u001e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u001aJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00160\u001a2\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\u0002¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState;", "", "()V", "submittedLines", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo;", "Lkotlin/collections/HashMap;", "successfulLines", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ResettableSnippetsHistory;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", "getSuccessfulLines$annotations", "computeFileScopes", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "lineInfo", "fileScopeFactory", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory;", "lineFailure", "", "ktFile", "lineSuccess", "codeLine", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "reset", "", "resetToLine", "lineId", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "submitLine", "LineInfo", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ResettableAnalyzerState {
        private final ResettableSnippetsHistory<LineInfo.SuccessfulLine> successfulLines = new ResettableSnippetsHistory<>(null, 1, null);
        private final HashMap<KtFile, LineInfo> submittedLines = new HashMap<>();

        private static /* synthetic */ void getSuccessfulLines$annotations() {
        }

        public final List<SourceCodeByReplLine> resetToLine(ILineId lineId) {
            Intrinsics.checkNotNullParameter(lineId, "lineId");
            List<Pair<SourceCodeByReplLine, LineInfo.SuccessfulLine>> listResetToLine = this.successfulLines.resetToLine(lineId);
            Iterator<T> it = listResetToLine.iterator();
            while (it.hasNext()) {
                this.submittedLines.remove(((LineInfo.SuccessfulLine) ((Pair) it.next()).getSecond()).getLinePsi());
            }
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listResetToLine, 10));
            Iterator<T> it2 = listResetToLine.iterator();
            while (it2.hasNext()) {
                arrayList.add((SourceCodeByReplLine) ((Pair) it2.next()).getFirst());
            }
            return arrayList;
        }

        public final List<SourceCodeByReplLine> reset() {
            this.submittedLines.clear();
            List<Pair<SourceCodeByReplLine, LineInfo.SuccessfulLine>> listReset = this.successfulLines.reset();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listReset, 10));
            Iterator<T> it = listReset.iterator();
            while (it.hasNext()) {
                arrayList.add((SourceCodeByReplLine) ((Pair) it.next()).getFirst());
            }
            return arrayList;
        }

        public final void submitLine(final KtFile ktFile) {
            Intrinsics.checkNotNullParameter(ktFile, "ktFile");
            this.submittedLines.put(ktFile, new LineInfo.SubmittedLine(ktFile, this.successfulLines.lastValue()));
            FileScopeProviderKt.setFileScopesCustomizer(ktFile, new FileScopesCustomizer() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase$ResettableAnalyzerState$submitLine$1
                @Override // org.jetbrains.kotlin.resolve.lazy.FileScopesCustomizer
                public FileScopes createFileScopes(FileScopeFactory fileScopeFactory) {
                    FileScopes fileScopesComputeFileScopes;
                    Intrinsics.checkNotNullParameter(fileScopeFactory, "fileScopeFactory");
                    ReplCodeAnalyzerBase.ResettableAnalyzerState.LineInfo lineInfo = this.this$0.lineInfo(ktFile);
                    return (lineInfo == null || (fileScopesComputeFileScopes = this.this$0.computeFileScopes(lineInfo, fileScopeFactory)) == null) ? FileScopeFactory.createScopesForFile$default(fileScopeFactory, ktFile, null, false, 6, null) : fileScopesComputeFileScopes;
                }
            });
        }

        public final void lineSuccess(KtFile ktFile, SourceCodeByReplLine codeLine, ClassDescriptorWithResolutionScopes scriptDescriptor) {
            Intrinsics.checkNotNullParameter(ktFile, "ktFile");
            Intrinsics.checkNotNullParameter(codeLine, "codeLine");
            Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
            LineInfo.SuccessfulLine successfulLine = new LineInfo.SuccessfulLine(ktFile, this.successfulLines.lastValue(), scriptDescriptor);
            this.submittedLines.put(ktFile, successfulLine);
            ResettableSnippetsHistory<LineInfo.SuccessfulLine> resettableSnippetsHistory = this.successfulLines;
            String name2 = ktFile.get_name();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            resettableSnippetsHistory.add(new CompiledCode(name2, codeLine), successfulLine);
        }

        public final void lineFailure(KtFile ktFile) {
            Intrinsics.checkNotNullParameter(ktFile, "ktFile");
            this.submittedLines.put(ktFile, new LineInfo.FailedLine(ktFile, this.successfulLines.lastValue()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final LineInfo lineInfo(KtFile ktFile) {
            return this.submittedLines.get(ktFile);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u000b\f\rB\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0003\u000e\u000f\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo;", "", "()V", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", "getParentLine", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", "FailedLine", "SubmittedLine", "SuccessfulLine", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$FailedLine;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SubmittedLine;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
        static abstract class LineInfo {
            public /* synthetic */ LineInfo(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public abstract KtFile getLinePsi();

            public abstract SuccessfulLine getParentLine();

            private LineInfo() {
            }

            /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
            @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SubmittedLine;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo;", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;)V", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getParentLine", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
            public static final class SubmittedLine extends LineInfo {
                private final KtFile linePsi;
                private final SuccessfulLine parentLine;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public SubmittedLine(KtFile linePsi, SuccessfulLine successfulLine) {
                    super(null);
                    Intrinsics.checkNotNullParameter(linePsi, "linePsi");
                    this.linePsi = linePsi;
                    this.parentLine = successfulLine;
                }

                @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ResettableAnalyzerState.LineInfo
                public KtFile getLinePsi() {
                    return this.linePsi;
                }

                @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ResettableAnalyzerState.LineInfo
                public SuccessfulLine getParentLine() {
                    return this.parentLine;
                }
            }

            /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
            @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0000X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo;", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "lineDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;)V", "getLineDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getParentLine", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
            public static final class SuccessfulLine extends LineInfo {
                private final ClassDescriptorWithResolutionScopes lineDescriptor;
                private final KtFile linePsi;
                private final SuccessfulLine parentLine;

                @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ResettableAnalyzerState.LineInfo
                public KtFile getLinePsi() {
                    return this.linePsi;
                }

                @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ResettableAnalyzerState.LineInfo
                public SuccessfulLine getParentLine() {
                    return this.parentLine;
                }

                public final ClassDescriptorWithResolutionScopes getLineDescriptor() {
                    return this.lineDescriptor;
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public SuccessfulLine(KtFile linePsi, SuccessfulLine successfulLine, ClassDescriptorWithResolutionScopes lineDescriptor) {
                    super(null);
                    Intrinsics.checkNotNullParameter(linePsi, "linePsi");
                    Intrinsics.checkNotNullParameter(lineDescriptor, "lineDescriptor");
                    this.linePsi = linePsi;
                    this.parentLine = successfulLine;
                    this.lineDescriptor = lineDescriptor;
                }
            }

            /* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
            @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$FailedLine;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo;", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;)V", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getParentLine", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$ResettableAnalyzerState$LineInfo$SuccessfulLine;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
            public static final class FailedLine extends LineInfo {
                private final KtFile linePsi;
                private final SuccessfulLine parentLine;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public FailedLine(KtFile linePsi, SuccessfulLine successfulLine) {
                    super(null);
                    Intrinsics.checkNotNullParameter(linePsi, "linePsi");
                    this.linePsi = linePsi;
                    this.parentLine = successfulLine;
                }

                @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ResettableAnalyzerState.LineInfo
                public KtFile getLinePsi() {
                    return this.linePsi;
                }

                @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase.ResettableAnalyzerState.LineInfo
                public SuccessfulLine getParentLine() {
                    return this.parentLine;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:8:0x002d  */
        public final FileScopes computeFileScopes(LineInfo lineInfo, FileScopeFactory fileScopeFactory) {
            boolean z;
            ClassDescriptorWithResolutionScopes lineDescriptor;
            LexicalScope scopeForInitializerResolution;
            KtFile linePsi = lineInfo.getLinePsi();
            if (linePsi.getImportDirectives().isEmpty()) {
                ExtraImportsProviderExtension.Companion companion = ExtraImportsProviderExtension.INSTANCE;
                Project project = linePsi.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                if (companion.getInstance(project).getExtraImports(linePsi).isEmpty()) {
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = true;
            }
            LineInfo.SuccessfulLine parentLine = lineInfo.getParentLine();
            if (parentLine == null || (lineDescriptor = parentLine.getLineDescriptor()) == null || (scopeForInitializerResolution = lineDescriptor.getScopeForInitializerResolution()) == null) {
                return null;
            }
            for (HierarchicalScope hierarchicalScope : ScopeUtilsKt.getParentsWithSelf(scopeForInitializerResolution)) {
                if (hierarchicalScope instanceof ImportingScope) {
                    Intrinsics.checkNotNull(hierarchicalScope, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.ImportingScope");
                    FileScopes fileScopesCreateScopesForFile = fileScopeFactory.createScopesForFile(linePsi, (ImportingScope) hierarchicalScope, false);
                    if (z) {
                        scopeForInitializerResolution = ScopeUtilsKt.replaceImportingScopes(scopeForInitializerResolution, fileScopesCreateScopesForFile.getImportingScope());
                    }
                    return new FileScopes(scopeForInitializerResolution, fileScopesCreateScopesForFile.getImportingScope(), fileScopesCreateScopesForFile.getImportForceResolver());
                }
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
    }
}
