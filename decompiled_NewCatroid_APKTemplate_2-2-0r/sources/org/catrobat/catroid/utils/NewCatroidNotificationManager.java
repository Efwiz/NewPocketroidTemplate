package org.catrobat.catroid.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.graphics.drawable.IconCompat;
import androidx.preference.PreferenceManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NewCatroidNotificationManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: NewCatroidNotificationManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002+,B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0018J\u0016\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0006J\u000e\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005J\u0010\u0010 \u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0005H\u0007J>\u0010!\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u0018J \u0010'\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010(\u001a\u00020)H\u0002J\u0016\u0010*\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010(\u001a\u00020)R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\b¨\u0006-"}, d2 = {"Lorg/catrobat/catroid/utils/NewCatroidNotificationManager;", "", "()V", "activeDrafts", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lorg/catrobat/catroid/utils/NewCatroidNotificationManager$NotificationState;", "getActiveDrafts", "()Ljava/util/concurrent/ConcurrentHashMap;", "pendingActions", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "getPendingActions", "()Ljava/util/concurrent/ConcurrentLinkedQueue;", "savedReplies", "getSavedReplies", "addActionButton", "", "id", "actionId", "text", "iconPath", "behavior", "", "hasInput", "", "inputHint", "autoClose", "buildAndShow", "context", "Landroid/content/Context;", "state", "cancelNotification", "cleanStringId", "configureDraft", "channelName", "title", "largeIconPath", "importance", "isOngoing", "schedule", "delaySeconds", "", "showOrSchedule", "NotificationActionState", "NotificationState", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCatroidNotificationManager {
    public static final NewCatroidNotificationManager INSTANCE = new NewCatroidNotificationManager();
    private static final ConcurrentHashMap<String, String> savedReplies = new ConcurrentHashMap<>();
    private static final ConcurrentLinkedQueue<String> pendingActions = new ConcurrentLinkedQueue<>();
    private static final ConcurrentHashMap<String, NotificationState> activeDrafts = new ConcurrentHashMap<>();

    private NewCatroidNotificationManager() {
    }

    public final ConcurrentHashMap<String, String> getSavedReplies() {
        return savedReplies;
    }

    public final ConcurrentLinkedQueue<String> getPendingActions() {
        return pendingActions;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NewCatroidNotificationManager$NotificationActionState.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NewCatroidNotificationManager.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\t¢\u0006\u0002\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/utils/NewCatroidNotificationManager$NotificationActionState;", "", "actionId", "", "text", "iconPath", "behavior", "", "hasInput", "", "inputHint", "autoClose", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;Z)V", "getActionId", "()Ljava/lang/String;", "getAutoClose", "()Z", "getBehavior", "()I", "getHasInput", "getIconPath", "getInputHint", "getText", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotificationActionState {
        private final String actionId;
        private final boolean autoClose;
        private final int behavior;
        private final boolean hasInput;
        private final String iconPath;
        private final String inputHint;
        private final String text;

        public NotificationActionState(String actionId, String text, String iconPath, int i, boolean z, String inputHint, boolean z2) {
            Intrinsics.checkNotNullParameter(actionId, "actionId");
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(iconPath, "iconPath");
            Intrinsics.checkNotNullParameter(inputHint, "inputHint");
            this.actionId = actionId;
            this.text = text;
            this.iconPath = iconPath;
            this.behavior = i;
            this.hasInput = z;
            this.inputHint = inputHint;
            this.autoClose = z2;
        }

        public final String getActionId() {
            return this.actionId;
        }

        public final String getText() {
            return this.text;
        }

        public final String getIconPath() {
            return this.iconPath;
        }

        public final int getBehavior() {
            return this.behavior;
        }

        public final boolean getHasInput() {
            return this.hasInput;
        }

        public final String getInputHint() {
            return this.inputHint;
        }

        public final boolean getAutoClose() {
            return this.autoClose;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NewCatroidNotificationManager$NotificationState.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NewCatroidNotificationManager.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\f\"\u0004\b\u001c\u0010\u0004R\u001a\u0010\u001d\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\f\"\u0004\b\u001f\u0010\u0004R\u001a\u0010 \u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\f\"\u0004\b\"\u0010\u0004¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/utils/NewCatroidNotificationManager$NotificationState;", "", "id", "", "(Ljava/lang/String;)V", "actions", "", "Lorg/catrobat/catroid/utils/NewCatroidNotificationManager$NotificationActionState;", "getActions", "()Ljava/util/List;", "channelName", "getChannelName", "()Ljava/lang/String;", "setChannelName", "getId", "importance", "", "getImportance", "()I", "setImportance", "(I)V", "isOngoing", "", "()Z", "setOngoing", "(Z)V", "largeIconPath", "getLargeIconPath", "setLargeIconPath", "text", "getText", "setText", "title", "getTitle", "setTitle", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotificationState {
        private final List<NotificationActionState> actions;
        private String channelName;
        private final String id;
        private int importance;
        private boolean isOngoing;
        private String largeIconPath;
        private String text;
        private String title;

        public NotificationState(String id2) {
            Intrinsics.checkNotNullParameter(id2, "id");
            this.id = id2;
            this.channelName = "App Notifications";
            this.title = "";
            this.text = "";
            this.largeIconPath = "";
            this.importance = 1;
            this.actions = new ArrayList();
        }

        public final String getId() {
            return this.id;
        }

        public final String getChannelName() {
            return this.channelName;
        }

        public final void setChannelName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.channelName = str;
        }

        public final String getTitle() {
            return this.title;
        }

        public final void setTitle(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.title = str;
        }

        public final String getText() {
            return this.text;
        }

        public final void setText(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.text = str;
        }

        public final String getLargeIconPath() {
            return this.largeIconPath;
        }

        public final void setLargeIconPath(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.largeIconPath = str;
        }

        public final int getImportance() {
            return this.importance;
        }

        public final void setImportance(int i) {
            this.importance = i;
        }

        /* JADX INFO: renamed from: isOngoing, reason: from getter */
        public final boolean getIsOngoing() {
            return this.isOngoing;
        }

        public final void setOngoing(boolean z) {
            this.isOngoing = z;
        }

        public final List<NotificationActionState> getActions() {
            return this.actions;
        }
    }

    @JvmStatic
    public static final String cleanStringId(String id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Double doubleOrNull = StringsKt.toDoubleOrNull(id2);
        return (doubleOrNull == null || !Intrinsics.areEqual(doubleOrNull, (double) ((long) doubleOrNull.doubleValue()))) ? id2 : String.valueOf((long) doubleOrNull.doubleValue());
    }

    public final ConcurrentHashMap<String, NotificationState> getActiveDrafts() {
        return activeDrafts;
    }

    public final void configureDraft(String id2, String channelName, String title, String text, String largeIconPath, int importance, boolean isOngoing) {
        NotificationState notificationStatePutIfAbsent;
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(channelName, "channelName");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(largeIconPath, "largeIconPath");
        ConcurrentHashMap<String, NotificationState> concurrentHashMap = activeDrafts;
        NotificationState notificationState = concurrentHashMap.get(id2);
        if (notificationState == null && (notificationStatePutIfAbsent = concurrentHashMap.putIfAbsent(id2, (notificationState = new NotificationState(id2)))) != null) {
            notificationState = notificationStatePutIfAbsent;
        }
        NotificationState notificationState2 = notificationState;
        notificationState2.getActions().clear();
        notificationState2.setChannelName(channelName);
        notificationState2.setTitle(title);
        notificationState2.setText(text);
        notificationState2.setLargeIconPath(largeIconPath);
        notificationState2.setImportance(importance);
        notificationState2.setOngoing(isOngoing);
    }

    public final void addActionButton(String id2, final String actionId, String text, String iconPath, int behavior, boolean hasInput, String inputHint, boolean autoClose) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(actionId, "actionId");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(iconPath, "iconPath");
        Intrinsics.checkNotNullParameter(inputHint, "inputHint");
        NotificationState notificationState = activeDrafts.get(id2);
        if (notificationState == null) {
            return;
        }
        CollectionsKt.removeAll((List) notificationState.getActions(), (Function1) new Function1<NotificationActionState, Boolean>() { // from class: org.catrobat.catroid.utils.NewCatroidNotificationManager.addActionButton.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(NotificationActionState it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getActionId(), actionId));
            }
        });
        notificationState.getActions().add(new NotificationActionState(actionId, text, iconPath, behavior, hasInput, inputHint, autoClose));
    }

    public final void cancelNotification(String id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Context appContext = CatroidApplication.getAppContext();
        if (appContext == null) {
            return;
        }
        String strCleanStringId = cleanStringId(id2);
        activeDrafts.remove(strCleanStringId);
        Object systemService = appContext.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        ((NotificationManager) systemService).cancel(strCleanStringId.hashCode());
    }

    public final void showOrSchedule(String id2, double delaySeconds) {
        NotificationState notificationState;
        Intrinsics.checkNotNullParameter(id2, "id");
        Context appContext = CatroidApplication.getAppContext();
        if (appContext == null || (notificationState = activeDrafts.get(id2)) == null) {
            return;
        }
        if (delaySeconds <= 0.0d) {
            buildAndShow(appContext, notificationState);
        } else {
            schedule(appContext, notificationState, delaySeconds);
        }
    }

    /* JADX WARN: Code duplicated, block: B:62:0x023a  */
    public final void buildAndShow(Context context, NotificationState state) {
        int i;
        Class cls;
        int i2;
        int i3;
        int i4;
        PendingIntent broadcast;
        IconCompat iconCompatCreateWithBitmap;
        int i5;
        int i6;
        File filesDir;
        File parentFile;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(state, "state");
        int iHashCode = state.getId().hashCode();
        String str = "catroid_chan_" + state.getChannelName().hashCode();
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        int importance = state.getImportance();
        if (importance != 0) {
            i = importance != 2 ? 3 : 4;
        } else {
            i = 2;
        }
        notificationManager.createNotificationChannel(new NotificationChannel(str, state.getChannelName(), i));
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pref_workspace_stage", false)) {
            try {
                cls = Class.forName("org.catrobat.catroid.stage.StageWorkspaceActivity");
            } catch (Exception unused) {
                cls = StageActivity.class;
            }
        } else {
            cls = StageActivity.class;
        }
        Intent intent = new Intent(context, (Class<?>) cls);
        intent.putExtra("NOTIFICATION_CLICKED_ID", state.getId());
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        String absolutePath = (currentProject == null || (filesDir = currentProject.getFilesDir()) == null || (parentFile = filesDir.getParentFile()) == null) ? null : parentFile.getAbsolutePath();
        if (absolutePath != null) {
            intent.putExtra(StageActivity.EXTRA_PROJECT_PATH, absolutePath);
        }
        int i7 = 872415232;
        intent.setFlags(872415232);
        int i8 = 1;
        NotificationCompat.Builder contentIntent = new NotificationCompat.Builder(context, str).setContentTitle(state.getTitle()).setContentText(state.getText()).setSmallIcon(context.getApplicationInfo().icon).setAutoCancel(!state.getIsOngoing()).setOngoing(state.getIsOngoing()).setContentIntent(PendingIntent.getActivity(context, iHashCode, intent, 67108864 | 134217728));
        Intrinsics.checkNotNullExpressionValue(contentIntent, "setContentIntent(...)");
        int importance2 = state.getImportance();
        if (importance2 != 0) {
            i2 = importance2 != 2 ? 0 : 1;
        } else {
            i2 = -1;
        }
        contentIntent.setPriority(i2);
        if (state.getLargeIconPath().length() > 0) {
            File file = new File(state.getLargeIconPath());
            if (file.exists()) {
                try {
                    Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
                    if (bitmapDecodeFile != null) {
                        contentIntent.setLargeIcon(bitmapDecodeFile);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Iterator it = state.getActions().iterator();
        while (it.hasNext()) {
            NotificationActionState notificationActionState = (NotificationActionState) it.next();
            int iHashCode2 = (state.getId() + notificationActionState.getActionId()).hashCode();
            if (notificationActionState.getBehavior() == i8) {
                Intent intent2 = new Intent(context, (Class<?>) cls);
                intent2.putExtra("NOTIFICATION_ACTION_ID", notificationActionState.getActionId());
                intent2.putExtra("NOTIF_ID", iHashCode);
                intent2.putExtra("NOTIF_DRAFT_ID", state.getId());
                intent2.putExtra("AUTO_CLOSE", notificationActionState.getAutoClose());
                intent2.setFlags(i7);
                if (Build.VERSION.SDK_INT >= 31) {
                    i5 = 134217728;
                    i6 = 33554432;
                } else {
                    i5 = 134217728;
                    i6 = 0;
                }
                broadcast = PendingIntent.getActivity(context, iHashCode2, intent2, i6 | i5);
                Intrinsics.checkNotNullExpressionValue(broadcast, "getActivity(...)");
                i3 = 134217728;
            } else {
                Intent intent3 = new Intent(context, (Class<?>) NotificationActionReceiver.class);
                intent3.setAction("org.catrobat.catroid.ACTION_NOTIF_CLICK");
                intent3.setData(Uri.parse("custom://notification/" + state.getId() + "/" + notificationActionState.getActionId()));
                intent3.putExtra("ACTION_ID", notificationActionState.getActionId());
                intent3.putExtra("NOTIF_ID", iHashCode);
                intent3.putExtra("NOTIF_DRAFT_ID", state.getId());
                intent3.putExtra("AUTO_CLOSE", notificationActionState.getAutoClose());
                if (Build.VERSION.SDK_INT >= 31) {
                    i3 = 134217728;
                    i4 = 33554432;
                } else {
                    i3 = 134217728;
                    i4 = 0;
                }
                broadcast = PendingIntent.getBroadcast(context, iHashCode2, intent3, i4 | i3);
                Intrinsics.checkNotNullExpressionValue(broadcast, "getBroadcast(...)");
            }
            if (notificationActionState.getIconPath().length() <= 0 || !new File(notificationActionState.getIconPath()).exists()) {
                iconCompatCreateWithBitmap = null;
            } else {
                try {
                    Bitmap bitmapDecodeFile2 = BitmapFactory.decodeFile(notificationActionState.getIconPath());
                    if (bitmapDecodeFile2 != null) {
                        iconCompatCreateWithBitmap = IconCompat.createWithBitmap(bitmapDecodeFile2);
                    } else {
                        iconCompatCreateWithBitmap = null;
                    }
                } catch (Exception unused2) {
                }
            }
            NotificationCompat.Action.Builder builder = new NotificationCompat.Action.Builder(iconCompatCreateWithBitmap, notificationActionState.getText(), broadcast);
            if (notificationActionState.getHasInput()) {
                RemoteInput remoteInputBuild = new RemoteInput.Builder("KEY_REPLY").setLabel(notificationActionState.getInputHint()).build();
                Intrinsics.checkNotNullExpressionValue(remoteInputBuild, "build(...)");
                builder.addRemoteInput(remoteInputBuild);
            }
            contentIntent.addAction(builder.build());
            it = it;
            i7 = 872415232;
            i8 = 1;
        }
        notificationManager.notify(iHashCode, contentIntent.build());
    }

    private final void schedule(Context context, NotificationState state, double delaySeconds) {
        Object systemService = context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
        AlarmManager alarmManager = (AlarmManager) systemService;
        Intent intent = new Intent(context, (Class<?>) NotificationPublisherReceiver.class);
        intent.putExtra("id", state.getId());
        intent.putExtra("channelName", state.getChannelName());
        intent.putExtra("title", state.getTitle());
        intent.putExtra("text", state.getText());
        intent.putExtra("largeIconPath", state.getLargeIconPath());
        intent.putExtra("importance", state.getImportance());
        intent.putExtra("isOngoing", state.getIsOngoing());
        PendingIntent broadcast = PendingIntent.getBroadcast(context, state.getId().hashCode(), intent, 67108864 | 134217728);
        long jCurrentTimeMillis = System.currentTimeMillis() + ((long) (delaySeconds * ((double) 1000)));
        try {
            alarmManager.setExactAndAllowWhileIdle(0, jCurrentTimeMillis, broadcast);
        } catch (SecurityException unused) {
            alarmManager.set(0, jCurrentTimeMillis, broadcast);
        }
    }
}
