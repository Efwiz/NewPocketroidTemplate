package org.jetbrains.kotlin.resolve.scopes;

import java.util.Collections;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: loaded from: classes6.dex */
public final class ScopeUtils {
    /* JADX WARN: Code duplicated, block: B:20:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "propertyDescriptor";
        } else if (i == 2) {
            objArr[0] = "propertyHeader";
        } else if (i == 3) {
            objArr[0] = "propertyDescriptor";
        } else if (i == 5) {
            objArr[0] = "variableDescriptor";
        } else if (i != 6) {
            objArr[0] = "parent";
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/scopes/ScopeUtils";
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/resolve/scopes/ScopeUtils";
        } else {
            objArr[1] = "printStructure";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "makeScopeForPropertyInitializer";
        } else if (i == 4 || i == 5) {
            objArr[2] = "makeScopeForDelegateConventionFunctions";
        } else if (i != 6) {
            objArr[2] = "makeScopeForPropertyHeader";
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    private ScopeUtils() {
    }

    public static LexicalScope makeScopeForPropertyHeader(LexicalScope lexicalScope, final PropertyDescriptor propertyDescriptor) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(0);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        return new LexicalScopeImpl(lexicalScope, propertyDescriptor, false, null, Collections.emptyList(), LexicalScopeKind.PROPERTY_HEADER, LocalRedeclarationChecker.DO_NOTHING.INSTANCE, new Function1() { // from class: org.jetbrains.kotlin.resolve.scopes.ScopeUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ScopeUtils.lambda$makeScopeForPropertyHeader$0(propertyDescriptor, (LexicalScopeImpl.InitializeHandler) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$makeScopeForPropertyHeader$0(PropertyDescriptor propertyDescriptor, LexicalScopeImpl.InitializeHandler initializeHandler) {
        Iterator<TypeParameterDescriptor> it = propertyDescriptor.getTypeParameters().iterator();
        while (it.hasNext()) {
            initializeHandler.addClassifierDescriptor(it.next());
        }
        return Unit.INSTANCE;
    }

    public static LexicalScope makeScopeForPropertyInitializer(LexicalScope lexicalScope, PropertyDescriptor propertyDescriptor) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(2);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        return new LexicalScopeImpl(lexicalScope, propertyDescriptor, false, null, Collections.emptyList(), LexicalScopeKind.PROPERTY_INITIALIZER_OR_DELEGATE);
    }

    public static LexicalScope makeScopeForDelegateConventionFunctions(LexicalScope lexicalScope, VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(4);
        }
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(5);
        }
        return new LexicalScopeImpl(lexicalScope, variableDescriptorWithAccessors, true, variableDescriptorWithAccessors.getExtensionReceiverParameter(), Collections.emptyList(), LexicalScopeKind.PROPERTY_DELEGATE_METHOD);
    }

    public static String printStructure(MemberScope memberScope) {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(sb);
        if (memberScope == null) {
            printer.println("null");
        } else {
            memberScope.mo6105printScopeStructure(printer);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(6);
        }
        return string;
    }
}
