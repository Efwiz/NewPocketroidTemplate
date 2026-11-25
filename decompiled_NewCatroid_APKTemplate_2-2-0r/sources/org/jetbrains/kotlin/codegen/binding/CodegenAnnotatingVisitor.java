package org.jetbrains.kotlin.codegen.binding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import net.bytebuddy.implementation.Implementation;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.backend.common.SamType;
import org.jetbrains.kotlin.backend.common.SamTypeApproximator;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cfg.WhenChecker;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.CodeFragmentCodegen;
import org.jetbrains.kotlin.codegen.CodeFragmentCodegenInfo;
import org.jetbrains.kotlin.codegen.DelegatedPropertiesCodegenHelper;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.JvmRuntimeTypes;
import org.jetbrains.kotlin.codegen.SamCodegenUtil;
import org.jetbrains.kotlin.codegen.SyntheticClassDescriptorForLambda;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.when.SwitchCodegenProvider;
import org.jetbrains.kotlin.codegen.when.WhenByEnumsMapping;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.coroutines.CoroutineUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.tower.NewVariableAsFunctionResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionsOnDeclarationBodyChecker;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
class CodegenAnnotatingVisitor extends KtVisitorVoid {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet BINARY_OPERATIONS = TokenSet.orSet(KtTokens.AUGMENTED_ASSIGNMENTS, TokenSet.create(KtTokens.PLUS, KtTokens.MINUS, KtTokens.MUL, KtTokens.DIV, KtTokens.PERC, KtTokens.RANGE, KtTokens.LT, KtTokens.GT, KtTokens.LTEQ, KtTokens.GTEQ, KtTokens.IDENTIFIER));
    private final Map<String, Integer> anonymousSubclassesCount;
    private final BindingContext bindingContext;
    private final BindingTrace bindingTrace;
    private final ClassBuilderMode classBuilderMode;
    private final Stack<ClassDescriptor> classStack;
    private final DelegatedPropertiesCodegenHelper delegatedPropertiesCodegenHelper;
    private final GenerationState.GenerateClassFilter filter;
    private final Stack<FunctionDescriptor> functionsStack;
    private final JvmDefaultMode jvmDefaultMode;
    private final LanguageVersionSettings languageVersionSettings;
    private final Stack<String> nameStack;
    private final JvmRuntimeTypes runtimeTypes;
    private final SamTypeApproximator samTypeApproximator;
    private final SwitchCodegenProvider switchCodegenProvider;
    private final Set<ClassDescriptor> uninitializedClasses;

