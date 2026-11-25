package org.jetbrains.kotlin.scripting.resolve;

import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.script.experimental.dependencies.AsyncDependenciesResolver;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: refineCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$result$1", f = "refineCompilationConfiguration.kt", i = {}, l = {276}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$result$1 extends SuspendLambda implements Function1<Continuation<? super DependenciesResolver.ResolveResult>, Object> {
    final /* synthetic */ Map<String, Object> $environment;
    final /* synthetic */ DependenciesResolver $resolver;
    final /* synthetic */ ScriptContentLoader.BasicScriptContents $scriptContents;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$result$1(DependenciesResolver dependenciesResolver, ScriptContentLoader.BasicScriptContents basicScriptContents, Map<String, ? extends Object> map, Continuation<? super RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$result$1> continuation) {
        super(1, continuation);
        this.$resolver = dependenciesResolver;
        this.$scriptContents = basicScriptContents;
        this.$environment = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$result$1(this.$resolver, this.$scriptContents, this.$environment, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation<? super DependenciesResolver.ResolveResult> continuation) {
        return ((RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$result$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            obj = ((AsyncDependenciesResolver) this.$resolver).resolveAsync(this.$scriptContents, this.$environment, this);
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
