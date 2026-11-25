package org.catrobat.catroid.content.actions;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LookRequester.class
  classes14.dex
 */
/* JADX INFO: compiled from: PaintNewLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/content/actions/LookRequester;", "", "()V", "anyAsked", "", "getAnyAsked", "()Z", "setAnyAsked", "(Z)V", "getFile", "Ljava/io/File;", "requestNewLook", "pocketPaintAction", "Lorg/catrobat/catroid/content/actions/PocketPaintAction;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookRequester {
    public static final LookRequester INSTANCE = new LookRequester();
    private static boolean anyAsked;

    private LookRequester() {
    }

    public final boolean getAnyAsked() {
        return anyAsked;
    }

    public final void setAnyAsked(boolean z) {
        anyAsked = z;
    }

    public final synchronized boolean requestNewLook(PocketPaintAction pocketPaintAction) {
        Message messageObtainMessage;
        Intrinsics.checkNotNullParameter(pocketPaintAction, "pocketPaintAction");
        if (anyAsked) {
            return false;
        }
        Handler handler = StageActivity.messageHandler;
        if (handler != null && (messageObtainMessage = handler.obtainMessage(0, CollectionsKt.arrayListOf(pocketPaintAction))) != null) {
            messageObtainMessage.sendToTarget();
        }
        anyAsked = true;
        return true;
    }

    public final synchronized File getFile() {
        File fileCopyFileToDir;
        try {
            fileCopyFileToDir = StorageOperations.copyFileToDir(new File(Constants.POCKET_PAINT_CACHE_DIRECTORY, "image.png"), new File(ProjectManager.getInstance().getCurrentlyPlayingScene().getDirectory(), Constants.IMAGE_DIRECTORY_NAME));
        } catch (IOException e) {
            Log.e("LookRequester", Log.getStackTraceString(e));
            fileCopyFileToDir = null;
        }
        anyAsked = false;
        return fileCopyFileToDir;
    }
}
