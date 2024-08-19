package emi.testing_mod.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class LaserParticle extends AnimatedParticle {


    LaserParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.scale = 1.5F;
        this.velocityMultiplier = 0;
        this.ascending = true;
        this.maxAge = 60 + this.random.nextInt(12);
        //this.setTargetColor(15916745);
        this.setSpriteForAge(spriteProvider);
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick()
    {
        super.tick();
        //fadeOut();
    }

    private void fadeOut() {
        //linear fading out
        this.alpha = (-(1/(float)maxAge) * age + 1);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType>{
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet)
        {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new LaserParticle(world,x,y,z,velocityX,velocityY,velocityZ,this.sprites);
        }
    }

    public int getBrightness(float tint) {
        return 15728880;
    }

}
