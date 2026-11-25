package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes11.dex */
public final class ThreadLocalRandom extends Random {
    private static final long addend = 11;
    private static volatile long initialSeedUniquifier = 0;
    private static final long mask = 281474976710655L;
    private static final long multiplier = 25214903917L;
    private static volatile long seedGeneratorEndTime = 0;
    private static final long seedGeneratorStartTime;
    private static final Thread seedGeneratorThread;
    private static final BlockingQueue<Long> seedQueue;
    private static final long serialVersionUID = -5851777807851030925L;
    boolean initialized;
    private long pad0;
    private long pad1;
    private long pad2;
    private long pad3;
    private long pad4;
    private long pad5;
    private long pad6;
    private long pad7;
    private long rnd;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ThreadLocalRandom.class);
    private static final AtomicLong seedUniquifier = new AtomicLong();

    private static long mix64(long j) {
        long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
        long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
        return j3 ^ (j3 >>> 33);
    }

    static {
        initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L);
        if (initialSeedUniquifier == 0) {
            if (SystemPropertyUtil.getBoolean("java.util.secureRandomSeed", false)) {
                seedQueue = new LinkedBlockingQueue();
                seedGeneratorStartTime = System.nanoTime();
                Thread thread = new Thread("initialSeedUniquifierGenerator") { // from class: io.netty.util.internal.ThreadLocalRandom.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        byte[] bArrGenerateSeed = new SecureRandom().generateSeed(8);
                        long unused = ThreadLocalRandom.seedGeneratorEndTime = System.nanoTime();
                        ThreadLocalRandom.seedQueue.add(Long.valueOf(((((long) bArrGenerateSeed[0]) & 255) << 56) | ((((long) bArrGenerateSeed[1]) & 255) << 48) | ((((long) bArrGenerateSeed[2]) & 255) << 40) | ((((long) bArrGenerateSeed[3]) & 255) << 32) | ((((long) bArrGenerateSeed[4]) & 255) << 24) | ((((long) bArrGenerateSeed[5]) & 255) << 16) | ((((long) bArrGenerateSeed[6]) & 255) << 8) | (((long) bArrGenerateSeed[7]) & 255)));
                    }
                };
                seedGeneratorThread = thread;
                thread.setDaemon(true);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: io.netty.util.internal.ThreadLocalRandom.2
                    @Override // java.lang.Thread.UncaughtExceptionHandler
                    public void uncaughtException(Thread thread2, Throwable th) {
                        ThreadLocalRandom.logger.debug("An exception has been raised by {}", thread2.getName(), th);
                    }
                });
                thread.start();
                return;
            }
            initialSeedUniquifier = mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime());
            seedGeneratorThread = null;
            seedQueue = null;
            seedGeneratorStartTime = 0L;
            return;
        }
        seedGeneratorThread = null;
        seedQueue = null;
        seedGeneratorStartTime = 0L;
    }

    public static void setInitialSeedUniquifier(long j) {
        initialSeedUniquifier = j;
    }

    public static long getInitialSeedUniquifier() {
        Long lPoll;
        boolean z;
        long j = initialSeedUniquifier;
        if (j != 0) {
            return j;
        }
        synchronized (ThreadLocalRandom.class) {
            long jLongValue = initialSeedUniquifier;
            if (jLongValue != 0) {
                return jLongValue;
            }
            long nanos = seedGeneratorStartTime + TimeUnit.SECONDS.toNanos(3L);
            while (true) {
                long jNanoTime = nanos - System.nanoTime();
                if (jNanoTime <= 0) {
                    try {
                        lPoll = seedQueue.poll();
                    } catch (InterruptedException unused) {
                        logger.warn("Failed to generate a seed from SecureRandom due to an InterruptedException.");
                        z = true;
                    }
                } else {
                    lPoll = seedQueue.poll(jNanoTime, TimeUnit.NANOSECONDS);
                }
                z = false;
                if (lPoll != null) {
                    jLongValue = lPoll.longValue();
                    break;
                }
                if (jNanoTime <= 0) {
                    seedGeneratorThread.interrupt();
                    logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entropy?", (Object) 3L);
                    break;
                }
            }
            long jReverse = (jLongValue ^ 3627065505421648153L) ^ Long.reverse(System.nanoTime());
            initialSeedUniquifier = jReverse;
            if (z) {
                Thread.currentThread().interrupt();
                seedGeneratorThread.interrupt();
            }
            if (seedGeneratorEndTime == 0) {
                seedGeneratorEndTime = System.nanoTime();
            }
            return jReverse;
        }
    }

    private static long newSeed() {
        AtomicLong atomicLong;
        long j;
        long initialSeedUniquifier2;
        long j2;
        do {
            atomicLong = seedUniquifier;
            j = atomicLong.get();
            initialSeedUniquifier2 = j != 0 ? j : getInitialSeedUniquifier();
            j2 = 181783497276652981L * initialSeedUniquifier2;
        } while (!atomicLong.compareAndSet(j, j2));
        if (j == 0) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                if (seedGeneratorEndTime != 0) {
                    internalLogger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", Long.valueOf(initialSeedUniquifier2), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(seedGeneratorEndTime - seedGeneratorStartTime))));
                } else {
                    internalLogger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x", Long.valueOf(initialSeedUniquifier2)));
                }
            }
        }
        return System.nanoTime() ^ j2;
    }

    ThreadLocalRandom() {
        super(newSeed());
        this.initialized = true;
    }

    public static ThreadLocalRandom current() {
        return InternalThreadLocalMap.get().random();
    }

    @Override // java.util.Random
    public void setSeed(long j) {
        if (this.initialized) {
            throw new UnsupportedOperationException();
        }
        this.rnd = (j ^ multiplier) & mask;
    }

    @Override // java.util.Random
    protected int next(int i) {
        long j = ((this.rnd * multiplier) + addend) & mask;
        this.rnd = j;
        return (int) (j >>> (48 - i));
    }

    public int nextInt(int i, int i2) {
        if (i >= i2) {
            throw new IllegalArgumentException();
        }
        return nextInt(i2 - i) + i;
    }

    public long nextLong(long j) {
        long j2 = 0;
        if (j <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        while (j >= 2147483647L) {
            int next = next(2);
            long j3 = j >>> 1;
            if ((next & 2) != 0) {
                j3 = j - j3;
            }
            if ((next & 1) == 0) {
                j2 += j - j3;
            }
            j = j3;
        }
        return j2 + ((long) nextInt((int) j));
    }

    public long nextLong(long j, long j2) {
        if (j >= j2) {
            throw new IllegalArgumentException();
        }
        return nextLong(j2 - j) + j;
    }

    public double nextDouble(double d) {
        if (d <= 0.0d) {
            throw new IllegalArgumentException("n must be positive");
        }
        return nextDouble() * d;
    }

    public double nextDouble(double d, double d2) {
        if (d >= d2) {
            throw new IllegalArgumentException();
        }
        return (nextDouble() * (d2 - d)) + d;
    }
}
