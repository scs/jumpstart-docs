package ch.scs.jumpstart.trailerdetector.exercise.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NetworkDevice {
  @Id private String macAddress;

  public NetworkDevice() {}

  public NetworkDevice(String macAddress) {
    this.macAddress = macAddress;
  }

  public String getMacAddress() {
    return macAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NetworkDevice that = (NetworkDevice) o;
    return macAddress.equals(that.macAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(macAddress);
  }
}
