package javaslang.match;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

/* JADX INFO: loaded from: classes11.dex */
class UnapplyChecker {
    static boolean isValid(ExecutableElement executableElement, Messager messager) {
        if (!ensure(executableElement, doesNotThrow(executableElement), messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return UnapplyChecker.lambda$isValid$0();
            }
        }) || !ensure(executableElement, !executableElement.isDefault(), messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return UnapplyChecker.lambda$isValid$1();
            }
        }) || !ensure(executableElement, !executableElement.isVarArgs(), messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return UnapplyChecker.lambda$isValid$2();
            }
        })) {
            return false;
        }
        if (ensure(executableElement, executableElement.getParameters().size() == 1, messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return UnapplyChecker.lambda$isValid$3();
            }
        })) {
            return ensure(executableElement, ((VariableElement) executableElement.getParameters().get(0)).asType().getKind() == TypeKind.DECLARED, messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return UnapplyChecker.lambda$isValid$4();
                }
            }) && ensure(executableElement, executableElement.getReturnType().toString().startsWith("javaslang.Tuple"), messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return UnapplyChecker.lambda$isValid$5();
                }
            }) && ensure(executableElement, executableElement.getReturnType().toString().endsWith("Tuple") ^ true, messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return UnapplyChecker.lambda$isValid$6();
                }
            }) && ensure(executableElement, hasAll(executableElement, Modifier.STATIC), messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return UnapplyChecker.lambda$isValid$7();
                }
            }) && ensure(executableElement, hasNone(executableElement, Modifier.PRIVATE, Modifier.PROTECTED, Modifier.ABSTRACT), messager, new Supplier() { // from class: javaslang.match.UnapplyChecker$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    return UnapplyChecker.lambda$isValid$8();
                }
            });
        }
        return false;
    }

    static /* synthetic */ String lambda$isValid$0() {
        return "@Unapply method should not throw (checked) exceptions.";
    }

    static /* synthetic */ String lambda$isValid$1() {
        return "@Unapply method needs to be declared in a class, not an interface.";
    }

    static /* synthetic */ String lambda$isValid$2() {
        return "@Unapply method has varargs.";
    }

    static /* synthetic */ String lambda$isValid$3() {
        return "Unapply method must have exactly one parameter of the object to be deconstructed.";
    }

    static /* synthetic */ String lambda$isValid$4() {
        return "Unapply method parameter must be a declared type.";
    }

    static /* synthetic */ String lambda$isValid$5() {
        return "Return type of unapply method must be a Tuple.";
    }

    static /* synthetic */ String lambda$isValid$6() {
        return "Return type is no Tuple implementation.";
    }

    static /* synthetic */ String lambda$isValid$7() {
        return "Unapply method needs to be static.";
    }

    static /* synthetic */ String lambda$isValid$8() {
        return "Unapply method may not be private or protected.";
    }

    private static boolean ensure(ExecutableElement executableElement, boolean z, Messager messager, Supplier<String> supplier) {
        if (!z) {
            messager.printMessage(Diagnostic.Kind.ERROR, supplier.get(), executableElement);
        }
        return z;
    }

    private static boolean hasAll(ExecutableElement executableElement, Modifier... modifierArr) {
        return executableElement.getModifiers().containsAll(Arrays.asList(modifierArr));
    }

    private static boolean hasNone(ExecutableElement executableElement, Modifier... modifierArr) {
        Set modifiers = executableElement.getModifiers();
        for (Modifier modifier : modifierArr) {
            if (modifiers.contains(modifier)) {
                return false;
            }
        }
        return true;
    }

    private static boolean doesNotThrow(ExecutableElement executableElement) {
        return executableElement.getThrownTypes().isEmpty();
    }
}
