package org.catrobat.catroid.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.catrobat.catroid.ProjectManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/BroadcastMessageContainer.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class BroadcastMessageContainer {
    private final List<String> broadcastMessages = new ArrayList();

    public void update() {
        Set<String> broadcastMessagesInUse = ProjectManager.getInstance().getCurrentlyEditedScene().getBroadcastMessagesInUse();
        this.broadcastMessages.clear();
        this.broadcastMessages.addAll(broadcastMessagesInUse);
    }

    public boolean addBroadcastMessage(String messageToAdd) {
        return (messageToAdd == null || messageToAdd.isEmpty() || this.broadcastMessages.contains(messageToAdd) || !this.broadcastMessages.add(messageToAdd)) ? false : true;
    }

    public boolean removeBroadcastMessage(String messageToRemove) {
        return messageToRemove != null && !messageToRemove.isEmpty() && this.broadcastMessages.contains(messageToRemove) && this.broadcastMessages.remove(messageToRemove);
    }

    public List<String> getBroadcastMessages() {
        if (this.broadcastMessages.size() == 0) {
            update();
        }
        return this.broadcastMessages;
    }
}
