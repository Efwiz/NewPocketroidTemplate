package org.jetbrains.kotlin.resolve;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.inline.InlineAnalyzerExtension;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.inline.ReasonableInlineRule;

/* JADX INFO: loaded from: classes6.dex */
public class AnalyzerExtensions {
    private LanguageVersionSettings languageVersionSettings;
    private final Iterable<ReasonableInlineRule> reasonableInlineRules;
    private final BindingTrace trace;

    public interface AnalyzerExtension {
        void process(CallableMemberDescriptor callableMemberDescriptor, KtCallableDeclaration ktCallableDeclaration, BindingTrace bindingTrace);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 8 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 8 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "reasonableInlineRules";
                break;
            case 2:
                objArr[0] = "languageVersionSettings";
                break;
            case 3:
                objArr[0] = "bodiesResolveContext";
                break;
            case 4:
                objArr[0] = "functionDescriptor";
                break;
            case 5:
            case 6:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/resolve/AnalyzerExtensions";
                break;
            case 7:
                objArr[0] = "propertyDescriptor";
                break;
            default:
                objArr[0] = "trace";
                break;
        }
        if (i == 5 || i == 6) {
            objArr[1] = "getFunctionExtensions";
        } else if (i == 8 || i == 9) {
            objArr[1] = "getPropertyExtensions";
        } else {
            objArr[1] = "org/jetbrains/kotlin/resolve/AnalyzerExtensions";
        }
        switch (i) {
            case 3:
                objArr[2] = "process";
                break;
            case 4:
                objArr[2] = "getFunctionExtensions";
                break;
            case 5:
            case 6:
            case 8:
            case 9:
                break;
            case 7:
                objArr[2] = "getPropertyExtensions";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 8 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public AnalyzerExtensions(BindingTrace bindingTrace, Iterable<ReasonableInlineRule> iterable, LanguageVersionSettings languageVersionSettings) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        if (iterable == null) {
            $$$reportNull$$$0(1);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(2);
        }
        this.trace = bindingTrace;
        this.reasonableInlineRules = iterable;
        this.languageVersionSettings = languageVersionSettings;
    }

    public void process(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(3);
        }
        for (Map.Entry<KtNamedFunction, SimpleFunctionDescriptor> entry : bodiesResolveContext.getFunctions().entrySet()) {
            KtNamedFunction key = entry.getKey();
            SimpleFunctionDescriptor value = entry.getValue();
            Iterator<InlineAnalyzerExtension> it = getFunctionExtensions(value).iterator();
            while (it.hasNext()) {
                it.next().process(value, key, this.trace);
            }
        }
        for (Map.Entry<KtProperty, PropertyDescriptor> entry2 : bodiesResolveContext.getProperties().entrySet()) {
            KtProperty key2 = entry2.getKey();
            PropertyDescriptor value2 = entry2.getValue();
            Iterator<InlineAnalyzerExtension> it2 = getPropertyExtensions(value2).iterator();
            while (it2.hasNext()) {
                it2.next().process(value2, key2, this.trace);
            }
        }
    }

    private List<InlineAnalyzerExtension> getFunctionExtensions(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (InlineUtil.isInline(functionDescriptor)) {
            List<InlineAnalyzerExtension> listSingletonList = Collections.singletonList(new InlineAnalyzerExtension(this.reasonableInlineRules, this.languageVersionSettings));
            if (listSingletonList == null) {
                $$$reportNull$$$0(5);
            }
            return listSingletonList;
        }
        List<InlineAnalyzerExtension> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(6);
        }
        return listEmptyList;
    }

    private List<InlineAnalyzerExtension> getPropertyExtensions(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (InlineUtil.hasInlineAccessors(propertyDescriptor)) {
            List<InlineAnalyzerExtension> listSingletonList = Collections.singletonList(new InlineAnalyzerExtension(this.reasonableInlineRules, this.languageVersionSettings));
            if (listSingletonList == null) {
                $$$reportNull$$$0(8);
            }
            return listSingletonList;
        }
        List<InlineAnalyzerExtension> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(9);
        }
        return listEmptyList;
    }
}
