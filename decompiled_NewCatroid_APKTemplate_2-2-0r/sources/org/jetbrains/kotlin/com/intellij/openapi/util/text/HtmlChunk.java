package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.apkeditor.Options;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.xml.serialize.Method;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap;

/* JADX INFO: loaded from: classes3.dex */
public abstract class HtmlChunk {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 36:
            case 38:
            case 39:
            case 40:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 7:
            case 9:
            case 12:
            case 20:
            case 22:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 36:
            case 38:
            case 39:
            case 40:
                i2 = 2;
                break;
            case 7:
            case 9:
            case 12:
            case 20:
            case 22:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 36:
            case 38:
            case 39:
            case 40:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/HtmlChunk";
                break;
            case 7:
            default:
                objArr[0] = "tagName";
                break;
            case 9:
            case 12:
            case 20:
                objArr[0] = "style";
                break;
            case 22:
                objArr[0] = TypedValues.Custom.S_COLOR;
                break;
            case 28:
            case 32:
            case 34:
                objArr[0] = "text";
                break;
            case 30:
                objArr[0] = "rawHtml";
                break;
            case 31:
            case 33:
                objArr[0] = "target";
                break;
            case 35:
                objArr[0] = "htmlEntity";
                break;
            case 37:
                objArr[0] = "chunks";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "wrapWith";
                break;
            case 3:
                objArr[1] = ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY;
                break;
            case 4:
                objArr[1] = "bold";
                break;
            case 5:
                objArr[1] = "italic";
                break;
            case 6:
                objArr[1] = "strikethrough";
                break;
            case 7:
            case 9:
            case 12:
            case 20:
            case 22:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/HtmlChunk";
                break;
            case 8:
            case 10:
                objArr[1] = "div";
                break;
            case 11:
            case 13:
                objArr[1] = "span";
                break;
            case 14:
                objArr[1] = CompressorStreamFactory.BROTLI;
                break;
            case 15:
                objArr[1] = "li";
                break;
            case 16:
                objArr[1] = "ul";
                break;
            case 17:
                objArr[1] = LanguageCodeUtil.HR;
                break;
            case 18:
                objArr[1] = "p";
                break;
            case 19:
                objArr[1] = "head";
                break;
            case 21:
                objArr[1] = "styleTag";
                break;
            case 23:
            case 24:
                objArr[1] = "font";
                break;
            case 25:
                objArr[1] = "body";
                break;
            case 26:
                objArr[1] = Method.HTML;
                break;
            case 27:
                objArr[1] = "nbsp";
                break;
            case 29:
                objArr[1] = "empty";
                break;
            case 36:
                objArr[1] = "toString";
                break;
            case 38:
                objArr[1] = "fragment";
                break;
            case 39:
            case 40:
                objArr[1] = "toFragment";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 36:
            case 38:
            case 39:
            case 40:
                break;
            case 7:
                objArr[2] = "tag";
                break;
            case 9:
                objArr[2] = "div";
                break;
            case 12:
                objArr[2] = "span";
                break;
            case 20:
                objArr[2] = "styleTag";
                break;
            case 22:
                objArr[2] = "font";
                break;
            case 28:
                objArr[2] = "text";
                break;
            case 30:
                objArr[2] = Options.TYPE_RAW;
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "link";
                break;
            case 35:
                objArr[2] = "htmlEntity";
                break;
            case 37:
                objArr[2] = "fragment";
                break;
            default:
                objArr[2] = "wrapWith";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 36:
            case 38:
            case 39:
            case 40:
                throw new IllegalStateException(str2);
            case 7:
            case 9:
            case 12:
            case 20:
            case 22:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public abstract void appendTo(StringBuilder sb);

    private static class Empty extends HtmlChunk {
        private static final Empty INSTANCE = new Empty();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "builder", "org/jetbrains/kotlin/com/intellij/openapi/util/text/HtmlChunk$Empty", "appendTo"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.text.HtmlChunk
        public void appendTo(StringBuilder sb) {
            if (sb == null) {
                $$$reportNull$$$0(0);
            }
        }

        private Empty() {
        }
    }

