package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * DataTransferObject for creating Place
 *
 * @author miroslav.laco@gmail.com
 */
public class PlaceCreateDTO {

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    //Getters + Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Equals + Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceCreateDTO)) return false;

        PlaceCreateDTO that = (PlaceCreateDTO) o;

        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    //ToString

    @Override
    public String toString() {
        return "PlaceCreateDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
