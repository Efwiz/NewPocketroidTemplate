package org.apache.wml.dom;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.wml.WMLDocument;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
public class WMLDocumentImpl extends DocumentImpl implements WMLDocument {
    private static final Class[] _elemClassSigWML = {WMLDocumentImpl.class, String.class};
    private static Hashtable _elementTypesWML = null;
    private static final long serialVersionUID = -6582904849512384104L;

    static {
        Hashtable hashtable = new Hashtable();
        _elementTypesWML = hashtable;
        hashtable.put(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, WMLBElementImpl.class);
        _elementTypesWML.put("noop", WMLNoopElementImpl.class);
        _elementTypesWML.put("a", WMLAElementImpl.class);
        _elementTypesWML.put("setvar", WMLSetvarElementImpl.class);
        _elementTypesWML.put("access", WMLAccessElementImpl.class);
        _elementTypesWML.put("strong", WMLStrongElementImpl.class);
        _elementTypesWML.put("postfield", WMLPostfieldElementImpl.class);
        _elementTypesWML.put(PsiKeyword.DO, WMLDoElementImpl.class);
        _elementTypesWML.put("wml", WMLWmlElementImpl.class);
        _elementTypesWML.put(LanguageCodeUtil.TR, WMLTrElementImpl.class);
        _elementTypesWML.put("go", WMLGoElementImpl.class);
        _elementTypesWML.put("big", WMLBigElementImpl.class);
        _elementTypesWML.put("anchor", WMLAnchorElementImpl.class);
        _elementTypesWML.put("timer", WMLTimerElementImpl.class);
        _elementTypesWML.put("small", WMLSmallElementImpl.class);
        _elementTypesWML.put("optgroup", WMLOptgroupElementImpl.class);
        _elementTypesWML.put("head", WMLHeadElementImpl.class);
        _elementTypesWML.put("td", WMLTdElementImpl.class);
        _elementTypesWML.put("fieldset", WMLFieldsetElementImpl.class);
        _elementTypesWML.put("img", WMLImgElementImpl.class);
        _elementTypesWML.put("refresh", WMLRefreshElementImpl.class);
        _elementTypesWML.put("onevent", WMLOneventElementImpl.class);
        _elementTypesWML.put("input", WMLInputElementImpl.class);
        _elementTypesWML.put("prev", WMLPrevElementImpl.class);
        _elementTypesWML.put("table", WMLTableElementImpl.class);
        _elementTypesWML.put("meta", WMLMetaElementImpl.class);
        _elementTypesWML.put(ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, WMLTemplateElementImpl.class);
        _elementTypesWML.put(CompressorStreamFactory.BROTLI, WMLBrElementImpl.class);
        _elementTypesWML.put("option", WMLOptionElementImpl.class);
        _elementTypesWML.put("u", WMLUElementImpl.class);
        _elementTypesWML.put("p", WMLPElementImpl.class);
        _elementTypesWML.put("select", WMLSelectElementImpl.class);
        _elementTypesWML.put("em", WMLEmElementImpl.class);
        _elementTypesWML.put(ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, WMLIElementImpl.class);
        _elementTypesWML.put("card", WMLCardElementImpl.class);
    }

    public WMLDocumentImpl(DocumentType documentType) {
        super(documentType, false);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    protected boolean canRenameElements(String str, String str2, ElementImpl elementImpl) {
        return _elementTypesWML.get(str2) == _elementTypesWML.get(elementImpl.getTagName());
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public Element createElement(String str) throws DOMException {
        Class cls = (Class) _elementTypesWML.get(str);
        if (cls == null) {
            return new WMLElementImpl(this, str);
        }
        try {
            return (Element) cls.getConstructor(_elemClassSigWML).newInstance(this, str);
        } catch (Exception e) {
            e = e;
            if (e instanceof InvocationTargetException) {
                e = ((InvocationTargetException) e).getTargetException();
            }
            System.out.println("Exception " + e.getClass().getName());
            System.out.println(e.getMessage());
            throw new IllegalStateException("Tag '" + str + "' associated with an Element class that failed to construct.");
        }
    }
}
