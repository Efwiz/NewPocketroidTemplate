package org.apache.xerces.impl.xs.util;

import java.util.ArrayList;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.XSModelImpl;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xs.XSModel;

/* JADX INFO: loaded from: classes13.dex */
public class XSGrammarPool extends XMLGrammarPoolImpl {
    public XSModel toXSModel() {
        return toXSModel((short) 1);
    }

    public XSModel toXSModel(short s) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.fGrammars.length; i++) {
            for (XMLGrammarPoolImpl.Entry entry = this.fGrammars[i]; entry != null; entry = entry.next) {
                if (entry.desc.getGrammarType().equals("http://www.w3.org/2001/XMLSchema")) {
                    arrayList.add(entry.grammar);
                }
            }
        }
        int size = arrayList.size();
        return size == 0 ? toXSModel(new SchemaGrammar[0], s) : toXSModel((SchemaGrammar[]) arrayList.toArray(new SchemaGrammar[size]), s);
    }

    protected XSModel toXSModel(SchemaGrammar[] schemaGrammarArr, short s) {
        return new XSModelImpl(schemaGrammarArr, s);
    }
}
