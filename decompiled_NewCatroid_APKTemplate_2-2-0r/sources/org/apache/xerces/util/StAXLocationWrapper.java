package org.apache.xerces.util;

import javax.xml.stream.Location;
import org.apache.xerces.xni.XMLLocator;

/* JADX INFO: loaded from: classes13.dex */
public final class StAXLocationWrapper implements XMLLocator {
    private Location fLocation = null;

    @Override // org.apache.xerces.xni.XMLLocator
    public String getBaseSystemId() {
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public int getCharacterOffset() {
        Location location = this.fLocation;
        if (location != null) {
            return location.getCharacterOffset();
        }
        return -1;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public int getColumnNumber() {
        Location location = this.fLocation;
        if (location != null) {
            return location.getColumnNumber();
        }
        return -1;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getEncoding() {
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getExpandedSystemId() {
        return getLiteralSystemId();
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public int getLineNumber() {
        Location location = this.fLocation;
        if (location != null) {
            return location.getLineNumber();
        }
        return -1;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getLiteralSystemId() {
        Location location = this.fLocation;
        if (location != null) {
            return location.getSystemId();
        }
        return null;
    }

    public Location getLocation() {
        return this.fLocation;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getPublicId() {
        Location location = this.fLocation;
        if (location != null) {
            return location.getPublicId();
        }
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getXMLVersion() {
        return null;
    }

    public void setLocation(Location location) {
        this.fLocation = location;
    }
}
