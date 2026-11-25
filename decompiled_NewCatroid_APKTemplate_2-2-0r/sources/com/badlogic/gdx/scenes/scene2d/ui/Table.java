package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;
import java.util.Arrays;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Table.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Table extends WidgetGroup {
    private static float[] columnWeightedWidth;
    private static float[] rowWeightedHeight;
    int align;
    Drawable background;
    private final Cell cellDefaults;
    private final Array<Cell> cells;
    private boolean clip;
    private final Array<Cell> columnDefaults;
    private float[] columnMinWidth;
    private float[] columnPrefWidth;
    private float[] columnWidth;
    private int columns;
    Debug debug;
    Array<DebugRect> debugRects;
    private float[] expandHeight;
    private float[] expandWidth;
    private boolean implicitEndRow;
    Value padBottom;
    Value padLeft;
    Value padRight;
    Value padTop;
    boolean round;
    private Cell rowDefaults;
    private float[] rowHeight;
    private float[] rowMinHeight;
    private float[] rowPrefHeight;
    private int rows;
    private boolean sizeInvalid;
    private Skin skin;
    private float tableMinHeight;
    private float tableMinWidth;
    private float tablePrefHeight;
    private float tablePrefWidth;
    public static Color debugTableColor = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    public static Color debugCellColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static Color debugActorColor = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    static final Pool<Cell> cellPool = new Pool<Cell>() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Table.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Cell newObject() {
            return new Cell();
        }
    };
    public static Value backgroundTop = new Value() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Table.2
        @Override // com.badlogic.gdx.scenes.scene2d.ui.Value
        public float get(Actor actor) {
            Drawable drawable = ((Table) actor).background;
            if (drawable == null) {
                return 0.0f;
            }
            return drawable.getTopHeight();
        }
    };
    public static Value backgroundLeft = new Value() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Table.3
        @Override // com.badlogic.gdx.scenes.scene2d.ui.Value
        public float get(Actor actor) {
            Drawable drawable = ((Table) actor).background;
            if (drawable == null) {
                return 0.0f;
            }
            return drawable.getLeftWidth();
        }
    };
    public static Value backgroundBottom = new Value() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Table.4
        @Override // com.badlogic.gdx.scenes.scene2d.ui.Value
        public float get(Actor actor) {
            Drawable drawable = ((Table) actor).background;
            if (drawable == null) {
                return 0.0f;
            }
            return drawable.getBottomHeight();
        }
    };
    public static Value backgroundRight = new Value() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Table.5
        @Override // com.badlogic.gdx.scenes.scene2d.ui.Value
        public float get(Actor actor) {
            Drawable drawable = ((Table) actor).background;
            if (drawable == null) {
                return 0.0f;
            }
            return drawable.getRightWidth();
        }
    };

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Table$Debug.class
      classes8.dex
     */
    public enum Debug {
        none,
        all,
        table,
        cell,
        actor
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Table$DebugRect.class
      classes8.dex
     */
    public static class DebugRect extends Rectangle {
        static Pool<DebugRect> pool = Pools.get(DebugRect.class);
        Color color;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    protected void drawDebugBounds(ShapeRenderer shapeRenderer) {
    }

    public Table() {
        this(null);
    }

    public Table(Skin skin) {
        this.cells = new Array<>(4);
        this.columnDefaults = new Array<>(2);
        this.sizeInvalid = true;
        this.padTop = backgroundTop;
        this.padLeft = backgroundLeft;
        this.padBottom = backgroundBottom;
        this.padRight = backgroundRight;
        this.align = 1;
        this.debug = Debug.none;
        this.round = true;
        this.skin = skin;
        this.cellDefaults = obtainCell();
        setTransform(false);
        setTouchable(Touchable.childrenOnly);
    }

    private Cell obtainCell() {
        Cell cellObtain = cellPool.obtain();
        cellObtain.setTable(this);
        return cellObtain;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        validate();
        if (isTransform()) {
            applyTransform(batch, computeTransform());
            drawBackground(batch, f, 0.0f, 0.0f);
            if (this.clip) {
                batch.flush();
                float f2 = this.padLeft.get(this);
                float f3 = this.padBottom.get(this);
                if (clipBegin(f2, f3, (getWidth() - f2) - this.padRight.get(this), (getHeight() - f3) - this.padTop.get(this))) {
                    drawChildren(batch, f);
                    batch.flush();
                    clipEnd();
                }
            } else {
                drawChildren(batch, f);
            }
            resetTransform(batch);
            return;
        }
        drawBackground(batch, f, getX(), getY());
        super.draw(batch, f);
    }

    protected void drawBackground(Batch batch, float f, float f2, float f3) {
        if (this.background == null) {
            return;
        }
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * f);
        this.background.draw(batch, f2, f3, getWidth(), getHeight());
    }

    public void setBackground(String str) {
        Skin skin = this.skin;
        if (skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }
        setBackground(skin.getDrawable(str));
    }

    public void setBackground(Drawable drawable) {
        if (this.background == drawable) {
            return;
        }
        float padTop = getPadTop();
        float padLeft = getPadLeft();
        float padBottom = getPadBottom();
        float padRight = getPadRight();
        this.background = drawable;
        float padTop2 = getPadTop();
        float padLeft2 = getPadLeft();
        float padBottom2 = getPadBottom();
        float padRight2 = getPadRight();
        if (padTop + padBottom != padTop2 + padBottom2 || padLeft + padRight != padLeft2 + padRight2) {
            invalidateHierarchy();
        } else {
            if (padTop == padTop2 && padLeft == padLeft2 && padBottom == padBottom2 && padRight == padRight2) {
                return;
            }
            invalidate();
        }
    }

    public Table background(Drawable drawable) {
        setBackground(drawable);
        return this;
    }

    public Table background(String str) {
        setBackground(str);
        return this;
    }

    public Drawable getBackground() {
        return this.background;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public Actor hit(float f, float f2, boolean z) {
        if (!this.clip || (!(z && getTouchable() == Touchable.disabled) && f >= 0.0f && f < getWidth() && f2 >= 0.0f && f2 < getHeight())) {
            return super.hit(f, f2, z);
        }
        return null;
    }

    public Table clip() {
        setClip(true);
        return this;
    }

    public Table clip(boolean z) {
        setClip(z);
        return this;
    }

    public void setClip(boolean z) {
        this.clip = z;
        setTransform(z);
        invalidate();
    }

    public boolean getClip() {
        return this.clip;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void invalidate() {
        this.sizeInvalid = true;
        super.invalidate();
    }

    public <T extends Actor> Cell<T> add(T t) {
        Cell<T> cellObtainCell = obtainCell();
        cellObtainCell.actor = t;
        if (this.implicitEndRow) {
            this.implicitEndRow = false;
            this.rows--;
            this.cells.peek().endRow = false;
        }
        int i = this.cells.size;
        if (i > 0) {
            Cell cellPeek = this.cells.peek();
            if (!cellPeek.endRow) {
                cellObtainCell.column = cellPeek.column + cellPeek.colspan.intValue();
                cellObtainCell.row = cellPeek.row;
            } else {
                cellObtainCell.column = 0;
                cellObtainCell.row = cellPeek.row + 1;
            }
            if (cellObtainCell.row > 0) {
                Cell[] cellArr = this.cells.items;
                loop0: for (int i2 = i - 1; i2 >= 0; i2--) {
                    Cell cell = cellArr[i2];
                    int i3 = cell.column;
                    int iIntValue = cell.colspan.intValue() + i3;
                    while (i3 < iIntValue) {
                        if (i3 == cellObtainCell.column) {
                            cellObtainCell.cellAboveIndex = i2;
                            break loop0;
                        }
                        i3++;
                    }
                }
            }
        } else {
            cellObtainCell.column = 0;
            cellObtainCell.row = 0;
        }
        this.cells.add(cellObtainCell);
        cellObtainCell.set(this.cellDefaults);
        if (cellObtainCell.column < this.columnDefaults.size) {
            cellObtainCell.merge(this.columnDefaults.get(cellObtainCell.column));
        }
        cellObtainCell.merge(this.rowDefaults);
        if (t != null) {
            addActor(t);
        }
        return cellObtainCell;
    }

    public Table add(Actor... actorArr) {
        for (Actor actor : actorArr) {
            add(actor);
        }
        return this;
    }

    public Cell<Label> add(CharSequence charSequence) {
        if (this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }
        return add(new Label(charSequence, this.skin));
    }

    public Cell<Label> add(CharSequence charSequence, String str) {
        if (this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }
        return add(new Label(charSequence, (Label.LabelStyle) this.skin.get(str, Label.LabelStyle.class)));
    }

    public Cell<Label> add(CharSequence charSequence, String str, Color color) {
        if (this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }
        return add(new Label(charSequence, new Label.LabelStyle(this.skin.getFont(str), color)));
    }

    public Cell<Label> add(CharSequence charSequence, String str, String str2) {
        if (this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }
        return add(new Label(charSequence, new Label.LabelStyle(this.skin.getFont(str), this.skin.getColor(str2))));
    }

    public Cell add() {
        return add((Actor) null);
    }

    public Cell<Stack> stack(Actor... actorArr) {
        Stack stack = new Stack();
        if (actorArr != null) {
            for (Actor actor : actorArr) {
                stack.addActor(actor);
            }
        }
        return add(stack);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public boolean removeActor(Actor actor) {
        return removeActor(actor, true);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public boolean removeActor(Actor actor, boolean z) {
        if (!super.removeActor(actor, z)) {
            return false;
        }
        Cell cell = getCell(actor);
        if (cell == null) {
            return true;
        }
        cell.actor = null;
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public Actor removeActorAt(int i, boolean z) {
        Actor actorRemoveActorAt = super.removeActorAt(i, z);
        Cell cell = getCell(actorRemoveActorAt);
        if (cell != null) {
            cell.actor = null;
        }
        return actorRemoveActorAt;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public void clearChildren(boolean z) {
        Cell[] cellArr = this.cells.items;
        for (int i = this.cells.size - 1; i >= 0; i--) {
            Actor actor = cellArr[i].actor;
            if (actor != null) {
                actor.remove();
            }
        }
        Pool<Cell> pool = cellPool;
        pool.freeAll(this.cells);
        this.cells.clear();
        this.rows = 0;
        this.columns = 0;
        Cell cell = this.rowDefaults;
        if (cell != null) {
            pool.free(cell);
        }
        this.rowDefaults = null;
        this.implicitEndRow = false;
        super.clearChildren(z);
    }

    public void reset() {
        clearChildren();
        this.padTop = backgroundTop;
        this.padLeft = backgroundLeft;
        this.padBottom = backgroundBottom;
        this.padRight = backgroundRight;
        this.align = 1;
        debug(Debug.none);
        this.cellDefaults.reset();
        int i = this.columnDefaults.size;
        for (int i2 = 0; i2 < i; i2++) {
            Cell cell = this.columnDefaults.get(i2);
            if (cell != null) {
                cellPool.free(cell);
            }
        }
        this.columnDefaults.clear();
    }

    public Cell row() {
        if (this.cells.size > 0) {
            if (!this.implicitEndRow) {
                if (this.cells.peek().endRow) {
                    return this.rowDefaults;
                }
                endRow();
            }
            invalidate();
        }
        this.implicitEndRow = false;
        Cell cell = this.rowDefaults;
        if (cell != null) {
            cellPool.free(cell);
        }
        Cell cellObtainCell = obtainCell();
        this.rowDefaults = cellObtainCell;
        cellObtainCell.clear();
        return this.rowDefaults;
    }

    private void endRow() {
        Cell[] cellArr = this.cells.items;
        int iIntValue = 0;
        for (int i = this.cells.size - 1; i >= 0; i--) {
            Cell cell = cellArr[i];
            if (cell.endRow) {
                break;
            }
            iIntValue += cell.colspan.intValue();
        }
        this.columns = Math.max(this.columns, iIntValue);
        this.rows++;
        this.cells.peek().endRow = true;
    }

    public Cell columnDefaults(int i) {
        Cell cellObtainCell = this.columnDefaults.size > i ? this.columnDefaults.get(i) : null;
        if (cellObtainCell == null) {
            cellObtainCell = obtainCell();
            cellObtainCell.clear();
            if (i >= this.columnDefaults.size) {
                for (int i2 = this.columnDefaults.size; i2 < i; i2++) {
                    this.columnDefaults.add(null);
                }
                this.columnDefaults.add(cellObtainCell);
            } else {
                this.columnDefaults.set(i, cellObtainCell);
            }
        }
        return cellObtainCell;
    }

    public <T extends Actor> Cell<T> getCell(T t) {
        if (t == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        Cell<T>[] cellArr = this.cells.items;
        int i = this.cells.size;
        for (int i2 = 0; i2 < i; i2++) {
            Cell<T> cell = cellArr[i2];
            if (cell.actor == t) {
                return cell;
            }
        }
        return null;
    }

    public Array<Cell> getCells() {
        return this.cells;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        float f = this.tablePrefWidth;
        Drawable drawable = this.background;
        return drawable != null ? Math.max(f, drawable.getMinWidth()) : f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        float f = this.tablePrefHeight;
        Drawable drawable = this.background;
        return drawable != null ? Math.max(f, drawable.getMinHeight()) : f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tableMinWidth;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tableMinHeight;
    }

    public Cell defaults() {
        return this.cellDefaults;
    }

    public Table pad(Value value) {
        if (value == null) {
            throw new IllegalArgumentException("pad cannot be null.");
        }
        this.padTop = value;
        this.padLeft = value;
        this.padBottom = value;
        this.padRight = value;
        this.sizeInvalid = true;
        return this;
    }

    public Table pad(Value value, Value value2, Value value3, Value value4) {
        if (value == null) {
            throw new IllegalArgumentException("top cannot be null.");
        }
        if (value2 == null) {
            throw new IllegalArgumentException("left cannot be null.");
        }
        if (value3 == null) {
            throw new IllegalArgumentException("bottom cannot be null.");
        }
        if (value4 == null) {
            throw new IllegalArgumentException("right cannot be null.");
        }
        this.padTop = value;
        this.padLeft = value2;
        this.padBottom = value3;
        this.padRight = value4;
        this.sizeInvalid = true;
        return this;
    }

    public Table padTop(Value value) {
        if (value == null) {
            throw new IllegalArgumentException("padTop cannot be null.");
        }
        this.padTop = value;
        this.sizeInvalid = true;
        return this;
    }

    public Table padLeft(Value value) {
        if (value == null) {
            throw new IllegalArgumentException("padLeft cannot be null.");
        }
        this.padLeft = value;
        this.sizeInvalid = true;
        return this;
    }

    public Table padBottom(Value value) {
        if (value == null) {
            throw new IllegalArgumentException("padBottom cannot be null.");
        }
        this.padBottom = value;
        this.sizeInvalid = true;
        return this;
    }

    public Table padRight(Value value) {
        if (value == null) {
            throw new IllegalArgumentException("padRight cannot be null.");
        }
        this.padRight = value;
        this.sizeInvalid = true;
        return this;
    }

    public Table pad(float f) {
        pad(Value.Fixed.valueOf(f));
        return this;
    }

    public Table pad(float f, float f2, float f3, float f4) {
        this.padTop = Value.Fixed.valueOf(f);
        this.padLeft = Value.Fixed.valueOf(f2);
        this.padBottom = Value.Fixed.valueOf(f3);
        this.padRight = Value.Fixed.valueOf(f4);
        this.sizeInvalid = true;
        return this;
    }

    public Table padTop(float f) {
        this.padTop = Value.Fixed.valueOf(f);
        this.sizeInvalid = true;
        return this;
    }

    public Table padLeft(float f) {
        this.padLeft = Value.Fixed.valueOf(f);
        this.sizeInvalid = true;
        return this;
    }

    public Table padBottom(float f) {
        this.padBottom = Value.Fixed.valueOf(f);
        this.sizeInvalid = true;
        return this;
    }

    public Table padRight(float f) {
        this.padRight = Value.Fixed.valueOf(f);
        this.sizeInvalid = true;
        return this;
    }

    public Table align(int i) {
        this.align = i;
        return this;
    }

    public Table center() {
        this.align = 1;
        return this;
    }

    public Table top() {
        this.align = (this.align | 2) & (-5);
        return this;
    }

    public Table left() {
        this.align = (this.align | 8) & (-17);
        return this;
    }

    public Table bottom() {
        this.align = (this.align | 4) & (-3);
        return this;
    }

    public Table right() {
        this.align = (this.align | 16) & (-9);
        return this;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setDebug(boolean z) {
        debug(z ? Debug.all : Debug.none);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public Table debug() {
        super.debug();
        return this;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    public Table debugAll() {
        super.debugAll();
        return this;
    }

    public Table debugTable() {
        super.setDebug(true);
        if (this.debug != Debug.table) {
            this.debug = Debug.table;
            invalidate();
        }
        return this;
    }

    public Table debugCell() {
        super.setDebug(true);
        if (this.debug != Debug.cell) {
            this.debug = Debug.cell;
            invalidate();
        }
        return this;
    }

    public Table debugActor() {
        super.setDebug(true);
        if (this.debug != Debug.actor) {
            this.debug = Debug.actor;
            invalidate();
        }
        return this;
    }

    public Table debug(Debug debug) {
        super.setDebug(debug != Debug.none);
        if (this.debug != debug) {
            this.debug = debug;
            if (debug == Debug.none) {
                clearDebugRects();
            } else {
                invalidate();
            }
        }
        return this;
    }

    public Debug getTableDebug() {
        return this.debug;
    }

    public Value getPadTopValue() {
        return this.padTop;
    }

    public float getPadTop() {
        return this.padTop.get(this);
    }

    public Value getPadLeftValue() {
        return this.padLeft;
    }

    public float getPadLeft() {
        return this.padLeft.get(this);
    }

    public Value getPadBottomValue() {
        return this.padBottom;
    }

    public float getPadBottom() {
        return this.padBottom.get(this);
    }

    public Value getPadRightValue() {
        return this.padRight;
    }

    public float getPadRight() {
        return this.padRight.get(this);
    }

    public float getPadX() {
        return this.padLeft.get(this) + this.padRight.get(this);
    }

    public float getPadY() {
        return this.padTop.get(this) + this.padBottom.get(this);
    }

    public int getAlign() {
        return this.align;
    }

    public int getRow(float f) {
        int i = this.cells.size;
        if (i == 0) {
            return -1;
        }
        float padTop = f + getPadTop();
        Cell[] cellArr = this.cells.items;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = i2 + 1;
            Cell cell = cellArr[i2];
            if (cell.actorY + cell.computedPadTop < padTop) {
                return i3;
            }
            if (cell.endRow) {
                i3++;
            }
            i2 = i4;
        }
        return -1;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public void setRound(boolean z) {
        this.round = z;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public float getRowHeight(int i) {
        float[] fArr = this.rowHeight;
        if (fArr == null) {
            return 0.0f;
        }
        return fArr[i];
    }

    public float getRowMinHeight(int i) {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.rowMinHeight[i];
    }

    public float getRowPrefHeight(int i) {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.rowPrefHeight[i];
    }

    public float getColumnWidth(int i) {
        float[] fArr = this.columnWidth;
        if (fArr == null) {
            return 0.0f;
        }
        return fArr[i];
    }

    public float getColumnMinWidth(int i) {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.columnMinWidth[i];
    }

    public float getColumnPrefWidth(int i) {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.columnPrefWidth[i];
    }

    private float[] ensureSize(float[] fArr, int i) {
        if (fArr == null || fArr.length < i) {
            return new float[i];
        }
        Arrays.fill(fArr, 0, i, 0.0f);
        return fArr;
    }

    private void computeSize() {
        this.sizeInvalid = false;
        Cell[] cellArr = this.cells.items;
        int i = this.cells.size;
        if (i > 0 && !cellArr[i - 1].endRow) {
            endRow();
            this.implicitEndRow = true;
        }
        int i2 = this.columns;
        int i3 = this.rows;
        float[] fArrEnsureSize = ensureSize(this.columnMinWidth, i2);
        this.columnMinWidth = fArrEnsureSize;
        float[] fArrEnsureSize2 = ensureSize(this.rowMinHeight, i3);
        this.rowMinHeight = fArrEnsureSize2;
        float[] fArrEnsureSize3 = ensureSize(this.columnPrefWidth, i2);
        this.columnPrefWidth = fArrEnsureSize3;
        float[] fArrEnsureSize4 = ensureSize(this.rowPrefHeight, i3);
        this.rowPrefHeight = fArrEnsureSize4;
        this.columnWidth = ensureSize(this.columnWidth, i2);
        this.rowHeight = ensureSize(this.rowHeight, i3);
        float[] fArrEnsureSize5 = ensureSize(this.expandWidth, i2);
        this.expandWidth = fArrEnsureSize5;
        float[] fArrEnsureSize6 = ensureSize(this.expandHeight, i3);
        this.expandHeight = fArrEnsureSize6;
        int i4 = 0;
        float f = 0.0f;
        while (i4 < i) {
            Cell cell = cellArr[i4];
            int i5 = cell.column;
            int i6 = cell.row;
            int i7 = i;
            int iIntValue = cell.colspan.intValue();
            int i8 = i4;
            Actor actor = cell.actor;
            float[] fArr = fArrEnsureSize2;
            if (cell.expandY.intValue() != 0 && fArrEnsureSize6[i6] == 0.0f) {
                fArrEnsureSize6[i6] = cell.expandY.intValue();
            }
            if (iIntValue == 1 && cell.expandX.intValue() != 0 && fArrEnsureSize5[i5] == 0.0f) {
                fArrEnsureSize5[i5] = cell.expandX.intValue();
            }
            float[] fArr2 = fArrEnsureSize6;
            cell.computedPadLeft = cell.padLeft.get(actor) + (i5 == 0 ? 0.0f : Math.max(0.0f, cell.spaceLeft.get(actor) - f));
            cell.computedPadTop = cell.padTop.get(actor);
            if (cell.cellAboveIndex != -1) {
                cell.computedPadTop += Math.max(0.0f, cell.spaceTop.get(actor) - cellArr[cell.cellAboveIndex].spaceBottom.get(actor));
            }
            float f2 = cell.spaceRight.get(actor);
            cell.computedPadRight = cell.padRight.get(actor) + (i5 + iIntValue == i2 ? 0.0f : f2);
            cell.computedPadBottom = cell.padBottom.get(actor) + (i6 == i3 + (-1) ? 0.0f : cell.spaceBottom.get(actor));
            float f3 = cell.prefWidth.get(actor);
            float f4 = cell.prefHeight.get(actor);
            float f5 = cell.minWidth.get(actor);
            int i9 = i3;
            float fCeil = cell.minHeight.get(actor);
            int i10 = i2;
            float fCeil2 = cell.maxWidth.get(actor);
            float[] fArr3 = fArrEnsureSize5;
            float fCeil3 = cell.maxHeight.get(actor);
            if (f3 < f5) {
                f3 = f5;
            }
            if (f4 < fCeil) {
                f4 = fCeil;
            }
            if (fCeil2 <= 0.0f || f3 <= fCeil2) {
                fCeil2 = f3;
            }
            if (fCeil3 <= 0.0f || f4 <= fCeil3) {
                fCeil3 = f4;
            }
            if (this.round) {
                float fCeil4 = (float) Math.ceil(f5);
                fCeil = (float) Math.ceil(fCeil);
                fCeil2 = (float) Math.ceil(fCeil2);
                fCeil3 = (float) Math.ceil(fCeil3);
                f5 = fCeil4;
            }
            if (iIntValue == 1) {
                float f6 = cell.computedPadLeft + cell.computedPadRight;
                fArrEnsureSize3[i5] = Math.max(fArrEnsureSize3[i5], fCeil2 + f6);
                fArrEnsureSize[i5] = Math.max(fArrEnsureSize[i5], f5 + f6);
            }
            float f7 = cell.computedPadTop + cell.computedPadBottom;
            fArrEnsureSize4[i6] = Math.max(fArrEnsureSize4[i6], fCeil3 + f7);
            fArr[i6] = Math.max(fArr[i6], fCeil + f7);
            i4 = i8 + 1;
            i = i7;
            fArrEnsureSize2 = fArr;
            fArrEnsureSize6 = fArr2;
            f = f2;
            i3 = i9;
            i2 = i10;
            fArrEnsureSize5 = fArr3;
        }
        int i11 = i2;
        int i12 = i3;
        float[] fArr4 = fArrEnsureSize2;
        float[] fArr5 = fArrEnsureSize5;
        int i13 = i;
        float fMax = 0.0f;
        float fMax2 = 0.0f;
        float fMax3 = 0.0f;
        float fMax4 = 0.0f;
        for (int i14 = 0; i14 < i13; i14++) {
            Cell cell2 = cellArr[i14];
            int i15 = cell2.column;
            int iIntValue2 = cell2.expandX.intValue();
            if (iIntValue2 != 0) {
                int iIntValue3 = cell2.colspan.intValue() + i15;
                int i16 = i15;
                while (true) {
                    if (i16 >= iIntValue3) {
                        int i17 = i15;
                        while (i17 < iIntValue3) {
                            fArr5[i17] = iIntValue2;
                            i17++;
                            iIntValue3 = iIntValue3;
                        }
                        break;
                    }
                    if (fArr5[i16] != 0.0f) {
                        break;
                    } else {
                        i16++;
                    }
                }
            }
            if (cell2.uniformX == Boolean.TRUE && cell2.colspan.intValue() == 1) {
                float f8 = cell2.computedPadLeft + cell2.computedPadRight;
                fMax3 = Math.max(fMax3, fArrEnsureSize[i15] - f8);
                fMax = Math.max(fMax, fArrEnsureSize3[i15] - f8);
            }
            if (cell2.uniformY == Boolean.TRUE) {
                float f9 = cell2.computedPadTop + cell2.computedPadBottom;
                fMax4 = Math.max(fMax4, fArr4[cell2.row] - f9);
                fMax2 = Math.max(fMax2, fArrEnsureSize4[cell2.row] - f9);
            }
        }
        if (fMax > 0.0f || fMax2 > 0.0f) {
            for (int i18 = 0; i18 < i13; i18++) {
                Cell cell3 = cellArr[i18];
                if (fMax > 0.0f && cell3.uniformX == Boolean.TRUE && cell3.colspan.intValue() == 1) {
                    float f10 = cell3.computedPadLeft + cell3.computedPadRight;
                    fArrEnsureSize[cell3.column] = fMax3 + f10;
                    fArrEnsureSize3[cell3.column] = f10 + fMax;
                }
                if (fMax2 > 0.0f && cell3.uniformY == Boolean.TRUE) {
                    float f11 = cell3.computedPadTop + cell3.computedPadBottom;
                    fArr4[cell3.row] = fMax4 + f11;
                    fArrEnsureSize4[cell3.row] = f11 + fMax2;
                }
            }
        }
        for (int i19 = 0; i19 < i13; i19++) {
            Cell cell4 = cellArr[i19];
            int iIntValue4 = cell4.colspan.intValue();
            if (iIntValue4 != 1) {
                int i20 = cell4.column;
                Actor actor2 = cell4.actor;
                float fCeil5 = cell4.minWidth.get(actor2);
                float f12 = cell4.prefWidth.get(actor2);
                float fCeil6 = cell4.maxWidth.get(actor2);
                if (f12 < fCeil5) {
                    f12 = fCeil5;
                }
                if (fCeil6 <= 0.0f || f12 <= fCeil6) {
                    fCeil6 = f12;
                }
                if (this.round) {
                    fCeil5 = (float) Math.ceil(fCeil5);
                    fCeil6 = (float) Math.ceil(fCeil6);
                }
                float f13 = -(cell4.computedPadLeft + cell4.computedPadRight);
                int i21 = i20 + iIntValue4;
                float f14 = f13;
                float f15 = 0.0f;
                for (int i22 = i20; i22 < i21; i22++) {
                    f13 += fArrEnsureSize[i22];
                    f14 += fArrEnsureSize3[i22];
                    f15 += fArr5[i22];
                }
                float fMax5 = Math.max(0.0f, fCeil5 - f13);
                float fMax6 = Math.max(0.0f, fCeil6 - f14);
                while (i20 < i21) {
                    float f16 = f15 == 0.0f ? 1.0f / iIntValue4 : fArr5[i20] / f15;
                    fArrEnsureSize[i20] = fArrEnsureSize[i20] + (fMax5 * f16);
                    fArrEnsureSize3[i20] = fArrEnsureSize3[i20] + (f16 * fMax6);
                    i20++;
                }
            }
        }
        float f17 = this.padLeft.get(this) + this.padRight.get(this);
        float f18 = this.padTop.get(this) + this.padBottom.get(this);
        this.tableMinWidth = f17;
        this.tablePrefWidth = f17;
        for (int i23 = 0; i23 < i11; i23++) {
            this.tableMinWidth += fArrEnsureSize[i23];
            this.tablePrefWidth += fArrEnsureSize3[i23];
        }
        this.tableMinHeight = f18;
        this.tablePrefHeight = f18;
        for (int i24 = 0; i24 < i12; i24++) {
            float f19 = this.tableMinHeight;
            float f20 = fArr4[i24];
            this.tableMinHeight = f19 + f20;
            this.tablePrefHeight += Math.max(f20, fArrEnsureSize4[i24]);
        }
        this.tablePrefWidth = Math.max(this.tableMinWidth, this.tablePrefWidth);
        this.tablePrefHeight = Math.max(this.tableMinHeight, this.tablePrefHeight);
    }

    /* JADX WARN: Code duplicated, block: B:112:0x0284  */
    /* JADX WARN: Code duplicated, block: B:114:0x0289  */
    /* JADX WARN: Code duplicated, block: B:116:0x028d  */
    /* JADX WARN: Code duplicated, block: B:117:0x0291  */
    /* JADX WARN: Code duplicated, block: B:120:0x0298  */
    /* JADX WARN: Code duplicated, block: B:122:0x02a8 A[LOOP:14: B:121:0x02a6->B:122:0x02a8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:125:0x02ca  */
    /* JADX WARN: Code duplicated, block: B:127:0x02e7  */
    /* JADX WARN: Code duplicated, block: B:128:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:131:0x02f6  */
    /* JADX WARN: Code duplicated, block: B:133:0x031c  */
    /* JADX WARN: Code duplicated, block: B:134:0x0325  */
    /* JADX WARN: Code duplicated, block: B:137:0x0330  */
    /* JADX WARN: Code duplicated, block: B:138:0x0333  */
    /* JADX WARN: Code duplicated, block: B:140:0x0337  */
    /* JADX WARN: Code duplicated, block: B:141:0x033f  */
    /* JADX WARN: Code duplicated, block: B:144:0x034d  */
    /* JADX WARN: Code duplicated, block: B:146:0x0354  */
    /* JADX WARN: Code duplicated, block: B:148:0x0358  */
    /* JADX WARN: Code duplicated, block: B:149:0x0365  */
    /* JADX WARN: Code duplicated, block: B:152:0x0385  */
    /* JADX WARN: Code duplicated, block: B:155:0x03b1  */
    /* JADX WARN: Code duplicated, block: B:156:0x03c1  */
    /* JADX WARN: Code duplicated, block: B:159:0x03c7  */
    /* JADX WARN: Code duplicated, block: B:160:0x03ce  */
    /* JADX WARN: Code duplicated, block: B:164:0x03e9  */
    /* JADX WARN: Code duplicated, block: B:166:0x03ef  */
    /* JADX WARN: Code duplicated, block: B:170:0x03fd  */
    /* JADX WARN: Code duplicated, block: B:199:0x03f4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:202:? A[RETURN, SYNTHETIC] */
    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        float[] fArr;
        float[] fArr2;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        int i;
        Actor[] actorArr;
        int i2;
        int i3;
        Object obj;
        Cell cell;
        int i4;
        int iIntValue;
        float f7;
        float f8;
        float f9;
        float fFloatValue;
        float fFloatValue2;
        int i5;
        int iIntValue2;
        float f10;
        float f11;
        Table table = this;
        if (table.sizeInvalid) {
            computeSize();
        }
        float width = getWidth();
        float height = getHeight();
        int i6 = table.columns;
        int i7 = table.rows;
        float[] fArr3 = table.columnWidth;
        float[] fArr4 = table.rowHeight;
        float f12 = table.padLeft.get(table);
        float f13 = table.padRight.get(table) + f12;
        float f14 = table.padTop.get(table);
        float f15 = table.padBottom.get(table) + f14;
        float f16 = table.tablePrefWidth;
        float f17 = table.tableMinWidth;
        float f18 = f16 - f17;
        if (f18 == 0.0f) {
            fArr = table.columnMinWidth;
        } else {
            float fMin = Math.min(f18, Math.max(0.0f, width - f17));
            float[] fArrEnsureSize = table.ensureSize(columnWeightedWidth, i6);
            columnWeightedWidth = fArrEnsureSize;
            float[] fArr5 = table.columnMinWidth;
            float[] fArr6 = table.columnPrefWidth;
            for (int i8 = 0; i8 < i6; i8++) {
                float f19 = fArr6[i8];
                float f20 = fArr5[i8];
                fArrEnsureSize[i8] = f20 + (((f19 - f20) / f18) * fMin);
            }
            fArr = fArrEnsureSize;
        }
        float f21 = table.tablePrefHeight - table.tableMinHeight;
        if (f21 == 0.0f) {
            fArr2 = table.rowMinHeight;
        } else {
            float[] fArrEnsureSize2 = table.ensureSize(rowWeightedHeight, i7);
            rowWeightedHeight = fArrEnsureSize2;
            float fMin2 = Math.min(f21, Math.max(0.0f, height - table.tableMinHeight));
            float[] fArr7 = table.rowMinHeight;
            float[] fArr8 = table.rowPrefHeight;
            for (int i9 = 0; i9 < i7; i9++) {
                float f22 = fArr8[i9];
                float f23 = fArr7[i9];
                fArrEnsureSize2[i9] = f23 + (((f22 - f23) / f21) * fMin2);
            }
            fArr2 = fArrEnsureSize2;
        }
        Cell[] cellArr = table.cells.items;
        int i10 = table.cells.size;
        int i11 = 0;
        while (i11 < i10) {
            Cell cell2 = cellArr[i11];
            int i12 = cell2.column;
            Cell[] cellArr2 = cellArr;
            int i13 = cell2.row;
            int i14 = i10;
            Actor actor = cell2.actor;
            float f24 = height;
            int iIntValue3 = cell2.colspan.intValue();
            float f25 = f15;
            int i15 = i12 + iIntValue3;
            float f26 = width;
            int i16 = i7;
            float f27 = 0.0f;
            for (int i17 = i12; i17 < i15; i17++) {
                f27 += fArr[i17];
            }
            float f28 = fArr2[i13];
            float f29 = cell2.prefWidth.get(actor);
            float[] fArr9 = fArr2;
            float f30 = cell2.prefHeight.get(actor);
            float[] fArr10 = fArr;
            float f31 = cell2.minWidth.get(actor);
            float f32 = f13;
            float f33 = cell2.minHeight.get(actor);
            int i18 = i6;
            float f34 = cell2.maxWidth.get(actor);
            float f35 = cell2.maxHeight.get(actor);
            if (f29 < f31) {
                f29 = f31;
            }
            if (f30 < f33) {
                f30 = f33;
            }
            if (f34 <= 0.0f || f29 <= f34) {
                f34 = f29;
            }
            if (f35 <= 0.0f || f30 <= f35) {
                f35 = f30;
            }
            cell2.actorWidth = Math.min((f27 - cell2.computedPadLeft) - cell2.computedPadRight, f34);
            cell2.actorHeight = Math.min((f28 - cell2.computedPadTop) - cell2.computedPadBottom, f35);
            if (iIntValue3 == 1) {
                fArr3[i12] = Math.max(fArr3[i12], f27);
            }
            fArr4[i13] = Math.max(fArr4[i13], f28);
            i11++;
            table = this;
            cellArr = cellArr2;
            i10 = i14;
            height = f24;
            f15 = f25;
            i7 = i16;
            width = f26;
            fArr2 = fArr9;
            fArr = fArr10;
            f13 = f32;
            i6 = i18;
        }
        float f36 = width;
        float f37 = height;
        int i19 = i6;
        int i20 = i7;
        Cell[] cellArr3 = cellArr;
        float f38 = f13;
        float f39 = f15;
        float[] fArr11 = fArr;
        int i21 = i10;
        float[] fArr12 = table.expandWidth;
        float[] fArr13 = table.expandHeight;
        float f40 = 0.0f;
        for (int i22 = 0; i22 < i19; i22++) {
            f40 += fArr12[i22];
        }
        float f41 = 0.0f;
        if (f40 > 0.0f) {
            float f42 = f36 - f38;
            for (int i23 = 0; i23 < i19; i23++) {
                f42 -= fArr3[i23];
            }
            if (f42 > 0.0f) {
                float f43 = 0.0f;
                int i24 = 0;
                int i25 = 0;
                while (i24 < i19) {
                    float f44 = fArr12[i24];
                    if (f44 != f41) {
                        float f45 = (f44 * f42) / f40;
                        fArr3[i24] = fArr3[i24] + f45;
                        f43 += f45;
                        i25 = i24;
                    }
                    i24++;
                    f41 = 0.0f;
                }
                fArr3[i25] = fArr3[i25] + (f42 - f43);
            }
        }
        float f46 = 0.0f;
        for (int i26 = 0; i26 < i20; i26++) {
            f46 += fArr13[i26];
        }
        float f47 = 0.0f;
        if (f46 > 0.0f) {
            float f48 = f37 - f39;
            for (int i27 = 0; i27 < i20; i27++) {
                f48 -= fArr4[i27];
            }
            if (f48 > 0.0f) {
                float f49 = 0.0f;
                int i28 = 0;
                int i29 = 0;
                while (i28 < i20) {
                    float f50 = fArr13[i28];
                    if (f50 != f47) {
                        float f51 = (f50 * f48) / f46;
                        fArr4[i28] = fArr4[i28] + f51;
                        f49 += f51;
                        i29 = i28;
                    }
                    i28++;
                    f47 = 0.0f;
                }
                fArr4[i29] = fArr4[i29] + (f48 - f49);
            }
        }
        int i30 = i21;
        for (int i31 = 0; i31 < i30; i31++) {
            Cell cell3 = cellArr3[i31];
            int iIntValue4 = cell3.colspan.intValue();
            if (iIntValue4 != 1) {
                int i32 = cell3.column;
                int i33 = i32 + iIntValue4;
                float f52 = 0.0f;
                while (i32 < i33) {
                    f52 += fArr11[i32] - fArr3[i32];
                    i32++;
                }
                float fMax = (f52 - Math.max(0.0f, cell3.computedPadLeft + cell3.computedPadRight)) / iIntValue4;
                if (fMax > 0.0f) {
                    int i34 = cell3.column;
                    int i35 = iIntValue4 + i34;
                    while (i34 < i35) {
                        fArr3[i34] = fArr3[i34] + fMax;
                        i34++;
                    }
                }
            }
        }
        float f53 = f38;
        for (int i36 = 0; i36 < i19; i36++) {
            f53 += fArr3[i36];
        }
        float f54 = f39;
        for (int i37 = 0; i37 < i20; i37++) {
            f54 += fArr4[i37];
        }
        int i38 = table.align;
        if ((i38 & 16) == 0) {
            if ((i38 & 8) == 0) {
                f2 = (f36 - f53) / 2.0f;
            } else {
                f = f12;
            }
            if ((i38 & 4) != 0) {
                if ((i38 & 2) == 0) {
                    f4 = (f37 - f54) / 2.0f;
                } else {
                    f3 = f14;
                }
                f5 = f;
                f6 = f3;
                i = 0;
                while (i < i30) {
                    cell = cellArr3[i];
                    i4 = cell.column;
                    iIntValue = cell.colspan.intValue() + i4;
                    f7 = 0.0f;
                    while (i4 < iIntValue) {
                        f7 += fArr3[i4];
                        i4++;
                    }
                    f8 = f7 - (cell.computedPadLeft + cell.computedPadRight);
                    f9 = f5 + cell.computedPadLeft;
                    fFloatValue = cell.fillX.floatValue();
                    fFloatValue2 = cell.fillY.floatValue();
                    if (fFloatValue > 0.0f) {
                        i5 = i30;
                        cell.actorWidth = Math.max(fFloatValue * f8, cell.minWidth.get(cell.actor));
                        f11 = cell.maxWidth.get(cell.actor);
                        if (f11 > 0.0f) {
                            cell.actorWidth = Math.min(cell.actorWidth, f11);
                        }
                    } else {
                        i5 = i30;
                    }
                    if (fFloatValue2 > 0.0f) {
                        cell.actorHeight = Math.max(((fArr4[cell.row] * fFloatValue2) - cell.computedPadTop) - cell.computedPadBottom, cell.minHeight.get(cell.actor));
                        f10 = cell.maxHeight.get(cell.actor);
                        if (f10 > 0.0f) {
                            cell.actorHeight = Math.min(cell.actorHeight, f10);
                        }
                    }
                    iIntValue2 = cell.align.intValue();
                    if ((iIntValue2 & 8) != 0) {
                        cell.actorX = f9;
                    } else if ((iIntValue2 & 16) != 0) {
                        cell.actorX = (f9 + f8) - cell.actorWidth;
                    } else {
                        cell.actorX = ((f8 - cell.actorWidth) / 2.0f) + f9;
                    }
                    if ((iIntValue2 & 2) != 0) {
                        cell.actorY = cell.computedPadTop;
                    } else {
                        if ((iIntValue2 & 4) != 0) {
                            cell.actorY = (fArr4[cell.row] - cell.actorHeight) - cell.computedPadBottom;
                        } else {
                            cell.actorY = (((fArr4[cell.row] - cell.actorHeight) + cell.computedPadTop) - cell.computedPadBottom) / 2.0f;
                        }
                        cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
                        if (table.round) {
                            cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                            cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                            cell.actorX = (float) Math.floor(cell.actorX);
                            cell.actorY = (float) Math.floor(cell.actorY);
                        }
                        if (cell.actor != null) {
                            cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
                        }
                        if (cell.endRow) {
                            f6 += fArr4[cell.row];
                            f5 = f;
                        } else {
                            f5 = f9 + f8 + cell.computedPadRight;
                        }
                        i++;
                        fArr3 = fArr3;
                        i30 = i5;
                    }
                    cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
                    if (table.round) {
                        cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                        cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                        cell.actorX = (float) Math.floor(cell.actorX);
                        cell.actorY = (float) Math.floor(cell.actorY);
                    }
                    if (cell.actor != null) {
                        cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
                    }
                    if (cell.endRow) {
                        f6 += fArr4[cell.row];
                        f5 = f;
                    } else {
                        f5 = f9 + f8 + cell.computedPadRight;
                    }
                    i++;
                    fArr3 = fArr3;
                    i30 = i5;
                }
                SnapshotArray<Actor> children = getChildren();
                actorArr = children.items;
                i2 = children.size;
                for (i3 = 0; i3 < i2; i3++) {
                    obj = actorArr[i3];
                    if (obj instanceof Layout) {
                        ((Layout) obj).validate();
                    }
                }
                if (table.debug != Debug.none) {
                    table.addDebugRects(f, f3, f53 - f38, f54 - f39);
                }
            }
            f4 = f37 - f54;
            f3 = f14 + f4;
            f5 = f;
            f6 = f3;
            i = 0;
            while (i < i30) {
                cell = cellArr3[i];
                i4 = cell.column;
                iIntValue = cell.colspan.intValue() + i4;
                f7 = 0.0f;
                while (i4 < iIntValue) {
                    f7 += fArr3[i4];
                    i4++;
                }
                f8 = f7 - (cell.computedPadLeft + cell.computedPadRight);
                f9 = f5 + cell.computedPadLeft;
                fFloatValue = cell.fillX.floatValue();
                fFloatValue2 = cell.fillY.floatValue();
                if (fFloatValue > 0.0f) {
                    i5 = i30;
                    cell.actorWidth = Math.max(fFloatValue * f8, cell.minWidth.get(cell.actor));
                    f11 = cell.maxWidth.get(cell.actor);
                    if (f11 > 0.0f) {
                        cell.actorWidth = Math.min(cell.actorWidth, f11);
                    }
                } else {
                    i5 = i30;
                }
                if (fFloatValue2 > 0.0f) {
                    cell.actorHeight = Math.max(((fArr4[cell.row] * fFloatValue2) - cell.computedPadTop) - cell.computedPadBottom, cell.minHeight.get(cell.actor));
                    f10 = cell.maxHeight.get(cell.actor);
                    if (f10 > 0.0f) {
                        cell.actorHeight = Math.min(cell.actorHeight, f10);
                    }
                }
                iIntValue2 = cell.align.intValue();
                if ((iIntValue2 & 8) != 0) {
                    cell.actorX = f9;
                } else if ((iIntValue2 & 16) != 0) {
                    cell.actorX = (f9 + f8) - cell.actorWidth;
                } else {
                    cell.actorX = ((f8 - cell.actorWidth) / 2.0f) + f9;
                }
                if ((iIntValue2 & 2) != 0) {
                    cell.actorY = cell.computedPadTop;
                } else {
                    if ((iIntValue2 & 4) != 0) {
                        cell.actorY = (fArr4[cell.row] - cell.actorHeight) - cell.computedPadBottom;
                    } else {
                        cell.actorY = (((fArr4[cell.row] - cell.actorHeight) + cell.computedPadTop) - cell.computedPadBottom) / 2.0f;
                    }
                    cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
                    if (table.round) {
                        cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                        cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                        cell.actorX = (float) Math.floor(cell.actorX);
                        cell.actorY = (float) Math.floor(cell.actorY);
                    }
                    if (cell.actor != null) {
                        cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
                    }
                    if (cell.endRow) {
                        f6 += fArr4[cell.row];
                        f5 = f;
                    } else {
                        f5 = f9 + f8 + cell.computedPadRight;
                    }
                    i++;
                    fArr3 = fArr3;
                    i30 = i5;
                }
                cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
                if (table.round) {
                    cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                    cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                    cell.actorX = (float) Math.floor(cell.actorX);
                    cell.actorY = (float) Math.floor(cell.actorY);
                }
                if (cell.actor != null) {
                    cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
                }
                if (cell.endRow) {
                    f6 += fArr4[cell.row];
                    f5 = f;
                } else {
                    f5 = f9 + f8 + cell.computedPadRight;
                }
                i++;
                fArr3 = fArr3;
                i30 = i5;
            }
            SnapshotArray<Actor> children2 = getChildren();
            actorArr = children2.items;
            i2 = children2.size;
            while (i3 < i2) {
                obj = actorArr[i3];
                if (obj instanceof Layout) {
                    ((Layout) obj).validate();
                }
            }
            if (table.debug != Debug.none) {
                table.addDebugRects(f, f3, f53 - f38, f54 - f39);
            }
        }
        f2 = f36 - f53;
        f = f12 + f2;
        if ((i38 & 4) != 0) {
            if ((i38 & 2) == 0) {
                f4 = (f37 - f54) / 2.0f;
            } else {
                f3 = f14;
            }
            f5 = f;
            f6 = f3;
            i = 0;
            while (i < i30) {
                cell = cellArr3[i];
                i4 = cell.column;
                iIntValue = cell.colspan.intValue() + i4;
                f7 = 0.0f;
                while (i4 < iIntValue) {
                    f7 += fArr3[i4];
                    i4++;
                }
                f8 = f7 - (cell.computedPadLeft + cell.computedPadRight);
                f9 = f5 + cell.computedPadLeft;
                fFloatValue = cell.fillX.floatValue();
                fFloatValue2 = cell.fillY.floatValue();
                if (fFloatValue > 0.0f) {
                    i5 = i30;
                    cell.actorWidth = Math.max(fFloatValue * f8, cell.minWidth.get(cell.actor));
                    f11 = cell.maxWidth.get(cell.actor);
                    if (f11 > 0.0f) {
                        cell.actorWidth = Math.min(cell.actorWidth, f11);
                    }
                } else {
                    i5 = i30;
                }
                if (fFloatValue2 > 0.0f) {
                    cell.actorHeight = Math.max(((fArr4[cell.row] * fFloatValue2) - cell.computedPadTop) - cell.computedPadBottom, cell.minHeight.get(cell.actor));
                    f10 = cell.maxHeight.get(cell.actor);
                    if (f10 > 0.0f) {
                        cell.actorHeight = Math.min(cell.actorHeight, f10);
                    }
                }
                iIntValue2 = cell.align.intValue();
                if ((iIntValue2 & 8) != 0) {
                    cell.actorX = f9;
                } else if ((iIntValue2 & 16) != 0) {
                    cell.actorX = (f9 + f8) - cell.actorWidth;
                } else {
                    cell.actorX = ((f8 - cell.actorWidth) / 2.0f) + f9;
                }
                if ((iIntValue2 & 2) != 0) {
                    cell.actorY = cell.computedPadTop;
                } else {
                    if ((iIntValue2 & 4) != 0) {
                        cell.actorY = (fArr4[cell.row] - cell.actorHeight) - cell.computedPadBottom;
                    } else {
                        cell.actorY = (((fArr4[cell.row] - cell.actorHeight) + cell.computedPadTop) - cell.computedPadBottom) / 2.0f;
                    }
                    cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
                    if (table.round) {
                        cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                        cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                        cell.actorX = (float) Math.floor(cell.actorX);
                        cell.actorY = (float) Math.floor(cell.actorY);
                    }
                    if (cell.actor != null) {
                        cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
                    }
                    if (cell.endRow) {
                        f6 += fArr4[cell.row];
                        f5 = f;
                    } else {
                        f5 = f9 + f8 + cell.computedPadRight;
                    }
                    i++;
                    fArr3 = fArr3;
                    i30 = i5;
                }
                cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
                if (table.round) {
                    cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                    cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                    cell.actorX = (float) Math.floor(cell.actorX);
                    cell.actorY = (float) Math.floor(cell.actorY);
                }
                if (cell.actor != null) {
                    cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
                }
                if (cell.endRow) {
                    f6 += fArr4[cell.row];
                    f5 = f;
                } else {
                    f5 = f9 + f8 + cell.computedPadRight;
                }
                i++;
                fArr3 = fArr3;
                i30 = i5;
            }
            SnapshotArray<Actor> children3 = getChildren();
            actorArr = children3.items;
            i2 = children3.size;
            while (i3 < i2) {
                obj = actorArr[i3];
                if (obj instanceof Layout) {
                    ((Layout) obj).validate();
                }
            }
            if (table.debug != Debug.none) {
                table.addDebugRects(f, f3, f53 - f38, f54 - f39);
            }
        }
        f4 = f37 - f54;
        f3 = f14 + f4;
        f5 = f;
        f6 = f3;
        i = 0;
        while (i < i30) {
            cell = cellArr3[i];
            i4 = cell.column;
            iIntValue = cell.colspan.intValue() + i4;
            f7 = 0.0f;
            while (i4 < iIntValue) {
                f7 += fArr3[i4];
                i4++;
            }
            f8 = f7 - (cell.computedPadLeft + cell.computedPadRight);
            f9 = f5 + cell.computedPadLeft;
            fFloatValue = cell.fillX.floatValue();
            fFloatValue2 = cell.fillY.floatValue();
            if (fFloatValue > 0.0f) {
                i5 = i30;
                cell.actorWidth = Math.max(fFloatValue * f8, cell.minWidth.get(cell.actor));
                f11 = cell.maxWidth.get(cell.actor);
                if (f11 > 0.0f) {
                    cell.actorWidth = Math.min(cell.actorWidth, f11);
                }
            } else {
                i5 = i30;
            }
            if (fFloatValue2 > 0.0f) {
                cell.actorHeight = Math.max(((fArr4[cell.row] * fFloatValue2) - cell.computedPadTop) - cell.computedPadBottom, cell.minHeight.get(cell.actor));
                f10 = cell.maxHeight.get(cell.actor);
                if (f10 > 0.0f) {
                    cell.actorHeight = Math.min(cell.actorHeight, f10);
                }
            }
            iIntValue2 = cell.align.intValue();
            if ((iIntValue2 & 8) != 0) {
                cell.actorX = f9;
            } else if ((iIntValue2 & 16) != 0) {
                cell.actorX = (f9 + f8) - cell.actorWidth;
            } else {
                cell.actorX = ((f8 - cell.actorWidth) / 2.0f) + f9;
            }
            if ((iIntValue2 & 2) != 0) {
                cell.actorY = cell.computedPadTop;
            } else {
                if ((iIntValue2 & 4) != 0) {
                    cell.actorY = (fArr4[cell.row] - cell.actorHeight) - cell.computedPadBottom;
                } else {
                    cell.actorY = (((fArr4[cell.row] - cell.actorHeight) + cell.computedPadTop) - cell.computedPadBottom) / 2.0f;
                }
                cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
                if (table.round) {
                    cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                    cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                    cell.actorX = (float) Math.floor(cell.actorX);
                    cell.actorY = (float) Math.floor(cell.actorY);
                }
                if (cell.actor != null) {
                    cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
                }
                if (cell.endRow) {
                    f6 += fArr4[cell.row];
                    f5 = f;
                } else {
                    f5 = f9 + f8 + cell.computedPadRight;
                }
                i++;
                fArr3 = fArr3;
                i30 = i5;
            }
            cell.actorY = ((f37 - f6) - cell.actorY) - cell.actorHeight;
            if (table.round) {
                cell.actorWidth = (float) Math.ceil(cell.actorWidth);
                cell.actorHeight = (float) Math.ceil(cell.actorHeight);
                cell.actorX = (float) Math.floor(cell.actorX);
                cell.actorY = (float) Math.floor(cell.actorY);
            }
            if (cell.actor != null) {
                cell.actor.setBounds(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight);
            }
            if (cell.endRow) {
                f6 += fArr4[cell.row];
                f5 = f;
            } else {
                f5 = f9 + f8 + cell.computedPadRight;
            }
            i++;
            fArr3 = fArr3;
            i30 = i5;
        }
        SnapshotArray<Actor> children4 = getChildren();
        actorArr = children4.items;
        i2 = children4.size;
        while (i3 < i2) {
            obj = actorArr[i3];
            if (obj instanceof Layout) {
                ((Layout) obj).validate();
            }
        }
        if (table.debug != Debug.none) {
            table.addDebugRects(f, f3, f53 - f38, f54 - f39);
        }
    }

    private void addDebugRects(float f, float f2, float f3, float f4) {
        clearDebugRects();
        if (this.debug == Debug.table || this.debug == Debug.all) {
            addDebugRect(0.0f, 0.0f, getWidth(), getHeight(), debugTableColor);
            addDebugRect(f, getHeight() - f2, f3, -f4, debugTableColor);
        }
        int i = this.cells.size;
        float f5 = f;
        for (int i2 = 0; i2 < i; i2++) {
            Cell cell = this.cells.get(i2);
            if (this.debug == Debug.actor || this.debug == Debug.all) {
                addDebugRect(cell.actorX, cell.actorY, cell.actorWidth, cell.actorHeight, debugActorColor);
            }
            int i3 = cell.column;
            int iIntValue = cell.colspan.intValue() + i3;
            float f6 = 0.0f;
            while (i3 < iIntValue) {
                f6 += this.columnWidth[i3];
                i3++;
            }
            float f7 = f6 - (cell.computedPadLeft + cell.computedPadRight);
            float f8 = f5 + cell.computedPadLeft;
            if (this.debug == Debug.cell || this.debug == Debug.all) {
                float f9 = (this.rowHeight[cell.row] - cell.computedPadTop) - cell.computedPadBottom;
                addDebugRect(f8, getHeight() - (cell.computedPadTop + f2), f7, -f9, debugCellColor);
            }
            if (cell.endRow) {
                f2 += this.rowHeight[cell.row];
                f5 = f;
            } else {
                f5 = f8 + f7 + cell.computedPadRight;
            }
        }
    }

    private void clearDebugRects() {
        if (this.debugRects == null) {
            this.debugRects = new Array<>();
        }
        DebugRect.pool.freeAll(this.debugRects);
        this.debugRects.clear();
    }

    private void addDebugRect(float f, float f2, float f3, float f4, Color color) {
        DebugRect debugRectObtain = DebugRect.pool.obtain();
        debugRectObtain.color = color;
        debugRectObtain.set(f, f2, f3, f4);
        this.debugRects.add(debugRectObtain);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void drawDebug(ShapeRenderer shapeRenderer) {
        float f;
        float f2;
        if (isTransform()) {
            applyTransform(shapeRenderer, computeTransform());
            drawDebugRects(shapeRenderer);
            if (this.clip) {
                shapeRenderer.flush();
                float width = getWidth();
                float height = getHeight();
                if (this.background != null) {
                    f = this.padLeft.get(this);
                    f2 = this.padBottom.get(this);
                    width -= this.padRight.get(this) + f;
                    height -= this.padTop.get(this) + f2;
                } else {
                    f = 0.0f;
                    f2 = 0.0f;
                }
                if (clipBegin(f, f2, width, height)) {
                    drawDebugChildren(shapeRenderer);
                    clipEnd();
                }
            } else {
                drawDebugChildren(shapeRenderer);
            }
            resetTransform(shapeRenderer);
            return;
        }
        drawDebugRects(shapeRenderer);
        super.drawDebug(shapeRenderer);
    }

    private void drawDebugRects(ShapeRenderer shapeRenderer) {
        float x;
        float y;
        if (this.debugRects == null || !getDebug()) {
            return;
        }
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        if (getStage() != null) {
            shapeRenderer.setColor(getStage().getDebugColor());
        }
        if (isTransform()) {
            x = 0.0f;
            y = 0.0f;
        } else {
            x = getX();
            y = getY();
        }
        int i = this.debugRects.size;
        for (int i2 = 0; i2 < i; i2++) {
            DebugRect debugRect = this.debugRects.get(i2);
            shapeRenderer.setColor(debugRect.color);
            shapeRenderer.rect(debugRect.x + x, debugRect.y + y, debugRect.width, debugRect.height);
        }
    }

    public Skin getSkin() {
        return this.skin;
    }
}