    /* JADX WARN: Code duplicated, block: B:11:0x0020  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 12 && i != 29 && i != 14 && i != 15 && i != 58 && i != 59) {
            switch (i) {
                case 33:
                case 34:
                case 35:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 12 && i != 29 && i != 14 && i != 15 && i != 58 && i != 59) {
            switch (i) {
                case 33:
                case 34:
                case 35:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
            case 8:
            case 16:
            case 49:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 9:
                objArr[0] = "callableDescriptor";
                break;
            case 3:
            case 10:
                objArr[0] = "supertypes";
                break;
            case 4:
            case 7:
            case 11:
            case 28:
                objArr[0] = "name";
                break;
            case 6:
                objArr[0] = "functionDescriptor";
                break;
            case 12:
            case 14:
            case 15:
            case 29:
            case 33:
            case 34:
            case 35:
            case 58:
            case 59:
                objArr[0] = "org/jetbrains/kotlin/codegen/binding/CodegenAnnotatingVisitor";
                break;
            case 13:
            case 38:
                objArr[0] = "descriptor";
                break;
            case 17:
                objArr[0] = "script";
                break;
            case 18:
            case 57:
                objArr[0] = "file";
                break;
            case 19:
                objArr[0] = "enumEntry";
                break;
            case 20:
            case 31:
                objArr[0] = "declaration";
                break;
            case 21:
                objArr[0] = "klass";
                break;
            case 22:
            case 24:
            case 26:
            case 39:
            case 40:
            case 41:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
                objArr[0] = "expression";
                break;
            case 23:
                objArr[0] = "lambdaExpression";
                break;
            case 25:
                objArr[0] = "resolvedCall";
                break;
            case 27:
                objArr[0] = "classDescriptor";
                break;
            case 30:
            case 32:
                objArr[0] = "property";
                break;
            case 36:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 37:
                objArr[0] = "function";
                break;
            case 42:
            case 43:
            case 45:
            case 46:
            case 47:
            case 52:
                objArr[0] = "call";
                break;
            case 44:
                objArr[0] = "valueParametersWithSAMConversion";
                break;
            case 48:
                objArr[0] = "parameter";
                break;
            case 50:
                objArr[0] = "operation";
                break;
            case 60:
                objArr[0] = "stack";
                break;
            default:
                objArr[0] = "state";
                break;
        }
        if (i == 12) {
            objArr[1] = "recordClassForCallable";
        } else if (i == 29) {
            objArr[1] = "recordClosure";
        } else if (i == 14) {
            objArr[1] = "correctContainerForLambda";
        } else if (i == 15) {
            objArr[1] = "inventAnonymousClassName";
        } else if (i != 58 && i != 59) {
            switch (i) {
                case 33:
                case 34:
                case 35:
                    objArr[1] = "getMetadataOwner";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/codegen/binding/CodegenAnnotatingVisitor";
                    break;
            }
        } else {
            objArr[1] = "getCurrentTopLevelClassOrPackagePartInternalName";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "recordClassForCallable";
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "recordClassForFunction";
                break;
            case 12:
            case 14:
            case 15:
            case 29:
            case 33:
            case 34:
            case 35:
            case 58:
            case 59:
                break;
            case 13:
                objArr[2] = "correctContainerForLambda";
                break;
            case 16:
                objArr[2] = "visitKtElement";
                break;
            case 17:
                objArr[2] = "visitScript";
                break;
            case 18:
                objArr[2] = "visitKtFile";
                break;
            case 19:
                objArr[2] = "visitEnumEntry";
                break;
            case 20:
                objArr[2] = "visitObjectDeclaration";
                break;
            case 21:
                objArr[2] = "visitClass";
                break;
            case 22:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 23:
                objArr[2] = "visitLambdaExpression";
                break;
            case 24:
            case 25:
                objArr[2] = "isAdaptedCallableReference";
                break;
            case 26:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 27:
            case 28:
                objArr[2] = "recordClosure";
                break;
            case 30:
                objArr[2] = "visitProperty";
                break;
            case 31:
                objArr[2] = "checkRuntimeAsserionsOnDeclarationBody";
                break;
            case 32:
                objArr[2] = "getMetadataOwner";
                break;
            case 36:
                objArr[2] = "visitPropertyAccessor";
                break;
            case 37:
                objArr[2] = "visitNamedFunction";
                break;
            case 38:
                objArr[2] = "getNameForClassOrPackageMember";
                break;
            case 39:
                objArr[2] = "visitCallExpression";
                break;
            case 40:
                objArr[2] = "checkCrossinlineCall";
                break;
            case 41:
                objArr[2] = "checkSamCall";
                break;
            case 42:
                objArr[2] = "recordSamValuesForNewInference";
                break;
            case 43:
                objArr[2] = "getNewResolvedCallForCallWithPossibleSamConversions";
                break;
            case 44:
                objArr[2] = "writeSamValueForValueParameters";
                break;
            case 45:
                objArr[2] = "visitSuperTypeCallEntry";
                break;
            case 46:
                objArr[2] = "isSuperTypeCallForAnonymousObject";
                break;
            case 47:
                objArr[2] = "visitConstructorDelegationCall";
                break;
            case 48:
                objArr[2] = "visitParameter";
                break;
            case 49:
            case 50:
                objArr[2] = "withinUninitializedClass";
                break;
            case 51:
            case 52:
                objArr[2] = "recordSamConstructorIfNeeded";
                break;
            case 53:
                objArr[2] = "visitBinaryExpression";
                break;
            case 54:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case 55:
                objArr[2] = "visitWhenExpression";
                break;
            case 56:
                objArr[2] = "isWhenWithEnums";
                break;
            case 57:
                objArr[2] = "getCurrentTopLevelClassOrPackagePartInternalName";
                break;
            case 60:
                objArr[2] = "peekFromStack";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12 && i != 29 && i != 14 && i != 15 && i != 58 && i != 59) {
            switch (i) {
                case 33:
                case 34:
                case 35:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public CodegenAnnotatingVisitor(GenerationState generationState) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        this.anonymousSubclassesCount = new HashMap();
        this.classStack = new Stack<>();
        this.nameStack = new Stack<>();
        this.functionsStack = new Stack<>();
        this.uninitializedClasses = new HashSet();
        this.bindingTrace = generationState.getBindingTrace();
        this.bindingContext = generationState.getBindingContext();
        this.filter = generationState.getGenerateDeclaredClassFilter();
        this.runtimeTypes = generationState.getJvmRuntimeTypes();
        this.switchCodegenProvider = new SwitchCodegenProvider(generationState);
        this.languageVersionSettings = generationState.getLanguageVersionSettings();
        this.classBuilderMode = generationState.getClassBuilderMode();
        this.delegatedPropertiesCodegenHelper = new DelegatedPropertiesCodegenHelper(generationState);
        this.jvmDefaultMode = generationState.getJvmDefaultMode();
        this.samTypeApproximator = new SamTypeApproximator(generationState.getModule().getBuiltIns(), generationState.getLanguageVersionSettings());
    }

    private ClassDescriptor recordClassForCallable(KtElement ktElement, CallableDescriptor callableDescriptor, Collection<KotlinType> collection, String str) {
        if (ktElement == null) {
            $$$reportNull$$$0(1);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (collection == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return recordClassForCallable(ktElement, callableDescriptor, collection, str, null);
    }

    private ClassDescriptor recordClassForFunction(KtElement ktElement, FunctionDescriptor functionDescriptor, String str, DeclarationDescriptor declarationDescriptor) {
        if (ktElement == null) {
            $$$reportNull$$$0(5);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return recordClassForCallable(ktElement, functionDescriptor, this.runtimeTypes.getSupertypesForClosure(functionDescriptor), str, declarationDescriptor);
    }

    private ClassDescriptor recordClassForCallable(KtElement ktElement, CallableDescriptor callableDescriptor, Collection<KotlinType> collection, String str, DeclarationDescriptor declarationDescriptor) {
        if (ktElement == null) {
            $$$reportNull$$$0(8);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (collection == null) {
            $$$reportNull$$$0(10);
        }
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        String strSubstring = str.substring(str.lastIndexOf(47) + 1);
        if (declarationDescriptor == null) {
            declarationDescriptor = correctContainerForLambda(callableDescriptor);
        }
        SyntheticClassDescriptorForLambda syntheticClassDescriptorForLambda = new SyntheticClassDescriptorForLambda(declarationDescriptor, Name.special("<closure-" + strSubstring + ">"), collection, ktElement);
        this.bindingTrace.record(CodegenBinding.CLASS_FOR_CALLABLE, callableDescriptor, syntheticClassDescriptorForLambda);
        return syntheticClassDescriptorForLambda;
    }

    private DeclarationDescriptor correctContainerForLambda(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        DeclarationDescriptor containingDeclaration = callableDescriptor.getContainingModule();
        while (containingDeclaration instanceof ConstructorDescriptor) {
            ClassDescriptor constructedClass = ((ConstructorDescriptor) containingDeclaration).getConstructedClass();
            if (!DescriptorUtils.isAnonymousObject(constructedClass) || !this.uninitializedClasses.contains(constructedClass)) {
                break;
            }
            containingDeclaration = constructedClass.getContainingModule();
        }
        if (containingDeclaration == null) {
            $$$reportNull$$$0(14);
        }
        return containingDeclaration;
    }

    private String inventAnonymousClassName() {
        String str = (String) peekFromStack(this.nameStack);
        Integer num = this.anonymousSubclassesCount.get(str);
        if (num == null) {
            num = 0;
        }
        this.anonymousSubclassesCount.put(str, Integer.valueOf(num.intValue() + 1));
        String str2 = str + "$" + (num.intValue() + 1);
        if (str2 == null) {
            $$$reportNull$$$0(15);
        }
        return str2;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement] */
    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitKtElement(KtElement ktElement) {
        ?? stub;
        KotlinFileStub stub2;
        if (ktElement == null) {
            $$$reportNull$$$0(16);
        }
        super.visitKtElement(ktElement);
        if (!this.classBuilderMode.generateBodies) {
            if ((ktElement instanceof KtFile) && (stub2 = ((KtFile) ktElement).get$ktFileStub()) != null) {
                Iterator<StubElement> it = stub2.getChildrenStubs().iterator();
                while (it.hasNext()) {
                    it.next().getPsi().accept(this);
                }
                return;
            } else if (ktElement instanceof KtElementImplStub) {
                KtElementImplStub ktElementImplStub = (KtElementImplStub) ktElement;
                if (ktElementImplStub.getStub() != 0 && (stub = ktElementImplStub.getStub()) != 0) {
                    Iterator<StubElement> it2 = stub.getChildrenStubs().iterator();
                    while (it2.hasNext()) {
                        it2.next().getPsi().accept(this);
                    }
                    return;
                }
            }
        }
        ktElement.acceptChildren(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitScript(KtScript ktScript) {
        if (ktScript == null) {
            $$$reportNull$$$0(17);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) this.bindingContext.get(BindingContext.SCRIPT, ktScript);
        if (classDescriptor == null) {
            return;
        }
        String strInternalNameByFqNameWithoutInnerClasses = AsmUtil.internalNameByFqNameWithoutInnerClasses(ktScript.mo7391getFqName());
        recordClosure(classDescriptor, strInternalNameByFqNameWithoutInnerClasses);
        this.classStack.push(classDescriptor);
        this.nameStack.push(strInternalNameByFqNameWithoutInnerClasses);
        ktScript.acceptChildren(this);
        this.nameStack.pop();
        this.classStack.pop();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitKtFile(KtFile ktFile) {
        String strInternalNameByFqNameWithoutInnerClasses;
        if (ktFile == null) {
            $$$reportNull$$$0(18);
        }
        if (ktFile instanceof KtCodeFragment) {
            CodeFragmentCodegenInfo codeFragmentInfo = CodeFragmentCodegen.getCodeFragmentInfo((KtCodeFragment) ktFile);
            strInternalNameByFqNameWithoutInnerClasses = codeFragmentInfo.getClassDescriptor().getShortName().asString() + "$" + codeFragmentInfo.getMethodDescriptor().getShortName().asString();
        } else {
            strInternalNameByFqNameWithoutInnerClasses = AsmUtil.internalNameByFqNameWithoutInnerClasses(ktFile.getPackageFqName());
        }
        this.nameStack.push(strInternalNameByFqNameWithoutInnerClasses);
        visitKtElement(ktFile);
        this.nameStack.pop();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitEnumEntry(KtEnumEntry ktEnumEntry) {
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(19);
        }
        if (ktEnumEntry.getDeclarations().isEmpty()) {
            Iterator<KtSuperTypeListEntry> it = ktEnumEntry.getSuperTypeListEntries().iterator();
            while (it.hasNext()) {
                it.next().accept(this);
            }
        } else {
            ClassDescriptor classDescriptor = (ClassDescriptor) this.bindingContext.get(BindingContext.CLASS, ktEnumEntry);
            if (classDescriptor == null) {
                return;
            }
            this.bindingTrace.record(CodegenBinding.ENUM_ENTRY_CLASS_NEED_SUBCLASS, classDescriptor);
            super.visitEnumEntry(ktEnumEntry);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitObjectDeclaration(KtObjectDeclaration ktObjectDeclaration) {
        ClassDescriptor classDescriptor;
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(20);
        }
        if (this.filter.shouldAnnotateClass(ktObjectDeclaration) && (classDescriptor = (ClassDescriptor) this.bindingContext.get(BindingContext.CLASS, ktObjectDeclaration)) != null) {
            String name2 = getName(classDescriptor);
            recordClosure(classDescriptor, name2);
            this.classStack.push(classDescriptor);
            this.nameStack.push(name2);
            super.visitObjectDeclaration(ktObjectDeclaration);
            this.nameStack.pop();
            this.classStack.pop();
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitClass(KtClass ktClass) {
        ClassDescriptor classDescriptor;
        if (ktClass == null) {
            $$$reportNull$$$0(21);
        }
        if (this.filter.shouldAnnotateClass(ktClass) && (classDescriptor = (ClassDescriptor) this.bindingContext.get(BindingContext.CLASS, ktClass)) != null) {
            String name2 = getName(classDescriptor);
            recordClosure(classDescriptor, name2);
            this.classStack.push(classDescriptor);
            this.nameStack.push(name2);
            super.visitClass(ktClass);
            this.nameStack.pop();
            this.classStack.pop();
        }
    }

    private String getName(ClassDescriptor classDescriptor) {
        String str = (String) peekFromStack(this.nameStack);
        Name nameSafeIdentifier = SpecialNames.safeIdentifier(classDescriptor.getShortName());
        if (DescriptorUtils.isTopLevelDeclaration(classDescriptor)) {
            return str.isEmpty() ? nameSafeIdentifier.asString() : str + '/' + nameSafeIdentifier;
        }
        return str + "$" + nameSafeIdentifier.asString();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(22);
        }
        KtObjectDeclaration objectDeclaration = ktObjectLiteralExpression.getObjectDeclaration();
        ClassDescriptor classDescriptor = (ClassDescriptor) this.bindingContext.get(BindingContext.CLASS, objectDeclaration);
        if (classDescriptor == null) {
            super.visitObjectLiteralExpression(ktObjectLiteralExpression);
            return;
        }
        recordClosure(classDescriptor, inventAnonymousClassName());
        KtSuperTypeList superTypeList = objectDeclaration.getSuperTypeList();
        if (superTypeList != null) {
            superTypeList.accept(this);
        }
        this.classStack.push(classDescriptor);
        this.nameStack.push(CodegenBinding.getAsmType(this.bindingContext, classDescriptor).getInternalName());
        KtClassBody body = objectDeclaration.getBody();
        if (body != null) {
            super.visitClassBody(body);
        }
        this.nameStack.pop();
        this.classStack.pop();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitLambdaExpression(KtLambdaExpression ktLambdaExpression) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(23);
        }
        KtFunctionLiteral functionLiteral = ktLambdaExpression.getFunctionLiteral();
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, functionLiteral);
        if (functionDescriptor == null) {
            return;
        }
        String strInventAnonymousClassName = inventAnonymousClassName();
        ClassDescriptor classDescriptorRecordClassForCallable = recordClassForCallable(functionLiteral, functionDescriptor, this.runtimeTypes.getSupertypesForClosure(functionDescriptor), strInventAnonymousClassName);
        MutableClosure mutableClosureRecordClosure = recordClosure(classDescriptorRecordClassForCallable, strInventAnonymousClassName);
        this.classStack.push(classDescriptorRecordClassForCallable);
        this.nameStack.push(strInventAnonymousClassName);
        if (CoroutineUtilKt.isSuspendLambda(functionDescriptor)) {
            createAndRecordSuspendFunctionView(mutableClosureRecordClosure, (SimpleFunctionDescriptor) functionDescriptor, true);
        }
        this.functionsStack.push(functionDescriptor);
        super.visitLambdaExpression(ktLambdaExpression);
        this.functionsStack.pop();
        this.nameStack.pop();
        this.classStack.pop();
    }

    private boolean isAdaptedCallableReference(KtCallableReferenceExpression ktCallableReferenceExpression, ResolvedCall<?> resolvedCall, boolean z) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(24);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(25);
        }
        if (z) {
            return true;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (!(resultingDescriptor instanceof FunctionDescriptor)) {
            return false;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) resultingDescriptor;
        if (!resolvedCall.getValueArguments().isEmpty()) {
            return true;
        }
        KotlinType type = this.bindingContext.getType(ktCallableReferenceExpression);
        if (type == null) {
            return false;
        }
        KotlinType nullableAnyType = ((TypeProjection) CollectionsKt.last((List) type.getArguments())).getNullableAnyType();
        KotlinType returnType = functionDescriptor.getReturnType();
        return (returnType == null || !KotlinBuiltIns.isUnit(nullableAnyType) || KotlinBuiltIns.isUnit(returnType)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression) {
        KotlinType type;
        CallableDescriptor callableDescriptor;
        Collection<KotlinType> collectionSingleton;
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(26);
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktCallableReferenceExpression.getCallableReference(), this.bindingContext);
        if (resolvedCall == null) {
            return;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
        if (dispatchReceiver != null && !(dispatchReceiver instanceof TransientReceiver)) {
            type = dispatchReceiver.getType();
        } else {
            type = (extensionReceiver == null || (extensionReceiver instanceof TransientReceiver)) ? null : extensionReceiver.getType();
        }
        boolean z = true;
        if (resultingDescriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) resultingDescriptor;
            callableDescriptor = (CallableDescriptor) this.bindingContext.get(BindingContext.FUNCTION, ktCallableReferenceExpression);
            if (callableDescriptor == null) {
                return;
            }
            KotlinType type2 = this.bindingContext.getType(ktCallableReferenceExpression);
            boolean z2 = (functionDescriptor.isSuspend() || type2 == null || !FunctionTypesKt.isKSuspendFunctionType(type2)) ? false : true;
            collectionSingleton = this.runtimeTypes.getSupertypesForFunctionReference(functionDescriptor, (AnonymousFunctionDescriptor) callableDescriptor, type != null, isAdaptedCallableReference(ktCallableReferenceExpression, resolvedCall, z2), z2);
        } else if (!(resultingDescriptor instanceof PropertyDescriptor) || (callableDescriptor = (CallableDescriptor) this.bindingContext.get(BindingContext.VARIABLE, ktCallableReferenceExpression)) == null) {
            return;
        } else {
            collectionSingleton = Collections.singleton(this.runtimeTypes.getSupertypeForPropertyReference((PropertyDescriptor) resultingDescriptor, ReflectionTypes.INSTANCE.isNumberedKMutablePropertyType(callableDescriptor.getReturnType()), type != null));
        }
        String strInventAnonymousClassName = inventAnonymousClassName();
        ClassDescriptor classDescriptorRecordClassForCallable = recordClassForCallable(ktCallableReferenceExpression, callableDescriptor, collectionSingleton, strInventAnonymousClassName);
        MutableClosure mutableClosureRecordClosure = recordClosure(classDescriptorRecordClassForCallable, strInventAnonymousClassName);
        if (callableDescriptor instanceof SimpleFunctionDescriptor) {
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) callableDescriptor;
            if (simpleFunctionDescriptor.isSuspend()) {
                createAndRecordSuspendFunctionView(mutableClosureRecordClosure, simpleFunctionDescriptor, false);
                Iterator<FunctionDescriptor> it = this.functionsStack.iterator();
                do {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                } while (it.next() != resultingDescriptor);
                this.bindingTrace.record(CodegenBinding.RECURSIVE_SUSPEND_CALLABLE_REFERENCE, classDescriptorRecordClassForCallable, Boolean.valueOf(z));
            }
        }
        if (type != null) {
            mutableClosureRecordClosure.setCustomCapturedReceiverType(type);
        }
        super.visitCallableReferenceExpression(ktCallableReferenceExpression);
    }

    private SimpleFunctionDescriptor createAndRecordSuspendFunctionView(MutableClosure mutableClosure, SimpleFunctionDescriptor simpleFunctionDescriptor, boolean z) {
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(simpleFunctionDescriptor, this.bindingContext);
        this.bindingTrace.record(CodegenBinding.SUSPEND_FUNCTION_TO_JVM_VIEW, simpleFunctionDescriptor, simpleFunctionDescriptor2);
        mutableClosure.setSuspend(true);
        if (z) {
            mutableClosure.setSuspendLambda();
        }
        return simpleFunctionDescriptor2;
    }

    private MutableClosure recordClosure(ClassDescriptor classDescriptor, String str) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        MutableClosure mutableClosureRecordClosure = CodegenBinding.recordClosure(this.bindingTrace, classDescriptor, getProperEnclosingClass(), Type.getObjectType(JvmCodegenUtil.sanitizeNameIfNeeded(str, this.languageVersionSettings)));
        if (mutableClosureRecordClosure == null) {
            $$$reportNull$$$0(29);
        }
        return mutableClosureRecordClosure;
    }

