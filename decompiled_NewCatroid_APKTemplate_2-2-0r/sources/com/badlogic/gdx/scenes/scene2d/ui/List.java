package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/List.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class List<T> extends Widget implements Cullable {
    private int alignment;
    private Rectangle cullingArea;
    float itemHeight;
    final Array<T> items;
    private InputListener keyListener;
    int overIndex;
    private float prefHeight;
    private float prefWidth;
    int pressedIndex;
    ArraySelection<T> selection;
    ListStyle style;
    boolean typeToSelect;

    public List(Skin skin) {
        this((ListStyle) skin.get(ListStyle.class));
    }

    public List(Skin skin, String str) {
        this((ListStyle) skin.get(str, ListStyle.class));
    }

    public List(ListStyle listStyle) {
        Array<T> array = new Array<>();
        this.items = array;
        ArraySelection<T> arraySelection = new ArraySelection<>(array);
        this.selection = arraySelection;
        this.alignment = 8;
        this.pressedIndex = -1;
        this.overIndex = -1;
        arraySelection.setActor(this);
        this.selection.setRequired(true);
        setStyle(listStyle);
        setSize(getPrefWidth(), getPrefHeight());
        InputListener inputListener = new InputListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.List.1
            String prefix;
            long typeTimeout;

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean keyDown(InputEvent inputEvent, int i) {
                if (List.this.items.isEmpty()) {
                    return false;
                }
                if (i == 3) {
                    List.this.setSelectedIndex(0);
                    return true;
                }
                if (i != 29) {
                    if (i == 111) {
                        if (List.this.getStage() != null) {
                            List.this.getStage().setKeyboardFocus(null);
                        }
                        return true;
                    }
                    if (i == 123) {
                        List list = List.this;
                        list.setSelectedIndex(list.items.size - 1);
                        return true;
                    }
                    if (i == 19) {
                        int iIndexOf = List.this.items.indexOf((T) List.this.getSelected(), false) - 1;
                        if (iIndexOf < 0) {
                            iIndexOf = List.this.items.size - 1;
                        }
                        List.this.setSelectedIndex(iIndexOf);
                        return true;
                    }
                    if (i == 20) {
                        int iIndexOf2 = List.this.items.indexOf((T) List.this.getSelected(), false) + 1;
                        List.this.setSelectedIndex(iIndexOf2 < List.this.items.size ? iIndexOf2 : 0);
                        return true;
                    }
                } else if (UIUtils.ctrl() && List.this.selection.getMultiple()) {
                    List.this.selection.clear();
                    List.this.selection.addAll(List.this.items);
                    return true;
                }
                return false;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean keyTyped(InputEvent inputEvent, char c) {
                if (!List.this.typeToSelect) {
                    return false;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis > this.typeTimeout) {
                    this.prefix = "";
                }
                this.typeTimeout = jCurrentTimeMillis + 300;
                this.prefix += Character.toLowerCase(c);
                int i = List.this.items.size;
                for (int i2 = 0; i2 < i; i2++) {
                    List list = List.this;
                    if (list.toString(list.items.get(i2)).toLowerCase().startsWith(this.prefix)) {
                        List.this.setSelectedIndex(i2);
                        break;
                    }
                }
                return false;
            }
        };
        this.keyListener = inputListener;
        addListener(inputListener);
        addListener(new InputListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.List.2
            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent inputEvent, float f, float f2, int i, int i2) {
                int itemIndexAt;
                if (i != 0 || i2 != 0 || List.this.selection.isDisabled()) {
                    return true;
                }
                if (List.this.getStage() != null) {
                    List.this.getStage().setKeyboardFocus(List.this);
                }
                if (List.this.items.size == 0 || (itemIndexAt = List.this.getItemIndexAt(f2)) == -1) {
                    return true;
                }
                List.this.selection.choose(List.this.items.get(itemIndexAt));
                List.this.pressedIndex = itemIndexAt;
                return true;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchUp(InputEvent inputEvent, float f, float f2, int i, int i2) {
                if (i == 0 && i2 == 0) {
                    List.this.pressedIndex = -1;
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchDragged(InputEvent inputEvent, float f, float f2, int i) {
                List list = List.this;
                list.overIndex = list.getItemIndexAt(f2);
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean mouseMoved(InputEvent inputEvent, float f, float f2) {
                List list = List.this;
                list.overIndex = list.getItemIndexAt(f2);
                return false;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void exit(InputEvent inputEvent, float f, float f2, int i, Actor actor) {
                if (i == 0) {
                    List.this.pressedIndex = -1;
                }
                if (i == -1) {
                    List.this.overIndex = -1;
                }
            }
        });
    }

    public void setStyle(ListStyle listStyle) {
        if (listStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = listStyle;
        invalidateHierarchy();
    }

    public ListStyle getStyle() {
        return this.style;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        BitmapFont bitmapFont = this.style.font;
        Drawable drawable = this.style.selection;
        float capHeight = bitmapFont.getCapHeight() - (bitmapFont.getDescent() * 2.0f);
        this.itemHeight = capHeight;
        this.itemHeight = capHeight + drawable.getTopHeight() + drawable.getBottomHeight();
        this.prefWidth = 0.0f;
        Pool pool = Pools.get(GlyphLayout.class);
        GlyphLayout glyphLayout = (GlyphLayout) pool.obtain();
        for (int i = 0; i < this.items.size; i++) {
            glyphLayout.setText(bitmapFont, toString(this.items.get(i)));
            this.prefWidth = Math.max(glyphLayout.width, this.prefWidth);
        }
        pool.free(glyphLayout);
        this.prefWidth += drawable.getLeftWidth() + drawable.getRightWidth();
        this.prefHeight = this.items.size * this.itemHeight;
        Drawable drawable2 = this.style.background;
        if (drawable2 != null) {
            this.prefWidth = Math.max(this.prefWidth + drawable2.getLeftWidth() + drawable2.getRightWidth(), drawable2.getMinWidth());
            this.prefHeight = Math.max(this.prefHeight + drawable2.getTopHeight() + drawable2.getBottomHeight(), drawable2.getMinHeight());
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x010f  */
    /* JADX WARN: Code duplicated, block: B:42:0x011c A[SYNTHETIC] */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        Drawable drawable;
        Drawable drawable2;
        int i;
        validate();
        drawBackground(batch, f);
        BitmapFont bitmapFont = this.style.font;
        Drawable drawable3 = this.style.selection;
        Color color = this.style.fontColorSelected;
        Color color2 = this.style.fontColorUnselected;
        Color color3 = getColor();
        batch.setColor(color3.r, color3.g, color3.b, color3.a * f);
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        Drawable drawable4 = this.style.background;
        if (drawable4 != null) {
            float leftWidth = drawable4.getLeftWidth();
            x += leftWidth;
            height -= drawable4.getTopHeight();
            width -= leftWidth + drawable4.getRightWidth();
        }
        float f2 = x;
        float f3 = width;
        float leftWidth2 = drawable3.getLeftWidth();
        float rightWidth = (f3 - leftWidth2) - drawable3.getRightWidth();
        float topHeight = drawable3.getTopHeight() - bitmapFont.getDescent();
        bitmapFont.setColor(color2.r, color2.g, color2.b, color2.a * f);
        int i2 = 0;
        float f4 = height;
        while (i2 < this.items.size) {
            Rectangle rectangle = this.cullingArea;
            if (rectangle == null || (f4 - this.itemHeight <= rectangle.y + this.cullingArea.height && f4 >= this.cullingArea.y)) {
                T t = this.items.get(i2);
                boolean zContains = this.selection.contains(t);
                if (this.pressedIndex == i2 && this.style.down != null) {
                    drawable = this.style.down;
                } else {
                    if (zContains) {
                        bitmapFont.setColor(color.r, color.g, color.b, color.a * f);
                        drawable2 = drawable3;
                    } else {
                        drawable = (this.overIndex != i2 || this.style.over == null) ? null : this.style.over;
                    }
                    float f5 = y + f4;
                    float f6 = this.itemHeight;
                    drawSelection(batch, drawable2, f2, f5 - f6, f3, f6);
                    float f7 = f5 - topHeight;
                    i = i2;
                    drawItem(batch, bitmapFont, i2, t, f2 + leftWidth2, f7, rightWidth);
                    if (zContains) {
                        bitmapFont.setColor(color2.r, color2.g, color2.b, color2.a * f);
                    }
                }
                drawable2 = drawable;
                float f8 = y + f4;
                float f9 = this.itemHeight;
                drawSelection(batch, drawable2, f2, f8 - f9, f3, f9);
                float f10 = f8 - topHeight;
                i = i2;
                drawItem(batch, bitmapFont, i2, t, f2 + leftWidth2, f10, rightWidth);
                if (zContains) {
                    bitmapFont.setColor(color2.r, color2.g, color2.b, color2.a * f);
                }
            } else if (f4 < this.cullingArea.y) {
                return;
            } else {
                i = i2;
            }
            f4 -= this.itemHeight;
            i2 = i + 1;
        }
    }

    protected void drawSelection(Batch batch, Drawable drawable, float f, float f2, float f3, float f4) {
        if (drawable != null) {
            drawable.draw(batch, f, f2, f3, f4);
        }
    }

    protected void drawBackground(Batch batch, float f) {
        if (this.style.background != null) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * f);
            this.style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
    }

    protected GlyphLayout drawItem(Batch batch, BitmapFont bitmapFont, int i, T t, float f, float f2, float f3) {
        String string = toString(t);
        return bitmapFont.draw(batch, string, f, f2, 0, string.length(), f3, this.alignment, false, "...");
    }

    public ArraySelection<T> getSelection() {
        return this.selection;
    }

    public void setSelection(ArraySelection<T> arraySelection) {
        this.selection = arraySelection;
    }

    public T getSelected() {
        return this.selection.first();
    }

    public void setSelected(T t) {
        if (this.items.contains(t, false)) {
            this.selection.set(t);
        } else if (this.selection.getRequired() && this.items.size > 0) {
            this.selection.set(this.items.first());
        } else {
            this.selection.clear();
        }
    }

    public int getSelectedIndex() {
        OrderedSet<T> orderedSetItems = this.selection.items();
        if (orderedSetItems.size == 0) {
            return -1;
        }
        return this.items.indexOf(orderedSetItems.first(), false);
    }

    public void setSelectedIndex(int i) {
        if (i < -1 || i >= this.items.size) {
            throw new IllegalArgumentException("index must be >= -1 and < " + this.items.size + ": " + i);
        }
        if (i == -1) {
            this.selection.clear();
        } else {
            this.selection.set(this.items.get(i));
        }
    }

    public T getOverItem() {
        int i = this.overIndex;
        if (i == -1) {
            return null;
        }
        return this.items.get(i);
    }

    public T getPressedItem() {
        int i = this.pressedIndex;
        if (i == -1) {
            return null;
        }
        return this.items.get(i);
    }

    public T getItemAt(float f) {
        int itemIndexAt = getItemIndexAt(f);
        if (itemIndexAt == -1) {
            return null;
        }
        return this.items.get(itemIndexAt);
    }

    public int getItemIndexAt(float f) {
        float height = getHeight();
        Drawable drawable = this.style.background;
        if (drawable != null) {
            height -= drawable.getTopHeight() + drawable.getBottomHeight();
            f -= drawable.getBottomHeight();
        }
        int i = (int) ((height - f) / this.itemHeight);
        if (i < 0 || i >= this.items.size) {
            return -1;
        }
        return i;
    }

    public void setItems(T... tArr) {
        if (tArr == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }
        float prefWidth = getPrefWidth();
        float prefHeight = getPrefHeight();
        this.items.clear();
        this.items.addAll(tArr);
        this.overIndex = -1;
        this.pressedIndex = -1;
        this.selection.validate();
        invalidate();
        if (prefWidth == getPrefWidth() && prefHeight == getPrefHeight()) {
            return;
        }
        invalidateHierarchy();
    }

    public void setItems(Array array) {
        if (array == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }
        float prefWidth = getPrefWidth();
        float prefHeight = getPrefHeight();
        Array<T> array2 = this.items;
        if (array != array2) {
            array2.clear();
            this.items.addAll(array);
        }
        this.overIndex = -1;
        this.pressedIndex = -1;
        this.selection.validate();
        invalidate();
        if (prefWidth == getPrefWidth() && prefHeight == getPrefHeight()) {
            return;
        }
        invalidateHierarchy();
    }

    public void clearItems() {
        if (this.items.size == 0) {
            return;
        }
        this.items.clear();
        this.overIndex = -1;
        this.pressedIndex = -1;
        this.selection.clear();
        invalidateHierarchy();
    }

    public Array<T> getItems() {
        return this.items;
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        validate();
        return this.prefWidth;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        validate();
        return this.prefHeight;
    }

    public String toString(T t) {
        return t.toString();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Cullable
    public void setCullingArea(Rectangle rectangle) {
        this.cullingArea = rectangle;
    }

    public Rectangle getCullingArea() {
        return this.cullingArea;
    }

    public void setAlignment(int i) {
        this.alignment = i;
    }

    public int getAlignment() {
        return this.alignment;
    }

    public void setTypeToSelect(boolean z) {
        this.typeToSelect = z;
    }

    public InputListener getKeyListener() {
        return this.keyListener;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/List$ListStyle.class
      classes8.dex
     */
    public static class ListStyle {
        public Drawable background;
        public Drawable down;
        public BitmapFont font;
        public Color fontColorSelected = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        public Color fontColorUnselected = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        public Drawable over;
        public Drawable selection;

        public ListStyle() {
        }

        public ListStyle(BitmapFont bitmapFont, Color color, Color color2, Drawable drawable) {
            this.font = bitmapFont;
            this.fontColorSelected.set(color);
            this.fontColorUnselected.set(color2);
            this.selection = drawable;
        }

        public ListStyle(ListStyle listStyle) {
            this.font = listStyle.font;
            this.fontColorSelected.set(listStyle.fontColorSelected);
            this.fontColorUnselected.set(listStyle.fontColorUnselected);
            this.selection = listStyle.selection;
            this.down = listStyle.down;
            this.over = listStyle.over;
            this.background = listStyle.background;
        }
    }
}
