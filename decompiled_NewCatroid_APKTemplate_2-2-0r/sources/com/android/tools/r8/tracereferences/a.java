package com.android.tools.r8.tracereferences;

import com.android.tools.r8.internal.C2471lZ;
import com.android.tools.r8.internal.UT$$ExternalSyntheticLambda5;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.PackageReference;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.Y2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class a {
    static final /* synthetic */ boolean b = true;
    private final StringBuilder a = new StringBuilder();

    a() {
    }

    final String a() {
        return this.a.toString();
    }

    protected abstract void a(List list);

    protected abstract void b(TraceReferencesConsumer.TracedClass tracedClass);

    protected abstract void b(TraceReferencesConsumer.TracedField tracedField);

    protected final void b(String str) {
        this.a.append(Y2.b(str));
    }

    protected final void a(String str) {
        this.a.append(str);
    }

    protected final void b(MethodReference methodReference) {
        if (methodReference.getMethodName().equals("<init>")) {
            ((b) this).a.append("<init>");
            return;
        }
        this.a.append(methodReference.getReturnType() != null ? methodReference.getReturnType().getTypeName() : PsiKeyword.VOID);
        this.a.append(" ");
        this.a.append(methodReference.getMethodName());
    }

    protected final void a(MethodReference methodReference) {
        Y2.a(this.a, V1.a((Collection) methodReference.getFormalTypes(), (Function) new UT$$ExternalSyntheticLambda5()), ",", Y2.a.PARENS);
    }

    final void a(C2471lZ c2471lZ) {
        a(c2471lZ.b(), c2471lZ.c(), c2471lZ.a(), c2471lZ.d());
    }

    private void a(Map map, Map map2, Set set, Set set2) {
        ArrayList<TraceReferencesConsumer.TracedClass> arrayList = new ArrayList(set);
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.tracereferences.a$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return a.a((TraceReferencesConsumer.TracedClass) obj);
            }
        }));
        for (TraceReferencesConsumer.TracedClass tracedClass : arrayList) {
            Set<TraceReferencesConsumer.TracedMethod> set3 = (Set) map2.getOrDefault(tracedClass.getReference(), Collections.emptySet());
            Set set4 = (Set) map.getOrDefault(tracedClass.getReference(), Collections.emptySet());
            if (!tracedClass.isMissingDefinition()) {
                b(tracedClass);
                ArrayList<TraceReferencesConsumer.TracedMethod> arrayList2 = new ArrayList(set3.size());
                for (TraceReferencesConsumer.TracedMethod tracedMethod : set3) {
                    if (!tracedMethod.isMissingDefinition()) {
                        if (!b && tracedMethod.getAccessFlags() == null) {
                            throw new AssertionError();
                        }
                        arrayList2.add(tracedMethod);
                    }
                }
                arrayList2.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.tracereferences.a$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return a.a((TraceReferencesConsumer.TracedMethod) obj);
                    }
                }));
                for (TraceReferencesConsumer.TracedMethod tracedMethod2 : arrayList2) {
                    b bVar = (b) this;
                    if (!tracedMethod2.getReference().getMethodName().equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                        bVar.a("  ");
                        if (tracedMethod2.getAccessFlags().isPublic()) {
                            bVar.a("public ");
                        } else if (tracedMethod2.getAccessFlags().isPrivate()) {
                            bVar.a("private ");
                        } else if (tracedMethod2.getAccessFlags().isProtected()) {
                            bVar.a("protected ");
                        }
                        if (tracedMethod2.getAccessFlags().isStatic()) {
                            bVar.a("static ");
                        }
                        bVar.b(tracedMethod2.getReference());
                        bVar.a(tracedMethod2.getReference());
                        bVar.b(";");
                    }
                }
                ArrayList<TraceReferencesConsumer.TracedField> arrayList3 = new ArrayList(set4);
                arrayList3.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.tracereferences.a$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return a.a((TraceReferencesConsumer.TracedField) obj);
                    }
                }));
                for (TraceReferencesConsumer.TracedField tracedField : arrayList3) {
                    if (!tracedField.isMissingDefinition()) {
                        b(tracedField);
                    }
                }
                ((b) this).b("}");
            }
        }
        a((List) set2.stream().map(new Function() { // from class: com.android.tools.r8.tracereferences.a$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PackageReference) obj).getPackageName();
            }
        }).sorted().collect(Collectors.toList()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(TraceReferencesConsumer.TracedClass tracedClass) {
        return tracedClass.getReference().getTypeName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(TraceReferencesConsumer.TracedMethod tracedMethod) {
        return tracedMethod.getReference().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(TraceReferencesConsumer.TracedField tracedField) {
        return tracedField.getReference().toString();
    }
}
