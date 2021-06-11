package ch.scs.jumpstart.trailerdetector.exercise.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Trailer {
  private Integer trailerNumber;
  private final Set<String> networkDevices = new HashSet<>();

  public Integer getTrailerNumber() {
    return trailerNumber;
  }

  public void setTrailerNumber(Integer trailerNumber) {
    this.trailerNumber = trailerNumber;
  }

  public void addNetworkDevice(String s) {
    networkDevices.add(s);
  }

  public void removeNetworkDevices(Collection<String> s) {
    networkDevices.removeAll(s);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Trailer trailer = (Trailer) o;
    return Objects.equals(trailerNumber, trailer.trailerNumber)
        && networkDevices.equals(trailer.networkDevices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trailerNumber, networkDevices);
  }

  @Override
  public String toString() {
    return "Trailer{" + "trailerNumber=" + trailerNumber + '}';
  }
}
