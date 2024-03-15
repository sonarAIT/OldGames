public class Star{
  int x;
  int y;

  Star(int x,int y){
    this.x = x;
    this.y = y;
  }

  int getx(){
    return x;
  }

  int gety(){
    return y;
  }

  void setx(int x){
    this.x = x;
  }

  void sety(int y){
    this.y = y;
  }

  void setx2(int x){
    this.x += x;
  }
}
