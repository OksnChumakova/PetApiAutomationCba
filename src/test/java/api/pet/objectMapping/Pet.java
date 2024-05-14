package api.pet.objectMapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.PetStatus;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(onConstructor_ = {@JsonCreator})
public class Pet {
    private long id;
    private PetCategory petCategory;
    private String name;
    private List<String> photoUrls;
    private List<PetTag> tags;
    private PetStatus petStatus;
}