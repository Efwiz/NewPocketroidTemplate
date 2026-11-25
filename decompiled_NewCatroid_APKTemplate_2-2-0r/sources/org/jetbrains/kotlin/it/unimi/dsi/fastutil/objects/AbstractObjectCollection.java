package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.AbstractCollection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObjectCollection<K> extends AbstractCollection<K> implements ObjectCollection<K> {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
    public abstract ObjectIterator<K> iterator();

    protected AbstractObjectCollection() {
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator<K> it = iterator();
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size != 0) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                K next = it.next();
                if (this == next) {
                    sb.append("(this collection)");
                } else {
                    sb.append(String.valueOf(next));
                }
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
