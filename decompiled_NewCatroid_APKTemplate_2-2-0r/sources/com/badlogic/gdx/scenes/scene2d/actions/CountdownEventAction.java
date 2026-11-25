package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Event;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/CountdownEventAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class CountdownEventAction<T extends Event> extends EventAction<T> {
    int count;
    int current;

    public CountdownEventAction(Class<? extends T> cls, int i) {
        super(cls);
        this.count = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.EventAction
    public boolean handle(T t) {
        int i = this.current + 1;
        this.current = i;
        return i >= this.count;
    }
}
