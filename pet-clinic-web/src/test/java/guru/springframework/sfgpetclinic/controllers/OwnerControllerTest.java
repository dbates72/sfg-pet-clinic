package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    private Set<Owner> owners;
    private MockMvc mockMvc;
    private Owner owner1;
    private Owner owner2;

    @BeforeEach
    void setUp() {
        //Setting up the data for our model
        owner1 = new Owner();
        owner1.setId(1L);
        owner2 = new Owner();
        owner2.setId(2L);
        owners = new HashSet<>();
        owners.add(owner1);
        owners.add(owner2);

        //Setting up our OwnerController in the MockMvc Spring Test service
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        ArrayList<Owner> ownerList=new ArrayList<>();
        ownerList.add(owner1);
        ownerList.add(owner2);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
        verify(ownerService, times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        ArrayList<Owner> ownerList=new ArrayList<>();
        ownerList.add(owner1);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(ownerService, times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    void displayOwner() throws Exception {
        //Setup the Mockito mock service layer for the OwnerController that we are testing
        when(ownerService.findById(1L)).thenReturn(owner1);

        //Test the OwnerController through the MockMvc Spring Test service
        mockMvc.perform(get("/owners/1")) //Perform an HTTP Get operation on URL /owners/{ownerId}
                .andExpect(status().is(200)) //Expect that HTTP returns a 200
                .andExpect(view().name("owners/ownerDetails")) //Expect that controller provides a view called "owners/ownerDetails"
                .andExpect(model().attribute("owner", hasProperty("id", is(1L)))); //Expect a model that is not null
    }

}