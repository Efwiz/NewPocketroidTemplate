package javax.xml.transform.sax;

import javax.xml.transform.Templates;
import org.xml.sax.ContentHandler;

/* JADX INFO: loaded from: classes11.dex */
public interface TemplatesHandler extends ContentHandler {
    String getSystemId();

    Templates getTemplates();

    void setSystemId(String str);
}
