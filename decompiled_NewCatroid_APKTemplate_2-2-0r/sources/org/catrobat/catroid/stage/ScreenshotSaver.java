package org.catrobat.catroid.stage;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import com.badlogic.gdx.Files;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/ScreenshotSaver.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScreenshotSaver.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0005J*\u0010\u0013\u001a\u00020\u00142\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/stage/ScreenshotSaver;", "", "gdxFileHandler", "Lcom/badlogic/gdx/Files;", "folder", "", "width", "", "height", "(Lcom/badlogic/gdx/Files;Ljava/lang/String;II)V", "getScreenshot", "Ljava/io/InputStream;", "data", "", "retrieveColorFromData", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "saveScreenshot", "", PsiTreeChangeEvent.PROP_FILE_NAME, "saveScreenshotAndNotify", "", "callback", "Lorg/catrobat/catroid/stage/ScreenshotSaverCallback;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScreenshotSaver {
    private static final int IMAGE_QUALITY = 100;
    private static final int MAX_SCREEN_SHOT_HEIGHT = 480;
    private static final int MAX_SCREEN_SHOT_WIDTH = 480;
    private static final int NEW_MAX_SCREEN_SHOT_HEIGHT = 3000;
    private static final int NEW_MAX_SCREEN_SHOT_WIDTH = 3000;
    private static final int NUMBER_OF_COLORS = 4;
    private final String folder;
    private final Files gdxFileHandler;
    private final int height;
    private final int width;
    private static final String TAG = "ScreenshotSaver";
    private static final Regex VALID_FILENAME_REGEX = new Regex("^[^<>:;,?\"*|/]+$");
    private static final Regex ONLY_WHITESPACE_REGEX = new Regex("\\s*");

    public ScreenshotSaver(Files gdxFileHandler, String folder, int i, int i2) {
        Intrinsics.checkNotNullParameter(gdxFileHandler, "gdxFileHandler");
        Intrinsics.checkNotNullParameter(folder, "folder");
        this.gdxFileHandler = gdxFileHandler;
        this.folder = folder;
        this.width = i;
        this.height = i2;
    }

    public final void saveScreenshotAndNotify(byte[] data, String fileName, ScreenshotSaverCallback callback, CoroutineScope coroutineScope) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(coroutineScope, "coroutineScope");
        if (data == null) {
            Log.d(TAG, "Screenshot data is null");
            callback.screenshotSaved(false);
            return;
        }
        if (fileName != null) {
            String str = fileName;
            if (!ONLY_WHITESPACE_REGEX.matches(str) && VALID_FILENAME_REGEX.matches(str)) {
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(data, fileName, callback, null), 2, null);
                return;
            }
        }
        Log.d(TAG, "Screenshot filename invalid");
        callback.screenshotSaved(false);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/ScreenshotSaver$saveScreenshotAndNotify$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.stage.ScreenshotSaver$saveScreenshotAndNotify$1, reason: invalid class name */
    /* JADX INFO: compiled from: ScreenshotSaver.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.stage.ScreenshotSaver$saveScreenshotAndNotify$1", f = "ScreenshotSaver.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ScreenshotSaverCallback $callback;
        final /* synthetic */ byte[] $data;
        final /* synthetic */ String $fileName;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(byte[] bArr, String str, ScreenshotSaverCallback screenshotSaverCallback, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$data = bArr;
            this.$fileName = str;
            this.$callback = screenshotSaverCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ScreenshotSaver.this.new AnonymousClass1(this.$data, this.$fileName, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            this.$callback.screenshotSaved(ScreenshotSaver.this.saveScreenshot(this.$data, this.$fileName));
            return Unit.INSTANCE;
        }
    }

    public final boolean saveScreenshot(byte[] data, String fileName) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        int length = data.length;
        int i = length / 4;
        int[] iArr = new int[i];
        if (i == this.width * this.height && i != 0) {
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, length), 4);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if ((step > 0 && first <= last) || (step < 0 && last <= first)) {
                while (true) {
                    iArr[first / 4] = retrieveColorFromData(data, first);
                    if (first == last) {
                        break;
                    }
                    first += step;
                }
            }
            int i2 = this.width;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iArr, 0, i2, i2, this.height, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            int i3 = (int) (((double) 480) * (((double) this.width) / ((double) this.height)));
            if (i3 > 480) {
                i3 = 480;
            }
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, i3, 480, false);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
            OutputStream outputStreamWrite = this.gdxFileHandler.absolute(this.folder + fileName).write(false);
            try {
                new File(this.folder + Constants.NO_MEDIA_FILE).createNewFile();
                bitmapCreateScaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStreamWrite);
                outputStreamWrite.close();
                if (ProjectManager.getInstance().getCurrentProject() == null) {
                    return true;
                }
                String str = ProjectManager.getInstance().getCurrentProject().getDirectory().getAbsolutePath() + "/";
                OutputStream outputStreamWrite2 = this.gdxFileHandler.absolute(str + fileName).write(false);
                new File(str + Constants.NO_MEDIA_FILE).createNewFile();
                bitmapCreateScaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStreamWrite2);
                outputStreamWrite2.close();
                return true;
            } catch (IOException e) {
                Log.w(TAG, "Could not save screenshot to file", e);
            }
        }
        return false;
    }

    public final InputStream getScreenshot(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        int length = data.length;
        int i = length / 4;
        int[] iArr = new int[i];
        if (i != this.width * this.height || i == 0) {
            throw new IllegalArgumentException("Invalid data for screenshot");
        }
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, length), 4);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if ((step > 0 && first <= last) || (step < 0 && last <= first)) {
            while (true) {
                iArr[first / 4] = retrieveColorFromData(data, first);
                if (first == last) {
                    break;
                }
                first += step;
            }
        }
        int i2 = this.width;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iArr, 0, i2, i2, this.height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        int i3 = (int) (((double) 3000) * (((double) this.width) / ((double) this.height)));
        if (i3 > 3000) {
            i3 = 3000;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, i3, 3000, false);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapCreateScaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private final int retrieveColorFromData(byte[] data, int i) {
        return Color.argb(255, data[i] & 255, data[i + 1] & 255, data[i + 2] & 255);
    }
}
