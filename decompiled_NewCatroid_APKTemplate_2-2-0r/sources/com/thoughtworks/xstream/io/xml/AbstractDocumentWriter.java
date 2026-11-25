package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.util.ArrayList;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractDocumentWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractDocumentWriter extends AbstractXmlWriter implements DocumentWriter {
    private final FastStack nodeStack;
    private final List result;

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void close() {
    }

    protected abstract Object createNode(String str);

    public void endNodeInternally() {
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void flush() {
    }

    public AbstractDocumentWriter(Object obj, NameCoder nameCoder) {
        super(nameCoder);
        ArrayList arrayList = new ArrayList();
        this.result = arrayList;
        FastStack fastStack = new FastStack(16);
        this.nodeStack = fastStack;
        if (obj != null) {
            fastStack.push(obj);
            arrayList.add(obj);
        }
    }

    public AbstractDocumentWriter(Object obj, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(obj, (NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public final void startNode(String str) {
        this.nodeStack.push(createNode(str));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public final void endNode() {
        endNodeInternally();
        Object objPop = this.nodeStack.pop();
        if (this.nodeStack.size() == 0) {
            this.result.add(objPop);
        }
    }

    protected final Object getCurrent() {
        return this.nodeStack.peek();
    }

    @Override // com.thoughtworks.xstream.io.xml.DocumentWriter
    public List getTopLevelNodes() {
        return this.result;
    }
}
