package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.jvm.functions.Function0;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.CodegenContextUtil;
import org.jetbrains.kotlin.codegen.context.DelegatingToPartContext;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.InlineLambdaContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.context.MultifileClassFacadeContext;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.NameGenerator;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.inline.SourceMapper;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.codegen.state.TypeMapperUtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.AnnotatedImpl;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MemberCodegen<T extends KtPureElement> implements InnerClassConsumer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Comparator<AccessorForCompanionObjectInstanceFieldDescriptor> BY_COMPANION_FQN;
    private boolean alwaysWriteSourceMap;
    public final BindingContext bindingContext;
    private ExpressionCodegen clInit;
    protected final FieldOwnerContext<?> context;
    protected final T element;
    public final FunctionCodegen functionCodegen;
    private NameGenerator inlineNameGenerator;
    private final Collection<ClassDescriptor> innerClasses;
    private boolean jvmAssertFieldGenerated;
    private boolean nopSeparatorNeeded;
    private final MemberCodegen<?> parentCodegen;
    public final PropertyCodegen propertyCodegen;
    private final ReifiedTypeParametersUsages reifiedTypeParametersUsages;
    private SourceMapper sourceMapper;
    public final GenerationState state;
    private final Collection<SyntheticInnerClassInfo> syntheticInnerClasses;
    public final KotlinTypeMapper typeMapper;
    public final ClassBuilder v;

    /* JADX WARN: Code duplicated, block: B:10:0x001a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 31 && i != 32 && i != 34 && i != 41 && i != 42) {
            switch (i) {
                case 49:
                case 50:
                case 51:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 31 && i != 32 && i != 34 && i != 41 && i != 42) {
            switch (i) {
                case 49:
                case 50:
                case 51:
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
            case 28:
            case 30:
                objArr[0] = "context";
                break;
            case 2:
                objArr[0] = "builder";
                break;
            case 3:
                objArr[0] = "wrapped";
                break;
            case 4:
            case 6:
            case 24:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 5:
            case 27:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 7:
                objArr[0] = "declaration";
                break;
            case 8:
                objArr[0] = "typeAlias";
                break;
            case 9:
            case 18:
            case 33:
            case 40:
                objArr[0] = "descriptor";
                break;
            case 10:
                objArr[0] = "syntheticMethod";
                break;
            case 11:
                objArr[0] = "annotations";
                break;
            case 12:
            case 15:
                objArr[0] = "parentContext";
                break;
            case 13:
            case 16:
                objArr[0] = "aClass";
                break;
            case 14:
            case 17:
            case 48:
            default:
                objArr[0] = "state";
                break;
            case 19:
                objArr[0] = "innerClasses";
                break;
            case 20:
                objArr[0] = "classDescriptor";
                break;
            case 21:
            case 22:
                objArr[0] = "innerClass";
                break;
            case 23:
            case 25:
            case 29:
                objArr[0] = "typeMapper";
                break;
            case 26:
                objArr[0] = "jvmDefaultMode";
                break;
            case 31:
            case 32:
            case 34:
            case 41:
            case 42:
            case 49:
            case 50:
            case 51:
                objArr[0] = "org/jetbrains/kotlin/codegen/MemberCodegen";
                break;
            case 35:
                objArr[0] = "createCodegen";
                break;
            case 36:
                objArr[0] = "codegen";
                break;
            case 37:
            case 38:
            case 39:
            case 47:
                objArr[0] = "property";
                break;
            case 43:
                objArr[0] = "propertyDescriptor";
                break;
            case 44:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 45:
            case 52:
                objArr[0] = "thisAsmType";
                break;
            case 46:
            case 57:
                objArr[0] = "iv";
                break;
            case 53:
                objArr[0] = "fieldAsmType";
                break;
            case 54:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 55:
                objArr[0] = "accessorForCallableDescriptor";
                break;
            case 56:
                objArr[0] = "functionDescriptor";
                break;
        }
        if (i == 31) {
            objArr[1] = "getInlineNameGenerator";
        } else if (i == 32) {
            objArr[1] = "createOrGetClInitCodegen";
        } else if (i == 34) {
            objArr[1] = "createClInitFunctionDescriptor";
        } else if (i != 41 && i != 42) {
            switch (i) {
                case 49:
                    objArr[1] = "getContext";
                    break;
                case 50:
                    objArr[1] = "getReifiedTypeParametersUsages";
                    break;
                case 51:
                    objArr[1] = "getOrCreateSourceMapper";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/codegen/MemberCodegen";
                    break;
            }
        } else {
            objArr[1] = "getPropertyOrDelegateType";
        }
        switch (i) {
            case 4:
                objArr[2] = "markLineNumberForDescriptor";
                break;
            case 5:
            case 6:
                objArr[2] = "markLineNumberForElement";
                break;
            case 7:
                objArr[2] = "genSimpleMember";
                break;
            case 8:
                objArr[2] = "genTypeAlias";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "generateSyntheticAnnotationsMethod";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "genClassOrObject";
                break;
            case 19:
                objArr[2] = "addParentsToInnerClassesIfNeeded";
                break;
            case 20:
                objArr[2] = "addInnerClassInfoFromAnnotation";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "writeInnerClass";
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "computeOuterClass";
                break;
            case 29:
            case 30:
                objArr[2] = "computeEnclosingMethod";
                break;
            case 31:
            case 32:
            case 34:
            case 41:
            case 42:
            case 49:
            case 50:
            case 51:
                break;
            case 33:
                objArr[2] = "createClInitFunctionDescriptor";
                break;
            case 35:
                objArr[2] = "generateInitializers";
                break;
            case 36:
            case 37:
                objArr[2] = "initializeProperty";
                break;
            case 38:
                objArr[2] = "shouldInitializeProperty";
                break;
            case 39:
            case 40:
                objArr[2] = "getPropertyOrDelegateType";
                break;
            case 43:
            case 44:
                objArr[2] = "skipDefaultValue";
                break;
            case 45:
                objArr[2] = "generatePropertyMetadataArrayFieldIfNeeded";
                break;
            case 46:
            case 47:
            case 48:
                objArr[2] = "generatePropertyReference";
                break;
            case 52:
            case 53:
                objArr[2] = "generateConstInstance";
                break;
            case 54:
                objArr[2] = "generateSyntheticAccessorForCompanionObject";
                break;
            case 55:
                objArr[2] = "generateSyntheticAccessor";
                break;
            case 56:
            case 57:
                objArr[2] = "generateMethodCallTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 31 && i != 32 && i != 34 && i != 41 && i != 42) {
            switch (i) {
                case 49:
                case 50:
                case 51:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    protected ClassDescriptor classForInnerClassRecord() {
        return null;
    }

    /* JADX INFO: renamed from: generateBody */
    protected abstract void mo6419generateBody();

    /* JADX INFO: renamed from: generateDeclaration */
    protected abstract void mo6420generateDeclaration();

    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected abstract void mo6421generateKotlinMetadataAnnotation();

    protected void generateSyntheticPartsAfterBody() {
    }

    protected void generateSyntheticPartsBeforeBody() {
    }

    public MemberCodegen(GenerationState generationState, MemberCodegen<?> memberCodegen, FieldOwnerContext fieldOwnerContext, T t, ClassBuilder classBuilder) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        if (fieldOwnerContext == null) {
            $$$reportNull$$$0(1);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(2);
        }
        this.reifiedTypeParametersUsages = new ReifiedTypeParametersUsages();
        this.innerClasses = new LinkedHashSet();
        this.syntheticInnerClasses = new LinkedHashSet();
        this.nopSeparatorNeeded = false;
        this.BY_COMPANION_FQN = new Comparator() { // from class: org.jetbrains.kotlin.codegen.MemberCodegen$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DescriptorUtils.getFqName(((AccessorForCompanionObjectInstanceFieldDescriptor) obj).getCompanionObjectDescriptor()).asString().compareTo(DescriptorUtils.getFqName(((AccessorForCompanionObjectInstanceFieldDescriptor) obj2).getCompanionObjectDescriptor()).asString());
            }
        };
        this.state = generationState;
        this.typeMapper = generationState.getTypeMapper();
        this.bindingContext = generationState.getBindingContext();
        this.element = t;
        this.context = fieldOwnerContext;
        this.v = classBuilder;
        FunctionCodegen functionCodegen = new FunctionCodegen(fieldOwnerContext, classBuilder, generationState, this);
        this.functionCodegen = functionCodegen;
        this.propertyCodegen = new PropertyCodegen(fieldOwnerContext, classBuilder, functionCodegen, this);
        this.parentCodegen = memberCodegen;
        this.jvmAssertFieldGenerated = false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected MemberCodegen(MemberCodegen<T> memberCodegen, T t, FieldOwnerContext fieldOwnerContext) {
        this(memberCodegen.state, memberCodegen.getParentCodegen(), fieldOwnerContext, t, memberCodegen.v);
        if (memberCodegen == null) {
            $$$reportNull$$$0(3);
        }
    }

    public void generate() {
        mo6420generateDeclaration();
        boolean z = !(this.element instanceof KtClassOrObject) || this.state.getGenerateDeclaredClassFilter().shouldGenerateClassMembers((KtClassOrObject) this.element);
        if (z) {
            generateSyntheticPartsBeforeBody();
        }
        mo6419generateBody();
        if (z) {
            generateSyntheticPartsAfterBody();
        }
        if (this.state.getClassBuilderMode().generateMetadata) {
            mo6421generateKotlinMetadataAnnotation();
        }
        done();
    }

    public static void markLineNumberForDescriptor(ClassDescriptor classDescriptor, InstructionAdapter instructionAdapter) {
        PsiElement sourceFromDescriptor;
        if (instructionAdapter == null) {
            $$$reportNull$$$0(4);
        }
        if (classDescriptor == null || (sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(classDescriptor)) == null) {
            return;
        }
        markLineNumberForElement(sourceFromDescriptor, instructionAdapter);
    }

    public static void markLineNumberForElement(PsiElement psiElement, InstructionAdapter instructionAdapter) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(6);
        }
        Integer lineNumberForElement = CodegenUtil.getLineNumberForElement(psiElement, false);
        if (lineNumberForElement != null) {
            Label label = new Label();
            instructionAdapter.visitLabel(label);
            instructionAdapter.visitLineNumber(lineNumberForElement.intValue(), label);
        }
    }

    protected void done() {
        SourceMapper sourceMapper;
        ExpressionCodegen expressionCodegen = this.clInit;
        if (expressionCodegen != null) {
            expressionCodegen.v.visitInsn(177);
            FunctionCodegen.endVisit(this.clInit.v, "static initializer", this.element);
        }
        writeInnerClasses();
        if (this.alwaysWriteSourceMap || ((sourceMapper = this.sourceMapper) != null && !sourceMapper.isTrivial())) {
            this.v.visitSMAP(getOrCreateSourceMapper(), !this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.CorrectSourceMappingSyntax));
        }
        this.v.done(this.state.getConfig().getGenerateSmapCopyToAnnotation());
    }

    public void genSimpleMember(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(7);
        }
        if (ktDeclaration instanceof KtNamedFunction) {
            try {
                this.functionCodegen.gen((KtNamedFunction) ktDeclaration);
                return;
            } catch (CompilationException e) {
                throw e;
            } catch (ProcessCanceledException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new CompilationException("Failed to generate function " + ktDeclaration.getName(), e3, ktDeclaration);
            }
        }
        if (ktDeclaration instanceof KtProperty) {
            try {
                this.propertyCodegen.gen((KtProperty) ktDeclaration);
                return;
            } catch (CompilationException e4) {
                throw e4;
            } catch (ProcessCanceledException e5) {
                throw e5;
            } catch (Exception e6) {
                throw new CompilationException("Failed to generate property " + ktDeclaration.getName(), e6, ktDeclaration);
            }
        }
        if (ktDeclaration instanceof KtTypeAlias) {
            genTypeAlias((KtTypeAlias) ktDeclaration);
            return;
        }
        if (ktDeclaration instanceof KtDestructuringDeclarationEntry) {
            try {
                this.propertyCodegen.genDestructuringDeclaration((KtDestructuringDeclarationEntry) ktDeclaration);
                return;
            } catch (CompilationException e7) {
                throw e7;
            } catch (ProcessCanceledException e8) {
                throw e8;
            } catch (Exception e9) {
                throw new CompilationException("Failed to generate destructuring declaration entry " + ktDeclaration.getName(), e9, ktDeclaration);
            }
        }
        throw new IllegalArgumentException("Unknown parameter: " + ktDeclaration);
    }

    private void genTypeAlias(KtTypeAlias ktTypeAlias) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(8);
        }
        if (this.state.getClassBuilderMode().generateMetadata) {
            TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) this.bindingContext.get(BindingContext.TYPE_ALIAS, ktTypeAlias);
            if (typeAliasDescriptor == null) {
                throw ExceptionLogger.logDescriptorNotFound("Type alias " + ktTypeAlias.getName() + " should have a descriptor", ktTypeAlias);
            }
            genTypeAliasAnnotationsMethodIfRequired(typeAliasDescriptor);
        }
    }

    private void genTypeAliasAnnotationsMethodIfRequired(TypeAliasDescriptor typeAliasDescriptor) {
        boolean zIsImplementationOwner = CodegenContextUtil.isImplementationOwner(this.context, typeAliasDescriptor);
        Annotations annotations = typeAliasDescriptor.getAnnotations();
        if (!zIsImplementationOwner || annotations.isEmpty()) {
            return;
        }
        generateSyntheticAnnotationsMethod(typeAliasDescriptor, new Method(JvmAbi.getSyntheticMethodNameForAnnotatedTypeAlias(typeAliasDescriptor.getShortName()), "()V"), annotations);
    }

    protected void generateSyntheticAnnotationsMethod(MemberDescriptor memberDescriptor, Method method, Annotations annotations) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (method == null) {
            $$$reportNull$$$0(10);
        }
        if (annotations == null) {
            $$$reportNull$$$0(11);
        }
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin(memberDescriptor), DescriptorAsmUtil.getVisibilityAccessFlag(memberDescriptor) | 135176, method.getName(), method.getDescriptor(), null, null);
        AnnotationCodegen.forMethod(methodVisitorNewMethod, this, this.state).genAnnotations(new AnnotatedImpl(annotations), Type.VOID_TYPE, null);
        methodVisitorNewMethod.visitCode();
        methodVisitorNewMethod.visitInsn(177);
        methodVisitorNewMethod.visitEnd();
    }

    public static void genClassOrObject(CodegenContext codegenContext, KtClassOrObject ktClassOrObject, GenerationState generationState, MemberCodegen<?> memberCodegen) {
        if (codegenContext == null) {
            $$$reportNull$$$0(12);
        }
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(13);
        }
        if (generationState == null) {
            $$$reportNull$$$0(14);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) generationState.getBindingContext().get(BindingContext.CLASS, ktClassOrObject);
        if (classDescriptor == null || ErrorUtils.isError(classDescriptor)) {
            badDescriptor(classDescriptor, generationState.getClassBuilderMode());
            return;
        }
        if (classDescriptor.getShortName().equals(SpecialNames.NO_NAME_PROVIDED)) {
            badDescriptor(classDescriptor, generationState.getClassBuilderMode());
        }
        genClassOrObject(codegenContext, ktClassOrObject, generationState, memberCodegen, classDescriptor);
    }

    private static void genClassOrObject(CodegenContext codegenContext, KtPureClassOrObject ktPureClassOrObject, GenerationState generationState, MemberCodegen<?> memberCodegen, ClassDescriptor classDescriptor) {
        if (codegenContext == null) {
            $$$reportNull$$$0(15);
        }
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(16);
        }
        if (generationState == null) {
            $$$reportNull$$$0(17);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(18);
        }
        new ImplementationBodyCodegen(ktPureClassOrObject, codegenContext.intoClass(classDescriptor, OwnerKind.IMPLEMENTATION, generationState), generationState.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOriginFromPure(ktPureClassOrObject, classDescriptor), generationState.getTypeMapper().mapClass(classDescriptor), ktPureClassOrObject.getContainingKtFile()), generationState, memberCodegen, false).generate();
    }

    public static void badDescriptor(ClassDescriptor classDescriptor, ClassBuilderMode classBuilderMode) {
        if (classBuilderMode.generateBodies) {
            throw new IllegalStateException("Generating bad descriptor in ClassBuilderMode = " + classBuilderMode + ": " + classDescriptor);
        }
    }

    public void genClassOrObject(KtClassOrObject ktClassOrObject) {
        genClassOrObject(this.context, ktClassOrObject, this.state, this);
    }

    public void genSyntheticClassOrObject(SyntheticClassOrObjectDescriptor syntheticClassOrObjectDescriptor) {
        genClassOrObject(this.context, syntheticClassOrObjectDescriptor.getSyntheticDeclaration(), this.state, this, syntheticClassOrObjectDescriptor);
    }

    public void addSyntheticAnonymousInnerClass(SyntheticInnerClassInfo syntheticInnerClassInfo) {
        this.syntheticInnerClasses.add(syntheticInnerClassInfo);
    }

    private void writeInnerClasses() {
        ClassDescriptor classDescriptorClassForInnerClassRecord = classForInnerClassRecord();
        if (classDescriptorClassForInnerClassRecord != null) {
            MemberCodegen<?> memberCodegen = this.parentCodegen;
            if (memberCodegen != null) {
                memberCodegen.innerClasses.add(classDescriptorClassForInnerClassRecord);
            }
            addParentsToInnerClassesIfNeeded(this.innerClasses);
        }
        Iterator<ClassDescriptor> it = this.innerClasses.iterator();
        while (it.hasNext()) {
            writeInnerClass(it.next());
        }
        for (SyntheticInnerClassInfo syntheticInnerClassInfo : this.syntheticInnerClasses) {
            this.v.visitInnerClass(syntheticInnerClassInfo.getInternalName(), null, null, syntheticInnerClassInfo.getFlags());
        }
    }

    protected void addParentsToInnerClassesIfNeeded(Collection<ClassDescriptor> collection) {
        if (collection == null) {
            $$$reportNull$$$0(19);
        }
        ClassDescriptor classDescriptorClassForInnerClassRecord = classForInnerClassRecord();
        if (classDescriptorClassForInnerClassRecord != null) {
            collection.add(classDescriptorClassForInnerClassRecord);
        }
        MemberCodegen<?> parentCodegen = getParentCodegen();
        if (parentCodegen != null) {
            parentCodegen.addParentsToInnerClassesIfNeeded(collection);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.jetbrains.kotlin.descriptors.ClassDescriptor] */
    /* JADX WARN: Type inference failed for: r3v1, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
    /* JADX WARN: Type inference failed for: r3v2, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
    @Override // org.jetbrains.kotlin.codegen.InnerClassConsumer
    public void addInnerClassInfoFromAnnotation(ClassDescriptor classDescriptor) {
        if (classDescriptor == 0) {
            $$$reportNull$$$0(20);
        }
        while (classDescriptor != 0 && !DescriptorUtils.isTopLevelDeclaration(classDescriptor)) {
            if (classDescriptor instanceof ClassDescriptor) {
                this.innerClasses.add((ClassDescriptor) classDescriptor);
            }
            classDescriptor = classDescriptor.getContainingDeclaration();
        }
    }

    private void writeInnerClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(21);
        }
        if (ErrorUtils.isError(classDescriptor)) {
            return;
        }
        writeInnerClass(classDescriptor, this.typeMapper, this.v);
    }

    public static void writeInnerClass(ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper, ClassBuilder classBuilder) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(22);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(23);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(24);
        }
        DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
        classBuilder.visitInnerClass(kotlinTypeMapper.classInternalName(classDescriptor), containingDeclaration instanceof ClassDescriptor ? kotlinTypeMapper.classInternalName((ClassDescriptor) containingDeclaration) : null, classDescriptor.getShortName().isSpecial() ? null : classDescriptor.getShortName().asString(), DescriptorAsmUtil.calculateInnerClassAccessFlags(classDescriptor));
    }

    protected void writeOuterClassAndEnclosingMethod() {
        CodegenContext<?> nonInlineOuterContext = getNonInlineOuterContext(this.context.getParentContext());
        Type typeComputeOuterClass = computeOuterClass(this.typeMapper, this.state.getJvmDefaultMode(), this.element, nonInlineOuterContext);
        if (typeComputeOuterClass != null) {
            Method methodComputeEnclosingMethod = computeEnclosingMethod(this.typeMapper, nonInlineOuterContext);
            this.v.visitOuterClass(typeComputeOuterClass.getInternalName(), methodComputeEnclosingMethod == null ? null : methodComputeEnclosingMethod.getName(), methodComputeEnclosingMethod != null ? methodComputeEnclosingMethod.getDescriptor() : null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static CodegenContext<?> getNonInlineOuterContext(CodegenContext<?> codegenContext) {
        while (codegenContext instanceof InlineLambdaContext) {
            codegenContext = codegenContext.getParentContext().getParentContext();
        }
        return codegenContext;
    }

    public static Type computeOuterClass(KotlinTypeMapper kotlinTypeMapper, JvmDefaultMode jvmDefaultMode, KtPureElement ktPureElement, CodegenContext<?> codegenContext) {
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(25);
        }
        if (jvmDefaultMode == null) {
            $$$reportNull$$$0(26);
        }
        if (ktPureElement == null) {
            $$$reportNull$$$0(27);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(28);
        }
        CodegenContext<? extends ClassOrPackageFragmentDescriptor> classOrPackageParentContext = codegenContext.getClassOrPackageParentContext();
        if (classOrPackageParentContext instanceof ClassContext) {
            ClassDescriptor classDescriptor = (ClassDescriptor) ((ClassContext) classOrPackageParentContext).getContextDescriptor();
            if (codegenContext instanceof MethodContext) {
                FunctionDescriptor functionDescriptor = ((MethodContext) codegenContext).getFunctionDescriptor();
                if (DescriptorUtils.isInterface(functionDescriptor.getContainingDeclaration()) && !JvmAnnotationUtilKt.isCompiledToJvmDefault(functionDescriptor, jvmDefaultMode)) {
                    return kotlinTypeMapper.mapDefaultImpls(classDescriptor);
                }
            }
            return kotlinTypeMapper.mapClass(classDescriptor);
        }
        if (!(classOrPackageParentContext instanceof MultifileClassFacadeContext) && !(classOrPackageParentContext instanceof DelegatingToPartContext)) {
            return null;
        }
        Type implementationOwnerClassType = CodegenContextUtil.getImplementationOwnerClassType(classOrPackageParentContext);
        return implementationOwnerClassType != null ? implementationOwnerClassType : Type.getObjectType(JvmFileClassUtil.getFileClassInternalName(ktPureElement.getContainingKtFile()));
    }

    public static Method computeEnclosingMethod(KotlinTypeMapper kotlinTypeMapper, CodegenContext codegenContext) {
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(29);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(30);
        }
        if (!(codegenContext instanceof MethodContext)) {
            return null;
        }
        MethodContext methodContext = (MethodContext) codegenContext;
        FunctionDescriptor functionDescriptor = methodContext.getFunctionDescriptor();
        if (MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME.equals(functionDescriptor.getShortName().asString())) {
            return null;
        }
        if (methodContext.isDefaultFunctionContext()) {
            return kotlinTypeMapper.mapDefaultMethod(functionDescriptor, codegenContext.getContextKind());
        }
        return kotlinTypeMapper.mapAsmMethod(functionDescriptor, codegenContext.getContextKind());
    }

    public NameGenerator getInlineNameGenerator() {
        if (this.inlineNameGenerator == null) {
            this.inlineNameGenerator = new NameGenerator(InlineCodegenUtilsKt.getInlineName(this.context, this.typeMapper));
        }
        NameGenerator nameGenerator = this.inlineNameGenerator;
        if (nameGenerator == null) {
            $$$reportNull$$$0(31);
        }
        return nameGenerator;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
    public final ExpressionCodegen createOrGetClInitCodegen() {
        if (this.clInit == null) {
            ?? contextDescriptor = this.context.getContextDescriptor();
            this.clInit = new ExpressionCodegen(this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin((DeclarationDescriptor) contextDescriptor), 8, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, "()V", null, null), new FrameMap(), Type.VOID_TYPE, this.context.intoFunction(createClInitFunctionDescriptor(contextDescriptor)), this.state, this);
        }
        ExpressionCodegen expressionCodegen = this.clInit;
        if (expressionCodegen == null) {
            $$$reportNull$$$0(32);
        }
        return expressionCodegen;
    }

    private SimpleFunctionDescriptorImpl createClInitFunctionDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(declarationDescriptor, Annotations.INSTANCE.getEMPTY(), Name.special(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME), CallableMemberDescriptor.Kind.SYNTHESIZED, KotlinSourceElementKt.toSourceElement(this.element));
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), (KotlinType) DescriptorUtilsKt.getModule(declarationDescriptor).getBuiltIns().getUnitType(), Modality.FINAL, DescriptorVisibilities.PRIVATE);
        if (simpleFunctionDescriptorImplCreate == null) {
            $$$reportNull$$$0(34);
        }
        return simpleFunctionDescriptorImplCreate;
    }

    private void generateNopSeparatorIfNeeded(NotNullLazyValue<ExpressionCodegen> notNullLazyValue) {
        if (this.nopSeparatorNeeded) {
            this.nopSeparatorNeeded = false;
            notNullLazyValue.invoke().v.nop();
        }
    }

    protected void generateInitializers(Function0<ExpressionCodegen> function0) {
        KtExpression body;
        if (function0 == null) {
            $$$reportNull$$$0(35);
        }
        NotNullLazyValue<ExpressionCodegen> notNullLazyValueCreateLazyValue = LockBasedStorageManager.NO_LOCKS.createLazyValue(function0);
        List<KtDeclaration> declarations = ((KtDeclarationContainer) this.element).getDeclarations();
        for (int i = 0; i < declarations.size(); i++) {
            KtDeclaration ktDeclaration = declarations.get(i);
            if (ktDeclaration instanceof KtProperty) {
                KtProperty ktProperty = (KtProperty) ktDeclaration;
                if (shouldInitializeProperty(ktProperty)) {
                    generateNopSeparatorIfNeeded(notNullLazyValueCreateLazyValue);
                    initializeProperty(notNullLazyValueCreateLazyValue.invoke(), ktProperty);
                }
            } else if (ktDeclaration instanceof KtDestructuringDeclaration) {
                generateNopSeparatorIfNeeded(notNullLazyValueCreateLazyValue);
                notNullLazyValueCreateLazyValue.invoke().initializeDestructuringDeclaration((KtDestructuringDeclaration) ktDeclaration, true);
            } else if ((ktDeclaration instanceof KtAnonymousInitializer) && (body = ((KtAnonymousInitializer) ktDeclaration).getBody()) != null) {
                generateNopSeparatorIfNeeded(notNullLazyValueCreateLazyValue);
                ExpressionCodegen expressionCodegenInvoke = notNullLazyValueCreateLazyValue.invoke();
                Type typeExpressionType = Type.VOID_TYPE;
                if (i == declarations.size() - 1 && (this instanceof ScriptCodegen)) {
                    typeExpressionType = expressionCodegenInvoke.expressionType(body);
                }
                boolean z = ktDeclaration instanceof KtClassInitializer;
                if (z) {
                    expressionCodegenInvoke.markLineNumber(((KtClassInitializer) ktDeclaration).getInitKeyword(), true);
                    expressionCodegenInvoke.v.nop();
                }
                expressionCodegenInvoke.gen(body, typeExpressionType);
                if (z) {
                    expressionCodegenInvoke.markLineNumber(ktDeclaration, true);
                    this.nopSeparatorNeeded = true;
                }
            }
        }
    }

    public void initializeProperty(ExpressionCodegen expressionCodegen, KtProperty ktProperty) {
        if (expressionCodegen == null) {
            $$$reportNull$$$0(36);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(37);
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) this.bindingContext.get(BindingContext.VARIABLE, ktProperty);
        KtExpression delegateExpressionOrInitializer = ktProperty.getDelegateExpressionOrInitializer();
        StackValue.Property propertyIntermediateValueForProperty = expressionCodegen.intermediateValueForProperty(propertyDescriptor, true, false, null, true, StackValue.LOCAL_0, null, false);
        if (ktProperty.getDelegateExpression() == null) {
            propertyIntermediateValueForProperty.store(expressionCodegen.gen(delegateExpressionOrInitializer), expressionCodegen.v);
            return;
        }
        StackValue.Property delegateOrNull = propertyIntermediateValueForProperty.getDelegateOrNull();
        ResolvedCall resolvedCall = (ResolvedCall) this.bindingContext.get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, propertyDescriptor);
        if (resolvedCall == null) {
            delegateOrNull.store(expressionCodegen.gen(delegateExpressionOrInitializer), expressionCodegen.v);
        } else {
            delegateOrNull.store(PropertyCodegen.invokeDelegatedPropertyConventionMethod(expressionCodegen, resolvedCall, expressionCodegen.gen(delegateExpressionOrInitializer), propertyDescriptor), expressionCodegen.v);
        }
    }

    public boolean shouldInitializeProperty(KtProperty ktProperty) {
        if (ktProperty == null) {
            $$$reportNull$$$0(38);
        }
        if (!ktProperty.hasDelegateExpressionOrInitializer()) {
            return false;
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) this.bindingContext.get(BindingContext.VARIABLE, ktProperty);
        if (propertyDescriptor.isConst()) {
            return false;
        }
        KtExpression initializer = ktProperty.getInitializer();
        ConstantValue<?> compileTimeConstant = initializer != null ? ExpressionCodegen.getCompileTimeConstant(initializer, this.bindingContext, this.state.getConfig().getShouldInlineConstVals()) : null;
        if (compileTimeConstant == null) {
            return this.state.getClassBuilderMode().generateBodies;
        }
        return !skipDefaultValue(propertyDescriptor, compileTimeConstant.getValue(), this.typeMapper.mapType(getPropertyOrDelegateType(ktProperty, propertyDescriptor)));
    }

    private KotlinType getPropertyOrDelegateType(KtProperty ktProperty, PropertyDescriptor propertyDescriptor) {
        if (ktProperty == null) {
            $$$reportNull$$$0(39);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(40);
        }
        KtExpression delegateExpression = ktProperty.getDelegateExpression();
        if (delegateExpression != null) {
            KotlinType type = this.bindingContext.getType(delegateExpression);
            if (type == null) {
                $$$reportNull$$$0(41);
            }
            return type;
        }
        KotlinType type2 = propertyDescriptor.getType();
        if (type2 == null) {
            $$$reportNull$$$0(42);
        }
        return type2;
    }

    private static boolean skipDefaultValue(PropertyDescriptor propertyDescriptor, Object obj, Type type) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(43);
        }
        if (type == null) {
            $$$reportNull$$$0(44);
        }
        if (!AsmUtil.isPrimitive(type)) {
            return obj == null;
        }
        if (!propertyDescriptor.getType().getIsMarkedNullable() && (obj instanceof Number)) {
            if (type == Type.INT_TYPE && ((Number) obj).intValue() == 0) {
                return true;
            }
            if (type == Type.BYTE_TYPE && ((Number) obj).byteValue() == 0) {
                return true;
            }
            if (type == Type.LONG_TYPE && ((Number) obj).longValue() == 0) {
                return true;
            }
            if (type == Type.SHORT_TYPE && ((Number) obj).shortValue() == 0) {
                return true;
            }
            if (type == Type.DOUBLE_TYPE && obj.equals(Double.valueOf(0.0d))) {
                return true;
            }
            if (type == Type.FLOAT_TYPE && obj.equals(Float.valueOf(0.0f))) {
                return true;
            }
        }
        if (type == Type.BOOLEAN_TYPE && (obj instanceof Boolean) && !((Boolean) obj).booleanValue()) {
            return true;
        }
        return type == Type.CHAR_TYPE && (obj instanceof Character) && ((Character) obj).charValue() == 0;
    }

    /* JADX WARN: Type inference failed for: r1v14, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
    protected void generatePropertyMetadataArrayFieldIfNeeded(Type type) {
        if (type == null) {
            $$$reportNull$$$0(45);
        }
        List list = (List) this.bindingContext.get(CodegenBinding.DELEGATED_PROPERTIES_WITH_METADATA, type);
        if (list == null || list.isEmpty()) {
            return;
        }
        this.v.newField(JvmDeclarationOrigin.NO_ORIGIN, ((this.context.getContextKind() == OwnerKind.DEFAULT_IMPLS || !DescriptorUtils.isInterface(this.context.getContextDescriptor())) ? 0 : 1) | 4120, JvmAbi.DELEGATED_PROPERTIES_ARRAY_NAME, "[" + AsmTypes.K_PROPERTY_TYPE, null, null);
        if (this.state.getClassBuilderMode().generateBodies) {
            InstructionAdapter instructionAdapter = createOrGetClInitCodegen().v;
            instructionAdapter.iconst(list.size());
            instructionAdapter.newarray(AsmTypes.K_PROPERTY_TYPE);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                instructionAdapter.dup();
                instructionAdapter.iconst(i);
                generatePropertyReference(instructionAdapter, (VariableDescriptorWithAccessors) list.get(i), this.state);
                instructionAdapter.astore(AsmTypes.K_PROPERTY_TYPE);
            }
            instructionAdapter.putstatic(type.getInternalName(), JvmAbi.DELEGATED_PROPERTIES_ARRAY_NAME, "[" + AsmTypes.K_PROPERTY_TYPE);
        }
    }

    public static void generatePropertyReference(InstructionAdapter instructionAdapter, VariableDescriptorWithAccessors variableDescriptorWithAccessors, GenerationState generationState) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(46);
        }
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(47);
        }
        if (generationState == null) {
            $$$reportNull$$$0(48);
        }
        int i = (variableDescriptorWithAccessors.mo6753getDispatchReceiverParameter() != null ? 1 : 0) + (variableDescriptorWithAccessors.getExtensionReceiverParameter() == null ? 0 : 1);
        Type type = variableDescriptorWithAccessors.isVar() ? AsmTypes.MUTABLE_PROPERTY_REFERENCE_IMPL[i] : AsmTypes.PROPERTY_REFERENCE_IMPL[i];
        instructionAdapter.anew(type);
        instructionAdapter.dup();
        ArrayList arrayList = new ArrayList();
        if (generationState.getConfig().getGenerateOptimizedCallableReferenceSuperClasses()) {
            CallableReferenceUtilKt.generateCallableReferenceDeclarationContainerClass(instructionAdapter, variableDescriptorWithAccessors, generationState);
            arrayList.add(AsmTypes.JAVA_CLASS_TYPE);
        } else {
            CallableReferenceUtilKt.generateCallableReferenceDeclarationContainer(instructionAdapter, variableDescriptorWithAccessors, generationState);
            arrayList.add(AsmTypes.K_DECLARATION_CONTAINER_TYPE);
        }
        instructionAdapter.aconst(variableDescriptorWithAccessors.getShortName().asString());
        CallableReferenceUtilKt.generatePropertyReferenceSignature(instructionAdapter, variableDescriptorWithAccessors, generationState);
        arrayList.add(AsmTypes.JAVA_STRING_TYPE);
        arrayList.add(AsmTypes.JAVA_STRING_TYPE);
        if (generationState.getConfig().getGenerateOptimizedCallableReferenceSuperClasses()) {
            instructionAdapter.aconst(Integer.valueOf(CallableReferenceUtilKt.getCallableReferenceTopLevelFlag(variableDescriptorWithAccessors)));
            arrayList.add(Type.INT_TYPE);
        }
        instructionAdapter.invokespecial(type.getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, (Type[]) arrayList.toArray(new Type[0])), false);
        Method wrapperMethodForPropertyReference = PropertyReferenceCodegen.getWrapperMethodForPropertyReference(variableDescriptorWithAccessors, i);
        instructionAdapter.invokestatic(AsmTypes.REFLECTION, wrapperMethodForPropertyReference.getName(), wrapperMethodForPropertyReference.getDescriptor(), false);
        StackValue.onStack(type).put(AsmTypes.K_PROPERTY_TYPE, instructionAdapter);
    }

    public String getClassName() {
        return this.v.getThisName();
    }

    public FieldOwnerContext<?> getContext() {
        FieldOwnerContext<?> fieldOwnerContext = this.context;
        if (fieldOwnerContext == null) {
            $$$reportNull$$$0(49);
        }
        return fieldOwnerContext;
    }

    public ReifiedTypeParametersUsages getReifiedTypeParametersUsages() {
        ReifiedTypeParametersUsages reifiedTypeParametersUsages = this.reifiedTypeParametersUsages;
        if (reifiedTypeParametersUsages == null) {
            $$$reportNull$$$0(50);
        }
        return reifiedTypeParametersUsages;
    }

    public MemberCodegen<?> getParentCodegen() {
        return this.parentCodegen;
    }

    public String toString() {
        return this.context.toString();
    }

    public SourceMapper getOrCreateSourceMapper() {
        if (this.sourceMapper == null) {
            this.sourceMapper = new SourceMapper(SourceInfo.INSTANCE.createFromPsi((KtElement) this.element, getClassName()));
        }
        SourceMapper sourceMapper = this.sourceMapper;
        if (sourceMapper == null) {
            $$$reportNull$$$0(51);
        }
        return sourceMapper;
    }

    protected void initDefaultSourceMappingIfNeeded() {
        if (this.state.getConfig().getIsInlineDisabled()) {
            return;
        }
        for (CodegenContext parentContext = this.context.getParentContext(); parentContext != null; parentContext = parentContext.getParentContext()) {
            if (parentContext.isInlineMethodContext()) {
                this.alwaysWriteSourceMap = true;
                return;
            }
        }
    }

    protected void generateConstInstance(Type type, Type type2) {
        if (type == null) {
            $$$reportNull$$$0(52);
        }
        if (type2 == null) {
            $$$reportNull$$$0(53);
        }
        this.v.newField(JvmDeclarationOriginKt.OtherOriginFromPure(this.element), 25, JvmAbi.INSTANCE_FIELD, type2.getDescriptor(), null, null);
        if (this.state.getClassBuilderMode().generateBodies) {
            InstructionAdapter instructionAdapter = createOrGetClInitCodegen().v;
            instructionAdapter.anew(type);
            instructionAdapter.dup();
            instructionAdapter.invokespecial(type.getInternalName(), "<init>", "()V", false);
            instructionAdapter.putstatic(type.getInternalName(), JvmAbi.INSTANCE_FIELD, type2.getDescriptor());
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
    protected final void generateSyntheticAccessors() {
        for (AccessorForCallableDescriptor<?> accessorForCallableDescriptor : this.context.getAccessors()) {
            boolean zIsCompiledToJvmDefault = JvmAnnotationUtilKt.isCompiledToJvmDefault(accessorForCallableDescriptor.getCalleeDescriptor(), this.state.getJvmDefaultMode());
            OwnerKind contextKind = this.context.getContextKind();
            if (!DescriptorUtils.isInterface(this.context.getContextDescriptor()) || ((zIsCompiledToJvmDefault && contextKind == OwnerKind.IMPLEMENTATION) || (!zIsCompiledToJvmDefault && contextKind == OwnerKind.DEFAULT_IMPLS))) {
                generateSyntheticAccessor(accessorForCallableDescriptor);
            }
        }
        Collection<AccessorForCompanionObjectInstanceFieldDescriptor> requiredAccessorsForCompanionObjects = this.context.getRequiredAccessorsForCompanionObjects();
        if (requiredAccessorsForCompanionObjects.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(requiredAccessorsForCompanionObjects);
        arrayList.sort(this.BY_COMPANION_FQN);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            generateSyntheticAccessorForCompanionObject((AccessorForCompanionObjectInstanceFieldDescriptor) it.next());
        }
    }

    private void generateSyntheticAccessorForCompanionObject(AccessorForCompanionObjectInstanceFieldDescriptor accessorForCompanionObjectInstanceFieldDescriptor) {
        if (accessorForCompanionObjectInstanceFieldDescriptor == null) {
            $$$reportNull$$$0(54);
        }
        final ClassDescriptor companionObjectDescriptor = accessorForCompanionObjectInstanceFieldDescriptor.getCompanionObjectDescriptor();
        companionObjectDescriptor.getContainingDeclaration();
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.Synthetic(null, companionObjectDescriptor), accessorForCompanionObjectInstanceFieldDescriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.MemberCodegen.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "codegen";
                } else {
                    objArr[0] = "signature";
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/MemberCodegen$1";
                objArr[2] = "doGenerateBody";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
                if (expressionCodegen == null) {
                    $$$reportNull$$$0(0);
                }
                if (jvmMethodSignature == null) {
                    $$$reportNull$$$0(1);
                }
                Type typeMapClass = MemberCodegen.this.typeMapper.mapClass(companionObjectDescriptor);
                StackValue.singleton(companionObjectDescriptor, MemberCodegen.this.typeMapper).put(typeMapClass, expressionCodegen.v);
                expressionCodegen.v.areturn(typeMapClass);
            }
        });
    }

    private void generateSyntheticAccessor(final AccessorForCallableDescriptor<?> accessorForCallableDescriptor) {
        if (accessorForCallableDescriptor == null) {
            $$$reportNull$$$0(55);
        }
        if (accessorForCallableDescriptor instanceof FunctionDescriptor) {
            final FunctionDescriptor functionDescriptor = (FunctionDescriptor) accessorForCallableDescriptor;
            final FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) accessorForCallableDescriptor.getCalleeDescriptor();
            this.functionCodegen.generateMethod(JvmDeclarationOriginKt.Synthetic(null, functionDescriptor2), functionDescriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.MemberCodegen.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = "codegen";
                    } else {
                        objArr[0] = "signature";
                    }
                    objArr[1] = "org/jetbrains/kotlin/codegen/MemberCodegen$2";
                    objArr[2] = "doGenerateBody";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased, org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
                public boolean skipNotNullAssertionsForParameters() {
                    return true;
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
                public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
                    if (expressionCodegen == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (jvmMethodSignature == null) {
                        $$$reportNull$$$0(1);
                    }
                    MemberCodegen.markLineNumberForElement(MemberCodegen.this.element.getPsiOrParent(), expressionCodegen.v);
                    if (accessorForCallableDescriptor.getAccessorKind() == AccessorKind.JVM_DEFAULT_COMPATIBILITY) {
                        FunctionDescriptor original = ((FunctionDescriptor) DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(functionDescriptor2)).getOriginal();
                        FunctionDescriptor functionDescriptor3 = functionDescriptor2;
                        if (original != functionDescriptor3) {
                            original = original.copy(functionDescriptor3.getContainingDeclaration(), original.getModality(), original.getVisibility(), original.getKind(), false);
                        }
                        MemberCodegen.this.generateMethodCallTo(original, functionDescriptor, expressionCodegen.v).coerceTo(jvmMethodSignature.getReturnType(), null, expressionCodegen.v);
                    } else {
                        MemberCodegen.this.generateMethodCallTo(functionDescriptor2, functionDescriptor, expressionCodegen.v).coerceTo(jvmMethodSignature.getReturnType(), null, expressionCodegen.v);
                    }
                    expressionCodegen.v.areturn(jvmMethodSignature.getReturnType());
                }
            });
        } else {
            if (accessorForCallableDescriptor instanceof AccessorForPropertyDescriptor) {
                AccessorForPropertyDescriptor accessorForPropertyDescriptor = (AccessorForPropertyDescriptor) accessorForCallableDescriptor;
                PropertyDescriptor calleeDescriptor = accessorForPropertyDescriptor.getCalleeDescriptor();
                if (accessorForPropertyDescriptor.getIsWithSyntheticGetterAccessor()) {
                    PropertyGetterDescriptorImpl getter = accessorForPropertyDescriptor.getGetter();
                    this.functionCodegen.generateMethod(JvmDeclarationOriginKt.Synthetic(null, calleeDescriptor.getGetter() != null ? calleeDescriptor.getGetter() : calleeDescriptor), getter, new FunctionGenerationStrategy.CodegenBased(this, getter, accessorForCallableDescriptor, calleeDescriptor, accessorForPropertyDescriptor) { // from class: org.jetbrains.kotlin.codegen.MemberCodegen.1PropertyAccessorStrategy
                        private final PropertyAccessorDescriptor callableDescriptor;
                        final /* synthetic */ MemberCodegen this$0;
                        final /* synthetic */ AccessorForPropertyDescriptor val$accessor;
                        final /* synthetic */ AccessorForCallableDescriptor val$accessorForCallableDescriptor;
                        final /* synthetic */ PropertyDescriptor val$original;

                        private static /* synthetic */ void $$$reportNull$$$0(int i) {
                            Object[] objArr = new Object[3];
                            if (i == 1) {
                                objArr[0] = "codegen";
                            } else if (i != 2) {
                                objArr[0] = "callableDescriptor";
                            } else {
                                objArr[0] = "signature";
                            }
                            objArr[1] = "org/jetbrains/kotlin/codegen/MemberCodegen$1PropertyAccessorStrategy";
                            if (i == 1 || i == 2) {
                                objArr[2] = "doGenerateBody";
                            } else {
                                objArr[2] = "<init>";
                            }
                            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                        }

                        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased, org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
                        public boolean skipNotNullAssertionsForParameters() {
                            return true;
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(this.state);
                            if (getter == null) {
                                $$$reportNull$$$0(0);
                            }
                            this.this$0 = this;
                            this.val$accessorForCallableDescriptor = accessorForCallableDescriptor;
                            this.val$original = calleeDescriptor;
                            this.val$accessor = accessorForPropertyDescriptor;
                            this.callableDescriptor = getter;
                        }

                        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
                        public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
                            if (expressionCodegen == null) {
                                $$$reportNull$$$0(1);
                            }
                            if (jvmMethodSignature == null) {
                                $$$reportNull$$$0(2);
                            }
                            if (this.val$accessorForCallableDescriptor.getAccessorKind() == AccessorKind.JVM_DEFAULT_COMPATIBILITY) {
                                MemberCodegen.markLineNumberForElement(this.this$0.element.getPsiOrParent(), expressionCodegen.v);
                                PropertyDescriptor original = ((PropertyDescriptor) DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(this.val$original)).getOriginal();
                                PropertyDescriptor propertyDescriptor = this.val$original;
                                if (original != propertyDescriptor) {
                                    original = (PropertyDescriptor) original.copy(propertyDescriptor.getContainingDeclaration(), original.getModality(), original.getVisibility(), original.getKind(), false);
                                }
                                boolean z = this.callableDescriptor instanceof PropertyGetterDescriptor;
                                this.this$0.generateMethodCallTo(z ? original.getGetter() : original.getSetter(), z ? this.val$accessor.getGetter() : this.val$accessor.getSetter(), expressionCodegen.v).coerceTo(jvmMethodSignature.getReturnType(), null, expressionCodegen.v);
                                expressionCodegen.v.areturn(jvmMethodSignature.getReturnType());
                                return;
                            }
                            AccessorForPropertyDescriptor accessorForPropertyDescriptor2 = this.val$accessor;
                            AccessorKind accessorKind = accessorForPropertyDescriptor2 instanceof AccessorForPropertyBackingField ? accessorForPropertyDescriptor2.getAccessorKind() : null;
                            boolean z2 = accessorKind == AccessorKind.FIELD_FROM_LOCAL;
                            boolean z3 = DescriptorsJvmAbiUtil.isPropertyWithBackingFieldInOuterClass(this.val$original) && !DescriptorUtils.isCompanionObject(this.val$accessor.getContainingDeclaration());
                            StackValue.Property propertyIntermediateValueForProperty = expressionCodegen.intermediateValueForProperty(this.val$original, z3 || z2 || this.val$original.getVisibility() == JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY, z2, this.val$accessor.getSuperCallTarget(), z3, StackValue.none(), null, accessorKind == AccessorKind.LATEINIT_INTRINSIC);
                            InstructionAdapter instructionAdapter = expressionCodegen.v;
                            MemberCodegen.markLineNumberForElement(this.this$0.element.getPsiOrParent(), instructionAdapter);
                            int size = 0;
                            for (Type type : jvmMethodSignature.getAsmMethod().getArgumentTypes()) {
                                instructionAdapter.load(size, type);
                                size += type.getSize();
                            }
                            if (this.callableDescriptor instanceof PropertyGetterDescriptor) {
                                propertyIntermediateValueForProperty.put(jvmMethodSignature.getReturnType(), instructionAdapter);
                            } else {
                                propertyIntermediateValueForProperty.store(StackValue.onStack(propertyIntermediateValueForProperty.type, propertyIntermediateValueForProperty.kotlinType), instructionAdapter, true);
                            }
                            instructionAdapter.areturn(jvmMethodSignature.getReturnType());
                        }
                    });
                }
                if (accessorForPropertyDescriptor.isVar() && accessorForPropertyDescriptor.getIsWithSyntheticSetterAccessor() && !isProhibitedAccessorToPrivatePropertySetter(calleeDescriptor)) {
                    PropertySetterDescriptor setter = accessorForPropertyDescriptor.getSetter();
                    this.functionCodegen.generateMethod(JvmDeclarationOriginKt.Synthetic(null, calleeDescriptor.getSetter() != null ? calleeDescriptor.getSetter() : calleeDescriptor), setter, new FunctionGenerationStrategy.CodegenBased(this, setter, accessorForCallableDescriptor, calleeDescriptor, accessorForPropertyDescriptor) { // from class: org.jetbrains.kotlin.codegen.MemberCodegen.1PropertyAccessorStrategy
                        private final PropertyAccessorDescriptor callableDescriptor;
                        final /* synthetic */ MemberCodegen this$0;
                        final /* synthetic */ AccessorForPropertyDescriptor val$accessor;
                        final /* synthetic */ AccessorForCallableDescriptor val$accessorForCallableDescriptor;
                        final /* synthetic */ PropertyDescriptor val$original;

                        private static /* synthetic */ void $$$reportNull$$$0(int i) {
                            Object[] objArr = new Object[3];
                            if (i == 1) {
                                objArr[0] = "codegen";
                            } else if (i != 2) {
                                objArr[0] = "callableDescriptor";
                            } else {
                                objArr[0] = "signature";
                            }
                            objArr[1] = "org/jetbrains/kotlin/codegen/MemberCodegen$1PropertyAccessorStrategy";
                            if (i == 1 || i == 2) {
                                objArr[2] = "doGenerateBody";
                            } else {
                                objArr[2] = "<init>";
                            }
                            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                        }

                        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased, org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
                        public boolean skipNotNullAssertionsForParameters() {
                            return true;
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(this.state);
                            if (setter == null) {
                                $$$reportNull$$$0(0);
                            }
                            this.this$0 = this;
                            this.val$accessorForCallableDescriptor = accessorForCallableDescriptor;
                            this.val$original = calleeDescriptor;
                            this.val$accessor = accessorForPropertyDescriptor;
                            this.callableDescriptor = setter;
                        }

                        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
                        public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
                            if (expressionCodegen == null) {
                                $$$reportNull$$$0(1);
                            }
                            if (jvmMethodSignature == null) {
                                $$$reportNull$$$0(2);
                            }
                            if (this.val$accessorForCallableDescriptor.getAccessorKind() == AccessorKind.JVM_DEFAULT_COMPATIBILITY) {
                                MemberCodegen.markLineNumberForElement(this.this$0.element.getPsiOrParent(), expressionCodegen.v);
                                PropertyDescriptor original = ((PropertyDescriptor) DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(this.val$original)).getOriginal();
                                PropertyDescriptor propertyDescriptor = this.val$original;
                                if (original != propertyDescriptor) {
                                    original = (PropertyDescriptor) original.copy(propertyDescriptor.getContainingDeclaration(), original.getModality(), original.getVisibility(), original.getKind(), false);
                                }
                                boolean z = this.callableDescriptor instanceof PropertyGetterDescriptor;
                                this.this$0.generateMethodCallTo(z ? original.getGetter() : original.getSetter(), z ? this.val$accessor.getGetter() : this.val$accessor.getSetter(), expressionCodegen.v).coerceTo(jvmMethodSignature.getReturnType(), null, expressionCodegen.v);
                                expressionCodegen.v.areturn(jvmMethodSignature.getReturnType());
                                return;
                            }
                            AccessorForPropertyDescriptor accessorForPropertyDescriptor2 = this.val$accessor;
                            AccessorKind accessorKind = accessorForPropertyDescriptor2 instanceof AccessorForPropertyBackingField ? accessorForPropertyDescriptor2.getAccessorKind() : null;
                            boolean z2 = accessorKind == AccessorKind.FIELD_FROM_LOCAL;
                            boolean z3 = DescriptorsJvmAbiUtil.isPropertyWithBackingFieldInOuterClass(this.val$original) && !DescriptorUtils.isCompanionObject(this.val$accessor.getContainingDeclaration());
                            StackValue.Property propertyIntermediateValueForProperty = expressionCodegen.intermediateValueForProperty(this.val$original, z3 || z2 || this.val$original.getVisibility() == JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY, z2, this.val$accessor.getSuperCallTarget(), z3, StackValue.none(), null, accessorKind == AccessorKind.LATEINIT_INTRINSIC);
                            InstructionAdapter instructionAdapter = expressionCodegen.v;
                            MemberCodegen.markLineNumberForElement(this.this$0.element.getPsiOrParent(), instructionAdapter);
                            int size = 0;
                            for (Type type : jvmMethodSignature.getAsmMethod().getArgumentTypes()) {
                                instructionAdapter.load(size, type);
                                size += type.getSize();
                            }
                            if (this.callableDescriptor instanceof PropertyGetterDescriptor) {
                                propertyIntermediateValueForProperty.put(jvmMethodSignature.getReturnType(), instructionAdapter);
                            } else {
                                propertyIntermediateValueForProperty.store(StackValue.onStack(propertyIntermediateValueForProperty.type, propertyIntermediateValueForProperty.kotlinType), instructionAdapter, true);
                            }
                            instructionAdapter.areturn(jvmMethodSignature.getReturnType());
                        }
                    });
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    private boolean isProhibitedAccessorToPrivatePropertySetter(PropertyDescriptor propertyDescriptor) {
        DeclarationDescriptor contextDescriptor;
        PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) DescriptorUtils.unwrapFakeOverride(propertyDescriptor);
        PropertySetterDescriptor setter = propertyDescriptor2.getSetter();
        return (setter == null || !DescriptorVisibilities.isPrivate(setter.getVisibility()) || (contextDescriptor = this.context.getContextDescriptor()) == propertyDescriptor2.getContainingDeclaration() || contextDescriptor == DescriptorUtils.getContainingClass(propertyDescriptor2)) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0029  */
    protected StackValue generateMethodCallTo(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, InstructionAdapter instructionAdapter) {
        boolean z;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(56);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(57);
        }
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        if (functionDescriptor2 instanceof AccessorForCallableDescriptor) {
            AccessorForCallableDescriptor accessorForCallableDescriptor = (AccessorForCallableDescriptor) functionDescriptor2;
            if (accessorForCallableDescriptor.getSuperCallTarget() != null || accessorForCallableDescriptor.getAccessorKind() == AccessorKind.JVM_DEFAULT_COMPATIBILITY) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        CallableMethod callableMethodMapToCallableMethod = kotlinTypeMapper.mapToCallableMethod(functionDescriptor, z, (functionDescriptor2 == null || !TypeMapperUtilsKt.isInlineClassConstructorAccessor(functionDescriptor2)) ? null : OwnerKind.ERASED_INLINE_CLASS);
        boolean zIsJvmStaticInObjectOrClassOrInterface = CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(functionDescriptor);
        boolean z2 = (DescriptorUtils.isStaticDeclaration(functionDescriptor) || JvmCodegenUtil.isNonDefaultInterfaceMember(functionDescriptor, this.state.getJvmDefaultMode()) || zIsJvmStaticInObjectOrClassOrInterface || InlineClassesUtilsKt.isInlineClass(functionDescriptor.getContainingDeclaration())) ? false : true;
        boolean z3 = functionDescriptor2 instanceof AccessorForConstructorDescriptor;
        ReceiverParameterDescriptor dispatchReceiverParameter = functionDescriptor.mo6753getDispatchReceiverParameter();
        Type typeMapType = (dispatchReceiverParameter == null || z3) ? AsmTypes.OBJECT_TYPE : this.typeMapper.mapType(dispatchReceiverParameter.getType());
        int i = (!z2 || z3) ? 0 : 1;
        int size = z2 ? typeMapType.getSize() : 0;
        if (!z3 && (functionDescriptor instanceof ConstructorDescriptor)) {
            instructionAdapter.anew(callableMethodMapToCallableMethod.getOwner());
            instructionAdapter.dup();
            i = 0;
            size = 0;
        } else if (KotlinTypeMapper.isAccessor(functionDescriptor2) && ((z2 || z3) && !zIsJvmStaticInObjectOrClassOrInterface)) {
            instructionAdapter.load(0, typeMapType);
        }
        Type[] parameterTypes = callableMethodMapToCallableMethod.getParameterTypes();
        Type[] parameterTypes2 = functionDescriptor2 != null ? this.typeMapper.mapToCallableMethod(functionDescriptor2, false).getParameterTypes() : parameterTypes;
        for (Type type : parameterTypes) {
            if (AsmTypes.DEFAULT_CONSTRUCTOR_MARKER.equals(type)) {
                instructionAdapter.aconst(null);
            } else {
                Type type2 = parameterTypes2[i];
                instructionAdapter.load(size, type2);
                StackValue.coerce(type2, type, instructionAdapter);
                size += type2.getSize();
            }
            i++;
        }
        callableMethodMapToCallableMethod.genInvokeInstruction(instructionAdapter);
        return StackValue.onStack(callableMethodMapToCallableMethod.getReturnType(), functionDescriptor.getReturnType());
    }

    public void generateAssertField() {
        if (this.jvmAssertFieldGenerated) {
            return;
        }
        AssertCodegenUtilKt.generateAssertionsDisabledFieldInitialization(this.v, createOrGetClInitCodegen().v, this.v.getThisName());
        this.jvmAssertFieldGenerated = true;
    }
}
