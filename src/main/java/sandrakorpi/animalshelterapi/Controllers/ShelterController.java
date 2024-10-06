package sandrakorpi.animalshelterapi.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sandrakorpi.animalshelterapi.Dtos.ShelterDto;
import sandrakorpi.animalshelterapi.Models.Shelter;
import sandrakorpi.animalshelterapi.Services.ShelterService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shelter")
public class ShelterController {


    @Autowired
    private ShelterService shelterService;


    @GetMapping
    public ResponseEntity<List<Shelter>> getAllShelters() {
        List<Shelter> shelters = shelterService.getAllShelters();
        return ResponseEntity.ok(shelters);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Shelter> getShelterByName(@PathVariable String name) {
        Optional<Shelter> shelter = shelterService.getShelterByName(name);
        return shelter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")    //hämtar shelter per ID
    public ResponseEntity<Shelter> getOneShelter(@PathVariable long id) {
        Optional<Shelter> shelter = shelterService.getOneShelter(id);
        return shelter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/available-beds")
    public ResponseEntity<Void> updateAvailableBeds(@PathVariable long id, @RequestBody String availableBeds) {
        boolean updated = shelterService.updateAvailableBeds(id, availableBeds);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shelter> updateOneShelter(@PathVariable long id, @RequestBody ShelterDto shelterDto) {
        Optional<Shelter> updatedShelter = shelterService.updateOneShelter(id, shelterDto);
        return updatedShelter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public HttpEntity<Shelter> createNewShelter(@RequestBody ShelterDto shelterDTO) {
        Shelter createdShelter = shelterService.createNewShelter(shelterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShelter);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOneShelter(@PathVariable int id) {
        boolean deleted = shelterService.deleteOneShelter(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
