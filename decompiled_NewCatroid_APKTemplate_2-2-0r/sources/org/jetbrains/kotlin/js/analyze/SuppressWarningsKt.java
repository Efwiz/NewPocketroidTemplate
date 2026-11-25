package org.jetbrains.kotlin.js.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: suppressWarnings.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"nativeAnnotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "isLexicallyInsideJsNative", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SuppressWarningsKt {
    private static final List<FqName> nativeAnnotations;

    static {
        Set<ClassId> set = JsStandardClassIds.Annotations.nativeAnnotations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(((ClassId) it.next()).asSingleFqName());
        }
        nativeAnnotations = arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isLexicallyInsideJsNative(DeclarationDescriptor declarationDescriptor) {
        do {
            Annotations annotations = declarationDescriptor.getAnnotations();
            if (!annotations.isEmpty()) {
                List<FqName> list = nativeAnnotations;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        if (annotations.hasAnnotation((FqName) it.next())) {
                            return true;
                        }
                    }
                }
            }
            declarationDescriptor = declarationDescriptor.getModuleDescriptor();
        } while (declarationDescriptor != null);
        return false;
    }
}
