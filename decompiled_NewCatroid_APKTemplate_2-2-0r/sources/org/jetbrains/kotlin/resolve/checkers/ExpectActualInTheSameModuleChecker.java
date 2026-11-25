package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.platform.TargetPlatformKt;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolver;
import org.jetbrains.kotlin.resolve.multiplatform.IsCommonSourceKt;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;

/* JADX INFO: compiled from: ExpectActualInTheSameModuleChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ExpectActualInTheSameModuleChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "declarationSource", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "getDeclarationSource", "(Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;)Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualInTheSameModuleChecker implements DeclarationChecker {
    public static final ExpectActualInTheSameModuleChecker INSTANCE = new ExpectActualInTheSameModuleChecker();

    private ExpectActualInTheSameModuleChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiPlatformProjects) && (descriptor instanceof MemberDescriptor) && (declaration instanceof KtNamedDeclaration)) {
            MemberDescriptor memberDescriptor = (MemberDescriptor) descriptor;
            if (memberDescriptor.get$isExpect() && (memberDescriptor.getContainingModule() instanceof PackageFragmentDescriptor)) {
                ModuleDescriptor module = DescriptorUtilsKt.getModule(descriptor);
                Map mapFindActualForExpected$default = ExpectedActualResolver.findActualForExpected$default(ExpectedActualResolver.INSTANCE, memberDescriptor, module, null, 4, null);
                if (mapFindActualForExpected$default != null) {
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Map.Entry entry : mapFindActualForExpected$default.entrySet()) {
                        if (ExpectedActualDeclarationChecker.INSTANCE.isCompatibleOrWeakCompatible$frontend((ExpectActualCompatibility) entry.getKey())) {
                            linkedHashMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    ArrayList arrayList = new ArrayList();
                    Iterator it = linkedHashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        CollectionsKt.addAll(arrayList, (List) ((Map.Entry) it.next()).getValue());
                    }
                    ArrayList arrayList2 = arrayList;
                    if (arrayList2.isEmpty()) {
                        arrayList2 = null;
                    }
                    if (arrayList2 != null) {
                        if (TargetPlatformKt.isCommon(module.getPlatform())) {
                            ArrayList arrayList3 = arrayList2;
                            if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
                                return;
                            }
                            Iterator it2 = arrayList3.iterator();
                            while (it2.hasNext()) {
                                if (Intrinsics.areEqual(DescriptorUtilsKt.getModule((MemberDescriptor) it2.next()), module)) {
                                }
                            }
                            return;
                        }
                        KtFile containingKtFile = ((KtNamedDeclaration) declaration).getContainingKtFile();
                        Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                        if (Intrinsics.areEqual((Object) IsCommonSourceKt.isCommonSource(containingKtFile), (Object) true)) {
                            return;
                        }
                        context.getTrace().report(Errors.EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE.on(declaration, descriptor));
                        Iterator it3 = arrayList2.iterator();
                        while (it3.hasNext()) {
                            KtNamedDeclaration declarationSource = getDeclarationSource((MemberDescriptor) it3.next());
                            if (declarationSource != null) {
                                context.getTrace().report(Errors.EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE.on(declarationSource, descriptor));
                            }
                        }
                    }
                }
            }
        }
    }

    private final KtNamedDeclaration getDeclarationSource(MemberDescriptor memberDescriptor) {
        SourceElement source = memberDescriptor.getSourceElement();
        KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
        KtElement psi = kotlinSourceElement != null ? kotlinSourceElement.getPsi() : null;
        if (psi instanceof KtNamedDeclaration) {
            return (KtNamedDeclaration) psi;
        }
        return null;
    }
}
