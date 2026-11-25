package com.badlogic.gdx;

import com.badlogic.gdx.utils.IntSet;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/AbstractInput.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractInput implements Input {
    protected boolean keyJustPressed;
    protected int pressedKeyCount;
    private final IntSet keysToCatch = new IntSet();
    protected final boolean[] pressedKeys = new boolean[256];
    protected final boolean[] justPressedKeys = new boolean[256];

    @Override // com.badlogic.gdx.Input
    public boolean isKeyPressed(int i) {
        if (i == -1) {
            return this.pressedKeyCount > 0;
        }
        if (i < 0 || i > 255) {
            return false;
        }
        return this.pressedKeys[i];
    }

    @Override // com.badlogic.gdx.Input
    public boolean isKeyJustPressed(int i) {
        if (i == -1) {
            return this.keyJustPressed;
        }
        if (i < 0 || i > 255) {
            return false;
        }
        return this.justPressedKeys[i];
    }

    @Override // com.badlogic.gdx.Input
    public void setCatchKey(int i, boolean z) {
        if (!z) {
            this.keysToCatch.remove(i);
        } else {
            this.keysToCatch.add(i);
        }
    }

    @Override // com.badlogic.gdx.Input
    public boolean isCatchKey(int i) {
        return this.keysToCatch.contains(i);
    }
}
