package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import kotlin.Metadata;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.stage.ScreenshotSaverCallback;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ScreenShotAction$captureAndSetLookAsync$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScreenShotAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"org/catrobat/catroid/content/actions/ScreenShotAction$captureAndSetLookAsync$1$1", "Lorg/catrobat/catroid/stage/ScreenshotSaverCallback;", "screenshotSaved", "", FirebaseAnalytics.Param.SUCCESS, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScreenShotAction$captureAndSetLookAsync$1$1 implements ScreenshotSaverCallback {
    final /* synthetic */ String $filesDir;
    final /* synthetic */ ScreenShotAction this$0;

    ScreenShotAction$captureAndSetLookAsync$1$1(String str, ScreenShotAction screenShotAction) {
        this.$filesDir = str;
        this.this$0 = screenShotAction;
    }

    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
    public void screenshotSaved(boolean success) {
        if (success) {
            final File file = new File(this.$filesDir, "screenshot.png");
            final ScreenShotAction screenShotAction = this.this$0;
            new Thread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ScreenShotAction$captureAndSetLookAsync$1$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenShotAction$captureAndSetLookAsync$1$1.screenshotSaved$lambda$5(screenShotAction, file);
                }
            }).start();
        } else {
            Log.e("ScreenShotAction", "Failed to capture hybrid screenshot");
            Application application = Gdx.f82app;
            final ScreenShotAction screenShotAction2 = this.this$0;
            application.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.ScreenShotAction$captureAndSetLookAsync$1$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenShotAction$captureAndSetLookAsync$1$1.screenshotSaved$lambda$6(screenShotAction2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void screenshotSaved$lambda$5(final ScreenShotAction this$0, File savedFile) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(savedFile, "$savedFile");
        try {
            String name2 = this$0.getName();
            if (name2 == null) {
                name2 = "screenshot";
            }
            File fileCreateTempFile = File.createTempFile(name2, Constants.DEFAULT_IMAGE_EXTENSION);
            fileCreateTempFile.deleteOnExit();
            FileInputStream fileInputStream = new FileInputStream(savedFile);
            try {
                FileInputStream fileInputStream2 = fileInputStream;
                Intrinsics.checkNotNull(fileCreateTempFile);
                FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
                try {
                    ByteStreamsKt.copyTo$default(fileInputStream2, fileOutputStream, 0, 2, null);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    CloseableKt.closeFinally(fileInputStream, null);
                    savedFile.delete();
                    final LookData lookData = new LookData(name2, fileCreateTempFile);
                    lookData.getCollisionInformation().calculate();
                    Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.ScreenShotAction$captureAndSetLookAsync$1$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            ScreenShotAction$captureAndSetLookAsync$1$1.screenshotSaved$lambda$5$lambda$3(this$0, lookData);
                        }
                    });
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(fileInputStream, th3);
                    throw th4;
                }
            }
        } catch (Exception e) {
            Log.e("ScreenShotAction", "Failed to build LookData from file", e);
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.ScreenShotAction$captureAndSetLookAsync$1$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenShotAction$captureAndSetLookAsync$1$1.screenshotSaved$lambda$5$lambda$4(this$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void screenshotSaved$lambda$5$lambda$3(ScreenShotAction this$0, LookData lookData) throws InterruptedException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(lookData, "$lookData");
        this$0.setLook(lookData);
        this$0.isDone = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void screenshotSaved$lambda$5$lambda$4(ScreenShotAction this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isDone = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void screenshotSaved$lambda$6(ScreenShotAction this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isDone = true;
    }
}
