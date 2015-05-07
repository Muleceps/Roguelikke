
public class Item {
	int value, weight, x, y, slot, bonus, special;
	String name;
	
	Item(World world){
		name = "thing";
		int buildpoints = (int) ((Math.log(world.currentfloor + 1) + 1) * (World.getRand(20, 40) / 4));
		int choice = World.getRand(0,3);
		value = buildpoints / 2;
		if(choice == 0){
			//armour
			slot = 1;
			bonus = 1 + buildpoints / World.getRand(4,8);
		}
		if(choice == 1){
			//weapon
			slot = 2;
			if(World.getRand(0,3) == 2){
				bonus = 1 + buildpoints / World.getRand(2,6);
				slot = 4;
			}else {
				bonus = 1 + buildpoints / World.getRand(4,8);
			}
		}
		if(choice == 2){
			//shield
			slot = 3;
			bonus = 1 + buildpoints / World.getRand(6,10);
		}
	}

	public String description() {
		String description = "";
		if(slot == 1){
			description += "this piece of armour offers you " + bonus + " points of ac.";
		}
		if(slot == 2){
			description += "this weapon takes one hand and increases your damage by " + bonus + " points.";
		}
		if(slot == 4){
			description += "this weapon takes up both of your hands and increases your damage by " + bonus + " points.";
		}
		if(slot == 3){
			description += "this shield offers you " + bonus + " points of ac.";
		}
		return description;
	}
}
