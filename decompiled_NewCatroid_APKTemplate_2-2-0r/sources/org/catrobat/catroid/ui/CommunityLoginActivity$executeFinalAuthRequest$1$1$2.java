package org.catrobat.catroid.ui;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.databinding.ActivityCommunityLoginBinding;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityLoginActivity$executeFinalAuthRequest$1$1$2.class
  classes14.dex
 */
/* JADX INFO: compiled from: CommunityLoginActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.CommunityLoginActivity$executeFinalAuthRequest$1$1$2", f = "CommunityLoginActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class CommunityLoginActivity$executeFinalAuthRequest$1$1$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ CommunityLoginActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CommunityLoginActivity$executeFinalAuthRequest$1$1$2(CommunityLoginActivity communityLoginActivity, Continuation<? super CommunityLoginActivity$executeFinalAuthRequest$1$1$2> continuation) {
        super(2, continuation);
        this.this$0 = communityLoginActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CommunityLoginActivity$executeFinalAuthRequest$1$1$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CommunityLoginActivity$executeFinalAuthRequest$1$1$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        ActivityCommunityLoginBinding activityCommunityLoginBinding = this.this$0.binding;
        if (activityCommunityLoginBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding = null;
        }
        activityCommunityLoginBinding.btnAction.setEnabled(true);
        return Unit.INSTANCE;
    }
}
