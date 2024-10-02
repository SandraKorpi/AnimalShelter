package sandrakorpi.animalshelterapi.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sandrakorpi.animalshelterapi.Models.Animals;
import sandrakorpi.animalshelterapi.Services.AnimalsService;
import sandrakorpi.animalshelterapi.Dtos.AnimalsDto;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor

public class AnimalsController {

    private final AnimalsService animalsService;

    //Hjälpfunktion för att returnera instans av AnimalsDto från Animals
    private AnimalsDto convertToDto(Animals animal) {
        return new AnimalsDto(
                animal.getId(),
                animal.getName(),
                animal.getAnimalType(),
                animal.getBreed(),
                animal.getAge());
    }

    //Hjälpfunktion för att hämta alla djur genom konvertering av lista av Animals till lista av AnimalsDto.
    //Returnerar ResponseEntity
    private ResponseEntity<List<AnimalsDto>> getAnimalsResponseEntity(List<Animals> animals) {
        List<AnimalsDto> animalDtos = animals.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(animalDtos);
    }

    @GetMapping("")
    public ResponseEntity<List<AnimalsDto>>getAllAnimals(){
        List <Animals> animals = animalsService.getAllAnimals();

        return getAnimalsResponseEntity(animals);
    }

    @GetMapping("/cats")
    public ResponseEntity<List<AnimalsDto>> getAllCats() {
        List<Animals> cats = animalsService.getAllCats();

        return getAnimalsResponseEntity(cats);
    }

    @GetMapping("/dogs")
    public ResponseEntity<List<AnimalsDto>> getAllDogs() {
        List<Animals> dogs = animalsService.getAllDogs();

        return getAnimalsResponseEntity(dogs);
    }


   @GetMapping("/{id}")
    public ResponseEntity<AnimalsDto> getOneAnimal(@PathVariable long id){
        Optional<Animals> animal = animalsService.getOneAnimal(id);

       if (animal.isPresent()) {
           return ResponseEntity.ok(convertToDto(animal.get()));
       } else {
           return ResponseEntity.notFound().build();
       }
    }

    @PostMapping("")
    public ResponseEntity<AnimalsDto> createNewAnimal(@RequestBody Animals newAnimal){
        Animals animal = animalsService.saveAnimal(newAnimal);

        return ResponseEntity.ok(convertToDto(animal));
    }

    @PatchMapping("{id}")
    public ResponseEntity<AnimalsDto> updateOneAnimal(@PathVariable Long id,
                                                      @RequestBody Animals newAnimal){
        Animals patchedAnimal = animalsService.patchAnimal(newAnimal, id);
        return ResponseEntity.ok(convertToDto(patchedAnimal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneAnimal(@PathVariable long id){
        animalsService.deleteAnimal(id);

        return ResponseEntity.ok("Removed Successfully");
    }
}
