package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.container.FixedBlockContainer;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.xml.XMLNode;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ResXmlNode extends FixedBlockContainer implements JSONConvert<JSONObject> {
    public static final String NAME_node_type = "node_type";

    abstract void addEvents(ParserEventList parserEventList);

    abstract int autoSetLineNumber(int i);

    public abstract int getDepth();

    abstract void linkStringReferences();

    abstract void onRemoved();

    public abstract void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException;

    public abstract void serialize(XmlSerializer xmlSerializer, boolean z) throws IOException;

    public abstract XMLNode toXml(boolean z);

    ResXmlNode(int i) {
        super(i);
    }

    public void autoSetLineNumber() {
        autoSetLineNumber(1);
    }

    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        serialize(xmlSerializer, true);
    }
}
