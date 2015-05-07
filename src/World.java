import java.util.Date;
import java.util.LinkedList;
import java.util.Random;


public class World {
	public LinkedList<Map> dungeon;
	private static long oldseed = 0;
	int currentfloor = 0, maxfloor = 0;
	public String log = "";
	public int time = 0;
	
	public World(){
		dungeon = new LinkedList<Map>();
		
		generateFloor();
	}
	
	public static int getRand(int min, int max){
		//instantiate a new date function
		Date now = new Date();
		//make a new seed based on the previous seed and the time since the epoch
		long seed = now.getTime()	+ oldseed;
		//set this to be the new oldseed
		oldseed = seed;
 
		//make a new randomizer seeded with the new seed
		Random randomizer = new Random(seed);
		int n = max - min;
		int i = randomizer.nextInt(n);
		//if for some reason it ends up negative turn it positive
		if (i < 0)
			i = -i;
		//return the random value plus the minimum value
		return min + i;
	}
	
	public void setPlayer(Player player){
		boolean found = false;
		while(!found){
			int x = getRand(3, dungeon.get(currentfloor).width-4);
			int y = getRand(3, dungeon.get(currentfloor).height-4);
			if(dungeon.get(currentfloor).map[x][y] == '1'){
				found = true;
				player.x = x;
				player.y = y;
			}
		}
	}
	
	
	public int numWall(int x, int y, Map map, int distance){
		int count = 0;
		
		for(int i = 0; i < distance*2 + 1; i++){
			for(int j = 0; j < distance*2 + 1; j++){
				if(x - distance + i <= -1 || x - distance + i >= map.width || y - distance + i <= -1 || y - distance + i >= map.height){
					count ++;
					}
				else{
					if(map.map[(x - distance + i)][(y - distance + i)] == '0'){
						if(x - distance + i != x || y - distance + i != y){
							count++;
						}
					}
						
				}
					
			}
		}
		
		
		return count;
	}
	
