package org.jetbrains.kotlin.incremental.js;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.container.SpecTypePair;
import java.io.File;
import java.util.Collection;
import kotlin.Metadata;

/* JADX INFO: compiled from: IncrementalResultsConsumer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0012\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J0\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH&J\u0016\u0010\u000f\u001a\u00020\u00032\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H&JZ\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u00052\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H&J\u0018\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\u0005H&J(\u0010 \u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006$À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/IncrementalResultsConsumer;", "", "processHeader", "", "headerMetadata", "", "processInlineFunction", "sourceFile", "Ljava/io/File;", "fqName", "", "inlineFunction", Constant.LINE, "", "column", "processInlineFunctions", "functions", "", "Lorg/jetbrains/kotlin/incremental/js/JsInlineFunctionHash;", "processIrFile", "fileData", SpecTypePair.NAME_types, "signatures", "strings", "declarations", "bodies", "fqn", "fileMetadata", "debugInfo", "processPackageMetadata", "packageName", "metadata", "processPackagePart", "packagePartMetadata", "binaryAst", "inlineData", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IncrementalResultsConsumer {
    void processHeader(byte[] headerMetadata);

    void processInlineFunction(File sourceFile, String fqName, Object inlineFunction, int line, int column);

    /* JADX INFO: renamed from: processInlineFunctions */
    void mo6751processInlineFunctions(Collection<JsInlineFunctionHash> functions);

    void processIrFile(File sourceFile, byte[] fileData, byte[] types, byte[] signatures, byte[] strings, byte[] declarations, byte[] bodies, byte[] fqn, byte[] fileMetadata, byte[] debugInfo);

    void processPackageMetadata(String packageName, byte[] metadata);

    void processPackagePart(File sourceFile, byte[] packagePartMetadata, byte[] binaryAst, byte[] inlineData);
}
