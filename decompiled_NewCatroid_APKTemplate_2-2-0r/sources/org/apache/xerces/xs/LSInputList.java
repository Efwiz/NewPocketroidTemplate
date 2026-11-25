package org.apache.xerces.xs;

import java.util.List;
import org.w3c.dom.ls.LSInput;

/* JADX INFO: loaded from: classes13.dex */
public interface LSInputList extends List {
    int getLength();

    LSInput item(int i);
}
