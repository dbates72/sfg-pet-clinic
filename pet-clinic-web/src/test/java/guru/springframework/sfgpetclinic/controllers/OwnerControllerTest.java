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

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
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

    @BeforeEach
    void setUp() {
        //Setting up the data for our model
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner1.setId(2L);
        owners = new HashSet<>();
        owners.add(owner1);
        owners.add(owner2);

        //Setting up our OwnerController in the MockMvc Spring Test service
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void listOwners() throws Exception {
        //Setup the Mockito mock service layer for the OwnerController that we are testing
        when(ownerService.findAll()).thenReturn(owners);

        //Test the OwnerController through the MockMvc Spring Test service
        mockMvc.perform(get("/owners")) //Perform an HTTP Get operation on URL /owners
                .andExpect(status().is(200)) //Expect that HTTP returns a 200
                .andExpect(view().name("owners/index")) //Expect that controller provides a view called "owners/index"
                .andExpect(model().attribute("owners", hasSize(2))); //Expect a model provided to the view with size of 2
    }

    @Test
    void listOwnersByIndex() throws Exception {
        //Setup the Mockito mock service layer for the OwnerController that we are testing
        when(ownerService.findAll()).thenReturn(owners);

        //Test the OwnerController through the MockMvc Spring Test service
        mockMvc.perform(get("/owners/index")) //Perform an HTTP Get operation on URL /owners
                .andExpect(status().is(200)) //Expect that HTTP returns a 200
                .andExpect(view().name("owners/index")) //Expect that controller provides a view called "owners/index"
                .andExpect(model().attribute("owners", hasSize(2))); //Expect a model provided to the view with size of 2
    }

    @Test
    void listOwnersByIndexHtml() throws Exception {
        //Setup the Mockito mock service layer for the OwnerController that we are testing
        when(ownerService.findAll()).thenReturn(owners);

        //Test the OwnerController through the MockMvc Spring Test service
        mockMvc.perform(get("/owners/index.html")) //Perform an HTTP Get operation on URL /owners
                .andExpect(status().is(200)) //Expect that HTTP returns a 200
                .andExpect(view().name("owners/index")) //Expect that controller provides a view called "owners/index"
                .andExpect(model().attribute("owners", hasSize(2))); //Expect a model provided to the view with size of 2
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notimplemented"));
        verifyNoInteractions(ownerService);
    }
}