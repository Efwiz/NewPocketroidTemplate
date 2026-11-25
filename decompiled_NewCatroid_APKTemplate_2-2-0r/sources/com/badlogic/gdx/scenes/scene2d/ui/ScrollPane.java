package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ScrollPane.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ScrollPane extends WidgetGroup {
    private Actor actor;
    final Rectangle actorArea;
    private final Rectangle actorCullingArea;
    float amountX;
    float amountY;
    boolean cancelTouchFocus;
    private boolean clamp;
    boolean disableX;
    boolean disableY;
    int draggingPointer;
    float fadeAlpha;
    float fadeAlphaSeconds;
    float fadeDelay;
    float fadeDelaySeconds;
    boolean fadeScrollBars;
    boolean flickScroll;
    private ActorGestureListener flickScrollListener;
    float flingTime;
    float flingTimer;
    private boolean forceScrollX;
    private boolean forceScrollY;
    final Rectangle hKnobBounds;
    final Rectangle hScrollBounds;
    boolean hScrollOnBottom;
    final Vector2 lastPoint;
    float maxX;
    float maxY;
    private float overscrollDistance;
    private float overscrollSpeedMax;
    private float overscrollSpeedMin;
    private boolean overscrollX;
    private boolean overscrollY;
    boolean scrollBarTouch;
    boolean scrollX;
    boolean scrollY;
    private boolean scrollbarsOnTop;
    boolean smoothScrolling;
    private ScrollPaneStyle style;
    boolean touchScrollH;
    boolean touchScrollV;
    final Rectangle vKnobBounds;
    final Rectangle vScrollBounds;
    boolean vScrollOnRight;
    private boolean variableSizeKnobs;
    float velocityX;
    float velocityY;
    float visualAmountX;
    float visualAmountY;

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinHeight() {
        return 0.0f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinWidth() {
        return 0.0f;
    }

    public ScrollPane(Actor actor) {
        this(actor, new ScrollPaneStyle());
    }

    public ScrollPane(Actor actor, Skin skin) {
        this(actor, (ScrollPaneStyle) skin.get(ScrollPaneStyle.class));
    }

    public ScrollPane(Actor actor, Skin skin, String str) {
        this(actor, (ScrollPaneStyle) skin.get(str, ScrollPaneStyle.class));
    }

    public ScrollPane(Actor actor, ScrollPaneStyle scrollPaneStyle) {
        this.actorArea = new Rectangle();
        this.hScrollBounds = new Rectangle();
        this.hKnobBounds = new Rectangle();
        this.vScrollBounds = new Rectangle();
        this.vKnobBounds = new Rectangle();
        this.actorCullingArea = new Rectangle();
        this.vScrollOnRight = true;
        this.hScrollOnBottom = true;
        this.lastPoint = new Vector2();
        this.fadeScrollBars = true;
        this.smoothScrolling = true;
        this.scrollBarTouch = true;
        this.fadeAlphaSeconds = 1.0f;
        this.fadeDelaySeconds = 1.0f;
        this.cancelTouchFocus = true;
        this.flickScroll = true;
        this.flingTime = 1.0f;
        this.overscrollX = true;
        this.overscrollY = true;
        this.overscrollDistance = 50.0f;
        this.overscrollSpeedMin = 30.0f;
        this.overscrollSpeedMax = 200.0f;
        this.clamp = true;
        this.variableSizeKnobs = true;
        this.draggingPointer = -1;
        if (scrollPaneStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = scrollPaneStyle;
        setActor(actor);
        setSize(150.0f, 150.0f);
        addCaptureListener();
        ActorGestureListener flickScrollListener = getFlickScrollListener();
        this.flickScrollListener = flickScrollListener;
        addListener(flickScrollListener);
        addScrollListener();
    }

    protected void addCaptureListener() {
        addCaptureListener(new InputListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.1
            private float handlePosition;

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent inputEvent, float f, float f2, int i, int i2) {
                if (ScrollPane.this.draggingPointer != -1) {
                    return false;
                }
                if (i == 0 && i2 != 0) {
                    return false;
                }
                if (ScrollPane.this.getStage() != null) {
                    ScrollPane.this.getStage().setScrollFocus(ScrollPane.this);
                }
                if (!ScrollPane.this.flickScroll) {
                    ScrollPane.this.setScrollbarsVisible(true);
                }
                if (ScrollPane.this.fadeAlpha == 0.0f) {
                    return false;
                }
                if (ScrollPane.this.scrollBarTouch && ScrollPane.this.scrollX && ScrollPane.this.hScrollBounds.contains(f, f2)) {
                    inputEvent.stop();
                    ScrollPane.this.setScrollbarsVisible(true);
                    if (ScrollPane.this.hKnobBounds.contains(f, f2)) {
                        ScrollPane.this.lastPoint.set(f, f2);
                        this.handlePosition = ScrollPane.this.hKnobBounds.x;
                        ScrollPane.this.touchScrollH = true;
                        ScrollPane.this.draggingPointer = i;
                        return true;
                    }
                    ScrollPane scrollPane = ScrollPane.this;
                    scrollPane.setScrollX(scrollPane.amountX + (ScrollPane.this.actorArea.width * (f >= ScrollPane.this.hKnobBounds.x ? 1 : -1)));
                    return true;
                }
                if (!ScrollPane.this.scrollBarTouch || !ScrollPane.this.scrollY || !ScrollPane.this.vScrollBounds.contains(f, f2)) {
                    return false;
                }
                inputEvent.stop();
                ScrollPane.this.setScrollbarsVisible(true);
                if (ScrollPane.this.vKnobBounds.contains(f, f2)) {
                    ScrollPane.this.lastPoint.set(f, f2);
                    this.handlePosition = ScrollPane.this.vKnobBounds.y;
                    ScrollPane.this.touchScrollV = true;
                    ScrollPane.this.draggingPointer = i;
                    return true;
                }
                ScrollPane scrollPane2 = ScrollPane.this;
                scrollPane2.setScrollY(scrollPane2.amountY + (ScrollPane.this.actorArea.height * (f2 < ScrollPane.this.vKnobBounds.y ? 1 : -1)));
                return true;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchUp(InputEvent inputEvent, float f, float f2, int i, int i2) {
                if (i != ScrollPane.this.draggingPointer) {
                    return;
                }
                ScrollPane.this.cancel();
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchDragged(InputEvent inputEvent, float f, float f2, int i) {
                if (i != ScrollPane.this.draggingPointer) {
                    return;
                }
                if (ScrollPane.this.touchScrollH) {
                    float f3 = this.handlePosition + (f - ScrollPane.this.lastPoint.x);
                    this.handlePosition = f3;
                    float fMin = Math.min((ScrollPane.this.hScrollBounds.x + ScrollPane.this.hScrollBounds.width) - ScrollPane.this.hKnobBounds.width, Math.max(ScrollPane.this.hScrollBounds.x, f3));
                    float f4 = ScrollPane.this.hScrollBounds.width - ScrollPane.this.hKnobBounds.width;
                    if (f4 != 0.0f) {
                        ScrollPane scrollPane = ScrollPane.this;
                        scrollPane.setScrollPercentX((fMin - scrollPane.hScrollBounds.x) / f4);
                    }
                    ScrollPane.this.lastPoint.set(f, f2);
                    return;
                }
                if (ScrollPane.this.touchScrollV) {
                    float f5 = this.handlePosition + (f2 - ScrollPane.this.lastPoint.y);
                    this.handlePosition = f5;
                    float fMin2 = Math.min((ScrollPane.this.vScrollBounds.y + ScrollPane.this.vScrollBounds.height) - ScrollPane.this.vKnobBounds.height, Math.max(ScrollPane.this.vScrollBounds.y, f5));
                    float f6 = ScrollPane.this.vScrollBounds.height - ScrollPane.this.vKnobBounds.height;
                    if (f6 != 0.0f) {
                        ScrollPane scrollPane2 = ScrollPane.this;
                        scrollPane2.setScrollPercentY(1.0f - ((fMin2 - scrollPane2.vScrollBounds.y) / f6));
                    }
                    ScrollPane.this.lastPoint.set(f, f2);
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean mouseMoved(InputEvent inputEvent, float f, float f2) {
                if (ScrollPane.this.flickScroll) {
                    return false;
                }
                ScrollPane.this.setScrollbarsVisible(true);
                return false;
            }
        });
    }

    protected ActorGestureListener getFlickScrollListener() {
        return new ActorGestureListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.2
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
            public void pan(InputEvent inputEvent, float f, float f2, float f3, float f4) {
                ScrollPane.this.setScrollbarsVisible(true);
                if (!ScrollPane.this.scrollX) {
                    f3 = 0.0f;
                }
                if (!ScrollPane.this.scrollY) {
                    f4 = 0.0f;
                }
                ScrollPane.this.amountX -= f3;
                ScrollPane.this.amountY += f4;
                ScrollPane.this.clamp();
                if (ScrollPane.this.cancelTouchFocus) {
                    if (f3 == 0.0f && f4 == 0.0f) {
                        return;
                    }
                    ScrollPane.this.cancelTouchFocus();
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
            public void fling(InputEvent inputEvent, float f, float f2, int i) {
                if (Math.abs(f) <= 150.0f || !ScrollPane.this.scrollX) {
                    f = 0.0f;
                }
                float f3 = (Math.abs(f2) <= 150.0f || !ScrollPane.this.scrollY) ? 0.0f : -f2;
                if (f == 0.0f && f3 == 0.0f) {
                    return;
                }
                if (ScrollPane.this.cancelTouchFocus) {
                    ScrollPane.this.cancelTouchFocus();
                }
                ScrollPane scrollPane = ScrollPane.this;
                scrollPane.fling(scrollPane.flingTime, f, f3);
            }

            @Override // com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener, com.badlogic.gdx.scenes.scene2d.EventListener
            public boolean handle(Event event) {
                if (super.handle(event)) {
                    if (((InputEvent) event).getType() != InputEvent.Type.touchDown) {
                        return true;
                    }
                    ScrollPane.this.flingTimer = 0.0f;
                    return true;
                }
                if (!(event instanceof InputEvent) || !((InputEvent) event).isTouchFocusCancel()) {
                    return false;
                }
                ScrollPane.this.cancel();
                return false;
            }
        };
    }

    protected void addScrollListener() {
        addListener(new InputListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.3
            /* JADX WARN: Code duplicated, block: B:21:0x0037  */
            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean scrolled(InputEvent inputEvent, float f, float f2, float f3, float f4) {
                inputEvent.cancel();
                ScrollPane.this.setScrollbarsVisible(true);
                if (!ScrollPane.this.scrollY && !ScrollPane.this.scrollX) {
                    return false;
                }
                if (ScrollPane.this.scrollY) {
                    if (ScrollPane.this.scrollX || f4 != 0.0f) {
                        f4 = f3;
                        f3 = f4;
                    } else {
                        f4 = f3;
                    }
                } else if (ScrollPane.this.scrollX && f3 == 0.0f) {
                    f3 = f4;
                } else {
                    f4 = f3;
                    f3 = f4;
                }
                ScrollPane scrollPane = ScrollPane.this;
                scrollPane.setScrollY(scrollPane.amountY + (ScrollPane.this.getMouseWheelY() * f3));
                ScrollPane scrollPane2 = ScrollPane.this;
                scrollPane2.setScrollX(scrollPane2.amountX + (ScrollPane.this.getMouseWheelX() * f4));
                return true;
            }
        });
    }

    public void setScrollbarsVisible(boolean z) {
        if (z) {
            this.fadeAlpha = this.fadeAlphaSeconds;
            this.fadeDelay = this.fadeDelaySeconds;
        } else {
            this.fadeAlpha = 0.0f;
            this.fadeDelay = 0.0f;
        }
    }

    public void cancelTouchFocus() {
        Stage stage = getStage();
        if (stage != null) {
            stage.cancelTouchFocusExcept(this.flickScrollListener, this);
        }
    }

    public void cancel() {
        this.draggingPointer = -1;
        this.touchScrollH = false;
        this.touchScrollV = false;
        this.flickScrollListener.getGestureDetector().cancel();
    }

    void clamp() {
        float fClamp;
        float fClamp2;
        if (this.clamp) {
            if (this.overscrollX) {
                float f = this.amountX;
                float f2 = this.overscrollDistance;
                fClamp = MathUtils.clamp(f, -f2, this.maxX + f2);
            } else {
                fClamp = MathUtils.clamp(this.amountX, 0.0f, this.maxX);
            }
            scrollX(fClamp);
            if (this.overscrollY) {
                float f3 = this.amountY;
                float f4 = this.overscrollDistance;
                fClamp2 = MathUtils.clamp(f3, -f4, this.maxY + f4);
            } else {
                fClamp2 = MathUtils.clamp(this.amountY, 0.0f, this.maxY);
            }
            scrollY(fClamp2);
        }
    }

    public void setStyle(ScrollPaneStyle scrollPaneStyle) {
        if (scrollPaneStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = scrollPaneStyle;
        invalidateHierarchy();
    }

    public ScrollPaneStyle getStyle() {
        return this.style;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x01f6 A[PHI: r1
      0x01f6: PHI (r1v5 boolean) = (r1v4 boolean), (r1v6 boolean), (r1v6 boolean), (r1v6 boolean) binds: [B:71:0x0153, B:88:0x01a8, B:90:0x01ac, B:97:0x01d4] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void act(float f) {
        boolean z;
        Stage stage;
        super.act(f);
        boolean zIsPanning = this.flickScrollListener.getGestureDetector().isPanning();
        float f2 = this.fadeAlpha;
        boolean z2 = true;
        if (f2 <= 0.0f || !this.fadeScrollBars || zIsPanning || this.touchScrollH || this.touchScrollV) {
            z = false;
        } else {
            float f3 = this.fadeDelay - f;
            this.fadeDelay = f3;
            if (f3 <= 0.0f) {
                this.fadeAlpha = Math.max(0.0f, f2 - f);
            }
            z = true;
        }
        if (this.flingTimer > 0.0f) {
            setScrollbarsVisible(true);
            float f4 = this.flingTimer / this.flingTime;
            this.amountX -= (this.velocityX * f4) * f;
            this.amountY -= (this.velocityY * f4) * f;
            clamp();
            float f5 = this.amountX;
            float f6 = this.overscrollDistance;
            if (f5 == (-f6)) {
                this.velocityX = 0.0f;
            }
            if (f5 >= this.maxX + f6) {
                this.velocityX = 0.0f;
            }
            float f7 = this.amountY;
            if (f7 == (-f6)) {
                this.velocityY = 0.0f;
            }
            if (f7 >= this.maxY + f6) {
                this.velocityY = 0.0f;
            }
            float f8 = this.flingTimer - f;
            this.flingTimer = f8;
            if (f8 <= 0.0f) {
                this.velocityX = 0.0f;
                this.velocityY = 0.0f;
            }
            z = true;
        }
        if (this.smoothScrolling && this.flingTimer <= 0.0f && !zIsPanning && ((!this.touchScrollH || (this.scrollX && this.maxX / (this.hScrollBounds.width - this.hKnobBounds.width) > this.actorArea.width * 0.1f)) && (!this.touchScrollV || (this.scrollY && this.maxY / (this.vScrollBounds.height - this.vKnobBounds.height) > this.actorArea.height * 0.1f)))) {
            float f9 = this.visualAmountX;
            float f10 = this.amountX;
            if (f9 != f10) {
                if (f9 < f10) {
                    visualScrollX(Math.min(f10, f9 + Math.max(f * 200.0f, (f10 - f9) * 7.0f * f)));
                } else {
                    visualScrollX(Math.max(f10, f9 - Math.max(f * 200.0f, ((f9 - f10) * 7.0f) * f)));
                }
                z = true;
            }
            float f11 = this.visualAmountY;
            float f12 = this.amountY;
            if (f11 != f12) {
                if (f11 < f12) {
                    visualScrollY(Math.min(f12, f11 + Math.max(200.0f * f, (f12 - f11) * 7.0f * f)));
                } else {
                    visualScrollY(Math.max(f12, f11 - Math.max(200.0f * f, ((f11 - f12) * 7.0f) * f)));
                }
                z = true;
            }
        } else {
            float f13 = this.visualAmountX;
            float f14 = this.amountX;
            if (f13 != f14) {
                visualScrollX(f14);
            }
            float f15 = this.visualAmountY;
            float f16 = this.amountY;
            if (f15 != f16) {
                visualScrollY(f16);
            }
        }
        if (zIsPanning) {
            z2 = z;
        } else {
            if (this.overscrollX && this.scrollX) {
                float f17 = this.amountX;
                if (f17 < 0.0f) {
                    setScrollbarsVisible(true);
                    float f18 = this.amountX;
                    float f19 = this.overscrollSpeedMin;
                    float f20 = f18 + ((f19 + (((this.overscrollSpeedMax - f19) * (-f18)) / this.overscrollDistance)) * f);
                    this.amountX = f20;
                    if (f20 > 0.0f) {
                        scrollX(0.0f);
                    }
                } else if (f17 > this.maxX) {
                    setScrollbarsVisible(true);
                    float f21 = this.amountX;
                    float f22 = this.overscrollSpeedMin;
                    float f23 = this.overscrollSpeedMax - f22;
                    float f24 = this.maxX;
                    float f25 = f21 - ((f22 + ((f23 * (-(f24 - f21))) / this.overscrollDistance)) * f);
                    this.amountX = f25;
                    if (f25 < f24) {
                        scrollX(f24);
                    }
                }
                z = true;
            }
            if (this.overscrollY && this.scrollY) {
                float f26 = this.amountY;
                if (f26 < 0.0f) {
                    setScrollbarsVisible(true);
                    float f27 = this.amountY;
                    float f28 = this.overscrollSpeedMin;
                    float f29 = f27 + ((f28 + (((this.overscrollSpeedMax - f28) * (-f27)) / this.overscrollDistance)) * f);
                    this.amountY = f29;
                    if (f29 > 0.0f) {
                        scrollY(0.0f);
                    }
                } else if (f26 > this.maxY) {
                    setScrollbarsVisible(true);
                    float f30 = this.amountY;
                    float f31 = this.overscrollSpeedMin;
                    float f32 = this.overscrollSpeedMax - f31;
                    float f33 = this.maxY;
                    float f34 = f30 - ((f31 + ((f32 * (-(f33 - f30))) / this.overscrollDistance)) * f);
                    this.amountY = f34;
                    if (f34 < f33) {
                        scrollY(f33);
                    }
                } else {
                    z2 = z;
                }
            } else {
                z2 = z;
            }
        }
        if (z2 && (stage = getStage()) != null && stage.getActionsRequestRendering()) {
            Gdx.graphics.requestRendering();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        float bottomHeight;
        float minWidth;
        float rightWidth;
        float topHeight;
        float width;
        float height;
        Drawable drawable = this.style.background;
        Drawable drawable2 = this.style.hScrollKnob;
        Drawable drawable3 = this.style.vScrollKnob;
        if (drawable != null) {
            minWidth = drawable.getLeftWidth();
            rightWidth = drawable.getRightWidth();
            topHeight = drawable.getTopHeight();
            bottomHeight = drawable.getBottomHeight();
        } else {
            bottomHeight = 0.0f;
            minWidth = 0.0f;
            rightWidth = 0.0f;
            topHeight = 0.0f;
        }
        float width2 = getWidth();
        float height2 = getHeight() - topHeight;
        this.actorArea.set(minWidth, bottomHeight, (width2 - minWidth) - rightWidth, height2 - bottomHeight);
        if (this.actor == null) {
            return;
        }
        float minHeight = drawable2 != null ? drawable2.getMinHeight() : 0.0f;
        if (this.style.hScroll != null) {
            minHeight = Math.max(minHeight, this.style.hScroll.getMinHeight());
        }
        float minWidth2 = drawable3 != null ? drawable3.getMinWidth() : 0.0f;
        if (this.style.vScroll != null) {
            minWidth2 = Math.max(minWidth2, this.style.vScroll.getMinWidth());
        }
        Actor actor = this.actor;
        if (actor instanceof Layout) {
            Layout layout = (Layout) actor;
            width = layout.getPrefWidth();
            height = layout.getPrefHeight();
        } else {
            width = actor.getWidth();
            height = this.actor.getHeight();
        }
        boolean z = false;
        this.scrollX = this.forceScrollX || (width > this.actorArea.width && !this.disableX);
        if (this.forceScrollY || (height > this.actorArea.height && !this.disableY)) {
            z = true;
        }
        this.scrollY = z;
        if (!this.scrollbarsOnTop) {
            if (z) {
                this.actorArea.width -= minWidth2;
                if (!this.vScrollOnRight) {
                    this.actorArea.x += minWidth2;
                }
                if (!this.scrollX && width > this.actorArea.width && !this.disableX) {
                    this.scrollX = true;
                }
            }
            if (this.scrollX) {
                this.actorArea.height -= minHeight;
                if (this.hScrollOnBottom) {
                    this.actorArea.y += minHeight;
                }
                if (!this.scrollY && height > this.actorArea.height && !this.disableY) {
                    this.scrollY = true;
                    this.actorArea.width -= minWidth2;
                    if (!this.vScrollOnRight) {
                        this.actorArea.x += minWidth2;
                    }
                }
            }
        }
        float fMax = this.disableX ? this.actorArea.width : Math.max(this.actorArea.width, width);
        float fMax2 = this.disableY ? this.actorArea.height : Math.max(this.actorArea.height, height);
        this.maxX = fMax - this.actorArea.width;
        this.maxY = fMax2 - this.actorArea.height;
        scrollX(MathUtils.clamp(this.amountX, 0.0f, this.maxX));
        scrollY(MathUtils.clamp(this.amountY, 0.0f, this.maxY));
        if (this.scrollX) {
            if (drawable2 != null) {
                this.hScrollBounds.set(this.scrollbarsOnTop ? minWidth : this.actorArea.x, this.hScrollOnBottom ? bottomHeight : height2 - minHeight, this.actorArea.width, minHeight);
                if (this.scrollY && this.scrollbarsOnTop) {
                    this.hScrollBounds.width -= minWidth2;
                    if (!this.vScrollOnRight) {
                        this.hScrollBounds.x += minWidth2;
                    }
                }
                if (this.variableSizeKnobs) {
                    this.hKnobBounds.width = Math.max(drawable2.getMinWidth(), (int) ((this.hScrollBounds.width * this.actorArea.width) / fMax));
                } else {
                    this.hKnobBounds.width = drawable2.getMinWidth();
                }
                if (this.hKnobBounds.width > fMax) {
                    this.hKnobBounds.width = 0.0f;
                }
                this.hKnobBounds.height = drawable2.getMinHeight();
                this.hKnobBounds.x = this.hScrollBounds.x + ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getScrollPercentX()));
                this.hKnobBounds.y = this.hScrollBounds.y;
            } else {
                this.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                this.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        if (this.scrollY) {
            if (drawable3 != null) {
                float f = this.vScrollOnRight ? (width2 - rightWidth) - minWidth2 : minWidth;
                if (!this.scrollbarsOnTop) {
                    bottomHeight = this.actorArea.y;
                }
                this.vScrollBounds.set(f, bottomHeight, minWidth2, this.actorArea.height);
                if (this.scrollX && this.scrollbarsOnTop) {
                    this.vScrollBounds.height -= minHeight;
                    if (this.hScrollOnBottom) {
                        this.vScrollBounds.y += minHeight;
                    }
                }
                this.vKnobBounds.width = drawable3.getMinWidth();
                if (this.variableSizeKnobs) {
                    this.vKnobBounds.height = Math.max(drawable3.getMinHeight(), (int) ((this.vScrollBounds.height * this.actorArea.height) / fMax2));
                } else {
                    this.vKnobBounds.height = drawable3.getMinHeight();
                }
                if (this.vKnobBounds.height > fMax2) {
                    this.vKnobBounds.height = 0.0f;
                }
                Rectangle rectangle = this.vKnobBounds;
                if (this.vScrollOnRight) {
                    minWidth = (width2 - rightWidth) - drawable3.getMinWidth();
                }
                rectangle.x = minWidth;
                this.vKnobBounds.y = this.vScrollBounds.y + ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (1.0f - getScrollPercentY())));
            } else {
                this.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                this.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        updateActorPosition();
        Actor actor2 = this.actor;
        if (actor2 instanceof Layout) {
            actor2.setSize(fMax, fMax2);
            ((Layout) this.actor).validate();
        }
    }

    private void updateActorPosition() {
        float f = this.actorArea.x - (this.scrollX ? (int) this.visualAmountX : 0);
        float f2 = this.actorArea.y - ((int) (this.scrollY ? this.maxY - this.visualAmountY : this.maxY));
        this.actor.setPosition(f, f2);
        if (this.actor instanceof Cullable) {
            this.actorCullingArea.x = this.actorArea.x - f;
            this.actorCullingArea.y = this.actorArea.y - f2;
            this.actorCullingArea.width = this.actorArea.width;
            this.actorCullingArea.height = this.actorArea.height;
            ((Cullable) this.actor).setCullingArea(this.actorCullingArea);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        if (this.actor == null) {
            return;
        }
        validate();
        applyTransform(batch, computeTransform());
        if (this.scrollX) {
            this.hKnobBounds.x = this.hScrollBounds.x + ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getVisualScrollPercentX()));
        }
        if (this.scrollY) {
            this.vKnobBounds.y = this.vScrollBounds.y + ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (1.0f - getVisualScrollPercentY())));
        }
        updateActorPosition();
        Color color = getColor();
        float fApply = color.a * f;
        if (this.style.background != null) {
            batch.setColor(color.r, color.g, color.b, fApply);
            this.style.background.draw(batch, 0.0f, 0.0f, getWidth(), getHeight());
        }
        batch.flush();
        if (clipBegin(this.actorArea.x, this.actorArea.y, this.actorArea.width, this.actorArea.height)) {
            drawChildren(batch, f);
            batch.flush();
            clipEnd();
        }
        batch.setColor(color.r, color.g, color.b, fApply);
        if (this.fadeScrollBars) {
            fApply *= Interpolation.fade.apply(this.fadeAlpha / this.fadeAlphaSeconds);
        }
        drawScrollBars(batch, color.r, color.g, color.b, fApply);
        resetTransform(batch);
    }

    protected void drawScrollBars(Batch batch, float f, float f2, float f3, float f4) {
        if (f4 <= 0.0f) {
            return;
        }
        batch.setColor(f, f2, f3, f4);
        boolean z = this.scrollX && this.hKnobBounds.width > 0.0f;
        boolean z2 = this.scrollY && this.vKnobBounds.height > 0.0f;
        if (z) {
            if (z2 && this.style.corner != null) {
                this.style.corner.draw(batch, this.hScrollBounds.x + this.hScrollBounds.width, this.hScrollBounds.y, this.vScrollBounds.width, this.vScrollBounds.y);
            }
            if (this.style.hScroll != null) {
                this.style.hScroll.draw(batch, this.hScrollBounds.x, this.hScrollBounds.y, this.hScrollBounds.width, this.hScrollBounds.height);
            }
            if (this.style.hScrollKnob != null) {
                this.style.hScrollKnob.draw(batch, this.hKnobBounds.x, this.hKnobBounds.y, this.hKnobBounds.width, this.hKnobBounds.height);
            }
        }
        if (z2) {
            if (this.style.vScroll != null) {
                this.style.vScroll.draw(batch, this.vScrollBounds.x, this.vScrollBounds.y, this.vScrollBounds.width, this.vScrollBounds.height);
            }
            if (this.style.vScrollKnob != null) {
                this.style.vScrollKnob.draw(batch, this.vKnobBounds.x, this.vKnobBounds.y, this.vKnobBounds.width, this.vKnobBounds.height);
            }
        }
    }

    public void fling(float f, float f2, float f3) {
        this.flingTimer = f;
        this.velocityX = f2;
        this.velocityY = f3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        float width;
        Actor actor = this.actor;
        if (actor instanceof Layout) {
            width = ((Layout) actor).getPrefWidth();
        } else {
            width = actor != 0 ? actor.getWidth() : 0.0f;
        }
        Drawable drawable = this.style.background;
        if (drawable != null) {
            width = Math.max(width + drawable.getLeftWidth() + drawable.getRightWidth(), drawable.getMinWidth());
        }
        if (!this.scrollY) {
            return width;
        }
        float minWidth = this.style.vScrollKnob != null ? this.style.vScrollKnob.getMinWidth() : 0.0f;
        if (this.style.vScroll != null) {
            minWidth = Math.max(minWidth, this.style.vScroll.getMinWidth());
        }
        return width + minWidth;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        float height;
        Actor actor = this.actor;
        if (actor instanceof Layout) {
            height = ((Layout) actor).getPrefHeight();
        } else {
            height = actor != 0 ? actor.getHeight() : 0.0f;
        }
        Drawable drawable = this.style.background;
        if (drawable != null) {
            height = Math.max(height + drawable.getTopHeight() + drawable.getBottomHeight(), drawable.getMinHeight());
        }
        if (!this.scrollX) {
            return height;
        }
        float minHeight = this.style.hScrollKnob != null ? this.style.hScrollKnob.getMinHeight() : 0.0f;
        if (this.style.hScroll != null) {
            minHeight = Math.max(minHeight, this.style.hScroll.getMinHeight());
        }
        return height + minHeight;
    }

    public void setActor(Actor actor) {
        Actor actor2 = this.actor;
        if (actor2 == this) {
            throw new IllegalArgumentException("actor cannot be the ScrollPane.");
        }
        if (actor2 != null) {
            super.removeActor(actor2);
        }
        this.actor = actor;
        if (actor != null) {
            super.addActor(actor);
        }
    }

    public Actor getActor() {
        return this.actor;
    }

    @Deprecated
    public void setWidget(Actor actor) {
        setActor(actor);
    }

    @Deprecated
    public Actor getWidget() {
        return this.actor;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    @Deprecated
    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setActor.");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    @Deprecated
    public void addActorAt(int i, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setActor.");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    @Deprecated
    public void addActorBefore(Actor actor, Actor actor2) {
        throw new UnsupportedOperationException("Use ScrollPane#setActor.");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    @Deprecated
    public void addActorAfter(Actor actor, Actor actor2) {
        throw new UnsupportedOperationException("Use ScrollPane#setActor.");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public boolean removeActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        if (actor != this.actor) {
            return false;
        }
        setActor(null);
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public boolean removeActor(Actor actor, boolean z) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        if (actor != this.actor) {
            return false;
        }
        this.actor = null;
        return super.removeActor(actor, z);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public Actor removeActorAt(int i, boolean z) {
        Actor actorRemoveActorAt = super.removeActorAt(i, z);
        if (actorRemoveActorAt == this.actor) {
            this.actor = null;
        }
        return actorRemoveActorAt;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public Actor hit(float f, float f2, boolean z) {
        if (f < 0.0f || f >= getWidth() || f2 < 0.0f || f2 >= getHeight()) {
            return null;
        }
        if (z && getTouchable() == Touchable.enabled && isVisible()) {
            if (this.scrollX && this.touchScrollH && this.hScrollBounds.contains(f, f2)) {
                return this;
            }
            if (this.scrollY && this.touchScrollV && this.vScrollBounds.contains(f, f2)) {
                return this;
            }
        }
        return super.hit(f, f2, z);
    }

    protected void scrollX(float f) {
        this.amountX = f;
    }

    protected void scrollY(float f) {
        this.amountY = f;
    }

    protected void visualScrollX(float f) {
        this.visualAmountX = f;
    }

    protected void visualScrollY(float f) {
        this.visualAmountY = f;
    }

    protected float getMouseWheelX() {
        return Math.min(this.actorArea.width, Math.max(this.actorArea.width * 0.9f, this.maxX * 0.1f) / 4.0f);
    }

    protected float getMouseWheelY() {
        return Math.min(this.actorArea.height, Math.max(this.actorArea.height * 0.9f, this.maxY * 0.1f) / 4.0f);
    }

    public void setScrollX(float f) {
        scrollX(MathUtils.clamp(f, 0.0f, this.maxX));
    }

    public float getScrollX() {
        return this.amountX;
    }

    public void setScrollY(float f) {
        scrollY(MathUtils.clamp(f, 0.0f, this.maxY));
    }

    public float getScrollY() {
        return this.amountY;
    }

    public void updateVisualScroll() {
        this.visualAmountX = this.amountX;
        this.visualAmountY = this.amountY;
    }

    public float getVisualScrollX() {
        if (this.scrollX) {
            return this.visualAmountX;
        }
        return 0.0f;
    }

    public float getVisualScrollY() {
        if (this.scrollY) {
            return this.visualAmountY;
        }
        return 0.0f;
    }

    public float getVisualScrollPercentX() {
        float f = this.maxX;
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.clamp(this.visualAmountX / f, 0.0f, 1.0f);
    }

    public float getVisualScrollPercentY() {
        float f = this.maxY;
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.clamp(this.visualAmountY / f, 0.0f, 1.0f);
    }

    public float getScrollPercentX() {
        float f = this.maxX;
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.clamp(this.amountX / f, 0.0f, 1.0f);
    }

    public void setScrollPercentX(float f) {
        scrollX(this.maxX * MathUtils.clamp(f, 0.0f, 1.0f));
    }

    public float getScrollPercentY() {
        float f = this.maxY;
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.clamp(this.amountY / f, 0.0f, 1.0f);
    }

    public void setScrollPercentY(float f) {
        scrollY(this.maxY * MathUtils.clamp(f, 0.0f, 1.0f));
    }

    public void setFlickScroll(boolean z) {
        if (this.flickScroll == z) {
            return;
        }
        this.flickScroll = z;
        if (z) {
            addListener(this.flickScrollListener);
        } else {
            removeListener(this.flickScrollListener);
        }
        invalidate();
    }

    public void setFlickScrollTapSquareSize(float f) {
        this.flickScrollListener.getGestureDetector().setTapSquareSize(f);
    }

    public void scrollTo(float f, float f2, float f3, float f4) {
        scrollTo(f, f2, f3, f4, false, false);
    }

    public void scrollTo(float f, float f2, float f3, float f4, boolean z, boolean z2) {
        float fClamp;
        float fClamp2;
        validate();
        float f5 = this.amountX;
        if (z) {
            fClamp = f + ((f3 - this.actorArea.width) / 2.0f);
        } else {
            fClamp = MathUtils.clamp(f5, f, (f3 + f) - this.actorArea.width);
        }
        scrollX(MathUtils.clamp(fClamp, 0.0f, this.maxX));
        float f6 = this.amountY;
        float f7 = this.maxY - f2;
        if (z2) {
            fClamp2 = f7 + ((this.actorArea.height + f4) / 2.0f);
        } else {
            fClamp2 = MathUtils.clamp(f6, f4 + f7, f7 + this.actorArea.height);
        }
        scrollY(MathUtils.clamp(fClamp2, 0.0f, this.maxY));
    }

    public float getMaxX() {
        return this.maxX;
    }

    public float getMaxY() {
        return this.maxY;
    }

    public float getScrollBarHeight() {
        if (!this.scrollX) {
            return 0.0f;
        }
        float minHeight = this.style.hScrollKnob != null ? this.style.hScrollKnob.getMinHeight() : 0.0f;
        return this.style.hScroll != null ? Math.max(minHeight, this.style.hScroll.getMinHeight()) : minHeight;
    }

    public float getScrollBarWidth() {
        if (!this.scrollY) {
            return 0.0f;
        }
        float minWidth = this.style.vScrollKnob != null ? this.style.vScrollKnob.getMinWidth() : 0.0f;
        return this.style.vScroll != null ? Math.max(minWidth, this.style.vScroll.getMinWidth()) : minWidth;
    }

    public float getScrollWidth() {
        return this.actorArea.width;
    }

    public float getScrollHeight() {
        return this.actorArea.height;
    }

    public boolean isScrollX() {
        return this.scrollX;
    }

    public boolean isScrollY() {
        return this.scrollY;
    }

    public void setScrollingDisabled(boolean z, boolean z2) {
        if (z == this.disableX && z2 == this.disableY) {
            return;
        }
        this.disableX = z;
        this.disableY = z2;
        invalidate();
    }

    public boolean isScrollingDisabledX() {
        return this.disableX;
    }

    public boolean isScrollingDisabledY() {
        return this.disableY;
    }

    public boolean isLeftEdge() {
        return !this.scrollX || this.amountX <= 0.0f;
    }

    public boolean isRightEdge() {
        return !this.scrollX || this.amountX >= this.maxX;
    }

    public boolean isTopEdge() {
        return !this.scrollY || this.amountY <= 0.0f;
    }

    public boolean isBottomEdge() {
        return !this.scrollY || this.amountY >= this.maxY;
    }

    public boolean isDragging() {
        return this.draggingPointer != -1;
    }

    public boolean isPanning() {
        return this.flickScrollListener.getGestureDetector().isPanning();
    }

    public boolean isFlinging() {
        return this.flingTimer > 0.0f;
    }

    public void setVelocityX(float f) {
        this.velocityX = f;
    }

    public float getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityY(float f) {
        this.velocityY = f;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setOverscroll(boolean z, boolean z2) {
        this.overscrollX = z;
        this.overscrollY = z2;
    }

    public void setupOverscroll(float f, float f2, float f3) {
        this.overscrollDistance = f;
        this.overscrollSpeedMin = f2;
        this.overscrollSpeedMax = f3;
    }

    public float getOverscrollDistance() {
        return this.overscrollDistance;
    }

    public void setForceScroll(boolean z, boolean z2) {
        this.forceScrollX = z;
        this.forceScrollY = z2;
    }

    public boolean isForceScrollX() {
        return this.forceScrollX;
    }

    public boolean isForceScrollY() {
        return this.forceScrollY;
    }

    public void setFlingTime(float f) {
        this.flingTime = f;
    }

    public void setClamp(boolean z) {
        this.clamp = z;
    }

    public void setScrollBarPositions(boolean z, boolean z2) {
        this.hScrollOnBottom = z;
        this.vScrollOnRight = z2;
    }

    public void setFadeScrollBars(boolean z) {
        if (this.fadeScrollBars == z) {
            return;
        }
        this.fadeScrollBars = z;
        if (!z) {
            this.fadeAlpha = this.fadeAlphaSeconds;
        }
        invalidate();
    }

    public void setupFadeScrollBars(float f, float f2) {
        this.fadeAlphaSeconds = f;
        this.fadeDelaySeconds = f2;
    }

    public boolean getFadeScrollBars() {
        return this.fadeScrollBars;
    }

    public void setScrollBarTouch(boolean z) {
        this.scrollBarTouch = z;
    }

    public void setSmoothScrolling(boolean z) {
        this.smoothScrolling = z;
    }

    public void setScrollbarsOnTop(boolean z) {
        this.scrollbarsOnTop = z;
        invalidate();
    }

    public boolean getVariableSizeKnobs() {
        return this.variableSizeKnobs;
    }

    public void setVariableSizeKnobs(boolean z) {
        this.variableSizeKnobs = z;
    }

    public void setCancelTouchFocus(boolean z) {
        this.cancelTouchFocus = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void drawDebug(ShapeRenderer shapeRenderer) {
        drawDebugBounds(shapeRenderer);
        applyTransform(shapeRenderer, computeTransform());
        if (clipBegin(this.actorArea.x, this.actorArea.y, this.actorArea.width, this.actorArea.height)) {
            drawDebugChildren(shapeRenderer);
            shapeRenderer.flush();
            clipEnd();
        }
        resetTransform(shapeRenderer);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ScrollPane$ScrollPaneStyle.class
      classes8.dex
     */
    public static class ScrollPaneStyle {
        public Drawable background;
        public Drawable corner;
        public Drawable hScroll;
        public Drawable hScrollKnob;
        public Drawable vScroll;
        public Drawable vScrollKnob;

        public ScrollPaneStyle() {
        }

        public ScrollPaneStyle(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
            this.background = drawable;
            this.hScroll = drawable2;
            this.hScrollKnob = drawable3;
            this.vScroll = drawable4;
            this.vScrollKnob = drawable5;
        }

        public ScrollPaneStyle(ScrollPaneStyle scrollPaneStyle) {
            this.background = scrollPaneStyle.background;
            this.corner = scrollPaneStyle.corner;
            this.hScroll = scrollPaneStyle.hScroll;
            this.hScrollKnob = scrollPaneStyle.hScrollKnob;
            this.vScroll = scrollPaneStyle.vScroll;
            this.vScrollKnob = scrollPaneStyle.vScrollKnob;
        }
    }
}
