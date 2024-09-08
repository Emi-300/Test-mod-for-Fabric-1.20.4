package emi.testing_mod.block.entity.renderer;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.entity.LaserBlockEntity;
import emi.testing_mod.render.ModRenderLayers;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3i;

import java.io.File;
import java.io.FileNotFoundException;


public class LaserBlockEntityRenderer implements BlockEntityRenderer<LaserBlockEntity> {

    public static final Identifier OBJ_FILE = new Identifier(Testing_mod.MOD_ID,"models/obj/icosphere.obj");
    public static final Identifier OBJ_FILE_2 = new Identifier(Testing_mod.MOD_ID,"models/obj/cylinder.obj");
    public static final ResourceFinder OBJ_FILE_PATH = new ResourceFinder(Testing_mod.MOD_ID,"");

    public static final Identifier NOISE_TEXTURE_1 = new Identifier(Testing_mod.MOD_ID,"textures/noise/tiled_noise.png");

    public String s = System.getProperty("user.dir");

    public File objFile;
    public File objFile2;
    public static ObjFileReader fileReader;
    public static ObjFileReader fileReader2;

    public static final float size = 0.45f;

    public float length;

    public LaserBlockEntityRenderer(BlockEntityRendererFactory.Context context){
        s = s.substring(0,(s.length()-3)) + "src\\main\\resources\\assets\\";

        objFile = new File(s + OBJ_FILE_PATH.toResourcePath(OBJ_FILE).getPath());
        objFile2 = new File(s + OBJ_FILE_PATH.toResourcePath(OBJ_FILE_2).getPath());


    }

    @Override
    public void render(LaserBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {


        float spinRate = 100;
        float spin = (entity.getWorld().getTime() + tickDelta) % (spinRate * 360);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getLayer());

        if(fileReader == null)
        {
            try {
                fileReader = new ObjFileReader(objFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if(fileReader2 == null)
        {
            try {
                fileReader2 = new ObjFileReader(objFile2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        matrices.push();

        float x = 0.5f;
        float y = 0.5f;
        float z = 0.5f;

        Vector3i dir = new Vector3i();

        try {
            Direction direction = entity.getBlockState().get(Properties.FACING);

            x -= direction.getOffsetX() * 2;
            y -= direction.getOffsetY() * 2;
            z -= direction.getOffsetZ() * 2;

            dir.x += direction.getOffsetX();
            dir.y += direction.getOffsetY();
            dir.z += direction.getOffsetZ();

        } catch (Exception e)
        {
            Testing_mod.LOGGER.info("Laser block does not exist anymore");
        }

        int timer = entity.createNbt().getInt("orb timer");


        float scale = -(size / ((float)Math.pow(1.5,timer))) + size;

       // Testing_mod.LOGGER.info("Timer: " + timer);
        if(timer > 0)
        {

            //rendering circles

            matrices.translate(x, y, z);
            matrices.scale(scale, scale, scale);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(spin));

            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            Matrix3f matrix3f = matrices.peek().getNormalMatrix();


            fileReader.render(matrix4f, matrix3f, vertexConsumer, light, overlay);
            matrices.scale(0.75f, 0.75f, 0.75f);

            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-2 * spin));

            matrix4f = matrices.peek().getPositionMatrix();
            matrix3f = matrices.peek().getNormalMatrix();

            fileReader.render(matrix4f, matrix3f, vertexConsumer, light, overlay);
        }

        matrices.pop();

        matrices.push();

        if(timer == 15)
        {
            //rendering laser

            float l = entity.createNbt().getInt("laser length");

            if(l != 0)
            {
                length = l - 2;
            }


            matrices.translate(x, y, z);
            matrices.translate(-(length * 0.5f - 0.2) * dir.x,-(length * 0.5f - 0.2) * dir.y,-(length * 0.5f - 0.2) * dir.z);
            matrices.scale(size * 0.8f + (length * 0.5f) * dir.x, size * 0.8f + (length * 0.5f) * dir.y, size * 0.8f + (length * 0.5f) * dir.z);


            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90 * dir.x));

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180 * dir.z));

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(spin * dir.x));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(spin * dir.y));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(spin * dir.z));


            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            Matrix3f matrix3f = matrices.peek().getNormalMatrix();

            fileReader2.render(matrix4f, matrix3f, vertexConsumer, light, overlay);

            matrices.scale(Math.abs(dir.y) * 0.25f + 0.75f,Math.abs(dir.x) * 0.25f + 0.75f,Math.abs(dir.z) * 0.25f + 0.75f);

            fileReader2.render(matrix4f, matrix3f, vertexConsumer, light, overlay);

            matrices.scale(Math.abs(dir.y) * 0.25f + 0.75f,Math.abs(dir.x) * 0.25f + 0.75f,Math.abs(dir.z) * 0.25f + 0.75f);

            fileReader2.render(matrix4f, matrix3f, vertexConsumer, light, overlay);



        }

        matrices.pop();
    }


    private int getLightLevel(World world, BlockPos blockPos)
    {
        int bLight = world.getLightLevel(LightType.BLOCK, blockPos);
        int sLight = world.getLightLevel(LightType.SKY, blockPos);
        return LightmapTextureManager.pack(bLight,sLight);
    }


    protected RenderLayer getLayer() {
        RenderLayer baseLayer = ModRenderLayers.LASER_RENDER_LAYER;
        //return baseLayer == null ? null : TestingModClient.bloomBuffer.getRenderLayer(baseLayer);
        return baseLayer;
    }

}
