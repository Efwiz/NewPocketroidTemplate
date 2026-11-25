package com.squareup.moshi;

import com.squareup.moshi.internal.Util;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes11.dex */
public final class Moshi {
    static final List<JsonAdapter.Factory> BUILT_IN_FACTORIES;
    private final List<JsonAdapter.Factory> factories;
    private final ThreadLocal<LookupChain> lookupChainThreadLocal = new ThreadLocal<>();
    private final Map<Object, JsonAdapter<?>> adapterCache = new LinkedHashMap();

    static {
        ArrayList arrayList = new ArrayList(5);
        BUILT_IN_FACTORIES = arrayList;
        arrayList.add(StandardJsonAdapters.FACTORY);
        arrayList.add(CollectionJsonAdapter.FACTORY);
        arrayList.add(MapJsonAdapter.FACTORY);
        arrayList.add(ArrayJsonAdapter.FACTORY);
        arrayList.add(ClassJsonAdapter.FACTORY);
    }

    Moshi(Builder builder) {
        int size = builder.factories.size();
        List<JsonAdapter.Factory> list = BUILT_IN_FACTORIES;
        ArrayList arrayList = new ArrayList(size + list.size());
        arrayList.addAll(builder.factories);
        arrayList.addAll(list);
        this.factories = Collections.unmodifiableList(arrayList);
    }

    @CheckReturnValue
    public <T> JsonAdapter<T> adapter(Type type) {
        return adapter(type, Util.NO_ANNOTATIONS);
    }

    @CheckReturnValue
    public <T> JsonAdapter<T> adapter(Class<T> cls) {
        return adapter(cls, Util.NO_ANNOTATIONS);
    }

    @CheckReturnValue
    public <T> JsonAdapter<T> adapter(Type type, Class<? extends Annotation> cls) {
        if (cls == null) {
            throw new NullPointerException("annotationType == null");
        }
        return adapter(type, Collections.singleton(Types.createJsonQualifierImplementation(cls)));
    }

    @CheckReturnValue
    public <T> JsonAdapter<T> adapter(Type type, Class<? extends Annotation>... clsArr) {
        if (clsArr.length == 1) {
            return adapter(type, clsArr[0]);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(clsArr.length);
        for (Class<? extends Annotation> cls : clsArr) {
            linkedHashSet.add(Types.createJsonQualifierImplementation(cls));
        }
        return adapter(type, Collections.unmodifiableSet(linkedHashSet));
    }

    @CheckReturnValue
    public <T> JsonAdapter<T> adapter(Type type, Set<? extends Annotation> set) {
        return adapter(type, set, null);
    }

    @CheckReturnValue
    public <T> JsonAdapter<T> adapter(Type type, Set<? extends Annotation> set, @Nullable String str) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        if (set == null) {
            throw new NullPointerException("annotations == null");
        }
        Type typeCanonicalize = Util.canonicalize(type);
        Object objCacheKey = cacheKey(typeCanonicalize, set);
        synchronized (this.adapterCache) {
            JsonAdapter<T> jsonAdapter = (JsonAdapter) this.adapterCache.get(objCacheKey);
            if (jsonAdapter != null) {
                return jsonAdapter;
            }
            LookupChain lookupChain = this.lookupChainThreadLocal.get();
            if (lookupChain == null) {
                lookupChain = new LookupChain();
                this.lookupChainThreadLocal.set(lookupChain);
            }
            JsonAdapter<T> jsonAdapterPush = lookupChain.push(typeCanonicalize, str, objCacheKey);
            try {
                if (jsonAdapterPush == null) {
                    try {
                        int size = this.factories.size();
                        for (int i = 0; i < size; i++) {
                            JsonAdapter<T> jsonAdapter2 = (JsonAdapter<T>) this.factories.get(i).create(typeCanonicalize, set, this);
                            if (jsonAdapter2 != null) {
                                lookupChain.adapterFound(jsonAdapter2);
                                lookupChain.pop(true);
                                return jsonAdapter2;
                            }
                        }
                        throw new IllegalArgumentException("No JsonAdapter for " + Util.typeAnnotatedWithAnnotations(typeCanonicalize, set));
                    } catch (IllegalArgumentException e) {
                        throw lookupChain.exceptionWithLookupStack(e);
                    }
                }
                lookupChain.pop(false);
                return jsonAdapterPush;
            } catch (Throwable th) {
                lookupChain.pop(false);
                throw th;
            }
        }
    }

