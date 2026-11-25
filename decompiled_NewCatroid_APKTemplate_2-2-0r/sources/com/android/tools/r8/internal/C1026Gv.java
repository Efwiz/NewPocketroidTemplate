package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1026Gv extends AbstractProcessor {
    public static String a(String str) {
        return str + "$$KeepEdges";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(String str) {
        return new ArrayList();
    }

    public final boolean process(Set set, RoundEnvironment roundEnvironment) {
        AnnotationMirror annotationMirror;
        C2736ov c2736ovA;
        HashMap map = new HashMap();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            for (Element enclosingElement : roundEnvironment.getElementsAnnotatedWith((TypeElement) it.next())) {
                String name2 = C2043g2.a.a.getName();
                Iterator it2 = enclosingElement.getAnnotationMirrors().iterator();
                do {
                    if (!it2.hasNext()) {
                        annotationMirror = null;
                        break;
                    }
                    annotationMirror = (AnnotationMirror) it2.next();
                } while (!annotationMirror.getAnnotationType().toString().equals(name2));
                if (annotationMirror == null) {
                    c2736ovA = null;
                } else {
                    C2736ov.a aVarA = C2736ov.a();
                    b(aVarA, annotationMirror);
                    a(aVarA, annotationMirror);
                    c2736ovA = aVarA.a();
                }
                if (c2736ovA != null) {
                    while (enclosingElement != null && !(enclosingElement instanceof TypeElement)) {
                        enclosingElement = enclosingElement.getEnclosingElement();
                    }
                    ((List) map.computeIfAbsent(((TypeElement) enclosingElement).getQualifiedName().toString(), new Function() { // from class: com.android.tools.r8.internal.Gv$$ExternalSyntheticLambda2
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return C1026Gv.b((String) obj);
                        }
                    })).add(c2736ovA);
                }
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            String strA = a((String) entry.getKey());
            List list = (List) entry.getValue();
            String strReplace = strA.replace('.', '/');
            C1549Za c1549Za = new C1549Za(0);
            c1549Za.a(C2422kw.h, 49, strReplace, null, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, null);
            c1549Za.a("SynthesizedKeepEdge", (String) null);
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                C2651nw.a((C2736ov) it3.next(), c1549Za);
            }
            try {
                ((AbstractProcessor) this).processingEnv.getFiler().createClassFile(strA, new Element[0]).openOutputStream().write(c1549Za.b());
            } catch (IOException e) {
                ((AbstractProcessor) this).processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC2190hx.a aVar, AnnotationValue annotationValue) {
        C2572mv.a aVarA = C2572mv.a();
        AnnotationMirror annotationMirror = ((C0871Bv) new C0871Bv().a(annotationValue)).a;
        C0846Aw.a aVarB = C0846Aw.b();
        a(aVarB, annotationMirror);
        aVarA.a(aVarB.a());
        aVar.a(aVarA.a());
    }

    private void b(C2736ov.a aVar, AnnotationMirror annotationMirror) {
        AnnotationValue annotationValueA = a(annotationMirror, "preconditions");
        if (annotationValueA == null) {
            return;
        }
        final AbstractC2190hx.a aVarB = AbstractC2190hx.b();
        new C0845Av(new Consumer() { // from class: com.android.tools.r8.internal.Gv$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(aVarB, (AnnotationValue) obj);
            }
        }).a(annotationValueA);
        aVar.a(aVarB.a());
    }

    private void a(C2736ov.a aVar, AnnotationMirror annotationMirror) {
        AnnotationValue annotationValueA = a(annotationMirror, "consequences");
        if (annotationValueA == null) {
            return;
        }
        final C2649nv.a aVarA = C2649nv.a();
        new C0845Av(new Consumer() { // from class: com.android.tools.r8.internal.Gv$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(aVarA, (AnnotationValue) obj);
            }
        }).a(annotationValueA);
        aVar.a(aVarA.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2649nv.a aVar, AnnotationValue annotationValue) {
        C2814px.a aVarA = C2814px.a();
        AnnotationMirror annotationMirror = ((C0871Bv) new C0871Bv().a(annotationValue)).a;
        C0846Aw.a aVarB = C0846Aw.b();
        a(aVarB, annotationMirror);
        aVarA.a(aVarB.a());
        aVar.a(aVarA.a());
    }

    private static String a(DeclaredType declaredType) {
        try {
            Object obj = declaredType.getClass().getField("tsym").get(declaredType);
            return obj.getClass().getField("flatname").get(obj).toString();
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            throw new C2883qv("Unable to obtain the class type name for: " + declaredType);
        }
    }

    private static void a(C0846Aw.a aVar, AnnotationMirror annotationMirror) {
        AnnotationValue annotationValueA = a(annotationMirror, "classConstant");
        if (annotationValueA != null) {
            aVar.a(C2424kx.a(a(((C3535zv) new C3535zv().a(annotationValueA)).a)));
        }
        AnnotationValue annotationValueA2 = a(annotationMirror, "methodName");
        AnnotationValue annotationValueA3 = a(annotationMirror, "fieldName");
        if (annotationValueA2 != null && annotationValueA3 != null) {
            throw new C2883qv("Cannot define both a method and a field name pattern");
        }
        if (annotationValueA2 != null) {
            aVar.a(C1463Vw.k().a(AbstractC1309Qw.a(((C0911Cv) new C0911Cv().a(annotationValueA2)).a)).a());
        } else if (annotationValueA3 != null) {
            aVar.a(C3319ww.k().a(AbstractC3244vw.a(((C0911Cv) new C0911Cv().a(annotationValueA3)).a)).a());
        }
    }

    private static AnnotationValue a(AnnotationMirror annotationMirror, String str) {
        for (Map.Entry entry : annotationMirror.getElementValues().entrySet()) {
            if (((ExecutableElement) entry.getKey()).getSimpleName().toString().equals(str)) {
                return (AnnotationValue) entry.getValue();
            }
        }
        return null;
    }
}
