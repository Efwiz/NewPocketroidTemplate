package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;

/* JADX INFO: loaded from: classes13.dex */
public class CMNodeFactory {
    private static final boolean DEBUG = false;
    private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final int MULTIPLICITY = 1;
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private XMLErrorReporter fErrorReporter;
    private int maxNodeLimit;
    private int nodeCount = 0;
    private SecurityManager fSecurityManager = null;

    public CMNode getCMBinOpNode(int i, CMNode cMNode, CMNode cMNode2) {
        nodeCountCheck();
        return new XSCMBinOp(i, cMNode, cMNode2);
    }

    public CMNode getCMLeafNode(int i, Object obj, int i2, int i3) {
        nodeCountCheck();
        return new XSCMLeaf(i, obj, i2, i3);
    }

    public CMNode getCMRepeatingLeafNode(int i, Object obj, int i2, int i3, int i4, int i5) {
        nodeCountCheck();
        return new XSCMRepeatingLeaf(i, obj, i2, i3, i4, i5);
    }

    public CMNode getCMUniOpNode(int i, CMNode cMNode) {
        nodeCountCheck();
        return new XSCMUniOp(i, cMNode);
    }

    public void nodeCountCheck() {
        if (this.fSecurityManager != null) {
            int i = this.nodeCount;
            this.nodeCount = i + 1;
            if (i > this.maxNodeLimit) {
                this.fErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, "maxOccurLimit", new Object[]{new Integer(this.maxNodeLimit)}, (short) 2);
                this.nodeCount = 0;
            }
        }
    }

    public void reset() {
        SecurityManager securityManager = this.fSecurityManager;
        if (securityManager != null) {
            this.maxNodeLimit = securityManager.getMaxOccurNodeLimit();
        }
    }

    public void reset(XMLComponentManager xMLComponentManager) {
        this.fErrorReporter = (XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        try {
            this.fSecurityManager = (SecurityManager) xMLComponentManager.getProperty(SECURITY_MANAGER);
            reset();
        } catch (XMLConfigurationException unused) {
            this.fSecurityManager = null;
        }
    }

    public void resetNodeCount() {
        this.nodeCount = 0;
    }

    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
            if (length == Constants.SECURITY_MANAGER_PROPERTY.length() && str.endsWith(Constants.SECURITY_MANAGER_PROPERTY)) {
                SecurityManager securityManager = (SecurityManager) obj;
                this.fSecurityManager = securityManager;
                this.maxNodeLimit = securityManager != null ? securityManager.getMaxOccurNodeLimit() : 0;
            } else if (length == Constants.ERROR_REPORTER_PROPERTY.length() && str.endsWith(Constants.ERROR_REPORTER_PROPERTY)) {
                this.fErrorReporter = (XMLErrorReporter) obj;
            }
        }
    }
}
