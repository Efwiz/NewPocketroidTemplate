package org.jetbrains.kotlin.modules;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.build.JvmSourceRoot;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.config.IncrementalCompilation;
import org.jetbrains.kotlin.utils.Printer;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: KotlinModuleXmlBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u0000 (2\u00020\u0001:\u0001(B\u0005¢\u0006\u0002\u0010\u0002J\u0086\u0001\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00042\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0007J\u008c\u0001\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00042\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001b\u001a\u00020\u0004J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\fH\u0002J\u0010\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u0010H\u0002J\u0018\u0010#\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\fH\u0002J,\u0010$\u001a\u00020\u001f2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u00192\u0006\u0010\u001b\u001a\u00020\u0004H\u0002J\u0016\u0010&\u001a\u00020\u001f2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00120\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bj\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/modules/KotlinModuleXmlBuilder;", "", "()V", ES6Iterator.DONE_PROPERTY, "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "xml", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "addModule", "moduleName", "", ModuleXmlParser.OUTPUT_DIR, "sourceFiles", "", "Ljava/io/File;", ModuleXmlParser.JAVA_SOURCE_ROOTS, "Lorg/jetbrains/kotlin/build/JvmSourceRoot;", "classpathRoots", "commonSourceFiles", ModuleXmlParser.MODULAR_JDK_ROOT, "targetTypeId", "isTests", "directoriesToFilterOut", "", "friendDirs", "isIncrementalCompilation", "asText", "", "closeTag", "", "tag", "getEscapedPath", "sourceFile", "openTag", "processClasspath", "files", "processJavaSourceRoots", PsiTreeChangeEvent.PROP_ROOTS, "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinModuleXmlBuilder {
    private static final Companion Companion = new Companion(null);
    private static final Regex xmlEscapeRegex;
    private static final Map<String, String> xmlEscapeReplacement;
    private boolean done;
    private final Printer p;
    private final StringBuilder xml;

    public KotlinModuleXmlBuilder() {
        StringBuilder sb = new StringBuilder();
        this.xml = sb;
        Printer printer = new Printer(sb);
        this.p = printer;
        openTag(printer, "modules");
    }

    @Deprecated(message = "State of IC should be set explicitly")
    public final KotlinModuleXmlBuilder addModule(String moduleName, String outputDir, Iterable<? extends File> sourceFiles, Iterable<JvmSourceRoot> javaSourceRoots, Iterable<? extends File> classpathRoots, Iterable<? extends File> commonSourceFiles, File modularJdkRoot, String targetTypeId, boolean isTests, Set<? extends File> directoriesToFilterOut, Iterable<? extends File> friendDirs) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(javaSourceRoots, "javaSourceRoots");
        Intrinsics.checkNotNullParameter(classpathRoots, "classpathRoots");
        Intrinsics.checkNotNullParameter(commonSourceFiles, "commonSourceFiles");
        Intrinsics.checkNotNullParameter(targetTypeId, "targetTypeId");
        Intrinsics.checkNotNullParameter(directoriesToFilterOut, "directoriesToFilterOut");
        Intrinsics.checkNotNullParameter(friendDirs, "friendDirs");
        return addModule(moduleName, outputDir, sourceFiles, javaSourceRoots, classpathRoots, commonSourceFiles, modularJdkRoot, targetTypeId, isTests, directoriesToFilterOut, friendDirs, IncrementalCompilation.isEnabledForJvm());
    }

    public final KotlinModuleXmlBuilder addModule(String moduleName, String outputDir, Iterable<? extends File> sourceFiles, Iterable<JvmSourceRoot> javaSourceRoots, Iterable<? extends File> classpathRoots, Iterable<? extends File> commonSourceFiles, File modularJdkRoot, String targetTypeId, boolean isTests, Set<? extends File> directoriesToFilterOut, Iterable<? extends File> friendDirs, boolean isIncrementalCompilation) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(javaSourceRoots, "javaSourceRoots");
        Intrinsics.checkNotNullParameter(classpathRoots, "classpathRoots");
        Intrinsics.checkNotNullParameter(commonSourceFiles, "commonSourceFiles");
        Intrinsics.checkNotNullParameter(targetTypeId, "targetTypeId");
        Intrinsics.checkNotNullParameter(directoriesToFilterOut, "directoriesToFilterOut");
        Intrinsics.checkNotNullParameter(friendDirs, "friendDirs");
        this.p.println("<!-- Module script for " + (isTests ? "tests" : "production") + " -->");
        Printer printer = this.p;
        Companion companion = Companion;
        printer.println("<", "module", " ", "name", "=\"", companion.escapeXml(moduleName), "\" ", ModuleXmlParser.TYPE, "=\"", companion.escapeXml(targetTypeId), "\" ", ModuleXmlParser.OUTPUT_DIR, "=\"", getEscapedPath(new File(outputDir)), "\">");
        this.p.pushIndent();
        Iterator<? extends File> it = friendDirs.iterator();
        while (it.hasNext()) {
            this.p.println("<", ModuleXmlParser.FRIEND_DIR, " ", "path", "=\"", getEscapedPath(it.next()), "\"/>");
        }
        Iterator<? extends File> it2 = sourceFiles.iterator();
        while (it2.hasNext()) {
            this.p.println("<", "sources", " ", "path", "=\"", getEscapedPath(it2.next()), "\"/>");
        }
        Iterator<? extends File> it3 = commonSourceFiles.iterator();
        while (it3.hasNext()) {
            this.p.println("<", ModuleXmlParser.COMMON_SOURCES, " ", "path", "=\"", getEscapedPath(it3.next()), "\"/>");
        }
        processJavaSourceRoots(javaSourceRoots);
        processClasspath(classpathRoots, directoriesToFilterOut, isIncrementalCompilation);
        if (modularJdkRoot != null) {
            this.p.println("<", ModuleXmlParser.MODULAR_JDK_ROOT, " ", "path", "=\"", getEscapedPath(modularJdkRoot), "\"/>");
        }
        closeTag(this.p, "module");
        return this;
    }

    private final void processClasspath(Iterable<? extends File> files, Set<? extends File> directoriesToFilterOut, boolean isIncrementalCompilation) {
        this.p.println("<!-- Classpath -->");
        for (File file : files) {
            boolean z = directoriesToFilterOut.contains(file) && !isIncrementalCompilation;
            if (z) {
                this.p.println("<!-- Output directory, commented out -->");
                this.p.println("<!-- ");
                this.p.pushIndent();
            }
            this.p.println("<", ModuleXmlParser.CLASSPATH, " ", "path", "=\"", getEscapedPath(file), "\"/>");
            if (z) {
                this.p.popIndent();
                this.p.println("-->");
            }
        }
    }

    private final void processJavaSourceRoots(Iterable<JvmSourceRoot> roots) {
        this.p.println("<!-- Java source roots -->");
        for (JvmSourceRoot jvmSourceRoot : roots) {
            this.p.print("<");
            this.p.printWithNoIndent(ModuleXmlParser.JAVA_SOURCE_ROOTS, " ", "path", "=\"", getEscapedPath(jvmSourceRoot.getFile()), "\"");
            if (jvmSourceRoot.getPackagePrefix() != null) {
                this.p.printWithNoIndent(" ", ModuleXmlParser.JAVA_SOURCE_PACKAGE_PREFIX, "=\"", jvmSourceRoot.getPackagePrefix(), "\"");
            }
            this.p.printWithNoIndent("/>");
            this.p.println(new Object[0]);
        }
    }

    public final CharSequence asText() {
        if (!this.done) {
            closeTag(this.p, "modules");
            this.done = true;
        }
        return this.xml;
    }

    private final void openTag(Printer p, String tag) {
        p.println("<" + tag + '>');
        p.pushIndent();
    }

    private final void closeTag(Printer p, String tag) {
        p.popIndent();
        p.println("</" + tag + '>');
    }

    private final String getEscapedPath(File sourceFile) {
        return Companion.escapeXml(FilesKt.getInvariantSeparatorsPath(sourceFile));
    }

    /* JADX INFO: compiled from: KotlinModuleXmlBuilder.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/modules/KotlinModuleXmlBuilder$Companion;", "", "()V", "xmlEscapeRegex", "Lkotlin/text/Regex;", "xmlEscapeReplacement", "", "", "escapeXml", "string", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String escapeXml(String string) {
            return KotlinModuleXmlBuilder.xmlEscapeRegex.replace(string, new Function1<MatchResult, CharSequence>() { // from class: org.jetbrains.kotlin.modules.KotlinModuleXmlBuilder$Companion$escapeXml$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(MatchResult it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return (CharSequence) MapsKt.getValue(KotlinModuleXmlBuilder.xmlEscapeReplacement, it.getValue());
                }
            });
        }
    }

    static {
        Map<String, String> mapMapOf = MapsKt.mapOf(TuplesKt.to("<", "&lt;"), TuplesKt.to(">", "&gt;"), TuplesKt.to(ContainerUtils.FIELD_DELIMITER, "&amp;"), TuplesKt.to(FormatHelper.QUOTE, "&#39;"), TuplesKt.to("\"", "&quot;"));
        xmlEscapeReplacement = mapMapOf;
        xmlEscapeRegex = new Regex(CollectionsKt.joinToString$default(mapMapOf.keySet(), "|", "(?:", Constant.AFTER_QUTO, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.modules.KotlinModuleXmlBuilder$Companion$xmlEscapeRegex$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Regex.INSTANCE.escape(it);
            }
        }, 24, null));
    }
}
