package org.jetbrains.kotlin.daemon;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.container.SpecTypePair;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.incremental.js.FunctionWithSourceInfo;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.incremental.js.JsInlineFunctionHash;

/* JADX INFO: compiled from: RemoteIncrementalResultsConsumer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001:\u00015B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J0\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016J\u0016\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0016JZ\u0010$\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u00142\u0006\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020\u00142\u0006\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020\u00142\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00142\b\u0010-\u001a\u0004\u0018\u00010\u0014H\u0016J\u0018\u0010.\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u0014H\u0016J(\u00101\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u00102\u001a\u00020\u00142\u0006\u00103\u001a\u00020\u00142\u0006\u00104\u001a\u00020\u0014H\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteIncrementalResultsConsumer;", "Lorg/jetbrains/kotlin/incremental/js/IncrementalResultsConsumer;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "eventManager", "Lorg/jetbrains/kotlin/daemon/EventManager;", "rpcProfiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;Lorg/jetbrains/kotlin/daemon/EventManager;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "deferInlineFuncs", "", "Lorg/jetbrains/kotlin/daemon/RemoteIncrementalResultsConsumer$JsInlineFunction;", "getFacade", "()Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "getRpcProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "flush", "", "processHeader", "headerMetadata", "", "processInlineFunction", "sourceFile", "Ljava/io/File;", "fqName", "", "inlineFunction", "", Constant.LINE, "", "column", "processInlineFunctions", "", "functions", "", "Lorg/jetbrains/kotlin/incremental/js/JsInlineFunctionHash;", "processIrFile", "fileData", SpecTypePair.NAME_types, "signatures", "strings", "declarations", "bodies", "fqn", "fileMetadata", "debugInfo", "processPackageMetadata", "packageName", "metadata", "processPackagePart", "packagePartMetadata", "binaryAst", "inlineData", "JsInlineFunction", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteIncrementalResultsConsumer implements IncrementalResultsConsumer {
    private final List<JsInlineFunction> deferInlineFuncs;
    private final CompilerCallbackServicesFacade facade;
    private final Profiler rpcProfiler;

    public RemoteIncrementalResultsConsumer(CompilerCallbackServicesFacade facade, EventManager eventManager, Profiler rpcProfiler) {
        Intrinsics.checkNotNullParameter(facade, "facade");
        Intrinsics.checkNotNullParameter(eventManager, "eventManager");
        Intrinsics.checkNotNullParameter(rpcProfiler, "rpcProfiler");
        this.facade = facade;
        this.rpcProfiler = rpcProfiler;
        eventManager.onCompilationFinished(new AnonymousClass1(this));
        this.deferInlineFuncs = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    /* JADX INFO: renamed from: processInlineFunctions, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ void mo6751processInlineFunctions(Collection collection) {
        processInlineFunctions((Collection<JsInlineFunctionHash>) collection);
    }

    public final CompilerCallbackServicesFacade getFacade() {
        return this.facade;
    }

    public final Profiler getRpcProfiler() {
        return this.rpcProfiler;
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
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.daemon.RemoteIncrementalResultsConsumer$1, reason: invalid class name */
    /* JADX INFO: compiled from: RemoteIncrementalResultsConsumer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function0<Unit> {
        AnonymousClass1(Object obj) {
            super(0, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "flush()V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "flush";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(RemoteIncrementalResultsConsumer.class);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() throws RemoteException {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() throws RemoteException {
            ((RemoteIncrementalResultsConsumer) this.receiver).flush();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processHeader(byte[] headerMetadata) throws RemoteException {
        Intrinsics.checkNotNullParameter(headerMetadata, "headerMetadata");
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        this.facade.incrementalResultsConsumer_processHeader(headerMetadata);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processPackagePart(File sourceFile, byte[] packagePartMetadata, byte[] binaryAst, byte[] inlineData) throws RemoteException {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(packagePartMetadata, "packagePartMetadata");
        Intrinsics.checkNotNullParameter(binaryAst, "binaryAst");
        Intrinsics.checkNotNullParameter(inlineData, "inlineData");
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        CompilerCallbackServicesFacade compilerCallbackServicesFacade = this.facade;
        String path = sourceFile.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        compilerCallbackServicesFacade.incrementalResultsConsumer_processPackagePart(path, packagePartMetadata, binaryAst, inlineData);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }

    /* JADX INFO: compiled from: RemoteIncrementalResultsConsumer.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteIncrementalResultsConsumer$JsInlineFunction;", "", "sourceFilePath", "", "fqName", "inlineFunction", "Lorg/jetbrains/kotlin/incremental/js/FunctionWithSourceInfo;", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/incremental/js/FunctionWithSourceInfo;)V", "getFqName", "()Ljava/lang/String;", "getInlineFunction", "()Lorg/jetbrains/kotlin/incremental/js/FunctionWithSourceInfo;", "getSourceFilePath", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JsInlineFunction {
        private final String fqName;
        private final FunctionWithSourceInfo inlineFunction;
        private final String sourceFilePath;

        public JsInlineFunction(String sourceFilePath, String fqName, FunctionWithSourceInfo inlineFunction) {
            Intrinsics.checkNotNullParameter(sourceFilePath, "sourceFilePath");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(inlineFunction, "inlineFunction");
            this.sourceFilePath = sourceFilePath;
            this.fqName = fqName;
            this.inlineFunction = inlineFunction;
        }

        public final String getFqName() {
            return this.fqName;
        }

        public final FunctionWithSourceInfo getInlineFunction() {
            return this.inlineFunction;
        }

        public final String getSourceFilePath() {
            return this.sourceFilePath;
        }
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processInlineFunction(File sourceFile, String fqName, Object inlineFunction, int line, int column) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(inlineFunction, "inlineFunction");
        List<JsInlineFunction> list = this.deferInlineFuncs;
        String path = sourceFile.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        list.add(new JsInlineFunction(path, fqName, new FunctionWithSourceInfo(inlineFunction, line, column)));
    }

    public Void processInlineFunctions(Collection<JsInlineFunctionHash> functions) {
        Intrinsics.checkNotNullParameter(functions, "functions");
        throw new IllegalStateException("Should not be called in Daemon Server".toString());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer
    public void processPackageMetadata(String packageName, byte[] metadata) throws RemoteException {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        this.facade.incrementalResultsConsumer_processPackageMetadata(packageName, metadata);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    public final void flush() throws RemoteException {
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        CompilerCallbackServicesFacade compilerCallbackServicesFacade = this.facade;
        List<JsInlineFunction> list = this.deferInlineFuncs;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (JsInlineFunction jsInlineFunction : list) {
            arrayList.add(new JsInlineFunctionHash(jsInlineFunction.getSourceFilePath(), jsInlineFunction.getFqName(), jsInlineFunction.getInlineFunction().getMd5()));
        }
        compilerCallbackServicesFacade.incrementalResultsConsumer_processInlineFunctions(arrayList);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }
}
