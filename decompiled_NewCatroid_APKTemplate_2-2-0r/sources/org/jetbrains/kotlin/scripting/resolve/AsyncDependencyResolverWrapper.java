package org.jetbrains.kotlin.scripting.resolve;

import java.util.Map;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.KotlinScriptExternalDependencies;
import kotlin.script.dependencies.ScriptContents;
import kotlin.script.dependencies.ScriptDependenciesResolver;
import kotlin.script.experimental.dependencies.AsyncDependenciesResolver;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.impl.RunSuspendKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: AsyncDependencyResolverWrapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J*\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\fj\u0002`\u000fH\u0016J0\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\fj\u0002`\u000fH\u0096@¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0003\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/AsyncDependencyResolverWrapper;", "Lkotlin/script/experimental/dependencies/AsyncDependenciesResolver;", "Lorg/jetbrains/kotlin/scripting/resolve/DependencyResolverWrapper;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lkotlin/script/experimental/dependencies/AsyncDependenciesResolver;)V", "getDelegate", "()Lkotlin/script/experimental/dependencies/AsyncDependenciesResolver;", "resolve", "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult;", "scriptContents", "Lkotlin/script/dependencies/ScriptContents;", "environment", "", "", "", "Lkotlin/script/dependencies/Environment;", "resolveAsync", "(Lkotlin/script/dependencies/ScriptContents;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AsyncDependencyResolverWrapper implements AsyncDependenciesResolver, DependencyResolverWrapper<AsyncDependenciesResolver> {
    private final AsyncDependenciesResolver delegate;

    public AsyncDependencyResolverWrapper(AsyncDependenciesResolver delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // kotlin.script.dependencies.ScriptDependenciesResolver
    public Future<KotlinScriptExternalDependencies> resolve(ScriptContents scriptContents, Map<String, ? extends Object> map, Function3<? super ScriptDependenciesResolver.ReportSeverity, ? super String, ? super ScriptContents.Position, Unit> function3, KotlinScriptExternalDependencies kotlinScriptExternalDependencies) {
        return AsyncDependenciesResolver.DefaultImpls.resolve(this, scriptContents, map, function3, kotlinScriptExternalDependencies);
    }

    @Override // org.jetbrains.kotlin.scripting.resolve.DependencyResolverWrapper
    public AsyncDependenciesResolver getDelegate() {
        return this.delegate;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.resolve.AsyncDependencyResolverWrapper$resolve$1, reason: invalid class name */
    /* JADX INFO: compiled from: AsyncDependencyResolverWrapper.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.scripting.resolve.AsyncDependencyResolverWrapper$resolve$1", f = "AsyncDependencyResolverWrapper.kt", i = {}, l = {23}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function1<Continuation<? super DependenciesResolver.ResolveResult>, Object> {
        final /* synthetic */ Map<String, Object> $environment;
        final /* synthetic */ ScriptContents $scriptContents;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ScriptContents scriptContents, Map<String, ? extends Object> map, Continuation<? super AnonymousClass1> continuation) {
            super(1, continuation);
            this.$scriptContents = scriptContents;
            this.$environment = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return AsyncDependencyResolverWrapper.this.new AnonymousClass1(this.$scriptContents, this.$environment, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super DependenciesResolver.ResolveResult> continuation) {
            return ((AnonymousClass1) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = AsyncDependencyResolverWrapper.this.getDelegate().resolveAsync(this.$scriptContents, this.$environment, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Override // kotlin.script.experimental.dependencies.AsyncDependenciesResolver, kotlin.script.experimental.dependencies.DependenciesResolver
    public DependenciesResolver.ResolveResult resolve(ScriptContents scriptContents, Map<String, ? extends Object> environment) {
        Intrinsics.checkNotNullParameter(scriptContents, "scriptContents");
        Intrinsics.checkNotNullParameter(environment, "environment");
        return (DependenciesResolver.ResolveResult) RunSuspendKt.internalScriptingRunSuspend(new AnonymousClass1(scriptContents, environment, null));
    }

    @Override // kotlin.script.experimental.dependencies.AsyncDependenciesResolver
    public Object resolveAsync(ScriptContents scriptContents, Map<String, ? extends Object> map, Continuation<? super DependenciesResolver.ResolveResult> continuation) {
        return getDelegate().resolveAsync(scriptContents, map, continuation);
    }
}
