package javax.xml.transform;

import java.util.Properties;

/* JADX INFO: loaded from: classes11.dex */
public interface Templates {
    Properties getOutputProperties();

    Transformer newTransformer() throws TransformerConfigurationException;
}
