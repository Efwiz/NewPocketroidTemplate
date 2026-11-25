package org.jetbrains.kotlin.fir.extensions.predicate;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: AbstractPredicate.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u00020\u0002:\t\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001aJ;\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0001\u0010\f\"\u0004\b\u0002\u0010\r2\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH&¢\u0006\u0002\u0010\u0011R\u001c\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\b\u0082\u0001\u0006\u001b\u001c\u001d\u001e\u001f ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006!À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "P", "", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotations", "()Ljava/util/Set;", "metaAnnotations", "getMetaAnnotations", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "AncestorAnnotatedWith", "And", "Annotated", "AnnotatedWith", "BuilderContext", "HasAnnotatedWith", "MetaAnnotatedWith", "Or", "ParentAnnotatedWith", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$And;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$MetaAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Or;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AbstractPredicate<P extends AbstractPredicate<P>> {

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\b&\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\u00028\u00012\u001a\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u00060\bj\u0002`\t0\u0007\"\u00060\bj\u0002`\tH&¢\u0006\u0002\u0010\nJ\u001f\u0010\u0005\u001a\u00028\u00012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u00060\bj\u0002`\t0\u000bH&¢\u0006\u0002\u0010\fJ)\u0010\r\u001a\u00028\u00012\u001a\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u00060\bj\u0002`\t0\u0007\"\u00060\bj\u0002`\tH&¢\u0006\u0002\u0010\nJ\u001f\u0010\r\u001a\u00028\u00012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u00060\bj\u0002`\t0\u000bH&¢\u0006\u0002\u0010\fJ)\u0010\u000e\u001a\u00028\u00012\u001a\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u00060\bj\u0002`\t0\u0007\"\u00060\bj\u0002`\tH&¢\u0006\u0002\u0010\nJ\u001f\u0010\u000e\u001a\u00028\u00012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u00060\bj\u0002`\t0\u000bH&¢\u0006\u0002\u0010\fJ)\u0010\u000f\u001a\u00028\u00012\u001a\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u00060\bj\u0002`\t0\u0007\"\u00060\bj\u0002`\tH&¢\u0006\u0002\u0010\nJ\u001f\u0010\u000f\u001a\u00028\u00012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u00060\bj\u0002`\t0\u000bH&¢\u0006\u0002\u0010\fJ)\u0010\u0010\u001a\u00028\u00012\u001a\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u00060\bj\u0002`\t0\u0007\"\u00060\bj\u0002`\tH&¢\u0006\u0002\u0010\nJ\u001f\u0010\u0010\u001a\u00028\u00012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u00060\bj\u0002`\t0\u000bH&¢\u0006\u0002\u0010\fJ\u001a\u0010\u0011\u001a\u00028\u0001*\u00028\u00012\u0006\u0010\u0012\u001a\u00028\u0001H¦\u0004¢\u0006\u0002\u0010\u0013J\u001a\u0010\u0014\u001a\u00028\u0001*\u00028\u00012\u0006\u0010\u0012\u001a\u00028\u0001H¦\u0004¢\u0006\u0002\u0010\u0013¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$BuilderContext;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "", "()V", "ancestorAnnotated", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "([Lorg/jetbrains/kotlin/name/FqName;)Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "", "(Ljava/util/Collection;)Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "annotated", "annotatedOrUnder", "hasAnnotated", "parentAnnotated", "and", "other", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;)Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "or", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class BuilderContext<P extends AbstractPredicate<P>> {
        public abstract P ancestorAnnotated(Collection<FqName> annotations);

        public abstract P ancestorAnnotated(FqName... annotations);

        public abstract P and(P p, P p2);

        public abstract P annotated(Collection<FqName> annotations);

        public abstract P annotated(FqName... annotations);

        public abstract P annotatedOrUnder(Collection<FqName> annotations);

        public abstract P annotatedOrUnder(FqName... annotations);

        public abstract P hasAnnotated(Collection<FqName> annotations);

        public abstract P hasAnnotated(FqName... annotations);

        public abstract P or(P p, P p2);

        public abstract P parentAnnotated(Collection<FqName> annotations);

        public abstract P parentAnnotated(FqName... annotations);
    }

    <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data);

    Set<FqName> getAnnotations();

    Set<FqName> getMetaAnnotations();

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0002J;\u0010\b\u001a\u0002H\t\"\u0004\b\u0002\u0010\t\"\u0004\b\u0003\u0010\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u000eR\u0012\u0010\u0003\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\u0082\u0001\u0002\u000f\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Or;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "a", "getA", "()Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getB", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Or;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate$Or;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Or<P extends AbstractPredicate<P>> extends AbstractPredicate<P> {
        P getA();

        P getB();

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitOr(this, data);
        }
    }

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0002J;\u0010\b\u001a\u0002H\t\"\u0004\b\u0002\u0010\t\"\u0004\b\u0003\u0010\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u000eR\u0012\u0010\u0003\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\u0082\u0001\u0002\u000f\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$And;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "a", "getA", "()Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getB", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$And;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate$And;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface And<P extends AbstractPredicate<P>> extends AbstractPredicate<P> {
        P getA();

        P getB();

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAnd(this, data);
        }
    }

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0002J;\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0002\u0010\u0004\"\u0004\b\u0003\u0010\u00052\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00072\u0006\u0010\b\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\t\u0082\u0001\u0006\n\u000b\f\r\u000e\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Annotated;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AncestorAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$HasAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$ParentAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$Annotated;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate$Annotated;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Annotated<P extends AbstractPredicate<P>> extends AbstractPredicate<P> {
        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAnnotated(this, data);
        }
    }

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J;\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0002\u0010\u0005\"\u0004\b\u0003\u0010\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\n\u0082\u0001\u0002\u000b\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AnnotatedWith;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Annotated;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$AnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate$AnnotatedWith;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface AnnotatedWith<P extends AbstractPredicate<P>> extends Annotated<P> {
        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J;\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0002\u0010\u0005\"\u0004\b\u0003\u0010\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\n\u0082\u0001\u0002\u000b\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AncestorAnnotatedWith;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Annotated;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$AncestorAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate$AncestorAnnotatedWith;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface AncestorAnnotatedWith<P extends AbstractPredicate<P>> extends Annotated<P> {
        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitAncestorAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J;\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0002\u0010\u0005\"\u0004\b\u0003\u0010\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\n\u0082\u0001\u0002\u000b\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$ParentAnnotatedWith;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Annotated;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$ParentAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate$ParentAnnotatedWith;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ParentAnnotatedWith<P extends AbstractPredicate<P>> extends Annotated<P> {
        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitParentAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J;\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0002\u0010\u0005\"\u0004\b\u0003\u0010\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\n\u0082\u0001\u0002\u000b\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$HasAnnotatedWith;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Annotated;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$HasAnnotatedWith;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate$HasAnnotatedWith;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface HasAnnotatedWith<P extends AbstractPredicate<P>> extends Annotated<P> {
        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate.Annotated, org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitHasAnnotatedWith(this, data);
        }
    }

    /* JADX INFO: compiled from: AbstractPredicate.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0002J;\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0002\u0010\b\"\u0004\b\u0003\u0010\t2\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\u000b2\u0006\u0010\f\u001a\u0002H\tH\u0016¢\u0006\u0002\u0010\rR\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0001\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$MetaAnnotatedWith;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "includeItself", "", "getIncludeItself", "()Z", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate$MetaAnnotatedWith;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface MetaAnnotatedWith<P extends AbstractPredicate<P>> extends AbstractPredicate<P> {
        boolean getIncludeItself();

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate
        default <R, D> R accept(PredicateVisitor<P, R, D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitMetaAnnotatedWith(this, data);
        }
    }
}
