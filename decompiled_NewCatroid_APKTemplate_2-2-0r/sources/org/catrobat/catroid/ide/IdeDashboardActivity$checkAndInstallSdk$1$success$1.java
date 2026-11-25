package org.catrobat.catroid.ide;

import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeDashboardActivity$checkAndInstallSdk$1$success$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: IdeDashboardActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", NotificationCompat.CATEGORY_MESSAGE, "", NotificationCompat.CATEGORY_STATUS, "Lorg/catrobat/catroid/ide/DownloadStatus;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class IdeDashboardActivity$checkAndInstallSdk$1$success$1 extends Lambda implements Function2<String, DownloadStatus, Unit> {
    final /* synthetic */ IdeDashboardActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    IdeDashboardActivity$checkAndInstallSdk$1$success$1(IdeDashboardActivity ideDashboardActivity) {
        super(2);
        this.this$0 = ideDashboardActivity;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(String str, DownloadStatus downloadStatus) {
        invoke2(str, downloadStatus);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(final String msg, final DownloadStatus status) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Intrinsics.checkNotNullParameter(status, "status");
        final IdeDashboardActivity ideDashboardActivity = this.this$0;
        ideDashboardActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$checkAndInstallSdk$1$success$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                IdeDashboardActivity$checkAndInstallSdk$1$success$1.invoke$lambda$0(ideDashboardActivity, msg, status);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(IdeDashboardActivity this$0, String msg, DownloadStatus status) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(msg, "$msg");
        Intrinsics.checkNotNullParameter(status, "$status");
        TextView textView = this$0.tvLoadingText;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLoadingText");
            textView = null;
        }
        textView.setText(msg);
        if (status instanceof DownloadStatus.Error) {
            Toast.makeText(this$0, "Ошибка SDK: " + ((DownloadStatus.Error) status).getMessage(), 1).show();
            this$0.hideLoading();
        }
    }
}
