package sandrakorpi.animalshelterapi.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Dtos.AnimalsDto;
import sandrakorpi.animalshelterapi.Models.Animals;
import sandrakorpi.animalshelterapi.Repositories.AnimalsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalsService {

    private final AnimalsRepository animalsRepository;

    //Konverterar från entitet till dto
    private AnimalsDto convertToDto(Animals animal) {
        return new AnimalsDto(
                animal.getId(),
                animal.getName(),
                animal.getAnimalType(),
                animal.getBreed(),
                animal.getAge()
        );
    }

    //Konverterar från dto till entitet.
    private Animals convertToEntity(AnimalsDto animalsDto) {
        Animals animal = new Animals();
        animal.setId(animalsDto.getId());
        animal.setName(animalsDto.getName());
        animal.setAnimalType(animalsDto.getAnimalType());
        animal.setBreed(animalsDto.getBreed());
        animal.setAge(animalsDto.getAge());
        return animal;
    }

    public List<AnimalsDto> getAllAnimals() {
        return animalsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AnimalsDto> getAllCats() {
        return animalsRepository.findAll().stream()
                .filter(animal -> "cat".equalsIgnoreCase(animal.getAnimalType()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AnimalsDto> getAllDogs() {
        return animalsRepository.findAll().stream()
                .filter(animal -> "dog".equalsIgnoreCase(animal.getAnimalType()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<AnimalsDto> getOneAnimal(long id) {
        return animalsRepository.findById(id)
                .map(this::convertToDto);
    }

    public AnimalsDto createAnimal(AnimalsDto animalDto) {
        Animals animal = convertToEntity(animalDto);
        Animals savedAnimal = animalsRepository.save(animal);
        return convertToDto(savedAnimal);
    }

    public Optional<AnimalsDto> updateOneAnimal(Long id, AnimalsDto animalDto) {
        return animalsRepository.findById(id).map(existingAnimal -> {

            if (animalDto.getName() != null) existingAnimal.setName(animalDto.getName());
            if (animalDto.getAnimalType() != null) existingAnimal.setAnimalType(animalDto.getAnimalType());
            if (animalDto.getBreed() != null) existingAnimal.setBreed(animalDto.getBreed());
            if (animalDto.getAge() > 0) existingAnimal.setAge(animalDto.getAge());

            return convertToDto(animalsRepository.save(existingAnimal));
        });
    }

    public boolean deleteAnimal(Long id) {
        if (animalsRepository.existsById(id)) {
            animalsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
