package org.catrobat.paintroid.iotasks;

import android.app.Activity;
import android.util.Log;
import java.io.File;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.paintroid.FileIO;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: CreateFile.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u000f2\u00020\u0001:\u0002\u000f\u0010B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\r\u001a\u00020\u000eR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/iotasks/CreateFile;", "", "callback", "Lorg/catrobat/paintroid/iotasks/CreateFile$CreateFileCallback;", "requestCode", "", "filename", "", "scopeIO", "Lkotlinx/coroutines/CoroutineScope;", "(Lorg/catrobat/paintroid/iotasks/CreateFile$CreateFileCallback;ILjava/lang/String;Lkotlinx/coroutines/CoroutineScope;)V", "callbackRef", "Ljava/lang/ref/WeakReference;", "execute", "", "Companion", "CreateFileCallback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CreateFile {
    private static final String TAG = "CreateFile";
    private final WeakReference<CreateFileCallback> callbackRef;
    private final String filename;
    private final int requestCode;
    private final CoroutineScope scopeIO;

    /* JADX INFO: compiled from: CreateFile.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/catrobat/paintroid/iotasks/CreateFile$CreateFileCallback;", "", "fileActivity", "Landroid/app/Activity;", "getFileActivity", "()Landroid/app/Activity;", "isFinishing", "", "()Z", "onCreateFilePostExecute", "", "requestCode", "", "file", "Ljava/io/File;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface CreateFileCallback {
        Activity getFileActivity();

        boolean isFinishing();

        void onCreateFilePostExecute(int requestCode, File file);
    }

    public CreateFile(CreateFileCallback callback, int i, String str, CoroutineScope scopeIO) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(scopeIO, "scopeIO");
        this.requestCode = i;
        this.filename = str;
        this.scopeIO = scopeIO;
        this.callbackRef = new WeakReference<>(callback);
    }

    public final void execute() {
        CreateFileCallback createFileCallback = this.callbackRef.get();
        BuildersKt__Builders_commonKt.launch$default(this.scopeIO, null, null, new AnonymousClass1(new Ref.ObjectRef(), this, createFileCallback, null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.iotasks.CreateFile$execute$1, reason: invalid class name */
    /* JADX INFO: compiled from: CreateFile.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.iotasks.CreateFile$execute$1", f = "CreateFile.kt", i = {}, l = {50}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CreateFileCallback $callback;
        final /* synthetic */ Ref.ObjectRef<File> $file;
        int label;
        final /* synthetic */ CreateFile this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Ref.ObjectRef<File> objectRef, CreateFile createFile, CreateFileCallback createFileCallback, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$file = objectRef;
            this.this$0 = createFile;
            this.$callback = createFileCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$file, this.this$0, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r3v5, types: [T, java.io.File] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    Ref.ObjectRef<File> objectRef = this.$file;
                    String str = this.this$0.filename;
                    CreateFileCallback createFileCallback = this.$callback;
                    objectRef.element = FileIO.createNewEmptyPictureFile(str, createFileCallback == null ? null : createFileCallback.getFileActivity());
                } catch (NullPointerException e) {
                    Log.e(CreateFile.TAG, "Can't create file", e);
                }
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02711(this.$callback, this.this$0, this.$file, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: org.catrobat.paintroid.iotasks.CreateFile$execute$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: CreateFile.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "org.catrobat.paintroid.iotasks.CreateFile$execute$1$1", f = "CreateFile.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02711 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ CreateFileCallback $callback;
            final /* synthetic */ Ref.ObjectRef<File> $file;
            int label;
            final /* synthetic */ CreateFile this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02711(CreateFileCallback createFileCallback, CreateFile createFile, Ref.ObjectRef<File> objectRef, Continuation<? super C02711> continuation) {
                super(2, continuation);
                this.$callback = createFileCallback;
                this.this$0 = createFile;
                this.$file = objectRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02711(this.$callback, this.this$0, this.$file, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                CreateFileCallback createFileCallback = this.$callback;
                if (createFileCallback != null && !createFileCallback.isFinishing()) {
                    this.$callback.onCreateFilePostExecute(this.this$0.requestCode, this.$file.element);
                }
                return Unit.INSTANCE;
            }
        }
    }
}
