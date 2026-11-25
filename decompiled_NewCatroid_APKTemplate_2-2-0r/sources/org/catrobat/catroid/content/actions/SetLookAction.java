package org.catrobat.catroid.content.actions;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.SetLookEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetLookAction;", "Lorg/catrobat/catroid/content/actions/MultiSpriteEventAction;", "()V", "lookData", "Lorg/catrobat/catroid/common/LookData;", "getLookData", "()Lorg/catrobat/catroid/common/LookData;", "setLookData", "(Lorg/catrobat/catroid/common/LookData;)V", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", "getEventId", "Lorg/catrobat/catroid/content/eventids/EventId;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SetLookAction extends MultiSpriteEventAction {
    private LookData lookData;
    private Sprite sprite;

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final LookData getLookData() {
        return this.lookData;
    }

    public final void setLookData(LookData lookData) {
        this.lookData = lookData;
    }

    @Override // org.catrobat.catroid.content.actions.EventAction
    public EventId getEventId() {
        Sprite sprite;
        List<LookData> lookList;
        if (this.lookData == null || (sprite = getSprite()) == null || (lookList = sprite.getLookList()) == null || !lookList.contains(this.lookData)) {
            return null;
        }
        Sprite sprite2 = getSprite();
        Intrinsics.checkNotNull(sprite2);
        sprite2.look.setLookData(this.lookData);
        return new SetLookEventId(getSprite(), this.lookData);
    }
}
