package org.jetbrains.kotlin.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: FiniteBoundRestrictionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0011\u0012B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J%\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u000e2\u0006\u0010\u000f\u001a\u0002H\rH\u0002¢\u0006\u0002\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FiniteBoundRestrictionChecker;", "", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtClass;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "isInCycle", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/FiniteBoundRestrictionChecker$Graph;", TypedValues.TransitionType.S_FROM, "(Lorg/jetbrains/kotlin/resolve/FiniteBoundRestrictionChecker$Graph;Ljava/lang/Object;)Z", "Graph", "GraphBuilder", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FiniteBoundRestrictionChecker {
    public static final FiniteBoundRestrictionChecker INSTANCE = new FiniteBoundRestrictionChecker();

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FiniteBoundRestrictionChecker.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\bb\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u001b\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b2\u0006\u0010\t\u001a\u00028\u0000H&¢\u0006\u0002\u0010\nR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FiniteBoundRestrictionChecker$Graph;", ExifInterface.GPS_DIRECTION_TRUE, "", "nodes", "", "getNodes", "()Ljava/util/Set;", "getNeighbors", "", "node", "(Ljava/lang/Object;)Ljava/util/List;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    interface Graph<T> {
        List<T> getNeighbors(T node);

        Set<T> getNodes();
    }

    private FiniteBoundRestrictionChecker() {
    }

    @JvmStatic
    public static final void check(KtClass declaration, ClassDescriptor classDescriptor, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        TypeConstructor typeConstructor = classDescriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        if (typeConstructor.getParameters().isEmpty()) {
            return;
        }
        Graph<TypeParameterDescriptor> graphBuild = new GraphBuilder(typeConstructor).build();
        Set<TypeParameterDescriptor> nodes = graphBuild.getNodes();
        ArrayList arrayList = new ArrayList();
        for (Object obj : nodes) {
            if (INSTANCE.isInCycle(graphBuild, (TypeParameterDescriptor) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return;
        }
        for (TypeParameterDescriptor typeParameterDescriptor : typeConstructor.getParameters()) {
            if (arrayList2.contains(typeParameterDescriptor)) {
                Intrinsics.checkNotNull(typeParameterDescriptor);
                KtClass ktClassDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(typeParameterDescriptor);
                if (ktClassDescriptorToDeclaration == null) {
                    ktClassDescriptorToDeclaration = declaration;
                }
                diagnosticHolder.report(Errors.FINITE_BOUNDS_VIOLATION.on(ktClassDescriptorToDeclaration));
                return;
            }
        }
        ArrayList arrayList3 = arrayList2;
        if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                if (!Intrinsics.areEqual(((TypeParameterDescriptor) it.next()).getSourceElement(), SourceElement.NO_SOURCE)) {
                    return;
                }
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(((TypeParameterDescriptor) it2.next()).getContainingModule());
        }
        ArrayList<DeclarationDescriptor> arrayList5 = arrayList4;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        for (DeclarationDescriptor declarationDescriptor : arrayList5) {
            Intrinsics.checkNotNull(declarationDescriptor);
            arrayList6.add(DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor).asString());
        }
        diagnosticHolder.report(Errors.FINITE_BOUNDS_VIOLATION_IN_JAVA.on(declaration, CollectionsKt.joinToString$default(CollectionsKt.toSortedSet(arrayList6), ", ", null, null, 0, null, null, 62, null)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FiniteBoundRestrictionChecker.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0007H\u0002J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R6\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u00030\rj\b\u0012\u0004\u0012\u00020\u0003`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FiniteBoundRestrictionChecker$GraphBuilder;", "", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/types/TypeConstructor;)V", "edgeLists", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "", "Lkotlin/collections/HashMap;", "nodes", "", "processedTypeConstructors", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getTypeConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "addEdge", "", TypedValues.TransitionType.S_FROM, "to", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/resolve/FiniteBoundRestrictionChecker$Graph;", "buildGraph", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class GraphBuilder {
        private final HashMap<TypeParameterDescriptor, List<TypeParameterDescriptor>> edgeLists;
        private final Set<TypeParameterDescriptor> nodes;
        private final HashSet<TypeConstructor> processedTypeConstructors;
        private final TypeConstructor typeConstructor;

        public GraphBuilder(TypeConstructor typeConstructor) {
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            this.typeConstructor = typeConstructor;
            this.nodes = new HashSet();
            this.edgeLists = new HashMap<>();
            this.processedTypeConstructors = new HashSet<>();
        }

        public final TypeConstructor getTypeConstructor() {
            return this.typeConstructor;
        }

        public final Graph<TypeParameterDescriptor> build() {
            buildGraph(this.typeConstructor);
            return new Graph<TypeParameterDescriptor>() { // from class: org.jetbrains.kotlin.resolve.FiniteBoundRestrictionChecker$GraphBuilder$build$1
                private final Set<TypeParameterDescriptor> nodes;

                {
                    this.nodes = this.this$0.nodes;
                }

                @Override // org.jetbrains.kotlin.resolve.FiniteBoundRestrictionChecker.Graph
                public Set<TypeParameterDescriptor> getNodes() {
                    return this.nodes;
                }

                @Override // org.jetbrains.kotlin.resolve.FiniteBoundRestrictionChecker.Graph
                public List<TypeParameterDescriptor> getNeighbors(TypeParameterDescriptor node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    List<TypeParameterDescriptor> list = (List) this.this$0.edgeLists.get(node);
                    return list == null ? CollectionsKt.emptyList() : list;
                }
            };
        }

        private final boolean addEdge(TypeParameterDescriptor from, TypeParameterDescriptor to) {
            HashMap<TypeParameterDescriptor, List<TypeParameterDescriptor>> map = this.edgeLists;
            ArrayList arrayList = map.get(from);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(from, arrayList);
            }
            return arrayList.add(to);
        }

        private final void buildGraph(TypeConstructor typeConstructor) {
            List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            for (TypeParameterDescriptor typeParameterDescriptor : parameters) {
                List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                for (KotlinType kotlinType : TypeUtilsKt.constituentTypes(TypeUtilsKt.boundClosure(upperBounds))) {
                    TypeConstructor constructor = kotlinType.getConstructor();
                    if (!this.processedTypeConstructors.contains(constructor)) {
                        this.processedTypeConstructors.add(constructor);
                        buildGraph(constructor);
                    }
                    if (constructor.getParameters().size() == kotlinType.getArguments().size()) {
                        int i = 0;
                        for (Object obj : kotlinType.getArguments()) {
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }
                            if (((TypeProjection) obj).getProjectionKind() != Variance.INVARIANT) {
                                Set<TypeParameterDescriptor> set = this.nodes;
                                Intrinsics.checkNotNull(typeParameterDescriptor);
                                set.add(typeParameterDescriptor);
                                Set<TypeParameterDescriptor> set2 = this.nodes;
                                TypeParameterDescriptor typeParameterDescriptor2 = constructor.getParameters().get(i);
                                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor2, "get(...)");
                                set2.add(typeParameterDescriptor2);
                                TypeParameterDescriptor typeParameterDescriptor3 = constructor.getParameters().get(i);
                                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor3, "get(...)");
                                addEdge(typeParameterDescriptor, typeParameterDescriptor3);
                            }
                            i = i2;
                        }
                    }
                }
            }
        }
    }

    private final <T> boolean isInCycle(final Graph<T> graph, final T t) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        DFS.VisitedWithSet<T> visitedWithSet = new DFS.VisitedWithSet<T>() { // from class: org.jetbrains.kotlin.resolve.FiniteBoundRestrictionChecker$isInCycle$visited$1
            @Override // org.jetbrains.kotlin.utils.DFS.VisitedWithSet, org.jetbrains.kotlin.utils.DFS.Visited
            public boolean checkAndMarkVisited(T current) {
                boolean zCheckAndMarkVisited = super.checkAndMarkVisited(current);
                if (!zCheckAndMarkVisited && Intrinsics.areEqual(current, t)) {
                    booleanRef.element = true;
                }
                return zCheckAndMarkVisited;
            }
        };
        DFS.AbstractNodeHandler<T, Unit> abstractNodeHandler = new DFS.AbstractNodeHandler<T, Unit>() { // from class: org.jetbrains.kotlin.resolve.FiniteBoundRestrictionChecker$isInCycle$handler$1
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m7422result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m7422result();
                return Unit.INSTANCE;
            }
        };
        DFS.dfs(CollectionsKt.listOf(t), new DFS.Neighbors<T>() { // from class: org.jetbrains.kotlin.resolve.FiniteBoundRestrictionChecker$isInCycle$neighbors$1
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public List<T> getNeighbors(T current) {
                return graph.getNeighbors(current);
            }
        }, visitedWithSet, abstractNodeHandler);
        return booleanRef.element;
    }
}
