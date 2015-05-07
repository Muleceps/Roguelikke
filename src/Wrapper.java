import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Wrapper extends BasicGame{
	
	public static int gamewidth = 1000;
	public static int gameheight = 700;
	private enum STATE{
		GAME,
		MENU
	};
	
	private STATE state = STATE.GAME;
	private World world;
	private Player player;
	public int selected;
	Input myinput = new Input(600);
	private boolean wPressed = false, nwPressed = false, nPressed = false, nePressed = false, ePressed = false, sePressed = false, sPressed = false, swPressed = false;
 
    public Wrapper(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Wrapper()
    {
        super("Down Among the Stones");
    }
 
    public void init(GameContainer gc) 
			throws SlickException {
    	world = new World();
    	player = new Player(world);
    	world.setPlayer(player);
    	gc.setMaximumLogicUpdateInterval(130);
		gc.setMinimumLogicUpdateInterval(130);
    }
 
    public void update(GameContainer gc, int delta) 
			throws SlickException     
    {	
    	
    	checkPressed();
    	if(state == STATE.GAME){
	    	if(swPressed){
				world.clearLog();
	    		moveto(player.x-1, player.y+1);
	    		world.update(player);
	    	}else if(wPressed){
				world.clearLog();
				moveto(player.x-1, player.y);
				world.update(player);
			}else if(nwPressed){
				world.clearLog();
	    		moveto(player.x-1, player.y-1);
	    		world.update(player);
	    	}else if(nPressed){
				world.clearLog();
				moveto(player.x, player.y-1);
				world.update(player);
			}else if(nePressed){
				world.clearLog();
	    		moveto(player.x+1, player.y-1);
	    		world.update(player);
	    	}else if(ePressed){
				world.clearLog();
				moveto(player.x+1, player.y);
				world.update(player);
			}else if(sePressed){
				world.clearLog();
				moveto(player.x+1, player.y+1);
				world.update(player);
			}else if(sPressed){
				world.clearLog();
				moveto(player.x, player.y+1);
				world.update(player);
			}
    	}else if(state == STATE.MENU){
    		if(sPressed){
    			if(selected < player.inventory.size() - 1)
    				selected++;
    			else
    				selected = 0;
    		}
    		if(nPressed){
    			if(selected > 0)
    				selected--;
    			else
    				selected = player.inventory.size() - 1;
    		}
    		if(myinput.isKeyDown(Input.KEY_E)){
    			player.equip(selected);
    			if(selected > player.inventory.size() - 1 )
    				selected = player.inventory.size();
    		}
    		if(myinput.isKeyDown(Input.KEY_D)){
    			player.drop(selected, world);
    			if(selected > player.inventory.size() - 1 )
    				selected = player.inventory.size();
    		}
    	}
    	resetPressed();

		
		
    	
    }
    
    public void moveto(int x, int y){
    	if(world.dungeon.get(world.currentfloor).width <= x || x <= 0 || world.dungeon.get(world.currentfloor).height <= y || y <= 0)
    		return;
    	if(world.dungeon.get(world.currentfloor).isEnemy(x,y)){
    		world.fight(x, y, player);	
    	}else if(world.checkOpen(x, y)){
    		player.x = x;
    		player.y = y;
    	}
    }
    
    public void resetPressed(){
    	wPressed = false;
    	swPressed = false;
    	sPressed = false;
    	sePressed = false;
    	ePressed = false;
    	nePressed = false;
    	nPressed = false;
    	nwPressed = false;
    }
    
    public void checkPressed(){
    	if(myinput.isKeyDown(Input.KEY_PERIOD) && (myinput.isKeyDown(Input.KEY_RSHIFT) || myinput.isKeyDown(Input.KEY_LSHIFT)) && world.dungeon.get(world.currentfloor).map[player.x][player.y] == '>'){
    		world.increaseLevel(player);
    	}
    	if(myinput.isKeyDown(Input.KEY_COMMA) && (myinput.isKeyDown(Input.KEY_RSHIFT) || myinput.isKeyDown(Input.KEY_LSHIFT)) && world.dungeon.get(world.currentfloor).map[player.x][player.y] == '<'){
    		world.decreaseLevel(player);
    	}
    	if(myinput.isKeyDown(Input.KEY_I)){
    		if(state == STATE.GAME){
    			state = STATE.MENU;
    			selected = 0;
    		}else if(state == STATE.MENU)
    			state = STATE.GAME;
    	}
    	if(myinput.isKeyDown(Input.KEY_X) && world.dungeon.get(world.currentfloor).map[player.x][player.y] == '$'){
    		world.pickupItem(player);
    	}
    	if(myinput.isKeyDown(Input.KEY_4) || myinput.isKeyDown(Input.KEY_LEFT)){
    		wPressed = true;
    	}
    	if(myinput.isKeyDown(Input.KEY_1))
    			swPressed = true;
    	if(myinput.isKeyDown(Input.KEY_2) || myinput.isKeyDown(Input.KEY_DOWN)){
    		sPressed = true;
    	}
    	if(myinput.isKeyDown(Input.KEY_3))
			sePressed = true;
    	if(myinput.isKeyDown(Input.KEY_6) || myinput.isKeyDown(Input.KEY_RIGHT)){
    		ePressed = true;
    	}
    	if(myinput.isKeyDown(Input.KEY_7))
			nwPressed = true;
    	if(myinput.isKeyDown(Input.KEY_8) || myinput.isKeyDown(Input.KEY_UP)){
    		nPressed = true;
    	}
    	if(myinput.isKeyDown(Input.KEY_9))
			nePressed = true;
    	
    	if(wPressed && ePressed){
    		wPressed = false;
    		ePressed = false;
    	}
    	
    	if(nPressed && sPressed){
    		nPressed = false;
    		sPressed = false;
    	}
    		
    	
    	if(wPressed && sPressed){
    		swPressed = true;
    		wPressed = false;
    		sPressed = false;
    	}
    	if(wPressed && nPressed){
    		nwPressed = true;
    		wPressed = false;
    		nPressed = false;
    	}
    	if(ePressed && nPressed){
    		nePressed = true;
    		nPressed = false;
    		ePressed = false;
    	}
    	if(ePressed && sPressed){
    		sePressed = true;
    		ePressed = false;
    		sPressed = false;
    	}
    }
 
    public void render(GameContainer gc, Graphics g) 
			throws SlickException 
    {
    	if(state == STATE.GAME){
	    	Image floor = new Image("res/wood.png");
	    	Image wall = new Image("res/brick.png");
	    	Image hero = new Image("res/hero.png");
	    	Image stairdown = new Image("res/stairdown.png");
	    	Image stairup = new Image("res/stairup.png");
	    	Image enemy = new Image("res/goblin.png");
	    	Image item = new Image("res/chest.png");
	    	for(int i = 0; i < 16;i++){
	    		for(int j = 0; j < 14;j++){
	    			if(player.x-8+i < world.dungeon.get(world.currentfloor).width && player.x-8+i > 0 && player.y-7+j < world.dungeon.get(world.currentfloor).height && player.y-7+j > 0){
		    			if(world.dungeon.get(world.currentfloor).map[player.x-8+i][player.y-7+j] == '1'){
		    					floor.draw(i*50,j*50);
		    			}else if(world.dungeon.get(world.currentfloor).map[player.x-8+i][player.y-7+j] == '>'){
		    				stairdown.draw(i*50,j*50);
		    			}else if(world.dungeon.get(world.currentfloor).map[player.x-8+i][player.y-7+j] == '<'){
		    				stairup.draw(i*50,j*50);
		    			}else if(world.dungeon.get(world.currentfloor).map[player.x-8+i][player.y-7+j] == '0')
		    				wall.draw(i*50, j*50);
		    			else if(world.dungeon.get(world.currentfloor).map[player.x-8+i][player.y-7+j] == '$')
		    				item.draw(i*50, j*50);
		    			
		    			if(world.dungeon.get(world.currentfloor).isEnemy(player.x-8+i, player.y-7+j))
		    				enemy.draw(i*50, j*50);
		    				
		    			
	    			}
	    			
	    			if(8 == i && 7 == j){
	    				hero.draw(i*50,j*50);
	    			}
	    		}
	    	}
    	}else if(state == STATE.MENU){
    		if(player.inventory.size() >= 10){
    			if(selected + 9 >= player.inventory.size()){
    				for(int i = 0; i <  9; i++){
		    			if(i + player.inventory.size() - 9 == selected){
		    				g.drawRect(10, i*70 + 30, 710, 70);
		    			}
		    			g.drawRect(15, i*70 + 35, 700, 60);
		    			g.drawString(player.inventory.get(i + player.inventory.size() - 9).description(), 20, i*70 + 40);
    				
    	    		}
    			}else
    			for(int i = 0; i <  9; i++){
    				if(i + selected < player.inventory.size()){
		    			if(i == 0){
		    				g.drawRect(10, i*70 + 30, 710, 70);
		    			}
		    			g.drawRect(15, i*70 + 35, 700, 60);
		    			g.drawString(player.inventory.get(i + selected).description(), 20, i*70 + 40);
    				}
	    		}
    		}else{
	    		for(int i = 0; i < player.inventory.size(); i++){
	    			if(i == selected){
	    				g.drawRect(10, i*70 + 30, 710, 70);
	    			}
	    			g.drawRect(15, i*70 + 35, 700, 60);
	    			g.drawString(player.inventory.get(i).description(), 20, i*70 + 40);
	    		}
    		}
    	}
    	
    	printlog(g);
    	
    	
 
    }
 
    private void printlog(Graphics g) {
    	g.drawString("hp:" + player.health + " ac:" + player.ac, 807, 0);
    	g.drawString("mp:" + player.mana, 807, 15);
    	for(int i = 0, l = 0; i < world.log.length() - 1; i+=19, l++)
    		if(i + 20 < world.log.length() -1){
    			String output = "";
    			int k = 0;
    			while(output.length() + world.log.substring(i, i + 20).split(" ")[k].length() < 19){
    				output += world.log.substring(i, i + 20).split(" ")[k];
    				output += " ";
    				k++;
    				if(k > world.log.substring(i, i + 20).split(" ").length)
    					break;
    				
    			}
    			i -= world.log.substring(i, i + 20).split(" ")[k].length();
    			output = output.trim();
    			g.drawString(output, 807, 30 + l * 15);
    			//g.drawString(world.log.substring(i, i + 20), 807, 30 + i);
    		}else
    			g.drawString(world.log.substring(i), 807, 30 + l * 15);
	}

	public static void main(String[] args) 
			throws SlickException
    {
         AppGameContainer app = 
			new AppGameContainer(new Wrapper());
 
         app.setDisplayMode(1000, 700, false);
         app.setTargetFrameRate(100);
         app.start();
    }
}