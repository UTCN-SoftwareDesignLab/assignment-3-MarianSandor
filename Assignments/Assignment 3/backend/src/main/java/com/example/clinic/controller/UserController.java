package com.example.clinic.controller;

import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.dto.DoctorDTO;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.service.ConsultationService;
import com.example.clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.example.clinic.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConsultationService consultationService;

    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.findAll();
    }

    @GetMapping(ENTITY)
    public UserDTO getUser(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping(SEARCH + QUERY)
    public List<UserDTO> allUsersBy(@PathVariable String query){
        return userService.search(query);
    }

    @GetMapping(ENTITY + CONSULTATIONS)
    public List<ConsultationDTO> getConsultationsForUser(@PathVariable Long id) {
        return consultationService.getPastConsultationsForUser(id);
    }

    /** This request returns a response which is interpreted different based on the query format.
     *  Functionality 1:
     *      query format: "dd-MM-yyyy hh:mm" -> response contains a list with only one element
     *                                              which is a list containing all the doctors available at the given date.
     *  Functionality 2:
     *      query format: "dd-MM-yyyy" -> response contains list of 24 lists (index meaning hours in a day)
     *                                      each containing the doctors available at the hour of the given day.
     * @param query
     * @return
     */
    @GetMapping(AVAILABLE + QUERY)
    public List<List<DoctorDTO>> getDoctorsAvailable(@PathVariable String query) { return userService.getDoctorsAvailable(query); }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO item) {
        return userService.create(item);
    }

    @PutMapping(ENTITY)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO item) {
        return userService.edit(id, item);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
