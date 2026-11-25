package org.jetbrains.kotlin.fir.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirRegisteredPluginAnnotations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\r\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u000e2\n\u0010\u000f\u001a\u00060\u0007j\u0002`\bH\u0016J\"\u0010\u0010\u001a\u00020\u00112\n\u0010\u000f\u001a\u00060\u0007j\u0002`\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000eH\u0016J\u001a\u0010\u0014\u001a\u00020\u00112\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u000eH\u0014R\u001e\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\u0016\u0012\b\u0012\u00060\u0007j\u0002`\b\u0012\b\u0012\u00060\u0007j\u0002`\b0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirRegisteredPluginAnnotationsImpl;", "Lorg/jetbrains/kotlin/fir/extensions/AbstractFirRegisteredPluginAnnotations;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotations", "()Ljava/util/Set;", "userDefinedAnnotations", "Lorg/jetbrains/kotlin/com/google/common/collect/Multimap;", "getAnnotationsWithMetaAnnotation", "", "metaAnnotation", "registerUserDefinedAnnotation", "", "annotationClasses", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "saveAnnotationsFromPlugin", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRegisteredPluginAnnotationsImpl extends AbstractFirRegisteredPluginAnnotations {
    private final Set<FqName> annotations;
    private final Multimap<FqName, FqName> userDefinedAnnotations;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirRegisteredPluginAnnotationsImpl(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        this.annotations = new LinkedHashSet();
        LinkedHashMultimap linkedHashMultimapCreate = LinkedHashMultimap.create();
        Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate, "create(...)");
        this.userDefinedAnnotations = linkedHashMultimapCreate;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
    public Set<FqName> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
    public Collection<FqName> getAnnotationsWithMetaAnnotation(FqName metaAnnotation) {
        Intrinsics.checkNotNullParameter(metaAnnotation, "metaAnnotation");
        Collection<FqName> collection = this.userDefinedAnnotations.get(metaAnnotation);
        Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
        return collection;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.AbstractFirRegisteredPluginAnnotations
    protected void saveAnnotationsFromPlugin(Collection<FqName> annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        CollectionsKt.addAll(getAnnotations(), annotations);
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
    public void registerUserDefinedAnnotation(FqName metaAnnotation, Collection<? extends FirRegularClass> annotationClasses) {
        Intrinsics.checkNotNullParameter(metaAnnotation, "metaAnnotation");
        Intrinsics.checkNotNullParameter(annotationClasses, "annotationClasses");
        Collection<? extends FirRegularClass> collection = annotationClasses;
        if (!collection.isEmpty()) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                if (((FirRegularClass) it.next()).getClassKind() != ClassKind.ANNOTATION_CLASS) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it2 = collection.iterator();
        while (it2.hasNext()) {
            arrayList.add(((FirRegularClass) it2.next()).getSymbol().getClassId().asSingleFqName());
        }
        ArrayList arrayList2 = arrayList;
        CollectionsKt.addAll(getAnnotations(), arrayList2);
        this.userDefinedAnnotations.putAll(metaAnnotation, arrayList2);
    }
}
