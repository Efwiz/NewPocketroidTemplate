package org.jetbrains.kotlin.codegen.context;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor;
import org.jetbrains.kotlin.codegen.AccessorForCompanionObjectInstanceFieldDescriptor;
import org.jetbrains.kotlin.codegen.AccessorForConstructorDescriptor;
import org.jetbrains.kotlin.codegen.AccessorForFunctionDescriptor;
import org.jetbrains.kotlin.codegen.AccessorForPropertyBackingField;
import org.jetbrains.kotlin.codegen.AccessorForPropertyDescriptor;
import org.jetbrains.kotlin.codegen.AccessorKind;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.SyntheticAccessorUtilKt;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CodegenContext<T extends DeclarationDescriptor> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Map<AccessorKey, AccessorForCallableDescriptor<?>> accessors;
    private final Map<ClassDescriptor, AccessorForCompanionObjectInstanceFieldDescriptor> accessorsForCompanionObjects;
    private Map<DeclarationDescriptor, CodegenContext> childContexts;
    public final MutableClosure closure;
    private final T contextDescriptor;
    private final OwnerKind contextKind;
    private final LocalLookup enclosingLocalLookup;
    private final NullableLazyValue<StackValue.Field> outerExpression;
    private final CodegenContext parentContext;
    private Map<AccessorKey, AccessorForPropertyDescriptorFactory> propertyAccessorFactories;
    private final ClassDescriptor thisDescriptor;

    /* JADX WARN: Code duplicated, block: B:20:0x003e A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:95:0x0150  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 18 && i != 19 && i != 23 && i != 40 && i != 43 && i != 54 && i != 65 && i != 67 && i != 68) {
            switch (i) {
                default:
                    switch (i) {
                        case 56:
                        case 57:
                        case 58:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 18 && i != 19 && i != 23 && i != 40 && i != 43 && i != 54 && i != 65 && i != 67 && i != 68) {
            switch (i) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 56:
                        case 57:
                        case 58:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "contextKind";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 18:
            case 19:
            case 23:
            case 40:
            case 43:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 54:
            case 56:
            case 57:
            case 58:
            case 65:
            case 67:
            case 68:
                objArr[0] = "org/jetbrains/kotlin/codegen/context/CodegenContext";
                break;
            case 7:
            case 8:
            case 12:
            case 15:
            case 20:
            case 24:
            case 38:
            case 39:
            case 41:
            case 53:
            case 55:
                objArr[0] = "descriptor";
                break;
            case 9:
            case 13:
                objArr[0] = "multifileClassType";
                break;
            case 10:
            case 14:
                objArr[0] = "filePartType";
                break;
            case 11:
                objArr[0] = "sourceFile";
                break;
            case 16:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 17:
            case 42:
                objArr[0] = "state";
                break;
            case 21:
                objArr[0] = "codegen";
                break;
            case 22:
                objArr[0] = "ownerKind";
                break;
            case 25:
            case 29:
            case 32:
            case 36:
                objArr[0] = "typeMapper";
                break;
            case 26:
                objArr[0] = "script";
                break;
            case 27:
                objArr[0] = "earlierScripts";
                break;
            case 28:
                objArr[0] = "classDescriptor";
                break;
            case 30:
                objArr[0] = "funDescriptor";
                break;
            case 31:
            case 35:
                objArr[0] = "localLookup";
                break;
            case 33:
                objArr[0] = "jvmViewOfSuspendLambda";
                break;
            case 34:
                objArr[0] = "originalSuspendLambdaDescriptor";
                break;
            case 37:
                objArr[0] = "propertyDescriptor";
                break;
            case 44:
            case 46:
                objArr[0] = "possiblySubstitutedDescriptor";
                break;
            case 45:
            case 47:
                objArr[0] = "accessorKind";
                break;
            case 59:
                objArr[0] = "unwrappedDescriptor";
                break;
            case 60:
                objArr[0] = "descriptorContext";
                break;
            case 61:
            case 63:
                objArr[0] = "child";
                break;
            case 62:
                objArr[0] = "childContextDescriptor";
                break;
            case 64:
                objArr[0] = "value";
                break;
            case 66:
                objArr[0] = "companionObjectDescriptor";
                break;
            default:
                objArr[0] = "contextDescriptor";
                break;
        }
        if (i == 2) {
            objArr[1] = "getState";
        } else if (i == 3) {
            objArr[1] = "getThisDescriptor";
        } else if (i == 4) {
            objArr[1] = "getClassOrPackageParentContext";
        } else if (i == 5) {
            objArr[1] = "getContextDescriptor";
        } else if (i == 6) {
            objArr[1] = "getContextKind";
        } else if (i == 18 || i == 19) {
            objArr[1] = "intoClass";
        } else if (i == 23) {
            objArr[1] = "intoFunction";
        } else if (i == 40) {
            objArr[1] = "getAccessor";
        } else if (i == 43) {
            objArr[1] = "getAccessorForSuperCallIfNeeded";
        } else if (i == 54) {
            objArr[1] = "accessibleDescriptor";
        } else if (i == 65) {
            objArr[1] = "getFirstCrossInlineOrNonInlineContext";
        } else if (i == 67) {
            objArr[1] = "getOrCreateAccessorForCompanionObject";
        } else if (i != 68) {
            switch (i) {
                case 48:
                case 49:
                case 50:
                case 51:
                    objArr[1] = "getAccessor";
                    break;
                case 52:
                    objArr[1] = "getAccessors";
                    break;
                default:
                    switch (i) {
                        case 56:
                        case 57:
                        case 58:
                            objArr[1] = "accessibleDescriptorIfNeeded";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/codegen/context/CodegenContext";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getRequiredAccessorsForCompanionObjects";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 18:
            case 19:
            case 23:
            case 40:
            case 43:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 54:
            case 56:
            case 57:
            case 58:
            case 65:
            case 67:
            case 68:
                break;
            case 7:
                objArr[2] = "intoPackagePart";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "intoMultifileClassPart";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "intoMultifileClass";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "intoClass";
                break;
            case 20:
            case 21:
            case 22:
                objArr[2] = "intoAnonymousClass";
                break;
            case 24:
            case 25:
                objArr[2] = "intoConstructor";
                break;
            case 26:
            case 27:
            case 28:
            case 29:
                objArr[2] = "intoScript";
                break;
            case 30:
            case 31:
            case 32:
                objArr[2] = "intoClosure";
                break;
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[2] = "intoCoroutineClosure";
                break;
            case 37:
                objArr[2] = "getPropertyAccessor";
                break;
            case 38:
                objArr[2] = "getAccessorForJvmDefaultCompatibility";
                break;
            case 39:
            case 44:
            case 45:
            case 46:
            case 47:
                objArr[2] = "getAccessor";
                break;
            case 41:
            case 42:
                objArr[2] = "getAccessorForSuperCallIfNeeded";
                break;
            case 53:
                objArr[2] = "accessibleDescriptor";
                break;
            case 55:
                objArr[2] = "accessibleDescriptorIfNeeded";
                break;
            case 59:
            case 60:
                objArr[2] = "isAccessorRequired";
                break;
            case 61:
                objArr[2] = "addChild";
                break;
            case 62:
                objArr[2] = "shouldAddChild";
                break;
            case 63:
                objArr[2] = "findChildContext";
                break;
            case 64:
                objArr[2] = "isStaticField";
                break;
            case 66:
                objArr[2] = "getOrCreateAccessorForCompanionObject";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 18 && i != 19 && i != 23 && i != 40 && i != 43 && i != 54 && i != 65 && i != 67 && i != 68) {
            switch (i) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                    break;
                default:
                    switch (i) {
                        case 56:
                        case 57:
                        case 58:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    protected StackValue.Field computeOuterExpression() {
        return null;
    }

    public CodegenContext getFirstCrossInlineOrNonInlineContext() {
        return this;
    }

    public boolean isContextWithUninitializedThis() {
        return false;
    }

    public boolean isInlineMethodContext() {
        return false;
    }

    private static class AccessorKey {
        public final AccessorKind accessorKind;
        public final DeclarationDescriptor descriptor;
        public final ClassDescriptor superCallLabelTarget;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "descriptor";
            } else {
                objArr[0] = "accessorKind";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/context/CodegenContext$AccessorKey";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public AccessorKey(DeclarationDescriptor declarationDescriptor, ClassDescriptor classDescriptor, AccessorKind accessorKind) {
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (accessorKind == null) {
                $$$reportNull$$$0(1);
            }
            this.descriptor = declarationDescriptor;
            this.superCallLabelTarget = classDescriptor;
            this.accessorKind = accessorKind;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof AccessorKey)) {
                return false;
            }
            AccessorKey accessorKey = (AccessorKey) obj;
            return this.descriptor.equals(accessorKey.descriptor) && this.accessorKind == accessorKey.accessorKind && Objects.equals(this.superCallLabelTarget, accessorKey.superCallLabelTarget);
        }

        public int hashCode() {
            int iHashCode = (this.descriptor.hashCode() * 31) + this.accessorKind.hashCode();
            ClassDescriptor classDescriptor = this.superCallLabelTarget;
            return iHashCode + (classDescriptor == null ? 0 : classDescriptor.hashCode());
        }

        public String toString() {
            return this.descriptor.toString();
        }
    }

    private static class AccessorForPropertyDescriptorFactory {
        private final AccessorKind accessorKind;
        private final DeclarationDescriptor containingDeclaration;
        private final String nameSuffix;
        private final PropertyDescriptor property;
        private final ClassDescriptor superCallTarget;
        private AccessorForPropertyDescriptor withSyntheticGetter;
        private AccessorForPropertyDescriptor withSyntheticGetterAndSetter;
        private AccessorForPropertyDescriptor withSyntheticSetter;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "containingDeclaration";
            } else if (i == 2) {
                objArr[0] = "nameSuffix";
            } else if (i == 3) {
                objArr[0] = "accessorKind";
            } else if (i != 4) {
                objArr[0] = "property";
            } else {
                objArr[0] = "org/jetbrains/kotlin/codegen/context/CodegenContext$AccessorForPropertyDescriptorFactory";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/codegen/context/CodegenContext$AccessorForPropertyDescriptorFactory";
            } else {
                objArr[1] = "getOrCreateAccessorWithSyntheticGetterAndSetter";
            }
            if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        public AccessorForPropertyDescriptorFactory(PropertyDescriptor propertyDescriptor, DeclarationDescriptor declarationDescriptor, ClassDescriptor classDescriptor, String str, AccessorKind accessorKind) {
            if (propertyDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(1);
            }
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (accessorKind == null) {
                $$$reportNull$$$0(3);
            }
            this.withSyntheticGetterAndSetter = null;
            this.withSyntheticGetter = null;
            this.withSyntheticSetter = null;
            this.property = propertyDescriptor;
            this.containingDeclaration = declarationDescriptor;
            this.superCallTarget = classDescriptor;
            this.nameSuffix = str;
            this.accessorKind = accessorKind;
        }

        public PropertyDescriptor getOrCreateAccessorIfNeeded(boolean z, boolean z2) {
            if (z && z2) {
                return getOrCreateAccessorWithSyntheticGetterAndSetter();
            }
            if (z && !z2) {
                if (this.withSyntheticGetter == null) {
                    this.withSyntheticGetter = new AccessorForPropertyDescriptor(this.property, this.containingDeclaration, this.superCallTarget, this.nameSuffix, true, false, this.accessorKind);
                }
                return this.withSyntheticGetter;
            }
            if (!z && z2) {
                if (this.withSyntheticSetter == null) {
                    this.withSyntheticSetter = new AccessorForPropertyDescriptor(this.property, this.containingDeclaration, this.superCallTarget, this.nameSuffix, false, true, this.accessorKind);
                }
                return this.withSyntheticSetter;
            }
            return this.property;
        }

        public AccessorForPropertyDescriptor getOrCreateAccessorWithSyntheticGetterAndSetter() {
            if (this.withSyntheticGetterAndSetter == null) {
                this.withSyntheticGetterAndSetter = new AccessorForPropertyDescriptor(this.property, this.containingDeclaration, this.superCallTarget, this.nameSuffix, true, true, this.accessorKind);
            }
            AccessorForPropertyDescriptor accessorForPropertyDescriptor = this.withSyntheticGetterAndSetter;
            if (accessorForPropertyDescriptor == null) {
                $$$reportNull$$$0(4);
            }
            return accessorForPropertyDescriptor;
        }
    }

    public CodegenContext(T t, OwnerKind ownerKind, CodegenContext codegenContext, MutableClosure mutableClosure, ClassDescriptor classDescriptor, LocalLookup localLookup) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(1);
        }
        this.accessorsForCompanionObjects = new LinkedHashMap();
        this.contextDescriptor = t;
        this.contextKind = ownerKind;
        this.parentContext = codegenContext;
        this.closure = mutableClosure;
        this.thisDescriptor = classDescriptor;
        this.enclosingLocalLookup = localLookup;
        this.outerExpression = LockBasedStorageManager.NO_LOCKS.createNullableLazyValue(new Function0() { // from class: org.jetbrains.kotlin.codegen.context.CodegenContext$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.computeOuterExpression();
            }
        });
        if (codegenContext != null) {
            codegenContext.addChild(this);
        }
    }

    public GenerationState getState() {
        GenerationState state = this.parentContext.getState();
        if (state == null) {
            $$$reportNull$$$0(2);
        }
        return state;
    }

    public final ClassDescriptor getThisDescriptor() {
        ClassDescriptor classDescriptor = this.thisDescriptor;
        if (classDescriptor == null) {
            throw new UnsupportedOperationException("Context doesn't have a \"this\": " + this);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        return classDescriptor;
    }

    public final boolean hasThisDescriptor() {
        return this.thisDescriptor != null;
    }

    public CodegenContext<? extends ClassOrPackageFragmentDescriptor> getClassOrPackageParentContext() {
        CodegenContext parentContext = this;
        while (!(parentContext.getContextDescriptor() instanceof ClassOrPackageFragmentDescriptor)) {
            parentContext = parentContext.getParentContext();
        }
        if (parentContext == null) {
            $$$reportNull$$$0(4);
        }
        return parentContext;
    }

    public final CallableDescriptor getCallableDescriptorWithReceiver() {
        if (!(this.contextDescriptor instanceof CallableDescriptor)) {
            return null;
        }
        CallableDescriptor callableDescriptor = (CallableDescriptor) getContextDescriptor();
        if (callableDescriptor.getExtensionReceiverParameter() != null) {
            return callableDescriptor;
        }
        return null;
    }

    public StackValue getOuterExpression(StackValue stackValue, boolean z) {
        return getOuterExpression(stackValue, z, true);
    }

    private StackValue getOuterExpression(StackValue stackValue, boolean z, boolean z2) {
        if (this.outerExpression.invoke() == null) {
            if (z) {
                return null;
            }
            throw new UnsupportedOperationException("Don't know how to generate outer expression: " + this);
        }
        if (z2) {
            MutableClosure mutableClosure = this.closure;
            if (mutableClosure == null) {
                throw new IllegalStateException("Can't capture this for context without closure: " + this);
            }
            mutableClosure.setNeedsCaptureOuterClass();
        }
        return StackValue.changeReceiverForFieldAndSharedVar(this.outerExpression.invoke(), stackValue);
    }

    public T getContextDescriptor() {
        T t = this.contextDescriptor;
        if (t == null) {
            $$$reportNull$$$0(5);
        }
        return t;
    }

    public OwnerKind getContextKind() {
        OwnerKind ownerKind = this.contextKind;
        if (ownerKind == null) {
            $$$reportNull$$$0(6);
        }
        return ownerKind;
    }

    public PackageContext intoPackagePart(PackageFragmentDescriptor packageFragmentDescriptor, Type type, KtFile ktFile) {
        if (packageFragmentDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        return new PackageContext(packageFragmentDescriptor, this, type, ktFile);
    }

    public MultifileClassPartContext intoMultifileClassPart(PackageFragmentDescriptor packageFragmentDescriptor, Type type, Type type2, KtFile ktFile) {
        if (packageFragmentDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (type == null) {
            $$$reportNull$$$0(9);
        }
        if (type2 == null) {
            $$$reportNull$$$0(10);
        }
        if (ktFile == null) {
            $$$reportNull$$$0(11);
        }
        return new MultifileClassPartContext(packageFragmentDescriptor, this, type, type2, ktFile);
    }

    public FieldOwnerContext<PackageFragmentDescriptor> intoMultifileClass(PackageFragmentDescriptor packageFragmentDescriptor, Type type, Type type2) {
        if (packageFragmentDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (type == null) {
            $$$reportNull$$$0(13);
        }
        if (type2 == null) {
            $$$reportNull$$$0(14);
        }
        return new MultifileClassFacadeContext(packageFragmentDescriptor, this, type, type2);
    }

    public ClassContext intoDefaultImplsClass(ClassDescriptor classDescriptor, ClassContext classContext, GenerationState generationState) {
        return new DefaultImplsClassContext(generationState.getTypeMapper(), classDescriptor, OwnerKind.DEFAULT_IMPLS, this, null, classContext);
    }

    public ClassContext intoClass(ClassDescriptor classDescriptor, OwnerKind ownerKind, GenerationState generationState) {
        CodegenContext codegenContextFindChildContext;
        if (classDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(16);
        }
        if (generationState == null) {
            $$$reportNull$$$0(17);
        }
        if (shouldAddChild(classDescriptor) && (codegenContextFindChildContext = findChildContext(classDescriptor)) != null) {
            ClassContext classContext = (ClassContext) codegenContextFindChildContext;
            if (classContext == null) {
                $$$reportNull$$$0(18);
            }
            return classContext;
        }
        ClassContext classContext2 = new ClassContext(generationState.getTypeMapper(), classDescriptor, ownerKind, this, null);
        if (classDescriptor.mo6346getCompanionObjectDescriptor() != null) {
            classContext2.intoClass(classDescriptor.mo6346getCompanionObjectDescriptor(), OwnerKind.IMPLEMENTATION, generationState);
        }
        return classContext2;
    }

    public ClassContext intoAnonymousClass(ClassDescriptor classDescriptor, ExpressionCodegen expressionCodegen, OwnerKind ownerKind) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(21);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(22);
        }
        return new AnonymousClassContext(expressionCodegen.getState().getTypeMapper(), classDescriptor, ownerKind, this, expressionCodegen);
    }

    public MethodContext intoFunction(FunctionDescriptor functionDescriptor, boolean z) {
        return new MethodContext(functionDescriptor, getContextKind(), this, null, z);
    }

    public MethodContext intoFunction(FunctionDescriptor functionDescriptor) {
        MethodContext methodContextIntoFunction = intoFunction(functionDescriptor, false);
        if (methodContextIntoFunction == null) {
            $$$reportNull$$$0(23);
        }
        return methodContextIntoFunction;
    }

    public MethodContext intoInlinedLambda(FunctionDescriptor functionDescriptor, boolean z, boolean z2) {
        return new InlineLambdaContext(functionDescriptor, getContextKind(), this, null, z, z2);
    }

    public ConstructorContext intoConstructor(ConstructorDescriptor constructorDescriptor, KotlinTypeMapper kotlinTypeMapper) {
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(25);
        }
        return new ConstructorContext(constructorDescriptor, getContextKind(), this, this.closure, kotlinTypeMapper);
    }

    public ScriptContext intoScript(ScriptDescriptor scriptDescriptor, List<ScriptDescriptor> list, ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper) {
        if (scriptDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        if (list == null) {
            $$$reportNull$$$0(27);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(28);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(29);
        }
        return new ScriptContext(kotlinTypeMapper, scriptDescriptor, list, classDescriptor, this);
    }

    public ClosureContext intoClosure(FunctionDescriptor functionDescriptor, LocalLookup localLookup, KotlinTypeMapper kotlinTypeMapper) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        if (localLookup == null) {
            $$$reportNull$$$0(31);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(32);
        }
        return new ClosureContext(kotlinTypeMapper, functionDescriptor, this, localLookup);
    }

    public ClosureContext intoCoroutineClosure(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, LocalLookup localLookup, KotlinTypeMapper kotlinTypeMapper) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        if (functionDescriptor2 == null) {
            $$$reportNull$$$0(34);
        }
        if (localLookup == null) {
            $$$reportNull$$$0(35);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(36);
        }
        return new ClosureContext(kotlinTypeMapper, functionDescriptor, this, localLookup, functionDescriptor2);
    }

    public ClassContext intoWrapperForErasedInlineClass(ClassDescriptor classDescriptor, GenerationState generationState) {
        return new ClassContext(generationState.getTypeMapper(), classDescriptor, OwnerKind.ERASED_INLINE_CLASS, this, null);
    }

    public CodegenContext getParentContext() {
        return this.parentContext;
    }

    public CodegenContext getEnclosingClassContext() {
        for (CodegenContext enclosingThisContext = getEnclosingThisContext(); enclosingThisContext != null; enclosingThisContext = enclosingThisContext.getParentContext()) {
            if (enclosingThisContext.getContextDescriptor() instanceof ClassDescriptor) {
                return enclosingThisContext;
            }
        }
        return null;
    }

    public CodegenContext getEnclosingThisContext() {
        CodegenContext parentContext = getParentContext();
        while (parentContext != null && parentContext.isContextWithUninitializedThis()) {
            parentContext = parentContext.getParentContext().getParentContext();
        }
        return parentContext;
    }

    public ClassDescriptor getEnclosingClass() {
        CodegenContext enclosingClassContext = getEnclosingClassContext();
        if (enclosingClassContext == null) {
            return null;
        }
        return (ClassDescriptor) enclosingClassContext.getContextDescriptor();
    }

    public CodegenContext findParentContextWithDescriptor(DeclarationDescriptor declarationDescriptor) {
        CodegenContext<T> parentContext = this;
        while (parentContext != null && (parentContext.isShadowedByParentContext() || parentContext.getContextDescriptor() != declarationDescriptor)) {
            parentContext = parentContext.getParentContext();
        }
        return parentContext;
    }

    private boolean isShadowedByParentContext() {
        return getContextKind() == OwnerKind.ERASED_INLINE_CLASS;
    }

    private PropertyDescriptor getPropertyAccessor(PropertyDescriptor propertyDescriptor, ClassDescriptor classDescriptor, boolean z, boolean z2) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(37);
        }
        return (PropertyDescriptor) getAccessor(propertyDescriptor, AccessorKind.NORMAL, null, classDescriptor, z, z2);
    }

    public <D extends CallableMemberDescriptor> D getAccessorForJvmDefaultCompatibility(D d) {
        if (d == null) {
            $$$reportNull$$$0(38);
        }
        if (d instanceof PropertyAccessorDescriptor) {
            boolean z = d instanceof PropertyGetterDescriptor;
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) getAccessor(((PropertyAccessorDescriptor) d).getCorrespondingProperty(), AccessorKind.JVM_DEFAULT_COMPATIBILITY, null, null, z, d instanceof PropertySetterDescriptor);
            return z ? propertyDescriptor.getGetter() : propertyDescriptor.getSetter();
        }
        return (D) getAccessor(d, AccessorKind.JVM_DEFAULT_COMPATIBILITY, null, null);
    }

    private <D extends CallableMemberDescriptor> D getAccessor(D d, ClassDescriptor classDescriptor) {
        if (d == null) {
            $$$reportNull$$$0(39);
        }
        D d2 = (D) getAccessor(d, AccessorKind.NORMAL, null, classDescriptor);
        if (d2 == null) {
            $$$reportNull$$$0(40);
        }
        return d2;
    }

    public <D extends CallableMemberDescriptor> D getAccessorForSuperCallIfNeeded(D d, ClassDescriptor classDescriptor, GenerationState generationState) {
        CodegenContext firstCrossInlineOrNonInlineContext;
        CodegenContext codegenContextFindParentContextWithDescriptor;
        if (d == null) {
            $$$reportNull$$$0(41);
        }
        if (generationState == null) {
            $$$reportNull$$$0(42);
        }
        if (classDescriptor != null && !JvmCodegenUtil.isNonDefaultInterfaceMember(d, generationState.getJvmDefaultMode()) && (codegenContextFindParentContextWithDescriptor = (firstCrossInlineOrNonInlineContext = getFirstCrossInlineOrNonInlineContext()).findParentContextWithDescriptor(classDescriptor)) != firstCrossInlineOrNonInlineContext.getParentContext()) {
            return (D) codegenContextFindParentContextWithDescriptor.getAccessor(d, classDescriptor);
        }
        if (d == null) {
            $$$reportNull$$$0(43);
        }
        return d;
    }

    public <D extends CallableMemberDescriptor> D getAccessor(D d, AccessorKind accessorKind, KotlinType kotlinType, ClassDescriptor classDescriptor) {
        if (d == null) {
            $$$reportNull$$$0(44);
        }
        if (accessorKind == null) {
            $$$reportNull$$$0(45);
        }
        return (D) getAccessor(d, accessorKind, kotlinType, classDescriptor, true, true);
    }

    private <D extends CallableMemberDescriptor> D getAccessor(D d, AccessorKind accessorKind, KotlinType kotlinType, ClassDescriptor classDescriptor, boolean z, boolean z2) {
        AccessorForCallableDescriptor<?> accessorForPropertyBackingField;
        if (d == null) {
            $$$reportNull$$$0(46);
        }
        if (accessorKind == null) {
            $$$reportNull$$$0(47);
        }
        if (this.accessors == null) {
            this.accessors = new LinkedHashMap();
        }
        if (this.propertyAccessorFactories == null) {
            this.propertyAccessorFactories = new LinkedHashMap();
        }
        CallableMemberDescriptor original = d.getOriginal();
        AccessorKey accessorKey = new AccessorKey(original, classDescriptor, accessorKind);
        AccessorForPropertyDescriptorFactory accessorForPropertyDescriptorFactory = this.propertyAccessorFactories.get(accessorKey);
        if (accessorForPropertyDescriptorFactory != null) {
            PropertyDescriptor orCreateAccessorIfNeeded = accessorForPropertyDescriptorFactory.getOrCreateAccessorIfNeeded(z, z2);
            if (orCreateAccessorIfNeeded == null) {
                $$$reportNull$$$0(48);
            }
            return orCreateAccessorIfNeeded;
        }
        AccessorForCallableDescriptor<?> accessorForCallableDescriptor = this.accessors.get(accessorKey);
        if (accessorForCallableDescriptor != null) {
            D d2 = (D) accessorForCallableDescriptor;
            if (d2 == null) {
                $$$reportNull$$$0(49);
            }
            return d2;
        }
        String accessorNameSuffix = SyntheticAccessorUtilKt.getAccessorNameSuffix(original, accessorKey.superCallLabelTarget, accessorKind);
        if (original instanceof SimpleFunctionDescriptor) {
            accessorForPropertyBackingField = new AccessorForFunctionDescriptor((FunctionDescriptor) original, this.contextDescriptor, classDescriptor, accessorNameSuffix, accessorKind);
        } else if (original instanceof ClassConstructorDescriptor) {
            accessorForPropertyBackingField = new AccessorForConstructorDescriptor((ClassConstructorDescriptor) original, this.contextDescriptor, classDescriptor, accessorKind);
        } else if (original instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) original;
            if (accessorKind == AccessorKind.NORMAL || accessorKind == AccessorKind.JVM_DEFAULT_COMPATIBILITY) {
                AccessorForPropertyDescriptorFactory accessorForPropertyDescriptorFactory2 = new AccessorForPropertyDescriptorFactory(propertyDescriptor, this.contextDescriptor, classDescriptor, accessorNameSuffix, accessorKind);
                this.propertyAccessorFactories.put(accessorKey, accessorForPropertyDescriptorFactory2);
                this.accessors.put(accessorKey, accessorForPropertyDescriptorFactory2.getOrCreateAccessorWithSyntheticGetterAndSetter());
                PropertyDescriptor orCreateAccessorIfNeeded2 = accessorForPropertyDescriptorFactory2.getOrCreateAccessorIfNeeded(z, z2);
                if (orCreateAccessorIfNeeded2 == null) {
                    $$$reportNull$$$0(50);
                }
                return orCreateAccessorIfNeeded2;
            }
            accessorForPropertyBackingField = new AccessorForPropertyBackingField(propertyDescriptor, this.contextDescriptor, kotlinType, accessorKind == AccessorKind.IN_CLASS_COMPANION ? null : propertyDescriptor.getExtensionReceiverParameter(), accessorKind == AccessorKind.IN_CLASS_COMPANION ? null : propertyDescriptor.mo6753getDispatchReceiverParameter(), accessorNameSuffix, accessorKind);
        } else {
            throw new UnsupportedOperationException("Do not know how to create accessor for descriptor " + original + " in context " + this);
        }
        this.accessors.put(accessorKey, accessorForPropertyBackingField);
        return (D) accessorForPropertyBackingField;
    }

    public StackValue lookupInContext(DeclarationDescriptor declarationDescriptor, StackValue stackValue, GenerationState generationState, boolean z) {
        StackValue stackValueLookupInContext;
        StackValue outerExpression;
        MutableClosure mutableClosure = this.closure;
        if (mutableClosure != null) {
            EnclosedValueDescriptor enclosedValueDescriptor = mutableClosure.getCaptureVariables().get(declarationDescriptor);
            if (enclosedValueDescriptor != null) {
                return StackValue.changeReceiverForFieldAndSharedVar(enclosedValueDescriptor.getInnerValue(), stackValue);
            }
            for (LocalLookup.LocalLookupCase localLookupCase : LocalLookup.LocalLookupCase.values()) {
                if (localLookupCase.isCase(declarationDescriptor)) {
                    StackValue.StackValueWithSimpleReceiver stackValueWithSimpleReceiverInnerValue = localLookupCase.innerValue(declarationDescriptor, this.enclosingLocalLookup, generationState, this.closure, generationState.getTypeMapper().mapType(getThisDescriptor()));
                    if (stackValueWithSimpleReceiverInnerValue == null) {
                        break;
                    }
                    return StackValue.changeReceiverForFieldAndSharedVar(stackValueWithSimpleReceiverInnerValue, stackValue);
                }
            }
            outerExpression = getOuterExpression(stackValue, z, false);
            stackValueLookupInContext = outerExpression;
        } else {
            stackValueLookupInContext = stackValue;
            outerExpression = null;
        }
        if (outerExpression == null || getEnclosingClass() != declarationDescriptor) {
            CodegenContext enclosingThisContext = getEnclosingThisContext();
            stackValueLookupInContext = enclosingThisContext != null ? enclosingThisContext.lookupInContext(declarationDescriptor, stackValueLookupInContext, generationState, z) : null;
        }
        if (outerExpression != null && stackValueLookupInContext != null && !isStaticField(stackValueLookupInContext)) {
            this.closure.setNeedsCaptureOuterClass();
        }
        return stackValueLookupInContext;
    }

    public Collection<? extends AccessorForCallableDescriptor<?>> getAccessors() {
        Map<AccessorKey, AccessorForCallableDescriptor<?>> map = this.accessors;
        Collection<AccessorForCallableDescriptor<?>> collectionEmptySet = map == null ? Collections.emptySet() : map.values();
        if (collectionEmptySet == null) {
            $$$reportNull$$$0(52);
        }
        return collectionEmptySet;
    }

    public <D extends CallableMemberDescriptor> D accessibleDescriptor(D d, ClassDescriptor classDescriptor) {
        if (d == null) {
            $$$reportNull$$$0(53);
        }
        CodegenContext firstCrossInlineOrNonInlineContext = getFirstCrossInlineOrNonInlineContext();
        DeclarationDescriptor containingDeclaration = d.getContainingModule();
        boolean zIsInlineMethodContext = firstCrossInlineOrNonInlineContext.isInlineMethodContext();
        boolean z = JvmAnnotationUtilKt.isCompiledToJvmDefault(d, getState().getJvmDefaultMode()) == JvmAnnotationUtilKt.isCallableMemberCompiledToJvmDefault(firstCrossInlineOrNonInlineContext.contextDescriptor, getState().getJvmDefaultMode()) || (firstCrossInlineOrNonInlineContext.contextDescriptor instanceof AccessorForCallableDescriptor);
        if (zIsInlineMethodContext || (firstCrossInlineOrNonInlineContext.hasThisDescriptor() && !((containingDeclaration == firstCrossInlineOrNonInlineContext.getThisDescriptor() && z) || (containingDeclaration == firstCrossInlineOrNonInlineContext.getClassOrPackageParentContext().getContextDescriptor() && z)))) {
            return (D) firstCrossInlineOrNonInlineContext.accessibleDescriptorIfNeeded(d, classDescriptor, zIsInlineMethodContext);
        }
        if (d == null) {
            $$$reportNull$$$0(54);
        }
        return d;
    }

    private <D extends CallableMemberDescriptor> D accessibleDescriptorIfNeeded(D d, ClassDescriptor classDescriptor, boolean z) {
        CodegenContext codegenContextFindParentContextWithDescriptor;
        if (d == null) {
            $$$reportNull$$$0(55);
        }
        CallableMemberDescriptor callableMemberDescriptorUnwrapFakeOverride = DescriptorUtils.unwrapFakeOverride(d);
        DeclarationDescriptor containingDeclaration = d.getContainingModule();
        CodegenContext codegenContextFindParentContextWithDescriptor2 = findParentContextWithDescriptor(containingDeclaration);
        if (codegenContextFindParentContextWithDescriptor2 == null && DescriptorUtils.isCompanionObject(containingDeclaration)) {
            CodegenContext codegenContextFindParentContextWithDescriptor3 = findParentContextWithDescriptor(containingDeclaration.getContainingModule());
            if (codegenContextFindParentContextWithDescriptor3 instanceof ClassContext) {
                codegenContextFindParentContextWithDescriptor2 = ((ClassContext) codegenContextFindParentContextWithDescriptor3).getCompanionObjectContext();
            }
        }
        if (codegenContextFindParentContextWithDescriptor2 == null && JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY == d.getVisibility() && !(d.getOriginal() instanceof SamConstructorDescriptor)) {
            classDescriptor = (ClassDescriptor) containingDeclaration;
            codegenContextFindParentContextWithDescriptor2 = ExpressionCodegen.getParentContextSubclassOf(classDescriptor, this);
        }
        if (codegenContextFindParentContextWithDescriptor2 == null && z && classDescriptor != null) {
            codegenContextFindParentContextWithDescriptor2 = ExpressionCodegen.getParentContextSubclassOf((ClassDescriptor) containingDeclaration, this);
        }
        if (codegenContextFindParentContextWithDescriptor2 == null && (d instanceof ClassConstructorDescriptor)) {
            ClassDescriptor containingModule = ((ClassConstructorDescriptor) d).getContainingModule();
            if (DescriptorUtils.isSealedClass(containingModule) && (codegenContextFindParentContextWithDescriptor = findParentContextWithDescriptor(containingModule.getContainingModule())) != null) {
                codegenContextFindParentContextWithDescriptor2 = codegenContextFindParentContextWithDescriptor.findChildContext(containingModule);
            }
        }
        if (codegenContextFindParentContextWithDescriptor2 == null) {
            if (d == null) {
                $$$reportNull$$$0(56);
            }
            return d;
        }
        if (getState().getJvmDefaultMode() == JvmDefaultMode.ENABLE && JvmAnnotationUtilKt.hasJvmDefaultAnnotation(d) && (codegenContextFindParentContextWithDescriptor2 instanceof DefaultImplsClassContext)) {
            codegenContextFindParentContextWithDescriptor2 = ((DefaultImplsClassContext) codegenContextFindParentContextWithDescriptor2).getInterfaceContext();
        }
        if (d instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) d;
            int visibilityAccessFlag = DescriptorAsmUtil.getVisibilityAccessFlag(d);
            PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
            boolean zIsAccessorRequired = isAccessorRequired(getter == null ? visibilityAccessFlag : DescriptorAsmUtil.getVisibilityAccessFlag(getter) | visibilityAccessFlag, callableMemberDescriptorUnwrapFakeOverride, codegenContextFindParentContextWithDescriptor2, z, classDescriptor != null);
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            if (setter != null && setter.getVisibility().normalize() != DescriptorVisibilities.INVISIBLE_FAKE) {
                visibilityAccessFlag |= DescriptorAsmUtil.getVisibilityAccessFlag(setter);
            }
            boolean zIsAccessorRequired2 = isAccessorRequired(visibilityAccessFlag, callableMemberDescriptorUnwrapFakeOverride, codegenContextFindParentContextWithDescriptor2, z, classDescriptor != null);
            if (zIsAccessorRequired || zIsAccessorRequired2) {
                return codegenContextFindParentContextWithDescriptor2.getPropertyAccessor(propertyDescriptor, classDescriptor, zIsAccessorRequired, zIsAccessorRequired2);
            }
            if (d == null) {
                $$$reportNull$$$0(57);
            }
            return d;
        }
        if (isAccessorRequired(DescriptorAsmUtil.getVisibilityAccessFlag(callableMemberDescriptorUnwrapFakeOverride), callableMemberDescriptorUnwrapFakeOverride, codegenContextFindParentContextWithDescriptor2, z, classDescriptor != null)) {
            return (D) codegenContextFindParentContextWithDescriptor2.getAccessor(d, classDescriptor);
        }
        if (d == null) {
            $$$reportNull$$$0(58);
        }
        return d;
    }

    private static boolean isAccessorRequired(int i, CallableMemberDescriptor callableMemberDescriptor, CodegenContext codegenContext, boolean z, boolean z2) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(59);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(60);
        }
        if (InlineOnlyKt.isInlineOnlyPrivateInBytecode(callableMemberDescriptor)) {
            return false;
        }
        if ((!z2 || !z) && (i & 2) == 0) {
            if ((i & 4) == 0) {
                return false;
            }
            if (!z && JvmCodegenUtil.isInSamePackage(callableMemberDescriptor, codegenContext.getContextDescriptor())) {
                return false;
            }
        }
        return true;
    }

    private void addChild(CodegenContext codegenContext) {
        if (codegenContext == null) {
            $$$reportNull$$$0(61);
        }
        if (shouldAddChild(codegenContext.contextDescriptor)) {
            if (this.childContexts == null) {
                this.childContexts = new HashMap();
            }
            this.childContexts.put(codegenContext.getContextDescriptor(), codegenContext);
        }
    }

    private static boolean shouldAddChild(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(62);
        }
        return DescriptorUtils.isCompanionObject(declarationDescriptor) || DescriptorUtils.isSealedClass(declarationDescriptor);
    }

    protected CodegenContext findChildContext(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(63);
        }
        Map<DeclarationDescriptor, CodegenContext> map = this.childContexts;
        if (map == null) {
            return null;
        }
        return map.get(declarationDescriptor);
    }

    private static boolean isStaticField(StackValue stackValue) {
        if (stackValue == null) {
            $$$reportNull$$$0(64);
        }
        return (stackValue instanceof StackValue.Field) && ((StackValue.Field) stackValue).isStaticPut;
    }

    public LocalLookup getEnclosingLocalLookup() {
        return this.enclosingLocalLookup;
    }

    public AccessorForCompanionObjectInstanceFieldDescriptor getOrCreateAccessorForCompanionObject(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(66);
        }
        AccessorForCompanionObjectInstanceFieldDescriptor accessorForCompanionObjectInstanceFieldDescriptorComputeIfAbsent = this.accessorsForCompanionObjects.computeIfAbsent(classDescriptor, new Function() { // from class: org.jetbrains.kotlin.codegen.context.CodegenContext$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CodegenContext.lambda$getOrCreateAccessorForCompanionObject$0((ClassDescriptor) obj);
            }
        });
        if (accessorForCompanionObjectInstanceFieldDescriptorComputeIfAbsent == null) {
            $$$reportNull$$$0(67);
        }
        return accessorForCompanionObjectInstanceFieldDescriptorComputeIfAbsent;
    }

    static /* synthetic */ AccessorForCompanionObjectInstanceFieldDescriptor lambda$getOrCreateAccessorForCompanionObject$0(ClassDescriptor classDescriptor) {
        return new AccessorForCompanionObjectInstanceFieldDescriptor(classDescriptor, Name.identifier(JvmCodegenUtil.getCompanionObjectAccessorName(classDescriptor)));
    }

    public Collection<AccessorForCompanionObjectInstanceFieldDescriptor> getRequiredAccessorsForCompanionObjects() {
        Collection<AccessorForCompanionObjectInstanceFieldDescriptor> collectionValues = this.accessorsForCompanionObjects.values();
        if (collectionValues == null) {
            $$$reportNull$$$0(68);
        }
        return collectionValues;
    }
}
