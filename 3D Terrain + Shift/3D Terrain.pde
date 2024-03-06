int cols, rows;
int scale = 20;

//movement
float velX = 0;
float velY = 0;
float moveSpeed;
float walk = 0.025;
float run = 0.05;
boolean isUp, isDown, isLeft, isRight, isFast, isTurn;

//2d array to contain z values
float[][] terrain;

//2d array of hair objects. a random amt of them will not contain hair. will be
//compared with terrain array
Hair[][] hairArray;

void setup() {
  size(960, 720, P3D);
  //# of columns & rows of triangles to be shown
  cols = 100;
  rows = 100;

  //create empty float vars (z) in array to be used when drawing
  terrain = new float[cols][rows];
  //same with hair
  hairArray = new Hair[cols][rows];
}

void draw() {
  background(127);
  
  //text
  fill(64,32,0);
  textSize(15);
  textAlign(CENTER);
  text("Controls: WASD, +shift to run",width/2,20);
  
  //skin color
  stroke(64, 32, 0, 100);
  fill(200, 160, 127);

  //controls
  if (isFast == false)moveSpeed = walk;
  if (isFast)moveSpeed = run;
  if (isUp)velY -= moveSpeed;
  if (isDown)velY += moveSpeed;
  if (isLeft)velX -= moveSpeed;
  if (isRight)velX += moveSpeed;

  //loop to create z values
  //this loop also moves all points since it uses velX & velY
  float posY = velY;
  for (int y = 0; y < rows; y++) {
    float posX = velX;
    for (int x = 0; x < cols; x++) {
      terrain[x][y] = map(noise(posX, posY), 0, 1, -50, 50);
      
      //10% chance that vertex will have hair
      if (int(random(1,11))==10) {
        hairArray[x][y] = new Hair(posX,posY,terrain[x][y]);
      }
      posX += .1;
    }
    posY += .1;
  }

  //rotate & translate grid to view it
  translate(width/2, height/2);
  rotateX(PI/2.5);
  translate(-width, -height*1.5, 80);

  //draw grid
  strokeWeight(1);
  for (int y = 0; y < rows-1; y++) {
    beginShape(TRIANGLE_STRIP);
    for (int x = 0; x < cols; x++) {
      //triangle_strips are drawn by plotting a zigzag
      vertex(x*scale, y*scale, terrain[x][y]);
      vertex(x*scale, (y+1)*scale, terrain[x][y+1]);
      
      //check if hair exists; if it does, draw
      if (hairArray[x][y] == null){
        ;
      }
      else {
        //hairArray[x][y].draw();
      }
      
    }
    endShape();
  }
  
}

/*-----------FUNCTIONS-----------*/

void keyPressed() {
  setMove(keyCode, true);
}

void keyReleased() {
  setMove(keyCode, false);
}

boolean setMove(int k, boolean b) {
  switch (k) {
  case 87: //keycode for W
    return isUp = b;

  case 83: //keycode for S
    return isDown = b;

  case 65: //keycode for A
    return isLeft = b;

  case 68: //keycode for D
    return isRight = b;

  case SHIFT:
    return isFast = b;

  case LEFT:
    return isTurn = b;

  default:
    return b;
  }
}

/*-----------CLASSES-----------*/

class Hair {
  float x, y, z;
  //make a random # of hair segments. will contain coordinates
  int[] hairSegs = new int[int(random(5,11))];
  
  Hair(float tempX, float tempY, float tempZ) {
    x = tempX;
    y = tempY;
    z = tempZ;
  }
  
  void draw() {  
    stroke(0);
    strokeWeight(hairSegs.length);
    float prevX = x;
    float prevY = y;
    float prevZ = z;
    
    //use loop to draw line upwards
    //make sure line segments are connected by using prev&next coords
    for (int i=0; i<hairSegs.length; i++) {
      float nextX = prevX+random(0,1);
      float nextY = prevY+random(0,1);
      float nextZ = prevZ+scale;
      line(prevX,prevY,prevZ,nextX,nextY,nextZ);
      
      //make thinner as it goes up
      strokeWeight(hairSegs.length-i);
      
      //make next the new prev
      prevX = nextX;
      prevY = nextY; 
      prevZ = nextZ;    
    }

  }
  
}
