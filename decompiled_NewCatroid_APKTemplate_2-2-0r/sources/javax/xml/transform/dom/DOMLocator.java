package javax.xml.transform.dom;

import javax.xml.transform.SourceLocator;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes11.dex */
public interface DOMLocator extends SourceLocator {
    Node getOriginatingNode();
}
