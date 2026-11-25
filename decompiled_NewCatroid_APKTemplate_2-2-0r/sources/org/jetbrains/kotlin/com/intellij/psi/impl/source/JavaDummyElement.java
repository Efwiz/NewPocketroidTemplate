package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class JavaDummyElement extends FileElement {
    private final boolean myConsumeAll;
    private final LanguageLevel myLanguageLevel;
    private final JavaParserUtil.ParserWrapper myParser;
    private Throwable myParserError;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = FirebaseAnalytics.Param.LEVEL;
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaDummyElement";
        } else {
            objArr[0] = "parser";
        }
        if (i == 4) {
            objArr[1] = "getParser";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaDummyElement";
        } else {
            objArr[1] = "getLanguageLevel";
        }
        if (i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JavaDummyElement(CharSequence charSequence, JavaParserUtil.ParserWrapper parserWrapper, LanguageLevel languageLevel) {
        this(charSequence, parserWrapper, languageLevel, false);
        if (parserWrapper == null) {
            $$$reportNull$$$0(0);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaDummyElement(CharSequence charSequence, JavaParserUtil.ParserWrapper parserWrapper, LanguageLevel languageLevel, boolean z) {
        super(JavaElementType.DUMMY_ELEMENT, charSequence);
        if (parserWrapper == null) {
            $$$reportNull$$$0(2);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(3);
        }
        this.myParser = parserWrapper;
        this.myLanguageLevel = languageLevel;
        this.myConsumeAll = z;
    }

    public JavaParserUtil.ParserWrapper getParser() {
        JavaParserUtil.ParserWrapper parserWrapper = this.myParser;
        if (parserWrapper == null) {
            $$$reportNull$$$0(4);
        }
        return parserWrapper;
    }

    public boolean consumeAll() {
        return this.myConsumeAll;
    }

    public LanguageLevel getLanguageLevel() {
        LanguageLevel languageLevel = this.myLanguageLevel;
        if (languageLevel == null) {
            $$$reportNull$$$0(5);
        }
        return languageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getFirstChildNode() {
        try {
            return super.getFirstChildNode();
        } catch (AssertionError e) {
            this.myParserError = e;
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getLastChildNode() {
        try {
            return super.getLastChildNode();
        } catch (AssertionError e) {
            this.myParserError = e;
            return null;
        }
    }

    public Throwable getParserError() {
        return this.myParserError;
    }
}
