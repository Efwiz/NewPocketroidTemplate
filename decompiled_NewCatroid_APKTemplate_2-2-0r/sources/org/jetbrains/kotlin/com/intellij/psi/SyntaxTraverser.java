package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Conditions;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.FilteredTraverserBase;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class SyntaxTraverser<T> extends FilteredTraverserBase<T, SyntaxTraverser<T>> implements UserDataHolder {
    public final Api<T> api;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
            case 23:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 5:
            case 6:
            case 12:
            case 14:
            case 18:
            case 20:
            case 22:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
            case 23:
                i2 = 3;
                break;
            case 5:
            case 6:
            case 12:
            case 14:
            case 18:
            case 20:
            case 22:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 3:
            case 7:
                objArr[0] = "builder";
                break;
            case 4:
            case 8:
                objArr[0] = "api";
                break;
            case 5:
            case 6:
            case 12:
            case 14:
            case 18:
            case 20:
            case 22:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser";
                break;
            case 9:
                objArr[0] = "meta";
                break;
            case 10:
            case 13:
                objArr[0] = "function";
                break;
            case 11:
                objArr[0] = "reverse";
                break;
            case 15:
            case 16:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 17:
            case 19:
            case 21:
                objArr[0] = "c";
                break;
            case 23:
                objArr[0] = "range";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "psiApiReversed";
                break;
            case 2:
                objArr[1] = "astApi";
                break;
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
            case 23:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser";
                break;
            case 5:
                objArr[1] = "psiTraverser";
                break;
            case 6:
                objArr[1] = "astTraverser";
                break;
            case 12:
            case 14:
                objArr[1] = "map";
                break;
            case 18:
                objArr[1] = "expandTypes";
                break;
            case 20:
                objArr[1] = "filterTypes";
                break;
            case 22:
                objArr[1] = "forceDisregardTypes";
                break;
            case 24:
                objArr[1] = "onRange";
                break;
            default:
                objArr[1] = "psiApi";
                break;
        }
        switch (i) {
            case 3:
                objArr[2] = "lightApi";
                break;
            case 4:
                objArr[2] = "syntaxTraverser";
                break;
            case 7:
                objArr[2] = "lightTraverser";
                break;
            case 8:
                objArr[2] = "<init>";
                break;
            case 9:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
            case 10:
            case 11:
            case 13:
                objArr[2] = "map";
                break;
            case 15:
                objArr[2] = "getUserData";
                break;
            case 16:
                objArr[2] = "putUserData";
                break;
            case 17:
                objArr[2] = "expandTypes";
                break;
            case 19:
                objArr[2] = "filterTypes";
                break;
            case 21:
                objArr[2] = "forceDisregardTypes";
                break;
            case 23:
                objArr[2] = "onRange";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
            case 23:
                throw new IllegalArgumentException(str2);
            case 5:
            case 6:
            case 12:
            case 14:
            case 18:
            case 20:
            case 22:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static ApiEx<PsiElement> psiApi() {
        ApiEx<PsiElement> apiEx = PsiApi.INSTANCE;
        if (apiEx == null) {
            $$$reportNull$$$0(0);
        }
        return apiEx;
    }

    public static ApiEx<PsiElement> psiApiReversed() {
        ApiEx<PsiElement> apiEx = PsiApi.INSTANCE_REV;
        if (apiEx == null) {
            $$$reportNull$$$0(1);
        }
        return apiEx;
    }

    public static ApiEx<ASTNode> astApi() {
        ASTApi aSTApi = ASTApi.INSTANCE;
        if (aSTApi == null) {
            $$$reportNull$$$0(2);
        }
        return aSTApi;
    }

    public static Api<LighterASTNode> lightApi(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(3);
        }
        return new LighterASTApi(psiBuilder);
    }

    public static <T> SyntaxTraverser<T> syntaxTraverser(Api<T> api) {
        if (api == null) {
            $$$reportNull$$$0(4);
        }
        return new SyntaxTraverser<>(api, null);
    }

    public static SyntaxTraverser<PsiElement> psiTraverser() {
        return new SyntaxTraverser<>(psiApi(), null);
    }

    public static SyntaxTraverser<PsiElement> psiTraverser(PsiElement psiElement) {
        SyntaxTraverser<T> syntaxTraverserWithRoot = psiTraverser().withRoot(psiElement);
        if (syntaxTraverserWithRoot == null) {
            $$$reportNull$$$0(5);
        }
        return syntaxTraverserWithRoot;
    }

    public static SyntaxTraverser<PsiElement> revPsiTraverser() {
        return new SyntaxTraverser<>(psiApiReversed(), null);
    }

    public static SyntaxTraverser<ASTNode> astTraverser() {
        return new SyntaxTraverser<>(astApi(), null);
    }

    public static SyntaxTraverser<ASTNode> astTraverser(ASTNode aSTNode) {
        SyntaxTraverser<T> syntaxTraverserWithRoot = astTraverser().withRoot(aSTNode);
        if (syntaxTraverserWithRoot == null) {
            $$$reportNull$$$0(6);
        }
        return syntaxTraverserWithRoot;
    }

    public static SyntaxTraverser<LighterASTNode> lightTraverser(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(7);
        }
        final LighterASTApi lighterASTApi = new LighterASTApi(psiBuilder);
        FilteredTraverserBase.Meta metaCreate = FilteredTraverserBase.Meta.create(lighterASTApi);
        Objects.requireNonNull(lighterASTApi);
        return new SyntaxTraverser<>(lighterASTApi, metaCreate.forceExpand(Conditions.compose(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return lighterASTApi.typeOf((LighterASTNode) obj);
            }
        }, Conditions.instanceOf(IFileElementType.class))).withRoots(JBIterable.of(lighterASTApi.getStructure().getRoot())));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    protected SyntaxTraverser(final Api<T> api, FilteredTraverserBase.Meta<T> meta) {
        if (api == null) {
            $$$reportNull$$$0(8);
        }
        if (meta == null) {
            FilteredTraverserBase.Meta metaCreate = FilteredTraverserBase.Meta.create(api);
            Objects.requireNonNull(api);
            meta = metaCreate.forceExpand(Conditions.compose(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return api.typeOf(obj);
                }
            }, Conditions.instanceOf(IFileElementType.class)));
        }
        super(meta);
        this.api = api;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.util.containers.FilteredTraverserBase
    public SyntaxTraverser<T> newInstance(FilteredTraverserBase.Meta<T> meta) {
        if (meta == null) {
            $$$reportNull$$$0(9);
        }
        return new SyntaxTraverser<>(this.api, meta);
    }

    public final <S> SyntaxTraverser<S> map(Function<? super T, ? extends S> function, Function<? super S, ? extends T> function2) {
        if (function == null) {
            $$$reportNull$$$0(10);
        }
        if (function2 == null) {
            $$$reportNull$$$0(11);
        }
        SyntaxTraverser<S> syntaxTraverser = (SyntaxTraverser) super.mapImpl(function, function2);
        if (syntaxTraverser == null) {
            $$$reportNull$$$0(12);
        }
        return syntaxTraverser;
    }

    public final <S> SyntaxTraverser<S> map(Function<? super T, ? extends S> function) {
        if (function == null) {
            $$$reportNull$$$0(13);
        }
        SyntaxTraverser<S> syntaxTraverser = (SyntaxTraverser) super.mapImpl(function);
        if (syntaxTraverser == null) {
            $$$reportNull$$$0(14);
        }
        return syntaxTraverser;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <K> K getUserData(Key<K> key) {
        if (key == null) {
            $$$reportNull$$$0(15);
        }
        return (K) getUserDataHolder().getUserData(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <K> void putUserData(Key<K> key, K k) {
        if (key == null) {
            $$$reportNull$$$0(16);
        }
        getUserDataHolder().putUserData(key, k);
    }

    private UserDataHolder getUserDataHolder() {
        Api<T> api = this.api;
        if (!(api instanceof LighterASTApi)) {
            return (UserDataHolder) api.parents(getRoot()).last();
        }
        return ((LighterASTApi) api).userDataHolder;
    }

    public SyntaxTraverser<T> expandTypes(Condition<? super IElementType> condition) {
        if (condition == null) {
            $$$reportNull$$$0(17);
        }
        SyntaxTraverser<T> syntaxTraverser = (SyntaxTraverser) super.expand(Conditions.compose(this.api.TO_TYPE, condition));
        if (syntaxTraverser == null) {
            $$$reportNull$$$0(18);
        }
        return syntaxTraverser;
    }

    public SyntaxTraverser<T> filterTypes(Condition<? super IElementType> condition) {
        if (condition == null) {
            $$$reportNull$$$0(19);
        }
        SyntaxTraverser<T> syntaxTraverser = (SyntaxTraverser) super.filter(Conditions.compose(this.api.TO_TYPE, condition));
        if (syntaxTraverser == null) {
            $$$reportNull$$$0(20);
        }
        return syntaxTraverser;
    }

    public SyntaxTraverser<T> forceDisregardTypes(Condition<? super IElementType> condition) {
        if (condition == null) {
            $$$reportNull$$$0(21);
        }
        SyntaxTraverser<T> syntaxTraverser = (SyntaxTraverser) super.forceDisregard(Conditions.compose(this.api.TO_TYPE, condition));
        if (syntaxTraverser == null) {
            $$$reportNull$$$0(22);
        }
        return syntaxTraverser;
    }

    public T getRawDeepestLast() {
        T tLast = (T) JBIterable.from(getRoots()).last();
        while (tLast != null) {
            JBIterable<T> jBIterableChildren = children(tLast);
            if (jBIterableChildren.isEmpty()) {
                return tLast;
            }
            tLast = jBIterableChildren.last();
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onRange$0$org-jetbrains-kotlin-com-intellij-psi-SyntaxTraverser, reason: not valid java name */
    /* synthetic */ boolean m6536xba449301(TextRange textRange, Object obj) {
        return this.api.rangeOf(obj).intersects(textRange);
    }

    public final SyntaxTraverser<T> onRange(final TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(23);
        }
        SyntaxTraverser<T> syntaxTraverserOnRange = onRange(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return this.f$0.m6536xba449301(textRange, obj);
            }
        });
        if (syntaxTraverserOnRange == null) {
            $$$reportNull$$$0(24);
        }
        return syntaxTraverserOnRange;
    }

    public static abstract class Api<T> implements Function<T, Iterable<? extends T>> {
        public final Function<T, IElementType> TO_TYPE = new Function<T, IElementType>() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api.1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public IElementType fun(T t) {
                return Api.this.typeOf(t);
            }

            public String toString() {
                return "TO_TYPE";
            }
        };
        public final Function<T, CharSequence> TO_TEXT = new Function<T, CharSequence>() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public CharSequence fun(T t) {
                return Api.this.textOf(t);
            }

            public String toString() {
                return "TO_TEXT";
            }
        };
        public final Function<T, TextRange> TO_RANGE = new Function<T, TextRange>() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api.3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public TextRange fun(T t) {
                return Api.this.rangeOf(t);
            }

            public String toString() {
                return "TO_RANGE";
            }
        };

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$Api", "parents"));
        }

        public abstract JBIterable<? extends T> children(T t);

        /* JADX INFO: renamed from: parent, reason: merged with bridge method [inline-methods] */
        public abstract T m6537xf2479dc2(T t);

        public abstract TextRange rangeOf(T t);

        public abstract CharSequence textOf(T t);

        public abstract IElementType typeOf(T t);

        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public JBIterable<? extends T> fun(T t) {
            return children(t);
        }

        public JBIterable<T> parents(T t) {
            JBIterable<T> jBIterableGenerate = JBIterable.generate(t, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser$Api$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6537xf2479dc2(obj);
                }
            });
            if (jBIterableGenerate == null) {
                $$$reportNull$$$0(0);
            }
            return jBIterableGenerate;
        }
    }

    public static abstract class ApiEx<T> extends Api<T> {
        private final Function<T, T> TO_NEXT = new Function<T, T>() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx.1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public T fun(T t) {
                return (T) ApiEx.this.next(t);
            }

            public String toString() {
                return "TO_NEXT";
            }
        };

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 4) ? 2 : 3];
            if (i == 1 || i == 2 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$ApiEx";
            } else {
                objArr[0] = "node";
            }
            if (i == 1 || i == 2) {
                objArr[1] = "children";
            } else if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$ApiEx";
            } else {
                objArr[1] = "siblings";
            }
            if (i != 1 && i != 2) {
                if (i == 3) {
                    objArr[2] = "siblings";
                } else if (i != 4) {
                    objArr[2] = "children";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        public abstract T first(T t);

        public abstract T last(T t);

        public abstract T next(T t);

        public abstract T previous(T t);

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public JBIterable<? extends T> children(T t) {
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            T tFirst = first(t);
            if (tFirst == null) {
                JBIterable<? extends T> jBIterableEmpty = JBIterable.empty();
                if (jBIterableEmpty == null) {
                    $$$reportNull$$$0(1);
                }
                return jBIterableEmpty;
            }
            JBIterable<? extends T> jBIterableSiblings = siblings(tFirst);
            if (jBIterableSiblings == null) {
                $$$reportNull$$$0(2);
            }
            return jBIterableSiblings;
        }

        public JBIterable<? extends T> siblings(T t) {
            if (t == null) {
                $$$reportNull$$$0(3);
            }
            JBIterable<? extends T> jBIterableGenerate = JBIterable.generate(t, this.TO_NEXT);
            if (jBIterableGenerate == null) {
                $$$reportNull$$$0(4);
            }
            return jBIterableGenerate;
        }
    }

    private static class PsiApi extends ApiEx<PsiElement> {
        static final ApiEx<PsiElement> INSTANCE = new PsiApi();
        static final ApiEx<PsiElement> INSTANCE_REV = new PsiApi() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                objArr[0] = "node";
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$PsiApi$1";
                if (i == 1) {
                    objArr[2] = ES6Iterator.NEXT_METHOD;
                } else if (i == 2) {
                    objArr[2] = "last";
                } else if (i != 3) {
                    objArr[2] = "previous";
                } else {
                    objArr[2] = "first";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
            /* JADX INFO: renamed from: parent */
            public /* bridge */ /* synthetic */ Object m6537xf2479dc2(Object obj) {
                return super.m6537xf2479dc2((PsiElement) obj);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
            public /* bridge */ /* synthetic */ TextRange rangeOf(Object obj) {
                return super.rangeOf((PsiElement) obj);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
            public /* bridge */ /* synthetic */ CharSequence textOf(Object obj) {
                return super.textOf((PsiElement) obj);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
            public /* bridge */ /* synthetic */ IElementType typeOf(Object obj) {
                return super.typeOf((PsiElement) obj);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
            public PsiElement previous(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(0);
                }
                return super.next(psiElement);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
            public PsiElement next(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(1);
                }
                return super.previous(psiElement);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
            public PsiElement last(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(2);
                }
                return super.first(psiElement);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.PsiApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
            public PsiElement first(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(3);
                }
                return super.last(psiElement);
            }
        };

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 5 || i == 7 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 5 || i == 7 || i == 9) ? 2 : 3];
            if (i == 5 || i == 7 || i == 9) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$PsiApi";
            } else {
                objArr[0] = "node";
            }
            if (i == 5) {
                objArr[1] = "typeOf";
            } else if (i == 7) {
                objArr[1] = "rangeOf";
            } else if (i != 9) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$PsiApi";
            } else {
                objArr[1] = "textOf";
            }
            switch (i) {
                case 1:
                    objArr[2] = "last";
                    break;
                case 2:
                    objArr[2] = ES6Iterator.NEXT_METHOD;
                    break;
                case 3:
                    objArr[2] = "previous";
                    break;
                case 4:
                    objArr[2] = "typeOf";
                    break;
                case 5:
                case 7:
                case 9:
                    break;
                case 6:
                    objArr[2] = "rangeOf";
                    break;
                case 8:
                    objArr[2] = "textOf";
                    break;
                case 10:
                    objArr[2] = "parent";
                    break;
                default:
                    objArr[2] = "first";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 5 && i != 7 && i != 9) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        private PsiApi() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public PsiElement first(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            return psiElement.getFirstChild();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public PsiElement last(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            return psiElement.getLastChild();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public PsiElement next(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(2);
            }
            return psiElement.getNextSibling();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public PsiElement previous(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(3);
            }
            return psiElement.getPrevSibling();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public IElementType typeOf(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(4);
            }
            IElementType elementType = PsiUtilCore.getElementType(psiElement);
            if (elementType == null) {
                elementType = IElementType.find((short) 0);
            }
            if (elementType == null) {
                $$$reportNull$$$0(5);
            }
            return elementType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public TextRange rangeOf(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(6);
            }
            TextRange textRange = psiElement.getTextRange();
            if (textRange == null) {
                $$$reportNull$$$0(7);
            }
            return textRange;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public CharSequence textOf(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(8);
            }
            String text = psiElement.getText();
            if (text == null) {
                $$$reportNull$$$0(9);
            }
            return text;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        /* JADX INFO: renamed from: parent, reason: merged with bridge method [inline-methods] */
        public PsiElement m6537xf2479dc2(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(10);
            }
            if (psiElement instanceof PsiFile) {
                return null;
            }
            return psiElement.getLightParent();
        }
    }

    private static class ASTApi extends ApiEx<ASTNode> {
        static final ASTApi INSTANCE = new ASTApi();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 5 || i == 7 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 5 || i == 7 || i == 9) ? 2 : 3];
            if (i == 5 || i == 7 || i == 9) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$ASTApi";
            } else {
                objArr[0] = "node";
            }
            if (i == 5) {
                objArr[1] = "typeOf";
            } else if (i == 7) {
                objArr[1] = "rangeOf";
            } else if (i != 9) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$ASTApi";
            } else {
                objArr[1] = "textOf";
            }
            switch (i) {
                case 1:
                    objArr[2] = "last";
                    break;
                case 2:
                    objArr[2] = ES6Iterator.NEXT_METHOD;
                    break;
                case 3:
                    objArr[2] = "previous";
                    break;
                case 4:
                    objArr[2] = "typeOf";
                    break;
                case 5:
                case 7:
                case 9:
                    break;
                case 6:
                    objArr[2] = "rangeOf";
                    break;
                case 8:
                    objArr[2] = "textOf";
                    break;
                case 10:
                    objArr[2] = "parent";
                    break;
                default:
                    objArr[2] = "first";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 5 && i != 7 && i != 9) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        private ASTApi() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public ASTNode first(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return aSTNode.getFirstChildNode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public ASTNode last(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(1);
            }
            return aSTNode.getLastChildNode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public ASTNode next(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(2);
            }
            return aSTNode.getTreeNext();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.ApiEx
        public ASTNode previous(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(3);
            }
            return aSTNode.getTreePrev();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public IElementType typeOf(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(4);
            }
            IElementType elementType = aSTNode.getElementType();
            if (elementType == null) {
                $$$reportNull$$$0(5);
            }
            return elementType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public TextRange rangeOf(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(6);
            }
            TextRange textRange = aSTNode.getTextRange();
            if (textRange == null) {
                $$$reportNull$$$0(7);
            }
            return textRange;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public CharSequence textOf(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(8);
            }
            String text = aSTNode.getText();
            if (text == null) {
                $$$reportNull$$$0(9);
            }
            return text;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public ASTNode m6537xf2479dc2(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(10);
            }
            return aSTNode.getTreeParent();
        }
    }

    private static abstract class FlyweightApi<T> extends Api<T> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = "node";
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$FlyweightApi";
            if (i != 1) {
                objArr[2] = "parent";
            } else {
                objArr[2] = "children";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        abstract FlyweightCapableTreeStructure<T> getStructure();

        private FlyweightApi() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        /* JADX INFO: renamed from: parent */
        public T m6537xf2479dc2(T t) {
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            return getStructure().getParent(t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public JBIterable<? extends T> children(final T t) {
            if (t == null) {
                $$$reportNull$$$0(1);
            }
            return new JBIterable<T>() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.FlyweightApi.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Iterable
                public Iterator<T> iterator() {
                    FlyweightCapableTreeStructure structure = FlyweightApi.this.getStructure();
                    Ref refCreate = Ref.create();
                    int children = structure.getChildren(t, refCreate);
                    if (children == 0) {
                        return Collections.emptyIterator();
                    }
                    Object[] objArr = (Object[]) refCreate.get();
                    LinkedList linkedList = new LinkedList();
                    for (int i = 0; i < children; i++) {
                        Object obj = objArr[i];
                        IElementType iElementTypeTypeOf = FlyweightApi.this.typeOf(obj);
                        if (iElementTypeTypeOf != TokenType.WHITE_SPACE && iElementTypeTypeOf != TokenType.BAD_CHARACTER) {
                            objArr[i] = null;
                            linkedList.addLast(obj);
                        }
                    }
                    structure.disposeChildren(objArr, children);
                    return linkedList.iterator();
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LighterASTApi extends FlyweightApi<LighterASTNode> {
        private final PsiBuilder builder;
        private final ThreadLocalCachedValue<FlyweightCapableTreeStructure<LighterASTNode>> structure;
        private final UserDataHolder userDataHolder;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 3 || i == 5 || i == 7) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 3 || i == 5 || i == 7) ? 3 : 2];
            if (i == 1 || i == 3 || i == 5 || i == 7) {
                objArr[0] = "node";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$LighterASTApi";
            }
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$LighterASTApi";
                    break;
                case 2:
                    objArr[1] = "typeOf";
                    break;
                case 4:
                    objArr[1] = "rangeOf";
                    break;
                case 6:
                    objArr[1] = "textOf";
                    break;
                default:
                    objArr[1] = "getStructure";
                    break;
            }
            if (i == 1) {
                objArr[2] = "typeOf";
            } else if (i == 3) {
                objArr[2] = "rangeOf";
            } else if (i == 5) {
                objArr[2] = "textOf";
            } else if (i == 7) {
                objArr[2] = "parent";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 3 && i != 5 && i != 7) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        LighterASTApi(PsiBuilder psiBuilder) {
            super();
            this.userDataHolder = new UserDataHolderBase();
            this.structure = new ThreadLocalCachedValue<FlyweightCapableTreeStructure<LighterASTNode>>() { // from class: org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.LighterASTApi.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/SyntaxTraverser$LighterASTApi$1", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME));
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedValue
                public FlyweightCapableTreeStructure<LighterASTNode> create() {
                    FlyweightCapableTreeStructure<LighterASTNode> lightTree = LighterASTApi.this.builder.getLightTree();
                    if (lightTree == null) {
                        $$$reportNull$$$0(0);
                    }
                    return lightTree;
                }
            };
            this.builder = psiBuilder;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.FlyweightApi
        FlyweightCapableTreeStructure<LighterASTNode> getStructure() {
            FlyweightCapableTreeStructure<LighterASTNode> value = this.structure.getValue();
            if (value == null) {
                $$$reportNull$$$0(0);
            }
            return value;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public IElementType typeOf(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(1);
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (tokenType == null) {
                $$$reportNull$$$0(2);
            }
            return tokenType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public TextRange rangeOf(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(3);
            }
            TextRange textRangeCreate = TextRange.create(lighterASTNode.getStartOffset(), lighterASTNode.getEndOffset());
            if (textRangeCreate == null) {
                $$$reportNull$$$0(4);
            }
            return textRangeCreate;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        public CharSequence textOf(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(5);
            }
            CharSequence charSequenceSubSequence = rangeOf(lighterASTNode).subSequence(this.builder.getOriginalText());
            if (charSequenceSubSequence == null) {
                $$$reportNull$$$0(6);
            }
            return charSequenceSubSequence;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.FlyweightApi, org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser.Api
        /* JADX INFO: renamed from: parent, reason: merged with bridge method [inline-methods] */
        public LighterASTNode m6537xf2479dc2(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(7);
            }
            if (lighterASTNode instanceof LighterASTTokenNode) {
                return null;
            }
            return (LighterASTNode) super.m6537xf2479dc2(lighterASTNode);
        }
    }
}
