package guru.springframework.sfgpetclinic.model.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.model.services.PetService;
import guru.springframework.sfgpetclinic.model.services.PetTypeService;
import guru.springframework.sfgpetclinic.model.services.VisitService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
    private final PetTypeService petTypeService;
    private final PetService petService;

    public VisitMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Visit object) {
        super.delete(object);
    }

    @Override
    public Visit save(Visit object) {
        if(object!=null) {
            if(object.getPet()!=null) {
                Pet pet= object.getPet();
                if(pet.getPetType() !=null) {
                    if(pet.getPetType().getId()==null){
                        pet.setPetType(petTypeService.save(pet.getPetType()));
                    }
                }
                else {
                    throw new RuntimeException("Pet Type is required.");
                }
                if(pet.getId() == null) {
                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                }
            }
            return super.save(object);
        }
        return null;
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
