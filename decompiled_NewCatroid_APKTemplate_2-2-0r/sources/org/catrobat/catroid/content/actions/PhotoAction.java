package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhotoAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PhotoAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0010J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0014H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/actions/PhotoAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "toast", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getToast", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setToast", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "createlook", "Lorg/catrobat/catroid/common/LookData;", "file", "Ljava/io/File;", "setLook", "", "look", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "updateLookListIndex", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhotoAction extends TemporalAction {
    private Scope scope;
    private Formula toast;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getToast() {
        return this.toast;
    }

    public final void setToast(Formula formula) {
        this.toast = formula;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhotoAction$update$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.PhotoAction$update$1, reason: invalid class name */
    /* JADX INFO: compiled from: PhotoAction.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", FirebaseAnalytics.Param.SUCCESS, "", "file", "Ljava/io/File;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass1 extends Lambda implements Function2<Boolean, File, Unit> {
        AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool, File file) {
            invoke(bool.booleanValue(), file);
            return Unit.INSTANCE;
        }

        public final void invoke(boolean z, final File file) {
            if (!z || file == null) {
                return;
            }
            Application application = Gdx.f82app;
            final PhotoAction photoAction = PhotoAction.this;
            application.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.PhotoAction$update$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException {
                    PhotoAction.AnonymousClass1.invoke$lambda$0(file, photoAction);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(File file, PhotoAction this$0) throws InterruptedException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Log.d("PhotoAction", "Running on libGDX thread...");
            LookData lookData = new LookData(file.getName(), file);
            Pixmap pixmap = lookData.getPixmap();
            if (pixmap != null && pixmap.getWidth() > 1) {
                this$0.setLook(lookData);
                Log.d("PhotoAction", "Look successfully prepared and set!");
            } else {
                Log.e("PhotoAction", "Failed to load Pixmap from file. Look remains unchanged.");
            }
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageActivity.getActiveCameraManager().takePicture2(new AnonymousClass1());
    }

    private final LookData createlook(File file) {
        LookData lookData = new LookData(file.getName(), file);
        lookData.getCollisionInformation().calculate();
        return lookData;
    }

    public final void setLook(LookData look) throws InterruptedException {
        Thread thread;
        Sprite sprite;
        Intrinsics.checkNotNullParameter(look, "look");
        updateLookListIndex();
        Scope scope = this.scope;
        Look look2 = (scope == null || (sprite = scope.getSprite()) == null) ? null : sprite.look;
        if (look2 != null) {
            look2.setLookData(look);
        }
        CollisionInformation collisionInformation = look.getCollisionInformation();
        if (collisionInformation != null && (thread = collisionInformation.collisionPolygonCalculationThread) != null) {
            thread.join();
        }
        look.setIsWebRequest(false);
    }

    private final void updateLookListIndex() {
        Sprite sprite;
        List<LookData> lookList;
        Sprite sprite2;
        Look look;
        Sprite sprite3;
        Sprite sprite4;
        Scope scope = this.scope;
        LookData lookData = null;
        Look look2 = (scope == null || (sprite4 = scope.getSprite()) == null) ? null : sprite4.look;
        int iIndexOf = -1;
        if (look2 == null || look2.getLookListIndexBeforeLookRequest() <= -1) {
            Scope scope2 = this.scope;
            Look look3 = (scope2 == null || (sprite3 = scope2.getSprite()) == null) ? null : sprite3.look;
            if (look3 == null) {
                return;
            }
            Scope scope3 = this.scope;
            if (scope3 != null && (sprite = scope3.getSprite()) != null && (lookList = sprite.getLookList()) != null) {
                Scope scope4 = this.scope;
                if (scope4 != null && (sprite2 = scope4.getSprite()) != null && (look = sprite2.look) != null) {
                    lookData = look.getLookData();
                }
                iIndexOf = lookList.indexOf(lookData);
            }
            look3.setLookListIndexBeforeLookRequest(iIndexOf);
        }
    }
}
