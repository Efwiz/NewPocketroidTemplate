package org.jetbrains.kotlin.com.intellij.psi.augment;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiAugmentProvider {
    private final Key<CachedValue<Map<Class, List>>> myCacheKey = Key.create(getClass().getName());
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiAugmentProvider.class);
    public static final ExtensionPointName<PsiAugmentProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.lang.psiAugmentProvider");
    private static final NotNullLazyValue<ExtensionPoint<PsiAugmentProvider>> EP = NotNullLazyValue.lazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda3
        @Override // java.util.function.Supplier
        public final Object get() {
            return PsiAugmentProvider.EP_NAME.getPoint();
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 9 || i == 14 || i == 17 || i == 19 || i == 25) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 9 || i == 14 || i == 17 || i == 19 || i == 25) ? 2 : 3];
        switch (i) {
            case 1:
            case 8:
            case 16:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
            case 3:
            case 9:
            case 14:
            case 17:
            case 19:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/augment/PsiAugmentProvider";
                break;
            case 4:
                objArr[0] = "aClass";
                break;
            case 5:
            case 18:
                objArr[0] = "nameHint";
                break;
            case 6:
                objArr[0] = "context";
                break;
            case 7:
            case 15:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 10:
            case 11:
            case 20:
            case 21:
                objArr[0] = "typeElement";
                break;
            case 12:
            case 22:
                objArr[0] = "modifierList";
                break;
            case 13:
            case 24:
                objArr[0] = "modifiers";
                break;
            case 23:
                objArr[0] = "project";
                break;
        }
        if (i == 2 || i == 3 || i == 9) {
            objArr[1] = "getAugments";
        } else if (i == 14) {
            objArr[1] = "transformModifiers";
        } else if (i == 17) {
            objArr[1] = "collectAugments";
        } else if (i == 19) {
            objArr[1] = "collectExtensionMethods";
        } else if (i != 25) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/augment/PsiAugmentProvider";
        } else {
            objArr[1] = "transformModifierProperties";
        }
        switch (i) {
            case 2:
            case 3:
            case 9:
            case 14:
            case 17:
            case 19:
            case 25:
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "getExtensionMethods";
                break;
            case 7:
            case 8:
            default:
                objArr[2] = "getAugments";
                break;
            case 10:
                objArr[2] = "inferType";
                break;
            case 11:
                objArr[2] = "canInferType";
                break;
            case 12:
            case 13:
                objArr[2] = "transformModifiers";
                break;
            case 15:
            case 16:
                objArr[2] = "collectAugments";
                break;
            case 18:
                objArr[2] = "collectExtensionMethods";
                break;
            case 20:
                objArr[2] = "getInferredType";
                break;
            case 21:
                objArr[2] = "isInferredType";
                break;
            case 22:
            case 23:
            case 24:
                objArr[2] = "transformModifierProperties";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 9 && i != 14 && i != 17 && i != 19 && i != 25) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected PsiType inferType(PsiTypeElement psiTypeElement) {
        if (psiTypeElement != null) {
            return null;
        }
        $$$reportNull$$$0(10);
        return null;
    }

    protected <Psi extends PsiElement> List<Psi> getAugments(final PsiElement psiElement, Class<Psi> cls, String str) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            List<Psi> listM6538xa9ee6059 = m6538xa9ee6059(psiElement, cls);
            if (listM6538xa9ee6059 == null) {
                $$$reportNull$$$0(2);
            }
            return listM6538xa9ee6059;
        }
        List<Psi> list = (List) ((Map) CachedValuesManager.getCachedValue(psiElement, this.myCacheKey, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6539x141de878(psiElement);
            }
        })).get(cls);
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        return list;
    }

    /* JADX INFO: renamed from: lambda$getAugments$2$org-jetbrains-kotlin-com-intellij-psi-augment-PsiAugmentProvider, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6539x141de878(final PsiElement psiElement) {
        return CachedValueProvider.Result.create(ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6538xa9ee6059(psiElement, (Class) obj);
            }
        }), PsiModificationTracker.MODIFICATION_COUNT);
    }

    protected List<PsiExtensionMethod> getExtensionMethods(PsiClass psiClass, String str, PsiElement psiElement) {
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    /* JADX INFO: renamed from: getAugments, reason: merged with bridge method [inline-methods] */
    public <Psi extends PsiElement> List<Psi> m6538xa9ee6059(PsiElement psiElement, Class<Psi> cls) {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        if (cls == null) {
            $$$reportNull$$$0(8);
        }
        List<Psi> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(9);
        }
        return listEmptyList;
    }

    protected boolean canInferType(PsiTypeElement psiTypeElement) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(11);
        }
        return inferType(psiTypeElement) != null;
    }

    protected Set<String> transformModifiers(PsiModifierList psiModifierList, Set<String> set) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(12);
        }
        if (set == null) {
            $$$reportNull$$$0(13);
        }
        if (set == null) {
            $$$reportNull$$$0(14);
        }
        return set;
    }

    public static <Psi extends PsiElement> List<Psi> collectAugments(final PsiElement psiElement, final Class<? extends Psi> cls, final String str) {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        if (cls == null) {
            $$$reportNull$$$0(16);
        }
        final SmartList smartList = new SmartList();
        forEach(psiElement.getProject(), new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return PsiAugmentProvider.lambda$collectAugments$3(psiElement, cls, str, smartList, (PsiAugmentProvider) obj);
            }
        });
        return smartList;
    }

    static /* synthetic */ boolean lambda$collectAugments$3(PsiElement psiElement, Class cls, String str, List list, PsiAugmentProvider psiAugmentProvider) {
        for (PsiElement psiElement2 : psiAugmentProvider.getAugments(psiElement, cls, str)) {
            if (str == null || !(psiElement2 instanceof PsiNamedElement) || str.equals(((PsiNamedElement) psiElement2).getName())) {
                try {
                    PsiUtilCore.ensureValid(psiElement2);
                    list.add(psiElement2);
                } catch (ProcessCanceledException e) {
                    throw e;
                } catch (Throwable th) {
                    LOG.error((Throwable) PluginException.createByClass(th, psiAugmentProvider.getClass()));
                }
            }
        }
        return true;
    }

    public static List<PsiExtensionMethod> collectExtensionMethods(final PsiClass psiClass, final String str, final PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        final SmartList smartList = new SmartList();
        forEach(psiClass.getProject(), new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return PsiAugmentProvider.lambda$collectExtensionMethods$4(psiClass, str, psiElement, smartList, (PsiAugmentProvider) obj);
            }
        });
        return smartList;
    }

    static /* synthetic */ boolean lambda$collectExtensionMethods$4(PsiClass psiClass, String str, PsiElement psiElement, List list, PsiAugmentProvider psiAugmentProvider) {
        for (PsiExtensionMethod psiExtensionMethod : psiAugmentProvider.getExtensionMethods(psiClass, str, psiElement)) {
            try {
                PsiUtilCore.ensureValid(psiExtensionMethod);
                list.add(psiExtensionMethod);
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Throwable th) {
                LOG.error((Throwable) PluginException.createByClass(th, psiAugmentProvider.getClass()));
            }
        }
        return true;
    }

    public static PsiType getInferredType(final PsiTypeElement psiTypeElement) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(20);
        }
        final Ref refCreate = Ref.create();
        forEach(psiTypeElement.getProject(), new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return PsiAugmentProvider.lambda$getInferredType$5(psiTypeElement, refCreate, (PsiAugmentProvider) obj);
            }
        });
        return (PsiType) refCreate.get();
    }

    static /* synthetic */ boolean lambda$getInferredType$5(PsiTypeElement psiTypeElement, Ref ref, PsiAugmentProvider psiAugmentProvider) {
        PsiType psiTypeInferType = psiAugmentProvider.inferType(psiTypeElement);
        if (psiTypeInferType == null) {
            return true;
        }
        try {
            PsiUtil.ensureValidType(psiTypeInferType);
            ref.set(psiTypeInferType);
            return false;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            throw PluginException.createByClass(th, psiAugmentProvider.getClass());
        }
    }

    public static boolean isInferredType(final PsiTypeElement psiTypeElement) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(21);
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        forEach(psiTypeElement.getProject(), new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return PsiAugmentProvider.lambda$isInferredType$6(psiTypeElement, atomicBoolean, (PsiAugmentProvider) obj);
            }
        });
        return atomicBoolean.get();
    }

    static /* synthetic */ boolean lambda$isInferredType$6(PsiTypeElement psiTypeElement, AtomicBoolean atomicBoolean, PsiAugmentProvider psiAugmentProvider) {
        boolean zCanInferType = psiAugmentProvider.canInferType(psiTypeElement);
        if (zCanInferType) {
            atomicBoolean.set(true);
        }
        return !zCanInferType;
    }

    public static Set<String> transformModifierProperties(final PsiModifierList psiModifierList, Project project, Set<String> set) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(22);
        }
        if (project == null) {
            $$$reportNull$$$0(23);
        }
        if (set == null) {
            $$$reportNull$$$0(24);
        }
        final Ref refCreate = Ref.create(set);
        forEach(project, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return PsiAugmentProvider.lambda$transformModifierProperties$7(refCreate, psiModifierList, (PsiAugmentProvider) obj);
            }
        });
        Set<String> set2 = (Set) refCreate.get();
        if (set2 == null) {
            $$$reportNull$$$0(25);
        }
        return set2;
    }

    static /* synthetic */ boolean lambda$transformModifierProperties$7(Ref ref, PsiModifierList psiModifierList, PsiAugmentProvider psiAugmentProvider) {
        ref.set(psiAugmentProvider.transformModifiers(psiModifierList, Collections.unmodifiableSet((Set) ref.get())));
        return true;
    }

    private static void forEach(Project project, Processor<? super PsiAugmentProvider> processor) {
        boolean zIsDumb = DumbService.isDumb(project);
        for (PsiAugmentProvider psiAugmentProvider : EP.getValue().getExtensionList()) {
            if (!zIsDumb || DumbService.isDumbAware(psiAugmentProvider)) {
                try {
                    if (!processor.process(psiAugmentProvider)) {
                        return;
                    }
                } catch (ProcessCanceledException e) {
                    throw e;
                } catch (Exception e2) {
                    Logger.getInstance((Class<?>) PsiAugmentProvider.class).error("provider: " + psiAugmentProvider, e2);
                }
            }
        }
    }
}
