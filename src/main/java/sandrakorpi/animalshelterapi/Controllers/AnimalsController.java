package sandrakorpi.animalshelterapi.Controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sandrakorpi.animalshelterapi.Services.AnimalsService;
import sandrakorpi.animalshelterapi.Dtos.AnimalsDto;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
@Tag(name = "ANIMALS", description = "Endpoints for managing animals")

public class AnimalsController {

    private final AnimalsService animalsService;

    @GetMapping("")
    @Operation(summary = "Get all Animals", description = "Get a list of all animals")
    public ResponseEntity<List<AnimalsDto>>getAllAnimals(){
        List<AnimalsDto> animals = animalsService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/cats")
    @Operation(summary = "Get all Cats", description = "Get a list of all cats")
    public ResponseEntity<List<AnimalsDto>> getAllCats() {
        List<AnimalsDto> cats = animalsService.getAllCats();
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/dogs")
    @Operation(summary = "Get all Dogs", description = "Get a list of all dogs")
    public ResponseEntity<List<AnimalsDto>> getAllDogs() {
        List<AnimalsDto> dogs = animalsService.getAllDogs();

        return ResponseEntity.ok(dogs);
    }


   @GetMapping("/{id}")
   @Operation(summary = "Get an animal by ID", description = "Get an animal by its ID.")
    public ResponseEntity<AnimalsDto> getOneAnimal(@PathVariable long id){
        Optional<AnimalsDto> animal = animalsService.getOneAnimal(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @Operation(summary = "Create a new animal", description = "Add a new animal to the list.")
    public ResponseEntity<AnimalsDto> createNewAnimal(@RequestBody AnimalsDto animalDto){
        AnimalsDto createdAnimal = animalsService.createAnimal(animalDto);
        return ResponseEntity.ok(createdAnimal);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update an existing animal", description = "Update an animal's details by its ID.")
    public ResponseEntity<Optional<AnimalsDto>> updateOneAnimal(@PathVariable Long id,
                                                                @RequestBody AnimalsDto animalDto){
        Optional<Optional<AnimalsDto>> updatedAnimal = Optional.ofNullable(animalsService.updateOneAnimal(id, animalDto));
        return updatedAnimal.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an animal", description = "Delete an animal from the list.")
    public ResponseEntity<String> deleteOneAnimal(@PathVariable long id){
        boolean deleted = animalsService.deleteAnimal(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
