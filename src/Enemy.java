import java.util.Stack;


public class Enemy {
	int health, x, y, ac, str, Int, dex, dmgmin, dmgmax, exp;
	Stack<Integer> path;
	int oldtargetx, oldtargety;
	int speed = 0;
	int lastaction = 0;
	
	Enemy(World world){
		boolean placed = false;
		while(!placed){
			x = World.getRand(0, world.dungeon.get(world.currentfloor).width);
			y = World.getRand(0, world.dungeon.get(world.currentfloor).height);
			if(world.dungeon.get(world.currentfloor).map[x][y] == '1'){
				placed = true;
			}		
		}
		int buildpoints = (int) ((Math.log(world.currentfloor + 1) + 1) * (World.getRand(30, 40) / 4));
		health = World.getRand(buildpoints / 5, buildpoints / 3);
		exp = buildpoints;
		buildpoints -= health / 3;
		
		if((int)(buildpoints / 10) == (int)(buildpoints / 8)){
			ac = 10 + World.getRand(0, 1);
		}else
			ac = 10 + World.getRand(buildpoints / 10, buildpoints / 8);
		
		buildpoints -= ac - 10;
		str = 0;
		dex = 0;
		Int = 0;
		dmgmin = 0;
		dmgmax = 0;
		for(int i = 0; i < buildpoints / 4; i++){
			str += World.getRand(1,6);
			dmgmin += World.getRand(0, 1);
			dmgmax += World.getRand(1, 2);
			dex += World.getRand(1, 6);
			Int += World.getRand(1, 6);
		}
		speed = 100 - dex;
		dmgmin += (str - 10)/2;
		dmgmax += (str - 10)/2;
		System.out.println(speed);
	}
	
	public void move(World world, Player player){
		
		if(isAdjacent(player)){
			attack(world, player);
			return;
		}
		
		if (player.x > x && world.checkOpen(x+1, y)){
			if(player.x != x+1 || player.y != y)
				x++;
		}
		if (player.x < x && world.checkOpen(x-1, y)){
			if(player.x != x-1 || player.y != y)
				x--;
		}
		if (player.y > y && world.checkOpen(x, y+1)){
			if(player.x != x || player.y != y+1)
				y++;
		}
		if (player.y < y && world.checkOpen(x, y-1)){
			if(player.x != x || player.y != y-1)
				y--;
		}
		
		/*path = Pathfinding(player.x, player.y, world);
		
		if(path == null){
			System.out.println("I have failed everyone");
			return;
		}
		
		if(path.isEmpty()){
			System.out.println("I have failed you");
			return;
		}
		
		int direction = path.pop();
		
		if(direction == 1){
			y++;
		}else if(direction == 2){
			y++;
			x++;
		}else if(direction == 3){
			x++;
		}else  if(direction == 4){
			y--;
			x++;
		}else  if(direction == 5){
			y--;
		}else  if(direction == 6){
			y--;
			x--;
		}else  if(direction == 7){
			x--;
		}else  if(direction == 8){
			y++;
			x--;
		}*/
		
		
	}
	
	private void attack(World world, Player player) {
		if(World.getRand(0 , 20) + str - 10 > player.ac){
			int dmg = World.getRand(dmgmin, dmgmax) + (str - 10);
			if(dmg <= 0){
				world.log += "the monster misses you. ----------------- ";
			}else{
				player.health -= dmg;
				world.log += "the monster strikes you for " + dmg + " points of damage. ----------------- ";
			}
		}else{
		world.log += "the monster misses you. ----------------- ";
		}
	}

	Stack<Integer> Pathfinding(int targetx, int targety, World world){
		Stack<Square> stack = new Stack<Square>();
		Stack<Integer> path = new Stack<Integer>();
		Square start = new Square();
		start.x = x;
		start.y = y;
		start.distance = 0;
		
		Square current, previous;
		
		current = start;
		boolean finished = false;
		
		stack.add(start);
		
		int i = 0;
		
		while(!finished){
			while(SelectAdjacent(current, world, stack)){
				if(stack.peek().x == targetx && stack.peek().y == targety)
					finished = true;
			}
			
			
			i++;
			
			current = stack.get(i);
		}
		
		current.x = targetx;
		current.y = targety;
		current.distance = stack.peek().distance;
		previous = current;
		current = FindCorrect(stack, current);
		
		while(current.x != start.x && current.y != start.y){
			path.add(direction(current, previous));
			previous = current;
			current = FindCorrect(stack, current);
		}
		
		
		return path;
	}
	
	public void printstack(Stack<Square> stack){
		for(int i = 0; i < stack.size(); i++){
			System.out.println(stack.get(i).x + ", " + stack.get(i).y + " " + stack.get(i).distance);
		}
	}
	
