package org.catrobat.catroid.content.actions;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PlaySoundAtAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PlaySoundAtAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001cH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/content/actions/PlaySoundAtAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", TypedValues.CycleType.S_WAVE_OFFSET, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getOffset", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setOffset", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sound", "Lorg/catrobat/catroid/common/SoundInfo;", "getSound", "()Lorg/catrobat/catroid/common/SoundInfo;", "setSound", "(Lorg/catrobat/catroid/common/SoundInfo;)V", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", "runWithMockedSoundManager", "", "manager", "Lorg/catrobat/catroid/io/SoundManager;", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlaySoundAtAction extends TemporalAction {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int SECONDS_TO_MILLISECONDS = 1000;
    private static final String TAG;
    public Formula offset;
    private Scope scope;
    private SoundInfo sound;
    public Sprite sprite;

    public final Sprite getSprite() {
        Sprite sprite = this.sprite;
        if (sprite != null) {
            return sprite;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sprite");
        return null;
    }

    public final void setSprite(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "<set-?>");
        this.sprite = sprite;
    }

    public final Formula getOffset() {
        Formula formula = this.offset;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException(TypedValues.CycleType.S_WAVE_OFFSET);
        return null;
    }

    public final void setOffset(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.offset = formula;
    }

    public final SoundInfo getSound() {
        return this.sound;
    }

    public final void setSound(SoundInfo soundInfo) {
        this.sound = soundInfo;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        SoundInfo soundInfo;
        try {
            int iFloatValue = (int) (getOffset().interpretFloat(this.scope).floatValue() * 1000);
            if (!getSprite().getSoundList().contains(this.sound) || (soundInfo = this.sound) == null) {
                return;
            }
            if (soundInfo.isMidiFile()) {
                MidiSoundManager.getInstance().playSoundFileWithStartTime(soundInfo.getFile().getAbsolutePath(), getSprite(), iFloatValue);
            } else {
                SoundManager.getInstance().playSoundFileWithStartTime(soundInfo.getFile().getAbsolutePath(), getSprite(), iFloatValue);
            }
        } catch (InterpretationException e) {
            Log.d(TAG, "Failed to interpret Delay", e);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PlaySoundAtAction$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: PlaySoundAtAction.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/content/actions/PlaySoundAtAction$Companion;", "", "()V", "SECONDS_TO_MILLISECONDS", "", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return PlaySoundAtAction.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("PlaySoundAction", "getSimpleName(...)");
        TAG = "PlaySoundAction";
    }

    public final float runWithMockedSoundManager(SoundManager manager) {
        File file;
        Intrinsics.checkNotNullParameter(manager, "manager");
        try {
            int iFloatValue = (int) (getOffset().interpretFloat(this.scope).floatValue() * 1000);
            if (!getSprite().getSoundList().contains(this.sound)) {
                return -1.0f;
            }
            SoundInfo soundInfo = this.sound;
            if (soundInfo != null) {
                manager.playSoundFileWithStartTime(soundInfo.getFile().getAbsolutePath(), getSprite(), iFloatValue);
            }
            SoundInfo soundInfo2 = this.sound;
            return manager.getDurationOfSoundFile((soundInfo2 == null || (file = soundInfo2.getFile()) == null) ? null : file.getAbsolutePath()) - iFloatValue;
        } catch (InterpretationException e) {
            Log.d(TAG, "Failed to interpret Delay", e);
            return -1.0f;
        }
    }
}
