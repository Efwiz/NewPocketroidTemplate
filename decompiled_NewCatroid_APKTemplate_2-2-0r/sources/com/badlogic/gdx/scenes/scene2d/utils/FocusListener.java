package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/FocusListener.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class FocusListener implements EventListener {
    public void keyboardFocusChanged(FocusEvent focusEvent, Actor actor, boolean z) {
    }

    public void scrollFocusChanged(FocusEvent focusEvent, Actor actor, boolean z) {
    }

    @Override // com.badlogic.gdx.scenes.scene2d.EventListener
    public boolean handle(Event event) {
        if (!(event instanceof FocusEvent)) {
            return false;
        }
        FocusEvent focusEvent = (FocusEvent) event;
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$scenes$scene2d$utils$FocusListener$FocusEvent$Type[focusEvent.getType().ordinal()];
        if (i == 1) {
            keyboardFocusChanged(focusEvent, event.getTarget(), focusEvent.isFocused());
        } else if (i == 2) {
            scrollFocusChanged(focusEvent, event.getTarget(), focusEvent.isFocused());
        }
        return false;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/FocusListener$1.class
      classes8.dex
     */
    /* JADX INFO: renamed from: com.badlogic.gdx.scenes.scene2d.utils.FocusListener$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$scenes$scene2d$utils$FocusListener$FocusEvent$Type;

        static {
            int[] iArr = new int[FocusEvent.Type.values().length];
            $SwitchMap$com$badlogic$gdx$scenes$scene2d$utils$FocusListener$FocusEvent$Type = iArr;
            try {
                iArr[FocusEvent.Type.keyboard.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$utils$FocusListener$FocusEvent$Type[FocusEvent.Type.scroll.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/FocusListener$FocusEvent.class
      classes8.dex
     */
    public static class FocusEvent extends Event {
        private boolean focused;
        private Actor relatedActor;
        private Type type;

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/FocusListener$FocusEvent$Type.class
          classes8.dex
         */
        public enum Type {
            keyboard,
            scroll
        }

        @Override // com.badlogic.gdx.scenes.scene2d.Event, com.badlogic.gdx.utils.Pool.Poolable
        public void reset() {
            super.reset();
            this.relatedActor = null;
        }

        public boolean isFocused() {
            return this.focused;
        }

        public void setFocused(boolean z) {
            this.focused = z;
        }

        public Type getType() {
            return this.type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Actor getRelatedActor() {
            return this.relatedActor;
        }

        public void setRelatedActor(Actor actor) {
            this.relatedActor = actor;
        }
    }
}
