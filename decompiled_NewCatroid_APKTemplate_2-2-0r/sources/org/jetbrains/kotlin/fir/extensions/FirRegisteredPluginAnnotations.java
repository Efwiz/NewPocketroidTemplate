package org.jetbrains.kotlin.fir.extensions;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: FirRegisteredPluginAnnotations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000f\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u00042\u0006\u0010\u0010\u001a\u00020\u0011H&J\u001e\u0010\u0012\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u00132\n\u0010\u0014\u001a\u00060\u0005j\u0002`\u0006H&J\b\u0010\u0015\u001a\u00020\u0016H'J\"\u0010\u0017\u001a\u00020\u00162\n\u0010\u0014\u001a\u00060\u0005j\u0002`\u00062\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0013H&R\u001c\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\b¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirRegisteredPluginAnnotations;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotations", "()Ljava/util/Set;", "hasRegisteredAnnotations", "", "getHasRegisteredAnnotations", "()Z", "metaAnnotations", "getMetaAnnotations", "getAnnotationsForPredicate", "predicate", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "getAnnotationsWithMetaAnnotation", "", "metaAnnotation", "initialize", "", "registerUserDefinedAnnotation", "annotationClasses", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Empty", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirRegisteredPluginAnnotations implements FirSessionComponent {
    public abstract Set<FqName> getAnnotations();

    public abstract Set<FqName> getAnnotationsForPredicate(DeclarationPredicate predicate);

    public abstract Collection<FqName> getAnnotationsWithMetaAnnotation(FqName metaAnnotation);

    public abstract Set<FqName> getMetaAnnotations();

    @PluginServicesInitialization
    public abstract void initialize();

    public abstract void registerUserDefinedAnnotation(FqName metaAnnotation, Collection<? extends FirRegularClass> annotationClasses);

    public final boolean getHasRegisteredAnnotations() {
        return (getAnnotations().isEmpty() && getMetaAnnotations().isEmpty()) ? false : true;
    }

    /* JADX INFO: compiled from: FirRegisteredPluginAnnotations.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000b\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\u001e\u0010\u000e\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u000f2\n\u0010\u0010\u001a\u00060\u0005j\u0002`\u0006H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0017J\"\u0010\u0013\u001a\u00020\u00122\n\u0010\u0010\u001a\u00060\u0005j\u0002`\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000fH\u0016R\u001e\u0010\u0003\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\f\u0012\b\u0012\u00060\u0005j\u0002`\u00060\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirRegisteredPluginAnnotations$Empty;", "Lorg/jetbrains/kotlin/fir/extensions/FirRegisteredPluginAnnotations;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotations", "()Ljava/util/Set;", "metaAnnotations", "getMetaAnnotations", "getAnnotationsForPredicate", "predicate", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "getAnnotationsWithMetaAnnotation", "", "metaAnnotation", "initialize", "", "registerUserDefinedAnnotation", "annotationClasses", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends FirRegisteredPluginAnnotations {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
        public Set<FqName> getAnnotations() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
        public Set<FqName> getMetaAnnotations() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
        public Collection<FqName> getAnnotationsWithMetaAnnotation(FqName metaAnnotation) {
            Intrinsics.checkNotNullParameter(metaAnnotation, "metaAnnotation");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
        public void registerUserDefinedAnnotation(FqName metaAnnotation, Collection<? extends FirRegularClass> annotationClasses) {
            Intrinsics.checkNotNullParameter(metaAnnotation, "metaAnnotation");
            Intrinsics.checkNotNullParameter(annotationClasses, "annotationClasses");
            AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
            throw null;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
        public Set<FqName> getAnnotationsForPredicate(DeclarationPredicate predicate) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
        @PluginServicesInitialization
        public void initialize() {
            AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
            throw null;
        }
    }
}
