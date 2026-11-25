package org.catrobat.catroid.content.actions;

import android.os.AsyncTask;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.DeviceVariableAccessor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReadVariableFromDeviceAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReadVariableFromDeviceAction extends AsynchronousAction {
    private boolean readActionFinished;
    private UserVariable userVariable;

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (this.userVariable == null) {
            return true;
        }
        return super.act(delta);
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public void initialize() {
        this.readActionFinished = false;
        new ReadTask().execute(this.userVariable);
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public boolean isFinished() {
        return this.readActionFinished;
    }

    public void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReadVariableFromDeviceAction$ReadTask.class
      classes14.dex
     */
    private class ReadTask extends AsyncTask<UserVariable, Void, Void> {
        private ReadTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(UserVariable[] userVariables) {
            DeviceVariableAccessor deviceVariableAccessor = new DeviceVariableAccessor(ProjectManager.getInstance().getCurrentProject().getDirectory());
            for (UserVariable userVariable : userVariables) {
                deviceVariableAccessor.readUserData(userVariable);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void o) {
            ReadVariableFromDeviceAction.this.readActionFinished = true;
        }
    }
}
