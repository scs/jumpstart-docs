package ch.scs.jumpstart.trailerdetector.exercise.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Trailer {
  @SuppressWarnings("unused")
  @Id
  @GeneratedValue
  private int id;

  @Column(unique = true)
  private Integer trailerNumber;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private final Set<NetworkDevice> networkDevices = new HashSet<>();

  public Integer getTrailerNumber() {
    return trailerNumber;
  }

  public void setTrailerNumber(Integer trailerNumber) {
    this.trailerNumber = trailerNumber;
  }

  public Set<NetworkDevice> getNetworkDevices() {
    return networkDevices;
  }

  public void addNetworkDevice(NetworkDevice s) {
    networkDevices.add(s);
  }

  public void removeNetworkDevices(Collection<NetworkDevice> s) {
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
