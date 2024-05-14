package api.pet.objectMapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;
import utils.PetStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(onConstructor_ = {@JsonCreator})
public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<PetTag> tags;
    private PetStatus status;
}