    @CheckReturnValue
    public <T> JsonAdapter<T> nextAdapter(JsonAdapter.Factory factory, Type type, Set<? extends Annotation> set) {
        if (set == null) {
            throw new NullPointerException("annotations == null");
        }
        Type typeCanonicalize = Util.canonicalize(type);
        int iIndexOf = this.factories.indexOf(factory);
        if (iIndexOf == -1) {
            throw new IllegalArgumentException("Unable to skip past unknown factory " + factory);
        }
        int size = this.factories.size();
        for (int i = iIndexOf + 1; i < size; i++) {
            JsonAdapter<T> jsonAdapter = (JsonAdapter<T>) this.factories.get(i).create(typeCanonicalize, set, this);
            if (jsonAdapter != null) {
                return jsonAdapter;
            }
        }
        throw new IllegalArgumentException("No next JsonAdapter for " + Util.typeAnnotatedWithAnnotations(typeCanonicalize, set));
    }

    @CheckReturnValue
    public Builder newBuilder() {
        return new Builder().addAll(this.factories.subList(0, this.factories.size() - BUILT_IN_FACTORIES.size()));
    }

    private Object cacheKey(Type type, Set<? extends Annotation> set) {
        return set.isEmpty() ? type : Arrays.asList(type, set);
    }

    public static final class Builder {
        final List<JsonAdapter.Factory> factories = new ArrayList();

        public <T> Builder add(final Type type, final JsonAdapter<T> jsonAdapter) {
            if (type == null) {
                throw new IllegalArgumentException("type == null");
            }
            if (jsonAdapter == null) {
                throw new IllegalArgumentException("jsonAdapter == null");
            }
            return add(new JsonAdapter.Factory() { // from class: com.squareup.moshi.Moshi.Builder.1
                @Override // com.squareup.moshi.JsonAdapter.Factory
                @Nullable
                public JsonAdapter<?> create(Type type2, Set<? extends Annotation> set, Moshi moshi) {
                    if (set.isEmpty() && Util.typesMatch(type, type2)) {
                        return jsonAdapter;
                    }
                    return null;
                }
            });
        }

        public <T> Builder add(final Type type, final Class<? extends Annotation> cls, final JsonAdapter<T> jsonAdapter) {
            if (type == null) {
                throw new IllegalArgumentException("type == null");
            }
            if (cls == null) {
                throw new IllegalArgumentException("annotation == null");
            }
            if (jsonAdapter == null) {
                throw new IllegalArgumentException("jsonAdapter == null");
            }
            if (!cls.isAnnotationPresent(JsonQualifier.class)) {
                throw new IllegalArgumentException(cls + " does not have @JsonQualifier");
            }
            if (cls.getDeclaredMethods().length > 0) {
                throw new IllegalArgumentException("Use JsonAdapter.Factory for annotations with elements");
            }
            return add(new JsonAdapter.Factory() { // from class: com.squareup.moshi.Moshi.Builder.2
                @Override // com.squareup.moshi.JsonAdapter.Factory
                @Nullable
                public JsonAdapter<?> create(Type type2, Set<? extends Annotation> set, Moshi moshi) {
                    if (Util.typesMatch(type, type2) && set.size() == 1 && Util.isAnnotationPresent(set, cls)) {
                        return jsonAdapter;
                    }
                    return null;
                }
            });
        }

        public Builder add(JsonAdapter.Factory factory) {
            if (factory == null) {
                throw new IllegalArgumentException("factory == null");
            }
            this.factories.add(factory);
            return this;
        }

        public Builder add(Object obj) {
            if (obj == null) {
                throw new IllegalArgumentException("adapter == null");
            }
            return add((JsonAdapter.Factory) AdapterMethodsFactory.get(obj));
        }

