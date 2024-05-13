package api.pet.objectMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utils.PetStatus;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class Pet {
    private String id;
    private PetCategory petCategory;
    private String name;
    private List<String> photoUrls;
    private List<PetTag> petTag;
    private PetStatus petStatus;
}