	public void generateFloor(){
		int width = getRand(20,50) + getRand(20,50) + getRand(20,50);
		int height = getRand(14,28) + getRand(14,28) + getRand(14,28);
		Map floor = new Map(width, height);
		int x = getRand(3, width-4);
		int y = getRand(3, height-4);
		
		Map floor2 = floor;
		
		//Fill the map with the base tile - Impassable terrain
		for(int i = 0; i < floor.width; i++)
		{
			for(int j = 0; j < floor.height; j++)
			{
				
				//if(getRand(0,100) < 45){
					floor.map[i][j] = '0';
				//}else
					//floor.map[i][j] = '1';
			}
		}
		
		//start actual generation
		
		for(int i = x; i < x + 4; i++){
			for(int j = y; j < y + 4; j++){
				floor.map[i][j] = '1';
			}
		}
		
		for(int i = 0; i < 500; i++){
			x = getRand(0, width);
			y = getRand(0, height);
			
			while(floor.map[x][y] == '0'){
				x = getRand(0, width);
				y = getRand(0, height);
			}
			
			int choice = getRand(0, 15);
			if(choice == 0 || choice == 8 || choice == 12){
				boolean works = true;
				if(x + 6 > width || y + 3 > height || y - 3 < 0){
					works = false;
				}else{
					
					for(int j = x + 1; j < x + 6; j++){
						for(int k = y - 3; k < y + 3; k++){
							if(floor.map[j][k] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					
					floor.map[x+1][y] = '1';
					
					for(int j = x + 2; j < x + 5; j++){
						for(int k = y - 2; k < y + 2; k++){
							floor.map[j][k] = '1';
						}
						
					}
				}
				
				
			}
			if(choice == 1 || choice == 9 || choice == 13){
				boolean works = true;
				if(x - 6 < 0 || y + 3 > height || y - 3 < 0){
					works = false;
				}else{
					
					for(int j = x - 1; j > x - 6; j--){
						for(int k = y - 3; k < y + 3; k++){
							if(floor.map[j][k] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					
					floor.map[x-1][y] = '1';
					
					for(int j = x - 2; j > x - 5; j--){
						for(int k = y - 2; k < y + 2; k++){
							floor.map[j][k] = '1';
						}
						
					}
				}
				
				
			}
			if(choice == 2 || choice == 10 || choice == 14){
				boolean works = true;
				if(y + 6 > height || x + 3 > width || x - 3 < 0){
					works = false;
				}else{
					
					for(int j = y + 1; j < y + 6; j++){
						for(int k = x - 3; k < x + 3; k++){
							if(floor.map[k][j] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					
					floor.map[x][y+1] = '1';
					
					for(int j = y + 2; j < y + 5; j++){
						for(int k = x - 2; k < x + 2; k++){
							floor.map[k][j] = '1';
						}
						
					}
				}
				
				
			}
			if(choice == 3 || choice == 11 || choice == 15){
				boolean works = true;
				if(y - 6 < 0 || x + 3 > width || x - 3 < 0){
					works = false;
				}else{
					
					for(int j = y - 1; j > y - 6; j--){
						for(int k = x - 3; k < x + 3; k++){
							if(floor.map[k][j] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					
					floor.map[x][y-1] = '1';
					
					for(int j = y - 2; j > y + 5; j--){
						for(int k = x - 2; k < x + 2; k++){
							floor.map[k][j] = '1';
						}
						
					}
				}
				
				
			}
			if(choice == 4){
				boolean works = true;
				if(x + 10 > width || y + 1 > height || y - 1 < 0){
					works = false;
				}else{
					
					for(int j = x + 1; j < x + 10; j++){
						for(int k = y - 1; k < y + 1; k++){
							if(floor.map[j][k] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					
					for(int j = x + 1; j < x + 9; j++){
							floor.map[j][y] = '1';			
					}
				}
				
				
			}
			if(choice == 5){
				boolean works = true;
				if(x - 10 < 0 || y + 1 > height || y - 1 < 0){
					works = false;
				}else{
					
					for(int j = x - 1; j > x - 10; j--){
						for(int k = y - 1; k < y + 1; k++){
							if(floor.map[j][k] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					for(int j = x - 1; j > x - 9; j--){
							floor.map[j][y] = '1';	
					}
				}
				
				
			}
			if(choice == 6){
				boolean works = true;
				if(y + 10 > height || x + 1 > width || x - 1 < 0){
					works = false;
				}else{
					
					for(int j = y + 1; j < y + 10; j++){
						for(int k = x - 1; k < x + 1; k++){
							if(floor.map[k][j] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					
					for(int j = y + 1; j < y + 9; j++){
							floor.map[x][j] = '1';
						
					}
				}
				
				
			}
			if(choice == 7){
				boolean works = true;
				if(y - 10 < 0 || x + 1 > width || x - 1 < 0){
					works = false;
				}else{
					
					for(int j = y - 1; j > y - 10; j--){
						for(int k = x - 1; k < x + 1; k++){
							if(floor.map[k][j] == '1')
								works = false;
						}
						
					}
					
				}
				if(works){
					
					for(int j = y - 1; j > y + 9; j--){
							floor.map[x][j] = '1';
					}
				}
				
				
			}
			
			
			
		}
		
		
		
		
		floor2.map = floor.map;
		
		
		
		dungeon.add(floor);
		
		
		boolean placeddownexit = false;
		while(!placeddownexit){
			x = getRand(0, width);
			y = getRand(0, height);
			if(dungeon.get(currentfloor).map[x][y] == '1'){
				dungeon.get(currentfloor).map[x][y] = '>';
				placeddownexit = true;
			}		
		}
		
		if(currentfloor != 0){
			boolean placedupexit = false;
			while(!placedupexit){
				x = getRand(0, width);
				y = getRand(0, height);
				if(dungeon.get(currentfloor).map[x][y] == '1' && dungeon.get(currentfloor).map[x][y] != '>'){
					dungeon.get(currentfloor).map[x][y] = '<';
					placedupexit = true;
				}
					
			}
		}
		
		for(int i = 0; i < width*height*.001; i++){
			Enemy orc = new Enemy(this);
			dungeon.get(currentfloor).enemies.add(orc);
		}
		
		for(int i = 0; i < 3; i++){
			if(getRand(0 , 2) == 0){
				boolean notplaced = true;
				while(notplaced){
					int s = getRand(0, floor.width) ,m = getRand(0, floor.height);
					if(floor.isopen(s,m)){
						Item item = new Item(this);
						item.x = s;
						item.y = m;
						dungeon.get(currentfloor).items.add(item);
						floor.map[s][m] = '$';
						notplaced = false;
					}
				}
			}
		}
		
	}
	
	/*private int checkAdjacent(int x, int y)
	{
		if((x-1 > 0) && dungeon.get(currentfloor).map[x-1][y] == '1')
		{
			return 0;
		}	
		else if((x+1 < 20) && dungeon.get(currentfloor).map[x+1][y] == '1')
		{
			return 1;
		}
		else if((y-1 > 0) && dungeon.get(currentfloor).map[x][y-1] == '1')
		{
			return 2;
		}
		else if((y+1 < 14) && dungeon.get(currentfloor).map[x][y+1] == '1')
		{
			return 3;
		}
		return -1;
	}*/
	
	public void increaseLevel(Player player){
		if(currentfloor == maxfloor){
			currentfloor++;
			maxfloor++;
			generateFloor();
		}else
			currentfloor++;
		
		int x = 0, y = 0;
		
		while(true){
			if(dungeon.get(currentfloor).map[x][y] == '<'){
				player.x = x;
				player.y = y;
				System.out.println("going down " + currentfloor);
				return;
			}
			x++;
			if(x > dungeon.get(currentfloor).width-1){
				y++;
				x = 0;
			}
		}
		
	}
	
	public void decreaseLevel(Player player){
		currentfloor--;
		int x = 0, y = 0;
		
		while(true){
			if(dungeon.get(currentfloor).map[x][y] == '>'){
				player.x = x;
				player.y = y;
				System.out.println("going up " + currentfloor);
				return;
			}
			x++;
			if(x > dungeon.get(currentfloor).width-1){
				y++;
				x = 0;
			}
		}
	}
	
	
	public boolean checkOpen(int x, int y)
	{
		// if it's outside the boundaries then it fails
		if(x < 0 || x > dungeon.get(currentfloor).width-1 || y < 0 || y > dungeon.get(currentfloor).height-1)
			return false;
		//if theres empty space there then return true
		if(!(dungeon.get(currentfloor).map[x][y] == '0')){
			if(dungeon.get(currentfloor).isEnemy(x, y) == false)
					return true;
			return false;
		}
		//otherwise it's false
		return false;
	}
	
	public void update(Player player){
		time += player.speed;
    	dungeon.get(currentfloor).moveEnemies(player, this);
	}
	
	public void clearLog(){
		log = "";
	}

	public void fight(int x, int y, Player player) {
		
		for(int i = 0; i < dungeon.get(currentfloor).enemies.size(); i++){
			if(dungeon.get(currentfloor).enemies.get(i).x == x && dungeon.get(currentfloor).enemies.get(i).y == y){
				if(getRand(0 , 20) + player.str - 10 > dungeon.get(currentfloor).enemies.get(i).ac){
					int dmg = getRand(player.dmgmin, player.dmgmax) + (player.str - 10);
					dungeon.get(currentfloor).enemies.get(i).health -= dmg;
					log += "you strike the monster for " + dmg + " points of damage. ----------------- ";
					if(dungeon.get(currentfloor).enemies.get(i).health <= 0){
						log += "you have slain the monster. ----------------- ";
						dungeon.get(currentfloor).enemies.remove(i);
					}
				}else{
				log += "you miss the monster. ----------------- ";
				}
			}
				
		}
		
	}

	public void pickupItem(Player player) {
		for(int i = 0; i < dungeon.get(currentfloor).items.size(); i++){
			if(dungeon.get(currentfloor).items.get(i).x == player.x && dungeon.get(currentfloor).items.get(i).y == player.y){
				player.inventory.add(dungeon.get(currentfloor).items.get(i));
				dungeon.get(currentfloor).items.remove(i);
				dungeon.get(currentfloor).map[player.x][player.y] = '1';
			}
		}
	}
}
