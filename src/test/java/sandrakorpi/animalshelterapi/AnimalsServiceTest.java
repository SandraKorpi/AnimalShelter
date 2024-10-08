package sandrakorpi.animalshelterapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sandrakorpi.animalshelterapi.Dtos.AnimalsDto;
import sandrakorpi.animalshelterapi.Models.Animals;
import sandrakorpi.animalshelterapi.Repositories.AnimalsRepository;
import sandrakorpi.animalshelterapi.Services.AnimalsService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AnimalsServiceTest {
    @Mock
    private AnimalsRepository animalsRepository;

    @InjectMocks
    private AnimalsService animalsService;

    private Animals animal1;
    private Animals animal2;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        animal1 = Animals.builder()
                .id(1L)
                .name("Odie")
                .animalType("dog")
                .breed("Labrador")
                .age(3)
                .build();

        animal2 = Animals.builder()
                .id(2L)
                .name("Maja")
                .animalType("cat")
                .breed("Farm cat")
                .age(8)
                .build();
    }

    @Test
    void testGetAllAnimals() {
        when (animalsRepository.findAll()).thenReturn(Arrays.asList(animal1, animal2));

        List <AnimalsDto> result = animalsService.getAllAnimals();

        assertEquals(2, result.size());
        assertEquals("Odie", result.get(0).getName());
        assertEquals("Maja", result.get(1).getName());

        verify(animalsRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCats() {
        when (animalsRepository.findAll()).thenReturn(Arrays.asList(animal1, animal2));

        List <AnimalsDto> result = animalsService.getAllCats();

        assertEquals(1, result.size());
        assertEquals("Maja", result.get(0).getName());

        verify(animalsRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDogs() {
        when (animalsRepository.findAll()).thenReturn(Arrays.asList(animal1, animal2));

        List <AnimalsDto> result = animalsService.getAllDogs();

        assertEquals(1, result.size());
        assertEquals("Odie", result.get(0).getName());

        verify(animalsRepository, times(1)).findAll();
    }

    @Test
    void testGetOneAnimal() {
        when(animalsRepository.findById(1L)).thenReturn(Optional.of(animal1));
        Optional<AnimalsDto> result = animalsService.getOneAnimal(1L);
        assertTrue(result.isPresent());

        AnimalsDto animalDto = result.get();
        assertEquals("Odie", animalDto.getName());
        assertEquals("dog", animalDto.getAnimalType());
        assertEquals("Labrador", animalDto.getBreed());
        assertEquals(3, animalDto.getAge());

        verify(animalsRepository, times (1)).findById(1L);
    }

    @Test
    void testCreateAnimal(){
        AnimalsDto animalDto = new AnimalsDto();
        animalDto.setId(animal1.getId());
        animalDto.setName(animal1.getName());
        animalDto.setAnimalType(animal1.getAnimalType());
        animalDto.setBreed(animal1.getBreed());
        animalDto.setAge(animal1.getAge());

        when(animalsRepository.save(any(Animals.class))).thenReturn(animal1);

        AnimalsDto result = animalsService.createAnimal(animalDto);

        assertNotNull(result);
        assertEquals("Odie", result.getName());
        assertEquals("dog", result.getAnimalType());
        assertEquals("Labrador", result.getBreed());
        assertEquals(3, result.getAge());

        verify(animalsRepository, times(1)).save(any(Animals.class));
    }
    @Test
    void testUpdateOneAnimal(){
        when(animalsRepository.findById(1L)).thenReturn(Optional.of(animal1));

        AnimalsDto updatedAnimalDto = new AnimalsDto();
        updatedAnimalDto.setName("Odie Updated");
        updatedAnimalDto.setAnimalType("dog");
        updatedAnimalDto.setBreed("Labrador Updated");
        updatedAnimalDto.setAge(4);//Uppdaterar ålder

        when(animalsRepository.save(any(Animals.class))).thenAnswer(invocation -> {
            Animals updatedAnimal = invocation.getArgument(0);
            updatedAnimal.setId(animal1.getId());
            return updatedAnimal;
        });
        Optional<AnimalsDto> result = animalsService.updateOneAnimal(1L, updatedAnimalDto);

        assertTrue(result.isPresent());
        AnimalsDto updatedResult = result.get();
        assertEquals("Odie Updated", updatedResult.getName());
        assertEquals("dog", updatedResult.getAnimalType());
        assertEquals("Labrador Updated", updatedResult.getBreed());
        assertEquals(4, updatedResult.getAge());

        verify(animalsRepository, times(1)).findById(1L);
        verify(animalsRepository, times(1)).save(any(Animals.class));
    }

    @Test
    void testDeleteAnimal(){
        when(animalsRepository.findById(1L)).thenReturn(Optional.of(animal1));
        when(animalsRepository.existsById(1L)).thenReturn(true);

        boolean result = animalsService.deleteAnimal((1L));

        assertTrue(result);
        verify(animalsRepository, times(1)).deleteById(1L);
    }

}
