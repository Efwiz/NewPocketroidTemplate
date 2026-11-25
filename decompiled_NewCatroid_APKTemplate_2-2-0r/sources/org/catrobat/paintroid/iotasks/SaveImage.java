package org.catrobat.paintroid.iotasks;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import androidx.test.espresso.idling.CountingIdlingResource;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
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
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.command.serialization.CommandSerializer;
import org.catrobat.paintroid.contract.LayerContracts;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: SaveImage.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 &2\u00020\u0001:\u0002&'BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\u0006\u0010\u0017\u001a\u00020\u0018J4\u0010\u0019\u001a\u0004\u0018\u00010\u000b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0002J\u001c\u0010#\u001a\u0004\u0018\u00010\u000b2\u0006\u0010$\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0002J<\u0010%\u001a\u0004\u0018\u00010\u000b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0002R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/catrobat/paintroid/iotasks/SaveImage;", "", "activity", "Lorg/catrobat/paintroid/iotasks/SaveImage$SaveImageCallback;", "requestCode", "", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "commandSerializer", "Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "uri", "Landroid/net/Uri;", "saveAsCopy", "", "context", "Landroid/content/Context;", "scopeIO", "Lkotlinx/coroutines/CoroutineScope;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "(Lorg/catrobat/paintroid/iotasks/SaveImage$SaveImageCallback;ILorg/catrobat/paintroid/contract/LayerContracts$Model;Lorg/catrobat/paintroid/command/serialization/CommandSerializer;Landroid/net/Uri;ZLandroid/content/Context;Lkotlinx/coroutines/CoroutineScope;Landroidx/test/espresso/idling/CountingIdlingResource;)V", "callbackRef", "Ljava/lang/ref/WeakReference;", "execute", "", "exportOraFile", "layers", "", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "bitmap", "Landroid/graphics/Bitmap;", "contentResolver", "Landroid/content/ContentResolver;", "getImageUri", "callback", "saveOraFile", "Companion", "SaveImageCallback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SaveImage {
    private static final String TAG = "SaveImage";
    private final WeakReference<SaveImageCallback> callbackRef;
    private final CommandSerializer commandSerializer;
    private final Context context;
    private final CountingIdlingResource idlingResource;
    private final LayerContracts.Model layerModel;
    private final int requestCode;
    private final boolean saveAsCopy;
    private final CoroutineScope scopeIO;
    private Uri uri;

    /* JADX INFO: compiled from: SaveImage.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\"\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H&J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/iotasks/SaveImage$SaveImageCallback;", "", "contentResolver", "Landroid/content/ContentResolver;", "getContentResolver", "()Landroid/content/ContentResolver;", "isFinishing", "", "()Z", "onSaveImagePostExecute", "", "requestCode", "", "uri", "Landroid/net/Uri;", "saveAsCopy", "onSaveImagePreExecute", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface SaveImageCallback {
        ContentResolver getContentResolver();

        boolean isFinishing();

        void onSaveImagePostExecute(int requestCode, Uri uri, boolean saveAsCopy);

        void onSaveImagePreExecute(int requestCode);
    }

    public SaveImage(SaveImageCallback activity, int i, LayerContracts.Model layerModel, CommandSerializer commandSerializer, Uri uri, boolean z, Context context, CoroutineScope scopeIO, CountingIdlingResource idlingResource) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scopeIO, "scopeIO");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        this.requestCode = i;
        this.layerModel = layerModel;
        this.commandSerializer = commandSerializer;
        this.uri = uri;
        this.saveAsCopy = z;
        this.context = context;
        this.scopeIO = scopeIO;
        this.idlingResource = idlingResource;
        this.callbackRef = new WeakReference<>(activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Uri getImageUri(SaveImageCallback callback, Bitmap bitmap) {
        String defaultFileName = FileIO.INSTANCE.getDefaultFileName();
        Uri uri = this.uri;
        if (uri == null) {
            return FileIO.INSTANCE.saveBitmapToFile(defaultFileName, bitmap, callback.getContentResolver(), this.context);
        }
        if (uri == null) {
            return null;
        }
        return FileIO.INSTANCE.saveBitmapToUri(uri, bitmap, this.context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Uri saveOraFile(List<? extends LayerContracts.Layer> layers, Uri uri, String fileName, Bitmap bitmap, ContentResolver contentResolver) {
        try {
            return OpenRasterFileFormatConversion.INSTANCE.saveOraFileToUri(layers, uri, fileName, bitmap, contentResolver);
        } catch (IOException e) {
            Log.d(TAG, Intrinsics.stringPlus("Can't save image file ", e.getMessage()));
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Uri exportOraFile(List<? extends LayerContracts.Layer> layers, String fileName, Bitmap bitmap, ContentResolver contentResolver) {
        try {
            return OpenRasterFileFormatConversion.INSTANCE.exportToOraFile(layers, fileName, bitmap, contentResolver);
        } catch (IOException e) {
            Log.d(TAG, Intrinsics.stringPlus("Can't save image file ", e.getMessage()));
            return null;
        }
    }

    public final void execute() {
        SaveImageCallback saveImageCallback = this.callbackRef.get();
        if (saveImageCallback == null || saveImageCallback.isFinishing()) {
            return;
        }
        saveImageCallback.onSaveImagePreExecute(this.requestCode);
        BuildersKt__Builders_commonKt.launch$default(this.scopeIO, null, null, new AnonymousClass1(new Ref.ObjectRef(), saveImageCallback, null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.iotasks.SaveImage$execute$1, reason: invalid class name */
    /* JADX INFO: compiled from: SaveImage.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.iotasks.SaveImage$execute$1", f = "SaveImage.kt", i = {}, l = {148}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SaveImageCallback $callback;
        final /* synthetic */ Ref.ObjectRef<Uri> $currentUri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Ref.ObjectRef<Uri> objectRef, SaveImageCallback saveImageCallback, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$currentUri = objectRef;
            this.$callback = saveImageCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SaveImage.this.new AnonymousClass1(this.$currentUri, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            T tSaveOraFile;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    SaveImage.this.idlingResource.increment();
                    Bitmap bitmapOfAllLayers = SaveImage.this.layerModel.getBitmapOfAllLayers();
                    String defaultFileName = FileIO.INSTANCE.getDefaultFileName();
                    Ref.ObjectRef<Uri> objectRef = this.$currentUri;
                    if (FileIO.fileType == FileIO.FileType.ORA) {
                        List<LayerContracts.Layer> layers = SaveImage.this.layerModel.getLayers();
                        if (SaveImage.this.uri == null || !StringsKt.endsWith$default(defaultFileName, FileIO.FileType.ORA.toExtension(), false, 2, (Object) null)) {
                            tSaveOraFile = SaveImage.this.exportOraFile(layers, defaultFileName, bitmapOfAllLayers, this.$callback.getContentResolver());
                        } else {
                            Uri uri = SaveImage.this.uri;
                            tSaveOraFile = uri == null ? 0 : SaveImage.this.saveOraFile(layers, uri, defaultFileName, bitmapOfAllLayers, this.$callback.getContentResolver());
                        }
                    } else if (FileIO.fileType == FileIO.FileType.CATROBAT) {
                        if (SaveImage.this.uri != null) {
                            Uri uri2 = SaveImage.this.uri;
                            if (uri2 != null) {
                                tSaveOraFile = SaveImage.this.commandSerializer.overWriteFile(defaultFileName, uri2, this.$callback.getContentResolver());
                            }
                        } else {
                            tSaveOraFile = SaveImage.this.commandSerializer.writeToFile(defaultFileName);
                        }
                    } else {
                        tSaveOraFile = SaveImage.this.getImageUri(this.$callback, bitmapOfAllLayers);
                    }
                    objectRef.element = tSaveOraFile;
                    SaveImage.this.idlingResource.decrement();
                } catch (Exception e) {
                    SaveImage.this.idlingResource.decrement();
                    if (e instanceof IOException) {
                        Log.d(SaveImage.TAG, "Can't save image file", e);
                    } else if (e instanceof NullPointerException) {
                        Log.e(SaveImage.TAG, "Can't load image file", e);
                    }
                }
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass3(this.$callback, SaveImage.this, this.$currentUri, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: org.catrobat.paintroid.iotasks.SaveImage$execute$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: SaveImage.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "org.catrobat.paintroid.iotasks.SaveImage$execute$1$3", f = "SaveImage.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ SaveImageCallback $callback;
            final /* synthetic */ Ref.ObjectRef<Uri> $currentUri;
            int label;
            final /* synthetic */ SaveImage this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(SaveImageCallback saveImageCallback, SaveImage saveImage, Ref.ObjectRef<Uri> objectRef, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$callback = saveImageCallback;
                this.this$0 = saveImage;
                this.$currentUri = objectRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.$callback, this.this$0, this.$currentUri, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                if (!this.$callback.isFinishing()) {
                    this.$callback.onSaveImagePostExecute(this.this$0.requestCode, this.$currentUri.element, this.this$0.saveAsCopy);
                }
                return Unit.INSTANCE;
            }
        }
    }
}
