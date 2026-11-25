package org.apache.xerces.xni.parser;

import java.io.IOException;
import org.apache.xerces.xni.XNIException;

/* JADX INFO: loaded from: classes13.dex */
public interface XMLPullParserConfiguration extends XMLParserConfiguration {
    void cleanup();

    boolean parse(boolean z) throws IOException, XNIException;

    void setInputSource(XMLInputSource xMLInputSource) throws XMLConfigurationException, IOException;
}
