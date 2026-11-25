package org.jetbrains.kotlin.resolve;

import java.util.Iterator;
import java.util.Map;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cfg.ControlFlowInformationProvider;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ControlFlowAnalyzer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KotlinBuiltIns builtIns;
    private final ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory;
    private final PlatformDiagnosticSuppressor diagnosticSuppressor;
    private final EnumWhenTracker enumWhenTracker;
    private final LanguageVersionSettings languageVersionSettings;
    private final BindingTrace trace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "builtIns";
                break;
            case 2:
                objArr[0] = "languageVersionSettings";
                break;
            case 3:
                objArr[0] = "diagnosticSuppressor";
                break;
            case 4:
                objArr[0] = "controlFlowInformationProviderFactory";
                break;
            case 5:
                objArr[0] = "enumWhenTracker";
                break;
            case 6:
            case 8:
            case 9:
            case 10:
                objArr[0] = "c";
                break;
            case 7:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 11:
                objArr[0] = "function";
                break;
            default:
                objArr[0] = "trace";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/ControlFlowAnalyzer";
        switch (i) {
            case 6:
                objArr[2] = "process";
                break;
            case 7:
                objArr[2] = "checkSecondaryConstructor";
                break;
            case 8:
                objArr[2] = "checkDeclarationContainer";
                break;
            case 9:
                objArr[2] = "checkProperty";
                break;
            case 10:
            case 11:
                objArr[2] = "checkFunction";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ControlFlowAnalyzer(BindingTrace bindingTrace, KotlinBuiltIns kotlinBuiltIns, LanguageVersionSettings languageVersionSettings, PlatformDiagnosticSuppressor platformDiagnosticSuppressor, ControlFlowInformationProvider.Factory factory, EnumWhenTracker enumWhenTracker) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(1);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(2);
        }
        if (platformDiagnosticSuppressor == null) {
            $$$reportNull$$$0(3);
        }
        if (factory == null) {
            $$$reportNull$$$0(4);
        }
        if (enumWhenTracker == null) {
            $$$reportNull$$$0(5);
        }
        this.trace = bindingTrace;
        this.builtIns = kotlinBuiltIns;
        this.languageVersionSettings = languageVersionSettings;
        this.diagnosticSuppressor = platformDiagnosticSuppressor;
        this.controlFlowInformationProviderFactory = factory;
        this.enumWhenTracker = enumWhenTracker;
    }

    public void process(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(6);
        }
        Iterator<KtFile> it = bodiesResolveContext.getFiles().iterator();
        while (it.hasNext()) {
            checkDeclarationContainer(bodiesResolveContext, it.next());
        }
        Iterator<KtClassOrObject> it2 = bodiesResolveContext.getDeclaredClasses().keySet().iterator();
        while (it2.hasNext()) {
            checkDeclarationContainer(bodiesResolveContext, it2.next());
        }
        Iterator<KtScript> it3 = bodiesResolveContext.getScripts().keySet().iterator();
        while (it3.hasNext()) {
            checkDeclarationContainer(bodiesResolveContext, it3.next());
        }
        Iterator<KtSecondaryConstructor> it4 = bodiesResolveContext.getSecondaryConstructors().keySet().iterator();
        while (it4.hasNext()) {
            checkSecondaryConstructor(it4.next());
        }
        for (Map.Entry<KtNamedFunction, SimpleFunctionDescriptor> entry : bodiesResolveContext.getFunctions().entrySet()) {
            KtNamedFunction key = entry.getKey();
            checkFunction(bodiesResolveContext, key, (key.hasBlockBody() || key.hasDeclaredReturnType()) ? entry.getValue().getReturnType() : TypeUtils.NO_EXPECTED_TYPE);
        }
        for (Map.Entry<KtProperty, PropertyDescriptor> entry2 : bodiesResolveContext.getProperties().entrySet()) {
            checkProperty(bodiesResolveContext, entry2.getKey(), entry2.getValue());
        }
    }

    private void checkSecondaryConstructor(KtSecondaryConstructor ktSecondaryConstructor) {
        if (ktSecondaryConstructor == null) {
            $$$reportNull$$$0(7);
        }
        ControlFlowInformationProvider controlFlowInformationProviderCreateControlFlowInformationProvider = this.controlFlowInformationProviderFactory.createControlFlowInformationProvider(ktSecondaryConstructor, this.trace, this.languageVersionSettings, this.diagnosticSuppressor, this.enumWhenTracker);
        controlFlowInformationProviderCreateControlFlowInformationProvider.checkDeclaration();
        controlFlowInformationProviderCreateControlFlowInformationProvider.checkFunction(this.builtIns.getUnitType());
    }

    private void checkDeclarationContainer(BodiesResolveContext bodiesResolveContext, KtDeclarationContainer ktDeclarationContainer) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(8);
        }
        ControlFlowInformationProvider controlFlowInformationProviderCreateControlFlowInformationProvider = this.controlFlowInformationProviderFactory.createControlFlowInformationProvider((KtElement) ktDeclarationContainer, this.trace, this.languageVersionSettings, this.diagnosticSuppressor, this.enumWhenTracker);
        if (bodiesResolveContext.getTopDownAnalysisMode().getIsLocalDeclarations()) {
            controlFlowInformationProviderCreateControlFlowInformationProvider.checkForLocalClassOrObjectMode();
        } else {
            controlFlowInformationProviderCreateControlFlowInformationProvider.checkDeclaration();
        }
    }

    private void checkProperty(BodiesResolveContext bodiesResolveContext, KtProperty ktProperty, PropertyDescriptor propertyDescriptor) {
        CallableDescriptor setter;
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(9);
        }
        for (KtPropertyAccessor ktPropertyAccessor : ktProperty.getAccessors()) {
            if (ktPropertyAccessor.isGetter()) {
                setter = propertyDescriptor.getGetter();
            } else {
                setter = propertyDescriptor.getSetter();
            }
            checkFunction(bodiesResolveContext, ktPropertyAccessor, setter.getReturnType());
        }
    }

    private void checkFunction(BodiesResolveContext bodiesResolveContext, KtDeclarationWithBody ktDeclarationWithBody, KotlinType kotlinType) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(10);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(11);
        }
        ControlFlowInformationProvider controlFlowInformationProviderCreateControlFlowInformationProvider = this.controlFlowInformationProviderFactory.createControlFlowInformationProvider(ktDeclarationWithBody, this.trace, this.languageVersionSettings, this.diagnosticSuppressor, this.enumWhenTracker);
        if (bodiesResolveContext.getTopDownAnalysisMode().getIsLocalDeclarations()) {
            controlFlowInformationProviderCreateControlFlowInformationProvider.checkForLocalClassOrObjectMode();
        } else {
            controlFlowInformationProviderCreateControlFlowInformationProvider.checkDeclaration();
            controlFlowInformationProviderCreateControlFlowInformationProvider.checkFunction(kotlinType);
        }
    }
}
