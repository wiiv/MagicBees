package magicbees.client;

import com.google.common.collect.Maps;
import forestry.core.models.BlankModel;
import magicbees.bees.EnumBeeModifiers;
import magicbees.client.tesr.TileEntityEffectJarRenderer;
import magicbees.elec332.corerepack.util.MoonPhase;
import magicbees.init.BlockRegister;
import magicbees.item.types.EnumNuggetType;
import magicbees.item.types.EnumResourceType;
import magicbees.tile.TileEntityEffectJar;
import magicbees.util.Config;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import static magicbees.init.ItemRegister.*;

/**
 * Created by Elec332 on 15-8-2017.
 */
@SideOnly(Side.CLIENT)
public class ModelHandler {

	private static final ModelResourceLocation moonDialModelLocation = new ModelResourceLocation(new MagicBeesResourceLocation("moondialworkaround"), "inventory");
	public static final ModelResourceLocation effectJarModel = new ModelResourceLocation(new MagicBeesResourceLocation("effectJar"), "normal");
	private static final ResourceLocation someModel = new ResourceLocation("stick");
	private static ItemCameraTransforms itemTransform;
	private static boolean tesrRegistered = false;

	@SubscribeEvent
	public void loadModels(ModelRegistryEvent event){
		ModelLoader.registerItemVariants(moonDial, someModel);
		setItemModelLocation(moonDial, stack -> moonDialModelLocation);
		setForestryModel(combItem, "bee_combs");
		setForestryModel(dropItem, "honey_drop");
		setForestryModel(pollenItem, "pollen");
		setForestryModel(propolisItem, "propolis");
		setForestryModel(waxItem, "beeswax");

		setItemModels(resourceItem, EnumResourceType.values().length, value -> {

			EnumResourceType resourceType = EnumResourceType.values()[value];
			return createMRL(resourceType.toString().toLowerCase());

		});

		setItemModels(orePartItem, EnumNuggetType.values().length, value -> {

			EnumNuggetType resourceType = EnumNuggetType.values()[value];
			return createMRL("part" + resourceType.toString().toLowerCase());

		});

		ModelResourceLocation on = createMRL("mysteriousmagnet_on");
		ModelResourceLocation off = createMRL("mysteriousmagnet_off");
		setItemModelLocation(mysteriousMagnet, stack -> mysteriousMagnet.isMagnetActive(stack) ? on : off, on, off);

		for (EnumBeeModifiers modifier : EnumBeeModifiers.values()){
			Item item = getBeeFrames().get(modifier);
			setItemModelLocation(item, createMRL("frame_"+modifier.getName().toLowerCase()));
		}

		setItemModelLocation(manasteelgrafter, createMRL("manasteel_grafter"));
		setItemModelLocation(manasteelScoop, createMRL("manasteel_scoop"));

		linkItemTextureToBlock(BlockRegister.hiveBlock);
		linkItemTextureToBlock(BlockRegister.enchantedEarth);

		setItemModelLocation(Item.getItemFromBlock(BlockRegister.effectJar), effectJarModel);

		registerTESRs();
	}

	private void registerTESRs(){
		MinecraftForge.EVENT_BUS.register(new Object(){

			@SubscribeEvent
			public void onGuiButtonClicked(GuiScreenEvent.ActionPerformedEvent.Post event){
				if (!(event.getGui() instanceof GuiVideoSettings)){
					return;
				}
				if (Minecraft.getMinecraft().gameSettings.fancyGraphics && !tesrRegistered){
					registerFancyTESRs();
				}
			}

		});
		registerFancyTESRs();
	}

