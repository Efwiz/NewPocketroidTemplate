package org.jetbrains.kotlin.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaLoadingKt;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: DeclaredMemberIndex.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0016\u001a\u00020\u000bH\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\u00182\u0006\u0010\u0016\u001a\u00020\u000bH\u0016J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0016\u001a\u00020\u000bH\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001bH\u0016J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001bH\u0016J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001bH\u0016R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00140\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/ClassDeclaredMemberIndex;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/DeclaredMemberIndex;", "jClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "memberFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lkotlin/jvm/functions/Function1;)V", "components", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getJClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "methodFilter", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "methods", "", "findFieldByName", "name", "findMethodsByName", "", "findRecordComponentByName", "getFieldNames", "", "getMethodNames", "getRecordComponentNames", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ClassDeclaredMemberIndex implements DeclaredMemberIndex {
    private final Map<Name, JavaRecordComponent> components;
    private final Map<Name, JavaField> fields;
    private final JavaClass jClass;
    private final Function1<JavaMember, Boolean> memberFilter;
    private final Function1<JavaMethod, Boolean> methodFilter;
    private final Map<Name, List<JavaMethod>> methods;

    /* JADX WARN: Multi-variable type inference failed */
    public ClassDeclaredMemberIndex(JavaClass jClass, Function1<? super JavaMember, Boolean> memberFilter) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(memberFilter, "memberFilter");
        this.jClass = jClass;
        this.memberFilter = memberFilter;
        Function1<JavaMethod, Boolean> function1 = new Function1<JavaMethod, Boolean>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.ClassDeclaredMemberIndex$methodFilter$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JavaMethod m) {
                Intrinsics.checkNotNullParameter(m, "m");
                return Boolean.valueOf(((Boolean) this.this$0.memberFilter.invoke(m)).booleanValue() && !JavaLoadingKt.isObjectMethodInInterface(m));
            }
        };
        this.methodFilter = function1;
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(jClass.mo7255getMethods()), function1);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : sequenceFilter) {
            Name name2 = ((JavaMethod) obj).getName();
            Object obj2 = linkedHashMap.get(name2);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(name2, obj2);
            }
            ((List) obj2).add(obj);
        }
        this.methods = linkedHashMap;
        Sequence sequenceFilter2 = SequencesKt.filter(CollectionsKt.asSequence(this.jClass.mo7254getFields()), this.memberFilter);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj3 : sequenceFilter2) {
            linkedHashMap2.put(((JavaField) obj3).getName(), obj3);
        }
        this.fields = linkedHashMap2;
        Collection<JavaRecordComponent> collectionMo7257getRecordComponents = this.jClass.mo7257getRecordComponents();
        Function1<JavaMember, Boolean> function2 = this.memberFilter;
        ArrayList arrayList = new ArrayList();
        for (Object obj4 : collectionMo7257getRecordComponents) {
            if (function2.invoke((JavaMember) obj4).booleanValue()) {
                arrayList.add(obj4);
            }
        }
        ArrayList arrayList2 = arrayList;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (Object obj5 : arrayList2) {
            linkedHashMap3.put(((JavaRecordComponent) obj5).getName(), obj5);
        }
        this.components = linkedHashMap3;
    }

    public final JavaClass getJClass() {
        return this.jClass;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
    public Collection<JavaMethod> findMethodsByName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List<JavaMethod> listEmptyList = this.methods.get(name2);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
    public Set<Name> getMethodNames() {
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(this.jClass.mo7255getMethods()), this.methodFilter);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((JavaMethod) it.next()).getName());
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
    public JavaField findFieldByName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.fields.get(name2);
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
    public Set<Name> getFieldNames() {
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(this.jClass.mo7254getFields()), this.memberFilter);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((JavaField) it.next()).getName());
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
    public Set<Name> getRecordComponentNames() {
        return this.components.keySet();
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
    public JavaRecordComponent findRecordComponentByName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.components.get(name2);
    }
}
