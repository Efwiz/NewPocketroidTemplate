package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public abstract class MessageToMessageDecoder<I> extends ChannelInboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    protected abstract void decode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    protected MessageToMessageDecoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
    }

    protected MessageToMessageDecoder(Class<? extends I> cls) {
        this.matcher = TypeParameterMatcher.get(cls);
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        return this.matcher.match(obj);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        CodecOutputList codecOutputListNewInstance = CodecOutputList.newInstance();
        int i = 0;
        try {
            try {
                if (acceptInboundMessage(obj)) {
                    try {
                        decode(channelHandlerContext, obj, codecOutputListNewInstance);
                        ReferenceCountUtil.release(obj);
                    } catch (Throwable th) {
                        ReferenceCountUtil.release(obj);
                        throw th;
                    }
                } else {
                    codecOutputListNewInstance.add(obj);
                }
                int size = codecOutputListNewInstance.size();
                while (i < size) {
                    channelHandlerContext.fireChannelRead(codecOutputListNewInstance.getUnsafe(i));
                    i++;
                }
                codecOutputListNewInstance.recycle();
            } catch (DecoderException e) {
                throw e;
            } catch (Exception e2) {
                throw new DecoderException(e2);
            }
        } catch (Throwable th2) {
            int size2 = codecOutputListNewInstance.size();
            while (i < size2) {
                channelHandlerContext.fireChannelRead(codecOutputListNewInstance.getUnsafe(i));
                i++;
            }
            codecOutputListNewInstance.recycle();
            throw th2;
        }
    }
}
