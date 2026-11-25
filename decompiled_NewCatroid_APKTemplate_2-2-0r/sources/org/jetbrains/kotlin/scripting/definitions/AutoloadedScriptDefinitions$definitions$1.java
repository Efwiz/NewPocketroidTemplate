package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.jvm.util.JvmClasspathUtilKt;
import kotlin.sequences.SequenceScope;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: AutoloadedScriptDefinitions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.scripting.definitions.AutoloadedScriptDefinitions$definitions$1", f = "AutoloadedScriptDefinitions.kt", i = {}, l = {30}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class AutoloadedScriptDefinitions$definitions$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super ScriptDefinition>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AutoloadedScriptDefinitions this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AutoloadedScriptDefinitions$definitions$1(AutoloadedScriptDefinitions autoloadedScriptDefinitions, Continuation<? super AutoloadedScriptDefinitions$definitions$1> continuation) {
        super(2, continuation);
        this.this$0 = autoloadedScriptDefinitions;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        AutoloadedScriptDefinitions$definitions$1 autoloadedScriptDefinitions$definitions$1 = new AutoloadedScriptDefinitions$definitions$1(this.this$0, continuation);
        autoloadedScriptDefinitions$definitions$1.L$0 = obj;
        return autoloadedScriptDefinitions$definitions$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super ScriptDefinition> sequenceScope, Continuation<? super Unit> continuation) {
        return ((AutoloadedScriptDefinitions$definitions$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope = (SequenceScope) this.L$0;
            File[] fileArrListFiles = this.this$0.getBasePath().listFiles(new FileFilter() { // from class: org.jetbrains.kotlin.scripting.definitions.AutoloadedScriptDefinitions$definitions$1$$ExternalSyntheticLambda0
                @Override // java.io.FileFilter
                public final boolean accept(File file) {
                    return AutoloadedScriptDefinitions$definitions$1.invokeSuspend$lambda$1(file);
                }
            });
            List list = fileArrListFiles != null ? ArraysKt.toList(fileArrListFiles) : null;
            if (list != null && list.size() >= AutoloadedScriptDefinitions.MAIN_KTS_JARS.size()) {
                this.label = 1;
                if (sequenceScope.yieldAll(ScriptiDefinitionsFromClasspathDiscoverySourceKt.loadScriptTemplatesFromClasspath(CollectionsKt.listOf("org.jetbrains.kotlin.mainKts.MainKtsScript"), list, CollectionsKt.emptyList(), this.this$0.baseClassloader, this.this$0.hostConfiguration, this.this$0.messageReporter), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean invokeSuspend$lambda$1(File file) {
        List list = AutoloadedScriptDefinitions.MAIN_KTS_JARS;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (JvmClasspathUtilKt.matchMaybeVersionedFile(file, (String) it.next()) && Intrinsics.areEqual(FilesKt.getExtension(file), "jar")) {
                return true;
            }
        }
        return false;
    }
}
