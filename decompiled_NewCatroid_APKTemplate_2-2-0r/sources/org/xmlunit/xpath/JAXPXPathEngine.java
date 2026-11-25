package org.xmlunit.xpath;

import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlunit.ConfigurationException;
import org.xmlunit.XMLUnitException;
import org.xmlunit.util.Convert;
import org.xmlunit.util.IterableNodeList;

/* JADX INFO: loaded from: classes4.dex */
public class JAXPXPathEngine implements XPathEngine {
    private final XPath xpath;

    public JAXPXPathEngine(XPathFactory xPathFactory) {
        try {
            this.xpath = xPathFactory.newXPath();
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    public JAXPXPathEngine() {
        this(XPathFactory.newInstance());
    }

    @Override // org.xmlunit.xpath.XPathEngine
    public Iterable<Node> selectNodes(String str, Source source) {
        try {
            return new IterableNodeList((NodeList) this.xpath.evaluate(str, Convert.toInputSource(source), XPathConstants.NODESET));
        } catch (XPathExpressionException e) {
            throw new XMLUnitException(e);
        }
    }

    @Override // org.xmlunit.xpath.XPathEngine
    public String evaluate(String str, Source source) {
        try {
            return this.xpath.evaluate(str, Convert.toInputSource(source));
        } catch (XPathExpressionException e) {
            throw new XMLUnitException(e);
        }
    }

    @Override // org.xmlunit.xpath.XPathEngine
    public Iterable<Node> selectNodes(String str, Node node) {
        try {
            return new IterableNodeList((NodeList) this.xpath.evaluate(str, node, XPathConstants.NODESET));
        } catch (XPathExpressionException e) {
            throw new XMLUnitException(e);
        }
    }

    @Override // org.xmlunit.xpath.XPathEngine
    public String evaluate(String str, Node node) {
        try {
            return this.xpath.evaluate(str, node);
        } catch (XPathExpressionException e) {
            throw new XMLUnitException(e);
        }
    }

    @Override // org.xmlunit.xpath.XPathEngine
    public void setNamespaceContext(Map<String, String> map) {
        this.xpath.setNamespaceContext(Convert.toNamespaceContext(map));
    }
}
