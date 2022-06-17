package net.id.incubus_core.util;

import java.util.Random;

public class RandomShim extends Random {
    private final net.minecraft.util.math.random.Random random;
    
    public RandomShim(net.minecraft.util.math.random.Random random) {
        this.random = random;
    }
    
    @Override
    public synchronized void setSeed(long seed) {
        random.setSeed(seed);
    }
    
    @Override
    protected int next(int bits) {
        return random.nextInt(bits);
    }
    
    @Override
    public int nextInt() {
        return random.nextInt();
    }
    
    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
    
    @Override
    public long nextLong() {
        return random.nextLong();
    }
    
    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }
    
    @Override
    public float nextFloat() {
        return random.nextFloat();
    }
    
    @Override
    public double nextDouble() {
        return random.nextDouble();
    }
    
    @Override
    public synchronized double nextGaussian() {
        return random.nextGaussian();
    }
    
    @Override
    public int nextInt(int origin, int bound) {
        return random.nextBetweenExclusive(origin, bound);
    }
}
