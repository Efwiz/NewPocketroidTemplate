package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.stubs.KotlinAnnotationEntryStub;
import org.jetbrains.kotlin.psi.stubs.KotlinClassStub;
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub;
import org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub;
import org.jetbrains.kotlin.psi.stubs.KotlinObjectStub;
import org.jetbrains.kotlin.psi.stubs.KotlinParameterStub;
import org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub;
import org.jetbrains.kotlin.psi.stubs.KotlinScriptStub;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeAliasStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFileStubImpl;

/* JADX INFO: compiled from: StubIndexService.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 !2\u00020\u0001:\u0001!B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00182\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u001a2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u001e2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020 H\u0016¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/StubIndexService;", "", "()V", "createFileStub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinFileStub;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "deserializeFileStub", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "indexAnnotation", "", "stub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinAnnotationEntryStub;", "sink", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IndexSink;", "indexClass", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassStub;", "indexFile", "indexFunction", "Lorg/jetbrains/kotlin/psi/stubs/KotlinFunctionStub;", "indexObject", "Lorg/jetbrains/kotlin/psi/stubs/KotlinObjectStub;", "indexParameter", "Lorg/jetbrains/kotlin/psi/stubs/KotlinParameterStub;", "indexProperty", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPropertyStub;", "indexScript", "Lorg/jetbrains/kotlin/psi/stubs/KotlinScriptStub;", "indexTypeAlias", "Lorg/jetbrains/kotlin/psi/stubs/KotlinTypeAliasStub;", "serializeFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class StubIndexService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final StubIndexService NO_INDEX = new StubIndexService();

    @JvmStatic
    public static final StubIndexService getInstance() {
        return INSTANCE.getInstance();
    }

    public void indexAnnotation(KotlinAnnotationEntryStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexClass(KotlinClassStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexFile(KotlinFileStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexFunction(KotlinFunctionStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexObject(KotlinObjectStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexParameter(KotlinParameterStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexProperty(KotlinPropertyStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexScript(KotlinScriptStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    public void indexTypeAlias(KotlinTypeAliasStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
    }

    protected StubIndexService() {
    }

    public KotlinFileStub createFileStub(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String strAsString = file.getPackageFqNameByTree().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return new KotlinFileStubImpl(file, strAsString, file.isScriptByTree());
    }

    public void serializeFileStub(KotlinFileStub stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeName(stub.getPackageFqName().asString());
        dataStream.writeBoolean(stub.getIsScript());
    }

    public KotlinFileStub deserializeFileStub(StubInputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        StringRef name2 = dataStream.readName();
        boolean z = dataStream.readBoolean();
        Intrinsics.checkNotNull(name2);
        String string = name2.getString();
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return new KotlinFileStubImpl(null, string, z);
    }

    /* JADX INFO: compiled from: StubIndexService.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/StubIndexService$Companion;", "", "()V", "NO_INDEX", "Lorg/jetbrains/kotlin/psi/stubs/elements/StubIndexService;", "getInstance", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final StubIndexService getInstance() {
            StubIndexService stubIndexService = (StubIndexService) ApplicationManager.getApplication().getService(StubIndexService.class);
            return stubIndexService == null ? StubIndexService.NO_INDEX : stubIndexService;
        }
    }
}
