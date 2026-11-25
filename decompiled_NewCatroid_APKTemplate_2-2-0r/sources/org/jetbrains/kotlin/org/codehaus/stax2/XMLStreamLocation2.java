package org.jetbrains.kotlin.org.codehaus.stax2;

import javax.xml.stream.Location;

/* JADX INFO: loaded from: classes4.dex */
public interface XMLStreamLocation2 extends Location {
    public static final XMLStreamLocation2 NOT_AVAILABLE = new XMLStreamLocation2() { // from class: org.jetbrains.kotlin.org.codehaus.stax2.XMLStreamLocation2.1
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
    };
}
