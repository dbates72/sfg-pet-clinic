package guru.springframework.sfgpetclinic.model.services.map;

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
        if(object!=null ||object.getPet() == null || object.getPet().getOwner() == null ||
                object.getPet().getPetType()== null || null == object.getPet().getOwner().getId()) {
            throw new RuntimeException("Invalid visit");
        }
        else{
            return super.save(object);
        }
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
