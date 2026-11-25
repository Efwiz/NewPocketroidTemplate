package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.coder.XmlSanitizer;
import com.reandroid.utils.collection.ArrayCollection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ParserEventList implements Iterator<ParserEvent> {
    private int index;
    private ParserEvent mCurrent;
    private int type = -1;
    private final List<ParserEvent> eventList = new ArrayCollection();

    public void clear() {
        this.eventList.clear();
        reset();
    }

    public int getType() {
        return this.type;
    }

    public String getText() {
        int i = this.type;
        if (i == 9) {
            return this.mCurrent.getComment();
        }
        if (i == 2 || i == 3) {
            return getElement().getName();
        }
        if (i != 4) {
            return null;
        }
        String text = ((ResXmlTextNode) getXmlNode()).getText();
        if (text == null) {
            text = "";
        }
        return XmlSanitizer.escapeSpecialCharacter(text);
    }

    public int getLineNumber() {
        int i;
        int i2 = this.type;
        if (i2 != 9 && i2 != 4 && i2 != 2 && i2 != 3) {
            return -1;
        }
        ResXmlNode xmlNode = getXmlNode();
        if (this.mCurrent.isEndComment() || (i = this.type) == 3) {
            return ((ResXmlElement) xmlNode).getEndLineNumber();
        }
        if (i == 4) {
            return ((ResXmlTextNode) xmlNode).getLineNumber();
        }
        return ((ResXmlElement) xmlNode).getStartLineNumber();
    }

    public ResXmlNode getXmlNode() {
        return this.mCurrent.getXmlNode();
    }

    public ResXmlElement getElement() {
        return (ResXmlElement) this.mCurrent.getXmlNode();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public ParserEvent next() {
        if (!hasNext()) {
            return null;
        }
        ParserEvent parserEvent = get(this.index);
        this.index++;
        this.mCurrent = parserEvent;
        this.type = parserEvent.getEvent();
        return parserEvent;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.index < size();
    }

    public int size() {
        return this.eventList.size();
    }

    public int getIndex() {
        return this.index;
    }

    public void reset() {
        this.index = 0;
        this.mCurrent = null;
        this.type = -1;
    }

    void add(ParserEvent parserEvent) {
        if (parserEvent == null) {
            return;
        }
        this.eventList.add(parserEvent);
    }

    private ParserEvent get(int i) {
        return this.eventList.get(i);
    }
}
