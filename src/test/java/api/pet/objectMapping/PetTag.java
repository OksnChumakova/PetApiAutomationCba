package api.pet.objectMapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor(onConstructor_ = {@JsonCreator})
@EqualsAndHashCode
public class PetTag {
    private int id;
    private String name;
}
