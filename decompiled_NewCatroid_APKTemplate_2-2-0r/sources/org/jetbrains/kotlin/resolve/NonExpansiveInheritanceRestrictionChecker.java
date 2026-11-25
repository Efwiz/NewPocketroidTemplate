package org.jetbrains.kotlin.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: NonExpansiveInheritanceRestrictionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003\u0015\u0016\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J+\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u000e2\u0006\u0010\u000f\u001a\u0002H\rH\u0002¢\u0006\u0002\u0010\u0010J&\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\r0\u0014H\u0002¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker;", "", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtClass;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "collectReachable", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$Graph;", TypedValues.TransitionType.S_FROM, "(Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$Graph;Ljava/lang/Object;)Ljava/util/List;", "isEdgeInCycle", "", "edge", "Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$ExpansiveEdge;", "ExpansiveEdge", "Graph", "GraphBuilder", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NonExpansiveInheritanceRestrictionChecker {
    public static final NonExpansiveInheritanceRestrictionChecker INSTANCE = new NonExpansiveInheritanceRestrictionChecker();

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: NonExpansiveInheritanceRestrictionChecker.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\bb\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u001b\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\n\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000bR\u001e\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$Graph;", ExifInterface.GPS_DIRECTION_TRUE, "", "expansiveEdges", "", "Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$ExpansiveEdge;", "getExpansiveEdges", "()Ljava/util/Set;", "getNeighbors", "", "node", "(Ljava/lang/Object;)Ljava/util/Collection;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    interface Graph<T> {
        /* JADX INFO: renamed from: getExpansiveEdges */
        Set<ExpansiveEdge<T>> getExpansiveEdges2();

        Collection<T> getNeighbors(T node);
    }

    private NonExpansiveInheritanceRestrictionChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
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
        Set<ExpansiveEdge<TypeParameterDescriptor>> expansiveEdges = graphBuild.getExpansiveEdges2();
        ArrayList arrayList = new ArrayList();
        for (Object obj : expansiveEdges) {
            if (INSTANCE.isEdgeInCycle(graphBuild, (ExpansiveEdge) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList<ExpansiveEdge> arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return;
        }
        ArrayList arrayList3 = new ArrayList();
        for (ExpansiveEdge expansiveEdge : arrayList2) {
            CollectionsKt.addAll(arrayList3, SetsKt.setOf((Object[]) new TypeParameterDescriptor[]{expansiveEdge.getFrom(), expansiveEdge.getTo()}));
        }
        ArrayList arrayList4 = arrayList3;
        for (TypeParameterDescriptor typeParameterDescriptor : typeConstructor.getParameters()) {
            if (arrayList4.contains(typeParameterDescriptor)) {
                Intrinsics.checkNotNull(typeParameterDescriptor);
                KtClass ktClassDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(typeParameterDescriptor);
                if (ktClassDescriptorToDeclaration == null) {
                    ktClassDescriptorToDeclaration = declaration;
                }
                diagnosticHolder.report(Errors.EXPANSIVE_INHERITANCE.on(ktClassDescriptorToDeclaration));
                return;
            }
        }
        ArrayList arrayList5 = arrayList4;
        if (!(arrayList5 instanceof Collection) || !arrayList5.isEmpty()) {
            Iterator it = arrayList5.iterator();
            while (it.hasNext()) {
                if (!Intrinsics.areEqual(((TypeParameterDescriptor) it.next()).getSourceElement(), SourceElement.NO_SOURCE)) {
                    return;
                }
            }
        }
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            arrayList6.add(((TypeParameterDescriptor) it2.next()).getContainingModule());
        }
        ArrayList<DeclarationDescriptor> arrayList7 = arrayList6;
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList7, 10));
        for (DeclarationDescriptor declarationDescriptor : arrayList7) {
            Intrinsics.checkNotNull(declarationDescriptor);
            arrayList8.add(DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor).asString());
        }
        diagnosticHolder.report(Errors.EXPANSIVE_INHERITANCE_IN_JAVA.on(declaration, CollectionsKt.joinToString$default(CollectionsKt.toSortedSet(arrayList8), ", ", null, null, 0, null, null, 62, null)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: NonExpansiveInheritanceRestrictionChecker.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00072\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0002J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u0018J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R6\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\f0\u000bj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$GraphBuilder;", "", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/types/TypeConstructor;)V", "edgeLists", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "", "Lkotlin/collections/HashMap;", "expansiveEdges", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$ExpansiveEdge;", "Lkotlin/collections/HashSet;", "processedTypeConstructors", "getTypeConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "addEdge", "", TypedValues.TransitionType.S_FROM, "to", "expansive", "", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$Graph;", "doBuildGraph", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class GraphBuilder {
        private final HashMap<TypeParameterDescriptor, Set<TypeParameterDescriptor>> edgeLists;
        private final HashSet<ExpansiveEdge<TypeParameterDescriptor>> expansiveEdges;
        private final HashSet<TypeConstructor> processedTypeConstructors;
        private final TypeConstructor typeConstructor;

        public GraphBuilder(TypeConstructor typeConstructor) {
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            this.typeConstructor = typeConstructor;
            this.processedTypeConstructors = new HashSet<>();
            this.expansiveEdges = new HashSet<>();
            this.edgeLists = new HashMap<>();
        }

        public final TypeConstructor getTypeConstructor() {
            return this.typeConstructor;
        }

        public final Graph<TypeParameterDescriptor> build() {
            doBuildGraph(this.typeConstructor);
            return new Graph<TypeParameterDescriptor>() { // from class: org.jetbrains.kotlin.resolve.NonExpansiveInheritanceRestrictionChecker$GraphBuilder$build$1
                private final HashSet<NonExpansiveInheritanceRestrictionChecker.ExpansiveEdge<TypeParameterDescriptor>> expansiveEdges;

                {
                    this.expansiveEdges = this.this$0.expansiveEdges;
                }

                @Override // org.jetbrains.kotlin.resolve.NonExpansiveInheritanceRestrictionChecker.Graph
                public Collection<TypeParameterDescriptor> getNeighbors(TypeParameterDescriptor node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    Collection<TypeParameterDescriptor> collectionEmptyList = (Set) this.this$0.edgeLists.get(node);
                    if (collectionEmptyList == null) {
                        collectionEmptyList = CollectionsKt.emptyList();
                    }
                    return collectionEmptyList;
                }

                @Override // org.jetbrains.kotlin.resolve.NonExpansiveInheritanceRestrictionChecker.Graph
                /* JADX INFO: renamed from: getExpansiveEdges, reason: merged with bridge method [inline-methods] */
                public Set<NonExpansiveInheritanceRestrictionChecker.ExpansiveEdge<TypeParameterDescriptor>> getExpansiveEdges2() {
                    return this.expansiveEdges;
                }
            };
        }

        private final void addEdge(TypeParameterDescriptor from, TypeParameterDescriptor to, boolean expansive) {
            HashMap<TypeParameterDescriptor, Set<TypeParameterDescriptor>> map = this.edgeLists;
            LinkedHashSet linkedHashSet = map.get(from);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
                map.put(from, linkedHashSet);
            }
            linkedHashSet.add(to);
            if (expansive) {
                this.expansiveEdges.add(new ExpansiveEdge<>(from, to));
            }
        }

        private final void doBuildGraph(TypeConstructor typeConstructor) {
            if (typeConstructor.getParameters().isEmpty()) {
                return;
            }
            List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Collection<KotlinType> collectionMo7438getSupertypes = typeConstructor.mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            for (KotlinType kotlinType : TypeUtilsKt.constituentTypes(collectionMo7438getSupertypes)) {
                TypeConstructor constructor = kotlinType.getConstructor();
                if (!this.processedTypeConstructors.contains(constructor)) {
                    this.processedTypeConstructors.add(constructor);
                    doBuildGraph(constructor);
                }
                if (constructor.getParameters().size() == kotlinType.getArguments().size()) {
                    int i = 0;
                    for (Object obj : kotlinType.getArguments()) {
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        TypeProjection typeProjection = (TypeProjection) obj;
                        if (typeProjection.getProjectionKind() == Variance.INVARIANT) {
                            Collection<KotlinType> collectionConstituentTypes = TypeUtilsKt.constituentTypes(SetsKt.setOf(typeProjection.getNullableAnyType()));
                            for (TypeParameterDescriptor typeParameterDescriptor : parameters) {
                                if (collectionConstituentTypes.contains(typeParameterDescriptor.getDefaultType()) || collectionConstituentTypes.contains(typeParameterDescriptor.getDefaultType().makeNullableAsSpecified(true))) {
                                    Intrinsics.checkNotNull(typeParameterDescriptor);
                                    TypeParameterDescriptor typeParameterDescriptor2 = constructor.getParameters().get(i);
                                    Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor2, "get(...)");
                                    addEdge(typeParameterDescriptor, typeParameterDescriptor2, !TypeUtils.isTypeParameter(typeProjection.getNullableAnyType()));
                                }
                            }
                        } else {
                            TypeParameterDescriptor typeParameterDescriptor3 = constructor.getParameters().get(i);
                            HashSet hashSet = new HashSet();
                            TypeSubstitutor typeSubstitutorBuildSubstitutor = TypeConstructorSubstitution.INSTANCE.create(kotlinType).buildSubstitutor();
                            List<KotlinType> upperBounds = typeParameterDescriptor3.getUpperBounds();
                            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                            ArrayList arrayList = new ArrayList();
                            Iterator<T> it = upperBounds.iterator();
                            while (it.hasNext()) {
                                KotlinType kotlinTypeSubstitute = typeSubstitutorBuildSubstitutor.substitute((KotlinType) it.next(), Variance.INVARIANT);
                                if (kotlinTypeSubstitute != null) {
                                    arrayList.add(kotlinTypeSubstitute);
                                }
                            }
                            hashSet.addAll(arrayList);
                            if (!typeProjection.isStarProjection()) {
                                hashSet.add(typeProjection.getNullableAnyType());
                            }
                            Collection<KotlinType> collectionConstituentTypes2 = TypeUtilsKt.constituentTypes(TypeUtilsKt.boundClosure(hashSet));
                            for (TypeParameterDescriptor typeParameterDescriptor4 : parameters) {
                                if (collectionConstituentTypes2.contains(typeParameterDescriptor4.getDefaultType()) || collectionConstituentTypes2.contains(typeParameterDescriptor4.getDefaultType().makeNullableAsSpecified(true))) {
                                    Intrinsics.checkNotNull(typeParameterDescriptor4);
                                    Intrinsics.checkNotNull(typeParameterDescriptor3);
                                    addEdge(typeParameterDescriptor4, typeParameterDescriptor3, true);
                                }
                            }
                        }
                        i = i2;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: NonExpansiveInheritanceRestrictionChecker.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\n\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0007J\u000e\u0010\u000b\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0007J(\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\rJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$ExpansiveEdge;", ExifInterface.GPS_DIRECTION_TRUE, "", TypedValues.TransitionType.S_FROM, "to", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getFrom", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getTo", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/resolve/NonExpansiveInheritanceRestrictionChecker$ExpansiveEdge;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class ExpansiveEdge<T> {
        private final T from;
        private final T to;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ExpansiveEdge copy$default(ExpansiveEdge expansiveEdge, Object obj, Object obj2, int i, Object obj3) {
            if ((i & 1) != 0) {
                obj = expansiveEdge.from;
            }
            if ((i & 2) != 0) {
                obj2 = expansiveEdge.to;
            }
            return expansiveEdge.copy(obj, obj2);
        }

        public final T component1() {
            return this.from;
        }

        public final T component2() {
            return this.to;
        }

        public final ExpansiveEdge<T> copy(T from, T to) {
            return new ExpansiveEdge<>(from, to);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExpansiveEdge)) {
                return false;
            }
            ExpansiveEdge expansiveEdge = (ExpansiveEdge) other;
            return Intrinsics.areEqual(this.from, expansiveEdge.from) && Intrinsics.areEqual(this.to, expansiveEdge.to);
        }

        public int hashCode() {
            T t = this.from;
            int iHashCode = (t == null ? 0 : t.hashCode()) * 31;
            T t2 = this.to;
            return iHashCode + (t2 != null ? t2.hashCode() : 0);
        }

        public String toString() {
            return "ExpansiveEdge(from=" + this.from + ", to=" + this.to + ')';
        }

        public ExpansiveEdge(T t, T t2) {
            this.from = t;
            this.to = t2;
        }

        public final T getFrom() {
            return this.from;
        }

        public final T getTo() {
            return this.to;
        }
    }

    private final <T> boolean isEdgeInCycle(Graph<T> graph, ExpansiveEdge<? extends T> expansiveEdge) {
        return collectReachable(graph, expansiveEdge.getTo()).contains(expansiveEdge.getFrom());
    }

    private final <T> List<T> collectReachable(final Graph<T> graph, T t) {
        DFS.NodeHandlerWithListResult<T, T> nodeHandlerWithListResult = new DFS.NodeHandlerWithListResult<T, T>() { // from class: org.jetbrains.kotlin.resolve.NonExpansiveInheritanceRestrictionChecker$collectReachable$handler$1
            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public void afterChildren(T current) {
                ((LinkedList) this.result).add(current);
            }
        };
        DFS.dfs(CollectionsKt.listOf(t), new DFS.Neighbors<T>() { // from class: org.jetbrains.kotlin.resolve.NonExpansiveInheritanceRestrictionChecker$collectReachable$neighbors$1
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public Iterable<T> getNeighbors(T current) {
                return graph.getNeighbors(current);
            }
        }, nodeHandlerWithListResult);
        List<T> listResult = nodeHandlerWithListResult.result();
        Intrinsics.checkNotNullExpressionValue(listResult, "result(...)");
        return listResult;
    }
}
