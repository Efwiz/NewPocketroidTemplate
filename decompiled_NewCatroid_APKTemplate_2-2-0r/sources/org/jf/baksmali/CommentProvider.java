package org.jf.baksmali;

import java.util.Map;

/* JADX INFO: loaded from: classes6.dex */
public abstract class CommentProvider {
    public abstract String getComment(int i);

    public static class CommentMap extends CommentProvider {
        private final Map<Integer, String> map;

        public CommentMap(Map<Integer, String> map) {
            this.map = map;
        }

        @Override // org.jf.baksmali.CommentProvider
        public String getComment(int i) {
            return this.map.get(Integer.valueOf(i));
        }
    }
}
