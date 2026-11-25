package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/ActorGestureListener.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ActorGestureListener implements EventListener {
    static final Vector2 tmpCoords = new Vector2();
    static final Vector2 tmpCoords2 = new Vector2();
    Actor actor;
    private final GestureDetector detector;
    InputEvent event;
    Actor touchDownTarget;

    public void fling(InputEvent inputEvent, float f, float f2, int i) {
    }

    public boolean longPress(Actor actor, float f, float f2) {
        return false;
    }

    public void pan(InputEvent inputEvent, float f, float f2, float f3, float f4) {
    }

    public void panStop(InputEvent inputEvent, float f, float f2, int i, int i2) {
    }

    public void pinch(InputEvent inputEvent, Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5) {
    }

    public void tap(InputEvent inputEvent, float f, float f2, int i, int i2) {
    }

    public void touchDown(InputEvent inputEvent, float f, float f2, int i, int i2) {
    }

    public void touchUp(InputEvent inputEvent, float f, float f2, int i, int i2) {
    }

    public void zoom(InputEvent inputEvent, float f, float f2) {
    }

    public ActorGestureListener() {
        this(20.0f, 0.4f, 1.1f, 2.1474836E9f);
    }

    public ActorGestureListener(float f, float f2, float f3, float f4) {
        this.detector = new GestureDetector(f, f2, f3, f4, new GestureDetector.GestureAdapter() { // from class: com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener.1
            private final Vector2 initialPointer1 = new Vector2();
            private final Vector2 initialPointer2 = new Vector2();
            private final Vector2 pointer1 = new Vector2();
            private final Vector2 pointer2 = new Vector2();

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean tap(float f5, float f6, int i, int i2) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(f5, f6));
                ActorGestureListener actorGestureListener = ActorGestureListener.this;
                actorGestureListener.tap(actorGestureListener.event, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, i, i2);
                return true;
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean longPress(float f5, float f6) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(f5, f6));
                ActorGestureListener actorGestureListener = ActorGestureListener.this;
                return actorGestureListener.longPress(actorGestureListener.actor, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y);
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean fling(float f5, float f6, int i) {
                stageToLocalAmount(ActorGestureListener.tmpCoords.set(f5, f6));
                ActorGestureListener actorGestureListener = ActorGestureListener.this;
                actorGestureListener.fling(actorGestureListener.event, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, i);
                return true;
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean pan(float f5, float f6, float f7, float f8) {
                stageToLocalAmount(ActorGestureListener.tmpCoords.set(f7, f8));
                float f9 = ActorGestureListener.tmpCoords.x;
                float f10 = ActorGestureListener.tmpCoords.y;
                ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(f5, f6));
                ActorGestureListener actorGestureListener = ActorGestureListener.this;
                actorGestureListener.pan(actorGestureListener.event, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, f9, f10);
                return true;
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean panStop(float f5, float f6, int i, int i2) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(f5, f6));
                ActorGestureListener actorGestureListener = ActorGestureListener.this;
                actorGestureListener.panStop(actorGestureListener.event, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, i, i2);
                return true;
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean zoom(float f5, float f6) {
                ActorGestureListener actorGestureListener = ActorGestureListener.this;
                actorGestureListener.zoom(actorGestureListener.event, f5, f6);
                return true;
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean pinch(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.initialPointer1.set(vector2));
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.initialPointer2.set(vector3));
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.pointer1.set(vector4));
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.pointer2.set(vector5));
                ActorGestureListener actorGestureListener = ActorGestureListener.this;
                actorGestureListener.pinch(actorGestureListener.event, this.initialPointer1, this.initialPointer2, this.pointer1, this.pointer2);
                return true;
            }

            private void stageToLocalAmount(Vector2 vector2) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(vector2);
                vector2.sub(ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords2.set(0.0f, 0.0f)));
            }
        });
    }

    @Override // com.badlogic.gdx.scenes.scene2d.EventListener
    public boolean handle(Event event) {
        if (!(event instanceof InputEvent)) {
            return false;
        }
        InputEvent inputEvent = (InputEvent) event;
        int i = AnonymousClass2.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[inputEvent.getType().ordinal()];
        if (i == 1) {
            this.actor = inputEvent.getListenerActor();
            this.touchDownTarget = inputEvent.getTarget();
            this.detector.touchDown(inputEvent.getStageX(), inputEvent.getStageY(), inputEvent.getPointer(), inputEvent.getButton());
            Actor actor = this.actor;
            Vector2 vector2 = tmpCoords;
            actor.stageToLocalCoordinates(vector2.set(inputEvent.getStageX(), inputEvent.getStageY()));
            touchDown(inputEvent, vector2.x, vector2.y, inputEvent.getPointer(), inputEvent.getButton());
            if (inputEvent.getTouchFocus()) {
                inputEvent.getStage().addTouchFocus(this, inputEvent.getListenerActor(), inputEvent.getTarget(), inputEvent.getPointer(), inputEvent.getButton());
            }
            return true;
        }
        if (i != 2) {
            if (i != 3) {
                return false;
            }
            this.event = inputEvent;
            this.actor = inputEvent.getListenerActor();
            this.detector.touchDragged(inputEvent.getStageX(), inputEvent.getStageY(), inputEvent.getPointer());
            return true;
        }
        boolean zIsTouchFocusCancel = inputEvent.isTouchFocusCancel();
        if (zIsTouchFocusCancel) {
            this.detector.reset();
        } else {
            this.event = inputEvent;
            this.actor = inputEvent.getListenerActor();
            this.detector.touchUp(inputEvent.getStageX(), inputEvent.getStageY(), inputEvent.getPointer(), inputEvent.getButton());
            Actor actor2 = this.actor;
            Vector2 vector3 = tmpCoords;
            actor2.stageToLocalCoordinates(vector3.set(inputEvent.getStageX(), inputEvent.getStageY()));
            touchUp(inputEvent, vector3.x, vector3.y, inputEvent.getPointer(), inputEvent.getButton());
        }
        this.event = null;
        this.actor = null;
        this.touchDownTarget = null;
        return !zIsTouchFocusCancel;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/ActorGestureListener$2.class
      classes8.dex
     */
    /* JADX INFO: renamed from: com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type;

        static {
            int[] iArr = new int[InputEvent.Type.values().length];
            $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type = iArr;
            try {
                iArr[InputEvent.Type.touchDown.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[InputEvent.Type.touchUp.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[InputEvent.Type.touchDragged.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public GestureDetector getGestureDetector() {
        return this.detector;
    }

    public Actor getTouchDownTarget() {
        return this.touchDownTarget;
    }
}
