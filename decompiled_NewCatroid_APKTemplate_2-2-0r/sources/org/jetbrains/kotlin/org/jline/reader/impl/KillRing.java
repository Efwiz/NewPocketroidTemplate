package org.jetbrains.kotlin.org.jline.reader.impl;

/* JADX INFO: loaded from: classes4.dex */
public final class KillRing {
    private int head;
    private boolean lastKill;
    private boolean lastYank;
    private final String[] slots;

    public KillRing(int i) {
        this.head = 0;
        this.lastKill = false;
        this.lastYank = false;
        this.slots = new String[i];
    }

    public KillRing() {
        this(60);
    }

    public void resetLastYank() {
        this.lastYank = false;
    }

    public void resetLastKill() {
        this.lastKill = false;
    }

    public boolean lastYank() {
        return this.lastYank;
    }

    public void add(String str) {
        this.lastYank = false;
        if (this.lastKill && this.slots[this.head] != null) {
            StringBuilder sb = new StringBuilder();
            String[] strArr = this.slots;
            int i = this.head;
            strArr[i] = sb.append(strArr[i]).append(str).toString();
            return;
        }
        this.lastKill = true;
        next();
        this.slots[this.head] = str;
    }

    public void addBackwards(String str) {
        this.lastYank = false;
        if (this.lastKill) {
            String[] strArr = this.slots;
            int i = this.head;
            if (strArr[i] != null) {
                strArr[i] = str + this.slots[this.head];
                return;
            }
        }
        this.lastKill = true;
        next();
        this.slots[this.head] = str;
    }

    public String yank() {
        this.lastKill = false;
        this.lastYank = true;
        return this.slots[this.head];
    }

    public String yankPop() {
        this.lastKill = false;
        if (!this.lastYank) {
            return null;
        }
        prev();
        return this.slots[this.head];
    }

    private void next() {
        int i = this.head;
        if (i == 0 && this.slots[0] == null) {
            return;
        }
        int i2 = i + 1;
        this.head = i2;
        if (i2 == this.slots.length) {
            this.head = 0;
        }
    }

    private void prev() {
        int i = this.head - 1;
        this.head = i;
        if (i == -1) {
            int length = this.slots.length - 1;
            while (length >= 0 && this.slots[length] == null) {
                length--;
            }
            this.head = length;
        }
    }
}
