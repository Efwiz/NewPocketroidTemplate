package org.jetbrains.kotlin.js.translate.general;

import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractTranslator {
    private final TranslationContext context;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/general/AbstractTranslator";
        } else {
            objArr[0] = "context";
        }
        if (i == 1) {
            objArr[1] = ConfigConstants.CONFIG_KEY_PROGRAM;
        } else if (i == 2) {
            objArr[1] = "context";
        } else if (i == 3) {
            objArr[1] = "bindingContext";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/general/AbstractTranslator";
        } else {
            objArr[1] = "namer";
        }
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected AbstractTranslator(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(0);
        }
        this.context = translationContext;
    }

    protected JsProgram program() {
        JsProgram jsProgramProgram = this.context.program();
        if (jsProgramProgram == null) {
            $$$reportNull$$$0(1);
        }
        return jsProgramProgram;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TranslationContext context() {
        TranslationContext translationContext = this.context;
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        return translationContext;
    }

    protected BindingContext bindingContext() {
        BindingContext bindingContext = this.context.bindingContext();
        if (bindingContext == null) {
            $$$reportNull$$$0(3);
        }
        return bindingContext;
    }

    protected Namer namer() {
        Namer namer = this.context.namer();
        if (namer == null) {
            $$$reportNull$$$0(4);
        }
        return namer;
    }
}
