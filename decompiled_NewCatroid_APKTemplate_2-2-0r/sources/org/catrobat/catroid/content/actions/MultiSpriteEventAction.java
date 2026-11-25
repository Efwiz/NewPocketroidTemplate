package org.catrobat.catroid.content.actions;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/MultiSpriteEventAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: MultiSpriteEventAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/content/actions/MultiSpriteEventAction;", "Lorg/catrobat/catroid/content/actions/EventAction;", "()V", "receivingSprites", "", "Lorg/catrobat/catroid/content/Sprite;", "getReceivingSprites", "()Ljava/util/List;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class MultiSpriteEventAction extends EventAction {
    private final List<Sprite> receivingSprites;

    public MultiSpriteEventAction() {
        List<Sprite> spriteListWithClones = ProjectManager.getInstance().getCurrentProject().getSpriteListWithClones();
        Intrinsics.checkNotNullExpressionValue(spriteListWithClones, "getSpriteListWithClones(...)");
        this.receivingSprites = spriteListWithClones;
    }

    @Override // org.catrobat.catroid.content.actions.EventAction
    protected List<Sprite> getReceivingSprites() {
        return this.receivingSprites;
    }
}
