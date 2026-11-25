package org.jetbrains.kotlin.scripting.resolve;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.KotlinScriptExternalDependencies;
import kotlin.script.dependencies.ScriptContents;
import kotlin.script.dependencies.ScriptDependenciesResolver;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import kotlin.script.experimental.dependencies.ScriptReport;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: legacyWrappers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J*\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0010j\u0002`\u0013H\u0016J\f\u0010\u0014\u001a\u00020\u0015*\u00020\u0016H\u0002J\f\u0010\u0017\u001a\u00020\u0018*\u00020\u0019H\u0002R\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ApiChangeDependencyResolverWrapper;", "Lkotlin/script/experimental/dependencies/DependenciesResolver;", "Lorg/jetbrains/kotlin/scripting/resolve/DependencyResolverWrapper;", "Lkotlin/script/dependencies/ScriptDependenciesResolver;", "Lorg/jetbrains/kotlin/scripting/resolve/LegacyResolverWrapper;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lkotlin/script/dependencies/ScriptDependenciesResolver;)V", "getDelegate", "()Lkotlin/script/dependencies/ScriptDependenciesResolver;", "previousDependencies", "Lkotlin/script/dependencies/KotlinScriptExternalDependencies;", "resolve", "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult;", "scriptContents", "Lkotlin/script/dependencies/ScriptContents;", "environment", "", "", "", "Lkotlin/script/dependencies/Environment;", "convertPosition", "Lkotlin/script/experimental/dependencies/ScriptReport$Position;", "Lkotlin/script/dependencies/ScriptContents$Position;", "convertSeverity", "Lkotlin/script/experimental/dependencies/ScriptReport$Severity;", "Lkotlin/script/dependencies/ScriptDependenciesResolver$ReportSeverity;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApiChangeDependencyResolverWrapper implements DependenciesResolver, DependencyResolverWrapper<ScriptDependenciesResolver>, LegacyResolverWrapper {
    private final ScriptDependenciesResolver delegate;
    private KotlinScriptExternalDependencies previousDependencies;

    /* JADX INFO: compiled from: legacyWrappers.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ScriptDependenciesResolver.ReportSeverity.values().length];
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.FATAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ApiChangeDependencyResolverWrapper(ScriptDependenciesResolver delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // kotlin.script.dependencies.ScriptDependenciesResolver
    public Future<KotlinScriptExternalDependencies> resolve(ScriptContents scriptContents, Map<String, ? extends Object> map, Function3<? super ScriptDependenciesResolver.ReportSeverity, ? super String, ? super ScriptContents.Position, Unit> function3, KotlinScriptExternalDependencies kotlinScriptExternalDependencies) {
        return DependenciesResolver.DefaultImpls.resolve(this, scriptContents, map, function3, kotlinScriptExternalDependencies);
    }

    @Override // org.jetbrains.kotlin.scripting.resolve.DependencyResolverWrapper
    public ScriptDependenciesResolver getDelegate() {
        return this.delegate;
    }

    @Override // kotlin.script.experimental.dependencies.DependenciesResolver
    public DependenciesResolver.ResolveResult resolve(ScriptContents scriptContents, Map<String, ? extends Object> environment) throws ExecutionException, InterruptedException {
        Intrinsics.checkNotNullParameter(scriptContents, "scriptContents");
        Intrinsics.checkNotNullParameter(environment, "environment");
        final ArrayList arrayList = new ArrayList();
        KotlinScriptExternalDependencies kotlinScriptExternalDependencies = getDelegate().resolve(scriptContents, environment, new Function3<ScriptDependenciesResolver.ReportSeverity, String, ScriptContents.Position, Unit>() { // from class: org.jetbrains.kotlin.scripting.resolve.ApiChangeDependencyResolverWrapper$resolve$legacyDeps$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ScriptDependenciesResolver.ReportSeverity reportSeverity, String str, ScriptContents.Position position) {
                invoke2(reportSeverity, str, position);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptDependenciesResolver.ReportSeverity sev, String msg, ScriptContents.Position position) {
                Intrinsics.checkNotNullParameter(sev, "sev");
                Intrinsics.checkNotNullParameter(msg, "msg");
                arrayList.add(new ScriptReport(msg, this.convertSeverity(sev), position != null ? this.convertPosition(position) : null));
            }
        }, this.previousDependencies).get();
        if (kotlinScriptExternalDependencies == null) {
            return new DependenciesResolver.ResolveResult.Failure(arrayList);
        }
        String javaHome = kotlinScriptExternalDependencies.getJavaHome();
        ScriptDependencies scriptDependencies = new ScriptDependencies(javaHome != null ? new File(javaHome) : null, CollectionsKt.toList(kotlinScriptExternalDependencies.getClasspath()), CollectionsKt.toList(kotlinScriptExternalDependencies.getImports()), CollectionsKt.toList(kotlinScriptExternalDependencies.getSources()), CollectionsKt.toList(kotlinScriptExternalDependencies.getScripts()));
        this.previousDependencies = kotlinScriptExternalDependencies;
        return new DependenciesResolver.ResolveResult.Success(scriptDependencies, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ScriptReport.Severity convertSeverity(ScriptDependenciesResolver.ReportSeverity reportSeverity) {
        int i = WhenMappings.$EnumSwitchMapping$0[reportSeverity.ordinal()];
        if (i == 1) {
            return ScriptReport.Severity.FATAL;
        }
        if (i == 2) {
            return ScriptReport.Severity.ERROR;
        }
        if (i == 3) {
            return ScriptReport.Severity.WARNING;
        }
        if (i == 4) {
            return ScriptReport.Severity.INFO;
        }
        if (i == 5) {
            return ScriptReport.Severity.DEBUG;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ScriptReport.Position convertPosition(ScriptContents.Position position) {
        return new ScriptReport.Position(position.getLine(), position.getCol(), null, null, 12, null);
    }
}
