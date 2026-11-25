package org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.utils.PathUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/* JADX INFO: compiled from: ReplSystemInWrapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002¨\u0006\u0003"}, d2 = {"parseXml", "", "inputMessage", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReplSystemInWrapperKt {
    private static final InputSource parseXml$strToSource(String str) {
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return new InputSource(new ByteArrayInputStream(bytes));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String parseXml(String str) {
        Node firstChild = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(parseXml$strToSource(str)).getFirstChild();
        Intrinsics.checkNotNull(firstChild, "null cannot be cast to non-null type org.w3c.dom.Element");
        String textContent = ((Element) firstChild).getTextContent();
        Intrinsics.checkNotNullExpressionValue(textContent, "getTextContent(...)");
        return textContent;
    }
}
