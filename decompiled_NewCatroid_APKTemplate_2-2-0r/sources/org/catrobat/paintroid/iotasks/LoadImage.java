package org.catrobat.paintroid.iotasks;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;
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
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.command.serialization.CatrobatFileContent;
import org.catrobat.paintroid.command.serialization.CommandSerializer;
import org.catrobat.paintroid.common.ConstantsKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: LoadImage.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001d\u001eBG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0006\u0010\u0015\u001a\u00020\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/paintroid/iotasks/LoadImage;", "", "callback", "Lorg/catrobat/paintroid/iotasks/LoadImage$LoadImageCallback;", "requestCode", "", "uri", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "scaleImage", "", "commandSerializer", "Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "scopeIO", "Lkotlinx/coroutines/CoroutineScope;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "(Lorg/catrobat/paintroid/iotasks/LoadImage$LoadImageCallback;ILandroid/net/Uri;Landroid/content/Context;ZLorg/catrobat/paintroid/command/serialization/CommandSerializer;Lkotlinx/coroutines/CoroutineScope;Landroidx/test/espresso/idling/CountingIdlingResource;)V", "callbackRef", "Ljava/lang/ref/WeakReference;", "execute", "", "getBitmapReturnValue", "Lorg/catrobat/paintroid/iotasks/BitmapReturnValue;", "resolver", "Landroid/content/ContentResolver;", "getMimeType", "", "Companion", "LoadImageCallback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LoadImage {
    private static final String TAG = "LoadImage";
    private final WeakReference<LoadImageCallback> callbackRef;
    private final CommandSerializer commandSerializer;
    private final WeakReference<Context> context;
    private final CountingIdlingResource idlingResource;
    private final int requestCode;
    private final boolean scaleImage;
    private final CoroutineScope scopeIO;
    private final Uri uri;

    /* JADX INFO: compiled from: LoadImage.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H&J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006\u0012"}, d2 = {"Lorg/catrobat/paintroid/iotasks/LoadImage$LoadImageCallback;", "", "contentResolver", "Landroid/content/ContentResolver;", "getContentResolver", "()Landroid/content/ContentResolver;", "isFinishing", "", "()Z", "onLoadImagePostExecute", "", "requestCode", "", "uri", "Landroid/net/Uri;", "result", "Lorg/catrobat/paintroid/iotasks/BitmapReturnValue;", "onLoadImagePreExecute", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface LoadImageCallback {
        ContentResolver getContentResolver();

        boolean isFinishing();

        void onLoadImagePostExecute(int requestCode, Uri uri, BitmapReturnValue result);

        void onLoadImagePreExecute(int requestCode);
    }

    public LoadImage(LoadImageCallback callback, int i, Uri uri, Context context, boolean z, CommandSerializer commandSerializer, CoroutineScope scopeIO, CountingIdlingResource idlingResource) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        Intrinsics.checkNotNullParameter(scopeIO, "scopeIO");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        this.requestCode = i;
        this.uri = uri;
        this.scaleImage = z;
        this.commandSerializer = commandSerializer;
        this.scopeIO = scopeIO;
        this.idlingResource = idlingResource;
        this.callbackRef = new WeakReference<>(callback);
        this.context = new WeakReference<>(context);
    }

    private final String getMimeType(Uri uri, ContentResolver resolver) {
        if (Intrinsics.areEqual(uri.getScheme(), FirebaseAnalytics.Param.CONTENT)) {
            return resolver.getType(uri);
        }
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        if (fileExtension.equals(ConstantsKt.CATROBAT_IMAGE_ENDING)) {
            return "application/octet-stream";
        }
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        Intrinsics.checkNotNullExpressionValue(fileExtension, "fileExtension");
        Locale US = Locale.US;
        Intrinsics.checkNotNullExpressionValue(US, "US");
        String lowerCase = fileExtension.toLowerCase(US);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        return singleton.getMimeTypeFromExtension(lowerCase);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BitmapReturnValue getBitmapReturnValue(Uri uri, ContentResolver resolver) {
        String mimeType = getMimeType(uri, resolver);
        if (Intrinsics.areEqual(mimeType, "application/zip") || Intrinsics.areEqual(mimeType, "application/octet-stream")) {
            try {
                CatrobatFileContent fromFile = this.commandSerializer.readFromFile(uri);
                return new BitmapReturnValue(fromFile.getCommandModel(), fromFile.getColorHistory());
            } catch (CommandSerializer.NotCatrobatImageException unused) {
                Log.e(TAG, "Image might be an ora file instead");
                return OpenRasterFileFormatConversion.INSTANCE.importOraFile(resolver, uri);
            }
        }
        if (this.scaleImage) {
            return FileIO.INSTANCE.getScaledBitmapFromUri(resolver, uri, this.context.get());
        }
        return FileIO.INSTANCE.getBitmapReturnValueFromUri(resolver, uri, this.context.get());
    }

    public final void execute() {
        LoadImageCallback loadImageCallback = this.callbackRef.get();
        if (loadImageCallback == null || loadImageCallback.isFinishing()) {
            return;
        }
        loadImageCallback.onLoadImagePreExecute(this.requestCode);
        BuildersKt__Builders_commonKt.launch$default(this.scopeIO, null, null, new AnonymousClass1(loadImageCallback, new Ref.ObjectRef(), null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.iotasks.LoadImage$execute$1, reason: invalid class name */
    /* JADX INFO: compiled from: LoadImage.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.iotasks.LoadImage$execute$1", f = "LoadImage.kt", i = {}, l = {111}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ LoadImageCallback $callback;
        final /* synthetic */ Ref.ObjectRef<BitmapReturnValue> $returnValue;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(LoadImageCallback loadImageCallback, Ref.ObjectRef<BitmapReturnValue> objectRef, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$callback = loadImageCallback;
            this.$returnValue = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return LoadImage.this.new AnonymousClass1(this.$callback, this.$returnValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r8v11, types: [T, org.catrobat.paintroid.iotasks.BitmapReturnValue] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LoadImage.this.idlingResource.increment();
                if (LoadImage.this.uri == null) {
                    Log.e(LoadImage.TAG, "Can't load image file, uri is null");
                } else {
                    try {
                        ContentResolver contentResolver = this.$callback.getContentResolver();
                        FileIO fileIO = FileIO.INSTANCE;
                        FileIO.filename = Constants.TMP_IMAGE_FILE_NAME;
                        Ref.ObjectRef<BitmapReturnValue> objectRef = this.$returnValue;
                        LoadImage loadImage = LoadImage.this;
                        objectRef.element = loadImage.getBitmapReturnValue(loadImage.uri, contentResolver);
                    } catch (IOException e) {
                        Log.e(LoadImage.TAG, "Can't load image file", e);
                    } catch (NullPointerException e2) {
                        Log.e(LoadImage.TAG, "Can't load image file", e2);
                    }
                }
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02721(this.$callback, LoadImage.this, this.$returnValue, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: org.catrobat.paintroid.iotasks.LoadImage$execute$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: LoadImage.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "org.catrobat.paintroid.iotasks.LoadImage$execute$1$1", f = "LoadImage.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02721 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ LoadImageCallback $callback;
            final /* synthetic */ Ref.ObjectRef<BitmapReturnValue> $returnValue;
            int label;
            final /* synthetic */ LoadImage this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02721(LoadImageCallback loadImageCallback, LoadImage loadImage, Ref.ObjectRef<BitmapReturnValue> objectRef, Continuation<? super C02721> continuation) {
                super(2, continuation);
                this.$callback = loadImageCallback;
                this.this$0 = loadImage;
                this.$returnValue = objectRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02721(this.$callback, this.this$0, this.$returnValue, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                if (!this.$callback.isFinishing()) {
                    try {
                        this.$callback.onLoadImagePostExecute(this.this$0.requestCode, this.this$0.uri, this.$returnValue.element);
                    } finally {
                        this.this$0.idlingResource.decrement();
                    }
                }
                return Unit.INSTANCE;
            }
        }
    }
}
