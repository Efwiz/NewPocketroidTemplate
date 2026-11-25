package org.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import org.apache.commons.lang3.CharEncoding;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMErrorImpl;
import org.apache.xerces.dom.DOMLocatorImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.DOMNormalizer;
import org.apache.xerces.dom.DOMStringListImpl;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;

/* JADX INFO: loaded from: classes13.dex */
public class DOMSerializerImpl implements LSSerializer, DOMConfiguration {
    protected static final short CDATA = 8;
    protected static final short COMMENTS = 32;
    protected static final short DISCARDDEFAULT = 64;
    protected static final short DOM_ELEMENT_CONTENT_WHITESPACE = 1024;
    protected static final short ENTITIES = 4;
    protected static final short INFOSET = 128;
    protected static final short NAMESPACES = 1;
    protected static final short NSDECL = 512;
    protected static final short PRETTY_PRINT = 2048;
    protected static final short SPLITCDATA = 16;
    protected static final short WELLFORMED = 2;
    protected static final short XMLDECL = 256;
    private DOMStringList fRecognizedParameters;
    protected short features;
    private XMLSerializer serializer;
    private XML11Serializer xml11Serializer;
    private DOMErrorHandler fErrorHandler = null;
    private final DOMErrorImpl fError = new DOMErrorImpl();
    private final DOMLocatorImpl fLocator = new DOMLocatorImpl();

    static class DocumentMethods {
        private static java.lang.reflect.Method fgDocumentGetInputEncodingMethod = null;
        private static java.lang.reflect.Method fgDocumentGetXmlEncodingMethod = null;
        private static java.lang.reflect.Method fgDocumentGetXmlVersionMethod = null;
        private static boolean fgDocumentMethodsAvailable = false;

        static {
            try {
                fgDocumentGetXmlVersionMethod = Document.class.getMethod("getXmlVersion", new Class[0]);
                fgDocumentGetInputEncodingMethod = Document.class.getMethod("getInputEncoding", new Class[0]);
                fgDocumentGetXmlEncodingMethod = Document.class.getMethod("getXmlEncoding", new Class[0]);
                fgDocumentMethodsAvailable = true;
            } catch (Exception unused) {
                fgDocumentGetXmlVersionMethod = null;
                fgDocumentGetInputEncodingMethod = null;
                fgDocumentGetXmlEncodingMethod = null;
                fgDocumentMethodsAvailable = false;
            }
        }

        private DocumentMethods() {
        }
    }

    public DOMSerializerImpl() {
        this.features = (short) 0;
        this.features = (short) (((short) (((short) (((short) (((short) (((short) (((short) (((short) (((short) (((short) (this.features | 1)) | 4)) | 32)) | 8)) | 16)) | 2)) | 512)) | 1024)) | 64)) | 256);
        XMLSerializer xMLSerializer = new XMLSerializer();
        this.serializer = xMLSerializer;
        initSerializer(xMLSerializer);
    }

