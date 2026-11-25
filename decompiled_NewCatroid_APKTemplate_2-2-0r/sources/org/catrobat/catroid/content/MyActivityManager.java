package org.catrobat.catroid.content;

import android.app.Activity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/MyActivityManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: MyActivityManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/content/MyActivityManager;", "", "()V", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MyActivityManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static Activity actor_activity;
    private static Activity base_activity;
    private static Activity project_activity;
    private static Activity sprite_activity;
    private static Activity stage_activity;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/MyActivityManager$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: MyActivityManager.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/MyActivityManager$Companion;", "", "()V", "actor_activity", "Landroid/app/Activity;", "getActor_activity", "()Landroid/app/Activity;", "setActor_activity", "(Landroid/app/Activity;)V", "base_activity", "getBase_activity", "setBase_activity", "project_activity", "getProject_activity", "setProject_activity", "sprite_activity", "getSprite_activity", "setSprite_activity", "stage_activity", "getStage_activity", "setStage_activity", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Activity getBase_activity() {
            return MyActivityManager.base_activity;
        }

        public final void setBase_activity(Activity activity) {
            MyActivityManager.base_activity = activity;
        }

        public final Activity getProject_activity() {
            return MyActivityManager.project_activity;
        }

        public final void setProject_activity(Activity activity) {
            MyActivityManager.project_activity = activity;
        }

        public final Activity getSprite_activity() {
            return MyActivityManager.sprite_activity;
        }

        public final void setSprite_activity(Activity activity) {
            MyActivityManager.sprite_activity = activity;
        }

        public final Activity getStage_activity() {
            return MyActivityManager.stage_activity;
        }

        public final void setStage_activity(Activity activity) {
            MyActivityManager.stage_activity = activity;
        }

        public final Activity getActor_activity() {
            return MyActivityManager.actor_activity;
        }

        public final void setActor_activity(Activity activity) {
            MyActivityManager.actor_activity = activity;
        }
    }
}
