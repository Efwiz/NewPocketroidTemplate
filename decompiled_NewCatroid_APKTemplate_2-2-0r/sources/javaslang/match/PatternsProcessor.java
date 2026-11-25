package javaslang.match;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javaslang.match.annotation.Patterns;
import javaslang.match.annotation.Unapply;
import javaslang.match.generator.Generator;
import javaslang.match.model.ClassModel;
import javaslang.match.model.MethodModel;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/* JADX INFO: loaded from: classes11.dex */
public class PatternsProcessor extends AbstractProcessor {
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Patterns.class.getName());
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            return true;
        }
        Set<TypeElement> set2 = (Set) roundEnvironment.getElementsAnnotatedWith(Patterns.class).stream().filter(new Predicate() { // from class: javaslang.match.PatternsProcessor$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PatternsProcessor.lambda$process$0((Element) obj);
            }
        }).map(new Function() { // from class: javaslang.match.PatternsProcessor$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PatternsProcessor.lambda$process$1((Element) obj);
            }
        }).collect(Collectors.toSet());
        if (set2.isEmpty()) {
            return true;
        }
        Set<ClassModel> setTransform = transform(set2);
        if (setTransform.isEmpty()) {
            return true;
        }
        generate(setTransform);
        return true;
    }

    static /* synthetic */ boolean lambda$process$0(Element element) {
        return element instanceof TypeElement;
    }

    static /* synthetic */ TypeElement lambda$process$1(Element element) {
        return (TypeElement) element;
    }

    private Set<ClassModel> transform(Set<TypeElement> set) {
        HashSet hashSet = new HashSet();
        Elements elementUtils = this.processingEnv.getElementUtils();
        final Messager messager = this.processingEnv.getMessager();
        Iterator<TypeElement> it = set.iterator();
        while (it.hasNext()) {
            ClassModel classModelOf = ClassModel.of(elementUtils, it.next());
            List list = (List) classModelOf.getMethods().stream().filter(new Predicate() { // from class: javaslang.match.PatternsProcessor$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((MethodModel) obj).isAnnotatedWith(Unapply.class);
                }
            }).collect(Collectors.toList());
            if (list.isEmpty()) {
                messager.printMessage(Diagnostic.Kind.WARNING, "No @Unapply methods found.", classModelOf.typeElement());
            } else if (((Boolean) list.stream().reduce(true, new BiFunction() { // from class: javaslang.match.PatternsProcessor$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return Boolean.valueOf(((Boolean) obj).booleanValue() && UnapplyChecker.isValid(((MethodModel) obj2).getExecutableElement(), messager));
                }
            }, new BinaryOperator() { // from class: javaslang.match.PatternsProcessor$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return Boolean.valueOf(((Boolean) obj).booleanValue() && ((Boolean) obj2).booleanValue());
                }
            })).booleanValue()) {
                hashSet.add(classModelOf);
            }
        }
        return hashSet;
    }

    private void generate(Set<ClassModel> set) {
        Filer filer = this.processingEnv.getFiler();
        for (ClassModel classModel : set) {
            String strDeriveClassName = deriveClassName(classModel);
            String strGenerate = Generator.generate(strDeriveClassName, classModel);
            String str = (classModel.hasDefaultPackage() ? "" : classModel.getPackageName() + ".") + strDeriveClassName;
            try {
                Writer writerOpenWriter = filer.createSourceFile(str, new Element[]{classModel.typeElement()}).openWriter();
                try {
                    writerOpenWriter.write(strGenerate);
                    if (writerOpenWriter != null) {
                        writerOpenWriter.close();
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        if (writerOpenWriter != null) {
                            try {
                                writerOpenWriter.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                        }
                        throw th2;
                    }
                }
            } catch (IOException e) {
                throw new Error("Error writing " + str, e);
            }
        }
    }

    private String deriveClassName(ClassModel classModel) {
        String className = classModel.getClassName();
        StringBuilder sb = new StringBuilder();
        if ("$".equals(className)) {
            className = "";
        } else if (className.endsWith(".$")) {
            className = className.substring(0, className.length() - 2);
        }
        return sb.append(className.replaceAll("\\.", "_")).append("Patterns").toString();
    }
}