    private static class Text extends HtmlChunk {
        private final String myContent;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "builder", "org/jetbrains/kotlin/com/intellij/openapi/util/text/HtmlChunk$Text", "appendTo"));
        }

        private Text(String str) {
            this.myContent = str;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.text.HtmlChunk
        public void appendTo(StringBuilder sb) {
            if (sb == null) {
                $$$reportNull$$$0(0);
            }
            sb.append(StringUtil.escapeXmlEntities(this.myContent).replaceAll("\n", "<br/>"));
        }
    }

    public static class Element extends HtmlChunk {
        private final UnmodifiableHashMap<String, String> myAttributes;
        private final List<HtmlChunk> myChildren;
        private final String myTagName;
        private static final Element HEAD = tag("head");
        private static final Element BODY = tag("body");
        private static final Element HTML = tag(Method.HTML);
        private static final Element BR = tag(CompressorStreamFactory.BROTLI);
        private static final Element UL = tag("ul");
        private static final Element LI = tag("li");
        private static final Element HR = tag(LanguageCodeUtil.HR);
        private static final Element P = tag("p");
        private static final Element DIV = tag("div");
        private static final Element SPAN = tag("span");

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 4 || i == 6) ? 2 : 3];
            switch (i) {
                case 1:
                case 2:
                case 4:
                case 6:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/HtmlChunk$Element";
                    break;
                case 3:
                case 5:
                    objArr[0] = "text";
                    break;
                case 7:
                case 8:
                    objArr[0] = "chunks";
                    break;
                case 9:
                    objArr[0] = "chunk";
                    break;
                default:
                    objArr[0] = "builder";
                    break;
            }
            if (i == 1) {
                objArr[1] = "style";
            } else if (i == 2) {
                objArr[1] = "setClass";
            } else if (i == 4) {
                objArr[1] = "addText";
            } else if (i != 6) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/HtmlChunk$Element";
            } else {
                objArr[1] = "addRaw";
            }
            switch (i) {
                case 1:
                case 2:
                case 4:
                case 6:
                    break;
                case 3:
                    objArr[2] = "addText";
                    break;
                case 5:
                    objArr[2] = "addRaw";
                    break;
                case 7:
                case 8:
                    objArr[2] = "children";
                    break;
                case 9:
                    objArr[2] = "child";
                    break;
                default:
                    objArr[2] = "appendTo";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 4 && i != 6) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        private Element(String str, UnmodifiableHashMap<String, String> unmodifiableHashMap, List<HtmlChunk> list) {
            this.myTagName = str;
            this.myAttributes = unmodifiableHashMap;
            this.myChildren = list;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.text.HtmlChunk
        public void appendTo(final StringBuilder sb) {
            if (sb == null) {
                $$$reportNull$$$0(0);
            }
            sb.append('<').append(this.myTagName);
            this.myAttributes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.HtmlChunk$Element$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Map.Entry entry = (Map.Entry) obj;
                    sb.append(io.netty.util.internal.StringUtil.SPACE).append((String) entry.getKey()).append("=\"").append(StringUtil.escapeXmlEntities((String) entry.getValue())).append('\"');
                }
            });
            if (this.myChildren.isEmpty()) {
                sb.append("/>");
                return;
            }
            sb.append(">");
            Iterator<HtmlChunk> it = this.myChildren.iterator();
            while (it.hasNext()) {
                it.next().appendTo(sb);
            }
            sb.append("</").append(this.myTagName).append(">");
        }

        public Element addText(String str) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            Element elementChild = child(text(str));
            if (elementChild == null) {
                $$$reportNull$$$0(4);
            }
            return elementChild;
        }

        public Element child(HtmlChunk htmlChunk) {
            if (htmlChunk == null) {
                $$$reportNull$$$0(9);
            }
            if (this.myChildren.isEmpty()) {
                return new Element(this.myTagName, this.myAttributes, Collections.singletonList(htmlChunk));
            }
            ArrayList arrayList = new ArrayList(this.myChildren.size() + 1);
            arrayList.addAll(this.myChildren);
            arrayList.add(htmlChunk);
            return new Element(this.myTagName, this.myAttributes, arrayList);
        }
    }

    public Element wrapWith(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return new Element(str, UnmodifiableHashMap.empty(), Collections.singletonList(this));
    }

    public static Element tag(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return new Element(str, UnmodifiableHashMap.empty(), Collections.emptyList());
    }

    public static HtmlChunk text(String str) {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        return str.isEmpty() ? empty() : new Text(str);
    }

    public static HtmlChunk empty() {
        Empty empty = Empty.INSTANCE;
        if (empty == null) {
            $$$reportNull$$$0(29);
        }
        return empty;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendTo(sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(36);
        }
        return string;
    }
}
