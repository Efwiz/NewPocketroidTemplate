package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.function.Function;
import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.INativeFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.UnknownFileType;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Iconable;
import org.jetbrains.kotlin.com.intellij.openapi.util.LastComputedIconCache;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullFactory;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.ui.CoreAwareIconManager;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;
import org.jetbrains.kotlin.com.intellij.util.PsiIconUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ElementBase extends UserDataHolderBase implements Iconable {
    public static final int FLAGS_LOCKED = 2048;
    private static final Logger LOG = Logger.getInstance((Class<?>) ElementBase.class);
    private static final Function<ElementIconRequest, Icon> ICON_COMPUTE = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ElementBase.lambda$static$0((ElementBase.ElementIconRequest) obj);
        }
    };
    private static final NotNullLazyValue<Icon> VISIBILITY_ICON_PLACEHOLDER = NotNullLazyValue.createValue(new NotNullFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase$$ExternalSyntheticLambda2
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullFactory, org.jetbrains.kotlin.com.intellij.openapi.util.Factory
        public final Object create() {
            return IconManager.getInstance().createEmptyIcon(PlatformIcons.PUBLIC_ICON);
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/ElementBase";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/ElementBase";
        } else {
            objArr[1] = "buildRowIcon";
        }
        if (i == 1) {
            objArr[2] = "doComputeIconNow";
        } else if (i != 2) {
            objArr[2] = "computeIconNow";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    protected boolean isVisibilitySupported() {
        return false;
    }

    static /* synthetic */ Icon lambda$static$0(ElementIconRequest elementIconRequest) {
        PsiElement element = elementIconRequest.myPointer.getElement();
        if (element == null) {
            return null;
        }
        Icon iconComputeIconNow = computeIconNow(element, elementIconRequest.myFlags);
        LastComputedIconCache.put(element, iconComputeIconNow, elementIconRequest.myFlags);
        return iconComputeIconNow;
    }

    public Icon getIcon(int i) {
        if (!(this instanceof PsiElement)) {
            return null;
        }
        try {
            return computeIcon(i);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (IndexNotReadyException e2) {
            throw e2;
        } catch (Exception e3) {
            LOG.error((Throwable) e3);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Icon computeIcon(final int i) {
        PsiElement psiElement = (PsiElement) this;
        if (!psiElement.isValid()) {
            return null;
        }
        if (Registry.is("psi.deferIconLoading", true)) {
            Icon icon = LastComputedIconCache.get(psiElement, i);
            if (icon == null) {
                icon = (Icon) AstLoadingFilter.disallowTreeLoading(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                    public final Object compute() {
                        return this.f$0.m6547xac440668(i);
                    }
                });
            }
            if (icon == null) {
                return null;
            }
            return IconManager.getInstance().createDeferredIcon(icon, new ElementIconRequest(psiElement, psiElement.getProject(), i), ICON_COMPUTE);
        }
        return computeIconNow(psiElement, i);
    }

    private static Icon computeIconNow(final PsiElement psiElement, final int i) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        return (Icon) AstLoadingFilter.disallowTreeLoading(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return ElementBase.doComputeIconNow(psiElement, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static Icon doComputeIconNow(PsiElement psiElement, int i) {
        if (psiElement == 0) {
            $$$reportNull$$$0(1);
        }
        Icon providersIcon = PsiIconUtil.getProvidersIcon(psiElement, i);
        if (providersIcon != null) {
            return providersIcon instanceof RowIcon ? providersIcon : IconManager.getInstance().createLayeredIcon(psiElement, providersIcon, i);
        }
        return ((ElementBase) psiElement).getElementIcon(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: computeBaseIcon, reason: merged with bridge method [inline-methods] */
    public Icon m6547xac440668(int i) {
        PsiFile containingFile;
        Icon adjustedBaseIcon = isVisibilitySupported() ? getAdjustedBaseIcon(getBaseIcon(), i) : getBaseIcon();
        return (!(this instanceof PsiElement) || (containingFile = ((PsiElement) this).getContainingFile()) == null) ? adjustedBaseIcon : IconManager.getInstance().createLayeredIcon(containingFile, adjustedBaseIcon, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Icon getBaseIcon() {
        PsiFile containingFile;
        if ((this instanceof PsiElement) && (containingFile = ((PsiElement) this).getContainingFile()) != null && !isNativeFileType(containingFile.getFileType())) {
            try {
                return containingFile.getFileType().getIcon();
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Throwable th) {
                LOG.error(th);
            }
        }
        return AllIcons.Nodes.NodePlaceholder;
    }

    public static boolean isNativeFileType(FileType fileType) {
        return ((fileType instanceof INativeFileType) && ((INativeFileType) fileType).useNativeIcon()) || (fileType instanceof UnknownFileType);
    }

    protected Icon getAdjustedBaseIcon(Icon icon, int i) {
        return BitUtil.isSet(i, 1) ? IconManager.getInstance().createRowIcon(icon, VISIBILITY_ICON_PLACEHOLDER.getValue()) : icon;
    }

    public static RowIcon buildRowIcon(Icon icon, Icon icon2) {
        RowIcon rowIconCreateRowIcon = IconManager.getInstance().createRowIcon(icon, icon2);
        if (rowIconCreateRowIcon == null) {
            $$$reportNull$$$0(2);
        }
        return rowIconCreateRowIcon;
    }

    public static Icon iconWithVisibilityIfNeeded(int i, Icon icon, Icon icon2) {
        return BitUtil.isSet(i, 1) ? buildRowIcon(icon, icon2) : icon;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ElementIconRequest {
        private final int myFlags;
        private final SmartPsiElementPointer<?> myPointer;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "project";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/ElementBase$ElementIconRequest";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private ElementIconRequest(PsiElement psiElement, Project project, int i) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            if (project == null) {
                $$$reportNull$$$0(1);
            }
            this.myPointer = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(psiElement);
            this.myFlags = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ElementIconRequest)) {
                return false;
            }
            ElementIconRequest elementIconRequest = (ElementIconRequest) obj;
            return this.myFlags == elementIconRequest.myFlags && this.myPointer.equals(elementIconRequest.myPointer);
        }

        public int hashCode() {
            return (this.myPointer.hashCode() * 31) + this.myFlags;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Icon getElementIcon(int i) {
        Icon icon;
        Icon icon2;
        PsiElement psiElement = (PsiElement) this;
        if (!psiElement.isValid()) {
            return null;
        }
        int i2 = (!BitUtil.isSet(i, 2) || psiElement.isWritable()) ? 0 : 2048;
        if ((psiElement instanceof ItemPresentation) && (icon2 = ((ItemPresentation) psiElement).getIcon(false)) != null) {
            return IconManager.getInstance().createLayeredIcon(this, icon2, i2);
        }
        if (psiElement instanceof PsiFile) {
            PsiFile psiFile = (PsiFile) psiElement;
            VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile == null) {
                icon = psiFile.getFileType().getIcon();
            } else {
                IconManager iconManager = IconManager.getInstance();
                if (iconManager instanceof CoreAwareIconManager) {
                    icon = ((CoreAwareIconManager) iconManager).getIcon(virtualFile, i & (-3), psiFile.getProject());
                }
            }
            return IconManager.getInstance().createLayeredIcon(this, icon, i2);
        }
        return null;
    }

    public static int transformFlags(PsiElement psiElement, int i) {
        int iClear = BitUtil.clear(i, 2);
        return (!BitUtil.isSet(i, 2) || psiElement.isWritable()) ? iClear : iClear | 2048;
    }
}
