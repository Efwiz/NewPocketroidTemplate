package org.jetbrains.kotlin.incremental.js;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.container.SpecTypePair;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IncrementalResultsConsumer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u0014\u001a\u00020\rH\u0016J0\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u00072\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*H\u0016J\u0016\u0010,\u001a\u00020#2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016JZ\u0010.\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\r2\u0006\u00100\u001a\u00020\r2\u0006\u00101\u001a\u00020\r2\u0006\u00102\u001a\u00020\r2\u0006\u00103\u001a\u00020\r2\u0006\u00104\u001a\u00020\r2\u0006\u00105\u001a\u00020\r2\u0006\u00106\u001a\u00020\r2\b\u00107\u001a\u0004\u0018\u00010\rH\u0016J\u0018\u00108\u001a\u00020#2\u0006\u00109\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\rH\u0016J(\u0010;\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020\r2\u0006\u0010>\u001a\u00020\rH\u0016RB\u0010\u0003\u001a6\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0004j\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006`\tX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\u0004j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r`\tX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\r@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R)\u0010\u0017\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00190\u00180\u00188F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u001d\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\u00188F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001bR\u001d\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\u00188F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001bR\u001d\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u00188F¢\u0006\u0006\u001a\u0004\b!\u0010\u001b¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/IncrementalResultsConsumerImpl;", "Lorg/jetbrains/kotlin/incremental/js/IncrementalResultsConsumer;", "()V", "_deferInlineFuncs", "Ljava/util/HashMap;", "Ljava/io/File;", "", "", "Lorg/jetbrains/kotlin/incremental/js/FunctionWithSourceInfo;", "Lkotlin/collections/HashMap;", "_irFileData", "Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "_packageMetadata", "", "_packageParts", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "_processedInlineFuncs", "", "Lorg/jetbrains/kotlin/incremental/js/JsInlineFunctionHash;", "<set-?>", "headerMetadata", "getHeaderMetadata", "()[B", "inlineFunctions", "", "", "getInlineFunctions", "()Ljava/util/Map;", "irFileData", "getIrFileData", "packageMetadata", "getPackageMetadata", "packageParts", "getPackageParts", "processHeader", "", "processInlineFunction", "sourceFile", "fqName", "inlineFunction", "", Constant.LINE, "", "column", "processInlineFunctions", "functions", "processIrFile", "fileData", SpecTypePair.NAME_types, "signatures", "strings", "declarations", "bodies", "fqn", "fileMetadata", "debugInfo", "processPackageMetadata", "packageName", "metadata", "processPackagePart", "packagePartMetadata", "binaryAst", "inlineData", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IncrementalResultsConsumerImpl implements IncrementalResultsConsumer {
    private Collection<JsInlineFunctionHash> _processedInlineFuncs;
    private byte[] headerMetadata;
    private final HashMap<File, TranslationResultValue> _packageParts = new HashMap<>();
    private final HashMap<File, Map<String, FunctionWithSourceInfo>> _deferInlineFuncs = new HashMap<>();
    private final HashMap<String, byte[]> _packageMetadata = new HashMap<>();
    private final HashMap<File, IrTranslationResultValue> _irFileData = new HashMap<>();

    public final byte[] getHeaderMetadata() {
        byte[] bArr = this.headerMetadata;
        if (bArr != null) {
            return bArr;
        }
        Intrinsics.throwUninitializedPropertyAccessException("headerMetadata");
        return null;
    }

    public final Map<File, TranslationResultValue> getPackageParts() {
        return this._packageParts;
    }

    public final Map<File, Map<String, Long>> getInlineFunctions() {
        HashMap map = new HashMap(this._deferInlineFuncs.size());
        for (Map.Entry<File, Map<String, FunctionWithSourceInfo>> entry : this._deferInlineFuncs.entrySet()) {
            File key = entry.getKey();
            Map<String, FunctionWithSourceInfo> value = entry.getValue();
            HashMap map2 = new HashMap(value.size());
            for (Map.Entry<String, FunctionWithSourceInfo> entry2 : value.entrySet()) {
                map2.put(entry2.getKey(), Long.valueOf(entry2.getValue().getMd5()));
            }
            map.put(key, map2);
        }
        Collection<JsInlineFunctionHash> collection = this._processedInlineFuncs;
        if (collection != null) {
            for (JsInlineFunctionHash jsInlineFunctionHash : collection) {
                HashMap map3 = map;
                File file = new File(jsInlineFunctionHash.getSourceFilePath());
                Object obj = map3.get(file);
                if (obj == null) {
                    obj = (Map) new LinkedHashMap();
                    map3.put(file, obj);
                }
                ((Map) obj).put(jsInlineFunctionHash.getFqName(), Long.valueOf(jsInlineFunctionHash.getInlineFunctionMd5Hash()));
            }
        }
        return map;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processHeader(byte[] headerMetadata) {
        Intrinsics.checkNotNullParameter(headerMetadata, "headerMetadata");
        this.headerMetadata = headerMetadata;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processPackagePart(File sourceFile, byte[] packagePartMetadata, byte[] binaryAst, byte[] inlineData) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(packagePartMetadata, "packagePartMetadata");
        Intrinsics.checkNotNullParameter(binaryAst, "binaryAst");
        Intrinsics.checkNotNullParameter(inlineData, "inlineData");
        this._packageParts.put(sourceFile, new TranslationResultValue(packagePartMetadata, binaryAst, inlineData));
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    /* JADX INFO: renamed from: processInlineFunctions */
    public void mo6751processInlineFunctions(Collection<JsInlineFunctionHash> functions) {
        Intrinsics.checkNotNullParameter(functions, "functions");
        if (this._processedInlineFuncs != null) {
            throw new IllegalStateException("Check failed.".toString());
        }
        this._processedInlineFuncs = functions;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processInlineFunction(File sourceFile, String fqName, Object inlineFunction, int line, int column) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(inlineFunction, "inlineFunction");
        HashMap<File, Map<String, FunctionWithSourceInfo>> map = this._deferInlineFuncs;
        HashMap map2 = map.get(sourceFile);
        if (map2 == null) {
            map2 = new HashMap();
            map.put(sourceFile, map2);
        }
        map2.put(fqName, new FunctionWithSourceInfo(inlineFunction, line, column));
    }

    public final Map<String, byte[]> getPackageMetadata() {
        return this._packageMetadata;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processPackageMetadata(String packageName, byte[] metadata) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this._packageMetadata.put(packageName, metadata);
    }

    public final Map<File, IrTranslationResultValue> getIrFileData() {
        return this._irFileData;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processIrFile(File sourceFile, byte[] fileData, byte[] types, byte[] signatures, byte[] strings, byte[] declarations, byte[] bodies, byte[] fqn, byte[] fileMetadata, byte[] debugInfo) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(fileData, "fileData");
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(bodies, "bodies");
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        Intrinsics.checkNotNullParameter(fileMetadata, "fileMetadata");
        this._irFileData.put(sourceFile, new IrTranslationResultValue(fileData, types, signatures, strings, declarations, bodies, fqn, fileMetadata, debugInfo));
    }
}
