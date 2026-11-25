package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.io.AttributeNameIterator;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractPullReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractPullReader extends AbstractXmlReader {
    protected static final int COMMENT = 4;
    protected static final int END_NODE = 2;
    protected static final int OTHER = 0;
    protected static final int START_NODE = 1;
    protected static final int TEXT = 3;
    private final FastStack elementStack;
    private final FastStack lookahead;
    private final FastStack lookback;
    private boolean marked;
    private final FastStack pool;

    protected abstract String pullElementName();

    protected abstract int pullNextEvent();

    protected abstract String pullText();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractPullReader$Event.class
      classes11.dex
     */
    private static class Event {
        int type;
        String value;

        private Event() {
        }
    }

    protected AbstractPullReader(NameCoder nameCoder) {
        super(nameCoder);
        this.elementStack = new FastStack(16);
        this.pool = new FastStack(16);
        this.lookahead = new FastStack(4);
        this.lookback = new FastStack(4);
    }

    protected AbstractPullReader(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public boolean hasMoreChildren() {
        int i;
        mark();
        do {
            i = readEvent().type;
            if (i == 1) {
                reset();
                return true;
            }
        } while (i != 2);
        reset();
        return false;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void moveDown() {
        int size = this.elementStack.size();
        while (this.elementStack.size() <= size) {
            move();
            if (this.elementStack.size() < size) {
                throw new RuntimeException();
            }
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void moveUp() {
        int size = this.elementStack.size();
        while (this.elementStack.size() >= size) {
            move();
        }
    }

    private void move() {
        Event event = readEvent();
        this.pool.push(event);
        int i = event.type;
        if (i == 1) {
            this.elementStack.push(pullElementName());
        } else {
            if (i != 2) {
                return;
            }
            this.elementStack.pop();
        }
    }

    private Event readEvent() {
        if (this.marked) {
            if (this.lookback.hasStuff()) {
                return (Event) this.lookahead.push(this.lookback.pop());
            }
            return (Event) this.lookahead.push(readRealEvent());
        }
        if (this.lookback.hasStuff()) {
            return (Event) this.lookback.pop();
        }
        return readRealEvent();
    }

    private Event readRealEvent() {
        Event event = this.pool.hasStuff() ? (Event) this.pool.pop() : new Event();
        event.type = pullNextEvent();
        if (event.type == 3) {
            event.value = pullText();
        } else if (event.type == 1) {
            event.value = pullElementName();
        } else {
            event.value = null;
        }
        return event;
    }

    public void mark() {
        this.marked = true;
    }

    public void reset() {
        while (this.lookahead.hasStuff()) {
            this.lookback.push(this.lookahead.pop());
        }
        this.marked = false;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getValue() {
        mark();
        Event event = readEvent();
        StringBuffer stringBuffer = null;
        String str = null;
        while (true) {
            if (event.type == 3) {
                String str2 = event.value;
                if (str2 != null && str2.length() > 0) {
                    if (str == null) {
                        str = str2;
                    } else {
                        if (stringBuffer == null) {
                            stringBuffer = new StringBuffer(str);
                        }
                        stringBuffer.append(str2);
                    }
                }
            } else if (event.type != 4) {
                break;
            }
            event = readEvent();
        }
        reset();
        if (stringBuffer != null) {
            return stringBuffer.toString();
        }
        return str == null ? "" : str;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public Iterator getAttributeNames() {
        return new AttributeNameIterator(this);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getNodeName() {
        return unescapeXmlName((String) this.elementStack.peek());
    }

    @Override // com.thoughtworks.xstream.io.AbstractReader, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamReader
    public String peekNextChild() {
        int i;
        mark();
        do {
            Event event = readEvent();
            i = event.type;
            if (i == 1) {
                reset();
                return event.value;
            }
        } while (i != 2);
        reset();
        return null;
    }
}
