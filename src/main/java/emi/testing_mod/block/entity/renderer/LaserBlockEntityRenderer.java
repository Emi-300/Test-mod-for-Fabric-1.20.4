package emi.testing_mod.block.entity.renderer;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.entity.LaserBlockEntity;
import emi.testing_mod.render.ModRenderLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.EndGatewayBlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.joml.Matrix4f;

import java.io.File;
import java.io.FileNotFoundException;


public class LaserBlockEntityRenderer implements BlockEntityRenderer<LaserBlockEntity> {

    public static final Identifier OBJ_FILE = new Identifier(Testing_mod.MOD_ID,"models/obj/player.obj");
    public static final ResourceFinder OBJ_FILE_PATH = new ResourceFinder(Testing_mod.MOD_ID,"");


    public String s = System.getProperty("user.dir");

    public File objFile = new File(s+ OBJ_FILE_PATH.toResourcePath(OBJ_FILE).getPath());
    public static ObjFileReader fileReader;

    public LaserBlockEntityRenderer(BlockEntityRendererFactory.Context context){
        s = s.substring(0,(s.length()-3)) + "src\\main\\resources\\assets\\";

        objFile = new File(s + OBJ_FILE_PATH.toResourcePath(OBJ_FILE).getPath());
    }

    @Override
    public void render(LaserBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {


        //long time = entity.getWorld().getTime();

        float spinRate = 100;
        float spin = (entity.getWorld().getTime() + tickDelta) % (spinRate * 360);
       // BeaconBlockEntityRenderer.renderBeam(matrices, vertexConsumers, BEAM_TEXTURE, tickDelta, 1.0f, time, entity.getPos().getY(), 256, BEAM_COLOR.getColorComponents(), 0.25F, 0.35F);
       // BeaconBlockEntityRenderer.renderBeam(matrices, vertexConsumers, BEAM_TEXTURE, tickDelta, 1.0f, time, entity.getPos().getY(), -256, BEAM_COLOR.getColorComponents(), 0.25F, 0.35F);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getLayer());

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getRenderStack();

        if(fileReader == null)
        {
            try {
                fileReader = new ObjFileReader(objFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        matrices.push();

        float x = 0.5f;
        float y = 0.5f;
        float z = 0.5f;

        boolean isPowered = false;

        try {
            Direction direction = (Direction)entity.getBlockState().get(Properties.FACING);

            x -= direction.getOffsetX() * 2;
            y -= direction.getOffsetY() * 2;
            z -= direction.getOffsetZ() * 2;

            isPowered = (entity.getBlockState().get(Properties.POWERED));

        } catch (Exception e)
        {
            Testing_mod.LOGGER.info("Laser block does not exist anymore");
        }

        matrices.translate(x,y,z);
        matrices.scale(0.35f,0.35f,0.35f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(spin));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        //matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(2 *tickDelta));

        Matrix4f matrix4f = matrices.peek().getPositionMatrix();


        fileReader.render(matrix4f,vertexConsumer);

        //rendering

//        this.renderSide(entity, matrix4f, vertexConsumer, 0.0F + x - 0.25f, 1.0F + x - 0.75f, 0.0F + y - 0.25f, 1.0F + y - 0.75f, 1.0F + z - 0.75f, 1.0F + z - 0.75f, 1.0F + z - 0.75f, 1.0F + z - 0.75f, Direction.SOUTH);
//        this.renderSide(entity, matrix4f, vertexConsumer, 0.0F + x - 0.25f, 1.0F + x - 0.75f, 1.0F + y - 0.75f, 0.0F + y - 0.25f, 0.0F + z - 0.25f, 0.0F + z - 0.25f, 0.0F + z - 0.25f, 0.0F + z - 0.25f, Direction.NORTH);
//        this.renderSide(entity, matrix4f, vertexConsumer, 1.0F + x - 0.75f, 1.0F + x - 0.75f, 1.0F + y -0.75f, 0.0F + y - 0.25f, 0.0F + z - 0.25f, 1.0F + z - 0.75f, 1.0F + z - 0.75f, 0.0F + z - 0.25f, Direction.EAST);
//        this.renderSide(entity, matrix4f, vertexConsumer, 0.0F + x - 0.25f, 0.0F + x - 0.25f, 0.0F + y - 0.25f, 1.0F + y -0.75f, 0.0F + z - 0.25f, 1.0F + z - 0.75f, 1.0F + z - 0.75f, 0.0F + z - 0.25f, Direction.WEST);
//        this.renderSide(entity, matrix4f, vertexConsumer, 0.0F + x - 0.25f, 1.0F + x - 0.75f, 0.0F + y -0.25f, 0.0f + y -0.25f, 0.0F + z - 0.25f, 0.0F + z - 0.25f, 1.0F + z - 0.75f, 1.0F + z - 0.75f, Direction.DOWN);
//        this.renderSide(entity, matrix4f, vertexConsumer, 0.0F + x - 0.25f, 1.0F + x - 0.75f, 1.0f + y - 0.75f, 1.0F + y - 0.75f, 1.0F + z - 0.75f, 1.0F + z - 0.75f, 0.0F + z - 0.25f, 0.0F + z - 0.25f, Direction.UP);



//        itemRenderer.renderItem(new ItemStack(ModItems.POLISHED_CRYSTAL_SHARD,1), ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),entity.getPos()),
//                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);


        matrices.pop();
    }








    private int getLightLevel(World world, BlockPos blockPos)
    {
        int bLight = world.getLightLevel(LightType.BLOCK, blockPos);
        int sLight = world.getLightLevel(LightType.SKY, blockPos);
        return LightmapTextureManager.pack(bLight,sLight);
    }


    protected RenderLayer getLayer() {
        return ModRenderLayers.LASER_RENDER_LAYER;
    }

}
