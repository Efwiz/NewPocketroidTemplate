package javax.xml.stream.events;

/* JADX INFO: loaded from: classes11.dex */
public interface Namespace extends Attribute {
    String getNamespaceURI();

    String getPrefix();

    boolean isDefaultNamespaceDeclaration();
}
