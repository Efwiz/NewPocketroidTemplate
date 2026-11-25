package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.SnapshotArray;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/VerticalGroup.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class VerticalGroup extends WidgetGroup {
    private int columnAlign;
    private FloatArray columnSizes;
    private boolean expand;
    private float fill;
    private float lastPrefWidth;
    private float padBottom;
    private float padLeft;
    private float padRight;
    private float padTop;
    private float prefHeight;
    private float prefWidth;
    private boolean reverse;
    private float space;
    private boolean wrap;
    private float wrapSpace;
    private boolean sizeInvalid = true;
    private int align = 2;
    private boolean round = true;

    public VerticalGroup() {
        setTouchable(Touchable.childrenOnly);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void computeSize() {
        int i;
        float width;
        float height;
        int i2 = 0;
        this.sizeInvalid = false;
        SnapshotArray<Actor> children = getChildren();
        int i3 = children.size;
        this.prefWidth = 0.0f;
        if (this.wrap) {
            this.prefHeight = 0.0f;
            FloatArray floatArray = this.columnSizes;
            if (floatArray == null) {
                this.columnSizes = new FloatArray();
            } else {
                floatArray.clear();
            }
            FloatArray floatArray2 = this.columnSizes;
            float f = this.space;
            float f2 = this.wrapSpace;
            float f3 = this.padTop + this.padBottom;
            float height2 = getHeight() - f3;
            if (this.reverse) {
                i2 = i3 - 1;
                i3 = -1;
                i = -1;
            } else {
                i = 1;
            }
            float f4 = 0.0f;
            float fMax = 0.0f;
            float f5 = 0.0f;
            while (i2 != i3) {
                Actor actor = children.get(i2);
                if (actor instanceof Layout) {
                    Layout layout = (Layout) actor;
                    width = layout.getPrefWidth();
                    height = layout.getPrefHeight();
                    if (height > height2) {
                        height = Math.max(height2, layout.getMinHeight());
                    }
                } else {
                    width = actor.getWidth();
                    height = actor.getHeight();
                }
                float f6 = height + (f4 > 0.0f ? f : 0.0f);
                if (f4 + f6 <= height2 || f4 <= 0.0f) {
                    height = f6;
                } else {
                    floatArray2.add(f4);
                    floatArray2.add(fMax);
                    this.prefHeight = Math.max(this.prefHeight, f4 + f3);
                    if (f5 > 0.0f) {
                        f5 += f2;
                    }
                    f5 += fMax;
                    f4 = 0.0f;
                    fMax = 0.0f;
                }
                f4 += height;
                fMax = Math.max(fMax, width);
                i2 += i;
            }
            floatArray2.add(f4);
            floatArray2.add(fMax);
            this.prefHeight = Math.max(this.prefHeight, f4 + f3);
            if (f5 > 0.0f) {
                f5 += f2;
            }
            this.prefWidth = Math.max(this.prefWidth, f5 + fMax);
        } else {
            this.prefHeight = this.padTop + this.padBottom + (this.space * (i3 - 1));
            while (i2 < i3) {
                Actor actor2 = children.get(i2);
                if (actor2 instanceof Layout) {
                    Layout layout2 = (Layout) actor2;
                    this.prefWidth = Math.max(this.prefWidth, layout2.getPrefWidth());
                    this.prefHeight += layout2.getPrefHeight();
                } else {
                    this.prefWidth = Math.max(this.prefWidth, actor2.getWidth());
                    this.prefHeight += actor2.getHeight();
                }
                i2++;
            }
        }
        float f7 = this.prefWidth + this.padLeft + this.padRight;
        this.prefWidth = f7;
        if (this.round) {
            this.prefWidth = (float) Math.ceil(f7);
            this.prefHeight = (float) Math.ceil(this.prefHeight);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0051  */
    /* JADX WARN: Code duplicated, block: B:25:0x0055  */
    /* JADX WARN: Code duplicated, block: B:26:0x005f  */
    /* JADX WARN: Code duplicated, block: B:29:0x0076  */
    /* JADX WARN: Code duplicated, block: B:30:0x007b  */
    /* JADX WARN: Code duplicated, block: B:32:0x0085  */
    /* JADX WARN: Code duplicated, block: B:34:0x008f  */
    /* JADX WARN: Code duplicated, block: B:35:0x009b  */
    /* JADX WARN: Code duplicated, block: B:38:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:40:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:47:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:50:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:51:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:55:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:56:0x010e  */
    /* JADX WARN: Code duplicated, block: B:58:0x011d  */
    /* JADX WARN: Code duplicated, block: B:63:0x0120 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        float height;
        int i;
        SnapshotArray<Actor> children;
        int i2;
        int i3;
        int i4;
        int i5;
        Actor actor;
        float width;
        float height2;
        Layout layout;
        float f;
        char c;
        float f2;
        float maxWidth;
        if (this.sizeInvalid) {
            computeSize();
        }
        if (this.wrap) {
            layoutWrapped();
            return;
        }
        boolean z = this.round;
        int i6 = this.align;
        float f3 = this.space;
        float width2 = this.padLeft;
        float f4 = this.fill;
        float width3 = ((this.expand ? getWidth() : this.prefWidth) - width2) - this.padRight;
        float f5 = (this.prefHeight - this.padTop) + f3;
        if ((i6 & 2) != 0) {
            height = getHeight() - this.prefHeight;
        } else {
            if ((i6 & 4) == 0) {
                height = (getHeight() - this.prefHeight) / 2.0f;
            }
            if ((i6 & 8) == 0) {
                if ((i6 & 16) != 0) {
                    width2 = (getWidth() - this.padRight) - width3;
                } else {
                    width2 += (((getWidth() - width2) - this.padRight) - width3) / 2.0f;
                }
            }
            i = this.columnAlign;
            children = getChildren();
            i2 = children.size;
            if (this.reverse) {
                i4 = i2 - 1;
                i3 = -1;
                i5 = -1;
            } else {
                i3 = i2;
                i4 = 0;
                i5 = 1;
            }
            while (i4 != i3) {
                actor = children.get(i4);
                if (actor instanceof Layout) {
                    layout = (Layout) actor;
                    width = layout.getPrefWidth();
                    height2 = layout.getPrefHeight();
                } else {
                    width = actor.getWidth();
                    height2 = actor.getHeight();
                    layout = null;
                }
                f = height2;
                if (f4 > 0.0f) {
                    width = width3 * f4;
                }
                if (layout != null) {
                    width = Math.max(width, layout.getMinWidth());
                    maxWidth = layout.getMaxWidth();
                    if (maxWidth > 0.0f && width > maxWidth) {
                        width = maxWidth;
                    }
                }
                if ((i & 16) != 0) {
                    f2 = (width3 - width) + width2;
                    c = 0;
                } else if ((i & 8) == 0) {
                    c = 0;
                    f2 = ((width3 - width) / 2.0f) + width2;
                } else {
                    c = 0;
                    f2 = width2;
                }
                f5 -= f + f3;
                if (z) {
                    actor.setBounds((float) Math.floor(f2), (float) Math.floor(f5), (float) Math.ceil(width), (float) Math.ceil(f));
                } else {
                    actor.setBounds(f2, f5, width, f);
                }
                if (layout != null) {
                    layout.validate();
                }
                i4 += i5;
                z = z;
                i = i;
                f3 = f3;
                width2 = width2;
                f4 = f4;
            }
        }
        f5 += height;
        if ((i6 & 8) == 0) {
            if ((i6 & 16) != 0) {
                width2 = (getWidth() - this.padRight) - width3;
            } else {
                width2 += (((getWidth() - width2) - this.padRight) - width3) / 2.0f;
            }
        }
        i = this.columnAlign;
        children = getChildren();
        i2 = children.size;
        if (this.reverse) {
            i4 = i2 - 1;
            i3 = -1;
            i5 = -1;
        } else {
            i3 = i2;
            i4 = 0;
            i5 = 1;
        }
        while (i4 != i3) {
            actor = children.get(i4);
            if (actor instanceof Layout) {
                layout = (Layout) actor;
                width = layout.getPrefWidth();
                height2 = layout.getPrefHeight();
            } else {
                width = actor.getWidth();
                height2 = actor.getHeight();
                layout = null;
            }
            f = height2;
            if (f4 > 0.0f) {
                width = width3 * f4;
            }
            if (layout != null) {
                width = Math.max(width, layout.getMinWidth());
                maxWidth = layout.getMaxWidth();
                if (maxWidth > 0.0f) {
                    width = maxWidth;
                }
            }
            if ((i & 16) != 0) {
                f2 = (width3 - width) + width2;
                c = 0;
            } else if ((i & 8) == 0) {
                c = 0;
                f2 = ((width3 - width) / 2.0f) + width2;
            } else {
                c = 0;
                f2 = width2;
            }
            f5 -= f + f3;
            if (z) {
                actor.setBounds((float) Math.floor(f2), (float) Math.floor(f5), (float) Math.ceil(width), (float) Math.ceil(f));
            } else {
                actor.setBounds(f2, f5, width, f);
            }
            if (layout != null) {
                layout.validate();
            }
            i4 += i5;
            z = z;
            i = i;
            f3 = f3;
            width2 = width2;
            f4 = f4;
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004b  */
    /* JADX WARN: Code duplicated, block: B:16:0x0051  */
    /* JADX WARN: Code duplicated, block: B:18:0x0055  */
    /* JADX WARN: Code duplicated, block: B:21:0x006d  */
    /* JADX WARN: Code duplicated, block: B:22:0x0073  */
    /* JADX WARN: Code duplicated, block: B:25:0x0080  */
    /* JADX WARN: Code duplicated, block: B:27:0x008e  */
    /* JADX WARN: Code duplicated, block: B:29:0x009d  */
    /* JADX WARN: Code duplicated, block: B:30:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:31:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:34:0x00c4 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:35:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:37:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:39:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:41:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:42:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:44:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:48:0x0100  */
    /* JADX WARN: Code duplicated, block: B:51:0x0106  */
    /* JADX WARN: Code duplicated, block: B:53:0x0116  */
    /* JADX WARN: Code duplicated, block: B:58:0x011f  */
    /* JADX WARN: Code duplicated, block: B:59:0x0125  */
    /* JADX WARN: Code duplicated, block: B:61:0x0129  */
    /* JADX WARN: Code duplicated, block: B:62:0x0131  */
    /* JADX WARN: Code duplicated, block: B:65:0x013c  */
    /* JADX WARN: Code duplicated, block: B:66:0x0160  */
    /* JADX WARN: Code duplicated, block: B:68:0x016d  */
    /* JADX WARN: Code duplicated, block: B:73:0x0170 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    private void layoutWrapped() {
        float width;
        float f;
        float f2;
        int i;
        FloatArray floatArray;
        SnapshotArray<Actor> children;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f3;
        float f4;
        Actor actor;
        float width2;
        float height;
        Layout layout;
        float f5;
        int iMin;
        float f6;
        float fMax;
        char c;
        float f7;
        float f8;
        float maxWidth;
        VerticalGroup verticalGroup = this;
        float prefWidth = getPrefWidth();
        if (prefWidth != verticalGroup.lastPrefWidth) {
            verticalGroup.lastPrefWidth = prefWidth;
            invalidateHierarchy();
        }
        int i7 = verticalGroup.align;
        boolean z = verticalGroup.round;
        float f9 = verticalGroup.space;
        float f10 = verticalGroup.padLeft;
        float f11 = verticalGroup.fill;
        float f12 = verticalGroup.wrapSpace;
        float f13 = (verticalGroup.prefHeight - verticalGroup.padTop) - verticalGroup.padBottom;
        float height2 = getHeight();
        float f14 = (verticalGroup.prefHeight - verticalGroup.padTop) + f9;
        if ((i7 & 16) != 0) {
            width = getWidth() - prefWidth;
        } else {
            if ((i7 & 8) == 0) {
                width = (getWidth() - prefWidth) / 2.0f;
            }
            if ((i7 & 2) != 0) {
                f = height2 - verticalGroup.prefHeight;
            } else {
                if ((i7 & 4) == 0) {
                    f = (height2 - verticalGroup.prefHeight) / 2.0f;
                }
                f2 = height2 - verticalGroup.padTop;
                i = verticalGroup.columnAlign;
                floatArray = verticalGroup.columnSizes;
                children = getChildren();
                i2 = children.size;
                i3 = 0;
                if (verticalGroup.reverse) {
                    i6 = i2 - 1;
                    i5 = -1;
                    i4 = -1;
                } else {
                    i4 = 1;
                    i5 = i2;
                    i6 = 0;
                }
                f3 = 0.0f;
                f4 = 0.0f;
                while (i6 != i5) {
                    actor = children.get(i6);
                    SnapshotArray<Actor> snapshotArray = children;
                    if (actor instanceof Layout) {
                        layout = (Layout) actor;
                        width2 = layout.getPrefWidth();
                        height = layout.getPrefHeight();
                        if (height > f2) {
                            height = Math.max(f2, layout.getMinHeight());
                        }
                    } else {
                        width2 = actor.getWidth();
                        height = actor.getHeight();
                        layout = null;
                    }
                    f5 = height;
                    float f15 = f2;
                    if ((f3 - f5) - f9 >= verticalGroup.padBottom || i3 == 0) {
                        iMin = Math.min(i3, floatArray.size - 2);
                        if ((i & 4) != 0) {
                            f6 = f13 - floatArray.get(iMin);
                        } else {
                            if ((i & 2) == 0) {
                                f6 = (f13 - floatArray.get(iMin)) / 2.0f;
                            } else {
                                f3 = f14;
                            }
                            if (iMin > 0) {
                                f10 = f10 + f12 + f4;
                            }
                            f4 = floatArray.get(iMin + 1);
                            i3 = iMin + 2;
                        }
                        f3 = f14 - f6;
                        if (iMin > 0) {
                            f10 = f10 + f12 + f4;
                        }
                        f4 = floatArray.get(iMin + 1);
                        i3 = iMin + 2;
                    }
                    if (f11 > 0.0f) {
                        width2 = f4 * f11;
                    }
                    fMax = width2;
                    if (layout != null) {
                        fMax = Math.max(fMax, layout.getMinWidth());
                        maxWidth = layout.getMaxWidth();
                        if (maxWidth > 0.0f && fMax > maxWidth) {
                            fMax = maxWidth;
                        }
                    }
                    if ((i & 16) != 0) {
                        f7 = (f4 - fMax) + f10;
                        c = 0;
                    } else if ((i & 8) == 0) {
                        c = 0;
                        f7 = ((f4 - fMax) / 2.0f) + f10;
                    } else {
                        c = 0;
                        f7 = f10;
                    }
                    int i8 = i;
                    f8 = f3 - (f5 + f9);
                    if (z) {
                        actor.setBounds((float) Math.floor(f7), (float) Math.floor(f8), (float) Math.ceil(fMax), (float) Math.ceil(f5));
                    } else {
                        actor.setBounds(f7, f8, fMax, f5);
                    }
                    if (layout != null) {
                        layout.validate();
                    }
                    i6 += i4;
                    verticalGroup = this;
                    z = z;
                    children = snapshotArray;
                    floatArray = floatArray;
                    i5 = i5;
                    f2 = f15;
                    f9 = f9;
                    f10 = f10;
                    f3 = f8;
                    i = i8;
                }
            }
            f14 += f;
            f2 = height2 - verticalGroup.padTop;
            i = verticalGroup.columnAlign;
            floatArray = verticalGroup.columnSizes;
            children = getChildren();
            i2 = children.size;
            i3 = 0;
            if (verticalGroup.reverse) {
                i6 = i2 - 1;
                i5 = -1;
                i4 = -1;
            } else {
                i4 = 1;
                i5 = i2;
                i6 = 0;
            }
            f3 = 0.0f;
            f4 = 0.0f;
            while (i6 != i5) {
                actor = children.get(i6);
                SnapshotArray<Actor> snapshotArray2 = children;
                if (actor instanceof Layout) {
                    layout = (Layout) actor;
                    width2 = layout.getPrefWidth();
                    height = layout.getPrefHeight();
                    if (height > f2) {
                        height = Math.max(f2, layout.getMinHeight());
                    }
                } else {
                    width2 = actor.getWidth();
                    height = actor.getHeight();
                    layout = null;
                }
                f5 = height;
                float f16 = f2;
                if ((f3 - f5) - f9 >= verticalGroup.padBottom) {
                    iMin = Math.min(i3, floatArray.size - 2);
                    if ((i & 4) != 0) {
                        f6 = f13 - floatArray.get(iMin);
                    } else {
                        if ((i & 2) == 0) {
                            f6 = (f13 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f3 = f14;
                        }
                        if (iMin > 0) {
                            f10 = f10 + f12 + f4;
                        }
                        f4 = floatArray.get(iMin + 1);
                        i3 = iMin + 2;
                    }
                    f3 = f14 - f6;
                    if (iMin > 0) {
                        f10 = f10 + f12 + f4;
                    }
                    f4 = floatArray.get(iMin + 1);
                    i3 = iMin + 2;
                } else {
                    iMin = Math.min(i3, floatArray.size - 2);
                    if ((i & 4) != 0) {
                        f6 = f13 - floatArray.get(iMin);
                    } else {
                        if ((i & 2) == 0) {
                            f6 = (f13 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f3 = f14;
                        }
                        if (iMin > 0) {
                            f10 = f10 + f12 + f4;
                        }
                        f4 = floatArray.get(iMin + 1);
                        i3 = iMin + 2;
                    }
                    f3 = f14 - f6;
                    if (iMin > 0) {
                        f10 = f10 + f12 + f4;
                    }
                    f4 = floatArray.get(iMin + 1);
                    i3 = iMin + 2;
                }
                if (f11 > 0.0f) {
                    width2 = f4 * f11;
                }
                fMax = width2;
                if (layout != null) {
                    fMax = Math.max(fMax, layout.getMinWidth());
                    maxWidth = layout.getMaxWidth();
                    if (maxWidth > 0.0f) {
                        fMax = maxWidth;
                    }
                }
                if ((i & 16) != 0) {
                    f7 = (f4 - fMax) + f10;
                    c = 0;
                } else if ((i & 8) == 0) {
                    c = 0;
                    f7 = ((f4 - fMax) / 2.0f) + f10;
                } else {
                    c = 0;
                    f7 = f10;
                }
                int i9 = i;
                f8 = f3 - (f5 + f9);
                if (z) {
                    actor.setBounds((float) Math.floor(f7), (float) Math.floor(f8), (float) Math.ceil(fMax), (float) Math.ceil(f5));
                } else {
                    actor.setBounds(f7, f8, fMax, f5);
                }
                if (layout != null) {
                    layout.validate();
                }
                i6 += i4;
                verticalGroup = this;
                z = z;
                children = snapshotArray2;
                floatArray = floatArray;
                i5 = i5;
                f2 = f16;
                f9 = f9;
                f10 = f10;
                f3 = f8;
                i = i9;
            }
        }
        f10 += width;
        if ((i7 & 2) != 0) {
            f = height2 - verticalGroup.prefHeight;
        } else {
            if ((i7 & 4) == 0) {
                f = (height2 - verticalGroup.prefHeight) / 2.0f;
            }
            f2 = height2 - verticalGroup.padTop;
            i = verticalGroup.columnAlign;
            floatArray = verticalGroup.columnSizes;
            children = getChildren();
            i2 = children.size;
            i3 = 0;
            if (verticalGroup.reverse) {
                i6 = i2 - 1;
                i5 = -1;
                i4 = -1;
            } else {
                i4 = 1;
                i5 = i2;
                i6 = 0;
            }
            f3 = 0.0f;
            f4 = 0.0f;
            while (i6 != i5) {
                actor = children.get(i6);
                SnapshotArray<Actor> snapshotArray3 = children;
                if (actor instanceof Layout) {
                    layout = (Layout) actor;
                    width2 = layout.getPrefWidth();
                    height = layout.getPrefHeight();
                    if (height > f2) {
                        height = Math.max(f2, layout.getMinHeight());
                    }
                } else {
                    width2 = actor.getWidth();
                    height = actor.getHeight();
                    layout = null;
                }
                f5 = height;
                float f17 = f2;
                if ((f3 - f5) - f9 >= verticalGroup.padBottom) {
                    iMin = Math.min(i3, floatArray.size - 2);
                    if ((i & 4) != 0) {
                        f6 = f13 - floatArray.get(iMin);
                    } else {
                        if ((i & 2) == 0) {
                            f6 = (f13 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f3 = f14;
                        }
                        if (iMin > 0) {
                            f10 = f10 + f12 + f4;
                        }
                        f4 = floatArray.get(iMin + 1);
                        i3 = iMin + 2;
                    }
                    f3 = f14 - f6;
                    if (iMin > 0) {
                        f10 = f10 + f12 + f4;
                    }
                    f4 = floatArray.get(iMin + 1);
                    i3 = iMin + 2;
                } else {
                    iMin = Math.min(i3, floatArray.size - 2);
                    if ((i & 4) != 0) {
                        f6 = f13 - floatArray.get(iMin);
                    } else {
                        if ((i & 2) == 0) {
                            f6 = (f13 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f3 = f14;
                        }
                        if (iMin > 0) {
                            f10 = f10 + f12 + f4;
                        }
                        f4 = floatArray.get(iMin + 1);
                        i3 = iMin + 2;
                    }
                    f3 = f14 - f6;
                    if (iMin > 0) {
                        f10 = f10 + f12 + f4;
                    }
                    f4 = floatArray.get(iMin + 1);
                    i3 = iMin + 2;
                }
                if (f11 > 0.0f) {
                    width2 = f4 * f11;
                }
                fMax = width2;
                if (layout != null) {
                    fMax = Math.max(fMax, layout.getMinWidth());
                    maxWidth = layout.getMaxWidth();
                    if (maxWidth > 0.0f) {
                        fMax = maxWidth;
                    }
                }
                if ((i & 16) != 0) {
                    f7 = (f4 - fMax) + f10;
                    c = 0;
                } else if ((i & 8) == 0) {
                    c = 0;
                    f7 = ((f4 - fMax) / 2.0f) + f10;
                } else {
                    c = 0;
                    f7 = f10;
                }
                int i10 = i;
                f8 = f3 - (f5 + f9);
                if (z) {
                    actor.setBounds((float) Math.floor(f7), (float) Math.floor(f8), (float) Math.ceil(fMax), (float) Math.ceil(f5));
                } else {
                    actor.setBounds(f7, f8, fMax, f5);
                }
                if (layout != null) {
                    layout.validate();
                }
                i6 += i4;
                verticalGroup = this;
                z = z;
                children = snapshotArray3;
                floatArray = floatArray;
                i5 = i5;
                f2 = f17;
                f9 = f9;
                f10 = f10;
                f3 = f8;
                i = i10;
            }
        }
        f14 += f;
        f2 = height2 - verticalGroup.padTop;
        i = verticalGroup.columnAlign;
        floatArray = verticalGroup.columnSizes;
        children = getChildren();
        i2 = children.size;
        i3 = 0;
        if (verticalGroup.reverse) {
            i6 = i2 - 1;
            i5 = -1;
            i4 = -1;
        } else {
            i4 = 1;
            i5 = i2;
            i6 = 0;
        }
        f3 = 0.0f;
        f4 = 0.0f;
        while (i6 != i5) {
            actor = children.get(i6);
            SnapshotArray<Actor> snapshotArray4 = children;
            if (actor instanceof Layout) {
                layout = (Layout) actor;
                width2 = layout.getPrefWidth();
                height = layout.getPrefHeight();
                if (height > f2) {
                    height = Math.max(f2, layout.getMinHeight());
                }
            } else {
                width2 = actor.getWidth();
                height = actor.getHeight();
                layout = null;
            }
            f5 = height;
            float f18 = f2;
            if ((f3 - f5) - f9 >= verticalGroup.padBottom) {
                iMin = Math.min(i3, floatArray.size - 2);
                if ((i & 4) != 0) {
                    f6 = f13 - floatArray.get(iMin);
                } else {
                    if ((i & 2) == 0) {
                        f6 = (f13 - floatArray.get(iMin)) / 2.0f;
                    } else {
                        f3 = f14;
                    }
                    if (iMin > 0) {
                        f10 = f10 + f12 + f4;
                    }
                    f4 = floatArray.get(iMin + 1);
                    i3 = iMin + 2;
                }
                f3 = f14 - f6;
                if (iMin > 0) {
                    f10 = f10 + f12 + f4;
                }
                f4 = floatArray.get(iMin + 1);
                i3 = iMin + 2;
            } else {
                iMin = Math.min(i3, floatArray.size - 2);
                if ((i & 4) != 0) {
                    f6 = f13 - floatArray.get(iMin);
                } else {
                    if ((i & 2) == 0) {
                        f6 = (f13 - floatArray.get(iMin)) / 2.0f;
                    } else {
                        f3 = f14;
                    }
                    if (iMin > 0) {
                        f10 = f10 + f12 + f4;
                    }
                    f4 = floatArray.get(iMin + 1);
                    i3 = iMin + 2;
                }
                f3 = f14 - f6;
                if (iMin > 0) {
                    f10 = f10 + f12 + f4;
                }
                f4 = floatArray.get(iMin + 1);
                i3 = iMin + 2;
            }
            if (f11 > 0.0f) {
                width2 = f4 * f11;
            }
            fMax = width2;
            if (layout != null) {
                fMax = Math.max(fMax, layout.getMinWidth());
                maxWidth = layout.getMaxWidth();
                if (maxWidth > 0.0f) {
                    fMax = maxWidth;
                }
            }
            if ((i & 16) != 0) {
                f7 = (f4 - fMax) + f10;
                c = 0;
            } else if ((i & 8) == 0) {
                c = 0;
                f7 = ((f4 - fMax) / 2.0f) + f10;
            } else {
                c = 0;
                f7 = f10;
            }
            int i11 = i;
            f8 = f3 - (f5 + f9);
            if (z) {
                actor.setBounds((float) Math.floor(f7), (float) Math.floor(f8), (float) Math.ceil(fMax), (float) Math.ceil(f5));
            } else {
                actor.setBounds(f7, f8, fMax, f5);
            }
            if (layout != null) {
                layout.validate();
            }
            i6 += i4;
            verticalGroup = this;
            z = z;
            children = snapshotArray4;
            floatArray = floatArray;
            i5 = i5;
            f2 = f18;
            f9 = f9;
            f10 = f10;
            f3 = f8;
            i = i11;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefWidth;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        if (this.wrap) {
            return 0.0f;
        }
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefHeight;
    }

    public int getColumns() {
        if (this.wrap) {
            return this.columnSizes.size >> 1;
        }
        return 1;
    }

    public void setRound(boolean z) {
        this.round = z;
    }

    public VerticalGroup reverse() {
        this.reverse = true;
        return this;
    }

    public VerticalGroup reverse(boolean z) {
        this.reverse = z;
        return this;
    }

    public boolean getReverse() {
        return this.reverse;
    }

    public VerticalGroup space(float f) {
        this.space = f;
        return this;
    }

    public float getSpace() {
        return this.space;
    }

    public VerticalGroup wrapSpace(float f) {
        this.wrapSpace = f;
        return this;
    }

    public float getWrapSpace() {
        return this.wrapSpace;
    }

    public VerticalGroup pad(float f) {
        this.padTop = f;
        this.padLeft = f;
        this.padBottom = f;
        this.padRight = f;
        return this;
    }

    public VerticalGroup pad(float f, float f2, float f3, float f4) {
        this.padTop = f;
        this.padLeft = f2;
        this.padBottom = f3;
        this.padRight = f4;
        return this;
    }

    public VerticalGroup padTop(float f) {
        this.padTop = f;
        return this;
    }

    public VerticalGroup padLeft(float f) {
        this.padLeft = f;
        return this;
    }

    public VerticalGroup padBottom(float f) {
        this.padBottom = f;
        return this;
    }

    public VerticalGroup padRight(float f) {
        this.padRight = f;
        return this;
    }

    public float getPadTop() {
        return this.padTop;
    }

    public float getPadLeft() {
        return this.padLeft;
    }

    public float getPadBottom() {
        return this.padBottom;
    }

    public float getPadRight() {
        return this.padRight;
    }

    public VerticalGroup align(int i) {
        this.align = i;
        return this;
    }

    public VerticalGroup center() {
        this.align = 1;
        return this;
    }

    public VerticalGroup top() {
        this.align = (this.align | 2) & (-5);
        return this;
    }

    public VerticalGroup left() {
        this.align = (this.align | 8) & (-17);
        return this;
    }

    public VerticalGroup bottom() {
        this.align = (this.align | 4) & (-3);
        return this;
    }

    public VerticalGroup right() {
        this.align = (this.align | 16) & (-9);
        return this;
    }

    public int getAlign() {
        return this.align;
    }

    public VerticalGroup fill() {
        this.fill = 1.0f;
        return this;
    }

    public VerticalGroup fill(float f) {
        this.fill = f;
        return this;
    }

    public float getFill() {
        return this.fill;
    }

    public VerticalGroup expand() {
        this.expand = true;
        return this;
    }

    public VerticalGroup expand(boolean z) {
        this.expand = z;
        return this;
    }

    public boolean getExpand() {
        return this.expand;
    }

    public VerticalGroup grow() {
        this.expand = true;
        this.fill = 1.0f;
        return this;
    }

    public VerticalGroup wrap() {
        this.wrap = true;
        return this;
    }

    public VerticalGroup wrap(boolean z) {
        this.wrap = z;
        return this;
    }

    public boolean getWrap() {
        return this.wrap;
    }

    public VerticalGroup columnAlign(int i) {
        this.columnAlign = i;
        return this;
    }

    public VerticalGroup columnCenter() {
        this.columnAlign = 1;
        return this;
    }

    public VerticalGroup columnTop() {
        this.columnAlign = (this.columnAlign | 2) & (-5);
        return this;
    }

    public VerticalGroup columnLeft() {
        this.columnAlign = (this.columnAlign | 8) & (-17);
        return this;
    }

    public VerticalGroup columnBottom() {
        this.columnAlign = (this.columnAlign | 4) & (-3);
        return this;
    }

    public VerticalGroup columnRight() {
        this.columnAlign = (this.columnAlign | 16) & (-9);
        return this;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    protected void drawDebugBounds(ShapeRenderer shapeRenderer) {
        super.drawDebugBounds(shapeRenderer);
        if (getDebug()) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            if (getStage() != null) {
                shapeRenderer.setColor(getStage().getDebugColor());
            }
            shapeRenderer.rect(getX() + this.padLeft, getY() + this.padBottom, getOriginX(), getOriginY(), (getWidth() - this.padLeft) - this.padRight, (getHeight() - this.padBottom) - this.padTop, getScaleX(), getScaleY(), getRotation());
        }
    }
}
