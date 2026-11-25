package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/FontsContractCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class FontsContractCompat {

    @Deprecated
    public static final String PARCEL_FONT_RESULTS = "font_results";

    @Deprecated
    static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;

    @Deprecated
    static final int RESULT_CODE_WRONG_CERTIFICATES = -2;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/FontsContractCompat$Columns.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$Columns.class
      classes.dex
     */
    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/FontsContractCompat$FontRequestCallback.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$FontRequestCallback.class
      classes.dex
     */
    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        @Deprecated
        public static final int RESULT_OK = 0;
        static final int RESULT_SUCCESS = 0;

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/FontsContractCompat$FontRequestCallback$FontRequestFailReason.class
          NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$FontRequestCallback$FontRequestFailReason.class
          classes.dex
         */
        @Retention(RetentionPolicy.SOURCE)
        public @interface FontRequestFailReason {
        }

        public void onTypefaceRequestFailed(int i) {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }
    }

    private FontsContractCompat() {
    }

    public static Typeface buildTypeface(Context context, CancellationSignal cancellationSignal, FontInfo[] fontInfoArr) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fontInfoArr, 0);
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        return FontProvider.getFontFamilyResult(context, fontRequest, cancellationSignal);
    }

    public static void requestFont(Context context, FontRequest fontRequest, FontRequestCallback fontRequestCallback, Handler handler) {
        CallbackWithHandler callbackWithHandler = new CallbackWithHandler(fontRequestCallback);
        FontRequestWorker.requestFontAsync(context.getApplicationContext(), fontRequest, 0, RequestExecutor.createHandlerExecutor(handler), callbackWithHandler);
    }

    public static Typeface requestFont(Context context, FontRequest fontRequest, int i, boolean z, int i2, Handler handler, FontRequestCallback fontRequestCallback) {
        CallbackWithHandler callbackWithHandler = new CallbackWithHandler(fontRequestCallback, handler);
        if (z) {
            return FontRequestWorker.requestFontSync(context, fontRequest, callbackWithHandler, i, i2);
        }
        return FontRequestWorker.requestFontAsync(context, fontRequest, i, null, callbackWithHandler);
    }

    public static void resetTypefaceCache() {
        FontRequestWorker.resetTypefaceCache();
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$TypefaceResult.class */
    private static final class TypefaceResult {
        final Typeface mTypeface;
        final int mResult;

        TypefaceResult(@Nullable Typeface typeface, int result) {
            this.mTypeface = typeface;
            this.mResult = result;
        }
    }

    /* JADX INFO: renamed from: androidx.core.provider.FontsContractCompat$1, reason: invalid class name */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$1.class */
    class AnonymousClass1 implements Callable<TypefaceResult> {
        final /* synthetic */ Context val$context;
        final /* synthetic */ FontRequest val$request;
        final /* synthetic */ int val$style;
        final /* synthetic */ String val$id;

        AnonymousClass1(Context context, FontRequest fontRequest, int i, String str) {
            this.val$context = context;
            this.val$request = fontRequest;
            this.val$style = i;
            this.val$id = str;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public TypefaceResult call() throws Exception {
            TypefaceResult typeface = FontsContractCompat.getFontInternal(this.val$context, this.val$request, this.val$style);
            if (typeface.mTypeface != null) {
                FontsContractCompat.sTypefaceCache.put(this.val$id, typeface.mTypeface);
            }
            return typeface;
        }
    }

    /* JADX INFO: renamed from: androidx.core.provider.FontsContractCompat$2, reason: invalid class name */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$2.class */
    class AnonymousClass2 implements SelfDestructiveThread.ReplyCallback<TypefaceResult> {
        final /* synthetic */ ResourcesCompat.FontCallback val$fontCallback;
        final /* synthetic */ Handler val$handler;

        AnonymousClass2(ResourcesCompat.FontCallback fontCallback, Handler handler) {
            this.val$fontCallback = fontCallback;
            this.val$handler = handler;
        }

        @Override // androidx.core.provider.SelfDestructiveThread.ReplyCallback
        public void onReply(TypefaceResult typeface) {
            if (typeface == null) {
                this.val$fontCallback.callbackFailAsync(1, this.val$handler);
            } else if (typeface.mResult == 0) {
                this.val$fontCallback.callbackSuccessAsync(typeface.mTypeface, this.val$handler);
            } else {
                this.val$fontCallback.callbackFailAsync(typeface.mResult, this.val$handler);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/FontsContractCompat$FontInfo.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$FontInfo.class
      classes.dex
     */
    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        @Deprecated
        public FontInfo(Uri uri, int i, int i2, boolean z, int i3) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = i;
            this.mWeight = i2;
            this.mItalic = z;
            this.mResultCode = i3;
        }

        static FontInfo create(Uri uri, int i, int i2, boolean z, int i3) {
            return new FontInfo(uri, i, i2, z, i3);
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    /* JADX INFO: renamed from: androidx.core.provider.FontsContractCompat$3, reason: invalid class name */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$3.class */
    class AnonymousClass3 implements SelfDestructiveThread.ReplyCallback<TypefaceResult> {
        final /* synthetic */ String val$id;

        AnonymousClass3(String str) {
            this.val$id = str;
        }

        @Override // androidx.core.provider.SelfDestructiveThread.ReplyCallback
        public void onReply(TypefaceResult typeface) {
            synchronized (FontsContractCompat.sLock) {
                ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>> replies = (ArrayList) FontsContractCompat.sPendingReplies.get(this.val$id);
                if (replies == null) {
                    return;
                }
                FontsContractCompat.sPendingReplies.remove(this.val$id);
                for (int i = 0; i < replies.size(); i++) {
                    replies.get(i).onReply(typeface);
                }
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/FontsContractCompat$FontFamilyResult.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$FontFamilyResult.class
      classes.dex
     */
    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @Deprecated
        public FontFamilyResult(int i, FontInfo[] fontInfoArr) {
            this.mStatusCode = i;
            this.mFonts = fontInfoArr;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }

        static FontFamilyResult create(int i, FontInfo[] fontInfoArr) {
            return new FontFamilyResult(i, fontInfoArr);
        }
    }

    @Deprecated
    public static Typeface getFontSync(Context context, FontRequest fontRequest, ResourcesCompat.FontCallback fontCallback, Handler handler, boolean z, int i, int i2) {
        return requestFont(context, fontRequest, i2, z, i, ResourcesCompat.FontCallback.getHandler(handler), new TypefaceCompat.ResourcesCallbackAdapter(fontCallback));
    }

    /* JADX INFO: renamed from: androidx.core.provider.FontsContractCompat$4, reason: invalid class name */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$4.class */
    class AnonymousClass4 implements Runnable {
        final /* synthetic */ Context val$appContext;
        final /* synthetic */ FontRequest val$request;
        final /* synthetic */ Handler val$callerThreadHandler;
        final /* synthetic */ FontRequestCallback val$callback;

        AnonymousClass4(Context context, FontRequest fontRequest, Handler handler, FontRequestCallback fontRequestCallback) {
            this.val$appContext = context;
            this.val$request = fontRequest;
            this.val$callerThreadHandler = handler;
            this.val$callback = fontRequestCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                FontFamilyResult result = FontsContractCompat.fetchFonts(this.val$appContext, null, this.val$request);
                if (result.getStatusCode() != 0) {
                    switch (result.getStatusCode()) {
                        case 1:
                            this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4.this.val$callback.onTypefaceRequestFailed(-2);
                                }
                            });
                            break;
                        case 2:
                            this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4.this.val$callback.onTypefaceRequestFailed(-3);
                                }
                            });
                            break;
                        default:
                            this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4.this.val$callback.onTypefaceRequestFailed(-3);
                                }
                            });
                            break;
                    }
                    return;
                }
                FontInfo[] fonts = result.getFonts();
                if (fonts == null || fonts.length == 0) {
                    this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.5
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass4.this.val$callback.onTypefaceRequestFailed(1);
                        }
                    });
                    return;
                }
                for (FontInfo font : fonts) {
                    if (font.getResultCode() != 0) {
                        final int resultCode = font.getResultCode();
                        if (resultCode < 0) {
                            this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.6
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4.this.val$callback.onTypefaceRequestFailed(-3);
                                }
                            });
                            return;
                        } else {
                            this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4.this.val$callback.onTypefaceRequestFailed(resultCode);
                                }
                            });
                            return;
                        }
                    }
                }
                final Typeface typeface = FontsContractCompat.buildTypeface(this.val$appContext, null, fonts);
                if (typeface == null) {
                    this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.8
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass4.this.val$callback.onTypefaceRequestFailed(-3);
                        }
                    });
                } else {
                    this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.9
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass4.this.val$callback.onTypefaceRetrieved(typeface);
                        }
                    });
                }
            } catch (PackageManager.NameNotFoundException e) {
                this.val$callerThreadHandler.post(new Runnable() { // from class: androidx.core.provider.FontsContractCompat.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass4.this.val$callback.onTypefaceRequestFailed(-1);
                    }
                });
            }
        }
    }

    @Deprecated
    public static void resetCache() {
        FontRequestWorker.resetTypefaceCache();
    }

    @Deprecated
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fontInfoArr, CancellationSignal cancellationSignal) {
        return TypefaceCompatUtil.readFontInfoIntoByteBuffer(context, fontInfoArr, cancellationSignal);
    }

    @Deprecated
    public static ProviderInfo getProvider(PackageManager packageManager, FontRequest fontRequest, Resources resources) throws PackageManager.NameNotFoundException {
        return FontProvider.getProvider(packageManager, fontRequest, resources);
    }

    /* JADX INFO: renamed from: androidx.core.provider.FontsContractCompat$5, reason: invalid class name */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/provider/FontsContractCompat$5.class */
    class AnonymousClass5 implements Comparator<byte[]> {
        AnonymousClass5() {
        }

        @Override // java.util.Comparator
        public int compare(byte[] l, byte[] r) {
            if (l.length != r.length) {
                return l.length - r.length;
            }
            for (int i = 0; i < l.length; i++) {
                if (l[i] != r[i]) {
                    return l[i] - r[i];
                }
            }
            return 0;
        }
    }
}
