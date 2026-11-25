package org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.utils.PathUtil;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;

/* JADX INFO: compiled from: IdeDiagnosticMessageHolder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\u0007H\u0016J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0016R6\u0010\u0003\u001a*\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004j\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/IdeDiagnosticMessageHolder;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/DiagnosticMessageHolder;", "()V", "diagnostics", "Ljava/util/ArrayList;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "", "Lkotlin/collections/ArrayList;", "renderMessage", "report", "", "diagnostic", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "render", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeDiagnosticMessageHolder implements DiagnosticMessageHolder {
    private final ArrayList<Pair<Diagnostic, String>> diagnostics = new ArrayList<>();

    @Override // org.jetbrains.kotlin.cli.common.messages.DiagnosticMessageReporter
    public void report(Diagnostic diagnostic, PsiFile file, String render) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(render, "render");
        this.diagnostics.add(new Pair<>(diagnostic, render));
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.DiagnosticMessageHolder
    public String renderMessage() {
        Document documentNewDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element elementCreateElement = documentNewDocument.createElement("report");
        documentNewDocument.appendChild(elementCreateElement);
        for (Pair<Diagnostic, String> pair : this.diagnostics) {
            Diagnostic diagnosticComponent1 = pair.component1();
            String strComponent2 = pair.component2();
            TextRange textRangeFirstRange = DiagnosticUtils.firstRange(diagnosticComponent1.getTextRanges());
            Intrinsics.checkNotNullExpressionValue(textRangeFirstRange, "firstRange(...)");
            Element elementCreateElement2 = documentNewDocument.createElement("reportEntry");
            elementCreateElement2.setAttribute("severity", diagnosticComponent1.getSeverity().toString());
            elementCreateElement2.setAttribute("rangeStart", String.valueOf(textRangeFirstRange.getStartOffset()));
            elementCreateElement2.setAttribute("rangeEnd", String.valueOf(textRangeFirstRange.getEndOffset()));
            elementCreateElement2.appendChild(documentNewDocument.createTextNode(StringUtil.escapeXmlEntities(strComponent2)));
            elementCreateElement.appendChild(elementCreateElement2);
        }
        DOMImplementation implementation = documentNewDocument.getImplementation();
        Intrinsics.checkNotNull(implementation, "null cannot be cast to non-null type org.w3c.dom.ls.DOMImplementationLS");
        String strWriteToString = ((DOMImplementationLS) implementation).createLSSerializer().writeToString(documentNewDocument);
        Intrinsics.checkNotNullExpressionValue(strWriteToString, "writeToString(...)");
        return strWriteToString;
    }
}
