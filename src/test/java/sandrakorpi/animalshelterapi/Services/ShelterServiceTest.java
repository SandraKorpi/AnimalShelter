package sandrakorpi.animalshelterapi.Services;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sandrakorpi.animalshelterapi.Dtos.ShelterDto;
import sandrakorpi.animalshelterapi.Models.Shelter;
import sandrakorpi.animalshelterapi.Repositories.ShelterRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


class ShelterServiceTest {

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private ShelterService shelterService;

    private ShelterDto shelterDto;
    private Shelter shelter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ShelterDto shelterDto = new ShelterDto(1L, "Test Shelter", "Test Address", "123456789", "Hej@Test.com", "10");
        shelter = new Shelter();
        shelter.setId(shelterDto.getId());
        shelter.setName(shelterDto.getName());
        shelter.setAddress(shelterDto.getAddress());
        shelter.setPhone(shelterDto.getPhone());
        shelter.setEmail(shelterDto.getEmail());
        shelter.setAvailableBeds(shelterDto.getAvailableBeds());
    }

    @Test
    public void testCreateNewShelter() {
        // Arrange
        long id = 1;
        ShelterDto shelterDto = new ShelterDto(id, "Test Shelter", "Test Address", "123456789", "Hej@Test.com", "10");

        // Skapa en Shelter-instans
        Shelter shelter = new Shelter();
        shelter.setId(id); // Sätt id
        shelter.setName(shelterDto.getName());
        shelter.setAddress(shelterDto.getAddress());
        shelter.setPhone(shelterDto.getPhone());
        shelter.setEmail(shelterDto.getEmail());
        shelter.setAvailableBeds(shelterDto.getAvailableBeds());

        // Mocka repository-funktionen
        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter); // Använda 'Shelter' som typ

        // Act
        ShelterDto createdShelter = shelterService.createNewShelter(shelterDto);

        // Assert
        assertNotNull(createdShelter);
        assertEquals(shelterDto.getName(), createdShelter.getName());
        assertEquals(shelterDto.getAddress(), createdShelter.getAddress());
        assertEquals(shelterDto.getPhone(), createdShelter.getPhone());
        assertEquals(shelterDto.getEmail(), createdShelter.getEmail());
        assertEquals(shelterDto.getAvailableBeds(), createdShelter.getAvailableBeds());

        // Verifiera att save-metoden kallades exakt en gång
        verify(shelterRepository, times(1)).save(any(Shelter.class));
    }

    @Test
    public void testGetAllShelters() {
        // Arrange: Skapa en lista med Shelter-objekt
        Shelter shelter1 = new Shelter(1L, "Test Shelter 1", "Address 1", "123456789", "email1@test.com", "5", null);
        Shelter shelter2 = new Shelter(2L, "Test Shelter 2", "Address 2", "987654321", "email2@test.com", "7", null);
        List<Shelter> shelters = Arrays.asList(shelter1, shelter2); // Använd Shelter-instansobjekt istället för Shelter-klass

        // Mocka repository-funktionen
        when(shelterRepository.findAll()).thenReturn(shelters);

        // Act: Anropa metoden som ska testas
        List<Shelter> result = shelterService.getAllShelters();

        // Assert: Kontrollera att resultatet inte är null och har rätt storlek
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Shelter 1", result.get(0).getName());
        assertEquals("Test Shelter 2", result.get(1).getName());

        // Verifiera att findAll() i repository anropades exakt en gång
        verify(shelterRepository, times(1)).findAll();
    }
    @Test
    public void testGetShelterByName() {
        // Arrange - Mocka repository-funktionen för att returnera shelter
        when(shelterRepository.findByName("Test Shelter")).thenReturn(Optional.of(shelter));

        // Act - Anropa service-metoden
        Optional<Shelter> result = shelterService.getShelterByName("Test Shelter");

        // Assert - Kontrollera att resultatet är som förväntat
        assertTrue(result.isPresent());
        assertEquals("Test Shelter", result.get().getName());

        // Verifiera att findByName anropades exakt en gång
        verify(shelterRepository, times(1)).findByName("Test Shelter");
    }

    @Test
    public void testGetOneShelter() {
        // Mocka repository-funktionen
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        // Act
        Optional<Shelter> result = shelterService.getOneShelter(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());

        // Verifiera att findById() anropades exakt en gång
        verify(shelterRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateOneShelter() {
        // Arrange
        long id = 1L;

        // Mocka repository-funktionen för att returnera en befintlig Shelter
        when(shelterRepository.findById(id)).thenReturn(Optional.of(shelter));

        // Mocka save-funktionen för att returnera den uppdaterade Shelter
        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);

        // Skapa en ShelterDto med nya värden
        ShelterDto updatedShelterDto = new ShelterDto(1L, "Updated Shelter", "Updated Address", "987654321", "updated@test.com", "15");

        // Act
        Optional<ShelterDto> updatedShelter = shelterService.updateOneShelter(id, updatedShelterDto);

        // Assert
        assertTrue(updatedShelter.isPresent());
        assertEquals(updatedShelterDto.getName(), updatedShelter.get().getName());
        assertEquals(updatedShelterDto.getAddress(), updatedShelter.get().getAddress());
        assertEquals(updatedShelterDto.getPhone(), updatedShelter.get().getPhone());
        assertEquals(updatedShelterDto.getEmail(), updatedShelter.get().getEmail());
        assertEquals(updatedShelterDto.getAvailableBeds(), updatedShelter.get().getAvailableBeds());

        // Verifiera att findById() anropades exakt en gång
        verify(shelterRepository, times(1)).findById(id);

        // Verifiera att save-metoden kallades exakt en gång
        verify(shelterRepository, times(1)).save(any(Shelter.class));
    }

    @Test
    public void testUpdateAvailableBeds() {
        // Mocka repository-funktionen
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        // Act
        boolean result = shelterService.updateAvailableBeds(1L, "15");

        // Assert
        assertTrue(result);
        assertEquals("15", shelter.getAvailableBeds());

        // Verifiera att findById() och save() anropades exakt en gång
        verify(shelterRepository, times(1)).findById(1L);
        verify(shelterRepository, times(1)).save(shelter);
    }
    @Test
    public void testDeleteOneShelter() {
        // Given
        when(shelterRepository.existsById(1L)).thenReturn(true);

        // When
        boolean deleted = shelterService.deleteOneShelter(1L);

        // Then
        assertTrue(deleted);
        verify(shelterRepository, times(1)).existsById(1L);
        verify(shelterRepository, times(1)).deleteById(1L);
    }



}