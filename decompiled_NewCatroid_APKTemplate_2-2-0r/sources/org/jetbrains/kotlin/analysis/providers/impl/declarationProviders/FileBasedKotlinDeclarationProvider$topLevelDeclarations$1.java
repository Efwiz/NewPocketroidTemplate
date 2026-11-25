package org.jetbrains.kotlin.analysis.providers.impl.declarationProviders;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: compiled from: FileBasedKotlinDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.analysis.providers.impl.declarationProviders.FileBasedKotlinDeclarationProvider$topLevelDeclarations$1", f = "FileBasedKotlinDeclarationProvider.kt", i = {0, 1}, l = {24, 26}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "$this$sequence"}, s = {"L$0", "L$0"})
final class FileBasedKotlinDeclarationProvider$topLevelDeclarations$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super KtDeclaration>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ FileBasedKotlinDeclarationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FileBasedKotlinDeclarationProvider$topLevelDeclarations$1(FileBasedKotlinDeclarationProvider fileBasedKotlinDeclarationProvider, Continuation<? super FileBasedKotlinDeclarationProvider$topLevelDeclarations$1> continuation) {
        super(2, continuation);
        this.this$0 = fileBasedKotlinDeclarationProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FileBasedKotlinDeclarationProvider$topLevelDeclarations$1 fileBasedKotlinDeclarationProvider$topLevelDeclarations$1 = new FileBasedKotlinDeclarationProvider$topLevelDeclarations$1(this.this$0, continuation);
        fileBasedKotlinDeclarationProvider$topLevelDeclarations$1.L$0 = obj;
        return fileBasedKotlinDeclarationProvider$topLevelDeclarations$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super KtDeclaration> sequenceScope, Continuation<? super Unit> continuation) {
        return ((FileBasedKotlinDeclarationProvider$topLevelDeclarations$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Iterator<KtDeclaration> it;
        SequenceScope sequenceScope;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            it = this.this$0.getKotlinFile().getDeclarations().iterator();
            sequenceScope = sequenceScope2;
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            it = (Iterator) this.L$1;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (it.hasNext()) {
            KtDeclaration next = it.next();
            if (next instanceof KtScript) {
                List<KtDeclaration> declarations = ((KtScript) next).getDeclarations();
                Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.label = 1;
                if (sequenceScope.yieldAll(declarations, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.label = 2;
                if (sequenceScope.yield(next, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
        }
        return Unit.INSTANCE;
    }
}