    private ClassDescriptor getProperEnclosingClass() {
        for (int size = this.classStack.size() - 1; size >= 0; size--) {
            ClassDescriptor classDescriptor = this.classStack.get(size);
            if (!this.uninitializedClasses.contains(classDescriptor)) {
                return classDescriptor;
            }
        }
        return null;
    }

    private void recordLocalVariablePropertyMetadata(LocalVariableDescriptor localVariableDescriptor) {
        if (JvmCodegenUtil.getPropertyDelegateType(localVariableDescriptor, this.bindingContext) == null) {
            return;
        }
        this.bindingTrace.record(CodegenBinding.LOCAL_VARIABLE_PROPERTY_METADATA, localVariableDescriptor, new LocalVariableDescriptor(localVariableDescriptor.getContainingModule(), Annotations.INSTANCE.getEMPTY(), Name.identifier(localVariableDescriptor.getShortName().asString() + "$metadata"), ReflectionTypes.INSTANCE.createKPropertyStarType(DescriptorUtilsKt.getModule(localVariableDescriptor)), SourceElement.NO_SOURCE));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitProperty(KtProperty ktProperty) {
        if (ktProperty == null) {
            $$$reportNull$$$0(30);
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktProperty);
        if (declarationDescriptor == null) {
            return;
        }
        checkRuntimeAsserionsOnDeclarationBody(ktProperty, declarationDescriptor);
        if (declarationDescriptor instanceof LocalVariableDescriptor) {
            recordLocalVariablePropertyMetadata((LocalVariableDescriptor) declarationDescriptor);
        }
        String nameForClassOrPackageMember = getNameForClassOrPackageMember(declarationDescriptor);
        if (nameForClassOrPackageMember != null) {
            this.nameStack.push(nameForClassOrPackageMember);
        } else {
            this.nameStack.push(((String) peekFromStack(this.nameStack)) + '$' + SpecialNames.safeIdentifier(ktProperty.getNameAsSafeName()).asString());
        }
        KtElement delegate = ktProperty.getDelegate();
        if (delegate != null && (declarationDescriptor instanceof VariableDescriptorWithAccessors)) {
            VariableDescriptorWithAccessors variableDescriptorWithAccessors = (VariableDescriptorWithAccessors) declarationDescriptor;
            String strInventAnonymousClassName = inventAnonymousClassName();
            recordClosure(recordClassForCallable(delegate, variableDescriptorWithAccessors, Collections.singleton(this.runtimeTypes.getSupertypeForPropertyReference(variableDescriptorWithAccessors, variableDescriptorWithAccessors.isVar(), false)), strInventAnonymousClassName), strInventAnonymousClassName);
            if (this.delegatedPropertiesCodegenHelper.isDelegatedPropertyMetadataRequired(variableDescriptorWithAccessors)) {
                Type metadataOwner = getMetadataOwner(ktProperty);
                List arrayList = (List) this.bindingTrace.get(CodegenBinding.DELEGATED_PROPERTIES_WITH_METADATA, metadataOwner);
                if (arrayList == null) {
                    arrayList = new ArrayList(1);
                    this.bindingTrace.record(CodegenBinding.DELEGATED_PROPERTIES_WITH_METADATA, metadataOwner, arrayList);
                }
                arrayList.add(variableDescriptorWithAccessors);
                this.bindingTrace.record(CodegenBinding.DELEGATED_PROPERTY_METADATA_OWNER, variableDescriptorWithAccessors, metadataOwner);
            } else {
                this.bindingTrace.record(CodegenBinding.DELEGATED_PROPERTY_WITH_OPTIMIZED_METADATA, variableDescriptorWithAccessors);
            }
        }
        super.visitProperty(ktProperty);
        this.nameStack.pop();
    }

    private void checkRuntimeAsserionsOnDeclarationBody(KtDeclaration ktDeclaration, DeclarationDescriptor declarationDescriptor) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(31);
        }
        if (this.classBuilderMode.generateBodies) {
            RuntimeAssertionsOnDeclarationBodyChecker.check(ktDeclaration, declarationDescriptor, this.bindingTrace, this.languageVersionSettings);
        }
    }

    private Type getMetadataOwner(KtProperty ktProperty) {
        if (ktProperty == null) {
            $$$reportNull$$$0(32);
        }
        for (int size = this.classStack.size() - 1; size >= 0; size--) {
            ClassDescriptor classDescriptor = this.classStack.get(size);
            if (!(classDescriptor instanceof SyntheticClassDescriptorForLambda)) {
                ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor);
                if (classId == null) {
                    Type asmType = CodegenBinding.getAsmType(this.bindingContext, classDescriptor);
                    if (asmType == null) {
                        $$$reportNull$$$0(33);
                    }
                    return asmType;
                }
                if (DescriptorUtils.isInterface(classDescriptor) && !this.jvmDefaultMode.getForAllMethodsWithBody()) {
                    classId = classId.createNestedClassId(Name.identifier(JvmAbi.DEFAULT_IMPLS_CLASS_NAME));
                }
                Type typeAsmTypeByClassId = AsmUtil.asmTypeByClassId(classId);
                if (typeAsmTypeByClassId == null) {
                    $$$reportNull$$$0(34);
                }
                return typeAsmTypeByClassId;
            }
        }
        Type objectType = Type.getObjectType(JvmFileClassUtil.getFileClassInternalName(ktProperty.getContainingKtFile()));
        if (objectType == null) {
            $$$reportNull$$$0(35);
        }
        return objectType;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitPropertyAccessor(KtPropertyAccessor ktPropertyAccessor) {
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(36);
        }
        PropertyAccessorDescriptor propertyAccessorDescriptor = (PropertyAccessorDescriptor) this.bindingContext.get(BindingContext.PROPERTY_ACCESSOR, ktPropertyAccessor);
        if (propertyAccessorDescriptor != null) {
            checkRuntimeAsserionsOnDeclarationBody(ktPropertyAccessor, propertyAccessorDescriptor);
        }
        super.visitPropertyAccessor(ktPropertyAccessor);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitNamedFunction(KtNamedFunction ktNamedFunction) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(37);
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktNamedFunction);
        if (functionDescriptor == null) {
            return;
        }
        checkRuntimeAsserionsOnDeclarationBody(ktNamedFunction, functionDescriptor);
        String nameForClassOrPackageMember = getNameForClassOrPackageMember(functionDescriptor);
        boolean z = functionDescriptor instanceof SimpleFunctionDescriptor;
        if (z && functionDescriptor.isSuspend() && !functionDescriptor.getVisibility().equals(DescriptorVisibilities.LOCAL)) {
            if (nameForClassOrPackageMember != null) {
                this.nameStack.push(nameForClassOrPackageMember);
            }
            String strInventAnonymousClassName = inventAnonymousClassName();
            for (Pair pair : CollectionsKt.zip(functionDescriptor.getValueParameters(), createAndRecordSuspendFunctionView(recordClosure(recordClassForFunction(ktNamedFunction, functionDescriptor, strInventAnonymousClassName, functionDescriptor), strInventAnonymousClassName), (SimpleFunctionDescriptor) functionDescriptor, false).getValueParameters())) {
                this.bindingTrace.record(CodegenBinding.PARAMETER_SYNONYM, pair.getFirst(), pair.getSecond());
            }
            this.functionsStack.push(functionDescriptor);
            super.visitNamedFunction(ktNamedFunction);
            this.functionsStack.pop();
            if (nameForClassOrPackageMember != null) {
                this.nameStack.pop();
                return;
            }
            return;
        }
        if (nameForClassOrPackageMember != null) {
            this.nameStack.push(nameForClassOrPackageMember);
            this.functionsStack.push(functionDescriptor);
            super.visitNamedFunction(ktNamedFunction);
            this.functionsStack.pop();
            this.nameStack.pop();
            return;
        }
        String strInventAnonymousClassName2 = inventAnonymousClassName();
        ClassDescriptor classDescriptorRecordClassForFunction = recordClassForFunction(ktNamedFunction, functionDescriptor, strInventAnonymousClassName2, null);
        MutableClosure mutableClosureRecordClosure = recordClosure(classDescriptorRecordClassForFunction, strInventAnonymousClassName2);
        this.classStack.push(classDescriptorRecordClassForFunction);
        this.nameStack.push(strInventAnonymousClassName2);
        if (z && functionDescriptor.isSuspend()) {
            createAndRecordSuspendFunctionView(mutableClosureRecordClosure, (SimpleFunctionDescriptor) functionDescriptor, true);
        }
        this.functionsStack.push(functionDescriptor);
        super.visitNamedFunction(ktNamedFunction);
        this.functionsStack.pop();
        this.nameStack.pop();
        this.classStack.pop();
    }

    private String getNameForClassOrPackageMember(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(38);
        }
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingModule();
        String str = (String) peekFromStack(this.nameStack);
        String strAsString = SpecialNames.safeIdentifier(declarationDescriptor.getShortName()).asString();
        if (containingDeclaration instanceof ClassDescriptor) {
            return str + '$' + strAsString;
        }
        if (!(containingDeclaration instanceof PackageFragmentDescriptor)) {
            return null;
        }
        return JvmFileClassUtil.getFileClassInternalName(DescriptorToSourceUtils.getContainingFile(declarationDescriptor)) + '$' + strAsString;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitCallExpression(KtCallExpression ktCallExpression) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(39);
        }
        super.visitCallExpression(ktCallExpression);
        checkSamCall(ktCallExpression);
        checkCrossinlineCall(ktCallExpression);
    }

    private void checkCrossinlineCall(KtCallExpression ktCallExpression) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(40);
        }
        ResolvedCall resolvedCall = (ResolvedCall) this.bindingContext.get(BindingContext.RESOLVED_CALL, (Call) this.bindingContext.get(BindingContext.CALL, ktCallExpression.getCalleeExpression()));
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            VariableDescriptor variableDescriptor = (VariableDescriptor) ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall().getResultingDescriptor();
            if (variableDescriptor instanceof ValueParameterDescriptor) {
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) variableDescriptor;
                if (valueParameterDescriptor.getIsCrossinline()) {
                    DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingModule();
                    if (this.functionsStack.peek().isSuspend()) {
                        this.bindingTrace.record(CodegenBinding.CALL_SITE_IS_SUSPEND_FOR_CROSSINLINE_LAMBDA, valueParameterDescriptor, true);
                    }
                    for (int size = this.functionsStack.size() - 1; size >= 0; size--) {
                        Boolean bool = (Boolean) this.bindingTrace.getContext().get(CodegenBinding.CAPTURES_CROSSINLINE_LAMBDA, this.functionsStack.get(size));
                        if (bool != null && bool.booleanValue()) {
                            return;
                        }
                        this.bindingTrace.record(CodegenBinding.CAPTURES_CROSSINLINE_LAMBDA, this.functionsStack.get(size), true);
                        if (this.functionsStack.get(size) == containingDeclaration) {
                            return;
                        }
                    }
                }
            }
        }
    }

    private void checkSamCall(KtCallElement ktCallElement) {
        if (ktCallElement == null) {
            $$$reportNull$$$0(41);
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktCallElement, this.bindingContext);
        if (resolvedCall == null) {
            return;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor instanceof FunctionDescriptor) {
            recordSamValuesForNewInference(resolvedCall);
            recordSamConstructorIfNeeded(ktCallElement, resolvedCall);
            recordSamValuesForOldInference(resolvedCall, resultingDescriptor);
        }
    }

    private void recordSamValuesForOldInference(ResolvedCall<?> resolvedCall, CallableDescriptor callableDescriptor) {
        FunctionDescriptor originalIfSamAdapter = SamCodegenUtil.getOriginalIfSamAdapter((FunctionDescriptor) callableDescriptor);
        if (originalIfSamAdapter == null) {
            return;
        }
        SmartList smartList = new SmartList();
        for (ValueParameterDescriptor valueParameterDescriptor : originalIfSamAdapter.getValueParameters()) {
            if (!KotlinTypeChecker.DEFAULT.equalTypes(callableDescriptor.getValueParameters().get(valueParameterDescriptor.getIndex()).getType(), valueParameterDescriptor.getType())) {
                smartList.add(valueParameterDescriptor);
            }
        }
        writeSamValueForValueParameters(smartList, resolvedCall.getValueArgumentsByIndex());
    }

    private void recordSamValuesForNewInference(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(42);
        }
        NewAbstractResolvedCall<?> newResolvedCallForCallWithPossibleSamConversions = getNewResolvedCallForCallWithPossibleSamConversions(resolvedCall);
        if (newResolvedCallForCallWithPossibleSamConversions instanceof NewResolvedCallImpl) {
            Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = newResolvedCallForCallWithPossibleSamConversions.getValueArguments();
            for (ValueParameterDescriptor valueParameterDescriptor : valueArguments.keySet()) {
                ResolvedValueArgument resolvedValueArgument = valueArguments.get(valueParameterDescriptor);
                if (resolvedValueArgument instanceof ExpressionValueArgument) {
                    ValueArgument valueArgument = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument();
                    if (valueArgument != null && ((NewResolvedCallImpl) newResolvedCallForCallWithPossibleSamConversions).getExpectedTypeForSamConvertedArgument(valueArgument) != null) {
                        recordSamTypeOnArgumentExpression(valueParameterDescriptor, valueArgument);
                    }
                } else if (resolvedValueArgument instanceof VarargValueArgument) {
                    for (ValueArgument valueArgument2 : ((VarargValueArgument) resolvedValueArgument).getArguments()) {
                        if (valueArgument2 != null && ((NewResolvedCallImpl) newResolvedCallForCallWithPossibleSamConversions).getExpectedTypeForSamConvertedArgument(valueArgument2) != null) {
                            recordSamTypeOnArgumentExpression(valueParameterDescriptor, valueArgument2);
                        }
                    }
                }
            }
        }
    }

    private static NewAbstractResolvedCall<?> getNewResolvedCallForCallWithPossibleSamConversions(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(43);
        }
        if (resolvedCall instanceof NewVariableAsFunctionResolvedCallImpl) {
            return ((NewVariableAsFunctionResolvedCallImpl) resolvedCall).getFunctionCall();
        }
        if (resolvedCall instanceof NewResolvedCallImpl) {
            return (NewResolvedCallImpl) resolvedCall;
        }
        return null;
    }

    private SamType createSamType(KotlinType kotlinType) {
        if (JavaSingleAbstractMethodUtils.isSamType(kotlinType)) {
            return new SamType(kotlinType);
        }
        return null;
    }

    private SamType createSamTypeByValueParameter(ValueParameterDescriptor valueParameterDescriptor) {
        KotlinType samTypeForValueParameter = this.samTypeApproximator.getSamTypeForValueParameter(valueParameterDescriptor, false);
        if (samTypeForValueParameter != null && JavaSingleAbstractMethodUtils.isSamType(samTypeForValueParameter)) {
            return new SamType(samTypeForValueParameter);
        }
        return null;
    }

    private void writeSamValueForValueParameters(Collection<ValueParameterDescriptor> collection, List<ResolvedValueArgument> list) {
        if (collection == null) {
            $$$reportNull$$$0(44);
        }
        if (list == null) {
            return;
        }
        for (ValueParameterDescriptor valueParameterDescriptor : collection) {
            SamType samTypeCreateSamTypeByValueParameter = createSamTypeByValueParameter(valueParameterDescriptor);
            if (samTypeCreateSamTypeByValueParameter != null) {
                recordSamTypeOnArgumentExpression(samTypeCreateSamTypeByValueParameter, ((ExpressionValueArgument) list.get(valueParameterDescriptor.getIndex())).getValueArgument());
            }
        }
    }

    private void recordSamTypeOnArgumentExpression(ValueParameterDescriptor valueParameterDescriptor, ValueArgument valueArgument) {
        SamType samTypeCreateSamTypeByValueParameter = createSamTypeByValueParameter(valueParameterDescriptor);
        if (samTypeCreateSamTypeByValueParameter == null) {
            return;
        }
        recordSamTypeOnArgumentExpression(samTypeCreateSamTypeByValueParameter, valueArgument);
    }

    private void recordSamTypeOnArgumentExpression(SamType samType, ValueArgument valueArgument) {
        this.bindingTrace.record(CodegenBinding.SAM_VALUE, valueArgument.getArgumentExpression(), samType);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitSuperTypeCallEntry(final KtSuperTypeCallEntry ktSuperTypeCallEntry) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(45);
        }
        if (!isSuperTypeCallForAnonymousObject(ktSuperTypeCallEntry)) {
            withinUninitializedClass(ktSuperTypeCallEntry, new Runnable() { // from class: org.jetbrains.kotlin.codegen.binding.CodegenAnnotatingVisitor$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6426xaebd5355(ktSuperTypeCallEntry);
                }
            });
        } else {
            super.visitSuperTypeCallEntry(ktSuperTypeCallEntry);
        }
        checkSamCall(ktSuperTypeCallEntry);
    }

    /* JADX INFO: renamed from: lambda$visitSuperTypeCallEntry$0$org-jetbrains-kotlin-codegen-binding-CodegenAnnotatingVisitor, reason: not valid java name */
    /* synthetic */ void m6426xaebd5355(KtSuperTypeCallEntry ktSuperTypeCallEntry) {
        super.visitSuperTypeCallEntry(ktSuperTypeCallEntry);
    }

    private static boolean isSuperTypeCallForAnonymousObject(KtSuperTypeCallEntry ktSuperTypeCallEntry) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(46);
        }
        PsiElement parent = ktSuperTypeCallEntry.getLightParent();
        if (!(parent instanceof KtSuperTypeList)) {
            return false;
        }
        PsiElement myParent = parent.getLightParent();
        return (myParent instanceof KtObjectDeclaration) && (myParent.getLightParent() instanceof KtObjectLiteralExpression);
    }

    /* JADX INFO: renamed from: lambda$visitConstructorDelegationCall$1$org-jetbrains-kotlin-codegen-binding-CodegenAnnotatingVisitor, reason: not valid java name */
    /* synthetic */ void m6424xc84199b(KtConstructorDelegationCall ktConstructorDelegationCall) {
        super.visitConstructorDelegationCall(ktConstructorDelegationCall);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitConstructorDelegationCall(final KtConstructorDelegationCall ktConstructorDelegationCall) {
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(47);
        }
        withinUninitializedClass(ktConstructorDelegationCall, new Runnable() { // from class: org.jetbrains.kotlin.codegen.binding.CodegenAnnotatingVisitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6424xc84199b(ktConstructorDelegationCall);
            }
        });
        checkSamCall(ktConstructorDelegationCall);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitParameter(KtParameter ktParameter) {
        if (ktParameter == null) {
            $$$reportNull$$$0(48);
        }
        PsiElement parent = ktParameter.getLightParent();
        if (parent != null && (parent.getLightParent() instanceof KtConstructor)) {
            final KtExpression defaultValue = ktParameter.getDefaultValue();
            if (defaultValue != null) {
                withinUninitializedClass(defaultValue, new Runnable() { // from class: org.jetbrains.kotlin.codegen.binding.CodegenAnnotatingVisitor$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m6425xcd42bf45(defaultValue);
                    }
                });
                return;
            }
            return;
        }
        super.visitParameter(ktParameter);
    }

    /* JADX INFO: renamed from: lambda$visitParameter$2$org-jetbrains-kotlin-codegen-binding-CodegenAnnotatingVisitor, reason: not valid java name */
    /* synthetic */ void m6425xcd42bf45(KtExpression ktExpression) {
        ktExpression.accept(this);
    }

    private void withinUninitializedClass(KtElement ktElement, Runnable runnable) {
        if (ktElement == null) {
            $$$reportNull$$$0(49);
        }
        if (runnable == null) {
            $$$reportNull$$$0(50);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) peekFromStack(this.classStack);
        this.uninitializedClasses.add(classDescriptor);
        runnable.run();
        this.uninitializedClasses.remove(classDescriptor);
    }

    private void recordSamConstructorIfNeeded(KtCallElement ktCallElement, ResolvedCall<?> resolvedCall) {
        List<ResolvedValueArgument> valueArgumentsByIndex;
        ValueArgument valueArgument;
        if (ktCallElement == null) {
            $$$reportNull$$$0(51);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(52);
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if ((resultingDescriptor.getOriginal() instanceof SamConstructorDescriptor) && (valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex()) != null && valueArgumentsByIndex.size() == 1) {
            ResolvedValueArgument resolvedValueArgument = valueArgumentsByIndex.get(0);
            if ((resolvedValueArgument instanceof ExpressionValueArgument) && (valueArgument = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument()) != null) {
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                this.bindingTrace.record(CodegenBinding.SAM_CONSTRUCTOR_TO_ARGUMENT, ktCallElement, argumentExpression);
                this.bindingTrace.record(CodegenBinding.SAM_VALUE, argumentExpression, createSamType(resultingDescriptor.getReturnType()));
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitBinaryExpression(KtBinaryExpression ktBinaryExpression) {
        SamType samTypeCreateSamTypeByValueParameter;
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(53);
        }
        super.visitBinaryExpression(ktBinaryExpression);
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktBinaryExpression.getOperationReference());
        if (declarationDescriptor instanceof FunctionDescriptor) {
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktBinaryExpression, this.bindingContext);
            if (resolvedCall != null) {
                recordSamValuesForNewInference(resolvedCall);
            }
            FunctionDescriptor originalIfSamAdapter = SamCodegenUtil.getOriginalIfSamAdapter((FunctionDescriptor) declarationDescriptor);
            if (originalIfSamAdapter == null || (samTypeCreateSamTypeByValueParameter = createSamTypeByValueParameter(originalIfSamAdapter.getValueParameters().get(0))) == null) {
                return;
            }
            IElementType operationToken = ktBinaryExpression.getOperationToken();
            if (BINARY_OPERATIONS.contains(operationToken)) {
                this.bindingTrace.record(CodegenBinding.SAM_VALUE, ktBinaryExpression.getRight(), samTypeCreateSamTypeByValueParameter);
            } else if (operationToken == KtTokens.IN_KEYWORD || operationToken == KtTokens.NOT_IN) {
                this.bindingTrace.record(CodegenBinding.SAM_VALUE, ktBinaryExpression.getLeft(), samTypeCreateSamTypeByValueParameter);
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(54);
        }
        super.visitArrayAccessExpression(ktArrayAccessExpression);
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktArrayAccessExpression);
        if (declarationDescriptor instanceof FunctionDescriptor) {
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktArrayAccessExpression, this.bindingContext);
            if (resolvedCall != null) {
                recordSamValuesForNewInference(resolvedCall);
            }
            boolean zEquals = declarationDescriptor.getShortName().asString().equals(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
            FunctionDescriptor originalIfSamAdapter = SamCodegenUtil.getOriginalIfSamAdapter((FunctionDescriptor) declarationDescriptor);
            if (originalIfSamAdapter == null) {
                return;
            }
            List<KtExpression> indexExpressions = ktArrayAccessExpression.getIndexExpressions();
            List<ValueParameterDescriptor> valueParameters = originalIfSamAdapter.getValueParameters();
            for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
                SamType samTypeCreateSamTypeByValueParameter = createSamTypeByValueParameter(valueParameterDescriptor);
                if (samTypeCreateSamTypeByValueParameter != null) {
                    if (zEquals && valueParameterDescriptor.getIndex() == valueParameters.size() - 1) {
                        PsiElement parent = ktArrayAccessExpression.getLightParent();
                        if (parent instanceof KtBinaryExpression) {
                            KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) parent;
                            if (ktBinaryExpression.getOperationToken() == KtTokens.EQ) {
                                this.bindingTrace.record(CodegenBinding.SAM_VALUE, ktBinaryExpression.getRight(), samTypeCreateSamTypeByValueParameter);
                            }
                        }
                    } else {
                        this.bindingTrace.record(CodegenBinding.SAM_VALUE, indexExpressions.get(valueParameterDescriptor.getIndex()), samTypeCreateSamTypeByValueParameter);
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitWhenExpression(KtWhenExpression ktWhenExpression) {
        boolean z;
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(55);
        }
        super.visitWhenExpression(ktWhenExpression);
        if (isWhenWithEnums(ktWhenExpression)) {
            String currentTopLevelClassOrPackagePartInternalName = getCurrentTopLevelClassOrPackagePartInternalName(ktWhenExpression.getContainingKtFile());
            if (this.bindingContext.get(CodegenBinding.MAPPINGS_FOR_WHENS_BY_ENUM_IN_CLASS_FILE, currentTopLevelClassOrPackagePartInternalName) == null) {
                this.bindingTrace.record(CodegenBinding.MAPPINGS_FOR_WHENS_BY_ENUM_IN_CLASS_FILE, currentTopLevelClassOrPackagePartInternalName, new ArrayList(1));
            }
            List list = (List) this.bindingContext.get(CodegenBinding.MAPPINGS_FOR_WHENS_BY_ENUM_IN_CLASS_FILE, currentTopLevelClassOrPackagePartInternalName);
            int size = list.size();
            ClassDescriptor classDescriptor = (ClassDescriptor) WhenChecker.whenSubjectType(ktWhenExpression, this.bindingContext).getConstructor().getThis$0();
            Iterator<FunctionDescriptor> it = this.functionsStack.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                FunctionDescriptor next = it.next();
                if (next.isInline()) {
                    z = !DescriptorVisibilities.isPrivate(next.getVisibility());
                    break;
                }
            }
            WhenByEnumsMapping whenByEnumsMapping = new WhenByEnumsMapping(classDescriptor, currentTopLevelClassOrPackagePartInternalName, size, z);
            for (ConstantValue<?> constantValue : this.switchCodegenProvider.getAllConstants(ktWhenExpression)) {
                if (!(constantValue instanceof NullValue)) {
                    whenByEnumsMapping.putFirstTime((EnumValue) constantValue, whenByEnumsMapping.size() + 1);
                }
            }
            list.add(whenByEnumsMapping);
            this.bindingTrace.record(CodegenBinding.MAPPING_FOR_WHEN_BY_ENUM, ktWhenExpression, whenByEnumsMapping);
        }
    }

    private boolean isWhenWithEnums(KtWhenExpression ktWhenExpression) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(56);
        }
        final ClassId classIdForEnumSubject = WhenChecker.getClassIdForEnumSubject(ktWhenExpression, this.bindingContext);
        if (classIdForEnumSubject == null) {
            return false;
        }
        return this.switchCodegenProvider.checkAllItemsAreConstantsSatisfying(ktWhenExpression, new Function1() { // from class: org.jetbrains.kotlin.codegen.binding.CodegenAnnotatingVisitor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(CodegenAnnotatingVisitor.isEnumEntryOrNull(classIdForEnumSubject, (ConstantValue) obj));
            }
        });
    }

    private static boolean isEnumEntryOrNull(ClassId classId, ConstantValue<?> constantValue) {
        return ((constantValue instanceof EnumValue) && ((EnumValue) constantValue).getEnumClassId().equals(classId)) || (constantValue instanceof NullValue);
    }

    private String getCurrentTopLevelClassOrPackagePartInternalName(KtFile ktFile) {
        if (ktFile == null) {
            $$$reportNull$$$0(57);
        }
        Stack<ClassDescriptor> stack = this.classStack;
        ListIterator<ClassDescriptor> listIterator = stack.listIterator(stack.size());
        while (listIterator.hasPrevious()) {
            ClassDescriptor classDescriptorPrevious = listIterator.previous();
            if (DescriptorUtils.isTopLevelOrInnerClass(classDescriptorPrevious)) {
                String internalName = CodegenBinding.getAsmType(this.bindingContext, classDescriptorPrevious).getInternalName();
                if (internalName == null) {
                    $$$reportNull$$$0(58);
                }
                return internalName;
            }
        }
        String facadeClassInternalName = JvmFileClassUtil.getFacadeClassInternalName(ktFile);
        if (facadeClassInternalName == null) {
            $$$reportNull$$$0(59);
        }
        return facadeClassInternalName;
    }

    private static <T> T peekFromStack(Stack<T> stack) {
        if (stack == null) {
            $$$reportNull$$$0(60);
        }
        if (stack.empty()) {
            return null;
        }
        return stack.peek();
    }
}
