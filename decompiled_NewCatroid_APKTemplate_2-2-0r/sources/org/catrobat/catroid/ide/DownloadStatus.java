package org.catrobat.catroid.ide;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DownloadStatus.class
  classes14.dex
 */
/* JADX INFO: compiled from: DependencyManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ide/DownloadStatus;", "", "()V", "Connecting", "Downloading", "Error", "Success", "Lorg/catrobat/catroid/ide/DownloadStatus$Connecting;", "Lorg/catrobat/catroid/ide/DownloadStatus$Downloading;", "Lorg/catrobat/catroid/ide/DownloadStatus$Error;", "Lorg/catrobat/catroid/ide/DownloadStatus$Success;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DownloadStatus {
    public /* synthetic */ DownloadStatus(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DownloadStatus$Connecting.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DependencyManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/catrobat/catroid/ide/DownloadStatus$Connecting;", "Lorg/catrobat/catroid/ide/DownloadStatus;", "()V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Connecting extends DownloadStatus {
        public static final Connecting INSTANCE = new Connecting();

        private Connecting() {
            super(null);
        }
    }

    private DownloadStatus() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DownloadStatus$Downloading.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DependencyManager.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/ide/DownloadStatus$Downloading;", "Lorg/catrobat/catroid/ide/DownloadStatus;", "progress", "", "(F)V", "getProgress", "()F", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Downloading extends DownloadStatus {
        private final float progress;

        public static /* synthetic */ Downloading copy$default(Downloading downloading, float f, int i, Object obj) {
            if ((i & 1) != 0) {
                f = downloading.progress;
            }
            return downloading.copy(f);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final float getProgress() {
            return this.progress;
        }

        public final Downloading copy(float progress) {
            return new Downloading(progress);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Downloading) && Float.compare(this.progress, ((Downloading) other).progress) == 0;
        }

        public int hashCode() {
            return Float.hashCode(this.progress);
        }

        public String toString() {
            return "Downloading(progress=" + this.progress + Constant.AFTER_QUTO;
        }

        public Downloading(float f) {
            super(null);
            this.progress = f;
        }

        public final float getProgress() {
            return this.progress;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DownloadStatus$Error.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DependencyManager.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/ide/DownloadStatus$Error;", "Lorg/catrobat/catroid/ide/DownloadStatus;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Error extends DownloadStatus {
        private final String message;

        public static /* synthetic */ Error copy$default(Error error, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = error.message;
            }
            return error.copy(str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        public final Error copy(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            return new Error(message);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Error) && Intrinsics.areEqual(this.message, ((Error) other).message);
        }

        public int hashCode() {
            return this.message.hashCode();
        }

        public String toString() {
            return "Error(message=" + this.message + Constant.AFTER_QUTO;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Error(String message) {
            super(null);
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }

        public final String getMessage() {
            return this.message;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DownloadStatus$Success.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DependencyManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/catrobat/catroid/ide/DownloadStatus$Success;", "Lorg/catrobat/catroid/ide/DownloadStatus;", "()V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Success extends DownloadStatus {
        public static final Success INSTANCE = new Success();

        private Success() {
            super(null);
        }
    }
}