	private void registerFancyTESRs(){

		if (Minecraft.getMinecraft().gameSettings.fancyGraphics && !tesrRegistered){

			if (Config.fancyJarRenderer){
				ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEffectJar.class, new TileEntityEffectJarRenderer());
			}

			tesrRegistered = true;
		}
	}

	@SubscribeEvent
	@SuppressWarnings("deprecation")
	public void loadSpecialModels(ModelBakeEvent event){
		itemTransform = item;//event.getModelRegistry().getObject(new ModelResourceLocation(new ResourceLocation("stick"), "inventory")).getItemCameraTransforms();
		event.getModelRegistry().putObject(moonDialModelLocation, new BlankModel() {

			@Override
			@Nonnull
			protected ItemOverrideList createOverrides() {
				return new ItemOverrideList(Collections.emptyList()){

					IBakedModel[] models = new IBakedModel[MoonPhase.values().length];

					@Override
					@Nonnull
					public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
						MoonPhase phase;
						if (world == null){
							world = Minecraft.getMinecraft().world;
						}
						phase = MoonPhase.getMoonPhase(world);
						int i = phase.ordinal();
						IBakedModel model = models[i];
						if (model == null){
							models[i] = model = new BlankModel() {

								List<BakedQuad> quad = ItemLayerModel.getQuadsForSprite(0, ModelLoader.defaultTextureGetter().apply(new MagicBeesResourceLocation("items/moondial." + i)), DefaultVertexFormats.ITEM, Optional.empty());

								@Override
								@Nonnull
								public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
									return side == null ? quad : super.getQuads(state, side, rand);
								}

								@Override
								@Nonnull
								public ItemCameraTransforms getItemCameraTransforms() {
									return itemTransform;
								}

							};
						}
						return model;
					}

				};
			}

		});
		
	}


	@SubscribeEvent
	public void registerTextures(TextureStitchEvent event){
		TextureMap map = event.getMap();
		for (int i = 0; i < MoonPhase.values().length; i++) {
			map.registerSprite(new MagicBeesResourceLocation("items/moondial." + i));
		}
	}

	@SuppressWarnings("all")
	private static void linkItemTextureToBlock(Block block){
		Item item = Item.getItemFromBlock(block);
		if (item == null || item == Items.AIR){
			return;
		}
		ModelLoader.registerItemVariants(item, someModel);
		IStateMapper stateMapper = new DefaultStateMapper();
		Map<IBlockState, ModelResourceLocation> modelLocsIbs = stateMapper.putStateModelLocations(block);
		Map<Integer, ModelResourceLocation> modelLocs = Maps.newHashMap();
		for (IBlockState state : modelLocsIbs.keySet()){
			modelLocs.put(state.getBlock().getMetaFromState(state), modelLocsIbs.get(state));
		}
		setItemModelLocation(item, stack -> {

			ModelResourceLocation loc = modelLocs.get(stack.getItemDamage());
			if (loc == null){
				return modelLocs.get(0);
			}
			return loc;

		});
	}

	private static ModelResourceLocation createMRL(String name){
		return new ModelResourceLocation(new MagicBeesResourceLocation(name), "inventory");
	}

	private static void setForestryModel(Item item, String resource){
		setItemModelLocation(item, new ModelResourceLocation(new ResourceLocation("forestry", resource), "inventory"));
	}

	private static void setItemModels(Item item, int types, IntFunction<ModelResourceLocation> loc){
		ModelResourceLocation[] locs = new ModelResourceLocation[types];
		for (int i = 0; i < types; i++) {
			locs[i] = loc.apply(i);
		}
		setItemModelLocation(item, stack -> {
			int d = stack.getItemDamage();
			if (d > types || d < 0){
				d = 0;
			}
			return locs[d];
		}, locs);
	}

	private static void setItemModelLocation(Item item, ModelResourceLocation loc){
		setItemModelLocation(item, stack -> loc, loc);
	}

	private static void setItemModelLocation(Item item, Function<ItemStack, ModelResourceLocation> func, ModelResourceLocation... vars){
		ModelLoader.registerItemVariants(item, vars);
		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {

			@Override
			@Nonnull
			public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
				return func.apply(stack);
			}

		});
	}

	@SuppressWarnings("deprecation")
	private static final ItemCameraTransforms item = new ItemCameraTransforms(new ItemTransformVec3f(new Vector3f(0, 0, 0), applyTranslationScale(new Vector3f(0, 3, 1)), new Vector3f(0.55f, 0.55f, 0.55f)), new ItemTransformVec3f(new Vector3f(0, 0, 0), applyTranslationScale(new Vector3f(0, 3, 1)), new Vector3f(0.55f, 0.55f, 0.55f)), new ItemTransformVec3f(new Vector3f(0, -90, 25), applyTranslationScale(new Vector3f(1.13f, 3.2f, 1.13f)), new Vector3f(0.68f, 0.68f, 0.68f)), new ItemTransformVec3f(new Vector3f(0, -90, 25), applyTranslationScale(new Vector3f(1.13f, 3.2f, 1.13f)), new Vector3f(0.68f, 0.68f, 0.68f)), new ItemTransformVec3f(new Vector3f(0, 180, 0), applyTranslationScale(new Vector3f(0, 13, 7)), new Vector3f(1, 1, 1)), ItemTransformVec3f.DEFAULT, new ItemTransformVec3f(new Vector3f(0, 0, 0), applyTranslationScale(new Vector3f(0, 2, 0)), new Vector3f(0.5f, 0.5f, 0.5f)), ItemTransformVec3f.DEFAULT);

	private static Vector3f applyTranslationScale(Vector3f vec){
		vec.scale(0.0625F);
		return vec;
	}

}
