package org.jetbrains.kotlin.com.fasterxml.aalto.impl;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;

/* JADX INFO: loaded from: classes3.dex */
public class StreamExceptionBase extends XMLStreamException {
    final String mMsg;

    public StreamExceptionBase(String str) {
        super(str);
        this.mMsg = str;
    }

    public StreamExceptionBase(Throwable th) {
        super(th.getMessage(), th);
        this.mMsg = th.getMessage();
        if (getCause() == null) {
            initCause(th);
        }
    }

    public StreamExceptionBase(String str, Location location) {
        super(str, location);
        this.mMsg = str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String locationDesc = getLocationDesc();
        if (locationDesc == null) {
            return super.getMessage();
        }
        StringBuilder sb = new StringBuilder(this.mMsg.length() + locationDesc.length() + 20);
        sb.append(this.mMsg);
        sb.append("\n at ");
        sb.append(locationDesc);
        return sb.toString();
    }

    @Override // java.lang.Throwable
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }

    protected String getLocationDesc() {
        Location location = getLocation();
        if (location == null) {
            return null;
        }
        return location.toString();
    }
}
