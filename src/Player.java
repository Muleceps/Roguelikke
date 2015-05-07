import java.util.LinkedList;


public class Player {
	public int x;
	public int y;
	public int health = 10;
	public int mana = 5;
	public int str = 10;
	public int dex = 10;
	public int Int = 10;
	public int speed = 100 - dex;
	public int dmgmin = 1;
	public int ac = 10;
	public int dmgmax = 4;
	public Item lHand;
	public Item rHand;
	public Item armor;
	public LinkedList<Item> inventory;
	
	Player(World world){
		x = 0;
		y = 0;
		inventory = new LinkedList<Item>();
		for(int i = 0; i< 10; i++){
			Item item = new Item(world);
			inventory.add(item);
		}
	}

	public void equip(int selected) {
		if(inventory.get(selected).slot == 1){
			if(armor != null){
				inventory.add(armor);
				ac -= armor.bonus;
			}
			armor = inventory.get(selected);
			ac += armor.bonus;
			inventory.remove(selected);
			return;
		}
		if(inventory.get(selected).slot == 2){
			if(rHand != null){
				inventory.add(rHand);
				dmgmin -= rHand.bonus;
				dmgmax -= rHand.bonus;
			}
			if(lHand.slot == 4){
				lHand = null;
			}
			rHand = inventory.get(selected);
			dmgmin += rHand.bonus;
			dmgmax += rHand.bonus;
			inventory.remove(selected);
			return;
		}
		if(inventory.get(selected).slot == 3){
			if(lHand != null){
				if(lHand.slot == 3){
					inventory.add(lHand);
					ac -= lHand.bonus;
				}if(lHand.slot == 4){
					inventory.add(lHand);
					dmgmin -= lHand.bonus;
					dmgmax -= lHand.bonus;
				}
			}
			lHand = inventory.get(selected);
			ac += lHand.bonus;
			inventory.remove(selected);
			return;
		}
		if(inventory.get(selected).slot == 4){
			if(lHand != null){
				if(lHand.slot == 3){
					inventory.add(lHand);
					ac -= lHand.bonus;
				}
			}
			if(rHand != null){
				inventory.add(rHand);
				dmgmin -= rHand.bonus;
				dmgmax -= rHand.bonus;
			}
			lHand = inventory.get(selected);
			rHand = inventory.get(selected);
			dmgmin += rHand.bonus;
			dmgmax += rHand.bonus;
			inventory.remove(selected);
			return;
		}
		
	}

	public void drop(int selected, World world) {
		inventory.get(selected).x = x;
		inventory.get(selected).y = y;
		world.dungeon.get(world.currentfloor).items.add(inventory.get(selected));
		world.dungeon.get(world.currentfloor).map[x][y] = '$';
		inventory.remove(selected);
		
	}
}
