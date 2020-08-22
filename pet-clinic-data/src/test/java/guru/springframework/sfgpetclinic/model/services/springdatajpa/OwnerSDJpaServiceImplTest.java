package guru.springframework.sfgpetclinic.model.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.model.repositories.PetRepository;
import guru.springframework.sfgpetclinic.model.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private OwnerSDJpaServiceImpl ownerSDJpaService;

    private Owner owner;
    private Set<Owner> ownerSet=new HashSet<>();
    private String lastName="LASTNAME";
    private Long id=1L;

    @BeforeEach
    void setUp() {
        //Return data setup
        owner = new Owner();
        owner.setId(id);
        owner.setLastName(lastName);
        ownerSet.add(owner);
    }

    @Test
    void findByLastName() {
        //Mockito to return the return data from the Mock objects
        //Remember: uses class that the class we are testing depends on (mocked class)
        when(ownerRepository.findByLastName(anyString())).thenReturn(owner);

        //Method test
        //Remember: uses class we are testing
        Owner foundOwner = ownerSDJpaService.findByLastName(lastName);

        //JUnit assertion tests
        assertEquals(lastName, foundOwner.getLastName());

        //Verify that the mock interaction occurred
        verify(ownerRepository).findByLastName(lastName);
    }

    @Test
    void findAll() {
        //Mockito to return the return data from the Mock objects
        //Remember: uses class that the class we are testing depends on (mocked class)
        when(ownerRepository.findAll()).thenReturn(ownerSet);

        //Method test
        //Remember: uses class we are testing
        Set<Owner> foundOwnerSet=ownerSDJpaService.findAll();

        //JUnit assertion tests
        assertEquals(1, foundOwnerSet.size());

        //Verify that the mock interaction occurred
        verify(ownerRepository).findAll();
    }

    @Test
    void findByIdNotFound() {
        //Mockito to return the return data from the Mock objects
        //Remember: uses class that the class we are testing depends on (mocked class)
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Method test
        //Remember: uses class we are testing
        Owner foundOwner = ownerSDJpaService.findById(id);

        //JUnit assertion tests
        assertNull(foundOwner);

        //Verify that the mock interaction occurred
        verify(ownerRepository).findById(id);
    }

    @Test
    void save() {
        //Mockito to return the return data from the Mock objects
        //Remember: uses class that the class we are testing depends on (mocked class)
        when(ownerRepository.save(owner)).thenReturn(owner);

        //Method test
        //Remember: uses class we are testing
        Owner savedOwner = ownerSDJpaService.save(owner);

        //JUnit assertion tests
        assertNotNull(savedOwner);

        //Verify that the mock interaction occurred
        verify(ownerRepository).save(owner);
    }

    @Test
    void delete() {
        //Mockito to return the return data from the Mock objects
        //Remember: uses class that the class we are testing depends on (mocked class)
        //No return data to test for this method

        //Method test
        //Remember: uses class we are testing
        ownerSDJpaService.delete(owner);

        //JUnit assertion tests
        //No assertions to test for this method

        //Verify that the mock interaction occurred
        verify(ownerRepository).delete(owner);
    }

    @Test
    void deleteById() {
        //Mockito to return the return data from the Mock objects
        //Remember: uses class that the class we are testing depends on
        //No return data to test for this method

        //Method test
        //Remember: uses class we are testing
        ownerSDJpaService.deleteById(id);

        //JUnit assertion tests
        //No assertions to test for this method

        //Verify that the mock interaction occurred
        verify(ownerRepository).deleteById(id);
    }
}