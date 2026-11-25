package org.jf.dexlib2.builder;

/* JADX INFO: loaded from: classes6.dex */
public class LocatedLabels extends LocatedItems<Label> {
    @Override // org.jf.dexlib2.builder.LocatedItems
    protected String getAddLocatedItemError() {
        return "Cannot add a label that is already placed.You must remove it from its current location first.";
    }
}
