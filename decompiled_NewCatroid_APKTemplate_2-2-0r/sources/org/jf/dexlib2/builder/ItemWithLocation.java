package org.jf.dexlib2.builder;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ItemWithLocation {
    MethodLocation location;

    public boolean isPlaced() {
        return this.location != null;
    }

    public void setLocation(MethodLocation methodLocation) {
        this.location = methodLocation;
    }
}
