package org.apache.xerces.xs.datatypes;

import java.util.List;
import org.apache.xerces.xs.XSException;

/* JADX INFO: loaded from: classes13.dex */
public interface ByteList extends List {
    boolean contains(byte b);

    int getLength();

    byte item(int i) throws XSException;

    byte[] toByteArray();
}
