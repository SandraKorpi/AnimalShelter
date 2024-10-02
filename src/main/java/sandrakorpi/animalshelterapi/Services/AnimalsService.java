package sandrakorpi.animalshelterapi.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Models.Animals;
import sandrakorpi.animalshelterapi.Repositories.AnimalsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalsService {


    private final AnimalsRepository animalsRepository;

    public List<Animals> getAllAnimals() {
        return animalsRepository.findAll();
    }

    public List<Animals> getAllCats() {
        return animalsRepository.findAll().stream()
                .filter(animal -> "cat".equalsIgnoreCase(animal.getAnimalType()))
                .collect(Collectors.toList());
    }

    public List<Animals> getAllDogs() {
        return animalsRepository.findAll().stream()
                .filter(animal -> "dog".equalsIgnoreCase(animal.getAnimalType()))
                .collect(Collectors.toList());
    }

    public Optional<Animals> getOneAnimal(long id) {
        return animalsRepository.findById(id);
    }

    public Animals saveAnimal(Animals animal){
        return animalsRepository.save(animal);
    }

    public Animals patchAnimal(Animals animal, Long id){
        Optional <Animals> currentAnimal = animalsRepository.findById(animal.getId());
        if (!animal.getName().equals(currentAnimal.get().getName())) currentAnimal.get().setName(animal.getName());
        return animalsRepository.save(currentAnimal.get());
    }

    public void deleteAnimal(Long id){
        animalsRepository.deleteById(id);
    }
}
