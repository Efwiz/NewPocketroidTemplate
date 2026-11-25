package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.patterns.compiler.PatternCompilerFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Tag;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Text;

/* JADX INFO: loaded from: classes3.dex */
@Tag("pattern")
public class ElementPatternBean {

    @Text
    public String text;

    @Attribute(ModuleXmlParser.TYPE)
    public String type;

    public ElementPattern<PsiElement> compilePattern() {
        return PatternCompilerFactory.getFactory().getPatternCompiler(this.type).compileElementPattern(this.text);
    }
}