        Builder addAll(List<JsonAdapter.Factory> list) {
            this.factories.addAll(list);
            return this;
        }

        @CheckReturnValue
        public Moshi build() {
            return new Moshi(this);
        }
    }

    final class LookupChain {
        boolean exceptionAnnotated;
        final List<Lookup<?>> callLookups = new ArrayList();
        final Deque<Lookup<?>> stack = new ArrayDeque();

        LookupChain() {
        }

        <T> JsonAdapter<T> push(Type type, @Nullable String str, Object obj) {
            int size = this.callLookups.size();
            for (int i = 0; i < size; i++) {
                Lookup<?> lookup = this.callLookups.get(i);
                if (lookup.cacheKey.equals(obj)) {
                    this.stack.add(lookup);
                    return lookup.adapter != null ? (JsonAdapter<T>) lookup.adapter : lookup;
                }
            }
            Lookup<?> lookup2 = new Lookup<>(type, str, obj);
            this.callLookups.add(lookup2);
            this.stack.add(lookup2);
            return null;
        }

        <T> void adapterFound(JsonAdapter<T> jsonAdapter) {
            this.stack.getLast().adapter = jsonAdapter;
        }

        void pop(boolean z) {
            this.stack.removeLast();
            if (this.stack.isEmpty()) {
                Moshi.this.lookupChainThreadLocal.remove();
                if (z) {
                    synchronized (Moshi.this.adapterCache) {
                        int size = this.callLookups.size();
                        for (int i = 0; i < size; i++) {
                            Lookup<?> lookup = this.callLookups.get(i);
                            JsonAdapter<T> jsonAdapter = (JsonAdapter) Moshi.this.adapterCache.put(lookup.cacheKey, lookup.adapter);
                            if (jsonAdapter != 0) {
                                lookup.adapter = jsonAdapter;
                                Moshi.this.adapterCache.put(lookup.cacheKey, jsonAdapter);
                            }
                        }
                    }
                }
            }
        }

        IllegalArgumentException exceptionWithLookupStack(IllegalArgumentException illegalArgumentException) {
            if (this.exceptionAnnotated) {
                return illegalArgumentException;
            }
            this.exceptionAnnotated = true;
            if (this.stack.size() == 1 && this.stack.getFirst().fieldName == null) {
                return illegalArgumentException;
            }
            StringBuilder sb = new StringBuilder(illegalArgumentException.getMessage());
            Iterator<Lookup<?>> itDescendingIterator = this.stack.descendingIterator();
            while (itDescendingIterator.hasNext()) {
                Lookup<?> next = itDescendingIterator.next();
                sb.append("\nfor ").append(next.type);
                if (next.fieldName != null) {
                    sb.append(StringUtil.SPACE).append(next.fieldName);
                }
            }
            return new IllegalArgumentException(sb.toString(), illegalArgumentException);
        }
    }

    static final class Lookup<T> extends JsonAdapter<T> {

        @Nullable
        JsonAdapter<T> adapter;
        final Object cacheKey;

        @Nullable
        final String fieldName;
        final Type type;

        Lookup(Type type, @Nullable String str, Object obj) {
            this.type = type;
            this.fieldName = str;
            this.cacheKey = obj;
        }

        @Override // com.squareup.moshi.JsonAdapter
        public T fromJson(JsonReader jsonReader) throws IOException {
            JsonAdapter<T> jsonAdapter = this.adapter;
            if (jsonAdapter == null) {
                throw new IllegalStateException("JsonAdapter isn't ready");
            }
            return jsonAdapter.fromJson(jsonReader);
        }

        @Override // com.squareup.moshi.JsonAdapter
        public void toJson(JsonWriter jsonWriter, T t) throws IOException {
            JsonAdapter<T> jsonAdapter = this.adapter;
            if (jsonAdapter == null) {
                throw new IllegalStateException("JsonAdapter isn't ready");
            }
            jsonAdapter.toJson(jsonWriter, t);
        }

        public String toString() {
            JsonAdapter<T> jsonAdapter = this.adapter;
            return jsonAdapter != null ? jsonAdapter.toString() : super.toString();
        }
    }
}
