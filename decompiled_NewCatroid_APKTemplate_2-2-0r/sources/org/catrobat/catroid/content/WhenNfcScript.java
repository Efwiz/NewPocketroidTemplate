package org.catrobat.catroid.content;

import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenNfcBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.NfcEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenNfcScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenNfcScript extends Script {
    private static final long serialVersionUID = 1;
    private boolean matchAll = true;
    private NfcTagData nfcTag;

    public WhenNfcScript() {
    }

    public WhenNfcScript(NfcTagData nfcTag) {
        this.nfcTag = nfcTag;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenNfcBrick(this);
        }
        return this.scriptBrick;
    }

    @Override // org.catrobat.catroid.content.Script
    public void addRequiredResources(final Brick.ResourcesSet resourcesSet) {
        resourcesSet.add((Integer) 16);
        super.addRequiredResources(resourcesSet);
    }

    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }

    public NfcTagData getNfcTag() {
        return this.nfcTag;
    }

    public void setNfcTag(NfcTagData nfcTag) {
        this.nfcTag = nfcTag;
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        if (this.matchAll) {
            return new EventId(5);
        }
        if (this.nfcTag != null) {
            return new NfcEventId(this.nfcTag.getNfcTagUid());
        }
        throw new RuntimeException("We want to identify a specific NfcTag, but null is given.");
    }
}
