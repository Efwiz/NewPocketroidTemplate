package org.xmlunit.builder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmlunit.XMLUnitException;

/* JADX INFO: loaded from: classes3.dex */
public class Input {

    public interface Builder {
        Source build();
    }

    public interface TransformationBuilder extends TransformationBuilderBase<TransformationBuilder>, Builder {
        TransformationBuilder withStylesheet(Builder builder);
    }

    private Input() {
    }

    private static class SourceHoldingBuilder implements Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected final Source source;

        protected SourceHoldingBuilder(Source source) {
            this.source = source;
        }

        @Override // org.xmlunit.builder.Input.Builder
        public Source build() {
            return this.source;
        }
    }

    public static Builder fromDocument(Document document) {
        return new SourceHoldingBuilder(new DOMSource(document));
    }

    public static Builder fromNode(Node node) {
        return new SourceHoldingBuilder(new DOMSource(node));
    }

    private static class StreamBuilder extends SourceHoldingBuilder {
        private StreamBuilder(File file) {
            super(new StreamSource(file));
        }

        private StreamBuilder(InputStream inputStream) {
            super(new StreamSource(inputStream));
        }

        private StreamBuilder(Reader reader) {
            super(new StreamSource(reader));
        }

        private StreamBuilder(final byte[] bArr) {
            super(new StreamSource() { // from class: org.xmlunit.builder.Input.StreamBuilder.1
                @Override // javax.xml.transform.stream.StreamSource
                public InputStream getInputStream() {
                    return new ByteArrayInputStream(bArr);
                }
            });
        }

        private StreamBuilder(final String str) {
            super(new StreamSource() { // from class: org.xmlunit.builder.Input.StreamBuilder.2
                @Override // javax.xml.transform.stream.StreamSource
                public Reader getReader() {
                    return new StringReader(str);
                }
            });
        }

        void setSystemId(String str) {
            if (str != null) {
                this.source.setSystemId(str);
            }
        }
    }

    public static Builder from(Object obj) {
        if (obj instanceof Source) {
            return new SourceHoldingBuilder((Source) obj);
        }
        if (obj instanceof Builder) {
            return (Builder) obj;
        }
        if (obj instanceof Document) {
            return fromDocument((Document) obj);
        }
        if (obj instanceof Node) {
            return fromNode((Node) obj);
        }
        if (obj instanceof byte[]) {
            return fromByteArray((byte[]) obj);
        }
        if (obj instanceof String) {
            return fromString((String) obj);
        }
        if (obj instanceof File) {
            return fromFile((File) obj);
        }
        if (obj instanceof URL) {
            return fromURL((URL) obj);
        }
        if (obj instanceof URI) {
            return fromURI((URI) obj);
        }
        if (obj instanceof InputStream) {
            return fromStream((InputStream) obj);
        }
        if (obj instanceof ReadableByteChannel) {
            return fromChannel((ReadableByteChannel) obj);
        }
        if (obj instanceof Path) {
            return fromPath((Path) obj);
        }
        return fromJaxb(obj);
    }

    public static JaxbBuilder fromJaxb(Object obj) {
        return JaxbBuilderFactoryLocator.getFactory().create(obj);
    }

    public static Builder fromFile(File file) {
        return new StreamBuilder(file);
    }

    public static Builder fromFile(String str) {
        return new StreamBuilder(new File(str));
    }

    public static Builder fromStream(InputStream inputStream) {
        return new StreamBuilder(inputStream);
    }

    public static Builder fromReader(Reader reader) {
        return new StreamBuilder(reader);
    }

    public static Builder fromString(String str) {
        return new StreamBuilder(str);
    }

    public static Builder fromByteArray(byte[] bArr) {
        return new StreamBuilder(bArr);
    }

    public static Builder fromChannel(ReadableByteChannel readableByteChannel) {
        return fromStream(Channels.newInputStream(readableByteChannel));
    }

    public static Builder fromURL(URL url) {
        try {
            InputStream inputStreamOpenStream = url.openStream();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[4096];
                while (true) {
                    int i = inputStreamOpenStream.read(bArr);
                    if (i < 0) {
                        break;
                    }
                    if (i > 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                    throw new XMLUnitException(e);
                }
                StreamBuilder streamBuilder = (StreamBuilder) fromByteArray(byteArrayOutputStream.toByteArray());
                try {
                    streamBuilder.setSystemId(url.toURI().toString());
                } catch (URISyntaxException unused) {
                    streamBuilder.setSystemId(url.toString());
                }
                if (inputStreamOpenStream != null) {
                    inputStreamOpenStream.close();
                }
                return streamBuilder;
            } catch (Throwable th) {
                if (inputStreamOpenStream != null) {
                    try {
                        inputStreamOpenStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            throw new XMLUnitException(e);
        }
    }

    public static Builder fromURI(URI uri) {
        try {
            return fromURL(uri.toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("uri " + uri + " is not an URL", e);
        }
    }

    public static Builder fromURI(String str) {
        try {
            return fromURI(new URI(str));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("uri " + str + " is not an URI", e);
        }
    }

    public static Builder fromPath(Path path) {
        return fromURI(path.toUri());
    }

    private static class Transformation extends AbstractTransformationBuilder<TransformationBuilder> implements TransformationBuilder {
        private Transformation(Source source) {
            super(source);
        }

        @Override // org.xmlunit.builder.Input.TransformationBuilder
        public TransformationBuilder withStylesheet(Builder builder) {
            return withStylesheet(builder.build());
        }

        @Override // org.xmlunit.builder.Input.Builder
        public Source build() {
            return new DOMSource(getHelper().transformToDocument());
        }
    }

    public static TransformationBuilder byTransforming(Source source) {
        return new Transformation(source);
    }

    public static TransformationBuilder byTransforming(Builder builder) {
        return byTransforming(builder.build());
    }
}
