package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping("/owners/find")
    public String findOwners(Model model) {
        model.addAttribute("owner",new Owner());
        return "owners/findOwners";
    }

    @GetMapping({"/owners", "/owners/index", "/owners/index.html"})
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        //allow parameterless GET request for /owners to return all records
        if(owner.getLastName() == null) {
            owner.setLastName("");
        }

        //find owners by last name
        List<Owner> results = this.ownerService.findAllByLastNameLike("%"+ owner.getLastName() + "%");

        if(results.isEmpty()) {
            //no owners found
            result.rejectValue("lastName", "notFound", "notFound");
            return "owners/findOwners";
        }
        else if (results.size() == 1) {
            //1 owner found
            owner=results.get(0);
            return "redirect:/owners/" + owner.getId();
        }
        else {
            //more than one owner found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(this.ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/owners/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/owners/new")
    public String processCreationForm(Owner owner, BindingResult result) {
        if(result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }
        else {
            Owner savedOwner=this.ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

   @GetMapping("/owners/{ownerId}/edit")
   public String initUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model) {
        Owner owner=ownerService.findById(ownerId);
        model.addAttribute("owner", owner);
        return "owners/createOrUpdateOwnerForm";
   }

   @PostMapping("/owners/{ownerId}/edit")
   public String processUpdateOwnerForm(Owner owner, BindingResult result, @PathVariable Long ownerId) {
        if(result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }
        else {
            //NOTE: Id needs to be explicitly set because we eliminated it in the binding
            owner.setId(ownerId);
            this.ownerService.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
   }

}
