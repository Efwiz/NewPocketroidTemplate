package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.math.Vector2;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/InputListener.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class InputListener implements EventListener {
    private static final Vector2 tmpCoords = new Vector2();

    public void enter(InputEvent inputEvent, float f, float f2, int i, Actor actor) {
    }

    public void exit(InputEvent inputEvent, float f, float f2, int i, Actor actor) {
    }

    public boolean keyDown(InputEvent inputEvent, int i) {
        return false;
    }

    public boolean keyTyped(InputEvent inputEvent, char c) {
        return false;
    }

    public boolean keyUp(InputEvent inputEvent, int i) {
        return false;
    }

    public boolean mouseMoved(InputEvent inputEvent, float f, float f2) {
        return false;
    }

    public boolean scrolled(InputEvent inputEvent, float f, float f2, float f3, float f4) {
        return false;
    }

    public boolean touchDown(InputEvent inputEvent, float f, float f2, int i, int i2) {
        return false;
    }

    public void touchDragged(InputEvent inputEvent, float f, float f2, int i) {
    }

    public void touchUp(InputEvent inputEvent, float f, float f2, int i, int i2) {
    }

    @Override // com.badlogic.gdx.scenes.scene2d.EventListener
    public boolean handle(Event event) {
        if (!(event instanceof InputEvent)) {
            return false;
        }
        InputEvent inputEvent = (InputEvent) event;
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[inputEvent.getType().ordinal()];
        if (i == 1) {
            return keyDown(inputEvent, inputEvent.getKeyCode());
        }
        if (i == 2) {
            return keyUp(inputEvent, inputEvent.getKeyCode());
        }
        if (i == 3) {
            return keyTyped(inputEvent, inputEvent.getCharacter());
        }
        Actor listenerActor = inputEvent.getListenerActor();
        Vector2 vector2 = tmpCoords;
        inputEvent.toCoordinates(listenerActor, vector2);
        switch (inputEvent.getType()) {
            case touchDown:
                boolean z = touchDown(inputEvent, vector2.x, vector2.y, inputEvent.getPointer(), inputEvent.getButton());
                if (z && inputEvent.getTouchFocus()) {
                    inputEvent.getStage().addTouchFocus(this, inputEvent.getListenerActor(), inputEvent.getTarget(), inputEvent.getPointer(), inputEvent.getButton());
                }
                return z;
            case touchUp:
                touchUp(inputEvent, vector2.x, vector2.y, inputEvent.getPointer(), inputEvent.getButton());
                return true;
            case touchDragged:
                touchDragged(inputEvent, vector2.x, vector2.y, inputEvent.getPointer());
                return true;
            case mouseMoved:
                return mouseMoved(inputEvent, vector2.x, vector2.y);
            case scrolled:
                return scrolled(inputEvent, vector2.x, vector2.y, inputEvent.getScrollAmountX(), inputEvent.getScrollAmountY());
            case enter:
                enter(inputEvent, vector2.x, vector2.y, inputEvent.getPointer(), inputEvent.getRelatedActor());
                return false;
            case exit:
                exit(inputEvent, vector2.x, vector2.y, inputEvent.getPointer(), inputEvent.getRelatedActor());
                return false;
            default:
                return false;
        }
    }
}
