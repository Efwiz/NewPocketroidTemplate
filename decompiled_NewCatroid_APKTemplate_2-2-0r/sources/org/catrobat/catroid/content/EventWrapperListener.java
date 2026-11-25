package org.catrobat.catroid.content;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.ScriptSequenceActionWithWaiter;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.UserDefinedBrickEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/EventWrapperListener.class
  classes14.dex
 */
/* JADX INFO: compiled from: EventWrapperListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u000bH\u0002J\u0018\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/content/EventWrapperListener;", "Lcom/badlogic/gdx/scenes/scene2d/EventListener;", "look", "Lorg/catrobat/catroid/content/Look;", "(Lorg/catrobat/catroid/content/Look;)V", "handle", "", "event", "Lcom/badlogic/gdx/scenes/scene2d/Event;", "handleEvent", "", "Lorg/catrobat/catroid/content/EventWrapper;", "handleUserBrickEvent", "sequenceAction", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EventWrapperListener implements EventListener {
    private final Look look;

    public EventWrapperListener(Look look) {
        Intrinsics.checkNotNullParameter(look, "look");
        this.look = look;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.EventListener
    public boolean handle(Event event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!(event instanceof EventWrapper)) {
            return false;
        }
        handleEvent((EventWrapper) event);
        return true;
    }

    private final void handleEvent(EventWrapper event) {
        Look look = this.look;
        Collection<ScriptSequenceAction> collection = look.sprite.getIdToEventThreadMap().get(event.getEventId());
        Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
        for (ScriptSequenceAction scriptSequenceAction : collection) {
            if (event.getEventId() instanceof UserDefinedBrickEventId) {
                Intrinsics.checkNotNull(scriptSequenceAction);
                handleUserBrickEvent(scriptSequenceAction, event);
                return;
            }
            look.stopThreadWithScript(scriptSequenceAction.getScript());
            Sprite sprite = look.sprite;
            Intrinsics.checkNotNullExpressionValue(sprite, "sprite");
            if (event.addSpriteToWaitList$catroid_apktemplateTemplateRelease(sprite)) {
                Intrinsics.checkNotNull(scriptSequenceAction);
                Sprite sprite2 = look.sprite;
                Intrinsics.checkNotNullExpressionValue(sprite2, "sprite");
                look.startThread(new ScriptSequenceActionWithWaiter(scriptSequenceAction, event, sprite2));
            } else {
                look.startThread(scriptSequenceAction);
            }
        }
    }

    private final void handleUserBrickEvent(ScriptSequenceAction sequenceAction, EventWrapper event) {
        Look look = this.look;
        Script script = sequenceAction.getScript();
        Intrinsics.checkNotNull(script, "null cannot be cast to non-null type org.catrobat.catroid.content.UserDefinedScript");
        Script scriptClone = ((UserDefinedScript) script).clone();
        Intrinsics.checkNotNull(scriptClone, "null cannot be cast to non-null type org.catrobat.catroid.content.UserDefinedScript");
        UserDefinedScript userDefinedScript = (UserDefinedScript) scriptClone;
        EventId eventId = event.getEventId();
        Intrinsics.checkNotNull(eventId, "null cannot be cast to non-null type org.catrobat.catroid.content.eventids.UserDefinedBrickEventId");
        userDefinedScript.setUserDefinedBrickInputs(((UserDefinedBrickEventId) eventId).userBrickParameters);
        ScriptSequenceAction scriptSequenceActionCloneAndChangeScript = sequenceAction.cloneAndChangeScript(userDefinedScript);
        Intrinsics.checkNotNullExpressionValue(scriptSequenceActionCloneAndChangeScript, "cloneAndChangeScript(...)");
        scriptSequenceActionCloneAndChangeScript.getScript().run(look.sprite, scriptSequenceActionCloneAndChangeScript);
        Sprite sprite = look.sprite;
        Intrinsics.checkNotNullExpressionValue(sprite, "sprite");
        event.addSpriteToWaitList$catroid_apktemplateTemplateRelease(sprite);
        Sprite sprite2 = look.sprite;
        Intrinsics.checkNotNullExpressionValue(sprite2, "sprite");
        look.startThread(new ScriptSequenceActionWithWaiter(scriptSequenceActionCloneAndChangeScript, event, sprite2));
    }
}
