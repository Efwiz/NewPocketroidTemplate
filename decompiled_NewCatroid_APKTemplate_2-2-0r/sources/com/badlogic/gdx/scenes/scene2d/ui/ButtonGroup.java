package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ButtonGroup.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ButtonGroup<T extends Button> {
    private final Array<T> buttons;
    private Array<T> checkedButtons;
    private T lastChecked;
    private int maxCheckCount;
    private int minCheckCount;
    private boolean uncheckLast;

    public ButtonGroup() {
        this.buttons = new Array<>();
        this.checkedButtons = new Array<>(1);
        this.maxCheckCount = 1;
        this.uncheckLast = true;
        this.minCheckCount = 1;
    }

    public ButtonGroup(T... tArr) {
        this.buttons = new Array<>();
        this.checkedButtons = new Array<>(1);
        this.maxCheckCount = 1;
        this.uncheckLast = true;
        this.minCheckCount = 0;
        add(tArr);
        this.minCheckCount = 1;
    }

    public void add(T t) {
        if (t == null) {
            throw new IllegalArgumentException("button cannot be null.");
        }
        t.buttonGroup = null;
        boolean z = t.isChecked() || this.buttons.size < this.minCheckCount;
        t.setChecked(false);
        t.buttonGroup = this;
        this.buttons.add(t);
        t.setChecked(z);
    }

    public void add(T... tArr) {
        if (tArr == null) {
            throw new IllegalArgumentException("buttons cannot be null.");
        }
        for (T t : tArr) {
            add(t);
        }
    }

    public void remove(T t) {
        if (t == null) {
            throw new IllegalArgumentException("button cannot be null.");
        }
        t.buttonGroup = null;
        this.buttons.removeValue(t, true);
        this.checkedButtons.removeValue(t, true);
    }

    public void remove(T... tArr) {
        if (tArr == null) {
            throw new IllegalArgumentException("buttons cannot be null.");
        }
        for (T t : tArr) {
            remove(t);
        }
    }

    public void clear() {
        this.buttons.clear();
        this.checkedButtons.clear();
    }

    public void setChecked(String str) {
        if (str == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        int i = this.buttons.size;
        for (int i2 = 0; i2 < i; i2++) {
            T t = this.buttons.get(i2);
            if ((t instanceof TextButton) && str.contentEquals(((TextButton) t).getText())) {
                t.setChecked(true);
                return;
            }
        }
    }

    protected boolean canCheck(T t, boolean z) {
        if (t.isChecked == z) {
            return false;
        }
        if (!z) {
            if (this.checkedButtons.size <= this.minCheckCount) {
                return false;
            }
            this.checkedButtons.removeValue(t, true);
        } else {
            if (this.maxCheckCount != -1 && this.checkedButtons.size >= this.maxCheckCount) {
                if (!this.uncheckLast) {
                    return false;
                }
                int i = 0;
                while (true) {
                    int i2 = this.minCheckCount;
                    this.minCheckCount = 0;
                    this.lastChecked.setChecked(false);
                    this.minCheckCount = i2;
                    if (t.isChecked == z) {
                        return false;
                    }
                    if (this.checkedButtons.size >= this.maxCheckCount) {
                        int i3 = i + 1;
                        if (i > 10) {
                            return false;
                        }
                        i = i3;
                    }
                }
            }
            this.checkedButtons.add(t);
            this.lastChecked = t;
        }
        return true;
    }

    public void uncheckAll() {
        int i = this.minCheckCount;
        this.minCheckCount = 0;
        int i2 = this.buttons.size;
        for (int i3 = 0; i3 < i2; i3++) {
            this.buttons.get(i3).setChecked(false);
        }
        this.minCheckCount = i;
    }

    public T getChecked() {
        if (this.checkedButtons.size > 0) {
            return this.checkedButtons.get(0);
        }
        return null;
    }

    public int getCheckedIndex() {
        if (this.checkedButtons.size > 0) {
            return this.buttons.indexOf(this.checkedButtons.get(0), true);
        }
        return -1;
    }

    public Array<T> getAllChecked() {
        return this.checkedButtons;
    }

    public Array<T> getButtons() {
        return this.buttons;
    }

    public void setMinCheckCount(int i) {
        this.minCheckCount = i;
    }

    public void setMaxCheckCount(int i) {
        if (i == 0) {
            i = -1;
        }
        this.maxCheckCount = i;
    }

    public void setUncheckLast(boolean z) {
        this.uncheckLast = z;
    }
}
