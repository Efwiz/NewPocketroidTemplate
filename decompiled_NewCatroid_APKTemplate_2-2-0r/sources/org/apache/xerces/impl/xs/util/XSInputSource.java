package org.apache.xerces.impl.xs.util;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xs.XSObject;

/* JADX INFO: loaded from: classes13.dex */
public final class XSInputSource extends XMLInputSource {
    private XSObject[] fComponents;
    private SchemaGrammar[] fGrammars;

    public XSInputSource(SchemaGrammar[] schemaGrammarArr) {
        super(null, null, null);
        this.fGrammars = schemaGrammarArr;
        this.fComponents = null;
    }

    public XSInputSource(XSObject[] xSObjectArr) {
        super(null, null, null);
        this.fGrammars = null;
        this.fComponents = xSObjectArr;
    }

    public XSObject[] getComponents() {
        return this.fComponents;
    }

    public SchemaGrammar[] getGrammars() {
        return this.fGrammars;
    }

    public void setComponents(XSObject[] xSObjectArr) {
        this.fComponents = xSObjectArr;
    }

    public void setGrammars(SchemaGrammar[] schemaGrammarArr) {
        this.fGrammars = schemaGrammarArr;
    }
}
