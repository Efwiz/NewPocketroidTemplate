package org.xmlunit.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/* JADX INFO: loaded from: classes3.dex */
public final class Nodes {
    private static final char SPACE = ' ';

    private Nodes() {
    }

    public static QName getQName(Node node) {
        String localName = node.getLocalName();
        String prefix = node.getPrefix();
        if (localName != null) {
            String namespaceURI = node.getNamespaceURI();
            if (prefix == null) {
                prefix = "";
            }
            return new QName(namespaceURI, localName, prefix);
        }
        return new QName(node.getNodeName());
    }

    public static String getMergedNestedText(Node node) {
        String nodeValue;
        StringBuilder sb = new StringBuilder();
        for (Node node2 : new IterableNodeList(node.getChildNodes())) {
            if ((node2 instanceof Text) && (nodeValue = node2.getNodeValue()) != null) {
                sb.append(nodeValue);
            }
        }
        return sb.toString();
    }

    public static Map<QName, String> getAttributes(Node node) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            int length = attributes.getLength();
            for (int i = 0; i < length; i++) {
                Attr attr = (Attr) attributes.item(i);
                linkedHashMap.put(getQName(attr), attr.getValue());
            }
        }
        return linkedHashMap;
    }

    public static Node stripWhitespace(Node node) {
        Node nodeCloneNode = node.cloneNode(true);
        nodeCloneNode.normalize();
        handleWsRec(nodeCloneNode, false);
        return nodeCloneNode;
    }

    public static Node normalizeWhitespace(Node node) {
        Node nodeCloneNode = node.cloneNode(true);
        nodeCloneNode.normalize();
        handleWsRec(nodeCloneNode, true);
        return nodeCloneNode;
    }

    public static Node stripElementContentWhitespace(Node node) {
        Node nodeCloneNode = node.cloneNode(true);
        nodeCloneNode.normalize();
        stripECW(nodeCloneNode);
        return nodeCloneNode;
    }

    private static void handleWsRec(Node node, boolean z) {
        if ((node instanceof CharacterData) || (node instanceof ProcessingInstruction)) {
            String strTrim = node.getNodeValue().trim();
            if (z) {
                strTrim = normalize(strTrim);
            }
            node.setNodeValue(strTrim);
        }
        LinkedList linkedList = new LinkedList();
        for (Node node2 : new IterableNodeList(node.getChildNodes())) {
            handleWsRec(node2, z);
            if (!(node instanceof Attr) && (node2 instanceof Text) && node2.getNodeValue().length() == 0) {
                linkedList.add(node2);
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            node.removeChild((Node) it.next());
        }
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            int length = attributes.getLength();
            for (int i = 0; i < length; i++) {
                handleWsRec(attributes.item(i), z);
            }
        }
    }

    static String normalize(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (Character.isWhitespace(cCharAt)) {
                if (z2) {
                    z = true;
                } else {
                    sb.append(' ');
                    z |= cCharAt != ' ';
                }
                z2 = true;
            } else {
                sb.append(cCharAt);
                z2 = false;
            }
        }
        return z ? sb.toString() : str;
    }

    private static void stripECW(Node node) {
        LinkedList linkedList = new LinkedList();
        for (Node node2 : new IterableNodeList(node.getChildNodes())) {
            stripECW(node2);
            if (!(node instanceof Attr) && (node2 instanceof Text) && node2.getNodeValue().trim().length() == 0) {
                linkedList.add(node2);
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            node.removeChild((Node) it.next());
        }
    }
}
