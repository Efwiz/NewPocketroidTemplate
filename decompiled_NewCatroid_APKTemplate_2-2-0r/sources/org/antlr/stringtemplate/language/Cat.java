package org.antlr.stringtemplate.language;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class Cat extends AbstractList {
    protected List elements = new ArrayList();

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator iterator() {
        return super.iterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        return this.elements.get(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.elements.size();
    }

    public Cat(List list) {
        for (int i = 0; i < list.size(); i++) {
            Object objConvertAnythingIteratableToIterator = ASTExpr.convertAnythingIteratableToIterator(list.get(i));
            if (objConvertAnythingIteratableToIterator instanceof Iterator) {
                Iterator it = (Iterator) objConvertAnythingIteratableToIterator;
                while (it.hasNext()) {
                    this.elements.add(it.next());
                }
            } else {
                this.elements.add(objConvertAnythingIteratableToIterator);
            }
        }
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.elements.size(); i++) {
            stringBuffer.append(this.elements.get(i));
        }
        return stringBuffer.toString();
    }
}
