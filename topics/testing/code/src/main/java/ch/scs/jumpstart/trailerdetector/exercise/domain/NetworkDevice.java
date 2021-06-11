package ch.scs.jumpstart.trailerdetector.exercise.domain;

public class NetworkDevice {
  private final String macAddress;

  public NetworkDevice(String macAddress) {
    this.macAddress = macAddress;
  }

  public String getMacAddress() {
    return macAddress;
  }
}
