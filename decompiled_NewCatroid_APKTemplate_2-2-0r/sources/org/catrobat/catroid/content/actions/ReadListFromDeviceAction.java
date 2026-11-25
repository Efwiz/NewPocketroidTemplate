package org.catrobat.catroid.content.actions;

import android.os.AsyncTask;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.io.DeviceListAccessor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReadListFromDeviceAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReadListFromDeviceAction extends AsynchronousAction {
    private boolean readActionFinished;
    private UserList userList;

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (this.userList == null) {
            return true;
        }
        return super.act(delta);
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public void initialize() {
        this.readActionFinished = false;
        new ReadTask().execute(this.userList);
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public boolean isFinished() {
        return this.readActionFinished;
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReadListFromDeviceAction$ReadTask.class
      classes14.dex
     */
    private class ReadTask extends AsyncTask<UserList, Void, Void> {
        private ReadTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(UserList[] userList) {
            DeviceListAccessor deviceListAccessor = new DeviceListAccessor(ProjectManager.getInstance().getCurrentProject().getDirectory());
            for (UserList userList2 : userList) {
                deviceListAccessor.readUserData(userList2);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void o) {
            ReadListFromDeviceAction.this.readActionFinished = true;
        }
    }
}
