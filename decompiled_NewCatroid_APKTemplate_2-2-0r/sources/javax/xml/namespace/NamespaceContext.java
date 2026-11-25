package javax.xml.namespace;

import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface NamespaceContext {
    String getNamespaceURI(String str);

    String getPrefix(String str);

    Iterator getPrefixes(String str);
}
