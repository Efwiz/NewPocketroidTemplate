package org.apache.xerces.impl.dtd;

import java.util.Hashtable;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;

/* JADX INFO: loaded from: classes13.dex */
public class DTDGrammarBucket {
    protected DTDGrammar fActiveGrammar;
    protected final Hashtable fGrammars = new Hashtable();
    protected boolean fIsStandalone;

    public void clear() {
        this.fGrammars.clear();
        this.fActiveGrammar = null;
        this.fIsStandalone = false;
    }

    DTDGrammar getActiveGrammar() {
        return this.fActiveGrammar;
    }

    public DTDGrammar getGrammar(XMLGrammarDescription xMLGrammarDescription) {
        return (DTDGrammar) this.fGrammars.get((XMLDTDDescription) xMLGrammarDescription);
    }

    boolean getStandalone() {
        return this.fIsStandalone;
    }

    public void putGrammar(DTDGrammar dTDGrammar) {
        this.fGrammars.put((XMLDTDDescription) dTDGrammar.getGrammarDescription(), dTDGrammar);
    }

    void setActiveGrammar(DTDGrammar dTDGrammar) {
        this.fActiveGrammar = dTDGrammar;
    }

    void setStandalone(boolean z) {
        this.fIsStandalone = z;
    }
}
