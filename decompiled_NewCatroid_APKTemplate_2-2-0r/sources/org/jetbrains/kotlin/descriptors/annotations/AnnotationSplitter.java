package org.jetbrains.kotlin.descriptors.annotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.lazy.LazyEntity;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: AnnotationSplitter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00142\u00020\u0001:\u0002\u0014\u0015B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\bJ\u0006\u0010\u0013\u001a\u00020\u0005RH\u0010\n\u001a<\u00128\u00126\u0012,\u0012*\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rj\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e`\u0010\u0012\u0004\u0012\u00020\u00050\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationSplitter;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "allAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "applicableTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Ljava/util/Set;)V", "splitAnnotations", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lkotlin/Pair;", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lkotlin/collections/HashMap;", "getAnnotationsForTarget", "target", "getOtherAnnotations", "Companion", "LazySplitAnnotations", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationSplitter {
    private static final Set<AnnotationUseSiteTarget> TARGET_PRIORITIES = SetsKt.setOf((Object[]) new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER, AnnotationUseSiteTarget.PROPERTY, AnnotationUseSiteTarget.FIELD});
    private final NotNullLazyValue<Pair<HashMap<AnnotationUseSiteTarget, List<AnnotationDescriptor>>, Annotations>> splitAnnotations;
    private final StorageManager storageManager;

    public AnnotationSplitter(StorageManager storageManager, final Annotations allAnnotations, final Set<? extends AnnotationUseSiteTarget> applicableTargets) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(allAnnotations, "allAnnotations");
        Intrinsics.checkNotNullParameter(applicableTargets, "applicableTargets");
        this.storageManager = storageManager;
        this.splitAnnotations = storageManager.createLazyValue(new Function0<Pair<? extends HashMap<AnnotationUseSiteTarget, List<AnnotationDescriptor>>, ? extends Annotations>>() { // from class: org.jetbrains.kotlin.descriptors.annotations.AnnotationSplitter$splitAnnotations$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Pair<? extends HashMap<AnnotationUseSiteTarget, List<AnnotationDescriptor>>, ? extends Annotations> invoke() {
                KotlinTarget kotlinTarget;
                HashMap map = new HashMap();
                ArrayList arrayList = new ArrayList();
                Set setIntersect = CollectionsKt.intersect(applicableTargets, AnnotationSplitter.TARGET_PRIORITIES);
                for (AnnotationDescriptor annotationDescriptor : allAnnotations) {
                    Iterator it = AnnotationSplitter.TARGET_PRIORITIES.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            AnnotationUseSiteTarget annotationUseSiteTarget = (AnnotationUseSiteTarget) it.next();
                            if (setIntersect.contains(annotationUseSiteTarget) && (kotlinTarget = KotlinTarget.INSTANCE.getUSE_SITE_MAPPING().get(annotationUseSiteTarget)) != null && AnnotationChecker.INSTANCE.applicableTargetSet(annotationDescriptor).contains(kotlinTarget)) {
                                HashMap map2 = map;
                                Object obj = map2.get(annotationUseSiteTarget);
                                if (obj == null) {
                                    obj = (List) new ArrayList();
                                    map2.put(annotationUseSiteTarget, obj);
                                }
                                ((List) obj).add(annotationDescriptor);
                                break;
                            }
                        } else {
                            arrayList.add(annotationDescriptor);
                            break;
                        }
                    }
                }
                for (AnnotationWithTarget annotationWithTarget : allAnnotations.getUseSiteTargetedAnnotations()) {
                    AnnotationDescriptor annotation = annotationWithTarget.getAnnotation();
                    AnnotationUseSiteTarget target = annotationWithTarget.getTarget();
                    if (applicableTargets.contains(target)) {
                        HashMap map3 = map;
                        Object obj2 = map3.get(target);
                        if (obj2 == null) {
                            obj2 = (List) new ArrayList();
                            map3.put(target, obj2);
                        }
                        ((List) obj2).add(annotation);
                    }
                }
                return TuplesKt.to(map, Annotations.INSTANCE.create(arrayList));
            }
        });
    }

    public final Annotations getOtherAnnotations() {
        return new LazySplitAnnotations(this, this.storageManager, null);
    }

    public final Annotations getAnnotationsForTarget(AnnotationUseSiteTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return new LazySplitAnnotations(this, this.storageManager, target);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AnnotationSplitter.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u0019H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u001b\u0010\b\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationSplitter$LazySplitAnnotations;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "Lorg/jetbrains/kotlin/resolve/lazy/LazyEntity;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "target", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationSplitter;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)V", "annotations", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotations$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "findAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "forceResolveAllContents", "", "hasAnnotation", "", "isEmpty", "iterator", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class LazySplitAnnotations implements Annotations, LazyEntity {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazySplitAnnotations.class), "annotations", "getAnnotations()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;"))};

        /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue annotations;
        private final AnnotationUseSiteTarget target;
        final /* synthetic */ AnnotationSplitter this$0;

        public LazySplitAnnotations(final AnnotationSplitter annotationSplitter, StorageManager storageManager, AnnotationUseSiteTarget annotationUseSiteTarget) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            this.this$0 = annotationSplitter;
            this.target = annotationUseSiteTarget;
            this.annotations = storageManager.createLazyValue(new Function0<Annotations>() { // from class: org.jetbrains.kotlin.descriptors.annotations.AnnotationSplitter$LazySplitAnnotations$annotations$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function0
                public final Annotations invoke() {
                    Annotations annotationsCreate;
                    Pair pair = (Pair) annotationSplitter.splitAnnotations.invoke();
                    HashMap map = (HashMap) pair.component1();
                    Annotations annotations = (Annotations) pair.component2();
                    if (this.getTarget() == null) {
                        return annotations;
                    }
                    List<? extends AnnotationDescriptor> list = (List) map.get(this.getTarget());
                    return (list == null || (annotationsCreate = Annotations.INSTANCE.create(list)) == null) ? Annotations.INSTANCE.getEMPTY() : annotationsCreate;
                }
            });
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
        @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
        public List<AnnotationWithTarget> getUseSiteTargetedAnnotations() {
            return Annotations.DefaultImpls.getUseSiteTargetedAnnotations(this);
        }

        public final AnnotationUseSiteTarget getTarget() {
            return this.target;
        }

        private final Annotations getAnnotations() {
            return (Annotations) StorageKt.getValue(this.annotations, this, (KProperty<?>) $$delegatedProperties[0]);
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyEntity
        public void forceResolveAllContents() {
            for (AnnotationDescriptor annotationDescriptor : this) {
            }
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
        public boolean isEmpty() {
            return getAnnotations().isEmpty();
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
        public boolean hasAnnotation(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return getAnnotations().hasAnnotation(fqName);
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
        /* JADX INFO: renamed from: findAnnotation */
        public AnnotationDescriptor mo6752findAnnotation(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return getAnnotations().mo6752findAnnotation(fqName);
        }

        @Override // java.lang.Iterable
        public Iterator<AnnotationDescriptor> iterator() {
            return getAnnotations().iterator();
        }

        public String toString() {
            return getAnnotations().toString();
        }
    }
}
