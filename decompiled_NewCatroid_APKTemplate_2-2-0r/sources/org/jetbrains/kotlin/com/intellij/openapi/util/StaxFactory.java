package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.fasterxml.aalto.in.ByteSourceBootstrapper;
import org.jetbrains.kotlin.com.fasterxml.aalto.in.ReaderConfig;
import org.jetbrains.kotlin.com.fasterxml.aalto.stax.StreamReaderImpl;
import org.jetbrains.kotlin.org.codehaus.stax2.XMLStreamReader2;

/* JADX INFO: compiled from: StaxFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u001a\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\f2\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u001a\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"config", "Lorg/jetbrains/kotlin/com/fasterxml/aalto/in/ReaderConfig;", "configWithCoalescing", "createConfig", "coalesce", "", "createNonCoalescingXmlStreamReader", "Lorg/jetbrains/kotlin/org/codehaus/stax2/XMLStreamReader2;", "input", "Ljava/io/InputStream;", "locationSource", "", "", "createXmlStreamReader", "reader", "Ljava/io/Reader;", "bytes", "intellij.platform.util.xmlDom"}, k = 2, mv = {1, 5, 1})
public final class StaxFactory {
    private static final ReaderConfig config = createConfig(false);
    private static final ReaderConfig configWithCoalescing = createConfig(true);

    private static final ReaderConfig createConfig(boolean z) {
        ReaderConfig readerConfig = new ReaderConfig();
        readerConfig.doAutoCloseInput(true);
        readerConfig.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        readerConfig.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        readerConfig.setProperty("org.jetbrains.kotlin.org.codehaus.stax2.internNames", false);
        readerConfig.setProperty("org.jetbrains.kotlin.org.codehaus.stax2.internNsUris", false);
        readerConfig.doPreserveLocation(false);
        readerConfig.setProperty("org.jetbrains.kotlin.org.codehaus.stax2.closeInputSource", true);
        readerConfig.setXmlEncoding("UTF-8");
        readerConfig.doCoalesceText(z);
        readerConfig.doParseLazily(true);
        return readerConfig;
    }

    public static final XMLStreamReader2 createXmlStreamReader(InputStream input) throws XMLStreamException {
        Intrinsics.checkNotNullParameter(input, "input");
        StreamReaderImpl streamReaderImplConstruct = StreamReaderImpl.construct(ByteSourceBootstrapper.construct(configWithCoalescing.createNonShared(null, null, "UTF-8"), input));
        Intrinsics.checkNotNullExpressionValue(streamReaderImplConstruct, "StreamReaderImpl.constru…, null, \"UTF-8\"), input))");
        return streamReaderImplConstruct;
    }

    public static final XMLStreamReader2 createXmlStreamReader(byte[] bytes) throws XMLStreamException {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        StreamReaderImpl streamReaderImplConstruct = StreamReaderImpl.construct(ByteSourceBootstrapper.construct(configWithCoalescing.createNonShared(null, null, "UTF-8"), bytes, 0, bytes.length));
        Intrinsics.checkNotNullExpressionValue(streamReaderImplConstruct, "StreamReaderImpl.constru…g, bytes, 0, bytes.size))");
        return streamReaderImplConstruct;
    }

    public static final XMLStreamReader2 createNonCoalescingXmlStreamReader(InputStream input, String str) throws XMLStreamException {
        Intrinsics.checkNotNullParameter(input, "input");
        StreamReaderImpl streamReaderImplConstruct = StreamReaderImpl.construct(ByteSourceBootstrapper.construct(config.createNonShared(null, str, "UTF-8"), input));
        Intrinsics.checkNotNullExpressionValue(streamReaderImplConstruct, "StreamReaderImpl.constru…Source, \"UTF-8\"), input))");
        return streamReaderImplConstruct;
    }

    public static final XMLStreamReader2 createNonCoalescingXmlStreamReader(byte[] input, String str) throws XMLStreamException {
        Intrinsics.checkNotNullParameter(input, "input");
        StreamReaderImpl streamReaderImplConstruct = StreamReaderImpl.construct(ByteSourceBootstrapper.construct(config.createNonShared(null, str, "UTF-8"), input, 0, input.length));
        Intrinsics.checkNotNullExpressionValue(streamReaderImplConstruct, "StreamReaderImpl.constru…), input, 0, input.size))");
        return streamReaderImplConstruct;
    }
}
