package thaumicbees.bees.genetics;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

/**
 * Simply a class to hold all the functions to manage species' default genomes.
 * @author MysteriousAges
 *
 */
public class BeeGenomeManager
{
	// Basic genome for All thaumic bees.
	private static IAllele[] getTemplateBaseThaumic()
	{
		IAllele[] genome = new IAllele[EnumBeeChromosome.values().length];

		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Esoteric;
		genome[EnumBeeChromosome.SPEED.ordinal()] =  Allele.getBaseAllele("speedSlowest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShorter");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolFalse");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolFalse");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolFalse");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.getBaseAllele("flowersVanilla");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlowest");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryDefault");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectNone");

		return genome;
	}
	
	// Basic genome for Arcane branch bees.
	private static IAllele[] getTemplateBaseArcane()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlow");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		
		return genome;
	}

	// Specialization genome for Esoteric bees.
	public static IAllele[] getTemplateEsoteric()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Esoteric;
		
		return genome;
	}

	public static IAllele[] getTemplateMysterious()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Mysterious;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		
		return genome;
	}

	public static IAllele[] getTemplateArcane()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Arcane;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlower");
		
		return genome;
	}

	private static IAllele[] getTemplateBaseSupernatural()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlower");
		
		return genome;
	}

	public static IAllele[] getTemplateCharmed()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Charmed;
		
		return genome;
	}

	public static IAllele[] getTemplateEnchanted()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Enchanted;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}

	public static IAllele[] getTemplateSupernatural()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Supernatural;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseScholarly()
	{
		IAllele[] genome = getTemplateBaseThaumic();

		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp1");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleFlower.flowerBookshelf;
		
		return genome;
	}

	public static IAllele[] getTemplatePupil()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Pupil;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlower");
		
		return genome;
	}

	public static IAllele[] getTemplateScholarly()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Scholarly;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}

	public static IAllele[] getTemplateSavant()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Savant;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseElemental()
	{
		IAllele[] genome = getTemplateBaseThaumic();

		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleFlower.flowerThaumcraft;
		
		return genome;
	}

	public static IAllele[] getTemplateStark()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Stark;
		
		return genome;
	}

	public static IAllele[] getTemplateAir()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Air;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFastest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShortened");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLargest");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringMaximum");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.moveSpeed;
		
		return genome;
	}

	public static IAllele[] getTemplateFire()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Fire;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFaster");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown3");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectIgnition");
		
		return genome;
	}

	public static IAllele[] getTemplateWater()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Water;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFast");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLong");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.cleansingEffect;
		
		return genome;
	}

	public static IAllele[] getTemplateEarth()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = Allele.Earth;
		genome[EnumBeeChromosome.SPEED.ordinal()] =  Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLonger");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown1");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlow"); // 1.6.5+: floweringFastest
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.digSpeed;
		
		return genome;
	}
	
}