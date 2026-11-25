package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.renderer.FirRenderer;
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirIntersectionTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirDynamicTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirFunctionTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirIntersectionTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirUserTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.FirFunctionTypeRefImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRefKt;
import org.jetbrains.kotlin.fir.types.impl.FirResolvedTypeRefImpl;
import org.jetbrains.kotlin.fir.types.impl.FirUserTypeRefImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;
import org.luaj.vm2.Lua;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a5\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u00192\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00190\u001dH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a-\u0010\u001f\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u00192\u0006\u0010 \u001a\u00020\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00190\u001dH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010!\u001aà\u0001\u0010\"\u001a\u00020#*\u00020#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'2\b\b\u0002\u0010(\u001a\u00020)2\b\b\u0002\u0010*\u001a\u00020)2\b\b\u0002\u0010+\u001a\u00020)2\b\b\u0002\u0010,\u001a\u00020)2\b\b\u0002\u0010-\u001a\u00020)2\b\b\u0002\u0010.\u001a\u00020)2\b\b\u0002\u0010/\u001a\u00020)2\b\b\u0002\u00100\u001a\u00020)2\b\b\u0002\u00101\u001a\u00020)2\b\b\u0002\u00102\u001a\u00020)2\b\b\u0002\u00103\u001a\u00020)2\b\b\u0002\u00104\u001a\u00020)2\b\b\u0002\u00105\u001a\u00020)2\b\b\u0002\u00106\u001a\u00020)2\b\b\u0002\u00107\u001a\u00020)2\b\b\u0002\u00108\u001a\u00020)2\b\b\u0002\u00109\u001a\u00020)2\b\b\u0002\u0010:\u001a\u00020)2\b\b\u0002\u0010;\u001a\u00020)\u001a#\u0010<\u001a\u0002H\u0019\"\b\b\u0000\u0010\u0019*\u00020=*\u0002H\u00192\b\u0010>\u001a\u0004\u0018\u00010?¢\u0006\u0002\u0010@\u001a!\u0010A\u001a\u0002H\u0019\"\b\b\u0000\u0010\u0019*\u00020=*\u0002H\u00192\u0006\u0010B\u001a\u00020C¢\u0006\u0002\u0010D\u001a\u0012\u0010E\u001a\u00020)*\u00020F2\u0006\u0010G\u001a\u00020H\u001a\n\u0010I\u001a\u00020J*\u00020\u0013\u001a\n\u0010K\u001a\u00020J*\u00020\u0013\u001a\u0010\u0010L\u001a\b\u0012\u0004\u0012\u00020N0M*\u00020O\u001a*\u0010P\u001a\b\u0012\u0004\u0012\u0002HQ0M\"\u0004\b\u0000\u0010Q*\b\u0012\u0004\u0012\u0002HQ0M2\f\u0010R\u001a\b\u0012\u0004\u0012\u0002HQ0M\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0015\u0010\f\u001a\u00020\r*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u0017\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u0017\u0010\u0016\u001a\u0004\u0018\u00010\u0012*\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006S"}, d2 = {"exceptionHandler", "Lorg/jetbrains/kotlin/fir/FirExceptionHandler;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getExceptionHandler", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirExceptionHandler;", "exceptionHandler$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "labelName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getLabelName", "(Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;)Lorg/jetbrains/kotlin/name/Name;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getPackageFqName", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;)Lorg/jetbrains/kotlin/name/FqName;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/fir/FirElement;", "getPsi", "(Lorg/jetbrains/kotlin/fir/FirElement;)Lcom/intellij/psi/PsiElement;", "realPsi", "getRealPsi", "whileAnalysing", "R", "session", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "block", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/FirElement;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withFileAnalysisExceptionWrapping", "file", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isExpect", "", "isActual", "isOverride", "isOperator", "isInfix", "isInline", "isTailRec", "isExternal", "isConst", "isLateInit", "isInner", "isCompanion", "isData", "isSuspend", "isStatic", "isFromSealedClass", "isFromEnumClass", "isFun", "hasStableParameterNames", "copyWithNewSource", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "newSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/KtSourceElement;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "copyWithNewSourceKind", "newKind", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/KtFakeSourceElementKind;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "isEnumEntries", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "containingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "render", "", "renderWithType", "returnExpressions", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "smartPlus", ExifInterface.GPS_DIRECTION_TRUE, "other", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(UtilsKt.class, Constants.TYPE_TREE), "exceptionHandler", "getExceptionHandler(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirExceptionHandler;"))};
    private static final ArrayMapAccessor exceptionHandler$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirExceptionHandler.class), (Object) null, 2, (Object) null);

    public static final List<FirExpression> returnExpressions(FirBlock firBlock) {
        Intrinsics.checkNotNullParameter(firBlock, "<this>");
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) firBlock.getStatements());
        return CollectionsKt.listOfNotNull(objLastOrNull instanceof FirExpression ? (FirExpression) objLastOrNull : null);
    }

    public static final <R extends FirTypeRef> R copyWithNewSourceKind(R r, KtFakeSourceElementKind newKind) {
        Intrinsics.checkNotNullParameter(r, "<this>");
        Intrinsics.checkNotNullParameter(newKind, "newKind");
        if (r.getSource() == null) {
            return r;
        }
        KtSourceElement source = r.getSource();
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, newKind)) {
            return r;
        }
        KtSourceElement source2 = r.getSource();
        return (R) copyWithNewSource(r, source2 != null ? KtSourceElementKt.fakeElement$default(source2, newKind, 0, 0, 6, null) : null);
    }

    public static final <R extends FirTypeRef> R copyWithNewSource(R r, KtSourceElement ktSourceElement) {
        FirIntersectionTypeRef firIntersectionTypeRefBuild;
        Intrinsics.checkNotNullParameter(r, "<this>");
        KtSourceElement source = r.getSource();
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, ktSourceElement != null ? ktSourceElement.getKind() : null)) {
            return r;
        }
        if (!(r instanceof FirResolvedTypeRefImpl)) {
            if (!(r instanceof FirErrorTypeRef)) {
                if (!(r instanceof FirUserTypeRefImpl)) {
                    if (!(r instanceof FirFunctionTypeRefImpl)) {
                        if (!(r instanceof FirDynamicTypeRef)) {
                            if (r instanceof FirImplicitBuiltinTypeRef) {
                                firIntersectionTypeRefBuild = FirImplicitBuiltinTypeRefKt.withNewSource((FirImplicitBuiltinTypeRef) r, ktSourceElement);
                            } else {
                                if (!(r instanceof FirIntersectionTypeRef)) {
                                    throw new NotImplementedError("An operation is not implemented: " + ("Not implemented for " + Reflection.getOrCreateKotlinClass(r.getClass())));
                                }
                                FirIntersectionTypeRefBuilder firIntersectionTypeRefBuilder = new FirIntersectionTypeRefBuilder();
                                firIntersectionTypeRefBuilder.setSource(ktSourceElement);
                                FirIntersectionTypeRef firIntersectionTypeRef = (FirIntersectionTypeRef) r;
                                firIntersectionTypeRefBuilder.setMarkedNullable(firIntersectionTypeRef.getIsMarkedNullable());
                                firIntersectionTypeRefBuilder.setLeftType(firIntersectionTypeRef.getLeftType());
                                firIntersectionTypeRefBuilder.setRightType(firIntersectionTypeRef.getRightType());
                                firIntersectionTypeRefBuild = firIntersectionTypeRefBuilder.mo6810build();
                            }
                        } else {
                            FirDynamicTypeRefBuilder firDynamicTypeRefBuilder = new FirDynamicTypeRefBuilder();
                            firDynamicTypeRefBuilder.setSource(ktSourceElement);
                            firDynamicTypeRefBuilder.setMarkedNullable(((FirDynamicTypeRef) r).getIsMarkedNullable());
                            CollectionsKt.addAll(firDynamicTypeRefBuilder.getAnnotations(), r.getAnnotations());
                            firIntersectionTypeRefBuild = firDynamicTypeRefBuilder.mo6810build();
                        }
                    } else {
                        FirFunctionTypeRef firFunctionTypeRef = (FirFunctionTypeRef) r;
                        FirFunctionTypeRefBuilder firFunctionTypeRefBuilder = new FirFunctionTypeRefBuilder();
                        firFunctionTypeRefBuilder.setSource(firFunctionTypeRef.getSource());
                        firFunctionTypeRefBuilder.getAnnotations().addAll(firFunctionTypeRef.getAnnotations());
                        firFunctionTypeRefBuilder.setMarkedNullable(firFunctionTypeRef.getIsMarkedNullable());
                        firFunctionTypeRefBuilder.setReceiverTypeRef(firFunctionTypeRef.getReceiverTypeRef());
                        firFunctionTypeRefBuilder.getParameters().addAll(firFunctionTypeRef.getParameters());
                        firFunctionTypeRefBuilder.setReturnTypeRef(firFunctionTypeRef.getReturnTypeRef());
                        firFunctionTypeRefBuilder.setSuspend(firFunctionTypeRef.getIsSuspend());
                        firFunctionTypeRefBuilder.getContextReceiverTypeRefs().addAll(firFunctionTypeRef.getContextReceiverTypeRefs());
                        firFunctionTypeRefBuilder.setSource(ktSourceElement);
                        firIntersectionTypeRefBuild = firFunctionTypeRefBuilder.mo6810build();
                    }
                } else {
                    FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
                    firUserTypeRefBuilder.setSource(ktSourceElement);
                    FirUserTypeRefImpl firUserTypeRefImpl = (FirUserTypeRefImpl) r;
                    firUserTypeRefBuilder.setMarkedNullable(firUserTypeRefImpl.getIsMarkedNullable());
                    CollectionsKt.addAll(firUserTypeRefBuilder.getQualifier(), firUserTypeRefImpl.getQualifier());
                    CollectionsKt.addAll(firUserTypeRefBuilder.getAnnotations(), MutableOrEmptyList.m6756boximpl(firUserTypeRefImpl.m7080getAnnotations5e3fPpI()));
                    firIntersectionTypeRefBuild = firUserTypeRefBuilder.mo6810build();
                }
            } else {
                FirErrorTypeRef firErrorTypeRef = (FirErrorTypeRef) r;
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setSource(firErrorTypeRef.getSource());
                firErrorTypeRefBuilder.setType(firErrorTypeRef.getType());
                firErrorTypeRefBuilder.setDelegatedTypeRef(firErrorTypeRef.getDelegatedTypeRef());
                firErrorTypeRefBuilder.setDiagnostic(firErrorTypeRef.getDiagnostic());
                firErrorTypeRefBuilder.setSource(ktSourceElement);
                firIntersectionTypeRefBuild = firErrorTypeRefBuilder.mo6810build();
            }
        } else {
            FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) r;
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setSource(firResolvedTypeRef.getSource());
            firResolvedTypeRefBuilder.getAnnotations().addAll(firResolvedTypeRef.getAnnotations());
            firResolvedTypeRefBuilder.setType(firResolvedTypeRef.getType());
            firResolvedTypeRefBuilder.setDelegatedTypeRef(firResolvedTypeRef.getDelegatedTypeRef());
            firResolvedTypeRefBuilder.setFromStubType(firResolvedTypeRef.getIsFromStubType());
            firResolvedTypeRefBuilder.setSource(ktSourceElement);
            firIntersectionTypeRefBuild = firResolvedTypeRefBuilder.mo6810build();
        }
        Intrinsics.checkNotNull(firIntersectionTypeRefBuild, "null cannot be cast to non-null type R of org.jetbrains.kotlin.fir.UtilsKt.copyWithNewSource");
        return firIntersectionTypeRefBuild;
    }

    public static final FqName getPackageFqName(FirFile firFile) {
        Intrinsics.checkNotNullParameter(firFile, "<this>");
        return firFile.getPackageDirective().getPackageFqName();
    }

    public static final PsiElement getPsi(FirElement firElement) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        KtSourceElement source = firElement.getSource();
        KtPsiSourceElement ktPsiSourceElement = source instanceof KtPsiSourceElement ? (KtPsiSourceElement) source : null;
        if (ktPsiSourceElement != null) {
            return ktPsiSourceElement.getPsi();
        }
        return null;
    }

    public static final PsiElement getRealPsi(FirElement firElement) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        KtSourceElement source = firElement.getSource();
        KtRealPsiSourceElement ktRealPsiSourceElement = source instanceof KtRealPsiSourceElement ? (KtRealPsiSourceElement) source : null;
        if (ktRealPsiSourceElement != null) {
            return ktRealPsiSourceElement.getPsi();
        }
        return null;
    }

    public static final Name getLabelName(FirContextReceiver firContextReceiver) {
        Intrinsics.checkNotNullParameter(firContextReceiver, "<this>");
        Name customLabelName = firContextReceiver.getCustomLabelName();
        return customLabelName == null ? firContextReceiver.getLabelNameFromTypeRef() : customLabelName;
    }

    public static final String renderWithType(FirElement firElement) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        return new FirRenderer(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Lua.MAXARG_sBx, null).renderElementWithTypeAsString(firElement);
    }

    public static final String render(FirElement firElement) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        return FirRenderer.renderElementAsString$default(new FirRenderer(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Lua.MAXARG_sBx, null), firElement, false, 2, null);
    }

    public static final FirDeclarationStatus copy(FirDeclarationStatus firDeclarationStatus, Visibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19) {
        FirResolvedDeclarationStatusImpl firDeclarationStatusImpl;
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<this>");
        Visibility visibility2 = visibility == null ? firDeclarationStatus.getVisibility() : visibility;
        Modality modality2 = modality == null ? firDeclarationStatus.getModality() : modality;
        if (firDeclarationStatus instanceof FirResolvedDeclarationStatus) {
            Intrinsics.checkNotNull(modality2);
            firDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility2, modality2, ((FirResolvedDeclarationStatus) firDeclarationStatus).getEffectiveVisibility());
        } else {
            firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility2, modality2);
        }
        firDeclarationStatusImpl.setExpect(z);
        firDeclarationStatusImpl.setActual(z2);
        firDeclarationStatusImpl.setOverride(z3);
        firDeclarationStatusImpl.setOperator(z4);
        firDeclarationStatusImpl.setInfix(z5);
        firDeclarationStatusImpl.setInline(z6);
        firDeclarationStatusImpl.setTailRec(z7);
        firDeclarationStatusImpl.setExternal(z8);
        firDeclarationStatusImpl.setConst(z9);
        firDeclarationStatusImpl.setLateInit(z10);
        firDeclarationStatusImpl.setInner(z11);
        firDeclarationStatusImpl.setCompanion(z12);
        firDeclarationStatusImpl.setData(z13);
        firDeclarationStatusImpl.setSuspend(z14);
        firDeclarationStatusImpl.setStatic(z15);
        firDeclarationStatusImpl.setFromSealedClass(z16);
        firDeclarationStatusImpl.setFromEnumClass(z17);
        firDeclarationStatusImpl.setFun(z18);
        firDeclarationStatusImpl.setHasStableParameterNames(z19);
        return firDeclarationStatusImpl;
    }

    public static final <R> R whileAnalysing(FirSession session, FirElement element, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            return block.invoke();
        } catch (Throwable th) {
            getExceptionHandler(session).handleExceptionOnElementAnalysis(element, th);
            throw null;
        }
    }

    public static final <R> R withFileAnalysisExceptionWrapping(FirFile file, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            return block.invoke();
        } catch (Throwable th) {
            getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }

    public static final FirExceptionHandler getExceptionHandler(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirExceptionHandler) exceptionHandler$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> List<T> smartPlus(List<? extends T> list, List<? extends T> other) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (other.isEmpty()) {
            return list;
        }
        if (list.isEmpty()) {
            return other;
        }
        ArrayList arrayList = new ArrayList(list.size() + other.size());
        arrayList.addAll(list);
        arrayList.addAll(other);
        return arrayList;
    }

    public static final boolean isEnumEntries(FirVariable firVariable, FirClass containingClass) {
        Intrinsics.checkNotNullParameter(firVariable, "<this>");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        return firVariable.getStatus().isStatic() && Intrinsics.areEqual(firVariable.getName(), StandardNames.ENUM_ENTRIES) && containingClass.getClassKind() == ClassKind.ENUM_CLASS;
    }
}
