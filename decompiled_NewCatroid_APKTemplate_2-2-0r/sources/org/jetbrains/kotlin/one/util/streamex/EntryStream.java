package org.jetbrains.kotlin.one.util.streamex;

import com.android.tools.r8.internal.YF$$ExternalSyntheticLambda1;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes2.dex */
public class EntryStream<K, V> extends AbstractStreamEx<Map.Entry<K, V>, EntryStream<K, V>> {
    EntryStream(Stream<? extends Map.Entry<K, V>> stream, StreamContext streamContext) {
        super(stream, streamContext);
    }

    EntryStream(Spliterator<? extends Map.Entry<K, V>> spliterator, StreamContext streamContext) {
        super(spliterator, streamContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx
    public EntryStream<K, V> supply(Stream<Map.Entry<K, V>> stream) {
        return new EntryStream<>(stream, this.context);
    }

    public EntryStream<K, V> filterValues(final Predicate<? super V> predicate) {
        return (EntryStream) filter(new Predicate() { // from class: org.jetbrains.kotlin.one.util.streamex.EntryStream$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return predicate.test(((Map.Entry) obj).getValue());
            }
        });
    }

    public StreamEx<K> keys() {
        return map((Function) new YF$$ExternalSyntheticLambda1());
    }

    public static <K, V> EntryStream<K, V> of(Stream<? extends Map.Entry<K, V>> stream) {
        if (stream instanceof AbstractStreamEx) {
            AbstractStreamEx abstractStreamEx = (AbstractStreamEx) stream;
            if (abstractStreamEx.spliterator != 0) {
                return new EntryStream<>(abstractStreamEx.spliterator(), abstractStreamEx.context);
            }
            return new EntryStream<>(abstractStreamEx.stream(), abstractStreamEx.context);
        }
        return new EntryStream<>(stream, StreamContext.of(stream));
    }

    public static <K, V> EntryStream<K, V> of(Map<K, V> map) {
        return of((Stream) map.entrySet().stream());
    }
}
