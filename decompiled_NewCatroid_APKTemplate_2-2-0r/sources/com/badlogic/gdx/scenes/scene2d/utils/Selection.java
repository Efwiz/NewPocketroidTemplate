package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.badlogic.gdx.utils.Pools;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/Selection.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Selection<T> implements Disableable, Iterable<T> {
    private Actor actor;
    boolean isDisabled;
    T lastSelected;
    boolean multiple;
    boolean required;
    private boolean toggle;
    final OrderedSet<T> selected = new OrderedSet<>();
    private final OrderedSet<T> old = new OrderedSet<>();
    private boolean programmaticChangeEvents = true;

    protected void changed() {
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public void choose(T t) {
        if (t == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }
        if (this.isDisabled) {
            return;
        }
        snapshot();
        try {
            boolean z = true;
            if ((!this.toggle && !UIUtils.ctrl()) || !this.selected.contains(t)) {
                boolean z2 = false;
                if (!this.multiple || (!this.toggle && !UIUtils.ctrl())) {
                    if (this.selected.size == 1 && this.selected.contains(t)) {
                        return;
                    }
                    if (this.selected.size <= 0) {
                        z = false;
                    }
                    this.selected.clear(8);
                    z2 = z;
                }
                if (!this.selected.add(t) && !z2) {
                    return;
                } else {
                    this.lastSelected = t;
                }
            } else {
                if (this.required && this.selected.size == 1) {
                    return;
                }
                this.selected.remove(t);
                this.lastSelected = null;
            }
            if (fireChangeEvent()) {
                revert();
            } else {
                changed();
            }
        } finally {
            cleanup();
        }
    }

    @Deprecated
    public boolean hasItems() {
        return this.selected.size > 0;
    }

    public boolean notEmpty() {
        return this.selected.size > 0;
    }

    public boolean isEmpty() {
        return this.selected.size == 0;
    }

    public int size() {
        return this.selected.size;
    }

    public OrderedSet<T> items() {
        return this.selected;
    }

    public T first() {
        if (this.selected.size == 0) {
            return null;
        }
        return this.selected.first();
    }

    void snapshot() {
        this.old.clear(this.selected.size);
        this.old.addAll((OrderedSet) this.selected);
    }

    void revert() {
        this.selected.clear(this.old.size);
        this.selected.addAll((OrderedSet) this.old);
    }

    void cleanup() {
        this.old.clear(32);
    }

    public void set(T t) {
        if (t == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }
        if (this.selected.size == 1 && this.selected.first() == t) {
            return;
        }
        snapshot();
        this.selected.clear(8);
        this.selected.add(t);
        if (this.programmaticChangeEvents && fireChangeEvent()) {
            revert();
        } else {
            this.lastSelected = t;
            changed();
        }
        cleanup();
    }

    public void setAll(Array<T> array) {
        snapshot();
        this.lastSelected = null;
        this.selected.clear(array.size);
        int i = array.size;
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            T t = array.get(i2);
            if (t == null) {
                throw new IllegalArgumentException("item cannot be null.");
            }
            if (this.selected.add(t)) {
                z = true;
            }
        }
        if (z) {
            if (this.programmaticChangeEvents && fireChangeEvent()) {
                revert();
            } else if (array.size > 0) {
                this.lastSelected = array.peek();
                changed();
            }
        }
        cleanup();
    }

    public void add(T t) {
        if (t == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }
        if (this.selected.add(t)) {
            if (this.programmaticChangeEvents && fireChangeEvent()) {
                this.selected.remove(t);
            } else {
                this.lastSelected = t;
                changed();
            }
        }
    }

    public void addAll(Array<T> array) {
        snapshot();
        int i = array.size;
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            T t = array.get(i2);
            if (t == null) {
                throw new IllegalArgumentException("item cannot be null.");
            }
            if (this.selected.add(t)) {
                z = true;
            }
        }
        if (z) {
            if (this.programmaticChangeEvents && fireChangeEvent()) {
                revert();
            } else {
                this.lastSelected = array.peek();
                changed();
            }
        }
        cleanup();
    }

    public void remove(T t) {
        if (t == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }
        if (this.selected.remove(t)) {
            if (this.programmaticChangeEvents && fireChangeEvent()) {
                this.selected.add(t);
            } else {
                this.lastSelected = null;
                changed();
            }
        }
    }

    public void removeAll(Array<T> array) {
        snapshot();
        int i = array.size;
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            T t = array.get(i2);
            if (t == null) {
                throw new IllegalArgumentException("item cannot be null.");
            }
            if (this.selected.remove(t)) {
                z = true;
            }
        }
        if (z) {
            if (this.programmaticChangeEvents && fireChangeEvent()) {
                revert();
            } else {
                this.lastSelected = null;
                changed();
            }
        }
        cleanup();
    }

    public void clear() {
        if (this.selected.size == 0) {
            this.lastSelected = null;
            return;
        }
        snapshot();
        this.selected.clear(8);
        if (this.programmaticChangeEvents && fireChangeEvent()) {
            revert();
        } else {
            this.lastSelected = null;
            changed();
        }
        cleanup();
    }

    public boolean fireChangeEvent() {
        if (this.actor == null) {
            return false;
        }
        ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent) Pools.obtain(ChangeListener.ChangeEvent.class);
        try {
            return this.actor.fire(changeEvent);
        } finally {
            Pools.free(changeEvent);
        }
    }

    public boolean contains(T t) {
        if (t == null) {
            return false;
        }
        return this.selected.contains(t);
    }

    public T getLastSelected() {
        T t = this.lastSelected;
        if (t != null) {
            return t;
        }
        if (this.selected.size > 0) {
            return this.selected.first();
        }
        return null;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return this.selected.iterator();
    }

    public Array<T> toArray() {
        return this.selected.iterator().toArray();
    }

    public Array<T> toArray(Array<T> array) {
        return this.selected.iterator().toArray(array);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public void setDisabled(boolean z) {
        this.isDisabled = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public boolean isDisabled() {
        return this.isDisabled;
    }

    public boolean getToggle() {
        return this.toggle;
    }

    public void setToggle(boolean z) {
        this.toggle = z;
    }

    public boolean getMultiple() {
        return this.multiple;
    }

    public void setMultiple(boolean z) {
        this.multiple = z;
    }

    public boolean getRequired() {
        return this.required;
    }

    public void setRequired(boolean z) {
        this.required = z;
    }

    public void setProgrammaticChangeEvents(boolean z) {
        this.programmaticChangeEvents = z;
    }

    public String toString() {
        return this.selected.toString();
    }
}
