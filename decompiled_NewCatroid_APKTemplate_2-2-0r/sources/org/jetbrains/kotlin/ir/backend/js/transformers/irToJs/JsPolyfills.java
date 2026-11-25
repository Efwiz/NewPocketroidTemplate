package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsAnnotations;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;

/* JADX INFO: compiled from: JsPolyfills.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u0005J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0007J\u001c\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013J\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000b*\b\u0012\u0004\u0012\u00020\u00070\u0015H\u0002J\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000b*\b\u0012\u0004\u0012\u00020\u00070\u0016H\u0002RJ\u0010\u0003\u001a>\u0012\u0004\u0012\u00020\u0005\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b0\u0004j\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsPolyfills;", "", "()V", "polyfillsPerFile", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lkotlin/collections/HashSet;", "Lkotlin/collections/HashMap;", "getAllPolyfillsFor", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "file", "registerDeclarationNativeImplementation", "", "declaration", "saveOnlyIntersectionOfNextDeclarationsFor", "declarations", "", "asImplementationList", "", "Lkotlin/sequences/Sequence;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPolyfills {
    private final HashMap<IrFile, HashSet<IrDeclaration>> polyfillsPerFile = new HashMap<>();

    public final void registerDeclarationNativeImplementation(IrFile file, IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (AnnotationUtilsKt.hasJsPolyfill(declaration)) {
            HashSet<IrDeclaration> hashSet = this.polyfillsPerFile.get(file);
            if (hashSet == null) {
                hashSet = new HashSet<>();
            }
            hashSet.add(declaration);
            this.polyfillsPerFile.put(file, hashSet);
        }
    }

    public final void saveOnlyIntersectionOfNextDeclarationsFor(IrFile file, Set<? extends IrDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        HashSet<IrDeclaration> hashSet = this.polyfillsPerFile.get(file);
        if (hashSet == null) {
            return;
        }
        this.polyfillsPerFile.put(file, CollectionsKt.toHashSet(CollectionsKt.intersect(hashSet, declarations)));
    }

    public final List<JsStatement> getAllPolyfillsFor(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        HashSet<IrDeclaration> hashSetEmptySet = this.polyfillsPerFile.get(file);
        if (hashSetEmptySet == null) {
            hashSetEmptySet = SetsKt.emptySet();
        }
        return asImplementationList(hashSetEmptySet);
    }

    private final List<JsStatement> asImplementationList(Iterable<? extends IrDeclaration> iterable) {
        return asImplementationList(CollectionsKt.asSequence(iterable));
    }

    private final List<JsStatement> asImplementationList(Sequence<? extends IrDeclaration> sequence) {
        return SequencesKt.toList(SequencesKt.flatMapIterable(SequencesKt.distinctBy(SequencesKt.map(sequence, new Function1<IrDeclaration, Pair<? extends IrDeclaration, ? extends IrExpression>>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsPolyfills.asImplementationList.1
            @Override // kotlin.jvm.functions.Function1
            public final Pair<IrDeclaration, IrExpression> invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrConstructorCall annotation = IrUtilsKt.getAnnotation(it, JsAnnotations.INSTANCE.getJsPolyfillFqn());
                Intrinsics.checkNotNull(annotation);
                IrExpression valueArgument = annotation.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument);
                return TuplesKt.to(it, valueArgument);
            }
        }), new Function1<Pair<? extends IrDeclaration, ? extends IrExpression>, String>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsPolyfills.asImplementationList.2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Pair<? extends IrDeclaration, ? extends IrExpression> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrExpression second = it.getSecond();
                Intrinsics.checkNotNull(second, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<kotlin.String>");
                return (String) ((IrConst) second).getValue();
            }
        }), new Function1<Pair<? extends IrDeclaration, ? extends IrExpression>, List<? extends JsStatement>>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsPolyfills.asImplementationList.3
            @Override // kotlin.jvm.functions.Function1
            public final List<JsStatement> invoke(Pair<? extends IrDeclaration, ? extends IrExpression> pair) {
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                List<JsStatement> listTranslateJsCodeIntoStatementList = JsCodeKt.translateJsCodeIntoStatementList(pair.component2(), (JsIrBackendContext) null, pair.component1());
                return listTranslateJsCodeIntoStatementList == null ? CollectionsKt.emptyList() : listTranslateJsCodeIntoStatementList;
            }
        }));
    }
}
