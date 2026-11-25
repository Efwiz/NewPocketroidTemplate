package org.catrobat.catroid.libraryeditor.data;

import android.content.Context;
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
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel$saveCurrentSession$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: LibraryEditorViewModel.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$saveCurrentSession$1$1", f = "LibraryEditorViewModel.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class LibraryEditorViewModel$saveCurrentSession$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ LibraryDraft $draft;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LibraryEditorViewModel$saveCurrentSession$1$1(Context context, LibraryDraft libraryDraft, Continuation<? super LibraryEditorViewModel$saveCurrentSession$1$1> continuation) {
        super(2, continuation);
        this.$context = context;
        this.$draft = libraryDraft;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LibraryEditorViewModel$saveCurrentSession$1$1(this.$context, this.$draft, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LibraryEditorViewModel$saveCurrentSession$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        LibraryStorageManager libraryStorageManager = LibraryStorageManager.INSTANCE;
        Context context = this.$context;
        LibraryDraft draft = this.$draft;
        Intrinsics.checkNotNullExpressionValue(draft, "$draft");
        libraryStorageManager.saveSession(context, draft);
        return Unit.INSTANCE;
    }
}
