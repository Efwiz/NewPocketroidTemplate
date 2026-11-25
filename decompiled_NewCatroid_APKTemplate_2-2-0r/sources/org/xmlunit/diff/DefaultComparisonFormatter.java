package org.xmlunit.diff;

import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import io.netty.util.internal.StringUtil;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.catrobat.catroid.BuildConfig;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xmlunit.util.TransformerFactoryConfigurer;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultComparisonFormatter implements ComparisonFormatter {
    private TransformerFactory factory;

    @Override // org.xmlunit.diff.ComparisonFormatter
    public String getDescription(Comparison comparison) {
        ComparisonType type = comparison.getType();
        String description = type.getDescription();
        Comparison.Detail controlDetails = comparison.getControlDetails();
        Comparison.Detail testDetails = comparison.getTestDetails();
        String shortString = getShortString(controlDetails.getTarget(), controlDetails.getXPath(), type);
        String shortString2 = getShortString(testDetails.getTarget(), testDetails.getXPath(), type);
        if (type == ComparisonType.ATTR_NAME_LOOKUP) {
            return String.format("Expected %s '%s' - comparing %s to %s", description, controlDetails.getXPath(), shortString, shortString2);
        }
        return String.format("Expected %s '%s' but was '%s' - comparing %s to %s", description, getValue(controlDetails.getValue(), type), getValue(testDetails.getValue(), type), shortString, shortString2);
    }

    public void setTransformerFactory(TransformerFactory transformerFactory) {
        this.factory = transformerFactory;
    }

    protected Object getValue(Object obj, ComparisonType comparisonType) {
        return comparisonType == ComparisonType.NODE_TYPE ? nodeType(((Short) obj).shortValue()) : obj;
    }

    protected String getShortString(Node node, String str, ComparisonType comparisonType) {
        StringBuilder sb = new StringBuilder();
        if (comparisonType == ComparisonType.HAS_DOCTYPE_DECLARATION) {
            Document document = (Document) node;
            appendDocumentType(sb, document.getDoctype());
            appendDocumentElementIndication(sb, document);
        } else if (node instanceof Document) {
            Document document2 = (Document) node;
            appendDocumentXmlDeclaration(sb, document2);
            appendDocumentElementIndication(sb, document2);
        } else if (node instanceof DocumentType) {
            DocumentType documentType = (DocumentType) node;
            appendDocumentType(sb, documentType);
            appendDocumentElementIndication(sb, documentType.getOwnerDocument());
        } else if (node instanceof Attr) {
            appendAttribute(sb, (Attr) node);
        } else if (node instanceof Element) {
            appendElement(sb, (Element) node);
        } else if (node instanceof Text) {
            appendText(sb, (Text) node);
        } else if (node instanceof Comment) {
            appendComment(sb, (Comment) node);
        } else if (node instanceof ProcessingInstruction) {
            appendProcessingInstruction(sb, (ProcessingInstruction) node);
        } else if (node == null) {
            sb.append("<NULL>");
        } else {
            sb.append("<!--NodeType ").append((int) node.getNodeType()).append(StringUtil.SPACE).append(node.getNodeName()).append('/').append(node.getNodeValue()).append("-->");
        }
        appendXPath(sb, str);
        return sb.toString();
    }

    protected void appendXPath(StringBuilder sb, String str) {
        if (str == null || str.length() <= 0) {
            return;
        }
        sb.append(" at ").append(str);
    }

    protected boolean appendDocumentXmlDeclaration(StringBuilder sb, Document document) {
        if (BuildConfig.VERSION_NAME.equals(document.getXmlVersion()) && document.getXmlEncoding() == null && !document.getXmlStandalone()) {
            return false;
        }
        sb.append("<?xml version=\"");
        sb.append(document.getXmlVersion());
        sb.append("\"");
        if (document.getXmlEncoding() != null) {
            sb.append(" encoding=\"");
            sb.append(document.getXmlEncoding());
            sb.append("\"");
        }
        if (document.getXmlStandalone()) {
            sb.append(" standalone=\"yes\"");
        }
        sb.append("?>");
        return true;
    }

    protected void appendDocumentElementIndication(StringBuilder sb, Document document) {
        sb.append("<");
        sb.append(document.getDocumentElement().getNodeName());
        sb.append("...>");
    }

    protected boolean appendDocumentType(StringBuilder sb, DocumentType documentType) {
        boolean z = false;
        if (documentType == null) {
            return false;
        }
        sb.append("<!DOCTYPE ").append(documentType.getName());
        if (documentType.getPublicId() == null || documentType.getPublicId().length() <= 0) {
            z = true;
        } else {
            sb.append(" PUBLIC \"").append(documentType.getPublicId()).append('\"');
        }
        if (documentType.getSystemId() != null && documentType.getSystemId().length() > 0) {
            if (z) {
                sb.append(" SYSTEM");
            }
            sb.append(" \"").append(documentType.getSystemId()).append("\"");
        }
        sb.append(">");
        return true;
    }

    protected void appendProcessingInstruction(StringBuilder sb, ProcessingInstruction processingInstruction) {
        sb.append("<?").append(processingInstruction.getTarget()).append(StringUtil.SPACE).append(processingInstruction.getData()).append("?>");
    }

    protected void appendComment(StringBuilder sb, Comment comment) {
        sb.append("<!--").append(comment.getNodeValue()).append("-->");
    }

    protected void appendText(StringBuilder sb, Text text) {
        sb.append("<").append(text.getParentNode().getNodeName()).append(" ...>");
        if (text instanceof CDATASection) {
            sb.append("<![CDATA[").append(text.getNodeValue()).append("]]>");
        } else {
            sb.append(text.getNodeValue());
        }
        sb.append("</").append(text.getParentNode().getNodeName()).append(">");
    }

    protected void appendElement(StringBuilder sb, Element element) {
        sb.append("<").append(element.getNodeName()).append("...").append(">");
    }

    protected void appendAttribute(StringBuilder sb, Attr attr) {
        sb.append("<").append(attr.getOwnerElement().getNodeName());
        sb.append(StringUtil.SPACE).append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\"...>");
    }

    @Override // org.xmlunit.diff.ComparisonFormatter
    public String getDetails(Comparison.Detail detail, ComparisonType comparisonType, boolean z) {
        if (detail.getTarget() == null) {
            return "<NULL>";
        }
        return getFullFormattedXml(detail.getTarget(), comparisonType, z);
    }

    protected String getFullFormattedXml(Node node, ComparisonType comparisonType, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (comparisonType == ComparisonType.CHILD_NODELIST_SEQUENCE) {
            node = node.getParentNode();
        } else {
            if (node instanceof Document) {
                appendFullDocumentHeader(sb, (Document) node);
                return sb.toString();
            }
            if (node instanceof DocumentType) {
                appendFullDocumentHeader(sb, node.getOwnerDocument());
                return sb.toString();
            }
            if (node instanceof Attr) {
                node = ((Attr) node).getOwnerElement();
            } else if (node instanceof CharacterData) {
                node = node.getParentNode();
            }
        }
        sb.append(getFormattedNodeXml(node, z));
        return sb.toString().trim();
    }

    protected void appendFullDocumentHeader(StringBuilder sb, Document document) {
        if (appendDocumentXmlDeclaration(sb, document)) {
            sb.append("\n");
        }
        if (appendDocumentType(sb, document.getDoctype())) {
            sb.append("\n");
        }
        appendOnlyElementStartTagWithAttributes(sb, document.getDocumentElement());
    }

    private void appendOnlyElementStartTagWithAttributes(StringBuilder sb, Element element) {
        sb.append("<");
        sb.append(element.getNodeName());
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node nodeItem = attributes.item(i);
            if (nodeItem instanceof Attr) {
                sb.append(" ");
                sb.append(((Attr) nodeItem).toString());
            }
        }
        if (element.hasChildNodes()) {
            sb.append(">\n  ...");
        } else {
            sb.append("/>");
        }
    }

    protected String getFormattedNodeXml(Node node, boolean z) {
        try {
            Transformer transformerCreateXmlTransformer = createXmlTransformer(z ? 2 : -1);
            StringWriter stringWriter = new StringWriter();
            transformerCreateXmlTransformer.transform(new DOMSource(node), new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (Exception e) {
            return "ERROR " + e.getMessage();
        }
    }

    protected Transformer createXmlTransformer(int i) throws TransformerConfigurationException {
        TransformerFactory transformerFactoryConfigure = this.factory;
        if (transformerFactoryConfigure == null) {
            TransformerFactoryConfigurer.Builder builderWithDTDLoadingDisabled = TransformerFactoryConfigurer.builder().withExternalStylesheetLoadingDisabled().withDTDLoadingDisabled();
            if (i >= 0) {
                builderWithDTDLoadingDisabled = builderWithDTDLoadingDisabled.withSafeAttribute("indent-number", Integer.valueOf(i));
            }
            transformerFactoryConfigure = builderWithDTDLoadingDisabled.build().configure(TransformerFactory.newInstance());
        }
        Transformer transformerNewTransformer = transformerFactoryConfigure.newTransformer();
        transformerNewTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformerNewTransformer.setOutputProperty("method", "xml");
        if (i >= 0) {
            try {
                transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(i));
            } catch (IllegalArgumentException unused) {
            }
            transformerNewTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }
        return transformerNewTransformer;
    }

    protected String nodeType(short s) {
        switch (s) {
            case 1:
                return "Element";
            case 2:
                return "Attribute";
            case 3:
                return "Text";
            case 4:
                return "CDATA Section";
            case 5:
                return "Entity Reference";
            case 6:
                return "Entity";
            case 7:
                return "Processing Instruction";
            case 8:
                return PngChunkTextVar.KEY_Comment;
            case 9:
            case 11:
            default:
                return Short.toString(s);
            case 10:
                return "Document Type";
            case 12:
                return "Notation";
        }
    }
}
