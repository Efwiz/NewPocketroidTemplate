package org.catrobat.catroid.content.actions;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.SetLookEventId;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.recyclerview.controller.LookController;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/DeleteLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: DeleteLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/content/actions/DeleteLookAction;", "Lorg/catrobat/catroid/content/actions/SingleSpriteEventAction;", "()V", "lookController", "Lorg/catrobat/catroid/ui/recyclerview/controller/LookController;", "xStreamSerializer", "Lorg/catrobat/catroid/io/XstreamSerializer;", "getEventId", "Lorg/catrobat/catroid/content/eventids/EventId;", "setLookController", "", "setNewLookData", "indexOfLookData", "", "setXStreamSerializer", "xstreamSerializer", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeleteLookAction extends SingleSpriteEventAction {
    private LookController lookController = new LookController();
    private XstreamSerializer xStreamSerializer;

    @Override // org.catrobat.catroid.content.actions.EventAction
    public EventId getEventId() {
        Look look;
        int iIndexOf;
        Look look2;
        Sprite sprite = getSprite();
        LookData lookData = null;
        if (sprite != null) {
            Look look3 = sprite.look;
            LookData lookData2 = look3 != null ? look3.getLookData() : null;
            if (lookData2 == null) {
                return null;
            }
            Intrinsics.checkNotNull(lookData2);
            if (lookData2.getIsWebRequest()) {
                setNewLookData(0);
                Sprite sprite2 = getSprite();
                Sprite sprite3 = getSprite();
                if (sprite3 != null && (look2 = sprite3.look) != null) {
                    lookData = look2.getLookData();
                }
                return new SetLookEventId(sprite2, lookData);
            }
            List<LookData> lookList = sprite.getLookList();
            if (lookList == null || lookList.isEmpty() || (iIndexOf = sprite.getLookList().indexOf(lookData2)) == -1) {
                return null;
            }
            setNewLookData(iIndexOf + 1);
            lookData2.invalidate();
            sprite.getLookList().remove(iIndexOf);
            if (this.xStreamSerializer == null) {
                XstreamSerializer xstreamSerializer = XstreamSerializer.getInstance();
                Intrinsics.checkNotNullExpressionValue(xstreamSerializer, "getInstance(...)");
                this.xStreamSerializer = xstreamSerializer;
            }
            XstreamSerializer xstreamSerializer2 = this.xStreamSerializer;
            if (xstreamSerializer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("xStreamSerializer");
                xstreamSerializer2 = null;
            }
            xstreamSerializer2.saveProject(ProjectManager.getInstance().getCurrentProject());
        }
        Sprite sprite4 = getSprite();
        Sprite sprite5 = getSprite();
        if (sprite5 != null && (look = sprite5.look) != null) {
            lookData = look.getLookData();
        }
        return new SetLookEventId(sprite4, lookData);
    }

    private final void setNewLookData(int indexOfLookData) {
        Sprite sprite = getSprite();
        if (sprite != null) {
            List<LookData> lookList = sprite.getLookList();
            if (lookList == null || lookList.isEmpty()) {
                sprite.look.setLookData(null);
                return;
            }
            Look look = sprite.look;
            LookData lookData = look != null ? look.getLookData() : null;
            if (lookData == null) {
                return;
            }
            Intrinsics.checkNotNull(lookData);
            if (sprite.look.getLookData().getIsWebRequest()) {
                sprite.look.setLookData(sprite.getLookList().get(indexOfLookData));
                return;
            }
            if (sprite.getLookList().size() > 1) {
                List<LookData> lookList2 = sprite.getLookList();
                Intrinsics.checkNotNullExpressionValue(lookList2, "getLookList(...)");
                if (Intrinsics.areEqual(CollectionsKt.last((List) lookList2), sprite.look.getLookData())) {
                    Look look2 = sprite.look;
                    List<LookData> lookList3 = sprite.getLookList();
                    Intrinsics.checkNotNullExpressionValue(lookList3, "getLookList(...)");
                    look2.setLookData((LookData) CollectionsKt.first((List) lookList3));
                    return;
                }
                sprite.look.setLookData(sprite.getLookList().get(indexOfLookData));
                return;
            }
            sprite.look.setLookData(null);
        }
    }

    public final void setLookController(LookController lookController) {
        Intrinsics.checkNotNullParameter(lookController, "lookController");
        this.lookController = lookController;
    }

    public final void setXStreamSerializer(XstreamSerializer xstreamSerializer) {
        Intrinsics.checkNotNullParameter(xstreamSerializer, "xstreamSerializer");
        this.xStreamSerializer = xstreamSerializer;
    }
}
