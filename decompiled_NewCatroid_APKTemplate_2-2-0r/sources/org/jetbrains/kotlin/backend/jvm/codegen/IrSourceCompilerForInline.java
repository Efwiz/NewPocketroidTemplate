package org.jetbrains.kotlin.backend.jvm.codegen;

import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.inline.ExpressionLambda;
import org.jetbrains.kotlin.codegen.inline.InlineCallSiteInfo;
import org.jetbrains.kotlin.codegen.inline.InlineIntrinsicsKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.inline.SMAPAndMethodNode;
import org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline;
import org.jetbrains.kotlin.codegen.inline.SourceCompilerForInlineKt;
import org.jetbrains.kotlin.codegen.inline.SourceMapper;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.incremental.components.LocationInfo;
import org.jetbrains.kotlin.incremental.components.Position;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiFactoryKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: IrSourceCompilerForInline.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0006\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J2\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u000102H\u0016J\u0018\u00104\u001a\u00020&2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0016J\u0016\u00109\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u0001020:H\u0016J\b\u0010;\u001a\u00020\u001aH\u0016J\u0010\u0010<\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020\u0010H\u0016J\u0010\u0010>\u001a\u00020*2\u0006\u0010?\u001a\u00020\u0010H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001dR\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrSourceCompilerForInline;", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "callElement", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "callee", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;)V", "getCallElement", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "callElementText", "", "getCallElementText", "()Ljava/lang/String;", "getCodegen$backend_jvm_codegen", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "inlineCallSiteInfo", "Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "getInlineCallSiteInfo", "()Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "isCallInsideSameModuleAsCallee", "", "isCallInsideSameModuleAsCallee$annotations", "()V", "()Z", "isFinallyMarkerRequired", "sourceMapper", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getSourceMapper", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "compileInlineFunction", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "jvmSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "generateFinallyBlocks", "", "finallyNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "curFinallyDepth", "", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "afterReturnLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "target", "generateLambdaBody", "lambdaInfo", "Lorg/jetbrains/kotlin/codegen/inline/ExpressionLambda;", "reifiedTypeParameters", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "getContextLabels", "", "hasFinallyBlocks", "isSuspendLambdaCapturedByOuterObjectOrLambda", "name", "reportSuspensionPointInsideMonitor", "stackTraceElement", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSourceCompilerForInline implements SourceCompilerForInline {
    private final IrFunctionAccessExpression callElement;
    private final IrFunction callee;
    private final ExpressionCodegen codegen;
    private final BlockInfo data;
    private final GenerationState state;

    public static /* synthetic */ void isCallInsideSameModuleAsCallee$annotations() {
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean isSuspendLambdaCapturedByOuterObjectOrLambda(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return false;
    }

    public IrSourceCompilerForInline(GenerationState state, IrFunctionAccessExpression callElement, IrFunction callee, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        this.state = state;
        this.callElement = callElement;
        this.callee = callee;
        this.codegen = codegen;
        this.data = data;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public GenerationState getState() {
        return this.state;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public IrFunctionAccessExpression getCallElement() {
        return this.callElement;
    }

    /* JADX INFO: renamed from: getCodegen$backend_jvm_codegen, reason: from getter */
    public final ExpressionCodegen getCodegen() {
        return this.codegen;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public String getCallElementText() {
        return IrUtilsKt.ir2string(getCallElement());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public InlineCallSiteInfo getInlineCallSiteInfo() {
        Method methodMapAsmMethod;
        Integer lineNumberForElement;
        IrFunction enclosingFunctionForLocalObjects = this.codegen.getEnclosingFunctionForLocalObjects();
        String internalName = this.codegen.getClassCodegen().getType().getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        if (enclosingFunctionForLocalObjects == this.codegen.getIrFunction()) {
            methodMapAsmMethod = this.codegen.getSignature().getAsmMethod();
        } else {
            methodMapAsmMethod = this.codegen.getMethodSignatureMapper().mapAsmMethod(enclosingFunctionForLocalObjects);
        }
        Method method = methodMapAsmMethod;
        Intrinsics.checkNotNull(method);
        IrFunction irFunction = enclosingFunctionForLocalObjects;
        DescriptorVisibility inlineScopeVisibility = JvmIrInlineUtilsKt.getInlineScopeVisibility(irFunction);
        File ioFile = JvmIrUtilsKt.getIoFile(JvmIrUtilsKt.getFileParent(irFunction));
        PsiElement psiElement = JvmIrUtilsKt.getPsiElement(getCallElement());
        return new InlineCallSiteInfo(internalName, method, inlineScopeVisibility, ioFile, (psiElement == null || (lineNumberForElement = CodegenUtil.getLineNumberForElement(psiElement, false)) == null) ? 0 : lineNumberForElement.intValue());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public SourceMapper getSourceMapper() {
        return this.codegen.getSmap();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public SMAPAndMethodNode generateLambdaBody(ExpressionLambda lambdaInfo, ReifiedTypeParametersUsages reifiedTypeParameters) {
        Intrinsics.checkNotNullParameter(lambdaInfo, "lambdaInfo");
        Intrinsics.checkNotNullParameter(reifiedTypeParameters, "reifiedTypeParameters");
        if (!(lambdaInfo instanceof IrExpressionLambdaImpl)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrExpressionLambdaImpl irExpressionLambdaImpl = (IrExpressionLambdaImpl) lambdaInfo;
        for (IrTypeParameter irTypeParameter : irExpressionLambdaImpl.getFunction().getTypeParameters()) {
            if (irTypeParameter.getIsReified()) {
                String strAsString = irTypeParameter.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                reifiedTypeParameters.addUsedReifiedParameter(strAsString);
            }
        }
        return new FunctionCodegen(irExpressionLambdaImpl.getFunction(), this.codegen.getClassCodegen()).generate(reifiedTypeParameters);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public SMAPAndMethodNode compileInlineFunction(JvmMethodSignature jvmSignature) {
        KtFile ktFile;
        Intrinsics.checkNotNullParameter(jvmSignature, "jvmSignature");
        SMAPAndMethodNode sMAPAndMethodNodeGenerateInlineIntrinsicForIr = InlineIntrinsicsKt.generateInlineIntrinsicForIr(IrBasedDescriptorsKt.toIrBasedDescriptor(this.callee));
        if (sMAPAndMethodNodeGenerateInlineIntrinsicForIr != null) {
            return sMAPAndMethodNodeGenerateInlineIntrinsicForIr;
        }
        if (!Intrinsics.areEqual(jvmSignature.getAsmMethod().getName(), this.callee.getName().asString()) && (((ktFile = JvmIrUtilsKt.getKtFile(JvmIrUtilsKt.getFileParent(this.codegen.getIrFunction()))) != null && KtPsiFactoryKt.getDoNotAnalyze(ktFile) == null) || ktFile == null)) {
            GenerationState state = getState();
            FqName kotlinFqName = AdditionalIrUtilsKt.getKotlinFqName(IrUtilsKt.getParentAsClass(this.callee));
            String name2 = jvmSignature.getAsmMethod().getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            SourceCompilerForInlineKt.trackLookup(state, kotlinFqName, name2, new LocationInfo(this) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.IrSourceCompilerForInline.compileInlineFunction.2
                private final String filePath;
                final /* synthetic */ IrSourceCompilerForInline this$0;

                {
                    VirtualFile virtualFile;
                    this.this$0 = this;
                    String name3 = (this.$ktFile == null || (virtualFile = this.$ktFile.getVirtualFile()) == null || (name3 = virtualFile.getPath()) == null) ? JvmIrUtilsKt.getFileParent(this.getCodegen().getIrFunction()).getFileEntry().getName() : name3;
                    Intrinsics.checkNotNull(name3);
                    this.filePath = name3;
                }

                @Override // org.jetbrains.kotlin.incremental.components.LocationInfo
                public String getFilePath() {
                    return this.filePath;
                }

                @Override // org.jetbrains.kotlin.incremental.components.LocationInfo
                public Position getPosition() {
                    KtFile ktFile2 = this.$ktFile;
                    if (ktFile2 == null) {
                        return Position.INSTANCE.getNO_POSITION();
                    }
                    PsiDiagnosticUtils.LineAndColumn lineAndColumnInPsiFile = DiagnosticUtils.getLineAndColumnInPsiFile(ktFile2, new TextRange(this.this$0.getCallElement().getStartOffset(), this.this$0.getCallElement().getEndOffset()));
                    return new Position(lineAndColumnInPsiFile.getLine(), lineAndColumnInPsiFile.getColumn());
                }
            });
        }
        ClassId parentClassId = JvmIrUtilsKt.getParentClassId(this.callee);
        if (parentClassId != null) {
            Method asmMethod = jvmSignature.getAsmMethod();
            Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
            return SourceCompilerForInlineKt.loadCompiledInlineFunction(parentClassId, asmMethod, AdditionalIrUtilsKt.isSuspend(this.callee), InlineClassAbiKt.getHasMangledReturnType(this.callee), getState());
        }
        return ClassCodegen.Companion.getOrCreate$default(ClassCodegen.INSTANCE, IrUtilsKt.getParentAsClass(this.callee), this.codegen.getContext(), null, 4, null).generateMethodNode(this.callee);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean hasFinallyBlocks() {
        return this.data.hasFinallyBlocks();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public void generateFinallyBlocks(MethodNode finallyNode, int curFinallyDepth, Type returnType, Label afterReturnLabel, Label target) {
        Intrinsics.checkNotNullParameter(finallyNode, "finallyNode");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(afterReturnLabel, "afterReturnLabel");
        ExpressionCodegen expressionCodegen = new ExpressionCodegen(this.codegen.getIrFunction(), this.codegen.getSignature(), this.codegen.getFrameMap(), new InstructionAdapter(finallyNode), this.codegen.getClassCodegen(), getSourceMapper(), this.codegen.getReifiedTypeParametersUsages());
        expressionCodegen.setFinallyDepth(curFinallyDepth);
        expressionCodegen.generateFinallyBlocksIfNeeded(returnType, afterReturnLabel, this.data, target);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean isCallInsideSameModuleAsCallee() {
        return Intrinsics.areEqual(AdditionalIrUtilsKt.getModule(this.callee), AdditionalIrUtilsKt.getModule(this.codegen.getIrFunction()));
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean isFinallyMarkerRequired() {
        return this.codegen.isFinallyMarkerRequired();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public Map<String, Label> getContextLabels() {
        Map<String, Label> mapMutableMapOf = MapsKt.mutableMapOf(TuplesKt.to(this.codegen.getIrFunction().getName().asString(), null));
        for (ExpressionInfo expressionInfo : this.data.getInfos()) {
            if (expressionInfo instanceof LoopInfo) {
                LoopInfo loopInfo = (LoopInfo) expressionInfo;
                mapMutableMapOf.put(IrSourceCompilerForInlineKt.nonLocalReturnLabel(loopInfo.getLoop(), false), loopInfo.getContinueLabel());
                mapMutableMapOf.put(IrSourceCompilerForInlineKt.nonLocalReturnLabel(loopInfo.getLoop(), true), loopInfo.getBreakLabel());
            }
        }
        return mapMutableMapOf;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public void reportSuspensionPointInsideMonitor(String stackTraceElement) {
        Intrinsics.checkNotNullParameter(stackTraceElement, "stackTraceElement");
        KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporter = this.codegen.getContext().getKtDiagnosticReporter();
        IrFunction owner = getCallElement().getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        ktDiagnosticReporter.at(owner).report(JvmBackendErrors.INSTANCE.getSUSPENSION_POINT_INSIDE_MONITOR(), stackTraceElement);
    }
}
