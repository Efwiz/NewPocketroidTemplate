package org.catrobat.catroid.content.bricks;

import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy;
import org.catrobat.catroid.content.strategy.ShowNotePickerFormulaEditorStrategy;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.ui.UiUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlayNoteForBeatsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlayNoteForBeatsBrick extends FormulaBrick {
    private final transient ShowFormulaEditorStrategy showFormulaEditorStrategy;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_play_note_for_beats;
    }

    public PlayNoteForBeatsBrick() {
        addAllowedBrickField(Brick.BrickField.NOTE_TO_PLAY, R.id.brick_play_note_for_beats_note_edit_text);
        addAllowedBrickField(Brick.BrickField.BEATS_TO_PLAY_NOTE, R.id.brick_play_note_for_beats_beats_edit_text);
        this.showFormulaEditorStrategy = new ShowNotePickerFormulaEditorStrategy();
    }

    public PlayNoteForBeatsBrick(int note, int pausedBeats) {
        this(new Formula(Integer.valueOf(note)), new Formula(Integer.valueOf(pausedBeats)));
    }

    public PlayNoteForBeatsBrick(Formula note, Formula beats) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NOTE_TO_PLAY, note);
        setFormulaWithBrickField(Brick.BrickField.BEATS_TO_PLAY_NOTE, beats);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        if (areAllBrickFieldsNumbers() && view.getId() == this.brickFieldToTextViewIdMap.get(Brick.BrickField.NOTE_TO_PLAY).intValue()) {
            this.showFormulaEditorStrategy.showFormulaEditorToEditFormula(view, new PlayNoteForBeatsBrickCallback(view));
        } else {
            superShowFormulaEditor(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void superShowFormulaEditor(View view) {
        super.showFormulaEditorToEditFormula(view);
    }

    private boolean areAllBrickFieldsNumbers() {
        return isBrickFieldANumber(Brick.BrickField.NOTE_TO_PLAY) && isBrickFieldANumber(Brick.BrickField.BEATS_TO_PLAY_NOTE);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlayNoteForBeatsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NOTE_TO_PLAY), getFormulaWithBrickField(Brick.BrickField.BEATS_TO_PLAY_NOTE)));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlayNoteForBeatsBrick$PlayNoteForBeatsBrickCallback.class
      classes14.dex
     */
    private final class PlayNoteForBeatsBrickCallback implements ShowFormulaEditorStrategy.Callback {
        private final View view;

        private PlayNoteForBeatsBrickCallback(View view) {
            this.view = view;
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void showFormulaEditor(View view) {
            PlayNoteForBeatsBrick.this.superShowFormulaEditor(view);
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void setValue(int value) {
            PlayNoteForBeatsBrick.this.setFormulaWithBrickField(Brick.BrickField.NOTE_TO_PLAY, new Formula(Integer.valueOf(value)));
            PlayNoteForBeatsBrick.this.notifyDataSetChanged(UiUtils.getActivityFromView(this.view));
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public int getValue() {
            try {
                return PlayNoteForBeatsBrick.this.getFormulaWithBrickField(Brick.BrickField.NOTE_TO_PLAY).interpretInteger(null).intValue();
            } catch (InterpretationException unused) {
                return 0;
            }
        }
    }
}
