package tqs;

import java.util.Objects;
import java.lang.Object;

public class Address {

    private String city;
    private String zio;
    private String road;
    private String houseNumber;


    public Address(String road, String city, String zio, String houseNumber) {
        this.city = city;
        this.zio = zio;
        this.road = road;
        this.houseNumber = houseNumber;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZio() {
        return zio;
    }

    public void setZio(String zio) {
        this.zio = zio;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Address other = (Address) obj;

        if (!zio.equals(other.zio) || !houseNumber.equals(other.houseNumber)) {
            return false;
        }
        
        if (!road.equals(other.road) || !city.equals(other.city)) {
            return false;
        }

        return true;
    }

    public String toString() {
        return "Address{" + "road=" + road + ", city=" + city + ", zio=" + zio + ", houseNumber=" + houseNumber + '}';
    }


    public int hashCode() {
        int hash = road.hashCode();

        hash = 31 * hash + (city != null ? city.hashCode() : 0);
        hash = 31 * hash + (zio != null ? zio.hashCode() : 0);
        hash = 31 * hash + (houseNumber != null ? houseNumber.hashCode() : 0);

        return hash;
    }

}