package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: compiled from: LightParameter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u00182\u00020\u00012\u00020\u0002:\u0001\u0018B3\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0015\u001a\u00020\u0004H\u0016J\b\u0010\u0016\u001a\u00020\fH\u0016J\b\u0010\u0017\u001a\u00020\u0004H\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/LightParameter;", "Lorg/jetbrains/kotlin/asJava/elements/LightVariableBuilder;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "myName", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "method", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "language", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "myVarArgs", "", "(Ljava/lang/String;Lcom/intellij/psi/PsiType;Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;Lcom/intellij/lang/Language;Z)V", "getMethod", "()Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "getDeclarationScope", "getName", "isVarArgs", "toString", "Companion", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LightParameter extends LightVariableBuilder implements PsiParameter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final LightParameter[] EMPTY_ARRAY = new LightParameter[0];
    private final KtLightMethod method;
    private final String myName;
    private final boolean myVarArgs;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightParameter(String myName, PsiType type, KtLightMethod method, Language language) {
        this(myName, type, method, language, false, 16, null);
        Intrinsics.checkNotNullParameter(myName, "myName");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(method, "method");
    }

    public final KtLightMethod getMethod() {
        return this.method;
    }

    public /* synthetic */ LightParameter(String str, PsiType psiType, KtLightMethod ktLightMethod, Language language, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, psiType, ktLightMethod, language, (i & 16) != 0 ? psiType instanceof PsiEllipsisType : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightParameter(String myName, PsiType type, KtLightMethod method, Language language, boolean z) {
        super(method.getManager(), myName, type, language);
        Intrinsics.checkNotNullParameter(myName, "myName");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(method, "method");
        this.myName = myName;
        this.method = method;
        this.myVarArgs = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: getDeclarationScope, reason: from getter */
    public KtLightMethod getMethod() {
        return this.method;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitParameter(this);
        }
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Light Parameter";
    }

    /* JADX INFO: renamed from: isVarArgs, reason: from getter */
    public boolean getMyVarArgs() {
        return this.myVarArgs;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return this.myName;
    }

    /* JADX INFO: compiled from: LightParameter.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/LightParameter$Companion;", "", "()V", "EMPTY_ARRAY", "", "Lorg/jetbrains/kotlin/asJava/elements/LightParameter;", "getEMPTY_ARRAY", "()[Lorg/jetbrains/kotlin/asJava/elements/LightParameter;", "[Lorg/jetbrains/kotlin/asJava/elements/LightParameter;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LightParameter[] getEMPTY_ARRAY() {
            return LightParameter.EMPTY_ARRAY;
        }
    }
}
