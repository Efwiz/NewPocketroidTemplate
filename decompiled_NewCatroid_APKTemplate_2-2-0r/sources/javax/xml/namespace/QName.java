package javax.xml.namespace;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.catrobat.catroid.BuildConfig;

/* JADX INFO: loaded from: classes11.dex */
public class QName implements Serializable {
    private static final long compatabilitySerialVersionUID = 4418622981026545151L;
    private static final long defaultSerialVersionUID = -9120448754896609940L;
    private static final long serialVersionUID;
    private final String localPart;
    private final String namespaceURI;
    private String prefix;
    private transient String qNameAsString;

    static {
        String str;
        try {
            str = (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.xml.namespace.QName.1
                @Override // java.security.PrivilegedAction
                public Object run() {
                    return System.getProperty("org.apache.xml.namespace.QName.useCompatibleSerialVersionUID");
                }
            });
        } catch (Exception unused) {
            str = null;
        }
        serialVersionUID = !BuildConfig.VERSION_NAME.equals(str) ? defaultSerialVersionUID : compatabilitySerialVersionUID;
    }

    public QName(String str) {
        this("", str, "");
    }

    public QName(String str, String str2) {
        this(str, str2, "");
    }

    public QName(String str, String str2, String str3) {
        this.namespaceURI = str == null ? "" : str;
        if (str2 == null) {
            throw new IllegalArgumentException("local part cannot be \"null\" when creating a QName");
        }
        this.localPart = str2;
        if (str3 == null) {
            throw new IllegalArgumentException("prefix cannot be \"null\" when creating a QName");
        }
        this.prefix = str3;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        if (this.prefix == null) {
            this.prefix = "";
        }
    }

    public static QName valueOf(String str) {
        if (str == null) {
            throw new IllegalArgumentException("cannot create QName from \"null\" or \"\" String");
        }
        if (str.length() != 0 && str.charAt(0) == '{') {
            if (str.startsWith("{}")) {
                throw new IllegalArgumentException(new StringBuffer("Namespace URI .equals(XMLConstants.NULL_NS_URI), .equals(\"\"), only the local part, \"").append(str.substring("".length() + 2)).append("\", should be provided.").toString());
            }
            int iIndexOf = str.indexOf(125);
            if (iIndexOf != -1) {
                return new QName(str.substring(1, iIndexOf), str.substring(iIndexOf + 1), "");
            }
            throw new IllegalArgumentException(new StringBuffer("cannot create QName from \"").append(str).append("\", missing closing \"}\"").toString());
        }
        return new QName("", str, "");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof QName)) {
            return false;
        }
        QName qName = (QName) obj;
        return this.localPart.equals(qName.localPart) && this.namespaceURI.equals(qName.namespaceURI);
    }

    public String getLocalPart() {
        return this.localPart;
    }

    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public final int hashCode() {
        return this.namespaceURI.hashCode() ^ this.localPart.hashCode();
    }

    public String toString() {
        String string = this.qNameAsString;
        if (string == null) {
            int length = this.namespaceURI.length();
            if (length == 0) {
                string = this.localPart;
            } else {
                StringBuffer stringBuffer = new StringBuffer(length + this.localPart.length() + 2);
                stringBuffer.append('{');
                stringBuffer.append(this.namespaceURI);
                stringBuffer.append('}');
                stringBuffer.append(this.localPart);
                string = stringBuffer.toString();
            }
            this.qNameAsString = string;
        }
        return string;
    }
}
