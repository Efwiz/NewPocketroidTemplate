package javax.xml.transform;

/* JADX INFO: loaded from: classes11.dex */
public interface ErrorListener {
    void error(TransformerException transformerException) throws TransformerException;

    void fatalError(TransformerException transformerException) throws TransformerException;

    void warning(TransformerException transformerException) throws TransformerException;
}
