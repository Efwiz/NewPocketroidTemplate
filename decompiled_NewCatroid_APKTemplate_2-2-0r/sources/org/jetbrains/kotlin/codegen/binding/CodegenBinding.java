package org.jetbrains.kotlin.codegen.binding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.backend.common.SamType;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.when.WhenByEnumsMapping;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice;
import org.jetbrains.kotlin.util.slicedMap.Slices;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class CodegenBinding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final WritableSlice<ClassDescriptor, MutableClosure> CLOSURE = Slices.createSimpleSlice();
    public static final WritableSlice<CallableDescriptor, ClassDescriptor> CLASS_FOR_CALLABLE = Slices.createSimpleSlice();
    public static final WritableSlice<ClassDescriptor, Type> ASM_TYPE = Slices.createSimpleSlice();
    public static final WritableSlice<ClassDescriptor, Boolean> ENUM_ENTRY_CLASS_NEED_SUBCLASS = Slices.createSimpleSetSlice();
    private static final WritableSlice<ClassDescriptor, Collection<ClassDescriptor>> INNER_CLASSES = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, SamType> SAM_VALUE = Slices.createSimpleSlice();
    public static final WritableSlice<KtCallElement, KtExpression> SAM_CONSTRUCTOR_TO_ARGUMENT = Slices.createSimpleSlice();
    public static final WritableSlice<KtWhenExpression, WhenByEnumsMapping> MAPPING_FOR_WHEN_BY_ENUM = Slices.createSimpleSlice();
    public static final WritableSlice<String, List<WhenByEnumsMapping>> MAPPINGS_FOR_WHENS_BY_ENUM_IN_CLASS_FILE = Slices.createSimpleSlice();
    public static final WritableSlice<FunctionDescriptor, FunctionDescriptor> SUSPEND_FUNCTION_TO_JVM_VIEW = Slices.createSimpleSlice();
    public static final WritableSlice<FunctionDescriptor, Boolean> CAPTURES_CROSSINLINE_LAMBDA = Slices.createSimpleSlice();
    public static final WritableSlice<ValueParameterDescriptor, Boolean> CALL_SITE_IS_SUSPEND_FOR_CROSSINLINE_LAMBDA = Slices.createSimpleSlice();
    public static final WritableSlice<ClassDescriptor, Boolean> RECURSIVE_SUSPEND_CALLABLE_REFERENCE = Slices.createSimpleSlice();
    public static final WritableSlice<ValueParameterDescriptor, ValueParameterDescriptor> PARAMETER_SYNONYM = Slices.createSimpleSlice();
    public static final WritableSlice<Type, List<VariableDescriptorWithAccessors>> DELEGATED_PROPERTIES_WITH_METADATA = Slices.createSimpleSlice();
    public static final WritableSlice<VariableDescriptorWithAccessors, Type> DELEGATED_PROPERTY_METADATA_OWNER = Slices.createSimpleSlice();
    public static final WritableSlice<VariableDescriptor, VariableDescriptor> LOCAL_VARIABLE_PROPERTY_METADATA = Slices.createSimpleSlice();
    public static final WritableSlice<FunctionDescriptor, Boolean> PROPERTY_METADATA_REQUIRED_FOR_OPERATOR_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<VariableDescriptorWithAccessors, Boolean> DELEGATED_PROPERTY_WITH_OPTIMIZED_METADATA = Slices.createSimpleSlice();
    public static final WritableSlice<FunctionDescriptor, String> CALL_LABEL_FOR_LAMBDA_ARGUMENT = Slices.createSimpleSlice();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 10 || i == 18 || i == 22 || i == 25 || i == 28) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 10 || i == 18 || i == 22 || i == 25 || i == 28) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "owner";
                break;
            case 2:
                objArr[0] = "state";
                break;
            case 3:
            case 6:
            case 8:
            case 11:
            case 13:
            case 23:
            case 27:
            default:
                objArr[0] = "bindingContext";
                break;
            case 4:
            case 7:
                objArr[0] = "expression";
                break;
            case 5:
            case 10:
            case 18:
            case 22:
            case 25:
            case 28:
                objArr[0] = "org/jetbrains/kotlin/codegen/binding/CodegenBinding";
                break;
            case 9:
            case 12:
                objArr[0] = "descriptor";
                break;
            case 14:
            case 16:
                objArr[0] = "classDescriptor";
                break;
            case 15:
                objArr[0] = "trace";
                break;
            case 17:
                objArr[0] = "asmType";
                break;
            case 19:
                objArr[0] = "bindingTrace";
                break;
            case 20:
                objArr[0] = "outer";
                break;
            case 21:
                objArr[0] = "inner";
                break;
            case 24:
                objArr[0] = "klass";
                break;
            case 26:
                objArr[0] = "variableDescriptor";
                break;
        }
        if (i == 5 || i == 10) {
            objArr[1] = "asmTypeForAnonymousClass";
        } else if (i == 18) {
            objArr[1] = "recordClosure";
        } else if (i == 22) {
            objArr[1] = "allFilesInPackages";
        } else if (i == 25) {
            objArr[1] = "getAsmType";
        } else if (i != 28) {
            objArr[1] = "org/jetbrains/kotlin/codegen/binding/CodegenBinding";
        } else {
            objArr[1] = "getDelegatedLocalVariableMetadata";
        }
        switch (i) {
            case 2:
                objArr[2] = "initTrace";
                break;
            case 3:
            case 4:
            case 8:
            case 9:
                objArr[2] = "asmTypeForAnonymousClass";
                break;
            case 5:
            case 10:
            case 18:
            case 22:
            case 25:
            case 28:
                break;
            case 6:
            case 7:
            case 11:
            case 12:
                objArr[2] = "asmTypeForAnonymousClassOrNull";
                break;
            case 13:
            case 14:
                objArr[2] = "canHaveOuter";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "recordClosure";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "recordInnerClass";
                break;
            case 23:
            case 24:
                objArr[2] = "getAsmType";
                break;
            case 26:
            case 27:
                objArr[2] = "getDelegatedLocalVariableMetadata";
                break;
            default:
                objArr[2] = "getLocalDelegatedProperties";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 10 && i != 18 && i != 22 && i != 25 && i != 28) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        BasicWritableSlice.initSliceDebugNames(CodegenBinding.class);
    }

    private CodegenBinding() {
    }

    public static List<LocalVariableDescriptor> getLocalDelegatedProperties(BindingContext bindingContext, Type type) {
        if (bindingContext == null) {
            $$$reportNull$$$0(0);
        }
        if (type == null) {
            $$$reportNull$$$0(1);
        }
        List list = (List) bindingContext.get(DELEGATED_PROPERTIES_WITH_METADATA, type);
        if (list == null) {
            return null;
        }
        return CollectionsKt.filterIsInstance(list, LocalVariableDescriptor.class);
    }

    public static void initTrace(GenerationState generationState, Collection<KtFile> collection) {
        PsiElement context;
        PsiFile containingFile;
        if (generationState == null) {
            $$$reportNull$$$0(2);
        }
        CodegenAnnotatingVisitor codegenAnnotatingVisitor = new CodegenAnnotatingVisitor(generationState);
        for (KtFile ktFile : allFilesInPackages(generationState.getBindingContext(), collection)) {
            ktFile.accept(codegenAnnotatingVisitor);
            if ((ktFile instanceof KtCodeFragment) && (context = ktFile.getContext()) != null && (containingFile = context.getContainingFile()) != null) {
                containingFile.accept(codegenAnnotatingVisitor);
            }
        }
    }

    public static boolean enumEntryNeedSubclass(BindingContext bindingContext, KtEnumEntry ktEnumEntry) {
        return enumEntryNeedSubclass(bindingContext, (ClassDescriptor) bindingContext.get(BindingContext.CLASS, ktEnumEntry));
    }

    public static boolean enumEntryNeedSubclass(BindingContext bindingContext, ClassDescriptor classDescriptor) {
        return Boolean.TRUE.equals(bindingContext.get(ENUM_ENTRY_CLASS_NEED_SUBCLASS, classDescriptor));
    }

    public static Type asmTypeForAnonymousClass(BindingContext bindingContext, KtElement ktElement) {
        if (bindingContext == null) {
            $$$reportNull$$$0(3);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(4);
        }
        Type typeAsmTypeForAnonymousClassOrNull = asmTypeForAnonymousClassOrNull(bindingContext, ktElement);
        if (typeAsmTypeForAnonymousClassOrNull == null) {
            throw new KotlinExceptionWithAttachments("Couldn't compute ASM type for expression").withPsiAttachment("expression.kt", ktElement);
        }
        if (typeAsmTypeForAnonymousClassOrNull == null) {
            $$$reportNull$$$0(5);
        }
        return typeAsmTypeForAnonymousClassOrNull;
    }

    public static Type asmTypeForAnonymousClassOrNull(BindingContext bindingContext, KtElement ktElement) {
        if (bindingContext == null) {
            $$$reportNull$$$0(6);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(7);
        }
        if (ktElement instanceof KtObjectLiteralExpression) {
            ktElement = ((KtObjectLiteralExpression) ktElement).getObjectDeclaration();
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) bindingContext.get(BindingContext.CLASS, ktElement);
        if (classDescriptor != null) {
            return (Type) bindingContext.get(ASM_TYPE, classDescriptor);
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) bindingContext.get(BindingContext.FUNCTION, ktElement);
        if (simpleFunctionDescriptor != null) {
            return asmTypeForAnonymousClassOrNull(bindingContext, simpleFunctionDescriptor);
        }
        VariableDescriptor variableDescriptor = (VariableDescriptor) bindingContext.get(BindingContext.VARIABLE, ktElement);
        if (variableDescriptor != null) {
            return asmTypeForAnonymousClassOrNull(bindingContext, variableDescriptor);
        }
        return null;
    }

    public static Type asmTypeForAnonymousClass(BindingContext bindingContext, CallableDescriptor callableDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(8);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        Type typeAsmTypeForAnonymousClassOrNull = asmTypeForAnonymousClassOrNull(bindingContext, callableDescriptor);
        if (typeAsmTypeForAnonymousClassOrNull == null) {
            throw new IllegalStateException("Type must not be null: " + callableDescriptor);
        }
        if (typeAsmTypeForAnonymousClassOrNull == null) {
            $$$reportNull$$$0(10);
        }
        return typeAsmTypeForAnonymousClassOrNull;
    }

    public static Type asmTypeForAnonymousClassOrNull(BindingContext bindingContext, CallableDescriptor callableDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(11);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) bindingContext.get(CLASS_FOR_CALLABLE, callableDescriptor);
        if (classDescriptor == null) {
            return null;
        }
        return (Type) bindingContext.get(ASM_TYPE, classDescriptor);
    }

    public static boolean canHaveOuter(BindingContext bindingContext, ClassDescriptor classDescriptor) {
        MutableClosure mutableClosure;
        if (bindingContext == null) {
            $$$reportNull$$$0(13);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        if (classDescriptor.getKind() != ClassKind.CLASS || (mutableClosure = (MutableClosure) bindingContext.get(CLOSURE, classDescriptor)) == null || mutableClosure.getEnclosingClass() == null) {
            return false;
        }
        DeclarationDescriptor packageFragment = classDescriptor.getContainingDeclaration();
        return classDescriptor.getIsInner() || (packageFragment instanceof ScriptDescriptor) || !(packageFragment instanceof ClassDescriptor);
    }

    static MutableClosure recordClosure(BindingTrace bindingTrace, ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2, Type type) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(15);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        if (type == null) {
            $$$reportNull$$$0(17);
        }
        MutableClosure mutableClosure = new MutableClosure(classDescriptor, classDescriptor2);
        if (classDescriptor.getIsInner()) {
            mutableClosure.setNeedsCaptureOuterClass();
        }
        bindingTrace.record(ASM_TYPE, classDescriptor, type);
        bindingTrace.record(CLOSURE, classDescriptor, mutableClosure);
        if (classDescriptor2 != null && !JvmCodegenUtil.isArgumentWhichWillBeInlined(bindingTrace.getContext(), classDescriptor)) {
            recordInnerClass(bindingTrace, classDescriptor2, classDescriptor);
        }
        return mutableClosure;
    }

    private static void recordInnerClass(BindingTrace bindingTrace, ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(19);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        if (classDescriptor2 == null) {
            $$$reportNull$$$0(21);
        }
        WritableSlice<ClassDescriptor, Collection<ClassDescriptor>> writableSlice = INNER_CLASSES;
        Collection arrayList = (Collection) bindingTrace.get(writableSlice, classDescriptor);
        if (arrayList == null) {
            arrayList = new ArrayList(1);
            bindingTrace.record(writableSlice, classDescriptor, arrayList);
        }
        arrayList.add(classDescriptor2);
    }

    private static Collection<KtFile> allFilesInPackages(BindingContext bindingContext, Collection<KtFile> collection) {
        HashSet hashSet = new HashSet();
        for (KtFile ktFile : collection) {
            if (!ktFile.isScript()) {
                hashSet.add(ktFile.getPackageFqName());
            }
        }
        HashSet hashSet2 = new HashSet();
        hashSet2.addAll(collection);
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Collection collection2 = (Collection) bindingContext.get(BindingContext.PACKAGE_TO_FILES, (FqName) it.next());
            if (collection2 != null) {
                hashSet2.addAll(collection2);
            }
        }
        ArrayList arrayList = new ArrayList(hashSet2);
        arrayList.sort(Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.codegen.binding.CodegenBinding$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((KtFile) obj).getVirtualFile().getPath();
            }
        }));
        return arrayList;
    }

    public static Type getAsmType(BindingContext bindingContext, ClassDescriptor classDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(23);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        Type type = (Type) bindingContext.get(ASM_TYPE, classDescriptor);
        if (type == null) {
            throw new IllegalStateException("Type is not yet recorded for " + classDescriptor);
        }
        if (type == null) {
            $$$reportNull$$$0(25);
        }
        return type;
    }

    public static VariableDescriptor getDelegatedLocalVariableMetadata(VariableDescriptor variableDescriptor, BindingContext bindingContext) {
        if (variableDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(27);
        }
        VariableDescriptor variableDescriptor2 = (VariableDescriptor) bindingContext.get(LOCAL_VARIABLE_PROPERTY_METADATA, variableDescriptor);
        if (variableDescriptor2 == null) {
            $$$reportNull$$$0(28);
        }
        return variableDescriptor2;
    }
}
