package suite01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;

import org.junit.Test;

import srdk.theDrake.Offset2D;
import srdk.theDrake.Troop;
import srdk.theDrake.TroopFace;

public class TroopTest {

	@Test
	public void classStructure() {
		// All attributes private and final
		for(Field f : Troop.class.getFields()) {
			assertTrue(Modifier.isPrivate(f.getModifiers()));
			assertTrue(Modifier.isFinal(f.getModifiers()));
		}
	}
	
	@Test
	public void behaviour() {
		Troop archer = new Troop("Archer", new Offset2D(1, 1), new Offset2D(0, 1), 
				Collections.emptyList(), Collections.emptyList());
		assertEquals("Archer", archer.name());
		assertTrue(archer.pivot(TroopFace.AVERS).equalsTo(1, 1));
		assertTrue(archer.pivot(TroopFace.REVERS).equalsTo(0, 1));
		
		Troop monk = new Troop("Monk", new Offset2D(1, 1),
				Collections.emptyList(), Collections.emptyList());
		assertEquals("Monk", monk.name());
		assertTrue(monk.pivot(TroopFace.AVERS).equalsTo(1, 1));
		assertTrue(monk.pivot(TroopFace.REVERS).equalsTo(1, 1));
		
		Troop drake = new Troop("Drake", 
				Collections.emptyList(), Collections.emptyList());
		assertEquals("Drake", drake.name());
		assertTrue(drake.pivot(TroopFace.AVERS).equalsTo(1, 1));
		assertTrue(drake.pivot(TroopFace.REVERS).equalsTo(1, 1));
	}
}
