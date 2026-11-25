package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.annotations.ThrowUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: NativeThrowsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001!B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J2\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\u001c\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00180\u001a2\u0006\u0010\r\u001a\u00020\u001bH\u0002J\u0016\u0010\u001c\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e0\u001d*\u00020\u0014H\u0002J\u001e\u0010\u001f\u001a\u00020\u0012*\u0006\u0012\u0002\b\u00030\u001e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeThrowsChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "cancellationExceptionAndSupersClassIds", "", "Lorg/jetbrains/kotlin/name/ClassId;", "cancellationExceptionFqName", "Lorg/jetbrains/kotlin/name/FqName;", "throwsFqName", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkInheritance", "", "throwsAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "reportLocation", "Lorg/jetbrains/kotlin/psi/KtElement;", "decodeThrowsFilter", "Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeThrowsChecker$ThrowsFilter;", "findInheritedThrows", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getVariadicArguments", "", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "isGlobalClassWithId", "classIds", "ThrowsFilter", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeThrowsChecker implements DeclarationChecker {
    private static final Set<ClassId> cancellationExceptionAndSupersClassIds;
    private static final FqName cancellationExceptionFqName;
    public static final NativeThrowsChecker INSTANCE = new NativeThrowsChecker();
    private static final FqName throwsFqName = ThrowUtilKt.getKOTLIN_THROWS_ANNOTATION_FQ_NAME();

    private NativeThrowsChecker() {
    }

    static {
        FqName fqName = new FqName("kotlin.coroutines.cancellation.CancellationException");
        cancellationExceptionFqName = fqName;
        cancellationExceptionAndSupersClassIds = SequencesKt.toSet(SequencesKt.map(SequencesKt.sequenceOf(StandardNames.FqNames.throwable, new FqName("kotlin.Exception"), new FqName("kotlin.RuntimeException"), new FqName("kotlin.IllegalStateException"), fqName), new Function1<FqName, ClassId>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeThrowsChecker$cancellationExceptionAndSupersClassIds$1
            @Override // kotlin.jvm.functions.Function1
            public final ClassId invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ClassId.topLevel(it);
            }
        }));
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Call call;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(throwsFqName);
        KtAnnotationEntry sourceFromAnnotation = annotationDescriptorMo6752findAnnotation != null ? DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorMo6752findAnnotation) : null;
        KtElement ktElement = sourceFromAnnotation != null ? sourceFromAnnotation : declaration;
        if (checkInheritance(declaration, descriptor, context, annotationDescriptorMo6752findAnnotation, ktElement) && annotationDescriptorMo6752findAnnotation != null) {
            BindingContext context2 = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            if (sourceFromAnnotation == null || (call = CallUtilKt.getCall(sourceFromAnnotation, context2)) == null || !NativeThrowsCheckerKt.hasUnresolvedArgumentsRecursive(call, context2)) {
                List<ConstantValue<?>> variadicArguments = getVariadicArguments(annotationDescriptorMo6752findAnnotation);
                if (variadicArguments.isEmpty()) {
                    context.getTrace().report(ErrorsNative.THROWS_LIST_EMPTY.on(ktElement));
                    return;
                }
                if (declaration.hasModifier(KtTokens.SUSPEND_KEYWORD)) {
                    List<ConstantValue<?>> list = variadicArguments;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            if (INSTANCE.isGlobalClassWithId((ConstantValue) it.next(), cancellationExceptionAndSupersClassIds)) {
                                return;
                            }
                        }
                    }
                    context.getTrace().report(ErrorsNative.MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND.on(ktElement, cancellationExceptionFqName));
                }
            }
        }
    }

    private final boolean checkInheritance(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context, AnnotationDescriptor throwsAnnotation, KtElement reportLocation) {
        Map.Entry entry;
        if (descriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
            if (!callableMemberDescriptor.getOverriddenDescriptors().isEmpty()) {
                Set<Map.Entry<CallableMemberDescriptor, ThrowsFilter>> setEntrySet = findInheritedThrows(callableMemberDescriptor).entrySet();
                HashSet hashSet = new HashSet();
                ArrayList arrayList = new ArrayList();
                for (Object obj : setEntrySet) {
                    if (hashSet.add((ThrowsFilter) ((Map.Entry) obj).getValue())) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (arrayList2.size() >= 2) {
                    BindingTrace trace = context.getTrace();
                    DiagnosticFactory1<KtDeclaration, Collection<DeclarationDescriptor>> diagnosticFactory1 = ErrorsNative.INCOMPATIBLE_THROWS_INHERITED;
                    KtDeclaration ktDeclaration = declaration;
                    ArrayList arrayList3 = arrayList2;
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                    Iterator it = arrayList3.iterator();
                    while (it.hasNext()) {
                        DeclarationDescriptor containingDeclaration = ((CallableMemberDescriptor) ((Map.Entry) it.next()).getKey()).getContainingModule();
                        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                        arrayList4.add(containingDeclaration);
                    }
                    trace.report(diagnosticFactory1.on(ktDeclaration, arrayList4));
                    return false;
                }
                if (throwsAnnotation == null || (entry = (Map.Entry) CollectionsKt.firstOrNull((List) arrayList2)) == null) {
                    return true;
                }
                CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) entry.getKey();
                if (!Intrinsics.areEqual(decodeThrowsFilter(throwsAnnotation), (ThrowsFilter) entry.getValue())) {
                    context.getTrace().report(ErrorsNative.INCOMPATIBLE_THROWS_OVERRIDE.on(reportLocation, callableMemberDescriptor2.getContainingModule()));
                    return false;
                }
            }
        }
        return true;
    }

    private final Map<CallableMemberDescriptor, ThrowsFilter> findInheritedThrows(CallableMemberDescriptor descriptor) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        DFS.dfs(descriptor.getOverriddenDescriptors(), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeThrowsChecker$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return NativeThrowsChecker.findInheritedThrows$lambda$4((CallableMemberDescriptor) obj);
            }
        }, new DFS.AbstractNodeHandler<CallableMemberDescriptor, Unit>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeThrowsChecker.findInheritedThrows.2
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m7441result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m7441result();
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(CallableMemberDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                AnnotationDescriptor annotationDescriptorMo6752findAnnotation = current.getAnnotations().mo6752findAnnotation(NativeThrowsChecker.throwsFqName);
                if (!current.getKind().isReal()) {
                    annotationDescriptorMo6752findAnnotation = null;
                }
                if (annotationDescriptorMo6752findAnnotation == null) {
                    Collection<? extends CallableMemberDescriptor> overriddenDescriptors = current.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                    if (!overriddenDescriptors.isEmpty()) {
                        return true;
                    }
                }
                Map<CallableMemberDescriptor, ThrowsFilter> map = linkedHashMap;
                CallableMemberDescriptor original = current.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                map.put(original, NativeThrowsChecker.INSTANCE.decodeThrowsFilter(annotationDescriptorMo6752findAnnotation));
                return false;
            }
        });
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable findInheritedThrows$lambda$4(CallableMemberDescriptor callableMemberDescriptor) {
        return callableMemberDescriptor.getOverriddenDescriptors();
    }

    private final List<ConstantValue<?>> getVariadicArguments(AnnotationDescriptor annotationDescriptor) {
        ConstantValue<?> constantValueFirstArgument = DescriptorUtilsKt.firstArgument(annotationDescriptor);
        ArrayValue arrayValue = constantValueFirstArgument instanceof ArrayValue ? (ArrayValue) constantValueFirstArgument : null;
        return arrayValue == null ? CollectionsKt.emptyList() : (List) arrayValue.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ThrowsFilter decodeThrowsFilter(AnnotationDescriptor throwsAnnotation) {
        List<ConstantValue<?>> variadicArguments;
        return new ThrowsFilter((throwsAnnotation == null || (variadicArguments = getVariadicArguments(throwsAnnotation)) == null) ? null : CollectionsKt.toSet(variadicArguments));
    }

    /* JADX INFO: compiled from: NativeThrowsChecker.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0015\u0010\b\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\t\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u001d\u0010\u0002\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeThrowsChecker$ThrowsFilter;", "", "classes", "", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "(Ljava/util/Set;)V", "getClasses", "()Ljava/util/Set;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ThrowsFilter {
        private final Set<ConstantValue<?>> classes;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ThrowsFilter copy$default(ThrowsFilter throwsFilter, Set set, int i, Object obj) {
            if ((i & 1) != 0) {
                set = throwsFilter.classes;
            }
            return throwsFilter.copy(set);
        }

        public final Set<ConstantValue<?>> component1() {
            return this.classes;
        }

        public final ThrowsFilter copy(Set<? extends ConstantValue<?>> classes) {
            return new ThrowsFilter(classes);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof ThrowsFilter) && Intrinsics.areEqual(this.classes, ((ThrowsFilter) other).classes);
        }

        public int hashCode() {
            Set<ConstantValue<?>> set = this.classes;
            if (set == null) {
                return 0;
            }
            return set.hashCode();
        }

        public String toString() {
            return "ThrowsFilter(classes=" + this.classes + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ThrowsFilter(Set<? extends ConstantValue<?>> set) {
            this.classes = set;
        }

        public final Set<ConstantValue<?>> getClasses() {
            return this.classes;
        }
    }

    private final boolean isGlobalClassWithId(ConstantValue<?> constantValue, Set<ClassId> set) {
        boolean zContains;
        if (!(constantValue instanceof KClassValue)) {
            return false;
        }
        KClassValue.Value value = ((KClassValue) constantValue).getValue();
        if (value instanceof KClassValue.Value.NormalClass) {
            zContains = set.contains(((KClassValue.Value.NormalClass) value).getClassId());
        } else {
            if (!(value instanceof KClassValue.Value.LocalClass)) {
                throw new NoWhenBranchMatchedException();
            }
            zContains = false;
        }
        return zContains;
    }
}
