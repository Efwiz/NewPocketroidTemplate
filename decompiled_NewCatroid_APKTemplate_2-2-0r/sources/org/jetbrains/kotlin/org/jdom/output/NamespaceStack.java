package org.jetbrains.kotlin.org.jdom.output;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Stack;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.org.jdom.Namespace;

/* JADX INFO: loaded from: classes4.dex */
class NamespaceStack {
    private Stack prefixes = new Stack();
    private Stack uris = new Stack();

    NamespaceStack() {
    }

    public void push(Namespace namespace) {
        this.prefixes.push(namespace.getPrefix());
        this.uris.push(namespace.getURI());
    }

    public String pop() {
        String str = (String) this.prefixes.pop();
        this.uris.pop();
        return str;
    }

    public int size() {
        return this.prefixes.size();
    }

    public String getURI(String str) {
        int iLastIndexOf = this.prefixes.lastIndexOf(str);
        if (iLastIndexOf == -1) {
            return null;
        }
        return (String) this.uris.elementAt(iLastIndexOf);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
        stringBuffer.append("Stack: " + this.prefixes.size() + property);
        for (int i = 0; i < this.prefixes.size(); i++) {
            stringBuffer.append(this.prefixes.elementAt(i) + ContainerUtils.FIELD_DELIMITER + this.uris.elementAt(i) + property);
        }
        return stringBuffer.toString();
    }
}
