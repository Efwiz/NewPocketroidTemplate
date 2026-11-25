package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/ChangeListener.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class ChangeListener implements EventListener {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/ChangeListener$ChangeEvent.class
      classes8.dex
     */
    public static class ChangeEvent extends Event {
    }

    public abstract void changed(ChangeEvent changeEvent, Actor actor);

    @Override // com.badlogic.gdx.scenes.scene2d.EventListener
    public boolean handle(Event event) {
        if (!(event instanceof ChangeEvent)) {
            return false;
        }
        changed((ChangeEvent) event, event.getTarget());
        return false;
    }
}
