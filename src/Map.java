import java.util.LinkedList;


public class Map {
	public char[][] map;
	public LinkedList<Enemy> enemies;
	public LinkedList<Item> items;
	public int width, height;
	
	Map(int width, int height){
		map = new char[width][height];
		enemies = new LinkedList<Enemy>();
		items = new LinkedList<Item>();
		this.width = width;
		this.height = height;
	}
	
	public void printmap(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				System.out.print(map[x][y]);
			}
			System.out.println();
		}
	}
	
	public boolean isEnemy(int x, int y){
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).x == x && enemies.get(i).y == y)
				return true;
		}
		return false;
	}
	
	boolean isopen(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)
			return true;
		if(map[x][y] != '0')
			return true;
		return false;
	}
	
	public void moveEnemies(Player player, World world){
		for(int i = 0; i < enemies.size(); i++){
			for(int l = enemies.get(i).lastaction - world.time ; l < 0; l += enemies.get(i).speed){
				enemies.get(i).move(world, player);
				enemies.get(i).lastaction += enemies.get(i).speed;
			}
		}
	}
}
