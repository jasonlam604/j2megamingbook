import java.util.TimerTask;

class CountDown extends TimerTask {
  public static final int TASK_SPLASH = 0;	
  public static final int TASK_TIMER = 1;	
	
  SplashScreen splashScreen;
  GameScreen gameScreen;

  int task;

  CountDown(SplashScreen splashScreen) {
    this.splashScreen = splashScreen;
    this.task = TASK_SPLASH;
  }
  
  CountDown(GameScreen gameScreen,int task) {
    this.gameScreen = gameScreen;
    this.task = task;
  }

  public void run() {    
    switch(this.task) {
      case TASK_SPLASH:  SplashScreen.access(this.splashScreen); break;
      case TASK_TIMER: gameScreen.timerTask(); break;
    };    
  }
}