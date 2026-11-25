package org.catrobat.catroid.content.bricks;

import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlaySoundAndWaitBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlaySoundAndWaitBrick extends PlaySoundBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.PlaySoundBrick
    protected void onViewCreated(View prototypeView) {
        ((TextView) this.view.findViewById(R.id.brick_play_sound_text_view)).setText(R.string.brick_play_sound_and_wait);
    }

    @Override // org.catrobat.catroid.content.bricks.PlaySoundBrick, org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.sound == null || this.sound.getFile() == null || !sprite.getSoundList().contains(this.sound)) {
            return;
        }
        sequence.addAction(sprite.getActionFactory().createPlaySoundAction(sprite, this.sound));
        sequence.addAction(sprite.getActionFactory().createWaitForSoundAction(sprite, sequence, new Formula(Float.valueOf(getDurationOfSound())), this.sound.getFile().getAbsolutePath()));
    }

    private float getDurationOfSound() {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.sound.getFile().getAbsolutePath());
        return Integer.parseInt(mediaMetadataRetriever.extractMetadata(9)) / 1000.0f;
    }
}
