package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/SplitPane.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SplitPane extends WidgetGroup {
    boolean cursorOverHandle;
    private Actor firstWidget;
    private final Rectangle firstWidgetBounds;
    final Rectangle handleBounds;
    Vector2 handlePosition;
    Vector2 lastPoint;
    float maxAmount;
    float minAmount;
    private Actor secondWidget;
    private final Rectangle secondWidgetBounds;
    float splitAmount;
    SplitPaneStyle style;
    private final Rectangle tempScissors;
    boolean vertical;

    public SplitPane(Actor actor, Actor actor2, boolean z, Skin skin) {
        this(actor, actor2, z, skin, "default-".concat(z ? "vertical" : "horizontal"));
    }

    public SplitPane(Actor actor, Actor actor2, boolean z, Skin skin, String str) {
        this(actor, actor2, z, (SplitPaneStyle) skin.get(str, SplitPaneStyle.class));
    }

    public SplitPane(Actor actor, Actor actor2, boolean z, SplitPaneStyle splitPaneStyle) {
        this.splitAmount = 0.5f;
        this.maxAmount = 1.0f;
        this.firstWidgetBounds = new Rectangle();
        this.secondWidgetBounds = new Rectangle();
        this.handleBounds = new Rectangle();
        this.tempScissors = new Rectangle();
        this.lastPoint = new Vector2();
        this.handlePosition = new Vector2();
        this.vertical = z;
        setStyle(splitPaneStyle);
        setFirstWidget(actor);
        setSecondWidget(actor2);
        setSize(getPrefWidth(), getPrefHeight());
        initialize();
    }

    private void initialize() {
        addListener(new InputListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.SplitPane.1
            int draggingPointer = -1;

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent inputEvent, float f, float f2, int i, int i2) {
                if (this.draggingPointer != -1) {
                    return false;
                }
                if ((i == 0 && i2 != 0) || !SplitPane.this.handleBounds.contains(f, f2)) {
                    return false;
                }
                this.draggingPointer = i;
                SplitPane.this.lastPoint.set(f, f2);
                SplitPane.this.handlePosition.set(SplitPane.this.handleBounds.x, SplitPane.this.handleBounds.y);
                return true;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchUp(InputEvent inputEvent, float f, float f2, int i, int i2) {
                if (i == this.draggingPointer) {
                    this.draggingPointer = -1;
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchDragged(InputEvent inputEvent, float f, float f2, int i) {
                if (i != this.draggingPointer) {
                    return;
                }
                Drawable drawable = SplitPane.this.style.handle;
                if (!SplitPane.this.vertical) {
                    float f3 = f - SplitPane.this.lastPoint.x;
                    float width = SplitPane.this.getWidth() - drawable.getMinWidth();
                    float f4 = SplitPane.this.handlePosition.x + f3;
                    SplitPane.this.handlePosition.x = f4;
                    SplitPane.this.splitAmount = Math.min(width, Math.max(0.0f, f4)) / width;
                    SplitPane.this.lastPoint.set(f, f2);
                } else {
                    float f5 = f2 - SplitPane.this.lastPoint.y;
                    float height = SplitPane.this.getHeight() - drawable.getMinHeight();
                    float f6 = SplitPane.this.handlePosition.y + f5;
                    SplitPane.this.handlePosition.y = f6;
                    SplitPane.this.splitAmount = 1.0f - (Math.min(height, Math.max(0.0f, f6)) / height);
                    SplitPane.this.lastPoint.set(f, f2);
                }
                SplitPane.this.invalidate();
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean mouseMoved(InputEvent inputEvent, float f, float f2) {
                SplitPane splitPane = SplitPane.this;
                splitPane.cursorOverHandle = splitPane.handleBounds.contains(f, f2);
                return false;
            }
        });
    }

    public void setStyle(SplitPaneStyle splitPaneStyle) {
        this.style = splitPaneStyle;
        invalidateHierarchy();
    }

    public SplitPaneStyle getStyle() {
        return this.style;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        clampSplitAmount();
        if (!this.vertical) {
            calculateHorizBoundsAndPositions();
        } else {
            calculateVertBoundsAndPositions();
        }
        Actor actor = this.firstWidget;
        if (actor != 0) {
            Rectangle rectangle = this.firstWidgetBounds;
            actor.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            if (actor instanceof Layout) {
                ((Layout) actor).validate();
            }
        }
        Actor actor2 = this.secondWidget;
        if (actor2 != 0) {
            Rectangle rectangle2 = this.secondWidgetBounds;
            actor2.setBounds(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
            if (actor2 instanceof Layout) {
                ((Layout) actor2).validate();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        float prefWidth;
        Actor actor = this.firstWidget;
        float prefWidth2 = 0.0f;
        if (actor == 0) {
            prefWidth = 0.0f;
        } else {
            prefWidth = actor instanceof Layout ? ((Layout) actor).getPrefWidth() : actor.getWidth();
        }
        Actor actor2 = this.secondWidget;
        if (actor2 != 0) {
            prefWidth2 = actor2 instanceof Layout ? ((Layout) actor2).getPrefWidth() : actor2.getWidth();
        }
        return this.vertical ? Math.max(prefWidth, prefWidth2) : prefWidth + this.style.handle.getMinWidth() + prefWidth2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        float prefHeight;
        Actor actor = this.firstWidget;
        float prefHeight2 = 0.0f;
        if (actor == 0) {
            prefHeight = 0.0f;
        } else {
            prefHeight = actor instanceof Layout ? ((Layout) actor).getPrefHeight() : actor.getHeight();
        }
        Actor actor2 = this.secondWidget;
        if (actor2 != 0) {
            prefHeight2 = actor2 instanceof Layout ? ((Layout) actor2).getPrefHeight() : actor2.getHeight();
        }
        return !this.vertical ? Math.max(prefHeight, prefHeight2) : prefHeight + this.style.handle.getMinHeight() + prefHeight2;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinWidth() {
        Object obj = this.firstWidget;
        float minWidth = obj instanceof Layout ? ((Layout) obj).getMinWidth() : 0.0f;
        Object obj2 = this.secondWidget;
        float minWidth2 = obj2 instanceof Layout ? ((Layout) obj2).getMinWidth() : 0.0f;
        return this.vertical ? Math.max(minWidth, minWidth2) : minWidth + this.style.handle.getMinWidth() + minWidth2;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinHeight() {
        Object obj = this.firstWidget;
        float minHeight = obj instanceof Layout ? ((Layout) obj).getMinHeight() : 0.0f;
        Object obj2 = this.secondWidget;
        float minHeight2 = obj2 instanceof Layout ? ((Layout) obj2).getMinHeight() : 0.0f;
        return !this.vertical ? Math.max(minHeight, minHeight2) : minHeight + this.style.handle.getMinHeight() + minHeight2;
    }

    public void setVertical(boolean z) {
        if (this.vertical == z) {
            return;
        }
        this.vertical = z;
        invalidateHierarchy();
    }

    public boolean isVertical() {
        return this.vertical;
    }

    private void calculateHorizBoundsAndPositions() {
        Drawable drawable = this.style.handle;
        float height = getHeight();
        float width = getWidth() - drawable.getMinWidth();
        float f = (int) (this.splitAmount * width);
        float minWidth = drawable.getMinWidth();
        this.firstWidgetBounds.set(0.0f, 0.0f, f, height);
        this.secondWidgetBounds.set(f + minWidth, 0.0f, width - f, height);
        this.handleBounds.set(f, 0.0f, minWidth, height);
    }

    private void calculateVertBoundsAndPositions() {
        Drawable drawable = this.style.handle;
        float width = getWidth();
        float height = getHeight();
        float minHeight = height - drawable.getMinHeight();
        float f = (int) (this.splitAmount * minHeight);
        float f2 = minHeight - f;
        float minHeight2 = drawable.getMinHeight();
        this.firstWidgetBounds.set(0.0f, height - f, width, f);
        this.secondWidgetBounds.set(0.0f, 0.0f, width, f2);
        this.handleBounds.set(0.0f, f2, width, minHeight2);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        Stage stage = getStage();
        if (stage == null) {
            return;
        }
        validate();
        Color color = getColor();
        float f2 = color.a * f;
        applyTransform(batch, computeTransform());
        Actor actor = this.firstWidget;
        if (actor != null && actor.isVisible()) {
            batch.flush();
            stage.calculateScissors(this.firstWidgetBounds, this.tempScissors);
            if (ScissorStack.pushScissors(this.tempScissors)) {
                this.firstWidget.draw(batch, f2);
                batch.flush();
                ScissorStack.popScissors();
            }
        }
        Actor actor2 = this.secondWidget;
        if (actor2 != null && actor2.isVisible()) {
            batch.flush();
            stage.calculateScissors(this.secondWidgetBounds, this.tempScissors);
            if (ScissorStack.pushScissors(this.tempScissors)) {
                this.secondWidget.draw(batch, f2);
                batch.flush();
                ScissorStack.popScissors();
            }
        }
        batch.setColor(color.r, color.g, color.b, f2);
        this.style.handle.draw(batch, this.handleBounds.x, this.handleBounds.y, this.handleBounds.width, this.handleBounds.height);
        resetTransform(batch);
    }

    public void setSplitAmount(float f) {
        this.splitAmount = f;
        invalidate();
    }

    public float getSplitAmount() {
        return this.splitAmount;
    }

    protected void clampSplitAmount() {
        float fMax = this.minAmount;
        float fMin = this.maxAmount;
        if (this.vertical) {
            float height = getHeight() - this.style.handle.getMinHeight();
            Object obj = this.firstWidget;
            if (obj instanceof Layout) {
                fMax = Math.max(fMax, Math.min(((Layout) obj).getMinHeight() / height, 1.0f));
            }
            Object obj2 = this.secondWidget;
            if (obj2 instanceof Layout) {
                fMin = Math.min(fMin, 1.0f - Math.min(((Layout) obj2).getMinHeight() / height, 1.0f));
            }
        } else {
            float width = getWidth() - this.style.handle.getMinWidth();
            Object obj3 = this.firstWidget;
            if (obj3 instanceof Layout) {
                fMax = Math.max(fMax, Math.min(((Layout) obj3).getMinWidth() / width, 1.0f));
            }
            Object obj4 = this.secondWidget;
            if (obj4 instanceof Layout) {
                fMin = Math.min(fMin, 1.0f - Math.min(((Layout) obj4).getMinWidth() / width, 1.0f));
            }
        }
        if (fMax > fMin) {
            this.splitAmount = (fMax + fMin) * 0.5f;
        } else {
            this.splitAmount = Math.max(Math.min(this.splitAmount, fMin), fMax);
        }
    }

    public float getMinSplitAmount() {
        return this.minAmount;
    }

    public void setMinSplitAmount(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new GdxRuntimeException("minAmount has to be >= 0 and <= 1");
        }
        this.minAmount = f;
    }

    public float getMaxSplitAmount() {
        return this.maxAmount;
    }

    public void setMaxSplitAmount(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new GdxRuntimeException("maxAmount has to be >= 0 and <= 1");
        }
        this.maxAmount = f;
    }

    public void setFirstWidget(Actor actor) {
        Actor actor2 = this.firstWidget;
        if (actor2 != null) {
            super.removeActor(actor2);
        }
        this.firstWidget = actor;
        if (actor != null) {
            super.addActor(actor);
        }
        invalidate();
    }

    public void setSecondWidget(Actor actor) {
        Actor actor2 = this.secondWidget;
        if (actor2 != null) {
            super.removeActor(actor2);
        }
        this.secondWidget = actor;
        if (actor != null) {
            super.addActor(actor);
        }
        invalidate();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use SplitPane#setWidget.");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public void addActorAt(int i, Actor actor) {
        throw new UnsupportedOperationException("Use SplitPane#setWidget.");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public void addActorBefore(Actor actor, Actor actor2) {
        throw new UnsupportedOperationException("Use SplitPane#setWidget.");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public boolean removeActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        if (actor == this.firstWidget) {
            setFirstWidget(null);
            return true;
        }
        if (actor == this.secondWidget) {
            setSecondWidget(null);
        }
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public boolean removeActor(Actor actor, boolean z) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        if (actor == this.firstWidget) {
            super.removeActor(actor, z);
            this.firstWidget = null;
            invalidate();
            return true;
        }
        if (actor != this.secondWidget) {
            return false;
        }
        super.removeActor(actor, z);
        this.secondWidget = null;
        invalidate();
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public Actor removeActorAt(int i, boolean z) {
        Actor actorRemoveActorAt = super.removeActorAt(i, z);
        if (actorRemoveActorAt == this.firstWidget) {
            super.removeActor(actorRemoveActorAt, z);
            this.firstWidget = null;
            invalidate();
        } else if (actorRemoveActorAt == this.secondWidget) {
            super.removeActor(actorRemoveActorAt, z);
            this.secondWidget = null;
            invalidate();
        }
        return actorRemoveActorAt;
    }

    public boolean isCursorOverHandle() {
        return this.cursorOverHandle;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/SplitPane$SplitPaneStyle.class
      classes8.dex
     */
    public static class SplitPaneStyle {
        public Drawable handle;

        public SplitPaneStyle() {
        }

        public SplitPaneStyle(Drawable drawable) {
            this.handle = drawable;
        }

        public SplitPaneStyle(SplitPaneStyle splitPaneStyle) {
            this.handle = splitPaneStyle.handle;
        }
    }
}
