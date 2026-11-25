package org.apache.xerces.stax;

import javax.xml.stream.Location;

/* JADX INFO: loaded from: classes13.dex */
public class ImmutableLocation implements Location {
    private final int fCharacterOffset;
    private final int fColumnNumber;
    private final int fLineNumber;
    private final String fPublicId;
    private final String fSystemId;

    public ImmutableLocation(int i, int i2, int i3, String str, String str2) {
        this.fCharacterOffset = i;
        this.fColumnNumber = i2;
        this.fLineNumber = i3;
        this.fPublicId = str;
        this.fSystemId = str2;
    }

    public ImmutableLocation(Location location) {
        this(location.getCharacterOffset(), location.getColumnNumber(), location.getLineNumber(), location.getPublicId(), location.getSystemId());
    }

    @Override // javax.xml.stream.Location
    public int getCharacterOffset() {
        return this.fCharacterOffset;
    }

    @Override // javax.xml.stream.Location
    public int getColumnNumber() {
        return this.fColumnNumber;
    }

    @Override // javax.xml.stream.Location
    public int getLineNumber() {
        return this.fLineNumber;
    }

    @Override // javax.xml.stream.Location
    public String getPublicId() {
        return this.fPublicId;
    }

    @Override // javax.xml.stream.Location
    public String getSystemId() {
        return this.fSystemId;
    }
}
