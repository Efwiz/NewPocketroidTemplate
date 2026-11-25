package org.apache.xerces.stax;

import javax.xml.stream.Location;

/* JADX INFO: loaded from: classes13.dex */
public final class EmptyLocation implements Location {
    private static final EmptyLocation EMPTY_LOCATION_INSTANCE = new EmptyLocation();

    private EmptyLocation() {
    }

    public static EmptyLocation getInstance() {
        return EMPTY_LOCATION_INSTANCE;
    }

    @Override // javax.xml.stream.Location
    public int getCharacterOffset() {
        return -1;
    }

    @Override // javax.xml.stream.Location
    public int getColumnNumber() {
        return -1;
    }

    @Override // javax.xml.stream.Location
    public int getLineNumber() {
        return -1;
    }

    @Override // javax.xml.stream.Location
    public String getPublicId() {
        return null;
    }

    @Override // javax.xml.stream.Location
    public String getSystemId() {
        return null;
    }
}
