package org.jetbrains.kotlin.fir.lightTree.fir.modifier;

import com.reandroid.arsc.chunk.SpecBlock;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: Modifier.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0012J\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0019J\u0006\u0010\u001a\u001a\u00020\u0012J\u0006\u0010\u001b\u001a\u00020\u0012J\u0006\u0010\u001c\u001a\u00020\u0012J\u0006\u0010\u001d\u001a\u00020\u0012J\u0006\u0010\u001e\u001a\u00020\u0012J\u0010\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!H\u0004J\u0006\u0010\"\u001a\u00020\u0012J\u0006\u0010#\u001a\u00020\u0012J\u0006\u0010$\u001a\u00020\u0012J\u000e\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020\u0014J\u0006\u0010'\u001a\u00020\u0012J\u0006\u0010(\u001a\u00020\u0012J\u0006\u0010)\u001a\u00020\u0012J\u0006\u0010*\u001a\u00020\u0012J\u0006\u0010+\u001a\u00020\u0012J\u0006\u0010,\u001a\u00020\u0012J\u0006\u0010-\u001a\u00020\u0012J\u0006\u0010.\u001a\u00020\u0012J\u0006\u0010/\u001a\u00020\u0012J\u0006\u00100\u001a\u00020\u0012J\u0006\u00101\u001a\u00020\u0012J\u0006\u00102\u001a\u00020\u0012J\u0006\u00103\u001a\u00020\u0012J\u0006\u00104\u001a\u00020\u0012J\u0012\u00105\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010!H\u0004J\b\u00106\u001a\u000207H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\u0004¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", "", "modifiers", "", "(J)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "getAnnotations", "()Ljava/util/List;", "getModifiers", "()J", "setModifiers", "addModifier", "", "modifier", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "isInClass", "", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isClassOrObject", "getVariance", "Lorg/jetbrains/kotlin/types/Variance;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "hasActual", "hasConst", "hasCrossinline", "hasExpect", "hasExternal", "hasFlag", SpecBlock.NAME_flag, "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/ModifierFlag;", "hasInfix", "hasInline", "hasLateinit", "hasModality", "modality", "hasNoinline", "hasOperator", "hasOverride", "hasSuspend", "hasTailrec", "hasVararg", "isAnnotation", "isCompanion", "isConst", "isDataClass", "isEnum", "isFunctionalInterface", "isInlineClass", "isInner", "setFlag", "toString", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class Modifier {
    private final List<FirAnnotationCall> annotations;
    private long modifiers;

    public Modifier() {
        this(0L, 1, null);
    }

    public Modifier(long j) {
        this.modifiers = j;
        this.annotations = new ArrayList();
    }

    public /* synthetic */ Modifier(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? ModifierFlag.NONE.getValue() : j);
    }

    public final long getModifiers() {
        return this.modifiers;
    }

    public final void setModifiers(long j) {
        this.modifiers = j;
    }

    public final List<FirAnnotationCall> getAnnotations() {
        return this.annotations;
    }

    public static /* synthetic */ void addModifier$default(Modifier modifier, LighterASTNode lighterASTNode, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addModifier");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        modifier.addModifier(lighterASTNode, z);
    }

    public final void addModifier(LighterASTNode modifier, boolean isInClass) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        IElementType tokenType = modifier.getTokenType();
        if (Intrinsics.areEqual(tokenType, KtTokens.CONST_KEYWORD)) {
            setFlag(ModifierFlag.PROPERTY_CONST);
            setFlag(ModifierFlag.PARAMETER_CONST);
        } else {
            if (Intrinsics.areEqual(tokenType, KtTokens.INLINE_KEYWORD) ? true : Intrinsics.areEqual(tokenType, KtTokens.VALUE_KEYWORD)) {
                setFlag(isInClass ? ModifierFlag.CLASS_INLINE : ModifierFlag.FUNCTION_INLINE);
            } else {
                setFlag(ModifierFlag.INSTANCE.getElementTypeToModifierFlagMap().get(tokenType));
            }
        }
    }

    public final boolean isEnum() {
        return hasFlag(ModifierFlag.CLASS_ENUM);
    }

    public final boolean isAnnotation() {
        return hasFlag(ModifierFlag.CLASS_ANNOTATION);
    }

    public final boolean isDataClass() {
        return hasFlag(ModifierFlag.CLASS_DATA);
    }

    public final boolean isInlineClass() {
        return hasFlag(ModifierFlag.CLASS_INLINE);
    }

    public final boolean isInner() {
        return hasFlag(ModifierFlag.CLASS_INNER);
    }

    public final boolean isCompanion() {
        return hasFlag(ModifierFlag.CLASS_COMPANION);
    }

    public final boolean isFunctionalInterface() {
        return hasFlag(ModifierFlag.CLASS_FUN);
    }

    public final boolean hasOverride() {
        return hasFlag(ModifierFlag.MEMBER_OVERRIDE);
    }

    public final boolean hasLateinit() {
        return hasFlag(ModifierFlag.MEMBER_LATEINIT);
    }

    public final Visibility getVisibility() {
        if (hasFlag(ModifierFlag.VISIBILITY_PRIVATE)) {
            return Visibilities.Private.INSTANCE;
        }
        if (hasFlag(ModifierFlag.VISIBILITY_PUBLIC)) {
            return Visibilities.Public.INSTANCE;
        }
        if (hasFlag(ModifierFlag.VISIBILITY_PROTECTED)) {
            return Visibilities.Protected.INSTANCE;
        }
        return hasFlag(ModifierFlag.VISIBILITY_INTERNAL) ? Visibilities.Internal.INSTANCE : Visibilities.Unknown.INSTANCE;
    }

    public final boolean hasTailrec() {
        return hasFlag(ModifierFlag.FUNCTION_TAILREC);
    }

    public final boolean hasOperator() {
        return hasFlag(ModifierFlag.FUNCTION_OPERATOR);
    }

    public final boolean hasInfix() {
        return hasFlag(ModifierFlag.FUNCTION_INFIX);
    }

    public final boolean hasInline() {
        return hasFlag(ModifierFlag.FUNCTION_INLINE);
    }

    public final boolean hasExternal() {
        return hasFlag(ModifierFlag.FUNCTION_EXTERNAL);
    }

    public final boolean hasSuspend() {
        return hasFlag(ModifierFlag.FUNCTION_SUSPEND);
    }

    public final boolean isConst() {
        return hasFlag(ModifierFlag.PROPERTY_CONST);
    }

    public final boolean hasModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "modality");
        if (modality == Modality.FINAL && hasFlag(ModifierFlag.INHERITANCE_FINAL)) {
            return true;
        }
        if (modality == Modality.SEALED && hasFlag(ModifierFlag.INHERITANCE_SEALED)) {
            return true;
        }
        if (modality == Modality.ABSTRACT && hasFlag(ModifierFlag.INHERITANCE_ABSTRACT)) {
            return true;
        }
        return modality == Modality.OPEN && hasFlag(ModifierFlag.INHERITANCE_OPEN);
    }

    public final Modality getModality(boolean isClassOrObject) {
        if (hasFlag(ModifierFlag.INHERITANCE_FINAL)) {
            return Modality.FINAL;
        }
        if (hasFlag(ModifierFlag.INHERITANCE_SEALED)) {
            if (isClassOrObject) {
                return Modality.SEALED;
            }
        } else {
            if (hasFlag(ModifierFlag.INHERITANCE_ABSTRACT)) {
                return Modality.ABSTRACT;
            }
            if (hasFlag(ModifierFlag.INHERITANCE_OPEN)) {
                return Modality.OPEN;
            }
        }
        return null;
    }

    public final Variance getVariance() {
        if (hasFlag(ModifierFlag.VARIANCE_IN)) {
            return Variance.IN_VARIANCE;
        }
        return hasFlag(ModifierFlag.VARIANCE_OUT) ? Variance.OUT_VARIANCE : Variance.INVARIANT;
    }

    public final boolean hasVararg() {
        return hasFlag(ModifierFlag.PARAMETER_VARARG);
    }

    public final boolean hasNoinline() {
        return hasFlag(ModifierFlag.PARAMETER_NOINLINE);
    }

    public final boolean hasCrossinline() {
        return hasFlag(ModifierFlag.PARAMETER_CROSSINLINE);
    }

    public final boolean hasExpect() {
        return hasFlag(ModifierFlag.PLATFORM_EXPECT) || hasFlag(ModifierFlag.PLATFORM_HEADER);
    }

    public final boolean hasActual() {
        return hasFlag(ModifierFlag.PLATFORM_ACTUAL) || hasFlag(ModifierFlag.PLATFORM_IMPL);
    }

    public final boolean hasConst() {
        return hasFlag(ModifierFlag.PARAMETER_CONST);
    }

    protected final boolean hasFlag(ModifierFlag flag) {
        Intrinsics.checkNotNullParameter(flag, "flag");
        return (this.modifiers & flag.getValue()) == flag.getValue();
    }

    protected final void setFlag(ModifierFlag flag) {
        if (flag != null) {
            this.modifiers |= flag.getValue();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (ModifierFlag modifierFlag : ModifierFlag.INSTANCE.getValues()) {
            if (hasFlag(modifierFlag) && modifierFlag != ModifierFlag.NONE) {
                if (z) {
                    z = false;
                } else {
                    sb.append(" ");
                }
                sb.append(modifierFlag.name());
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
