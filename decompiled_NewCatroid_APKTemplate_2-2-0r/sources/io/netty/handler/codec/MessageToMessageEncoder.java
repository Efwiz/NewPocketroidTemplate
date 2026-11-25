package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.PromiseCombiner;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public abstract class MessageToMessageEncoder<I> extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    protected abstract void encode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    protected MessageToMessageEncoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
    }

    protected MessageToMessageEncoder(Class<? extends I> cls) {
        this.matcher = TypeParameterMatcher.get(cls);
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return this.matcher.match(obj);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        CodecOutputList codecOutputList = null;
        try {
            try {
                if (acceptOutboundMessage(obj)) {
                    try {
                        CodecOutputList codecOutputListNewInstance = CodecOutputList.newInstance();
                        try {
                            encode(channelHandlerContext, obj, codecOutputListNewInstance);
                            ReferenceCountUtil.release(obj);
                            if (codecOutputListNewInstance.isEmpty()) {
                                codecOutputListNewInstance.recycle();
                                throw new EncoderException(StringUtil.simpleClassName(this) + " must produce at least one message.");
                            }
                            codecOutputList = codecOutputListNewInstance;
                        } catch (Throwable th) {
                            ReferenceCountUtil.release(obj);
                            throw th;
                        }
                    } catch (EncoderException e) {
                        throw e;
                    } catch (Throwable th2) {
                        th = th2;
                        throw new EncoderException(th);
                    }
                } else {
                    channelHandlerContext.write(obj, channelPromise);
                }
                if (codecOutputList != null) {
                    int size = codecOutputList.size() - 1;
                    if (size == 0) {
                        channelHandlerContext.write(codecOutputList.getUnsafe(0), channelPromise);
                    } else if (size > 0) {
                        if (channelPromise == channelHandlerContext.voidPromise()) {
                            writeVoidPromise(channelHandlerContext, codecOutputList);
                        } else {
                            writePromiseCombiner(channelHandlerContext, codecOutputList, channelPromise);
                        }
                    }
                    codecOutputList.recycle();
                }
            } catch (Throwable th3) {
                if (0 != 0) {
                    int size2 = codecOutputList.size() - 1;
                    if (size2 == 0) {
                        channelHandlerContext.write(codecOutputList.getUnsafe(0), channelPromise);
                    } else if (size2 > 0) {
                        if (channelPromise == channelHandlerContext.voidPromise()) {
                            writeVoidPromise(channelHandlerContext, null);
                        } else {
                            writePromiseCombiner(channelHandlerContext, null, channelPromise);
                        }
                    }
                    codecOutputList.recycle();
                }
                throw th3;
            }
        } catch (EncoderException e2) {
            throw e2;
        } catch (Throwable th4) {
            th = th4;
        }
    }

    private static void writeVoidPromise(ChannelHandlerContext channelHandlerContext, CodecOutputList codecOutputList) {
        ChannelPromise channelPromiseVoidPromise = channelHandlerContext.voidPromise();
        for (int i = 0; i < codecOutputList.size(); i++) {
            channelHandlerContext.write(codecOutputList.getUnsafe(i), channelPromiseVoidPromise);
        }
    }

    private static void writePromiseCombiner(ChannelHandlerContext channelHandlerContext, CodecOutputList codecOutputList, ChannelPromise channelPromise) {
        PromiseCombiner promiseCombiner = new PromiseCombiner(channelHandlerContext.executor());
        for (int i = 0; i < codecOutputList.size(); i++) {
            promiseCombiner.add(channelHandlerContext.write(codecOutputList.getUnsafe(i)));
        }
        promiseCombiner.finish(channelPromise);
    }
}
