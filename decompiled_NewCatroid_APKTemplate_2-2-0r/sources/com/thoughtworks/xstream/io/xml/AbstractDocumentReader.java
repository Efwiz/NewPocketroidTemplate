package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.io.AttributeNameIterator;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractDocumentReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractDocumentReader extends AbstractXmlReader implements DocumentReader {
    private Object current;
    private FastStack pointers;

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader, com.thoughtworks.xstream.converters.ErrorReporter
    public void appendErrors(ErrorWriter errorWriter) {
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void close() {
    }

    protected abstract Object getChild(int i);

    protected abstract int getChildCount();

    protected abstract Object getParent();

    protected abstract void reassignCurrentElement(Object obj);

    protected AbstractDocumentReader(Object obj) {
        this(obj, new XmlFriendlyNameCoder());
    }

    protected AbstractDocumentReader(Object obj, NameCoder nameCoder) {
        super(nameCoder);
        FastStack fastStack = new FastStack(16);
        this.pointers = fastStack;
        this.current = obj;
        fastStack.push(new Pointer());
        reassignCurrentElement(this.current);
    }

    protected AbstractDocumentReader(Object obj, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(obj, (NameCoder) xmlFriendlyReplacer);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractDocumentReader$Pointer.class
      classes11.dex
     */
    private static class Pointer {
        public int v;

        private Pointer() {
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public boolean hasMoreChildren() {
        return ((Pointer) this.pointers.peek()).v < getChildCount();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void moveUp() {
        this.current = getParent();
        this.pointers.popSilently();
        reassignCurrentElement(this.current);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void moveDown() {
        Pointer pointer = (Pointer) this.pointers.peek();
        this.pointers.push(new Pointer());
        this.current = getChild(pointer.v);
        pointer.v++;
        reassignCurrentElement(this.current);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public Iterator getAttributeNames() {
        return new AttributeNameIterator(this);
    }

    @Override // com.thoughtworks.xstream.io.xml.DocumentReader
    public Object getCurrent() {
        return this.current;
    }
}
