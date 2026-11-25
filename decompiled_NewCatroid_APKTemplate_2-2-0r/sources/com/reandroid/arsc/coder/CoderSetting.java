package com.reandroid.arsc.coder;

import com.reandroid.arsc.coder.xml.AaptXmlStringDecoder;
import com.reandroid.arsc.coder.xml.RawXmlStringDecoder;
import com.reandroid.arsc.coder.xml.XmlCoderLogger;
import com.reandroid.arsc.coder.xml.XmlStringDecoder;

/* JADX INFO: loaded from: classes10.dex */
public class CoderSetting {
    private XmlCoderLogger logger;
    private XmlStringDecoder stringDecoder = new RawXmlStringDecoder();

    public XmlStringDecoder getStringDecoder() {
        return this.stringDecoder;
    }

    public void setStringDecoder(XmlStringDecoder xmlStringDecoder) {
        if (xmlStringDecoder == null) {
            throw new NullPointerException("Null XmlStringDecoder");
        }
        this.stringDecoder = xmlStringDecoder;
    }

    public boolean isAapt() {
        return this.stringDecoder instanceof AaptXmlStringDecoder;
    }

    public XmlCoderLogger getLogger() {
        return this.logger;
    }

    public void setLogger(XmlCoderLogger xmlCoderLogger) {
        this.logger = xmlCoderLogger;
    }
}
