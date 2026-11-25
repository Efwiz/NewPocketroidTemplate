package org.apache.commons.collections;

import java.util.Collection;
import java.util.HashMap;

/* JADX INFO: loaded from: classes12.dex */
public class HashBag extends DefaultMapBag implements Bag {
    public HashBag() {
        super(new HashMap());
    }

    public HashBag(Collection collection) {
        this();
        addAll(collection);
    }
}
