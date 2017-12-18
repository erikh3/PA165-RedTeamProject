package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;

public class ChangeRidePriceDTO {
    @NotNull
    private Long rideId;

    @NotNull
    private Double price;

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeRidePriceDTO that = (ChangeRidePriceDTO) o;

        if (rideId != null ? !rideId.equals(that.rideId) : that.rideId != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = rideId != null ? rideId.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChangeRidePriceDTO{" +
                "rideId=" + rideId +
                ", price=" + price +
                '}';
    }
}