	int direction(Square current, Square next){
		if(current.x == next.x && current.y < next.y)
			return 1;
		if(current.x < next.x && current.y < next.y)
			return 2;
		if(current.x < next.x && current.y == next.y)
			return 3;
		if(current.x < next.x && current.y > next.y)
			return 4;
		if(current.x == next.x && current.y > next.y)
			return 5;
		if(current.x > next.x && current.y > next.y)
			return 6;
		if(current.x > next.x && current.y == next.y)
			return 7;
		if(current.x > next.x && current.y < next.y)
			return 8;
		if(current.x == next.x && current.y == next.y)
			return 0;
		return -1;
	}
	
	Square FindCorrect(Stack<Square> stack, Square  current){
		if(Stackcontains(stack, current.x, current.y+1)){
			int location = Indexof(stack, current.x, current.y+1);
			if(stack.get(location).distance == current.distance-1){
				return stack.get(location);
			}
		}
		if(Stackcontains(stack, current.x+1, current.y)){
			int location = Indexof(stack, current.x+1, current.y);
			if(stack.get(location).distance == current.distance-1){
				return stack.get(location);
			}
		}
		if(Stackcontains(stack, current.x-1, current.y)){
			int location = Indexof(stack, current.x-1, current.y);
			if(stack.get(location).distance == current.distance+1){
				return stack.get(location);
			}
		}
		if(Stackcontains(stack, current.x, current.y-1)){
			int location = Indexof(stack, current.x, current.y-1);
			if(stack.get(location).distance == current.distance-1){
				return stack.get(location);
			}
		}
		if(Stackcontains(stack, current.x-1, current.y-1)){
			int location = Indexof(stack, current.x-1, current.y-1);
			if(stack.get(location).distance == current.distance-1){
				return stack.get(location);
			}
		}
		if(Stackcontains(stack, current.x+1, current.y-1)){
			int location = Indexof(stack, current.x+1, current.y-1);
			if(stack.get(location).distance == current.distance-1){
				return stack.get(location);
			}
		}
		if(Stackcontains(stack, current.x-1, current.y+1)){
			int location = Indexof(stack, current.x-1, current.y+1);
			if(stack.get(location).distance == current.distance-1){
				return stack.get(location);
			}
		}
		if(Stackcontains(stack, current.x+1, current.y+1)){
			int location = Indexof(stack, current.x+1, current.y+1);
			if(stack.get(location).distance == current.distance-1){
				return stack.get(location);
			}
		}
		return null;
	}
	
	int Indexof(Stack<Square> stack, int x, int y){
		for(int i = 0; i < stack.size(); i++){
			if(stack.get(i).x == x && stack.get(i).y == y){
				return i;
			}
		}
		return -1;
	}
	
	boolean SelectAdjacent(Square square, World world, Stack<Square> stack){
		boolean value = false;
		if(!Stackcontains(stack, square.x-1, square.y-1) && world.checkOpen(square.x-1, square.y-1)){
			Square mysquare = new Square();
			mysquare.x = square.x-1;
			mysquare.y = square.y-1;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}else if(!Stackcontains(stack, square.x, square.y-1) && world.checkOpen(square.x, square.y-1)){
			Square mysquare = new Square();
			mysquare.x = square.x;
			mysquare.y = square.y-1;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}else if(!Stackcontains(stack, square.x+1, square.y-1) &&  world.checkOpen(square.x+1, square.y-1)){
			Square mysquare = new Square();
			mysquare.x = square.x+1;
			mysquare.y = square.y-1;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}else if(!Stackcontains(stack, square.x-1, square.y) &&  world.checkOpen(square.x-1, square.y)){
			Square mysquare = new Square();
			mysquare.x = square.x-1;
			mysquare.y = square.y;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}else if(!Stackcontains(stack, square.x-1, square.y+1) &&  world.checkOpen(square.x-1, square.y+1)){
			Square mysquare = new Square();
			mysquare.x = square.x-1;
			mysquare.y = square.y+1;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}else if(!Stackcontains(stack, square.x+1, square.y+1) &&  world.checkOpen(square.x+1, square.y+1)){
			Square mysquare = new Square();
			mysquare.x = square.x+1;
			mysquare.y = square.y+1;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}else if(!Stackcontains(stack, square.x+1, square.y) &&  world.checkOpen(square.x+1, square.y)){
			Square mysquare = new Square();
			mysquare.x = square.x+1;
			mysquare.y = square.y;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}else if(!Stackcontains(stack, square.x, square.y+1) &&  world.checkOpen(square.x, square.y+1)){
			Square mysquare = new Square();
			mysquare.x = square.x;
			mysquare.y = square.y+1;
			mysquare.distance = square.distance + 1;
			stack.add(mysquare);
			value = true;
		}
		return value;
			
	}
	
	boolean isAdjacent(Player player){
		if((player.y == y-1 && player.x == x) || (player.y == y+1 && player.x == x) || (player.y == y && player.x == x-1) || (player.y == y && player.x == x+1)){
			return true;
		}
		return false;
	}
	
	boolean Stackcontains(Stack<Square> stack, int x, int y){
		for(int i = 0; i < stack.size(); i++){
			if(stack.get(i).x == x && stack.get(i).y == y){
				return true;
			}
		}
		return false;
	}
}
