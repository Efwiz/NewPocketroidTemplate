package org.jetbrains.kotlin.fir.extensions.predicate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DeclarationPredicate.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u0001:\n\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J;\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f\"\u0004\b\u0001\u0010\r2\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH&¢\u0006\u0002\u0010\u0011R\u001c\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\b\u0082\u0001\u0004\u001c\u001d\u001e\u001f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotations", "()Ljava/util/Set;", "metaAnnotations", "getMetaAnnotations", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "AncestorAnnotatedWith", "And", "Annotated", "AnnotatedWith", "BuilderContext", "Companion", "HasAnnotatedWith", "MetaAnnotatedWith", "Or", "ParentAnnotatedWith", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$And;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$MetaAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Or;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeclarationPredicate implements AbstractPredicate<DeclarationPredicate> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ DeclarationPredicate(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
    public abstract <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data);

    @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
    public abstract Set<FqName> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
    public abstract Set<FqName> getMetaAnnotations();

    private DeclarationPredicate() {
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J;\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017R\u0014\u0010\u0003\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\f\u0012\b\u0012\u00060\nj\u0002`\u000b0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0007R\u001e\u0010\u000f\u001a\f\u0012\b\u0012\u00060\nj\u0002`\u000b0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Or;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Or;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;)V", "getA", "()Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotations", "()Ljava/util/Set;", "getB", "metaAnnotations", "getMetaAnnotations", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Or extends DeclarationPredicate implements AbstractPredicate.Or<DeclarationPredicate> {
        private final DeclarationPredicate a;
        private final Set<FqName> annotations;
        private final DeclarationPredicate b;
        private final Set<FqName> metaAnnotations;

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.Or
        public DeclarationPredicate getA() {
            return this.a;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.Or
        public DeclarationPredicate getB() {
            return this.b;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Or(DeclarationPredicate a, DeclarationPredicate b) {
            super(null);
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            this.a = a;
            this.b = b;
            this.annotations = SetsKt.plus((Set) getA().getAnnotations(), (Iterable) getB().getAnnotations());
            this.metaAnnotations = SetsKt.plus((Set) getA().getMetaAnnotations(), (Iterable) getB().getMetaAnnotations());
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public Set<FqName> getAnnotations() {
            return this.annotations;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public Set<FqName> getMetaAnnotations() {
            return this.metaAnnotations;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitOr(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J;\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017R\u0014\u0010\u0003\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\f\u0012\b\u0012\u00060\nj\u0002`\u000b0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0007R\u001e\u0010\u000f\u001a\f\u0012\b\u0012\u00060\nj\u0002`\u000b0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$And;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$And;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;)V", "getA", "()Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotations", "()Ljava/util/Set;", "getB", "metaAnnotations", "getMetaAnnotations", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class And extends DeclarationPredicate implements AbstractPredicate.And<DeclarationPredicate> {
        private final DeclarationPredicate a;
        private final Set<FqName> annotations;
        private final DeclarationPredicate b;
        private final Set<FqName> metaAnnotations;

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.And
        public DeclarationPredicate getA() {
            return this.a;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.And
        public DeclarationPredicate getB() {
            return this.b;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public And(DeclarationPredicate a, DeclarationPredicate b) {
            super(null);
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            this.a = a;
            this.b = b;
            this.annotations = SetsKt.plus((Set) getA().getAnnotations(), (Iterable) getB().getAnnotations());
            this.metaAnnotations = SetsKt.plus((Set) getA().getMetaAnnotations(), (Iterable) getB().getMetaAnnotations());
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public Set<FqName> getAnnotations() {
            return this.annotations;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public Set<FqName> getMetaAnnotations() {
            return this.metaAnnotations;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAnd(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00010\u0002B\u0019\b\u0004\u0012\u0010\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004¢\u0006\u0002\u0010\u0007J;\u0010\f\u001a\u0002H\r\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e2\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00102\u0006\u0010\u0011\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0012R\u001b\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\n\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u00048F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t\u0082\u0001\u0004\u0013\u0014\u0015\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Annotated;", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "(Ljava/util/Set;)V", "getAnnotations", "()Ljava/util/Set;", "metaAnnotations", "getMetaAnnotations", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$AncestorAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$AnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$HasAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$ParentAnnotatedWith;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Annotated extends DeclarationPredicate implements AbstractPredicate.Annotated<DeclarationPredicate> {
        private final Set<FqName> annotations;

        public /* synthetic */ Annotated(Set set, DefaultConstructorMarker defaultConstructorMarker) {
            this(set);
        }

        private Annotated(Set<FqName> set) {
            super(null);
            this.annotations = set;
            if (set.isEmpty()) {
                throw new IllegalArgumentException("Annotations should be not empty".toString());
            }
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public final Set<FqName> getAnnotations() {
            return this.annotations;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public final Set<FqName> getMetaAnnotations() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAnnotated(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u0005¢\u0006\u0002\u0010\bJ;\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$AnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "(Ljava/util/Set;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnnotatedWith extends Annotated implements AbstractPredicate.AnnotatedWith<DeclarationPredicate> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnnotatedWith(Set<FqName> annotations) {
            super(annotations, null);
            Intrinsics.checkNotNullParameter(annotations, "annotations");
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u0005¢\u0006\u0002\u0010\bJ;\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$AncestorAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AncestorAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "(Ljava/util/Set;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AncestorAnnotatedWith extends Annotated implements AbstractPredicate.AncestorAnnotatedWith<DeclarationPredicate> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AncestorAnnotatedWith(Set<FqName> annotations) {
            super(annotations, null);
            Intrinsics.checkNotNullParameter(annotations, "annotations");
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAncestorAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u0005¢\u0006\u0002\u0010\bJ;\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$ParentAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$ParentAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "(Ljava/util/Set;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ParentAnnotatedWith extends Annotated implements AbstractPredicate.ParentAnnotatedWith<DeclarationPredicate> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ParentAnnotatedWith(Set<FqName> annotations) {
            super(annotations, null);
            Intrinsics.checkNotNullParameter(annotations, "annotations");
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitParentAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u0005¢\u0006\u0002\u0010\bJ;\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$HasAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$HasAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "(Ljava/util/Set;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class HasAnnotatedWith extends Annotated implements AbstractPredicate.HasAnnotatedWith<DeclarationPredicate> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public HasAnnotatedWith(Set<FqName> annotations) {
            super(annotations, null);
            Intrinsics.checkNotNullParameter(annotations, "annotations");
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitHasAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00010\u0002B\u001f\u0012\u0010\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ;\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00122\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016R\u001e\u0010\n\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$MetaAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$MetaAnnotatedWith;", "metaAnnotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "includeItself", "", "(Ljava/util/Set;Z)V", "annotations", "getAnnotations", "()Ljava/util/Set;", "getIncludeItself", "()Z", "getMetaAnnotations", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MetaAnnotatedWith extends DeclarationPredicate implements AbstractPredicate.MetaAnnotatedWith<DeclarationPredicate> {
        private final boolean includeItself;
        private final Set<FqName> metaAnnotations;

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public Set<FqName> getMetaAnnotations() {
            return this.metaAnnotations;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.MetaAnnotatedWith
        public boolean getIncludeItself() {
            return this.includeItself;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MetaAnnotatedWith(Set<FqName> metaAnnotations, boolean z) {
            super(null);
            Intrinsics.checkNotNullParameter(metaAnnotations, "metaAnnotations");
            this.metaAnnotations = metaAnnotations;
            this.includeItself = z;
            if (getMetaAnnotations().isEmpty()) {
                throw new IllegalArgumentException("Annotations should be not empty".toString());
            }
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public Set<FqName> getAnnotations() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        public <R, D> R accept(PredicateVisitor<DeclarationPredicate, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitMetaAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J)\u0010\u0004\u001a\u00020\u00022\u001a\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0007j\u0002`\b0\u0006\"\u00060\u0007j\u0002`\bH\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\u0004\u001a\u00020\u00022\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\nH\u0016J)\u0010\u000b\u001a\u00020\u00022\u001a\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0007j\u0002`\b0\u0006\"\u00060\u0007j\u0002`\bH\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\u000b\u001a\u00020\u00022\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\nH\u0016J)\u0010\f\u001a\u00020\u00022\u001a\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0007j\u0002`\b0\u0006\"\u00060\u0007j\u0002`\bH\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\f\u001a\u00020\u00022\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\nH\u0016J)\u0010\r\u001a\u00020\u00022\u001a\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0007j\u0002`\b0\u0006\"\u00060\u0007j\u0002`\bH\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\r\u001a\u00020\u00022\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\nH\u0016J/\u0010\u000e\u001a\u00020\u00022\u001a\u0010\u000f\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0007j\u0002`\b0\u0006\"\u00060\u0007j\u0002`\b2\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J \u0010\u000e\u001a\u00020\u00022\u0010\u0010\u000f\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\n2\u0006\u0010\u0010\u001a\u00020\u0011J)\u0010\u0013\u001a\u00020\u00022\u001a\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0007j\u0002`\b0\u0006\"\u00060\u0007j\u0002`\bH\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\u0013\u001a\u00020\u00022\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\nH\u0016J\u0015\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002H\u0096\u0004J\u0015\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002H\u0096\u0004¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$BuilderContext;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$BuilderContext;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "()V", "ancestorAnnotated", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "([Lorg/jetbrains/kotlin/name/FqName;)Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "", "annotated", "annotatedOrUnder", "hasAnnotated", "metaAnnotated", "metaAnnotations", "includeItself", "", "([Lorg/jetbrains/kotlin/name/FqName;Z)Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "parentAnnotated", "and", "other", "or", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BuilderContext extends AbstractPredicate.BuilderContext<DeclarationPredicate> {
        public static final BuilderContext INSTANCE = new BuilderContext();

        private BuilderContext() {
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public /* bridge */ /* synthetic */ AbstractPredicate ancestorAnnotated(Collection collection) {
            return ancestorAnnotated((Collection<FqName>) collection);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public /* bridge */ /* synthetic */ AbstractPredicate annotated(Collection collection) {
            return annotated((Collection<FqName>) collection);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public /* bridge */ /* synthetic */ AbstractPredicate annotatedOrUnder(Collection collection) {
            return annotatedOrUnder((Collection<FqName>) collection);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public /* bridge */ /* synthetic */ AbstractPredicate hasAnnotated(Collection collection) {
            return hasAnnotated((Collection<FqName>) collection);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public /* bridge */ /* synthetic */ AbstractPredicate parentAnnotated(Collection collection) {
            return parentAnnotated((Collection<FqName>) collection);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate or(DeclarationPredicate declarationPredicate, DeclarationPredicate other) {
            Intrinsics.checkNotNullParameter(declarationPredicate, "<this>");
            Intrinsics.checkNotNullParameter(other, "other");
            return new Or(declarationPredicate, other);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate and(DeclarationPredicate declarationPredicate, DeclarationPredicate other) {
            Intrinsics.checkNotNullParameter(declarationPredicate, "<this>");
            Intrinsics.checkNotNullParameter(other, "other");
            return new And(declarationPredicate, other);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate annotated(FqName... annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return annotated((Collection<FqName>) ArraysKt.toList(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate ancestorAnnotated(FqName... annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return ancestorAnnotated((Collection<FqName>) ArraysKt.toList(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate parentAnnotated(FqName... annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return parentAnnotated((Collection<FqName>) ArraysKt.toList(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate hasAnnotated(FqName... annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return hasAnnotated((Collection<FqName>) ArraysKt.toList(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate annotatedOrUnder(FqName... annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return or(annotated((FqName[]) Arrays.copyOf(annotations, annotations.length)), ancestorAnnotated((FqName[]) Arrays.copyOf(annotations, annotations.length)));
        }

        public final DeclarationPredicate metaAnnotated(FqName[] metaAnnotations, boolean includeItself) {
            Intrinsics.checkNotNullParameter(metaAnnotations, "metaAnnotations");
            return new MetaAnnotatedWith(ArraysKt.toSet(metaAnnotations), includeItself);
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate annotated(Collection<FqName> annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return new AnnotatedWith(CollectionsKt.toSet(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate ancestorAnnotated(Collection<FqName> annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return new AncestorAnnotatedWith(CollectionsKt.toSet(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate parentAnnotated(Collection<FqName> annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return new ParentAnnotatedWith(CollectionsKt.toSet(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate hasAnnotated(Collection<FqName> annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return new HasAnnotatedWith(CollectionsKt.toSet(annotations));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.BuilderContext
        public DeclarationPredicate annotatedOrUnder(Collection<FqName> annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return or(annotated(annotations), ancestorAnnotated(annotations));
        }

        public final DeclarationPredicate metaAnnotated(Collection<FqName> metaAnnotations, boolean includeItself) {
            Intrinsics.checkNotNullParameter(metaAnnotations, "metaAnnotations");
            return new MetaAnnotatedWith(CollectionsKt.toSet(metaAnnotations), includeItself);
        }
    }

    /* JADX INFO: compiled from: DeclarationPredicate.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\b\bH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$BuilderContext;", "Lkotlin/ExtensionFunctionType;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DeclarationPredicate create(Function1<? super BuilderContext, ? extends DeclarationPredicate> init) {
            Intrinsics.checkNotNullParameter(init, "init");
            return init.invoke(BuilderContext.INSTANCE);
        }
    }
}
