import java.util.TimerTask;

class CountDown extends TimerTask {
  private final SplashScreen splashScreen;

  CountDown(SplashScreen splashScreen) {
    this.splashScreen = splashScreen;
  }

  public void run() {
    SplashScreen.access(this.splashScreen);
  }
}