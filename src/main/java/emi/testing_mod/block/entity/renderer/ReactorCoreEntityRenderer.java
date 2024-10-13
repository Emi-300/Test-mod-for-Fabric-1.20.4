package emi.testing_mod.block.entity.renderer;

import emi.testing_mod.TestingModClient;
import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.entity.ReactorCoreEntity;
import emi.testing_mod.render.ModRenderLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileNotFoundException;

public class ReactorCoreEntityRenderer implements BlockEntityRenderer<ReactorCoreEntity> {

    public static final Identifier OBJ_FILE = new Identifier(Testing_mod.MOD_ID,"models/obj/icosphere.obj");
    public static final Identifier OBJ_FILE_2 = new Identifier(Testing_mod.MOD_ID,"models/obj/cylinder.obj");
    public static final ResourceFinder OBJ_FILE_PATH = new ResourceFinder(Testing_mod.MOD_ID,"");

    public static final Identifier NOISE_TEXTURE_1 = new Identifier(Testing_mod.MOD_ID,"textures/noise/tiled_noise.png");

    public String s = System.getProperty("user.dir");

    public File objFile;
    public File objFile2;
    public static ObjFileReader fileReader;
    public static ObjFileReader fileReader2;


    public ReactorCoreEntityRenderer(BlockEntityRendererFactory.Context context){
        s = s.substring(0,(s.length()-3)) + "src\\main\\resources\\assets\\";

        objFile = new File(s + OBJ_FILE_PATH.toResourcePath(OBJ_FILE).getPath());
        objFile2 = new File(s + OBJ_FILE_PATH.toResourcePath(OBJ_FILE_2).getPath());


    }



    @Override
    public void render(ReactorCoreEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {


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

        matrices.pop();

    }

    protected RenderLayer getLayer() {
        RenderLayer baseLayer = ModRenderLayers.LASER_RENDER_LAYER;
        return baseLayer == null ? null : TestingModClient.bloomBuffer.getRenderLayer(baseLayer);
        //return baseLayer;
    }

}
