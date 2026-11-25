package org.jetbrains.kotlin.cli.common.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.backend.common.output.OutputFileCollection;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.OutputMessageUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;

/* JADX INFO: compiled from: outputUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001aR\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042>\u0010\u0005\u001a:\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0006\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u0012\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0011"}, d2 = {"writeAll", "", "Lorg/jetbrains/kotlin/backend/common/output/OutputFileCollection;", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "report", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "sources", "output", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "reportOutputFiles", "", "writeAllTo", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OutputUtilsKt {
    public static final void writeAll(OutputFileCollection outputFileCollection, File outputDir, Function2<? super List<? extends File>, ? super File, Unit> function2) throws IOException {
        Intrinsics.checkNotNullParameter(outputFileCollection, "<this>");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        for (OutputFile outputFile : outputFileCollection.asList()) {
            List<File> sourceFiles = outputFile.getSourceFiles();
            File file = new File(outputDir, outputFile.getRelativePath());
            if (function2 != null) {
                function2.invoke(sourceFiles, file);
            }
            try {
                FileUtil.writeToFile(file, outputFile.getContent());
            } catch (FileNotFoundException e) {
                if (outputDir.isDirectory()) {
                    throw new NoPermissionException("error while writing " + file + " (Permission denied)", e);
                }
                throw e;
            }
        }
    }

    public static final void writeAllTo(OutputFileCollection outputFileCollection, File outputDir) throws IOException {
        Intrinsics.checkNotNullParameter(outputFileCollection, "<this>");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        writeAll(outputFileCollection, outputDir, null);
    }

    public static final void writeAll(OutputFileCollection outputFileCollection, File outputDir, final MessageCollector messageCollector, boolean z) {
        Intrinsics.checkNotNullParameter(outputFileCollection, "<this>");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        try {
            if (!z) {
                writeAllTo(outputFileCollection, outputDir);
            } else {
                writeAll(outputFileCollection, outputDir, new Function2<List<? extends File>, File, Unit>() { // from class: org.jetbrains.kotlin.cli.common.output.OutputUtilsKt.writeAll.1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(List<? extends File> list, File file) {
                        invoke2(list, file);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(List<? extends File> sources, File output) {
                        Intrinsics.checkNotNullParameter(sources, "sources");
                        Intrinsics.checkNotNullParameter(output, "output");
                        MessageCollector messageCollector2 = messageCollector;
                        CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.OUTPUT;
                        String outputMessage = OutputMessageUtil.formatOutputMessage(sources, output);
                        Intrinsics.checkNotNullExpressionValue(outputMessage, "formatOutputMessage(...)");
                        MessageCollector.report$default(messageCollector2, compilerMessageSeverity, outputMessage, null, 4, null);
                    }
                });
            }
        } catch (FileNotFoundException unused) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "directory not found: " + outputDir, null, 4, null);
        } catch (NoPermissionException e) {
            CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
            String message = e.getMessage();
            Intrinsics.checkNotNull(message);
            MessageCollector.report$default(messageCollector, compilerMessageSeverity, message, null, 4, null);
        }
    }
}
