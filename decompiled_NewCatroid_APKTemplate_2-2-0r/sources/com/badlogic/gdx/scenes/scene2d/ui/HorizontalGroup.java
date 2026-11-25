package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.SnapshotArray;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/HorizontalGroup.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class HorizontalGroup extends WidgetGroup {
    private boolean expand;
    private float fill;
    private float lastPrefHeight;
    private float padBottom;
    private float padLeft;
    private float padRight;
    private float padTop;
    private float prefHeight;
    private float prefWidth;
    private boolean reverse;
    private int rowAlign;
    private FloatArray rowSizes;
    private float space;
    private boolean wrap;
    private boolean wrapReverse;
    private float wrapSpace;
    private boolean sizeInvalid = true;
    private int align = 8;
    private boolean round = true;

    public HorizontalGroup() {
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
        float f = 0.0f;
        this.prefHeight = 0.0f;
        if (this.wrap) {
            this.prefWidth = 0.0f;
            FloatArray floatArray = this.rowSizes;
            if (floatArray == null) {
                this.rowSizes = new FloatArray();
            } else {
                floatArray.clear();
            }
            FloatArray floatArray2 = this.rowSizes;
            float f2 = this.space;
            float f3 = this.wrapSpace;
            float f4 = this.padLeft + this.padRight;
            float width2 = getWidth() - f4;
            if (this.reverse) {
                i2 = i3 - 1;
                i3 = -1;
                i = -1;
            } else {
                i = 1;
            }
            float f5 = 0.0f;
            float fMax = 0.0f;
            float f6 = 0.0f;
            while (i2 != i3) {
                Actor actor = children.get(i2);
                if (actor instanceof Layout) {
                    Layout layout = (Layout) actor;
                    width = layout.getPrefWidth();
                    if (width > width2) {
                        width = Math.max(width2, layout.getMinWidth());
                    }
                    height = layout.getPrefHeight();
                } else {
                    width = actor.getWidth();
                    height = actor.getHeight();
                }
                float f7 = width + (f5 > f ? f2 : f);
                if (f5 + f7 <= width2 || f5 <= f) {
                    width = f7;
                } else {
                    floatArray2.add(f5);
                    floatArray2.add(fMax);
                    this.prefWidth = Math.max(this.prefWidth, f5 + f4);
                    if (f6 > 0.0f) {
                        f6 += f3;
                    }
                    f6 += fMax;
                    f5 = 0.0f;
                    fMax = 0.0f;
                }
                f5 += width;
                fMax = Math.max(fMax, height);
                i2 += i;
                f = 0.0f;
            }
            floatArray2.add(f5);
            floatArray2.add(fMax);
            this.prefWidth = Math.max(this.prefWidth, f5 + f4);
            if (f6 > 0.0f) {
                f6 += f3;
            }
            this.prefHeight = Math.max(this.prefHeight, f6 + fMax);
        } else {
            this.prefWidth = this.padLeft + this.padRight + (this.space * (i3 - 1));
            while (i2 < i3) {
                Actor actor2 = children.get(i2);
                if (actor2 instanceof Layout) {
                    Layout layout2 = (Layout) actor2;
                    this.prefWidth += layout2.getPrefWidth();
                    this.prefHeight = Math.max(this.prefHeight, layout2.getPrefHeight());
                } else {
                    this.prefWidth += actor2.getWidth();
                    this.prefHeight = Math.max(this.prefHeight, actor2.getHeight());
                }
                i2++;
            }
        }
        this.prefHeight += this.padTop + this.padBottom;
        if (this.round) {
            this.prefWidth = (float) Math.ceil(this.prefWidth);
            this.prefHeight = (float) Math.ceil(this.prefHeight);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004d  */
    /* JADX WARN: Code duplicated, block: B:25:0x0051  */
    /* JADX WARN: Code duplicated, block: B:26:0x005b  */
    /* JADX WARN: Code duplicated, block: B:29:0x0072  */
    /* JADX WARN: Code duplicated, block: B:30:0x0077  */
    /* JADX WARN: Code duplicated, block: B:32:0x0081  */
    /* JADX WARN: Code duplicated, block: B:34:0x008b  */
    /* JADX WARN: Code duplicated, block: B:35:0x0097  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:41:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:48:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:49:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:51:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:52:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:54:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:55:0x0101  */
    /* JADX WARN: Code duplicated, block: B:58:0x0111  */
    /* JADX WARN: Code duplicated, block: B:63:0x0114 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        float width;
        int i;
        SnapshotArray<Actor> children;
        int i2;
        int i3;
        int i4;
        int i5;
        Actor actor;
        float width2;
        float height;
        Layout layout;
        float fMax;
        char c;
        float f;
        float maxHeight;
        if (this.sizeInvalid) {
            computeSize();
        }
        if (this.wrap) {
            layoutWrapped();
            return;
        }
        boolean z = this.round;
        int i6 = this.align;
        float f2 = this.space;
        float height2 = this.padBottom;
        float f3 = this.fill;
        float height3 = ((this.expand ? getHeight() : this.prefHeight) - this.padTop) - height2;
        float f4 = this.padLeft;
        if ((i6 & 16) != 0) {
            width = getWidth() - this.prefWidth;
        } else {
            if ((i6 & 8) == 0) {
                width = (getWidth() - this.prefWidth) / 2.0f;
            }
            if ((i6 & 4) == 0) {
                if ((i6 & 2) != 0) {
                    height2 = (getHeight() - this.padTop) - height3;
                } else {
                    height2 += (((getHeight() - height2) - this.padTop) - height3) / 2.0f;
                }
            }
            i = this.rowAlign;
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
                    width2 = layout.getPrefWidth();
                    height = layout.getPrefHeight();
                } else {
                    width2 = actor.getWidth();
                    height = actor.getHeight();
                    layout = null;
                }
                if (f3 > 0.0f) {
                    height = height3 * f3;
                }
                fMax = height;
                if (layout != null) {
                    fMax = Math.max(fMax, layout.getMinHeight());
                    maxHeight = layout.getMaxHeight();
                    if (maxHeight > 0.0f && fMax > maxHeight) {
                        fMax = maxHeight;
                    }
                }
                if ((i & 2) != 0) {
                    f = (height3 - fMax) + height2;
                    c = 0;
                } else if ((i & 4) == 0) {
                    c = 0;
                    f = ((height3 - fMax) / 2.0f) + height2;
                } else {
                    c = 0;
                    f = height2;
                }
                if (z) {
                    actor.setBounds((float) Math.floor(f4), (float) Math.floor(f), (float) Math.ceil(width2), (float) Math.ceil(fMax));
                } else {
                    actor.setBounds(f4, f, width2, fMax);
                }
                f4 += width2 + f2;
                if (layout != null) {
                    layout.validate();
                }
                i4 += i5;
                f3 = f3;
                z = z;
                i = i;
                height2 = height2;
                height3 = height3;
            }
        }
        f4 += width;
        if ((i6 & 4) == 0) {
            if ((i6 & 2) != 0) {
                height2 = (getHeight() - this.padTop) - height3;
            } else {
                height2 += (((getHeight() - height2) - this.padTop) - height3) / 2.0f;
            }
        }
        i = this.rowAlign;
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
                width2 = layout.getPrefWidth();
                height = layout.getPrefHeight();
            } else {
                width2 = actor.getWidth();
                height = actor.getHeight();
                layout = null;
            }
            if (f3 > 0.0f) {
                height = height3 * f3;
            }
            fMax = height;
            if (layout != null) {
                fMax = Math.max(fMax, layout.getMinHeight());
                maxHeight = layout.getMaxHeight();
                if (maxHeight > 0.0f) {
                    fMax = maxHeight;
                }
            }
            if ((i & 2) != 0) {
                f = (height3 - fMax) + height2;
                c = 0;
            } else if ((i & 4) == 0) {
                c = 0;
                f = ((height3 - fMax) / 2.0f) + height2;
            } else {
                c = 0;
                f = height2;
            }
            if (z) {
                actor.setBounds((float) Math.floor(f4), (float) Math.floor(f), (float) Math.ceil(width2), (float) Math.ceil(fMax));
            } else {
                actor.setBounds(f4, f, width2, fMax);
            }
            f4 += width2 + f2;
            if (layout != null) {
                layout.validate();
            }
            i4 += i5;
            f3 = f3;
            z = z;
            i = i;
            height2 = height2;
            height3 = height3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004a  */
    /* JADX WARN: Code duplicated, block: B:15:0x0055  */
    /* JADX WARN: Code duplicated, block: B:18:0x005b  */
    /* JADX WARN: Code duplicated, block: B:20:0x0061  */
    /* JADX WARN: Code duplicated, block: B:22:0x0065  */
    /* JADX WARN: Code duplicated, block: B:25:0x007e  */
    /* JADX WARN: Code duplicated, block: B:26:0x0083  */
    /* JADX WARN: Code duplicated, block: B:29:0x0091  */
    /* JADX WARN: Code duplicated, block: B:31:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:33:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:34:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:36:0x00be  */
    /* JADX WARN: Code duplicated, block: B:39:0x00d5 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:42:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:44:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:46:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:48:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:49:0x010a  */
    /* JADX WARN: Code duplicated, block: B:52:0x0115  */
    /* JADX WARN: Code duplicated, block: B:56:0x0123  */
    /* JADX WARN: Code duplicated, block: B:59:0x012d  */
    /* JADX WARN: Code duplicated, block: B:61:0x013d  */
    /* JADX WARN: Code duplicated, block: B:66:0x0146  */
    /* JADX WARN: Code duplicated, block: B:67:0x014c  */
    /* JADX WARN: Code duplicated, block: B:69:0x0150  */
    /* JADX WARN: Code duplicated, block: B:70:0x0158  */
    /* JADX WARN: Code duplicated, block: B:72:0x015d  */
    /* JADX WARN: Code duplicated, block: B:73:0x0182  */
    /* JADX WARN: Code duplicated, block: B:76:0x0193  */
    /* JADX WARN: Code duplicated, block: B:81:0x0196 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    private void layoutWrapped() {
        float height;
        float f;
        float f2;
        float f3;
        int i;
        FloatArray floatArray;
        SnapshotArray<Actor> children;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f4;
        float f5;
        Actor actor;
        float width;
        float height2;
        Layout layout;
        float f6;
        float f7;
        int iMin;
        float f8;
        float f9;
        float f10;
        float fMax;
        float f11;
        float maxHeight;
        float prefHeight = getPrefHeight();
        if (prefHeight != this.lastPrefHeight) {
            this.lastPrefHeight = prefHeight;
            invalidateHierarchy();
        }
        int i7 = this.align;
        boolean z = this.round;
        float f12 = this.space;
        float f13 = this.fill;
        float f14 = this.wrapSpace;
        float f15 = (this.prefWidth - this.padLeft) - this.padRight;
        float f16 = prefHeight - this.padTop;
        float width2 = getWidth();
        float f17 = this.padLeft;
        if ((i7 & 2) != 0) {
            height = getHeight() - prefHeight;
        } else {
            if ((i7 & 4) == 0) {
                height = (getHeight() - prefHeight) / 2.0f;
            }
            if (this.wrapReverse) {
                f16 -= prefHeight + this.rowSizes.get(1);
                f = 1.0f;
            } else {
                f = -1.0f;
            }
            if ((i7 & 16) != 0) {
                f2 = width2 - this.prefWidth;
            } else {
                if ((i7 & 8) == 0) {
                    f2 = (width2 - this.prefWidth) / 2.0f;
                }
                f3 = width2 - this.padRight;
                i = this.rowAlign;
                floatArray = this.rowSizes;
                children = getChildren();
                i2 = children.size;
                if (this.reverse) {
                    i4 = i2 - 1;
                    i5 = -1;
                    i3 = -1;
                } else {
                    i3 = i2;
                    i4 = 0;
                    i5 = 1;
                }
                i6 = 0;
                f4 = 0.0f;
                f5 = 0.0f;
                while (i4 != i3) {
                    int i8 = i3;
                    actor = children.get(i4);
                    SnapshotArray<Actor> snapshotArray = children;
                    if (actor instanceof Layout) {
                        layout = (Layout) actor;
                        width = layout.getPrefWidth();
                        if (width > f3) {
                            width = Math.max(f3, layout.getMinWidth());
                        }
                        height2 = layout.getPrefHeight();
                    } else {
                        i5 = i5;
                        width = actor.getWidth();
                        height2 = actor.getHeight();
                        layout = null;
                    }
                    f6 = width;
                    f7 = height2;
                    if (f4 + f6 <= f3 || i6 == 0) {
                        iMin = Math.min(i6, floatArray.size - 2);
                        if ((i & 16) != 0) {
                            f9 = f15 - floatArray.get(iMin);
                        } else {
                            if ((i & 8) == 0) {
                                f9 = (f15 - floatArray.get(iMin)) / 2.0f;
                            } else {
                                f8 = f17;
                            }
                            f10 = floatArray.get(iMin + 1);
                            if (iMin > 0) {
                                f16 += f14 * f;
                            }
                            f16 += f10 * f;
                            i6 = iMin + 2;
                        }
                        f8 = f9 + f17;
                        f10 = floatArray.get(iMin + 1);
                        if (iMin > 0) {
                            f16 += f14 * f;
                        }
                        f16 += f10 * f;
                        i6 = iMin + 2;
                    } else {
                        f10 = f5;
                        f8 = f4;
                    }
                    if (f13 > 0.0f) {
                        f7 = f10 * f13;
                    }
                    int i9 = i6;
                    fMax = f7;
                    float f18 = f;
                    if (layout != null) {
                        fMax = Math.max(fMax, layout.getMinHeight());
                        maxHeight = layout.getMaxHeight();
                        if (maxHeight > 0.0f && fMax > maxHeight) {
                            fMax = maxHeight;
                        }
                    }
                    if ((i & 2) != 0) {
                        f11 = (f10 - fMax) + f16;
                    } else if ((i & 4) == 0) {
                        f11 = ((f10 - fMax) / 2.0f) + f16;
                    } else {
                        f11 = f16;
                    }
                    if (z) {
                        actor.setBounds((float) Math.floor(f8), (float) Math.floor(f11), (float) Math.ceil(f6), (float) Math.ceil(fMax));
                    } else {
                        actor.setBounds(f8, f11, f6, fMax);
                    }
                    float f19 = f8 + f6 + f12;
                    if (layout != null) {
                        layout.validate();
                    }
                    i4 += i5;
                    f13 = f13;
                    f3 = f3;
                    f15 = f15;
                    children = snapshotArray;
                    i3 = i8;
                    f = f18;
                    i5 = i5;
                    z = z;
                    i = i;
                    f14 = f14;
                    f5 = f10;
                    f4 = f19;
                    i6 = i9;
                }
            }
            f17 += f2;
            f3 = width2 - this.padRight;
            i = this.rowAlign;
            floatArray = this.rowSizes;
            children = getChildren();
            i2 = children.size;
            if (this.reverse) {
                i4 = i2 - 1;
                i5 = -1;
                i3 = -1;
            } else {
                i3 = i2;
                i4 = 0;
                i5 = 1;
            }
            i6 = 0;
            f4 = 0.0f;
            f5 = 0.0f;
            while (i4 != i3) {
                int i10 = i3;
                actor = children.get(i4);
                SnapshotArray<Actor> snapshotArray2 = children;
                if (actor instanceof Layout) {
                    layout = (Layout) actor;
                    width = layout.getPrefWidth();
                    if (width > f3) {
                        width = Math.max(f3, layout.getMinWidth());
                    }
                    height2 = layout.getPrefHeight();
                } else {
                    i5 = i5;
                    width = actor.getWidth();
                    height2 = actor.getHeight();
                    layout = null;
                }
                f6 = width;
                f7 = height2;
                if (f4 + f6 <= f3) {
                    iMin = Math.min(i6, floatArray.size - 2);
                    if ((i & 16) != 0) {
                        f9 = f15 - floatArray.get(iMin);
                    } else {
                        if ((i & 8) == 0) {
                            f9 = (f15 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f8 = f17;
                        }
                        f10 = floatArray.get(iMin + 1);
                        if (iMin > 0) {
                            f16 += f14 * f;
                        }
                        f16 += f10 * f;
                        i6 = iMin + 2;
                    }
                    f8 = f9 + f17;
                    f10 = floatArray.get(iMin + 1);
                    if (iMin > 0) {
                        f16 += f14 * f;
                    }
                    f16 += f10 * f;
                    i6 = iMin + 2;
                } else {
                    iMin = Math.min(i6, floatArray.size - 2);
                    if ((i & 16) != 0) {
                        f9 = f15 - floatArray.get(iMin);
                    } else {
                        if ((i & 8) == 0) {
                            f9 = (f15 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f8 = f17;
                        }
                        f10 = floatArray.get(iMin + 1);
                        if (iMin > 0) {
                            f16 += f14 * f;
                        }
                        f16 += f10 * f;
                        i6 = iMin + 2;
                    }
                    f8 = f9 + f17;
                    f10 = floatArray.get(iMin + 1);
                    if (iMin > 0) {
                        f16 += f14 * f;
                    }
                    f16 += f10 * f;
                    i6 = iMin + 2;
                }
                if (f13 > 0.0f) {
                    f7 = f10 * f13;
                }
                int i11 = i6;
                fMax = f7;
                float f110 = f;
                if (layout != null) {
                    fMax = Math.max(fMax, layout.getMinHeight());
                    maxHeight = layout.getMaxHeight();
                    if (maxHeight > 0.0f) {
                        fMax = maxHeight;
                    }
                }
                if ((i & 2) != 0) {
                    f11 = (f10 - fMax) + f16;
                } else if ((i & 4) == 0) {
                    f11 = ((f10 - fMax) / 2.0f) + f16;
                } else {
                    f11 = f16;
                }
                if (z) {
                    actor.setBounds((float) Math.floor(f8), (float) Math.floor(f11), (float) Math.ceil(f6), (float) Math.ceil(fMax));
                } else {
                    actor.setBounds(f8, f11, f6, fMax);
                }
                float f111 = f8 + f6 + f12;
                if (layout != null) {
                    layout.validate();
                }
                i4 += i5;
                f13 = f13;
                f3 = f3;
                f15 = f15;
                children = snapshotArray2;
                i3 = i10;
                f = f110;
                i5 = i5;
                z = z;
                i = i;
                f14 = f14;
                f5 = f10;
                f4 = f111;
                i6 = i11;
            }
        }
        f16 += height;
        if (this.wrapReverse) {
            f16 -= prefHeight + this.rowSizes.get(1);
            f = 1.0f;
        } else {
            f = -1.0f;
        }
        if ((i7 & 16) != 0) {
            f2 = width2 - this.prefWidth;
        } else {
            if ((i7 & 8) == 0) {
                f2 = (width2 - this.prefWidth) / 2.0f;
            }
            f3 = width2 - this.padRight;
            i = this.rowAlign;
            floatArray = this.rowSizes;
            children = getChildren();
            i2 = children.size;
            if (this.reverse) {
                i4 = i2 - 1;
                i5 = -1;
                i3 = -1;
            } else {
                i3 = i2;
                i4 = 0;
                i5 = 1;
            }
            i6 = 0;
            f4 = 0.0f;
            f5 = 0.0f;
            while (i4 != i3) {
                int i12 = i3;
                actor = children.get(i4);
                SnapshotArray<Actor> snapshotArray3 = children;
                if (actor instanceof Layout) {
                    layout = (Layout) actor;
                    width = layout.getPrefWidth();
                    if (width > f3) {
                        width = Math.max(f3, layout.getMinWidth());
                    }
                    height2 = layout.getPrefHeight();
                } else {
                    i5 = i5;
                    width = actor.getWidth();
                    height2 = actor.getHeight();
                    layout = null;
                }
                f6 = width;
                f7 = height2;
                if (f4 + f6 <= f3) {
                    iMin = Math.min(i6, floatArray.size - 2);
                    if ((i & 16) != 0) {
                        f9 = f15 - floatArray.get(iMin);
                    } else {
                        if ((i & 8) == 0) {
                            f9 = (f15 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f8 = f17;
                        }
                        f10 = floatArray.get(iMin + 1);
                        if (iMin > 0) {
                            f16 += f14 * f;
                        }
                        f16 += f10 * f;
                        i6 = iMin + 2;
                    }
                    f8 = f9 + f17;
                    f10 = floatArray.get(iMin + 1);
                    if (iMin > 0) {
                        f16 += f14 * f;
                    }
                    f16 += f10 * f;
                    i6 = iMin + 2;
                } else {
                    iMin = Math.min(i6, floatArray.size - 2);
                    if ((i & 16) != 0) {
                        f9 = f15 - floatArray.get(iMin);
                    } else {
                        if ((i & 8) == 0) {
                            f9 = (f15 - floatArray.get(iMin)) / 2.0f;
                        } else {
                            f8 = f17;
                        }
                        f10 = floatArray.get(iMin + 1);
                        if (iMin > 0) {
                            f16 += f14 * f;
                        }
                        f16 += f10 * f;
                        i6 = iMin + 2;
                    }
                    f8 = f9 + f17;
                    f10 = floatArray.get(iMin + 1);
                    if (iMin > 0) {
                        f16 += f14 * f;
                    }
                    f16 += f10 * f;
                    i6 = iMin + 2;
                }
                if (f13 > 0.0f) {
                    f7 = f10 * f13;
                }
                int i13 = i6;
                fMax = f7;
                float f112 = f;
                if (layout != null) {
                    fMax = Math.max(fMax, layout.getMinHeight());
                    maxHeight = layout.getMaxHeight();
                    if (maxHeight > 0.0f) {
                        fMax = maxHeight;
                    }
                }
                if ((i & 2) != 0) {
                    f11 = (f10 - fMax) + f16;
                } else if ((i & 4) == 0) {
                    f11 = ((f10 - fMax) / 2.0f) + f16;
                } else {
                    f11 = f16;
                }
                if (z) {
                    actor.setBounds((float) Math.floor(f8), (float) Math.floor(f11), (float) Math.ceil(f6), (float) Math.ceil(fMax));
                } else {
                    actor.setBounds(f8, f11, f6, fMax);
                }
                float f113 = f8 + f6 + f12;
                if (layout != null) {
                    layout.validate();
                }
                i4 += i5;
                f13 = f13;
                f3 = f3;
                f15 = f15;
                children = snapshotArray3;
                i3 = i12;
                f = f112;
                i5 = i5;
                z = z;
                i = i;
                f14 = f14;
                f5 = f10;
                f4 = f113;
                i6 = i13;
            }
        }
        f17 += f2;
        f3 = width2 - this.padRight;
        i = this.rowAlign;
        floatArray = this.rowSizes;
        children = getChildren();
        i2 = children.size;
        if (this.reverse) {
            i4 = i2 - 1;
            i5 = -1;
            i3 = -1;
        } else {
            i3 = i2;
            i4 = 0;
            i5 = 1;
        }
        i6 = 0;
        f4 = 0.0f;
        f5 = 0.0f;
        while (i4 != i3) {
            int i14 = i3;
            actor = children.get(i4);
            SnapshotArray<Actor> snapshotArray4 = children;
            if (actor instanceof Layout) {
                layout = (Layout) actor;
                width = layout.getPrefWidth();
                if (width > f3) {
                    width = Math.max(f3, layout.getMinWidth());
                }
                height2 = layout.getPrefHeight();
            } else {
                i5 = i5;
                width = actor.getWidth();
                height2 = actor.getHeight();
                layout = null;
            }
            f6 = width;
            f7 = height2;
            if (f4 + f6 <= f3) {
                iMin = Math.min(i6, floatArray.size - 2);
                if ((i & 16) != 0) {
                    f9 = f15 - floatArray.get(iMin);
                } else {
                    if ((i & 8) == 0) {
                        f9 = (f15 - floatArray.get(iMin)) / 2.0f;
                    } else {
                        f8 = f17;
                    }
                    f10 = floatArray.get(iMin + 1);
                    if (iMin > 0) {
                        f16 += f14 * f;
                    }
                    f16 += f10 * f;
                    i6 = iMin + 2;
                }
                f8 = f9 + f17;
                f10 = floatArray.get(iMin + 1);
                if (iMin > 0) {
                    f16 += f14 * f;
                }
                f16 += f10 * f;
                i6 = iMin + 2;
            } else {
                iMin = Math.min(i6, floatArray.size - 2);
                if ((i & 16) != 0) {
                    f9 = f15 - floatArray.get(iMin);
                } else {
                    if ((i & 8) == 0) {
                        f9 = (f15 - floatArray.get(iMin)) / 2.0f;
                    } else {
                        f8 = f17;
                    }
                    f10 = floatArray.get(iMin + 1);
                    if (iMin > 0) {
                        f16 += f14 * f;
                    }
                    f16 += f10 * f;
                    i6 = iMin + 2;
                }
                f8 = f9 + f17;
                f10 = floatArray.get(iMin + 1);
                if (iMin > 0) {
                    f16 += f14 * f;
                }
                f16 += f10 * f;
                i6 = iMin + 2;
            }
            if (f13 > 0.0f) {
                f7 = f10 * f13;
            }
            int i15 = i6;
            fMax = f7;
            float f114 = f;
            if (layout != null) {
                fMax = Math.max(fMax, layout.getMinHeight());
                maxHeight = layout.getMaxHeight();
                if (maxHeight > 0.0f) {
                    fMax = maxHeight;
                }
            }
            if ((i & 2) != 0) {
                f11 = (f10 - fMax) + f16;
            } else if ((i & 4) == 0) {
                f11 = ((f10 - fMax) / 2.0f) + f16;
            } else {
                f11 = f16;
            }
            if (z) {
                actor.setBounds((float) Math.floor(f8), (float) Math.floor(f11), (float) Math.ceil(f6), (float) Math.ceil(fMax));
            } else {
                actor.setBounds(f8, f11, f6, fMax);
            }
            float f115 = f8 + f6 + f12;
            if (layout != null) {
                layout.validate();
            }
            i4 += i5;
            f13 = f13;
            f3 = f3;
            f15 = f15;
            children = snapshotArray4;
            i3 = i14;
            f = f114;
            i5 = i5;
            z = z;
            i = i;
            f14 = f14;
            f5 = f10;
            f4 = f115;
            i6 = i15;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        if (this.wrap) {
            return 0.0f;
        }
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefWidth;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefHeight;
    }

    public int getRows() {
        if (this.wrap) {
            return this.rowSizes.size >> 1;
        }
        return 1;
    }

    public void setRound(boolean z) {
        this.round = z;
    }

    public HorizontalGroup reverse() {
        this.reverse = true;
        return this;
    }

    public HorizontalGroup reverse(boolean z) {
        this.reverse = z;
        return this;
    }

    public boolean getReverse() {
        return this.reverse;
    }

    public HorizontalGroup wrapReverse() {
        this.wrapReverse = true;
        return this;
    }

    public HorizontalGroup wrapReverse(boolean z) {
        this.wrapReverse = z;
        return this;
    }

    public boolean getWrapReverse() {
        return this.wrapReverse;
    }

    public HorizontalGroup space(float f) {
        this.space = f;
        return this;
    }

    public float getSpace() {
        return this.space;
    }

    public HorizontalGroup wrapSpace(float f) {
        this.wrapSpace = f;
        return this;
    }

    public float getWrapSpace() {
        return this.wrapSpace;
    }

    public HorizontalGroup pad(float f) {
        this.padTop = f;
        this.padLeft = f;
        this.padBottom = f;
        this.padRight = f;
        return this;
    }

    public HorizontalGroup pad(float f, float f2, float f3, float f4) {
        this.padTop = f;
        this.padLeft = f2;
        this.padBottom = f3;
        this.padRight = f4;
        return this;
    }

    public HorizontalGroup padTop(float f) {
        this.padTop = f;
        return this;
    }

    public HorizontalGroup padLeft(float f) {
        this.padLeft = f;
        return this;
    }

    public HorizontalGroup padBottom(float f) {
        this.padBottom = f;
        return this;
    }

    public HorizontalGroup padRight(float f) {
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

    public HorizontalGroup align(int i) {
        this.align = i;
        return this;
    }

    public HorizontalGroup center() {
        this.align = 1;
        return this;
    }

    public HorizontalGroup top() {
        this.align = (this.align | 2) & (-5);
        return this;
    }

    public HorizontalGroup left() {
        this.align = (this.align | 8) & (-17);
        return this;
    }

    public HorizontalGroup bottom() {
        this.align = (this.align | 4) & (-3);
        return this;
    }

    public HorizontalGroup right() {
        this.align = (this.align | 16) & (-9);
        return this;
    }

    public int getAlign() {
        return this.align;
    }

    public HorizontalGroup fill() {
        this.fill = 1.0f;
        return this;
    }

    public HorizontalGroup fill(float f) {
        this.fill = f;
        return this;
    }

    public float getFill() {
        return this.fill;
    }

    public HorizontalGroup expand() {
        this.expand = true;
        return this;
    }

    public HorizontalGroup expand(boolean z) {
        this.expand = z;
        return this;
    }

    public boolean getExpand() {
        return this.expand;
    }

    public HorizontalGroup grow() {
        this.expand = true;
        this.fill = 1.0f;
        return this;
    }

    public HorizontalGroup wrap() {
        this.wrap = true;
        return this;
    }

    public HorizontalGroup wrap(boolean z) {
        this.wrap = z;
        return this;
    }

    public boolean getWrap() {
        return this.wrap;
    }

    public HorizontalGroup rowAlign(int i) {
        this.rowAlign = i;
        return this;
    }

    public HorizontalGroup rowCenter() {
        this.rowAlign = 1;
        return this;
    }

    public HorizontalGroup rowTop() {
        this.rowAlign = (this.rowAlign | 2) & (-5);
        return this;
    }

    public HorizontalGroup rowLeft() {
        this.rowAlign = (this.rowAlign | 8) & (-17);
        return this;
    }

    public HorizontalGroup rowBottom() {
        this.rowAlign = (this.rowAlign | 4) & (-3);
        return this;
    }

    public HorizontalGroup rowRight() {
        this.rowAlign = (this.rowAlign | 16) & (-9);
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
