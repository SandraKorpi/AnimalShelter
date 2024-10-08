package sandrakorpi.animalshelterapi.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sandrakorpi.animalshelterapi.Dtos.ShelterDto;
import sandrakorpi.animalshelterapi.Models.Shelter;
import sandrakorpi.animalshelterapi.Services.ShelterService;
import java.util.List;
import java.util.Optional;


@Tag(name = "SHELTERS", description = "Endpoints related to managing shelters")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shelter")
public class ShelterController {

    private final ShelterService shelterService;


    @GetMapping
    @Operation(summary = "Get all shelters", description = "Retrieve a list of all available shelters.")
    public ResponseEntity<List<Shelter>> getAllShelters() {
        List<Shelter> shelters = shelterService.getAllShelters();
            return ResponseEntity.ok(shelters);
    }


    @GetMapping("/name/{name}")
    @Operation(summary = "Get a shelter by name", description = "Retrieve details of a shelter by its name.")
    public ResponseEntity<Shelter> getShelterByName(@PathVariable String name) {
        Optional<Shelter> shelter = shelterService.getShelterByName(name);
            return shelter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")    //hämtar shelter per ID
    @Operation(summary = "Get a shelter by ID", description = "Retrieve details of a shelter by its unique ID.")
    public ResponseEntity<Shelter> getOneShelter(@PathVariable long id) {
        Optional<Shelter> shelter = shelterService.getOneShelter(id);
            return shelter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/available-beds")
    @Operation(summary = "Update the available beds of a shelter", description = "Modify the number of available beds for a given shelter.")
    public ResponseEntity<Void> updateAvailableBeds(@PathVariable long id, @RequestBody String availableBeds) {
        boolean updated = shelterService.updateAvailableBeds(id, availableBeds);
            return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a shelter", description = "Update the information of a specific shelter.")
    public ResponseEntity<ShelterDto> updateOneShelter(@PathVariable long id, @RequestBody ShelterDto shelterDto) {
        Optional<ShelterDto> updatedShelter = shelterService.updateOneShelter(id, shelterDto);
            return updatedShelter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    @Operation(summary = "Create a new shelter", description = "Create a new shelter with the provided information.")
    public ResponseEntity<ShelterDto> createNewShelter(@RequestBody ShelterDto shelterDto) {
        ShelterDto createdShelter = shelterService.createNewShelter(shelterDto);
            return ResponseEntity.status(201).body(createdShelter);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a shelter", description = "Delete a specific shelter by its unique ID. Only accessible to admin users.")
    public ResponseEntity<Void> deleteOneShelter(@PathVariable int id) {
        boolean deleted = shelterService.deleteOneShelter(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
