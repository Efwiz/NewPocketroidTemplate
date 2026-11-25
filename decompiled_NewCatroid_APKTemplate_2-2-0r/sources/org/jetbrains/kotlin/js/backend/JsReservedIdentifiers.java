package org.jetbrains.kotlin.js.backend;

import androidx.core.app.NotificationCompat;
import androidx.core.text.util.LocalePreferences;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import com.google.firebase.analytics.FirebaseAnalytics;
import gnu.trove.THashSet;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes5.dex */
public class JsReservedIdentifiers {
    public static final Set<String> reservedGlobalSymbols;

    static {
        String[] strArr = {"NaN", "Infinity", "undefined", "eval", "parseInt", "parseFloat", "isNan", "isFinite", "decodeURI", "decodeURIComponent", "encodeURI", "encodeURIComponent", CommonClassNames.JAVA_LANG_OBJECT_SHORT, "Function", "Array", CommonClassNames.JAVA_LANG_STRING_SHORT, "Boolean", "Number", "Date", "RegExp", "Error", "EvalError", "RangeError", "ReferenceError", "SyntaxError", "TypeError", "URIError", "Math", "arguments", "escape", "unescape", "applicationCache", "closed", "Components", FirebaseAnalytics.Param.CONTENT, "controllers", "crypto", "defaultStatus", "dialogArguments", "directories", "document", "frameElement", "frames", "fullScreen", "globalStorage", "history", "innerHeight", "innerWidth", "length", FirebaseAnalytics.Param.LOCATION, "locationbar", "localStorage", "menubar", "mozInnerScreenX", "mozInnerScreenY", "mozScreenPixelsPerCssPixel", "name", "navigator", "opener", "outerHeight", "outerWidth", "pageXOffset", "pageYOffset", "parent", "personalbar", "pkcs11", "returnValue", "screen", "scrollbars", "scrollMaxX", "scrollMaxY", "self", "sessionStorage", "sidebar", NotificationCompat.CATEGORY_STATUS, "statusbar", "toolbar", "top", ConfigConstants.CONFIG_KEY_WINDOW, "alert", "addEventListener", "atob", "back", "blur", "btoa", "captureEvents", "clearInterval", "clearTimeout", "close", "confirm", "disableExternalCapture", "dispatchEvent", ArchiveStreamFactory.DUMP, "enableExternalCapture", "escape", "find", "focus", "forward", "GeckoActiveXObject", "getAttention", "getAttentionWithCycleCount", "getComputedStyle", "getSelection", "home", "maximize", "minimize", "moveBy", "moveTo", "open", "openDialog", "postMessage", "print", "prompt", "QueryInterface", "releaseEvents", "removeEventListener", "resizeBy", "resizeTo", "restore", "routeEvent", "scroll", "scrollBy", "scrollByLines", "scrollByPages", "scrollTo", "setInterval", "setResizeable", "setTimeout", "showModalDialog", "sizeToContent", "stop", "uuescape", "updateCommands", "XPCNativeWrapper", "XPCSafeJSOjbectWrapper", "onabort", "onbeforeunload", "onchange", "onclick", "onclose", "oncontextmenu", "ondragdrop", "onerror", "onfocus", "onhashchange", "onkeydown", "onkeypress", "onkeyup", "onload", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onmozorientation", "onpaint", "onreset", "onresize", "onscroll", "onselect", "onsubmit", "onunload", "ontouchcancel", "ontouchend", "ontouchmove", "ontouchstart", "ongesturestart", "ongesturechange", "ongestureend", "uneval", "getPrototypeOf", "let", PsiKeyword.YIELD, "abstract", "int", "short", "boolean", PsiKeyword.INTERFACE, "static", "byte", "long", PsiKeyword.CHAR, "final", "native", "synchronized", "float", "package", PsiKeyword.THROWS, PsiKeyword.GOTO, "private", "transient", PsiKeyword.IMPLEMENTS, "protected", "volatile", "double", "public", "attachEvent", "clientInformation", "clipboardData", "createPopup", "dialogHeight", "dialogLeft", "dialogTop", "dialogWidth", "onafterprint", "onbeforedeactivate", "onbeforeprint", "oncontrolselect", "ondeactivate", "onhelp", "onresizeend", "event", "external", "Debug", "Enumerator", "Global", "Image", "ActiveXObject", "VBArray", "Components", "toString", "getClass", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "prototype", "valueOf", "Anchor", "Applet", "Attr", "Canvas", "CanvasGradient", "CanvasPattern", "CanvasRenderingContext2D", "CDATASection", "CharacterData", PngChunkTextVar.KEY_Comment, "CSS2Properties", "CSSRule", "CSSStyleSheet", "Document", "DocumentFragment", "DocumentType", "DOMException", "DOMImplementation", "DOMParser", "Element", "Event", "ExternalInterface", "FlashPlayer", "Form", "Frame", "History", "HTMLCollection", "HTMLDocument", "HTMLElement", "IFrame", "Image", "Input", "JSObject", "KeyEvent", "Link", "Location", "MimeType", "MouseEvent", "Navigator", "Node", "NodeList", "Option", "Plugin", "ProcessingInstruction", "Range", "RangeException", "Screen", "Select", "Table", "TableCell", "TableRow", "TableSelection", "Text", "TextArea", "UIEvent", "Window", "XMLHttpRequest", "XMLSerializer", "XPathException", "XPathResult", "XSLTProcessor", SuffixConstants.EXTENSION_java, "Packages", "netscape", LocalePreferences.FirstDayOfWeek.SUNDAY, "JavaObject", "JavaClass", "JavaArray", "JavaMember", "$wnd", "$doc", "$entry", "$moduleName", "$moduleBase", "$gwt_version", "$sessionId", "$stack", "$stackDepth", "$location"};
        THashSet tHashSet = new THashSet(304);
        reservedGlobalSymbols = tHashSet;
        Collections.addAll(tHashSet, strArr);
    }

    private JsReservedIdentifiers() {
    }
}
