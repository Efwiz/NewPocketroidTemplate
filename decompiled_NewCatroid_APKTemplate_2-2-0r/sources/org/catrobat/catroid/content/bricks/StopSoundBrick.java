package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StopSoundBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: StopSoundBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u00020\u00030\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001aH\u0016J\u001a\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001a2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001aH\u0016J\u0018\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020!H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/content/bricks/StopSoundBrick;", "Lorg/catrobat/catroid/content/bricks/BrickBaseType;", "Lorg/catrobat/catroid/content/bricks/brickspinner/BrickSpinner$OnItemSelectedListener;", "Lorg/catrobat/catroid/common/SoundInfo;", "Lorg/catrobat/catroid/ui/recyclerview/dialog/dialoginterface/NewItemInterface;", "()V", "sound", "getSound", "()Lorg/catrobat/catroid/common/SoundInfo;", "setSound", "(Lorg/catrobat/catroid/common/SoundInfo;)V", "spinner", "Lorg/catrobat/catroid/content/bricks/brickspinner/BrickSpinner;", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "addItem", "item", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "getViewResource", "", "onEditOptionSelected", "spinnerId", "onItemSelected", "onNewOptionSelected", "onStringOptionSelected", "string", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StopSoundBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<SoundInfo>, NewItemInterface<SoundInfo> {
    private SoundInfo sound;
    private transient BrickSpinner<SoundInfo> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_stop_sound;
    }

    public void onEditOptionSelected(int spinnerId) {
    }

    public void onStringOptionSelected(int spinnerId, String string) {
        Intrinsics.checkNotNullParameter(string, "string");
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public /* bridge */ /* synthetic */ void onEditOptionSelected(Integer num) {
        onEditOptionSelected(num.intValue());
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public /* bridge */ /* synthetic */ void onItemSelected(Integer num, SoundInfo soundInfo) {
        onItemSelected(num.intValue(), soundInfo);
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public /* bridge */ /* synthetic */ void onNewOptionSelected(Integer num) {
        onNewOptionSelected(num.intValue());
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public /* bridge */ /* synthetic */ void onStringOptionSelected(Integer num, String str) {
        onStringOptionSelected(num.intValue(), str);
    }

    public final SoundInfo getSound() {
        return this.sound;
    }

    public final void setSound(SoundInfo soundInfo) {
        this.sound = soundInfo;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        List listMutableListOf = CollectionsKt.mutableListOf(new NewOption(context.getString(R.string.new_option)));
        List<SoundInfo> soundList = ProjectManager.getInstance().getCurrentSprite().getSoundList();
        Intrinsics.checkNotNullExpressionValue(soundList, "getSoundList(...)");
        listMutableListOf.addAll(soundList);
        BrickSpinner<SoundInfo> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_stop_sound_spinner), this.view, listMutableListOf);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        brickSpinner.setSelection(this.sound);
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    public void onNewOptionSelected(int spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        SpriteActivity spriteActivity = activityFromView instanceof SpriteActivity ? (SpriteActivity) activityFromView : null;
        if (spriteActivity != null) {
            spriteActivity.registerOnNewSoundListener(this);
            spriteActivity.handleAddSoundButton();
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface
    public void addItem(SoundInfo item) {
        Intrinsics.checkNotNullParameter(item, "item");
        BrickSpinner<SoundInfo> brickSpinner = this.spinner;
        BrickSpinner<SoundInfo> brickSpinner2 = null;
        if (brickSpinner == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spinner");
            brickSpinner = null;
        }
        SoundInfo soundInfo = item;
        brickSpinner.add(soundInfo);
        BrickSpinner<SoundInfo> brickSpinner3 = this.spinner;
        if (brickSpinner3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spinner");
        } else {
            brickSpinner2 = brickSpinner3;
        }
        brickSpinner2.setSelection(soundInfo);
    }

    public void onItemSelected(int spinnerId, SoundInfo item) {
        this.sound = item;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        sequence.addAction(sprite.getActionFactory().createStopSoundAction(sprite, this.sound));
    }
}
