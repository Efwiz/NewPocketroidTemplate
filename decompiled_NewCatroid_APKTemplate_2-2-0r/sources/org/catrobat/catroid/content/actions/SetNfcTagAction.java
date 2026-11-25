package org.catrobat.catroid.content.actions;

import android.nfc.NdefMessage;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetNfcTagAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetNfcTagAction extends Action {
    private static final String TAG = "SetNfcTagAction";
    private boolean firstExecution = true;
    private NdefMessage message;
    private Formula nfcNdefMessage;
    private int nfcTagNdefSpinnerSelection;
    private Scope scope;

    public void setNfcTagNdefSpinnerSelection(int spinnerSelection) {
        this.nfcTagNdefSpinnerSelection = spinnerSelection;
    }

    public void setNfcNdefMessage(Formula nfcNdefMessage) {
        this.nfcNdefMessage = nfcNdefMessage;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.firstExecution = true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Formula formula = this.nfcNdefMessage;
        if (formula == null) {
            return true;
        }
        if (this.firstExecution) {
            try {
                this.message = NfcHandler.createMessage(formula.interpretString(this.scope), this.nfcTagNdefSpinnerSelection);
                synchronized (StageActivity.class) {
                    StageActivity.setNfcTagMessage(this.message);
                }
                this.firstExecution = false;
            } catch (Exception e) {
                Log.d(TAG, "No new message was added to the Stage", e);
                return true;
            }
        }
        return StageActivity.getNfcTagMessage() != this.message;
    }
}