    private String _getInputEncoding(Node node) {
        Document ownerDocument = node.getNodeType() == 9 ? (Document) node : node.getOwnerDocument();
        if (ownerDocument != null && DocumentMethods.fgDocumentMethodsAvailable) {
            try {
                return (String) DocumentMethods.fgDocumentGetInputEncodingMethod.invoke(ownerDocument, null);
            } catch (ThreadDeath e) {
                throw e;
            } catch (VirtualMachineError e2) {
                throw e2;
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    private String _getXmlEncoding(Node node) {
        Document ownerDocument = node.getNodeType() == 9 ? (Document) node : node.getOwnerDocument();
        if (ownerDocument != null && DocumentMethods.fgDocumentMethodsAvailable) {
            try {
                return (String) DocumentMethods.fgDocumentGetXmlEncodingMethod.invoke(ownerDocument, null);
            } catch (ThreadDeath e) {
                throw e;
            } catch (VirtualMachineError e2) {
                throw e2;
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    private String _getXmlVersion(Node node) {
        Document ownerDocument = node.getNodeType() == 9 ? (Document) node : node.getOwnerDocument();
        if (ownerDocument != null && DocumentMethods.fgDocumentMethodsAvailable) {
            try {
                return (String) DocumentMethods.fgDocumentGetXmlVersionMethod.invoke(ownerDocument, null);
            } catch (ThreadDeath e) {
                throw e;
            } catch (VirtualMachineError e2) {
                throw e2;
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    private void copySettings(XMLSerializer xMLSerializer, XMLSerializer xMLSerializer2) {
        xMLSerializer2.fDOMErrorHandler = this.fErrorHandler;
        xMLSerializer2._format.setEncoding(xMLSerializer._format.getEncoding());
        xMLSerializer2._format.setLineSeparator(xMLSerializer._format.getLineSeparator());
        xMLSerializer2.fDOMFilter = xMLSerializer.fDOMFilter;
    }

    private void initSerializer(XMLSerializer xMLSerializer) {
        xMLSerializer.fNSBinder = new NamespaceSupport();
        xMLSerializer.fLocalNSBinder = new NamespaceSupport();
        xMLSerializer.fSymbolTable = new SymbolTable();
    }

    private void prepareForSerialization(XMLSerializer xMLSerializer, Node node) {
        xMLSerializer.reset();
        xMLSerializer.features = this.features;
        xMLSerializer.fDOMErrorHandler = this.fErrorHandler;
        boolean zBooleanValue = true;
        xMLSerializer.fNamespaces = (this.features & 1) != 0;
        xMLSerializer.fNamespacePrefixes = (this.features & 512) != 0;
        xMLSerializer._format.setIndenting((this.features & 2048) != 0);
        xMLSerializer._format.setOmitComments((this.features & 32) == 0);
        xMLSerializer._format.setOmitXMLDeclaration((this.features & 256) == 0);
        if ((this.features & 2) != 0) {
            Document ownerDocument = node.getNodeType() == 9 ? (Document) node : node.getOwnerDocument();
            try {
                java.lang.reflect.Method method = ownerDocument.getClass().getMethod("isXMLVersionChanged()", new Class[0]);
                if (method != null) {
                    zBooleanValue = ((Boolean) method.invoke(ownerDocument, null)).booleanValue();
                }
            } catch (Exception unused) {
            }
            if (node.getFirstChild() == null) {
                verify(node, zBooleanValue, false);
                return;
            }
            Node parentNode = node;
            while (parentNode != null) {
                verify(parentNode, zBooleanValue, false);
                Node firstChild = parentNode.getFirstChild();
                while (true) {
                    if (firstChild != null) {
                        parentNode = firstChild;
                        break;
                    }
                    firstChild = parentNode.getNextSibling();
                    if (firstChild == null) {
                        parentNode = parentNode.getParentNode();
                        if (node == parentNode) {
                            parentNode = null;
                            break;
                        }
                        firstChild = parentNode.getNextSibling();
                    }
                }
            }
        }
    }

    private void verify(Node node, boolean z, boolean z2) {
        short nodeType = node.getNodeType();
        this.fLocator.fRelatedNode = node;
        if (nodeType == 1) {
            if (z) {
                if (!((this.features & 1) != 0 ? CoreDocumentImpl.isValidQName(node.getPrefix(), node.getLocalName(), z2) : CoreDocumentImpl.isXMLName(node.getNodeName(), z2)) && this.fErrorHandler != null) {
                    DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "wf-invalid-character-in-node-name", new Object[]{"Element", node.getNodeName()}), (short) 3, "wf-invalid-character-in-node-name");
                }
            }
            NamedNodeMap attributes = node.hasAttributes() ? node.getAttributes() : null;
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    Attr attr = (Attr) attributes.item(i);
                    this.fLocator.fRelatedNode = attr;
                    DOMNormalizer.isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, attributes, attr, attr.getValue(), z2);
                    if (z && !CoreDocumentImpl.isXMLName(attr.getNodeName(), z2)) {
                        DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "wf-invalid-character-in-node-name", new Object[]{"Attr", node.getNodeName()}), (short) 3, "wf-invalid-character-in-node-name");
                    }
                }
            }
        } else if (nodeType == 3 || nodeType == 4) {
            DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, node.getNodeValue(), z2);
        } else if (nodeType != 5) {
            if (nodeType == 7) {
                ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
                String target = processingInstruction.getTarget();
                if (z) {
                    if (!(z2 ? XML11Char.isXML11ValidName(target) : XMLChar.isValidName(target))) {
                        DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "wf-invalid-character-in-node-name", new Object[]{"Element", node.getNodeName()}), (short) 3, "wf-invalid-character-in-node-name");
                    }
                }
                DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, processingInstruction.getData(), z2);
            } else if (nodeType == 8 && (this.features & 32) != 0) {
                DOMNormalizer.isCommentWF(this.fErrorHandler, this.fError, this.fLocator, ((Comment) node).getData(), z2);
            }
        } else if (z && (this.features & 4) != 0) {
            CoreDocumentImpl.isXMLName(node.getNodeName(), z2);
        }
        this.fLocator.fRelatedNode = null;
    }

    @Override // org.w3c.dom.DOMConfiguration
    public boolean canSetParameter(String str, Object obj) {
        if (obj == null) {
            return true;
        }
        if (!(obj instanceof Boolean)) {
            return (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER) && obj == null) || (obj instanceof DOMErrorHandler);
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        if (str.equalsIgnoreCase("namespaces") || str.equalsIgnoreCase(Constants.DOM_SPLIT_CDATA) || str.equalsIgnoreCase(Constants.DOM_DISCARD_DEFAULT_CONTENT) || str.equalsIgnoreCase(Constants.DOM_XMLDECL) || str.equalsIgnoreCase(Constants.DOM_WELLFORMED) || str.equalsIgnoreCase(Constants.DOM_INFOSET) || str.equalsIgnoreCase(Constants.DOM_ENTITIES) || str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS) || str.equalsIgnoreCase(Constants.DOM_COMMENTS) || str.equalsIgnoreCase(Constants.DOM_FORMAT_PRETTY_PRINT) || str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS)) {
            return true;
        }
        if (str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_VALIDATE) || str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS)) {
            return !zBooleanValue;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE) || str.equalsIgnoreCase(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS)) {
            return zBooleanValue;
        }
        return false;
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public DOMConfiguration getDomConfig() {
        return this;
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public LSSerializerFilter getFilter() {
        return this.serializer.fDOMFilter;
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public String getNewLine() {
        return this.serializer._format.getLineSeparator();
    }

    @Override // org.w3c.dom.DOMConfiguration
    public Object getParameter(String str) throws DOMException {
        if (str.equalsIgnoreCase(Constants.DOM_COMMENTS)) {
            return (this.features & 32) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase("namespaces")) {
            return (this.features & 1) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_XMLDECL)) {
            return (this.features & 256) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS)) {
            return (this.features & 8) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ENTITIES)) {
            return (this.features & 4) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_SPLIT_CDATA)) {
            return (this.features & 16) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_WELLFORMED)) {
            return (this.features & 2) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS)) {
            return (this.features & 512) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE) || str.equalsIgnoreCase(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS)) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_DISCARD_DEFAULT_CONTENT)) {
            return (this.features & 64) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_FORMAT_PRETTY_PRINT)) {
            return (this.features & 2048) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_INFOSET)) {
            short s = this.features;
            return ((s & 4) != 0 || (s & 8) != 0 || (s & 1) == 0 || (s & 512) == 0 || (s & 2) == 0 || (s & 32) == 0) ? Boolean.FALSE : Boolean.TRUE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS) || str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_VALIDATE) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION)) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
            return this.fErrorHandler;
        }
        if (str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER) || str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION) || str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE)) {
            throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_SUPPORTED", new Object[]{str}));
        }
        throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_FOUND", new Object[]{str}));
    }

    @Override // org.w3c.dom.DOMConfiguration
    public DOMStringList getParameterNames() {
        if (this.fRecognizedParameters == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("namespaces");
            arrayList.add(Constants.DOM_SPLIT_CDATA);
            arrayList.add(Constants.DOM_DISCARD_DEFAULT_CONTENT);
            arrayList.add(Constants.DOM_XMLDECL);
            arrayList.add(Constants.DOM_CANONICAL_FORM);
            arrayList.add(Constants.DOM_VALIDATE_IF_SCHEMA);
            arrayList.add(Constants.DOM_VALIDATE);
            arrayList.add(Constants.DOM_CHECK_CHAR_NORMALIZATION);
            arrayList.add(Constants.DOM_DATATYPE_NORMALIZATION);
            arrayList.add(Constants.DOM_FORMAT_PRETTY_PRINT);
            arrayList.add(Constants.DOM_NORMALIZE_CHARACTERS);
            arrayList.add(Constants.DOM_WELLFORMED);
            arrayList.add(Constants.DOM_INFOSET);
            arrayList.add(Constants.DOM_NAMESPACE_DECLARATIONS);
            arrayList.add(Constants.DOM_ELEMENT_CONTENT_WHITESPACE);
            arrayList.add(Constants.DOM_ENTITIES);
            arrayList.add(Constants.DOM_CDATA_SECTIONS);
            arrayList.add(Constants.DOM_COMMENTS);
            arrayList.add(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS);
            arrayList.add(Constants.DOM_ERROR_HANDLER);
            this.fRecognizedParameters = new DOMStringListImpl(arrayList);
        }
        return this.fRecognizedParameters;
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public void setFilter(LSSerializerFilter lSSerializerFilter) {
        this.serializer.fDOMFilter = lSSerializerFilter;
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public void setNewLine(String str) {
        this.serializer._format.setLineSeparator(str);
    }

    @Override // org.w3c.dom.DOMConfiguration
    public void setParameter(String str, Object obj) throws DOMException {
        int i;
        short s;
        if (!(obj instanceof Boolean)) {
            if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
                if (obj != null && !(obj instanceof DOMErrorHandler)) {
                    throw new DOMException((short) 17, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "TYPE_MISMATCH_ERR", new Object[]{str}));
                }
                this.fErrorHandler = (DOMErrorHandler) obj;
                return;
            }
            if (!str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER) && !str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION) && (!str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE) || obj == null)) {
                throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_FOUND", new Object[]{str}));
            }
            throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_SUPPORTED", new Object[]{str}));
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        if (!str.equalsIgnoreCase(Constants.DOM_INFOSET)) {
            if (str.equalsIgnoreCase(Constants.DOM_XMLDECL)) {
                short s2 = this.features;
                i = zBooleanValue ? s2 | 256 : s2 & (-257);
            } else {
                if (str.equalsIgnoreCase("namespaces")) {
                    short s3 = this.features;
                    this.features = (short) (zBooleanValue ? s3 | 1 : s3 & (-2));
                    this.serializer.fNamespaces = zBooleanValue;
                    return;
                }
                if (str.equalsIgnoreCase(Constants.DOM_SPLIT_CDATA)) {
                    short s4 = this.features;
                    i = zBooleanValue ? s4 | 16 : s4 & (-17);
                } else if (str.equalsIgnoreCase(Constants.DOM_DISCARD_DEFAULT_CONTENT)) {
                    short s5 = this.features;
                    i = zBooleanValue ? s5 | 64 : s5 & (-65);
                } else if (str.equalsIgnoreCase(Constants.DOM_WELLFORMED)) {
                    short s6 = this.features;
                    i = zBooleanValue ? s6 | 2 : s6 & (-3);
                } else if (str.equalsIgnoreCase(Constants.DOM_ENTITIES)) {
                    short s7 = this.features;
                    i = zBooleanValue ? s7 | 4 : s7 & (-5);
                } else if (str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS)) {
                    short s8 = this.features;
                    i = zBooleanValue ? s8 | 8 : s8 & (-9);
                } else if (str.equalsIgnoreCase(Constants.DOM_COMMENTS)) {
                    s = this.features;
                    if (!zBooleanValue) {
                        i = s & (-33);
                    }
                } else {
                    if (!str.equalsIgnoreCase(Constants.DOM_FORMAT_PRETTY_PRINT)) {
                        if (str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_VALIDATE) || str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS)) {
                            if (zBooleanValue) {
                                throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_SUPPORTED", new Object[]{str}));
                            }
                            return;
                        } else if (str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS)) {
                            short s9 = this.features;
                            this.features = (short) (zBooleanValue ? s9 | 512 : s9 & (-513));
                            this.serializer.fNamespacePrefixes = zBooleanValue;
                            return;
                        } else {
                            if (!str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE) && !str.equalsIgnoreCase(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS)) {
                                throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_FOUND", new Object[]{str}));
                            }
                            if (!zBooleanValue) {
                                throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_SUPPORTED", new Object[]{str}));
                            }
                            return;
                        }
                    }
                    short s10 = this.features;
                    i = zBooleanValue ? s10 | 2048 : s10 & (-2049);
                }
            }
            this.features = (short) i;
        }
        if (!zBooleanValue) {
            return;
        } else {
            s = (short) (((short) (((short) (((short) (((short) (this.features & (-5))) & (-9))) | 1)) | 512)) | 2);
        }
        i = s | 32;
        this.features = (short) i;
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public boolean write(Node node, LSOutput lSOutput) throws LSException {
        XMLSerializer xMLSerializer;
        if (node == null) {
            return false;
        }
        String str_getXmlVersion = _getXmlVersion(node);
        if (str_getXmlVersion == null || !str_getXmlVersion.equals(CompilerOptions.VERSION_1_1)) {
            xMLSerializer = this.serializer;
        } else {
            if (this.xml11Serializer == null) {
                XML11Serializer xML11Serializer = new XML11Serializer();
                this.xml11Serializer = xML11Serializer;
                initSerializer(xML11Serializer);
            }
            copySettings(this.serializer, this.xml11Serializer);
            xMLSerializer = this.xml11Serializer;
        }
        String encoding = lSOutput.getEncoding();
        if (encoding == null && (encoding = _getInputEncoding(node)) == null && (encoding = _getXmlEncoding(node)) == null) {
            encoding = "UTF-8";
        }
        try {
            try {
                try {
                    prepareForSerialization(xMLSerializer, node);
                    xMLSerializer._format.setEncoding(encoding);
                    OutputStream byteStream = lSOutput.getByteStream();
                    Writer characterStream = lSOutput.getCharacterStream();
                    String systemId = lSOutput.getSystemId();
                    if (characterStream != null) {
                        xMLSerializer.setOutputCharStream(characterStream);
                    } else if (byteStream != null) {
                        xMLSerializer.setOutputByteStream(byteStream);
                    } else {
                        if (systemId == null) {
                            String message = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "no-output-specified", null);
                            if (xMLSerializer.fDOMErrorHandler != null) {
                                DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
                                dOMErrorImpl.fType = "no-output-specified";
                                dOMErrorImpl.fMessage = message;
                                dOMErrorImpl.fSeverity = (short) 3;
                                xMLSerializer.fDOMErrorHandler.handleError(dOMErrorImpl);
                            }
                            throw new LSException((short) 82, message);
                        }
                        xMLSerializer.setOutputByteStream(XMLEntityManager.createOutputStream(systemId));
                    }
                    if (node.getNodeType() == 9) {
                        xMLSerializer.serialize((Document) node);
                    } else if (node.getNodeType() == 11) {
                        xMLSerializer.serialize((DocumentFragment) node);
                    } else {
                        if (node.getNodeType() != 1) {
                            xMLSerializer.clearDocumentState();
                            return false;
                        }
                        xMLSerializer.serialize((Element) node);
                    }
                    xMLSerializer.clearDocumentState();
                    return true;
                } catch (UnsupportedEncodingException e) {
                    if (xMLSerializer.fDOMErrorHandler != null) {
                        DOMErrorImpl dOMErrorImpl2 = new DOMErrorImpl();
                        dOMErrorImpl2.fException = e;
                        dOMErrorImpl2.fType = "unsupported-encoding";
                        dOMErrorImpl2.fMessage = e.getMessage();
                        dOMErrorImpl2.fSeverity = (short) 3;
                        xMLSerializer.fDOMErrorHandler.handleError(dOMErrorImpl2);
                    }
                    throw new LSException((short) 82, DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "unsupported-encoding", null));
                } catch (RuntimeException e2) {
                    if (e2 != DOMNormalizer.abort) {
                        throw ((LSException) DOMUtil.createLSException((short) 82, e2).fillInStackTrace());
                    }
                    xMLSerializer.clearDocumentState();
                    return false;
                }
            } catch (LSException e3) {
                throw e3;
            } catch (Exception e4) {
                if (xMLSerializer.fDOMErrorHandler != null) {
                    DOMErrorImpl dOMErrorImpl3 = new DOMErrorImpl();
                    dOMErrorImpl3.fException = e4;
                    dOMErrorImpl3.fMessage = e4.getMessage();
                    dOMErrorImpl3.fSeverity = (short) 2;
                    xMLSerializer.fDOMErrorHandler.handleError(dOMErrorImpl3);
                }
                throw ((LSException) DOMUtil.createLSException((short) 82, e4).fillInStackTrace());
            }
        } catch (Throwable th) {
            xMLSerializer.clearDocumentState();
            throw th;
        }
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public String writeToString(Node node) throws DOMException, LSException {
        XMLSerializer xMLSerializer;
        String str_getXmlVersion = _getXmlVersion(node);
        if (str_getXmlVersion == null || !str_getXmlVersion.equals(CompilerOptions.VERSION_1_1)) {
            xMLSerializer = this.serializer;
        } else {
            if (this.xml11Serializer == null) {
                XML11Serializer xML11Serializer = new XML11Serializer();
                this.xml11Serializer = xML11Serializer;
                initSerializer(xML11Serializer);
            }
            copySettings(this.serializer, this.xml11Serializer);
            xMLSerializer = this.xml11Serializer;
        }
        StringWriter stringWriter = new StringWriter();
        try {
            try {
                try {
                    try {
                        prepareForSerialization(xMLSerializer, node);
                        xMLSerializer._format.setEncoding(CharEncoding.UTF_16);
                        xMLSerializer.setOutputCharStream(stringWriter);
                        if (node.getNodeType() == 9) {
                            xMLSerializer.serialize((Document) node);
                        } else if (node.getNodeType() == 11) {
                            xMLSerializer.serialize((DocumentFragment) node);
                        } else {
                            if (node.getNodeType() != 1) {
                                String message = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "unable-to-serialize-node", null);
                                if (xMLSerializer.fDOMErrorHandler != null) {
                                    DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
                                    dOMErrorImpl.fType = "unable-to-serialize-node";
                                    dOMErrorImpl.fMessage = message;
                                    dOMErrorImpl.fSeverity = (short) 3;
                                    xMLSerializer.fDOMErrorHandler.handleError(dOMErrorImpl);
                                }
                                throw new LSException((short) 82, message);
                            }
                            xMLSerializer.serialize((Element) node);
                        }
                        xMLSerializer.clearDocumentState();
                        return stringWriter.toString();
                    } catch (LSException e) {
                        throw e;
                    }
                } catch (IOException e2) {
                    throw new DOMException((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "STRING_TOO_LONG", new Object[]{e2.getMessage()}));
                }
            } catch (RuntimeException e3) {
                if (e3 != DOMNormalizer.abort) {
                    throw ((LSException) DOMUtil.createLSException((short) 82, e3).fillInStackTrace());
                }
                xMLSerializer.clearDocumentState();
                return null;
            }
        } catch (Throwable th) {
            xMLSerializer.clearDocumentState();
            throw th;
        }
    }

    @Override // org.w3c.dom.ls.LSSerializer
    public boolean writeToURI(Node node, String str) throws LSException {
        XMLSerializer xMLSerializer;
        if (node == null) {
            return false;
        }
        String str_getXmlVersion = _getXmlVersion(node);
        if (str_getXmlVersion == null || !str_getXmlVersion.equals(CompilerOptions.VERSION_1_1)) {
            xMLSerializer = this.serializer;
        } else {
            if (this.xml11Serializer == null) {
                XML11Serializer xML11Serializer = new XML11Serializer();
                this.xml11Serializer = xML11Serializer;
                initSerializer(xML11Serializer);
            }
            copySettings(this.serializer, this.xml11Serializer);
            xMLSerializer = this.xml11Serializer;
        }
        String str_getInputEncoding = _getInputEncoding(node);
        if (str_getInputEncoding == null && (str_getInputEncoding = _getXmlEncoding(node)) == null) {
            str_getInputEncoding = "UTF-8";
        }
        try {
            try {
                try {
                    prepareForSerialization(xMLSerializer, node);
                    xMLSerializer._format.setEncoding(str_getInputEncoding);
                    xMLSerializer.setOutputByteStream(XMLEntityManager.createOutputStream(str));
                    if (node.getNodeType() == 9) {
                        xMLSerializer.serialize((Document) node);
                    } else if (node.getNodeType() == 11) {
                        xMLSerializer.serialize((DocumentFragment) node);
                    } else {
                        if (node.getNodeType() != 1) {
                            xMLSerializer.clearDocumentState();
                            return false;
                        }
                        xMLSerializer.serialize((Element) node);
                    }
                    xMLSerializer.clearDocumentState();
                    return true;
                } catch (LSException e) {
                    throw e;
                }
            } catch (RuntimeException e2) {
                if (e2 != DOMNormalizer.abort) {
                    throw ((LSException) DOMUtil.createLSException((short) 82, e2).fillInStackTrace());
                }
                xMLSerializer.clearDocumentState();
                return false;
            } catch (Exception e3) {
                if (xMLSerializer.fDOMErrorHandler != null) {
                    DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
                    dOMErrorImpl.fException = e3;
                    dOMErrorImpl.fMessage = e3.getMessage();
                    dOMErrorImpl.fSeverity = (short) 2;
                    xMLSerializer.fDOMErrorHandler.handleError(dOMErrorImpl);
                }
                throw ((LSException) DOMUtil.createLSException((short) 82, e3).fillInStackTrace());
            }
        } catch (Throwable th) {
            xMLSerializer.clearDocumentState();
            throw th;
        }
    }
}
