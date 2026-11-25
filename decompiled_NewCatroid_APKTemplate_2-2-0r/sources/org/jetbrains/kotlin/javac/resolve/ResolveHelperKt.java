package org.jetbrains.kotlin.javac.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;

/* JADX INFO: compiled from: ResolveHelper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"collectAllSupertypes", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac-wrapper"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolveHelperKt {
    public static final Set<JavaClass> collectAllSupertypes(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "<this>");
        HashSet hashSetHashSetOf = SetsKt.hashSetOf(javaClass);
        Collection<JavaClassifierType> supertypes = javaClass.getSupertypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = supertypes.iterator();
        while (it.hasNext()) {
            JavaClassifier classifier = ((JavaClassifierType) it.next()).getClassifier();
            JavaClass javaClass2 = classifier instanceof JavaClass ? (JavaClass) classifier : null;
            if (javaClass2 != null) {
                arrayList.add(javaClass2);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            hashSetHashSetOf.addAll(collectAllSupertypes((JavaClass) it2.next()));
        }
        return hashSetHashSetOf;
    }
}
