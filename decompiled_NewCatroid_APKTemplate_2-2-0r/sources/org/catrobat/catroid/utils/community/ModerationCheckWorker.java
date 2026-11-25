package org.catrobat.catroid.utils.community;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.CoroutineWorker;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.CommunityWebViewActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/community/ModerationCheckWorker.class
  classes14.dex
 */
/* JADX INFO: compiled from: ModerationCheckWorker.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00020\nH\u0096@¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J(\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/utils/community/ModerationCheckWorker;", "Landroidx/work/CoroutineWorker;", "context", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "httpClient", "Lokhttp3/OkHttpClient;", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseAndCheckModeration", "", "jsonResponse", "", "triggerStatusNotification", "gameId", "title", NotificationCompat.CATEGORY_STATUS, "reason", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModerationCheckWorker extends CoroutineWorker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Context context;
    private final OkHttpClient httpClient;

    @JvmStatic
    public static final void schedulePeriodicCheck(Context context) {
        INSTANCE.schedulePeriodicCheck(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModerationCheckWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(workerParams, "workerParams");
        this.context = context;
        this.httpClient = new OkHttpClient.Builder().connectTimeout(15L, TimeUnit.SECONDS).readTimeout(15L, TimeUnit.SECONDS).build();
    }

    @Override // androidx.work.CoroutineWorker
    public Object doWork(Continuation<? super ListenableWorker.Result> continuation) {
        if (!CommunityTokenManager.isLoggedIn(this.context)) {
            ListenableWorker.Result resultSuccess = ListenableWorker.Result.success();
            Intrinsics.checkNotNullExpressionValue(resultSuccess, "success(...)");
            return resultSuccess;
        }
        String token = CommunityTokenManager.getToken(this.context);
        if (token == null) {
            ListenableWorker.Result resultSuccess2 = ListenableWorker.Result.success();
            Intrinsics.checkNotNullExpressionValue(resultSuccess2, "success(...)");
            return resultSuccess2;
        }
        String username = CommunityTokenManager.getUsername(this.context);
        if (username == null) {
            ListenableWorker.Result resultSuccess3 = ListenableWorker.Result.success();
            Intrinsics.checkNotNullExpressionValue(resultSuccess3, "success(...)");
            return resultSuccess3;
        }
        try {
            Response responseExecute = this.httpClient.newCall(new Request.Builder().url("https://backend.sois.site/profiles/" + username + "/games").header("Authorization", "Bearer " + token).get().build()).execute();
            try {
                Response response = responseExecute;
                if (response.isSuccessful() && response.body() != null) {
                    ResponseBody responseBodyBody = response.body();
                    Intrinsics.checkNotNull(responseBodyBody);
                    parseAndCheckModeration(responseBodyBody.string());
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(responseExecute, null);
                ListenableWorker.Result resultSuccess4 = ListenableWorker.Result.success();
                Intrinsics.checkNotNullExpressionValue(resultSuccess4, "success(...)");
                return resultSuccess4;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(responseExecute, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Log.e("ModerationWorker", "Ошибка фоновой проверки модерации: ", e);
            ListenableWorker.Result resultRetry = ListenableWorker.Result.retry();
            Intrinsics.checkNotNullExpressionValue(resultRetry, "retry(...)");
            return resultRetry;
        }
    }

    private final void parseAndCheckModeration(String jsonResponse) throws JSONException {
        JSONArray jSONArrayOptJSONArray = new JSONObject(jsonResponse).optJSONArray(FirebaseAnalytics.Param.ITEMS);
        if (jSONArrayOptJSONArray == null) {
            return;
        }
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("community_moderation_tracker", 0);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        int length = jSONArrayOptJSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArrayOptJSONArray.getJSONObject(i);
            String string = jSONObject.getString("id");
            String string2 = jSONObject.getString("title");
            String string3 = jSONObject.getString(NotificationCompat.CATEGORY_STATUS);
            String strOptString = jSONObject.optString("moderation_reason", "");
            String string4 = sharedPreferences.getString(string, null);
            if (string4 != null && Intrinsics.areEqual(string4, "pending") && !Intrinsics.areEqual(string3, "pending")) {
                Intrinsics.checkNotNull(string);
                Intrinsics.checkNotNull(string2);
                Intrinsics.checkNotNull(string3);
                Intrinsics.checkNotNull(strOptString);
                triggerStatusNotification(string, string2, string3, strOptString);
            }
            editorEdit.putString(string, string3);
        }
        editorEdit.apply();
    }

    private final void triggerStatusNotification(String gameId, String title, String status, String reason) {
        int iHashCode = gameId.hashCode();
        Object systemService = this.context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        String string = this.context.getString(R.string.notification_moderation_channel);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        ((NotificationManager) systemService).createNotificationChannel(new NotificationChannel("community_moderation_channel", string, 3));
        Intent intent = new Intent(this.context, (Class<?>) CommunityWebViewActivity.class);
        intent.setFlags(268468224);
        NotificationCompat.Builder priority = new NotificationCompat.Builder(this.context, "community_moderation_channel").setSmallIcon(R.drawable.pc_toolbar_icon).setAutoCancel(true).setContentIntent(PendingIntent.getActivity(this.context, iHashCode, intent, 201326592)).setPriority(0);
        Intrinsics.checkNotNullExpressionValue(priority, "setPriority(...)");
        if (Intrinsics.areEqual(status, "approved")) {
            priority.setContentTitle(this.context.getString(R.string.notification_moderation_approved_title));
            priority.setContentText(this.context.getString(R.string.notification_moderation_approved_body, title));
        } else {
            if (!Intrinsics.areEqual(status, "rejected")) {
                return;
            }
            priority.setContentTitle(this.context.getString(R.string.notification_moderation_rejected_title));
            priority.setContentText(this.context.getString(R.string.notification_moderation_rejected_body, title, reason));
            priority.setStyle(new NotificationCompat.BigTextStyle().bigText(this.context.getString(R.string.notification_moderation_rejected_body, title, reason)));
        }
        try {
            NotificationManagerCompat.from(this.context).notify(iHashCode, priority.build());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/community/ModerationCheckWorker$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ModerationCheckWorker.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/utils/community/ModerationCheckWorker$Companion;", "", "()V", "schedulePeriodicCheck", "", "context", "Landroid/content/Context;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void schedulePeriodicCheck(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Constraints constraintsBuild = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
            Intrinsics.checkNotNullExpressionValue(constraintsBuild, "build(...)");
            PeriodicWorkRequest periodicWorkRequestBuild = new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) ModerationCheckWorker.class, 1L, TimeUnit.HOURS).setConstraints(constraintsBuild).build();
            Intrinsics.checkNotNullExpressionValue(periodicWorkRequestBuild, "build(...)");
            WorkManager.getInstance(context).enqueueUniquePeriodicWork("CommunityUploadSync", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequestBuild);
        }
    }
}
