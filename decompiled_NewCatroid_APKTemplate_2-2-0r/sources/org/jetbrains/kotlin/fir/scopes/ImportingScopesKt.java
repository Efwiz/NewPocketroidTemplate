package org.jetbrains.kotlin.fir.scopes;

import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.scopes.impl.DefaultImportPriority;
import org.jetbrains.kotlin.fir.scopes.impl.FirDefaultSimpleImportingScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirSingleLevelDefaultStarImportingScope;

/* JADX INFO: compiled from: ImportingScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a:\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013H\u0000\u001a.\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0016\u001a\u00020\u0013\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"ALL_IMPORTS", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/scopes/ListStorageFirScope;", "DEFAULT_SIMPLE_IMPORT", "Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultImportPriority;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirDefaultSimpleImportingScope;", "DEFAULT_STAR_IMPORT", "Lorg/jetbrains/kotlin/fir/scopes/DefaultStarImportKey;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirSingleLevelDefaultStarImportingScope;", "computeImportingScopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "file", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "includeDefaultImports", "", "includePackageImport", "createImportingScopes", "useCaching", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImportingScopesKt {
    private static final ScopeSessionKey<FirFile, ListStorageFirScope> ALL_IMPORTS = new ScopeSessionKey<FirFile, ListStorageFirScope>() { // from class: org.jetbrains.kotlin.fir.scopes.ImportingScopesKt$special$$inlined$scopeSessionKey$1
    };
    private static final ScopeSessionKey<DefaultStarImportKey, FirSingleLevelDefaultStarImportingScope> DEFAULT_STAR_IMPORT = new ScopeSessionKey<DefaultStarImportKey, FirSingleLevelDefaultStarImportingScope>() { // from class: org.jetbrains.kotlin.fir.scopes.ImportingScopesKt$special$$inlined$scopeSessionKey$2
    };
    private static final ScopeSessionKey<DefaultImportPriority, FirDefaultSimpleImportingScope> DEFAULT_SIMPLE_IMPORT = new ScopeSessionKey<DefaultImportPriority, FirDefaultSimpleImportingScope>() { // from class: org.jetbrains.kotlin.fir.scopes.ImportingScopesKt$special$$inlined$scopeSessionKey$3
    };

    public static /* synthetic */ List createImportingScopes$default(FirFile firFile, FirSession firSession, ScopeSession scopeSession, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return createImportingScopes(firFile, firSession, scopeSession, z);
    }

    public static final List<FirScope> createImportingScopes(FirFile file, FirSession session, ScopeSession scopeSession, boolean z) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        if (z) {
            ScopeSessionKey<FirFile, ListStorageFirScope> scopeSessionKey = ALL_IMPORTS;
            HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
            HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(file);
            if (map == null) {
                map = new HashMap<>();
                mapScopes.put(file, map);
            }
            HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
            Object listStorageFirScope = map2.get(scopeSessionKey);
            if (listStorageFirScope == null) {
                listStorageFirScope = new ListStorageFirScope(computeImportingScopes$default(file, session, scopeSession, false, false, 24, null));
                map2.put(scopeSessionKey, listStorageFirScope);
            }
            return ((ListStorageFirScope) listStorageFirScope).getResult();
        }
        return computeImportingScopes$default(file, session, scopeSession, false, false, 24, null);
    }

    public static /* synthetic */ List computeImportingScopes$default(FirFile firFile, FirSession firSession, ScopeSession scopeSession, boolean z, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        if ((i & 16) != 0) {
            z2 = true;
        }
        return computeImportingScopes(firFile, firSession, scopeSession, z, z2);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r14v0 ??, still in use, count: 1, list:
          (r14v0 ?? I:java.lang.Object) from 0x0246: INVOKE (r13v2 ?? I:java.util.Map), (r12v1 ?? I:java.lang.Object), (r14v0 ?? I:java.lang.Object) INTERFACE call: java.util.Map.put(java.lang.Object, java.lang.Object):java.lang.Object A[MD:(K, V):V (c)] (LINE:231)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:75)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    public static final java.util.List<org.jetbrains.kotlin.fir.scopes.FirScope> computeImportingScopes(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r14v0 ??, still in use, count: 1, list:
          (r14v0 ?? I:java.lang.Object) from 0x0246: INVOKE (r13v2 ?? I:java.util.Map), (r12v1 ?? I:java.lang.Object), (r14v0 ?? I:java.lang.Object) INTERFACE call: java.util.Map.put(java.lang.Object, java.lang.Object):java.lang.Object A[MD:(K, V):V (c)] (LINE:231)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:75)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r15v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:215)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:150)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:415)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:345)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:299)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:186)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
        	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:284)
        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:632)
        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:284)
        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:268)
        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:160)
        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:104)
        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
        	at jadx.core.ProcessClass.process(ProcessClass.java:89)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:127)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:405)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:393)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:343)
        */
}
