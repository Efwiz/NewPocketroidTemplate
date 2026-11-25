package org.jf.dexlib2.builder;

/* JADX INFO: loaded from: classes6.dex */
public class Label extends ItemWithLocation {
    Label() {
    }

    Label(MethodLocation methodLocation) {
        this.location = methodLocation;
    }

    public int getCodeAddress() {
        return getLocation().getCodeAddress();
    }

    public MethodLocation getLocation() {
        if (this.location == null) {
            throw new IllegalStateException("Cannot get the location of a label that hasn't been placed yet.");
        }
        return this.location;
    }
}
