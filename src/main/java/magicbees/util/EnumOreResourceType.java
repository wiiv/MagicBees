package magicbees.util;

import elec332.core.util.ItemStackHelper;
import magicbees.init.ItemRegister;
import magicbees.item.types.EnumNuggetType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * Created by Elec332 on 15-5-2017.
 */
public enum EnumOreResourceType {

	IRON(new ItemStack(ItemRegister.ironNugget), "nuggetIron"),
	GOLD(new ItemStack(Items.GOLD_NUGGET)),
	COPPER(EnumNuggetType.COPPER, "nuggetCopper"),
	TIN(EnumNuggetType.TIN, "nuggetTin"),
	SILVER("nuggetSilver", "dustTinySilver"),
	LEAD("nuggetLead", "dustTinyLead"),
	ALUMINIUM("nuggetAluminium", "nuggetAluminum"),
	ARDITE("nuggetArdite"),
	COBALT("nuggetCobalt"),
	MANYULLYN("nuggetManyullyn"),
	OSMIUM("nuggetOsmium"),
	DIAMOND(EnumNuggetType.DIAMOND),
	EMERALD(EnumNuggetType.EMERALD),
	APATITE(EnumNuggetType.APATITE),
	SILICON,
	CERTUS,
	FLUIX,
	PLATINUM("nuggetPlatinum"),
	NICKEL("nuggetNickel", "nuggetFerrous"),
	BRONZE(EnumNuggetType.BRONZE, "nuggetBronze"), //TODO: Add bronze nuggets? As bronze is a forestry resource?
	INVAR("nuggetInvar"),
	ELECTRUM("nuggetElectrum"),
	;

	EnumOreResourceType(EnumNuggetType nugget, String... oreDictA){
		this(nugget.getStack(), oreDictA);
	}

	EnumOreResourceType(String... oreDictA){
		this.oreDictA = oreDictA;
	}

	EnumOreResourceType(ItemStack stack, String... oreDictA){
		setStack(stack);
		this.oreDictA = oreDictA;
	}

	private String[] oreDictA;
	private ItemStack finalStack;

	private void setStack(ItemStack stack){
		if (!ItemStackHelper.isStackValid(stack)){
			stack = null;
		}
		this.finalStack = stack;
	}

	public boolean enabled(){
		getStack();
		return finalStack != null;
	}

	public ItemStack getStack() {
		if (oreDictA != null){
			primLoop:
			for (String oreDict : oreDictA) {
				List<ItemStack> l = OreDictionary.getOres(oreDict);
				if (!l.isEmpty()) {
					for (ItemStack stack_ : l) {
						if (ItemStackHelper.isStackValid(stack_)) {
							setStack(ItemStackHelper.copyItemStack(stack_));
							break primLoop;
						}
					}
				}
			}
			oreDictA = null;
		}
		return finalStack == null ? ItemStackHelper.NULL_STACK : finalStack;
	}

}
