package emi.testing_mod.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class LaserOffshootParticle extends SpriteBillboardParticle {


    protected LaserOffshootParticle(ClientWorld clientWorld, double d, double e, double f,SpriteProvider spriteSet, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
        this.velocityMultiplier = 0.6F;
        this.x = g;
        this.y = h;
        this.z = i;
        this.scale *= 0.75F;
        this.maxAge = 20;
        this.setSpriteForAge(spriteSet);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;

    }

    @Override
    public void tick() {
        super.tick();
        //fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)maxAge) * age + 1);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new LaserOffshootParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }

    public int getBrightness(float tint) {
        return 15728880;
    }


}
