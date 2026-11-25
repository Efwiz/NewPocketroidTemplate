package org.catrobat.catroid.ai;

import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveAutocompleteController$triggerInference$newBricks$1.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveAutocompleteController.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/catrobat/catroid/content/bricks/Brick;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ai.KoveAutocompleteController$triggerInference$newBricks$1", f = "KoveAutocompleteController.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class KoveAutocompleteController$triggerInference$newBricks$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends Brick>>, Object> {
    final /* synthetic */ String $resultDsl;
    final /* synthetic */ Sprite $sprite;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KoveAutocompleteController$triggerInference$newBricks$1(String str, Sprite sprite, Continuation<? super KoveAutocompleteController$triggerInference$newBricks$1> continuation) {
        super(2, continuation);
        this.$resultDsl = str;
        this.$sprite = sprite;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new KoveAutocompleteController$triggerInference$newBricks$1(this.$resultDsl, this.$sprite, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends Brick>> continuation) {
        return ((KoveAutocompleteController$triggerInference$newBricks$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        return KoveModelConverter.INSTANCE.buildBricks(new KoveParser(new KoveLexer(this.$resultDsl).tokenize()).parseBricks(), this.$sprite);
    }
}
