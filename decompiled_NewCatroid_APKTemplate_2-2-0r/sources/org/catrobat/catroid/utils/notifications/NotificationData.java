package org.catrobat.catroid.utils.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/notifications/NotificationData.class
  classes14.dex
 */
/* JADX INFO: compiled from: NotificationData.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b,\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 D2\u00020\u0001:\u0001DBe\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\u0003¢\u0006\u0002\u0010\u0010J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\rHÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0005HÆ\u0003J\t\u00100\u001a\u00020\u0005HÆ\u0003J\t\u00101\u001a\u00020\u0005HÆ\u0003J\t\u00102\u001a\u00020\u0005HÆ\u0003J\t\u00103\u001a\u00020\u0005HÆ\u0003J\t\u00104\u001a\u00020\u0003HÆ\u0003J\t\u00105\u001a\u00020\u0003HÆ\u0003J\t\u00106\u001a\u00020\rHÆ\u0003Jw\u00107\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u0003HÆ\u0001J\u0013\u00108\u001a\u00020\r2\b\u00109\u001a\u0004\u0018\u00010:HÖ\u0003J\t\u0010;\u001a\u00020\u0003HÖ\u0001J \u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00052\b\u0010A\u001a\u0004\u0018\u00010BJ\t\u0010C\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\u000e\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0016\"\u0004\b\u001b\u0010\u0018R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0012\"\u0004\b\u001d\u0010\u0014R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0016\"\u0004\b#\u0010\u0018R\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001f\"\u0004\b%\u0010!R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001f\"\u0004\b'\u0010!R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u001f\"\u0004\b)\u0010!R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001f\"\u0004\b+\u0010!¨\u0006E"}, d2 = {"Lorg/catrobat/catroid/utils/notifications/NotificationData;", "Ljava/io/Serializable;", "notificationIconResId", "", ReplaceExistingProjectDialogFragment.BUNDLE_KEY_PROGRAM_NAME, "", "titleWorking", "titleDone", "textWorking", "textDone", "progressInPercent", "maxProgress", "ongoing", "", "autoCancel", "notificationID", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZZI)V", "getAutoCancel", "()Z", "setAutoCancel", "(Z)V", "getMaxProgress", "()I", "setMaxProgress", "(I)V", "getNotificationID", "getNotificationIconResId", "setNotificationIconResId", "getOngoing", "setOngoing", "getProgramName", "()Ljava/lang/String;", "setProgramName", "(Ljava/lang/String;)V", "getProgressInPercent", "setProgressInPercent", "getTextDone", "setTextDone", "getTextWorking", "setTextWorking", "getTitleDone", "setTitleDone", "getTitleWorking", "setTitleWorking", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "toNotification", "Landroid/app/Notification;", "context", "Landroid/content/Context;", "channelId", "contentIntent", "Landroid/app/PendingIntent;", "toString", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class NotificationData implements Serializable {
    private static final long serialVersionUID = 42;
    private boolean autoCancel;
    private int maxProgress;
    private final int notificationID;
    private int notificationIconResId;
    private boolean ongoing;
    private String programName;
    private int progressInPercent;
    private String textDone;
    private String textWorking;
    private String titleDone;
    private String titleWorking;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getNotificationIconResId() {
        return this.notificationIconResId;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final boolean getAutoCancel() {
        return this.autoCancel;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final int getNotificationID() {
        return this.notificationID;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getProgramName() {
        return this.programName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getTitleWorking() {
        return this.titleWorking;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getTitleDone() {
        return this.titleDone;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getTextWorking() {
        return this.textWorking;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getTextDone() {
        return this.textDone;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final int getProgressInPercent() {
        return this.progressInPercent;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final int getMaxProgress() {
        return this.maxProgress;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final boolean getOngoing() {
        return this.ongoing;
    }

    public final NotificationData copy(int notificationIconResId, String programName, String titleWorking, String titleDone, String textWorking, String textDone, int progressInPercent, int maxProgress, boolean ongoing, boolean autoCancel, int notificationID) {
        Intrinsics.checkNotNullParameter(programName, "programName");
        Intrinsics.checkNotNullParameter(titleWorking, "titleWorking");
        Intrinsics.checkNotNullParameter(titleDone, "titleDone");
        Intrinsics.checkNotNullParameter(textWorking, "textWorking");
        Intrinsics.checkNotNullParameter(textDone, "textDone");
        return new NotificationData(notificationIconResId, programName, titleWorking, titleDone, textWorking, textDone, progressInPercent, maxProgress, ongoing, autoCancel, notificationID);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NotificationData)) {
            return false;
        }
        NotificationData notificationData = (NotificationData) other;
        return this.notificationIconResId == notificationData.notificationIconResId && Intrinsics.areEqual(this.programName, notificationData.programName) && Intrinsics.areEqual(this.titleWorking, notificationData.titleWorking) && Intrinsics.areEqual(this.titleDone, notificationData.titleDone) && Intrinsics.areEqual(this.textWorking, notificationData.textWorking) && Intrinsics.areEqual(this.textDone, notificationData.textDone) && this.progressInPercent == notificationData.progressInPercent && this.maxProgress == notificationData.maxProgress && this.ongoing == notificationData.ongoing && this.autoCancel == notificationData.autoCancel && this.notificationID == notificationData.notificationID;
    }

    public int hashCode() {
        return (((((((((((((((((((Integer.hashCode(this.notificationIconResId) * 31) + this.programName.hashCode()) * 31) + this.titleWorking.hashCode()) * 31) + this.titleDone.hashCode()) * 31) + this.textWorking.hashCode()) * 31) + this.textDone.hashCode()) * 31) + Integer.hashCode(this.progressInPercent)) * 31) + Integer.hashCode(this.maxProgress)) * 31) + Boolean.hashCode(this.ongoing)) * 31) + Boolean.hashCode(this.autoCancel)) * 31) + Integer.hashCode(this.notificationID);
    }

    public String toString() {
        return "NotificationData(notificationIconResId=" + this.notificationIconResId + ", programName=" + this.programName + ", titleWorking=" + this.titleWorking + ", titleDone=" + this.titleDone + ", textWorking=" + this.textWorking + ", textDone=" + this.textDone + ", progressInPercent=" + this.progressInPercent + ", maxProgress=" + this.maxProgress + ", ongoing=" + this.ongoing + ", autoCancel=" + this.autoCancel + ", notificationID=" + this.notificationID + Constant.AFTER_QUTO;
    }

    public NotificationData(int i, String programName, String titleWorking, String titleDone, String textWorking, String textDone, int i2, int i3, boolean z, boolean z2, int i4) {
        Intrinsics.checkNotNullParameter(programName, "programName");
        Intrinsics.checkNotNullParameter(titleWorking, "titleWorking");
        Intrinsics.checkNotNullParameter(titleDone, "titleDone");
        Intrinsics.checkNotNullParameter(textWorking, "textWorking");
        Intrinsics.checkNotNullParameter(textDone, "textDone");
        this.notificationIconResId = i;
        this.programName = programName;
        this.titleWorking = titleWorking;
        this.titleDone = titleDone;
        this.textWorking = textWorking;
        this.textDone = textDone;
        this.progressInPercent = i2;
        this.maxProgress = i3;
        this.ongoing = z;
        this.autoCancel = z2;
        this.notificationID = i4;
    }

    public /* synthetic */ NotificationData(int i, String str, String str2, String str3, String str4, String str5, int i2, int i3, boolean z, boolean z2, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, str3, str4, str5, (i5 & 64) != 0 ? 0 : i2, (i5 & 128) != 0 ? 0 : i3, (i5 & 256) != 0 ? false : z, (i5 & 512) != 0 ? false : z2, i4);
    }

    public final int getNotificationIconResId() {
        return this.notificationIconResId;
    }

    public final void setNotificationIconResId(int i) {
        this.notificationIconResId = i;
    }

    public final String getProgramName() {
        return this.programName;
    }

    public final void setProgramName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.programName = str;
    }

    public final String getTitleWorking() {
        return this.titleWorking;
    }

    public final void setTitleWorking(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.titleWorking = str;
    }

    public final String getTitleDone() {
        return this.titleDone;
    }

    public final void setTitleDone(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.titleDone = str;
    }

    public final String getTextWorking() {
        return this.textWorking;
    }

    public final void setTextWorking(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.textWorking = str;
    }

    public final String getTextDone() {
        return this.textDone;
    }

    public final void setTextDone(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.textDone = str;
    }

    public final int getProgressInPercent() {
        return this.progressInPercent;
    }

    public final void setProgressInPercent(int i) {
        this.progressInPercent = i;
    }

    public final int getMaxProgress() {
        return this.maxProgress;
    }

    public final void setMaxProgress(int i) {
        this.maxProgress = i;
    }

    public final boolean getOngoing() {
        return this.ongoing;
    }

    public final void setOngoing(boolean z) {
        this.ongoing = z;
    }

    public final boolean getAutoCancel() {
        return this.autoCancel;
    }

    public final void setAutoCancel(boolean z) {
        this.autoCancel = z;
    }

    public final int getNotificationID() {
        return this.notificationID;
    }

    public final Notification toNotification(Context context, String channelId, PendingIntent contentIntent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(channelId, "channelId");
        Notification notificationBuild = new NotificationCompat.Builder(context, channelId).setContentTitle((this.progressInPercent < this.maxProgress ? this.titleWorking : this.titleDone) + " " + this.programName).setContentText(this.progressInPercent < this.maxProgress ? this.textWorking : this.textDone).setSmallIcon(this.notificationIconResId).setProgress(this.maxProgress, this.progressInPercent, false).setOngoing(this.ongoing).setAutoCancel(this.autoCancel).setContentIntent(contentIntent).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        return notificationBuild;
    }
}
