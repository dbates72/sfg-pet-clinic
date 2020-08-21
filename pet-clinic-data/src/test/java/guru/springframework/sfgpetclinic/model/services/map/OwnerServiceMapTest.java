package guru.springframework.sfgpetclinic.model.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OwnerServiceMapTest {
    OwnerServiceMap ownerMapService;
    Long id=1L;
    String lastName="LASTNAME";
    Owner owner;

    @BeforeEach
    void setUp() {
        //Mocks up the auto-wiring that Spring would do from the Constructor
        ownerMapService= new OwnerServiceMap(new PetTypeMapService(), new PetServiceMap());
        owner = new Owner();
        owner.setId(id);
        owner.setLastName(lastName);
        Pet pet = new Pet();
        PetType petType = new PetType();
        pet.setPetType(petType);
        owner.getPets().add(pet);
        ownerMapService.save(owner);
    }

    @Test
    void findAll() {
        //Add some data
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertEquals(1,ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(id);
        assertEquals(1, owner.getId());
    }

    @Test
    void save() {
        //Make sure Owner is not null
        //Make if Owner has Pets, that the PetType is not null
        //Make sure if Owner has Pets, that the PetID is not null
        Owner owner = new Owner();
        owner.setId(id+1);
        Owner savedOwner = ownerMapService.save(owner);
        assertEquals(id+1, savedOwner.getId());
        Set<Owner> ownerSet= ownerMapService.findAll();
        Set<Pet> pets= savedOwner.getPets();
        assertEquals(2, ownerSet.size());
        pets.forEach(aPet-> assertNotNull(aPet.getId()));
        pets.forEach(aPet-> assertNotNull(aPet.getPetType()));
        pets.forEach(aPet-> assertNotNull(aPet.getPetType().getId()));
    }

    @Test
    void saveThrowsException() {
        Long ownerId=1L;
        Owner owner = new Owner();
        owner.setId(ownerId);
        Pet pet = new Pet();
        owner.getPets().add(pet);
        Assertions.assertThrows(RuntimeException.class, () -> ownerMapService.save(owner));
    }

    @Test
    void delete() {
        ownerMapService.delete(this.owner);
        Set<Owner> ownerSet= ownerMapService.findAll();
        assertEquals(0,ownerSet.size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(id);
        Set<Owner> ownerSet= ownerMapService.findAll();
        assertEquals(0,ownerSet.size());
    }

    @Test
    void findByLastName() {
        Owner owner=ownerMapService.findByLastName(lastName);
        assertEquals(lastName, owner.getLastName());
    }
}