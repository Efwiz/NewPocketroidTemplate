package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractDiagnostic;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryWithPsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ConflictingJvmDeclarationsData;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: duplicateJvmSignatureUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\t\u0010\u0014\u001a\u00020\u0010H\u0096\u0001J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0016H\u0096\u0003J\t\u0010\u0017\u001a\u00020\u0001H\u0096\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u0096\u0001J\u0011\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0096\u0001J\u0011\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0096\u0001R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0006¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/FilteredJvmDiagnostics;", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "jvmDiagnostics", "otherDiagnostics", "(Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;)V", "getJvmDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "modificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "getModificationTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", "getOtherDiagnostics", DevModeOverwritingStrategies.ALL, "", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "alreadyReported", "", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "forElement", "isEmpty", "iterator", "", "noSuppression", "resetCallback", "", "setCallback", "callback", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink$DiagnosticsCallback;", "setCallbackIfNotSet", "Companion", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilteredJvmDiagnostics implements Diagnostics {
    private static final Set<DiagnosticFactoryWithPsiElement<? extends PsiElement, ? extends AbstractDiagnostic<? extends PsiElement>>> higherPriorityDiagnosticFactories = SetsKt.setOf((Object[]) new DiagnosticFactoryWithPsiElement[]{Errors.CONFLICTING_OVERLOADS, Errors.REDECLARATION, Errors.NOTHING_TO_OVERRIDE, Errors.MANY_IMPL_MEMBER_NOT_IMPLEMENTED});
    private static final Set<DiagnosticFactory1<PsiElement, ConflictingJvmDeclarationsData>> jvmDiagnosticFactories = SetsKt.setOf((Object[]) new DiagnosticFactory1[]{ErrorsJvm.CONFLICTING_JVM_DECLARATIONS, ErrorsJvm.ACCIDENTAL_OVERRIDE, ErrorsJvm.CONFLICTING_INHERITED_JVM_DECLARATIONS});
    private final Diagnostics jvmDiagnostics;
    private final Diagnostics otherDiagnostics;

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public ModificationTracker getModificationTracker() {
        return this.jvmDiagnostics.getModificationTracker();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public boolean isEmpty() {
        return this.jvmDiagnostics.isEmpty();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics, java.lang.Iterable
    public Iterator<Diagnostic> iterator() {
        return this.jvmDiagnostics.iterator();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Diagnostics noSuppression() {
        return this.jvmDiagnostics.noSuppression();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public void resetCallback() {
        this.jvmDiagnostics.resetCallback();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public void setCallback(DiagnosticSink.DiagnosticsCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.jvmDiagnostics.setCallback(callback);
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public boolean setCallbackIfNotSet(DiagnosticSink.DiagnosticsCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return this.jvmDiagnostics.setCallbackIfNotSet(callback);
    }

    public FilteredJvmDiagnostics(Diagnostics jvmDiagnostics, Diagnostics otherDiagnostics) {
        Intrinsics.checkNotNullParameter(jvmDiagnostics, "jvmDiagnostics");
        Intrinsics.checkNotNullParameter(otherDiagnostics, "otherDiagnostics");
        this.jvmDiagnostics = jvmDiagnostics;
        this.otherDiagnostics = otherDiagnostics;
    }

    public final Diagnostics getJvmDiagnostics() {
        return this.jvmDiagnostics;
    }

    public final Diagnostics getOtherDiagnostics() {
        return this.otherDiagnostics;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0039  */
    private final boolean alreadyReported(PsiElement psiElement) {
        PsiElement parent;
        Collection<Diagnostic> collectionForElement = this.otherDiagnostics.forElement(psiElement);
        if ((collectionForElement instanceof Collection) && collectionForElement.isEmpty()) {
            if (psiElement instanceof KtPropertyAccessor) {
                parent = ((KtPropertyAccessor) psiElement).getMyParent();
                Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
                if (alreadyReported(parent)) {
                }
            }
            return false;
        }
        Iterator<T> it = collectionForElement.iterator();
        while (it.hasNext()) {
            if (CollectionsKt.contains(higherPriorityDiagnosticFactories, ((Diagnostic) it.next()).getFactory())) {
            }
        }
        if (psiElement instanceof KtPropertyAccessor) {
            parent = ((KtPropertyAccessor) psiElement).getMyParent();
            Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
            if (alreadyReported(parent)) {
            }
        }
        return false;
        return true;
    }

    private static final ConflictingJvmDeclarationsData forElement$data(Diagnostic diagnostic) {
        Object a = ((DiagnosticWithParameters1) DiagnosticFactory.INSTANCE.cast(diagnostic, jvmDiagnosticFactories)).getA();
        Intrinsics.checkNotNullExpressionValue(a, "<get-a>(...)");
        return (ConflictingJvmDeclarationsData) a;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Collection<Diagnostic> forElement(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Collection<Diagnostic> collectionForElement = this.jvmDiagnostics.forElement(psiElement);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : collectionForElement) {
            if (CollectionsKt.contains(jvmDiagnosticFactories, ((Diagnostic) obj).getFactory())) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        if (alreadyReported(psiElement)) {
            return list2;
        }
        ArrayList arrayList3 = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : list) {
            String name2 = forElement$data((Diagnostic) obj2).getSignature().getName();
            Object obj3 = linkedHashMap.get(name2);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(name2, obj3);
            }
            ((List) obj3).add(obj2);
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            List list3 = (List) ((Map.Entry) it.next()).getValue();
            if (list3.size() <= 1) {
                arrayList3.addAll(list3);
            } else {
                List list4 = list3;
                ArrayList arrayList4 = new ArrayList();
                for (Object obj4 : list4) {
                    Diagnostic diagnostic = (Diagnostic) obj4;
                    if (!(list4 instanceof Collection) || !list4.isEmpty()) {
                        Iterator it2 = list4.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                Diagnostic diagnostic2 = (Diagnostic) it2.next();
                                if (!Intrinsics.areEqual(diagnostic, diagnostic2) && ((Intrinsics.areEqual(diagnostic2.getFactory(), ErrorsJvm.CONFLICTING_JVM_DECLARATIONS) && (Intrinsics.areEqual(diagnostic.getFactory(), ErrorsJvm.ACCIDENTAL_OVERRIDE) || Intrinsics.areEqual(diagnostic.getFactory(), ErrorsJvm.CONFLICTING_INHERITED_JVM_DECLARATIONS))) || DuplicateJvmSignatureUtilKt.higherThan(forElement$data(diagnostic2), forElement$data(diagnostic)))) {
                                    break;
                                }
                            }
                        }
                    }
                    arrayList4.add(obj4);
                }
                arrayList3.addAll(arrayList4);
            }
        }
        return CollectionsKt.plus((Collection) arrayList3, (Iterable) list2);
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public Collection<Diagnostic> all() {
        Collection<Diagnostic> collectionAll = this.jvmDiagnostics.all();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionAll, 10));
        Iterator<T> it = collectionAll.iterator();
        while (it.hasNext()) {
            arrayList.add(((Diagnostic) it.next()).getPsiElement());
        }
        Set set = CollectionsKt.toSet(arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = set.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, forElement((PsiElement) it2.next()));
        }
        return arrayList2;
    }
}
