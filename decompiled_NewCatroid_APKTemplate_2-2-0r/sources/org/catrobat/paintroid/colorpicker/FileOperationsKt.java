package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FileOperations.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003\u001a#\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"HUNDRED", "", "TAG", "", "deleteBitmapFile", "", "context", "Landroid/content/Context;", "imageName", "loadBitmapByName", "Landroid/graphics/Bitmap;", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "storeBitmapTemporally", "", "bitmap", "colorpicker_signedRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class FileOperationsKt {
    private static final int HUNDRED = 100;
    private static final String TAG = "FileOperations";

    /* JADX INFO: renamed from: org.catrobat.paintroid.colorpicker.FileOperationsKt$loadBitmapByName$1, reason: invalid class name */
    /* JADX INFO: compiled from: FileOperations.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.colorpicker.FileOperationsKt", f = "FileOperations.kt", i = {0}, l = {55}, m = "loadBitmapByName", n = {"bitmap"}, s = {"L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FileOperationsKt.loadBitmapByName(null, null, this);
        }
    }

    public static final void storeBitmapTemporally(Bitmap bitmap, Context context, String imageName) throws IOException {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(imageName, "imageName");
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
            fileOutputStreamOpenFileOutput = context.openFileOutput(imageName, 0);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStreamOpenFileOutput);
            fileOutputStreamOpenFileOutput.flush();
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                Log.e(TAG, "FileNotFoundException: " + e.getMessage());
            } else if (e instanceof IOException) {
                Log.e(TAG, "IOException: " + e.getMessage());
            }
        } finally {
            if (fileOutputStreamOpenFileOutput != null) {
                fileOutputStreamOpenFileOutput.close();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final Object loadBitmapByName(Context context, String str, Continuation<? super Bitmap> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Ref.ObjectRef objectRef;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            CoroutineDispatcher io2 = Dispatchers.getIO();
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(context, str, objectRef2, null);
            anonymousClass1.L$0 = objectRef2;
            anonymousClass1.label = 1;
            if (BuildersKt.withContext(io2, anonymousClass2, anonymousClass1) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            objectRef = (Ref.ObjectRef) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return objectRef.element;
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.colorpicker.FileOperationsKt$loadBitmapByName$2, reason: invalid class name */
    /* JADX INFO: compiled from: FileOperations.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.colorpicker.FileOperationsKt$loadBitmapByName$2", f = "FileOperations.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef<Bitmap> $bitmap;
        final /* synthetic */ Context $context;
        final /* synthetic */ String $imageName;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Context context, String str, Ref.ObjectRef<Bitmap> objectRef, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$imageName = str;
            this.$bitmap = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$context, this.$imageName, this.$bitmap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v3, types: [T, android.graphics.Bitmap] */
        /* JADX WARN: Type inference failed for: r3v10 */
        /* JADX WARN: Type inference failed for: r3v11 */
        /* JADX WARN: Type inference failed for: r3v12 */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v5, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r3v6, types: [java.lang.Object, kotlin.Unit] */
        /* JADX WARN: Type inference failed for: r3v7, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r3v8, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r3v9 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            ?? r3;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                ?? OpenFileInput = 0;
                OpenFileInput = 0;
                OpenFileInput = 0;
                try {
                    try {
                        OpenFileInput = this.$context.openFileInput(this.$imageName);
                        this.$bitmap.element = BitmapFactory.decodeStream((InputStream) OpenFileInput);
                        r3 = OpenFileInput;
                        if (OpenFileInput != 0) {
                            r3.close();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        r3 = OpenFileInput;
                        if (OpenFileInput != 0) {
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        r3 = OpenFileInput;
                        if (OpenFileInput == 0) {
                        }
                    }
                    OpenFileInput = Unit.INSTANCE;
                    return OpenFileInput;
                } catch (Throwable th) {
                    if (OpenFileInput != 0) {
                        OpenFileInput.close();
                    }
                    throw th;
                }
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }

    public static final boolean deleteBitmapFile(Context context, String imageName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(imageName, "imageName");
        return context.deleteFile(imageName);
    }
}
